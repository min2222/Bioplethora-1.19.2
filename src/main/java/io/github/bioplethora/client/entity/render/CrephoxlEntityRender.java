package io.github.bioplethora.client.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.client.entity.model.CrephoxlEntityModel;
import io.github.bioplethora.entity.creatures.CrephoxlEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class CrephoxlEntityRender extends GeoEntityRenderer<CrephoxlEntity> {

    public CrephoxlEntityRender(EntityRendererProvider.Context renderManager) {
        super(renderManager, new CrephoxlEntityModel());
        this.shadowRadius = 1.5F;
    }

    @Override
    public void renderEarly(CrephoxlEntity animatable, PoseStack stackIn, float ticks, MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float partialTicks) {
        super.renderEarly(animatable, stackIn, ticks, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, partialTicks);
    }

    @Override
    public ResourceLocation getTextureLocation(CrephoxlEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "textures/entity/crephoxl.png");
    }

    @Override
    public RenderType getRenderType(CrephoxlEntity animatable, float partialTicks, PoseStack stack, MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, ResourceLocation textureLocation) {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }

    @Override
    public void renderRecursively(GeoBone bone, PoseStack matrixStack, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        super.renderRecursively(bone, matrixStack, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }

    @Override
    protected float getDeathMaxRotation(CrephoxlEntity entity) {
        return 0.0F;
    }
}
