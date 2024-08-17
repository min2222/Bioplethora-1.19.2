package io.github.bioplethora.client.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;

import io.github.bioplethora.client.entity.model.AlphemKingEntityModel;
import io.github.bioplethora.client.entity.render.layer.AlphemKingEntityBarrierLayer;
import io.github.bioplethora.client.entity.render.layer.AlphemKingEntityGlowLayer;
import io.github.bioplethora.entity.creatures.AlphemKingEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class AlphemKingEntityRender extends GeoEntityRenderer<AlphemKingEntity> {

    public AlphemKingEntityRender(EntityRendererProvider.Context renderManager) {
        super(renderManager, new AlphemKingEntityModel());
        this.addRenderLayer(new AlphemKingEntityGlowLayer(this));
        this.addRenderLayer(new AlphemKingEntityBarrierLayer(this));
        this.shadowRadius = 1.5F;
    }

    @Override
    protected void applyRotations(AlphemKingEntity entityLiving, PoseStack matrixStackIn, float ageInTicks, float rotationYaw, float partialTicks) {
        super.applyRotations(entityLiving, matrixStackIn, ageInTicks, rotationYaw, partialTicks);
        entityLiving.ageInTicks = ageInTicks;
    }
    
    @Override
    public RenderType getRenderType(AlphemKingEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
    	return RenderType.entityTranslucent(texture);
    }

    @Override
    protected float getDeathMaxRotation(AlphemKingEntity entity) {
        return 0.0F;
    }
}
