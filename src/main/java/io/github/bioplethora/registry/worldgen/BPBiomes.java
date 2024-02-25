package io.github.bioplethora.registry.worldgen;

import java.util.function.Supplier;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.world.biomes.end.CaeriForestBiome;
import io.github.bioplethora.world.biomes.end.CaeriPlainsBiome;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.yeoxuhang.biomeapireforged.fabric.api.biome.TheEndBiomes;

public class BPBiomes {
    public static final DeferredRegister<Biome> BIOMES = DeferredRegister.create(ForgeRegistries.BIOMES, Bioplethora.MOD_ID);
    
    // Overworld
    //public static final ResourceKey<Biome> ROCKY_WOODLANDS = add("rocky_woodlands", () -> RockyWoodlandBiome.make());

    // Nether
    //public static final ResourceKey<Biome> CRYEANUM_PLAINS = add("cryeanum_plains", () -> CryeanumPlains.make());

    // End
    public static final ResourceKey<Biome> CAERI_PLAINS = add("caeri_plains", () -> CaeriPlainsBiome.make());
    public static final ResourceKey<Biome> CAERI_FOREST = add("caeri_forest", () -> CaeriForestBiome.make());
    public static final ResourceKey<Biome> WINTERFEST = add("winterfest", () -> CaeriForestBiome.make());

    // End (Configurable)
    //public static final ResourceKey<Biome> LAVENDER_LAKES = add("lavender_lakes", () -> LavenderLakesBiome.make());
    //public static final ResourceKey<Biome> LAVENDER_PONDS = add("lavender_ponds", () -> LavenderLakesBiome.make());

	public static void generateBiomes() {
        //OverworldBiomes.addContinentalBiome(BPBiomes.ROCKY_WOODLANDS, ClimateSettings.COOL, 1);

		/*NetherBiomes.addNetherBiome(BPBiomes.CRYEANUM_PLAINS, CryeanumPlains.ATTRIBUTE);*/

        TheEndBiomes.addHighlandsBiome(BPBiomes.CAERI_FOREST, 7.0);
        TheEndBiomes.addMidlandsBiome(BPBiomes.CAERI_FOREST, BPBiomes.CAERI_PLAINS, 5);
        TheEndBiomes.addBarrensBiome(BPBiomes.CAERI_FOREST, BPBiomes.CAERI_PLAINS, 5);

        TheEndBiomes.addSmallIslandsBiome(BPBiomes.WINTERFEST, 4);

        /*if (BPConfig.WORLDGEN.createNewSpongeBiome.get()) {
            TheEndBiomes.addHighlandsBiome(BPBiomes.LAVENDER_LAKES, 7);
            TheEndBiomes.addMidlandsBiome(BPBiomes.LAVENDER_LAKES, BPBiomes.LAVENDER_PONDS, 5);
            TheEndBiomes.addBarrensBiome(BPBiomes.LAVENDER_LAKES, BPBiomes.LAVENDER_PONDS, 5);
        }*/
    }

    //==============================================
    //                OTHERS
    //==============================================
	
    private static ResourceKey<Biome> add(String name, Supplier<Biome> biome) {
        ResourceLocation id = new ResourceLocation(Bioplethora.MOD_ID, name);
        ResourceKey<Biome> key = ResourceKey.create(Registry.BIOME_REGISTRY, id);
        BIOMES.register(name, biome);
        return key;
    }

    public static ResourceKey<Biome> getKey(Biome biome) {
        ResourceLocation keyRL = ForgeRegistries.BIOMES.getKey(biome);
        assert keyRL != null;
        return ResourceKey.create(ForgeRegistries.Keys.BIOMES, keyRL);
    }
}
