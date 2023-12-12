package io.github.bioplethora.client.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.client.entity.model.GaugalemEntityModel;
import io.github.bioplethora.entity.creatures.GaugalemEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class GaugalemEntityRender extends GeoEntityRenderer<GaugalemEntity> {

    public GaugalemEntityRender(EntityRendererProvider.Context renderManager) {
        super(renderManager, new GaugalemEntityModel());
        this.shadowRadius = 0.5F;
    }

    @Override
    public void renderEarly(GaugalemEntity animatable, PoseStack stackIn, float ticks, MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float partialTicks) {
        super.renderEarly(animatable, stackIn, ticks, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, partialTicks);
    }

    @Override
    public ResourceLocation getTextureLocation(GaugalemEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "textures/entity/gaugalem.png");
    }

    @Override
    public RenderType getRenderType(GaugalemEntity animatable, float partialTicks, PoseStack stack, MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, ResourceLocation textureLocation) {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }

    @Override
    public void renderRecursively(GeoBone bone, PoseStack stack, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {

        if (/*bone.getName().equals("armr") ||*/ bone.getName().equals("handr")) {
            if ((!bone.isHidden)) {
                stack.pushPose();

                //position / translation
                stack.translate(0.45D, 1.5D, -0.15D);

                //rotation
                stack.mulPose(Vector3f.XP.rotationDegrees(-75));
                stack.mulPose(Vector3f.YP.rotationDegrees(0));
                stack.mulPose(Vector3f.ZP.rotationDegrees(0));

                //size / scale
                stack.scale(1.0f, 1.0f, 1.0f);

                Minecraft.getInstance().getItemRenderer().renderStatic(this.mainHand, ItemTransforms.TransformType.THIRD_PERSON_RIGHT_HAND, packedLightIn, packedOverlayIn, stack, this.rtb, 0);
                stack.popPose();
                bufferIn = rtb.getBuffer(RenderType.entitySmoothCutout(whTexture));
            }
        }
        if (bone.getName().equals("arml") || bone.getName().equals("handl")) {
            if (!bone.isHidden) {
                stack.pushPose();

                //position / translation
                stack.translate(-0.45D, 1.5D, -0.25D);

                //rotation
                stack.mulPose(Vector3f.XP.rotationDegrees(-75));
                stack.mulPose(Vector3f.YP.rotationDegrees(0));
                stack.mulPose(Vector3f.ZP.rotationDegrees(0));

                //size / scale
                stack.scale(1.0f, 1.0f, 1.0f);

                Minecraft.getInstance().getItemRenderer().renderStatic(this.offHand, ItemTransforms.TransformType.THIRD_PERSON_LEFT_HAND, packedLightIn, packedOverlayIn, stack, this.rtb, 0);
                stack.popPose();
                bufferIn = rtb.getBuffer(RenderType.entitySmoothCutout(whTexture));
            }
        }
        super.renderRecursively(bone, stack, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }

    @Override
    protected float getDeathMaxRotation(GaugalemEntity entity) {
        return 0.0F;
    }
}
