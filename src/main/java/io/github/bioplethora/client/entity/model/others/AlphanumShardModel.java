package io.github.bioplethora.client.entity.model.others;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.entity.others.AlphanumShardEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class AlphanumShardModel extends GeoModel<AlphanumShardEntity> {

    @Override
    public ResourceLocation getModelResource(AlphanumShardEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "geo/others/alphanum_shard.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(AlphanumShardEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "textures/entity/others/alphanum_shard.png");
    }

    @Override
    public ResourceLocation getAnimationResource(AlphanumShardEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "animations/others/alphanum_shard.animation.json");
    }
}
