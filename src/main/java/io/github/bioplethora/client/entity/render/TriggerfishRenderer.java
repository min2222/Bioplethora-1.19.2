package io.github.bioplethora.client.entity.render;

import io.github.bioplethora.client.entity.model.TriggerfishEntityModel;
import io.github.bioplethora.entity.creatures.TriggerfishEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class TriggerfishRenderer extends GeoEntityRenderer<TriggerfishEntity>  {

    public TriggerfishRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new TriggerfishEntityModel());
        this.shadowRadius = 0.5F;
    }
    
    @Override
    public RenderType getRenderType(TriggerfishEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
    	return RenderType.entityTranslucent(texture);
    }

    @Override
    protected float getDeathMaxRotation(TriggerfishEntity entityLivingBaseIn) {
        return 0.0F;
    }
}
