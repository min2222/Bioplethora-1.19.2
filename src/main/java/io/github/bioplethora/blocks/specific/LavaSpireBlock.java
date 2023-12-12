package io.github.bioplethora.blocks.specific;

import io.github.bioplethora.blocks.BPPlantBlock;
import io.github.bioplethora.enums.BioPlantShape;
import io.github.bioplethora.enums.BioPlantType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class LavaSpireBlock extends BPPlantBlock {

    public LavaSpireBlock(Properties properties) {
        super(BioPlantType.ALL_NETHER, BioPlantShape.SIMPLE_PLANT, properties);
    }

    @Override
    public void entityInside(BlockState pState, Level pLevel, BlockPos pPos, Entity pEntity) {
        super.entityInside(pState, pLevel, pPos, pEntity);
        pEntity.setSecondsOnFire(5);
    }
}
