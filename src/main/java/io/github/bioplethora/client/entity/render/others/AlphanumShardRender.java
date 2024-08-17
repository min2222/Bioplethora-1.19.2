package io.github.bioplethora.client.entity.render.others;

import io.github.bioplethora.client.entity.model.others.AlphanumShardModel;
import io.github.bioplethora.entity.others.AlphanumShardEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class AlphanumShardRender extends GeoEntityRenderer<AlphanumShardEntity> {

    public AlphanumShardRender(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new AlphanumShardModel());
    }

    @Override
    public RenderType getRenderType(AlphanumShardEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
    	return RenderType.entitySmoothCutout(texture);
    }

    protected int getBlockLightLevel(AlphanumShardEntity entityIn, BlockPos partialTicks) {
        return 10;
    }
}
