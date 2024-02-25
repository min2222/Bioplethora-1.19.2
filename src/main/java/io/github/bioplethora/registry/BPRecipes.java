package io.github.bioplethora.registry;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.gui.recipe.ReinforcingRecipe;
import io.github.bioplethora.gui.recipe.ReinforcingRecipe.ReinforcingRecipeType;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BPRecipes {
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, Bioplethora.MOD_ID);
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Bioplethora.MOD_ID);

    public static final RegistryObject<ReinforcingRecipeType> REINFORCING_TYPE = RECIPE_TYPES.register("reinforcing", () -> new ReinforcingRecipe.ReinforcingRecipeType());
    public static final RegistryObject<RecipeSerializer<ReinforcingRecipe>> REINFORCING = RECIPE_SERIALIZERS.register("reinforcing", ReinforcingRecipe.Serializer::new);
}
