package io.github.bioplethora.client.entity.render;

import org.joml.Vector3f;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.client.entity.model.ShachathEntityModel;
import io.github.bioplethora.client.entity.render.layer.ShachathEntityGlowLayer;
import io.github.bioplethora.entity.creatures.ShachathEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class ShachathEntityRender extends GeoEntityRenderer<ShachathEntity> {

    public ShachathEntityRender(EntityRendererProvider.Context renderManager) {
        super(renderManager, new ShachathEntityModel());
        this.addLayer(new ShachathEntityGlowLayer(this));
        this.shadowRadius = 0.8F;
    }

    @Override
    public void renderEarly(ShachathEntity animatable, PoseStack stackIn, float ticks, MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float partialTicks) {
        super.renderEarly(animatable, stackIn, ticks, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, partialTicks);
    }

    @Override
    public RenderType getRenderType(ShachathEntity animatable, float partialTicks, PoseStack stack, MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, ResourceLocation textureLocation) {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }

    @Override
    public ResourceLocation getTextureLocation(ShachathEntity entity) {
        if (entity.isClone()) {
            return new ResourceLocation(Bioplethora.MOD_ID, "textures/entity/shachath_clone.png");
        } else {
            return new ResourceLocation(Bioplethora.MOD_ID, "textures/entity/shachath.png");
        }
    }

    @Override
    public void renderRecursively(GeoBone bone, PoseStack stack, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {

        if (bone.getName().equals("LeftHand")) {
            if (!bone.isHidden) {
                stack.pushPose();
                stack.translate(-0.3D, 0.75D, -0.05D);
                stack.mulPose(Vector3f.XP.rotationDegrees(-75));
                stack.mulPose(Vector3f.YP.rotationDegrees(0));
                stack.mulPose(Vector3f.ZP.rotationDegrees(0));
                stack.scale(1.0f, 1.0f, 1.0f);
                Minecraft.getInstance().getItemRenderer().renderStatic(this.mainHand, ItemTransforms.TransformType.THIRD_PERSON_RIGHT_HAND, packedLightIn, packedOverlayIn, stack, this.rtb, 0);
                stack.popPose();
                bufferIn = rtb.getBuffer(RenderType.entitySmoothCutout(whTexture));
            }
        }

        super.renderRecursively(bone, stack, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }
}
