package io.github.bioplethora.api.world;

import io.github.bioplethora.registry.BPTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.AABB;

public class BlockUtils {

    public static boolean checkBlockstate(BlockGetter world, BlockPos pos, BlockState requiredState) {
        return world.getBlockState(pos) == requiredState;
    }

    public static boolean checkBlock(BlockGetter world, BlockPos pos, Block requiredBlock) {
        return checkBlockstate(world, pos, requiredBlock.defaultBlockState());
    }

    public static double blockDistance(BlockPos pos1, BlockPos pos2) {
        double d0 = pos2.getX() - pos1.getX();
        double d1 = pos2.getY() - pos1.getY();
        double d2 = pos2.getZ() - pos1.getZ();
        return Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
    }

    public static boolean checkNearestTaggedFluid(AABB pBb, LevelReader level, TagKey<Fluid> tag) {
        int i = Mth.floor(pBb.minX), j = Mth.ceil(pBb.maxX);
        int k = Mth.floor(pBb.minY), l = Mth.ceil(pBb.maxY);
        int i1 = Mth.floor(pBb.minZ), j1 = Mth.ceil(pBb.maxZ);
        BlockPos.MutableBlockPos blockpos$mutable = new BlockPos.MutableBlockPos();

        for(int k1 = i; k1 < j; ++k1) {
            for(int l1 = k; l1 < l; ++l1) {
                for(int i2 = i1; i2 < j1; ++i2) {
                    BlockState blockstate = level.getBlockState(blockpos$mutable.set(k1, l1, i2));
                    if (!blockstate.getFluidState().is(tag)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public static void knockUpRandomNearbyBlocks(Level world, double yDelta, BlockPos point, int radiusX, int radiusY, int radiusZ, boolean sendParticles, boolean randomYDelta) {
        for (int radY = point.getY(); radY <= point.getY() + radiusY; radY++) {
            for (int radX = point.getX() - radiusX; radX <= point.getX() + radiusX; radX++) {
                for (int radZ = point.getZ() - radiusZ; radZ <= point.getZ() + radiusZ; radZ++) {

                    BlockPos pos = new BlockPos(radX, radY, radZ);
                    BlockState blockState = world.getBlockState(pos);

                    if (!blockState.is(BlockTags.DRAGON_IMMUNE) && !blockState.is(BPTags.Blocks.ALPHANIA)) {
                        if (Math.random() <= 0.3) {
                            FallingBlockEntity blockEntity = new FallingBlockEntity(world, pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, blockState);
                            if (randomYDelta) {
                                blockEntity.setDeltaMovement(blockEntity.getDeltaMovement().add(0, yDelta + (Math.random() / 4), 0));
                            } else {
                                blockEntity.setDeltaMovement(blockEntity.getDeltaMovement().add(0, yDelta, 0));
                            }
                            world.addFreshEntity(blockEntity);

                            if (sendParticles) {
                                if (world instanceof ServerLevel) {
                                    ((ServerLevel) world).sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, blockState), pos.getX(), pos.getY() + 1, pos.getZ(),
                                            5, 0.6, 0.8, 0.6, 0.1);
                                }
                            }
                        }
                    }
                }
            }
        }

        BlockState pointstate = world.getBlockState(point);
        world.playSound(null, point, pointstate.getSoundType().getBreakSound(), SoundSource.PLAYERS, 1.0F, 1.0F);
    }

    public static void destroyNearbySpecificBlocks(Level world, BlockPos point, int radiusX, int radiusY, int radiusZ, boolean shouldDrop, Block... specifiedBlocks) {
        for (int radY = point.getY() - radiusX; radY <= point.getY() + radiusY; radY++) {
            for (int radX = point.getX() - radiusX; radX <= point.getX() + radiusX; radX++) {
                for (int radZ = point.getZ() - radiusZ; radZ <= point.getZ() + radiusZ; radZ++) {

                    BlockPos pos = new BlockPos(radX, radY, radZ);

                    for (Block targetBlocks : specifiedBlocks) {
                        if (world.getBlockState(pos).getBlock() == targetBlocks) {
                            world.destroyBlock(pos, shouldDrop);
                        }
                    }
                }
            }
        }
    }

    public static void destroyAllNearbyBlocks(Level world, BlockPos point, int radiusX, int radiusY, int radiusZ, boolean shouldDrop) {
        for (int radY = point.getY() - radiusX; radY <= point.getY() + radiusY; radY++) {
            for (int radX = point.getX() - radiusX; radX <= point.getX() + radiusX; radX++) {
                for (int radZ = point.getZ() - radiusZ; radZ <= point.getZ() + radiusZ; radZ++) {

                    BlockPos pos = new BlockPos(radX, radY, radZ);

                    world.destroyBlock(pos, shouldDrop);
                }
            }
        }
    }

    private BlockUtils(){}
}
