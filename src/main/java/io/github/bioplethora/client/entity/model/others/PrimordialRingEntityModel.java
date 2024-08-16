package io.github.bioplethora.client.entity.model.others;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.entity.others.PrimordialRingEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class PrimordialRingEntityModel extends GeoModel<PrimordialRingEntity> {

    @Override
    public ResourceLocation getModelResource(PrimordialRingEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "geo/others/primordial_ring.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(PrimordialRingEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "textures/entity/layers/altyrus_glow_layer_summoning.png");
    }

    @Override
    public ResourceLocation getAnimationResource(PrimordialRingEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "animations/others/primordial_ring.animation.json");
    }
}
