package io.github.bioplethora.entity.ai.controller;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.phys.Vec3;

public class WaterMoveController extends MoveControl {

    private final PathfinderMob entity;

    public WaterMoveController(PathfinderMob entity, double pSpeed) {
        super(entity);
        this.entity = entity;
        this.speedModifier = pSpeed;
    }

    public void tick() {
        if (this.operation == MoveControl.Operation.MOVE_TO) {
            Vec3 vector3d = new Vec3(this.wantedX - entity.getX(), this.wantedY - entity.getY(), this.wantedZ - entity.getZ());
            double d0 = vector3d.length();
            if (d0 < entity.getBoundingBox().getSize()) {
                this.operation = MoveControl.Operation.WAIT;
                entity.setDeltaMovement(entity.getDeltaMovement().scale(0.5D));
            } else {
                entity.setDeltaMovement(entity.getDeltaMovement().add(vector3d.scale(this.speedModifier * 0.05D / d0)));
                if (entity.getTarget() == null) {
                    Vec3 vector3d1 = entity.getDeltaMovement();
                    entity.yRot = -((float) Mth.atan2(vector3d1.x, vector3d1.z)) * (180F / (float)Math.PI);
                    entity.yBodyRot = entity.getYRot();
                } else {
                    double d2 = entity.getTarget().getX() - entity.getX();
                    double d1 = entity.getTarget().getZ() - entity.getZ();
                    entity.yRot = -((float)Mth.atan2(d2, d1)) * (180F / (float)Math.PI);
                    entity.yBodyRot = entity.getYRot();
                }	
            }
        }
    }
}
