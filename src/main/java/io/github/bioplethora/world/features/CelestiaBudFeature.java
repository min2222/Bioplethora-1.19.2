package io.github.bioplethora.world.features;

import com.mojang.serialization.Codec;

import io.github.bioplethora.registry.BPBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.KelpBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class CelestiaBudFeature extends Feature<NoneFeatureConfiguration> {

    public CelestiaBudFeature(Codec<NoneFeatureConfiguration> pCodec) {
        super(pCodec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> pContext) {
    	WorldGenLevel world = pContext.level();
    	RandomSource rand = pContext.random();
    	BlockPos pos = pContext.origin();
    	
        for (BlockPos iPos = pos; world.getBlockState(iPos.below()).getBlock() != BPBlocks.TENEDEBRIS.get() || !(world.getBlockState(iPos).is(Blocks.AIR)); iPos = iPos.below()) {
            pos = new BlockPos(iPos);
            if (pos.getY() <= 10 && world.getBlockState(pos.below()).isAir()) {
                return false;
            }
        }

        this.placeStrand(world, rand, pos);

        /*
        int radius = 8 + rand.nextInt(12);
        for (int sy = -radius; sy <= radius; sy++) {
            for (int sx = -radius; sx <= radius; sx++) {
                for (int sz = -radius; sz <= radius; sz++) {
                    if (rand.nextInt(30) == 1) {
                        BlockPos.MutableBlockPos tPos = pos.offset(sx, sy, sz).mutable();
                        if (world.isEmptyBlock(tPos) && world.getBlockState(tPos.below()).is(BPBlocks.TENEDEBRIS.get())) {
                            this.placeStrand(world, rand, tPos);
                        }
                    }
                }
            }
        }*/

        return true;
    }

    public void placeStrand(WorldGenLevel world, RandomSource rand, BlockPos pos) {
        BlockState blockstate = BPBlocks.CELESTIA_BUD.get().defaultBlockState();
        BlockState blockstate1 = BPBlocks.CELESTIA_BUD_PLANT.get().defaultBlockState();
        int k = 4 + rand.nextInt(8);
        for(int l = 0; l <= k; ++l) {
            if (world.getBlockState(pos).is(Blocks.AIR) && !world.getBlockState(pos.below()).is(BPBlocks.CELESTIA_BUD.get()) && world.getBlockState(pos.above()).is(Blocks.AIR) && blockstate1.canSurvive(world, pos)) {
                if (l == k) {
                    world.setBlock(pos, blockstate.setValue(KelpBlock.AGE, rand.nextInt(4) + 20), 2);
                } else {
                    world.setBlock(pos, blockstate1, 2);
                }
            } else if (l > 0) {
                BlockPos blockpos1 = pos.below();
                if (blockstate.canSurvive(world, blockpos1) && !world.getBlockState(blockpos1.below()).is(BPBlocks.CELESTIA_BUD.get())) {
                    world.setBlock(blockpos1, blockstate.setValue(KelpBlock.AGE, rand.nextInt(4) + 20), 2);
                }
                break;
            }

            pos = pos.above();
        }
    }
}
