package io.github.bioplethora.registry;

import java.util.function.Supplier;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.blocks.BPDoublePlantBlock;
import io.github.bioplethora.blocks.BPFlatBlock;
import io.github.bioplethora.blocks.BPFruitableVinesBlock;
import io.github.bioplethora.blocks.BPFruitableVinesTopBlock;
import io.github.bioplethora.blocks.BPIdeFanBlock;
import io.github.bioplethora.blocks.BPLanternPlantBlock;
import io.github.bioplethora.blocks.BPLeavesBlock;
import io.github.bioplethora.blocks.BPPlantBlock;
import io.github.bioplethora.blocks.BPReversePlantBlock;
import io.github.bioplethora.blocks.BPSaplingBlock;
import io.github.bioplethora.blocks.BPStandingSignBlock;
import io.github.bioplethora.blocks.BPVinesBlock;
import io.github.bioplethora.blocks.BPVinesTopBlock;
import io.github.bioplethora.blocks.BPWallSignBlock;
import io.github.bioplethora.blocks.BPWaterDoublePlantBlock;
import io.github.bioplethora.blocks.BPWaterPlantBlock;
import io.github.bioplethora.blocks.SmallMushroomBlock;
import io.github.bioplethora.blocks.specific.EnredeKelpBlock;
import io.github.bioplethora.blocks.specific.EnredeKelpTopBlock;
import io.github.bioplethora.blocks.specific.FleignariteRemainsBlock;
import io.github.bioplethora.blocks.specific.FleignariteVinesBlock;
import io.github.bioplethora.blocks.specific.FleignariteVinesTopBlock;
import io.github.bioplethora.blocks.specific.FrostbiteMetalCoreBlock;
import io.github.bioplethora.blocks.specific.FrostemBlock;
import io.github.bioplethora.blocks.specific.LavaSpireBlock;
import io.github.bioplethora.blocks.tile_entities.AlphanumNucleusBlock;
import io.github.bioplethora.blocks.tile_entities.FleignariteSplotchBlock;
import io.github.bioplethora.blocks.tile_entities.ReinforcingTableBlock;
import io.github.bioplethora.enums.BioPlantShape;
import io.github.bioplethora.enums.BioPlantType;
import io.github.bioplethora.item.functionals.BPBoatItem;
import io.github.bioplethora.registry.worldgen.BPNBTTrees;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SignItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.StandingSignBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraft.world.level.block.WoodButtonBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
//TODO required tag definition
public class BPBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Bioplethora.MOD_ID);
    public static final DeferredRegister<Item> BLOCK_ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Bioplethora.MOD_ID);

    public static final RegistryObject<ReinforcingTableBlock> REINFORCING_TABLE = registerBlock("reinforcing_table", () -> new ReinforcingTableBlock(BlockBehaviour.Properties.of(Material.METAL)
            .strength(20.0F, 35).sound(SoundType.NETHERITE_BLOCK).requiresCorrectToolForDrops().noOcclusion()), BPItemGroup.BioplethoraItemItemGroup);

    public static final RegistryObject<Block> NANDBRI_SCALE_BLOCK = registerBlock("nandbri_scale_block", () -> new Block(BlockBehaviour.Properties.of(Material.WOOL, MaterialColor.COLOR_LIGHT_GRAY)
            .strength(0.8F, 0.8F).sound(SoundType.BONE_BLOCK)), BPItemGroup.BioplethoraItemItemGroup);
    public static final RegistryObject<Block> BELLOPHITE_BLOCK = registerFireResBlock("frostbite_metal_block", () -> new Block(BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops()
            .strength(50.0F, 1200.0F).sound(SoundType.NETHERITE_BLOCK)), BPItemGroup.BioplethoraItemItemGroup);
    public static final RegistryObject<Block> BELLOPHITE_CORE_BLOCK = registerFireResBlock("frostbite_metal_core_block", () -> new FrostbiteMetalCoreBlock(BlockBehaviour.Properties.of(Material.GLASS)
            .strength(0.3F).sound(SoundType.GLASS).noOcclusion().hasPostProcess((bs, br, bp) -> true)), BPItemGroup.BioplethoraItemItemGroup);

    public static final RegistryObject<Block> GREEN_GRYLYNEN_CRYSTAL_BLOCK = registerFireResBlock("green_grylynen_crystal_block", () -> new Block(BlockBehaviour.Properties.of(Material.GLASS)
            .strength(0.3F).sound(SoundType.NETHER_GOLD_ORE).lightLevel((block) -> 7).hasPostProcess((bs, br, bp) -> true)), BPItemGroup.BioplethoraItemItemGroup);
    public static final RegistryObject<Block> YELLOW_GRYLYNEN_CRYSTAL_BLOCK = registerFireResBlock("yellow_grylynen_crystal_block", () -> new Block(BlockBehaviour.Properties.of(Material.GLASS)
            .strength(0.4F).sound(SoundType.NETHER_GOLD_ORE).lightLevel((block) -> 9).hasPostProcess((bs, br, bp) -> true)), BPItemGroup.BioplethoraItemItemGroup);
    public static final RegistryObject<Block> RED_GRYLYNEN_CRYSTAL_BLOCK = registerFireResBlock("red_grylynen_crystal_block", () -> new Block(BlockBehaviour.Properties.of(Material.GLASS)
            .strength(0.5F).sound(SoundType.NETHER_GOLD_ORE).lightLevel((block) -> 12).hasPostProcess((bs, br, bp) -> true)), BPItemGroup.BioplethoraItemItemGroup);

    public static final RegistryObject<Block> MIRESTONE = registerBlock("mirestone", () -> new Block(BlockBehaviour.Properties.of(Material.STONE)
            .strength(1.2F, 4.8F).sound(SoundType.GRAVEL)), null);

    // Fleignarite Blocks
    public static final RegistryObject<Block> FLEIGNARITE_REMAINS = registerBlock("fleignarite_remains", () -> new FleignariteRemainsBlock(BlockBehaviour.Properties.of(Material.PLANT)
            .strength(0.3F).friction(0.8F).sound(SoundType.SLIME_BLOCK).noOcclusion()
            .lightLevel((block) -> 5)), BPItemGroup.BioplethoraItemItemGroup);
    public static final RegistryObject<Block> FLEIGNARITE_SPLOTCH = registerBlock("fleignarite_splotch", () -> new FleignariteSplotchBlock(BlockBehaviour.Properties.of(Material.PLANT)
            .strength(0.3F).friction(0.8F).sound(SoundType.SLIME_BLOCK).noOcclusion()
            .lightLevel((block) -> 5)), BPItemGroup.BioplethoraItemItemGroup);

    public static final RegistryObject<Block> FLEIGNARITE_VINES = registerBlock("fleignarite_vines", () -> new FleignariteVinesTopBlock(BlockBehaviour.Properties.of(Material.PLANT)
            .strength(0.3F).instabreak().sound(SoundType.SLIME_BLOCK).noCollission()
            .lightLevel((block) -> 5)), BPItemGroup.BioplethoraItemItemGroup);
    public static final RegistryObject<Block> FLEIGNARITE_VINES_PLANT = registerBlock("fleignarite_vines_plant", () -> new FleignariteVinesBlock(BlockBehaviour.Properties.of(Material.PLANT)
            .strength(0.3F).instabreak().sound(SoundType.SLIME_BLOCK).noCollission()
            .lightLevel((block) -> 5)), null, false);

    // Nether Blocks
    public static final RegistryObject<Block> CRYEA = registerBlock("cryea", () -> new Block(BlockBehaviour.Properties.of(Material.GRASS, MaterialColor.COLOR_PINK)
            .strength(1.85F).sound(SoundType.NETHER_SPROUTS)), BPItemGroup.BioplethoraItemItemGroup);
    public static final RegistryObject<Block> CRYEA_CARPET = registerBlock("cryea_carpet", () -> new BPFlatBlock(BlockBehaviour.Properties.of(Material.REPLACEABLE_FIREPROOF_PLANT, MaterialColor.COLOR_PINK)
            .strength(1.85F).sound(SoundType.NETHER_SPROUTS)), BPItemGroup.BioplethoraItemItemGroup);
    // Nether Plants
    public static final RegistryObject<Block> KYRIA = registerBlock("kyria", () -> new BPPlantBlock(BioPlantType.CRYEANUM, BioPlantShape.SIMPLE_PLANT,
            BlockBehaviour.Properties.of(Material.REPLACEABLE_FIREPROOF_PLANT).sound(SoundType.TWISTING_VINES).strength(0.3F).instabreak().noCollission()), BPItemGroup.BioplethoraItemItemGroup);
    public static final RegistryObject<Block> KYRIA_BELINE = registerBlock("kyria_beline", () -> new BPDoublePlantBlock(BioPlantType.CRYEANUM, BioPlantShape.SIMPLE_PLANT,
            BlockBehaviour.Properties.of(Material.REPLACEABLE_FIREPROOF_PLANT).sound(SoundType.TWISTING_VINES).strength(0.3F).instabreak().noCollission()), BPItemGroup.BioplethoraItemItemGroup);
    public static final RegistryObject<Block> KYRIA_IDE_FAN = registerBlock("kyria_ide_fan", () -> new BPIdeFanBlock(BioPlantType.CRYEANUM, BioPlantShape.IDE_FAN,
            BlockBehaviour.Properties.of(Material.PLANT).sound(SoundType.TWISTING_VINES).strength(0.3F).instabreak().lightLevel(b -> b.getValue(BPIdeFanBlock.BUDDED) ? 4 : 7).noCollission()), BPItemGroup.BioplethoraItemItemGroup);

    public static final RegistryObject<Block> SOUL_MINISHROOM = registerBlock("soul_minishroom", () -> new SmallMushroomBlock(BioPlantType.SOUL_SAND_VALLEY,
            BlockBehaviour.Properties.of(Material.PLANT, MaterialColor.COLOR_LIGHT_BLUE).sound(SoundType.SOUL_SOIL).noCollission().strength(0.5F).lightLevel((block) -> 5 * block.getValue(SmallMushroomBlock.MINISHROOMS))), BPItemGroup.BioplethoraItemItemGroup);

    public static final RegistryObject<Block> SOUL_BIGSHROOM = registerBlock("soul_bigshroom", () -> new BPPlantBlock(BioPlantType.SOUL_SAND_VALLEY, BioPlantShape.BIG_MUSHROOM,
            BlockBehaviour.Properties.of(Material.PLANT, MaterialColor.COLOR_LIGHT_BLUE).sound(SoundType.SOUL_SOIL).noOcclusion().strength(0.5F).lightLevel((block) -> 15).emissiveRendering((bs, br, bp) -> true)), BPItemGroup.BioplethoraItemItemGroup);

    public static final RegistryObject<Block> LAVA_SPIRE = registerBlock("lava_spire", () -> new LavaSpireBlock(BlockBehaviour.Properties.of(Material.REPLACEABLE_FIREPROOF_PLANT)
            .sound(SoundType.NETHER_SPROUTS).strength(0.3F).instabreak().noCollission().lightLevel((block) -> 13)), BPItemGroup.BioplethoraItemItemGroup);
    public static final RegistryObject<Block> WARPED_DANCER = registerBlock("warped_dancer", () -> new BPPlantBlock(BioPlantType.WARPED_FOREST, BioPlantShape.SIMPLE_PLANT,
            BlockBehaviour.Properties.of(Material.REPLACEABLE_FIREPROOF_PLANT).sound(SoundType.NETHER_SPROUTS).strength(0.3F).instabreak().noCollission().lightLevel((block) -> 3)), BPItemGroup.BioplethoraItemItemGroup);

    public static final RegistryObject<Block> SOUL_SPROUTS = registerBlock("soul_sprouts", () -> new BPPlantBlock(BioPlantType.SOUL_SAND_VALLEY, BioPlantShape.SIMPLE_PLANT,
            BlockBehaviour.Properties.of(Material.REPLACEABLE_FIREPROOF_PLANT).sound(SoundType.SOUL_SOIL).instabreak().noCollission()), BPItemGroup.BioplethoraItemItemGroup);
    public static final RegistryObject<Block> SOUL_TALL_GRASS = registerBlock("soul_tall_grass", () -> new BPDoublePlantBlock(BioPlantType.SOUL_SAND_VALLEY, BioPlantShape.SIMPLE_PLANT,
            BlockBehaviour.Properties.of(Material.REPLACEABLE_FIREPROOF_PLANT).sound(SoundType.SOUL_SOIL).noCollission().instabreak()
                    .lightLevel((block) -> block.getValue(DoublePlantBlock.HALF) == DoubleBlockHalf.UPPER ? 12 : 3)), BPItemGroup.BioplethoraItemItemGroup);

    public static final RegistryObject<Block> PINK_TWI = registerBlock("pink_twi", () -> new BPVinesTopBlock.PinkTwiTopBlock(
            BlockBehaviour.Properties.of(Material.REPLACEABLE_FIREPROOF_PLANT).sound(SoundType.TWISTING_VINES).instabreak().noCollission().lightLevel((block) -> 7)), BPItemGroup.BioplethoraItemItemGroup);
    public static final RegistryObject<Block> PINK_TWI_PLANT = registerBlock("pink_twi_plant", () -> new BPVinesBlock.PinkTwiBlock(
            BlockBehaviour.Properties.of(Material.REPLACEABLE_FIREPROOF_PLANT).sound(SoundType.TWISTING_VINES).instabreak().noCollission().lightLevel((block) -> 15)), null, false);

    public static final RegistryObject<Block> RED_TWI = registerBlock("red_twi", () -> new BPVinesTopBlock.RedTwiTopBlock(
            BlockBehaviour.Properties.of(Material.REPLACEABLE_FIREPROOF_PLANT).sound(SoundType.TWISTING_VINES).instabreak().noCollission().lightLevel((block) -> 7)), BPItemGroup.BioplethoraItemItemGroup);
    public static final RegistryObject<Block> RED_TWI_PLANT = registerBlock("red_twi_plant", () -> new BPVinesBlock.RedTwiBlock(
            BlockBehaviour.Properties.of(Material.REPLACEABLE_FIREPROOF_PLANT).sound(SoundType.TWISTING_VINES).instabreak().noCollission().lightLevel((block) -> 15)), null, false);

    public static final RegistryObject<Block> SPIRIT_DANGLER = registerBlock("spirit_dangler", () -> new BPVinesTopBlock.SpiritDanglerTopBlock(
            BlockBehaviour.Properties.of(Material.REPLACEABLE_FIREPROOF_PLANT, MaterialColor.COLOR_LIGHT_BLUE).sound(SoundType.TWISTING_VINES).noCollission().instabreak().lightLevel((block) -> 13)), BPItemGroup.BioplethoraItemItemGroup);
    public static final RegistryObject<Block> SPIRIT_DANGLER_PLANT = registerBlock("spirit_dangler_plant", () -> new BPVinesBlock.SpiritDanglerBlock(
            BlockBehaviour.Properties.of(Material.REPLACEABLE_FIREPROOF_PLANT, MaterialColor.COLOR_LIGHT_BLUE).sound(SoundType.TWISTING_VINES).noCollission().instabreak().lightLevel((block) -> 15)), null, false);

    public static final RegistryObject<Block> BASALT_SPELEOTHERM = registerBlock("basalt_speleotherm", () -> new BPFruitableVinesTopBlock.BasaltSpeleothermTopBlock(
            BlockBehaviour.Properties.of(Material.REPLACEABLE_FIREPROOF_PLANT).sound(SoundType.BASALT).strength(0.3F).noOcclusion()), BPItemGroup.BioplethoraItemItemGroup);
    public static final RegistryObject<Block> BASALT_SPELEOTHERM_PLANT = registerBlock("basalt_speleotherm_plant", () -> new BPFruitableVinesBlock.BasaltSpeleothermBlock(
            BlockBehaviour.Properties.of(Material.REPLACEABLE_FIREPROOF_PLANT).sound(SoundType.BASALT).strength(0.3F).noOcclusion()), null, false);
    public static final RegistryObject<Block> FIERY_BASALT_SPELEOTHERM = registerBlock("fiery_basalt_speleotherm", () -> new BPFruitableVinesBlock.FieryBasaltSpeleothermBlock(
            BlockBehaviour.Properties.of(Material.REPLACEABLE_FIREPROOF_PLANT).sound(SoundType.BASALT).strength(0.3F).noOcclusion().lightLevel((block) -> 7)), null, false);

    public static final RegistryObject<Block> THONTUS_THISTLE = registerBlock("thontus_thistle", () -> new BPFruitableVinesTopBlock.ThontusThistleTopBlock(
            BlockBehaviour.Properties.of(Material.REPLACEABLE_FIREPROOF_PLANT).sound(SoundType.VINE).strength(0.3F).instabreak().noCollission()), BPItemGroup.BioplethoraItemItemGroup);
    public static final RegistryObject<Block> THONTUS_THISTLE_PLANT = registerBlock("thontus_thistle_plant", () -> new BPFruitableVinesBlock.ThontusThistleBlock(
            BlockBehaviour.Properties.of(Material.REPLACEABLE_FIREPROOF_PLANT).sound(SoundType.VINE).strength(0.3F).instabreak().noCollission()), null, false);
    public static final RegistryObject<Block> BERRIED_THONTUS_THISTLE = registerBlock("berried_thontus_thistle", () -> new BPFruitableVinesBlock.BerriedThontusThistleBlock(
            BlockBehaviour.Properties.of(Material.REPLACEABLE_FIREPROOF_PLANT).sound(SoundType.VINE).strength(0.3F).instabreak().noCollission().lightLevel((block) -> 7)), null, false);

    public static final RegistryObject<Block> TURQUOISE_PENDENT = registerBlock("turquoise_pendent", () -> new BPFruitableVinesTopBlock.TurquoisePendentTopBlock(
            BlockBehaviour.Properties.of(Material.REPLACEABLE_FIREPROOF_PLANT).sound(SoundType.TWISTING_VINES).strength(0.3F).instabreak().noCollission()), BPItemGroup.BioplethoraItemItemGroup);
    public static final RegistryObject<Block> TURQUOISE_PENDENT_PLANT = registerBlock("turquoise_pendent_plant", () -> new BPFruitableVinesBlock.TurquoisePendentBlock(
            BlockBehaviour.Properties.of(Material.REPLACEABLE_FIREPROOF_PLANT).sound(SoundType.TWISTING_VINES).strength(0.3F).instabreak().noCollission()), null, false);
    public static final RegistryObject<Block> BLOSSOMING_TURQUOISE_PENDENT = registerBlock("blossoming_turquoise_pendent", () -> new BPFruitableVinesBlock.BlossomingTurquoisePendentBlock(
            BlockBehaviour.Properties.of(Material.REPLACEABLE_FIREPROOF_PLANT).sound(SoundType.TWISTING_VINES).strength(0.3F).instabreak().noCollission().lightLevel((block) -> 7)), null, false);

    public static final RegistryObject<Block> CERISE_IVY = registerBlock("cerise_ivy", () -> new BPFruitableVinesTopBlock.CeriseIvyTopBlock(
            BlockBehaviour.Properties.of(Material.REPLACEABLE_FIREPROOF_PLANT).sound(SoundType.WEEPING_VINES).strength(0.3F).instabreak().noCollission()), BPItemGroup.BioplethoraItemItemGroup);
    public static final RegistryObject<Block> CERISE_IVY_PLANT = registerBlock("cerise_ivy_plant", () -> new BPFruitableVinesBlock.CeriseIvyBlock(
            BlockBehaviour.Properties.of(Material.REPLACEABLE_FIREPROOF_PLANT).sound(SoundType.WEEPING_VINES).strength(0.3F).instabreak().noCollission()), null, false);
    public static final RegistryObject<Block> SEEDED_CERISE_IVY = registerBlock("seeded_cerise_ivy", () -> new BPFruitableVinesBlock.SeededCeriseIvyBlock(
            BlockBehaviour.Properties.of(Material.REPLACEABLE_FIREPROOF_PLANT).sound(SoundType.WEEPING_VINES).strength(0.3F).instabreak().noCollission().lightLevel((block) -> 7)), null, false);

    public static final RegistryObject<Block> SOUL_ETERN = registerBlock("soul_etern", () -> new BPFruitableVinesTopBlock.SoulEternTopBlock(
            BlockBehaviour.Properties.of(Material.REPLACEABLE_FIREPROOF_PLANT).sound(SoundType.SOUL_SOIL).strength(0.3F).noCollission().instabreak().lightLevel((block) -> 10)), BPItemGroup.BioplethoraItemItemGroup);
    public static final RegistryObject<Block> SOUL_ETERN_PLANT = registerBlock("soul_etern_plant", () -> new BPFruitableVinesBlock.SoulEternBlock(
            BlockBehaviour.Properties.of(Material.REPLACEABLE_FIREPROOF_PLANT).sound(SoundType.SOUL_SOIL).strength(0.3F).noCollission().instabreak()), null, false);
    public static final RegistryObject<Block> FLOURISHED_SOUL_ETERN = registerBlock("flourished_soul_etern", () -> new BPFruitableVinesBlock.FlourishedSoulEternBlock(
            BlockBehaviour.Properties.of(Material.REPLACEABLE_FIREPROOF_PLANT).sound(SoundType.SOUL_SOIL).strength(0.3F).noCollission().instabreak().lightLevel((block) -> 13)), null, false);

    // Potted Plants
    public static final RegistryObject<FlowerPotBlock> POTTED_LAVA_SPIRE = registerBlock("potted_lava_spire", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, LAVA_SPIRE, BlockBehaviour.Properties.copy(Blocks.FLOWER_POT)), null, false);

    // Enivile Woodset
    public static final RegistryObject<Block> ENIVILE_PLANKS = registerBlock("enivile_planks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.CRIMSON_PLANKS).strength(2.4F).sound(SoundType.WOOD)), BPItemGroup.BioplethoraItemItemGroup);
    public static final RegistryObject<RotatedPillarBlock> ENIVILE_LOG = registerBlock("enivile_log", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.CRIMSON_HYPHAE).strength(2.4F).sound(SoundType.WOOD)), BPItemGroup.BioplethoraItemItemGroup);
    public static final RegistryObject<RotatedPillarBlock> STRIPPED_ENIVILE_LOG = registerBlock("stripped_enivile_log", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(BPBlocks.ENIVILE_LOG.get())), BPItemGroup.BioplethoraItemItemGroup);
    public static final RegistryObject<RotatedPillarBlock> ENIVILE_WOOD = registerBlock("enivile_wood", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.CRIMSON_HYPHAE).strength(2.4F).sound(SoundType.WOOD)), BPItemGroup.BioplethoraItemItemGroup);
    public static final RegistryObject<RotatedPillarBlock> STRIPPED_ENIVILE_WOOD = registerBlock("stripped_enivile_wood", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(BPBlocks.ENIVILE_WOOD.get())), BPItemGroup.BioplethoraItemItemGroup);

    public static final RegistryObject<LeavesBlock> ENIVILE_LEAVES_PINK = registerBlock("pink_enivile_leaves", () -> new BPLeavesBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LEAVES).sound(SoundType.GRASS).noOcclusion()), BPItemGroup.BioplethoraItemItemGroup);
    public static final RegistryObject<LeavesBlock> ENIVILE_LEAVES_RED = registerBlock("red_enivile_leaves", () -> new BPLeavesBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LEAVES).sound(SoundType.GRASS).noOcclusion()), BPItemGroup.BioplethoraItemItemGroup);
    public static final RegistryObject<SaplingBlock> ENIVILE_SAPLING = registerBlock("enivile_sapling", () -> new BPSaplingBlock(BioPlantType.CRYEANUM, new BPNBTTrees.EnivileNBTTree(), BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING).noCollission()), BPItemGroup.BioplethoraItemItemGroup);

    public static final RegistryObject<FenceBlock> ENIVILE_FENCE = registerBlock("enivile_fence", () -> new FenceBlock(BlockBehaviour.Properties.copy(BPBlocks.ENIVILE_PLANKS.get())), BPItemGroup.BioplethoraItemItemGroup);
    public static final RegistryObject<FenceGateBlock> ENIVILE_FENCE_GATE = registerBlock("enivile_fence_gate", () -> new FenceGateBlock(BlockBehaviour.Properties.copy(BPBlocks.ENIVILE_PLANKS.get())), BPItemGroup.BioplethoraItemItemGroup);
    public static final RegistryObject<StandingSignBlock> ENIVILE_SIGN = registerBlock("enivile_sign", () -> new BPStandingSignBlock(BlockBehaviour.Properties.copy(BPBlocks.ENIVILE_PLANKS.get()), BPWoodTypes.ENIVILE), null, false);
    public static final RegistryObject<WallSignBlock> ENIVILE_WALL_SIGN = registerBlock("enivile_wall_sign", () -> new BPWallSignBlock(BlockBehaviour.Properties.copy(BPBlocks.ENIVILE_PLANKS.get()), BPWoodTypes.ENIVILE), null, false);
    public static final RegistryObject<PressurePlateBlock> ENIVILE_PRESSURE_PLATE = registerBlock("enivile_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.copy(BPBlocks.ENIVILE_PLANKS.get())), BPItemGroup.BioplethoraItemItemGroup);
    public static final RegistryObject<DoorBlock> ENIVILE_DOOR = registerBlock("enivile_door", () -> new DoorBlock(BlockBehaviour.Properties.copy(BPBlocks.ENIVILE_PLANKS.get())), BPItemGroup.BioplethoraItemItemGroup);
    public static final RegistryObject<TrapDoorBlock> ENIVILE_TRAPDOOR = registerBlock("enivile_trapdoor", () -> new TrapDoorBlock(BlockBehaviour.Properties.copy(BPBlocks.ENIVILE_PLANKS.get())), BPItemGroup.BioplethoraItemItemGroup);
    public static final RegistryObject<WoodButtonBlock> ENIVILE_BUTTON = registerBlock("enivile_button", () -> new WoodButtonBlock(BlockBehaviour.Properties.copy(BPBlocks.ENIVILE_PLANKS.get())), BPItemGroup.BioplethoraItemItemGroup);
    public static final RegistryObject<StairBlock> ENIVILE_STAIRS = registerBlock("enivile_stairs", () -> new StairBlock(() -> BPBlocks.ENIVILE_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(BPBlocks.ENIVILE_PLANKS.get())), BPItemGroup.BioplethoraItemItemGroup);
    public static final RegistryObject<SlabBlock> ENIVILE_SLAB = registerBlock("enivile_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(BPBlocks.ENIVILE_PLANKS.get())), BPItemGroup.BioplethoraItemItemGroup);

    public static final RegistryObject<Item> ENIVILE_SIGN_ITEM = BLOCK_ITEMS.register("enivile_sign", () -> new SignItem(new Item.Properties().stacksTo(16).tab(BPItemGroup.BioplethoraItemItemGroup), BPBlocks.ENIVILE_SIGN.get(), BPBlocks.ENIVILE_WALL_SIGN.get()));
    public static final RegistryObject<Item> ENIVILE_BOAT = BLOCK_ITEMS.register("enivile_boat", () -> new BPBoatItem(new Item.Properties().stacksTo(1).tab(BPItemGroup.BioplethoraItemItemGroup), "enivile"));

    // Caerulwood Woodset
    public static final RegistryObject<Block> CAERULWOOD_PLANKS = registerBlock("caerulwood_planks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.CRIMSON_PLANKS).strength(2.4F).sound(SoundType.WOOD)), BPItemGroup.BioplethoraItemItemGroup);
    public static final RegistryObject<RotatedPillarBlock> CAERULWOOD_LOG = registerBlock("caerulwood_log", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.CRIMSON_HYPHAE).strength(2.4F).sound(SoundType.WOOD)), BPItemGroup.BioplethoraItemItemGroup);
    public static final RegistryObject<RotatedPillarBlock> STRIPPED_CAERULWOOD_LOG = registerBlock("stripped_caerulwood_log", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(BPBlocks.CAERULWOOD_LOG.get())), BPItemGroup.BioplethoraItemItemGroup);
    public static final RegistryObject<RotatedPillarBlock> CAERULWOOD_WOOD = registerBlock("caerulwood_wood", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.CRIMSON_HYPHAE).strength(2.4F).sound(SoundType.WOOD)), BPItemGroup.BioplethoraItemItemGroup);
    public static final RegistryObject<RotatedPillarBlock> STRIPPED_CAERULWOOD_WOOD = registerBlock("stripped_caerulwood_wood", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(BPBlocks.CAERULWOOD_WOOD.get())), BPItemGroup.BioplethoraItemItemGroup);

    public static final RegistryObject<LeavesBlock> CAERULWOOD_LEAVES = registerBlock("caerulwood_leaves", () -> new BPLeavesBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LEAVES).sound(SoundType.GRASS).noOcclusion()), BPItemGroup.BioplethoraItemItemGroup);
    public static final RegistryObject<SaplingBlock> CAERULWOOD_SAPLING = registerBlock("caerulwood_sapling", () -> new BPSaplingBlock(BioPlantType.CAERI, new BPNBTTrees.CaerulwoodNBTTree(), BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING).noCollission()), BPItemGroup.BioplethoraItemItemGroup);

    public static final RegistryObject<FenceBlock> CAERULWOOD_FENCE = registerBlock("caerulwood_fence", () -> new FenceBlock(BlockBehaviour.Properties.copy(BPBlocks.CAERULWOOD_PLANKS.get())), BPItemGroup.BioplethoraItemItemGroup);
    public static final RegistryObject<FenceGateBlock> CAERULWOOD_FENCE_GATE = registerBlock("caerulwood_fence_gate", () -> new FenceGateBlock(BlockBehaviour.Properties.copy(BPBlocks.CAERULWOOD_PLANKS.get())), BPItemGroup.BioplethoraItemItemGroup);
    public static final RegistryObject<StandingSignBlock> CAERULWOOD_SIGN = registerBlock("caerulwood_sign", () -> new BPStandingSignBlock(BlockBehaviour.Properties.copy(BPBlocks.CAERULWOOD_PLANKS.get()), BPWoodTypes.CAERULWOOD), null, false);
    public static final RegistryObject<WallSignBlock> CAERULWOOD_WALL_SIGN = registerBlock("caerulwood_wall_sign", () -> new BPWallSignBlock(BlockBehaviour.Properties.copy(BPBlocks.CAERULWOOD_PLANKS.get()), BPWoodTypes.CAERULWOOD), null, false);
    public static final RegistryObject<PressurePlateBlock> CAERULWOOD_PRESSURE_PLATE = registerBlock("caerulwood_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.copy(BPBlocks.CAERULWOOD_PLANKS.get())), BPItemGroup.BioplethoraItemItemGroup);
    public static final RegistryObject<DoorBlock> CAERULWOOD_DOOR = registerBlock("caerulwood_door", () -> new DoorBlock(BlockBehaviour.Properties.copy(BPBlocks.CAERULWOOD_PLANKS.get())), BPItemGroup.BioplethoraItemItemGroup);
    public static final RegistryObject<TrapDoorBlock> CAERULWOOD_TRAPDOOR = registerBlock("caerulwood_trapdoor", () -> new TrapDoorBlock(BlockBehaviour.Properties.copy(BPBlocks.CAERULWOOD_PLANKS.get())), BPItemGroup.BioplethoraItemItemGroup);
    public static final RegistryObject<WoodButtonBlock> CAERULWOOD_BUTTON = registerBlock("caerulwood_button", () -> new WoodButtonBlock(BlockBehaviour.Properties.copy(BPBlocks.CAERULWOOD_PLANKS.get())), BPItemGroup.BioplethoraItemItemGroup);
    public static final RegistryObject<StairBlock> CAERULWOOD_STAIRS = registerBlock("caerulwood_stairs", () -> new StairBlock(() -> BPBlocks.CAERULWOOD_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(BPBlocks.CAERULWOOD_PLANKS.get())), BPItemGroup.BioplethoraItemItemGroup);
    public static final RegistryObject<SlabBlock> CAERULWOOD_SLAB = registerBlock("caerulwood_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(BPBlocks.CAERULWOOD_PLANKS.get())), BPItemGroup.BioplethoraItemItemGroup);

    public static final RegistryObject<Item> CAERULWOOD_SIGN_ITEM = BLOCK_ITEMS.register("caerulwood_sign", () -> new SignItem(new Item.Properties().stacksTo(16).tab(BPItemGroup.BioplethoraItemItemGroup), BPBlocks.CAERULWOOD_SIGN.get(), BPBlocks.CAERULWOOD_WALL_SIGN.get()));
    public static final RegistryObject<Item> CAERULWOOD_BOAT = BLOCK_ITEMS.register("caerulwood_boat", () -> new BPBoatItem(new Item.Properties().stacksTo(1).tab(BPItemGroup.BioplethoraItemItemGroup), "caerulwood"));

    // End Plants
    public static final RegistryObject<Block> IRION_GRASS = registerBlock("irion_grass", () -> new BPPlantBlock(BioPlantType.CAERI, BioPlantShape.SIMPLE_PLANT,
            BlockBehaviour.Properties.copy(Blocks.TALL_GRASS).noCollission()), BPItemGroup.BioplethoraItemItemGroup);
    public static final RegistryObject<Block> IRION_TALL_GRASS = registerBlock("irion_tall_grass", () -> new BPDoublePlantBlock(BioPlantType.CAERI, BioPlantShape.SIMPLE_PLANT,
            BlockBehaviour.Properties.copy(BPBlocks.IRION_GRASS.get()).noCollission()), BPItemGroup.BioplethoraItemItemGroup);

    public static final RegistryObject<Block> CHORUS_IDON = registerBlock("chorus_idon", () -> new BPPlantBlock(BioPlantType.CHORUS, BioPlantShape.SIMPLE_PLANT,
            BlockBehaviour.Properties.of(Material.REPLACEABLE_FIREPROOF_PLANT).sound(SoundType.TWISTING_VINES).strength(0.3F).lightLevel(b -> 7).emissiveRendering((a, b, c) -> true).instabreak().noCollission()), BPItemGroup.BioplethoraItemItemGroup);
    public static final RegistryObject<Block> CHORUS_IDE_FAN = registerBlock("chorus_ide_fan", () -> new BPIdeFanBlock(BioPlantType.CHORUS, BioPlantShape.IDE_FAN,
            BlockBehaviour.Properties.of(Material.PLANT).sound(SoundType.TWISTING_VINES).strength(0.3F).instabreak().lightLevel(b -> b.getValue(BPIdeFanBlock.BUDDED) ? 2 : 4).noCollission()), BPItemGroup.BioplethoraItemItemGroup);

    public static final RegistryObject<Block> ENREDE_CORSASCILE = registerBlock("enrede_corsascile", () -> new BPWaterDoublePlantBlock(BioPlantType.CHORUS, BioPlantShape.SIMPLE_PLANT,
            BlockBehaviour.Properties.copy(BPBlocks.CHORUS_IDON.get()).noCollission()), BPItemGroup.BioplethoraItemItemGroup);
    public static final RegistryObject<Block> OCHAIM_PURPLE = registerBlock("ochaim_purple", () -> new BPWaterPlantBlock(BioPlantType.CHORUS, BioPlantShape.SIMPLE_PLANT,
            BlockBehaviour.Properties.of(Material.REPLACEABLE_FIREPROOF_PLANT).sound(SoundType.TWISTING_VINES).strength(0.3F).lightLevel(b -> 6).emissiveRendering((a, b, c) -> true).instabreak().noCollission()), BPItemGroup.BioplethoraItemItemGroup);
    public static final RegistryObject<Block> OCHAIM_RED = registerBlock("ochaim_red", () -> new BPWaterPlantBlock(BioPlantType.CHORUS, BioPlantShape.SIMPLE_PLANT,
            BlockBehaviour.Properties.of(Material.REPLACEABLE_FIREPROOF_PLANT).sound(SoundType.TWISTING_VINES).strength(0.3F).lightLevel(b -> 6).emissiveRendering((a, b, c) -> true).instabreak().noCollission()), BPItemGroup.BioplethoraItemItemGroup);
    public static final RegistryObject<Block> OCHAIM_GREEN = registerBlock("ochaim_green", () -> new BPWaterPlantBlock(BioPlantType.CHORUS, BioPlantShape.SIMPLE_PLANT,
            BlockBehaviour.Properties.of(Material.REPLACEABLE_FIREPROOF_PLANT).sound(SoundType.TWISTING_VINES).strength(0.3F).lightLevel(b -> 6).emissiveRendering((a, b, c) -> true).instabreak().noCollission()), BPItemGroup.BioplethoraItemItemGroup);

    public static final RegistryObject<Block> ENREDE_KELP = registerBlock("enrede_kelp", () -> new EnredeKelpTopBlock(BlockBehaviour.Properties.copy(Blocks.KELP).noCollission()), BPItemGroup.BioplethoraItemItemGroup);
    public static final RegistryObject<Block> ENREDE_KELP_PLANT = registerBlock("enrede_kelp_plant", () -> new EnredeKelpBlock(BlockBehaviour.Properties.copy(Blocks.KELP_PLANT).noCollission()), null, false);

    public static final RegistryObject<Block> AZURLIA = registerBlock("azurlia", () -> new BPReversePlantBlock(BioPlantType.CAERULWOOD_TREE, BioPlantShape.SIMPLE_PLANT,
            BlockBehaviour.Properties.copy(BPBlocks.CAERULWOOD_LEAVES.get()).lightLevel((block) -> 12).noCollission().emissiveRendering((n, e, d) -> true)), BPItemGroup.BioplethoraItemItemGroup);

    public static final RegistryObject<Block> CHORUS_MYCHRODEGIA_PART = registerBlock("chorus_mychrodegia_part", () -> new BPReversePlantBlock(BioPlantType.MYCHRODEGIA, BioPlantShape.SIMPLE_PLANT,
            BlockBehaviour.Properties.copy(BPBlocks.CAERULWOOD_LEAVES.get()).lightLevel((block) -> 7).noCollission().emissiveRendering((n, e, d) -> true)), BPItemGroup.BioplethoraItemItemGroup);

    public static final RegistryObject<Block> ARTAIRIUS = registerBlock("artairius", () -> new BPDoublePlantBlock(BioPlantType.CAERI, BioPlantShape.SIMPLE_PLANT,
            BlockBehaviour.Properties.copy(BPBlocks.IRION_GRASS.get()).lightLevel((block) -> block.getValue(DoublePlantBlock.HALF) == DoubleBlockHalf.UPPER ? 13 : 5).noCollission()), BPItemGroup.BioplethoraItemItemGroup);

    public static final RegistryObject<Block> FROSTEM = registerBlock("frostem", () -> new FrostemBlock(BioPlantType.END_ISLANDS_ICE, BioPlantShape.SIMPLE_PLANT,
            BlockBehaviour.Properties.of(Material.ICE, DyeColor.LIGHT_BLUE).strength(0.25F).sound(SoundType.GLASS).noCollission()), BPItemGroup.BioplethoraItemItemGroup);

    public static final RegistryObject<Block> SPINXELTHORN = registerBlock("spinxelthorn", () -> new BPVinesTopBlock.SpinxelthornTopBlock(
            BlockBehaviour.Properties.of(Material.STONE, DyeColor.WHITE).strength(0.7F).sound(SoundType.STONE).noCollission()), BPItemGroup.BioplethoraItemItemGroup);
    public static final RegistryObject<Block> SPINXELTHORN_PLANT = registerBlock("spinxelthorn_plant", () -> new BPVinesBlock.SpinxelthornBlock(
            BlockBehaviour.Properties.of(Material.STONE, DyeColor.WHITE).strength(0.7F).sound(SoundType.STONE).noCollission()), null, false);

    public static final RegistryObject<Block> GLACYNTH = registerBlock("glacynth", () -> new BPVinesTopBlock.GlacynthTopBlock(
            BlockBehaviour.Properties.of(Material.ICE, DyeColor.LIGHT_BLUE).strength(0.25F).sound(SoundType.GLASS).noCollission()), BPItemGroup.BioplethoraItemItemGroup);
    public static final RegistryObject<Block> GLACYNTH_PLANT = registerBlock("glacynth_plant", () -> new BPVinesBlock.GlacynthBlock(
            BlockBehaviour.Properties.of(Material.ICE, DyeColor.LIGHT_BLUE).strength(0.25F).sound(SoundType.GLASS).noCollission()), null, false);

    public static final RegistryObject<Block> CELESTIA_BUD = registerBlock("celestia_bud", () -> new BPVinesTopBlock.CelestiaBudTopBlock(
            BlockBehaviour.Properties.of(Material.STONE, DyeColor.LIGHT_BLUE).strength(0.25F).sound(SoundType.GILDED_BLACKSTONE).noOcclusion()), BPItemGroup.BioplethoraItemItemGroup);
    public static final RegistryObject<Block> CELESTIA_BUD_PLANT = registerBlock("celestia_bud_plant", () -> new BPVinesBlock.CelestiaBudBlock(
            BlockBehaviour.Properties.of(Material.STONE, DyeColor.LIGHT_BLUE).strength(0.25F).sound(SoundType.GILDED_BLACKSTONE).noCollission()), null, false);

    // End Blocks
    public static final RegistryObject<Block> ENDURION = registerBlock("endurion", () -> new Block(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_MAGENTA)
            .strength(1.6F).sound(SoundType.GILDED_BLACKSTONE)), BPItemGroup.BioplethoraItemItemGroup);
    public static final RegistryObject<Block> TENEDEBRIS = registerBlock("tenedebris", () -> new Block(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_BLACK)
            .strength(1.8F).sound(SoundType.GILDED_BLACKSTONE)), BPItemGroup.BioplethoraItemItemGroup);
    public static final RegistryObject<Block> IRION = registerBlock("irion", () -> new Block(BlockBehaviour.Properties.of(Material.GRASS, MaterialColor.COLOR_LIGHT_BLUE)
            .strength(1.8F).sound(SoundType.NETHER_SPROUTS)), BPItemGroup.BioplethoraItemItemGroup);
    public static final RegistryObject<Block> CRYOSOIL = registerBlock("cryosoil", () -> new Block(BlockBehaviour.Properties.of(Material.GRASS, MaterialColor.COLOR_BROWN)
            .strength(1.8F).sound(SoundType.GRAVEL)), BPItemGroup.BioplethoraItemItemGroup);

    public static final RegistryObject<Block> CYRA = registerBlock("cyra", () -> new Block(BlockBehaviour.Properties.of(Material.GRASS, MaterialColor.COLOR_BLUE)
            .strength(1.0F).sound(SoundType.NETHER_SPROUTS)), BPItemGroup.BioplethoraItemItemGroup);

    public static final RegistryObject<Block> CHORUS_MYCHRODEGIA = registerBlock("chorus_mychrodegia", () -> new Block(BlockBehaviour.Properties.of(Material.GRASS, MaterialColor.COLOR_PINK)
            .strength(1.0F).lightLevel((block) -> 5).sound(SoundType.NETHER_SPROUTS).emissiveRendering((n, e, d) -> true)), BPItemGroup.BioplethoraItemItemGroup);

    public static final RegistryObject<Block> BYRSS_FRUIT_BLOCK = registerBlock("byrss_fruit_block", () -> new Block(BlockBehaviour.Properties.of(Material.PLANT, MaterialColor.COLOR_LIGHT_BLUE)
            .strength(1.8F).sound(SoundType.WOOD).noOcclusion().lightLevel((block) -> 10).emissiveRendering((n, e, d) -> true)), BPItemGroup.BioplethoraItemItemGroup);
    public static final RegistryObject<Block> BYRSS_LANTERN_PLANT = registerBlock("byrss_lantern_plant", () -> new BPLanternPlantBlock(BYRSS_FRUIT_BLOCK.get(), BioPlantShape.SIMPLE_PLANT,
            BlockBehaviour.Properties.of(Material.GRASS, MaterialColor.COLOR_LIGHT_BLUE).strength(1.0F).sound(SoundType.NETHER_SPROUTS).noOcclusion().lightLevel((block) -> 5).emissiveRendering((n, e, d) -> true)), BPItemGroup.BioplethoraItemItemGroup);

    public static final RegistryObject<Block> CHORUS_CITRUS_BLOCK = registerBlock("chorus_citrus_block", () -> new Block(BlockBehaviour.Properties.of(Material.PLANT, MaterialColor.COLOR_PURPLE)
            .strength(1.8F).sound(SoundType.WOOD).noOcclusion().lightLevel((block) -> 10).emissiveRendering((n, e, d) -> true)), BPItemGroup.BioplethoraItemItemGroup);
    public static final RegistryObject<Block> CHORUS_LANTERN_PLANT = registerBlock("chorus_lantern_plant", () -> new BPLanternPlantBlock(CHORUS_CITRUS_BLOCK.get(), BioPlantShape.SIMPLE_PLANT,
            BlockBehaviour.Properties.of(Material.GRASS, MaterialColor.COLOR_PURPLE).strength(1.0F).sound(SoundType.NETHER_SPROUTS).noOcclusion().lightLevel((block) -> 5).emissiveRendering((n, e, d) -> true)), BPItemGroup.BioplethoraItemItemGroup);

    // Alphanum Set
    public static final RegistryObject<Block> ALPHANUM = registerBlock("alphanum", () -> new Block(BlockBehaviour.Properties.of(Material.STONE)
            .strength(50.0F, 1200.0F).requiresCorrectToolForDrops().sound(SoundType.NETHER_GOLD_ORE)), BPItemGroup.BioplethoraItemItemGroup);
    public static final RegistryObject<Block> ALPHANUM_BRICKS = registerBlock("alphanum_bricks", () ->
            new Block(BlockBehaviour.Properties.copy(BPBlocks.ALPHANUM.get())), BPItemGroup.BioplethoraItemItemGroup);
    public static final RegistryObject<Block> POLISHED_ALPHANUM = registerBlock("polished_alphanum", () ->
            new Block(BlockBehaviour.Properties.copy(BPBlocks.ALPHANUM.get())), BPItemGroup.BioplethoraItemItemGroup);
    public static final RegistryObject<RotatedPillarBlock> ALPHANUM_PILLAR = registerBlock("alphanum_pillar", () ->
            new RotatedPillarBlock(BlockBehaviour.Properties.copy(BPBlocks.ALPHANUM.get())), BPItemGroup.BioplethoraItemItemGroup);

    public static final RegistryObject<RotatedPillarBlock> ALPHANUM_NUCLEUS = registerBlock("alphanum_nucleus", () ->
            new AlphanumNucleusBlock(BlockBehaviour.Properties.copy(BPBlocks.ALPHANUM.get()).lightLevel((block) -> 8).emissiveRendering((t, s, a) -> true)), BPItemGroup.BioplethoraItemItemGroup);

    public static final RegistryObject<StairBlock> ALPHANUM_STAIRS = registerBlock("alphanum_stairs", () ->
            new StairBlock(() -> BPBlocks.ALPHANUM.get().defaultBlockState(), BlockBehaviour.Properties.copy(BPBlocks.ALPHANUM.get())), BPItemGroup.BioplethoraItemItemGroup);
    public static final RegistryObject<WallBlock> ALPHANUM_WALL = registerBlock("alphanum_wall", () ->
            new WallBlock(BlockBehaviour.Properties.copy(BPBlocks.ALPHANUM.get())), BPItemGroup.BioplethoraItemItemGroup);
    public static final RegistryObject<SlabBlock> ALPHANUM_SLAB = registerBlock("alphanum_slab", () ->
            new SlabBlock(BlockBehaviour.Properties.copy(BPBlocks.ALPHANUM.get())), BPItemGroup.BioplethoraItemItemGroup);
    public static final RegistryObject<StairBlock> ALPHANUM_STAIRS_BRICKS = registerBlock("alphanum_brick_stairs", () ->
            new StairBlock(() -> (BPBlocks.ALPHANUM_BRICKS.get().defaultBlockState()), BlockBehaviour.Properties.copy(BPBlocks.ALPHANUM.get())), BPItemGroup.BioplethoraItemItemGroup);
    public static final RegistryObject<WallBlock> ALPHANUM_WALL_BRICKS = registerBlock("alphanum_brick_wall", () ->
            new WallBlock(BlockBehaviour.Properties.copy(BPBlocks.ALPHANUM.get())), BPItemGroup.BioplethoraItemItemGroup);
    public static final RegistryObject<SlabBlock> ALPHANUM_SLAB_BRICKS = registerBlock("alphanum_brick_slab", () ->
            new SlabBlock(BlockBehaviour.Properties.copy(BPBlocks.ALPHANUM.get())), BPItemGroup.BioplethoraItemItemGroup);
    public static final RegistryObject<StairBlock> POLISHED_ALPHANUM_STAIRS = registerBlock("polished_alphanum_stairs", () ->
            new StairBlock(() -> BPBlocks.POLISHED_ALPHANUM.get().defaultBlockState(), BlockBehaviour.Properties.copy(BPBlocks.ALPHANUM.get())), BPItemGroup.BioplethoraItemItemGroup);
    public static final RegistryObject<WallBlock> POLISHED_ALPHANUM_WALL = registerBlock("polished_alphanum_wall", () ->
            new WallBlock(BlockBehaviour.Properties.copy(BPBlocks.ALPHANUM.get())), BPItemGroup.BioplethoraItemItemGroup);
    public static final RegistryObject<SlabBlock> POLISHED_ALPHANUM_SLAB = registerBlock("polished_alphanum_slab", () ->
            new SlabBlock(BlockBehaviour.Properties.copy(BPBlocks.ALPHANUM.get())), BPItemGroup.BioplethoraItemItemGroup);

    // todo: Petrawood set for Rocky Woodlands biome
    /*
    public static final RegistryObject<RotatedPillarBlock> PETRAWOOD_LOG = registerBlock("petrawood_log", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.CRIMSON_HYPHAE).strength(2.4F).sound(SoundType.WOOD).noOcclusion()), null);
    public static final RegistryObject<RotatedPillarBlock> PETRAWOOD_WOOD = registerBlock("petrawood_wood", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.CRIMSON_HYPHAE).strength(2.4F).sound(SoundType.WOOD).noOcclusion()), null);
    public static final RegistryObject<RotatedPillarBlock> STRIPPED_PETRAWOOD_LOG = registerBlock("stripped_petrawood_log", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(BPBlocks.PETRAWOOD_LOG.get()).strength(2.4F).sound(SoundType.WOOD).noOcclusion()), null);
    public static final RegistryObject<RotatedPillarBlock> STRIPPED_PETRAWOOD_WOOD = registerBlock("stripped_petrawood_wood", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(BPBlocks.PETRAWOOD_WOOD.get()).strength(2.4F).sound(SoundType.WOOD).noOcclusion()), null);
    public static final RegistryObject<Block> PETRAWOOD_PLANKS = registerBlock("petrawood_planks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.CRIMSON_PLANKS).strength(2.4F).sound(SoundType.WOOD).noOcclusion()), null);
    public static final RegistryObject<LeavesBlock> PETRAWOOD_LEAVES = registerBlock("petrawood_leaves", () -> new LeavesBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LEAVES).strength(2.4F).sound(SoundType.GRASS).noOcclusion()), null);
    public static final RegistryObject<SaplingBlock> PETRAWOOD_SAPLING = registerBlock("petrawood_sapling", () -> new SaplingBlock(new OakTree(), BlockBehaviour.Properties.of(Material.PLANT).strength(2.4F).sound(SoundType.GRASS).noOcclusion()), null);

    public static final RegistryObject<FenceBlock> PETRAWOOD_FENCE = registerBlock("petrawood_fence", () -> new FenceBlock(BlockBehaviour.Properties.copy(BPBlocks.PETRAWOOD_PLANKS.get())), null);
    public static final RegistryObject<FenceGateBlock> PETRAWOOD_FENCE_GATE = registerBlock("petrawood_fence_gate", () -> new FenceGateBlock(BlockBehaviour.Properties.copy(BPBlocks.PETRAWOOD_PLANKS.get())), null);
    public static final RegistryObject<SlabBlock> PETRAWOOD_SLAB = registerBlock("petrawood_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(BPBlocks.PETRAWOOD_PLANKS.get())), null);
    public static final RegistryObject<PressurePlateBlock> PETRAWOOD_PRESSURE_PLATE = registerBlock("petrawood_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.copy(BPBlocks.PETRAWOOD_PLANKS.get())), null);
    public static final RegistryObject<StairBlock> PETRAWOOD_STAIRS = registerBlock("petrawood_stairs", () -> new StairBlock(() -> BPBlocks.PETRAWOOD_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(BPBlocks.PETRAWOOD_PLANKS.get())), null);
    public static final RegistryObject<WoodButtonBlock> PETRAWOOD_BUTTON = registerBlock("petrawood_button", () -> new WoodButtonBlock(BlockBehaviour.Properties.copy(BPBlocks.PETRAWOOD_PLANKS.get())), null);
    public static final RegistryObject<StandingSignBlock> PETRAWOOD_SIGN = registerBlock("petrawood_sign", () -> new StandingSignBlock(BlockBehaviour.Properties.copy(BPBlocks.PETRAWOOD_PLANKS.get()), BPWoodTypes.PETRAWOOD), null);
    public static final RegistryObject<WallSignBlock> PETRAWOOD_WALL_SIGN = registerBlock("petrawood_wall_sign", () -> new WallSignBlock(BlockBehaviour.Properties.copy(BPBlocks.PETRAWOOD_PLANKS.get()), BPWoodTypes.PETRAWOOD), null);
    public static final RegistryObject<DoorBlock> PETRAWOOD_DOOR = registerBlock("petrawood_door", () -> new DoorBlock(BlockBehaviour.Properties.copy(BPBlocks.PETRAWOOD_PLANKS.get())), null);
    public static final RegistryObject<TrapDoorBlock> PETRAWOOD_TRAPDOOR = registerBlock("petrawood_trapdoor", () -> new TrapDoorBlock(BlockBehaviour.Properties.copy(BPBlocks.PETRAWOOD_PLANKS.get())), null);
    */

    //=================================================================================
    //                       REGULAR BLOCK CONSTRUCTORS
    //=================================================================================
    public static <B extends Block> RegistryObject<B> registerBlock(String name, Supplier<? extends B> supplier, CreativeModeTab itemGroup) {
        return registerBlock(name, supplier, itemGroup, true);
    }

    public static <B extends Block> RegistryObject<B> registerBlock(String name, Supplier<? extends B> supplier, CreativeModeTab itemGroup, boolean generateItem) {
        return registerBlock(name, supplier, itemGroup, 64, generateItem);
    }

    public static <B extends Block> RegistryObject<B> registerBlock(String name, Supplier<? extends B> supplier, CreativeModeTab itemGroup, int stackSize, boolean generateItem) {
        RegistryObject<B> block = BPBlocks.BLOCKS.register(name, supplier);
        if (generateItem)
            BLOCK_ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(itemGroup).stacksTo(stackSize)));
        return block;
    }

    //=================================================================================
    //                      FIRE RESISTANT BLOCK CONSTRUCTORS
    //=================================================================================
    public static <B extends Block> RegistryObject<B> registerFireResBlock(String name, Supplier<? extends B> supplier, CreativeModeTab itemGroup) {
        return registerFireResBlock(name, supplier, itemGroup, true);
    }

    public static <B extends Block> RegistryObject<B> registerFireResBlock(String name, Supplier<? extends B> supplier, CreativeModeTab itemGroup, boolean generateItem) {
        return registerFireResBlock(name, supplier, itemGroup, 64, generateItem);
    }

    public static <B extends Block> RegistryObject<B> registerFireResBlock(String name, Supplier<? extends B> supplier, CreativeModeTab itemGroup, int stackSize, boolean generateItem) {
        RegistryObject<B> block = BPBlocks.BLOCKS.register(name, supplier);
        if (generateItem)
            BLOCK_ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(itemGroup).stacksTo(stackSize).fireResistant()));
        return block;
    }
}