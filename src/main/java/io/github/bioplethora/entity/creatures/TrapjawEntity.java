package io.github.bioplethora.entity.creatures;

import java.util.UUID;
import java.util.function.Predicate;

import javax.annotation.Nullable;

import io.github.bioplethora.config.BPConfig;
import io.github.bioplethora.entity.BPAnimalEntity;
import io.github.bioplethora.entity.IBioClassification;
import io.github.bioplethora.entity.ISaddleable;
import io.github.bioplethora.entity.WaterAndLandAnimalEntity;
import io.github.bioplethora.entity.ai.gecko.GeckoMeleeGoal;
import io.github.bioplethora.entity.ai.gecko.GeckoMoveToTargetGoal;
import io.github.bioplethora.entity.ai.goals.WaterFollowOwnerGoal;
import io.github.bioplethora.enums.BPEntityClasses;
import io.github.bioplethora.registry.BPEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.ItemSteerable;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
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
import net.minecraft.world.entity.ai.goal.target.NonTameRandomTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.vehicle.DismountHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SaddleItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fluids.FluidType;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class TrapjawEntity extends WaterAndLandAnimalEntity implements GeoEntity, IBioClassification, ItemSteerable, ISaddleable {

    private static final EntityDataAccessor<Boolean> CARDINAL = SynchedEntityData.defineId(TrapjawEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> HAS_SADDLE = SynchedEntityData.defineId(TrapjawEntity.class, EntityDataSerializers.BOOLEAN);
    private final AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);
    public static final Predicate<LivingEntity> PREY_SELECTOR = (entity) ->
            entity instanceof Player ||  entity instanceof AlphemKingEntity || (entity instanceof Animal && !(entity instanceof TrapjawEntity));

    public TrapjawEntity(EntityType<? extends BPAnimalEntity> type, Level worldIn) {
        super(type, worldIn);
        this.setTame(false);
        this.noCulling = true;
    }

    @Override
    public BPEntityClasses getBioplethoraClass() {
        return BPEntityClasses.DANGERUM;
    }

    public static AttributeSupplier.Builder setCustomAttributes() {
        return Mob.createLivingAttributes()
                .add(Attributes.ARMOR, 4 * BPConfig.COMMON.mobArmorMultiplier.get())
                .add(Attributes.ATTACK_SPEED, 1.5)
                .add(Attributes.ATTACK_DAMAGE, 8 * BPConfig.COMMON.mobMeeleeDamageMultiplier.get())
                .add(Attributes.ATTACK_KNOCKBACK, 0.5 * BPConfig.COMMON.mobMeeleeDamageMultiplier.get())
                .add(Attributes.MAX_HEALTH, 80 * BPConfig.COMMON.mobHealthMultiplier.get())
                .add(Attributes.MOVEMENT_SPEED, 0.25D * BPConfig.COMMON.mobMovementSpeedMultiplier.get())
                .add(Attributes.FOLLOW_RANGE, 32D);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(2, new SitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(4, new GeckoMoveToTargetGoal<TrapjawEntity>(this, 1.6, 8) {
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
        this.goalSelector.addGoal(4, new GeckoMeleeGoal<>(this, 30, 0.5, 0.6));
        this.goalSelector.addGoal(5, new WaterFollowOwnerGoal(this, 1.2D, 10.0F, 2.0F, false));
        this.goalSelector.addGoal(5, new FollowOwnerGoal(this, 1.2D, 10.0F, 2.0F, false));
        this.goalSelector.addGoal(6, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(7, new RandomStrollGoal(this, 1.2, 8));
        this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(11, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(2, (new HurtByTargetGoal(this)).setAlertOthers());
        this.targetSelector.addGoal(1, new NonTameRandomTargetGoal<>(this, LivingEntity.class, false, PREY_SELECTOR));
    }

    // TODO: 11/03/2022 Pull the target to the mob 
    @Override
    public boolean doHurtTarget(Entity entity) {

        if (entity instanceof LivingEntity) {
            ((LivingEntity) entity).knockback(2, entity.getX() - this.getX(), entity.getZ() - this.getZ());
        }
        return super.doHurtTarget(entity);
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {

        if (Math.random() <= 0.05) {
            this.setCardinalVariant(true);
            if (BPConfig.IN_HELLMODE) {
                this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(16 * BPConfig.COMMON.mobMeeleeDamageMultiplier.get());
                this.getAttribute(Attributes.ARMOR).setBaseValue(8 * BPConfig.COMMON.mobArmorMultiplier.get());
                this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(145 * BPConfig.COMMON.mobHealthMultiplier.get());
                this.setHealth(145 * BPConfig.COMMON.mobHealthMultiplier.get());
            } else {
                this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(12 * BPConfig.COMMON.mobMeeleeDamageMultiplier.get());
                this.getAttribute(Attributes.ARMOR).setBaseValue(6.5 * BPConfig.COMMON.mobArmorMultiplier.get());
                this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(110 * BPConfig.COMMON.mobHealthMultiplier.get());
                this.setHealth(110 * BPConfig.COMMON.mobHealthMultiplier.get());
            }
        } else if (BPConfig.IN_HELLMODE) {
            this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(10 * BPConfig.COMMON.mobMeeleeDamageMultiplier.get());
            this.getAttribute(Attributes.ARMOR).setBaseValue(6 * BPConfig.COMMON.mobArmorMultiplier.get());
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(100 * BPConfig.COMMON.mobHealthMultiplier.get());
            this.setHealth(100 * BPConfig.COMMON.mobHealthMultiplier.get());
        }

        return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
    }

    @Override
    public void aiStep() {
        super.aiStep();
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob entity) {
        TrapjawEntity trapjaw = BPEntities.TRAPJAW.get().create(serverLevel);
        UUID uuid = this.getOwnerUUID();
        if (uuid != null) {
            trapjaw.setOwnerUUID(uuid);
            trapjaw.setTame(true);
        }

        return trapjaw;
    }

    protected float getStandingEyeHeight(Pose pose, EntityDimensions entitySize) {
        return this.isBaby() ? entitySize.height * 0.8F : 1.0F;
    }

    public boolean isFood(ItemStack pStack) {
        Item item = pStack.getItem();
        if (item.getFoodProperties() != null) {
            return item.getFoodProperties().isMeat();
        } else {
            return false;
        }
    }

    @Override
    public int getMaxSpawnClusterSize() {
        return 2;
    }

    public InteractionResult mobInteract(Player entity, InteractionHand resultType) {
        ItemStack itemstack = entity.getItemInHand(resultType);
        Item item = itemstack.getItem();
        if (this.level.isClientSide) {
            boolean flag = this.isOwnedBy(entity) || this.isTame() || this.isFood(itemstack) && !this.isTame();
            return flag ? InteractionResult.CONSUME : InteractionResult.PASS;
        } else {
            if (entity.isCrouching()) {
                if (this.isTame()) {
                    if (this.isFood(itemstack) && this.getHealth() < this.getMaxHealth()) {
                        if (!entity.getAbilities().instabuild) {
                            itemstack.shrink(1);
                        }

                        this.heal((float) item.getFoodProperties().getNutrition());
                        return InteractionResult.SUCCESS;

                    } else if (item instanceof SaddleItem && !this.isBaby()) {
                        if (!this.isSaddled()) {
                            this.setSaddled(true);
                            itemstack.shrink(1);
                            return InteractionResult.SUCCESS;
                        }
                        return InteractionResult.PASS;
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

                } else if (this.isFood(itemstack)) {
                    if (!entity.getAbilities().instabuild) {
                        itemstack.shrink(1);
                    }

                    if (this.random.nextInt(15) == 0 && !net.minecraftforge.event.ForgeEventFactory.onAnimalTame(this, entity)) {
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

            } else if (!entity.isCrouching()) {
                if (this.isSaddled() && !this.isInSittingPose()) {
                    entity.startRiding(this);
                }
            }
            return super.mobInteract(entity, resultType);
        }
    }

    public void addAdditionalSaveData(CompoundTag compoundNBT) {
        super.addAdditionalSaveData(compoundNBT);
        compoundNBT.putBoolean("isCardinal", this.isCardinalVariant());
        compoundNBT.putBoolean("isSaddled", this.isSaddled());
    }

    public void readAdditionalSaveData(CompoundTag compoundNBT) {
        super.readAdditionalSaveData(compoundNBT);
        this.setCardinalVariant(compoundNBT.getBoolean("isCardinal"));
        this.setSaddled(compoundNBT.getBoolean("isSaddled"));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(CARDINAL, false);
        this.entityData.define(HAS_SADDLE, false);
    }

    public boolean isSaddled() {
        return this.entityData.get(HAS_SADDLE);
    }

    public void setSaddled(boolean saddled) {
        this.entityData.set(HAS_SADDLE, saddled);
    }

    public boolean isCardinalVariant() {
        return this.entityData.get(CARDINAL);
    }

    public void setCardinalVariant(boolean cardinal) {
        this.entityData.set(CARDINAL, cardinal);
    }

    public boolean hurt(DamageSource damageSource, float pAmount) {
        if (this.isInvulnerableTo(damageSource)) {
            return false;
        } else {
            Entity entity = damageSource.getEntity();
            this.setOrderedToSit(false);
            if (entity != null && !(entity instanceof Player) && !(entity instanceof AbstractArrow)) {
                pAmount = (pAmount + 1.0F) / 2.0F;
            }

            return super.hurt(damageSource, pAmount);
        }
    }

    public boolean canMate(Animal entity) {
        if (entity == this) {
            return false;
        } else if (!this.isTame()) {
            return false;
        } else if (!(entity instanceof TrapjawEntity)) {
            return false;
        } else {
            TrapjawEntity trapjaw = (TrapjawEntity) entity;
            if (!trapjaw.isTame()) {
                return false;
            } else if (trapjaw.isInSittingPose()) {
                return false;
            } else {
                return this.isInLove() && trapjaw.isInLove();
            }
        }
    }

    public boolean wantsToAttack(LivingEntity pTarget, LivingEntity pOwner) {
        if (!(pTarget instanceof Creeper) && !(pTarget instanceof Ghast)) {
            if (pTarget instanceof TrapjawEntity) {
                TrapjawEntity trapjaw = (TrapjawEntity) pTarget;
                return !trapjaw.isTame() || trapjaw.getOwner() != pOwner;
            } else if (pTarget instanceof Player && pOwner instanceof Player && !((Player) pOwner).canHarmPlayer((Player) pTarget)) {
                return false;
            } else if (pTarget instanceof AbstractHorse && ((AbstractHorse) pTarget).isTamed()) {
                return false;
            } else {
                return !(pTarget instanceof TamableAnimal) || !((TamableAnimal) pTarget).isTame();
            }
        } else {
            return false;
        }
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.RAVAGER_AMBIENT;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.RAVAGER_DEATH;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.RAVAGER_HURT;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        super.playStepSound(pos, state);
        BlockState blockstate2 = this.level.getBlockState(pos.above());
        SoundType soundtype2 = blockstate2.is(Blocks.SNOW) ? blockstate2.getSoundType(level, pos, this) : state.getSoundType(level, pos, this);
        this.playSound(SoundEvents.RAVAGER_STEP, soundtype2.getVolume() * 0.15F, this.getVoicePitch());
    }

    @Override
	public float getVoicePitch() {
        return 0.5F;
    }

    public boolean canBreatheUnderwater() {
        return true;
    }

    public MobType getMobType() {
        return MobType.WATER;
    }

    /*
    public boolean checkSpawnObstruction(LevelReader worldReader) {
        return worldReader.isUnobstructed(this);
    }
     */

    public int getAmbientSoundInterval() {
        return 120;
    }

    @Override
    public double getPassengersRidingOffset() {
        return super.getPassengersRidingOffset() * (1.75 + (this.getOwner().getLookAngle().y / 100));
    }

    protected int getExperienceReward(Player playerEntity) {
        return 2 + this.level.random.nextInt(3);
    }

    public boolean isPushedByFluid() {
        return false;
    }

    @Override
    protected float getWaterSlowDown() {
        if (this.isVehicle()) {
            return 0.95F;
        } else {
            return super.getWaterSlowDown();
        }
    }

    public boolean canBeLeashed(Player entity) {
        return this.isTame() && super.canBeLeashed(entity);
    }

    @OnlyIn(Dist.CLIENT)
    public Vec3 getLeashOffset() {
        return new Vec3(0.0D, 0.6D * this.getEyeHeight(), this.getBbWidth() * 0.4F);
    }

    @Override
    public float getScale() {
        return super.getScale();
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar data) {
        data.add(new AnimationController<>(this, "trapjaw_controller", 0, this::predicate));
    }

    private <E extends GeoEntity> PlayState predicate(AnimationState<E> event) {
        if (this.getAttacking()) {
            event.getController().setAnimation(RawAnimation.begin().thenPlay("animation.trapjaw.attack"));
            return PlayState.CONTINUE;
        }
        if (this.isInSittingPose()) {
            event.getController().setAnimation(RawAnimation.begin().thenPlay("animation.trapjaw.sit"));
            return PlayState.CONTINUE;
        }
        if (this.isInWater()) {
            event.getController().setAnimation(RawAnimation.begin().thenPlay("animation.trapjaw.swim"));
            return PlayState.CONTINUE;
        }
        if ((event.isMoving() && this.getTarget() != null) || (!this.isInWater() && this.isVehicle() && event.isMoving())) {
            event.getController().setAnimation(RawAnimation.begin().thenPlay("animation.trapjaw.run"));
            return PlayState.CONTINUE;
        }
        if (event.isMoving() && this.getTarget() == null) {
            event.getController().setAnimation(RawAnimation.begin().thenPlay("animation.trapjaw.walk"));
            return PlayState.CONTINUE;
        }
        event.getController().setAnimation(RawAnimation.begin().thenPlay("animation.trapjaw.idle"));
        return PlayState.CONTINUE;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.factory;
    }

    @Override
    protected boolean canRide(Entity entity) {
        return super.canRide(entity);
    }

    public boolean canBeControlledByRider() {
        return this.getControllingPassenger() == this.getOwner();
    }

    @Nullable
    public LivingEntity getControllingPassenger() {
        return this.getPassengers().isEmpty() ? null : (LivingEntity) this.getPassengers().get(0);
    }

    @Nullable
    private Vec3 getDismountLocationInDirection(Vec3 vector3d1, LivingEntity entity) {
        double d0 = this.getX() + vector3d1.x;
        double d1 = this.getBoundingBox().minY;
        double d2 = this.getZ() + vector3d1.z;
        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();

        for(Pose pose : entity.getDismountPoses()) {
            mutable.set(d0, d1, d2);
            double d3 = this.getBoundingBox().maxY + 0.75D;

            while(true) {
                double d4 = this.level.getBlockFloorHeight(mutable);
                if ((double)mutable.getY() + d4 > d3) {
                    break;
                }

                if (DismountHelper.isBlockFloorValid(d4)) {
                    AABB axisalignedbb = entity.getLocalBoundsForPose(pose);
                    Vec3 vector3d = new Vec3(d0, (double)mutable.getY() + d4, d2);
                    if (DismountHelper.canDismountTo(this.level, entity, axisalignedbb.move(vector3d))) {
                        entity.setPose(pose);
                        return vector3d;
                    }
                }

                mutable.move(Direction.UP);
                if (!((double)mutable.getY() < d3)) {
                    break;
                }
            }
        }

        return null;
    }

    public Vec3 getDismountLocationForPassenger(LivingEntity pLivingEntity) {
        Vec3 vector3d = getCollisionHorizontalEscapeVector(this.getBbWidth(), pLivingEntity.getBbWidth(), this.yRot + (pLivingEntity.getMainArm() == HumanoidArm.RIGHT ? 90.0F : -90.0F));
        Vec3 vector3d1 = this.getDismountLocationInDirection(vector3d, pLivingEntity);
        if (vector3d1 != null) {
            return vector3d1;
        } else {
            Vec3 vector3d2 = getCollisionHorizontalEscapeVector(this.getBbWidth(), pLivingEntity.getBbWidth(), this.yRot + (pLivingEntity.getMainArm() == HumanoidArm.LEFT ? 90.0F : -90.0F));
            Vec3 vector3d3 = this.getDismountLocationInDirection(vector3d2, pLivingEntity);
            return vector3d3 != null ? vector3d3 : this.position();
        }
    }

    @Override
    public void travel(Vec3 dir) {
        Entity entity = this.getControllingPassenger();
        if (this.isVehicle()) {
            assert entity != null;
            this.yRot = entity.yRot;
            this.yRotO = this.yRot;
            this.xRot = entity.xRot * 0.5F;
            this.setRot(this.yRot, this.xRot);
            this.yBodyRot = entity.yRot;
            this.yHeadRot = entity.yRot;
            this.setMaxUpStep(1.0F);
            if (entity instanceof LivingEntity) {
                this.setSpeed((float) this.getAttributeValue(Attributes.MOVEMENT_SPEED));
                float forward = ((LivingEntity) entity).zza;
                float strafe = ((LivingEntity) entity).xxa;

                double d3 = this.getOwner().getLookAngle().y;
                Vec3 vector3d1 = this.getDeltaMovement();
                double d4 = d3 < -0.2D ? 0.085D : 0.06D;
                if (shouldVerticalMove()) {
                    this.setDeltaMovement(vector3d1.add(0.0D, (d3 - vector3d1.y) * d4, 0.0D));
                }
                super.travel(new Vec3(strafe, 0, forward));
            }
            this.calculateEntityAnimation(false);
            return;
        }
        this.setMaxUpStep(0.5F);
        super.travel(dir);
    }
    
    @Override
    protected float getFlyingSpeed() {
    	return this.isVehicle() && this.getControllingPassenger() != null ? this.getSpeed() * 0.15F : 0.02F;
    }

    @Override
    public boolean canBeRiddenUnderFluidType(FluidType type, Entity rider) {
    	return true;
    }

    @Override
    public boolean boost() {
        return false;
    }

    @Override
    public float getRiddenSpeed(Player pPlayer) {
        return 1.0F;
    }

    public boolean shouldVerticalMove() {
        return this.isInWater();
    }
}