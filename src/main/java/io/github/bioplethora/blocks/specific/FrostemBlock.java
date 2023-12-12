package io.github.bioplethora.blocks.specific;

import io.github.bioplethora.blocks.BPPlantBlock;
import io.github.bioplethora.enums.BioPlantShape;
import io.github.bioplethora.enums.BioPlantType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class FrostemBlock extends BPPlantBlock {

    public FrostemBlock(BioPlantType type, BioPlantShape shape, Properties properties) {
        super(type, shape, properties);
    }

    @Override
    public void entityInside(BlockState p_196262_1_, Level p_196262_2_, BlockPos p_196262_3_, Entity p_196262_4_) {
        super.entityInside(p_196262_1_, p_196262_2_, p_196262_3_, p_196262_4_);
        p_196262_2_.destroyBlock(p_196262_3_, false);
    }
}
