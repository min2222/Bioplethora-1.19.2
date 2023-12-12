package io.github.bioplethora.client.entity.model.others;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.entity.others.GrylynenCoreBombEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class GrylynenCoreBombModel extends AnimatedGeoModel<GrylynenCoreBombEntity> {

    @Override
    public ResourceLocation getModelResource(GrylynenCoreBombEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "geo/others/grylynen_core_bomb.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(GrylynenCoreBombEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "textures/entity/others/grylynen_core_bomb.png");
    }

    @Override
    public ResourceLocation getAnimationResource(GrylynenCoreBombEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "animations/others/grylynen_core_bomb.animation.json");
    }
}
