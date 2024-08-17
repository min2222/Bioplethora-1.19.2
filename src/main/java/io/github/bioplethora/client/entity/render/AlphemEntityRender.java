package io.github.bioplethora.client.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.client.entity.model.AlphemEntityModel;
import io.github.bioplethora.entity.creatures.AlphemEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class AlphemEntityRender extends GeoEntityRenderer<AlphemEntity> {

    public AlphemEntityRender(EntityRendererProvider.Context renderManager) {
        super(renderManager, new AlphemEntityModel());
        this.shadowRadius = 0.5F;
    }

    @Override
    public ResourceLocation getTextureLocation(AlphemEntity entity) {
        if (entity.isCharging()) {
            return new ResourceLocation(Bioplethora.MOD_ID, "textures/entity/alphem_charging.png");
        } else {
            return new ResourceLocation(Bioplethora.MOD_ID, "textures/entity/alphem.png");
        }
    }
    
    @Override
    public RenderType getRenderType(AlphemEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
    	return RenderType.entityTranslucent(texture);
    }
    
    @Override
    public void renderRecursively(PoseStack stack, AlphemEntity animatable, GeoBone bone, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer bufferIn, boolean isReRender, float partialTick, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        if (bone.getName().equals("right_hand") || bone.getName().equals("right_arm")) {
            if (!bone.isHidden()) {
                stack.pushPose();

                //position / translation
                stack.translate(0.45D, 0.15D, -0.15D);

                //rotation
                stack.mulPose(Axis.XP.rotationDegrees(-75));
                stack.mulPose(Axis.YP.rotationDegrees(-15));
                stack.mulPose(Axis.ZP.rotationDegrees(0));

                //size / scale
                stack.scale(1.0f, 1.0f, 1.0f);

                Minecraft.getInstance().getItemRenderer().renderStatic(animatable.getMainHandItem(), ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, packedLightIn, packedOverlayIn, stack, bufferSource, animatable.level, 0);
                stack.popPose();
                bufferIn = bufferSource.getBuffer(RenderType.entitySmoothCutout(this.getTextureLocation(animatable)));
            }
        }
        if (bone.getName().equals("left_hand") || bone.getName().equals("left_arm")) {
            if (!bone.isHidden()) {
                stack.pushPose();

                //position / translation
                stack.translate(-0.45D, 0.15D, -0.25D);

                //rotation
                stack.mulPose(Axis.XP.rotationDegrees(-75));
                stack.mulPose(Axis.YP.rotationDegrees(15));
                stack.mulPose(Axis.ZP.rotationDegrees(0));

                //size / scale
                stack.scale(1.0f, 1.0f, 1.0f);

                Minecraft.getInstance().getItemRenderer().renderStatic(animatable.getOffhandItem(), ItemDisplayContext.THIRD_PERSON_LEFT_HAND, packedLightIn, packedOverlayIn, stack, bufferSource, animatable.level, 0);
                stack.popPose();
                bufferIn = bufferSource.getBuffer(RenderType.entitySmoothCutout(this.getTextureLocation(animatable)));
            }
        }
    	super.renderRecursively(stack, animatable, bone, renderType, bufferSource, bufferIn, isReRender, partialTick, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }

    @Override
    protected float getDeathMaxRotation(AlphemEntity entity) {
        return 0.0F;
    }
}
