package io.github.bioplethora.item.weapons.fleignarite_set;

import java.util.List;

import io.github.bioplethora.api.BPItemSettings;
import io.github.bioplethora.registry.BPDamageSources;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class FleignariteAbilities {

    public static void hitSkill(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker instanceof Player) {
            if (!((Player) attacker).getCooldowns().isOnCooldown(stack.getItem())) {
                defaultHitSkill(target, attacker);
                ((Player) attacker).getCooldowns().addCooldown(stack.getItem(), 60);
            }
        } else {
            defaultHitSkill(target, attacker);
        }
    }

    public static void defaultHitSkill(LivingEntity target, LivingEntity attacker) {
        target.playSound(SoundEvents.SLIME_BLOCK_BREAK, 1.0F, 1.0F);
        target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, target.getRandom().nextBoolean() ? 40 : 60, 1, false, false, false));
        target.addEffect(new MobEffectInstance(MobEffects.CONFUSION, target.getRandom().nextBoolean() ? 60 : 40, 0, false, false, false));

        target.hurt(BPDamageSources.armorPiercingFleignarite(attacker, attacker), (float) 2);
    }

    public static void regenerateItem(ItemStack stack, Entity entity) {

        if (entity instanceof LivingEntity) {
            if (((LivingEntity) entity).getMainHandItem() != stack && ((LivingEntity) entity).getOffhandItem() != stack) {
                if (stack.getDamageValue() < stack.getMaxDamage()) {
                    stack.getOrCreateTag().putInt("regen_time", stack.getOrCreateTag().getInt("regen_time") + 1);

                    if (stack.getOrCreateTag().getInt("regen_time") == 400) {
                        int i = Math.min((int) (1 * stack.getXpRepairRatio()), stack.getDamageValue());
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

        tooltip.add(Component.translatable("item.bioplethora.fleignarite_weapon.gooey_stun.skill").withStyle(BPItemSettings.SKILL_NAME_COLOR));
        if (Screen.hasShiftDown() || Screen.hasControlDown()) {
            tooltip.add(Component.translatable("item.bioplethora.fleignarite_weapon.gooey_stun.desc").withStyle(BPItemSettings.SKILL_DESC_COLOR));
        }
    }
}
