package io.github.bioplethora.integration.jei;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.gui.recipe.ReinforcingRecipe;
import io.github.bioplethora.registry.BPBlocks;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.IRecipeSlotBuilder;
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
        categoryIcon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(BPBlocks.REINFORCING_TABLE.get()));
        categoryBackground = helper.drawableBuilder(new ResourceLocation(Bioplethora.MOD_ID, "textures/gui/container/jei/reinforcing_table.png"),
                0, 0, 170, 80).setTextureSize(170, 80).build();
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
    	IRecipeSlotBuilder intputSlot = recipeLayout.addSlot(RecipeIngredientRole.INPUT, 43, 13);
    	IRecipeSlotBuilder intputSlot2 = recipeLayout.addSlot(RecipeIngredientRole.INPUT, 43, 33);
    	IRecipeSlotBuilder intputSlot3 = recipeLayout.addSlot(RecipeIngredientRole.INPUT, 43, 52);
    	IRecipeSlotBuilder outputSlot = recipeLayout.addSlot(RecipeIngredientRole.OUTPUT, 126, 33);
    	//TODO
    	/*intputSlot.addIngredient(VanillaTypes.ITEM_STACK, recipe.getIngredients().get(0).getItems()[0]);
    	intputSlot2.addIngredient(VanillaTypes.ITEM_STACK, recipe.getIngredients().get(1).getItems()[0]);
    	intputSlot3.addIngredient(VanillaTypes.ITEM_STACK, recipe.getIngredients().get(2).getItems()[0]);*/
        outputSlot.addIngredient(VanillaTypes.ITEM_STACK, recipe.getResult());
    }

	@Override
	public RecipeType<ReinforcingRecipe> getRecipeType() {
		return CATEGORY_ID;
	}
}
