package io.github.bioplethora.world.features;

import com.mojang.serialization.Codec;

import io.github.bioplethora.registry.BPBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.TallSeagrassBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.ProbabilityFeatureConfiguration;

public class CorsascileFeature extends Feature<ProbabilityFeatureConfiguration> {

    public CorsascileFeature(Codec<ProbabilityFeatureConfiguration> pCodec) {
        super(pCodec);
    }

    @Override
    public boolean place(FeaturePlaceContext<ProbabilityFeatureConfiguration> pContext) {
    	WorldGenLevel world = pContext.level();
    	RandomSource rand = pContext.random();
    	BlockPos pos = pContext.origin();
    	
        boolean flag = false;
        int i = rand.nextInt(32) - rand.nextInt(32);
        int j = rand.nextInt(32) - rand.nextInt(32);
        int k = world.getHeight(Heightmap.Types.OCEAN_FLOOR, pos.getX() + i, pos.getZ() + j);
        BlockPos blockpos = new BlockPos(pos.getX() + i, k, pos.getZ() + j);
        if (world.getBlockState(blockpos).is(Blocks.WATER) && world.getBlockState(blockpos.above()).is(Blocks.WATER)) {
            BlockState blockstate = BPBlocks.ENREDE_CORSASCILE.get().defaultBlockState().setValue(TallSeagrassBlock.HALF, DoubleBlockHalf.LOWER);
            BlockState blockstate1 = BPBlocks.ENREDE_CORSASCILE.get().defaultBlockState().setValue(TallSeagrassBlock.HALF, DoubleBlockHalf.UPPER);
            if (blockstate.canSurvive(world, blockpos)) {
                world.setBlock(blockpos, blockstate, 2);
                world.setBlock(blockpos.above(), blockstate1, 2);
                flag = true;
            }
        }

        return flag;
    }
}
