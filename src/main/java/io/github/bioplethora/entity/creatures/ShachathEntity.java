package io.github.bioplethora.entity.creatures;

import java.util.EnumSet;

import javax.annotation.Nullable;

import io.github.bioplethora.api.world.EffectUtils;
import io.github.bioplethora.config.BPConfig;
import io.github.bioplethora.entity.IBioClassification;
import io.github.bioplethora.entity.IMobCappedEntity;
import io.github.bioplethora.entity.SummonableMonsterEntity;
import io.github.bioplethora.entity.ai.goals.CopyTargetOwnerGoal;
import io.github.bioplethora.entity.ai.goals.ShachathAttackWaveGoal;
import io.github.bioplethora.entity.ai.goals.ShachathCloningGoal;
import io.github.bioplethora.entity.ai.goals.ShachathEntityStrikeGoal;
import io.github.bioplethora.entity.ai.goals.ShachathQuickShootingGoal;
import io.github.bioplethora.entity.others.BPEffectEntity;
import io.github.bioplethora.enums.BPEffectTypes;
import io.github.bioplethora.enums.BPEntityClasses;
import io.github.bioplethora.registry.BPAttributes;
import io.github.bioplethora.registry.BPItems;
import io.github.bioplethora.registry.BPParticles;
import io.github.bioplethora.registry.BPSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.BossEvent.BossBarColor;
import net.minecraft.world.BossEvent.BossBarOverlay;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class ShachathEntity extends SummonableMonsterEntity implements GeoEntity, IBioClassification, IMobCappedEntity {
    protected static final EntityDataAccessor<Boolean> ATTACKING2 = SynchedEntityData.defineId(ShachathEntity.class, EntityDataSerializers.BOOLEAN);
    protected static final EntityDataAccessor<Boolean> STRIKING = SynchedEntityData.defineId(ShachathEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_IS_QUICKSHOOTING = SynchedEntityData.defineId(ShachathEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_IS_CLONE = SynchedEntityData.defineId(ShachathEntity.class, EntityDataSerializers.BOOLEAN);

    private final Component cloneProgText = Component.translatable("bossbar.bioplethora.shachath.clone_progress");

    private final ServerBossEvent bossInfo = new ServerBossEvent(this.getDisplayName(), BossBarColor.RED, BossBarOverlay.PROGRESS);
    private final ServerBossEvent cloneProgress = new ServerBossEvent(cloneProgText, BossBarColor.WHITE, BossBarOverlay.PROGRESS);
    private final AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);
    public BlockPos boundOrigin;
    protected int tpParticleAmount = 30;
    protected double tpParticleRadius = 0.3;
    public int cloneChargeTime;
    public int attackPhase;
    public int tpTimer;
    public int jumpTimer;

    public ShachathEntity(EntityType<? extends Monster> type, Level world) {
        super(type, world);
        this.moveControl = new ShachathEntity.NonGroundController(this);
        this.noCulling = true;
        this.tpTimer = 0;
    }

    public static AttributeSupplier.Builder setCustomAttributes() {
        return Mob.createLivingAttributes()
                .add(Attributes.ARMOR, 15 * BPConfig.COMMON.mobArmorMultiplier.get())
                .add(Attributes.ATTACK_SPEED, 1.5)
                .add(Attributes.ATTACK_DAMAGE, 15 * BPConfig.COMMON.mobMeeleeDamageMultiplier.get())
                .add(Attributes.ATTACK_KNOCKBACK, 0.5D)
                .add(Attributes.MAX_HEALTH, 100 * BPConfig.COMMON.mobHealthMultiplier.get())
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.5)
                .add(Attributes.MOVEMENT_SPEED, 0.65 * BPConfig.COMMON.mobMovementSpeedMultiplier.get())
                .add(Attributes.FOLLOW_RANGE, 64D)
                .add(BPAttributes.TRUE_DEFENSE.get(), 1D);
    }

    @Override
    public BPEntityClasses getBioplethoraClass() {
        return BPEntityClasses.HELLSENT;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 24.0F));
        this.goalSelector.addGoal(1, new ShachathEntity.NonGroundMoveRandomGoal());
        this.goalSelector.addGoal(2, new ShachathEntity.NonGroundMoveToTargetGoal());
        this.goalSelector.addGoal(2, new ShachathAttackWaveGoal.Wave1(this, 30, 0.5, 0.6));
        this.goalSelector.addGoal(2, new ShachathAttackWaveGoal.Wave2(this, 35, 0.6, 0.7));
        this.goalSelector.addGoal(2, new ShachathEntityStrikeGoal(this));
        this.goalSelector.addGoal(3, new ShachathQuickShootingGoal(this));
        this.goalSelector.addGoal(3, new ShachathCloningGoal(this));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this, ShachathEntity.class).setAlertOthers());
        this.targetSelector.addGoal(1, new CopyTargetOwnerGoal(this));

        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AltyrusEntity.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, FrostbiteGolemEntity.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AlphemEntity.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AlphemKingEntity.class, true));
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar data) {
        data.add(new AnimationController<>(this, "shachath_controller", 0, this::predicate));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.factory;
    }

    private <E extends GeoEntity>PlayState predicate(AnimationState<E> event) {

        if (this.isQuickShooting()) {
            event.getController().setAnimation(RawAnimation.begin().thenPlay("animation.shachath.quick_shooting"));
            event.getController().transitionLength(0);
            return PlayState.CONTINUE;
        }

        if (this.getStriking()) {
            event.getController().setAnimation(RawAnimation.begin().thenPlay("animation.shachath.striking"));
            event.getController().transitionLength(0);
            return PlayState.CONTINUE;
        }

        if (this.getAttacking2()) {
            event.getController().setAnimation(RawAnimation.begin().thenPlay("animation.shachath.attacking2"));
            event.getController().transitionLength(0);
            return PlayState.CONTINUE;
        }

        if (this.getAttacking()) {
            event.getController().setAnimation(RawAnimation.begin().thenPlay("animation.shachath.attacking"));
            event.getController().transitionLength(0);
            return PlayState.CONTINUE;
        }

        if (isInWater() || level.isEmptyBlock(blockPosition().below())) {
            event.getController().setAnimation(RawAnimation.begin().thenPlay("animation.shachath.float"));
            event.getController().transitionLength(5);
            return PlayState.CONTINUE;
        }

        if (event.isMoving()) {
            event.getController().setAnimation(RawAnimation.begin().thenPlay("animation.shachath.running"));
            event.getController().transitionLength(5);
            return PlayState.CONTINUE;
        }

        event.getController().setAnimation(RawAnimation.begin().thenPlay("animation.shachath.idle"));
        event.getController().transitionLength(5);
        return PlayState.CONTINUE;
    }

    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor iServerLevel, DifficultyInstance difficultyInstance, MobSpawnType spawnReason, @Nullable SpawnGroupData iLivingEntityData, @Nullable CompoundTag compoundNBT) {
        iLivingEntityData = super.finalizeSpawn(iServerLevel, difficultyInstance, spawnReason, iLivingEntityData, compoundNBT);
        if (!this.level.isClientSide()) {
            this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(BPItems.VERMILION_BLADE.get()));
        }
        return iLivingEntityData;
    }

    public void aiStep() {
        super.aiStep();
        if (!this.isClone()) {
            this.bossInfo.setProgress(this.getHealth() / this.getMaxHealth());
            this.cloneProgress.setProgress((float) this.cloneChargeTime / 300f);
        }
        if (this.getTarget() != null) {
            int areaint = 3;
            double x = this.getX(), y = this.getY(), z = this.getZ();
            AABB area = new AABB(x - (areaint / 2d), y, z - (areaint / 2d), x + (areaint / 2d), y + (areaint / 2d), z + (areaint / 2d));
            Level world = this.level;
            for (LivingEntity entityIterator : world.getEntitiesOfClass(LivingEntity.class, area)) {
                if (entityIterator == this.getTarget()) {
                    //entityIterator.hurt(BPDamageSources.helioSlashed(this, this), this.isClone() ? 1F : 1.5F);
                    ++this.tpTimer;
                    if (this.tpTimer == 40) {
                        this.teleportRandomly();
                        this.tpTimer = 0;
                    }
                }
                if ((entityIterator instanceof Mob)) {
                    if (((Mob) entityIterator).getTarget() == this) {
                        //entityIterator.hurt(BPDamageSources.helioSlashed(this, this), this.isClone() ? 1F : 1.5F);
                    }
                }
            }
        }
    }

    @Override
    public boolean isNoGravity() {
        return true;
    }

    public void particleCharge(int index) {
        if (!level.isClientSide()) {
            Vec3 from = new Vec3(getX(), getY(), getZ());
            if (index == 0) {
                Vec3 to = new Vec3(getX() + (-40 + getRandom().nextInt(80)), 255, getZ() + (-40 + getRandom().nextInt(80)));
                Vec3 per = to.subtract(from).normalize();
                Vec3 current = from.add(0, 0, 0);
                double distance = from.distanceTo(to);
                for (double i = 0; i < distance; i++) {
                    if (distance < 64) {
                        EffectUtils.sendParticles((ServerLevel) level, ParticleTypes.POOF, current.x(), current.y(), current.z(), 1, 0, 0, 0, 0.01, 0.1, 0.01);
                        current = current.add(per);
                    }
                }
            } else if (index == 1 || index == 2 || index == 3) {
                for (int i = 0; i < 180; i++) {
                    Vec3 to = new Vec3(getX() + ((Math.sin(i) * 200) * index), 255, getZ() + ((Math.cos(i) * 200) * index));
                    Vec3 per = to.subtract(from).normalize();
                    Vec3 current = from.add(0, 0, 0);
                    double distance = from.distanceTo(to);
                    for (double j = 0; j < distance; j++) {
                        if (distance < 64) {
                            ParticleOptions particle = index == 3 ? ParticleTypes.SOUL_FIRE_FLAME : ParticleTypes.FLAME;
                            EffectUtils.sendParticles((ServerLevel) level, particle, current.x(), current.y(), current.z(), 1, 0, 0, 0, Math.sin(i) / 4, 0.1, Math.cos(i) / 4);
                            current = current.add(per);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void swing(InteractionHand pHand) {
        super.swing(pHand);
        if (attackPhase == 0) {
            BPEffectEntity.createInstance(this, BPEffectTypes.SHACHATH_SLASH_FLAT);
            this.playSound(BPSoundEvents.SHACHATH_SLASH.get(), 0.75F, 0.75F + random.nextFloat());
            for (LivingEntity entities : level.getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(2.4, 0, 2.4))) {
                if (entities != this) {
                    double xa = entities.getX(), ya = entities.getY() + 1, za = entities.getZ();
                    entities.hurt(this.damageSources().mobAttack(this), this.isClone() ? 7F : 10F);
                    level.addParticle(BPParticles.SHACHATH_CLASH_INNER.get(), xa, ya, za, 0, 0, 0);
                    level.addParticle(BPParticles.SHACHATH_CLASH_OUTER.get(), xa, ya, za, 0, 0, 0);
                }
            }
        }
        double d0 = -Mth.sin(this.yRot * ((float)Math.PI / 180F)) * 6;
        double d1 = Mth.cos(this.yRot * ((float)Math.PI / 180F)) * 6;
        if (attackPhase == 1) {
            BPEffectEntity.createInstance(this, BPEffectTypes.SHACHATH_SLASH_FRONT);
            this.playSound(BPSoundEvents.SHACHATH_SLASH.get(), 0.75F, 0.75F + random.nextFloat());
            for (LivingEntity entities : level.getEntitiesOfClass(LivingEntity.class, new AABB(getX() - d0, getY() - 2.5, getZ() - d1, getX() + d0, getY() + 2.5, getZ() + d1))) {
                if (entities != this) {
                    double xa = entities.getX(), ya = entities.getY() + 1, za = entities.getZ();
                    entities.hurt(this.damageSources().mobAttack(this), this.isClone() ? 8F : 12F);
                    level.addParticle(BPParticles.SHACHATH_CLASH_INNER.get(), xa, ya, za, 0, 0, 0);
                    level.addParticle(BPParticles.SHACHATH_CLASH_OUTER.get(), xa, ya, za, 0, 0, 0);
                }
            }
        }
    }

    @Override
    public void startSeenByPlayer(ServerPlayer player) {
        super.startSeenByPlayer(player);
        if (!this.isClone()) {
            this.bossInfo.addPlayer(player);
            this.cloneProgress.addPlayer(player);
        }
    }

    @Override
    public void stopSeenByPlayer(ServerPlayer player) {
        super.stopSeenByPlayer(player);
        if (!this.isClone()) {
            this.bossInfo.removePlayer(player);
            this.cloneProgress.removePlayer(player);
        }
    }

    public boolean doHurtTarget (Entity entity) {

        this.teleportRandomly();
        this.tpTimer = 0;

        return super.doHurtTarget(entity);
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(STRIKING, false);
        this.entityData.define(ATTACKING2, false);
        this.entityData.define(DATA_IS_QUICKSHOOTING, false);
        this.entityData.define(DATA_IS_CLONE, false);
    }

    public boolean getStriking() {
        return this.entityData.get(STRIKING);
    }

    public void setStriking(boolean striking) {
        this.entityData.set(STRIKING, striking);
    }

    public boolean getAttacking2() {
        return this.entityData.get(ATTACKING2);
    }

    public void setAttacking2(boolean attacking2) {
        this.entityData.set(ATTACKING2, attacking2);
    }

    public boolean isQuickShooting() {
        return this.entityData.get(DATA_IS_QUICKSHOOTING);
    }

    public void setQuickShooting(boolean quickShooting) {
        this.entityData.set(DATA_IS_QUICKSHOOTING, quickShooting);
    }

    public boolean isClone() {
        return this.entityData.get(DATA_IS_CLONE);
    }

    public void setClone(boolean clone) {
        this.entityData.set(DATA_IS_CLONE, clone);
    }

    public void teleportRandomly() {
        boolean isNegVal = this.getRandom().nextBoolean();
        int tpLoc = this.getRandom().nextInt(15);

        this.level.playSound(null, this.getX(), this.getY(), this.getZ(), this.getTeleportSound(), SoundSource.HOSTILE, (float) 1, (float) 1);

        if (this.level instanceof ServerLevel) {
            ((ServerLevel) this.level).sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE, this.getX(), this.getY(), this.getZ(), this.tpParticleAmount, this.tpParticleRadius, this.tpParticleRadius, this.tpParticleRadius, 0.01);
        }

        BlockPos blockpos = BlockPos.containing(this.getX() + (isNegVal ? tpLoc : -tpLoc), this.getY(), this.getZ() + (isNegVal ? tpLoc : -tpLoc));

        if (!this.level.getBlockState(blockpos).blocksMotion() && this.canSeePos(blockpos)) {
            this.teleportWithEffect(blockpos.getX(), blockpos.getY(), blockpos.getZ());
        }
    }

    public boolean canSeePos(BlockPos pEntity) {
        Vec3 vector3d = new Vec3(this.getX(), this.getEyeY(), this.getZ());
        Vec3 vector3d1 = new Vec3(pEntity.getX(), pEntity.getY(), pEntity.getZ());
        if (vector3d1.distanceToSqr(vector3d) > 128.0D * 128.0D) return false; //Forge Backport MC-209819
        return this.level.clip(new ClipContext(vector3d, vector3d1, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this)).getType() == HitResult.Type.MISS;
    }

    public void descendEffect() {
        for (int i = level.getHeight(); i > getY(); i--) {
            for (int c = 0; c < 90; c++) {
                level.addParticle(ParticleTypes.FLAME, getX(), i + 0.5, getZ(), Math.sin(c), 0.01, Math.cos(c));
            }
        }
    }

    public void teleportWithEffect(double xLoc, double yLoc, double zLoc) {
        this.level.playSound(null, xLoc, yLoc, zLoc, this.getTeleportSound(), SoundSource.HOSTILE, (float) 1, (float) 1);
        if (this.level instanceof ServerLevel) {
            ((ServerLevel) this.level).sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE, xLoc, yLoc, zLoc, this.tpParticleAmount, this.tpParticleRadius, this.tpParticleRadius, this.tpParticleRadius, 0.01);
        }
        this.moveTo(xLoc, yLoc, zLoc);
    }

    public float getSoundVolume() {
        return 0.6F;
    }

    public SoundEvent getTeleportSound() {
        return SoundEvents.BLAZE_SHOOT;
    }

    public boolean causeFallDamage(float distance, float damageMultiplier) {
        return false;
    }

    protected void checkFallDamage(double y, boolean onGroundIn, BlockState state, BlockPos pos) {
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundNBT) {
        super.addAdditionalSaveData(compoundNBT);
        compoundNBT.putBoolean("isClone", entityData.get(DATA_IS_CLONE));

        if (this.boundOrigin != null) {
            compoundNBT.putInt("BoundX", this.boundOrigin.getX());
            compoundNBT.putInt("BoundY", this.boundOrigin.getY());
            compoundNBT.putInt("BoundZ", this.boundOrigin.getZ());
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundNBT) {
        super.readAdditionalSaveData(compoundNBT);
        this.setClone(compoundNBT.getBoolean("isClone"));
        if (compoundNBT.contains("BoundX")) {
            this.boundOrigin = new BlockPos(compoundNBT.getInt("BoundX"), compoundNBT.getInt("BoundY"), compoundNBT.getInt("BoundZ"));
        }
    }

    @Override
    public SoundEvent getAmbientSound() {
        return BPSoundEvents.SHACHATH_IDLE.get();
    }

    @Override
    public SoundEvent getHurtSound(DamageSource damageSource) {
        return BPSoundEvents.SHACHATH_HURT.get();
    }

    @Override
    public SoundEvent getDeathSound() {
        return BPSoundEvents.SHACHATH_DEATH.get();
    }

    @Override
    public int getMaxDamageCap() {
        return BPConfig.COMMON.shachathMobCap.get();
    }

    public void setBoundOrigin(@Nullable BlockPos pBoundOrigin) {
        this.boundOrigin = pBoundOrigin;
    }

    @Nullable
    public BlockPos getBoundOrigin() {
        return this.boundOrigin;
    }

    public class NonGroundController extends MoveControl {
        public NonGroundController(ShachathEntity floatingMob) {
            super(floatingMob);
        }

        public void tick() {
            ShachathEntity shachath = ShachathEntity.this;
            if (this.operation == MoveControl.Operation.MOVE_TO && !shachath.isVehicle()) {
                Vec3 vector3d = new Vec3(this.wantedX - shachath.getX(), this.wantedY - shachath.getY(), this.wantedZ - shachath.getZ());
                double d0 = vector3d.length();
                if (d0 < shachath.getBoundingBox().getSize()) {
                    this.operation = MoveControl.Operation.WAIT;
                    shachath.setDeltaMovement(shachath.getDeltaMovement().scale(0.5D));
                } else {
                    shachath.setDeltaMovement(shachath.getDeltaMovement().add(vector3d.scale((this.speedModifier * 0.05D / d0) * (shachath.isInWater() ? 2.2 : 1))));
                    if (shachath.getTarget() == null) {
                        Vec3 vector3d1 = shachath.getDeltaMovement();
                        shachath.yRot = -((float) Mth.atan2(vector3d1.x, vector3d1.z)) * (180F / (float) Math.PI);
                    } else {
                        double d2 = shachath.getTarget().getX() - shachath.getX();
                        double d1 = shachath.getTarget().getZ() - shachath.getZ();
                        shachath.yRot = -((float) Mth.atan2(d2, d1)) * (180F / (float) Math.PI);
                    }
                    shachath.yBodyRot = shachath.yRot;
                }
            }
        }
    }
    
    public class NonGroundMoveToTargetGoal extends Goal {
        public NonGroundMoveToTargetGoal() {
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        public boolean canUse() {

            if (ShachathEntity.this.getTarget() != null) {
                return ShachathEntity.this.distanceToSqr(ShachathEntity.this.getTarget()) > 2.0D;
            } else {
                return false;
            }
        }

        public boolean canContinueToUse() {
            if (ShachathEntity.this.getOwner() != null) {
                if (!(ShachathEntity.this.distanceToSqr(ShachathEntity.this.getOwner()) <= 1024D)) {
                    return false;
                }
            }
            return ShachathEntity.this.getMoveControl().hasWanted() && ShachathEntity.this.getTarget() != null && ShachathEntity.this.getTarget().isAlive();
        }

        public void start() {
            LivingEntity livingentity = ShachathEntity.this.getTarget();
            Vec3 vector3d = livingentity.getEyePosition(1.0F);
            ShachathEntity.this.moveControl.setWantedPosition(vector3d.x, vector3d.y, vector3d.z, 1.75D);
        }

        public void stop() {
        }

        public void tick() {
            LivingEntity livingentity = ShachathEntity.this.getTarget();

            if (!ShachathEntity.this.getBoundingBox().intersects(livingentity.getBoundingBox())) {
                double d0 = ShachathEntity.this.distanceToSqr(livingentity);
                if (d0 < 9.0D) {
                    Vec3 vector3d = livingentity.getEyePosition(1.0F);
                    ShachathEntity.this.moveControl.setWantedPosition(vector3d.x, vector3d.y, vector3d.z, 1.0D);
                }
            }
        }
    }

    public class NonGroundMoveRandomGoal extends Goal {
        public NonGroundMoveRandomGoal() {
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        public boolean canUse() {
            return !ShachathEntity.this.getMoveControl().hasWanted() && ShachathEntity.this.getOwner() == null && ShachathEntity.this.random.nextInt(5) == 0;
        }

        public boolean canContinueToUse() {
            return false;
        }

        public void tick() {
            BlockPos blockpos = ShachathEntity.this.getBoundOrigin();
            if (blockpos == null) {
                blockpos = ShachathEntity.this.blockPosition();
            }

            for(int i = 0; i < 3; ++i) {
                BlockPos blockpos1 = blockpos.offset(ShachathEntity.this.random.nextInt(15) - 7, ShachathEntity.this.random.nextInt(11) - 5, ShachathEntity.this.random.nextInt(15) - 7);
                if (ShachathEntity.this.level.isEmptyBlock(blockpos1)) {
                    ShachathEntity.this.moveControl.setWantedPosition((double)blockpos1.getX() + 0.5D, (double)blockpos1.getY() + 0.5D, (double)blockpos1.getZ() + 0.5D, 0.25D);
                    if (ShachathEntity.this.getTarget() == null) {
                        ShachathEntity.this.getLookControl().setLookAt((double)blockpos1.getX() + 0.5D, (double)blockpos1.getY() + 0.5D, (double)blockpos1.getZ() + 0.5D, 180.0F, 20.0F);
                    }
                    break;
                }
            }
        }
    }
}
