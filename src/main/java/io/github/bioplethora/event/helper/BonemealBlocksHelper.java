package io.github.bioplethora.event.helper;

import com.google.common.collect.ImmutableList;

import io.github.bioplethora.api.world.EntityUtils;
import io.github.bioplethora.blocks.SmallMushroomBlock;
import io.github.bioplethora.registry.BPBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.eventbus.api.Event;

public class BonemealBlocksHelper {

    public static void performBonemealAction(BonemealEvent event) {
        Level world = event.getLevel();

        doBoneMeal(event,
                soulSandValleyPlants(world.getRandom()),
                ImmutableList.of(Blocks.SOUL_SOIL, Blocks.SOUL_SAND)
        );
    }

    public static BlockState soulSandValleyPlants(RandomSource random) {
        switch (random.nextInt(3)) {
            default: return BPBlocks.SOUL_TALL_GRASS.get().defaultBlockState();
            case 1: return BPBlocks.SOUL_MINISHROOM.get().defaultBlockState().setValue(SmallMushroomBlock.MINISHROOMS, 1 + random.nextInt(3));
            case 2: return BPBlocks.SOUL_BIGSHROOM.get().defaultBlockState();
        }
    }

    public static void doBoneMeal(BonemealEvent event, BlockState blockSelector, ImmutableList<Block> blockList) {

        Level pLevel = event.getLevel(); BlockState state = event.getBlock();
        BlockPos pPos = event.getPos(); ItemStack stack = event.getStack();
        Player player = event.getEntity();

        if (blockList.contains(pLevel.getBlockState(pPos).getBlock())) {
            EntityUtils.swingAHand(stack, player);
            stack.shrink(1);

            RandomSource pRand = pLevel.random;
            BlockPos targetPos = pPos.above();

            for (int i = 0; i < 128; i++) {
                targetPos = targetPos.offset(pRand.nextInt(5) - 1, (pRand.nextInt(4) - 1) * pRand.nextInt(4) / 2, pRand.nextInt(5) - 1);

                if (pLevel.getBlockState(targetPos).isAir()) {
                    if (blockList.contains(pLevel.getBlockState(targetPos.below()).getBlock())) {
                        if (blockSelector.getBlock() instanceof DoublePlantBlock) {
                        	//TODO need some tweaks, because static method can not be overriden
                            DoublePlantBlock.placeAt(pLevel, state, targetPos, 2);

                        } else pLevel.setBlock(targetPos, blockSelector, 2);
                    }
                }
            }

            for(int i = 0; i < 15; ++i) {
                double d2 = pRand.nextGaussian() * 0.02D;
                double d3 = pRand.nextGaussian() * 0.02D;
                double d4 = pRand.nextGaussian() * 0.02D;
                double d6 = (double)pPos.getX() + pRand.nextDouble() * 2.0D;
                double d7 = (double)pPos.getY() + pRand.nextDouble() * 3.0D;
                double d8 = (double)pPos.getZ() + pRand.nextDouble() * 2.0D;
                if (!pLevel.getBlockState((new BlockPos(d6, d7, d8)).below()).isAir()) {
                    pLevel.addParticle(ParticleTypes.SOUL, d6, d7, d8, d2, d3, d4);
                }
            }

            event.setResult(Event.Result.ALLOW);
        }
    }
}
