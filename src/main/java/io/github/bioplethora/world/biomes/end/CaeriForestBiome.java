package io.github.bioplethora.world.biomes.end;

import java.util.function.Supplier;

import io.github.bioplethora.api.BPBiomeSettings;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.structure.StructureFeatures;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import software.bernie.shadowed.fasterxml.jackson.annotation.JsonFormat.Features;

public class CaeriForestBiome {

    public static Biome make(final Supplier<ConfiguredSurfaceBuilder<?>> surfaceBuilder) {

        BiomeGenerationSettings.Builder biomeGenSettings = (new BiomeGenerationSettings.Builder()).surfaceBuilder(surfaceBuilder);

        biomeGenSettings.addStructureStart(StructureFeatures.END_CITY);
        biomeGenSettings.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, Features.END_GATEWAY);

        return BPBiomeSettings.caeriEndBiome(biomeGenSettings);
    }
}
