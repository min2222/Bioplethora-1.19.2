package io.github.bioplethora.blocks.tile_entities;

import javax.annotation.Nullable;

import io.github.bioplethora.blocks.BPFlatBlock;
import io.github.bioplethora.registry.BPBlocks;
import io.github.bioplethora.registry.BPParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class FleignariteSplotchBlock extends BPFlatBlock implements SimpleWaterloggedBlock, EntityBlock {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    protected static final VoxelShape SHAPE = Shapes.or(
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 1.0D, 16.0D),
            Block.box(4.0D, 1.0D, 5.0D, 12.0D, 6.0D, 13.0D),
            Block.box(8.0D, 1.0D, 2.0D, 14.0D, 4.0D, 9.0D),
            Block.box(1.0D, 1.0D, 10.0D, 5.0D, 3.0D, 14.0D),
            Block.box(2.0D, 1.0D, 3.0D, 6.0D, 5.0D, 7.0D)
    );

    public FleignariteSplotchBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED, false));
    }

    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        return !isFree(pLevel.getBlockState(pPos.below()));
    }

    public static boolean isFree(BlockState pState) {
        return pState.isAir() || pState.is(BlockTags.FIRE) || pState.liquid() || pState.canBeReplaced() || pState.getBlock() instanceof BPFlatBlock;
    }

    @Override
    public void entityInside(BlockState pState, Level pLevel, BlockPos pPos, Entity pEntity) {
        super.entityInside(pState, pLevel, pPos, pEntity);

        if (Math.random() < 0.1) {
            pLevel.addParticle(BPParticles.ANTIBIO_SPELL.get(), pPos.getX() + pLevel.random.nextDouble(),
                    pPos.getY() + 0.5D, pPos.getZ() + pLevel.random.nextDouble(),
                    0d, 0.05d, 0d);
        }

        if (pEntity instanceof LivingEntity) {
            pEntity.playSound(SoundEvents.SLIME_BLOCK_BREAK, 1.0F, 1.0F);
            ((LivingEntity) pEntity).addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 1, false, false, false));
            ((LivingEntity) pEntity).addEffect(new MobEffectInstance(MobEffects.CONFUSION, 60, 0, false, false, false));
            pLevel.destroyBlock(pPos, false);
            pLevel.setBlock(pPos, BPBlocks.FLEIGNARITE_REMAINS.get().defaultBlockState(), 2);
        }
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new FleignariteSplotchTileEntity(pos, state);
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        boolean flag = pContext.getLevel().getFluidState(pContext.getClickedPos()).getType() == Fluids.WATER;
        return this.defaultBlockState().setValue(WATERLOGGED, flag);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(WATERLOGGED);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        if (pState.getValue(WATERLOGGED)) {
            pLevel.scheduleTick(pCurrentPos, Fluids.WATER, Fluids.WATER.getTickDelay(pLevel));
        }

        return super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
    }
}
