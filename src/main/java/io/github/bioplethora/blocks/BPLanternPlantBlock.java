package io.github.bioplethora.blocks;

import javax.annotation.Nullable;

import io.github.bioplethora.enums.BioPlantShape;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.PlantType;
import net.minecraftforge.registries.RegistryObject;

public class BPLanternPlantBlock extends DoublePlantBlock {

    Block fruit;
    public BioPlantShape shape;

    public BPLanternPlantBlock(Block fruit, BioPlantShape shape, BlockBehaviour.Properties properties) {
        super(properties.offsetType(OffsetType.NONE));
        this.fruit = fruit;
        this.shape = shape;
        this.registerDefaultState(this.stateDefinition.any().setValue(HALF, DoubleBlockHalf.UPPER));
    }

    public BPLanternPlantBlock(RegistryObject<Block> fruit, BioPlantShape shape, BlockBehaviour.Properties properties) {
        this(fruit.get(), shape, properties);
    }

    @Override
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        BlockPos blockpos = pPos.above();
        BlockState groundState = pLevel.getBlockState(blockpos);
        if (pState.getValue(HALF) == DoubleBlockHalf.LOWER) {
            if (pState.getBlock() != this) return surviveCondition(pState, pLevel, pPos);
            return groundState.is(this) && groundState.getValue(HALF) == DoubleBlockHalf.UPPER;
        } else {
            if (pState.getBlock() == this) return surviveCondition(pState, pLevel, pPos);
            return this.mayPlaceOn(groundState, pLevel, blockpos);
        }
    }

    @Override
    public PlantType getPlantType(BlockGetter world, BlockPos pos) {
        return PlantType.NETHER;
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter reader, BlockPos pos) {
        return state.getBlock() == fruit;
    }

    public boolean surviveCondition(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        BlockPos blockpos = pPos.above();
        if (pState.getBlock() == this)
            return pLevel.getBlockState(blockpos).getBlock() == fruit;
        return this.mayPlaceOn(pLevel.getBlockState(blockpos), pLevel, blockpos);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        BlockPos blockpos = pContext.getClickedPos();
        return blockpos.getY() < 255 &&
                pContext.getLevel().getBlockState(blockpos.below()).canBeReplaced(pContext) ?
                this.defaultBlockState() : null;
    }

    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, LivingEntity pPlacer, ItemStack pStack) {
        pLevel.setBlock(pPos.below(), this.defaultBlockState().setValue(HALF, DoubleBlockHalf.LOWER), 3);
    }
    
    //TODO static method can not be overriden
    //@Override
    public void placeAt(LevelAccessor p_196390_1_, BlockPos p_196390_2_, int p_196390_3_) {
        p_196390_1_.setBlock(p_196390_2_, this.defaultBlockState().setValue(HALF, DoubleBlockHalf.LOWER), p_196390_3_);
        p_196390_1_.setBlock(p_196390_2_.above(), this.defaultBlockState().setValue(HALF, DoubleBlockHalf.UPPER), p_196390_3_);
        p_196390_1_.setBlock(p_196390_2_.above().above(), this.fruit.defaultBlockState(), p_196390_3_);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return getShape();
    }

    public VoxelShape getShape() {
        return shape.getShape();
    }

    @OnlyIn(Dist.CLIENT)
    public long getSeed(BlockState pState, BlockPos pPos) {
        return Mth.getSeed(pPos.getX(), pPos.below(pState.getValue(HALF) == DoubleBlockHalf.UPPER ? 0 : 1).getY(), pPos.getZ());
    }

    public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        DoubleBlockHalf doubleblockhalf = pState.getValue(HALF);
            if (pFacing.getAxis() != Direction.Axis.Y || doubleblockhalf == DoubleBlockHalf.UPPER != (pFacing == Direction.DOWN) || pFacingState.is(this) && pFacingState.getValue(HALF) != doubleblockhalf) {
            return doubleblockhalf == DoubleBlockHalf.UPPER && pFacing == Direction.UP && !pState.canSurvive(pLevel, pCurrentPos) ? Blocks.AIR.defaultBlockState() : updS1(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
        } else {
            return Blocks.AIR.defaultBlockState();
        }
    }

    public BlockState updS1(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        return !pState.canSurvive(pLevel, pCurrentPos) ? Blocks.AIR.defaultBlockState() : pState;
    }

    public void playerWillDestroy(Level pLevel, BlockPos pPos, BlockState pState, Player pPlayer) {
        if (!pLevel.isClientSide) {
            if (pPlayer.isCreative()) {
                preventCreativeDropFromBottomPart(pLevel, pPos, pState, pPlayer);
            } else {
                dropResources(pState, pLevel, pPos, null, pPlayer, pPlayer.getMainHandItem());
            }
        }

        pLevel.levelEvent(pPlayer, 2001, pPos, getId(pState));
        if (this.defaultBlockState().is(BlockTags.GUARDED_BY_PIGLINS)) {
            PiglinAi.angerNearbyPiglins(pPlayer, false);
        }
    }

    protected static void preventCreativeDropFromBottomPart(Level pLevel, BlockPos pPos, BlockState pState, Player pPlayer) {
        DoubleBlockHalf doubleblockhalf = pState.getValue(HALF);
        if (doubleblockhalf == DoubleBlockHalf.LOWER) {
            BlockPos blockpos = pPos.above();
            BlockState blockstate = pLevel.getBlockState(blockpos);
            if (blockstate.getBlock() == pState.getBlock() && blockstate.getValue(HALF) == DoubleBlockHalf.UPPER) {
                pLevel.setBlock(blockpos, Blocks.AIR.defaultBlockState(), 35);
                pLevel.levelEvent(pPlayer, 2001, blockpos, Block.getId(blockstate));
            }
        }

    }
}
