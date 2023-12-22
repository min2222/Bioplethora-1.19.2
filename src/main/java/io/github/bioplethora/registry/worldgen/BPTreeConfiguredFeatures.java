package io.github.bioplethora.registry.worldgen;

import java.util.Locale;

import com.google.common.collect.ImmutableList;

import io.github.bioplethora.Bioplethora;
import net.minecraft.core.Holder;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import software.bernie.shadowed.fasterxml.jackson.annotation.JsonFormat.Features;

public class BPTreeConfiguredFeatures {

    public static final Holder<ConfiguredFeature<NoneFeatureConfiguration, ?>> ENIVILE_TREE_CONFIG = register("enivile_tree", BPFeatures.ENIVILE_TREE.get(), new NoneFeatureConfiguration());
    
    public static final ConfiguredFeature<?, ?> CRYEANUM_FOREST_TREES = Feature.RANDOM_SELECTOR
            .configured(new MultipleRandomFeatureConfig(ImmutableList.of(ENIVILE_TREE_CONFIG.weighted(0.31333334F)), ENIVILE_TREE_CONFIG))
            .decorated(Placement.COUNT_MULTILAYER.configured(new FeatureSpreadConfig(2))
            );

    public static final ConfiguredFeature<?, ?> CAERULWOOD_TREE_CONFIG = BPFeatures.CAERULWOOD_TREE.get()
            .configured(new NoneFeatureConfiguration())
            .decorated(Placement.NOPE.configured(IPlacementConfig.NONE)
            );
    public static final ConfiguredFeature<?, ?> CAERI_FOREST_TREES = Feature.RANDOM_SELECTOR
            .configured(new MultipleRandomFeatureConfig(ImmutableList.of(CAERULWOOD_TREE_CONFIG.weighted(0.6666667F)), CAERULWOOD_TREE_CONFIG))
            .decorated(Features.Placements.HEIGHTMAP_SQUARE)
            .decorated(Placement.COUNT_EXTRA.configured(new AtSurfaceWithExtraConfig(6, 0.15F, 1))
            );
    
	public static <FC extends FeatureConfiguration, F extends Feature<FC>> Holder<ConfiguredFeature<FC, ?>> register(String name, F feature, FC featureConfiguration) {
		return BuiltinRegistries.registerExact(BuiltinRegistries.CONFIGURED_FEATURE, prefix(name).toString(), new ConfiguredFeature<>(feature, featureConfiguration));
	}
	
	public static ResourceLocation prefix(String name) {
		return new ResourceLocation(Bioplethora.MOD_ID, name.toLowerCase(Locale.ROOT));
	}
}
