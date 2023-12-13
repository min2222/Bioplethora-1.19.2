package io.github.bioplethora.world;

import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

import io.github.bioplethora.registry.worldgen.BPStructures;
import net.minecraft.core.Registry;
import net.minecraft.util.RegistryKey;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class BPStructureGeneration {

    @SubscribeEvent
    public static void generateStructures(final BiomeLoadingEvent event) {
        RegistryKey<Biome> key = RegistryKey.create(Registry.BIOME_REGISTRY, event.getName());
        Set<BiomeDictionary.Type> types = BiomeDictionary.getTypes(key);
        List<Supplier<StructureFeature<?, ?>>> structures = event.getGeneration().getStructures();

        if (types.contains(net.minecraftforge.common.Tags.Biomes.IS_COLD) || types.contains(BiomeDictionary.Type.MOUNTAIN) || types.contains(BiomeDictionary.Type.FOREST)) {
            structures.add(() -> BPStructures.ALPHANUM_MAUSOLEUM.get().configured(IFeatureConfig.NONE));
        }
    }
}
