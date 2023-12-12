package io.github.bioplethora.data;

import java.util.function.Consumer;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.gui.recipe.ReinforcingRecipeBuilder;
import io.github.bioplethora.registry.BPBlocks;
import io.github.bioplethora.registry.BPItems;
import io.github.bioplethora.registry.BPTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.data.recipes.SingleItemRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.ForgeRegistries;

public class BioRecipeProvider extends RecipeProvider {

    public BioRecipeProvider(DataGenerator gen) {
        super(gen);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {

        /** @Param consumer, providedItem, requiredItem **/
        shortsword(consumer, BPItems.NANDBRIC_SHORTSWORD.get(), BPItems.NANDBRI_SCALES.get(), BPItems.NANDBRI_FANG.get());

        ingotToBlock(consumer, BPBlocks.BELLOPHITE_BLOCK.get(), BPItems.BELLOPHITE.get());
        blockToIngot(consumer, BPItems.BELLOPHITE.get(), BPBlocks.BELLOPHITE_BLOCK.get());
        ingotToBlock(consumer, BPBlocks.NANDBRI_SCALE_BLOCK.get(), BPItems.NANDBRI_SCALES.get());
        blockToIngot(consumer, BPItems.NANDBRI_SCALES.get(), BPBlocks.NANDBRI_SCALE_BLOCK.get());

        ingotToBlock(consumer, BPBlocks.GREEN_GRYLYNEN_CRYSTAL_BLOCK.get(), BPItems.GREEN_GRYLYNEN_CRYSTAL.get());
        blockToIngot(consumer, BPItems.GREEN_GRYLYNEN_CRYSTAL.get(), BPBlocks.GREEN_GRYLYNEN_CRYSTAL_BLOCK.get());
        ingotToBlock(consumer, BPBlocks.YELLOW_GRYLYNEN_CRYSTAL_BLOCK.get(), BPItems.YELLOW_GRYLYNEN_CRYSTAL.get());
        blockToIngot(consumer, BPItems.YELLOW_GRYLYNEN_CRYSTAL.get(), BPBlocks.YELLOW_GRYLYNEN_CRYSTAL_BLOCK.get());
        ingotToBlock(consumer, BPBlocks.RED_GRYLYNEN_CRYSTAL_BLOCK.get(), BPItems.RED_GRYLYNEN_CRYSTAL.get());
        blockToIngot(consumer, BPItems.RED_GRYLYNEN_CRYSTAL.get(), BPBlocks.RED_GRYLYNEN_CRYSTAL_BLOCK.get());

        foodCooking(consumer, BPItems.RAW_CUTTLEFISH_MEAT.get(), BPItems.COOKED_CUTTLEFISH_MEAT.get(), 0.30F, 200);
        foodCooking(consumer, BPItems.RAW_FLENTAIR.get(), BPItems.COOKED_FLENTAIR.get(), 0.40F, 300);
        foodCooking(consumer, BPItems.RAW_MOSILE.get(), BPItems.COOKED_MOSILE.get(), 0.30F, 200);
        foodCooking(consumer, BPItems.RAW_JAWFLESH.get(), BPItems.COOKED_JAWFLESH.get(), 0.40F, 300);

        // TOOLS
        toolSetHelper(consumer,  BPItems.FLEIGNARITE_SCALES.get(), BPItems.FLEIGNARITE_SCALES.get(),
                BPItems.FLEIGNARITE_SWORD.get(), BPItems.FLEIGNARITE_SHOVEL.get(),
                BPItems.FLEIGNARITE_PICKAXE.get(), BPItems.FLEIGNARITE_AXE.get(), BPItems.FLEIGNARITE_HOE.get());

        reinforcing(consumer, BPItems.REINFORCED_FLEIGNARITE_SWORD.get(), Items.NETHERITE_INGOT, BPItems.BELLOPHITE.get(), BPItems.FLEIGNARITE_SWORD.get(), 0);
        reinforcing(consumer, BPItems.REINFORCED_FLEIGNARITE_SHOVEL.get(), Items.NETHERITE_INGOT, BPItems.BELLOPHITE.get(), BPItems.FLEIGNARITE_SHOVEL.get(), 0);
        reinforcing(consumer, BPItems.REINFORCED_FLEIGNARITE_PICKAXE.get(), Items.NETHERITE_INGOT, BPItems.BELLOPHITE.get(), BPItems.FLEIGNARITE_PICKAXE.get(), 0);
        reinforcing(consumer, BPItems.REINFORCED_FLEIGNARITE_AXE.get(), Items.NETHERITE_INGOT, BPItems.BELLOPHITE.get(), BPItems.FLEIGNARITE_AXE.get(), 0);
        reinforcing(consumer, BPItems.REINFORCED_FLEIGNARITE_HOE.get(), Items.NETHERITE_INGOT, BPItems.BELLOPHITE.get(), BPItems.FLEIGNARITE_HOE.get(), 0);

        reinforcing(consumer, BPItems.REINFORCED_FLEIGNARITE_SWORD.get(), Items.NETHERITE_SWORD, BPItems.BELLOPHITE.get(), BPItems.FLEIGNARITE_SWORD.get(), 1);
        reinforcing(consumer, BPItems.REINFORCED_FLEIGNARITE_SHOVEL.get(), Items.NETHERITE_SHOVEL, BPItems.BELLOPHITE.get(), BPItems.FLEIGNARITE_SHOVEL.get(), 1);
        reinforcing(consumer, BPItems.REINFORCED_FLEIGNARITE_PICKAXE.get(), Items.NETHERITE_PICKAXE, BPItems.BELLOPHITE.get(), BPItems.FLEIGNARITE_PICKAXE.get(), 1);
        reinforcing(consumer, BPItems.REINFORCED_FLEIGNARITE_AXE.get(), Items.NETHERITE_AXE, BPItems.BELLOPHITE.get(), BPItems.FLEIGNARITE_AXE.get(), 1);
        reinforcing(consumer, BPItems.REINFORCED_FLEIGNARITE_HOE.get(), Items.NETHERITE_HOE, BPItems.BELLOPHITE.get(), BPItems.FLEIGNARITE_HOE.get(), 1);

        reinforcing(consumer, BPItems.REINFORCED_FLEIGNARITE_SWORD.get(), BPItems.FLEIGNARITE_SWORD.get(), BPItems.BELLOPHITE.get(), Items.NETHERITE_SWORD, 2);
        reinforcing(consumer, BPItems.REINFORCED_FLEIGNARITE_SHOVEL.get(), BPItems.FLEIGNARITE_SHOVEL.get(), BPItems.BELLOPHITE.get(), Items.NETHERITE_SHOVEL, 2);
        reinforcing(consumer, BPItems.REINFORCED_FLEIGNARITE_PICKAXE.get(), BPItems.FLEIGNARITE_PICKAXE.get(), BPItems.BELLOPHITE.get(), Items.NETHERITE_PICKAXE, 2);
        reinforcing(consumer, BPItems.REINFORCED_FLEIGNARITE_AXE.get(), BPItems.FLEIGNARITE_AXE.get(), BPItems.BELLOPHITE.get(), Items.NETHERITE_AXE, 2);
        reinforcing(consumer, BPItems.REINFORCED_FLEIGNARITE_HOE.get(), BPItems.FLEIGNARITE_HOE.get(), BPItems.BELLOPHITE.get(), Items.NETHERITE_HOE, 2);

        // ARMOR
        armorSetHelper(consumer,  BPItems.FLEIGNARITE_SCALES.get(),
                BPItems.FLEIGNARITE_HELMET.get(), BPItems.FLEIGNARITE_CHESTPLATE.get(),
                BPItems.FLEIGNARITE_LEGGINGS.get(), BPItems.FLEIGNARITE_BOOTS.get());
        armorSetHelper(consumer,  BPItems.NANDBRI_SCALES.get(),
                BPItems.NANDBRIC_HELMET.get(), BPItems.NANDBRIC_CHESTPLATE.get(),
                BPItems.NANDBRIC_LEGGINGS.get(), BPItems.NANDBRIC_BOOTS.get());

        reinforcing(consumer, BPItems.REINFORCED_FLEIGNARITE_HELMET.get(), Items.NETHERITE_INGOT, BPItems.BELLOPHITE.get(), BPItems.FLEIGNARITE_HELMET.get(), 0);
        reinforcing(consumer, BPItems.REINFORCED_FLEIGNARITE_CHESTPLATE.get(), Items.NETHERITE_INGOT, BPItems.BELLOPHITE.get(), BPItems.FLEIGNARITE_CHESTPLATE.get(), 0);
        reinforcing(consumer, BPItems.REINFORCED_FLEIGNARITE_LEGGINGS.get(), Items.NETHERITE_INGOT, BPItems.BELLOPHITE.get(), BPItems.FLEIGNARITE_LEGGINGS.get(), 0);
        reinforcing(consumer, BPItems.REINFORCED_FLEIGNARITE_BOOTS.get(), Items.NETHERITE_INGOT, BPItems.BELLOPHITE.get(), BPItems.FLEIGNARITE_BOOTS.get(), 0);

        reinforcing(consumer, BPItems.REINFORCED_FLEIGNARITE_HELMET.get(), Items.NETHERITE_HELMET, BPItems.BELLOPHITE.get(), BPItems.FLEIGNARITE_HELMET.get(), 1);
        reinforcing(consumer, BPItems.REINFORCED_FLEIGNARITE_CHESTPLATE.get(), Items.NETHERITE_CHESTPLATE, BPItems.BELLOPHITE.get(), BPItems.FLEIGNARITE_CHESTPLATE.get(), 1);
        reinforcing(consumer, BPItems.REINFORCED_FLEIGNARITE_LEGGINGS.get(), Items.NETHERITE_LEGGINGS, BPItems.BELLOPHITE.get(), BPItems.FLEIGNARITE_LEGGINGS.get(), 1);
        reinforcing(consumer, BPItems.REINFORCED_FLEIGNARITE_BOOTS.get(), Items.NETHERITE_BOOTS, BPItems.BELLOPHITE.get(), BPItems.FLEIGNARITE_BOOTS.get(), 1);

        reinforcing(consumer, BPItems.REINFORCED_FLEIGNARITE_HELMET.get(), BPItems.FLEIGNARITE_HELMET.get(), BPItems.BELLOPHITE.get(), Items.NETHERITE_HELMET, 2);
        reinforcing(consumer, BPItems.REINFORCED_FLEIGNARITE_CHESTPLATE.get(), BPItems.FLEIGNARITE_CHESTPLATE.get(), BPItems.BELLOPHITE.get(), Items.NETHERITE_CHESTPLATE, 2);
        reinforcing(consumer, BPItems.REINFORCED_FLEIGNARITE_LEGGINGS.get(), BPItems.FLEIGNARITE_LEGGINGS.get(), BPItems.BELLOPHITE.get(), Items.NETHERITE_LEGGINGS, 2);
        reinforcing(consumer, BPItems.REINFORCED_FLEIGNARITE_BOOTS.get(), BPItems.FLEIGNARITE_BOOTS.get(), BPItems.BELLOPHITE.get(), Items.NETHERITE_BOOTS, 2);

        // WOODSET
        this.woodset(consumer, BPTags.Items.ENIVILE_LOGS,
                BPBlocks.ENIVILE_PLANKS.get(), BPBlocks.ENIVILE_LOG.get(), BPBlocks.STRIPPED_ENIVILE_LOG.get(),
                BPBlocks.ENIVILE_WOOD.get(), BPBlocks.STRIPPED_ENIVILE_WOOD.get(), BPBlocks.ENIVILE_FENCE.get(), 
                BPBlocks.ENIVILE_FENCE_GATE.get(), BPBlocks.ENIVILE_PRESSURE_PLATE.get(), BPBlocks.ENIVILE_DOOR.get(),
                BPBlocks.ENIVILE_TRAPDOOR.get(), BPBlocks.ENIVILE_BUTTON.get(), BPBlocks.ENIVILE_STAIRS.get(), 
                BPBlocks.ENIVILE_SLAB.get(), BPBlocks.ENIVILE_SIGN_ITEM.get(), BPBlocks.ENIVILE_BOAT.get()
        );
        this.woodset(consumer, BPTags.Items.CAERULWOOD_LOGS,
                BPBlocks.CAERULWOOD_PLANKS.get(), BPBlocks.CAERULWOOD_LOG.get(), BPBlocks.STRIPPED_CAERULWOOD_LOG.get(),
                BPBlocks.CAERULWOOD_WOOD.get(), BPBlocks.STRIPPED_CAERULWOOD_WOOD.get(), BPBlocks.CAERULWOOD_FENCE.get(),
                BPBlocks.CAERULWOOD_FENCE_GATE.get(), BPBlocks.CAERULWOOD_PRESSURE_PLATE.get(), BPBlocks.CAERULWOOD_DOOR.get(),
                BPBlocks.CAERULWOOD_TRAPDOOR.get(), BPBlocks.CAERULWOOD_BUTTON.get(), BPBlocks.CAERULWOOD_STAIRS.get(),
                BPBlocks.CAERULWOOD_SLAB.get(), BPBlocks.CAERULWOOD_SIGN_ITEM.get(), BPBlocks.CAERULWOOD_BOAT.get()
        );

        // STONE
        stoneSetHelper(consumer, BPBlocks.ALPHANUM.get(), BPBlocks.ALPHANUM_BRICKS.get(), BPBlocks.POLISHED_ALPHANUM.get(),
                BPBlocks.ALPHANUM_STAIRS.get(), BPBlocks.ALPHANUM_WALL.get(), BPBlocks.ALPHANUM_SLAB.get(),
                BPBlocks.ALPHANUM_STAIRS_BRICKS.get(), BPBlocks.ALPHANUM_WALL_BRICKS.get(), BPBlocks.ALPHANUM_SLAB_BRICKS.get(),
                BPBlocks.POLISHED_ALPHANUM_STAIRS.get(), BPBlocks.POLISHED_ALPHANUM_WALL.get(), BPBlocks.POLISHED_ALPHANUM_SLAB.get()
        );
        pillarCrafting(consumer, BPBlocks.ALPHANUM_PILLAR.get(), BPBlocks.ALPHANUM.get());

        // ETC.
        reinforcing(consumer, BPItems.ARBITRARY_BALLISTA.get(), BPItems.RED_GRYLYNEN_CRYSTAL.get(), BPItems.BELLOPHITE.get(), Items.CROSSBOW, 0);
        reinforcing(consumer, BPItems.INFERNAL_QUARTERSTAFF_DEACTIVATED.get(), BPItems.INFERNAL_QUARTERSTAFF_BLADE.get(), BPItems.INFERNAL_QUARTERSTAFF_BASE.get(), BPItems.INFERNAL_QUARTERSTAFF_BLADE.get(), 0);
        reinforcing(consumer, BPItems.INFERNAL_QUARTERSTAFF.get(), BPItems.FIERY_CUBE.get(), BPItems.INFERNAL_QUARTERSTAFF_DEACTIVATED.get(), BPItems.SOUL_CUBE.get(), 0);
    }

    public String getName() {
        return "Bioplethora Recipes";
    }

    public static void shortsword(Consumer<FinishedRecipe> consumer, ItemLike providedItem, ItemLike handle, ItemLike material) {
        ShapedRecipeBuilder.shaped(providedItem).define('I', handle).define('#', material).pattern(" I ").pattern(" # ")
                .unlockedBy("has_item", has(material)).save(consumer);
    }

    public static void ingotToBlock(Consumer<FinishedRecipe> consumer, ItemLike providedItem, ItemLike requiredItem) {
        ShapedRecipeBuilder.shaped(providedItem, 1).define('#', requiredItem).pattern("###").pattern("###").pattern("###")
                .unlockedBy("has_item", has(requiredItem)).save(consumer);
    }

    public static void blockToIngot(Consumer<FinishedRecipe> consumer, ItemLike providedItem, ItemLike requiredItem) {
        ShapelessRecipeBuilder.shapeless(providedItem, 9).requires(requiredItem)
                .unlockedBy("has_item", has(requiredItem)).save(consumer);
    }

    public void foodCooking(Consumer<FinishedRecipe> consumer, Item input, Item output, float exp, int defaultTime) {
        String inputItemString = input.getDescriptionId();
        
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(input), output, exp, defaultTime).unlockedBy("has_" + input.getDescriptionId(), has(input)).save(consumer);
        
        SimpleCookingRecipeBuilder.cooking(Ingredient.of(input), output, exp, defaultTime / 2, RecipeSerializer.SMOKING_RECIPE).unlockedBy("has_" + inputItemString, has(input))
                .save(consumer, new ResourceLocation(Bioplethora.MOD_ID, output.getDescriptionId() + "_smoking"));
        
        SimpleCookingRecipeBuilder.cooking(Ingredient.of(input), output, exp, defaultTime * 3, RecipeSerializer.CAMPFIRE_COOKING_RECIPE).unlockedBy("has_" + inputItemString, has(input))
                .save(consumer, new ResourceLocation(Bioplethora.MOD_ID, output.getDescriptionId() + "_campfire_cooking"));
    }

