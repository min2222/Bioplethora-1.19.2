package io.github.bioplethora.client.entity.render.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.entity.creatures.GrylynenEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

public class GrylynenEntityGlowLayer extends GeoRenderLayer<GrylynenEntity> {

    private static final ResourceLocation MODEL = new ResourceLocation(Bioplethora.MOD_ID, "geo/grylynen.geo.json");

    public GrylynenEntityGlowLayer(GeoRenderer<GrylynenEntity> entityRendererIn) {
        super(entityRendererIn);
    }

    public ResourceLocation getLayerTexture(GrylynenEntity entityIn) {
        return new ResourceLocation(Bioplethora.MOD_ID, "textures/entity/grylynen/grylynen_crystal_layer_" + entityIn.getGrylynenTier().getCrystalColor() + ".png");
    }
    
    @Override
    public void render(PoseStack matrixStackIn, GrylynenEntity entityLivingBaseIn, BakedGeoModel bakedModel, RenderType renderType, MultiBufferSource bufferIn, VertexConsumer buffer, float partialTicks, int packedLightIn, int packedOverlay) {
        RenderType cameo = RenderType.eyes(this.getLayerTexture(entityLivingBaseIn));
    	if (!entityLivingBaseIn.isDeadOrDying()) {
            this.getRenderer().reRender(this.getGeoModel().getBakedModel(MODEL), matrixStackIn, bufferIn, entityLivingBaseIn, cameo, bufferIn.getBuffer(cameo), partialTicks, packedLightIn, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, 1f);
        }
    }
}