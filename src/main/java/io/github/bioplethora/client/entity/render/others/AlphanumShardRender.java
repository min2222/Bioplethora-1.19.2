package io.github.bioplethora.client.entity.render.others;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import io.github.bioplethora.client.entity.model.others.AlphanumShardModel;
import io.github.bioplethora.entity.others.AlphanumShardEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class AlphanumShardRender extends GeoEntityRenderer<AlphanumShardEntity> {

    public AlphanumShardRender(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new AlphanumShardModel());
    }

    @Override
    public RenderType getRenderType(AlphanumShardEntity animatable, float partialTicks, PoseStack stack, MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, ResourceLocation textureLocation) {
        return RenderType.entitySmoothCutout(getTextureLocation(animatable));
    }

    protected int getBlockLightLevel(AlphanumShardEntity entityIn, BlockPos partialTicks) {
        return 10;
    }

    @Override
    public void renderEarly(AlphanumShardEntity animatable, PoseStack stackIn, float ticks, MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float partialTicks) {
        super.renderEarly(animatable, stackIn, ticks, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, partialTicks);
    }

    @Override
    public void renderRecursively(GeoBone bone, PoseStack stack, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        super.renderRecursively(bone, stack, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }
}
