package io.github.bioplethora.blocks.specific;

import io.github.bioplethora.registry.BPBlocks;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.KelpPlantBlock;

public class EnredeKelpBlock extends KelpPlantBlock {

    public EnredeKelpBlock(Properties properties) {
        super(properties);
    }

    protected GrowingPlantHeadBlock getHeadBlock() {
        return (GrowingPlantHeadBlock) BPBlocks.ENREDE_KELP.get();
    }
}
