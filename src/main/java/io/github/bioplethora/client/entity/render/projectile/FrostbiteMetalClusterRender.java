package io.github.bioplethora.client.entity.render.projectile;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.client.entity.model.projectile.FrostbiteMetalClusterModel;
import io.github.bioplethora.entity.projectile.FrostbiteMetalClusterEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class FrostbiteMetalClusterRender extends GeoEntityRenderer<FrostbiteMetalClusterEntity> {

    private static final RenderType BEAM = RenderType.entitySmoothCutout(new ResourceLocation(Bioplethora.MOD_ID, "textures/projectiles/frostbite_metal_cluster.png"));

    public FrostbiteMetalClusterRender(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new FrostbiteMetalClusterModel());
    }

    @Override
    public RenderType getRenderType(FrostbiteMetalClusterEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
    	return RenderType.eyes(texture);
    }

    protected int getBlockLightLevel(FrostbiteMetalClusterEntity entityIn, BlockPos partialTicks) {
        return 15;
    }
}