    public static void reinforcing(Consumer<FinishedRecipe> consumer, Item providedItem, ItemLike top, ItemLike mid, ItemLike bot, int dupeRecipe) {
        ReinforcingRecipeBuilder.reinforcing(Ingredient.of(top), Ingredient.of(mid), Ingredient.of(bot), providedItem).unlocks("has_item", has(bot))
                .save(consumer, new ResourceLocation(Bioplethora.MOD_ID, providedItem.getDescriptionId() + "_reinforcing" + (dupeRecipe <= 0 ? "" : "_" + dupeRecipe)));
    }

    public void woodset(Consumer<FinishedRecipe> consumer, TagKey<Item> logsTag,
                        ItemLike planks, ItemLike log, ItemLike strippedLog, ItemLike wood, ItemLike strippedWood,
                        ItemLike fence, ItemLike fenceGate, ItemLike pressurePlate, ItemLike door, ItemLike trapdoor,
                        ItemLike button, ItemLike stairs, ItemLike slab, ItemLike sign, ItemLike boat) {
        
        planksFromLogs(consumer, planks, logsTag);
        woodFromLogs(consumer, wood, log);
        woodFromLogs(consumer, strippedWood, strippedLog);
        
        woodenBoat(consumer, boat, planks);
        woodenButton(consumer, button, planks);
        woodenDoor(consumer, door, planks);
        woodenFence(consumer, fence, planks);
        woodenFenceGate(consumer, fenceGate, planks);
        woodenPressurePlate(consumer, pressurePlate, planks);
        woodenSlab(consumer, slab, planks);
        woodenStairs(consumer, stairs, planks);
        woodenTrapdoor(consumer, trapdoor, planks);
        woodenSign(consumer, sign, planks);
    }

