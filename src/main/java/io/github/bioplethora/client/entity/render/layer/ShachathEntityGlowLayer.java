package io.github.bioplethora.client.entity.render.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.entity.creatures.ShachathEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

public class ShachathEntityGlowLayer extends GeoRenderLayer<ShachathEntity> {

    private static final ResourceLocation GLOW = new ResourceLocation(Bioplethora.MOD_ID, "textures/entity/layers/shachath_glow_layer.png");
    private static final ResourceLocation MODEL = new ResourceLocation(Bioplethora.MOD_ID, "geo/shachath.geo.json");

    public ShachathEntityGlowLayer(GeoRenderer<ShachathEntity> entityRendererIn) {
        super(entityRendererIn);
    }
    
    @Override
    public void render(PoseStack matrixStackIn, ShachathEntity entityLivingBaseIn, BakedGeoModel bakedModel, RenderType renderType, MultiBufferSource bufferIn, VertexConsumer buffer, float partialTicks, int packedLightIn, int packedOverlay) {
        RenderType eyesRender = RenderType.eyes(GLOW);
    	if (!entityLivingBaseIn.isDeadOrDying()) {
            this.getRenderer().reRender(this.getGeoModel().getBakedModel(MODEL), matrixStackIn, bufferIn, entityLivingBaseIn, eyesRender, bufferIn.getBuffer(eyesRender), partialTicks, packedLightIn, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, 1f);
        }
    }
}