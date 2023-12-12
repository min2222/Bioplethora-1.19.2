package io.github.bioplethora.world.features;

import com.mojang.serialization.Codec;

import io.github.bioplethora.world.BPVanillaBiomeFeatureGeneration;
import io.github.bioplethora.world.featureconfigs.PendentBlocksFeatureConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

public class PendentFleignariteFeature extends PendentBlocksFeature {

    public PendentFleignariteFeature(Codec<PendentBlocksFeatureConfig> config) {
        super(config);
    }

    public boolean place(FeaturePlaceContext<PendentBlocksFeatureConfig> context) {
    	WorldGenLevel world = context.level();
    	BlockPos pos = context.origin();
    	PendentBlocksFeatureConfig config = context.config();
    	RandomSource rand = context.random();
        if (!world.isEmptyBlock(pos)) {
            return false;
        } else {
            if (BPVanillaBiomeFeatureGeneration.isFleignariteChunk(pos, world)) {
                if (!validPlace(world, pos, config)) {
                    return false;
                } else {
                    this.generateTopPart(world, rand, pos, config);
                    this.generatePendentsInSurroundings(world, rand, pos, config);
                    return true;
                }
            } else {
                return false;
            }
        }
    }

    public boolean validPlace(WorldGenLevel world, BlockPos pos, PendentBlocksFeatureConfig config) {

        BlockState blockstateTop2 = world.getBlockState(pos.above(2));
        BlockState blockstateTop = world.getBlockState(pos.above());
        BlockState blockstate = world.getBlockState(pos);
        BlockState blockstateBot = world.getBlockState(pos);

        return config.getWhitelist().contains(blockstateTop.getBlock()) &&
                config.getWhitelist().contains(blockstateTop2.getBlock()) &&
                blockstate.isAir() &&
                blockstateBot.isAir();
    }
}
