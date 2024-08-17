package io.github.bioplethora.client.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import io.github.bioplethora.client.entity.model.MyliothanEntityModel;
import io.github.bioplethora.client.entity.render.layer.MyliothanEntityChargeLayer;
import io.github.bioplethora.entity.creatures.MyliothanEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class MyliothanEntityRender extends GeoEntityRenderer<MyliothanEntity> {

    public MyliothanEntityRender(EntityRendererProvider.Context renderManager) {
        super(renderManager, new MyliothanEntityModel());
        this.shadowRadius = 1.5F;
        this.addRenderLayer(new MyliothanEntityChargeLayer(this));
    }
    
    @Override
    public void preRender(PoseStack poseStack, MyliothanEntity animatable, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        float f = 1;
        f = (float) ((double) f * 6.0D);
        poseStack.scale(f, f, f);
        poseStack.translate(0, 0, 0.75);
    	super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
    }
    
    @Override
    public RenderType getRenderType(MyliothanEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
    	return RenderType.entityCutoutNoCull(texture);
    }
}
