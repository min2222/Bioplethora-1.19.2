package io.github.bioplethora.client.entity.render.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.entity.creatures.VoidjawEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

public class VoidjawImpulseLayer extends GeoRenderLayer<VoidjawEntity> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(Bioplethora.MOD_ID, "textures/entity/voidjaw.png");
    private static final ResourceLocation MODEL = new ResourceLocation(Bioplethora.MOD_ID, "geo/voidjaw.geo.json");

    public VoidjawImpulseLayer(GeoRenderer<VoidjawEntity> entityRendererIn) {
        super(entityRendererIn);
    }
    
    @Override
    public void render(PoseStack matrixStackIn, VoidjawEntity entityLivingBaseIn, BakedGeoModel bakedModel, RenderType renderType, MultiBufferSource bufferIn, VertexConsumer buffer, float partialTicks, int packedLightIn, int packedOverlay) {
        if (entityLivingBaseIn.isSaving()) {
            RenderType trslct = RenderType.eyes(TEXTURE);
            matrixStackIn.scale(1.5F, 1.5F, 1.5F);
            this.getRenderer().reRender(this.getGeoModel().getBakedModel(MODEL), matrixStackIn, bufferIn, entityLivingBaseIn, trslct, bufferIn.getBuffer(trslct), partialTicks, packedLightIn, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, 0.15f);
        }
    }
}