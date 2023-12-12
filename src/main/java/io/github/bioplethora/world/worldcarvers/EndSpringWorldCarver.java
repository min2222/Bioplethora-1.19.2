package io.github.bioplethora.world.worldcarvers;

import java.util.BitSet;
import java.util.Random;
import java.util.function.Function;

import org.apache.commons.lang3.mutable.MutableBoolean;

import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Codec;

import io.github.bioplethora.registry.BPBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.carver.CaveCarverConfiguration;
import net.minecraft.world.level.levelgen.carver.CaveWorldCarver;

public class EndSpringLevelCarver extends CaveWorldCarver {
    private static final Direction[] DIRECTIONS = Direction.values();

    public EndSpringLevelCarver(Codec<CaveCarverConfiguration> p_i231921_1_) {
        super(p_i231921_1_);
        this.replaceableBlocks = ImmutableSet.of(Blocks.WATER);
    }

    @Override
    protected double getYScale() {
        return 10D;
    }

    protected int getCaveBound() {
        return 20;
    }

    protected int getCaveY(Random p_230361_1_) {
        return p_230361_1_.nextInt(p_230361_1_.nextInt(60) + 8);
    }

    protected float getThickness(Random pRandom) {
        return (pRandom.nextFloat() * 4.0F + pRandom.nextFloat()) * 4.0F;
    }

    protected boolean carveBlock(IChunk level, Function<BlockPos, Biome> pred, BitSet bitset, Random rand, BlockPos.MutableBlockPos pos, BlockPos.MutableBlockPos pos2, BlockPos.MutableBlockPos pos3, int p_230358_8_, int p_230358_9_, int p_230358_10_, int p_230358_11_, int p_230358_12_, int p_230358_13_, int p_230358_14_, int p_230358_15_, MutableBoolean p_230358_16_) {
        int i = p_230358_13_ | p_230358_15_ << 4 | p_230358_14_ << 8;
        if (bitset.get(i)) {
            return false;
        } else {
            bitset.set(i);
            pos.set(p_230358_11_, p_230358_14_, p_230358_12_);
            if (this.canReplaceBlock(level.getBlockState(pos))) {
                BlockState blockstate = BPBlocks.ENDURION.get().defaultBlockState();

                if (!level.getBlockState(pos).isAir()) {
                    level.setBlockState(pos, blockstate, false);
                }
                return true;
            } else {
                return false;
            }
        }
    }
}
