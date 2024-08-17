package io.github.bioplethora.client.entity.render.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.entity.creatures.AltyrusEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

public class AltyrusEntityGlowLayer extends GeoRenderLayer<AltyrusEntity> {

    private static final ResourceLocation GLOW_DEFAULT = new ResourceLocation(Bioplethora.MOD_ID, "textures/entity/layers/altyrus_glow_layer_default.png");
    private static final ResourceLocation GLOW_ATTACK_OR_SHOOT = new ResourceLocation(Bioplethora.MOD_ID, "textures/entity/layers/altyrus_glow_layer_shoot_or_attack.png");
    private static final ResourceLocation GLOW_SUMMONING = new ResourceLocation(Bioplethora.MOD_ID, "textures/entity/layers/altyrus_glow_layer_summoning.png");
    private static final ResourceLocation MODEL = new ResourceLocation(Bioplethora.MOD_ID, "geo/altyrus.geo.json");

    public AltyrusEntityGlowLayer(GeoRenderer<AltyrusEntity> entityRendererIn) {
        super(entityRendererIn);
    }
    
    @Override
    public void render(PoseStack matrixStackIn, AltyrusEntity entityLivingBaseIn, BakedGeoModel bakedModel, RenderType renderType, MultiBufferSource bufferIn, VertexConsumer buffer, float partialTicks, int packedLightIn, int packedOverlay) {
        if(!entityLivingBaseIn.isDeadOrDying()) {

            if (entityLivingBaseIn.getAttacking() || entityLivingBaseIn.isCharging()) {
                RenderType cameo =  RenderType.eyes(GLOW_ATTACK_OR_SHOOT);
                this.getRenderer().reRender(this.getGeoModel().getBakedModel(MODEL), matrixStackIn, bufferIn, entityLivingBaseIn, cameo, bufferIn.getBuffer(cameo), partialTicks, packedLightIn, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, 1f);

            } else if (entityLivingBaseIn.isSummoning()) {
                RenderType cameo =  RenderType.eyes(GLOW_SUMMONING);
                this.getRenderer().reRender(this.getGeoModel().getBakedModel(MODEL), matrixStackIn, bufferIn, entityLivingBaseIn, cameo, bufferIn.getBuffer(cameo), partialTicks, packedLightIn, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, 1f);

            } else {
                RenderType cameo =  RenderType.eyes(GLOW_DEFAULT);
                this.getRenderer().reRender(this.getGeoModel().getBakedModel(MODEL), matrixStackIn, bufferIn, entityLivingBaseIn, cameo, bufferIn.getBuffer(cameo), partialTicks, packedLightIn, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, 1f);
            }
        }
    }
}