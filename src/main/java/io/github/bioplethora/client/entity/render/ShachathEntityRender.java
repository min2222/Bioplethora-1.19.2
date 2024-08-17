package io.github.bioplethora.client.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.client.entity.model.ShachathEntityModel;
import io.github.bioplethora.client.entity.render.layer.ShachathEntityGlowLayer;
import io.github.bioplethora.entity.creatures.ShachathEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class ShachathEntityRender extends GeoEntityRenderer<ShachathEntity> {

    public ShachathEntityRender(EntityRendererProvider.Context renderManager) {
        super(renderManager, new ShachathEntityModel());
        this.addRenderLayer(new ShachathEntityGlowLayer(this));
        this.shadowRadius = 0.8F;
    }

    @Override
    public RenderType getRenderType(ShachathEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
    	return RenderType.entityTranslucent(texture);
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
    public void renderRecursively(PoseStack stack, ShachathEntity animatable, GeoBone bone, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer bufferIn, boolean isReRender, float partialTick, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {

        if (bone.getName().equals("LeftHand")) {
            if (!bone.isHidden()) {
                stack.pushPose();
                stack.translate(-0.3D, 0.75D, -0.05D);
                stack.mulPose(Axis.XP.rotationDegrees(-75));
                stack.mulPose(Axis.YP.rotationDegrees(0));
                stack.mulPose(Axis.ZP.rotationDegrees(0));
                stack.scale(1.0f, 1.0f, 1.0f);
                Minecraft.getInstance().getItemRenderer().renderStatic(animatable.getMainHandItem(), ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, packedLightIn, packedOverlayIn, stack, bufferSource, animatable.level, 0);
                stack.popPose();
                bufferIn = bufferSource.getBuffer(RenderType.entitySmoothCutout(getTextureLocation(animatable)));
            }
        }

    	super.renderRecursively(stack, animatable, bone, renderType, bufferSource, bufferIn, isReRender, partialTick, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }
}
