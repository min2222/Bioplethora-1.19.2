package io.github.bioplethora.world.blockplacers;

import java.util.Random;

import com.mojang.serialization.Codec;

import io.github.bioplethora.api.world.BlockUtils;
import io.github.bioplethora.registry.worldgen.BPBlockPlacers;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.ILevel;
import net.minecraft.world.gen.blockplacer.BlockPlacer;
import net.minecraft.world.gen.blockplacer.BlockPlacerType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class LavaEdgeBlockPlacer extends BlockPlacer {
    public static final LavaEdgeBlockPlacer INSTANCE = new LavaEdgeBlockPlacer();
    public static final Codec<LavaEdgeBlockPlacer> CODEC = Codec.unit(() -> INSTANCE);

    @Override
    public void place(ILevel pLevel, BlockPos pPos, BlockState pState, Random pRandom) {

        if (BlockUtils.checkNearestTaggedFluid(checkForLiquid(pPos), pLevel, FluidTags.LAVA)) {
            pLevel.setBlock(pPos, pState, 2);
        }
    }

    public AABB checkForLiquid(BlockPos pPos) {
        int areaint = 14;
        double x = pPos.getX(), y = pPos.getY(), z = pPos.getZ();
        return new AABB(
                x - (areaint / 2d), y - (areaint / 2d), z - (areaint / 2d),
                x + (areaint / 2d), y + (areaint / 2d), z + (areaint / 2d)
        );
    }

    @Override
    protected BlockPlacerType<?> type() {
        return BPBlockPlacers.LAVA_EDGE_PLACER.get();
    }
}
