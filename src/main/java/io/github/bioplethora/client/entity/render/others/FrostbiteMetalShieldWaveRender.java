package io.github.bioplethora.client.entity.render.others;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.client.entity.model.others.FrostbiteMetalShieldWaveModel;
import io.github.bioplethora.entity.others.FrostbiteMetalShieldWaveEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class FrostbiteMetalShieldWaveRender extends GeoEntityRenderer<FrostbiteMetalShieldWaveEntity> {

    private static final RenderType BEAM = RenderType.entitySmoothCutout(new ResourceLocation(Bioplethora.MOD_ID, "textures/entity/others/altyrus_summoning.png"));

    public FrostbiteMetalShieldWaveRender(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new FrostbiteMetalShieldWaveModel());
    }

    @Override
    public RenderType getRenderType(FrostbiteMetalShieldWaveEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
    	return RenderType.eyes(texture);
    }

    protected int getBlockLightLevel(FrostbiteMetalShieldWaveEntity entityIn, BlockPos partialTicks) {
        return 15;
    }
}
