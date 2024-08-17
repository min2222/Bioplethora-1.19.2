package io.github.bioplethora.client.entity.render.projectile;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.client.entity.model.projectile.CryoblazeModel;
import io.github.bioplethora.entity.projectile.CryoblazeEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class CryoblazeRender extends GeoEntityRenderer<CryoblazeEntity> {

    private static final RenderType BEAM = RenderType.entitySmoothCutout(new ResourceLocation(Bioplethora.MOD_ID, "textures/projectiles/cryoblaze.png"));

    public CryoblazeRender(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new CryoblazeModel());
    }

    @Override
    public RenderType getRenderType(CryoblazeEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
    	return BEAM;
    }

    protected int getBlockLightLevel(CryoblazeEntity entityIn, BlockPos partialTicks) {
        return 15;
    }

    @Override
    public void preRender(PoseStack poseStack, CryoblazeEntity animatable, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        float size = 1.2F;
        poseStack.scale(size, size, size);
    	super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
    }
}