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
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class BPEffectRender extends GeoEntityRenderer<BPEffectEntity> {

    public BPEffectRender(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new BPEffectModel());
    }

    @Override
    public RenderType getRenderType(BPEffectEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
    	return RenderType.entitySmoothCutout(texture);
    }

    protected int getBlockLightLevel(BPEffectEntity entityIn, BlockPos partialTicks) {
        return 15;
    }
    
    @Override
    public void preRender(PoseStack poseStack, BPEffectEntity animatable, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        float f = 1;
        f = (float) ((double) f * animatable.getEffectType().getScale());
        poseStack.scale(f, f, f);
    	super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
    	super.preRender(poseStack, animatable, model, bufferSource, bufferSource.getBuffer(RenderType.eyes(getTextureLocation(animatable))), isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
