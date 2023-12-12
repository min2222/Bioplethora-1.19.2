package io.github.bioplethora.blocks;

import javax.annotation.Nullable;

import io.github.bioplethora.blocks.tile_entities.BPSignTileEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.StandingSignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;

public class BPStandingSignBlock extends StandingSignBlock {

    public BPStandingSignBlock(Properties properties, WoodType woodType) {
        super(properties, woodType);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
    	return new BPSignTileEntity(pPos, pState);
    }
}
