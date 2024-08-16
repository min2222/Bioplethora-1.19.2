package io.github.bioplethora.item.weapons;

import java.util.List;

import javax.annotation.Nullable;

import io.github.bioplethora.api.BPItemSettings;
import io.github.bioplethora.api.IReachWeapon;
import io.github.bioplethora.api.world.ItemUtils;
import io.github.bioplethora.entity.others.BPEffectEntity;
import io.github.bioplethora.entity.projectile.VermilionBladeProjectileEntity;
import io.github.bioplethora.enums.BPEffectTypes;
import io.github.bioplethora.registry.BPParticles;
import io.github.bioplethora.registry.BPSoundEvents;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class VermilionBladeItem extends SwordItem implements IReachWeapon {

    public int bladeSize;

    public VermilionBladeItem(Tier tier, int attackDamageIn, float attackSpeedIn, Item.Properties builder) {
        super(tier, attackDamageIn, attackSpeedIn, builder);
        this.bladeSize = 1;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {

        if (!(worldIn instanceof ServerLevel)) {
            return new InteractionResultHolder<>(InteractionResult.PASS, playerIn.getItemInHand(handIn));
        }
        this.shootBlade(playerIn.getItemInHand(handIn), playerIn, worldIn);

        return new InteractionResultHolder<>(InteractionResult.SUCCESS, playerIn.getItemInHand(handIn));
    }

    public void emptySwingHandler(ItemStack stack, LivingEntity entity) {
        if (entity instanceof Player) {
            Player player = (Player) entity;
            if (!player.getCooldowns().isOnCooldown(stack.getItem())) {
                BPEffectEntity.createInstance(entity, BPEffectTypes.SHACHATH_SLASH_FLAT);
                entity.playSound(BPSoundEvents.SHACHATH_SLASH.get(), 0.75F, 0.75F + entity.getRandom().nextFloat());
                if (entity.getRandom().nextBoolean()) {
                    for (LivingEntity entities : entity.level.getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate(2.4, 0, 2.4))) {
                        if (entities != entity) {
                            double xa = entities.getX(), ya = entities.getY() + 1, za = entities.getZ();
                            entities.hurt(entity.damageSources().mobAttack(entity), 6F + (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SWEEPING_EDGE, stack) * 1.5F));
                            entity.level.addParticle(BPParticles.SHACHATH_CLASH_INNER.get(), xa, ya, za, 0, 0, 0);
                            entity.level.addParticle(BPParticles.SHACHATH_CLASH_OUTER.get(), xa, ya, za, 0, 0, 0);
                        }
                    }
                } else {
                    double d0 = -Mth.sin(entity.yRot * ((float) Math.PI / 180F)) * 6;
                    double d1 = Mth.cos(entity.yRot * ((float) Math.PI / 180F)) * 6;
                    BPEffectEntity.createInstance(entity, BPEffectTypes.SHACHATH_SLASH_FRONT);
                    entity.playSound(BPSoundEvents.SHACHATH_SLASH.get(), 0.75F, 0.75F + entity.getRandom().nextFloat());
                    for (LivingEntity entities : entity.level.getEntitiesOfClass(LivingEntity.class, new AABB(entity.getX() - d0, entity.getY() - 2.5, entity.getZ() - d1, entity.getX() + d0, entity.getY() + 2.5, entity.getZ() + d1))) {
                        if (entities != entity) {
                            double xa = entities.getX(), ya = entities.getY() + 1, za = entities.getZ();
                            entities.hurt(entity.damageSources().mobAttack(entity), 8F + (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SWEEPING_EDGE, stack) * 1.5F));
                            entity.level.addParticle(BPParticles.SHACHATH_CLASH_INNER.get(), xa, ya, za, 0, 0, 0);
                            entity.level.addParticle(BPParticles.SHACHATH_CLASH_OUTER.get(), xa, ya, za, 0, 0, 0);
                        }
                    }
                }
                player.getCooldowns().addCooldown(stack.getItem(), 10);
            }
        }
    }

    @Override
    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        this.emptySwingHandler(pStack, pAttacker);
        return super.hurtEnemy(pStack, pTarget, pAttacker);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        BPItemSettings.bossLevelText(tooltip);

        tooltip.add(Component.translatable("item.bioplethora.vermilion_blade.blade_master.skill").withStyle(BPItemSettings.SKILL_NAME_COLOR));
        if (Screen.hasShiftDown() || Screen.hasControlDown()) {
            tooltip.add(Component.translatable("item.bioplethora.vermilion_blade.blade_master.desc").withStyle(BPItemSettings.SKILL_DESC_COLOR));
        }

        tooltip.add(Component.translatable("item.bioplethora.vermilion_blade.pure_energy_concentration.skill").withStyle(BPItemSettings.SKILL_NAME_COLOR));
        if (Screen.hasShiftDown() || Screen.hasControlDown()) {
            tooltip.add(Component.translatable("item.bioplethora.vermilion_blade.pure_energy_concentration.desc").withStyle(BPItemSettings.SKILL_DESC_COLOR));
        }
    }

    public void shootBlade(ItemStack stack, LivingEntity entity, Level world) {

        if (ItemUtils.checkCooldownUsable(entity, stack)) {
        	VermilionBladeProjectileEntity projectile = new VermilionBladeProjectileEntity(world, entity, ItemUtils.projAngleX(entity), ItemUtils.projAngleY(entity), ItemUtils.projAngleZ(entity));
            projectile.setBladeSize(this.bladeSize);
            ItemUtils.shootWithItemBreakable(entity, projectile, world, stack, 1);

            entity.playSound(SoundEvents.BLAZE_SHOOT, 1.0F, 1.0F / (entity.getRandom().nextFloat() * 0.4F + 1.2F) + 0.5F);
            ++this.bladeSize;
            if (this.bladeSize >= 5) bladeSize = 1;

            ItemUtils.setStackOnCooldown(entity, stack, (5 * (this.bladeSize * 2)) - (EnchantmentHelper.getEnchantmentLevel(Enchantments.QUICK_CHARGE, entity) * 5), true);
        }
    }

    @Override
    public boolean canDisableShield(ItemStack stack, ItemStack shield, LivingEntity entity, LivingEntity attacker) {
        return true;
    }

    @Override
    public double getReachDistance() {
        return 7;
    }
}
