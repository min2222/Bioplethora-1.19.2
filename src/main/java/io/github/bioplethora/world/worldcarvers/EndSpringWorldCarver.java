package io.github.bioplethora.world.worldcarvers;

import java.util.Random;
import java.util.function.Function;

import org.apache.commons.lang3.mutable.MutableBoolean;

import com.mojang.serialization.Codec;

import io.github.bioplethora.registry.BPBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.CarvingMask;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.Aquifer;
import net.minecraft.world.level.levelgen.carver.CarvingContext;
import net.minecraft.world.level.levelgen.carver.CaveCarverConfiguration;
import net.minecraft.world.level.levelgen.carver.CaveWorldCarver;

public class EndSpringWorldCarver extends CaveWorldCarver {
    private static final Direction[] DIRECTIONS = Direction.values();

    public EndSpringWorldCarver(Codec<CaveCarverConfiguration> p_i231921_1_) {
        super(p_i231921_1_);
        //Handled in tag definition
        //this.replaceableBlocks = ImmutableSet.of(Blocks.WATER);
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
    
    @Override
    protected boolean carveBlock(CarvingContext pContext, CaveCarverConfiguration pConfig, ChunkAccess pChunk, Function<BlockPos, Holder<Biome>> pBiomeGetter, CarvingMask pCarvingMask, MutableBlockPos pPos, MutableBlockPos pCheckPos, Aquifer pAquifer, MutableBoolean pReachedSurface) {
        if (this.canReplaceBlock(pConfig, pChunk.getBlockState(pPos))) {
            BlockState blockstate = BPBlocks.ENDURION.get().defaultBlockState();

            if (!pChunk.getBlockState(pPos).isAir()) {
            	pChunk.setBlockState(pPos, blockstate, false);
            }
            return true;
        } else {
            return false;
        }
    }
}
