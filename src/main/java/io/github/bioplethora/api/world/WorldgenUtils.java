package io.github.bioplethora.api.world;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class WorldgenUtils {

    public static final String BASALT_DELTAS = getVanillaBiome("basalt_deltas");
    public static final String NETHER_WASTES = getVanillaBiome("nether_wastes");
    public static final String WARPED_FOREST = getVanillaBiome("warped_forest");
    public static final String CRIMSON_FOREST = getVanillaBiome("crimson_forest");
    public static final String SOUL_SAND_VALLEY = getVanillaBiome("soul_sand_valley");

    public static final String END_HIGHLANDS = getVanillaBiome("end_highlands");
    public static final String SMALL_END_ISLANDS = getVanillaBiome("small_end_islands");
    public static final String END_MIDLANDS = getVanillaBiome("end_midlands");
    public static final String END_BARRENS = getVanillaBiome("end_barrens");

    public static String getVanillaBiome(String biomeId) {
        return "minecraft:" + biomeId;
    }

    /*public static boolean getBiomeFromEvent(BiomeLoadingEvent event, String biome) {
        return new ResourceLocation(biome).equals(event.getName());
    }

    public static boolean getBiomeFromEvent(BiomeLoadingEvent event, RegistryObject<Biome> biome) {
        return new ResourceLocation(biome.getId().toString()).equals(event.getName());
    }*/

    public static abstract class NBTTree {

        protected abstract ConfiguredFeature<?, ?> getTree(RandomSource random);

        public boolean placeAt(WorldGenLevel worldIn, ChunkGenerator chunkGenerator, BlockPos pos, BlockState belowPos, RandomSource random) {
        	
            ConfiguredFeature<?, ?> tree = this.getTree(random);
            if (tree == null) {
                return false;

            } else {
                worldIn.setBlock(pos, Blocks.AIR.defaultBlockState(), 4);
                if (tree.place(worldIn, chunkGenerator, random, pos)) {
                    return true;
                } else {
                    worldIn.setBlock(pos, belowPos, 4);
                    return false;
                }
            }
        }
    }
}
