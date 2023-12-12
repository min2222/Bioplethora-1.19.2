package io.github.bioplethora.blocks;

import com.google.common.collect.ImmutableList;

import io.github.bioplethora.enums.BioPlantShape;
import io.github.bioplethora.enums.BioPlantType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.PlantType;

public class BPDoublePlantBlock extends DoublePlantBlock {

    public BioPlantType type;
    public BioPlantShape shape;

    public BPDoublePlantBlock(BioPlantType type, BioPlantShape shape, BlockBehaviour.Properties properties) {
        super(properties);
        this.type = type;
        this.shape = shape;
    }

    @Override
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        BlockPos blockpos = pPos.below();
        BlockState groundState = pLevel.getBlockState(blockpos);
        if (pState.getValue(HALF) == DoubleBlockHalf.UPPER) {
            if (pState.getBlock() != this) return surviveCondition(pState, pLevel, pPos);
            return groundState.is(this) && groundState.getValue(HALF) == DoubleBlockHalf.LOWER;
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
        return getWhitelist().contains(state.getBlock());
    }

    public boolean surviveCondition(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        BlockPos blockpos = pPos.below();
        if (pState.getBlock() == this)
            return getWhitelist().contains(pLevel.getBlockState(blockpos).getBlock());
        return this.mayPlaceOn(pLevel.getBlockState(blockpos), pLevel, blockpos);
    }

    public ImmutableList<Block> getWhitelist() {
        return type.getWhitelist().get();
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return getShape();
    }

    public VoxelShape getShape() {
        return shape.getShape();
    }
}
