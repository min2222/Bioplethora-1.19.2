package io.github.bioplethora.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import io.github.bioplethora.registry.BPTags;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChorusFlowerBlock;
import net.minecraft.world.level.block.PipeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.ChorusPlantFeature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

@Mixin(ChorusPlantFeature.class)
public abstract class ChorusPlantFeatureMixin {

    @Inject(method = "place*", at = @At("HEAD"), cancellable = true)
    private void place(LevelAccessor worldIn, ChunkGenerator chunkGenerator, RandomSource random, BlockPos blockPos, NoneFeatureConfiguration config, CallbackInfoReturnable<Boolean> info) {
        if (worldIn.isEmptyBlock(blockPos) && worldIn.getBlockState(blockPos.below()).is(BPTags.Blocks.CHORUS_GROWABLE)) {
            ChorusFlowerBlock.generatePlant(worldIn, blockPos, random, 8);
            BlockState bottom = worldIn.getBlockState(blockPos);
            if (bottom.is(Blocks.CHORUS_PLANT)) {
                worldIn.setBlock(blockPos, bottom.setValue(PipeBlock.DOWN, true), 1|16|2);
            }
            info.setReturnValue(true);
        }
    }
}
