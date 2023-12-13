package io.github.bioplethora.world.biomes.overworld;

import java.util.function.Supplier;

import io.github.bioplethora.registry.BPEntities;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.placement.MiscOverworldPlacements;
import net.minecraft.sounds.Musics;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.gen.feature.structure.StructureFeatures;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.level.biome.AmbientAdditionsSettings;
import net.minecraft.world.level.biome.AmbientMoodSettings;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;

public class RockyWoodlandBiome {

    public static Biome make() {
    	MobSpawnSettings.Builder spawnInfo = new MobSpawnSettings.Builder();
        BiomeDefaultFeatures.farmAnimals(spawnInfo);
        BiomeDefaultFeatures.commonSpawns(spawnInfo);

        spawnInfo.addSpawn(MobCategory.CREATURE,
                new MobSpawnSettings.SpawnerData(BPEntities.ALPHEM.get(), 50, 2, 6));

        BiomeGenerationSettings.Builder biomeGenSettings = (new BiomeGenerationSettings.Builder());//.surfaceBuilder(surfaceBuilder);

        biomeGenSettings.addStructureStart(StructureFeatures.VILLAGE_SNOWY);
        biomeGenSettings.addStructureStart(StructureFeatures.MINESHAFT);
        biomeGenSettings.addStructureStart(StructureFeatures.RUINED_PORTAL_SWAMP);
        biomeGenSettings.addStructureStart(StructureFeatures.BURIED_TREASURE);

        BiomeDefaultFeatures.addTaigaTrees(biomeGenSettings);
        BiomeDefaultFeatures.addTaigaTrees(biomeGenSettings);

        BiomeDefaultFeatures.addSurfaceFreezing(biomeGenSettings);

        BiomeDefaultFeatures.addDefaultCarversAndLakes(biomeGenSettings);
        BiomeDefaultFeatures.addTaigaGrass(biomeGenSettings);
        BiomeDefaultFeatures.addFerns(biomeGenSettings);
        BiomeDefaultFeatures.addForestFlowers(biomeGenSettings);
        BiomeDefaultFeatures.addDefaultMonsterRoom(biomeGenSettings);
        BiomeDefaultFeatures.addDefaultUndergroundVariety(biomeGenSettings);
        BiomeDefaultFeatures.addDefaultOres(biomeGenSettings);
        BiomeDefaultFeatures.addSwampClayDisk(biomeGenSettings);
        BiomeDefaultFeatures.addDefaultMushrooms(biomeGenSettings);
        BiomeDefaultFeatures.addDesertExtraVegetation(biomeGenSettings);
        BiomeDefaultFeatures.addDefaultSprings(biomeGenSettings);
        BiomeDefaultFeatures.addBambooVegetation(biomeGenSettings);
        BiomeDefaultFeatures.addMossyStoneBlock(biomeGenSettings);

        biomeGenSettings.addFeature(Decoration.LAKES, MiscOverworldPlacements.LAKE_LAVA_SURFACE);

        return (new Biome.BiomeBuilder()).precipitation(Biome.Precipitation.SNOW)//.biomeCategory(Biome.Category.ICY).depth(depth).scale(scale)
                .temperature(-0.5F).downfall(0.5F).specialEffects((new BiomeSpecialEffects.Builder())
                        .fogColor(12638463)
                        .waterColor(4159204)
                        .waterFogColor(329011)
                        .skyColor(7972607)
                        .foliageColorOverride(-13266085)
                        .grassColorOverride(-9923462)
                        .ambientLoopSound(SoundEvents.AMBIENT_CRIMSON_FOREST_LOOP)
                        .ambientMoodSound(new AmbientMoodSettings(SoundEvents.AMBIENT_WARPED_FOREST_MOOD, 6000, 8, 2.0D))
                        .ambientAdditionsSound(new AmbientAdditionsSettings(SoundEvents.AMBIENT_NETHER_WASTES_ADDITIONS, 0.0111D))
                        .backgroundMusic(Musics.createGameMusic(SoundEvents.MUSIC_BIOME_CRIMSON_FOREST))
                        .build())
                .mobSpawnSettings(spawnInfo.build()).generationSettings(biomeGenSettings.build()).build();
    }
}
