package io.github.bioplethora.client.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;

import io.github.bioplethora.client.entity.model.CavernFleignarEntityModel;
import io.github.bioplethora.entity.creatures.CavernFleignarEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class CavernFleignarEntityRender extends GeoEntityRenderer<CavernFleignarEntity> {

    public CavernFleignarEntityRender(EntityRendererProvider.Context renderManager) {
        super(renderManager, new CavernFleignarEntityModel());
        this.shadowRadius = 1.0F;
    }

    /*
    @Override
    public void renderEarly(CavernFleignarEntity animatable, PoseStack stackIn, float ticks, MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float partialTicks) {
        super.renderEarly(animatable, stackIn, ticks, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, partialTicks);
    }
     */

    @Override
    public void render(CavernFleignarEntity entity, float entityYaw, float partialTicks, PoseStack stack, MultiBufferSource bufferIn, int packedLightIn) {
        float hugeSize = entity.isHuge ? 1F : 0.75F;
        stack.scale(hugeSize, hugeSize, hugeSize);
        super.render(entity, entityYaw, partialTicks, stack, bufferIn, packedLightIn);
    }
    
    @Override
    public RenderType getRenderType(CavernFleignarEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
    	return RenderType.entityTranslucent(texture);
    }

    @Override
    protected float getDeathMaxRotation(CavernFleignarEntity entity) {
        return 0.0F;
    }
}
