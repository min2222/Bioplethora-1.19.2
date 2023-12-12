package io.github.bioplethora.client.entity.render.others;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import io.github.bioplethora.client.entity.model.others.PrimordialRingEntityModel;
import io.github.bioplethora.client.entity.render.layer.PrimordialRingEntityGlowLayer;
import io.github.bioplethora.entity.others.PrimordialRingEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class PrimordialRingEntityRender extends GeoEntityRenderer<PrimordialRingEntity> {

    public PrimordialRingEntityRender(EntityRendererProvider.Context renderManager) {
        super(renderManager, new PrimordialRingEntityModel());
        this.shadowRadius = 0.5F;
        this.addLayer(new PrimordialRingEntityGlowLayer(this));
    }

    @Override
    public void renderEarly(PrimordialRingEntity animatable, PoseStack stackIn, float ticks, MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float partialTicks) {
        super.renderEarly(animatable, stackIn, ticks, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, partialTicks);
    }

    @Override
    public RenderType getRenderType(PrimordialRingEntity animatable, float partialTicks, PoseStack stack, MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, ResourceLocation textureLocation) {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }

    @Override
    public void renderRecursively(GeoBone bone, PoseStack stack, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        super.renderRecursively(bone, stack, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }

    @Override
    protected float getDeathMaxRotation(PrimordialRingEntity entity) {
        return 0.0F;
    }
}
