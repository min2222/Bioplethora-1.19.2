package io.github.bioplethora.client.entity.model;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.entity.creatures.OnofishEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class OnofishEntityModel extends GeoModel<OnofishEntity> {

    @Override
    public ResourceLocation getModelResource(OnofishEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "geo/onofish.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(OnofishEntity entity) {
        switch (entity.getVariant()) {
            default: return new ResourceLocation(Bioplethora.MOD_ID, "textures/entity/onofish/onofish_purple.png");
            case 2: return new ResourceLocation(Bioplethora.MOD_ID, "textures/entity/onofish/onofish_blue.png");
            case 3: return new ResourceLocation(Bioplethora.MOD_ID, "textures/entity/onofish/onofish_cyan.png");
            case 4: return new ResourceLocation(Bioplethora.MOD_ID, "textures/entity/onofish/onofish_magenta.png");
        }
    }

    @Override
    public ResourceLocation getAnimationResource(OnofishEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "animations/onofish.animation.json");
    }
}
