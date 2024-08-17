package io.github.bioplethora.client.entity.model;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.entity.creatures.FrostbiteGolemEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class FrostbiteGolemEntityModel extends GeoModel<FrostbiteGolemEntity> {

    @Override
    public ResourceLocation getModelResource(FrostbiteGolemEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "geo/frostbite_golem.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(FrostbiteGolemEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "textures/entity/frostbite_golem.png");
    }

    @Override
    public ResourceLocation getAnimationResource(FrostbiteGolemEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "animations/frostbite_golem.animation.json");
    }

    @Override
    public void setCustomAnimations(FrostbiteGolemEntity entity, long uniqueID, @SuppressWarnings("rawtypes") AnimationState customPredicate) {
        super.setCustomAnimations(entity, uniqueID, customPredicate);

        CoreGeoBone head = this.getAnimationProcessor().getBone("head");
        EntityModelData extraData = (EntityModelData) customPredicate.getData(DataTickets.ENTITY_MODEL_DATA);
        head.setRotX((extraData.headPitch()) * ((float) Math.PI / 180F));
        head.setRotY((extraData.netHeadYaw()) * ((float) Math.PI / 270F));
    }
}
