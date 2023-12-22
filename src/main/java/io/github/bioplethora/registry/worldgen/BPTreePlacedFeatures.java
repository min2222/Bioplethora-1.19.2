package io.github.bioplethora.registry.worldgen;

import java.util.List;
import java.util.Locale;

import com.google.common.collect.ImmutableList;

import io.github.bioplethora.Bioplethora;
import net.minecraft.core.Holder;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.WeightedListInt;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.CountOnEveryLayerPlacement;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;

@SuppressWarnings("deprecation")
public class BPTreePlacedFeatures {
	
	public static final Holder<PlacedFeature> ENIVILE_TREE = register("enivile_tree", BPTreeConfiguredFeatures.ENIVILE_TREE_CONFIG, ImmutableList.<PlacementModifier>builder().add(CountOnEveryLayerPlacement.of(2), BiomeFilter.biome()).build());
	public static final Holder<PlacedFeature> CAERULWOOD_TREE = register("caerulwood_tree", BPTreeConfiguredFeatures.CAERULWOOD_TREE_CONFIG, 
			ImmutableList.<PlacementModifier>builder().add(InSquarePlacement.spread(), BiomeFilter.biome(), 
					CountPlacement.of(new WeightedListInt(SimpleWeightedRandomList.<IntProvider>builder().add(ConstantInt.of(6), 85).add(ConstantInt.of(7), 15).build()))).build());
	
	public static Holder<PlacedFeature> register(String name, Holder<? extends ConfiguredFeature<?, ?>> feature, List<PlacementModifier> placements) {
		return BuiltinRegistries.registerExact(BuiltinRegistries.PLACED_FEATURE, prefix(name).toString(), new PlacedFeature(Holder.hackyErase(feature), List.copyOf(placements)));
	}
	
	public static ResourceLocation prefix(String name) {
		return new ResourceLocation(Bioplethora.MOD_ID, name.toLowerCase(Locale.ROOT));
	}
}
