package io.github.bioplethora.registry.worldgen;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.config.BPConfig;
import io.github.bioplethora.world.biomes.end.CaeriForestBiome;
import io.github.bioplethora.world.biomes.end.CaeriPlainsBiome;
import io.github.bioplethora.world.biomes.end.WinterfestBiome;
import io.github.bioplethora.world.biomes.end.configurable.LavenderLakesBiome;
import io.github.bioplethora.world.biomes.nether.CryeanumPlains;
import io.github.bioplethora.world.biomes.overworld.RockyWoodlandBiome;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.yeoxuhang.biomeapireforged.fabric.api.biome.NetherBiomes;
import net.yeoxuhang.biomeapireforged.fabric.api.biome.TheEndBiomes;

public class BPBiomes {
    public static final DeferredRegister<Biome> BIOMES = DeferredRegister.create(ForgeRegistries.BIOMES, Bioplethora.MOD_ID);
    
    public static final ResourceKey<Biome> ROCKY_WOODLANDS_KEY = create("rocky_woodlands");
    public static final ResourceKey<Biome> CRYEANUM_PLAINS_KEY = create("cryeanum_plains");
    public static final ResourceKey<Biome> CAERI_PLAINS_KEY = create("caeri_plains");
    public static final ResourceKey<Biome> CAERI_FOREST_KEY = create("caeri_forest");
    public static final ResourceKey<Biome> WINTERFEST_KEY = create("winterfest");
    public static final ResourceKey<Biome> LAVENDER_LAKES_KEY = create("lavender_lakes");
    public static final ResourceKey<Biome> LAVENDER_PONDS_KEY = create("lavender_ponds");
    
    public static final RegistryObject<Biome> ROCKY_WOODLANDS = BIOMES.register("rocky_woodlands", () -> bootstrap(ROCKY_WOODLANDS_KEY, RockyWoodlandBiome.make()).get());
    public static final RegistryObject<Biome> CRYEANUM_PLAINS = BIOMES.register("cryeanum_plains", () -> bootstrap(CRYEANUM_PLAINS_KEY, CryeanumPlains.make()).get());
    public static final RegistryObject<Biome> CAERI_PLAINS = BIOMES.register("caeri_plains", () -> bootstrap(CAERI_PLAINS_KEY, CaeriPlainsBiome.make()).get());
    public static final RegistryObject<Biome> CAERI_FOREST = BIOMES.register("caeri_forest", () -> bootstrap(CAERI_FOREST_KEY, CaeriForestBiome.make()).get());
    public static final RegistryObject<Biome> WINTERFEST = BIOMES.register("lavender_lakes", () -> bootstrap(WINTERFEST_KEY, WinterfestBiome.make()).get());
    public static final RegistryObject<Biome> LAVENDER_LAKES = BIOMES.register("lavender_lakes", () -> bootstrap(LAVENDER_LAKES_KEY, LavenderLakesBiome.make()).get());
    public static final RegistryObject<Biome> LAVENDER_PONDS = BIOMES.register("lavender_ponds", () -> bootstrap(LAVENDER_PONDS_KEY, LavenderLakesBiome.make()).get());
    
    public static ResourceKey<Biome> create(String id) {
        return ResourceKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(Bioplethora.MOD_ID, id));
    }
    
    public static Holder<Biome> bootstrap(ResourceKey<Biome> key, Biome biome) {
    	return BuiltinRegistries.register(BuiltinRegistries.BIOME, key, biome);
    }

	public static void generateBiomes() {
        //OverworldBiomes.addContinentalBiome(BPBiomes.ROCKY_WOODLANDS, ClimateSettings.COOL, 1);

        NetherBiomes.addNetherBiome(BPBiomes.CRYEANUM_PLAINS.getKey(), CryeanumPlains.ATTRIBUTE);

        TheEndBiomes.addHighlandsBiome(BPBiomes.CAERI_FOREST.getKey(), 7.0);
        TheEndBiomes.addMidlandsBiome(BPBiomes.CAERI_FOREST.getKey(), BPBiomes.CAERI_PLAINS.getKey(), 5);
        TheEndBiomes.addBarrensBiome(BPBiomes.CAERI_FOREST.getKey(), BPBiomes.CAERI_PLAINS.getKey(), 5);

        TheEndBiomes.addSmallIslandsBiome(BPBiomes.WINTERFEST.getKey(), 4);

        if (BPConfig.WORLDGEN.createNewSpongeBiome.get()) {
            TheEndBiomes.addHighlandsBiome(BPBiomes.LAVENDER_LAKES.getKey(), 7);
            TheEndBiomes.addMidlandsBiome(BPBiomes.LAVENDER_LAKES.getKey(), BPBiomes.LAVENDER_PONDS.getKey(), 5);
            TheEndBiomes.addBarrensBiome(BPBiomes.LAVENDER_LAKES.getKey(), BPBiomes.LAVENDER_PONDS.getKey(), 5);
        }
    }

    /*public static final class Type {
        public static final BiomeDictionary.Type CRYEANUM = BiomeDictionary.Type.getType("CRYEANUM");
        public static final BiomeDictionary.Type CAERI = BiomeDictionary.Type.getType("CAERI");
        public static final BiomeDictionary.Type CAERI_PLAINS = BiomeDictionary.Type.getType("CAERI_PLAINS");
        public static final BiomeDictionary.Type CAERI_FOREST = BiomeDictionary.Type.getType("CAERI_FOREST");
        public static final BiomeDictionary.Type WINTERFEST = BiomeDictionary.Type.getType("WINTERFEST");
        public static final BiomeDictionary.Type LAVENDER_LAKE = BiomeDictionary.Type.getType("LAVENDE_LAKE");
        public static final BiomeDictionary.Type LAVENDER_POND = BiomeDictionary.Type.getType("LAVENDE_POND");
    }*/

    //==============================================
    //                OTHERS
    //==============================================

    public static ResourceKey<Biome> getKey(Biome biome) {
        ResourceLocation keyRL = ForgeRegistries.BIOMES.getKey(biome);
        assert keyRL != null;
        return ResourceKey.create(ForgeRegistries.Keys.BIOMES, keyRL);
    }
}
