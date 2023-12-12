package io.github.bioplethora.world.features;

import com.mojang.serialization.Codec;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.BlockStateConfiguration;

public class WaterPlantFeature extends Feature<BlockStateConfiguration> {

    public WaterPlantFeature(Codec<BlockStateConfiguration> pCodec) {
        super(pCodec);
    }

    @Override
    public boolean place(FeaturePlaceContext<BlockStateConfiguration> context) {
    	BlockPos pos = context.origin();
    	RandomSource rand = context.random();
    	WorldGenLevel world = context.level();
    	BlockStateConfiguration config = context.config();
    	
        boolean flag = false;
        int i = rand.nextInt(32) - rand.nextInt(32);
        int j = rand.nextInt(32) - rand.nextInt(32);
        int k = world.getHeight(Heightmap.Types.OCEAN_FLOOR, pos.getX() + i, pos.getZ() + j);
        BlockPos blockpos = new BlockPos(pos.getX() + i, k, pos.getZ() + j);
        if (world.getBlockState(blockpos).is(Blocks.WATER)) {
            if (config.state.canSurvive(world, blockpos)) {
                world.setBlock(blockpos, config.state, 2);
                flag = true;
            }
        }

        return flag;
    }
}
