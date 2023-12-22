package io.github.bioplethora.world.biomes.end;

import io.github.bioplethora.api.BPBiomeSettings;
import net.minecraft.data.worldgen.placement.EndPlacements;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;

public class CaeriPlainsBiome {

    public static Biome make() {

        BiomeGenerationSettings.Builder biomeGenSettings = (new BiomeGenerationSettings.Builder());//.surfaceBuilder(surfaceBuilder);

        //biomeGenSettings.addStructureStart(StructureFeatures.END_CITY);
        
        //need to use BuiltinRegistries.getHolderOrThrow, also resource key
        /*biomeGenSettings.addFeature(Decoration.UNDERGROUND_DECORATION, BPPlacedFeatures.CAERI_CAVERN);
        biomeGenSettings.addFeature(Decoration.VEGETAL_DECORATION, BPPlacedFeatures.IRION_GRASS);
        biomeGenSettings.addFeature(Decoration.VEGETAL_DECORATION, BPPlacedFeatures.IRION_TALL_GRASS);
        biomeGenSettings.addFeature(Decoration.VEGETAL_DECORATION, BPPlacedFeatures.ARTAIRIUS);
        biomeGenSettings.addFeature(Decoration.VEGETAL_DECORATION, BPPlacedFeatures.BYRSS_LANTERN_PLANT_PATCH);*/
        	
        biomeGenSettings.addFeature(Decoration.SURFACE_STRUCTURES, EndPlacements.END_GATEWAY_RETURN);

        return BPBiomeSettings.caeriEndBiome(biomeGenSettings);
    }
}
