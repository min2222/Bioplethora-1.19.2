package io.github.bioplethora.client.entity.model;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.entity.creatures.GrylynenEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class GrylynenEntityModel extends GeoModel<GrylynenEntity> {

    @Override
    public ResourceLocation getModelResource(GrylynenEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "geo/grylynen.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(GrylynenEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "textures/entity/grylynen/grylynen_" + entity.getGrylynenTier().getTierName() + ".png");
    }

    @Override
    public ResourceLocation getAnimationResource(GrylynenEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "animations/grylynen.animation.json");
    }

    @Override
    public void setCustomAnimations(GrylynenEntity entity, long uniqueID, @SuppressWarnings("rawtypes") AnimationState customPredicate) {
        super.setCustomAnimations(entity, uniqueID, customPredicate);

        CoreGeoBone head = this.getAnimationProcessor().getBone("head");
        EntityModelData extraData = (EntityModelData) customPredicate.getExtraData().get(0);
        head.setRotX((extraData.headPitch()) * ((float) Math.PI / 180F));
        head.setRotY((extraData.netHeadYaw()) * ((float) Math.PI / 270F));
    }
}
