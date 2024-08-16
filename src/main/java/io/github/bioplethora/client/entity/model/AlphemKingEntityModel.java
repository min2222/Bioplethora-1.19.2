package io.github.bioplethora.client.entity.model;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.api.IAdvancedGeoModel;
import io.github.bioplethora.entity.creatures.AlphemKingEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationProcessor;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class AlphemKingEntityModel extends GeoModel<AlphemKingEntity> implements IAdvancedGeoModel<AlphemKingEntity> {

    @Override
    public ResourceLocation getModelResource(AlphemKingEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "geo/alphem_king.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(AlphemKingEntity entity) {
        if (!entity.isBerserked()) {
            return new ResourceLocation(Bioplethora.MOD_ID, "textures/entity/alphem_king.png");
        } else {
            return new ResourceLocation(Bioplethora.MOD_ID, "textures/entity/alphem_king_berserked.png");
        }
    }

    @Override
    public ResourceLocation getAnimationResource(AlphemKingEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "animations/alphem_king.animation.json");
    }

    @Override
    public void setCustomAnimations(AlphemKingEntity entity, long uniqueID, @SuppressWarnings("rawtypes") AnimationState customPredicate) {
        super.setCustomAnimations(entity, uniqueID, customPredicate);

        AnimationProcessor ap = this.getAnimationProcessor();
        EntityModelData extraData = (EntityModelData) customPredicate.getExtraData().get(0);
        CoreGeoBone head = ap.getBone("head"), bodytop = ap.getBone("bodytop"), bodymid = ap.getBone("bodymid");
        CoreGeoBone arml = ap.getBone("arml"), armr = ap.getBone("armr");
        CoreGeoBone legl = ap.getBone("legl"), legr = ap.getBone("legr");

        adaptHeadOnLook(extraData, head);

        float tickCountNeg = entity.ageInTicks - (float) entity.tickCount;
        float lerpHelper = Mth.lerp(tickCountNeg, entity.prevHurtTime, entity.hurtTime) / entity.hurtDuration;
        float pi = (float) Math.PI;
        float rotationScale = entity.isBerserked() ? 0.5f : 0.25f;

        if (entity.isCharging() && !entity.getAttacking() && !entity.getAttacking() && !entity.getAttacking2() && !entity.getSmashing() && !entity.isPursuit()) {
            arml.setRotX(70);
            armr.setRotX(70);
        }

        if (entity.prevHurtTime > 0 && !Minecraft.getInstance().isPaused()) {
            lerpHelper = lerpHelper * lerpHelper * lerpHelper;
            head.setRotX(head.getRotX() + -Mth.sin(lerpHelper * pi) * rotationScale);
            bodytop.setRotX(bodytop.getRotX() + -Mth.sin(lerpHelper * pi) * rotationScale);

            float cosVal = Mth.cos(lerpHelper * 2 * pi);
            bodytop.setRotY(bodytop.getRotX() + (entity.hurtRandomizer ? cosVal * (rotationScale / 2) : -cosVal * -(rotationScale / 2)));

            bodymid.setRotX(bodymid.getRotX() + -Mth.sin(lerpHelper * pi) * rotationScale);
        }
    }
}
