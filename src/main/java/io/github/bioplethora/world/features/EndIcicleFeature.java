package io.github.bioplethora.world.features;

import com.mojang.serialization.Codec;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.phys.Vec3;

public class EndIcicleFeature extends Feature<NoneFeatureConfiguration> {

    public EndIcicleFeature(Codec<NoneFeatureConfiguration> pCodec) {
        super(pCodec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> pContext) {
    	WorldGenLevel world = pContext.level();
    	RandomSource rand = pContext.random();
    	BlockPos pos = pContext.origin();
        pos = new BlockPos(pos.getX(), 35 + rand.nextInt(35), pos.getZ());
        createSpike(world, pos);
        return true;
    }

    public static void createSpike(WorldGenLevel world, BlockPos pos) {
        boolean large = world.getRandom().nextInt(2) == 0;
        int tipMin = large ? 25 : 10;
        int tipRand = large ? 35 : 20;
        int radiusMin = large ? 8 : 4;
        int radiusRand = large ? 6 : 4;

        int tip = tipMin + world.getRandom().nextInt(tipRand);
        int topX = world.getRandom().nextInt(tip) - tip / 2;
        int topZ = world.getRandom().nextInt(tip) - tip / 2;

        int radius = radiusMin + world.getRandom().nextInt(radiusRand);

        Vec3 to = new Vec3(pos.getX() + topX, pos.getY() + tip, pos.getZ() + topZ);
        Vec3 opto = new Vec3(pos.getX() - topX, pos.getY() - tip, pos.getZ() - topZ);

        for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
                double fromCenter = Math.sqrt(Math.pow(x, 2) + Math.pow(z, 2));
                if (fromCenter <= radius) {
                    Vec3 from = new Vec3(pos.getX() + x, pos.getY(), pos.getZ() + z);

                    if (world.getBlockState(BlockPos.containing(from).above()).isAir()) {
                        continue;
                    }

                    Vec3 per = to.subtract(from).normalize();
                    Vec3 current = from.add(0, 0, 0);
                    double distance = from.distanceTo(to);

                    for (double i = 0; i < distance; i++) {
                        BlockPos targetPos = BlockPos.containing(current);
                        world.setBlock(targetPos, Blocks.ICE.defaultBlockState(), 3);
                        current = current.add(per);
                    }

                    Vec3 opper = opto.subtract(from).normalize();
                    Vec3 opcurrent = from.add(0, 0, 0);
                    double opdistance = from.distanceTo(opto);

                    for (double i = 0; i < opdistance; i++) {
                        BlockPos targetPos = BlockPos.containing(opcurrent);
                        world.setBlock(targetPos, Blocks.ICE.defaultBlockState(), 3);
                        opcurrent = opcurrent.add(opper);
                    }
                }
            }
        }
    }
}
