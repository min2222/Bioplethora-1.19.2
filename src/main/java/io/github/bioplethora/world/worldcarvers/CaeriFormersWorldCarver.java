package io.github.bioplethora.world.worldcarvers;

import java.util.Random;
import java.util.function.Function;

import org.apache.commons.lang3.mutable.MutableBoolean;

import com.mojang.serialization.Codec;

import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.CarvingMask;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.Aquifer;
import net.minecraft.world.level.levelgen.carver.CarvingContext;
import net.minecraft.world.level.levelgen.carver.CaveCarverConfiguration;
import net.minecraft.world.level.levelgen.carver.CaveWorldCarver;

public class CaeriFormersWorldCarver extends CaveWorldCarver {
    private static final Direction[] DIRECTIONS = Direction.values();

    public CaeriFormersWorldCarver(Codec<CaveCarverConfiguration> p_i231921_1_) {
        super(p_i231921_1_);
    }

    protected int getCaveBound() {
        return 10;
    }

    protected float getThickness(Random pRandom) {
        return (pRandom.nextFloat() * 2.0F + pRandom.nextFloat()) * 2.0F;
    }

    protected double getYScale() {
        return 5.0D;
    }

    /*protected int getCaveY(Random p_230361_1_) {
        return p_230361_1_.nextInt(this.genHeight);
    }*/

    @Override
    protected boolean carveBlock(CarvingContext pContext, CaveCarverConfiguration pConfig, ChunkAccess pChunk, Function<BlockPos, Holder<Biome>> pBiomeGetter, CarvingMask pCarvingMask, MutableBlockPos pPos, MutableBlockPos pCheckPos, Aquifer pAquifer, MutableBoolean pReachedSurface) {
    	if (this.canReplaceBlock(pConfig, pChunk.getBlockState(pPos))) {
            BlockState blockstate;
            if (pPos.getY() <= pContext.getMinGenY() + 31) {
                blockstate = Blocks.END_STONE.defaultBlockState();
            } else {
                blockstate = CAVE_AIR;
            }

            pChunk.setBlockState(pPos, blockstate, false);
            return true;
        } else {
        	return false;
        }
    }
}
