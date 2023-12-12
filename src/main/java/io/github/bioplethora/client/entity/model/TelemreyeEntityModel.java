package io.github.bioplethora.client.entity.model;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.entity.creatures.TelemreyeEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class TelemreyeEntityModel extends AnimatedGeoModel<TelemreyeEntity> {

    @Override
    public ResourceLocation getModelResource(TelemreyeEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "geo/telemreye.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(TelemreyeEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "textures/entity/telemreye.png");
    }

    @Override
    public ResourceLocation getAnimationResource(TelemreyeEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "animations/telemreye.animation.json");
    }
}
