package io.github.bioplethora.blocks;

import io.github.bioplethora.registry.BPBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.NetherVines;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public abstract class BPFruitableVinesTopBlock extends BPVinesTopBlock {
    public BPFruitableVinesTopBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    protected boolean canGrowInto(BlockState pState) {
        return NetherVines.isValidGrowthState(pState);
    }

    protected int getBlocksToGrowWhenBonemealed(RandomSource pRandom) {
        return NetherVines.getBlocksToGrowWhenBonemealed(pRandom);
    }

    public abstract Block getFruitedBodyBlock();

    @Override
    public boolean canSurvive(BlockState state, LevelReader reader, BlockPos pos) {
        BlockPos blockpos = pos.relative(this.growthDirection.getOpposite());
        BlockState blockstate = reader.getBlockState(blockpos);
        Block block = blockstate.getBlock();
        if (!this.canAttachTo(blockstate)) {
            return false;
        } else {
            return block == this.getHeadBlock() || block == this.getFruitedBodyBlock() || block == this.getBodyBlock() || blockstate.isFaceSturdy(reader, blockpos, this.growthDirection);
        }
    }

    public static class BasaltSpeleothermTopBlock extends BPFruitableVinesTopBlock {

        public BasaltSpeleothermTopBlock(BlockBehaviour.Properties properties) {
            super(properties);
        }

        @Override
        public Block getFruitedBodyBlock() {
            return BPBlocks.FIERY_BASALT_SPELEOTHERM.get();
        }

        @Override
        protected Block getBodyBlock() {
            return BPBlocks.BASALT_SPELEOTHERM_PLANT.get();
        }
    }

    public static class ThontusThistleTopBlock extends BPFruitableVinesTopBlock {

        public ThontusThistleTopBlock(BlockBehaviour.Properties properties) {
            super(properties);
        }

        @Override
        public Block getFruitedBodyBlock() {
            return BPBlocks.BERRIED_THONTUS_THISTLE.get();
        }

        @Override
        protected Block getBodyBlock() {
            return BPBlocks.THONTUS_THISTLE_PLANT.get();
        }
    }

    public static class TurquoisePendentTopBlock extends BPFruitableVinesTopBlock {

        public TurquoisePendentTopBlock(BlockBehaviour.Properties properties) {
            super(properties);
        }

        @Override
        public Block getFruitedBodyBlock() {
            return BPBlocks.BLOSSOMING_TURQUOISE_PENDENT.get();
        }

        @Override
        protected Block getBodyBlock() {
            return BPBlocks.TURQUOISE_PENDENT_PLANT.get();
        }
    }

    public static class CeriseIvyTopBlock extends BPFruitableVinesTopBlock {

        public CeriseIvyTopBlock(BlockBehaviour.Properties properties) {
            super(properties);
        }

        @Override
        public Block getFruitedBodyBlock() {
            return BPBlocks.SEEDED_CERISE_IVY.get();
        }

        @Override
        protected Block getBodyBlock() {
            return BPBlocks.CERISE_IVY_PLANT.get();
        }
    }

    public static class SoulEternTopBlock extends BPFruitableVinesTopBlock {

        public SoulEternTopBlock(BlockBehaviour.Properties properties) {
            super(properties);
        }

        @Override
        public Block getFruitedBodyBlock() {
            return BPBlocks.FLOURISHED_SOUL_ETERN.get();
        }

        @Override
        protected Block getBodyBlock() {
            return BPBlocks.SOUL_ETERN_PLANT.get();
        }
    }
}
