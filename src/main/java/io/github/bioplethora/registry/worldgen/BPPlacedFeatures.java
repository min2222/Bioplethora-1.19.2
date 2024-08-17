package io.github.bioplethora.registry.worldgen;

import java.util.List;
import java.util.Locale;

import com.google.common.collect.ImmutableList;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.config.BPConfig;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
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

public class BPPlacedFeatures {
	public static final ResourceKey<PlacedFeature> GLACYNTH = registerKey("glacynth");
	public static final ResourceKey<PlacedFeature> PINK_TWI = registerKey("pink_twi");
	public static final ResourceKey<PlacedFeature> RED_TWI = registerKey("red_twi");
	public static final ResourceKey<PlacedFeature> SPINXELTHORN = registerKey("spinxelthorn");
	public static final ResourceKey<PlacedFeature> SPIRIT_DANGLER = registerKey("spirit_dangler");
	public static final ResourceKey<PlacedFeature> LAVA_SPIRE = registerKey("lava_spire");
	public static final ResourceKey<PlacedFeature> FLEIGNARITE_REMAINS = registerKey("fleignarite_remains");
	public static final ResourceKey<PlacedFeature> FLEIGNARITE_VINES = registerKey("fleignarite_vines");
	public static final ResourceKey<PlacedFeature> CYRA_LAKE = registerKey("cyra_lake");
	public static final ResourceKey<PlacedFeature> BYRSS_LANTERN_PLANT_PATCH = registerKey("byrss_lantern_plant_patch");
	public static final ResourceKey<PlacedFeature> BYRSS_LANTERN_FOREST_PATCH = registerKey("byrss_lantern_forest_patch");
	public static final ResourceKey<PlacedFeature> END_LAND_SPONGE_PATCH_ML = registerKey("end_land_sponge_patch_ml");
	public static final ResourceKey<PlacedFeature> END_LAND_SPONGE_PATCH_HL = registerKey("end_land_sponge_patch_hl");
	public static final ResourceKey<PlacedFeature> CAERI_CAVERN = registerKey("caeri_cavern");
	public static final ResourceKey<PlacedFeature> CHORUS_MYCHRODEGIA = registerKey("chorus_plant");
	public static final ResourceKey<PlacedFeature> END_LAND_SPIKE_PATCH_HL = registerKey("end_land_spike_patch_hl");
	public static final ResourceKey<PlacedFeature> END_LAND_SPIKE_PATCH_ML = registerKey("end_land_spike_patch_ml");
	public static final ResourceKey<PlacedFeature> CHORUS_LANTERN_HIGHLANDS_PATCH = registerKey("chorus_lantern_highlands_patch");
	public static final ResourceKey<PlacedFeature> CHORUS_LANTERN_MIDLANDS_PATCH = registerKey("chorus_lantern_midlands_patch");
	public static final ResourceKey<PlacedFeature> ENREDE_KELP = registerKey("enrede_kelp");
	public static final ResourceKey<PlacedFeature> CELESTIA_BUD = registerKey("celestia_bud");
	public static final ResourceKey<PlacedFeature> ENREDE_CORSASCILE = registerKey("enrede_corsascile");
	public static final ResourceKey<PlacedFeature> OCHAIM_PURPLE = registerKey("ochaim_purple");
	public static final ResourceKey<PlacedFeature> OCHAIM_RED = registerKey("ochaim_red");
	public static final ResourceKey<PlacedFeature> OCHAIM_GREEN = registerKey("ochaim_green");
	public static final ResourceKey<PlacedFeature> END_ISLANDS_ICICLE_PATCH = registerKey("end_islands_icicle_patch");
	public static final ResourceKey<PlacedFeature> END_FROZEN_ISLAND_DECORATED = registerKey("end_frozen_island_decorated");
	public static final ResourceKey<PlacedFeature> IRION_GRASS = registerKey("irion_grass");
	public static final ResourceKey<PlacedFeature> IRION_TALL_GRASS = registerKey("irion_tall_grass");
	public static final ResourceKey<PlacedFeature> ARTAIRIUS = registerKey("artairius");
	public static final ResourceKey<PlacedFeature> FROSTEM = registerKey("frostem");
	public static final ResourceKey<PlacedFeature> BASALT_SPELEOTHERM = registerKey("basalt_speleotherm");
	public static final ResourceKey<PlacedFeature> THONTUS_THISTLE = registerKey("thontus_thistle");
	public static final ResourceKey<PlacedFeature> TURQUOISE_PENDENT = registerKey("turquoise_pendent");
	public static final ResourceKey<PlacedFeature> CERISE_IVY = registerKey("cerise_ivy");
	public static final ResourceKey<PlacedFeature> SOUL_ETERN = registerKey("soul_etern");
	public static final ResourceKey<PlacedFeature> CHORUS_IDON = registerKey("chorus_idon");
	public static final ResourceKey<PlacedFeature> CHORUS_IDE_FAN = registerKey("chorus_ide_fan");
	public static final ResourceKey<PlacedFeature> WARPED_DANCER = registerKey("warped_dancer");
	public static final ResourceKey<PlacedFeature> SOUL_MINISHROOM = registerKey("soul_minishroom");
	public static final ResourceKey<PlacedFeature> SOUL_BIGSHROOM = registerKey("soul_bigshroom");
	public static final ResourceKey<PlacedFeature> SOUL_SPROUTS = registerKey("soul_sprouts");
	public static final ResourceKey<PlacedFeature> SOUL_TALL_GRASS = registerKey("soul_tall_grass");
	public static final ResourceKey<PlacedFeature> KYRIA = registerKey("kyria");
	public static final ResourceKey<PlacedFeature> KYRIA_BELINE = registerKey("kyria_beline");
	public static final ResourceKey<PlacedFeature> KYRIA_IDE_FAN = registerKey("kyria_ide_fan");
	public static final ResourceKey<PlacedFeature> CRYEANUM_FOREST_TREES = registerKey("enivile_tree");
	public static final ResourceKey<PlacedFeature> CAERI_FOREST_TREES = registerKey("caerulwood_tree");
	
