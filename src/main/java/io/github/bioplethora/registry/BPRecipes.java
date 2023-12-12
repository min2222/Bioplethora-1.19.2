package io.github.bioplethora.registry;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.gui.recipe.ReinforcingRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BPRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Bioplethora.MOD_ID);

    public static final RecipeType<ReinforcingRecipe> REINFORCING = new ReinforcingRecipe.ReinforcingRecipeType();
    //public static final IRecipeType<ReinforcingTableRecipe> REINFORCING = IRecipeType.register(Bioplethora.MOD_ID + "_reinforcing");

    public static final RegistryObject<RecipeSerializer<?>> REINFORCING_SERIALIZER =
            RECIPE_SERIALIZERS.register("reinforcing", ReinforcingRecipe.Serializer::new);

    public static void registerRecipes(IEventBus eventBus) {
        RECIPE_SERIALIZERS.register(eventBus);
        //TODO not sure this is right
        ForgeRegistries.RECIPE_TYPES.register(Bioplethora.MOD_ID + "_reinforcing", REINFORCING);
    }
}
