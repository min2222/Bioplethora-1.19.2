package io.github.bioplethora.item.weapons;

import java.util.List;

import javax.annotation.Nullable;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;

import io.github.bioplethora.api.BPItemSettings;
import io.github.bioplethora.api.IReachWeapon;
import io.github.bioplethora.config.BPConfig;
import io.github.bioplethora.entity.projectile.AlphanumObliteratorSpearEntity;
import io.github.bioplethora.registry.BPEnchantments;
import io.github.bioplethora.registry.BPItems;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.Vanishable;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class AlphanumObliteratorItem extends Item implements Vanishable, IReachWeapon {
    private final Multimap<Attribute, AttributeModifier> defaultModifiers;

    public AlphanumObliteratorItem(Properties properties) {
        super(properties);
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Tool modifier", 13.0D, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Tool modifier", -2.6F, AttributeModifier.Operation.ADDITION));
        this.defaultModifiers = builder.build();
    }

    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.BOW;
    }

    public int getUseDuration(ItemStack pStack) {
        return 72000;
    }

    public void initiateShoot(ItemStack pStack, Level pLevel, LivingEntity pEntityLiving) {

        double x = pEntityLiving.getX(), y = pEntityLiving.getY(), z = pEntityLiving.getZ();
        if (!pLevel.isClientSide()) {
            ((ServerLevel) pLevel).sendParticles(ParticleTypes.FIREWORK, x, y + 1.2, z, 50, 0.75, 0.75, 0.75, 0.01);
        }

        if (pEntityLiving.getMainHandItem() == pStack) {
            pStack.hurtAndBreak(1, pEntityLiving, (entity) -> {
                entity.broadcastBreakEvent(EquipmentSlot.MAINHAND);
            });
        } else if (pEntityLiving.getOffhandItem() == pStack) {
            pStack.hurtAndBreak(1, pEntityLiving, (entity) -> {
                entity.broadcastBreakEvent(EquipmentSlot.OFFHAND);
            });
        }
        pLevel.playSound(null, x, y, z, SoundEvents.FIREWORK_ROCKET_LAUNCH, SoundSource.PLAYERS, 1, 1);
        this.shootProjectile(pStack, pLevel, pEntityLiving);
    }

    public void shootProjectile(ItemStack pStack, Level pLevel, LivingEntity pEntityLiving) {
        float xTarget = -Mth.sin(pEntityLiving.yHeadRot * ((float) Math.PI / 180F)) * Mth.cos(pEntityLiving.xRot * ((float) Math.PI / 180F));
        float yTarget = -Mth.sin(pEntityLiving.xRot * ((float) Math.PI / 180F));
        float zTarget = Mth.cos(pEntityLiving.yHeadRot * ((float) Math.PI / 180F)) * Mth.cos(pEntityLiving.xRot * ((float) Math.PI / 180F));
        float damageAdditions = (float) EnchantmentHelper.getItemEnchantmentLevel(BPEnchantments.DEVASTATING_BLAST.get(), pStack) * 1.5F;
        Vec3 vector3d = pEntityLiving.getViewVector(1.0F);

        AlphanumObliteratorSpearEntity projectile = new AlphanumObliteratorSpearEntity(pLevel, pEntityLiving, xTarget, yTarget, zTarget);
        projectile.setPos(pEntityLiving.getX(), pEntityLiving.getY() + 1.5, pEntityLiving.getZ());
        projectile.setOwner(pEntityLiving);
        projectile.setBaseDamage((BPConfig.IN_HELLMODE ? 15.0F : 12.0F) + damageAdditions);
        projectile.shootFromRotation(projectile, pEntityLiving.xRot, pEntityLiving.yHeadRot, 0, 1F, 0);
        pLevel.addFreshEntity(projectile);

        AlphanumObliteratorSpearEntity projectile1 = new AlphanumObliteratorSpearEntity(pLevel, pEntityLiving, xTarget, yTarget, zTarget);
        projectile1.setPos(pEntityLiving.getX() + vector3d.z * 4.0D, pEntityLiving.getY() + 1.5, pEntityLiving.getZ() + vector3d.z * -4.0D);
        projectile.setOwner(pEntityLiving);
        projectile1.setBaseDamage((BPConfig.IN_HELLMODE ? 15.0F : 12.0F) + damageAdditions);
        projectile1.shootFromRotation(projectile1, pEntityLiving.xRot, pEntityLiving.yHeadRot, 0, 1F, 1.0F);
        pLevel.addFreshEntity(projectile1);

        AlphanumObliteratorSpearEntity projectile2 = new AlphanumObliteratorSpearEntity(pLevel, pEntityLiving, xTarget, yTarget, zTarget);
        projectile2.setPos(pEntityLiving.getX() + vector3d.z * -4.0D, pEntityLiving.getY() + 1.5, pEntityLiving.getZ() + vector3d.z * 4.0D);
        projectile.setOwner(pEntityLiving);
        projectile2.setBaseDamage((BPConfig.IN_HELLMODE ? 15.0F : 12.0F) + damageAdditions);
        projectile2.shootFromRotation(projectile2, pEntityLiving.xRot, pEntityLiving.yHeadRot, 0, 1F, 1.0F);
        pLevel.addFreshEntity(projectile2);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level world, Entity entity, int pItemSlot, boolean pIsSelected) {
        super.inventoryTick(stack, world, entity, pItemSlot, pIsSelected);
        CompoundTag getTag = stack.getOrCreateTag();

        int reloadTime = getTag.getInt("reloadTime");
        int i = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.QUICK_CHARGE, stack);
        int quickChargeBonus = i <= 3 ? i * 10 : 30;

        if (entity instanceof LivingEntity) {
            if (!isCharged(stack)) {
                getTag.putInt("reloadTime", reloadTime + 1);

                if (reloadTime == 40 - quickChargeBonus) {
                    setCharged(stack, true);
                    getTag.putInt("reloadTime", 0);

                    entity.playSound(SoundEvents.CROSSBOW_LOADING_END, 1.0F,  1.0F);
                    if (((LivingEntity) entity).getMainHandItem() == stack) {
                        ((LivingEntity) entity).swing(InteractionHand.MAIN_HAND);
                    }
                    if (((LivingEntity) entity).getOffhandItem() == stack) {
                        ((LivingEntity) entity).swing(InteractionHand.OFF_HAND);
                    }
                }
            }
        }

        int offHandComboTimer = getTag.getInt("offComboTimer");

        if (entity instanceof LivingEntity) {
            if (getTag.getBoolean("shouldOffCombo")) {
                getTag.putInt("offComboTimer", offHandComboTimer + 1);

                if (offHandComboTimer >= 5) {
                    this.initiateShoot(stack, world, (LivingEntity) entity);
                    ((LivingEntity) entity).swing(InteractionHand.OFF_HAND);
                    setCharged(stack, false);
                    getTag.putBoolean("shouldOffCombo", false);
                    getTag.putInt("offComboTimer", 0);
                }
            }
        }
    }

    public void releaseUsing(ItemStack pStack, Level pLevel, LivingEntity pEntityLiving, int pTimeLeft) {
        if (pEntityLiving instanceof Player) {

            Player player = (Player) pEntityLiving;
            CompoundTag getOffTag = pEntityLiving.getOffhandItem().getOrCreateTag();
            int i = this.getUseDuration(pStack) - pTimeLeft;

            if (i >= 10 && isCharged(pStack)) {

                if (pEntityLiving.getMainHandItem() == pStack) {
                    this.initiateShoot(pStack, pLevel, pEntityLiving);

                    if (pEntityLiving.getOffhandItem().getItem() == BPItems.ALPHANUM_OBLITERATOR.get()) {
                        if (isCharged(pEntityLiving.getOffhandItem())) {
                            getOffTag.putBoolean("shouldOffCombo", true);
                        }
                    }
                } else {
                    this.initiateShoot(pStack, pLevel, pEntityLiving);
                }

                setCharged(pStack, false);
            }
        }
    }

    public static boolean isCharged(ItemStack obStack) {
        CompoundTag compoundnbt = obStack.getTag();
        return compoundnbt != null && compoundnbt.getBoolean("Charged");
    }

    public static void setCharged(ItemStack obStack, boolean pIsCharged) {
        CompoundTag compoundnbt = obStack.getOrCreateTag();
        compoundnbt.putBoolean("Charged", pIsCharged);
    }

    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);

        if (!isCharged(pPlayer.getItemInHand(pHand))) {
            return InteractionResultHolder.fail(itemstack);

        } else {
            pPlayer.startUsingItem(pHand);
            return InteractionResultHolder.consume(itemstack);
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        BPItemSettings.sacredLevelText(tooltip);

        tooltip.add(Component.translatable("item.bioplethora.alphanum_obliterator.skullbreaker.skill").withStyle(BPItemSettings.SKILL_NAME_COLOR));
        if (Screen.hasShiftDown() || Screen.hasControlDown()) {
            tooltip.add(Component.translatable("item.bioplethora.alphanum_obliterator.skullbreaker.desc").withStyle(BPItemSettings.SKILL_DESC_COLOR));
        }

        tooltip.add(Component.translatable("item.bioplethora.alphanum_obliterator.blasting_spears.skill").withStyle(BPItemSettings.SKILL_NAME_COLOR));
        if (Screen.hasShiftDown() || Screen.hasControlDown()) {
            tooltip.add(Component.translatable("item.bioplethora.alphanum_obliterator.blasting_spears.desc").withStyle(BPItemSettings.SKILL_DESC_COLOR));
        }
    }

    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        pStack.hurtAndBreak(1, pAttacker, (entity) -> {
            entity.broadcastBreakEvent(EquipmentSlot.MAINHAND);
        });
        pTarget.knockback(2, Mth.sin(pAttacker.yRot * ((float) Math.PI / 180F)), -Mth.cos(pAttacker.yRot * ((float) Math.PI / 180F)));
        pTarget.playSound(SoundEvents.ANVIL_PLACE, 1.0F, 0.75F);
        if (!pTarget.level.isClientSide()) {
            ((ServerLevel) pTarget.level).sendParticles(ParticleTypes.FIREWORK, pTarget.getX(), pTarget.getY(), pTarget.getZ(), 75, 0.75, 0.75, 0.75, 0.01);
            ((ServerLevel) pTarget.level).sendParticles(ParticleTypes.CRIT, pTarget.getX(), pTarget.getY(), pTarget.getZ(), 75, 0.75, 0.75, 0.75, 0.01);
        }
        return true;
    }

    public boolean mineBlock(ItemStack pStack, Level pLevel, BlockState pState, BlockPos pPos, LivingEntity pEntityLiving) {
        if ((double)pState.getDestroySpeed(pLevel, pPos) != 0.0D) {
            pStack.hurtAndBreak(2, pEntityLiving, (entity) -> {
                entity.broadcastBreakEvent(EquipmentSlot.MAINHAND);
            });
        }

        return true;
    }

    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot pEquipmentSlot, ItemStack stack) {
        return pEquipmentSlot == EquipmentSlot.MAINHAND ? this.defaultModifiers : super.getAttributeModifiers(pEquipmentSlot, stack);
    }

    public int getEnchantmentValue() {
        return 1;
    }

    @Override
    public double getReachDistance() {
        return 6;
    }
}