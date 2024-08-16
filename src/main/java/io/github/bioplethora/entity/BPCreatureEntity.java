package io.github.bioplethora.entity;

import io.github.bioplethora.entity.ai.gecko.IGeckoBaseEntity;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;

/**
 * Credits: WeirdNerd (Permission Granted)
 */
public abstract class BPCreatureEntity extends PathfinderMob implements GeoEntity, IGeckoBaseEntity {

    protected static final EntityDataAccessor<Boolean> MOVING = SynchedEntityData.defineId(BPCreatureEntity.class, EntityDataSerializers.BOOLEAN);
    protected static final EntityDataAccessor<Boolean> ATTACKING = SynchedEntityData.defineId(BPCreatureEntity.class, EntityDataSerializers.BOOLEAN);

    @Override
    public boolean hurt(DamageSource p_70097_1_, float p_70097_2_) {
        return super.hurt(p_70097_1_, p_70097_2_);
    }

    public BPCreatureEntity(EntityType<? extends PathfinderMob> type, Level worldIn) {
        super(type, worldIn);
    }

    @Override
    abstract public void registerControllers(AnimatableManager.ControllerRegistrar data);

    @Override
    abstract public AnimatableInstanceCache getAnimatableInstanceCache();

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(MOVING, false);
        this.entityData.define(ATTACKING, false);
    }

    public boolean getMoving() {
        return this.entityData.get(MOVING);
    }

    public void setMoving(boolean moving) {
        this.entityData.set(MOVING, moving);
    }

    public boolean getAttacking() {
        return this.entityData.get(ATTACKING);
    }

    public void setAttacking(boolean attacking) {
        this.entityData.set(ATTACKING, attacking);
    }
}