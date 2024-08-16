package io.github.bioplethora.client.entity.model.projectile;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.entity.projectile.AlphanumObliteratorSpearEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class AlphanumObliteratorSpearModel extends GeoModel<AlphanumObliteratorSpearEntity> {

    @Override
    public ResourceLocation getModelResource(AlphanumObliteratorSpearEntity object) {
        return new ResourceLocation(Bioplethora.MOD_ID, "geo/projectiles/alphanum_obliterator_spear.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(AlphanumObliteratorSpearEntity object) {
        return new ResourceLocation(Bioplethora.MOD_ID, "textures/projectiles/alphanum_obliterator_spear.png");
    }

    @Override
    public ResourceLocation getAnimationResource(AlphanumObliteratorSpearEntity animatable) {
        return new ResourceLocation(Bioplethora.MOD_ID, "animations/projectiles/alphanum_obliterator_spear.animation.json");
    }

    @Override
    public void setCustomAnimations(AlphanumObliteratorSpearEntity entity, long uniqueID, @SuppressWarnings("rawtypes") AnimationState customPredicate) {
        super.setCustomAnimations(entity, uniqueID, customPredicate);

        CoreGeoBone head = this.getAnimationProcessor().getBone("spear");
        EntityModelData extraData = (EntityModelData) customPredicate.getExtraData().get(0);
        head.setRotX((extraData.headPitch()) * ((float) Math.PI / 180F));
        head.setRotY((extraData.netHeadYaw()) * ((float) Math.PI / 270F));
    }
}
