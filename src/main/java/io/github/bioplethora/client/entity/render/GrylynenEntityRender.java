package io.github.bioplethora.client.entity.render;

import io.github.bioplethora.client.entity.model.GrylynenEntityModel;
import io.github.bioplethora.client.entity.render.layer.GrylynenEntityGlowLayer;
import io.github.bioplethora.entity.creatures.GrylynenEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class GrylynenEntityRender extends GeoEntityRenderer<GrylynenEntity> {

    public GrylynenEntityRender(EntityRendererProvider.Context renderManager) {
        super(renderManager, new GrylynenEntityModel());
        this.shadowRadius = 1.0F;
        this.addRenderLayer(new GrylynenEntityGlowLayer(this));
    }

    @Override
    public RenderType getRenderType(GrylynenEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
    	return RenderType.entityTranslucent(texture);
    }

    @Override
    protected float getDeathMaxRotation(GrylynenEntity entity) {
        return 0.0F;
    }
}
