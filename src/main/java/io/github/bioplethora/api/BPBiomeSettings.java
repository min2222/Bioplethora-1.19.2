package io.github.bioplethora.api;

import io.github.bioplethora.config.BPConfig;
import io.github.bioplethora.registry.BPEntities;
import io.github.bioplethora.registry.BPParticles;
import io.github.bioplethora.registry.worldgen.BPConfiguredWorldCarvers;
import io.github.bioplethora.registry.worldgen.BPPlacedFeatures;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.Carvers;
import net.minecraft.data.worldgen.placement.EndPlacements;
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
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class BPBiomeSettings {

    public static final TargetPoint ATTRIBUTE = Climate.target(-0.35F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
    
    public static Biome rockyWoodlandsBiome(HolderGetter<PlacedFeature> pPlacedFeatures, HolderGetter<ConfiguredWorldCarver<?>> pWorldCarvers) {
    	MobSpawnSettings.Builder spawnInfo = new MobSpawnSettings.Builder();
        BiomeDefaultFeatures.farmAnimals(spawnInfo);
        BiomeDefaultFeatures.commonSpawns(spawnInfo);

        spawnInfo.addSpawn(MobCategory.CREATURE,
                new MobSpawnSettings.SpawnerData(BPEntities.ALPHEM.get(), 50, 2, 6));

        BiomeGenerationSettings.Builder biomeGenSettings = new BiomeGenerationSettings.Builder(pPlacedFeatures, pWorldCarvers);

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

        return (new Biome.BiomeBuilder())
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
    

    public static Biome cryeanumPlainsBiome(HolderGetter<PlacedFeature> pPlacedFeatures, HolderGetter<ConfiguredWorldCarver<?>> pWorldCarvers) {
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
        BiomeGenerationSettings.Builder biomegenerationsettings$builder = new BiomeGenerationSettings.Builder(pPlacedFeatures, pWorldCarvers);

        biomegenerationsettings$builder.addCarver(Carving.AIR, Carvers.NETHER_CAVE);

        biomegenerationsettings$builder.addFeature(Decoration.VEGETAL_DECORATION, BPPlacedFeatures.KYRIA);
        biomegenerationsettings$builder.addFeature(Decoration.VEGETAL_DECORATION, BPPlacedFeatures.KYRIA_BELINE);
        biomegenerationsettings$builder.addFeature(Decoration.VEGETAL_DECORATION, BPPlacedFeatures.KYRIA_IDE_FAN);
        biomegenerationsettings$builder.addFeature(Decoration.VEGETAL_DECORATION, BPPlacedFeatures.RED_TWI);
        biomegenerationsettings$builder.addFeature(Decoration.VEGETAL_DECORATION, BPPlacedFeatures.PINK_TWI);
        biomegenerationsettings$builder.addFeature(Decoration.VEGETAL_DECORATION, BPPlacedFeatures.CRYEANUM_FOREST_TREES);

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
        builder1.hasPrecipitation(false);
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

    // Caeri
    public static Biome caeriPlainsBiome(HolderGetter<PlacedFeature> pPlacedFeatures, HolderGetter<ConfiguredWorldCarver<?>> pWorldCarvers) {
        BiomeGenerationSettings.Builder biomeGenSettings = new BiomeGenerationSettings.Builder(pPlacedFeatures, pWorldCarvers);

        biomeGenSettings.addFeature(Decoration.UNDERGROUND_DECORATION, BPPlacedFeatures.CAERI_CAVERN);

        biomeGenSettings.addFeature(Decoration.VEGETAL_DECORATION, BPPlacedFeatures.IRION_GRASS);
        biomeGenSettings.addFeature(Decoration.VEGETAL_DECORATION, BPPlacedFeatures.IRION_TALL_GRASS);
        biomeGenSettings.addFeature(Decoration.VEGETAL_DECORATION, BPPlacedFeatures.ARTAIRIUS);
        biomeGenSettings.addFeature(Decoration.VEGETAL_DECORATION, BPPlacedFeatures.BYRSS_LANTERN_PLANT_PATCH);
        	
        biomeGenSettings.addFeature(Decoration.SURFACE_STRUCTURES, EndPlacements.END_GATEWAY_RETURN);
        
    	MobSpawnSettings.Builder mobspawninfo$builder = new MobSpawnSettings.Builder();
    	BiomeDefaultFeatures.endSpawns(mobspawninfo$builder);
    	
        return (new Biome.BiomeBuilder())
                .hasPrecipitation(false)
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
                .generationSettings(biomeGenSettings.build()).build();
    }
    
    public static Biome caeriForestBiome(HolderGetter<PlacedFeature> pPlacedFeatures, HolderGetter<ConfiguredWorldCarver<?>> pWorldCarvers) {
        BiomeGenerationSettings.Builder biomeGenSettings = new BiomeGenerationSettings.Builder(pPlacedFeatures, pWorldCarvers);
        
        biomeGenSettings.addFeature(Decoration.SURFACE_STRUCTURES, EndPlacements.END_GATEWAY_RETURN);
        biomeGenSettings.addCarver(Carving.AIR, BPConfiguredWorldCarvers.CAERI_FORMERS);
        biomeGenSettings.addFeature(Decoration.UNDERGROUND_DECORATION, BPPlacedFeatures.CAERI_CAVERN);

        biomeGenSettings.addFeature(Decoration.VEGETAL_DECORATION, BPPlacedFeatures.CAERI_FOREST_TREES);

        biomeGenSettings.addFeature(Decoration.VEGETAL_DECORATION, BPPlacedFeatures.IRION_GRASS);
        biomeGenSettings.addFeature(Decoration.VEGETAL_DECORATION, BPPlacedFeatures.IRION_TALL_GRASS);
        biomeGenSettings.addFeature(Decoration.VEGETAL_DECORATION, BPPlacedFeatures.ARTAIRIUS);
        biomeGenSettings.addFeature(Decoration.VEGETAL_DECORATION, BPPlacedFeatures.BYRSS_LANTERN_FOREST_PATCH);

    	MobSpawnSettings.Builder mobspawninfo$builder = new MobSpawnSettings.Builder();
    	BiomeDefaultFeatures.endSpawns(mobspawninfo$builder);
    	
        return (new Biome.BiomeBuilder())
                .hasPrecipitation(false)
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
                .generationSettings(biomeGenSettings.build()).build();
    }

    // Winterfest
    public static Biome winterfestBiome(HolderGetter<PlacedFeature> pPlacedFeatures, HolderGetter<ConfiguredWorldCarver<?>> pWorldCarvers) {
        BiomeGenerationSettings.Builder biomeGenSettings = new BiomeGenerationSettings.Builder(pPlacedFeatures, pWorldCarvers);
        
        biomeGenSettings.addFeature(Decoration.SURFACE_STRUCTURES, EndPlacements.END_GATEWAY_RETURN);
        if (BPConfig.WORLDGEN.endIcicleIslands.get()) biomeGenSettings.addFeature(Decoration.VEGETAL_DECORATION, BPPlacedFeatures.END_ISLANDS_ICICLE_PATCH);
        if (BPConfig.WORLDGEN.endFrozenIslands.get()) biomeGenSettings.addFeature(Decoration.VEGETAL_DECORATION, BPPlacedFeatures.END_FROZEN_ISLAND_DECORATED);
        biomeGenSettings.addFeature(Decoration.VEGETAL_DECORATION, BPPlacedFeatures.FROSTEM);
        biomeGenSettings.addFeature(Decoration.VEGETAL_DECORATION, BPPlacedFeatures.SPINXELTHORN);
        biomeGenSettings.addFeature(Decoration.VEGETAL_DECORATION, BPPlacedFeatures.GLACYNTH);
        
    	MobSpawnSettings.Builder mobspawninfo$builder = new MobSpawnSettings.Builder();
    	BiomeDefaultFeatures.endSpawns(mobspawninfo$builder);
        return (new Biome.BiomeBuilder())
                .hasPrecipitation(false)
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
                .generationSettings(biomeGenSettings.build()).build();
    }
    
    public static Biome lavenderLakesBiome(HolderGetter<PlacedFeature> pPlacedFeatures, HolderGetter<ConfiguredWorldCarver<?>> pWorldCarvers) {
        BiomeGenerationSettings.Builder biomeGenSettings = new BiomeGenerationSettings.Builder(pPlacedFeatures, pWorldCarvers);
        MobSpawnSettings.Builder mobspawninfo$builder = new MobSpawnSettings.Builder();
        BiomeDefaultFeatures.endSpawns(mobspawninfo$builder);

        biomeGenSettings.addFeature(Decoration.SURFACE_STRUCTURES, EndPlacements.END_GATEWAY_RETURN);
        biomeGenSettings.addFeature(Decoration.VEGETAL_DECORATION, EndPlacements.CHORUS_PLANT);
        
        biomeGenSettings.addFeature(Decoration.VEGETAL_DECORATION, BPPlacedFeatures.CHORUS_MYCHRODEGIA);

        if (BPConfig.WORLDGEN.chorusLanternHighlands.get()) biomeGenSettings.addFeature(Decoration.VEGETAL_DECORATION, BPPlacedFeatures.CHORUS_LANTERN_HIGHLANDS_PATCH);
        if (BPConfig.WORLDGEN.endSpikeHighlands.get()) biomeGenSettings.addFeature(Decoration.VEGETAL_DECORATION, BPPlacedFeatures.END_LAND_SPIKE_PATCH_HL);
        if (BPConfig.WORLDGEN.chorusVegetationHighlands.get()) biomeGenSettings.addFeature(Decoration.VEGETAL_DECORATION, BPPlacedFeatures.CHORUS_IDON);
        if (BPConfig.WORLDGEN.chorusVegetationHighlands.get()) biomeGenSettings.addFeature(Decoration.VEGETAL_DECORATION, BPPlacedFeatures.CHORUS_IDE_FAN);
        if (BPConfig.WORLDGEN.chorusVegetationHighlands.get()) biomeGenSettings.addFeature(Decoration.VEGETAL_DECORATION, BPPlacedFeatures.ENREDE_KELP);
        if (BPConfig.WORLDGEN.chorusVegetationHighlands.get()) biomeGenSettings.addFeature(Decoration.VEGETAL_DECORATION, BPPlacedFeatures.ENREDE_CORSASCILE);
        if (BPConfig.WORLDGEN.chorusVegetationHighlands.get()) biomeGenSettings.addFeature(Decoration.VEGETAL_DECORATION, BPPlacedFeatures.OCHAIM_PURPLE);
        if (BPConfig.WORLDGEN.chorusVegetationHighlands.get()) biomeGenSettings.addFeature(Decoration.VEGETAL_DECORATION, BPPlacedFeatures.OCHAIM_RED);
        if (BPConfig.WORLDGEN.chorusVegetationHighlands.get()) biomeGenSettings.addFeature(Decoration.VEGETAL_DECORATION, BPPlacedFeatures.OCHAIM_GREEN);

        if (BPConfig.WORLDGEN.endSpongeHighlands.get()) biomeGenSettings.addFeature(Decoration.LOCAL_MODIFICATIONS, BPPlacedFeatures.END_LAND_SPONGE_PATCH_HL);

        return(new Biome.BiomeBuilder())
                .hasPrecipitation(false)
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
    
    public static Biome lavenderPondsBiome(HolderGetter<PlacedFeature> pPlacedFeatures, HolderGetter<ConfiguredWorldCarver<?>> pWorldCarvers) {

        BiomeGenerationSettings.Builder biomeGenSettings = new BiomeGenerationSettings.Builder(pPlacedFeatures, pWorldCarvers);
        MobSpawnSettings.Builder mobspawninfo$builder = new MobSpawnSettings.Builder();
        BiomeDefaultFeatures.endSpawns(mobspawninfo$builder);

        biomeGenSettings.addFeature(Decoration.SURFACE_STRUCTURES, EndPlacements.END_GATEWAY_RETURN);
        biomeGenSettings.addFeature(Decoration.VEGETAL_DECORATION, EndPlacements.CHORUS_PLANT);
        
        biomeGenSettings.addFeature(Decoration.VEGETAL_DECORATION, BPPlacedFeatures.CHORUS_MYCHRODEGIA);

        if (BPConfig.WORLDGEN.chorusLanternMidlands.get()) biomeGenSettings.addFeature(Decoration.VEGETAL_DECORATION, BPPlacedFeatures.CHORUS_LANTERN_MIDLANDS_PATCH);
        if (BPConfig.WORLDGEN.endSpikeMidlands.get()) biomeGenSettings.addFeature(Decoration.VEGETAL_DECORATION, BPPlacedFeatures.END_LAND_SPIKE_PATCH_ML);
        if (BPConfig.WORLDGEN.chorusVegetationMidlands.get()) biomeGenSettings.addFeature(Decoration.VEGETAL_DECORATION, BPPlacedFeatures.CHORUS_IDON);
        if (BPConfig.WORLDGEN.chorusVegetationMidlands.get()) biomeGenSettings.addFeature(Decoration.VEGETAL_DECORATION, BPPlacedFeatures.CHORUS_IDE_FAN);
        if (BPConfig.WORLDGEN.chorusVegetationMidlands.get()) biomeGenSettings.addFeature(Decoration.VEGETAL_DECORATION, BPPlacedFeatures.ENREDE_KELP);
        if (BPConfig.WORLDGEN.chorusVegetationMidlands.get()) biomeGenSettings.addFeature(Decoration.VEGETAL_DECORATION, BPPlacedFeatures.ENREDE_CORSASCILE);
        if (BPConfig.WORLDGEN.chorusVegetationMidlands.get()) biomeGenSettings.addFeature(Decoration.VEGETAL_DECORATION, BPPlacedFeatures.OCHAIM_PURPLE);
        if (BPConfig.WORLDGEN.chorusVegetationMidlands.get()) biomeGenSettings.addFeature(Decoration.VEGETAL_DECORATION, BPPlacedFeatures.OCHAIM_RED);
        if (BPConfig.WORLDGEN.chorusVegetationMidlands.get()) biomeGenSettings.addFeature(Decoration.VEGETAL_DECORATION, BPPlacedFeatures.OCHAIM_GREEN);

        if (BPConfig.WORLDGEN.endSpongeMidlands.get()) biomeGenSettings.addFeature(Decoration.LOCAL_MODIFICATIONS, BPPlacedFeatures.END_LAND_SPONGE_PATCH_ML);

        return(new Biome.BiomeBuilder())
                .hasPrecipitation(false)
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
