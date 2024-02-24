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
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.RandomPatchFeature;
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
    
    public static final RegistryObject<ConfiguredFeature<?, ?>> BIOPLETHORA = CONFIGURED_FEATURES.register("", () -> register().get());
    
    public static Holder<ConfiguredFeature<?, ?>> register() {
    	 Holder<ConfiguredFeature<?, ?>> GLACYNTH = makePendentConfig("glacynth", 
                Blocks.ICE, BPBlocks.GLACYNTH_PLANT.get(), BPBlocks.GLACYNTH.get(),
                ImmutableList.of(Blocks.ICE, Blocks.BLUE_ICE, Blocks.PACKED_ICE, Blocks.FROSTED_ICE),
                3, 9);
    	
    	 Holder<ConfiguredFeature<?, ?>> PINK_TWI = makePendentConfig("pink_twi", 
                BPBlocks.ENIVILE_LEAVES_PINK.get(), BPBlocks.PINK_TWI_PLANT.get(), BPBlocks.PINK_TWI.get(),
                ImmutableList.of(Blocks.SOUL_SOIL, Blocks.SOUL_SAND, Blocks.NETHERRACK, Blocks.BLACKSTONE),
                1, 6);
    	
         Holder<ConfiguredFeature<?, ?>> RED_TWI = makePendentConfig("red_twi", 
                BPBlocks.ENIVILE_LEAVES_RED.get(), BPBlocks.RED_TWI_PLANT.get(), BPBlocks.RED_TWI.get(),
                ImmutableList.of(Blocks.SOUL_SOIL, Blocks.SOUL_SAND, Blocks.NETHERRACK, Blocks.BLACKSTONE),
                1, 8);
        
         Holder<ConfiguredFeature<?, ?>> SPINXELTHORN = makePendentConfig("spinxelthorn", 
                Blocks.END_STONE, BPBlocks.SPINXELTHORN_PLANT.get(), BPBlocks.SPINXELTHORN.get(),
                ImmutableList.of(Blocks.END_STONE),
                1, 8);
        
         Holder<ConfiguredFeature<?, ?>> SPIRIT_DANGLER = makePendentConfig("spirit_dangler", 
                Blocks.SOUL_SOIL, BPBlocks.SPIRIT_DANGLER_PLANT.get(), BPBlocks.SPIRIT_DANGLER.get(),
                ImmutableList.of(Blocks.SOUL_SOIL, Blocks.SOUL_SAND, Blocks.NETHERRACK, Blocks.BLACKSTONE),
                1, 8);
        
         Holder<ConfiguredFeature<?, ?>> LAVA_SPIRE = register("lava_spire", BPFeatures.LAVA_EDGE_CLUSTER.get(), new RandomPatchConfiguration(64, 7, 3, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(BPBlocks.LAVA_SPIRE.get())))));
        
         Holder<ConfiguredFeature<?, ?>> FLEIGNARITE_REMAINS = register("fleignarite_remains", BPFeatures.FLEIGNARITE_PATCH.get(),
        		new FleignariteSplotchConfig(BPBlocks.FLEIGNARITE_REMAINS.get().defaultBlockState(),
                        BPBlocks.FLEIGNARITE_SPLOTCH.get().defaultBlockState(),
                        BPVanillaBiomeFeatureGeneration.stoneBlockstates(),
                        ImmutableList.of(Blocks.CAVE_AIR.defaultBlockState(), Blocks.AIR.defaultBlockState()),
                        ImmutableList.of(Blocks.CAVE_AIR.defaultBlockState(), Blocks.AIR.defaultBlockState())));
        
         Holder<ConfiguredFeature<?, ?>> FLEIGNARITE_VINES = register("fleignarite_vines", BPFeatures.PENDENT_FLEIGNARITE.get(),
        		new PendentBlocksFeatureConfig.Builder()
                        .setTopBlock(Blocks.STONE).setMiddleBlock(BPBlocks.FLEIGNARITE_VINES_PLANT.get())
                        .setFruitedBlock(BPBlocks.FLEIGNARITE_VINES_PLANT.get()).setEndBlock(BPBlocks.FLEIGNARITE_VINES.get().defaultBlockState().setValue(GrowingPlantHeadBlock.AGE, 23))
                        .setWhitelist(BPVanillaBiomeFeatureGeneration.stoneBlocks())
                        .setMinLength(1)
                        .setMaxLength(2)
                        .build());

        // End Plants
         Holder<ConfiguredFeature<?, ?>> IRION_GRASS = register("irion_grass", new RandomPatchFeature(RandomPatchConfiguration.CODEC), 
        		new RandomPatchConfiguration(64, 7, 3, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(BPBlocks.IRION_GRASS.get())))));

         Holder<ConfiguredFeature<?, ?>> IRION_TALL_GRASS = register("irion_tall_grass", Feature.RANDOM_PATCH, 
        		new RandomPatchConfiguration(64, 7, 3, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(BPBlocks.IRION_TALL_GRASS.get())))));
        
         Holder<ConfiguredFeature<?, ?>> ARTAIRIUS = register("artairius", Feature.RANDOM_PATCH, 
        		new RandomPatchConfiguration(64, 7, 3, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(BPBlocks.ARTAIRIUS.get())))));
        
         Holder<ConfiguredFeature<?, ?>> CYRA_LAKE = register("cyra_lake", BPFeatures.EXPANDED_LAKE.get(),
                	new ExpandedLakeFeatureConfig.Builder()
                        .setLiquid(Blocks.WATER.defaultBlockState())
                        .setSides(BPBlocks.CYRA.get().defaultBlockState())
                        .setBase(Blocks.OBSIDIAN.defaultBlockState())
                        .build());

         Holder<ConfiguredFeature<?, ?>> BYRSS_LANTERN_PLANT = register("byrss_lantern_plant", BPFeatures.SINGULAR_BLOCK.get(), new BlockPileConfiguration(BlockStateProvider.simple(BPBlocks.BYRSS_LANTERN_PLANT.get().defaultBlockState())));
        
         Holder<ConfiguredFeature<?, ?>> BYRSS_LANTERN_PLANT_PATCH = register("byrss_lantern_plant_patch", Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(BPPlacedFeatures.BYRSS_LANTERN_PLANT_PATCH, 0.4222667F)), BPPlacedFeatures.BYRSS_LANTERN_PLANT_PATCH));
         Holder<ConfiguredFeature<?, ?>> BYRSS_LANTERN_FOREST_PATCH = register("byrss_lantern_forest_patch", Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(BPPlacedFeatures.BYRSS_LANTERN_FOREST_PATCH, 0.4446667F)), BPPlacedFeatures.BYRSS_LANTERN_FOREST_PATCH));

         Holder<ConfiguredFeature<?, ?>> END_LAND_SPONGE_PATCH_ML = register("end_land_sponge_patch_ml", BPFeatures.END_LAND_SPONGE.get(),
        		new DeltaFeatureConfiguration(Blocks.WATER.defaultBlockState(), BPBlocks.ENDURION.get().defaultBlockState(), UniformInt.of(5, 15), UniformInt.of(2, 4)));
         Holder<ConfiguredFeature<?, ?>> END_LAND_SPONGE_PATCH_HL = register("end_land_sponge_patch_hl", BPFeatures.END_LAND_SPONGE.get(),
        		new DeltaFeatureConfiguration(Blocks.WATER.defaultBlockState(), BPBlocks.ENDURION.get().defaultBlockState(), UniformInt.of(7, 19), UniformInt.of(3, 6)));

         Holder<ConfiguredFeature<?, ?>> CAERI_CAVERN = register("caeri_cavern", BPFeatures.CAERI_CAVERN.get(), new NoneFeatureConfiguration());

         Holder<ConfiguredFeature<?, ?>> CHORUS_MYCHRODEGIA = register("chorus_plant", BPFeatures.CHORUS_MYCHRODEGIA.get(), new NoneFeatureConfiguration());

         Holder<ConfiguredFeature<?, ?>> END_LAND_SPIKE_HL = register("end_land_spike_hl", BPFeatures.END_LAND_SPIKE.get(), new NoneFeatureConfiguration());
        
         Holder<ConfiguredFeature<?, ?>> END_LAND_SPIKE_PATCH_HL = register("end_land_spike_patch_hl", Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(BPPlacedFeatures.END_LAND_SPIKE_PATCH_HL, 0.4446667F)), BPPlacedFeatures.END_LAND_SPIKE_PATCH_HL));

         Holder<ConfiguredFeature<?, ?>> END_LAND_SPIKE_ML = register("end_land_spike_ml", BPFeatures.END_LAND_SPIKE.get(), new NoneFeatureConfiguration());
         Holder<ConfiguredFeature<?, ?>> END_LAND_SPIKE_PATCH_ML = register("end_land_spike_patch_ml", Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(BPPlacedFeatures.END_LAND_SPIKE_PATCH_ML, 0.4446667F)), BPPlacedFeatures.END_LAND_SPIKE_PATCH_ML));

         Holder<ConfiguredFeature<?, ?>> CHORUS_LANTERN_PLANT = register("chorus_lantern_plant", BPFeatures.SINGULAR_BLOCK.get(), new BlockPileConfiguration(BlockStateProvider.simple(BPBlocks.CHORUS_LANTERN_PLANT.get().defaultBlockState())));
        
         Holder<ConfiguredFeature<?, ?>> CHORUS_LANTERN_HIGHLANDS_PATCH = register("chorus_lantern_highlands_patch", Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(BPPlacedFeatures.CHORUS_LANTERN_HIGHLANDS_PATCH, 0.4022667F)), BPPlacedFeatures.CHORUS_LANTERN_HIGHLANDS_PATCH));
         Holder<ConfiguredFeature<?, ?>> CHORUS_LANTERN_MIDLANDS_PATCH = register("chorus_lantern_midlands_patch", Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(BPPlacedFeatures.CHORUS_LANTERN_MIDLANDS_PATCH, 0.4224447F)), BPPlacedFeatures.CHORUS_LANTERN_MIDLANDS_PATCH));

         Holder<ConfiguredFeature<?, ?>> ENREDE_KELP = register("enrede_kelp", BPFeatures.ENREDE_KELP.get(), new NoneFeatureConfiguration());

         Holder<ConfiguredFeature<?, ?>> CELESTIA_BUD = register("celestia_bud", BPFeatures.CELESTIA_BUD.get(), new NoneFeatureConfiguration());

         Holder<ConfiguredFeature<?, ?>> ENREDE_CORSASCILE = register("enrede_corsascile", BPFeatures.CORSASCILE.get(), new ProbabilityFeatureConfiguration(0.3F));
         Holder<ConfiguredFeature<?, ?>> OCHAIM_PURPLE = register("ochaim_purple",  BPFeatures.WATER_PLANT.get(), new BlockStateConfiguration(BPBlocks.OCHAIM_PURPLE.get().defaultBlockState()));
         Holder<ConfiguredFeature<?, ?>> OCHAIM_RED = register("ochaim_red", BPFeatures.WATER_PLANT.get(), new BlockStateConfiguration(BPBlocks.OCHAIM_RED.get().defaultBlockState()));
         Holder<ConfiguredFeature<?, ?>> OCHAIM_GREEN = register("ochaim_green", BPFeatures.WATER_PLANT.get(), new BlockStateConfiguration(BPBlocks.OCHAIM_GREEN.get().defaultBlockState()));

         Holder<ConfiguredFeature<?, ?>> END_ISLANDS_ICICLE = register("end_islands_icicle", BPFeatures.END_ICICLE.get(), new NoneFeatureConfiguration());
         Holder<ConfiguredFeature<?, ?>> END_ISLANDS_ICICLE_PATCH = register("end_islands_icicle_patch", Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(BPPlacedFeatures.END_ISLANDS_ICICLE_PATCH, 0.2224447F)), BPPlacedFeatures.END_ISLANDS_ICICLE_PATCH));

         Holder<ConfiguredFeature<?, ?>> END_FROZEN_ISLAND = register("end_frozen_island", BPFeatures.END_FROZEN_ISLAND.get(), new NoneFeatureConfiguration());
         Holder<ConfiguredFeature<?, ?>> END_FROZEN_ISLAND_DECORATED = register("end_frozen_island_decorated", END_FROZEN_ISLAND.get());
        return GLACYNTH;
    }
    
    public static final ResourceKey<ConfiguredFeature<?, ?>> GLACYNTH_KEY = createKey("GLACYNTH");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PINK_TWI_KEY = createKey("PINK_TWI");
    public static final ResourceKey<ConfiguredFeature<?, ?>> RED_TWI_KEY = createKey("RED_TWI");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SPINXELTHORN_KEY = createKey("SPINXELTHORN");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SPIRIT_DANGLER_KEY = createKey("SPIRIT_DANGLER");
    public static final ResourceKey<ConfiguredFeature<?, ?>> LAVA_SPIRE_KEY = createKey("LAVA_SPIRE");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FLEIGNARITE_REMAINS_KEY = createKey("FLEIGNARITE_REMAINS");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FLEIGNARITE_VINES_KEY = createKey("FLEIGNARITE_VINES");
    public static final ResourceKey<ConfiguredFeature<?, ?>> IRION_GRASS_KEY = createKey("IRION_GRASS");
    public static final ResourceKey<ConfiguredFeature<?, ?>> IRION_TALL_GRASS_KEY = createKey("IRION_TALL_GRASS");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ARTAIRIUS_KEY = createKey("ARTAIRIUS");
    public static final ResourceKey<ConfiguredFeature<?, ?>> CYRA_LAKE_KEY = createKey("CYRA_LAKE");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BYRSS_LANTERN_PLANT_KEY = createKey("BYRSS_LANTERN_PLANT");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BYRSS_LANTERN_PLANT_PATCH_KEY = createKey("BYRSS_LANTERN_PLANT_PATCH");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BYRSS_LANTERN_FOREST_PATCH_KEY = createKey("BYRSS_LANTERN_FOREST_PATCH");
    public static final ResourceKey<ConfiguredFeature<?, ?>> END_LAND_SPONGE_PATCH_ML_KEY = createKey("END_LAND_SPONGE_PATCH_ML");
    public static final ResourceKey<ConfiguredFeature<?, ?>> END_LAND_SPONGE_PATCH_HL_KEY = createKey("END_LAND_SPONGE_PATCH_HL");
    public static final ResourceKey<ConfiguredFeature<?, ?>> CAERI_CAVERN_KEY = createKey("CAERI_CAVERN");
    public static final ResourceKey<ConfiguredFeature<?, ?>> CHORUS_PLANT_KEY = createKey("CHORUS_PLANT");
    public static final ResourceKey<ConfiguredFeature<?, ?>> END_LAND_SPIKE_HL_KEY = createKey("END_LAND_SPIKE_HL");
    public static final ResourceKey<ConfiguredFeature<?, ?>> END_LAND_SPIKE_PATCH_HL_KEY = createKey("END_LAND_SPIKE_PATCH_HL");
    public static final ResourceKey<ConfiguredFeature<?, ?>> END_LAND_SPIKE_ML_KEY = createKey("END_LAND_SPIKE_ML");
    public static final ResourceKey<ConfiguredFeature<?, ?>> END_LAND_SPIKE_PATCH_ML_KEY = createKey("END_LAND_SPIKE_PATCH_ML");
    public static final ResourceKey<ConfiguredFeature<?, ?>> CHORUS_LANTERN_PLANT_KEY = createKey("CHORUS_LANTERN_PLANT");
    public static final ResourceKey<ConfiguredFeature<?, ?>> CHORUS_LANTERN_HIGHLANDS_PATCH_KEY = createKey("CHORUS_LANTERN_HIGHLANDS_PATCH");
    public static final ResourceKey<ConfiguredFeature<?, ?>> CHORUS_LANTERN_MIDLANDS_PATCH_KEY = createKey("CHORUS_LANTERN_MIDLANDS_PATCH");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ENREDE_KELP_KEY = createKey("ENREDE_KELP");
    public static final ResourceKey<ConfiguredFeature<?, ?>> CELESTIA_BUD_KEY = createKey("CELESTIA_BUD");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ENREDE_CORSASCILE_KEY = createKey("ENREDE_CORSASCILE");
    public static final ResourceKey<ConfiguredFeature<?, ?>> OCHAIM_PURPLE_KEY = createKey("OCHAIM_PURPLE");
    public static final ResourceKey<ConfiguredFeature<?, ?>> OCHAIM_RED_KEY = createKey("OCHAIM_RED");
    public static final ResourceKey<ConfiguredFeature<?, ?>> OCHAIM_GREEN_KEY = createKey("OCHAIM_GREEN");
    public static final ResourceKey<ConfiguredFeature<?, ?>> END_ISLANDS_ICICLE_KEY = createKey("END_ISLANDS_ICICLE");
    public static final ResourceKey<ConfiguredFeature<?, ?>> END_ISLANDS_ICICLE_PATCH_KEY = createKey("END_ISLANDS_ICICLE_PATCH");
    public static final ResourceKey<ConfiguredFeature<?, ?>> END_FROZEN_ISLAND_KEY = createKey("END_FROZEN_ISLAND");
    public static final ResourceKey<ConfiguredFeature<?, ?>> END_FROZEN_ISLAND_DECORATED_KEY = createKey("END_FROZEN_ISLAND_DECORATED");
    
	public static final Holder<ConfiguredFeature<?, ?>> GLACYNTH = BuiltinRegistries.CONFIGURED_FEATURE.getOrCreateHolderOrThrow(GLACYNTH_KEY);
	
	public static final Holder<ConfiguredFeature<?, ?>> PINK_TWI = BuiltinRegistries.CONFIGURED_FEATURE.getOrCreateHolderOrThrow(PINK_TWI_KEY);
	
    public static final Holder<ConfiguredFeature<?, ?>> RED_TWI = BuiltinRegistries.CONFIGURED_FEATURE.getOrCreateHolderOrThrow(RED_TWI_KEY);
    
    public static final Holder<ConfiguredFeature<?, ?>> SPINXELTHORN = BuiltinRegistries.CONFIGURED_FEATURE.getOrCreateHolderOrThrow(SPINXELTHORN_KEY);
    
    public static final Holder<ConfiguredFeature<?, ?>> SPIRIT_DANGLER = BuiltinRegistries.CONFIGURED_FEATURE.getOrCreateHolderOrThrow(SPIRIT_DANGLER_KEY);
    
    public static final Holder<ConfiguredFeature<?, ?>> LAVA_SPIRE = BuiltinRegistries.CONFIGURED_FEATURE.getOrCreateHolderOrThrow(LAVA_SPIRE_KEY);
    
    public static final Holder<ConfiguredFeature<?, ?>> FLEIGNARITE_REMAINS = BuiltinRegistries.CONFIGURED_FEATURE.getOrCreateHolderOrThrow(FLEIGNARITE_REMAINS_KEY);
    
    public static final Holder<ConfiguredFeature<?, ?>> FLEIGNARITE_VINES = BuiltinRegistries.CONFIGURED_FEATURE.getOrCreateHolderOrThrow(FLEIGNARITE_VINES_KEY);

    // End Plants
    public static final Holder<ConfiguredFeature<?, ?>> IRION_GRASS = BuiltinRegistries.CONFIGURED_FEATURE.getOrCreateHolderOrThrow(IRION_GRASS_KEY);

    public static final Holder<ConfiguredFeature<?, ?>> IRION_TALL_GRASS = BuiltinRegistries.CONFIGURED_FEATURE.getOrCreateHolderOrThrow(IRION_TALL_GRASS_KEY);
    
    public static final Holder<ConfiguredFeature<?, ?>> ARTAIRIUS = BuiltinRegistries.CONFIGURED_FEATURE.getOrCreateHolderOrThrow(ARTAIRIUS_KEY);
    
    public static final Holder<ConfiguredFeature<?, ?>> CYRA_LAKE = BuiltinRegistries.CONFIGURED_FEATURE.getOrCreateHolderOrThrow(CYRA_LAKE_KEY);

    public static final Holder<ConfiguredFeature<?, ?>> BYRSS_LANTERN_PLANT = BuiltinRegistries.CONFIGURED_FEATURE.getOrCreateHolderOrThrow(BYRSS_LANTERN_PLANT_KEY);
    
    public static final Holder<ConfiguredFeature<?, ?>> BYRSS_LANTERN_PLANT_PATCH = BuiltinRegistries.CONFIGURED_FEATURE.getOrCreateHolderOrThrow(BYRSS_LANTERN_PLANT_PATCH_KEY);
    public static final Holder<ConfiguredFeature<?, ?>> BYRSS_LANTERN_FOREST_PATCH = BuiltinRegistries.CONFIGURED_FEATURE.getOrCreateHolderOrThrow(BYRSS_LANTERN_FOREST_PATCH_KEY);

    public static final Holder<ConfiguredFeature<?, ?>> END_LAND_SPONGE_PATCH_ML = BuiltinRegistries.CONFIGURED_FEATURE.getOrCreateHolderOrThrow(END_LAND_SPONGE_PATCH_ML_KEY);
    public static final Holder<ConfiguredFeature<?, ?>> END_LAND_SPONGE_PATCH_HL = BuiltinRegistries.CONFIGURED_FEATURE.getOrCreateHolderOrThrow(END_LAND_SPONGE_PATCH_HL_KEY);

    public static final Holder<ConfiguredFeature<?, ?>> CAERI_CAVERN = BuiltinRegistries.CONFIGURED_FEATURE.getOrCreateHolderOrThrow(CAERI_CAVERN_KEY);

    public static final Holder<ConfiguredFeature<?, ?>> CHORUS_MYCHRODEGIA = BuiltinRegistries.CONFIGURED_FEATURE.getOrCreateHolderOrThrow(CHORUS_PLANT_KEY);

    public static final Holder<ConfiguredFeature<?, ?>> END_LAND_SPIKE_HL = BuiltinRegistries.CONFIGURED_FEATURE.getOrCreateHolderOrThrow(END_LAND_SPIKE_HL_KEY);
    
    public static final Holder<ConfiguredFeature<?, ?>> END_LAND_SPIKE_PATCH_HL = BuiltinRegistries.CONFIGURED_FEATURE.getOrCreateHolderOrThrow(END_LAND_SPIKE_PATCH_HL_KEY);

    public static final Holder<ConfiguredFeature<?, ?>> END_LAND_SPIKE_ML = BuiltinRegistries.CONFIGURED_FEATURE.getOrCreateHolderOrThrow(END_LAND_SPIKE_ML_KEY);
    public static final Holder<ConfiguredFeature<?, ?>> END_LAND_SPIKE_PATCH_ML = BuiltinRegistries.CONFIGURED_FEATURE.getOrCreateHolderOrThrow(END_LAND_SPIKE_PATCH_ML_KEY);

    public static final Holder<ConfiguredFeature<?, ?>> CHORUS_LANTERN_PLANT = BuiltinRegistries.CONFIGURED_FEATURE.getOrCreateHolderOrThrow(CHORUS_LANTERN_PLANT_KEY);
    
    public static final Holder<ConfiguredFeature<?, ?>> CHORUS_LANTERN_HIGHLANDS_PATCH = BuiltinRegistries.CONFIGURED_FEATURE.getOrCreateHolderOrThrow(CHORUS_LANTERN_HIGHLANDS_PATCH_KEY);
    public static final Holder<ConfiguredFeature<?, ?>> CHORUS_LANTERN_MIDLANDS_PATCH = BuiltinRegistries.CONFIGURED_FEATURE.getOrCreateHolderOrThrow(CHORUS_LANTERN_MIDLANDS_PATCH_KEY);

    public static final Holder<ConfiguredFeature<?, ?>> ENREDE_KELP = BuiltinRegistries.CONFIGURED_FEATURE.getOrCreateHolderOrThrow(ENREDE_KELP_KEY);

    public static final Holder<ConfiguredFeature<?, ?>> CELESTIA_BUD = BuiltinRegistries.CONFIGURED_FEATURE.getOrCreateHolderOrThrow(CELESTIA_BUD_KEY);

    public static final Holder<ConfiguredFeature<?, ?>> ENREDE_CORSASCILE = BuiltinRegistries.CONFIGURED_FEATURE.getOrCreateHolderOrThrow(ENREDE_CORSASCILE_KEY);
    public static final Holder<ConfiguredFeature<?, ?>> OCHAIM_PURPLE = BuiltinRegistries.CONFIGURED_FEATURE.getOrCreateHolderOrThrow(OCHAIM_PURPLE_KEY);
    public static final Holder<ConfiguredFeature<?, ?>> OCHAIM_RED = BuiltinRegistries.CONFIGURED_FEATURE.getOrCreateHolderOrThrow(OCHAIM_RED_KEY);
    public static final Holder<ConfiguredFeature<?, ?>> OCHAIM_GREEN = BuiltinRegistries.CONFIGURED_FEATURE.getOrCreateHolderOrThrow(OCHAIM_GREEN_KEY);

    public static final Holder<ConfiguredFeature<?, ?>> END_ISLANDS_ICICLE = BuiltinRegistries.CONFIGURED_FEATURE.getOrCreateHolderOrThrow(END_ISLANDS_ICICLE_KEY);
    public static final Holder<ConfiguredFeature<?, ?>> END_ISLANDS_ICICLE_PATCH = BuiltinRegistries.CONFIGURED_FEATURE.getOrCreateHolderOrThrow(END_ISLANDS_ICICLE_PATCH_KEY);

    public static final Holder<ConfiguredFeature<?, ?>> END_FROZEN_ISLAND = BuiltinRegistries.CONFIGURED_FEATURE.getOrCreateHolderOrThrow(END_FROZEN_ISLAND_KEY);
    public static final Holder<ConfiguredFeature<?, ?>> END_FROZEN_ISLAND_DECORATED = BuiltinRegistries.CONFIGURED_FEATURE.getOrCreateHolderOrThrow(END_FROZEN_ISLAND_DECORATED_KEY);
    
	public static Holder<ConfiguredFeature<?, ?>> makePendentConfig(String name, Block top, Block middle, Block end, ImmutableList<Block> whitelist, int minLength, int maxLength) {
		return register(name, BPFeatures.PENDENT_BLOCKS.get(), new PendentBlocksFeatureConfig.Builder()
                .setTopBlock(top).setMiddleBlock(middle)
                .setFruitedBlock(middle).setEndBlock(end.defaultBlockState().setValue(GrowingPlantHeadBlock.AGE, 23))
                .setWhitelist(whitelist)
                .setMinLength(minLength)
                .setMaxLength(maxLength)
                .build());
	}
	
	public static Holder<ConfiguredFeature<?, ?>> makeFruitablePendentConfig(String name, Block top, Block middle, Block fruited, Block end, ImmutableList<Block> whitelist, int minLength, int maxLength) {
		return register(name, BPFeatures.PENDENT_BLOCKS.get(), new PendentBlocksFeatureConfig.Builder()
                .setTopBlock(top).setMiddleBlock(middle)
                .setFruitedBlock(fruited).setEndBlock(end.defaultBlockState().setValue(GrowingPlantHeadBlock.AGE, 23))
                .setWhitelist(whitelist)
                .setMinLength(minLength)
                .setMaxLength(maxLength)
                .build());
	}
	
	public static ResourceKey<ConfiguredFeature<?, ?>> createKey(String name) {
		return ResourceKey.create(Registry.CONFIGURED_FEATURE_REGISTRY, new ResourceLocation(Bioplethora.MOD_ID, name.toLowerCase(Locale.ROOT)));
	}
	
	public static <FC extends FeatureConfiguration, F extends Feature<FC>> Holder<ConfiguredFeature<?, ?>> register(String name, ConfiguredFeature<FC, F> feature) {
		return BuiltinRegistries.register(BuiltinRegistries.CONFIGURED_FEATURE, prefix(name).toString(), feature);
	}
	
	public static <FC extends FeatureConfiguration, F extends Feature<FC>> Holder<ConfiguredFeature<?, ?>> register(String name, F feature, FC featureConfiguration) {
		return BuiltinRegistries.register(BuiltinRegistries.CONFIGURED_FEATURE, prefix(name).toString(), new ConfiguredFeature<>(feature, featureConfiguration));
	}
	
	public static ResourceLocation prefix(String name) {
		return new ResourceLocation(Bioplethora.MOD_ID, name.toLowerCase(Locale.ROOT));
	}
}
