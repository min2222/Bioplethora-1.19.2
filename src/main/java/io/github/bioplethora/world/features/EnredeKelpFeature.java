package io.github.bioplethora.world.features;

import com.mojang.serialization.Codec;

import io.github.bioplethora.registry.BPBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.KelpBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class EnredeKelpFeature extends Feature<NoneFeatureConfiguration> {

    public EnredeKelpFeature(Codec<NoneFeatureConfiguration> pCodec) {
        super(pCodec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> pContext) {
    	WorldGenLevel world = pContext.level();
    	RandomSource rand = pContext.random();
    	BlockPos pos = pContext.origin();
    	
        int i = 0;
        int j = world.getHeight(Heightmap.Types.OCEAN_FLOOR, pos.getX(), pos.getZ());
        BlockPos blockpos = new BlockPos(pos.getX(), j, pos.getZ());
        if (world.getBlockState(blockpos).is(Blocks.WATER)) {
            BlockState blockstate = BPBlocks.ENREDE_KELP.get().defaultBlockState();
            BlockState blockstate1 = BPBlocks.ENREDE_KELP_PLANT.get().defaultBlockState();
            int k = 3 + rand.nextInt(10);

            for(int l = 0; l <= k; ++l) {
                if (world.getBlockState(blockpos).is(Blocks.WATER) && world.getBlockState(blockpos.above()).is(Blocks.WATER) && blockstate1.canSurvive(world, blockpos)) {
                    if (l == k) {
                        world.setBlock(blockpos, blockstate.setValue(KelpBlock.AGE, rand.nextInt(4) + 20), 2);
                        ++i;
                    } else {
                        world.setBlock(blockpos, blockstate1, 2);
                    }
                } else if (l > 0) {
                    BlockPos blockpos1 = blockpos.below();
                    if (blockstate.canSurvive(world, blockpos1) && !world.getBlockState(blockpos1.below()).is(BPBlocks.ENREDE_KELP.get())) {
                        world.setBlock(blockpos1, blockstate.setValue(KelpBlock.AGE, rand.nextInt(4) + 20), 2);
                        ++i;
                    }
                    break;
                }

                blockpos = blockpos.above();
            }
        }

        return i > 0;
    }
}
