package io.github.bioplethora.item.weapons;

import java.util.List;

import javax.annotation.Nullable;

import io.github.bioplethora.api.BPItemSettings;
import io.github.bioplethora.config.BPConfig;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class NandbricShortswordItem extends SwordItem {
    private LivingEntity target;
    private boolean using;

    public NandbricShortswordItem(Tier tier, int attackDamageModifier, float attackSpeedModifier, Properties properties) {
        super(tier, attackDamageModifier, attackSpeedModifier, properties);
        this.target = null;
        this.using = false;
    }

    public void inventoryTick(ItemStack itemstack, Level world, Entity entity, int slot, boolean selected) {
        LivingEntity attacker = (LivingEntity)entity;
        if(selected) {
            if(using) {
                InteractionHand hand = attacker.getUsedItemHand();
                BlockPos attackerPos = attacker.blockPosition();

                if (this.target != null) {
                    AABB hitrange = attacker.getBoundingBox().inflate(2);

                    if (hitrange.intersects(this.target.getBoundingBox())) {
                        this.target.hurt(attacker.damageSources().mobAttack(attacker), 7);
                        this.target.addEffect(new MobEffectInstance(MobEffects.POISON, 200, 1));
                        if (attacker instanceof Player) {
                            ((Player) attacker).getCooldowns().addCooldown(itemstack.getItem(), 22);
                            ((Player) attacker).awardStat(Stats.ITEM_USED.get(this));
                            if (!((Player) attacker).getAbilities().instabuild) {
                                itemstack.hurtAndBreak(1, attacker, (user) -> user.broadcastBreakEvent(hand));
                            }
                        }
                        world.playSound(null, attackerPos, SoundEvents.ZOMBIE_INFECT, SoundSource.PLAYERS, 1, 1);
                        world.playSound(null, attackerPos, SoundEvents.PLAYER_ATTACK_SWEEP, SoundSource.PLAYERS, 1, 1);
                        if (world instanceof ServerLevel) {
                            ((ServerLevel) world).sendParticles(ParticleTypes.SNEEZE, this.target.getX(), this.target.getY() - (this.target.getBbHeight() / 2), this.target.getZ(), 30, 0.8, 1.2, 0.8, 0);
                        }
                        using = false;
                    } else {
                        double yDifference = this.target.getY() - attacker.getY();
                        boolean flag = yDifference <= 3 && yDifference > -1;
                        boolean canUseInNonCreative = attacker.getY() > this.target.getY() || flag;
                        double vecX = this.target.getX() - attacker.getX(),
                                vecY = this.target.getY() - attacker.getY(),
                                vecZ = this.target.getZ() - attacker.getZ();
                        double vecM = Math.sqrt(Math.pow(vecX, 2) + Math.pow(vecY, 2) + Math.pow(vecZ, 2));
                        final double speedModifier = BPConfig.COMMON.toxinRushSpeedModifier.get();
                        vecX = (vecX / vecM) * speedModifier;
                        vecY = (vecY / vecM) * speedModifier;
                        vecZ = (vecZ / vecM) * speedModifier;

                        if (((Player) attacker).getAbilities().instabuild || canUseInNonCreative) {
                            attacker.setDeltaMovement(vecX, vecY, vecZ);
                        } else using = false;
                    }
                } else using = false;
            }
        } else using = false;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        BPItemSettings.sacredLevelText(tooltip);

        tooltip.add(Component.translatable("item.bioplethora.nandbric_shortsword.fast_strike.skill").withStyle(BPItemSettings.SKILL_NAME_COLOR));
        if(Screen.hasShiftDown() || Screen.hasControlDown()) {
            tooltip.add(Component.translatable("item.bioplethora.nandbric_shortsword.fast_strike.desc").withStyle(BPItemSettings.SKILL_DESC_COLOR));
        }

        tooltip.add(Component.translatable("item.bioplethora.nandbric_shortsword.toxin_rush.skill").withStyle(BPItemSettings.SKILL_NAME_COLOR));
        if(Screen.hasShiftDown() || Screen.hasControlDown()) {
            tooltip.add(Component.translatable("item.bioplethora.nandbric_shortsword.toxin_rush.desc").withStyle(BPItemSettings.SKILL_DESC_COLOR));
        }
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity entity, LivingEntity source) {
        boolean retval = super.hurtEnemy(stack, entity, source);
        Level world = entity.level;
        double x = entity.getX(), y = entity.getY(), z = entity.getZ();
        BlockPos pos = BlockPos.containing(x, y, z);

        if(retval) {
            entity.addEffect(new MobEffectInstance(MobEffects.POISON, 60, 5));
            entity.invulnerableTime = 5;

            world.playSound(null, pos, SoundEvents.ZOMBIE_INFECT, SoundSource.HOSTILE, 1, 1);
        }

        return retval;
    }

    @Override
    public boolean onEntitySwing(ItemStack itemstack, LivingEntity entity) {
        double range = 24.0D;
        double distance = range * range;
        Vec3 vec = entity.getEyePosition(1);
        Vec3 vec1 = entity.getViewVector(1);
        Vec3 targetVec = vec.add(vec1.x * range, vec1.y * range, vec1.z * range);
        AABB aabb = entity.getBoundingBox().expandTowards(vec1.scale(range)).inflate(4.0D, 4.0D, 4.0D);
        EntityHitResult result = ProjectileUtil.getEntityHitResult(entity, vec, targetVec, aabb, (filter) -> !filter.isSpectator() && filter != entity, distance);
        boolean flag = result != null && result.getEntity() instanceof LivingEntity && result.getEntity().isAlive();

        if(flag && !((Player)entity).getCooldowns().isOnCooldown(itemstack.getItem())) {
            this.target = (LivingEntity)result.getEntity();
            this.using = true;
        }
        return super.onEntitySwing(itemstack, entity);
    }
}
