package io.github.bioplethora.client.entity.render.layer;

import com.mojang.blaze3d.vertex.PoseStack;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.entity.others.PrimordialRingEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;

public class PrimordialRingEntityGlowLayer extends GeoRenderLayer<PrimordialRingEntity> {

    private static final ResourceLocation GLOW = new ResourceLocation(Bioplethora.MOD_ID, "textures/entity/layers/altyrus_glow_layer_summoning.png");
    private static final ResourceLocation MODEL = new ResourceLocation(Bioplethora.MOD_ID, "geo/others/primordial_ring.geo.json");

    public PrimordialRingEntityGlowLayer(IGeoRenderer<PrimordialRingEntity> entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    public void render(PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, PrimordialRingEntity entityLivingBaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        RenderType cameo =  RenderType.eyes(GLOW);
        if(!entityLivingBaseIn.isDeadOrDying()) {
            this.getRenderer().render(this.getEntityModel().getModel(MODEL), entityLivingBaseIn, partialTicks, cameo, matrixStackIn, bufferIn, bufferIn.getBuffer(cameo), packedLightIn, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, 1f);
        }
    }
}