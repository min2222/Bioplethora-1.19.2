package io.github.bioplethora.registry.worldgen;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.google.common.collect.ImmutableList;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.config.BPConfig;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.util.valueproviders.WeightedListInt;
import net.minecraft.world.level.levelgen.GenerationStep.Carving;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.CarvingMaskPlacement;
import net.minecraft.world.level.levelgen.placement.CountOnEveryLayerPlacement;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.HeightmapPlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.NoiseThresholdCountPlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.RarityFilter;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

@SuppressWarnings("deprecation")
public class BPPlacedFeatures {

	public static final DeferredRegister<PlacedFeature> PLACED_FEATURES = DeferredRegister.create(Registry.PLACED_FEATURE_REGISTRY, Bioplethora.MOD_ID);

    public static final RegistryObject<PlacedFeature> BIOPLETHORA = PLACED_FEATURES.register("", () -> register().get());
    public static final Map<String, ResourceKey<PlacedFeature>> MAP = new HashMap<>();

    public static Holder<PlacedFeature> register() {
    	 Holder<PlacedFeature> GLACYNTH = register("glacynth", BPConfiguredFeatures.GLACYNTH, createPlacementModifier(115, 0, 131).build());
    	 Holder<PlacedFeature> PINK_TWI = register("pink_twi", BPConfiguredFeatures.PINK_TWI, createPlacementModifier(115, 0, 131).build());
    	 Holder<PlacedFeature> RED_TWI = register("red_twi", BPConfiguredFeatures.RED_TWI, createPlacementModifier(105, 0, 119).build());
    	 Holder<PlacedFeature> SPINXELTHORN = register("spinxelthorn", BPConfiguredFeatures.SPINXELTHORN, createPlacementModifier(132, 0, 155).build());
    	 Holder<PlacedFeature> SPIRIT_DANGLER = register("spirit_dangler", BPConfiguredFeatures.SPIRIT_DANGLER, createPlacementModifier(115, 0, 131).build());
    	 Holder<PlacedFeature> LAVA_SPIRE = register("lava_spire", BPConfiguredFeatures.LAVA_SPIRE, createPlacementModifier(19).build());
    	 Holder<PlacedFeature> FLEIGNARITE_REMAINS = register("fleignarite_remains", BPConfiguredFeatures.FLEIGNARITE_REMAINS, 
    			ImmutableList.<PlacementModifier>builder().add(CarvingMaskPlacement.forStep(Carving.AIR)).add(RarityFilter.onAverageOnceEvery(10)).add(CountPlacement.of(UniformInt.of(14, 41))).build());
    	 Holder<PlacedFeature> FLEIGNARITE_VINES = register("fleignarite_vines", BPConfiguredFeatures.FLEIGNARITE_VINES, 
    			ImmutableList.<PlacementModifier>builder().add(CountPlacement.of(512)).add(InSquarePlacement.spread()).add(HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.absolute(127))).build());
    	 Holder<PlacedFeature> CYRA_LAKE = register("cyra_lake", BPConfiguredFeatures.CYRA_LAKE, 
    			ImmutableList.<PlacementModifier>builder().add(CountPlacement.of(1)).add(InSquarePlacement.spread()).add(HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.absolute(BPConfig.WORLDGEN.cyraLakesEndAmount.get() - 1))).build());
    	 Holder<PlacedFeature> BYRSS_LANTERN_PLANT_PATCH = register("byrss_lantern_plant_patch", BPConfiguredFeatures.BYRSS_LANTERN_PLANT_PATCH, 
    			ImmutableList.<PlacementModifier>builder()
    			.add(CountPlacement.of(2))
    			.add(InSquarePlacement.spread())
    			.add(HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.absolute(54)))
    			.add(CountPlacement.of(new WeightedListInt(SimpleWeightedRandomList.<IntProvider>builder().add(ConstantInt.of(2), 98).add(ConstantInt.of(3), 3).build()))).build());
    	 Holder<PlacedFeature> BYRSS_LANTERN_FOREST_PATCH = register("byrss_lantern_forest_patch", BPConfiguredFeatures.BYRSS_LANTERN_FOREST_PATCH, 
    			ImmutableList.<PlacementModifier>builder()
    			.add(CountPlacement.of(5))
    			.add(InSquarePlacement.spread())
    			.add(HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.absolute(59)))
    			.add(CountPlacement.of(new WeightedListInt(SimpleWeightedRandomList.<IntProvider>builder().add(ConstantInt.of(2), 98).add(ConstantInt.of(3), 2).build()))).build());
    	 Holder<PlacedFeature> END_LAND_SPONGE_PATCH_ML = register("end_land_sponge_patch_ml", BPConfiguredFeatures.END_LAND_SPONGE_PATCH_ML, 
    			ImmutableList.<PlacementModifier>builder()
    			.add(CountOnEveryLayerPlacement.of(50)).build());
    	 Holder<PlacedFeature> END_LAND_SPONGE_PATCH_HL = register("end_land_sponge_patch_hl", BPConfiguredFeatures.END_LAND_SPONGE_PATCH_HL, 
    			ImmutableList.<PlacementModifier>builder()
    			.add(CountOnEveryLayerPlacement.of(40)).build());
    	 Holder<PlacedFeature> CAERI_CAVERN = register("caeri_cavern", BPConfiguredFeatures.CAERI_CAVERN, 
    			ImmutableList.<PlacementModifier>builder()
    			.add(CountPlacement.of(1))
    			.add(InSquarePlacement.spread())
    			.add(HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.absolute(6)))
    			.add(CountPlacement.of(new WeightedListInt(SimpleWeightedRandomList.<IntProvider>builder().add(ConstantInt.of(1), 99).add(ConstantInt.of(2), 2).build()))).build());
    	 Holder<PlacedFeature> CHORUS_MYCHRODEGIA = register("chorus_plant", BPConfiguredFeatures.CHORUS_MYCHRODEGIA, 
    			ImmutableList.<PlacementModifier>builder()
    			.add(CountPlacement.of(UniformInt.of(0, 1)))
    			.add(InSquarePlacement.spread())
    			.add(HeightmapPlacement.onHeightmap(Types.MOTION_BLOCKING)).build());
    	 Holder<PlacedFeature> END_LAND_SPIKE_PATCH_HL = register("end_land_spike_patch_hl", BPConfiguredFeatures.END_LAND_SPIKE_PATCH_HL, 
    			ImmutableList.<PlacementModifier>builder()
    			.add(CountPlacement.of(5))
    			.add(InSquarePlacement.spread())
    			.add(HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.absolute(BPConfig.WORLDGEN.endSpikeHighlandsAmount.get() - 1)))
    			.add(CountPlacement.of(new WeightedListInt(SimpleWeightedRandomList.<IntProvider>builder().add(ConstantInt.of(2), 85).add(ConstantInt.of(3), 15).build()))).build());
    	 Holder<PlacedFeature> END_LAND_SPIKE_PATCH_ML = register("end_land_spike_patch_ml", BPConfiguredFeatures.END_LAND_SPIKE_PATCH_ML, 
    			ImmutableList.<PlacementModifier>builder()
    			.add(CountPlacement.of(1))
    			.add(InSquarePlacement.spread())
    			.add(HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.absolute(BPConfig.WORLDGEN.endSpikeMidlandsAmount.get() - 1)))
    			.add(CountPlacement.of(new WeightedListInt(SimpleWeightedRandomList.<IntProvider>builder().add(ConstantInt.of(1), 55).add(ConstantInt.of(2), 45).build()))).build());
    	 Holder<PlacedFeature> CHORUS_LANTERN_HIGHLANDS_PATCH = register("chorus_lantern_highlands_patch", BPConfiguredFeatures.CHORUS_LANTERN_HIGHLANDS_PATCH, 
    			ImmutableList.<PlacementModifier>builder()
    			.add(CountPlacement.of(2))
    			.add(InSquarePlacement.spread())
    			.add(HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.absolute(BPConfig.WORLDGEN.chorusLanternHighlandsAmount.get() - 1)))
    			.add(CountPlacement.of(new WeightedListInt(SimpleWeightedRandomList.<IntProvider>builder().add(ConstantInt.of(1), 97).add(ConstantInt.of(2), 4).build()))).build());
    	 Holder<PlacedFeature> CHORUS_LANTERN_MIDLANDS_PATCH = register("chorus_lantern_midlands_patch", BPConfiguredFeatures.CHORUS_LANTERN_MIDLANDS_PATCH, 
    			ImmutableList.<PlacementModifier>builder()
    			.add(CountPlacement.of(1))
    			.add(InSquarePlacement.spread())
    			.add(HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.absolute(BPConfig.WORLDGEN.chorusLanternMidlandsAmount.get() - 1)))
    			.add(CountPlacement.of(new WeightedListInt(SimpleWeightedRandomList.<IntProvider>builder().add(ConstantInt.of(1), 97).add(ConstantInt.of(2), 4).build()))).build());
    	 Holder<PlacedFeature> ENREDE_KELP = register("enrede_kelp", BPConfiguredFeatures.ENREDE_KELP, 
    			ImmutableList.<PlacementModifier>builder()
    			.add(CountPlacement.of(8))
    			.add(InSquarePlacement.spread())
    			.add(NoiseThresholdCountPlacement.of(-0.8, 5, 10))
    			.add(HeightmapPlacement.onHeightmap(Types.OCEAN_FLOOR_WG)).build());
    	 Holder<PlacedFeature> CELESTIA_BUD = register("celestia_bud", BPConfiguredFeatures.CELESTIA_BUD, 
    			ImmutableList.<PlacementModifier>builder()
    			.add(CountPlacement.of(128))
    			.add(CountPlacement.of(6))
    			.add(NoiseThresholdCountPlacement.of(-0.8, 4, 8))
    			.add(InSquarePlacement.spread())
    			.add(HeightmapPlacement.onHeightmap(Types.MOTION_BLOCKING)).build());
    	 Holder<PlacedFeature> ENREDE_CORSASCILE = register("enrede_corsascile", BPConfiguredFeatures.ENREDE_CORSASCILE, 
    			ImmutableList.<PlacementModifier>builder()
    			.add(CountPlacement.of(80))
    			.add(InSquarePlacement.spread())
    			.add(HeightmapPlacement.onHeightmap(Types.OCEAN_FLOOR_WG)).build());
    	 Holder<PlacedFeature> OCHAIM_PURPLE = register("ochaim_purple", BPConfiguredFeatures.OCHAIM_PURPLE, 
    			ImmutableList.<PlacementModifier>builder()
    			.add(CountPlacement.of(50))
    			.add(InSquarePlacement.spread())
    			.add(HeightmapPlacement.onHeightmap(Types.OCEAN_FLOOR_WG)).build());
    	 Holder<PlacedFeature> OCHAIM_RED = register("ochaim_red", BPConfiguredFeatures.OCHAIM_RED, 
    			ImmutableList.<PlacementModifier>builder()
    			.add(CountPlacement.of(50))
    			.add(InSquarePlacement.spread())
    			.add(HeightmapPlacement.onHeightmap(Types.OCEAN_FLOOR_WG)).build());
    	 Holder<PlacedFeature> OCHAIM_GREEN = register("ochaim_green", BPConfiguredFeatures.OCHAIM_GREEN, 
    			ImmutableList.<PlacementModifier>builder()
    			.add(CountPlacement.of(50))
    			.add(InSquarePlacement.spread())
    			.add(HeightmapPlacement.onHeightmap(Types.OCEAN_FLOOR_WG)).build());
    	 Holder<PlacedFeature> END_ISLANDS_ICICLE_PATCH = register("end_islands_icicle_patch", BPConfiguredFeatures.END_ISLANDS_ICICLE_PATCH, 
    			ImmutableList.<PlacementModifier>builder()
    			.add(CountPlacement.of(2))
    			.add(InSquarePlacement.spread())
    			.add(HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.absolute(BPConfig.WORLDGEN.endIcicleIslandsAmount.get() - 1)))
    			.add(CountPlacement.of(new WeightedListInt(SimpleWeightedRandomList.<IntProvider>builder().add(ConstantInt.of(2), 97).add(ConstantInt.of(3), 4).build()))).build());
    	 Holder<PlacedFeature> END_FROZEN_ISLAND_DECORATED = register("end_frozen_island_decorated", BPConfiguredFeatures.END_FROZEN_ISLAND_DECORATED, 
    			ImmutableList.<PlacementModifier>builder()
    			.add(CountPlacement.of(1))
    			.add(InSquarePlacement.spread())
    			.add(HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.absolute(BPConfig.WORLDGEN.endFrozenIslandsAmount.get() - 1)))
    			.add(CountPlacement.of(new WeightedListInt(SimpleWeightedRandomList.<IntProvider>builder().add(ConstantInt.of(1), 99).add(ConstantInt.of(2), 2).build()))).build());
    	 Holder<PlacedFeature> IRION_GRASS = register("irion_grass", BPConfiguredFeatures.IRION_GRASS, 
    			ImmutableList.<PlacementModifier>builder()
    			.add(CountPlacement.of(32))
    			.add(InSquarePlacement.spread())
    			.add(HeightmapPlacement.onHeightmap(Types.MOTION_BLOCKING)).build());
    	 Holder<PlacedFeature> IRION_TALL_GRASS = register("irion_tall_grass", BPConfiguredFeatures.IRION_TALL_GRASS, 
    			ImmutableList.<PlacementModifier>builder()
    			.add(CountPlacement.of(13))
    			.add(NoiseThresholdCountPlacement.of(-0.8, 5, 10))
    			.add(HeightmapPlacement.onHeightmap(Types.MOTION_BLOCKING)).build());
    	 Holder<PlacedFeature> ARTAIRIUS = register("artairius", BPConfiguredFeatures.ARTAIRIUS, 
    			ImmutableList.<PlacementModifier>builder()
    			.add(NoiseThresholdCountPlacement.of(-0.8, 5, 10))
    			.add(HeightmapPlacement.onHeightmap(Types.MOTION_BLOCKING)).build());
    	 return GLACYNTH;
    }
    public static final ResourceKey<PlacedFeature> GLACYNTH_KEY = createKey("GLACYNTH");
    public static final ResourceKey<PlacedFeature> PINK_TWI_KEY = createKey("PINK_TWI");
    public static final ResourceKey<PlacedFeature> RED_TWI_KEY = createKey("RED_TWI");
    public static final ResourceKey<PlacedFeature> SPINXELTHORN_KEY = createKey("SPINXELTHORN");
    public static final ResourceKey<PlacedFeature> SPIRIT_DANGLER_KEY = createKey("SPIRIT_DANGLER");
    public static final ResourceKey<PlacedFeature> LAVA_SPIRE_KEY = createKey("LAVA_SPIRE");
    public static final ResourceKey<PlacedFeature> FLEIGNARITE_REMAINS_KEY = createKey("FLEIGNARITE_REMAINS");
    public static final ResourceKey<PlacedFeature> FLEIGNARITE_VINES_KEY = createKey("FLEIGNARITE_VINES");
    public static final ResourceKey<PlacedFeature> IRION_GRASS_KEY = createKey("IRION_GRASS");
    public static final ResourceKey<PlacedFeature> IRION_TALL_GRASS_KEY = createKey("IRION_TALL_GRASS");
    public static final ResourceKey<PlacedFeature> ARTAIRIUS_KEY = createKey("ARTAIRIUS");
    public static final ResourceKey<PlacedFeature> CYRA_LAKE_KEY = createKey("CYRA_LAKE");
    public static final ResourceKey<PlacedFeature> BYRSS_LANTERN_PLANT_KEY = createKey("BYRSS_LANTERN_PLANT");
    public static final ResourceKey<PlacedFeature> BYRSS_LANTERN_PLANT_PATCH_KEY = createKey("BYRSS_LANTERN_PLANT_PATCH");
    public static final ResourceKey<PlacedFeature> BYRSS_LANTERN_FOREST_PATCH_KEY = createKey("BYRSS_LANTERN_FOREST_PATCH");
    public static final ResourceKey<PlacedFeature> END_LAND_SPONGE_PATCH_ML_KEY = createKey("END_LAND_SPONGE_PATCH_ML");
    public static final ResourceKey<PlacedFeature> END_LAND_SPONGE_PATCH_HL_KEY = createKey("END_LAND_SPONGE_PATCH_HL");
    public static final ResourceKey<PlacedFeature> CAERI_CAVERN_KEY = createKey("CAERI_CAVERN");
    public static final ResourceKey<PlacedFeature> CHORUS_PLANT_KEY = createKey("CHORUS_PLANT");
    public static final ResourceKey<PlacedFeature> END_LAND_SPIKE_HL_KEY = createKey("END_LAND_SPIKE_HL");
    public static final ResourceKey<PlacedFeature> END_LAND_SPIKE_PATCH_HL_KEY = createKey("END_LAND_SPIKE_PATCH_HL");
    public static final ResourceKey<PlacedFeature> END_LAND_SPIKE_ML_KEY = createKey("END_LAND_SPIKE_ML");
    public static final ResourceKey<PlacedFeature> END_LAND_SPIKE_PATCH_ML_KEY = createKey("END_LAND_SPIKE_PATCH_ML");
    public static final ResourceKey<PlacedFeature> CHORUS_LANTERN_PLANT_KEY = createKey("CHORUS_LANTERN_PLANT");
    public static final ResourceKey<PlacedFeature> CHORUS_LANTERN_HIGHLANDS_PATCH_KEY = createKey("CHORUS_LANTERN_HIGHLANDS_PATCH");
    public static final ResourceKey<PlacedFeature> CHORUS_LANTERN_MIDLANDS_PATCH_KEY = createKey("CHORUS_LANTERN_MIDLANDS_PATCH");
    public static final ResourceKey<PlacedFeature> ENREDE_KELP_KEY = createKey("ENREDE_KELP");
    public static final ResourceKey<PlacedFeature> CELESTIA_BUD_KEY = createKey("CELESTIA_BUD");
    public static final ResourceKey<PlacedFeature> ENREDE_CORSASCILE_KEY = createKey("ENREDE_CORSASCILE");
    public static final ResourceKey<PlacedFeature> OCHAIM_PURPLE_KEY = createKey("OCHAIM_PURPLE");
    public static final ResourceKey<PlacedFeature> OCHAIM_RED_KEY = createKey("OCHAIM_RED");
    public static final ResourceKey<PlacedFeature> OCHAIM_GREEN_KEY = createKey("OCHAIM_GREEN");
    public static final ResourceKey<PlacedFeature> END_ISLANDS_ICICLE_KEY = createKey("END_ISLANDS_ICICLE");
    public static final ResourceKey<PlacedFeature> END_ISLANDS_ICICLE_PATCH_KEY = createKey("END_ISLANDS_ICICLE_PATCH");
    public static final ResourceKey<PlacedFeature> END_FROZEN_ISLAND_KEY = createKey("END_FROZEN_ISLAND");
    public static final ResourceKey<PlacedFeature> END_FROZEN_ISLAND_DECORATED_KEY = createKey("END_FROZEN_ISLAND_DECORATED");
    
	public static final Holder<PlacedFeature> GLACYNTH = BuiltinRegistries.PLACED_FEATURE.getOrCreateHolderOrThrow(GLACYNTH_KEY);
	public static final Holder<PlacedFeature> PINK_TWI = BuiltinRegistries.PLACED_FEATURE.getOrCreateHolderOrThrow(PINK_TWI_KEY);
	public static final Holder<PlacedFeature> RED_TWI = BuiltinRegistries.PLACED_FEATURE.getOrCreateHolderOrThrow(RED_TWI_KEY);
	public static final Holder<PlacedFeature> SPINXELTHORN = BuiltinRegistries.PLACED_FEATURE.getOrCreateHolderOrThrow(SPINXELTHORN_KEY);
	public static final Holder<PlacedFeature> SPIRIT_DANGLER = BuiltinRegistries.PLACED_FEATURE.getOrCreateHolderOrThrow(SPIRIT_DANGLER_KEY);
	public static final Holder<PlacedFeature> LAVA_SPIRE = BuiltinRegistries.PLACED_FEATURE.getOrCreateHolderOrThrow(LAVA_SPIRE_KEY);
	public static final Holder<PlacedFeature> FLEIGNARITE_REMAINS = BuiltinRegistries.PLACED_FEATURE.getOrCreateHolderOrThrow(FLEIGNARITE_REMAINS_KEY);
	public static final Holder<PlacedFeature> FLEIGNARITE_VINES = BuiltinRegistries.PLACED_FEATURE.getOrCreateHolderOrThrow(FLEIGNARITE_VINES_KEY);
	public static final Holder<PlacedFeature> CYRA_LAKE = BuiltinRegistries.PLACED_FEATURE.getOrCreateHolderOrThrow(CYRA_LAKE_KEY);
	public static final Holder<PlacedFeature> BYRSS_LANTERN_PLANT_PATCH = BuiltinRegistries.PLACED_FEATURE.getOrCreateHolderOrThrow(BYRSS_LANTERN_PLANT_PATCH_KEY);
	public static final Holder<PlacedFeature> BYRSS_LANTERN_FOREST_PATCH = BuiltinRegistries.PLACED_FEATURE.getOrCreateHolderOrThrow(BYRSS_LANTERN_FOREST_PATCH_KEY);
	public static final Holder<PlacedFeature> END_LAND_SPONGE_PATCH_ML = BuiltinRegistries.PLACED_FEATURE.getOrCreateHolderOrThrow(END_LAND_SPONGE_PATCH_ML_KEY);
	public static final Holder<PlacedFeature> END_LAND_SPONGE_PATCH_HL = BuiltinRegistries.PLACED_FEATURE.getOrCreateHolderOrThrow(END_LAND_SPONGE_PATCH_HL_KEY);
	public static final Holder<PlacedFeature> CAERI_CAVERN = BuiltinRegistries.PLACED_FEATURE.getOrCreateHolderOrThrow(CAERI_CAVERN_KEY);
	public static final Holder<PlacedFeature> CHORUS_MYCHRODEGIA = BuiltinRegistries.PLACED_FEATURE.getOrCreateHolderOrThrow(CHORUS_PLANT_KEY);
	public static final Holder<PlacedFeature> END_LAND_SPIKE_PATCH_HL = BuiltinRegistries.PLACED_FEATURE.getOrCreateHolderOrThrow(END_LAND_SPIKE_PATCH_HL_KEY);
	public static final Holder<PlacedFeature> END_LAND_SPIKE_PATCH_ML = BuiltinRegistries.PLACED_FEATURE.getOrCreateHolderOrThrow(END_LAND_SPIKE_PATCH_ML_KEY);
	public static final Holder<PlacedFeature> CHORUS_LANTERN_HIGHLANDS_PATCH = BuiltinRegistries.PLACED_FEATURE.getOrCreateHolderOrThrow(CHORUS_LANTERN_HIGHLANDS_PATCH_KEY);
	public static final Holder<PlacedFeature> CHORUS_LANTERN_MIDLANDS_PATCH = BuiltinRegistries.PLACED_FEATURE.getOrCreateHolderOrThrow(CHORUS_LANTERN_MIDLANDS_PATCH_KEY);
	public static final Holder<PlacedFeature> ENREDE_KELP = BuiltinRegistries.PLACED_FEATURE.getOrCreateHolderOrThrow(ENREDE_KELP_KEY);
	public static final Holder<PlacedFeature> CELESTIA_BUD = BuiltinRegistries.PLACED_FEATURE.getOrCreateHolderOrThrow(CELESTIA_BUD_KEY);
	public static final Holder<PlacedFeature> ENREDE_CORSASCILE = BuiltinRegistries.PLACED_FEATURE.getOrCreateHolderOrThrow(ENREDE_CORSASCILE_KEY);
	public static final Holder<PlacedFeature> OCHAIM_PURPLE = BuiltinRegistries.PLACED_FEATURE.getOrCreateHolderOrThrow(OCHAIM_PURPLE_KEY);
	public static final Holder<PlacedFeature> OCHAIM_RED = BuiltinRegistries.PLACED_FEATURE.getOrCreateHolderOrThrow(OCHAIM_RED_KEY);
	public static final Holder<PlacedFeature> OCHAIM_GREEN = BuiltinRegistries.PLACED_FEATURE.getOrCreateHolderOrThrow(OCHAIM_GREEN_KEY);
	public static final Holder<PlacedFeature> END_ISLANDS_ICICLE_PATCH = BuiltinRegistries.PLACED_FEATURE.getOrCreateHolderOrThrow(END_ISLANDS_ICICLE_PATCH_KEY);
	public static final Holder<PlacedFeature> END_FROZEN_ISLAND_DECORATED = BuiltinRegistries.PLACED_FEATURE.getOrCreateHolderOrThrow(END_FROZEN_ISLAND_DECORATED_KEY);
	public static final Holder<PlacedFeature> IRION_GRASS = BuiltinRegistries.PLACED_FEATURE.getOrCreateHolderOrThrow(IRION_GRASS_KEY);
	public static final Holder<PlacedFeature> IRION_TALL_GRASS = BuiltinRegistries.PLACED_FEATURE.getOrCreateHolderOrThrow(IRION_TALL_GRASS_KEY);
	public static final Holder<PlacedFeature> ARTAIRIUS = BuiltinRegistries.PLACED_FEATURE.getOrCreateHolderOrThrow(ARTAIRIUS_KEY);
	
	public static ResourceKey<PlacedFeature> createKey(String name) {
		return ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY, new ResourceLocation(Bioplethora.MOD_ID, name.toLowerCase(Locale.ROOT)));
	}
	
	private static ImmutableList.Builder<PlacementModifier> createPlacementModifier(int count) {
		return ImmutableList.<PlacementModifier>builder().add(CountPlacement.of(count), InSquarePlacement.spread(), BiomeFilter.biome());
	}
	
	private static ImmutableList.Builder<PlacementModifier> createPlacementModifier(int count, int mininclusive, int maxinclusive) {
		return ImmutableList.<PlacementModifier>builder().add(CountPlacement.of(count), InSquarePlacement.spread(), BiomeFilter.biome(), HeightRangePlacement.uniform(VerticalAnchor.absolute(mininclusive), VerticalAnchor.absolute(maxinclusive)));
	}
	
	public static Holder<PlacedFeature> register(String name, Holder<ConfiguredFeature<?, ?>> feature, List<PlacementModifier> placements) {
		return BuiltinRegistries.register(BuiltinRegistries.PLACED_FEATURE, prefix(name).toString(), new PlacedFeature(feature, List.copyOf(placements)));
	}
	
	public static ResourceLocation prefix(String name) {
		return new ResourceLocation(Bioplethora.MOD_ID, name.toLowerCase(Locale.ROOT));
	}
}
