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
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
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
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class BPConfiguredFeatures {

    public static final ResourceKey<ConfiguredFeature<?, ?>> SOUL_MINISHROOM =  registerKey("soul_minishroom");
    
    public static final ResourceKey<ConfiguredFeature<?, ?>> SOUL_BIGSHROOM =  registerKey("soul_bigshroom");
    
    public static final ResourceKey<ConfiguredFeature<?, ?>> KYRIA =  registerKey("kyria");
    
    public static final ResourceKey<ConfiguredFeature<?, ?>> KYRIA_BELINE =  registerKey("kyria_beline");
    
    public static final ResourceKey<ConfiguredFeature<?, ?>> KYRIA_IDE_FAN =  registerKey("kyria_ide_fan");

    public static final ResourceKey<ConfiguredFeature<?, ?>> SOUL_SPROUTS = registerKey("soul_sprouts");
    
    public static final ResourceKey<ConfiguredFeature<?, ?>> SOUL_TALL_GRASS =  registerKey("soul_tall_grass");
    
	public static final ResourceKey<ConfiguredFeature<?, ?>> GLACYNTH = registerKey("glacynth");
	
	public static final ResourceKey<ConfiguredFeature<?, ?>> PINK_TWI = registerKey("pink_twi");
	
    public static final ResourceKey<ConfiguredFeature<?, ?>> RED_TWI = registerKey("red_twi");
    
    public static final ResourceKey<ConfiguredFeature<?, ?>> SPINXELTHORN = registerKey("spinxelthorn");
    
    public static final ResourceKey<ConfiguredFeature<?, ?>> SPIRIT_DANGLER = registerKey("spirit_dangler");
    
    public static final ResourceKey<ConfiguredFeature<?, ?>> LAVA_SPIRE = registerKey("lava_spire");
    
    public static final ResourceKey<ConfiguredFeature<?, ?>> FLEIGNARITE_REMAINS = registerKey("fleignarite_remains");
    
    public static final ResourceKey<ConfiguredFeature<?, ?>> FLEIGNARITE_VINES = registerKey("fleignarite_vines");
    
    public static final ResourceKey<ConfiguredFeature<?, ?>> BASALT_SPELEOTHERM = registerKey("basalt_speleotherm");

    public static final ResourceKey<ConfiguredFeature<?, ?>> THONTUS_THISTLE = registerKey("thontus_thistle");

    public static final ResourceKey<ConfiguredFeature<?, ?>> TURQUOISE_PENDENT = registerKey("turquoise_pendent");

    public static final ResourceKey<ConfiguredFeature<?, ?>> CERISE_IVY = registerKey("cerise_ivy");

    public static final ResourceKey<ConfiguredFeature<?, ?>> SOUL_ETERN = registerKey("soul_etern");
    
    public static final ResourceKey<ConfiguredFeature<?, ?>> CHORUS_IDON = registerKey("chorus_idon");

    public static final ResourceKey<ConfiguredFeature<?, ?>> CHORUS_IDE_FAN = registerKey("chorus_ide_fan");
    public static final ResourceKey<ConfiguredFeature<?, ?>> WARPED_DANCER  = registerKey("warped_dancer");
    // End Plants
    public static final ResourceKey<ConfiguredFeature<?, ?>> IRION_GRASS = registerKey("irion_grass");

    public static final ResourceKey<ConfiguredFeature<?, ?>> IRION_TALL_GRASS = registerKey("irion_tall_grass");
    
    public static final ResourceKey<ConfiguredFeature<?, ?>> ARTAIRIUS = registerKey("artairius");
    
    public static final ResourceKey<ConfiguredFeature<?, ?>> FROSTEM = registerKey("frostem");
    
    public static final ResourceKey<ConfiguredFeature<?, ?>> CYRA_LAKE = registerKey("cyra_lake");

    public static final ResourceKey<ConfiguredFeature<?, ?>> BYRSS_LANTERN_PLANT = registerKey("byrss_lantern_plant");
    
    public static final ResourceKey<ConfiguredFeature<?, ?>> BYRSS_LANTERN_PLANT_PATCH = registerKey("byrss_lantern_plant_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BYRSS_LANTERN_FOREST_PATCH = registerKey("byrss_lantern_forest_patch");

    public static final ResourceKey<ConfiguredFeature<?, ?>> END_LAND_SPONGE_PATCH_ML = registerKey("end_land_sponge_patch_ml");
    public static final ResourceKey<ConfiguredFeature<?, ?>> END_LAND_SPONGE_PATCH_HL = registerKey("end_land_sponge_patch_hl");

    public static final ResourceKey<ConfiguredFeature<?, ?>> CAERI_CAVERN = registerKey("caeri_cavern");

    public static final ResourceKey<ConfiguredFeature<?, ?>> CHORUS_MYCHRODEGIA = registerKey("chorus_plant");

    public static final ResourceKey<ConfiguredFeature<?, ?>> END_LAND_SPIKE_HL = registerKey("end_land_spike_hl");
    
    public static final ResourceKey<ConfiguredFeature<?, ?>> END_LAND_SPIKE_PATCH_HL = registerKey("end_land_spike_patch_hl");

    public static final ResourceKey<ConfiguredFeature<?, ?>> END_LAND_SPIKE_ML = registerKey("end_land_spike_ml");
    public static final ResourceKey<ConfiguredFeature<?, ?>> END_LAND_SPIKE_PATCH_ML = registerKey("end_land_spike_patch_ml");

    public static final ResourceKey<ConfiguredFeature<?, ?>> CHORUS_LANTERN_PLANT = registerKey("chorus_lantern_plant");
    
    public static final ResourceKey<ConfiguredFeature<?, ?>> CHORUS_LANTERN_HIGHLANDS_PATCH = registerKey("chorus_lantern_highlands_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> CHORUS_LANTERN_MIDLANDS_PATCH = registerKey("chorus_lantern_midlands_patch");

    public static final ResourceKey<ConfiguredFeature<?, ?>> ENREDE_KELP = registerKey("enrede_kelp");

    public static final ResourceKey<ConfiguredFeature<?, ?>> CELESTIA_BUD = registerKey("celestia_bud");

    public static final ResourceKey<ConfiguredFeature<?, ?>> ENREDE_CORSASCILE = registerKey("enrede_corsascile");
    public static final ResourceKey<ConfiguredFeature<?, ?>> OCHAIM_PURPLE = registerKey("ochaim_purple");
    public static final ResourceKey<ConfiguredFeature<?, ?>> OCHAIM_RED = registerKey("ochaim_red");
    public static final ResourceKey<ConfiguredFeature<?, ?>> OCHAIM_GREEN = registerKey("ochaim_green");

    public static final ResourceKey<ConfiguredFeature<?, ?>> END_ISLANDS_ICICLE = registerKey("end_islands_icicle");
    public static final ResourceKey<ConfiguredFeature<?, ?>> END_ISLANDS_ICICLE_PATCH = registerKey("end_islands_icicle_patch");

    public static final ResourceKey<ConfiguredFeature<?, ?>> END_FROZEN_ISLAND = registerKey("end_frozen_island");
    public static final ResourceKey<ConfiguredFeature<?, ?>> END_FROZEN_ISLAND_DECORATED = registerKey("end_frozen_island_decorated");
    
    public static final ResourceKey<ConfiguredFeature<?, ?>> ENIVILE_TREE_CONFIG = registerKey("enivile_tree");
    
    public static final ResourceKey<ConfiguredFeature<?, ?>> CRYEANUM_FOREST_TREES = registerKey("cryeanum_forest_trees");
    
    public static final ResourceKey<ConfiguredFeature<?, ?>> CAERULWOOD_TREE_CONFIG = registerKey("caerulwood_tree");
    
    public static final ResourceKey<ConfiguredFeature<?, ?>> CAERI_FOREST_TREES = registerKey("caeri_forest_trees");
    
	public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
		return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(Bioplethora.MOD_ID, name));
	}
	
    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
		HolderGetter<PlacedFeature> features = context.lookup(Registries.PLACED_FEATURE);
    	context.register(SOUL_MINISHROOM, new ConfiguredFeature<>(Feature.RANDOM_PATCH, 
        		new RandomPatchConfiguration(64, 7, 3, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(BPBlocks.SOUL_MINISHROOM.get()))))));
        
    	context.register(SOUL_BIGSHROOM, new ConfiguredFeature<>(Feature.RANDOM_PATCH, 
        		new RandomPatchConfiguration(64, 7, 3, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(BPBlocks.SOUL_BIGSHROOM.get()))))));
        
    	context.register(KYRIA, new ConfiguredFeature<>(Feature.RANDOM_PATCH, 
        		new RandomPatchConfiguration(64, 7, 3, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(BPBlocks.KYRIA.get()))))));
        
    	context.register(KYRIA_BELINE, new ConfiguredFeature<>(Feature.RANDOM_PATCH, 
        		new RandomPatchConfiguration(64, 7, 3, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(BPBlocks.KYRIA_BELINE.get()))))));
        
    	context.register(KYRIA_IDE_FAN, new ConfiguredFeature<>(Feature.RANDOM_PATCH, 
        		new RandomPatchConfiguration(64, 7, 3, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(BPBlocks.KYRIA_IDE_FAN.get()))))));

    	context.register(SOUL_SPROUTS, new ConfiguredFeature<>(Feature.RANDOM_PATCH, 
        		new RandomPatchConfiguration(64, 7, 3, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(BPBlocks.SOUL_SPROUTS.get()))))));
        
    	context.register(SOUL_TALL_GRASS, new ConfiguredFeature<>(Feature.RANDOM_PATCH, 
        		new RandomPatchConfiguration(64, 7, 3, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(BPBlocks.SOUL_TALL_GRASS.get()))))));
        
    	context.register(GLACYNTH, makePendentConfig("glacynth", 
                Blocks.ICE, BPBlocks.GLACYNTH_PLANT.get(), BPBlocks.GLACYNTH.get(),
                ImmutableList.of(Blocks.ICE, Blocks.BLUE_ICE, Blocks.PACKED_ICE, Blocks.FROSTED_ICE),
                3, 9));
    	
    	context.register(PINK_TWI, makePendentConfig("pink_twi", 
                BPBlocks.ENIVILE_LEAVES_PINK.get(), BPBlocks.PINK_TWI_PLANT.get(), BPBlocks.PINK_TWI.get(),
                ImmutableList.of(Blocks.SOUL_SOIL, Blocks.SOUL_SAND, Blocks.NETHERRACK, Blocks.BLACKSTONE),
                1, 6));
    	
    	context.register(RED_TWI, makePendentConfig("red_twi", 
                BPBlocks.ENIVILE_LEAVES_RED.get(), BPBlocks.RED_TWI_PLANT.get(), BPBlocks.RED_TWI.get(),
                ImmutableList.of(Blocks.SOUL_SOIL, Blocks.SOUL_SAND, Blocks.NETHERRACK, Blocks.BLACKSTONE),
                1, 8));
        
    	context.register(SPINXELTHORN, makePendentConfig("spinxelthorn", 
                Blocks.END_STONE, BPBlocks.SPINXELTHORN_PLANT.get(), BPBlocks.SPINXELTHORN.get(),
                ImmutableList.of(Blocks.END_STONE),
                1, 8));
        
    	context.register(SPIRIT_DANGLER, makePendentConfig("spirit_dangler", 
                Blocks.SOUL_SOIL, BPBlocks.SPIRIT_DANGLER_PLANT.get(), BPBlocks.SPIRIT_DANGLER.get(),
                ImmutableList.of(Blocks.SOUL_SOIL, Blocks.SOUL_SAND, Blocks.NETHERRACK, Blocks.BLACKSTONE),
                1, 8));
        
    	context.register(LAVA_SPIRE, new ConfiguredFeature<>(BPFeatures.LAVA_EDGE_CLUSTER.get(), new RandomPatchConfiguration(64, 7, 3, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(BPBlocks.LAVA_SPIRE.get()))))));
        
    	context.register(FLEIGNARITE_REMAINS, new ConfiguredFeature<>(BPFeatures.FLEIGNARITE_PATCH.get(),
        		new FleignariteSplotchConfig(BPBlocks.FLEIGNARITE_REMAINS.get().defaultBlockState(),
                        BPBlocks.FLEIGNARITE_SPLOTCH.get().defaultBlockState(),
                        BPVanillaBiomeFeatureGeneration.stoneBlockstates(),
                        ImmutableList.of(Blocks.CAVE_AIR.defaultBlockState(), Blocks.AIR.defaultBlockState()),
                        ImmutableList.of(Blocks.CAVE_AIR.defaultBlockState(), Blocks.AIR.defaultBlockState()))));
        
    	context.register(FLEIGNARITE_VINES, new ConfiguredFeature<>(BPFeatures.PENDENT_FLEIGNARITE.get(),
        		new PendentBlocksFeatureConfig.Builder()
                        .setTopBlock(Blocks.STONE).setMiddleBlock(BPBlocks.FLEIGNARITE_VINES_PLANT.get())
                        .setFruitedBlock(BPBlocks.FLEIGNARITE_VINES_PLANT.get()).setEndBlock(BPBlocks.FLEIGNARITE_VINES.get().defaultBlockState().setValue(GrowingPlantHeadBlock.AGE, 23))
                        .setWhitelist(BPVanillaBiomeFeatureGeneration.stoneBlocks())
                        .setMinLength(1)
                        .setMaxLength(2)
                        .build()));
        
    	context.register(BASALT_SPELEOTHERM, makeFruitablePendentConfig("basalt_speleotherm", 
                Blocks.BASALT, BPBlocks.BASALT_SPELEOTHERM_PLANT.get(), BPBlocks.FIERY_BASALT_SPELEOTHERM.get(), BPBlocks.BASALT_SPELEOTHERM.get(),
                ImmutableList.of(Blocks.BASALT, Blocks.NETHERRACK, Blocks.BLACKSTONE),
                1, 8));

    	context.register(THONTUS_THISTLE, makeFruitablePendentConfig("thontus_thistle", 
                Blocks.NETHERRACK, BPBlocks.THONTUS_THISTLE_PLANT.get(), BPBlocks.BERRIED_THONTUS_THISTLE.get(), BPBlocks.THONTUS_THISTLE.get(),
                ImmutableList.of(Blocks.NETHERRACK, Blocks.BLACKSTONE),
                2, 10));

    	context.register(TURQUOISE_PENDENT, makeFruitablePendentConfig("turquoise_pendent", 
                Blocks.WARPED_WART_BLOCK, BPBlocks.TURQUOISE_PENDENT_PLANT.get(), BPBlocks.BLOSSOMING_TURQUOISE_PENDENT.get(), BPBlocks.TURQUOISE_PENDENT.get(),
                ImmutableList.of(Blocks.WARPED_WART_BLOCK, Blocks.NETHERRACK, Blocks.BLACKSTONE),
                2, 8));

    	context.register(CERISE_IVY, makeFruitablePendentConfig("cerise_ivy",
                Blocks.NETHER_WART_BLOCK, BPBlocks.CERISE_IVY_PLANT.get(), BPBlocks.SEEDED_CERISE_IVY.get(), BPBlocks.CERISE_IVY.get(),
                ImmutableList.of(Blocks.NETHER_WART_BLOCK, Blocks.NETHERRACK, Blocks.BLACKSTONE),
                1, 8));

    	context.register(SOUL_ETERN, makeFruitablePendentConfig("soul_etern",
                Blocks.SOUL_SOIL, BPBlocks.SOUL_ETERN_PLANT.get(), BPBlocks.FLOURISHED_SOUL_ETERN.get(), BPBlocks.SOUL_ETERN.get(),
                ImmutableList.of(Blocks.SOUL_SOIL, Blocks.SOUL_SAND, Blocks.NETHERRACK, Blocks.BLACKSTONE),
                2, 10));
        
    	context.register(CHORUS_IDON, new ConfiguredFeature<>(Feature.RANDOM_PATCH, 
        		new RandomPatchConfiguration(64, 7, 3, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(BPBlocks.CHORUS_IDON.get()))))));

    	context.register(CHORUS_IDE_FAN, new ConfiguredFeature<>(Feature.RANDOM_PATCH, 
        		new RandomPatchConfiguration(64, 7, 3, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(BPBlocks.CHORUS_IDE_FAN.get()))))));
    	context.register(WARPED_DANCER , new ConfiguredFeature<>(Feature.RANDOM_PATCH, 
        		new RandomPatchConfiguration(64, 7, 3, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(BPBlocks.SOUL_SPROUTS.get()))))));
        // End Plants
    	context.register(IRION_GRASS, new ConfiguredFeature<>(Feature.RANDOM_PATCH, 
        		new RandomPatchConfiguration(64, 7, 3, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(BPBlocks.IRION_GRASS.get()))))));

    	context.register(IRION_TALL_GRASS, new ConfiguredFeature<>(Feature.RANDOM_PATCH, 
        		new RandomPatchConfiguration(64, 7, 3, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(BPBlocks.IRION_TALL_GRASS.get()))))));
        
    	context.register(ARTAIRIUS, new ConfiguredFeature<>(Feature.RANDOM_PATCH, 
        		new RandomPatchConfiguration(64, 7, 3, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(BPBlocks.ARTAIRIUS.get()))))));
        
    	context.register(FROSTEM, new ConfiguredFeature<>(Feature.RANDOM_PATCH, 
        		new RandomPatchConfiguration(64, 7, 3, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(BPBlocks.FROSTEM.get()))))));
        
    	context.register(CYRA_LAKE, new ConfiguredFeature<>(BPFeatures.EXPANDED_LAKE.get(),
                	new ExpandedLakeFeatureConfig.Builder()
                        .setLiquid(Blocks.WATER.defaultBlockState())
                        .setSides(BPBlocks.CYRA.get().defaultBlockState())
                        .setBase(Blocks.OBSIDIAN.defaultBlockState())
                        .build()));

    	context.register(BYRSS_LANTERN_PLANT, new ConfiguredFeature<>(BPFeatures.SINGULAR_BLOCK.get(), new BlockPileConfiguration(BlockStateProvider.simple(BPBlocks.BYRSS_LANTERN_PLANT.get().defaultBlockState()))));
        
    	context.register(BYRSS_LANTERN_PLANT_PATCH, new ConfiguredFeature<>(Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(features.getOrThrow(BPPlacedFeatures.BYRSS_LANTERN_PLANT_PATCH), 0.4222667F)), features.getOrThrow(BPPlacedFeatures.BYRSS_LANTERN_PLANT_PATCH))));
    	context.register(BYRSS_LANTERN_FOREST_PATCH, new ConfiguredFeature<>(Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(features.getOrThrow(BPPlacedFeatures.BYRSS_LANTERN_FOREST_PATCH), 0.4446667F)), features.getOrThrow(BPPlacedFeatures.BYRSS_LANTERN_FOREST_PATCH))));

    	context.register(END_LAND_SPONGE_PATCH_ML, new ConfiguredFeature<>(BPFeatures.END_LAND_SPONGE.get(),
        		new DeltaFeatureConfiguration(Blocks.WATER.defaultBlockState(), BPBlocks.ENDURION.get().defaultBlockState(), UniformInt.of(5, 15), UniformInt.of(2, 4))));
    	context.register(END_LAND_SPONGE_PATCH_HL, new ConfiguredFeature<>(BPFeatures.END_LAND_SPONGE.get(),
        		new DeltaFeatureConfiguration(Blocks.WATER.defaultBlockState(), BPBlocks.ENDURION.get().defaultBlockState(), UniformInt.of(7, 16), UniformInt.of(3, 6))));

    	context.register(CAERI_CAVERN, new ConfiguredFeature<>(BPFeatures.CAERI_CAVERN.get(), new NoneFeatureConfiguration()));

    	context.register(CHORUS_MYCHRODEGIA, new ConfiguredFeature<>(BPFeatures.CHORUS_MYCHRODEGIA.get(), new NoneFeatureConfiguration()));

    	context.register(END_LAND_SPIKE_HL, new ConfiguredFeature<>(BPFeatures.END_LAND_SPIKE.get(), new NoneFeatureConfiguration()));
        
    	context.register(END_LAND_SPIKE_PATCH_HL, new ConfiguredFeature<>(Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(features.getOrThrow(BPPlacedFeatures.END_LAND_SPIKE_PATCH_HL), 0.4446667F)), features.getOrThrow(BPPlacedFeatures.END_LAND_SPIKE_PATCH_HL))));

    	context.register(END_LAND_SPIKE_ML, new ConfiguredFeature<>(BPFeatures.END_LAND_SPIKE.get(), new NoneFeatureConfiguration()));
    	context.register(END_LAND_SPIKE_PATCH_ML, new ConfiguredFeature<>(Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(features.getOrThrow(BPPlacedFeatures.END_LAND_SPIKE_PATCH_ML), 0.4446667F)), features.getOrThrow(BPPlacedFeatures.END_LAND_SPIKE_PATCH_ML))));

    	context.register(CHORUS_LANTERN_PLANT, new ConfiguredFeature<>(BPFeatures.SINGULAR_BLOCK.get(), new BlockPileConfiguration(BlockStateProvider.simple(BPBlocks.CHORUS_LANTERN_PLANT.get().defaultBlockState()))));
        
    	context.register(CHORUS_LANTERN_HIGHLANDS_PATCH, new ConfiguredFeature<>(Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(features.getOrThrow(BPPlacedFeatures.CHORUS_LANTERN_HIGHLANDS_PATCH), 0.4022667F)), features.getOrThrow(BPPlacedFeatures.CHORUS_LANTERN_HIGHLANDS_PATCH))));
    	context.register(CHORUS_LANTERN_MIDLANDS_PATCH, new ConfiguredFeature<>(Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(features.getOrThrow(BPPlacedFeatures.CHORUS_LANTERN_MIDLANDS_PATCH), 0.4224447F)), features.getOrThrow(BPPlacedFeatures.CHORUS_LANTERN_MIDLANDS_PATCH))));

    	context.register(ENREDE_KELP, new ConfiguredFeature<>(BPFeatures.ENREDE_KELP.get(), new NoneFeatureConfiguration()));

    	context.register(CELESTIA_BUD, new ConfiguredFeature<>(BPFeatures.CELESTIA_BUD.get(), new NoneFeatureConfiguration()));

    	context.register(ENREDE_CORSASCILE, new ConfiguredFeature<>(BPFeatures.CORSASCILE.get(), new ProbabilityFeatureConfiguration(0.3F)));
    	context.register(OCHAIM_PURPLE, new ConfiguredFeature<>(BPFeatures.WATER_PLANT.get(), new BlockStateConfiguration(BPBlocks.OCHAIM_PURPLE.get().defaultBlockState())));
    	context.register(OCHAIM_RED, new ConfiguredFeature<>(BPFeatures.WATER_PLANT.get(), new BlockStateConfiguration(BPBlocks.OCHAIM_RED.get().defaultBlockState())));
    	context.register(OCHAIM_GREEN, new ConfiguredFeature<>(BPFeatures.WATER_PLANT.get(), new BlockStateConfiguration(BPBlocks.OCHAIM_GREEN.get().defaultBlockState())));

    	context.register(END_ISLANDS_ICICLE, new ConfiguredFeature<>(BPFeatures.END_ICICLE.get(), new NoneFeatureConfiguration()));
    	context.register(END_ISLANDS_ICICLE_PATCH, new ConfiguredFeature<>(Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(features.getOrThrow(BPPlacedFeatures.END_ISLANDS_ICICLE_PATCH), 0.2224447F)), features.getOrThrow(BPPlacedFeatures.END_ISLANDS_ICICLE_PATCH))));

    	context.register(END_FROZEN_ISLAND, new ConfiguredFeature<>(BPFeatures.END_FROZEN_ISLAND.get(), new NoneFeatureConfiguration()));
    	context.register(END_FROZEN_ISLAND_DECORATED, new ConfiguredFeature<>(BPFeatures.END_FROZEN_ISLAND.get(), new NoneFeatureConfiguration()));
        
    	context.register(ENIVILE_TREE_CONFIG, new ConfiguredFeature<>(BPFeatures.ENIVILE_TREE.get(), new NoneFeatureConfiguration()));
        
    	context.register(CRYEANUM_FOREST_TREES, new ConfiguredFeature<>(Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(features.getOrThrow(BPPlacedFeatures.CRYEANUM_FOREST_TREES), 0.31333334F)), features.getOrThrow(BPPlacedFeatures.CRYEANUM_FOREST_TREES))));
        
    	context.register(CAERULWOOD_TREE_CONFIG, new ConfiguredFeature<>(BPFeatures.CAERULWOOD_TREE.get(), new NoneFeatureConfiguration()));
        
    	context.register(CAERI_FOREST_TREES, new ConfiguredFeature<>(Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(features.getOrThrow(BPPlacedFeatures.CAERI_FOREST_TREES), 0.6666667F)), features.getOrThrow(BPPlacedFeatures.CAERI_FOREST_TREES))));
    }
    
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