package io.github.bioplethora.entity;

import io.github.bioplethora.entity.ai.gecko.IGeckoBaseEntity;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

/**
 * Credits: WeirdNerd (Permission Granted)
 */
public abstract class BPMonsterEntity extends Monster implements IAnimatable, IGeckoBaseEntity {

    protected static final EntityDataAccessor<Boolean> MOVING = SynchedEntityData.defineId(BPMonsterEntity.class, EntityDataSerializers.BOOLEAN);
    protected static final EntityDataAccessor<Boolean> ATTACKING = SynchedEntityData.defineId(BPMonsterEntity.class, EntityDataSerializers.BOOLEAN);

    public int prevHurtTime;
    public boolean hurtRandomizer;

    @OnlyIn(Dist.CLIENT)
    public float ageInTicks;

    public BPMonsterEntity(EntityType<? extends Monster> type, Level worldIn) {
        super(type, worldIn);
    }

    @Override
    abstract public void registerControllers(AnimationData data);

    @Override
    abstract public AnimationFactory getFactory();

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        this.hurtRandomizer = this.getRandom().nextBoolean();
        return super.hurt(pSource, pAmount);
    }

    @Override
    public void baseTick() {
        this.prevHurtTime = hurtTime;
        super.baseTick();
    }

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