package io.github.bioplethora.entity;

import io.github.bioplethora.api.world.EntityUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;

public abstract class SummonableMonsterEntity extends BPMonsterEntity implements GeoEntity {

    private LivingEntity owner;
    private boolean hasLimitedLife;
    private boolean explodeOnExpiry;
    private int lifeLimitBeforeDeath;
    private int limitedLifeTicks = 0;

    public SummonableMonsterEntity(EntityType<? extends Monster> type, Level worldIn) {
        super(type, worldIn);
    }

    @Override
    abstract public void registerControllers(AnimatableManager.ControllerRegistrar data);

    @Override
    abstract public AnimatableInstanceCache getAnimatableInstanceCache();

    public LivingEntity getOwner() {
        return this.owner;
    }

    public void setOwner(LivingEntity mobEntity) {
        this.owner = mobEntity;
    }

    public void addAdditionalSaveData(CompoundTag compoundNBT) {
        super.addAdditionalSaveData(compoundNBT);
        compoundNBT.putBoolean("HasLifeLimit", this.hasLimitedLife);
        if (this.hasLimitedLife) {
            compoundNBT.putInt("LifeTicks", this.limitedLifeTicks);
            compoundNBT.putInt("LifeLimit", this.lifeLimitBeforeDeath);
            compoundNBT.putBoolean("ExplodeOnExpiry", this.explodeOnExpiry);
        }
    }

    public void readAdditionalSaveData(CompoundTag compoundNBT) {
        super.readAdditionalSaveData(compoundNBT);
        this.setHasLimitedLife(compoundNBT.getBoolean("HasLifeLimit"));
        this.setLimitedLifeTicks(compoundNBT.getInt("LifeTicks"));
        this.setLifeLimitBeforeDeath(compoundNBT.getInt("LifeLimit"));
        this.setExplodeOnExpiry(compoundNBT.getBoolean("ExplodeOnExpiry"));
    }

    public void setLimitedLifeTicks(int limitedLifeTicks) {
        this.limitedLifeTicks = limitedLifeTicks;
    }

    public void setHasLimitedLife(boolean hasLimitedLife) {
        this.hasLimitedLife = hasLimitedLife;
    }

    public void setExplodeOnExpiry(boolean explodeOnExpiry) {
        this.explodeOnExpiry = explodeOnExpiry;
    }

    public void setLifeLimitBeforeDeath(int lifeLimitBeforeDeath) {
        this.lifeLimitBeforeDeath = lifeLimitBeforeDeath;
    }

    public void aiStep() {
        super.aiStep();
        if (this.hasLimitedLife) {
            ++limitedLifeTicks;

            if (this.limitedLifeTicks >= this.lifeLimitBeforeDeath) {
                if (this.explodeOnExpiry) {
                    this.level.explode(this, this.getX(), this.getY(0.0625D), this.getZ(), 3F, EntityUtils.getMobGriefingEvent(this.level, this));
                }
                this.discard();
            }

            if (this.getOwner() != null) {
                if (!this.level.isClientSide && this.getOwner().isDeadOrDying() && this.explodeOnExpiry) {
                    this.level.explode(this, this.getX(), this.getY(0.0625D), this.getZ(), 2F, EntityUtils.getMobGriefingEvent(this.level, this));
                    this.kill();
                }
            }
        }
    }
}
