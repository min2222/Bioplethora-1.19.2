package io.github.bioplethora.client.entity.model;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.entity.creatures.NandbriEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class NandbriEntityModel extends AnimatedGeoModel<NandbriEntity> {

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
    public void setLivingAnimations(NandbriEntity entity, Integer uniqueID, @SuppressWarnings("rawtypes") AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);

        IBone head = this.getAnimationProcessor().getBone("head");
        EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
        head.setRotationX((extraData.headPitch) * ((float) Math.PI / 180F));
        head.setRotationY((extraData.netHeadYaw) * ((float) Math.PI / 270F));
    }
}
