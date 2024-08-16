package io.github.bioplethora.item.weapons;

import java.util.List;

import javax.annotation.Nullable;

import io.github.bioplethora.api.BPItemSettings;
import io.github.bioplethora.api.IReachWeapon;
import io.github.bioplethora.api.extras.IntegrationUtils;
import io.github.bioplethora.api.world.ItemUtils;
import io.github.bioplethora.entity.projectile.AbyssalScalesEntity;
import io.github.bioplethora.registry.BPItems;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class AbyssalBladeItem extends SwordItem implements IReachWeapon {

    public AbyssalBladeItem(Tier tier, int attackDamageIn, float attackSpeedIn, Item.Properties builder) {
        super(tier, attackDamageIn, attackSpeedIn, builder);
    }

    public UseAnim getUseAnimation(ItemStack p_77661_1_) {
        return UseAnim.SPEAR;
    }

    @Override
    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        return super.hurtEnemy(pStack, pTarget, pAttacker);
    }

    public void inventoryTick(ItemStack stack, Level world, Entity entity, int p_77663_4_, boolean p_77663_5_) {
        super.inventoryTick(stack, world, entity, p_77663_4_, p_77663_5_);

        LivingEntity living = (LivingEntity)entity;
        if ((living.getMainHandItem().getItem() == stack.getItem()) || (living.getOffhandItem().getItem() == stack.getItem())) {
            living.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, 5));
        }

        IntegrationUtils.addWobrSaber(stack, 10, 15, 1);
    }

    public int getUseDuration(ItemStack p_77626_1_) {
        return 72000;
    }

    public InteractionResultHolder<ItemStack> use(Level world, Player entity, InteractionHand handIn) {
        ItemStack itemstack = entity.getItemInHand(handIn);
        if (itemstack.getDamageValue() >= itemstack.getMaxDamage() - 1) {
            return InteractionResultHolder.fail(itemstack);
        } else {
            entity.startUsingItem(handIn);
            return InteractionResultHolder.consume(itemstack);
        }
    }

    public void releaseUsing(ItemStack stack, Level world, LivingEntity entity, int value) {
        super.releaseUsing(stack, world, entity, value);

        if (entity instanceof Player) {
            Player playerentity = (Player)entity;

            int i = this.getUseDuration(stack) - value;
            if (i >= 10) {
                if (ItemUtils.checkCooldownUsable(entity, stack)) {
                    float f7 = playerentity.yRot;
                    float f = playerentity.xRot;
                    float f1 = -Mth.sin(f7 * ((float) Math.PI / 180F)) * Mth.cos(f * ((float) Math.PI / 180F));
                    float f2 = -Mth.sin(f * ((float) Math.PI / 180F));
                    float f3 = Mth.cos(f7 * ((float) Math.PI / 180F)) * Mth.cos(f * ((float) Math.PI / 180F));
                    float f4 = Mth.sqrt(f1 * f1 + f2 * f2 + f3 * f3);
                    float f5 = 3.0F; //* ((1.0F + (float) j) / 4.0F);
                    f1 = f1 * (f5 / f4);
                    f2 = f2 * (f5 / f4);
                    f3 = f3 * (f5 / f4);
                    playerentity.push(f1, f2, f3);
                    playerentity.startAutoSpinAttack(20);
                    if (playerentity.onGround()) {
                        float f6 = 1.1999999F;
                        playerentity.move(MoverType.SELF, new Vec3(0.0D, f6, 0.0D));
                    }
                    playerentity.awardStat(Stats.ITEM_USED.get(this));

                    world.playSound(null, playerentity, SoundEvents.TRIDENT_RIPTIDE_3, SoundSource.PLAYERS, 1.0F, 1.0F);

                    playerentity.getCooldowns().addCooldown(stack.getItem(), 20);
                }
            }
        }
    }

    public void shootHandler(Player entity, ItemStack stack, float yRotAddition) {
        AbyssalScalesEntity scales = new AbyssalScalesEntity(entity.level, entity);
        scales.setItem(new ItemStack(BPItems.ABYSSAL_SCALES.get()));
        scales.setOwner(entity);
        scales.shootFromRotation(entity, entity.xRot, entity.yRot + yRotAddition, 0.0F, 2.0F, 1.0F);
        entity.level.addFreshEntity(scales);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        BPItemSettings.sacredLevelText(tooltip);

        tooltip.add(Component.translatable("item.bioplethora.abyssal_blade.tridented_blade.skill").withStyle(BPItemSettings.SKILL_NAME_COLOR));
        if (Screen.hasShiftDown() || Screen.hasControlDown()) {
            tooltip.add(Component.translatable("item.bioplethora.abyssal_blade.tridented_blade.desc").withStyle(BPItemSettings.SKILL_DESC_COLOR));
        }
    }

    @Override
    public double getReachDistance() {
        return 7;
    }
}
