package io.github.bioplethora.client.entity.render.projectile;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import io.github.bioplethora.client.entity.model.projectile.UltimateFrostbiteMetalClusterModel;
import io.github.bioplethora.entity.projectile.UltimateFrostbiteMetalClusterEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoEntityRenderer;


public class UltimateFrostbiteMetalClusterRender extends GeoEntityRenderer<UltimateFrostbiteMetalClusterEntity> {

    public UltimateFrostbiteMetalClusterRender(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new UltimateFrostbiteMetalClusterModel());
    }

    @Override
    public RenderType getRenderType(UltimateFrostbiteMetalClusterEntity animatable, float partialTicks, PoseStack stack, MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, ResourceLocation textureLocation) {
        return RenderType.eyes(getTextureLocation(animatable));
    }

    protected int getBlockLightLevel(UltimateFrostbiteMetalClusterEntity entityIn, BlockPos partialTicks) {
        return 15;
    }

    @Override
    public void renderEarly(UltimateFrostbiteMetalClusterEntity animatable, PoseStack stackIn, float ticks, MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float partialTicks) {
        super.renderEarly(animatable, stackIn, ticks, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, partialTicks);
    }

    @Override
    public void renderRecursively(GeoBone bone, PoseStack stack, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        super.renderRecursively(bone, stack, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }
}