package io.github.bioplethora.client.block.model;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.blocks.tile_entities.FleignariteSplotchTileEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class FleignariteSplotchBlockModel extends GeoModel<FleignariteSplotchTileEntity> {

    @Override
    public ResourceLocation getModelResource(FleignariteSplotchTileEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "geo/blocks/fleignarite_splotch.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(FleignariteSplotchTileEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "textures/block/fleignarite_splotch.png");
    }

    @Override
    public ResourceLocation getAnimationResource(FleignariteSplotchTileEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "animations/blocks/fleignarite_splotch.animation.json");
    }
}
