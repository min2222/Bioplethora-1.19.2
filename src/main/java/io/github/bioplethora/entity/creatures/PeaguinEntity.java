package io.github.bioplethora.entity.creatures;

import java.util.UUID;
import java.util.function.Predicate;

import javax.annotation.Nullable;

import io.github.bioplethora.config.BPConfig;
import io.github.bioplethora.entity.BPAnimalEntity;
import io.github.bioplethora.entity.IBioClassification;
import io.github.bioplethora.entity.WaterAndLandAnimalEntity;
import io.github.bioplethora.entity.ai.gecko.GeckoMeleeGoal;
import io.github.bioplethora.entity.ai.gecko.GeckoMoveToTargetGoal;
import io.github.bioplethora.entity.ai.goals.WaterFollowOwnerGoal;
import io.github.bioplethora.enums.BPEntityClasses;
import io.github.bioplethora.registry.BPEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.SitWhenOrderedToGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NonTameRandomTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class PeaguinEntity extends WaterAndLandAnimalEntity implements GeoEntity, NeutralMob, IBioClassification {

    private static final EntityDataAccessor<Integer> DATA_REMAINING_ANGER_TIME = SynchedEntityData.defineId(PeaguinEntity.class, EntityDataSerializers.INT);
    private static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(20, 39);

    public static final Predicate<LivingEntity> PREY_SELECTOR = (entity) -> entity instanceof AbstractFish;

    private UUID persistentAngerTarget;

    private final AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);

    public PeaguinEntity(EntityType<? extends BPAnimalEntity> type, Level worldIn) {
        super(type, worldIn);
        this.setTame(false);
        this.noCulling = true;
    }

    @Override
    public BPEntityClasses getBioplethoraClass() {
        return BPEntityClasses.PLETHONEUTRAL;
    }

    public static AttributeSupplier.Builder setCustomAttributes() {
        return Mob.createLivingAttributes()
                .add(Attributes.ARMOR, 2 * BPConfig.COMMON.mobArmorMultiplier.get())
                .add(Attributes.ATTACK_SPEED, 1.5)
                .add(Attributes.ATTACK_DAMAGE, 2 * BPConfig.COMMON.mobMeeleeDamageMultiplier.get())
                .add(Attributes.ATTACK_KNOCKBACK, 0.5 * BPConfig.COMMON.mobMeeleeDamageMultiplier.get())
                .add(Attributes.MAX_HEALTH, 20 * BPConfig.COMMON.mobHealthMultiplier.get())
                .add(Attributes.MOVEMENT_SPEED, 0.25 * BPConfig.COMMON.mobMovementSpeedMultiplier.get())
                .add(Attributes.FOLLOW_RANGE, 16D);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(2, new SitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(4, new GeckoMoveToTargetGoal<PeaguinEntity>(this, 1.6, 8) {
            @Override
            public boolean canUse() {
                if (!entity.isInWater()) {
                    return super.canUse();
                } else {
                    return false;
                }
            }

            @Override
            public boolean canContinueToUse() {
                if (!entity.isInWater()) {
                    return super.canContinueToUse();
                } else {
                    return false;
                }
            }
        });
        this.goalSelector.addGoal(4, new GeckoMeleeGoal<>(this, 20, 0.5, 0.75));
        this.goalSelector.addGoal(5, new WaterFollowOwnerGoal(this, 1.2D, 10.0F, 1.0F, false));
        this.goalSelector.addGoal(5, new FollowOwnerGoal(this, 1.2D, 10.0F, 1.0F, false));
        this.goalSelector.addGoal(6, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(7, new RandomStrollGoal(this, 1.2, 8));

        this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(11, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(2, (new HurtByTargetGoal(this)).setAlertOthers());
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, this::isAngryAt));
        this.targetSelector.addGoal(3, new NonTameRandomTargetGoal<>(this, AbstractFish.class, false, PREY_SELECTOR));
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar data) {
        data.add(new AnimationController<>(this, "peaguin_controller", 0, this::predicate));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.factory;
    }

    @Override
    public SoundEvent getAmbientSound() {
        if (this.isInWater()) {
            return SoundEvents.DOLPHIN_AMBIENT_WATER;
        } else {
            return SoundEvents.DOLPHIN_AMBIENT;
        }
    }

    @Override
    public SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.DOLPHIN_HURT;
    }

    @Override
    public SoundEvent getDeathSound() {
        return SoundEvents.DOLPHIN_DEATH;
    }

    @Override
    public void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.SALMON_FLOP, 0.15f, 1);
    }

    private <E extends GeoEntity> PlayState predicate(AnimationState<E> event) {

        if (this.isDeadOrDying() || this.dead) {
            event.getController().setAnimation(RawAnimation.begin().thenPlay("animation.peaguin.death"));
            return PlayState.CONTINUE;
        }

        if (this.isInSittingPose()) {
            event.getController().setAnimation(RawAnimation.begin().thenPlay("animation.peaguin.sitting"));
            return PlayState.CONTINUE;
        }

        if (this.getAttacking() && this.shouldDropLoot()) {
            event.getController().setAnimation(RawAnimation.begin().thenPlay("animation.peaguin.attacking"));
            return PlayState.CONTINUE;
        }

        if (this.isSwimming() || this.isInWaterOrBubble() || this.isInWater()) {
            event.getController().setAnimation(RawAnimation.begin().thenPlay("animation.peaguin.swimming"));
            return PlayState.CONTINUE;
        }

        if (event.isMoving()) {
            event.getController().setAnimation(RawAnimation.begin().thenPlay("animation.peaguin.walking"));
            return PlayState.CONTINUE;
        }

        event.getController().setAnimation(RawAnimation.begin().thenPlay("animation.peaguin.idle"));
        return PlayState.CONTINUE;
    }

    public PeaguinEntity getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableEntity) {
        PeaguinEntity peaguinEntity = BPEntities.PEAGUIN.get().create(serverLevel);
        UUID uuid = this.getOwnerUUID();
        if (uuid != null) {
            peaguinEntity.setOwnerUUID(uuid);
            peaguinEntity.setTame(true);
        }

        return peaguinEntity;
    }

    protected float getStandingEyeHeight(Pose pose, EntityDimensions entitySize) {
        return this.isBaby() ? entitySize.height * 0.8F : 1.0F;
    }

    public boolean isFood(ItemStack pStack) {
        return pStack.is(ItemTags.FISHES);
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_REMAINING_ANGER_TIME, 0);
    }

    public InteractionResult mobInteract(Player entity, InteractionHand resultType) {
        ItemStack itemstack = entity.getItemInHand(resultType);
        Item item = itemstack.getItem();
        if (this.level.isClientSide) {
            boolean flag = this.isOwnedBy(entity) || this.isTame() || this.isFood(itemstack) && !this.isTame() && !this.isAngry();
            return flag ? InteractionResult.CONSUME : InteractionResult.PASS;
        } else {
            if (this.isTame()) {
                if (this.isFood(itemstack) && this.getHealth() < this.getMaxHealth()) {
                    if (!entity.getAbilities().instabuild) {
                        itemstack.shrink(1);
                    }

                    this.heal((float) item.getFoodProperties().getNutrition());
                    return InteractionResult.SUCCESS;
                }

                InteractionResult actionresulttype = super.mobInteract(entity, resultType);
                if ((!actionresulttype.consumesAction() || this.isBaby()) && this.isOwnedBy(entity)) {
                    this.setOrderedToSit(!this.isOrderedToSit());
                    this.jumping = false;
                    this.navigation.stop();
                    this.setTarget(null);
                    return InteractionResult.SUCCESS;
                }

                return actionresulttype;

            } else if (this.isFood(itemstack) && !this.isAngry()) {
                if (!entity.getAbilities().instabuild) {
                    itemstack.shrink(1);
                }

                if (this.random.nextInt(3) == 0 && !net.minecraftforge.event.ForgeEventFactory.onAnimalTame(this, entity)) {
                    this.tame(entity);
                    this.navigation.stop();
                    this.setTarget(null);
                    this.setOrderedToSit(true);
                    this.level.broadcastEntityEvent(this, (byte) 7);
                } else {
                    this.level.broadcastEntityEvent(this, (byte) 6);
                }

                return InteractionResult.SUCCESS;
            }

            return super.mobInteract(entity, resultType);
        }
    }

    public boolean hurt(DamageSource damageSource, float p_70097_2_) {
        if (this.isInvulnerableTo(damageSource)) {
            return false;
        } else {
            Entity entity = damageSource.getEntity();
            this.setOrderedToSit(false);
            if (entity != null && !(entity instanceof Player) && !(entity instanceof AbstractArrow)) {
                p_70097_2_ = (p_70097_2_ + 1.0F) / 2.0F;
            }

            return super.hurt(damageSource, p_70097_2_);
        }
    }

    public boolean canMate(Animal entity) {
        if (entity == this) {
            return false;
        } else if (!this.isTame()) {
            return false;
        } else if (!(entity instanceof PeaguinEntity)) {
            return false;
        } else {
            PeaguinEntity peaguinEntity = (PeaguinEntity) entity;
            if (!peaguinEntity.isTame()) {
                return false;
            } else if (peaguinEntity.isInSittingPose()) {
                return false;
            } else {
                return this.isInLove() && peaguinEntity.isInLove();
            }
        }
    }

    public boolean wantsToAttack(LivingEntity p_142018_1_, LivingEntity p_142018_2_) {
        if (!(p_142018_1_ instanceof Creeper) && !(p_142018_1_ instanceof Ghast)) {
            if (p_142018_1_ instanceof PeaguinEntity) {
                PeaguinEntity peaguinEntity = (PeaguinEntity) p_142018_1_;
                return !peaguinEntity.isTame() || peaguinEntity.getOwner() != p_142018_2_;
            } else if (p_142018_1_ instanceof Player && p_142018_2_ instanceof Player && !((Player) p_142018_2_).canHarmPlayer((Player) p_142018_1_)) {
                return false;
            } else if (p_142018_1_ instanceof AbstractHorse && ((AbstractHorse) p_142018_1_).isTamed()) {
                return false;
            } else {
                return !(p_142018_1_ instanceof TamableAnimal) || !((TamableAnimal) p_142018_1_).isTame();
            }
        } else {
            return false;
        }
    }

    public int getRemainingPersistentAngerTime() {
        return this.entityData.get(DATA_REMAINING_ANGER_TIME);
    }

    public void setRemainingPersistentAngerTime(int p_230260_1_) {
        this.entityData.set(DATA_REMAINING_ANGER_TIME, p_230260_1_);
    }

    public void startPersistentAngerTimer() {
        this.setRemainingPersistentAngerTime(PERSISTENT_ANGER_TIME.sample(this.random));
    }

    @Nullable
    public UUID getPersistentAngerTarget() {
        return this.persistentAngerTarget;
    }

    public void setPersistentAngerTarget(@Nullable UUID p_230259_1_) {
        this.persistentAngerTarget = p_230259_1_;
    }

    public void addAdditionalSaveData(CompoundTag compoundNBT) {
        super.addAdditionalSaveData(compoundNBT);

        this.addPersistentAngerSaveData(compoundNBT);
    }

    public void readAdditionalSaveData(CompoundTag compoundNBT) {
        super.readAdditionalSaveData(compoundNBT);

        if (!level.isClientSide)
            this.readPersistentAngerSaveData((ServerLevel) this.level, compoundNBT);
    }

    public boolean canBreatheUnderwater() {
        return true;
    }

    public MobType getMobType() {
        return MobType.WATER;
    }

    public boolean checkSpawnObstruction(LevelReader p_205019_1_) {
        return p_205019_1_.isUnobstructed(this);
    }

    public int getAmbientSoundInterval() {
        return 120;
    }

    protected int getExperienceReward(Player playerEntity) {
        return 1 + this.level.random.nextInt(3);
    }

    public boolean isPushedByFluid() {
        return false;
    }

    public boolean canBeLeashed(Player p_184652_1_) {
        return !this.isAngry() && super.canBeLeashed(p_184652_1_);
    }

    @OnlyIn(Dist.CLIENT)
    public Vec3 getLeashOffset() {
        return new Vec3(0.0D, 0.6D * this.getEyeHeight(), this.getBbWidth() * 0.4F);
    }
}
