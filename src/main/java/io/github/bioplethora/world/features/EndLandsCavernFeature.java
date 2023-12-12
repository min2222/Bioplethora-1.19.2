package io.github.bioplethora.world.features;

import com.mojang.serialization.Codec;

import io.github.bioplethora.registry.BPBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class EndLandsCavernFeature extends Feature<NoneFeatureConfiguration> {

    public EndLandsCavernFeature(Codec<NoneFeatureConfiguration> pCodec) {
        super(pCodec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> pContext) {
    	WorldGenLevel world = pContext.level();
    	RandomSource rand = pContext.random();
    	BlockPos pos = pContext.origin();
        pos = new BlockPos(pos.getX(), 20 + rand.nextInt(20), pos.getZ());
        int radius = 7 + rand.nextInt(11);
        for (int sx = -radius; sx <= radius; sx++) {
            for (int sy = -radius; sy <= radius; sy++) {
                for (int sz = -radius; sz <= radius; sz++) {
                    if (sx * sx + sy * sy + sz * sz <= radius * radius) {
                        BlockPos.MutableBlockPos tPos = pos.offset(sx, sy, sz).mutable();
                        if (!world.getBlockState(tPos).isAir()) {
                            if (replaceWithEndurion(world, tPos)) {
                                this.setBlock(world, tPos, BPBlocks.ENDURION.get().defaultBlockState());
                            } else {
                                this.setBlock(world, tPos, Blocks.WATER.defaultBlockState());
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    public boolean replaceWithEndurion(WorldGenLevel world, BlockPos pos) {
        return world.getBlockState(pos.offset(1, 0, 0)).isAir() || world.getBlockState(pos.offset(0, 0, 1)).isAir() || world.getBlockState(pos.offset(-1, 0, 0)).isAir() || world.getBlockState(pos.offset(0, 0, -1)).isAir() || world.getBlockState(pos.offset(0, -1, 0)).isAir();
    }
}
