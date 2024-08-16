package io.github.bioplethora.client.entity.model.projectile;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.entity.projectile.CryoblazeEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class CryoblazeModel extends GeoModel<CryoblazeEntity> {

    @Override
    public ResourceLocation getModelResource(CryoblazeEntity object) {
        return new ResourceLocation(Bioplethora.MOD_ID, "geo/projectiles/cryoblaze.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(CryoblazeEntity object) {
        return new ResourceLocation(Bioplethora.MOD_ID, "textures/projectiles/cryoblaze.png");
    }

    @Override
    public ResourceLocation getAnimationResource(CryoblazeEntity animatable) {
        return new ResourceLocation(Bioplethora.MOD_ID, "animations/projectiles/cryoblaze.animation.json");
    }
}
