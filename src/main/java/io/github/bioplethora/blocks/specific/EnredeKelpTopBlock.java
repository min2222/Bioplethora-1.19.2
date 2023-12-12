package io.github.bioplethora.blocks.specific;

import io.github.bioplethora.registry.BPBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.KelpBlock;

public class EnredeKelpTopBlock extends KelpBlock {

    public EnredeKelpTopBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected Block getBodyBlock() {
        return BPBlocks.ENREDE_KELP_PLANT.get();
    }
}
