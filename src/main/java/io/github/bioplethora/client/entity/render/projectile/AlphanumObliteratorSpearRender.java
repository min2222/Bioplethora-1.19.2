package io.github.bioplethora.client.entity.render.projectile;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.client.entity.model.projectile.AlphanumObliteratorSpearModel;
import io.github.bioplethora.entity.projectile.AlphanumObliteratorSpearEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class AlphanumObliteratorSpearRender extends GeoEntityRenderer<AlphanumObliteratorSpearEntity> {

    private static final RenderType BEAM = RenderType.entitySmoothCutout(new ResourceLocation(Bioplethora.MOD_ID, "textures/projectiles/alphanum_obliterator_spear.png"));

    public AlphanumObliteratorSpearRender(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new AlphanumObliteratorSpearModel());
    }
    
    @Override
    public RenderType getRenderType(AlphanumObliteratorSpearEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
    	return BEAM;
    }
    
    @Override
    protected int getBlockLightLevel(AlphanumObliteratorSpearEntity pEntity, BlockPos pPos) {
    	return 15;
    }
    
    @Override
    public void preRender(PoseStack poseStack, AlphanumObliteratorSpearEntity animatable, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        float size = 1.2F;
        poseStack.scale(size, size, size);
        poseStack.mulPose(Axis.YP.rotationDegrees(-90));
    	super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
    }
}