package io.github.bioplethora.client.entity.render.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.entity.creatures.EurydnEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

public class EurydnEntityGlowLayer extends GeoRenderLayer<EurydnEntity> {

    private static final ResourceLocation GLOW_FIERY = new ResourceLocation(Bioplethora.MOD_ID, "textures/entity/layers/fiery_eurydn_glow_layer.png");
    private static final ResourceLocation GLOW_SOUL = new ResourceLocation(Bioplethora.MOD_ID, "textures/entity/layers/soul_eurydn_glow_layer.png");

    private static final ResourceLocation TRSLCT_FIERY = new ResourceLocation(Bioplethora.MOD_ID, "textures/entity/fiery_eurydn.png");
    private static final ResourceLocation TRSLCT_SOUL = new ResourceLocation(Bioplethora.MOD_ID, "textures/entity/soul_eurydn.png");

    private static final ResourceLocation MODEL = new ResourceLocation(Bioplethora.MOD_ID, "geo/eurydn.geo.json");

    public EurydnEntityGlowLayer(GeoRenderer<EurydnEntity> entityRendererIn) {
        super(entityRendererIn);
    }
    
    @Override
    public void render(PoseStack matrixStackIn, EurydnEntity entityLivingBaseIn, BakedGeoModel bakedModel, RenderType renderType, MultiBufferSource bufferIn, VertexConsumer buffer, float partialTicks, int packedLightIn, int packedOverlay) {
        boolean isFiery = entityLivingBaseIn.variant == EurydnEntity.Variant.FIERY;

        if (!entityLivingBaseIn.isDeadOrDying()) {
            RenderType cameo =  isFiery ? RenderType.eyes(GLOW_FIERY) : RenderType.eyes(GLOW_SOUL);

            this.getRenderer().reRender(this.getGeoModel().getBakedModel(MODEL), matrixStackIn, bufferIn, entityLivingBaseIn, cameo, bufferIn.getBuffer(cameo), partialTicks, packedLightIn, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, 0.75f);
        }

        if (!entityLivingBaseIn.level.isEmptyBlock(entityLivingBaseIn.blockPosition())) {
            RenderType trslct =  isFiery ? RenderType.eyes(TRSLCT_FIERY) : RenderType.eyes(TRSLCT_SOUL);

            matrixStackIn.scale(1.5F, 1.5F, 1.5F);
            this.getRenderer().reRender(this.getGeoModel().getBakedModel(MODEL), matrixStackIn, bufferIn, entityLivingBaseIn, trslct, bufferIn.getBuffer(trslct), partialTicks, packedLightIn, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, 0.15f);
        }
    }
}