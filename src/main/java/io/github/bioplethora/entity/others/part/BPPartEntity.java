package io.github.bioplethora.entity.others.part;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Pose;
import net.minecraftforge.entity.PartEntity;

public class BPPartEntity<T extends Mob> extends PartEntity<T> {
    public final T parentMob;
    public final String name;
    private final EntityDimensions size;

    public BPPartEntity(T parent, String name, float width, float height) {
        super(parent);
        size = EntityDimensions.scalable(width, height);
        refreshDimensions();
        parentMob = parent;
        this.name = name;
    }

    @Override
    public EntityDimensions getDimensions(Pose pPose) {
        return size;
    }

    @Override
    public boolean isPickable() {
        return true;
    }

    @Override
    public boolean is(Entity pEntity) {
        return this == pEntity || parentMob == pEntity;
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        return !isInvulnerableTo(pSource) && this.getParent().hurt(pSource, pAmount);
    }

    @Override
    protected void defineSynchedData() {
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {
    }
}
