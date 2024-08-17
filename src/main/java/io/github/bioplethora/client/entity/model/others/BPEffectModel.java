package io.github.bioplethora.client.entity.model.others;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.entity.others.BPEffectEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class BPEffectModel extends GeoModel<BPEffectEntity> {

    @Override
    public ResourceLocation getModelResource(BPEffectEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "geo/others/" + entity.getEffectType().getModel().getModelString() + ".geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(BPEffectEntity entity) {
        if (entity.getEffectType().getFrames() > 0) {
            return new ResourceLocation(Bioplethora.MOD_ID, "textures/entity/others/" + entity.getEffectType().getTexture() + "_" + entity.getFrameLevel() + ".png");
        } else {
            return new ResourceLocation(Bioplethora.MOD_ID, "textures/entity/others/" + entity.getEffectType().getTexture() + ".png");
        }
    }

    @Override
    public ResourceLocation getAnimationResource(BPEffectEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "animations/others/bp_effect.animation.json");
    }

    /*
    @Override
    public void setCustomAnimations(InfernalQuarterstaffSlashEntity entity, long uniqueID, @Nullable AnimationState customPredicate) {
        super.setCustomAnimations(entity, uniqueID, customPredicate);
        CoreGeoBone head = this.getAnimationProcessor().getBone("bone");
        EntityModelData extraData = (EntityModelData) customPredicate.getData(DataTickets.ENTITY_MODEL_DATA);
        head.setRotY((extraData.netHeadYaw()) * ((float) Math.PI / 270F));
    }*/
}
