package io.github.bioplethora.world.biomes.nether;

import io.github.bioplethora.config.BPConfig;
import io.github.bioplethora.registry.BPEntities;
import io.github.bioplethora.registry.BPParticles;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.Carvers;
import net.minecraft.data.worldgen.placement.MiscOverworldPlacements;
import net.minecraft.data.worldgen.placement.NetherPlacements;
import net.minecraft.data.worldgen.placement.OrePlacements;
import net.minecraft.sounds.Musics;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.AmbientAdditionsSettings;
import net.minecraft.world.level.biome.AmbientMoodSettings;
import net.minecraft.world.level.biome.AmbientParticleSettings;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.Climate.TargetPoint;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep.Carving;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;

public class CryeanumPlains {
    public static final TargetPoint ATTRIBUTE = Climate.target(-0.35F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);

    public static Biome make() {
        double d0 = 0.7D;
        double d1 = 0.15D;
        MobSpawnSettings.Builder spawnInfoBuilder = new MobSpawnSettings.Builder();

        if (BPConfig.COMMON.spawnMyuthine.get()) {
            spawnInfoBuilder.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(BPEntities.MYUTHINE.get(), 15, 5, 5));
        }

        spawnInfoBuilder.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.GHAST, 50, 4, 4));
        spawnInfoBuilder.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.ENDERMAN, 1, 4, 4));
        spawnInfoBuilder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.STRIDER, 60, 1, 2));

        if (BPConfig.COMMON.spawnMyuthine.get()) {
            spawnInfoBuilder.addMobCharge(BPEntities.MYUTHINE.get(), 0.7D, 0.15D);
        }

        spawnInfoBuilder.addMobCharge(EntityType.GHAST, 0.7D, 0.15D);
        spawnInfoBuilder.addMobCharge(EntityType.ENDERMAN, 0.7D, 0.15D);
        spawnInfoBuilder.addMobCharge(EntityType.STRIDER, 0.7D, 0.15D);

        MobSpawnSettings mobspawninfo = spawnInfoBuilder.build();
        BiomeGenerationSettings.Builder biomegenerationsettings$builder = new BiomeGenerationSettings.Builder();

        //biomegenerationsettings$builder.surfaceBuilder(surfaceBuilder);

        //Handled in tag definition
        /*biomegenerationsettings$builder.addStructureStart(StructureFeatures.NETHER_BRIDGE);
        biomegenerationsettings$builder.addStructureStart(StructureFeatures.NETHER_FOSSIL);
        biomegenerationsettings$builder.addStructureStart(StructureFeatures.RUINED_PORTAL_NETHER);
        biomegenerationsettings$builder.addStructureStart(StructureFeatures.BASTION_REMNANT);*/

        biomegenerationsettings$builder.addCarver(Carving.AIR, Carvers.NETHER_CAVE);

        biomegenerationsettings$builder.addFeature(Decoration.VEGETAL_DECORATION, MiscOverworldPlacements.SPRING_LAVA);
        biomegenerationsettings$builder.addFeature(Decoration.LOCAL_MODIFICATIONS, NetherPlacements.BASALT_PILLAR);
        biomegenerationsettings$builder.addFeature(Decoration.UNDERGROUND_DECORATION, NetherPlacements.SPRING_OPEN);
        biomegenerationsettings$builder.addFeature(Decoration.UNDERGROUND_DECORATION, NetherPlacements.GLOWSTONE_EXTRA);
        biomegenerationsettings$builder.addFeature(Decoration.UNDERGROUND_DECORATION, NetherPlacements.GLOWSTONE);
        biomegenerationsettings$builder.addFeature(Decoration.UNDERGROUND_DECORATION, NetherPlacements.PATCH_CRIMSON_ROOTS);
        biomegenerationsettings$builder.addFeature(Decoration.UNDERGROUND_DECORATION, NetherPlacements.PATCH_FIRE);
        biomegenerationsettings$builder.addFeature(Decoration.UNDERGROUND_DECORATION, NetherPlacements.PATCH_SOUL_FIRE);
        biomegenerationsettings$builder.addFeature(Decoration.UNDERGROUND_DECORATION, OrePlacements.ORE_MAGMA);
        biomegenerationsettings$builder.addFeature(Decoration.UNDERGROUND_DECORATION, NetherPlacements.SPRING_CLOSED);
        biomegenerationsettings$builder.addFeature(Decoration.UNDERGROUND_DECORATION, OrePlacements.ORE_SOUL_SAND);

        BiomeDefaultFeatures.addNetherDefaultOres(biomegenerationsettings$builder);
        Biome.BiomeBuilder builder1 = new Biome.BiomeBuilder();
        builder1.precipitation(Biome.Precipitation.NONE);
        /*builder1.biomeCategory(Biome.Category.NETHER);
        builder1.depth(0.1F);
        builder1.scale(0.2F);*/
        builder1.temperature(1.0F);
        builder1.downfall(0.0F);

        builder1.specialEffects((new BiomeSpecialEffects.Builder())
                .waterColor(4159204)
                .waterFogColor(329011)
                .fogColor(1787717)
                .skyColor(calculateSkyColor(1.0F))
                .ambientParticle(new AmbientParticleSettings(BPParticles.PINK_ENIVILE_LEAF.get(), 0.00225F))
                .ambientLoopSound(SoundEvents.AMBIENT_WARPED_FOREST_LOOP)
                .ambientMoodSound(new AmbientMoodSettings(SoundEvents.AMBIENT_NETHER_WASTES_MOOD, 6000, 8, 2.0D))
                .ambientAdditionsSound(new AmbientAdditionsSettings(SoundEvents.AMBIENT_CRIMSON_FOREST_ADDITIONS, 0.0111D))
                .backgroundMusic(Musics.createGameMusic(SoundEvents.MUSIC_BIOME_SOUL_SAND_VALLEY))
                .build());

        builder1.mobSpawnSettings(mobspawninfo);
        builder1.generationSettings(biomegenerationsettings$builder.build());
        return builder1.build();
    }


    private static int calculateSkyColor(float pTemperature) {
        float lvt_1_1_ = pTemperature / 3.0F;
        lvt_1_1_ = Mth.clamp(lvt_1_1_, -1.0F, 1.0F);
        return Mth.hsvToRgb(0.62222224F - lvt_1_1_ * 0.05F, 0.5F + lvt_1_1_ * 0.1F, 1.0F);
    }
}
