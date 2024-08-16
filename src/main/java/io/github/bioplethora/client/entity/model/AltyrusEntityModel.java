package io.github.bioplethora.client.entity.model;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.api.IAdvancedGeoModel;
import io.github.bioplethora.entity.creatures.AltyrusEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class AltyrusEntityModel extends GeoModel<AltyrusEntity> implements IAdvancedGeoModel<AltyrusEntity> {

    @Override
    public ResourceLocation getModelResource(AltyrusEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "geo/altyrus.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(AltyrusEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "textures/entity/altyrus.png");
    }

    @Override
    public ResourceLocation getAnimationResource(AltyrusEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "animations/altyrus.animation.json");
    }

    @Override
    public void setCustomAnimations(AltyrusEntity entity, long uniqueID, @SuppressWarnings("rawtypes") AnimationState event) {
        super.setCustomAnimations(entity, uniqueID, event);

        CoreGeoBone head = getB("head"), altyrus = getB("altyrus");
        CoreGeoBone ringsCenter = getB("rings_crenter"), bodyBot = getB("bodybot");
        CoreGeoBone htr = getB("hand_top_right"), htl = getB("hand_top_left"), hbr = getB("hand_bottom_right"), hbl = getB("hand_bottom_left");

        EntityModelData extraData = (EntityModelData) event.getExtraData().get(0);

        if (entity.isCharging()) {
            adaptHeadOnLook(extraData, altyrus);
        } else {
            adaptHeadOnLook(extraData, head);
        }

        float tickCountNeg = entity.ageInTicks - (float) entity.tickCount;
        float lerpHelper = Mth.lerp(tickCountNeg, entity.prevHurtTime, entity.hurtTime) / entity.hurtDuration;
        float pi = (float) Math.PI;

        if (entity.prevHurtTime > 0 && !Minecraft.getInstance().isPaused()) {
            lerpHelper = lerpHelper * lerpHelper * lerpHelper;
            altyrus.setRotX(altyrus.getRotX() + -Mth.cos(lerpHelper * pi) * 0.30f);
            head.setRotX(head.getRotX() + -Mth.sin(lerpHelper * pi) * 0.30f);
            ringsCenter.setRotZ(ringsCenter.getRotZ() + -Mth.cos(lerpHelper * pi) * 0.75f);
            bodyBot.setRotX(bodyBot.getRotX() + Mth.sin(lerpHelper * pi) * 0.45f);

            htr.setRotX(htr.getRotX() + -Mth.cos(lerpHelper * pi) * 0.30f);
            htl.setRotX(htl.getRotX() + -Mth.cos(lerpHelper * pi) * 0.30f);
            hbr.setRotX(hbr.getRotX() + -Mth.cos(lerpHelper * pi) * -0.30f);
            hbl.setRotX(hbl.getRotX() + -Mth.cos(lerpHelper * pi) * -0.30f);
        }
    }

    public CoreGeoBone getB(String bone) {
        return this.getAnimationProcessor().getBone(bone);
    }
}
