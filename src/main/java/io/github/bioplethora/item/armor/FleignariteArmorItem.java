package io.github.bioplethora.item.armor;

import java.util.List;

import javax.annotation.Nullable;

import io.github.bioplethora.api.BPItemSettings;
import io.github.bioplethora.api.IHurtSkillArmor;
import io.github.bioplethora.registry.BPDamageSources;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public class FleignariteArmorItem extends ArmorItem implements IHurtSkillArmor {

    public FleignariteArmorItem(ArmorMaterial material, ArmorItem.Type type, Item.Properties properties) {
        super(material, type, properties);
    }

    @Override
    public void hurtTrigger(LivingEntity user, LivingEntity attacker, ItemStack stack) {
        attacker.playSound(SoundEvents.SLIME_BLOCK_BREAK, 1.0F, 1.0F);
        attacker.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, attacker.getRandom().nextBoolean() ? 40 : 60, 1, false, false, false));
        attacker.addEffect(new MobEffectInstance(MobEffects.CONFUSION, attacker.getRandom().nextBoolean() ? 60 : 40, 0, false, false, false));

        attacker.hurt(BPDamageSources.armorPiercingFleignarite(attacker, attacker), (float) 2);
    }

    @Override
    public int getHurtAbilityCooldown() {
        return 120;
    }

    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pItemSlot, boolean pIsSelected) {
        if (pStack.getDamageValue() < pStack.getMaxDamage()) {
            pStack.getOrCreateTag().putInt("regen_time", pStack.getOrCreateTag().getInt("regen_time") + 1);

            if (pStack.getOrCreateTag().getInt("regen_time") == 400) {
                int i = Math.min((int) (1 * pStack.getXpRepairRatio()), pStack.getDamageValue());
                pStack.setDamageValue(pStack.getDamageValue() - i);
                pStack.getOrCreateTag().putInt("regen_time", 0);
            }
        }
        cdHelper(pStack);
        super.inventoryTick(pStack, pLevel, pEntity, pItemSlot, pIsSelected);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
        super.appendHoverText(pStack, pLevel, pTooltip, pFlag);

        BPItemSettings.sacredLevelText(pTooltip);

        pTooltip.add(Component.translatable("item.bioplethora.fleignarite_armor.sticky_piece.skill").withStyle(BPItemSettings.SKILL_NAME_COLOR));
        if (Screen.hasShiftDown() || Screen.hasControlDown()) {
            pTooltip.add(Component.translatable("item.bioplethora.fleignarite_armor.sticky_piece.desc").withStyle(BPItemSettings.SKILL_DESC_COLOR));
        }
    }
}
