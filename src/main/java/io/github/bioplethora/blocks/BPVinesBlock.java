package io.github.bioplethora.blocks;

import io.github.bioplethora.registry.BPBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GrowingPlantBodyBlock;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;

public abstract class BPVinesBlock extends GrowingPlantBodyBlock {
    public static final VoxelShape SHAPE = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 16.0D, 15.0D);

    protected BPVinesBlock(BlockBehaviour.Properties properties) {
        super(properties, Direction.DOWN, SHAPE, true);
    }

    protected BPVinesBlock(BlockBehaviour.Properties properties, Direction direction) {
        super(properties, direction, SHAPE, true);
    }

    public boolean canSurviveOnLeafUtil(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        BlockPos blockpos = pPos.relative(this.growthDirection.getOpposite());
        BlockState blockstate = pLevel.getBlockState(blockpos);
        Block block = blockstate.getBlock();
        if (!this.canAttachTo(blockstate)) {
            return false;
        } else {
            return block == this.getBodyBlock() || block instanceof LeavesBlock || blockstate.isFaceSturdy(pLevel, blockpos, this.growthDirection);
        }
    }

    public static class PinkTwiBlock extends BPVinesBlock {
        public PinkTwiBlock(Properties properties) {
            super(properties);
        }
        @Override
        protected GrowingPlantHeadBlock getHeadBlock() {
            return (GrowingPlantHeadBlock) BPBlocks.PINK_TWI.get();
        }
        @Override
        public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
            return canSurviveOnLeafUtil(pState, pLevel, pPos);
        }
    }
    public static class RedTwiBlock extends BPVinesBlock {
        public RedTwiBlock(Properties properties) {
            super(properties);
        }
        @Override
        protected GrowingPlantHeadBlock getHeadBlock() {
            return (GrowingPlantHeadBlock) BPBlocks.RED_TWI.get();
        }
        @Override
        public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
            return canSurviveOnLeafUtil(pState, pLevel, pPos);
        }
    }
    public static class SpiritDanglerBlock extends BPVinesBlock {
        public SpiritDanglerBlock(Properties properties) {
            super(properties);
        }
        @Override
        protected GrowingPlantHeadBlock getHeadBlock() {
            return (GrowingPlantHeadBlock) BPBlocks.SPIRIT_DANGLER.get();
        }
    }

    public static class SpinxelthornBlock extends BPVinesBlock {
        public SpinxelthornBlock(Properties properties) {
            super(properties);
        }
        @Override
        protected GrowingPlantHeadBlock getHeadBlock() {
            return (GrowingPlantHeadBlock) BPBlocks.SPINXELTHORN.get();
        }
    }
    public static class GlacynthBlock extends BPVinesBlock {
        public GlacynthBlock(Properties properties) {
            super(properties);
        }
        @Override
        protected GrowingPlantHeadBlock getHeadBlock() {
            return (GrowingPlantHeadBlock) BPBlocks.GLACYNTH.get();
        }
        @Override
        public void entityInside(BlockState p_196262_1_, Level p_196262_2_, BlockPos p_196262_3_, Entity p_196262_4_) {
            super.entityInside(p_196262_1_, p_196262_2_, p_196262_3_, p_196262_4_);
            p_196262_2_.destroyBlock(p_196262_3_, false);
        }
    }
    public static class CelestiaBudBlock extends BPVinesBlock {
        public CelestiaBudBlock(Properties properties) {
            super(properties, Direction.UP);
        }
        @Override
        protected GrowingPlantHeadBlock getHeadBlock() {
            return (GrowingPlantHeadBlock) BPBlocks.CELESTIA_BUD.get();
        }
        @Override
        public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
            return canSurviveOnLeafUtil(pState, pLevel, pPos);
        }
    }
}
