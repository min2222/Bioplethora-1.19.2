package io.github.bioplethora.client.entity.render;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.entity.BPBoatEntity;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.vehicle.Boat;

public class BPBoatRender extends BoatRenderer {

    public BPBoatRender(EntityRendererProvider.Context manager) {
        super(manager, false);
    }

    @Override
    public ResourceLocation getTextureLocation(Boat entity) {
        BPBoatEntity bpBoat = ((BPBoatEntity) entity);
        return new ResourceLocation(Bioplethora.MOD_ID, "textures/entity/boat/" + bpBoat.getWoodType() + ".png");
    }
}
