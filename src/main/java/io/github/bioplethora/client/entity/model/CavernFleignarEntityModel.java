package io.github.bioplethora.client.entity.model;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.entity.creatures.CavernFleignarEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class CavernFleignarEntityModel extends AnimatedGeoModel<CavernFleignarEntity> {

    @Override
    public ResourceLocation getModelResource(CavernFleignarEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "geo/cavern_fleignar.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(CavernFleignarEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "textures/entity/cavern_fleignar.png");
    }

    @Override
    public ResourceLocation getAnimationResource(CavernFleignarEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "animations/cavern_fleignar.animation.json");
    }

    @Override
    public void setLivingAnimations(CavernFleignarEntity entity, Integer uniqueID, @SuppressWarnings("rawtypes") AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
    }
}
