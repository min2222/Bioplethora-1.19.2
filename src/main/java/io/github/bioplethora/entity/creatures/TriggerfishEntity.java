package io.github.bioplethora.entity.creatures;

import javax.annotation.Nullable;

import io.github.bioplethora.registry.BPItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.animal.AbstractSchoolingFish;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType.EDefaultLoopTypes;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class TriggerfishEntity extends AbstractSchoolingFish implements IAnimatable {
    private static final EntityDataAccessor<Boolean> IS_END = SynchedEntityData.defineId(TriggerfishEntity.class, EntityDataSerializers.BOOLEAN);
    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);

    public TriggerfishEntity(EntityType<? extends AbstractSchoolingFish> type, Level world) {
        super(type, world);
    }

    @Override
	public ItemStack getBucketItemStack() {
        return new ItemStack(BPItems.TRIGGERFISH_BUCKET.get());
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.TROPICAL_FISH_AMBIENT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.TROPICAL_FISH_DEATH;
    }

    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return SoundEvents.TROPICAL_FISH_HURT;
    }

    @Override
    protected SoundEvent getFlopSound() {
        return SoundEvents.TROPICAL_FISH_FLOP;
    }

    @Override
    public int getMaxSchoolSize() {
        return 7;
    }

    @Override
	public float getVoicePitch() {
        return 0.75F;
    }

    public void saveToBucketTag(ItemStack pBucketStack) {
        super.saveToBucketTag(pBucketStack);
        CompoundTag compoundnbt = pBucketStack.getOrCreateTag();
        compoundnbt.putInt("BucketVariantTag", this.getIsEnd() ? 1 : 0);
    }

    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {
        pSpawnData = super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
        if (pDataTag != null && pDataTag.contains("BucketVariantTag", 3)) {
            this.setIsEnd(pDataTag.getInt("BucketVariantTag") == 1);
        } else {
            if (this.level.dimension().equals(Level.END)) {
                this.setIsEnd(true);
            }
        }
        return pSpawnData;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "triggerfish_controller", 0, this::predicate));
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.triggerfish.swim", EDefaultLoopTypes.LOOP));
        return PlayState.CONTINUE;
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(IS_END, false);
    }

    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putBoolean("isEnd", this.getIsEnd());
    }

    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.setIsEnd(pCompound.getBoolean("isEnd"));
    }

    public void setIsEnd(boolean value) {
        this.entityData.set(IS_END, value);
    }

    public boolean getIsEnd() {
        return this.entityData.get(IS_END);
    }
}
