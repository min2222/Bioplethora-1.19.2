package io.github.bioplethora.client.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.client.entity.model.TerraithEntityModel;
import io.github.bioplethora.entity.creatures.TerraithEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class TerraithEntityRender extends GeoEntityRenderer<TerraithEntity> {
    public TerraithEntityRender(EntityRendererProvider.Context rendererManager) {
        super(rendererManager, new TerraithEntityModel());
        this.shadowRadius = 1F;
    }

    @Override
    public void renderEarly(TerraithEntity animatable, PoseStack stackIn, float ticks, MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float partialTicks) {
        super.renderEarly(animatable, stackIn, ticks, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, partialTicks);
    }

    @Override
    public ResourceLocation getTextureLocation(TerraithEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "textures/entity/terraith.png");
    }

    @Override
    public RenderType getRenderType(TerraithEntity animatable, float partialTicks, PoseStack stack, MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, ResourceLocation textureLocation) {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }

    @Override
    public void renderRecursively(GeoBone bone, PoseStack matrixStack, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        super.renderRecursively(bone, matrixStack, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }
}
