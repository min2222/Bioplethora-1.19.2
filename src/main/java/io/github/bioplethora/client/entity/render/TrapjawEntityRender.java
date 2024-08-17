package io.github.bioplethora.client.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import io.github.bioplethora.client.entity.model.TrapjawEntityModel;
import io.github.bioplethora.entity.creatures.TrapjawEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class TrapjawEntityRender extends GeoEntityRenderer<TrapjawEntity> {

    public TrapjawEntityRender(EntityRendererProvider.Context renderManager) {
        super(renderManager, new TrapjawEntityModel());
        this.shadowRadius = 0.5F;
    }
    
    @Override
    public void preRender(PoseStack poseStack, TrapjawEntity animatable, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        float size = 0.5F;
        if (animatable.isBaby()) {
            poseStack.scale(size, size, size);
        }
    	super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    public RenderType getRenderType(TrapjawEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
    	return RenderType.entityTranslucent(texture);
    }
}
