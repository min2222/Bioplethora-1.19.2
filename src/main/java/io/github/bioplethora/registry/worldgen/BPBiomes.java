package io.github.bioplethora.registry.worldgen;

import java.util.function.Supplier;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.config.BPConfig;
import io.github.bioplethora.world.biomes.end.CaeriForestBiome;
import io.github.bioplethora.world.biomes.end.CaeriPlainsBiome;
import io.github.bioplethora.world.biomes.end.configurable.LavenderLakesBiome;
import io.github.bioplethora.world.biomes.nether.CryeanumPlains;
import io.github.bioplethora.world.biomes.overworld.RockyWoodlandBiome;
import net.fabricmc.fabric.api.biome.v1.NetherBiomes;
import net.fabricmc.fabric.api.biome.v1.OverworldClimate;
import net.fabricmc.fabric.api.biome.v1.TheEndBiomes;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.biome.OverworldBiomes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BPBiomes {
    public static final DeferredRegister<Biome> BIOMES = DeferredRegister.create(ForgeRegistries.BIOMES, Bioplethora.MOD_ID);

    // Overworld
    public static final ResourceKey<Biome> ROCKY_WOODLANDS =
            add("rocky_woodlands", () -> RockyWoodlandBiome.make(() -> BPConfiguredSurfaceBuilders.ROCKY_WOODLANDS_SURFACE, 0.15f, 1.4f));

    // Nether
    public static final ResourceKey<Biome> CRYEANUM_PLAINS =
            add("cryeanum_plains", () -> CryeanumPlains.make(() -> BPConfiguredSurfaceBuilders.CRYEANUM_SURFACE),
                    BiomeDictionary.Type.NETHER, Type.CRYEANUM, BiomeDictionary.Type.PLAINS,  BiomeDictionary.Type.DENSE, BiomeDictionary.Type.HOT, BiomeDictionary.Type.MAGICAL
            );

    // End
    public static final ResourceKey<Biome> CAERI_PLAINS =
            add("caeri_plains", () -> CaeriPlainsBiome.make(() -> BPConfiguredSurfaceBuilders.CAERI_SURFACE),
                    Type.CAERI, BiomeDictionary.Type.END, Type.CAERI_PLAINS, BiomeDictionary.Type.PLAINS, BiomeDictionary.Type.DENSE, BiomeDictionary.Type.WET, BiomeDictionary.Type.MAGICAL
            );
    public static final ResourceKey<Biome> CAERI_FOREST =
            add("caeri_forest", () -> CaeriForestBiome.make(() -> BPConfiguredSurfaceBuilders.CAERI_SURFACE),
                    Type.CAERI, BiomeDictionary.Type.END, Type.CAERI_FOREST, BiomeDictionary.Type.FOREST, BiomeDictionary.Type.DENSE, BiomeDictionary.Type.WET,BiomeDictionary.Type.MAGICAL
            );
    public static final ResourceKey<Biome> WINTERFEST =
            add("winterfest", () -> CaeriForestBiome.make(() -> BPConfiguredSurfaceBuilders.ENDURION_SURFACE),
                    Type.WINTERFEST, BiomeDictionary.Type.END, BiomeDictionary.Type.COLD, BiomeDictionary.Type.WET, BiomeDictionary.Type.MAGICAL
            );

    // End (Configurable)
    public static final ResourceKey<Biome> LAVENDER_LAKES =
            add("lavender_lakes", () -> LavenderLakesBiome.make(() -> BPConfiguredSurfaceBuilders.ENDURION_SURFACE),
                    Type.LAVENDER_LAKE, BiomeDictionary.Type.END, BiomeDictionary.Type.LUSH, BiomeDictionary.Type.DENSE, BiomeDictionary.Type.WET, BiomeDictionary.Type.MAGICAL
            );
    public static final ResourceKey<Biome> LAVENDER_PONDS =
            add("lavender_ponds", () -> LavenderLakesBiome.make(() -> BPConfiguredSurfaceBuilders.ENDURION_SURFACE),
                    Type.LAVENDER_POND, BiomeDictionary.Type.END, BiomeDictionary.Type.LUSH, BiomeDictionary.Type.DENSE, BiomeDictionary.Type.WET, BiomeDictionary.Type.MAGICAL
            );

    public static void generateBiomes() {
        OverworldBiomes.addContinentalBiome(BPBiomes.ROCKY_WOODLANDS, OverworldClimate.COOL, 1);

        NetherBiomes.addNetherBiome(BPBiomes.CRYEANUM_PLAINS, CryeanumPlains.ATTRIBUTE);

        TheEndBiomes.addHighlandsBiome(BPBiomes.CAERI_FOREST, 7.0);
        TheEndBiomes.addMidlandsBiome(BPBiomes.CAERI_FOREST, BPBiomes.CAERI_PLAINS, 5);
        TheEndBiomes.addBarrensBiome(BPBiomes.CAERI_FOREST, BPBiomes.CAERI_PLAINS, 5);

        TheEndBiomes.addSmallIslandsBiome(BPBiomes.WINTERFEST, 4);

        if (BPConfig.WORLDGEN.createNewSpongeBiome.get()) {
            TheEndBiomes.addHighlandsBiome(BPBiomes.LAVENDER_LAKES, 7);
            TheEndBiomes.addMidlandsBiome(BPBiomes.LAVENDER_LAKES, BPBiomes.LAVENDER_PONDS, 5);
            TheEndBiomes.addBarrensBiome(BPBiomes.LAVENDER_LAKES, BPBiomes.LAVENDER_PONDS, 5);
        }
    }

    public static final class Type {
        public static final BiomeDictionary.Type CRYEANUM = BiomeDictionary.Type.getType("CRYEANUM");
        public static final BiomeDictionary.Type CAERI = BiomeDictionary.Type.getType("CAERI");
        public static final BiomeDictionary.Type CAERI_PLAINS = BiomeDictionary.Type.getType("CAERI_PLAINS");
        public static final BiomeDictionary.Type CAERI_FOREST = BiomeDictionary.Type.getType("CAERI_FOREST");
        public static final BiomeDictionary.Type WINTERFEST = BiomeDictionary.Type.getType("WINTERFEST");
        public static final BiomeDictionary.Type LAVENDER_LAKE = BiomeDictionary.Type.getType("LAVENDE_LAKE");
        public static final BiomeDictionary.Type LAVENDER_POND = BiomeDictionary.Type.getType("LAVENDE_POND");
    }

    //==============================================
    //                OTHERS
    //==============================================
    private static ResourceKey<Biome> add(String name, Supplier<Biome> biome, BiomeDictionary.Type... types) {
        ResourceLocation id = new ResourceLocation(Bioplethora.MOD_ID, name);
        ResourceKey<Biome> key = ResourceKey.create(Registry.BIOME_REGISTRY, id);
        BiomeDictionary.addTypes(key, types);
        BIOMES.register(name, biome);
        return key;
    }

    public static ResourceKey<Biome> getKey(Biome biome) {
        ResourceLocation keyRL = ForgeRegistries.BIOMES.getKey(biome);
        assert keyRL != null;
        return ResourceKey.create(ForgeRegistries.Keys.BIOMES, keyRL);
    }
}
