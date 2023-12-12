package io.github.bioplethora.item.armor;

import java.util.function.Consumer;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.client.armor.model.NandbricArmorModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

public class NandbricArmorItem extends ArmorItem {
    public NandbricArmorItem(ArmorMaterial material, EquipmentSlot slot, Properties properties) {
        super(material, slot, properties);
    }
    
    
    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
    	consumer.accept(new IClientItemExtensions()
    	{
    	    @Override
    	    @OnlyIn(Dist.CLIENT)
    	    public HumanoidModel<?> getHumanoidArmorModel(LivingEntity entity, ItemStack stack, EquipmentSlot slot, HumanoidModel<?> defaultModel) {
    	        return slot == EquipmentSlot.LEGS ? defaultModel : matchingModel(entity, slot, defaultModel);
    	    }
		});
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slotType, String type) {
        String defaultTexture = Bioplethora.MOD_ID + ":textures/models/armor/nandbric_layer_1.png";
        String legTexture = Bioplethora.MOD_ID + ":textures/models/armor/nandbric_layer_2.png";

        return slot == EquipmentSlot.LEGS ? legTexture : defaultTexture;
    }

    @OnlyIn(Dist.CLIENT)
    public static <A extends HumanoidModel<?>> A matchingModel(LivingEntity entity, EquipmentSlot slot, A defaultModel) {
        boolean crouching = entity.isCrouching();
        boolean riding = defaultModel.riding;
        boolean young = entity.isBaby();

        switch (slot) {
            case HEAD:
                BipedModel helmet = new BipedModel(1);
                helmet.head = new NandbricArmorModel<>().helmet;
                helmet.crouching = crouching;
                helmet.riding = riding;
                helmet.young = young;
                return (A) helmet;
            case CHEST:
                HumanoidModel<?> chestplate = new BipedModel(1);
                chestplate.body = new NandbricArmorModel<>().chestplate;
                chestplate.leftArm = new NandbricArmorModel<>().leftarm;
                chestplate.rightArm = new NandbricArmorModel<>().rightarm;
                chestplate.crouching = crouching;
                chestplate.riding = riding;
                chestplate.young = young;
                return (A) chestplate;
            case FEET:
                BipedModel boots = new BipedModel(1);
                boots.leftLeg = new NandbricArmorModel<>().leftboot;
                boots.rightLeg = new NandbricArmorModel<>().rightboot;
                boots.crouching = crouching;
                boots.riding = riding;
                boots.young = young;
                return (A) boots;
		default:
			break;
        }
        return defaultModel;
    }
}