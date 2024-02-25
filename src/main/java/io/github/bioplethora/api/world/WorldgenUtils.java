package io.github.bioplethora.api.world;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class WorldgenUtils {
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
