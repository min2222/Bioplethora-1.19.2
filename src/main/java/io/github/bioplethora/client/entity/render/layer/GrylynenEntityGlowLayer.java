package io.github.bioplethora.client.entity.render.layer;

import com.mojang.blaze3d.vertex.PoseStack;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.entity.creatures.GrylynenEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;

public class GrylynenEntityGlowLayer extends GeoRenderLayer<GrylynenEntity> {

    private static final ResourceLocation MODEL = new ResourceLocation(Bioplethora.MOD_ID, "geo/grylynen.geo.json");

    public GrylynenEntityGlowLayer(IGeoRenderer<GrylynenEntity> entityRendererIn) {
        super(entityRendererIn);
    }

    public ResourceLocation getLayerTexture(GrylynenEntity entityIn) {
        return new ResourceLocation(Bioplethora.MOD_ID, "textures/entity/grylynen/grylynen_crystal_layer_" + entityIn.getGrylynenTier().getCrystalColor() + ".png");
    }

    @Override
    public void render(PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, GrylynenEntity entityLivingBaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        RenderType cameo =  RenderType.eyes(this.getLayerTexture(entityLivingBaseIn));
        if(!entityLivingBaseIn.isDeadOrDying()) {
            this.getRenderer().render(this.getEntityModel().getModel(MODEL), entityLivingBaseIn, partialTicks, cameo, matrixStackIn, bufferIn, bufferIn.getBuffer(cameo), packedLightIn, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, 1f);
        }
    }
}