	public static ResourceKey<PlacedFeature> registerKey(String name) {
		return ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(Bioplethora.MOD_ID, name));
	}

	public static void bootstrap(BootstapContext<PlacedFeature> context) {
		HolderGetter<ConfiguredFeature<?, ?>> features = context.lookup(Registries.CONFIGURED_FEATURE);
		context.register(GLACYNTH, register(features.getOrThrow(BPConfiguredFeatures.GLACYNTH), createPlacementModifier(115, 0, 131).build()));
		context.register(PINK_TWI, register(features.getOrThrow(BPConfiguredFeatures.PINK_TWI), createPlacementModifier(115, 0, 131).build()));
		context.register(RED_TWI, register(features.getOrThrow(BPConfiguredFeatures.RED_TWI), createPlacementModifier(105, 0, 119).build()));
		context.register(SPINXELTHORN, register(features.getOrThrow(BPConfiguredFeatures.SPINXELTHORN), createPlacementModifier(132, 0, 155).build()));
		context.register(SPIRIT_DANGLER, register(features.getOrThrow(BPConfiguredFeatures.SPIRIT_DANGLER), createPlacementModifier(115, 0, 131).build()));
		context.register(LAVA_SPIRE, register(features.getOrThrow(BPConfiguredFeatures.LAVA_SPIRE), createPlacementModifier(19).build()));
		context.register(FLEIGNARITE_REMAINS, register(features.getOrThrow(BPConfiguredFeatures.FLEIGNARITE_REMAINS), 
				ImmutableList.<PlacementModifier>builder().add(CarvingMaskPlacement.forStep(Carving.AIR)).add(RarityFilter.onAverageOnceEvery(10)).add(CountPlacement.of(UniformInt.of(14, 41))).build()));
		context.register(FLEIGNARITE_VINES, register(features.getOrThrow(BPConfiguredFeatures.FLEIGNARITE_VINES), 
				ImmutableList.<PlacementModifier>builder().add(CountPlacement.of(256)).add(InSquarePlacement.spread()).add(HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.absolute(127))).build()));
		context.register(CYRA_LAKE, register(features.getOrThrow(BPConfiguredFeatures.CYRA_LAKE), 
				ImmutableList.<PlacementModifier>builder().add(CountPlacement.of(1)).add(InSquarePlacement.spread()).add(HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.absolute(BPConfig.WORLDGEN.cyraLakesEndAmount.get() - 1))).build()));
		context.register(BYRSS_LANTERN_PLANT_PATCH, register(features.getOrThrow(BPConfiguredFeatures.BYRSS_LANTERN_PLANT_PATCH), 
				ImmutableList.<PlacementModifier>builder()
				.add(CountPlacement.of(2))
				.add(InSquarePlacement.spread())
				.add(HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.absolute(54)))
				.add(CountPlacement.of(new WeightedListInt(SimpleWeightedRandomList.<IntProvider>builder().add(ConstantInt.of(2), 98).add(ConstantInt.of(3), 3).build()))).build()));
		context.register(BYRSS_LANTERN_FOREST_PATCH, register(features.getOrThrow(BPConfiguredFeatures.BYRSS_LANTERN_FOREST_PATCH), 
				ImmutableList.<PlacementModifier>builder()
				.add(CountPlacement.of(5))
				.add(InSquarePlacement.spread())
				.add(HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.absolute(59)))
				.add(CountPlacement.of(new WeightedListInt(SimpleWeightedRandomList.<IntProvider>builder().add(ConstantInt.of(2), 98).add(ConstantInt.of(3), 2).build()))).build()));
		context.register(END_LAND_SPONGE_PATCH_ML, register(features.getOrThrow(BPConfiguredFeatures.END_LAND_SPONGE_PATCH_ML), 
				ImmutableList.<PlacementModifier>builder()
				.add(CountOnEveryLayerPlacement.of(50)).build()));
		context.register(END_LAND_SPONGE_PATCH_HL, register(features.getOrThrow(BPConfiguredFeatures.END_LAND_SPONGE_PATCH_HL), 
				ImmutableList.<PlacementModifier>builder()
				.add(CountOnEveryLayerPlacement.of(40)).build()));
		context.register(CAERI_CAVERN, register(features.getOrThrow(BPConfiguredFeatures.CAERI_CAVERN), 
				ImmutableList.<PlacementModifier>builder()
				.add(CountPlacement.of(1))
				.add(InSquarePlacement.spread())
				.add(HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.absolute(6)))
				.add(CountPlacement.of(new WeightedListInt(SimpleWeightedRandomList.<IntProvider>builder().add(ConstantInt.of(1), 99).add(ConstantInt.of(2), 2).build()))).build()));
		context.register(CHORUS_MYCHRODEGIA, register(features.getOrThrow(BPConfiguredFeatures.CHORUS_MYCHRODEGIA), 
				ImmutableList.<PlacementModifier>builder()
				.add(CountPlacement.of(UniformInt.of(0, 1)))
				.add(InSquarePlacement.spread())
				.add(HeightmapPlacement.onHeightmap(Types.MOTION_BLOCKING)).build()));
		context.register(END_LAND_SPIKE_PATCH_HL, register(features.getOrThrow(BPConfiguredFeatures.END_LAND_SPIKE_PATCH_HL), 
				ImmutableList.<PlacementModifier>builder()
				.add(CountPlacement.of(5))
				.add(InSquarePlacement.spread())
				.add(HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.absolute(BPConfig.WORLDGEN.endSpikeHighlandsAmount.get() - 1)))
				.add(CountPlacement.of(new WeightedListInt(SimpleWeightedRandomList.<IntProvider>builder().add(ConstantInt.of(2), 85).add(ConstantInt.of(3), 15).build()))).build()));
		context.register(END_LAND_SPIKE_PATCH_ML, register(features.getOrThrow(BPConfiguredFeatures.END_LAND_SPIKE_PATCH_ML), 
				ImmutableList.<PlacementModifier>builder()
				.add(CountPlacement.of(1))
				.add(InSquarePlacement.spread())
				.add(HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.absolute(BPConfig.WORLDGEN.endSpikeMidlandsAmount.get() - 1)))
				.add(CountPlacement.of(new WeightedListInt(SimpleWeightedRandomList.<IntProvider>builder().add(ConstantInt.of(1), 55).add(ConstantInt.of(2), 45).build()))).build()));
		context.register(CHORUS_LANTERN_HIGHLANDS_PATCH, register(features.getOrThrow(BPConfiguredFeatures.CHORUS_LANTERN_HIGHLANDS_PATCH), 
				ImmutableList.<PlacementModifier>builder()
				.add(CountPlacement.of(2))
				.add(InSquarePlacement.spread())
				.add(HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.absolute(BPConfig.WORLDGEN.chorusLanternHighlandsAmount.get() - 1)))
				.add(CountPlacement.of(new WeightedListInt(SimpleWeightedRandomList.<IntProvider>builder().add(ConstantInt.of(1), 97).add(ConstantInt.of(2), 4).build()))).build()));
		context.register(CHORUS_LANTERN_MIDLANDS_PATCH, register(features.getOrThrow(BPConfiguredFeatures.CHORUS_LANTERN_MIDLANDS_PATCH), 
				ImmutableList.<PlacementModifier>builder()
				.add(CountPlacement.of(1))
				.add(InSquarePlacement.spread())
				.add(HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.absolute(BPConfig.WORLDGEN.chorusLanternMidlandsAmount.get() - 1)))
				.add(CountPlacement.of(new WeightedListInt(SimpleWeightedRandomList.<IntProvider>builder().add(ConstantInt.of(1), 97).add(ConstantInt.of(2), 4).build()))).build()));
		context.register(ENREDE_KELP, register(features.getOrThrow(BPConfiguredFeatures.ENREDE_KELP), 
				ImmutableList.<PlacementModifier>builder()
				.add(CountPlacement.of(8))
				.add(InSquarePlacement.spread())
				.add(NoiseThresholdCountPlacement.of(-0.8, 5, 10))
				.add(HeightmapPlacement.onHeightmap(Types.OCEAN_FLOOR_WG)).build()));
		context.register(CELESTIA_BUD, register(features.getOrThrow(BPConfiguredFeatures.CELESTIA_BUD), 
				ImmutableList.<PlacementModifier>builder()
				.add(CountPlacement.of(128))
				.add(CountPlacement.of(6))
				.add(NoiseThresholdCountPlacement.of(-0.8, 4, 8))
				.add(InSquarePlacement.spread())
				.add(HeightmapPlacement.onHeightmap(Types.MOTION_BLOCKING)).build()));
		context.register(ENREDE_CORSASCILE, register(features.getOrThrow(BPConfiguredFeatures.ENREDE_CORSASCILE), 
				ImmutableList.<PlacementModifier>builder()
				.add(CountPlacement.of(80))
				.add(InSquarePlacement.spread())
				.add(HeightmapPlacement.onHeightmap(Types.OCEAN_FLOOR_WG)).build()));
		context.register(OCHAIM_PURPLE, register(features.getOrThrow(BPConfiguredFeatures.OCHAIM_PURPLE), 
				ImmutableList.<PlacementModifier>builder()
				.add(CountPlacement.of(50))
				.add(InSquarePlacement.spread())
				.add(HeightmapPlacement.onHeightmap(Types.OCEAN_FLOOR_WG)).build()));
		context.register(OCHAIM_RED, register(features.getOrThrow(BPConfiguredFeatures.OCHAIM_RED), 
				ImmutableList.<PlacementModifier>builder()
				.add(CountPlacement.of(50))
				.add(InSquarePlacement.spread())
				.add(HeightmapPlacement.onHeightmap(Types.OCEAN_FLOOR_WG)).build()));
		context.register(OCHAIM_GREEN, register(features.getOrThrow(BPConfiguredFeatures.OCHAIM_GREEN), 
				ImmutableList.<PlacementModifier>builder()
				.add(CountPlacement.of(50))
				.add(InSquarePlacement.spread())
				.add(HeightmapPlacement.onHeightmap(Types.OCEAN_FLOOR_WG)).build()));
		context.register(END_ISLANDS_ICICLE_PATCH, register(features.getOrThrow(BPConfiguredFeatures.END_ISLANDS_ICICLE_PATCH), 
				ImmutableList.<PlacementModifier>builder()
				.add(CountPlacement.of(2))
				.add(InSquarePlacement.spread())
				.add(HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.absolute(BPConfig.WORLDGEN.endIcicleIslandsAmount.get() - 1)))
				.add(CountPlacement.of(new WeightedListInt(SimpleWeightedRandomList.<IntProvider>builder().add(ConstantInt.of(2), 97).add(ConstantInt.of(3), 4).build()))).build()));
		context.register(END_FROZEN_ISLAND_DECORATED, register(features.getOrThrow(BPConfiguredFeatures.END_FROZEN_ISLAND_DECORATED), 
				ImmutableList.<PlacementModifier>builder()
				.add(CountPlacement.of(1))
				.add(InSquarePlacement.spread())
				.add(HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.absolute(BPConfig.WORLDGEN.endFrozenIslandsAmount.get() - 1)))
				.add(CountPlacement.of(new WeightedListInt(SimpleWeightedRandomList.<IntProvider>builder().add(ConstantInt.of(1), 99).add(ConstantInt.of(2), 2).build()))).build()));
		context.register(IRION_GRASS, register(features.getOrThrow(BPConfiguredFeatures.IRION_GRASS), 
				ImmutableList.<PlacementModifier>builder()
				.add(CountPlacement.of(32))
				.add(InSquarePlacement.spread())
				.add(HeightmapPlacement.onHeightmap(Types.MOTION_BLOCKING)).build()));
		context.register(IRION_TALL_GRASS, register(features.getOrThrow(BPConfiguredFeatures.IRION_TALL_GRASS), 
				ImmutableList.<PlacementModifier>builder()
				.add(CountPlacement.of(13))
				.add(NoiseThresholdCountPlacement.of(-0.8, 5, 10))
				.add(HeightmapPlacement.onHeightmap(Types.MOTION_BLOCKING)).build()));
		context.register(ARTAIRIUS, register(features.getOrThrow(BPConfiguredFeatures.ARTAIRIUS), 
				ImmutableList.<PlacementModifier>builder()
				.add(NoiseThresholdCountPlacement.of(-0.8, 5, 10))
				.add(HeightmapPlacement.onHeightmap(Types.MOTION_BLOCKING)).build()));
		context.register(FROSTEM, register(features.getOrThrow(BPConfiguredFeatures.FROSTEM), 
				ImmutableList.<PlacementModifier>builder()
				.add(CountPlacement.of(48))
				.add(HeightmapPlacement.onHeightmap(Types.MOTION_BLOCKING)).build()));
		context.register(BASALT_SPELEOTHERM, register(features.getOrThrow(BPConfiguredFeatures.BASALT_SPELEOTHERM), 
				ImmutableList.<PlacementModifier>builder()
				.add(CountPlacement.of(115))
				.add(HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.absolute(131))).build()));
		context.register(THONTUS_THISTLE, register(features.getOrThrow(BPConfiguredFeatures.THONTUS_THISTLE), 
				ImmutableList.<PlacementModifier>builder()
				.add(CountPlacement.of(125))
				.add(HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.absolute(131))).build()));
		context.register(TURQUOISE_PENDENT, register(features.getOrThrow(BPConfiguredFeatures.TURQUOISE_PENDENT), 
				ImmutableList.<PlacementModifier>builder()
				.add(CountPlacement.of(122))
				.add(HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.absolute(131))).build()));
		context.register(CERISE_IVY, register(features.getOrThrow(BPConfiguredFeatures.CERISE_IVY), 
				ImmutableList.<PlacementModifier>builder()
				.add(CountPlacement.of(122))
				.add(HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.absolute(131))).build()));
		context.register(SOUL_ETERN, register(features.getOrThrow(BPConfiguredFeatures.SOUL_ETERN), 
				ImmutableList.<PlacementModifier>builder()
				.add(CountPlacement.of(125))
				.add(HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.absolute(131))).build()));
		context.register(CHORUS_IDON, register(features.getOrThrow(BPConfiguredFeatures.CHORUS_IDON), 
				ImmutableList.<PlacementModifier>builder()
				.add(CountPlacement.of(32))
				.add(HeightmapPlacement.onHeightmap(Types.MOTION_BLOCKING)).build()));
		context.register(CHORUS_IDE_FAN, register(features.getOrThrow(BPConfiguredFeatures.CHORUS_IDE_FAN), 
				ImmutableList.<PlacementModifier>builder()
				.add(CountPlacement.of(12))
				.add(NoiseThresholdCountPlacement.of(-0.8, 4, 7))
				.add(HeightmapPlacement.onHeightmap(Types.MOTION_BLOCKING)).build()));
		context.register(WARPED_DANCER, register(features.getOrThrow(BPConfiguredFeatures.WARPED_DANCER), 
				ImmutableList.<PlacementModifier>builder()
				.add(CountPlacement.of(15))
				.add(HeightmapPlacement.onHeightmap(Types.MOTION_BLOCKING)).build()));
		context.register(SOUL_MINISHROOM, register(features.getOrThrow(BPConfiguredFeatures.SOUL_MINISHROOM), 
				ImmutableList.<PlacementModifier>builder()
				.add(CountPlacement.of(15))
				.add(HeightmapPlacement.onHeightmap(Types.MOTION_BLOCKING)).build()));
		context.register(SOUL_BIGSHROOM, register(features.getOrThrow(BPConfiguredFeatures.SOUL_BIGSHROOM), 
				ImmutableList.<PlacementModifier>builder()
				.add(CountPlacement.of(12))
				.add(HeightmapPlacement.onHeightmap(Types.MOTION_BLOCKING)).build()));
		context.register(SOUL_SPROUTS, register(features.getOrThrow(BPConfiguredFeatures.SOUL_SPROUTS), 
				ImmutableList.<PlacementModifier>builder()
				.add(CountPlacement.of(26))
				.add(HeightmapPlacement.onHeightmap(Types.MOTION_BLOCKING)).build()));
		context.register(SOUL_TALL_GRASS, register(features.getOrThrow(BPConfiguredFeatures.SOUL_TALL_GRASS), 
				ImmutableList.<PlacementModifier>builder()
				.add(CountPlacement.of(22))
				.add(NoiseThresholdCountPlacement.of(-0.8D, 5, 10))
				.add(HeightmapPlacement.onHeightmap(Types.MOTION_BLOCKING)).build()));
		context.register(KYRIA, register(features.getOrThrow(BPConfiguredFeatures.KYRIA), 
				ImmutableList.<PlacementModifier>builder()
				.add(CountPlacement.of(55))
				.add(CountPlacement.of(18))
				.add(HeightmapPlacement.onHeightmap(Types.MOTION_BLOCKING)).build()));
		context.register(KYRIA_BELINE, register(features.getOrThrow(BPConfiguredFeatures.KYRIA_BELINE), 
				ImmutableList.<PlacementModifier>builder()
				.add(CountPlacement.of(19))
				.add(NoiseThresholdCountPlacement.of(-0.8D, 5, 10))
				.add(HeightmapPlacement.onHeightmap(Types.MOTION_BLOCKING)).build()));
		context.register(KYRIA_IDE_FAN, register(features.getOrThrow(BPConfiguredFeatures.KYRIA_IDE_FAN), 
				ImmutableList.<PlacementModifier>builder()
				.add(CountPlacement.of(12))
				.add(CountPlacement.of(30))
				.add(NoiseThresholdCountPlacement.of(-0.8D, 4, 7))
				.add(HeightmapPlacement.onHeightmap(Types.MOTION_BLOCKING)).build()));
		context.register(CRYEANUM_FOREST_TREES, register(features.getOrThrow(BPConfiguredFeatures.ENIVILE_TREE_CONFIG), 
				ImmutableList.<PlacementModifier>builder().
				add(CountOnEveryLayerPlacement.of(2), BiomeFilter.biome()).build()));
		context.register(CAERI_FOREST_TREES, register(features.getOrThrow(BPConfiguredFeatures.CAERULWOOD_TREE_CONFIG), 
				ImmutableList.<PlacementModifier>builder()
				.add(InSquarePlacement.spread(), BiomeFilter.biome(), CountPlacement.of(new WeightedListInt(SimpleWeightedRandomList.<IntProvider>builder()
						.add(ConstantInt.of(6), 85)
						.add(ConstantInt.of(7), 15).build()))).build()));
	}
	
	private static ImmutableList.Builder<PlacementModifier> createPlacementModifier(int count) {
		return ImmutableList.<PlacementModifier>builder().add(CountPlacement.of(count), InSquarePlacement.spread(), BiomeFilter.biome());
	}
	
	private static ImmutableList.Builder<PlacementModifier> createPlacementModifier(int count, int mininclusive, int maxinclusive) {
		return ImmutableList.<PlacementModifier>builder().add(CountPlacement.of(count), InSquarePlacement.spread(), BiomeFilter.biome(), HeightRangePlacement.uniform(VerticalAnchor.absolute(mininclusive), VerticalAnchor.absolute(maxinclusive)));
	}
	
	public static PlacedFeature register(Holder<ConfiguredFeature<?, ?>> feature, List<PlacementModifier> placements) {
		return new PlacedFeature(feature, List.copyOf(placements));
	}
	
	public static ResourceLocation prefix(String name) {
		return new ResourceLocation(Bioplethora.MOD_ID, name.toLowerCase(Locale.ROOT));
	}
}