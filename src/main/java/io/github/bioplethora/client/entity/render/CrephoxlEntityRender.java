package io.github.bioplethora.client.entity.render;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.client.entity.model.CrephoxlEntityModel;
import io.github.bioplethora.entity.creatures.CrephoxlEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class CrephoxlEntityRender extends GeoEntityRenderer<CrephoxlEntity> {

    public CrephoxlEntityRender(EntityRendererProvider.Context renderManager) {
        super(renderManager, new CrephoxlEntityModel());
        this.shadowRadius = 1.5F;
    }

    @Override
    public ResourceLocation getTextureLocation(CrephoxlEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "textures/entity/crephoxl.png");
    }

    @Override
    public RenderType getRenderType(CrephoxlEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
    	return RenderType.entityTranslucent(texture);
    }

    @Override
    protected float getDeathMaxRotation(CrephoxlEntity entity) {
        return 0.0F;
    }
}
