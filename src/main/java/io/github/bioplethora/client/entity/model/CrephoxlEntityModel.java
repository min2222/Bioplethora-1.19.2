package io.github.bioplethora.client.entity.model;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.entity.creatures.CrephoxlEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class CrephoxlEntityModel extends GeoModel<CrephoxlEntity> {

    @Override
    public ResourceLocation getModelResource(CrephoxlEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "geo/crephoxl.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(CrephoxlEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "textures/entity/crephoxl.png");
    }

    @Override
    public ResourceLocation getAnimationResource(CrephoxlEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "animations/crephoxl.animation.json");
    }

    @Override
    public void setCustomAnimations(CrephoxlEntity entity, long uniqueID, @SuppressWarnings("rawtypes") AnimationState customPredicate) {
        super.setCustomAnimations(entity, uniqueID, customPredicate);

        CoreGeoBone head = this.getAnimationProcessor().getBone("neckbot");
        EntityModelData extraData = (EntityModelData) customPredicate.getExtraData().get(0);
        head.setRotX((extraData.headPitch()) * ((float) Math.PI / 180F));
        head.setRotY((extraData.netHeadYaw()) * ((float) Math.PI / 270F));
    }
}
