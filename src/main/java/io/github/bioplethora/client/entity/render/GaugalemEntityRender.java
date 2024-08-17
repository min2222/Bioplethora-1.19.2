package io.github.bioplethora.client.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.client.entity.model.GaugalemEntityModel;
import io.github.bioplethora.entity.creatures.GaugalemEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class GaugalemEntityRender extends GeoEntityRenderer<GaugalemEntity> {

    public GaugalemEntityRender(EntityRendererProvider.Context renderManager) {
        super(renderManager, new GaugalemEntityModel());
        this.shadowRadius = 0.5F;
    }

    @Override
    public ResourceLocation getTextureLocation(GaugalemEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "textures/entity/gaugalem.png");
    }

    @Override
    public RenderType getRenderType(GaugalemEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
    	return RenderType.entityTranslucent(texture);
    }

    @Override
    public void renderRecursively(PoseStack stack, GaugalemEntity animatable, GeoBone bone, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer bufferIn, boolean isReRender, float partialTick, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {

        if (/*bone.getName().equals("armr") ||*/ bone.getName().equals("handr")) {
            if ((!bone.isHidden())) {
                stack.pushPose();

                //position / translation
                stack.translate(0.45D, 1.5D, -0.15D);

                //rotation
                stack.mulPose(Axis.XP.rotationDegrees(-75));
                stack.mulPose(Axis.YP.rotationDegrees(0));
                stack.mulPose(Axis.ZP.rotationDegrees(0));

                //size / scale
                stack.scale(1.0f, 1.0f, 1.0f);

                Minecraft.getInstance().getItemRenderer().renderStatic(animatable.getMainHandItem(), ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, packedLightIn, packedOverlayIn, stack, bufferSource, animatable.level, 0);
                stack.popPose();
                bufferIn = bufferSource.getBuffer(RenderType.entitySmoothCutout(getTextureLocation(animatable)));
            }
        }
        if (bone.getName().equals("arml") || bone.getName().equals("handl")) {
            if (!bone.isHidden()) {
                stack.pushPose();

                //position / translation
                stack.translate(-0.45D, 1.5D, -0.25D);

                //rotation
                stack.mulPose(Axis.XP.rotationDegrees(-75));
                stack.mulPose(Axis.YP.rotationDegrees(0));
                stack.mulPose(Axis.ZP.rotationDegrees(0));

                //size / scale
                stack.scale(1.0f, 1.0f, 1.0f);

                Minecraft.getInstance().getItemRenderer().renderStatic(animatable.getOffhandItem(), ItemDisplayContext.THIRD_PERSON_LEFT_HAND, packedLightIn, packedOverlayIn, stack, bufferSource, animatable.level, 0);
                stack.popPose();
                bufferIn = bufferSource.getBuffer(RenderType.entitySmoothCutout(getTextureLocation(animatable)));
            }
        }
    	super.renderRecursively(stack, animatable, bone, renderType, bufferSource, bufferIn, isReRender, partialTick, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }

    @Override
    protected float getDeathMaxRotation(GaugalemEntity entity) {
        return 0.0F;
    }
}
