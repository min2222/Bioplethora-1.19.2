package io.github.bioplethora.client.entity.model;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.entity.creatures.TrapjawEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class TrapjawEntityModel extends GeoModel<TrapjawEntity> {

    @Override
    public ResourceLocation getModelResource(TrapjawEntity entity) {
        if (!entity.isSaddled()) {
            return new ResourceLocation(Bioplethora.MOD_ID, "geo/trapjaw.geo.json");
        } else {
            return new ResourceLocation(Bioplethora.MOD_ID, "geo/trapjaw_saddled.geo.json");
        }
    }

    @Override
    public ResourceLocation getTextureResource(TrapjawEntity entity) {
        if (entity.isCardinalVariant()) {
            return new ResourceLocation(Bioplethora.MOD_ID, "textures/entity/cardinal_trapjaw.png");
        } else {
            return new ResourceLocation(Bioplethora.MOD_ID, "textures/entity/trapjaw.png");
        }
    }

    @Override
    public ResourceLocation getAnimationResource(TrapjawEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "animations/trapjaw.animation.json");
    }
}
