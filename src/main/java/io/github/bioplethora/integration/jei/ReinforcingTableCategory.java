package io.github.bioplethora.integration.jei;

//TODO
/*public class ReinforcingTableCategory implements IRecipeCategory<ReinforcingRecipe> {

    public static final ResourceLocation CATEGORY_ID = new ResourceLocation(BPRecipes.REINFORCING.toString());
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

        IGuiItemStackGroup recipeStacks = recipeLayout.getItemStacks();

        recipeStacks.init(0, true, 43, 13);
        recipeStacks.init(1, true, 43, 33);
        recipeStacks.init(2, true, 43, 52);

        recipeStacks.init(3, false, 126, 33);
        recipeStacks.set(3, recipe.getResult());

        List<List<ItemStack>> inputs = recipeLayout.getInputs(VanillaTypes.ITEM_STACK);
        List<ItemStack> input;
        for (int i = 0; i <= 2; i++) {
            input = inputs.get(i);
            if (input != null && !input.isEmpty()) {
                recipeStacks.set(i, input);
            }
        }
    }

	@Override
	public RecipeType<ReinforcingRecipe> getRecipeType() {
		return null;
	}
}*/
