package io.github.bioplethora.world.features;

import com.mojang.serialization.Codec;

import io.github.bioplethora.blocks.BPLanternPlantBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.BlockPileConfiguration;

public class SingularBlockFeature extends Feature<BlockPileConfiguration> {

    public SingularBlockFeature(Codec<BlockPileConfiguration> pCodec) {
        super(pCodec);
    }

    @Override
    public boolean place(FeaturePlaceContext<BlockPileConfiguration> context) {
    	WorldGenLevel world = context.level();
    	BlockPos pos = context.origin();
    	BlockPileConfiguration config = context.config();
    	RandomSource rand = context.random();
        BlockState state = config.stateProvider.getState(rand, pos);
        
        if (state.getBlock() instanceof DoublePlantBlock) {

            if (state.getBlock() instanceof BPLanternPlantBlock) {
                if (pos.getY() >= 55 && world.isEmptyBlock(pos) && world.isEmptyBlock(pos.above()) && world.isEmptyBlock(pos.above().above())) {
                    ((BPLanternPlantBlock) state.getBlock()).placeAt(world, pos, 3);
                    return true;
                } else {
                    return false;
                }
            } else {
                if (world.isEmptyBlock(pos) && world.isEmptyBlock(pos.above())) {
                	//TODO need some tweak
                    DoublePlantBlock.placeAt(world, state, pos, 3);
                    return true;
                } else {
                    return false;
                }
            }

        } else if (world.isEmptyBlock(pos)) {
            this.setBlock(world, pos, state);
            return true;

        } else {
            return false;
        }
    }
}
