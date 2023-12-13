package io.github.bioplethora.mixin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import io.github.bioplethora.registry.BPTags;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.ChorusFlowerBlock;
import net.minecraft.world.level.block.ChorusPlantBlock;
import net.minecraft.world.level.block.state.BlockState;

@Mixin(ChorusFlowerBlock.class)
public abstract class ChorusFlowerBlockMixin {

    @Shadow @Final private ChorusPlantBlock plant;

    @Shadow protected abstract void placeGrownFlower(Level pLevel, BlockPos pPos, int pAge);

    @Inject(method = "randomTick", at = @At("HEAD"), cancellable = true)
    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom, CallbackInfo ci) {
        BlockState blockstate = pLevel.getBlockState(pPos.below());
        if (blockstate.is(BPTags.Blocks.CHORUS_GROWABLE)) {
            BlockPos abovePos = pPos.above();
            if (pLevel.isEmptyBlock(abovePos) && abovePos.getY() < 256) {
                int i = pState.getValue(ChorusFlowerBlock.AGE);
                if (i < 5) {
                    this.placeGrownFlower(pLevel, abovePos, i + 1);
                    pLevel.setBlock(pPos, plant.defaultBlockState().setValue(ChorusPlantBlock.UP, true).setValue(ChorusPlantBlock.DOWN, true), 1|16|2);
                    ci.cancel();
                }
            }
        }
    }

    @Inject(method = "canSurvive", at = @At("HEAD"), cancellable = true)
    public void canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos, CallbackInfoReturnable<Boolean> cir) {
        BlockState blockstate = pLevel.getBlockState(pPos.below());
        if (blockstate.is(BPTags.Blocks.CHORUS_GROWABLE)) {
            cir.setReturnValue(true);
            cir.cancel();
        }
    }
}
