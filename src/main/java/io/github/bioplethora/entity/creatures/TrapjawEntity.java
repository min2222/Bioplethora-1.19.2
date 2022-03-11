package io.github.bioplethora.entity.creatures;

import io.github.bioplethora.BioplethoraConfig;
import io.github.bioplethora.entity.*;
import io.github.bioplethora.entity.ai.WaterAndLandFollowOwnerGoal;
import io.github.bioplethora.entity.ai.tameable.BPAnimalMeleeGoal;
import io.github.bioplethora.entity.ai.tameable.BPAnimalMoveToTargetGoal;
import io.github.bioplethora.enums.BPEntityClasses;
import io.github.bioplethora.registry.BioplethoraEntities;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.monster.GhastEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.passive.horse.AbstractHorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SaddleItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nullable;
import java.util.UUID;
import java.util.function.Predicate;

public class TrapjawEntity extends WaterAndLandAnimalEntity implements IAnimatable, IBioClassification, IRideable, IVerticalMount, ISaddleable {

    private static final DataParameter<Boolean> HAS_SADDLE = EntityDataManager.defineId(TrapjawEntity.class, DataSerializers.BOOLEAN);
    private final AnimationFactory factory = new AnimationFactory(this);
    public static final Predicate<LivingEntity> PREY_SELECTOR = (entity) -> entity instanceof PlayerEntity || entity instanceof AnimalEntity;

    public TrapjawEntity(EntityType<? extends BPAnimalEntity> type, World worldIn) {
        super(type, worldIn);
        this.setTame(false);
        this.noCulling = true;
    }

    @Override
    public BPEntityClasses getBioplethoraClass() {
        return BPEntityClasses.DANGERUM;
    }

