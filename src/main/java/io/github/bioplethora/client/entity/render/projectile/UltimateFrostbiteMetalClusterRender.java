package io.github.bioplethora.client.entity.render.projectile;

import io.github.bioplethora.client.entity.model.projectile.UltimateFrostbiteMetalClusterModel;
import io.github.bioplethora.entity.projectile.UltimateFrostbiteMetalClusterEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoEntityRenderer;


public class UltimateFrostbiteMetalClusterRender extends GeoEntityRenderer<UltimateFrostbiteMetalClusterEntity> {

    public UltimateFrostbiteMetalClusterRender(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new UltimateFrostbiteMetalClusterModel());
    }

    @Override
    public RenderType getRenderType(UltimateFrostbiteMetalClusterEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
    	return RenderType.eyes(texture);
    }
    
    protected int getBlockLightLevel(UltimateFrostbiteMetalClusterEntity entityIn, BlockPos partialTicks) {
        return 15;
    }
}