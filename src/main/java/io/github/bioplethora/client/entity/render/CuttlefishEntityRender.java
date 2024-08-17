package io.github.bioplethora.client.entity.render;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.client.entity.model.CuttlefishEntityModel;
import io.github.bioplethora.entity.creatures.CuttlefishEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class CuttlefishEntityRender extends GeoEntityRenderer<CuttlefishEntity> {

    public CuttlefishEntityRender(EntityRendererProvider.Context renderManager) {
        super(renderManager, new CuttlefishEntityModel());
        this.shadowRadius = 0.5F;
    }

    @Override
    public ResourceLocation getTextureLocation(CuttlefishEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "textures/entity/cuttlefish.png");
    }

    @Override
    public RenderType getRenderType(CuttlefishEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
    	return RenderType.entityTranslucent(texture);
    }

    @Override
    protected float getDeathMaxRotation(CuttlefishEntity entity) {
        return 0.0F;
    }
}
