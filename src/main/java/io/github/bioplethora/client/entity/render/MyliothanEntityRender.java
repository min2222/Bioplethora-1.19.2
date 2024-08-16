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
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class MyliothanEntityRender extends GeoEntityRenderer<MyliothanEntity> {

    public MyliothanEntityRender(EntityRendererProvider.Context renderManager) {
        super(renderManager, new MyliothanEntityModel());
        this.shadowRadius = 1.5F;
        this.addLayer(new MyliothanEntityChargeLayer(this));
    }

    @Override
    public void renderEarly(MyliothanEntity animatable, PoseStack stackIn, float ticks, MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float partialTicks) {
        float f = 1;
        f = (float) ((double) f * 6.0D);
        stackIn.scale(f, f, f);
        stackIn.translate(0, 0, 0.75);
        super.renderEarly(animatable, stackIn, ticks, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, partialTicks);
    }

    @Override
    public RenderType getRenderType(MyliothanEntity animatable, float partialTicks, PoseStack stack, MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, ResourceLocation textureLocation) {
        return RenderType.entityCutoutNoCull(getTextureLocation(animatable));
    }

    @Override
    public void renderRecursively(GeoBone bone, PoseStack stack, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        super.renderRecursively(bone, stack, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }
}
