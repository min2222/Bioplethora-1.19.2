package io.github.bioplethora.world;

import com.mojang.serialization.Codec;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.config.BPConfig;
import io.github.bioplethora.registry.BPParticles;
import io.github.bioplethora.registry.worldgen.BPPlacedFeatures;
import io.github.bioplethora.world.EntitySpawnManager.BioplethoraMobSpawns;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.AmbientMoodSettings;
import net.minecraft.world.level.biome.AmbientParticleSettings;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ModifiableBiomeInfo;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.server.ServerLifecycleHooks;

public class BPBiomeModifier implements BiomeModifier {
    private static final RegistryObject<Codec<? extends BiomeModifier>> SERIALIZER = RegistryObject.create(new ResourceLocation(Bioplethora.MOD_ID, "bp_modifiers"), ForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, Bioplethora.MOD_ID);

    public BPBiomeModifier() {
    	
    }

    @Override
    public void modify(Holder<Biome> biome, Phase phase, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
        if (phase == Phase.ADD) {
        	if(biome.is(BiomeTags.IS_OVERWORLD)) {
        		BioplethoraMobSpawns.OVERWORLD_ENTITIES.accept(builder.getMobSpawnSettings());
        	}
        	if(biome.is(BiomeTags.IS_OVERWORLD) && biome.is(BiomeTags.IS_FOREST)) {
        		BioplethoraMobSpawns.FOREST_ENTITIES.accept(builder.getMobSpawnSettings());
        	}
        	if(biome.is(BiomeTags.IS_OVERWORLD) && biome.is(Tags.Biomes.IS_DRY)) {
        		BioplethoraMobSpawns.DESERT_ENTITIES.accept(builder.getMobSpawnSettings());
        	}
        	if(biome.is(BiomeTags.IS_OVERWORLD) && biome.is(Tags.Biomes.IS_SWAMP)) {
        		BioplethoraMobSpawns.SWAMP_ENTITIES.accept(builder.getMobSpawnSettings());
        	}
        	if(biome.is(BiomeTags.IS_OVERWORLD) && biome.is(BiomeTags.IS_JUNGLE)) {
        		BioplethoraMobSpawns.JUNGLE_ENTITIES.accept(builder.getMobSpawnSettings());
        	}
        	if(biome.is(BiomeTags.IS_OVERWORLD) && (biome.is(Tags.Biomes.IS_CONIFEROUS) || biome.is(Tags.Biomes.IS_COLD))) {
        		BioplethoraMobSpawns.TAIGA_ENTITIES.accept(builder.getMobSpawnSettings());
        	}
        	if(biome.is(BiomeTags.IS_OVERWORLD) && biome.is(Tags.Biomes.IS_COLD)) {
        		BioplethoraMobSpawns.ICY_ENTITIES.accept(builder.getMobSpawnSettings());
        	}
        	if(biome.is(BiomeTags.IS_OVERWORLD) && biome.is(BiomeTags.IS_SAVANNA)) {
        		BioplethoraMobSpawns.SAVANNA_ENTITIES.accept(builder.getMobSpawnSettings());
        	}
        	if(biome.is(BiomeTags.IS_OVERWORLD) && biome.is(BiomeTags.IS_RIVER)) {
        		BioplethoraMobSpawns.RIVER_ENTITIES.accept(builder.getMobSpawnSettings());
        	}
        	if(biome.is(BiomeTags.IS_OVERWORLD) && biome.is(BiomeTags.IS_OCEAN)) {
        		BioplethoraMobSpawns.OCEAN_ENTITIES.accept(builder.getMobSpawnSettings());
        	}
        	if(biome.is(BiomeTags.IS_NETHER)) {
        		 if(biome.is(Biomes.BASALT_DELTAS)) {
        			 BioplethoraMobSpawns.BASALT_DELTAS_ENTITIES.accept(builder.getMobSpawnSettings());
        		 }
        		 if(biome.is(Biomes.NETHER_WASTES)) {
        			 BioplethoraMobSpawns.NETHER_WASTES_ENTITIES.accept(builder.getMobSpawnSettings());
        		 }
        		 if(biome.is(Biomes.WARPED_FOREST)) {
        			 BioplethoraMobSpawns.WARPED_FOREST_ENTITIES.accept(builder.getMobSpawnSettings());
        		 }
        		 if(biome.is(Biomes.CRIMSON_FOREST)) {
        			 BioplethoraMobSpawns.CRIMSON_FOREST_ENTITIES.accept(builder.getMobSpawnSettings());
        		 }
        		 if(biome.is(Biomes.SOUL_SAND_VALLEY)) {
        			 BioplethoraMobSpawns.SOUL_SAND_VALLEY_ENTITIES.accept(builder.getMobSpawnSettings());
        		 }
        	}
        	if(biome.is(BiomeTags.IS_END)) {
        		BioplethoraMobSpawns.END_ENTITIES.accept(builder.getMobSpawnSettings());
        	}
        	
        	if(biome.is(BiomeTags.IS_OVERWORLD)) {
            	ServerLevel overWorld = ServerLifecycleHooks.getCurrentServer().getLevel(Level.OVERWORLD);
            	if(overWorld == null)
            		return;
        		builder.getGenerationSettings().getFeatures(Decoration.UNDERGROUND_DECORATION).add(overWorld.registryAccess().registryOrThrow(Registries.PLACED_FEATURE).getHolderOrThrow(BPPlacedFeatures.FLEIGNARITE_REMAINS));
        		builder.getGenerationSettings().getFeatures(Decoration.UNDERGROUND_DECORATION).add(overWorld.registryAccess().registryOrThrow(Registries.PLACED_FEATURE).getHolderOrThrow(BPPlacedFeatures.FLEIGNARITE_VINES));
        		builder.getGenerationSettings().getFeatures(Decoration.VEGETAL_DECORATION).add(overWorld.registryAccess().registryOrThrow(Registries.PLACED_FEATURE).getHolderOrThrow(BPPlacedFeatures.FLEIGNARITE_REMAINS));
        		builder.getGenerationSettings().getFeatures(Decoration.VEGETAL_DECORATION).add(overWorld.registryAccess().registryOrThrow(Registries.PLACED_FEATURE).getHolderOrThrow(BPPlacedFeatures.FLEIGNARITE_VINES));
        	}
        	if(biome.is(BiomeTags.IS_NETHER)) {
            	ServerLevel nether = ServerLifecycleHooks.getCurrentServer().getLevel(Level.NETHER);
            	if(nether == null)
            		return;
        		if(biome.is(Biomes.BASALT_DELTAS)) {
            		builder.getGenerationSettings().getFeatures(Decoration.VEGETAL_DECORATION).add(nether.registryAccess().registryOrThrow(Registries.PLACED_FEATURE).getHolderOrThrow(BPPlacedFeatures.BASALT_SPELEOTHERM));
            		builder.getGenerationSettings().getFeatures(Decoration.VEGETAL_DECORATION).add(nether.registryAccess().registryOrThrow(Registries.PLACED_FEATURE).getHolderOrThrow(BPPlacedFeatures.LAVA_SPIRE));
        		}
        		if(biome.is(Biomes.NETHER_WASTES)) {
            		builder.getGenerationSettings().getFeatures(Decoration.VEGETAL_DECORATION).add(nether.registryAccess().registryOrThrow(Registries.PLACED_FEATURE).getHolderOrThrow(BPPlacedFeatures.THONTUS_THISTLE));
            		builder.getGenerationSettings().getFeatures(Decoration.VEGETAL_DECORATION).add(nether.registryAccess().registryOrThrow(Registries.PLACED_FEATURE).getHolderOrThrow(BPPlacedFeatures.LAVA_SPIRE));
        		}
        		if(biome.is(Biomes.WARPED_FOREST)) {
            		builder.getGenerationSettings().getFeatures(Decoration.VEGETAL_DECORATION).add(nether.registryAccess().registryOrThrow(Registries.PLACED_FEATURE).getHolderOrThrow(BPPlacedFeatures.WARPED_DANCER));
            		builder.getGenerationSettings().getFeatures(Decoration.VEGETAL_DECORATION).add(nether.registryAccess().registryOrThrow(Registries.PLACED_FEATURE).getHolderOrThrow(BPPlacedFeatures.TURQUOISE_PENDENT));
        		}
        		if(biome.is(Biomes.CRIMSON_FOREST)) {
            		builder.getGenerationSettings().getFeatures(Decoration.VEGETAL_DECORATION).add(nether.registryAccess().registryOrThrow(Registries.PLACED_FEATURE).getHolderOrThrow(BPPlacedFeatures.CERISE_IVY));
            		builder.getGenerationSettings().getFeatures(Decoration.VEGETAL_DECORATION).add(nether.registryAccess().registryOrThrow(Registries.PLACED_FEATURE).getHolderOrThrow(BPPlacedFeatures.LAVA_SPIRE));
        		}
        		if(biome.is(Biomes.SOUL_SAND_VALLEY)) {
        			
        			builder.getGenerationSettings().getFeatures(Decoration.VEGETAL_DECORATION).add(nether.registryAccess().registryOrThrow(Registries.PLACED_FEATURE).getHolderOrThrow(BPPlacedFeatures.SOUL_MINISHROOM));
            		builder.getGenerationSettings().getFeatures(Decoration.VEGETAL_DECORATION).add(nether.registryAccess().registryOrThrow(Registries.PLACED_FEATURE).getHolderOrThrow(BPPlacedFeatures.SOUL_BIGSHROOM));
            		builder.getGenerationSettings().getFeatures(Decoration.VEGETAL_DECORATION).add(nether.registryAccess().registryOrThrow(Registries.PLACED_FEATURE).getHolderOrThrow(BPPlacedFeatures.SOUL_SPROUTS));
            		builder.getGenerationSettings().getFeatures(Decoration.VEGETAL_DECORATION).add(nether.registryAccess().registryOrThrow(Registries.PLACED_FEATURE).getHolderOrThrow(BPPlacedFeatures.SOUL_TALL_GRASS));
            		builder.getGenerationSettings().getFeatures(Decoration.VEGETAL_DECORATION).add(nether.registryAccess().registryOrThrow(Registries.PLACED_FEATURE).getHolderOrThrow(BPPlacedFeatures.SPIRIT_DANGLER));
            		builder.getGenerationSettings().getFeatures(Decoration.VEGETAL_DECORATION).add(nether.registryAccess().registryOrThrow(Registries.PLACED_FEATURE).getHolderOrThrow(BPPlacedFeatures.SOUL_ETERN));
        		}
        	}
        	if(biome.is(Biomes.THE_END)) {
            	ServerLevel end = ServerLifecycleHooks.getCurrentServer().getLevel(Level.END);
            	if(end == null)
            		return;
                if (BPConfig.WORLDGEN.cyraLakesEnd.get()) builder.getGenerationSettings().getFeatures(Decoration.UNDERGROUND_DECORATION).add(end.registryAccess().registryOrThrow(Registries.PLACED_FEATURE).getHolderOrThrow(BPPlacedFeatures.CYRA_LAKE));
                if (!BPConfig.WORLDGEN.createNewSpongeBiome.get()) {
                    if (biome.is(Biomes.END_HIGHLANDS)) {
                        builder.getGenerationSettings().getFeatures(Decoration.VEGETAL_DECORATION).add(end.registryAccess().registryOrThrow(Registries.PLACED_FEATURE).getHolderOrThrow(BPPlacedFeatures.CHORUS_MYCHRODEGIA));
                        builder.getSpecialEffects().waterColor(-6599759)
                        .waterFogColor(-13158998)
                        .fogColor(-12378263)
                        .skyColor(-12378263)
                        .ambientParticle(new AmbientParticleSettings(BPParticles.NIGHT_GAZE.get(), 0.04F))
                        .ambientLoopSound(SoundEvents.AMBIENT_SOUL_SAND_VALLEY_MOOD)
                        .ambientMoodSound(new AmbientMoodSettings(SoundEvents.AMBIENT_NETHER_WASTES_MOOD, 6000, 8, 2.0D))
                        .build();

                        if (BPConfig.WORLDGEN.chorusVegetationHighlands.get()) builder.getGenerationSettings().getFeatures(Decoration.TOP_LAYER_MODIFICATION).add(end.registryAccess().registryOrThrow(Registries.PLACED_FEATURE).getHolderOrThrow(BPPlacedFeatures.ENREDE_KELP));

                        if (BPConfig.WORLDGEN.chorusLanternHighlands.get()) builder.getGenerationSettings().getFeatures(Decoration.VEGETAL_DECORATION).add(end.registryAccess().registryOrThrow(Registries.PLACED_FEATURE).getHolderOrThrow(BPPlacedFeatures.CHORUS_LANTERN_HIGHLANDS_PATCH));
                        if (BPConfig.WORLDGEN.chorusVegetationHighlands.get()) builder.getGenerationSettings().getFeatures(Decoration.VEGETAL_DECORATION).add(end.registryAccess().registryOrThrow(Registries.PLACED_FEATURE).getHolderOrThrow(BPPlacedFeatures.CHORUS_IDON));
                        if (BPConfig.WORLDGEN.chorusVegetationHighlands.get()) builder.getGenerationSettings().getFeatures(Decoration.VEGETAL_DECORATION).add(end.registryAccess().registryOrThrow(Registries.PLACED_FEATURE).getHolderOrThrow(BPPlacedFeatures.CHORUS_IDE_FAN));
                        if (BPConfig.WORLDGEN.chorusVegetationHighlands.get()) builder.getGenerationSettings().getFeatures(Decoration.VEGETAL_DECORATION).add(end.registryAccess().registryOrThrow(Registries.PLACED_FEATURE).getHolderOrThrow(BPPlacedFeatures.ENREDE_CORSASCILE));
                        if (BPConfig.WORLDGEN.chorusVegetationHighlands.get()) builder.getGenerationSettings().getFeatures(Decoration.VEGETAL_DECORATION).add(end.registryAccess().registryOrThrow(Registries.PLACED_FEATURE).getHolderOrThrow(BPPlacedFeatures.OCHAIM_PURPLE));
                        if (BPConfig.WORLDGEN.chorusVegetationHighlands.get()) builder.getGenerationSettings().getFeatures(Decoration.VEGETAL_DECORATION).add(end.registryAccess().registryOrThrow(Registries.PLACED_FEATURE).getHolderOrThrow(BPPlacedFeatures.OCHAIM_RED));
                        if (BPConfig.WORLDGEN.chorusVegetationHighlands.get()) builder.getGenerationSettings().getFeatures(Decoration.VEGETAL_DECORATION).add(end.registryAccess().registryOrThrow(Registries.PLACED_FEATURE).getHolderOrThrow(BPPlacedFeatures.OCHAIM_GREEN));

                        if (BPConfig.WORLDGEN.endSpikeHighlands.get()) builder.getGenerationSettings().getFeatures(Decoration.LOCAL_MODIFICATIONS).add(end.registryAccess().registryOrThrow(Registries.PLACED_FEATURE).getHolderOrThrow(BPPlacedFeatures.END_LAND_SPIKE_PATCH_HL));
                        
                        if (BPConfig.WORLDGEN.endSpongeHighlands.get()) builder.getGenerationSettings().getFeatures(Decoration.LOCAL_MODIFICATIONS).add(end.registryAccess().registryOrThrow(Registries.PLACED_FEATURE).getHolderOrThrow(BPPlacedFeatures.END_LAND_SPONGE_PATCH_HL));
                    }

                    if (biome.is(Biomes.END_MIDLANDS) || biome.is(Biomes.END_BARRENS)) {
                        builder.getGenerationSettings().getFeatures(Decoration.VEGETAL_DECORATION).add(end.registryAccess().registryOrThrow(Registries.PLACED_FEATURE).getHolderOrThrow(BPPlacedFeatures.CHORUS_MYCHRODEGIA));
                        builder.getSpecialEffects()
                        .waterColor(-6599759)
                        .waterFogColor(-13158998)
                        .fogColor(-12378263)
                        .skyColor(-12378263)
                        .ambientParticle(new AmbientParticleSettings(BPParticles.NIGHT_GAZE.get(), 0.03F))
                        .ambientLoopSound(SoundEvents.AMBIENT_SOUL_SAND_VALLEY_MOOD)
                        .ambientMoodSound(new AmbientMoodSettings(SoundEvents.AMBIENT_NETHER_WASTES_MOOD, 6000, 8, 2.0D))
                        .build();

                        if (BPConfig.WORLDGEN.chorusVegetationHighlands.get()) builder.getGenerationSettings().getFeatures(Decoration.TOP_LAYER_MODIFICATION).add(end.registryAccess().registryOrThrow(Registries.PLACED_FEATURE).getHolderOrThrow(BPPlacedFeatures.ENREDE_KELP));

                        if (BPConfig.WORLDGEN.chorusLanternMidlands.get()) builder.getGenerationSettings().getFeatures(Decoration.VEGETAL_DECORATION).add(end.registryAccess().registryOrThrow(Registries.PLACED_FEATURE).getHolderOrThrow(BPPlacedFeatures.CHORUS_LANTERN_MIDLANDS_PATCH));
                        if (BPConfig.WORLDGEN.chorusVegetationMidlands.get()) builder.getGenerationSettings().getFeatures(Decoration.VEGETAL_DECORATION).add(end.registryAccess().registryOrThrow(Registries.PLACED_FEATURE).getHolderOrThrow(BPPlacedFeatures.CHORUS_IDON));
                        if (BPConfig.WORLDGEN.chorusVegetationMidlands.get()) builder.getGenerationSettings().getFeatures(Decoration.VEGETAL_DECORATION).add(end.registryAccess().registryOrThrow(Registries.PLACED_FEATURE).getHolderOrThrow(BPPlacedFeatures.CHORUS_IDE_FAN));
                        if (BPConfig.WORLDGEN.chorusVegetationMidlands.get()) builder.getGenerationSettings().getFeatures(Decoration.VEGETAL_DECORATION).add(end.registryAccess().registryOrThrow(Registries.PLACED_FEATURE).getHolderOrThrow(BPPlacedFeatures.ENREDE_KELP));
                        if (BPConfig.WORLDGEN.chorusVegetationMidlands.get()) builder.getGenerationSettings().getFeatures(Decoration.VEGETAL_DECORATION).add(end.registryAccess().registryOrThrow(Registries.PLACED_FEATURE).getHolderOrThrow(BPPlacedFeatures.ENREDE_CORSASCILE));
                        if (BPConfig.WORLDGEN.chorusVegetationMidlands.get()) builder.getGenerationSettings().getFeatures(Decoration.VEGETAL_DECORATION).add(end.registryAccess().registryOrThrow(Registries.PLACED_FEATURE).getHolderOrThrow(BPPlacedFeatures.OCHAIM_PURPLE));
                        if (BPConfig.WORLDGEN.chorusVegetationMidlands.get()) builder.getGenerationSettings().getFeatures(Decoration.VEGETAL_DECORATION).add(end.registryAccess().registryOrThrow(Registries.PLACED_FEATURE).getHolderOrThrow(BPPlacedFeatures.OCHAIM_RED));
                        if (BPConfig.WORLDGEN.chorusVegetationMidlands.get()) builder.getGenerationSettings().getFeatures(Decoration.VEGETAL_DECORATION).add(end.registryAccess().registryOrThrow(Registries.PLACED_FEATURE).getHolderOrThrow(BPPlacedFeatures.OCHAIM_GREEN));

                        if (BPConfig.WORLDGEN.endSpikeMidlands.get()) builder.getGenerationSettings().getFeatures(Decoration.LOCAL_MODIFICATIONS).add(end.registryAccess().registryOrThrow(Registries.PLACED_FEATURE).getHolderOrThrow(BPPlacedFeatures.END_LAND_SPIKE_PATCH_ML));
                        if (BPConfig.WORLDGEN.endSpongeMidlands.get()) builder.getGenerationSettings().getFeatures(Decoration.LOCAL_MODIFICATIONS).add(end.registryAccess().registryOrThrow(Registries.PLACED_FEATURE).getHolderOrThrow(BPPlacedFeatures.END_LAND_SPONGE_PATCH_ML));
                    }
                }
        	}
        }
    }
    
    @Override
    public Codec<? extends BiomeModifier> codec() {
        return SERIALIZER.get();
    }

    public static Codec<BPBiomeModifier> makeCodec() {
        return Codec.unit(BPBiomeModifier::new);
    }
}
