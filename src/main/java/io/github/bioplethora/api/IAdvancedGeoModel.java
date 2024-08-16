package io.github.bioplethora.api;

import net.minecraft.world.entity.LivingEntity;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.model.data.EntityModelData;

public interface IAdvancedGeoModel<E extends LivingEntity> {

    default void adaptHeadOnLook(EntityModelData data, CoreGeoBone... bones) {
        for (CoreGeoBone bone : bones) {
            adaptHeadOnLook(data, bone);
        }
    }

    default void adaptHeadOnLook(EntityModelData data, CoreGeoBone bone) {
        bone.setRotX((data.headPitch()) * ((float) Math.PI / 180F));
        bone.setRotY((data.netHeadYaw()) * ((float) Math.PI / 270F));
    }
}
