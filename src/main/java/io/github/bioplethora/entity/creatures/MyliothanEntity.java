package io.github.bioplethora.entity.creatures;

import javax.annotation.Nullable;

import io.github.bioplethora.config.BPConfig;
import io.github.bioplethora.entity.IBioClassification;
import io.github.bioplethora.entity.ai.goals.MyliothanChargeAttackGoal;
import io.github.bioplethora.entity.ai.goals.MyliothanShakeGoal;
import io.github.bioplethora.entity.others.part.BPPartEntity;
import io.github.bioplethora.enums.BPEntityClasses;
import io.github.bioplethora.registry.BPSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MoveTowardsTargetGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.world.entity.ai.goal.TryFindWaterGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.entity.PartEntity;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class MyliothanEntity extends WaterAnimal implements GeoEntity, IBioClassification {

    public final BPPartEntity[] subEntities;
    public final BPPartEntity head;
    public final BPPartEntity tail;
    public final BPPartEntity leftWing;
    public final BPPartEntity rightWing;
    public final BPPartEntity leftWingTip;
    public final BPPartEntity rightWingTip;

    public Vec3 moveTargetPoint = Vec3.ZERO;
    private final double[][] positions = new double[64][3];
    private int posPointer = -1;
    private float yRotA;
    public boolean inWall;

    private static final EntityDataAccessor<Boolean> SHAKING = SynchedEntityData.defineId(MyliothanEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> CHARGING = SynchedEntityData.defineId(MyliothanEntity.class, EntityDataSerializers.BOOLEAN);
    private final AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);

    public MyliothanEntity(EntityType<? extends WaterAnimal> type, Level worldIn) {
        super(type, worldIn);
        this.moveControl = new MyliothanMoveController(this);
        this.lookControl = new SmoothSwimmingLookControl(this, 10);
        this.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
        this.noCulling = true;

        head = new BPPartEntity<>(this, "head", 9.5f, 3.2f);
        tail = new BPPartEntity<>(this, "tail", 9f, 3.0f);
        leftWing = new BPPartEntity<>(this, "wing", 12.5f, 2.5f);
        rightWing = new BPPartEntity<>(this, "wing", 12.5f, 2.5f);
        leftWingTip = new BPPartEntity<>(this, "wing", 9.5f, 2.3f);
        rightWingTip = new BPPartEntity<>(this, "wing", 9.5f, 2.3f);
        subEntities = new BPPartEntity[]{head, tail, leftWing, rightWing, leftWingTip, rightWingTip};

    }

    @Nullable
    @Override
    public PartEntity<?>[] getParts() {
        return subEntities;
    }

    @Override
    public boolean isMultipartEntity() {
        return true;
    }

    @Override
    public BPEntityClasses getBioplethoraClass() {
        return BPEntityClasses.ELDERIA;
    }

    public static AttributeSupplier.Builder setCustomAttributes() {
        return Mob.createLivingAttributes()
                .add(Attributes.ARMOR, (BPConfig.IN_HELLMODE ? 16 : 14) * BPConfig.COMMON.mobArmorMultiplier.get())
                .add(Attributes.ATTACK_SPEED, 10)
                .add(Attributes.ATTACK_KNOCKBACK, 10D)
                .add(Attributes.ATTACK_DAMAGE, (BPConfig.IN_HELLMODE ? 32 : 27) * BPConfig.COMMON.mobMeeleeDamageMultiplier.get())
                .add(Attributes.MAX_HEALTH, (BPConfig.IN_HELLMODE ? 355 : 305) * BPConfig.COMMON.mobHealthMultiplier.get())
                .add(Attributes.MOVEMENT_SPEED, 1.2 * BPConfig.COMMON.mobMovementSpeedMultiplier.get())
                .add(Attributes.KNOCKBACK_RESISTANCE, 10D)
                .add(Attributes.FOLLOW_RANGE, 64D);
    }

    @Override
    public void tick() {
        super.tick();

        if (posPointer < 0) {
            for (int i = 0; i < positions.length; ++i) {
                positions[i][0] = getViewYRot(1.0F);
                positions[i][1] = getY();
            }
        }

        if (++posPointer == positions.length) {
            posPointer = 0;
        }

        // Target Point Deltas
        double tpdx = moveTargetPoint.x - getX(), tpdz = moveTargetPoint.z - getZ();

        if (Math.abs(tpdx) > (double) 1.0E-5F || Math.abs(tpdz) > (double) 1.0E-5F) {
            float yRotAModifier = Mth.clamp(
                    Mth.wrapDegrees(180.0F - (float) Mth.atan2(tpdx, tpdz) *
                            (180F / (float) Math.PI) - getViewYRot(1.0F)),
                    -10.0F, 10.0F);

            yRotA *= 0.8F;
            yRotA += yRotAModifier * getTurnSpeed();
        }

        float posLatency = (float) (getLatencyPos(5, 1.0F)[1]
                - getLatencyPos(10, 1.0F)[1]) * 10.0F * ((float) Math.PI / 180F);
        float cosLatency = Mth.cos(posLatency);
        float sinLatency = Mth.sin(posLatency);
        float sinRotMod = Mth.sin(getViewYRot(1.0F) * ((float) Math.PI / 180F) - yRotA * 0.01F);
        float cosRotMod = Mth.cos(getViewYRot(1.0F) * ((float) Math.PI / 180F) - yRotA * 0.01F);
        float yHeadOffset = getHeadYOffset();

        float yRadians = getViewYRot(1.0F) * ((float) Math.PI / 180F);
        float wingXOffset = Mth.cos(yRadians);
        float wingZOffset = Mth.sin(yRadians);

        tickPart(head, (-sinRotMod * (8f * getScale() * 0.8f) * cosLatency),
                (yHeadOffset + sinLatency * 6.5f),
                (cosRotMod * (8f * getScale() * 0.8f) * cosLatency));

        tickPart(tail, (sinRotMod * (10f * getScale() * 0.8f) * cosLatency),
                (yHeadOffset + sinLatency * 6.5f),
                (-cosRotMod * (10f * getScale() * 0.8f) * cosLatency));

        tickPart(leftWing, wingXOffset * 6f, 0.5f, wingZOffset * 6f);
        tickPart(rightWing, wingXOffset * -6f, 0.5f, wingZOffset * -6f);
        tickPart(leftWingTip, wingXOffset * 16f, 0.0f, wingZOffset * 16f);
        tickPart(rightWingTip, wingXOffset * -16f, 0.0f, wingZOffset * -16f);

        Vec3[] subVec = new Vec3[subEntities.length];

        for (int i = 0; i < subEntities.length; ++i) {
            subVec[i] = new Vec3(subEntities[i].getX(), subEntities[i].getY(), subEntities[i].getZ());
        }

        for (int i = 0; i < subEntities.length; ++i) {
            subEntities[i].xo = subVec[i].x;
            subEntities[i].yo = subVec[i].y;
            subEntities[i].zo = subVec[i].z;
            subEntities[i].xOld = subVec[i].x;
            subEntities[i].yOld = subVec[i].y;
            subEntities[i].zOld = subVec[i].z;
        }
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (this.inWall) {
            this.move(MoverType.SELF, this.getDeltaMovement().scale(0.8F));
        } else {
            this.move(MoverType.SELF, this.getDeltaMovement());
        }

        if (!this.level.isClientSide) {
            this.inWall = this.checkWalls(this.getBoundingBox()) |
                    this.checkWalls(this.head.getBoundingBox()) | this.checkWalls(this.leftWing.getBoundingBox()) |
                            this.checkWalls(this.rightWing.getBoundingBox()) | this.checkWalls(this.rightWingTip.getBoundingBox()) |
                                    this.checkWalls(this.leftWingTip.getBoundingBox()) | this.checkWalls(this.tail.getBoundingBox());
        }

        if (this.getTarget() != null) {
            if (this.getTarget().isInWater()) {
                this.getTarget().setDeltaMovement(0, 0.5, 0);
            }
        }
    }

    @Override
    public boolean doHurtTarget(Entity pEntity) {
        if (pEntity instanceof Player) {
            Player entity = (Player) pEntity;
            if (entity.getUseItem().canPerformAction(net.minecraftforge.common.ToolActions.SHIELD_BLOCK)) {
                entity.getCooldowns().addCooldown(entity.getUseItem().getItem(), 300);
            }
        }
        return super.doHurtTarget(pEntity);
    }

    @Override
    protected void handleAirSupply(int pAirSupply) {
        this.setAirSupply(300);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new TryFindWaterGoal(this));
        this.goalSelector.addGoal(1, new MyliothanChargeAttackGoal(this));
        this.goalSelector.addGoal(2, new MyliothanShakeGoal(this));
        this.goalSelector.addGoal(2, new MoveTowardsTargetGoal(this, 1.6, 8));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 24.0F));
        this.goalSelector.addGoal(4, new RandomSwimmingGoal(this, 1.0D, 10));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    private boolean checkWalls(AABB pArea) {
        int i = Mth.floor(pArea.minX);
        int j = Mth.floor(pArea.minY);
        int k = Mth.floor(pArea.minZ);
        int l = Mth.floor(pArea.maxX);
        int i1 = Mth.floor(pArea.maxY);
        int j1 = Mth.floor(pArea.maxZ);
        boolean flag = false;
        boolean flag1 = false;

        for(int k1 = i; k1 <= l; ++k1) {
            for(int l1 = j; l1 <= i1; ++l1) {
                for(int i2 = k; i2 <= j1; ++i2) {
                    BlockPos blockpos = new BlockPos(k1, l1, i2);
                    BlockState blockstate = this.level.getBlockState(blockpos);
                    if (!blockstate.isAir() && !blockstate.is(BlockTags.FIRE)) {
                        if (net.minecraftforge.common.ForgeHooks.canEntityDestroy(this.level, blockpos, this) && !blockstate.is(BlockTags.DRAGON_IMMUNE)) {
                            flag1 = this.level.removeBlock(blockpos, false) || flag1;
                        } else {
                            flag = true;
                        }
                    }
                }
            }
        }

        if (flag1) {
            BlockPos blockpos1 = new BlockPos(i + this.random.nextInt(l - i + 1), j + this.random.nextInt(i1 - j + 1), k + this.random.nextInt(j1 - k + 1));
            this.level.levelEvent(2008, blockpos1, 0);
        }

        return flag;
    }

    // TODO: 26/01/2022 - Better Myliothan charging animation.
    private <E extends GeoEntity> PlayState predicate(AnimationState<E> event) {

        if (this.isCharging()) {
            event.getController().setAnimation(RawAnimation.begin().thenPlay("animation.myliothan.charge"));
            return PlayState.CONTINUE;
        }

        event.getController().setAnimation(RawAnimation.begin().thenPlay("animation.myliothan.idle"));
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar data) {
        data.add(new AnimationController<>(this, "myliothan_controller", 5, this::predicate));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.factory;
    }

    public static boolean checkMyliothanSpawnRules(EntityType<MyliothanEntity> myliothan, ServerLevelAccessor world, MobSpawnType pSpawnType, BlockPos pos, RandomSource pRandom) {
        if (pos.getY() > 45 && pos.getY() < world.getSeaLevel()) {
            Holder<Biome> optional = world.getBiome(pos);
            return optional.is(Biomes.OCEAN) && world.getFluidState(pos).is(FluidTags.WATER);
        } else {
            return false;
        }
    }

    @Override
    public void checkDespawn() {
        if (this.level.getDifficulty() == Difficulty.PEACEFUL && this.shouldDespawnInPeaceful()) {
            this.discard();
        }
    }

    protected PathNavigation createNavigation(Level world) {
        return new WaterBoundPathNavigation(this, world);
    }

    public void travel(Vec3 vector) {
        if (this.isEffectiveAi() && this.isInWater()) {
            this.moveRelative(this.getSpeed(), vector);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.9D));
            if (this.getTarget() == null) {
                this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.005D, 0.0D));
            }
        } else {
            super.travel(vector);
        }
    }

    @Override
    public SoundEvent getAmbientSound() {
        return BPSoundEvents.MYLIOTHAN_IDLE.get();
    }

    @Override
    public SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.GENERIC_HURT;
    }

    @Override
    public SoundEvent getDeathSound() {
        return SoundEvents.GENERIC_DEATH;
    }

    @Override
    public boolean canBeLeashed(Player player) {
        return false;
    }

    static class MyliothanMoveController extends MoveControl {
        private final MyliothanEntity myliothan;
        private float speed = 0.1F;

        public MyliothanMoveController(MyliothanEntity entity) {
            super(entity);
            this.myliothan = entity;
        }

        public void tick() {
            if (this.myliothan.isInWater()) {
                this.myliothan.setDeltaMovement(this.myliothan.getDeltaMovement().add(0.0D, 0.005D, 0.0D));
            }

            if (this.myliothan.isCharging()) {
                float f = (float) (this.myliothan.moveTargetPoint.x - this.myliothan.getX());
                float f1 = (float) (this.myliothan.moveTargetPoint.y - this.myliothan.getY());
                float f2 = (float) (this.myliothan.moveTargetPoint.z - this.myliothan.getZ());
                double d0 = Mth.sqrt(f * f + f2 * f2);
                double d1 = 1.0D - (double) Mth.abs(f1 * 0.7F) / d0;
                f = (float) ((double) f * d1);
                f2 = (float) ((double) f2 * d1);
                d0 = Mth.sqrt(f * f + f2 * f2);
                double d2 = Mth.sqrt(f * f + f2 * f2 + f1 * f1);
                float f3 = this.myliothan.getYRot();
                float f4 = (float) Mth.atan2(f2, f);
                float f5 = Mth.wrapDegrees(this.myliothan.getYRot() + 90.0F);
                float f6 = Mth.wrapDegrees(f4 * (180F / (float) Math.PI));
                this.myliothan.yRot = Mth.approachDegrees(f5, f6, 4.0F) - 90.0F;
                this.myliothan.yBodyRot = this.myliothan.getYRot();
                if (Mth.degreesDifferenceAbs(f3, this.myliothan.getYRot()) < 3.0F) {
                    this.speed = Mth.approach(this.speed, 1.8F, 0.005F * (1.8F / this.speed));
                } else {
                    this.speed = Mth.approach(this.speed, 0.2F, 0.025F);
                }

                float f7 = (float) (-(Mth.atan2(-f1, d0) * (double) (180F / (float) Math.PI)));
                this.myliothan.xRot = f7;
                float f8 = this.myliothan.getYRot() + 90.0F;
                double d3 = (double) (this.speed * Mth.cos(f8 * ((float) Math.PI / 180F))) * Math.abs((double) f / d2);
                double d4 = (double) (this.speed * Mth.sin(f8 * ((float) Math.PI / 180F))) * Math.abs((double) f2 / d2);
                double d5 = (double) (this.speed * Mth.sin(f7 * ((float) Math.PI / 180F))) * Math.abs((double) f1 / d2);
                Vec3 vector3d = this.myliothan.getDeltaMovement();
                this.myliothan.setDeltaMovement(vector3d.add((new Vec3(d3, d5, d4)).subtract(vector3d).scale(0.2D)));
            }

            if (this.operation == MoveControl.Operation.MOVE_TO && !this.myliothan.getNavigation().isDone()) {
                double d0 = this.wantedX - this.myliothan.getX();
                double d1 = this.wantedY - this.myliothan.getY();
                double d2 = this.wantedZ - this.myliothan.getZ();
                double d3 = d0 * d0 + d1 * d1 + d2 * d2;
                if (d3 < (double)2.5000003E-7F) {
                    this.mob.setZza(0.0F);
                } else {
                    float f = (float)(Mth.atan2(d2, d0) * (double)(180F / (float)Math.PI)) - 90.0F;
                    this.myliothan.yRot = this.rotlerp(this.myliothan.getYRot(), f, 10.0F);
                    this.myliothan.yBodyRot = this.myliothan.getYRot();
                    this.myliothan.yHeadRot = this.myliothan.getYRot();
                    float f1 = (float)(this.speedModifier * this.myliothan.getAttributeValue(Attributes.MOVEMENT_SPEED));
                    if (this.myliothan.isInWater()) {
                        this.myliothan.setSpeed(f1 * 0.02F);
                        float f2 = -((float)(Mth.atan2(d1, Math.sqrt(d0 * d0 + d2 * d2)) * (double)(180F / (float)Math.PI)));
                        f2 = Mth.clamp(Mth.wrapDegrees(f2), -85.0F, 85.0F);
                        this.myliothan.xRot = this.rotlerp(this.myliothan.getXRot(), f2, 5.0F);
                        float f3 = Mth.cos(this.myliothan.getXRot() * ((float)Math.PI / 180F));
                        float f4 = Mth.sin(this.myliothan.getXRot() * ((float)Math.PI / 180F));
                        this.myliothan.zza = f3 * f1;
                        this.myliothan.yya = -f4 * f1;
                    } else {
                        this.myliothan.setSpeed(f1 * 0.1F);
                    }
                }
            } else {
                this.myliothan.setSpeed(0.0F);
                this.myliothan.setXxa(0.0F);
                this.myliothan.setYya(0.0F);
                this.myliothan.setZza(0.0F);
            }
        }
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(SHAKING, false);
        this.entityData.define(CHARGING, false);
    }

    public boolean isShaking() {
        return this.entityData.get(SHAKING);
    }

    public void setShaking(boolean shaking) {
        this.entityData.set(SHAKING, shaking);
    }

    public boolean isCharging() {
        return this.entityData.get(CHARGING);
    }

    public void setCharging(boolean charging) {
        this.entityData.set(CHARGING, charging);
    }

    private void tickPart(BPPartEntity pPart, double offsetX, double offsetY, double offsetZ) {
        pPart.setPos(getX() + offsetX, getY() + offsetY, getZ() + offsetZ);
    }

    private float getHeadYOffset() {
        double[] latencyPos = getLatencyPos(5, 1.0F);
        double[] latencyPos1 = getLatencyPos(0, 1.0F);
        return (float) (latencyPos[1] - latencyPos1[1]);
    }

    public double[] getLatencyPos(int pointer, float multiplier) {
        if (isDeadOrDying()) {
            multiplier = 0.0F;
        }

        multiplier = 1.0F - multiplier;
        int item = posPointer - pointer & 63;
        int previousItem = posPointer - pointer - 1 & 63;
        double[] latencyPos = new double[3];
        double yawOffset = positions[item][0];
        double yOffsetMinusYawOffset = Mth.wrapDegrees(positions[previousItem][0] - yawOffset);
        latencyPos[0] = yawOffset + yOffsetMinusYawOffset * (double) multiplier;
        yawOffset = positions[item][1];
        yOffsetMinusYawOffset = positions[previousItem][1] - yawOffset;
        latencyPos[1] = yawOffset + yOffsetMinusYawOffset * (double) multiplier;
        latencyPos[2] = Mth.lerp(multiplier, positions[item][2], positions[previousItem][2]);
        return latencyPos;
    }

    public float getTurnSpeed() {
        float deltaDistance = (float) (getDeltaMovement().length() + 1.0F);
        float min = Math.min(deltaDistance, 40.0F);
        return 0.7F / min / deltaDistance;
    }
}
