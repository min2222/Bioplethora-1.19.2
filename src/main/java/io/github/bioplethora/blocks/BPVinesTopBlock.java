package io.github.bioplethora.blocks;

import io.github.bioplethora.registry.BPBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.NetherVines;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;

public abstract class BPVinesTopBlock extends GrowingPlantHeadBlock {
    protected static final VoxelShape SHAPE = Block.box(4.0D, 9.0D, 4.0D, 12.0D, 16.0D, 12.0D);

    public BPVinesTopBlock(BlockBehaviour.Properties properties) {
        this(properties, Direction.DOWN);
    }

    protected BPVinesTopBlock(BlockBehaviour.Properties properties, Direction direction) {
        this(properties, direction, SHAPE);
    }

    protected BPVinesTopBlock(BlockBehaviour.Properties properties, Direction direction, VoxelShape shape) {
        super(properties, direction, shape, true, 0.1D);
    }

    protected boolean canGrowInto(BlockState pState) {
        return NetherVines.isValidGrowthState(pState);
    }

    protected int getBlocksToGrowWhenBonemealed(RandomSource pRandom) {
        return NetherVines.getBlocksToGrowWhenBonemealed(pRandom);
    }

    public boolean canSurviveOnLeafUtil(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        BlockPos blockpos = pPos.relative(this.growthDirection.getOpposite());
        BlockState blockstate = pLevel.getBlockState(blockpos);
        Block block = blockstate.getBlock();
        if (!this.canAttachTo(blockstate)) {
            return false;
        } else {
            return block == this.getHeadBlock() || block == this.getBodyBlock() || block instanceof LeavesBlock || blockstate.isFaceSturdy(pLevel, blockpos, this.growthDirection);
        }
    }

    public static class PinkTwiTopBlock extends BPVinesTopBlock {
        public PinkTwiTopBlock(Properties properties) {
            super(properties);
        }
        @Override
        protected Block getBodyBlock() {
            return BPBlocks.PINK_TWI_PLANT.get();
        }
        @Override
        public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
            return canSurviveOnLeafUtil(pState, pLevel, pPos);
        }
    }
    public static class RedTwiTopBlock extends BPVinesTopBlock {
        public RedTwiTopBlock(Properties properties) {
            super(properties);
        }
        @Override
        protected Block getBodyBlock() {
            return BPBlocks.RED_TWI_PLANT.get();
        }
        @Override
        public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
            return canSurviveOnLeafUtil(pState, pLevel, pPos);
        }
    }
    public static class SpiritDanglerTopBlock extends BPVinesTopBlock {
        public SpiritDanglerTopBlock(Properties properties) {
            super(properties);
        }
        @Override
        protected Block getBodyBlock() {
            return BPBlocks.SPIRIT_DANGLER_PLANT.get();
        }
    }

    public static class SpinxelthornTopBlock extends BPVinesTopBlock {
        public SpinxelthornTopBlock(Properties properties) {
            super(properties);
        }
        @Override
        protected Block getBodyBlock() {
            return BPBlocks.SPINXELTHORN_PLANT.get();
        }
    }
    public static class GlacynthTopBlock extends BPVinesTopBlock {
        public GlacynthTopBlock(Properties properties) {
            super(properties);
        }
        @Override
        protected Block getBodyBlock() {
            return BPBlocks.GLACYNTH_PLANT.get();
        }
        @Override
        public void entityInside(BlockState p_196262_1_, Level p_196262_2_, BlockPos p_196262_3_, Entity p_196262_4_) {
            super.entityInside(p_196262_1_, p_196262_2_, p_196262_3_, p_196262_4_);
            p_196262_2_.destroyBlock(p_196262_3_, false);
        }
    }
    public static class CelestiaBudTopBlock extends BPVinesTopBlock {
        public CelestiaBudTopBlock(Properties properties) {
            super(properties, Direction.UP, Block.box(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D));
        }
        @Override
        protected Block getBodyBlock() {
            return BPBlocks.CELESTIA_BUD_PLANT.get();
        }
    }
}
