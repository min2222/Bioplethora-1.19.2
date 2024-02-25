package io.github.bioplethora.world.biomes.end.configurable;

import io.github.bioplethora.config.BPConfig;
import io.github.bioplethora.registry.BPParticles;
import io.github.bioplethora.registry.worldgen.BPPlacedFeatures;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.placement.EndPlacements;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.biome.AmbientMoodSettings;
import net.minecraft.world.level.biome.AmbientParticleSettings;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;

public class LavenderPondsBiome {

    public static Biome make() {

        BiomeGenerationSettings.Builder biomeGenSettings = (new BiomeGenerationSettings.Builder());
        MobSpawnSettings.Builder mobspawninfo$builder = new MobSpawnSettings.Builder();
        BiomeDefaultFeatures.endSpawns(mobspawninfo$builder);

        biomeGenSettings.addFeature(Decoration.SURFACE_STRUCTURES, EndPlacements.END_GATEWAY_RETURN);
        biomeGenSettings.addFeature(Decoration.VEGETAL_DECORATION, EndPlacements.CHORUS_PLANT);
        
        biomeGenSettings.addFeature(Decoration.VEGETAL_DECORATION, BPPlacedFeatures.CHORUS_MYCHRODEGIA.getHolder().get());

        if (BPConfig.WORLDGEN.chorusLanternMidlands.get()) biomeGenSettings.addFeature(Decoration.VEGETAL_DECORATION, BPPlacedFeatures.CHORUS_LANTERN_MIDLANDS_PATCH.getHolder().get());
        if (BPConfig.WORLDGEN.endSpikeMidlands.get()) biomeGenSettings.addFeature(Decoration.VEGETAL_DECORATION, BPPlacedFeatures.END_LAND_SPIKE_PATCH_ML.getHolder().get());
        if (BPConfig.WORLDGEN.chorusVegetationMidlands.get()) biomeGenSettings.addFeature(Decoration.VEGETAL_DECORATION, BPPlacedFeatures.CHORUS_IDON.getHolder().get());
        if (BPConfig.WORLDGEN.chorusVegetationMidlands.get()) biomeGenSettings.addFeature(Decoration.VEGETAL_DECORATION, BPPlacedFeatures.CHORUS_IDE_FAN.getHolder().get());
        if (BPConfig.WORLDGEN.chorusVegetationMidlands.get()) biomeGenSettings.addFeature(Decoration.VEGETAL_DECORATION, BPPlacedFeatures.ENREDE_KELP.getHolder().get());
        if (BPConfig.WORLDGEN.chorusVegetationMidlands.get()) biomeGenSettings.addFeature(Decoration.VEGETAL_DECORATION, BPPlacedFeatures.ENREDE_CORSASCILE.getHolder().get());
        if (BPConfig.WORLDGEN.chorusVegetationMidlands.get()) biomeGenSettings.addFeature(Decoration.VEGETAL_DECORATION, BPPlacedFeatures.OCHAIM_PURPLE.getHolder().get());
        if (BPConfig.WORLDGEN.chorusVegetationMidlands.get()) biomeGenSettings.addFeature(Decoration.VEGETAL_DECORATION, BPPlacedFeatures.OCHAIM_RED.getHolder().get());
        if (BPConfig.WORLDGEN.chorusVegetationMidlands.get()) biomeGenSettings.addFeature(Decoration.VEGETAL_DECORATION, BPPlacedFeatures.OCHAIM_GREEN.getHolder().get());

        if (BPConfig.WORLDGEN.endSpongeMidlands.get()) biomeGenSettings.addFeature(Decoration.LOCAL_MODIFICATIONS, BPPlacedFeatures.END_LAND_SPONGE_PATCH_ML.getHolder().get());

        return(new Biome.BiomeBuilder())
                .precipitation(Biome.Precipitation.NONE)
                .temperature(0.5F).downfall(0.5F)
                .specialEffects(new BiomeSpecialEffects.Builder()
                                .waterColor(-6599759)
                                .waterFogColor(-13158998)
                                .fogColor(-12378263)
                                .skyColor(-12378263)
                                .ambientParticle(new AmbientParticleSettings(BPParticles.NIGHT_GAZE.get(), 0.04F))
                                .ambientLoopSound(SoundEvents.AMBIENT_SOUL_SAND_VALLEY_MOOD)
                                .ambientMoodSound(new AmbientMoodSettings(SoundEvents.AMBIENT_NETHER_WASTES_MOOD, 6000, 8, 2.0D))
                                .build())
                .mobSpawnSettings(mobspawninfo$builder.build())
                .generationSettings(biomeGenSettings.build()).build();
    }
}
