package io.github.bioplethora.client.entity.model;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.entity.creatures.CuttlefishEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class CuttlefishEntityModel extends GeoModel<CuttlefishEntity> {

    @Override
    public ResourceLocation getModelResource(CuttlefishEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "geo/cuttlefish.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(CuttlefishEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "textures/entity/cuttlefish.png");
    }

    @Override
    public ResourceLocation getAnimationResource(CuttlefishEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "animations/cuttlefish.animation.json");
    }
}
