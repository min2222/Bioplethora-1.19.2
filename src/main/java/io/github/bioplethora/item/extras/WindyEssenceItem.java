package io.github.bioplethora.item.extras;

import io.github.bioplethora.registry.BPBlocks;
import io.github.bioplethora.registry.worldgen.BPStructures;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.EyeOfEnder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public class WindyEssenceItem extends Item {

    public WindyEssenceItem(Properties properties) {
        super(properties);
    }

    // Eye of ender code copy moment
    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        BlockHitResult blockResult = getPlayerPOVHitResult(pLevel, pPlayer, ClipContext.Fluid.NONE);
        if (blockResult.getType() == HitResult.Type.BLOCK && pLevel.getBlockState(blockResult.getBlockPos()).is(BPBlocks.ALPHANUM_NUCLEUS.get())) {
            return InteractionResultHolder.pass(itemstack);
        } else {
            pPlayer.startUsingItem(pHand);
            if (pLevel instanceof ServerLevel) {
                BlockPos blockpos = ((ServerLevel)pLevel).getChunkSource().getGenerator().findNearestMapStructure((ServerLevel)pLevel, BPStructures.ALPHANUM_MAUSOLEUM.get(), pPlayer.blockPosition(), 100, false);
                if (blockpos != null) {
                    EyeOfEnder eyeofenderentity = new EyeOfEnder(pLevel, pPlayer.getX(), pPlayer.getY(0.5D), pPlayer.getZ());
                    eyeofenderentity.setItem(itemstack);
                    eyeofenderentity.signalTo(blockpos);
                    pLevel.addFreshEntity(eyeofenderentity);
                    if (pPlayer instanceof ServerPlayer) {
                        CriteriaTriggers.USED_ENDER_EYE.trigger((ServerPlayer)pPlayer, blockpos);
                    }

                    pLevel.playSound(null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), SoundEvents.ENDER_EYE_LAUNCH, SoundSource.NEUTRAL, 0.5F, 0.4F / (pPlayer.getRandom().nextFloat() * 0.4F + 0.8F));
                    pLevel.levelEvent(null, 1003, pPlayer.blockPosition(), 0);
                    if (!pPlayer.getAbilities().instabuild) {
                        itemstack.shrink(1);
                    }

                    pPlayer.awardStat(Stats.ITEM_USED.get(this));
                    pPlayer.swing(pHand, true);
                    return InteractionResultHolder.success(itemstack);
                }
            }

            return InteractionResultHolder.consume(itemstack);
        }
    }
}
