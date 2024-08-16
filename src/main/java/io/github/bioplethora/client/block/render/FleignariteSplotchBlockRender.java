package io.github.bioplethora.client.block.render;

import io.github.bioplethora.blocks.tile_entities.FleignariteSplotchTileEntity;
import io.github.bioplethora.client.block.model.FleignariteSplotchBlockModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

@OnlyIn(Dist.CLIENT)
public class FleignariteSplotchBlockRender extends GeoBlockRenderer<FleignariteSplotchTileEntity> {

    public FleignariteSplotchBlockRender(Context renderManager) {
        super(new FleignariteSplotchBlockModel());
    }

    @Override
    public RenderType getRenderType(FleignariteSplotchTileEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
    	return RenderType.entityTranslucent(texture);
    }
}
