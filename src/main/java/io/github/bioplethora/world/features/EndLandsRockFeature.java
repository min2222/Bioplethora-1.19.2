package io.github.bioplethora.world.features;

import com.mojang.serialization.Codec;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.registry.BPBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class EndLandsRockFeature extends Feature<NoneFeatureConfiguration> {

    public EndLandsRockFeature(Codec<NoneFeatureConfiguration> pCodec) {
        super(pCodec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> pContext) {
    	WorldGenLevel world = pContext.level();
    	RandomSource rand = pContext.random();
    	BlockPos pos = pContext.origin();
        for (int i = 0; i < 1 + rand.nextInt(3); i++) {
            createRock(world, rand, pos, 6 + rand.nextInt(10));
        }
        return true;
    }

    public void createRock(WorldGenLevel world, RandomSource rand, BlockPos pos, int length) {
        BlockPos origin = new BlockPos(pos.offset(7 + rand.nextInt(10), 0, 7 + rand.nextInt(10)));
        do {
            origin = origin.below();
            if (origin.getY() <= 10 && world.getBlockState(origin.below()).isAir()) {
                Bioplethora.LOGGER.info("HAH! LOOKS LIKE ONE OF YOUR FEATURES GENERATED ABOVE VOID LMAO");
                return;
            }
        } while (!world.getBlockState(origin.below()).isFaceSturdy(world, origin, Direction.UP) || !world.getBlockState(origin).is(Blocks.WATER));
        Bioplethora.LOGGER.info("Successful locating Block Position for End Rock!");

        int maxWidth = 2 + rand.nextInt(3);

        for (int yy = 0; yy < length; yy++) {
            maxWidth = (int) (maxWidth - Math.sin(Math.PI / length - 3));
            for (int xx = -maxWidth; xx < maxWidth; xx++) {
                for (int zz = -maxWidth; zz < maxWidth; zz++) {
                    if (xx * xx + zz * zz <= maxWidth * maxWidth) {
                        BlockPos posMutable = origin.offset(xx, yy, zz);
                        if (world.isWaterAt(posMutable)) {
                            this.setBlock(world, posMutable, BPBlocks.ENDURION.get().defaultBlockState());
                        }
                    }
                }
            }
        }
    }
}
