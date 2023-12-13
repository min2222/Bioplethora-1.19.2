package io.github.bioplethora.item.armor;

import java.util.function.Consumer;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.api.IHurtSkillArmor;
import io.github.bioplethora.api.world.EffectUtils;
import io.github.bioplethora.api.world.EntityUtils;
import io.github.bioplethora.client.armor.model.ReinforcedFleignariteArmorModel;
import io.github.bioplethora.registry.BPDamageSources;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

public class ReinforcedFleignariteArmorItem extends ArmorItem implements IHurtSkillArmor {

    public ReinforcedFleignariteArmorItem(ArmorMaterial material, EquipmentSlot type, Properties properties) {
        super(material, type, properties);
    }

    @Override
    public void hurtTrigger(LivingEntity user, LivingEntity target, ItemStack stack) {
        target.playSound(SoundEvents.ANVIL_PLACE, 1.0F, 0.75F);

        EffectUtils.addCircleParticleForm(target.level, target, ParticleTypes.FIREWORK, 75, 0.75, 0.01);
        EffectUtils.addCircleParticleForm(target.level, target, ParticleTypes.CRIT, 75, 0.75, 0.01);

        boolean random = target.getRandom().nextBoolean();
        EffectUtils.addEffectNoIcon(target, MobEffects.MOVEMENT_SLOWDOWN, random ? 70 : 90, 2);
        EffectUtils.addEffectNoIcon(target, MobEffects.DIG_SLOWDOWN, random ? 60 : 80, 0);
        EffectUtils.addEffectNoIcon(target, MobEffects.CONFUSION, random ? 80 : 60, 1);

        EntityUtils.knockbackAwayFromUser(0.5F, user, target);
        target.hurt(BPDamageSources.armorPiercingFleignarite(target, target), (float) 2);
    }

    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pItemSlot, boolean pIsSelected) {
        if (pStack.getDamageValue() < pStack.getMaxDamage()) {
            pStack.getOrCreateTag().putInt("regen_time", pStack.getOrCreateTag().getInt("regen_time") + 1);

            if (pStack.getOrCreateTag().getInt("regen_time") == 260) {
                int i = Math.min((int) (2 * pStack.getXpRepairRatio()), pStack.getDamageValue());
                pStack.setDamageValue(pStack.getDamageValue() - i);
                pStack.getOrCreateTag().putInt("regen_time", 0);
            }
        }
        cdHelper(pStack);
        super.inventoryTick(pStack, pLevel, pEntity, pItemSlot, pIsSelected);
    }

    @Override
    public int getHurtAbilityCooldown() {
        return 80;
    }
    
    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
    	consumer.accept(new IClientItemExtensions() {

    	    @Override
    	    public HumanoidModel<?> getHumanoidArmorModel(LivingEntity entity, ItemStack stack, EquipmentSlot slot, HumanoidModel<?> defaultModel) {
    	        return slot == EquipmentSlot.LEGS ? defaultModel : new ReinforcedFleignariteArmorModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(ReinforcedFleignariteArmorModel.LAYER_LOCATION));
    	    }
    	});
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        String defaultTexture = Bioplethora.MOD_ID + ":textures/models/armor/reinforced_fleignarite_layer_1.png";
        String legTexture = Bioplethora.MOD_ID + ":textures/models/armor/reinforced_fleignarite_layer_2.png";

        return slot == EquipmentSlot.LEGS ? legTexture : defaultTexture;
    }
}
