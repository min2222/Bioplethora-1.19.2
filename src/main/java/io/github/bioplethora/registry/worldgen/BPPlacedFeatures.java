package io.github.bioplethora.registry.worldgen;

import java.util.List;
import java.util.Locale;

import com.google.common.collect.ImmutableList;

import io.github.bioplethora.Bioplethora;
import net.minecraft.core.Holder;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;

public class BPPlacedFeatures {
	
	public static final Holder<PlacedFeature> GLACYNTH = register("glacynth", BPConfiguredFeatures.GLACYNTH, createPlacementModifier(115, 0, 131).build());
	public static final Holder<PlacedFeature> PINK_TWI = register("pink_twi", BPConfiguredFeatures.PINK_TWI, createPlacementModifier(115, 0, 131).build());
	public static final Holder<PlacedFeature> RED_TWI = register("red_twi", BPConfiguredFeatures.RED_TWI, createPlacementModifier(105, 0, 119).build());
	public static final Holder<PlacedFeature> SPINXELTHORN = register("spinxelthorn", BPConfiguredFeatures.SPINXELTHORN, createPlacementModifier(132, 0, 155).build());
	public static final Holder<PlacedFeature> SPIRIT_DANGLER = register("spirit_dangler", BPConfiguredFeatures.SPIRIT_DANGLER, createPlacementModifier(115, 0, 131).build());
	public static final Holder<PlacedFeature> LAVA_SPIRE = register("lava_spire", BPConfiguredFeatures.LAVA_SPIRE, createPlacementModifier(19).build());
	
	private static ImmutableList.Builder<PlacementModifier> createPlacementModifier(int count) {
		return ImmutableList.<PlacementModifier>builder().add(CountPlacement.of(count), InSquarePlacement.spread(), BiomeFilter.biome());
	}
	
	private static ImmutableList.Builder<PlacementModifier> createPlacementModifier(int count, int mininclusive, int maxinclusive) {
		return ImmutableList.<PlacementModifier>builder().add(CountPlacement.of(count), InSquarePlacement.spread(), BiomeFilter.biome(), HeightRangePlacement.uniform(VerticalAnchor.absolute(mininclusive), VerticalAnchor.absolute(maxinclusive)));
	}
	
	public static Holder<PlacedFeature> register(String name, Holder<? extends ConfiguredFeature<?, ?>> feature, List<PlacementModifier> placements) {
		return BuiltinRegistries.registerExact(BuiltinRegistries.PLACED_FEATURE, prefix(name).toString(), new PlacedFeature(Holder.hackyErase(feature), List.copyOf(placements)));
	}
	
	public static ResourceLocation prefix(String name) {
		return new ResourceLocation(Bioplethora.MOD_ID, name.toLowerCase(Locale.ROOT));
	}
}
