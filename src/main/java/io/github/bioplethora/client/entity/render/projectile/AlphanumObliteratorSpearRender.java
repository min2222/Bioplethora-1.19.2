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
    protected int getSkyLightLevel(AlphanumObliteratorSpearEntity pEntity, BlockPos pPos) {
    	return 15;
    }

    @Override
    public void renderEarly(AlphanumObliteratorSpearEntity animatable, PoseStack stackIn, float ticks, MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float partialTicks) {
        float size = 1.2F;
        stackIn.scale(size, size, size);
        stackIn.mulPose(Axis.YP.rotationDegrees(-90));
        super.renderEarly(animatable, stackIn, ticks, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, partialTicks);
    }
}