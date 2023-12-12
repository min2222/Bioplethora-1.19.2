package io.github.bioplethora.api;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.biome.AmbientMoodSettings;
import net.minecraft.world.level.biome.AmbientParticleSettings;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;

public class BPBiomeSettings {

    // End
    public static Biome baseEndBiome(BiomeGenerationSettings.Builder pGenerationSettingsBuilder) {
    	MobSpawnSettings.Builder mobspawninfo$builder = new MobSpawnSettings.Builder();
        //DefaultBiomeFeatures.endSpawns(mobspawninfo$builder);
        return (new Biome.BiomeBuilder())
                .precipitation(Biome.Precipitation.NONE)
                .temperature(0.5F).downfall(0.5F)
                .specialEffects(
                        (new BiomeSpecialEffects.Builder())
                        .waterColor(4159204)
                        .waterFogColor(329011)
                        .fogColor(10518688)
                        .skyColor(0)
                        .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS).build()
                )
                .mobSpawnSettings(mobspawninfo$builder.build())
                .generationSettings(pGenerationSettingsBuilder.build()).build();
    }

    // Caeri
    public static Biome caeriEndBiome(BiomeGenerationSettings.Builder pGenerationSettingsBuilder) {
    	MobSpawnSettings.Builder mobspawninfo$builder = new MobSpawnSettings.Builder();
        //DefaultBiomeFeatures.endSpawns(mobspawninfo$builder);

        return (new Biome.BiomeBuilder())
                .precipitation(Biome.Precipitation.NONE)
                .temperature(0.5F).downfall(0.5F)
                .specialEffects(
                        (new BiomeSpecialEffects.Builder())
                                .waterColor(-14271360)
                                .waterFogColor(-13348438)
                                .fogColor(-14791063)
                                .skyColor(-14791063)
                                .ambientParticle(new AmbientParticleSettings(ParticleTypes.WHITE_ASH, 0.005F))
                                .ambientLoopSound(SoundEvents.AMBIENT_WARPED_FOREST_LOOP)
                                .ambientMoodSound(new AmbientMoodSettings(SoundEvents.AMBIENT_WARPED_FOREST_MOOD, 6000, 8, 2.0D))
                                .build()
                )
                .mobSpawnSettings(mobspawninfo$builder.build())
                .generationSettings(pGenerationSettingsBuilder.build()).build();
    }

    // Winterfest
    public static Biome winterfestBiome(BiomeGenerationSettings.Builder pGenerationSettingsBuilder) {
    	MobSpawnSettings.Builder mobspawninfo$builder = new MobSpawnSettings.Builder();
        //DefaultBiomeFeatures.endSpawns(mobspawninfo$builder);
        return (new Biome.BiomeBuilder())
                .precipitation(Biome.Precipitation.NONE)
                .temperature(0.5F).downfall(0.5F)
                .specialEffects(
                        (new BiomeSpecialEffects.Builder())
                                .waterColor(-14271360)
                                .waterFogColor(-13348438)
                                .fogColor(-14791063)
                                .skyColor(-14791063)
                                .ambientParticle(new AmbientParticleSettings(ParticleTypes.WHITE_ASH, 0.25F))
                                .ambientLoopSound(SoundEvents.AMBIENT_BASALT_DELTAS_LOOP)
                                .ambientMoodSound(new AmbientMoodSettings(SoundEvents.AMBIENT_BASALT_DELTAS_MOOD, 6000, 8, 2.0D))
                                .build()
                )
                .mobSpawnSettings(mobspawninfo$builder.build())
                .generationSettings(pGenerationSettingsBuilder.build()).build();
    }
}
