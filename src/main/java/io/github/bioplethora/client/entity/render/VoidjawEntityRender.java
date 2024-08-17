package io.github.bioplethora.client.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import io.github.bioplethora.client.entity.model.VoidjawEntityModel;
import io.github.bioplethora.client.entity.render.layer.VoidjawImpulseLayer;
import io.github.bioplethora.entity.creatures.VoidjawEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class VoidjawEntityRender extends GeoEntityRenderer<VoidjawEntity> {
    @OnlyIn(Dist.CLIENT)
    private static float alp = 1.0F;

    public VoidjawEntityRender(EntityRendererProvider.Context renderManager) {
        super(renderManager, new VoidjawEntityModel());
        this.addRenderLayer(new VoidjawImpulseLayer(this));
        this.shadowRadius = 0.5F;
    }
    
    @Override
    public void preRender(PoseStack poseStack, VoidjawEntity animatable, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        float size = 0.5F;
        if (animatable.isBaby()) {
            poseStack.scale(size, size, size);
        }
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().player.getVehicle() == animatable) {
            alp = 0.15F;
        } else {
            alp = 1.0F;
        }
    	super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    public RenderType getRenderType(VoidjawEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
    	return RenderType.entityTranslucent(texture);
    }
}
