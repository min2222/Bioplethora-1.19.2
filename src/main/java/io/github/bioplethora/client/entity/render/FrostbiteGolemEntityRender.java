package io.github.bioplethora.client.entity.render;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.client.entity.model.FrostbiteGolemEntityModel;
import io.github.bioplethora.client.entity.render.layer.FrostbiteGolemEntityGlowLayer;
import io.github.bioplethora.entity.creatures.FrostbiteGolemEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class FrostbiteGolemEntityRender extends GeoEntityRenderer<FrostbiteGolemEntity> {

    public FrostbiteGolemEntityRender(EntityRendererProvider.Context renderManager) {
        super(renderManager, new FrostbiteGolemEntityModel());
        this.addRenderLayer(new FrostbiteGolemEntityGlowLayer(this));
        this.shadowRadius = 2.2F;
    }

    @Override
    public ResourceLocation getTextureLocation(FrostbiteGolemEntity entity) {
        if (((LivingEntity) entity).getHealth() <= 100) {
            return new ResourceLocation(Bioplethora.MOD_ID, "textures/entity/frostbite_golem_cracked.png");
        } else {
            return new ResourceLocation(Bioplethora.MOD_ID, "textures/entity/frostbite_golem.png");
        }
    }

    @Override
    public RenderType getRenderType(FrostbiteGolemEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
    	return RenderType.entityTranslucent(texture);
    }

    @Override
    protected float getDeathMaxRotation(FrostbiteGolemEntity entity) {
        return 0.0F;
    }
}
