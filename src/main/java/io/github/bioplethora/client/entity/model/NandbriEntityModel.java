package io.github.bioplethora.client.entity.model;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.entity.creatures.NandbriEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class NandbriEntityModel extends GeoModel<NandbriEntity> {

    @Override
    public ResourceLocation getModelResource(NandbriEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "geo/nandbri.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(NandbriEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "textures/entity/nandbri.png");
    }

    @Override
    public ResourceLocation getAnimationResource(NandbriEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "animations/nandbri.animation.json");
    }

    @Override
    public void setCustomAnimations(NandbriEntity entity, long uniqueID, @SuppressWarnings("rawtypes") AnimationState customPredicate) {
        super.setCustomAnimations(entity, uniqueID, customPredicate);

        CoreGeoBone head = this.getAnimationProcessor().getBone("head");
        EntityModelData extraData = (EntityModelData) customPredicate.getExtraData().get(0);
        head.setRotX((extraData.headPitch()) * ((float) Math.PI / 180F));
        head.setRotY((extraData.netHeadYaw()) * ((float) Math.PI / 270F));
    }
}