    public void stoneSetHelper(Consumer<FinishedRecipe> consumer, ItemLike baseItem, ItemLike bricksBase, ItemLike polishedBase,
                                 ItemLike stairs, ItemLike wall, ItemLike slab,
                                 ItemLike brickStairs, ItemLike brickWall, ItemLike brickSlab,
                                 ItemLike polishedStairs, ItemLike polishedWall, ItemLike polishedSlab) {

        brickCrafting(consumer, bricksBase, baseItem);
        polishedCrafting(consumer, polishedBase, bricksBase);

        stairsCrafting(consumer, stairs, baseItem);
        wallsCrafting(consumer, wall, baseItem);
        slabsCrafting(consumer, slab, baseItem);
        stairsCrafting(consumer, brickStairs, bricksBase);
        wallsCrafting(consumer, brickWall, bricksBase);
        slabsCrafting(consumer, brickSlab, bricksBase);
        stairsCrafting(consumer, polishedStairs, polishedBase);
        wallsCrafting(consumer, polishedWall, polishedBase);
        slabsCrafting(consumer, polishedSlab, polishedBase);

        stoneCutting(consumer, baseItem, bricksBase);
        stoneCutting(consumer, baseItem, polishedBase);
        stoneCutting(consumer, baseItem, stairs);
        stoneCutting(consumer, baseItem, wall);
        stoneCutting(consumer, baseItem, slab, 2);
        stoneCutting(consumer, bricksBase, brickStairs);
        stoneCutting(consumer, bricksBase, brickWall);
        stoneCutting(consumer, bricksBase, brickSlab, 2);
        stoneCutting(consumer, polishedBase, polishedStairs);
        stoneCutting(consumer, polishedBase, polishedWall);
        stoneCutting(consumer, polishedBase, polishedSlab, 2);
    }

