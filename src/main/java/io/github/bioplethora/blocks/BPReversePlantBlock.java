package io.github.bioplethora.blocks;

import io.github.bioplethora.enums.BioPlantShape;
import io.github.bioplethora.enums.BioPlantType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;

public class BPReversePlantBlock extends BPPlantBlock {

    public BPReversePlantBlock(BioPlantType type, BioPlantShape shape, Properties properties) {
        super(type, shape, properties.offsetType(OffsetType.XZ));
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader reader, BlockPos pos) {
        BlockPos blockpos = pos.above();
        if (state.getBlock() == this) return getWhitelist().contains(reader.getBlockState(blockpos).getBlock());
        return this.mayPlaceOn(reader.getBlockState(blockpos), reader, blockpos);
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter reader, BlockPos pos) {
        return getWhitelist().contains(state.getBlock());
    }
}
