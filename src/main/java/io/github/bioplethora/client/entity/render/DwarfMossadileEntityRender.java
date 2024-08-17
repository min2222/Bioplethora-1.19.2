package io.github.bioplethora.client.entity.render;

import io.github.bioplethora.client.entity.model.DwarfMossadileEntityModel;
import io.github.bioplethora.entity.creatures.DwarfMossadileEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class DwarfMossadileEntityRender extends GeoEntityRenderer<DwarfMossadileEntity> {

    public DwarfMossadileEntityRender(EntityRendererProvider.Context renderManager) {
        super(renderManager, new DwarfMossadileEntityModel());
        this.shadowRadius = 0.75F;
    }
    
    @Override
    public RenderType getRenderType(DwarfMossadileEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
    	return RenderType.entityTranslucent(texture);
    }
}
