package io.github.bioplethora.client.entity.model;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.entity.creatures.EurydnEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationProcessor;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class EurydnEntityModel extends GeoModel<EurydnEntity> {

    @Override
    public ResourceLocation getModelResource(EurydnEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "geo/eurydn.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(EurydnEntity entity) {
        if (entity.variant == EurydnEntity.Variant.FIERY) {
            return new ResourceLocation(Bioplethora.MOD_ID, "textures/entity/fiery_eurydn.png");
        } else if (entity.variant == EurydnEntity.Variant.SOUL) {
            return new ResourceLocation(Bioplethora.MOD_ID, "textures/entity/soul_eurydn.png");
        } else {
            throw new IllegalStateException("Invalid Eurydn variant!");
        }
    }

    @Override
    public ResourceLocation getAnimationResource(EurydnEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "animations/eurydn.animation.json");
    }

    @Override
    public void setCustomAnimations(EurydnEntity entity, long uniqueID, @SuppressWarnings("rawtypes") AnimationState event) {
        super.setCustomAnimations(entity, uniqueID, event);

        AnimationProcessor ap = this.getAnimationProcessor();
        EntityModelData extraData = (EntityModelData) event.getExtraData().get(0);
        CoreGeoBone head = ap.getBone("head");
        CoreGeoBone tailfront = ap.getBone("tailfront"), tailmid = ap.getBone("tailmid"), tailbot = ap.getBone("tailbot");

        head.setRotX((extraData.headPitch()) * ((float) Math.PI / 180F));
        head.setRotY((extraData.netHeadYaw()) * ((float) Math.PI / 270F));

        tailfront.setRotX(tailfront.getRotX() + this.tailRotationHelper(0.4F, 0.5F * 0.1F, 3F, -0.1F, event.getLimbSwing(), event.getLimbSwingAmount(), false));
        tailmid.setRotX(tailmid.getRotX() + this.tailRotationHelper(0.4F, 0.5F * 0.1F, 3F, -0.1F, event.getLimbSwing(), event.getLimbSwingAmount(), true));
        tailbot.setRotX(tailbot.getRotX() + this.tailRotationHelper(0.4F, 0.5F * 0.1F, 3F, -0.1F, event.getLimbSwing(), event.getLimbSwingAmount(), true));
    }

    public float tailRotationHelper(float speed, float degree, float offset, float weight, float limbswing, float limbswingamount, boolean neg) {
        float rotation = (Mth.cos(limbswing * (speed) + offset) * (degree) * limbswingamount) + (weight * limbswingamount);
        return neg ? -rotation : rotation;
    }
}
