package io.github.bioplethora.registry.worldgen;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.api.BPBiomeSettings;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BPBiomes {
    public static final DeferredRegister<Biome> BIOMES = DeferredRegister.create(ForgeRegistries.BIOMES, Bioplethora.MOD_ID);
    // Overworld
    public static final ResourceKey<Biome> ROCKY_WOODLANDS = createKey("rocky_woodlands");

    // Nether
    public static final ResourceKey<Biome> CRYEANUM_PLAINS = createKey("cryeanum_plains");

    // End
    public static final ResourceKey<Biome> CAERI_PLAINS = createKey("caeri_plains");
    public static final ResourceKey<Biome> CAERI_FOREST = createKey("caeri_forest");
    public static final ResourceKey<Biome> WINTERFEST = createKey("winterfest");

    // End (Configurable)
    public static final ResourceKey<Biome> LAVENDER_LAKES = createKey("lavender_lakes");
    public static final ResourceKey<Biome> LAVENDER_PONDS = createKey("lavender_ponds");
    
    public static void bootstrap(BootstapContext<Biome> pContext) {
        HolderGetter<PlacedFeature> holdergetter = pContext.lookup(Registries.PLACED_FEATURE);
        HolderGetter<ConfiguredWorldCarver<?>> holdergetter1 = pContext.lookup(Registries.CONFIGURED_CARVER);
        pContext.register(ROCKY_WOODLANDS, BPBiomeSettings.rockyWoodlandsBiome(holdergetter, holdergetter1));
        pContext.register(CRYEANUM_PLAINS, BPBiomeSettings.cryeanumPlainsBiome(holdergetter, holdergetter1));
        pContext.register(CAERI_PLAINS, BPBiomeSettings.caeriPlainsBiome(holdergetter, holdergetter1));
        pContext.register(CAERI_FOREST, BPBiomeSettings.caeriForestBiome(holdergetter, holdergetter1));
        pContext.register(WINTERFEST, BPBiomeSettings.winterfestBiome(holdergetter, holdergetter1));
        pContext.register(LAVENDER_LAKES, BPBiomeSettings.lavenderLakesBiome(holdergetter, holdergetter1));
        pContext.register(LAVENDER_PONDS, BPBiomeSettings.lavenderPondsBiome(holdergetter, holdergetter1));
    }

	public static void generateBiomes() {
        //OverworldBiomes.addContinentalBiome(BPBiomes.ROCKY_WOODLANDS, ClimateSettings.COOL, 1);

		//TODO BiomeApiReforged Port
		/*NetherBiomes.addNetherBiome(BPBiomes.CRYEANUM_PLAINS, BPBiomeSettings.ATTRIBUTE);

		TheEndBiomes.addHighlandsBiome(BPBiomes.CAERI_FOREST, 7.0);
        TheEndBiomes.addMidlandsBiome(BPBiomes.CAERI_FOREST, BPBiomes.CAERI_PLAINS, 5);
        TheEndBiomes.addBarrensBiome(BPBiomes.CAERI_FOREST, BPBiomes.CAERI_PLAINS, 5);

        TheEndBiomes.addSmallIslandsBiome(BPBiomes.WINTERFEST, 4);

        if (BPConfig.WORLDGEN.createNewSpongeBiome.get()) {
            TheEndBiomes.addHighlandsBiome(BPBiomes.LAVENDER_LAKES, 7);
            TheEndBiomes.addMidlandsBiome(BPBiomes.LAVENDER_LAKES, BPBiomes.LAVENDER_PONDS, 5);
            TheEndBiomes.addBarrensBiome(BPBiomes.LAVENDER_LAKES, BPBiomes.LAVENDER_PONDS, 5);
        }*/
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
