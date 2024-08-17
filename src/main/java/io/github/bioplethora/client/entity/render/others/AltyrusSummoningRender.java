package io.github.bioplethora.client.entity.render.others;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.client.entity.model.others.AltyrusSummoningModel;
import io.github.bioplethora.entity.others.AltyrusSummoningEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class AltyrusSummoningRender extends GeoEntityRenderer<AltyrusSummoningEntity> {

    private static final RenderType BEAM = RenderType.entitySmoothCutout(new ResourceLocation(Bioplethora.MOD_ID, "textures/entity/others/altyrus_summoning.png"));

    public AltyrusSummoningRender(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new AltyrusSummoningModel());
    }
    
    @Override
    public RenderType getRenderType(AltyrusSummoningEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
    	return RenderType.eyes(texture);
    }

    protected int getBlockLightLevel(AltyrusSummoningEntity entityIn, BlockPos partialTicks) {
        return 15;
    }
}
