package io.github.bioplethora.client.entity.render;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.client.entity.model.TerraithEntityModel;
import io.github.bioplethora.entity.creatures.TerraithEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class TerraithEntityRender extends GeoEntityRenderer<TerraithEntity> {
    public TerraithEntityRender(EntityRendererProvider.Context rendererManager) {
        super(rendererManager, new TerraithEntityModel());
        this.shadowRadius = 1F;
    }

    @Override
    public ResourceLocation getTextureLocation(TerraithEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "textures/entity/terraith.png");
    }

    @Override
    public RenderType getRenderType(TerraithEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
    	return RenderType.entityTranslucent(texture);
    }
}
