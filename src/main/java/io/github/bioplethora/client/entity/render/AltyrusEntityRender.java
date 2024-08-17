package io.github.bioplethora.client.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;

import io.github.bioplethora.client.entity.model.AltyrusEntityModel;
import io.github.bioplethora.client.entity.render.layer.AltyrusEntityGlowLayer;
import io.github.bioplethora.entity.creatures.AltyrusEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class AltyrusEntityRender extends GeoEntityRenderer<AltyrusEntity> {

    public AltyrusEntityRender(EntityRendererProvider.Context renderManager) {
        super(renderManager, new AltyrusEntityModel());
        this.addRenderLayer(new AltyrusEntityGlowLayer(this));
        this.shadowRadius = 1.5F;
    }

    @Override
    protected void applyRotations(AltyrusEntity entityLiving, PoseStack matrixStackIn, float ageInTicks, float rotationYaw, float partialTicks) {
        super.applyRotations(entityLiving, matrixStackIn, ageInTicks, rotationYaw, partialTicks);
        entityLiving.ageInTicks = ageInTicks;
    }
    
    @Override
    public RenderType getRenderType(AltyrusEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
    	return RenderType.entityTranslucent(texture);
    }

    @Override
    protected float getDeathMaxRotation(AltyrusEntity entity) {
        return 0.0F;
    }
}