    public void armorSetHelper(Consumer<FinishedRecipe> consumer, ItemLike material, ItemLike helmet, ItemLike chestplate, ItemLike leggings, ItemLike boots) {
        ShapedRecipeBuilder.shaped(helmet).define('S', material).pattern("SSS").pattern("S S")
                .unlockedBy("has_" + material.asItem().getDescriptionId(), has(material)).save(consumer);
        ShapedRecipeBuilder.shaped(chestplate).define('S', material).pattern("S S").pattern("SSS").pattern("SSS")
                .unlockedBy("has_" + material.asItem().getDescriptionId(), has(material)).save(consumer);
        ShapedRecipeBuilder.shaped(leggings).define('S', material).pattern("SSS").pattern("S S").pattern("S S")
                .unlockedBy("has_" + material.asItem().getDescriptionId(), has(material)).save(consumer);
        ShapedRecipeBuilder.shaped(boots).define('S', material).pattern("S S").pattern("S S")
                .unlockedBy("has_" + material.asItem().getDescriptionId(), has(material)).save(consumer);
    }

    public void toolSetHelper(Consumer<FinishedRecipe> consumer, ItemLike material, ItemLike stick, ItemLike sword, ItemLike shovel, ItemLike pickaxe, ItemLike axe, ItemLike hoe) {
        ShapedRecipeBuilder.shaped(sword).define('S', stick).define('M', material).pattern(" M ").pattern(" M ").pattern(" S ")
                .unlockedBy("has_" + material.asItem().getDescriptionId(), has(material)).save(consumer);
        ShapedRecipeBuilder.shaped(shovel).define('S', stick).define('M', material).pattern(" M ").pattern(" S ").pattern(" S ")
                .unlockedBy("has_" + material.asItem().getDescriptionId(), has(material)).save(consumer);
        ShapedRecipeBuilder.shaped(pickaxe).define('S', stick).define('M', material).pattern("MMM").pattern(" S ").pattern(" S ")
                .unlockedBy("has_" + material.asItem().getDescriptionId(), has(material)).save(consumer);
        ShapedRecipeBuilder.shaped(axe).define('S', stick).define('M', material).pattern("MM ").pattern("MS ").pattern(" S ")
                .unlockedBy("has_" + material.asItem().getDescriptionId(), has(material)).save(consumer);
        ShapedRecipeBuilder.shaped(hoe).define('S', stick).define('M', material).pattern("MM ").pattern(" S ").pattern(" S ")
                .unlockedBy("has_" + material.asItem().getDescriptionId(), has(material)).save(consumer);
    }

