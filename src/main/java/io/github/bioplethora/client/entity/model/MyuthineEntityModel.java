package io.github.bioplethora.client.entity.model;

import javax.annotation.Nullable;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.api.IAdvancedGeoModel;
import io.github.bioplethora.entity.creatures.MyuthineEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class MyuthineEntityModel extends GeoModel<MyuthineEntity> implements IAdvancedGeoModel<MyuthineEntity> {

    @Override
    public ResourceLocation getModelResource(MyuthineEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "geo/myuthine.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(MyuthineEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "textures/entity/myuthine.png");
    }

    @Override
    public ResourceLocation getAnimationResource(MyuthineEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "animations/myuthine.animation.json");
    }

    @Override
    public void setCustomAnimations(MyuthineEntity entity, long uniqueID, @Nullable AnimationState customPredicate) {
        super.setCustomAnimations(entity, uniqueID, customPredicate);
        EntityModelData extraData = (EntityModelData) customPredicate.getExtraData().get(0);

        CoreGeoBone head = getB("bodytop");
        float tickCountNeg = entity.ageInTicks - (float) entity.tickCount;
        float lerpHelper = Mth.lerp(tickCountNeg, entity.prevHurtTime, entity.hurtTime) / entity.hurtDuration;
        float pi = (float) Math.PI;

        this.adaptHeadOnLook(extraData, head);

        if (entity.prevHurtTime > 0 && !Minecraft.getInstance().isPaused()) {
            lerpHelper = lerpHelper * lerpHelper * lerpHelper;
            head.setRotX(head.getRotX() + -Mth.sin(lerpHelper * pi) * 0.35f);
        }
    }

    public CoreGeoBone getB(String bone) {
        return this.getAnimationProcessor().getBone(bone);
    }
}
