package io.github.bioplethora.gui.recipe;

import javax.annotation.Nullable;

import com.google.gson.JsonObject;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.registry.BPBlocks;
import io.github.bioplethora.registry.BPRecipes;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.level.Level;

public class ReinforcingRecipe implements Recipe<Container> {

    protected final ResourceLocation recipeId;
    public final Ingredient topIngredient, midIngredient, botIngredient;
    protected final ItemStack resultItem;

    public ReinforcingRecipe(ResourceLocation recipeId, Ingredient topIngredient, Ingredient midIngredient, Ingredient botIngredient,
                             ItemStack resultItem) {
        this.recipeId = recipeId;
        this.topIngredient = topIngredient;
        this.midIngredient = midIngredient;
        this.botIngredient = botIngredient;
        this.resultItem = resultItem;
    }

    @Override
    public boolean matches(Container inv, Level world) {
        return this.topIngredient.test(inv.getItem(0))
                && this.midIngredient.test(inv.getItem(1))
                && this.botIngredient.test(inv.getItem(2));
    }

    @Override
    public ItemStack assemble(Container pInv, RegistryAccess access) {
        ItemStack itemstack = this.resultItem.copy();
        CompoundTag compoundnbt = pInv.getItem(2).getTag();
        if (compoundnbt != null) {
            itemstack.setTag(compoundnbt.copy());
        }

        return itemstack;
    }

    public ItemStack[] addIngredient(int slotToPlace) {

        NonNullList<Ingredient> totalIngredients = NonNullList.create();
        totalIngredients.add(topIngredient);
        totalIngredients.add(midIngredient);
        totalIngredients.add(botIngredient);

        Ingredient ing = totalIngredients.get(slotToPlace);
        return ing.getItems();
    }

    public ItemStack getIcon() {
        return new ItemStack(BPBlocks.REINFORCING_TABLE.get());
    }

    public ItemStack getResult() {
        return resultItem;
    }

    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return pWidth * pHeight >= 2;
    }

    public ItemStack getResultItem(RegistryAccess access) {
        return this.resultItem;
    }

    public ItemStack getToastSymbol() {
        return new ItemStack(BPBlocks.REINFORCING_TABLE.get());
    }

    @Override
    public ResourceLocation getId() {
        return recipeId;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return BPRecipes.REINFORCING.get();
    }

    public RecipeType<?> getType() {
        return BPRecipes.REINFORCING_TYPE.get();
    }

    public static class ReinforcingRecipeType implements RecipeType<ReinforcingRecipe> {

        @Override
        public String toString () {
            return new ResourceLocation(Bioplethora.MOD_ID, "reinforcing").toString();
        }
    }

    public static class Serializer implements RecipeSerializer<ReinforcingRecipe> {

    	@Override
        public ReinforcingRecipe fromJson(ResourceLocation recipeId, JsonObject json) {

            Ingredient core = Ingredient.fromJson(GsonHelper.getAsJsonObject(json, "core"));
            Ingredient material = Ingredient.fromJson(GsonHelper.getAsJsonObject(json, "material"));
            Ingredient weapon = Ingredient.fromJson(GsonHelper.getAsJsonObject(json, "weapon"));

            Item result = ShapedRecipe.itemFromJson(GsonHelper.getAsJsonObject(json, "result"));

            return new ReinforcingRecipe(recipeId, core, material, weapon, new ItemStack(result));
        }

        @Nullable
        @Override
        public ReinforcingRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            Ingredient core = Ingredient.fromNetwork(buffer);
            Ingredient material = Ingredient.fromNetwork(buffer);
            Ingredient weapon = Ingredient.fromNetwork(buffer);

            ItemStack result = buffer.readItem();

            return new ReinforcingRecipe(recipeId, core, material, weapon, result);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, ReinforcingRecipe recipe) {
            buffer.writeInt(recipe.getIngredients().size());
            for (Ingredient ing : recipe.getIngredients()) {
                ing.toNetwork(buffer);
            }
            buffer.writeItemStack(recipe.getResult(), false);
        }
    }
}
