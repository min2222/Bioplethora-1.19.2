package io.github.bioplethora.item.weapons;

import static net.minecraft.world.item.CrossbowItem.clearChargedProjectiles;
import static net.minecraft.world.item.CrossbowItem.getChargedProjectiles;
import static net.minecraft.world.item.CrossbowItem.getShotPitches;
import static net.minecraft.world.item.CrossbowItem.shootProjectile;
import static net.minecraft.world.item.CrossbowItem.tryLoadProjectiles;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;

import io.github.bioplethora.api.BPItemSettings;
import io.github.bioplethora.api.mixin.IAbstractArrowMixin;
import io.github.bioplethora.registry.BPEffects;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.CrossbowAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Vanishable;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ArbitraryBallistaItem extends CrossbowItem implements Vanishable {

    public static int drawTime = 3;
    private boolean startSoundPlayed = false;
    private boolean midLoadSoundPlayed = false;

    public ArbitraryBallistaItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player entity, InteractionHand hand) {
        ItemStack itemstack = entity.getItemInHand(hand);
        if (isCharged(itemstack)) {
            performShooting(world, entity, hand, itemstack, getShootingPower(itemstack), 1.0F);
            setCharged(itemstack, false);

            for (LivingEntity area : world.getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate(1))) {
                if (area != entity) {
                    area.hurt(DamageSource.explosion(entity), 5);
                }
            }

            entity.hurt(DamageSource.explosion((LivingEntity) null), 1);

            return InteractionResultHolder.consume(itemstack);
        } else if (!entity.getProjectile(itemstack).isEmpty()) {
            if (!isCharged(itemstack)) {
                this.startSoundPlayed = false;
                this.midLoadSoundPlayed = false;
                entity.startUsingItem(hand);
            }

            return InteractionResultHolder.consume(itemstack);
        } else {
            return InteractionResultHolder.fail(itemstack);
        }
    }

    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pItemSlot, boolean pIsSelected) {
        super.inventoryTick(pStack, pLevel, pEntity, pItemSlot, pIsSelected);

        if (pEntity instanceof LivingEntity) {
            if (((LivingEntity) pEntity).getMainHandItem() == pStack || ((LivingEntity) pEntity).getOffhandItem() == pStack) {
                if (!((LivingEntity) pEntity).hasEffect(BPEffects.SPIRIT_FISSION.get())) {
                    ((LivingEntity) pEntity).addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 5));
                    ((LivingEntity) pEntity).addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 5));
                    ((LivingEntity) pEntity).addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 5));
                }
            }
        }
    }

    private static void shootProjectile(Level level, LivingEntity entity, Hand hand, ItemStack stack, ItemStack itemStack, float p_220016_5_, boolean b, float p_220016_7_, float p_220016_8_, float v) {

        double x = entity.getX(), y = entity.getY(), z = entity.getZ();

        if (!level.isClientSide) {
            boolean flag = itemStack.getItem() == Items.FIREWORK_ROCKET;
            Projectile projectileentity;
            if (flag) {
                projectileentity = new FireworkRocketEntity(level, itemStack, entity, entity.getX(), entity.getEyeY() - (double)0.15F, entity.getZ(), true);
            } else {
                projectileentity = getArrow(level, entity, stack, itemStack);
                if (b || v != 0.0F) {
                    ((AbstractArrow)projectileentity).pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
                }
            }

            if ((!(level.isClientSide()))) {
                ((ServerLevel) level).sendParticles(ParticleTypes.CLOUD, x, y + 1.2, z, 25, 0.75, 0.75, 0.75, 0.01);
                ((ServerLevel) level).sendParticles(ParticleTypes.EXPLOSION, x, y + 1.2, z, 25, 0.75, 0.75, 0.75, 0);
            }

            level.playSound(null, x, y, z, SoundEvents.GENERIC_EXPLODE, SoundSource.PLAYERS, 1, 1);

            if (entity instanceof CrossbowAttackMob) {
            	CrossbowAttackMob icrossbowuser = (CrossbowAttackMob)entity;
                icrossbowuser.shootCrossbowProjectile(icrossbowuser.getTarget(), stack, projectileentity, v);
            } else {
                Vec3 vector3d1 = entity.getUpVector(1.0F);
                Quaternion quaternion = new Quaternion(new Vector3f(vector3d1), v, true);
                Vec3 vector3d = entity.getViewVector(1.0F);
                Vector3f vector3f = new Vector3f(vector3d);
                vector3f.transform(quaternion);
                projectileentity.shoot(vector3f.x(), vector3f.y(), vector3f.z(), p_220016_7_, p_220016_8_);
            }

            stack.hurtAndBreak(flag ? 3 : 1, entity, (living) -> living.broadcastBreakEvent(hand));
            level.addFreshEntity(projectileentity);
            level.playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.CROSSBOW_SHOOT, SoundSource.PLAYERS, 1.0F, p_220016_5_);
        }
    }

    private static AbstractArrow getArrow(Level world, LivingEntity entity, ItemStack stack, ItemStack stack1) {
        ArrowItem arrowitem = (ArrowItem)(stack1.getItem() instanceof ArrowItem ? stack1.getItem() : Items.ARROW);
        AbstractArrow abstractarrowentity = arrowitem.createArrow(world, stack1, entity);
        if (entity instanceof Player) {
            abstractarrowentity.setCritArrow(true);
        }

        abstractarrowentity.setSoundEvent(SoundEvents.CROSSBOW_HIT);
        abstractarrowentity.setShotFromCrossbow(true);
        abstractarrowentity.setNoGravity(true);
        abstractarrowentity.setBaseDamage(abstractarrowentity.getBaseDamage() * 1.75);

        ((IAbstractArrowMixin) abstractarrowentity).setExplosionRadius(3.0F);
        ((IAbstractArrowMixin) abstractarrowentity).setShouldExplode(true);

        int i = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.PIERCING, stack);
        if (i > 0) {
            abstractarrowentity.setPierceLevel((byte)i);
        }

        return abstractarrowentity;
    }

    public static void performShooting(Level world, LivingEntity entity, InteractionHand hand, ItemStack stack, float projectile, float shootProjectile) {
        List<ItemStack> list = getChargedProjectiles(stack);
        float[] afloat = getShotPitches(entity.getRandom());

        for(int i = 0; i < list.size(); ++i) {
            ItemStack itemstack = list.get(i);
            boolean flag = entity instanceof Player && ((Player) entity).getAbilities().instabuild;
            if (!itemstack.isEmpty()) {
                shootProjectile(world, entity, hand, stack, itemstack, afloat[i] / 2F, flag, projectile, shootProjectile, 0.0F);
            } else if (i == 1) {
                shootProjectile(world, entity, hand, stack, itemstack, afloat[i] / 2F, flag, projectile, shootProjectile, -10.0F);
            } else if (i == 2) {
                shootProjectile(world, entity, hand, stack, itemstack, afloat[i] / 2F, flag, projectile, shootProjectile, 10.0F);
            }
        }

        onCrossbowShot(world, entity, stack);
    }

    @Override
    public void releaseUsing(ItemStack pStack, Level pLevel, LivingEntity pEntityLiving, int pTimeLeft) {
        int i = this.getUseDuration(pStack) - pTimeLeft;
        float f = getPowerForTime(i, pStack);
        if (f >= 1.0F && !isCharged(pStack) && tryLoadProjectiles(pEntityLiving, pStack)) {
            setCharged(pStack, true);
            SoundSource soundcategory = pEntityLiving instanceof Player ? SoundSource.PLAYERS : SoundSource.HOSTILE;
            pLevel.playSound(null, pEntityLiving.getX(), pEntityLiving.getY(), pEntityLiving.getZ(), SoundEvents.CROSSBOW_LOADING_END, soundcategory, 1.0F, 1.0F / (random.nextFloat() * 0.5F + 1.0F) + 0.2F);
        }
    }

    public static float getPowerForTime(int pUseTime, ItemStack pCrossbowStack) {
        float f = (float)pUseTime / (float)getChargeDuration(pCrossbowStack);
        if (f > 1.0F) {
            f = 1.0F;
        }

        return f;
    }

    private static float[] getShotPitches(Random p_220028_0_) {
        boolean flag = p_220028_0_.nextBoolean();
        return new float[]{1.0F, getRandomShotPitch(flag), getRandomShotPitch(!flag)};
    }

    private static float getRandomShotPitch(boolean p_220032_0_) {
        float f = p_220032_0_ ? 0.63F : 0.43F;
        return 1.0F / (new Random().nextFloat() * 0.5F + 1.8F) + f;
    }

    private static void onCrossbowShot(Level p_220015_0_, LivingEntity p_220015_1_, ItemStack p_220015_2_) {
        if (p_220015_1_ instanceof ServerPlayer) {
            ServerPlayer serverplayerentity = (ServerPlayer)p_220015_1_;
            if (!p_220015_0_.isClientSide) {
                CriteriaTriggers.SHOT_CROSSBOW.trigger(serverplayerentity, p_220015_2_);
            }

            serverplayerentity.awardStat(Stats.ITEM_USED.get(p_220015_2_.getItem()));
        }

        clearChargedProjectiles(p_220015_2_);
    }

    @Override
    public void onUseTick(Level world, LivingEntity entity, ItemStack stack, int num) {
        if (!world.isClientSide) {
            int i = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.QUICK_CHARGE, stack);
            SoundEvent soundevent = this.getStartSound(i);
            SoundEvent soundevent1 = i == 0 ? SoundEvents.CROSSBOW_LOADING_MIDDLE : null;
            float f = (float)(stack.getUseDuration() - num) / (float)getChargeDuration(stack);
            if (f < 0.2F) {
                this.startSoundPlayed = false;
                this.midLoadSoundPlayed = false;
            }

            if (f >= 0.2F && !this.startSoundPlayed) {
                this.startSoundPlayed = true;
                world.playSound(null, entity.getX(), entity.getY(), entity.getZ(), soundevent, SoundSource.PLAYERS, 0.5F, 0.5F);
            }

            if (f >= 0.5F && soundevent1 != null && !this.midLoadSoundPlayed) {
                this.midLoadSoundPlayed = true;
                world.playSound(null, entity.getX(), entity.getY(), entity.getZ(), soundevent1, SoundSource.PLAYERS, 0.5F, 0.5F);
            }
        }
    }

    public int getUseDuration(ItemStack stack) {
        return getChargeDuration(stack) + 3;
    }

    public static int getChargeDuration(ItemStack stack) {
        int i = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.QUICK_CHARGE, stack);
        return i == 0 ? 25 * drawTime : (25 - 5 * i) * drawTime;
    }

    private SoundEvent getStartSound(int i) {
        switch(i) {
            case 1:
                return SoundEvents.CROSSBOW_QUICK_CHARGE_1;
            case 2:
                return SoundEvents.CROSSBOW_QUICK_CHARGE_2;
            case 3:
                return SoundEvents.CROSSBOW_QUICK_CHARGE_3;
            default:
                return SoundEvents.CROSSBOW_LOADING_START;
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, world, tooltip, flag);
        BPItemSettings.sacredLevelText(tooltip);

        tooltip.add(Component.translatable("item.bioplethora.arbitrary_ballista.heavy_duty_ballista.skill").withStyle(BPItemSettings.SKILL_NAME_COLOR));
        if (Screen.hasShiftDown() || Screen.hasControlDown()) {
            tooltip.add(Component.translatable("item.bioplethora.arbitrary_ballista.heavy_duty_ballista.desc").withStyle(BPItemSettings.SKILL_DESC_COLOR));
        }
    }

    private static float getShootingPower(ItemStack p_220013_0_) {
        return p_220013_0_.getItem() == Items.CROSSBOW && containsChargedProjectile(p_220013_0_, Items.FIREWORK_ROCKET) ? 1.6F : 3.15F;
    }

    @Override
    public int getDefaultProjectileRange() {
        return 8;
    }
}
