package io.github.bioplethora.world.features;

import java.util.Random;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;

import io.github.bioplethora.registry.BPBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.ILevel;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.feature.BasaltDeltasFeature;
import net.minecraft.world.gen.feature.structure.BasaltDeltasStructure;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;

public class EndLandsSpongeFeature extends BasaltDeltasStructure {
    private static final ImmutableList<Block> CANNOT_REPLACE = ImmutableList.of(Blocks.BEDROCK);
    private static final Direction[] DIRECTIONS = Direction.values();
    
    public EndLandsSpongeFeature(Codec<BasaltDeltasFeature> pCodec) {
        super(pCodec);
    }

    @Override
    public boolean place(ISeedReader level, ChunkGenerator chunkGen, Random rand, BlockPos pos, BasaltDeltasFeature config) {
        boolean flag = false;
        int i = config.rimSize().sample(rand);
        int j = config.rimSize().sample(rand);
        boolean flag2 = i != 0 && j != 0;
        int k = config.size().sample(rand);
        int l = config.size().sample(rand);
        int i1 = Math.max(k, l);
        for (BlockPos blockpos : BlockPos.withinManhattan(pos, k, 3, l)) {
            if (blockpos.distManhattan(pos) > i1) {
                break;
            }

            if (isClearer(level, blockpos, config)) {
                if (flag2) {
                    flag = true;
                    if (!level.getBlockState(blockpos).is(BPBlocks.CHORUS_MYCHRODEGIA.get()) && !level.getBlockState(blockpos).is(BPBlocks.CHORUS_MYCHRODEGIA_PART.get())) {
                        this.setBlock(level, blockpos, config.rim());
                    }
                }
                this.carveOpenings(level, blockpos, config);

                BlockPos blockpos1 = blockpos.offset(i, 0, j);
                if (isClearOptimized(level, blockpos1)) {
                    flag = true;
                    this.carveGap(level, blockpos1, config, false);
                    this.carveOpenings(level, blockpos1, config);
                }
            }
        }

        return flag;
    }

    public void carveGap(ILevel pLevel, BlockPos pPos, BasaltDeltasFeature config, boolean checkOpt) {
        if (pLevel.getBlockState(pPos).is(BPBlocks.CHORUS_MYCHRODEGIA.get()) || pLevel.getBlockState(pPos).is(BPBlocks.CHORUS_MYCHRODEGIA_PART.get())) {
            return;
        }

        if (checkOpt) {
            if (isClearOptimized(pLevel, pPos)) {
                if (pLevel.getBlockState(pPos.above()).is(BPBlocks.ENREDE_KELP.get()) || pLevel.getBlockState(pPos.above()).is(BPBlocks.ENREDE_KELP_PLANT.get())) {
                    this.setBlock(pLevel, pPos, BPBlocks.ENREDE_KELP_PLANT.get().defaultBlockState());
                } else {
                    this.setBlock(pLevel, pPos, config.contents());
                }
            }
        } else {
            if (pLevel.getBlockState(pPos.above()).is(BPBlocks.ENREDE_KELP.get()) || pLevel.getBlockState(pPos.above()).is(BPBlocks.ENREDE_KELP_PLANT.get())) {
                this.setBlock(pLevel, pPos, BPBlocks.ENREDE_KELP_PLANT.get().defaultBlockState());
            } else {
                this.setBlock(pLevel, pPos, config.contents());
            }
        }
    }

    public void carveOpenings(ILevel pLevel, BlockPos pPos, BasaltDeltasFeature config) {
        if (pLevel.getBlockState(pPos).is(BPBlocks.CHORUS_MYCHRODEGIA.get()) || pLevel.getBlockState(pPos).is(BPBlocks.CHORUS_MYCHRODEGIA_PART.get())) {
            return;
        }

        for (int sy = -7; sy <= -1; sy++) {
            BlockPos.MutableBlockPos tPos = pPos.offset(0, sy, 0).mutable();
            if (isClearOptimized(pLevel, tPos)) {
                if (pLevel.getBlockState(tPos.above()).is(BPBlocks.ENREDE_KELP.get()) || pLevel.getBlockState(tPos.above()).is(BPBlocks.ENREDE_KELP_PLANT.get())) {
                    this.setBlock(pLevel, tPos, BPBlocks.ENREDE_KELP_PLANT.get().defaultBlockState());
                } else {
                    this.setBlock(pLevel, tPos, config.contents());
                }
            }
        }
    }

    private static boolean isClearer(ILevel pLevel, BlockPos pPos, BasaltDeltasFeature pConfig) {
        BlockState blockstate = pLevel.getBlockState(pPos);
        if (blockstate.is(pConfig.contents().getBlock())) {
            return false;
        } else if (CANNOT_REPLACE.contains(blockstate.getBlock())) {
            return false;
        } else {
            for (Direction direction : DIRECTIONS) {
                boolean flag = pLevel.getBlockState(pPos.relative(direction)).isAir();
                if (flag && direction != Direction.UP || !flag && direction == Direction.UP) {
                    return false;
                }
            }

            return true;
        }
    }

    private static boolean isClear(ILevel pLevel, BlockPos pPos, BasaltDeltasFeature pConfig) {
        BlockState blockstate = pLevel.getBlockState(pPos);
        if (CANNOT_REPLACE.contains(blockstate.getBlock())) {
            return false;
        } else {
            for(Direction direction : DIRECTIONS) {
                boolean flag = pLevel.isEmptyBlock(pPos.relative(direction));
                if (flag && direction != Direction.UP) {
                    return false;
                }
            }

            return true;
        }
    }

    private static boolean isClearOptimized(ILevel world, BlockPos pos) {
        boolean flag = world.isEmptyBlock(pos.east()) || world.isEmptyBlock(pos.west()) || world.isEmptyBlock(pos.south()) || world.isEmptyBlock(pos.north()) || world.isEmptyBlock(pos.below());
        return !flag;
    }
}
