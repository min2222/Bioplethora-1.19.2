package io.github.bioplethora.client.entity.model;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.entity.creatures.TriggerfishEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class TriggerfishEntityModel extends AnimatedGeoModel<TriggerfishEntity> {

    @Override
    public ResourceLocation getModelResource(TriggerfishEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "geo/triggerfish.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(TriggerfishEntity entity) {
        if (entity.getIsEnd()) {
            return new ResourceLocation(Bioplethora.MOD_ID, "textures/entity/triggerfish_end.png");
        } else {
            return new ResourceLocation(Bioplethora.MOD_ID, "textures/entity/triggerfish.png");
        }
    }

    @Override
    public ResourceLocation getAnimationResource(TriggerfishEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "animations/triggerfish.animation.json");
    }
}
