package io.github.bioplethora.client.entity.render.others;

import io.github.bioplethora.client.entity.model.others.GrylynenCoreBombModel;
import io.github.bioplethora.entity.others.GrylynenCoreBombEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class GrylynenCoreBombRender extends GeoEntityRenderer<GrylynenCoreBombEntity> {

    public GrylynenCoreBombRender(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new GrylynenCoreBombModel());
    }

    @Override
    public RenderType getRenderType(GrylynenCoreBombEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
    	return RenderType.eyes(texture);
    }

    protected int getBlockLightLevel(GrylynenCoreBombEntity entityIn, BlockPos partialTicks) {
        return 15;
    }
}
