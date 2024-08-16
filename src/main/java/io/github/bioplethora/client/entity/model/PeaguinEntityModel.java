package io.github.bioplethora.client.entity.model;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.entity.creatures.PeaguinEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import software.bernie.geckolib.model.GeoModel;

public class PeaguinEntityModel extends GeoModel<PeaguinEntity> {

    @Override
    public ResourceLocation getModelResource(PeaguinEntity entity) {
        if (((LivingEntity) entity).isBaby()) {
            return new ResourceLocation(Bioplethora.MOD_ID, "geo/baby_peaguin.geo.json");
        } else {
            return new ResourceLocation(Bioplethora.MOD_ID, "geo/peaguin.geo.json");
        }
    }

    @Override
    public ResourceLocation getTextureResource(PeaguinEntity entity) {
        if (((LivingEntity) entity).isBaby()) {
            return new ResourceLocation(Bioplethora.MOD_ID, "textures/entity/baby_peaguin.png");
        } else {
            return new ResourceLocation(Bioplethora.MOD_ID, "textures/entity/peaguin.png");
        }
    }

    @Override
    public ResourceLocation getAnimationResource(PeaguinEntity entity) {
        if (((LivingEntity) entity).isBaby()) {
            return new ResourceLocation(Bioplethora.MOD_ID, "animations/baby_peaguin.animation.json");
        } else {
            return new ResourceLocation(Bioplethora.MOD_ID, "animations/peaguin.animation.json");
        }
    }

    /*@Override
    public void setCustomAnimations(PeaguinEntity entity, long uniqueID, @SuppressWarnings("rawtypes") AnimationState customPredicate) {
        super.setCustomAnimations(entity, uniqueID, customPredicate);

        if (!((LivingEntity) entity).isBaby()) {
            CoreGeoBone head = this.getAnimationProcessor().getBone("head");
            EntityModelData extraData = (EntityModelData) customPredicate.getExtraData().get(0);
            head.setRotX((extraData.headPitch()) * ((float) Math.PI / 180F));
            head.setRotY((extraData.netHeadYaw()) * ((float) Math.PI / 270F));
        }
    }*/
}
