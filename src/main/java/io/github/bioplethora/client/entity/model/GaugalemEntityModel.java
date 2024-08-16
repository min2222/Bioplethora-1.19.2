package io.github.bioplethora.client.entity.model;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.entity.creatures.GaugalemEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class GaugalemEntityModel extends GeoModel<GaugalemEntity> {

    @Override
    public ResourceLocation getModelResource(GaugalemEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "geo/gaugalem.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(GaugalemEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "textures/entity/gaugalem.png");
    }

    @Override
    public ResourceLocation getAnimationResource(GaugalemEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "animations/gaugalem.animation.json");
    }

    @Override
    public void setCustomAnimations(GaugalemEntity entity, long uniqueID, @SuppressWarnings("rawtypes") AnimationState customPredicate) {
        super.setCustomAnimations(entity, uniqueID, customPredicate);

        CoreGeoBone head = this.getAnimationProcessor().getBone("gaugalem");
        EntityModelData extraData = (EntityModelData) customPredicate.getExtraData().get(0);
        head.setRotX((extraData.headPitch()) * ((float) Math.PI / 180F));
    }
}
