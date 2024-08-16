package io.github.bioplethora.client.entity.model;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.entity.creatures.DwarfMossadileEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class DwarfMossadileEntityModel extends GeoModel<DwarfMossadileEntity> {

    @Override
    public ResourceLocation getModelResource(DwarfMossadileEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "geo/dwarf_mossadile.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(DwarfMossadileEntity entity) {
        if (entity.isNetherVariant()) {
            return new ResourceLocation(Bioplethora.MOD_ID, "textures/entity/dwarf_mossadile_nether.png");
        } else {
            return new ResourceLocation(Bioplethora.MOD_ID, "textures/entity/dwarf_mossadile.png");
        }
    }

    @Override
    public ResourceLocation getAnimationResource(DwarfMossadileEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "animations/dwarf_mossadile.animation.json");
    }

    /*@Override
    public void setCustomAnimations(DwarfMossadileEntity entity, long uniqueID, @SuppressWarnings("rawtypes") AnimationState customPredicate) {
        super.setCustomAnimations(entity, uniqueID, customPredicate);

        CoreGeoBone head = this.getAnimationProcessor().getBone("head2");
        EntityModelData extraData = (EntityModelData) customPredicate.getExtraData().get(0);
        head.setRotX((extraData.headPitch()) * ((float) Math.PI / 180F));
        head.setRotY((extraData.netHeadYaw()) * ((float) Math.PI / 270F));
    }*/
}
