package io.github.bioplethora.client.entity.render.others;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import io.github.bioplethora.client.entity.model.others.BPEffectModel;
import io.github.bioplethora.entity.others.BPEffectEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;

public class BPEffectRender extends GeoProjectilesRenderer<BPEffectEntity> {

    public BPEffectRender(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new BPEffectModel());
    }

    @Override
    public RenderType getRenderType(BPEffectEntity animatable, float partialTicks, PoseStack stack, MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, ResourceLocation textureLocation) {
        return RenderType.entitySmoothCutout(getTextureLocation(animatable));
    }

    protected int getBlockLightLevel(BPEffectEntity entityIn, BlockPos partialTicks) {
        return 15;
    }

    @Override
    public void renderEarly(BPEffectEntity animatable, PoseStack stackIn, float ticks, MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float partialTicks) {
        float f = 1;
        f = (float) ((double) f * animatable.getEffectType().getScale());
        stackIn.scale(f, f, f);
        super.renderEarly(animatable, stackIn, ticks, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, partialTicks);
        super.renderEarly(animatable, stackIn, ticks, renderTypeBuffer, renderTypeBuffer.getBuffer(RenderType.eyes(getTextureLocation(animatable))), packedLightIn, packedOverlayIn, red, green, blue, partialTicks);
    }

    @Override
    public void renderLate(BPEffectEntity animatable, PoseStack stackIn, float partialTicks, MultiBufferSource renderTypeBuffer, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        //stackIn.mulPose(Vector3f.YP.rotationDegrees(Mth.lerp(partialTicks, animatable.yRotO, animatable.yRot) - 90.0F));
        super.renderLate(animatable, stackIn, partialTicks, renderTypeBuffer, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }

    @Override
    public void renderRecursively(GeoBone bone, PoseStack stack, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        super.renderRecursively(bone, stack, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }
}
