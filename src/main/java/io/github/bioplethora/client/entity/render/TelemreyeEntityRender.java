package io.github.bioplethora.client.entity.render;

import io.github.bioplethora.client.entity.model.TelemreyeEntityModel;
import io.github.bioplethora.entity.creatures.TelemreyeEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class TelemreyeEntityRender extends GeoEntityRenderer<TelemreyeEntity> {

    public TelemreyeEntityRender(EntityRendererProvider.Context renderManager) {
        super(renderManager, new TelemreyeEntityModel());
        this.shadowRadius = 0.5F;
    }
    
    @Override
    public RenderType getRenderType(TelemreyeEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
    	return RenderType.entityTranslucent(texture);
    }

    @Override
    protected float getDeathMaxRotation(TelemreyeEntity entity) {
        return 0.0F;
    }
}
