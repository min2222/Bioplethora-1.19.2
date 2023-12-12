package io.github.bioplethora.potion;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;

public class SpiritManipulationEffect extends MobEffect {

    int armorRegenTimer;

    public SpiritManipulationEffect(MobEffectCategory effectType, int potionParticleColor) {
        super(effectType, potionParticleColor);
        armorRegenTimer = 0;
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        ++armorRegenTimer;
        if (armorRegenTimer == 60) {
            for (EquipmentSlot type : EquipmentSlot.values()) {
                if (type != EquipmentSlot.MAINHAND && type != EquipmentSlot.OFFHAND) {
                    pLivingEntity.getItemBySlot(type).setDamageValue(pLivingEntity.getItemBySlot(type).getDamageValue() + 2);
                }
            }

            armorRegenTimer = 0;
        }
        super.applyEffectTick(pLivingEntity, pAmplifier);
    }
}
