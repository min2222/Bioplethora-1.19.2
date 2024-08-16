package io.github.bioplethora.client.entity.model.others;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.entity.others.AltyrusSummoningEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class AltyrusSummoningModel extends GeoModel<AltyrusSummoningEntity> {

    @Override
    public ResourceLocation getModelResource(AltyrusSummoningEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "geo/others/altyrus_summoning.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(AltyrusSummoningEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "textures/entity/others/altyrus_summoning.png");
    }

    @Override
    public ResourceLocation getAnimationResource(AltyrusSummoningEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "animations/others/altyrus_summoning.animation.json");
    }
}
