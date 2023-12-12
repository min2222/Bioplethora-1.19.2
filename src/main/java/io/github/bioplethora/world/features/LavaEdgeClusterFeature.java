package io.github.bioplethora.world.features;

import com.mojang.serialization.Codec;

import io.github.bioplethora.api.world.BlockUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.levelgen.feature.RandomPatchFeature;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.phys.AABB;

public class LavaEdgeClusterFeature extends RandomPatchFeature {

    public LavaEdgeClusterFeature(Codec<RandomPatchConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean isValid(LevelAccessor pLevel, BlockPos pPos, RandomPatchConfiguration pConfig) {
        return super.isValid(pLevel, pPos, pConfig) && BlockUtils.checkNearestTaggedFluid(checkForLiquid(pPos), pLevel, FluidTags.LAVA);
    }

    public AABB checkForLiquid(BlockPos pPos) {
        int areaint = 14;
        double x = pPos.getX(), y = pPos.getY(), z = pPos.getZ();
        return new AABB(
                x - (areaint / 2d), y - (areaint / 2d), z - (areaint / 2d),
                x + (areaint / 2d), y + (areaint / 2d), z + (areaint / 2d)
        );
    }
}
