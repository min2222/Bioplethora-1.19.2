package io.github.bioplethora.item.armor;

import java.util.function.Consumer;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.client.armor.model.NandbricArmorModel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
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
    public NandbricArmorItem(ArmorMaterial material, ArmorItem.Type slot, Properties properties) {
        super(material, slot, properties);
    }
    
    
    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
    	consumer.accept(new IClientItemExtensions()
    	{
    	    @Override
    	    @OnlyIn(Dist.CLIENT)
    	    public HumanoidModel<?> getHumanoidArmorModel(LivingEntity entity, ItemStack stack, EquipmentSlot slot, HumanoidModel<?> defaultModel) {
    	        return slot == EquipmentSlot.LEGS ? defaultModel : new NandbricArmorModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(NandbricArmorModel.LAYER_LOCATION));
    	    }
		});	
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slotType, String type) {
        String defaultTexture = Bioplethora.MOD_ID + ":textures/models/armor/nandbric_layer_1.png";
        String legTexture = Bioplethora.MOD_ID + ":textures/models/armor/nandbric_layer_2.png";

        return this.type == ArmorItem.Type.LEGGINGS ? legTexture : defaultTexture;
    }
}