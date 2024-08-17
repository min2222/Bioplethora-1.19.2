package io.github.bioplethora.client.entity.model;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.entity.creatures.MyliothanEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class MyliothanEntityModel extends GeoModel<MyliothanEntity> {

    @Override
    public ResourceLocation getModelResource(MyliothanEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "geo/myliothan.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(MyliothanEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "textures/entity/myliothan.png");
    }

    @Override
    public ResourceLocation getAnimationResource(MyliothanEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "animations/myliothan.animation.json");
    }

    /*@Override
    public void setCustomAnimations(MyliothanEntity entity, long uniqueID, @SuppressWarnings("rawtypes") AnimationState customPredicate) {
        super.setCustomAnimations(entity, uniqueID, customPredicate);

        CoreGeoBone head = this.getAnimationProcessor().getBone("head");
        EntityModelData extraData = (EntityModelData) customPredicate.getData(DataTickets.ENTITY_MODEL_DATA);
        head.setRotX((extraData.headPitch()) * ((float) Math.PI / 180F));
        head.setRotY((extraData.netHeadYaw()) * ((float) Math.PI / 270F));
    }*/
}
