package io.github.bioplethora.client.entity.model;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.entity.creatures.CavernFleignarEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;

public class CavernFleignarEntityModel extends GeoModel<CavernFleignarEntity> {

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
    public void setCustomAnimations(CavernFleignarEntity entity, long uniqueID, @SuppressWarnings("rawtypes") AnimationState customPredicate) {
        super.setCustomAnimations(entity, uniqueID, customPredicate);
    }
}
