package io.github.bioplethora.client.entity.render;

import io.github.bioplethora.client.entity.model.OnofishEntityModel;
import io.github.bioplethora.entity.creatures.OnofishEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class OnofishRenderer extends GeoEntityRenderer<OnofishEntity>  {

    public OnofishRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new OnofishEntityModel());
        this.shadowRadius = 1.1F;
    }

    @Override
    public RenderType getRenderType(OnofishEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
    	return RenderType.entityTranslucent(texture);
    }

    @Override
    protected float getDeathMaxRotation(OnofishEntity entityLivingBaseIn) {
        return 0.0F;
    }
}
