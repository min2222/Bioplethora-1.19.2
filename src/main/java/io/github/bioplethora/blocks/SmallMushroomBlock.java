package io.github.bioplethora.blocks;

import javax.annotation.Nullable;

import io.github.bioplethora.enums.BioPlantShape;
import io.github.bioplethora.enums.BioPlantType;
import io.github.bioplethora.registry.BPBlockstateProperties;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class SmallMushroomBlock extends BPPlantBlock {
    public static final IntegerProperty MINISHROOMS = BPBlockstateProperties.MINISHROOMS;
    public static final DirectionProperty FACING_DIRECTION = HorizontalDirectionalBlock.FACING;

    public SmallMushroomBlock(BioPlantType type, Properties properties) {
        super(type, BioPlantShape.MINISHROOM, properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(MINISHROOMS, 1));
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        BlockState blockstate = pContext.getLevel().getBlockState(pContext.getClickedPos());

        if (blockstate.is(this)) {
            return blockstate
                    .setValue(MINISHROOMS, Math.min(3, blockstate.getValue(MINISHROOMS) + 1))
                    .setValue(FACING_DIRECTION, pContext.getHorizontalDirection());
        } else {
            return super.getStateForPlacement(pContext);
        }
    }

    public boolean canBeReplaced(BlockState pState, BlockPlaceContext pUseContext) {
        return pUseContext.getItemInHand().getItem() == this.asItem() && pState.getValue(MINISHROOMS) < 3 || super.canBeReplaced(pState, pUseContext);
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING_DIRECTION, rotation.rotate(state.getValue(FACING_DIRECTION)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING_DIRECTION)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING_DIRECTION, MINISHROOMS);
    }
}
