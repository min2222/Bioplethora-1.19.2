package io.github.bioplethora.client.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.client.entity.model.FrostbiteGolemEntityModel;
import io.github.bioplethora.client.entity.render.layer.FrostbiteGolemEntityGlowLayer;
import io.github.bioplethora.entity.creatures.FrostbiteGolemEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class FrostbiteGolemEntityRender extends GeoEntityRenderer<FrostbiteGolemEntity> {

    public FrostbiteGolemEntityRender(EntityRendererProvider.Context renderManager) {
        super(renderManager, new FrostbiteGolemEntityModel());
        this.addLayer(new FrostbiteGolemEntityGlowLayer(this));
        this.shadowRadius = 2.2F;
    }

    @Override
    public void renderEarly(FrostbiteGolemEntity animatable, PoseStack stackIn, float ticks, MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float partialTicks) {
        super.renderEarly(animatable, stackIn, ticks, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, partialTicks);
    }

    @Override
    public ResourceLocation getTextureLocation(FrostbiteGolemEntity entity) {
        if (((LivingEntity) entity).getHealth() <= 100) {
            return new ResourceLocation(Bioplethora.MOD_ID, "textures/entity/frostbite_golem_cracked.png");
        } else {
            return new ResourceLocation(Bioplethora.MOD_ID, "textures/entity/frostbite_golem.png");
        }
    }

    @Override
    public RenderType getRenderType(FrostbiteGolemEntity animatable, float partialTicks, PoseStack stack, MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, ResourceLocation textureLocation) {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }

    @Override
    public void renderRecursively(GeoBone bone, PoseStack stack, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        super.renderRecursively(bone, stack, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }

    @Override
    protected float getDeathMaxRotation(FrostbiteGolemEntity entity) {
        return 0.0F;
    }
}
