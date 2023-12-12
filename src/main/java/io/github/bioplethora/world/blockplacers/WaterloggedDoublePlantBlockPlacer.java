package io.github.bioplethora.world.blockplacers;

import static net.minecraft.block.DoublePlantBlock.HALF;
import static net.minecraft.state.properties.BlockStateProperties.WATERLOGGED;

import java.util.Random;

import com.mojang.serialization.Codec;

import io.github.bioplethora.registry.worldgen.BPBlockPlacers;
import net.minecraft.core.BlockPos;
import net.minecraft.world.ILevel;
import net.minecraft.world.gen.blockplacer.BlockPlacer;
import net.minecraft.world.gen.blockplacer.BlockPlacerType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;

public class WaterloggedDoublePlantBlockPlacer extends BlockPlacer {
    public static final WaterloggedDoublePlantBlockPlacer INSTANCE = new WaterloggedDoublePlantBlockPlacer();
    public static final Codec<WaterloggedDoublePlantBlockPlacer> CODEC = Codec.unit(() -> INSTANCE);

    @Override
    public void place(ILevel pLevel, BlockPos pPos, BlockState pState, Random pRandom) {
        if (pLevel.isWaterAt(pPos) && pLevel.isWaterAt(pPos.above())) {
            pLevel.setBlock(pPos, pState.setValue(HALF, DoubleBlockHalf.LOWER).setValue(WATERLOGGED, true), 2);
            pLevel.setBlock(pPos.above(), pState.setValue(HALF, DoubleBlockHalf.UPPER).setValue(WATERLOGGED, true), 2);
        }
    }

    @Override
    protected BlockPlacerType<?> type() {
        return BPBlockPlacers.WATERLOGGED_DOUBLE_PLANT.get();
    }
}
