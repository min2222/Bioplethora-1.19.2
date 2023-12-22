package io.github.bioplethora.registry.worldgen;

import java.util.List;
import java.util.Locale;

import io.github.bioplethora.Bioplethora;
import net.minecraft.core.Holder;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomFeatureConfiguration;

public class BPTreeConfiguredFeatures {

    public static final Holder<ConfiguredFeature<NoneFeatureConfiguration, ?>> ENIVILE_TREE_CONFIG = register("enivile_tree", BPFeatures.ENIVILE_TREE.get(), new NoneFeatureConfiguration());
    
    public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> CRYEANUM_FOREST_TREES = register("cryeanum_forest_trees", Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(BPTreePlacedFeatures.ENIVILE_TREE, 0.31333334F)), BPTreePlacedFeatures.ENIVILE_TREE));
    
    public static final Holder<ConfiguredFeature<NoneFeatureConfiguration, ?>> CAERULWOOD_TREE_CONFIG = register("caerulwood_tree", BPFeatures.CAERULWOOD_TREE.get(), new NoneFeatureConfiguration());
    
    public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> CAERI_FOREST_TREES = register("caeri_forest_trees", Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(BPTreePlacedFeatures.CAERULWOOD_TREE, 0.6666667F)), BPTreePlacedFeatures.CAERULWOOD_TREE));
    
	public static <FC extends FeatureConfiguration, F extends Feature<FC>> Holder<ConfiguredFeature<FC, ?>> register(String name, F feature, FC featureConfiguration) {
		return BuiltinRegistries.registerExact(BuiltinRegistries.CONFIGURED_FEATURE, prefix(name).toString(), new ConfiguredFeature<>(feature, featureConfiguration));
	}
	
	public static ResourceLocation prefix(String name) {
		return new ResourceLocation(Bioplethora.MOD_ID, name.toLowerCase(Locale.ROOT));
	}
}
