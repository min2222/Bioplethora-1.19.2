package io.github.bioplethora.blocks;

import io.github.bioplethora.registry.BPBlocks;
import io.github.bioplethora.registry.BPParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;

public class BPLeavesBlock extends LeavesBlock {

    public BPLeavesBlock( Properties properties) {
        super(properties);
    }

    public ParticleOptions getLeafParticle() {
        if (this == BPBlocks.CAERULWOOD_LEAVES.get()) {
            return BPParticles.CAERULWOOD_LEAF.get();

        } else if (this == BPBlocks.ENIVILE_LEAVES_PINK.get()) {
            return BPParticles.PINK_ENIVILE_LEAF.get();

        } else if (this == BPBlocks.ENIVILE_LEAVES_RED.get()) {
            return BPParticles.RED_ENIVILE_LEAF.get();

        } else {
            throw new IllegalStateException("Invalid leaf block, make sure to add " + ForgeRegistries.BLOCKS.getKey(this).getPath() + " on the getLeafParticle() method.");
        }
    }

    @Override
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom) {
        super.animateTick(pState, pLevel, pPos, pRandom);

        if (pRandom.nextInt(15) == 1) {
            if (pLevel.getBlockState(pPos.below()).isAir()) {
                double speedX = pRandom.nextGaussian() * 0.02D;
                double speedY = pRandom.nextGaussian() * 0.03D;
                double speedZ = pRandom.nextGaussian() * 0.02D;
                pLevel.addParticle(getLeafParticle(), pPos.getX(), pPos.getY() - 1, pPos.getZ(), speedX, speedY, speedZ);
            }
        }
    }
}
