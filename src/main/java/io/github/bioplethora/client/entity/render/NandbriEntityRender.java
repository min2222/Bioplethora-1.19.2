package io.github.bioplethora.client.entity.render;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.client.entity.model.NandbriEntityModel;
import io.github.bioplethora.entity.creatures.NandbriEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class NandbriEntityRender extends GeoEntityRenderer<NandbriEntity> {

    public NandbriEntityRender(EntityRendererProvider.Context renderManager) {
        super(renderManager, new NandbriEntityModel());
        this.shadowRadius = 1.1F;
    }

    @Override
    public ResourceLocation getTextureLocation(NandbriEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "textures/entity/nandbri.png");
    }

    @Override
    public RenderType getRenderType(NandbriEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
    	return RenderType.entityTranslucent(texture);
    }
}
