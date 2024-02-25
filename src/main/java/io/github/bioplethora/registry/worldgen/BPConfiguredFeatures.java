package io.github.bioplethora.registry.worldgen;

import java.util.List;
import java.util.Locale;

import com.google.common.collect.ImmutableList;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.registry.BPBlocks;
import io.github.bioplethora.world.BPVanillaBiomeFeatureGeneration;
import io.github.bioplethora.world.featureconfigs.ExpandedLakeFeatureConfig;
import io.github.bioplethora.world.featureconfigs.FleignariteSplotchConfig;
import io.github.bioplethora.world.featureconfigs.PendentBlocksFeatureConfig;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.feature.configurations.BlockPileConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.BlockStateConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.DeltaFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.ProbabilityFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class BPConfiguredFeatures {

    public static final DeferredRegister<ConfiguredFeature<?, ?>> CONFIGURED_FEATURES = DeferredRegister.create(Registry.CONFIGURED_FEATURE_REGISTRY, Bioplethora.MOD_ID);
    
    public static final RegistryObject<ConfiguredFeature<?, ?>> SOUL_MINISHROOM =  CONFIGURED_FEATURES.register("soul_minishroom", () -> new ConfiguredFeature<>(Feature.RANDOM_PATCH, 
    		new RandomPatchConfiguration(64, 7, 3, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(BPBlocks.SOUL_MINISHROOM.get()))))));
    
    public static final RegistryObject<ConfiguredFeature<?, ?>> SOUL_BIGSHROOM =  CONFIGURED_FEATURES.register("soul_bigshroom", () -> new ConfiguredFeature<>(Feature.RANDOM_PATCH, 
    		new RandomPatchConfiguration(64, 7, 3, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(BPBlocks.SOUL_BIGSHROOM.get()))))));
    
    public static final RegistryObject<ConfiguredFeature<?, ?>> KYRIA =  CONFIGURED_FEATURES.register("kyria", () -> new ConfiguredFeature<>(Feature.RANDOM_PATCH, 
    		new RandomPatchConfiguration(64, 7, 3, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(BPBlocks.KYRIA.get()))))));
    
    public static final RegistryObject<ConfiguredFeature<?, ?>> KYRIA_BELINE =  CONFIGURED_FEATURES.register("kyria_beline", () -> new ConfiguredFeature<>(Feature.RANDOM_PATCH, 
    		new RandomPatchConfiguration(64, 7, 3, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(BPBlocks.KYRIA_BELINE.get()))))));
    
    public static final RegistryObject<ConfiguredFeature<?, ?>> KYRIA_IDE_FAN =  CONFIGURED_FEATURES.register("kyria_ide_fan", () -> new ConfiguredFeature<>(Feature.RANDOM_PATCH, 
    		new RandomPatchConfiguration(64, 7, 3, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(BPBlocks.KYRIA_IDE_FAN.get()))))));

    public static final RegistryObject<ConfiguredFeature<?, ?>> SOUL_SPROUTS = CONFIGURED_FEATURES.register("soul_sprouts", () -> new ConfiguredFeature<>(Feature.RANDOM_PATCH, 
    		new RandomPatchConfiguration(64, 7, 3, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(BPBlocks.SOUL_SPROUTS.get()))))));
    
    public static final RegistryObject<ConfiguredFeature<?, ?>> SOUL_TALL_GRASS =  CONFIGURED_FEATURES.register("soul_tall_grass", () -> new ConfiguredFeature<>(Feature.RANDOM_PATCH, 
    		new RandomPatchConfiguration(64, 7, 3, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(BPBlocks.SOUL_TALL_GRASS.get()))))));
    
	public static final RegistryObject<ConfiguredFeature<?, ?>> GLACYNTH = CONFIGURED_FEATURES.register("glacynth", () -> makePendentConfig("glacynth", 
            Blocks.ICE, BPBlocks.GLACYNTH_PLANT.get(), BPBlocks.GLACYNTH.get(),
            ImmutableList.of(Blocks.ICE, Blocks.BLUE_ICE, Blocks.PACKED_ICE, Blocks.FROSTED_ICE),
            3, 9));
	
	public static final RegistryObject<ConfiguredFeature<?, ?>> PINK_TWI = CONFIGURED_FEATURES.register("pink_twi", () -> makePendentConfig("pink_twi", 
            BPBlocks.ENIVILE_LEAVES_PINK.get(), BPBlocks.PINK_TWI_PLANT.get(), BPBlocks.PINK_TWI.get(),
            ImmutableList.of(Blocks.SOUL_SOIL, Blocks.SOUL_SAND, Blocks.NETHERRACK, Blocks.BLACKSTONE),
            1, 6));
	
    public static final RegistryObject<ConfiguredFeature<?, ?>> RED_TWI = CONFIGURED_FEATURES.register("red_twi", () -> makePendentConfig("red_twi", 
            BPBlocks.ENIVILE_LEAVES_RED.get(), BPBlocks.RED_TWI_PLANT.get(), BPBlocks.RED_TWI.get(),
            ImmutableList.of(Blocks.SOUL_SOIL, Blocks.SOUL_SAND, Blocks.NETHERRACK, Blocks.BLACKSTONE),
            1, 8));
    
    public static final RegistryObject<ConfiguredFeature<?, ?>> SPINXELTHORN = CONFIGURED_FEATURES.register("spinxelthorn", () -> makePendentConfig("spinxelthorn", 
            Blocks.END_STONE, BPBlocks.SPINXELTHORN_PLANT.get(), BPBlocks.SPINXELTHORN.get(),
            ImmutableList.of(Blocks.END_STONE),
            1, 8));
    
    public static final RegistryObject<ConfiguredFeature<?, ?>> SPIRIT_DANGLER = CONFIGURED_FEATURES.register("spirit_dangler", () -> makePendentConfig("spirit_dangler", 
            Blocks.SOUL_SOIL, BPBlocks.SPIRIT_DANGLER_PLANT.get(), BPBlocks.SPIRIT_DANGLER.get(),
            ImmutableList.of(Blocks.SOUL_SOIL, Blocks.SOUL_SAND, Blocks.NETHERRACK, Blocks.BLACKSTONE),
            1, 8));
    
    public static final RegistryObject<ConfiguredFeature<?, ?>> LAVA_SPIRE = CONFIGURED_FEATURES.register("lava_spire", () -> new ConfiguredFeature<>(BPFeatures.LAVA_EDGE_CLUSTER.get(), new RandomPatchConfiguration(64, 7, 3, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(BPBlocks.LAVA_SPIRE.get()))))));
    
    public static final RegistryObject<ConfiguredFeature<?, ?>> FLEIGNARITE_REMAINS = CONFIGURED_FEATURES.register("fleignarite_remains", () -> new ConfiguredFeature<>(BPFeatures.FLEIGNARITE_PATCH.get(),
    		new FleignariteSplotchConfig(BPBlocks.FLEIGNARITE_REMAINS.get().defaultBlockState(),
                    BPBlocks.FLEIGNARITE_SPLOTCH.get().defaultBlockState(),
                    BPVanillaBiomeFeatureGeneration.stoneBlockstates(),
                    ImmutableList.of(Blocks.CAVE_AIR.defaultBlockState(), Blocks.AIR.defaultBlockState()),
                    ImmutableList.of(Blocks.CAVE_AIR.defaultBlockState(), Blocks.AIR.defaultBlockState()))));
    
    public static final RegistryObject<ConfiguredFeature<?, ?>> FLEIGNARITE_VINES = CONFIGURED_FEATURES.register("fleignarite_vines", () -> new ConfiguredFeature<>(BPFeatures.PENDENT_FLEIGNARITE.get(),
    		new PendentBlocksFeatureConfig.Builder()
                    .setTopBlock(Blocks.STONE).setMiddleBlock(BPBlocks.FLEIGNARITE_VINES_PLANT.get())
                    .setFruitedBlock(BPBlocks.FLEIGNARITE_VINES_PLANT.get()).setEndBlock(BPBlocks.FLEIGNARITE_VINES.get().defaultBlockState().setValue(GrowingPlantHeadBlock.AGE, 23))
                    .setWhitelist(BPVanillaBiomeFeatureGeneration.stoneBlocks())
                    .setMinLength(1)
                    .setMaxLength(2)
                    .build()));
    
    public static final RegistryObject<ConfiguredFeature<?, ?>> BASALT_SPELEOTHERM = CONFIGURED_FEATURES.register("basalt_speleotherm", () -> makeFruitablePendentConfig("basalt_speleotherm", 
            Blocks.BASALT, BPBlocks.BASALT_SPELEOTHERM_PLANT.get(), BPBlocks.FIERY_BASALT_SPELEOTHERM.get(), BPBlocks.BASALT_SPELEOTHERM.get(),
            ImmutableList.of(Blocks.BASALT, Blocks.NETHERRACK, Blocks.BLACKSTONE),
            1, 8));

    public static final RegistryObject<ConfiguredFeature<?, ?>> THONTUS_THISTLE = CONFIGURED_FEATURES.register("thontus_thistle", () -> makeFruitablePendentConfig("thontus_thistle", 
            Blocks.NETHERRACK, BPBlocks.THONTUS_THISTLE_PLANT.get(), BPBlocks.BERRIED_THONTUS_THISTLE.get(), BPBlocks.THONTUS_THISTLE.get(),
            ImmutableList.of(Blocks.NETHERRACK, Blocks.BLACKSTONE),
            2, 10));

    public static final RegistryObject<ConfiguredFeature<?, ?>> TURQUOISE_PENDENT = CONFIGURED_FEATURES.register("turquoise_pendent", () -> makeFruitablePendentConfig("turquoise_pendent", 
            Blocks.WARPED_WART_BLOCK, BPBlocks.TURQUOISE_PENDENT_PLANT.get(), BPBlocks.BLOSSOMING_TURQUOISE_PENDENT.get(), BPBlocks.TURQUOISE_PENDENT.get(),
            ImmutableList.of(Blocks.WARPED_WART_BLOCK, Blocks.NETHERRACK, Blocks.BLACKSTONE),
            2, 8));

    public static final RegistryObject<ConfiguredFeature<?, ?>> CERISE_IVY = CONFIGURED_FEATURES.register("cerise_ivy", () -> makeFruitablePendentConfig("cerise_ivy",
            Blocks.NETHER_WART_BLOCK, BPBlocks.CERISE_IVY_PLANT.get(), BPBlocks.SEEDED_CERISE_IVY.get(), BPBlocks.CERISE_IVY.get(),
            ImmutableList.of(Blocks.NETHER_WART_BLOCK, Blocks.NETHERRACK, Blocks.BLACKSTONE),
            1, 8));

    public static final RegistryObject<ConfiguredFeature<?, ?>> SOUL_ETERN = CONFIGURED_FEATURES.register("soul_etern", () -> makeFruitablePendentConfig("soul_etern",
            Blocks.SOUL_SOIL, BPBlocks.SOUL_ETERN_PLANT.get(), BPBlocks.FLOURISHED_SOUL_ETERN.get(), BPBlocks.SOUL_ETERN.get(),
            ImmutableList.of(Blocks.SOUL_SOIL, Blocks.SOUL_SAND, Blocks.NETHERRACK, Blocks.BLACKSTONE),
            2, 10));
    
    public static final RegistryObject<ConfiguredFeature<?, ?>> CHORUS_IDON = CONFIGURED_FEATURES.register("chorus_idon", () -> new ConfiguredFeature<>(Feature.RANDOM_PATCH, 
    		new RandomPatchConfiguration(64, 7, 3, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(BPBlocks.CHORUS_IDON.get()))))));

    public static final RegistryObject<ConfiguredFeature<?, ?>> CHORUS_IDE_FAN = CONFIGURED_FEATURES.register("chorus_ide_fan", () -> new ConfiguredFeature<>(Feature.RANDOM_PATCH, 
    		new RandomPatchConfiguration(64, 7, 3, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(BPBlocks.CHORUS_IDE_FAN.get()))))));
    public static final RegistryObject<ConfiguredFeature<?, ?>> WARPED_DANCER  = CONFIGURED_FEATURES.register("warped_dancer", () -> new ConfiguredFeature<>(Feature.RANDOM_PATCH, 
    		new RandomPatchConfiguration(64, 7, 3, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(BPBlocks.SOUL_SPROUTS.get()))))));
    // End Plants
    /*public static final RegistryObject<ConfiguredFeature<?, ?>> IRION_GRASS = CONFIGURED_FEATURES.register("irion_grass", () -> new ConfiguredFeature<>(new RandomPatchFeature(RandomPatchConfiguration.CODEC), 
    		new RandomPatchConfiguration(64, 7, 3, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(BPBlocks.IRION_GRASS.get()))))));*/

    public static final RegistryObject<ConfiguredFeature<?, ?>> IRION_TALL_GRASS = CONFIGURED_FEATURES.register("irion_tall_grass", () -> new ConfiguredFeature<>(Feature.RANDOM_PATCH, 
    		new RandomPatchConfiguration(64, 7, 3, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(BPBlocks.IRION_TALL_GRASS.get()))))));
    
    public static final RegistryObject<ConfiguredFeature<?, ?>> ARTAIRIUS = CONFIGURED_FEATURES.register("artairius", () -> new ConfiguredFeature<>(Feature.RANDOM_PATCH, 
    		new RandomPatchConfiguration(64, 7, 3, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(BPBlocks.ARTAIRIUS.get()))))));
    
    public static final RegistryObject<ConfiguredFeature<?, ?>> FROSTEM = CONFIGURED_FEATURES.register("frostem", () -> new ConfiguredFeature<>(Feature.RANDOM_PATCH, 
    		new RandomPatchConfiguration(64, 7, 3, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(BPBlocks.FROSTEM.get()))))));
    
    public static final RegistryObject<ConfiguredFeature<?, ?>> CYRA_LAKE = CONFIGURED_FEATURES.register("cyra_lake", () -> new ConfiguredFeature<>(BPFeatures.EXPANDED_LAKE.get(),
            	new ExpandedLakeFeatureConfig.Builder()
                    .setLiquid(Blocks.WATER.defaultBlockState())
                    .setSides(BPBlocks.CYRA.get().defaultBlockState())
                    .setBase(Blocks.OBSIDIAN.defaultBlockState())
                    .build()));

    public static final RegistryObject<ConfiguredFeature<?, ?>> BYRSS_LANTERN_PLANT = CONFIGURED_FEATURES.register("byrss_lantern_plant", () -> new ConfiguredFeature<>(BPFeatures.SINGULAR_BLOCK.get(), new BlockPileConfiguration(BlockStateProvider.simple(BPBlocks.BYRSS_LANTERN_PLANT.get().defaultBlockState()))));
    
    public static final RegistryObject<ConfiguredFeature<?, ?>> BYRSS_LANTERN_PLANT_PATCH = CONFIGURED_FEATURES.register("byrss_lantern_plant_patch", () -> new ConfiguredFeature<>(Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(BPPlacedFeatures.BYRSS_LANTERN_PLANT_PATCH.getHolder().get(), 0.4222667F)), BPPlacedFeatures.BYRSS_LANTERN_PLANT_PATCH.getHolder().get())));
    public static final RegistryObject<ConfiguredFeature<?, ?>> BYRSS_LANTERN_FOREST_PATCH = CONFIGURED_FEATURES.register("byrss_lantern_forest_patch", () -> new ConfiguredFeature<>(Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(BPPlacedFeatures.BYRSS_LANTERN_FOREST_PATCH.getHolder().get(), 0.4446667F)), BPPlacedFeatures.BYRSS_LANTERN_FOREST_PATCH.getHolder().get())));

    public static final RegistryObject<ConfiguredFeature<?, ?>> END_LAND_SPONGE_PATCH_ML = CONFIGURED_FEATURES.register("end_land_sponge_patch_ml", () -> new ConfiguredFeature<>(BPFeatures.END_LAND_SPONGE.get(),
    		new DeltaFeatureConfiguration(Blocks.WATER.defaultBlockState(), BPBlocks.ENDURION.get().defaultBlockState(), UniformInt.of(5, 15), UniformInt.of(2, 4))));
    /*public static final RegistryObject<ConfiguredFeature<?, ?>> END_LAND_SPONGE_PATCH_HL = CONFIGURED_FEATURES.register("end_land_sponge_patch_hl", () -> new ConfiguredFeature<>(BPFeatures.END_LAND_SPONGE.get(),
    		new DeltaFeatureConfiguration(Blocks.WATER.defaultBlockState(), BPBlocks.ENDURION.get().defaultBlockState(), UniformInt.of(7, 19), UniformInt.of(3, 6))));*/

    public static final RegistryObject<ConfiguredFeature<?, ?>> CAERI_CAVERN = CONFIGURED_FEATURES.register("caeri_cavern", () -> new ConfiguredFeature<>(BPFeatures.CAERI_CAVERN.get(), new NoneFeatureConfiguration()));

    public static final RegistryObject<ConfiguredFeature<?, ?>> CHORUS_MYCHRODEGIA = CONFIGURED_FEATURES.register("chorus_plant", () -> new ConfiguredFeature<>(BPFeatures.CHORUS_MYCHRODEGIA.get(), new NoneFeatureConfiguration()));

    public static final RegistryObject<ConfiguredFeature<?, ?>> END_LAND_SPIKE_HL = CONFIGURED_FEATURES.register("end_land_spike_hl", () -> new ConfiguredFeature<>(BPFeatures.END_LAND_SPIKE.get(), new NoneFeatureConfiguration()));
    
    public static final RegistryObject<ConfiguredFeature<?, ?>> END_LAND_SPIKE_PATCH_HL = CONFIGURED_FEATURES.register("end_land_spike_patch_hl", () -> new ConfiguredFeature<>(Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(BPPlacedFeatures.END_LAND_SPIKE_PATCH_HL.getHolder().get(), 0.4446667F)), BPPlacedFeatures.END_LAND_SPIKE_PATCH_HL.getHolder().get())));

    public static final RegistryObject<ConfiguredFeature<?, ?>> END_LAND_SPIKE_ML = CONFIGURED_FEATURES.register("end_land_spike_ml", () -> new ConfiguredFeature<>(BPFeatures.END_LAND_SPIKE.get(), new NoneFeatureConfiguration()));
    public static final RegistryObject<ConfiguredFeature<?, ?>> END_LAND_SPIKE_PATCH_ML = CONFIGURED_FEATURES.register("end_land_spike_patch_ml", () -> new ConfiguredFeature<>(Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(BPPlacedFeatures.END_LAND_SPIKE_PATCH_ML.getHolder().get(), 0.4446667F)), BPPlacedFeatures.END_LAND_SPIKE_PATCH_ML.getHolder().get())));

    public static final RegistryObject<ConfiguredFeature<?, ?>> CHORUS_LANTERN_PLANT = CONFIGURED_FEATURES.register("chorus_lantern_plant", () -> new ConfiguredFeature<>(BPFeatures.SINGULAR_BLOCK.get(), new BlockPileConfiguration(BlockStateProvider.simple(BPBlocks.CHORUS_LANTERN_PLANT.get().defaultBlockState()))));
    
    public static final RegistryObject<ConfiguredFeature<?, ?>> CHORUS_LANTERN_HIGHLANDS_PATCH = CONFIGURED_FEATURES.register("chorus_lantern_highlands_patch", () -> new ConfiguredFeature<>(Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(BPPlacedFeatures.CHORUS_LANTERN_HIGHLANDS_PATCH.getHolder().get(), 0.4022667F)), BPPlacedFeatures.CHORUS_LANTERN_HIGHLANDS_PATCH.getHolder().get())));
    public static final RegistryObject<ConfiguredFeature<?, ?>> CHORUS_LANTERN_MIDLANDS_PATCH = CONFIGURED_FEATURES.register("chorus_lantern_midlands_patch", () -> new ConfiguredFeature<>(Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(BPPlacedFeatures.CHORUS_LANTERN_MIDLANDS_PATCH.getHolder().get(), 0.4224447F)), BPPlacedFeatures.CHORUS_LANTERN_MIDLANDS_PATCH.getHolder().get())));

    public static final RegistryObject<ConfiguredFeature<?, ?>> ENREDE_KELP = CONFIGURED_FEATURES.register("enrede_kelp", () -> new ConfiguredFeature<>(BPFeatures.ENREDE_KELP.get(), new NoneFeatureConfiguration()));

    public static final RegistryObject<ConfiguredFeature<?, ?>> CELESTIA_BUD = CONFIGURED_FEATURES.register("celestia_bud", () -> new ConfiguredFeature<>(BPFeatures.CELESTIA_BUD.get(), new NoneFeatureConfiguration()));

    public static final RegistryObject<ConfiguredFeature<?, ?>> ENREDE_CORSASCILE = CONFIGURED_FEATURES.register("enrede_corsascile", () -> new ConfiguredFeature<>(BPFeatures.CORSASCILE.get(), new ProbabilityFeatureConfiguration(0.3F)));
    public static final RegistryObject<ConfiguredFeature<?, ?>> OCHAIM_PURPLE = CONFIGURED_FEATURES.register("ochaim_purple", () -> new ConfiguredFeature<>(BPFeatures.WATER_PLANT.get(), new BlockStateConfiguration(BPBlocks.OCHAIM_PURPLE.get().defaultBlockState())));
    public static final RegistryObject<ConfiguredFeature<?, ?>> OCHAIM_RED = CONFIGURED_FEATURES.register("ochaim_red", () -> new ConfiguredFeature<>(BPFeatures.WATER_PLANT.get(), new BlockStateConfiguration(BPBlocks.OCHAIM_RED.get().defaultBlockState())));
    public static final RegistryObject<ConfiguredFeature<?, ?>> OCHAIM_GREEN = CONFIGURED_FEATURES.register("ochaim_green", () -> new ConfiguredFeature<>(BPFeatures.WATER_PLANT.get(), new BlockStateConfiguration(BPBlocks.OCHAIM_GREEN.get().defaultBlockState())));

    public static final RegistryObject<ConfiguredFeature<?, ?>> END_ISLANDS_ICICLE = CONFIGURED_FEATURES.register("end_islands_icicle", () -> new ConfiguredFeature<>(BPFeatures.END_ICICLE.get(), new NoneFeatureConfiguration()));
    public static final RegistryObject<ConfiguredFeature<?, ?>> END_ISLANDS_ICICLE_PATCH = CONFIGURED_FEATURES.register("end_islands_icicle_patch", () -> new ConfiguredFeature<>(Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(BPPlacedFeatures.END_ISLANDS_ICICLE_PATCH.getHolder().get(), 0.2224447F)), BPPlacedFeatures.END_ISLANDS_ICICLE_PATCH.getHolder().get())));

    public static final RegistryObject<ConfiguredFeature<?, ?>> END_FROZEN_ISLAND = CONFIGURED_FEATURES.register("end_frozen_island", () -> new ConfiguredFeature<>(BPFeatures.END_FROZEN_ISLAND.get(), new NoneFeatureConfiguration()));
    public static final RegistryObject<ConfiguredFeature<?, ?>> END_FROZEN_ISLAND_DECORATED = CONFIGURED_FEATURES.register("end_frozen_island_decorated", () -> END_FROZEN_ISLAND.get());
    
	public static ConfiguredFeature<?, ?> makePendentConfig(String name, Block top, Block middle, Block end, ImmutableList<Block> whitelist, int minLength, int maxLength) {
		return register(name, BPFeatures.PENDENT_BLOCKS.get(), new PendentBlocksFeatureConfig.Builder()
                .setTopBlock(top).setMiddleBlock(middle)
                .setFruitedBlock(middle).setEndBlock(end.defaultBlockState().setValue(GrowingPlantHeadBlock.AGE, 23))
                .setWhitelist(whitelist)
                .setMinLength(minLength)
                .setMaxLength(maxLength)
                .build());
	}
	
	public static ConfiguredFeature<?, ?> makeFruitablePendentConfig(String name, Block top, Block middle, Block fruited, Block end, ImmutableList<Block> whitelist, int minLength, int maxLength) {
		return register(name, BPFeatures.PENDENT_BLOCKS.get(), new PendentBlocksFeatureConfig.Builder()
                .setTopBlock(top).setMiddleBlock(middle)
                .setFruitedBlock(fruited).setEndBlock(end.defaultBlockState().setValue(GrowingPlantHeadBlock.AGE, 23))
                .setWhitelist(whitelist)
                .setMinLength(minLength)
                .setMaxLength(maxLength)
                .build());
	}
	
	public static <FC extends FeatureConfiguration, F extends Feature<FC>> ConfiguredFeature<?, ?> register(String name, ConfiguredFeature<FC, F> feature) {
		return feature;
	}
	
	public static <FC extends FeatureConfiguration, F extends Feature<FC>> ConfiguredFeature<?, ?> register(String name, F feature, FC featureConfiguration) {
		return new ConfiguredFeature<>(feature, featureConfiguration);
	}
	
	public static ResourceLocation prefix(String name) {
		return new ResourceLocation(Bioplethora.MOD_ID, name.toLowerCase(Locale.ROOT));
	}
}