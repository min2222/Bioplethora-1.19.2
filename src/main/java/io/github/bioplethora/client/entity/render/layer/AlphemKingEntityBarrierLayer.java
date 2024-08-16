package io.github.bioplethora.client.entity.render.layer;

import com.mojang.blaze3d.vertex.PoseStack;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.entity.creatures.AlphemKingEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

public class AlphemKingEntityBarrierLayer extends GeoRenderLayer<AlphemKingEntity> {

    private static final ResourceLocation MODEL = new ResourceLocation(Bioplethora.MOD_ID, "geo/alphem_king.geo.json");

    public AlphemKingEntityBarrierLayer(GeoRenderer<AlphemKingEntity> entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    public void render(PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, AlphemKingEntity entityLivingBaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        RenderType renderType = RenderType.entityGlint();
        if (entityLivingBaseIn.isBarriered()) {
            this.getRenderer().render(this.getEntityModel().getModel(MODEL), entityLivingBaseIn, partialTicks, renderType, matrixStackIn, bufferIn, bufferIn.getBuffer(renderType), packedLightIn, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, 1f);
        }
    }
}