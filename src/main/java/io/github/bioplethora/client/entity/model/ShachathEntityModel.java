package io.github.bioplethora.client.entity.model;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.entity.creatures.ShachathEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class ShachathEntityModel extends GeoModel<ShachathEntity> {

    @Override
    public ResourceLocation getModelResource(ShachathEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "geo/shachath.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(ShachathEntity entity) {
        if (entity.isClone()) {
            return new ResourceLocation(Bioplethora.MOD_ID, "textures/entity/shachath_clone.png");
        } else {
            return new ResourceLocation(Bioplethora.MOD_ID, "textures/entity/shachath.png");
        }
    }

    @Override
    public ResourceLocation getAnimationResource(ShachathEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "animations/shachath.animation.json");
    }

    @Override
    public void setCustomAnimations(ShachathEntity entity, long uniqueID, @SuppressWarnings("rawtypes") AnimationState customPredicate) {
        super.setCustomAnimations(entity, uniqueID, customPredicate);

        CoreGeoBone head = this.getAnimationProcessor().getBone("Head");
        EntityModelData extraData = (EntityModelData) customPredicate.getExtraData().get(0);
        head.setRotX((extraData.headPitch()) * ((float) Math.PI / 180F));
        head.setRotY((extraData.netHeadYaw()) * ((float) Math.PI / 270F));
    }
}
