package io.github.bioplethora.world;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

import org.apache.logging.log4j.LogManager;

import com.mojang.serialization.Codec;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.registry.worldgen.BPStructures;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RegistryKey;
import net.minecraft.world.gen.FlatChunkGenerator;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;

@Mod.EventBusSubscriber(modid = Bioplethora.MOD_ID)
public class BPStructureGeneration {

    @SubscribeEvent
    public static void generateStructures(final BiomeLoadingEvent event) {
        RegistryKey<Biome> key = RegistryKey.create(Registry.BIOME_REGISTRY, event.getName());
        Set<BiomeDictionary.Type> types = BiomeDictionary.getTypes(key);
        List<Supplier<StructureFeature<?, ?>>> structures = event.getGeneration().getStructures();

        if (types.contains(BiomeDictionary.Type.COLD) || types.contains(BiomeDictionary.Type.MOUNTAIN) || types.contains(BiomeDictionary.Type.FOREST)) {
            structures.add(() -> BPStructures.ALPHANUM_MAUSOLEUM.get().configured(IFeatureConfig.NONE));
        }
    }

    @SubscribeEvent
    public static void addDimensionalSpacing(final LevelEvent.Load event) {
        if(event.getLevel() instanceof ServerLevel) {
            ServerLevel serverLevel = (ServerLevel) event.getLevel();

            try {
                Method GETCODEC_METHOD = ObfuscationReflectionHelper.findMethod(ChunkGenerator.class, "func_230347_a_");
                ResourceLocation cgRL = Registry.CHUNK_GENERATOR.getKey((Codec<? extends ChunkGenerator>)GETCODEC_METHOD.invoke(serverLevel.getChunkSource().generator));

                if (cgRL != null && cgRL.getNamespace().equals("terraforged")) {
                    return;
                }
            } catch (Exception e) {
                LogManager.getLogger().error("Was unable to check if " + serverLevel.dimension().location() + " is using Terraforged's ChunkGenerator.");
            }

            // Prevent spawning our structure in Vanilla's superflat world
            if (serverLevel.getChunkSource().generator instanceof FlatChunkGenerator && serverLevel.dimension().equals(Level.OVERWORLD)) {
                return;
            }

            // Adding our Structure to the Map
            Map<Structure<?>, StructureSeparationSettings> tempMap = new HashMap<Structure<?>, StructureSeparationSettings>(serverLevel.getChunkSource().generator.getSettings().structureConfig());

            tempMap.putIfAbsent(BPStructures.ALPHANUM_MAUSOLEUM.get(), DimensionStructuresSettings.DEFAULTS.get(BPStructures.ALPHANUM_MAUSOLEUM.get()));

            serverLevel.getChunkSource().generator.getSettings().structureConfig = tempMap;
        }
    }
}
