package io.github.bioplethora.world;

import java.util.Random;

import com.google.common.collect.ImmutableList;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
public class BPVanillaBiomeFeatureGeneration {

    public static ImmutableList<Block> stoneBlocks() {
        return ImmutableList.of(Blocks.STONE, Blocks.ANDESITE, Blocks.DIORITE, Blocks.GRANITE);
    }

    public static ImmutableList<BlockState> stoneBlockstates() {
        return ImmutableList.of(Blocks.STONE.defaultBlockState(), Blocks.ANDESITE.defaultBlockState(), Blocks.DIORITE.defaultBlockState(), Blocks.GRANITE.defaultBlockState());
    }

    public static Random seedFleignarChunk(int pChunkX, int pChunkZ, long pSeed, long l) {
        return new Random(pSeed + ((long) pChunkX * pChunkX * 4987142) + (pChunkX * 5947611L) + (long) pChunkZ * pChunkZ * 4392871L + (pChunkZ * 389711L) ^ l);
    }

    public static boolean isFleignariteChunk(BlockPos pos, WorldGenLevel seedReader) {
        ChunkPos chunkpos = new ChunkPos(pos);
        return seedFleignarChunk(chunkpos.x, chunkpos.z, seedReader.getSeed(), 987234911L).nextInt(20) == 0;
    }
}
