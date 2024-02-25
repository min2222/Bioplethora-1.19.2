package io.github.bioplethora.gui.recipe;

import java.util.function.Consumer;

import javax.annotation.Nullable;

import com.google.gson.JsonObject;

import io.github.bioplethora.registry.BPRecipes;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.ForgeRegistries;

public class ReinforcingRecipeBuilder {

    private final Ingredient topIngredient, midIngredient, botIngredient;
    private final Item result;
    private final Advancement.Builder advancement = Advancement.Builder.advancement();
    private final RecipeSerializer<?> type;

    public ReinforcingRecipeBuilder(RecipeSerializer<?> pType, Ingredient topIngredient, Ingredient midIngredient, Ingredient botIngredient, Item pResult) {
        this.type = pType;
        this.topIngredient = topIngredient;
        this.midIngredient = midIngredient;
        this.botIngredient = botIngredient;
        this.result = pResult;
    }

    public static ReinforcingRecipeBuilder reinforcing(Ingredient topIngredient, Ingredient midIngredient, Ingredient botIngredient, Item pResult) {
        return new ReinforcingRecipeBuilder(BPRecipes.REINFORCING.get(), topIngredient, midIngredient, botIngredient, pResult);
    }

    public ReinforcingRecipeBuilder unlocks(String pName, CriterionTriggerInstance pCriterion) {
        this.advancement.addCriterion(pName, pCriterion);
        return this;
    }

    public void save(Consumer<FinishedRecipe> pFinishedRecipeConsumer, String pId) {
        this.save(pFinishedRecipeConsumer, new ResourceLocation(pId));
    }

    public void save(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ResourceLocation pId) {
        this.ensureValid(pId);
        this.advancement.parent(new ResourceLocation("recipes/root")).addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(pId)).rewards(AdvancementRewards.Builder.recipe(pId)).requirements(RequirementsStrategy.OR);
        pFinishedRecipeConsumer.accept(new ReinforcingRecipeBuilder.Result(pId, this.type, this.topIngredient, this.midIngredient, this.botIngredient, this.result, this.advancement, new ResourceLocation(pId.getNamespace(), "recipes/" + this.result.getItemCategory().getRecipeFolderName() + "/" + pId.getPath())));
    }

    private void ensureValid(ResourceLocation pId) {
        if (this.advancement.getCriteria().isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + pId);
        }
    }

    public static class Result implements FinishedRecipe {
        private final ResourceLocation id;
        private final Ingredient topIngredient, midIngredient, botIngredient;
        private final Item result;
        private final Advancement.Builder advancement;
        private final ResourceLocation advancementId;
        private final RecipeSerializer<?> type;

        public Result(ResourceLocation pId, RecipeSerializer<?> pType, Ingredient topIngredient, Ingredient midIngredient, Ingredient botIngredient, Item pResult, Advancement.Builder pAdvancement, ResourceLocation pAdvancementId) {
            this.id = pId;
            this.type = pType;
            this.topIngredient = topIngredient;
            this.midIngredient = midIngredient;
            this.botIngredient = botIngredient;
            this.result = pResult;
            this.advancement = pAdvancement;
            this.advancementId = pAdvancementId;
        }

        public void serializeRecipeData(JsonObject pJson) {
            pJson.add("core", this.topIngredient.toJson());
            pJson.add("material", this.midIngredient.toJson());
            pJson.add("weapon", this.botIngredient.toJson());
            JsonObject jsonobject = new JsonObject();
            jsonobject.addProperty("item", ForgeRegistries.ITEMS.getKey(this.result).toString());
            pJson.add("result", jsonobject);
        }

        /**
         * Gets the ID for the recipe.
         */
        public ResourceLocation getId() {
            return this.id;
        }

        public RecipeSerializer<?> getType() {
            return this.type;
        }

        /**
         * Gets the JSON for the advancement that unlocks this recipe. Null if there is no advancement.
         */
        @Nullable
        public JsonObject serializeAdvancement() {
            return this.advancement.serializeToJson();
        }

        @Nullable
        public ResourceLocation getAdvancementId() {
            return this.advancementId;
        }
    }
}
