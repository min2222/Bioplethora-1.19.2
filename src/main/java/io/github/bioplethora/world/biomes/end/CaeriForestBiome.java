package io.github.bioplethora.world.biomes.end;

import io.github.bioplethora.api.BPBiomeSettings;
import io.github.bioplethora.registry.worldgen.BPConfiguredWorldCarvers;
import io.github.bioplethora.registry.worldgen.BPPlacedFeatures;
import io.github.bioplethora.registry.worldgen.BPTreePlacedFeatures;
import net.minecraft.data.worldgen.placement.EndPlacements;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.levelgen.GenerationStep.Carving;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;

public class CaeriForestBiome {

    public static Biome make() {

        BiomeGenerationSettings.Builder biomeGenSettings = (new BiomeGenerationSettings.Builder());
        
        biomeGenSettings.addFeature(Decoration.SURFACE_STRUCTURES, EndPlacements.END_GATEWAY_RETURN);
        biomeGenSettings.addCarver(Carving.AIR, BPConfiguredWorldCarvers.CAERI_FORMERS.getHolder().get());
        biomeGenSettings.addFeature(Decoration.UNDERGROUND_DECORATION, BPPlacedFeatures.CAERI_CAVERN.getHolder().get());

        biomeGenSettings.addFeature(Decoration.VEGETAL_DECORATION, BPTreePlacedFeatures.CAERI_FOREST_TREES);

        //TODO
        //biomeGenSettings.addFeature(Decoration.VEGETAL_DECORATION, BPPlacedFeatures.IRION_GRASS);
        biomeGenSettings.addFeature(Decoration.VEGETAL_DECORATION, BPPlacedFeatures.IRION_TALL_GRASS.getHolder().get());
        biomeGenSettings.addFeature(Decoration.VEGETAL_DECORATION, BPPlacedFeatures.ARTAIRIUS.getHolder().get());
        biomeGenSettings.addFeature(Decoration.VEGETAL_DECORATION, BPPlacedFeatures.BYRSS_LANTERN_FOREST_PATCH.getHolder().get());

        return BPBiomeSettings.caeriEndBiome(biomeGenSettings);
    }
}
