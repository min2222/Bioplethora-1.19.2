package io.github.bioplethora.registry.worldgen;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.api.BPBiomeSettings;
import io.github.bioplethora.config.BPConfig;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.biome.NetherBiomes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.yeoxuhang.biomeapireforged.fabric.api.biome.TheEndBiomes;

public class BPBiomes {
    public static final DeferredRegister<Biome> BIOMES = DeferredRegister.create(ForgeRegistries.BIOMES, Bioplethora.MOD_ID);
    // Overworld
    public static final ResourceKey<Biome> ROCKY_WOODLANDS_KEY = createKey("rocky_woodlands");
    public static final RegistryObject<Biome> ROCKY_WOODLANDS = BIOMES.register("rocky_woodlands", () -> BPBiomeSettings.rockyWoodlandsBiome());

    // Nether
    public static final ResourceKey<Biome> CRYEANUM_PLAINS_KEY = createKey("cryeanum_plains");
    public static final RegistryObject<Biome> CRYEANUM_PLAINS = BIOMES.register("cryeanum_plains", () -> BPBiomeSettings.cryeanumPlainsBiome());

    // End
    public static final ResourceKey<Biome> CAERI_PLAINS_KEY = createKey("caeri_plains");
    public static final ResourceKey<Biome> CAERI_FOREST_KEY = createKey("caeri_forest");
    public static final ResourceKey<Biome> WINTERFEST_KEY = createKey("winterfest");
    
    public static final RegistryObject<Biome> CAERI_PLAINS = BIOMES.register("caeri_plains", () -> BPBiomeSettings.caeriPlainsBiome());
    public static final RegistryObject<Biome> CAERI_FOREST = BIOMES.register("caeri_forest", () -> BPBiomeSettings.caeriForestBiome());
    public static final RegistryObject<Biome> WINTERFEST = BIOMES.register("winterfest", () -> BPBiomeSettings.winterfestBiome());

    // End (Configurable)
    public static final ResourceKey<Biome> LAVENDER_LAKES_KEY = createKey("lavender_lakes");
    public static final ResourceKey<Biome> LAVENDER_PONDS_KEY = createKey("lavender_ponds");
    
    public static final RegistryObject<Biome> LAVENDER_LAKES = BIOMES.register("lavender_lakes", () -> BPBiomeSettings.lavenderLakesBiome());
    public static final RegistryObject<Biome> LAVENDER_PONDS = BIOMES.register("lavender_ponds", () -> BPBiomeSettings.lavenderPondsBiome());

	public static void generateBiomes() {
        //OverworldBiomes.addContinentalBiome(BPBiomes.ROCKY_WOODLANDS, ClimateSettings.COOL, 1);

		NetherBiomes.addNetherBiome(BPBiomes.CRYEANUM_PLAINS_KEY, BPBiomeSettings.ATTRIBUTE);

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
        ResourceKey<Biome> key = ResourceKey.create(Registries.BIOME, id);
        return key;
    }
}
