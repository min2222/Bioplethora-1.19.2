package io.github.bioplethora.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import io.github.bioplethora.registry.BPTags;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChorusFlowerBlock;
import net.minecraft.world.level.block.PipeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ChorusPlantFeature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

@Mixin(ChorusPlantFeature.class)
public abstract class ChorusPlantFeatureMixin {

    @Inject(method = "place*", at = @At("HEAD"), cancellable = true)
    private void place(FeaturePlaceContext<NoneFeatureConfiguration> p_159521_, CallbackInfoReturnable<Boolean> cir) {
        WorldGenLevel worldIn = p_159521_.level();
        BlockPos blockPos = p_159521_.origin();
        RandomSource random = p_159521_.random();
        if (worldIn.isEmptyBlock(blockPos) && worldIn.getBlockState(blockPos.below()).is(BPTags.Blocks.CHORUS_GROWABLE)) {
            ChorusFlowerBlock.generatePlant(worldIn, blockPos, random, 8);
            BlockState bottom = worldIn.getBlockState(blockPos);
            if (bottom.is(Blocks.CHORUS_PLANT)) {
                worldIn.setBlock(blockPos, bottom.setValue(PipeBlock.DOWN, true), 1|16|2);
            }
            cir.setReturnValue(true);
        }
    }
}
