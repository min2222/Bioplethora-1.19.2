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
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class CryoblazeRender extends GeoEntityRenderer<CryoblazeEntity> {

    private static final RenderType BEAM = RenderType.entitySmoothCutout(new ResourceLocation(Bioplethora.MOD_ID, "textures/projectiles/cryoblaze.png"));

    public CryoblazeRender(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new CryoblazeModel());
    }

    @Override
    public RenderType getRenderType(CryoblazeEntity animatable, float partialTicks, PoseStack stack, MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, ResourceLocation textureLocation) {
        return BEAM;
    }

    protected int getBlockLightLevel(CryoblazeEntity entityIn, BlockPos partialTicks) {
        return 15;
    }

    @Override
    public void renderEarly(CryoblazeEntity animatable, PoseStack stackIn, float ticks, MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float partialTicks) {
        float size = 1.2F;
        stackIn.scale(size, size, size);
        super.renderEarly(animatable, stackIn, ticks, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, partialTicks);
    }

    @Override
    public void renderRecursively(GeoBone bone, PoseStack stack, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        super.renderRecursively(bone, stack, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }
}