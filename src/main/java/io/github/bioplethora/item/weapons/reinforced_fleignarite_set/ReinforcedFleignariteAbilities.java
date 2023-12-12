package io.github.bioplethora.item.weapons.reinforced_fleignarite_set;

import java.util.List;

import io.github.bioplethora.api.BPItemSettings;
import io.github.bioplethora.registry.BPDamageSources;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ReinforcedFleignariteAbilities {

    public static void hitSkill(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker instanceof Player) {
            if (!((Player) attacker).getCooldowns().isOnCooldown(stack.getItem())) {
                defaultHitSkill(target, attacker);
                ((Player) attacker).getCooldowns().addCooldown(stack.getItem(), 100);
            }
        } else {
            defaultHitSkill(target, attacker);
        }
    }

    public static void defaultHitSkill(LivingEntity target, LivingEntity attacker) {
        target.playSound(SoundEvents.ANVIL_PLACE, 1.0F, 0.75F);
        if (!target.level.isClientSide()) {
            ((ServerLevel) target.level).sendParticles(ParticleTypes.FIREWORK, target.getX(), target.getY(), target.getZ(), 75, 0.75, 0.75, 0.75, 0.01);
            ((ServerLevel) target.level).sendParticles(ParticleTypes.CRIT, target.getX(), target.getY(), target.getZ(), 75, 0.75, 0.75, 0.75, 0.01);
        }
        target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, target.getRandom().nextBoolean() ? 70 : 90, 2, false, false, false));
        target.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, target.getRandom().nextBoolean() ? 60 : 80, 0, false, false, false));
        target.addEffect(new MobEffectInstance(MobEffects.CONFUSION, target.getRandom().nextBoolean() ? 80 : 60, 1, false, false, false));

        target.knockback(2, Mth.sin(attacker.yRot * ((float) Math.PI / 180F)), -Mth.cos(attacker.yRot * ((float) Math.PI / 180F)));
        target.hurt(BPDamageSources.armorPiercingFleignarite(attacker, attacker), (float) 5);
    }

    public static void regenerateItem(ItemStack stack, Entity entity) {

        if (entity instanceof LivingEntity) {
            if (((LivingEntity) entity).getMainHandItem() != stack && ((LivingEntity) entity).getOffhandItem() != stack) {
                if (stack.getDamageValue() < stack.getMaxDamage()) {
                    stack.getOrCreateTag().putInt("regen_time", stack.getOrCreateTag().getInt("regen_time") + 1);

                    if (stack.getOrCreateTag().getInt("regen_time") >= 320) {
                        int i = Math.min((int) (2 * stack.getXpRepairRatio()), stack.getDamageValue());
                        stack.setDamageValue(stack.getDamageValue() - i);
                        stack.getOrCreateTag().putInt("regen_time", 0);
                    }
                }
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static void abilityTooltip(List<Component> tooltip) {
        BPItemSettings.sacredLevelText(tooltip);

        tooltip.add(Component.translatable("item.bioplethora.reinforced_fleignarite_weapon.deadly_blow.skill").withStyle(BPItemSettings.SKILL_NAME_COLOR));
        if (Screen.hasShiftDown() || Screen.hasControlDown()) {
            tooltip.add(Component.translatable("item.bioplethora.reinforced_fleignarite_weapon.deadly_blow.desc").withStyle(BPItemSettings.SKILL_DESC_COLOR));
        }
    }
}
