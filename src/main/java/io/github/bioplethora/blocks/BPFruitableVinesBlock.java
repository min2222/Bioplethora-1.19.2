package io.github.bioplethora.blocks;

import java.util.Optional;

import io.github.bioplethora.registry.BPBlocks;
import io.github.bioplethora.registry.BPItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;

public abstract class BPFruitableVinesBlock extends BPVinesBlock {

    public BPFruitableVinesBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    public abstract Block getFruitedBodyBlock();

    public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        if (pFacing == this.growthDirection.getOpposite() && !pState.canSurvive(pLevel, pCurrentPos)) {
            pLevel.scheduleTick(pCurrentPos, this, 1);
        }
        GrowingPlantHeadBlock GrowingPlantHeadBlock = this.getHeadBlock();
        if (pFacing == this.growthDirection) {
            Block block = pFacingState.getBlock();
            if (block != getBodyBlock() && block != getFruitedBodyBlock() && block != GrowingPlantHeadBlock) {
                return GrowingPlantHeadBlock.getStateForPlacement(pLevel);
            }
        }
        if (this.scheduleFluidTicks) {
            pLevel.scheduleTick(pCurrentPos, Fluids.WATER, Fluids.WATER.getTickDelay(pLevel));
        }
        return pState;
    }

    @Override
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        BlockPos blockpos = pPos.relative(this.growthDirection.getOpposite());
        BlockState blockstate = pLevel.getBlockState(blockpos);
        Block block = blockstate.getBlock();
        if (!this.canAttachTo(blockstate)) {
            return false;
        } else {
            return block == this.getHeadBlock() || block == this.getFruitedBodyBlock() || block == this.getBodyBlock() || blockstate.isFaceSturdy(pLevel, blockpos, this.growthDirection);
        }
    }

    @Override
    public void performBonemeal(ServerLevel world, RandomSource random, BlockPos pos, BlockState state) {
        world.setBlock(pos, getFruitedBodyBlock().defaultBlockState(), 2);
        super.performBonemeal(world, random, pos, state);
    }

    @Override
    public Optional<BlockPos> getHeadPos(BlockGetter p_235501_1_, BlockPos p_235501_2_, Block p_235501_3_) {
        BlockPos blockpos = p_235501_2_;

        Block block;
        do {
            blockpos = blockpos.relative(this.growthDirection);
            block = p_235501_1_.getBlockState(blockpos).getBlock();
        } while(block == p_235501_3_ || block == getFruitedBodyBlock());

        return block == this.getHeadBlock() ? Optional.of(blockpos) : Optional.empty();
    }

    public static class BasaltSpeleothermBlock extends BPFruitableVinesBlock {

        public BasaltSpeleothermBlock(BlockBehaviour.Properties properties) {
            super(properties);
        }

        @Override
        protected GrowingPlantHeadBlock getHeadBlock() {
            return (GrowingPlantHeadBlock) BPBlocks.BASALT_SPELEOTHERM.get();
        }

        @Override
        public Block getFruitedBodyBlock() {
            return BPBlocks.FIERY_BASALT_SPELEOTHERM.get();
        }
    }
    public static class FieryBasaltSpeleothermBlock extends BPFruitableVinesBlock {

        public FieryBasaltSpeleothermBlock(BlockBehaviour.Properties properties) {
            super(properties);
        }

        @Override
        protected GrowingPlantHeadBlock getHeadBlock() {
            return (GrowingPlantHeadBlock) BPBlocks.BASALT_SPELEOTHERM.get();
        }

        @Override
        protected Block getBodyBlock() {
            return BPBlocks.BASALT_SPELEOTHERM_PLANT.get();
        }

        @Override
        public Block getFruitedBodyBlock() {
            return BPBlocks.FIERY_BASALT_SPELEOTHERM.get();
        }

        @Override
        public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {

            boolean flag = false;
            if (pPlayer.getItemInHand(pHand).getItem() == Items.GLASS_BOTTLE) {

                pLevel.setBlock(pPos, getBodyBlock().defaultBlockState(), 2);
                pPlayer.getItemInHand(pHand).shrink(1);
                pLevel.playSound(null, pPos, SoundEvents.BREWING_STAND_BREW, SoundSource.BLOCKS, 1.0F, 0.8F + pLevel.random.nextFloat() * 0.4F);

                if (pPlayer.getItemInHand(pHand).isEmpty()) {
                    pPlayer.setItemInHand(pHand, new ItemStack(BPItems.FIERY_SAP_BOTTLE.get()));

                } else if (!pPlayer.getInventory().add(new ItemStack(BPItems.FIERY_SAP_BOTTLE.get()))) {
                    pPlayer.drop(new ItemStack(BPItems.FIERY_SAP_BOTTLE.get()), false);
                }

                flag = true;
            }
            if (flag) {
                return InteractionResult.sidedSuccess(pLevel.isClientSide);

            } else {
                return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
            }
        }
    }

    public static class ThontusThistleBlock extends BPFruitableVinesBlock {

        public ThontusThistleBlock(BlockBehaviour.Properties properties) {
            super(properties);
        }

        @Override
        protected GrowingPlantHeadBlock getHeadBlock() {
            return (GrowingPlantHeadBlock) BPBlocks.THONTUS_THISTLE.get();
        }

        @Override
        public Block getFruitedBodyBlock() {
            return BPBlocks.BERRIED_THONTUS_THISTLE.get();
        }

        @Override
        public void entityInside(BlockState p_196262_1_, Level p_196262_2_, BlockPos p_196262_3_, Entity entity) {
            super.entityInside(p_196262_1_, p_196262_2_, p_196262_3_, entity);
            entity.hurt(entity.damageSources().cactus(), 1);
        }
    }
    public static class BerriedThontusThistleBlock extends BPFruitableVinesBlock {

        public BerriedThontusThistleBlock(BlockBehaviour.Properties properties) {
            super(properties);
        }

        @Override
        protected GrowingPlantHeadBlock getHeadBlock() {
            return (GrowingPlantHeadBlock) BPBlocks.THONTUS_THISTLE.get();
        }

        @Override
        protected Block getBodyBlock() {
            return BPBlocks.THONTUS_THISTLE_PLANT.get();
        }

        @Override
        public Block getFruitedBodyBlock() {
            return BPBlocks.BERRIED_THONTUS_THISTLE.get();
        }

        @Override
        public void entityInside(BlockState p_196262_1_, Level p_196262_2_, BlockPos p_196262_3_, Entity entity) {
            super.entityInside(p_196262_1_, p_196262_2_, p_196262_3_, entity);
            entity.hurt(entity.damageSources().cactus(), 1);
        }

        @Override
        public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
            int i = pLevel.random.nextBoolean() ? 2 : 3;
            pLevel.setBlock(pPos, getBodyBlock().defaultBlockState(), 2);
            pLevel.playSound(null, pPos, SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, SoundSource.BLOCKS, 1.0F, 0.8F + pLevel.random.nextFloat() * 0.4F);
            popResource(pLevel, pPos, new ItemStack(BPItems.THONTUS_BERRIES.get(), i));
            return InteractionResult.sidedSuccess(pLevel.isClientSide);
        }
    }

    public static class TurquoisePendentBlock extends BPFruitableVinesBlock {

        public TurquoisePendentBlock(BlockBehaviour.Properties properties) {
            super(properties);
        }

        @Override
        protected GrowingPlantHeadBlock getHeadBlock() {
            return (GrowingPlantHeadBlock) BPBlocks.TURQUOISE_PENDENT.get();
        }

        @Override
        public Block getFruitedBodyBlock() {
            return BPBlocks.BLOSSOMING_TURQUOISE_PENDENT.get();
        }
    }
    public static class BlossomingTurquoisePendentBlock extends BPFruitableVinesBlock {

        public BlossomingTurquoisePendentBlock(BlockBehaviour.Properties properties) {
            super(properties);
        }

        @Override
        protected GrowingPlantHeadBlock getHeadBlock() {
            return (GrowingPlantHeadBlock) BPBlocks.TURQUOISE_PENDENT.get();
        }

        @Override
        protected Block getBodyBlock() {
            return BPBlocks.TURQUOISE_PENDENT_PLANT.get();
        }

        @Override
        public Block getFruitedBodyBlock() {
            return BPBlocks.BLOSSOMING_TURQUOISE_PENDENT.get();
        }

        @Override
        public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
            int i = 2 + pLevel.random.nextInt(2);
            pLevel.setBlock(pPos, getBodyBlock().defaultBlockState(), 2);
            pLevel.playSound(null, pPos, SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, SoundSource.BLOCKS, 1.0F, 0.8F + pLevel.random.nextFloat() * 0.4F);
            popResource(pLevel, pPos, new ItemStack(BPItems.WARPED_GRAPES.get(), i));
            return InteractionResult.sidedSuccess(pLevel.isClientSide);
        }
    }

    public static class CeriseIvyBlock extends BPFruitableVinesBlock {

        public CeriseIvyBlock(BlockBehaviour.Properties properties) {
            super(properties);
        }

        @Override
        protected GrowingPlantHeadBlock getHeadBlock() {
            return (GrowingPlantHeadBlock) BPBlocks.CERISE_IVY.get();
        }

        @Override
        public Block getFruitedBodyBlock() {
            return BPBlocks.SEEDED_CERISE_IVY.get();
        }
    }
    public static class SeededCeriseIvyBlock extends BPFruitableVinesBlock {

        public SeededCeriseIvyBlock(BlockBehaviour.Properties properties) {
            super(properties);
        }

        @Override
        protected GrowingPlantHeadBlock getHeadBlock() {
            return (GrowingPlantHeadBlock) BPBlocks.CERISE_IVY.get();
        }

        @Override
        protected Block getBodyBlock() {
            return BPBlocks.CERISE_IVY_PLANT.get();
        }

        @Override
        public Block getFruitedBodyBlock() {
            return BPBlocks.SEEDED_CERISE_IVY.get();
        }

        @Override
        public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
            int i = 3 + pLevel.random.nextInt(5);
            pLevel.setBlock(pPos, getBodyBlock().defaultBlockState(), 2);
            pLevel.playSound(null, pPos, SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, SoundSource.BLOCKS, 1.0F, 0.8F + pLevel.random.nextFloat() * 0.4F);
            popResource(pLevel, pPos, new ItemStack(BPItems.CRIMSON_BITTERSWEET_SEEDS.get(), i));
            return InteractionResult.sidedSuccess(pLevel.isClientSide);
        }
    }

    public static class SoulEternBlock extends BPFruitableVinesBlock {

        public SoulEternBlock(BlockBehaviour.Properties properties) {
            super(properties);
        }

        @Override
        protected GrowingPlantHeadBlock getHeadBlock() {
            return (GrowingPlantHeadBlock) BPBlocks.SOUL_ETERN.get();
        }

        @Override
        public Block getFruitedBodyBlock() {
            return BPBlocks.FLOURISHED_SOUL_ETERN.get();
        }
    }
    public static class FlourishedSoulEternBlock extends BPFruitableVinesBlock {

        public FlourishedSoulEternBlock(BlockBehaviour.Properties properties) {
            super(properties);
        }

        @Override
        protected GrowingPlantHeadBlock getHeadBlock() {
            return (GrowingPlantHeadBlock) BPBlocks.SOUL_ETERN.get();
        }

        @Override
        protected Block getBodyBlock() {
            return BPBlocks.SOUL_ETERN_PLANT.get();
        }

        @Override
        public Block getFruitedBodyBlock() {
            return BPBlocks.FLOURISHED_SOUL_ETERN.get();
        }

        @Override
        public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {

            boolean flag = false;
            if (pPlayer.getItemInHand(pHand).getItem() == Items.GLASS_BOTTLE) {

                pLevel.setBlock(pPos, getBodyBlock().defaultBlockState(), 2);
                pPlayer.getItemInHand(pHand).shrink(1);
                pLevel.playSound(null, pPos, SoundEvents.BREWING_STAND_BREW, SoundSource.BLOCKS, 1.0F, 0.8F + pLevel.random.nextFloat() * 0.4F);

                if (pPlayer.getItemInHand(pHand).isEmpty()) {
                    pPlayer.setItemInHand(pHand, new ItemStack(BPItems.SOUL_SAP_BOTTLE.get()));

                } else if (!pPlayer.getInventory().add(new ItemStack(BPItems.SOUL_SAP_BOTTLE.get()))) {
                    pPlayer.drop(new ItemStack(BPItems.SOUL_SAP_BOTTLE.get()), false);
                }

                flag = true;
            }
            if (flag) {
                return InteractionResult.sidedSuccess(pLevel.isClientSide);

            } else {
                return InteractionResult.PASS;
            }
        }
    }
}
