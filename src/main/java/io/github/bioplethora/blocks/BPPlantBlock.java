package io.github.bioplethora.blocks;

import com.google.common.collect.ImmutableList;

import io.github.bioplethora.enums.BioPlantShape;
import io.github.bioplethora.enums.BioPlantType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BPPlantBlock extends BushBlock  {

    public BioPlantType type;
    public BioPlantShape shape;

    public BPPlantBlock(BioPlantType type, BioPlantShape shape, Properties properties) {
        super(properties.offsetType(OffsetType.XYZ));
        this.type = type;
        this.shape = shape;
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader reader, BlockPos pos) {
        BlockPos blockpos = pos.below();
        if (state.getBlock() == this) return getWhitelist().contains(reader.getBlockState(blockpos).getBlock());
        return this.mayPlaceOn(reader.getBlockState(blockpos), reader, blockpos);
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter reader, BlockPos pos) {
        return getWhitelist().contains(state.getBlock());
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        Vec3 vector3d = pState.getOffset(pLevel, pPos);
        return getShape().move(vector3d.x, vector3d.y, vector3d.z);
    }

    public ImmutableList<Block> getWhitelist() {
        return type.getWhitelist().get();
    }

    public VoxelShape getShape() {
        return shape.getShape();
    }
}
