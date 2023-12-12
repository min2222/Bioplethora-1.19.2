package io.github.bioplethora.blocks.tile_entities;

import io.github.bioplethora.registry.BPTileEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class BPSignTileEntity extends SignBlockEntity {

    public BPSignTileEntity(BlockPos pos, BlockState state) {
        super(pos, state);
    }

    @Override
    public BlockEntityType<?> getType() {
        return BPTileEntities.BP_SIGN.get();
    }
}
