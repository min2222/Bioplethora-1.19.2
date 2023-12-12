package io.github.bioplethora.client.entity.model;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.entity.creatures.AlphemEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class AlphemEntityModel extends AnimatedGeoModel<AlphemEntity> {

    @Override
    public ResourceLocation getModelResource(AlphemEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "geo/alphem.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(AlphemEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "textures/entity/alphem.png");
    }

    @Override
    public ResourceLocation getAnimationResource(AlphemEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "animations/alphem.animation.json");
    }
}
