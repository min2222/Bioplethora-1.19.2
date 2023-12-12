package io.github.bioplethora.world.features;

import com.mojang.serialization.Codec;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class EndFrozenIslandFeature extends Feature<NoneFeatureConfiguration> {

    public EndFrozenIslandFeature(Codec<NoneFeatureConfiguration> pCodec) {
        super(pCodec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> pContext) {
    	WorldGenLevel world = pContext.level();
    	RandomSource rand = pContext.random();
    	BlockPos pos = pContext.origin();

        if (rand.nextInt(3) != 1) return false;

        pos = new BlockPos((pos.getX() - 10) + rand.nextInt(20), 40 + rand.nextInt(20), (pos.getZ() - 10) + rand.nextInt(20));

        this.placeLayer(rand.nextInt(7) + 5, Blocks.ICE, world, rand, true, pos.offset(-3 + rand.nextInt(6), 0, -3 + rand.nextInt(6)));
        this.placeLayer(rand.nextInt(7) + 6, Blocks.SNOW_BLOCK, world, rand, false, pos.offset(-3 + rand.nextInt(6), -1, -3 + rand.nextInt(4)));
        this.placeLayer(rand.nextInt(6) + 5, Blocks.ICE, world, rand, false, pos.offset(-3 + rand.nextInt(6), -2, -3 + rand.nextInt(3)));
        this.placeLayer(rand.nextInt(4) + 3, Blocks.ICE, world, rand, false, pos.offset(-3 + rand.nextInt(6), -3, -3 + rand.nextInt(3)));

        if (rand.nextInt(3) == 1) {
            this.placeLayer(rand.nextInt(2) + 2, Blocks.ICE, world, rand, rand.nextInt(3) == 0, pos.offset(-3 + rand.nextInt(6), -4, -3 + rand.nextInt(2)));
        }

        return true;
    }

    public void placeLayer(int radius, Block block, WorldGenLevel world, RandomSource rand, boolean placeIcicle, BlockPos pos) {
        for (int sx = -radius; sx <= radius; sx++) {
            for (int sz = -radius; sz <= radius; sz++) {
                if (sx * sx + sz * sz <= radius * radius) {
                    BlockPos.MutableBlockPos newPos = pos.offset(sx, 0, sz).mutable();
                    if (world.isEmptyBlock(newPos)) {
                        setBlock(world, newPos, block.defaultBlockState());
                    }
                }
            }
        }

        if (placeIcicle) {
            EndIcicleFeature.createSpike(world, pos);
        }
    }
}
