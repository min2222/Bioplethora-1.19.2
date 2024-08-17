package io.github.bioplethora.client.entity.render.others;

import io.github.bioplethora.client.entity.model.others.PrimordialRingEntityModel;
import io.github.bioplethora.client.entity.render.layer.PrimordialRingEntityGlowLayer;
import io.github.bioplethora.entity.others.PrimordialRingEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class PrimordialRingEntityRender extends GeoEntityRenderer<PrimordialRingEntity> {

    public PrimordialRingEntityRender(EntityRendererProvider.Context renderManager) {
        super(renderManager, new PrimordialRingEntityModel());
        this.shadowRadius = 0.5F;
        this.addRenderLayer(new PrimordialRingEntityGlowLayer(this));
    }
    
    @Override
    public RenderType getRenderType(PrimordialRingEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
    	return RenderType.entityTranslucent(texture);
    }

    @Override
    protected float getDeathMaxRotation(PrimordialRingEntity entity) {
        return 0.0F;
    }
}
