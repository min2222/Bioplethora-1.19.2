package io.github.bioplethora.world.biomes.end;

import io.github.bioplethora.api.BPBiomeSettings;
import io.github.bioplethora.config.BPConfig;
import io.github.bioplethora.registry.worldgen.BPPlacedFeatures;
import net.minecraft.data.worldgen.placement.EndPlacements;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;

public class CaeriForestBiome {

    public static Biome make() {

        BiomeGenerationSettings.Builder biomeGenSettings = (new BiomeGenerationSettings.Builder());//.surfaceBuilder(surfaceBuilder);

        //biomeGenSettings.addStructureStart(StructureFeatures.END_CITY);
        biomeGenSettings.addFeature(Decoration.SURFACE_STRUCTURES, EndPlacements.END_GATEWAY_RETURN);
        if (BPConfig.WORLDGEN.endIcicleIslands.get()) biomeGenSettings.addFeature(Decoration.VEGETAL_DECORATION, BPPlacedFeatures.END_ISLANDS_ICICLE_PATCH.getHolder().get());
        if (BPConfig.WORLDGEN.endFrozenIslands.get()) biomeGenSettings.addFeature(Decoration.VEGETAL_DECORATION, BPPlacedFeatures.END_FROZEN_ISLAND_DECORATED.getHolder().get());
        biomeGenSettings.addFeature(Decoration.VEGETAL_DECORATION, BPPlacedFeatures.FROSTEM.getHolder().get());
        biomeGenSettings.addFeature(Decoration.VEGETAL_DECORATION, BPPlacedFeatures.SPINXELTHORN.getHolder().get());
        biomeGenSettings.addFeature(Decoration.VEGETAL_DECORATION, BPPlacedFeatures.GLACYNTH.getHolder().get());

        return BPBiomeSettings.caeriEndBiome(biomeGenSettings);
    }
}
