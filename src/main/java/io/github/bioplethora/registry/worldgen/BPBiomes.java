package io.github.bioplethora.registry.worldgen;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.config.BPConfig;
import io.github.bioplethora.world.biomes.end.CaeriForestBiome;
import io.github.bioplethora.world.biomes.end.CaeriPlainsBiome;
import io.github.bioplethora.world.biomes.end.WinterfestBiome;
import io.github.bioplethora.world.biomes.end.configurable.LavenderLakesBiome;
import io.github.bioplethora.world.biomes.end.configurable.LavenderPondsBiome;
import io.github.bioplethora.world.biomes.nether.CryeanumPlains;
import io.github.bioplethora.world.biomes.overworld.RockyWoodlandBiome;
import net.minecraft.core.Registry;
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
    // Overworld
    public static final ResourceKey<Biome> ROCKY_WOODLANDS_KEY = createKey("rocky_woodlands");
    public static final RegistryObject<Biome> ROCKY_WOODLANDS = BIOMES.register("rocky_woodlands", () -> RockyWoodlandBiome.make());

    // Nether
    public static final ResourceKey<Biome> CRYEANUM_PLAINS_KEY = createKey("cryeanum_plains");
    public static final RegistryObject<Biome> CRYEANUM_PLAINS = BIOMES.register("cryeanum_plains", () -> CryeanumPlains.make());

    // End
    public static final ResourceKey<Biome> CAERI_PLAINS_KEY = createKey("caeri_plains");
    public static final ResourceKey<Biome> CAERI_FOREST_KEY = createKey("caeri_forest");
    public static final ResourceKey<Biome> WINTERFEST_KEY = createKey("winterfest");
    
    public static final RegistryObject<Biome> CAERI_PLAINS = BIOMES.register("caeri_plains", () -> CaeriPlainsBiome.make());
    public static final RegistryObject<Biome> CAERI_FOREST = BIOMES.register("caeri_forest", () -> CaeriForestBiome.make());
    public static final RegistryObject<Biome> WINTERFEST = BIOMES.register("winterfest", () -> WinterfestBiome.make());

    // End (Configurable)
    public static final ResourceKey<Biome> LAVENDER_LAKES_KEY = createKey("lavender_lakes");
    public static final ResourceKey<Biome> LAVENDER_PONDS_KEY = createKey("lavender_ponds");
    
    public static final RegistryObject<Biome> LAVENDER_LAKES = BIOMES.register("lavender_lakes", () -> LavenderLakesBiome.make());
    public static final RegistryObject<Biome> LAVENDER_PONDS = BIOMES.register("lavender_ponds", () -> LavenderPondsBiome.make());

	public static void generateBiomes() {
        //OverworldBiomes.addContinentalBiome(BPBiomes.ROCKY_WOODLANDS, ClimateSettings.COOL, 1);

		NetherBiomes.addNetherBiome(BPBiomes.CRYEANUM_PLAINS_KEY, CryeanumPlains.ATTRIBUTE);

        TheEndBiomes.addHighlandsBiome(BPBiomes.CAERI_FOREST_KEY, 7.0);
        TheEndBiomes.addMidlandsBiome(BPBiomes.CAERI_FOREST_KEY, BPBiomes.CAERI_PLAINS_KEY, 5);
        TheEndBiomes.addBarrensBiome(BPBiomes.CAERI_FOREST_KEY, BPBiomes.CAERI_PLAINS_KEY, 5);

        TheEndBiomes.addSmallIslandsBiome(BPBiomes.WINTERFEST_KEY, 4);

        if (BPConfig.WORLDGEN.createNewSpongeBiome.get()) {
            TheEndBiomes.addHighlandsBiome(BPBiomes.LAVENDER_LAKES_KEY, 7);
            TheEndBiomes.addMidlandsBiome(BPBiomes.LAVENDER_LAKES_KEY, BPBiomes.LAVENDER_PONDS_KEY, 5);
            TheEndBiomes.addBarrensBiome(BPBiomes.LAVENDER_LAKES_KEY, BPBiomes.LAVENDER_PONDS_KEY, 5);
        }
    }

    //==============================================
    //                OTHERS
    //==============================================
	
    private static ResourceKey<Biome> createKey(String name) {
        ResourceLocation id = new ResourceLocation(Bioplethora.MOD_ID, name);
        ResourceKey<Biome> key = ResourceKey.create(Registry.BIOME_REGISTRY, id);
        return key;
    }
}
