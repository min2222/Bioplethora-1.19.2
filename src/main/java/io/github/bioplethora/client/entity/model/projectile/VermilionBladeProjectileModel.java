package io.github.bioplethora.client.entity.model.projectile;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.entity.projectile.VermilionBladeProjectileEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class VermilionBladeProjectileModel extends GeoModel<VermilionBladeProjectileEntity> {

    @Override
    public ResourceLocation getModelResource(VermilionBladeProjectileEntity object) {
        return new ResourceLocation(Bioplethora.MOD_ID, "geo/projectiles/vermilion_blade_projectile.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(VermilionBladeProjectileEntity object) {
        return new ResourceLocation(Bioplethora.MOD_ID, "textures/item/vermilion_blade.png");
    }

    @Override
    public ResourceLocation getAnimationResource(VermilionBladeProjectileEntity animatable) {
        return new ResourceLocation(Bioplethora.MOD_ID, "animations/projectiles/vermilion_blade_projectile.animation.json");
    }

    @Override
    public void setCustomAnimations(VermilionBladeProjectileEntity entity, long uniqueID, @SuppressWarnings("rawtypes") AnimationState customPredicate) {
        super.setCustomAnimations(entity, uniqueID, customPredicate);

        CoreGeoBone head = this.getAnimationProcessor().getBone("vermilion_blade");
        EntityModelData extraData = (EntityModelData) customPredicate.getData(DataTickets.ENTITY_MODEL_DATA);
        head.setRotX((extraData.headPitch()) * ((float) Math.PI / 180F));
        head.setRotY((extraData.netHeadYaw()) * ((float) Math.PI / 270F));
    }
}
