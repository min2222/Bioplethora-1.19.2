package io.github.bioplethora.client.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import io.github.bioplethora.client.entity.model.EurydnEntityModel;
import io.github.bioplethora.client.entity.render.layer.EurydnEntityGlowLayer;
import io.github.bioplethora.entity.creatures.EurydnEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class EurydnEntityRender extends GeoEntityRenderer<EurydnEntity> {

    public EurydnEntityRender(EntityRendererProvider.Context renderManager) {
        super(renderManager, new EurydnEntityModel());
        this.addRenderLayer(new EurydnEntityGlowLayer(this));
        this.shadowRadius = 0.5F;
    }
    
    @Override
    public void preRender(PoseStack poseStack, EurydnEntity animatable, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        float size = 1.2F;
        poseStack.scale(size, size, size);
    	super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
    }
    
    @Override
    public RenderType getRenderType(EurydnEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
    	return RenderType.entityTranslucent(texture);
    }
}