    public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
        return MobEntity.createLivingAttributes()
                .add(Attributes.ARMOR, 4 * BioplethoraConfig.COMMON.mobArmorMultiplier.get())
                .add(Attributes.ATTACK_SPEED, 1.5)
                .add(Attributes.ATTACK_DAMAGE, 8 * BioplethoraConfig.COMMON.mobMeeleeDamageMultiplier.get())
                .add(Attributes.ATTACK_KNOCKBACK, 0.5 * BioplethoraConfig.COMMON.mobMeeleeDamageMultiplier.get())
                .add(Attributes.MAX_HEALTH, 80 * BioplethoraConfig.COMMON.mobHealthMultiplier.get())
                .add(Attributes.MOVEMENT_SPEED, 0.25D * BioplethoraConfig.COMMON.mobMovementSpeedMultiplier.get())
                .add(Attributes.FOLLOW_RANGE, 32D);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(2, new SitGoal(this));
        this.goalSelector.addGoal(4, new BPAnimalMoveToTargetGoal(this, 1.6, 8) {
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
        this.goalSelector.addGoal(4, new BPAnimalMeleeGoal(this, 30, 0.5, 0.6));
        this.goalSelector.addGoal(5, new WaterAndLandFollowOwnerGoal(this, 1.0D, 10.0F, 2.0F, false));
        this.goalSelector.addGoal(6, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(7, new RandomWalkingGoal(this, 1.2, 8));

        this.goalSelector.addGoal(10, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(11, new LookRandomlyGoal(this));
        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(2, (new HurtByTargetGoal(this)).setAlertOthers());
        this.targetSelector.addGoal(1, new NonTamedTargetGoal<>(this, LivingEntity.class, false, PREY_SELECTOR));
    }

    // TODO: 11/03/2022 Pull the target to the mob 
    @Override
    public boolean doHurtTarget(Entity entity) {
        return super.doHurtTarget(entity);
    }

    @Nullable
    @Override
    public AgeableEntity getBreedOffspring(ServerWorld serverWorld, AgeableEntity entity) {
        TrapjawEntity trapjaw = BioplethoraEntities.TRAPJAW.get().create(serverWorld);
        UUID uuid = this.getOwnerUUID();
        if (uuid != null) {
            trapjaw.setOwnerUUID(uuid);
            trapjaw.setTame(true);
        }

        return trapjaw;
    }

    protected float getStandingEyeHeight(Pose pose, EntitySize entitySize) {
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

    public ActionResultType mobInteract(PlayerEntity entity, Hand resultType) {
        ItemStack itemstack = entity.getItemInHand(resultType);
        Item item = itemstack.getItem();
        if (this.level.isClientSide) {
            boolean flag = this.isOwnedBy(entity) || this.isTame() || this.isFood(itemstack) && !this.isTame();
            return flag ? ActionResultType.CONSUME : ActionResultType.PASS;
        } else {
            if (entity.isCrouching()) {
                if (this.isTame()) {
                    if (this.isFood(itemstack) && this.getHealth() < this.getMaxHealth()) {
                        if (!entity.abilities.instabuild) {
                            itemstack.shrink(1);
                        }

                        this.heal((float) item.getFoodProperties().getNutrition());
                        return ActionResultType.SUCCESS;

                    } else if (item instanceof SaddleItem && !this.isBaby()) {
                        this.setSaddled(true);
                        itemstack.shrink(1);
                        return ActionResultType.SUCCESS;
                    }

                    ActionResultType actionresulttype = super.mobInteract(entity, resultType);
                    if ((!actionresulttype.consumesAction() || this.isBaby()) && this.isOwnedBy(entity)) {
                        this.setOrderedToSit(!this.isOrderedToSit());
                        this.jumping = false;
                        this.navigation.stop();
                        this.setTarget(null);
                        return ActionResultType.SUCCESS;
                    }

                    return actionresulttype;

                } else if (this.isFood(itemstack)) {
                    if (!entity.abilities.instabuild) {
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

                    return ActionResultType.SUCCESS;
                }

            } else if (!entity.isCrouching()) {
                if (this.isSaddled()) {
                    entity.startRiding(this);
                }
            }
            return super.mobInteract(entity, resultType);
        }
    }

    public void addAdditionalSaveData(CompoundNBT compoundNBT) {
        super.addAdditionalSaveData(compoundNBT);
        compoundNBT.putBoolean("isSaddled", this.isSaddled());
    }

    public void readAdditionalSaveData(CompoundNBT compoundNBT) {
        super.readAdditionalSaveData(compoundNBT);
        this.setSaddled(compoundNBT.getBoolean("isSaddled"));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(HAS_SADDLE, false);
    }

    public boolean isSaddled() {
        return this.entityData.get(HAS_SADDLE);
    }

    public void setSaddled(boolean saddled) {
        this.entityData.set(HAS_SADDLE, saddled);
    }

    public boolean hurt(DamageSource damageSource, float pAmount) {
        if (this.isInvulnerableTo(damageSource)) {
            return false;
        } else {
            Entity entity = damageSource.getEntity();
            this.setOrderedToSit(false);
            if (entity != null && !(entity instanceof PlayerEntity) && !(entity instanceof AbstractArrowEntity)) {
                pAmount = (pAmount + 1.0F) / 2.0F;
            }

            return super.hurt(damageSource, pAmount);
        }
    }

    public boolean canMate(AnimalEntity entity) {
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
        if (!(pTarget instanceof CreeperEntity) && !(pTarget instanceof GhastEntity)) {
            if (pTarget instanceof TrapjawEntity) {
                TrapjawEntity trapjaw = (TrapjawEntity) pTarget;
                return !trapjaw.isTame() || trapjaw.getOwner() != pOwner;
            } else if (pTarget instanceof PlayerEntity && pOwner instanceof PlayerEntity && !((PlayerEntity) pOwner).canHarmPlayer((PlayerEntity) pTarget)) {
                return false;
            } else if (pTarget instanceof AbstractHorseEntity && ((AbstractHorseEntity) pTarget).isTamed()) {
                return false;
            } else {
                return !(pTarget instanceof TameableEntity) || !((TameableEntity) pTarget).isTame();
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
        this.playSound(SoundEvents.RAVAGER_STEP, this.getSoundVolume(), this.getVoicePitch());
    }

    @Override
    protected float getVoicePitch() {
        return 0.5F;
    }

    public boolean canBreatheUnderwater() {
        return true;
    }

    public CreatureAttribute getMobType() {
        return CreatureAttribute.WATER;
    }

    public boolean checkSpawnObstruction(IWorldReader worldReader) {
        return worldReader.isUnobstructed(this);
    }

    public int getAmbientSoundInterval() {
        return 120;
    }

    @Override
    public double getPassengersRidingOffset() {
        return super.getPassengersRidingOffset() * 1.75;
    }

    protected int getExperienceReward(PlayerEntity playerEntity) {
        return 1 + this.level.random.nextInt(3);
    }

    public boolean isPushedByFluid() {
        return false;
    }

    @Override
    protected float getWaterSlowDown() {
        return 1.0F;
    }

    public boolean canBeLeashed(PlayerEntity entity) {
        return this.isTame() && super.canBeLeashed(entity);
    }

    @OnlyIn(Dist.CLIENT)
    public Vector3d getLeashOffset() {
        return new Vector3d(0.0D, this.getEyeHeight(), this.getBbWidth() * 0.4F);
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "trapjaw_controller", 0, this::predicate));
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (this.getAttacking()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.trapjaw.attack", true));
            return PlayState.CONTINUE;
        }
        if (this.isInSittingPose()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.trapjaw.sit", true));
            return PlayState.CONTINUE;
        }
        if (this.isInWater()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.trapjaw.swim", true));
            return PlayState.CONTINUE;
        }
        if ((event.isMoving() && this.getTarget() != null) || (!this.isInWater() && this.isSaddled())) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.trapjaw.run", true));
            return PlayState.CONTINUE;
        }
        if ((event.isMoving() && this.getTarget() == null)) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.trapjaw.walk", true));
            return PlayState.CONTINUE;
        }
        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.trapjaw.idle", true));
        return PlayState.CONTINUE;
    }

    @Override
    public AnimationFactory getFactory() {
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
    public Entity getControllingPassenger() {
        return this.getPassengers().isEmpty() ? null : this.getPassengers().get(0);
    }

    @Nullable
    private Vector3d getDismountLocationInDirection(Vector3d vector3d1, LivingEntity entity) {
        double d0 = this.getX() + vector3d1.x;
        double d1 = this.getBoundingBox().minY;
        double d2 = this.getZ() + vector3d1.z;
        BlockPos.Mutable mutable = new BlockPos.Mutable();

        for(Pose pose : entity.getDismountPoses()) {
            mutable.set(d0, d1, d2);
            double d3 = this.getBoundingBox().maxY + 0.75D;

            while(true) {
                double d4 = this.level.getBlockFloorHeight(mutable);
                if ((double)mutable.getY() + d4 > d3) {
                    break;
                }

                if (TransportationHelper.isBlockFloorValid(d4)) {
                    AxisAlignedBB axisalignedbb = entity.getLocalBoundsForPose(pose);
                    Vector3d vector3d = new Vector3d(d0, (double)mutable.getY() + d4, d2);
                    if (TransportationHelper.canDismountTo(this.level, entity, axisalignedbb.move(vector3d))) {
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

    public Vector3d getDismountLocationForPassenger(LivingEntity pLivingEntity) {
        Vector3d vector3d = getCollisionHorizontalEscapeVector(this.getBbWidth(), pLivingEntity.getBbWidth(), this.yRot + (pLivingEntity.getMainArm() == HandSide.RIGHT ? 90.0F : -90.0F));
        Vector3d vector3d1 = this.getDismountLocationInDirection(vector3d, pLivingEntity);
        if (vector3d1 != null) {
            return vector3d1;
        } else {
            Vector3d vector3d2 = getCollisionHorizontalEscapeVector(this.getBbWidth(), pLivingEntity.getBbWidth(), this.yRot + (pLivingEntity.getMainArm() == HandSide.LEFT ? 90.0F : -90.0F));
            Vector3d vector3d3 = this.getDismountLocationInDirection(vector3d2, pLivingEntity);
            return vector3d3 != null ? vector3d3 : this.position();
        }
    }

    @Override
    public void travel(Vector3d dir) {
        Entity entity = this.getControllingPassenger();
        if (this.isVehicle()) {
            assert entity != null;
            this.yRot = entity.yRot;
            this.yRotO = this.yRot;
            this.xRot = entity.xRot * 0.5F;
            this.setRot(this.yRot, this.xRot);
            this.flyingSpeed = this.getSpeed() * 0.15F;
            this.yBodyRot = entity.yRot;
            this.yHeadRot = entity.yRot;
            this.maxUpStep = 1.0F;
            if (entity instanceof LivingEntity) {
                this.setSpeed((float) this.getAttributeValue(Attributes.MOVEMENT_SPEED));
                float forward = ((LivingEntity) entity).zza;
                float strafe = ((LivingEntity) entity).xxa;
                super.travel(new Vector3d(strafe, 0, forward));
            }
            this.animationSpeedOld = this.animationSpeed;
            double d1 = this.getX() - this.xo;
            double d0 = this.getZ() - this.zo;
            float f1 = MathHelper.sqrt(d1 * d1 + d0 * d0) * 4.0F;
            if (f1 > 1.0F)
                f1 = 1.0F;
            this.animationSpeed += (f1 - this.animationSpeed) * 0.4F;
            this.animationPosition += this.animationSpeed;
            return;
        }
        this.maxUpStep = 0.5F;
        this.flyingSpeed = 0.02F;
        super.travel(dir);
    }

    @Override
    public boolean rideableUnderWater() {
        return true;
    }

    @Override
    public boolean boost() {
        return false;
    }

    @Override
    public void travelWithInput(Vector3d pTravelVec) {
    }

    @Override
    public float getSteeringSpeed() {
        return 1.5F;
    }

    @Override
    public boolean shouldVerticalMove() {
        return this.isInWater();
    }
}