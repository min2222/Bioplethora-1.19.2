package io.github.bioplethora.client.entity.model;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.api.IAdvancedGeoModel;
import io.github.bioplethora.entity.creatures.VoidjawEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class VoidjawEntityModel extends GeoModel<VoidjawEntity> implements IAdvancedGeoModel<VoidjawEntity> {

    @Override
    public ResourceLocation getModelResource(VoidjawEntity entity) {
        if (!entity.isSaddled()) {
            return new ResourceLocation(Bioplethora.MOD_ID, "geo/voidjaw.geo.json");
        } else {
            return new ResourceLocation(Bioplethora.MOD_ID, "geo/voidjaw_saddled.geo.json");
        }
    }

    @Override
    public ResourceLocation getTextureResource(VoidjawEntity entity) {
        if (entity.isCardinalVariant()) {
            return new ResourceLocation(Bioplethora.MOD_ID, "textures/entity/cardinal_voidjaw.png");
        } else {
            return new ResourceLocation(Bioplethora.MOD_ID, "textures/entity/voidjaw.png");
        }
    }

    @Override
    public ResourceLocation getAnimationResource(VoidjawEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "animations/voidjaw.animation.json");
    }

    @Override
    public void setCustomAnimations(VoidjawEntity entity, long uniqueID, @SuppressWarnings("rawtypes") AnimationState event) {
        super.setCustomAnimations(entity, uniqueID, event);
        EntityModelData extraData = (EntityModelData) event.getData(DataTickets.ENTITY_MODEL_DATA);
        adaptHeadOnLook(extraData, getAnimationProcessor().getBone("trapjaw"));
    }
}
