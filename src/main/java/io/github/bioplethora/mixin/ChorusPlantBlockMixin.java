package io.github.bioplethora.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import io.github.bioplethora.registry.BPTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChorusPlantBlock;
import net.minecraft.world.level.block.PipeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

@Mixin(value = ChorusPlantBlock.class, priority = 100)
public abstract class ChorusPlantBlockMixin extends PipeBlock {

    public ChorusPlantBlockMixin(float p_i48355_1_, Properties pProperties) {
        super(p_i48355_1_, pProperties);
    }

    @Inject(method = "getStateForPlacement(Lnet/minecraft/item/BlockItemUseContext;)Lnet/minecraft/block/BlockState;", at = @At("RETURN"), cancellable = true)
    public void getStateForPlacement(BlockPlaceContext ctx, CallbackInfoReturnable<BlockState> info) {
        BlockPos pos = ctx.getClickedPos();
        Level world = ctx.getLevel();
        BlockState plant = info.getReturnValue();

        if (ctx.canPlace() && plant.is(Blocks.CHORUS_PLANT) && world.getBlockState(pos.below()).is(BPTags.Blocks.CHORUS_GROWABLE)) {
            info.setReturnValue(plant.setValue(BlockStateProperties.DOWN, true));
        }
    }

    @Inject(method = "getStateForPlacement(Lnet/minecraft/world/IBlockReader;Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/block/BlockState;", at = @At("RETURN"), cancellable = true)
    private void getStateForPlacementX(BlockGetter world, BlockPos pos, CallbackInfoReturnable<BlockState> info) {
        BlockState plant = info.getReturnValue();
        if (plant.is(Blocks.CHORUS_PLANT) && world.getBlockState(pos.below()).is(BPTags.Blocks.CHORUS_GROWABLE)) {
            info.setReturnValue(plant.setValue(BlockStateProperties.DOWN, true));
        }
    }

    @Inject(method = "canSurvive", at = @At("HEAD"), cancellable = true)
    private void canSurvive(BlockState state, LevelReader world, BlockPos pos, CallbackInfoReturnable<Boolean> info) {
        if (world.getBlockState(pos.below()).is(BPTags.Blocks.CHORUS_GROWABLE)) {
            info.setReturnValue(true);
        }
    }

    @Inject(method = "updateShape", at = @At("RETURN"), cancellable = true)
    private void updateShape(BlockState state, Direction direction, BlockState newState, LevelAccessor world, BlockPos pos, BlockPos posFrom, CallbackInfoReturnable<BlockState> info) {
        BlockState plant = info.getReturnValue();
        if (plant.is(Blocks.CHORUS_PLANT) && world.getBlockState(pos.below()).is(BPTags.Blocks.CHORUS_GROWABLE)) {
            plant = plant.setValue(BlockStateProperties.DOWN, true);
            info.setReturnValue(plant);

        }
    }
}
