package io.github.bioplethora.client.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import io.github.bioplethora.client.entity.model.AlphemKingEntityModel;
import io.github.bioplethora.client.entity.render.layer.AlphemKingEntityBarrierLayer;
import io.github.bioplethora.client.entity.render.layer.AlphemKingEntityGlowLayer;
import io.github.bioplethora.entity.creatures.AlphemKingEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class AlphemKingEntityRender extends GeoEntityRenderer<AlphemKingEntity> {

    public AlphemKingEntityRender(EntityRendererProvider.Context renderManager) {
        super(renderManager, new AlphemKingEntityModel());
        this.addLayer(new AlphemKingEntityGlowLayer(this));
        this.addLayer(new AlphemKingEntityBarrierLayer(this));
        this.shadowRadius = 1.5F;
    }

    @Override
    protected void applyRotations(AlphemKingEntity entityLiving, PoseStack matrixStackIn, float ageInTicks, float rotationYaw, float partialTicks) {
        super.applyRotations(entityLiving, matrixStackIn, ageInTicks, rotationYaw, partialTicks);
        entityLiving.ageInTicks = ageInTicks;
    }

    @Override
    public void renderEarly(AlphemKingEntity animatable, PoseStack stackIn, float ticks, MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float partialTicks) {
        super.renderEarly(animatable, stackIn, ticks, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, partialTicks);
    }

    @Override
    public RenderType getRenderType(AlphemKingEntity animatable, float partialTicks, PoseStack stack, MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, ResourceLocation textureLocation) {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }

    @Override
    public void renderRecursively(GeoBone bone, PoseStack matrixStack, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        super.renderRecursively(bone, matrixStack, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }

    @Override
    protected float getDeathMaxRotation(AlphemKingEntity entity) {
        return 0.0F;
    }
}
