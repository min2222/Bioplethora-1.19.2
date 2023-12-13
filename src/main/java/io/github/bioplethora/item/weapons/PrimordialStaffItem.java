package io.github.bioplethora.item.weapons;

import java.util.List;

import javax.annotation.Nullable;

import io.github.bioplethora.api.BPItemSettings;
import io.github.bioplethora.entity.others.PrimordialRingEntity;
import io.github.bioplethora.registry.BPEntities;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class PrimordialStaffItem extends Item {

	//FIXME crash on start
    public boolean hellConfig = false; //BPConfig.COMMON.hellMode.get();
    public int charge = 0;

    public PrimordialStaffItem(Properties properties) {
        super(properties);
    }

    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.SPEAR;
    }

    public int getUseDuration(ItemStack stack) {
        return 72000;
    }

    public InteractionResultHolder<ItemStack> use(Level world, Player entity, InteractionHand handIn) {
        ItemStack itemstack = entity.getItemInHand(handIn);
        if (entity.getCooldowns().isOnCooldown(itemstack.getItem())) {
            return InteractionResultHolder.fail(itemstack);
        } else {
            entity.startUsingItem(handIn);
            return InteractionResultHolder.consume(itemstack);
        }
    }

    public void onUsingTick(ItemStack stack, LivingEntity player, int count) {
        super.onUsingTick(stack, player, count);

        if (player instanceof Player) {
            Level worldIn = player.level;
            BlockPos blockpos = player.blockPosition();

            ++charge;
            if (charge == 20) {
                worldIn.playSound(null, blockpos, SoundEvents.BEACON_ACTIVATE, SoundSource.PLAYERS, 1, 1);
                if (worldIn instanceof ServerLevel) {
                    ((ServerLevel) worldIn).sendParticles(ParticleTypes.CRIT, player.getX(), player.getY(), player.getZ(), 50, 0.65, 0.65, 0.65, 0.01);
                }

                charge = 0;
            }
        }
    }

    public void releaseUsing(ItemStack stack, Level worldIn, LivingEntity entity, int value) {
        super.releaseUsing(stack, worldIn, entity, value);

        if (entity instanceof Player) {
            Player playerIn = (Player) entity;
            BlockPos blockpos = playerIn.blockPosition().offset(worldIn.getRandom().nextBoolean() ? -2 : 2, 0, worldIn.getRandom().nextBoolean() ? 2 : -2);

            int i = this.getUseDuration(stack) - value;
            if (i >= 10) {

                if (worldIn instanceof ServerLevel) {
                    PrimordialRingEntity ring = BPEntities.PRIMORDIAL_RING.get().create(worldIn);
                    ring.moveTo(blockpos, 0.0F, 0.0F);
                    ring.setOwner(playerIn);
                    ring.finalizeSpawn((ServerLevelAccessor) worldIn, worldIn.getCurrentDifficultyAt(blockpos), MobSpawnType.MOB_SUMMONED, null, null);

                    ring.setHasLimitedLife(true);
                    ring.setLifeLimitBeforeDeath(hellConfig ? 1000 : 850 + playerIn.getRandom().nextInt(200));

                    worldIn.addFreshEntity(ring);

                    PrimordialRingEntity ring2 = BPEntities.PRIMORDIAL_RING.get().create(worldIn);
                    ring2.moveTo(blockpos, 0.0F, 0.0F);
                    ring2.setOwner(playerIn);
                    ring2.finalizeSpawn((ServerLevelAccessor) worldIn, worldIn.getCurrentDifficultyAt(blockpos), MobSpawnType.MOB_SUMMONED, null, null);

                    ring2.setHasLimitedLife(true);
                    ring2.setLifeLimitBeforeDeath(hellConfig ? 1000 : 850 + playerIn.getRandom().nextInt(200));

                    worldIn.addFreshEntity(ring2);
                }

                worldIn.playSound(null, blockpos, SoundEvents.GHAST_SHOOT, SoundSource.PLAYERS, 1, 1);
                if (worldIn instanceof ServerLevel) {
                    ((ServerLevel) worldIn).sendParticles(ParticleTypes.POOF, (playerIn.getX()), (playerIn.getY()), (playerIn.getZ()), 100, 0.65, 0.65, 0.65, 0.01);
                }

                if (!playerIn.isCreative()) {
                    playerIn.getCooldowns().addCooldown(stack.getItem(), hellConfig ? 500 : 450 + playerIn.getRandom().nextInt(200));
                }
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        BPItemSettings.bossLevelText(tooltip);

        tooltip.add(Component.translatable("item.bioplethora.primordial_staff.cores_aid.skill").withStyle(BPItemSettings.SKILL_NAME_COLOR));
        if (Screen.hasShiftDown() || Screen.hasControlDown()) {
            tooltip.add(Component.translatable("item.bioplethora.primordial_staff.cores_aid.desc").withStyle(BPItemSettings.SKILL_DESC_COLOR));
        }
    }
}
