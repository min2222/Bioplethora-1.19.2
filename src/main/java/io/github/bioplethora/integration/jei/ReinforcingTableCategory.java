package io.github.bioplethora.integration.jei;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.gui.recipe.ReinforcingRecipe;
import io.github.bioplethora.registry.BPBlocks;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class ReinforcingTableCategory implements IRecipeCategory<ReinforcingRecipe> {

    public static final mezz.jei.api.recipe.RecipeType<ReinforcingRecipe> CATEGORY_ID = mezz.jei.api.recipe.RecipeType.create(Bioplethora.MOD_ID, "reinforcing", ReinforcingRecipe.class);
    private IDrawable categoryIcon;
    private IDrawable categoryBackground;

    public ReinforcingTableCategory(IGuiHelper helper) {
        categoryIcon = helper.createDrawableItemStack(new ItemStack(BPBlocks.REINFORCING_TABLE.get()));
        categoryBackground = helper.createDrawable(new ResourceLocation(Bioplethora.MOD_ID, "textures/gui/container/jei/reinforcing_table.png"), 0, 0, 176, 80);
    }

    @Override
    public Component getTitle() {
        return Component.literal("Reinforce");
    }

    @Override
    public IDrawable getIcon() {
        return categoryIcon;
    }

    @Override
    public IDrawable getBackground() {
        return categoryBackground;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder recipeLayout, ReinforcingRecipe recipe, IFocusGroup ingredients) {
        recipeLayout.addSlot(RecipeIngredientRole.INPUT, 44, 18)
        .addIngredients(recipe.topIngredient);

        recipeLayout.addSlot(RecipeIngredientRole.INPUT, 44, 37)
        .addIngredients(recipe.midIngredient);

        recipeLayout.addSlot(RecipeIngredientRole.INPUT, 44, 55)
        .addIngredients(recipe.botIngredient);
        
        recipeLayout.addSlot(RecipeIngredientRole.OUTPUT, 127, 37)
        .addItemStack(recipe.getResult());
    }

	@Override
	public RecipeType<ReinforcingRecipe> getRecipeType() {
		return CATEGORY_ID;
	}
}
