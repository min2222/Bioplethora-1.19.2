package io.github.bioplethora.client.entity.render;

import io.github.bioplethora.client.entity.model.PeaguinEntityModel;
import io.github.bioplethora.entity.creatures.PeaguinEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class PeaguinEntityRender extends GeoEntityRenderer<PeaguinEntity> {

    public PeaguinEntityRender(EntityRendererProvider.Context renderManager) {
        super(renderManager, new PeaguinEntityModel());
        this.shadowRadius = 0.5F;
    }
    
    @Override
    public RenderType getRenderType(PeaguinEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
    	return RenderType.entityTranslucent(texture);
    }

    @Override
    protected float getDeathMaxRotation(PeaguinEntity entity) {
        return 0.0F;
    }
}