    public void stoneCutting(Consumer<FinishedRecipe> consumer, ItemLike baseItem, ItemLike resultItem, int amount) {
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(baseItem), resultItem, amount).unlockedBy("has_stone", has(baseItem)).save(consumer, new ResourceLocation(Bioplethora.MOD_ID, resultItem.asItem().getDescriptionId() + "_from_stone_stonecutting"));
    }

    public void stoneCutting(Consumer<FinishedRecipe> consumer, ItemLike baseItem, ItemLike resultItem) {
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(baseItem), resultItem).unlockedBy("has_stone", has(baseItem)).save(consumer, new ResourceLocation(Bioplethora.MOD_ID, resultItem.asItem().getDescriptionId() + "_from_stone_stonecutting"));
    }

    public static void pillarCrafting(Consumer<FinishedRecipe> consumer, ItemLike providedItem, ItemLike requiredItem) {
        ShapedRecipeBuilder.shaped(providedItem, 2).define('S', requiredItem).pattern("S").pattern("S")
                .unlockedBy("has_" + requiredItem.asItem().getDescriptionId(), has(requiredItem)).save(consumer);
    }


    public static void brickCrafting(Consumer<FinishedRecipe> consumer, ItemLike providedItem, ItemLike requiredItem) {
        ShapedRecipeBuilder.shaped(providedItem).define('#', requiredItem).pattern("##").pattern("##")
                .unlockedBy("has_" + requiredItem.asItem().getDescriptionId(), has(requiredItem)).save(consumer);
    }

    public static void polishedCrafting(Consumer<FinishedRecipe> consumer, ItemLike providedItem, ItemLike requiredItem) {
        ShapedRecipeBuilder.shaped(providedItem, 4).define('S', requiredItem).pattern("SS").pattern("SS")
                .unlockedBy("has_" + requiredItem.asItem().getDescriptionId(), has(requiredItem)).save(consumer);
    }
    
    public static void stairsCrafting(Consumer<FinishedRecipe> consumer, ItemLike providedItem, ItemLike requiredItem) {
        ShapedRecipeBuilder.shaped(providedItem, 4).define('#', requiredItem).pattern("#  ").pattern("## ").pattern("###")
                .unlockedBy("has_" + requiredItem.asItem().getDescriptionId(), has(requiredItem)).save(consumer);
    }

    public static void wallsCrafting(Consumer<FinishedRecipe> consumer, ItemLike providedItem, ItemLike requiredItem) {
        ShapedRecipeBuilder.shaped(providedItem, 6).define('#', requiredItem).pattern("###").pattern("###")
                .unlockedBy("has_" + requiredItem.asItem().getDescriptionId(), has(requiredItem)).save(consumer);
    }

    public static void slabsCrafting(Consumer<FinishedRecipe> consumer, ItemLike providedItem, ItemLike requiredItem) {
        ShapedRecipeBuilder.shaped(providedItem, 6).define('#', requiredItem).pattern("###")
                .unlockedBy("has_" + requiredItem.asItem().getDescriptionId(), has(requiredItem)).save(consumer);

    }

    public static void planksFromLog(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ItemLike pPlanks, TagKey<Item> pLog) {
        ShapelessRecipeBuilder.shapeless(pPlanks, 4).requires(pLog).group("planks").unlockedBy("has_log", has(pLog)).save(pFinishedRecipeConsumer);
    }

    public static void planksFromLogs(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ItemLike pPlanks, TagKey<Item> pLogs) {
        ShapelessRecipeBuilder.shapeless(pPlanks, 4).requires(pLogs).group("planks").unlockedBy("has_logs", has(pLogs)).save(pFinishedRecipeConsumer);
    }

    public static void woodFromLogs(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ItemLike pWood, ItemLike pLog) {
        ShapedRecipeBuilder.shaped(pWood, 3).define('#', pLog).pattern("##").pattern("##").group("bark").unlockedBy("has_log", has(pLog)).save(pFinishedRecipeConsumer);
    }

    public static void woodenBoat(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ItemLike pBoat, ItemLike pMaterial) {
        ShapedRecipeBuilder.shaped(pBoat).define('#', pMaterial).pattern("# #").pattern("###").group("boat").unlockedBy("in_water", insideOf(Blocks.WATER)).save(pFinishedRecipeConsumer);
    }

    public static void woodenButton(Consumer<FinishedRecipe> p_240474_0_, ItemLike p_240474_1_, ItemLike p_240474_2_) {
        ShapelessRecipeBuilder.shapeless(p_240474_1_).requires(p_240474_2_).group("wooden_button").unlockedBy("has_planks", has(p_240474_2_)).save(p_240474_0_);
    }

    public static void woodenDoor(Consumer<FinishedRecipe> p_240475_0_, ItemLike p_240475_1_, ItemLike p_240475_2_) {
        ShapedRecipeBuilder.shaped(p_240475_1_, 3).define('#', p_240475_2_).pattern("##").pattern("##").pattern("##").group("wooden_door").unlockedBy("has_planks", has(p_240475_2_)).save(p_240475_0_);
    }

    public static void woodenFence(Consumer<FinishedRecipe> p_240476_0_, ItemLike p_240476_1_, ItemLike p_240476_2_) {
        ShapedRecipeBuilder.shaped(p_240476_1_, 3).define('#', Items.STICK).define('W', p_240476_2_).pattern("W#W").pattern("W#W").group("wooden_fence").unlockedBy("has_planks", has(p_240476_2_)).save(p_240476_0_);
    }

    public static void woodenFenceGate(Consumer<FinishedRecipe> p_240477_0_, ItemLike p_240477_1_, ItemLike p_240477_2_) {
        ShapedRecipeBuilder.shaped(p_240477_1_).define('#', Items.STICK).define('W', p_240477_2_).pattern("#W#").pattern("#W#").group("wooden_fence_gate").unlockedBy("has_planks", has(p_240477_2_)).save(p_240477_0_);
    }

    public static void woodenPressurePlate(Consumer<FinishedRecipe> p_240478_0_, ItemLike p_240478_1_, ItemLike p_240478_2_) {
        ShapedRecipeBuilder.shaped(p_240478_1_).define('#', p_240478_2_).pattern("##").group("wooden_pressure_plate").unlockedBy("has_planks", has(p_240478_2_)).save(p_240478_0_);
    }

    public static void woodenSlab(Consumer<FinishedRecipe> p_240479_0_, ItemLike p_240479_1_, ItemLike p_240479_2_) {
        ShapedRecipeBuilder.shaped(p_240479_1_, 6).define('#', p_240479_2_).pattern("###").group("wooden_slab").unlockedBy("has_planks", has(p_240479_2_)).save(p_240479_0_);
    }

    public static void woodenStairs(Consumer<FinishedRecipe> p_240480_0_, ItemLike p_240480_1_, ItemLike p_240480_2_) {
        ShapedRecipeBuilder.shaped(p_240480_1_, 4).define('#', p_240480_2_).pattern("#  ").pattern("## ").pattern("###").group("wooden_stairs").unlockedBy("has_planks", has(p_240480_2_)).save(p_240480_0_);
    }

    public static void woodenTrapdoor(Consumer<FinishedRecipe> p_240481_0_, ItemLike p_240481_1_, ItemLike p_240481_2_) {
        ShapedRecipeBuilder.shaped(p_240481_1_, 2).define('#', p_240481_2_).pattern("###").pattern("###").group("wooden_trapdoor").unlockedBy("has_planks", has(p_240481_2_)).save(p_240481_0_);
    }

    public static void woodenSign(Consumer<FinishedRecipe> p_240482_0_, ItemLike p_240482_1_, ItemLike p_240482_2_) {
        String s = ForgeRegistries.ITEMS.getKey(p_240482_2_.asItem()).getPath();
        ShapedRecipeBuilder.shaped(p_240482_1_, 3).group("sign").define('#', p_240482_2_).define('X', Items.STICK).pattern("###").pattern("###").pattern(" X ").unlockedBy("has_" + s, has(p_240482_2_)).save(p_240482_0_);
    }
}
