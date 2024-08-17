package io.github.bioplethora.entity.creatures;

import java.util.EnumSet;

import javax.annotation.Nullable;

import io.github.bioplethora.api.advancements.AdvancementUtils;
import io.github.bioplethora.api.world.BlockUtils;
import io.github.bioplethora.api.world.EntityUtils;
import io.github.bioplethora.config.BPConfig;
import io.github.bioplethora.entity.BPMonsterEntity;
import io.github.bioplethora.entity.IBioClassification;
import io.github.bioplethora.entity.IMobCappedEntity;
import io.github.bioplethora.entity.ai.gecko.GeckoDodgeableMeleeGoal;
import io.github.bioplethora.entity.ai.goals.AltyrusRangedAttackGoal;
import io.github.bioplethora.entity.ai.goals.AltyrusSummonGolemGoal;
import io.github.bioplethora.enums.BPEntityClasses;
import io.github.bioplethora.registry.BPAttributes;
import io.github.bioplethora.registry.BPSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.BossEvent.BossBarColor;
import net.minecraft.world.BossEvent.BossBarOverlay;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class AltyrusEntity extends BPMonsterEntity implements GeoEntity, FlyingAnimal, IBioClassification, IMobCappedEntity {

    private static final EntityDataAccessor<Boolean> DATA_IS_CHARGING = SynchedEntityData.defineId(AltyrusEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_IS_SUMMONING = SynchedEntityData.defineId(AltyrusEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_IS_DODGING = SynchedEntityData.defineId(AltyrusEntity.class, EntityDataSerializers.BOOLEAN);
    private final ServerBossEvent bossInfo = (ServerBossEvent) (new ServerBossEvent(this.getDisplayName(), BossBarColor.PURPLE, BossBarOverlay.PROGRESS).setDarkenScreen(true).setPlayBossMusic(true));
    private final AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);
    public BlockPos boundOrigin;
    public int dodgeTimer;

    public AltyrusEntity(EntityType<? extends BPMonsterEntity> type, Level world) {
        super(type, world);
        this.noCulling = true;
        this.moveControl = new AltyrusEntity.MoveHelperController(this);
        this.xpReward = 200;
    }

    @Override
    public BPEntityClasses getBioplethoraClass() {
        return BPEntityClasses.ELDERIA;
    }

    public static AttributeSupplier.Builder setCustomAttributes() {
        return Mob.createLivingAttributes()
                .add(Attributes.ARMOR, 15 * BPConfig.COMMON.mobArmorMultiplier.get())
                .add(Attributes.ATTACK_SPEED, 10)
                .add(Attributes.ATTACK_DAMAGE, 35 * BPConfig.COMMON.mobMeeleeDamageMultiplier.get())
                .add(Attributes.ATTACK_KNOCKBACK, 7D)
                .add(Attributes.MAX_HEALTH, 370 * BPConfig.COMMON.mobHealthMultiplier.get())
                .add(Attributes.KNOCKBACK_RESISTANCE, 10.0)
                .add(Attributes.MOVEMENT_SPEED, 1.5F * BPConfig.COMMON.mobMovementSpeedMultiplier.get())
                .add(Attributes.FLYING_SPEED, 1.5F * BPConfig.COMMON.mobMovementSpeedMultiplier.get())
                .add(Attributes.FOLLOW_RANGE, 64D)
                .add(BPAttributes.TRUE_DEFENSE.get(), 2D);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(3, new AltyrusEntity.ChargeAttackGoal());
        this.goalSelector.addGoal(3, new GeckoDodgeableMeleeGoal<>(this, 60, 0.5, 0.6));
        this.goalSelector.addGoal(4, new AltyrusEntity.MoveRandomGoal());
        this.goalSelector.addGoal(4, new AltyrusRangedAttackGoal(this));
        this.goalSelector.addGoal(5, new AltyrusSummonGolemGoal(this));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 24.0F));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, AlphemEntity.class, 24.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(8, new FloatGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, false));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, AlphemEntity.class, false));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, AlphemKingEntity.class, false));
        this.targetSelector.addGoal(3, new HurtByTargetGoal(this));
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar data) {
        data.add(new AnimationController<>(this, "altyrus_controller", 0, this::predicate));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.factory;
    }

    private <E extends GeoEntity>PlayState predicate(AnimationState<E> event) {

        if (this.isDeadOrDying() || this.dead) {
            event.getController().setAnimation(RawAnimation.begin().thenPlay("animation.altyrus.death"));
            return PlayState.CONTINUE;
        }

        if (this.isSummoning()) {
            event.getController().setAnimation(RawAnimation.begin().thenPlay("animation.altyrus.summoning"));
            return PlayState.CONTINUE;
        }

        if (this.getAttacking()) {
            event.getController().setAnimation(RawAnimation.begin().thenPlay("animation.altyrus.attacking"));
            return PlayState.CONTINUE;
        }

        if (this.isCharging()) {
            event.getController().setAnimation(RawAnimation.begin().thenPlay("animation.altyrus.charging"));
            return PlayState.CONTINUE;
        }

        if (this.isDodging()) {
            event.getController().setAnimation(RawAnimation.begin().thenPlay("animation.altyrus.dodging"));
            return PlayState.CONTINUE;
        }

        event.getController().setAnimation(RawAnimation.begin().thenPlay("animation.altyrus.idle"));
        return PlayState.CONTINUE;
    }

    public boolean doHurtTarget (Entity entity) {
        boolean flag = super.doHurtTarget(entity);
        double x = entity.getX(), y = entity.getY(), z = entity.getZ();

        this.level.explode(null, (int) x, (int) y, (int) z, (float) 3, EntityUtils.getMobGriefingEvent(this.level, this));
        if (this.level instanceof ServerLevel) {
            ((ServerLevel) this.level).sendParticles(ParticleTypes.POOF, x, y, z, 40, 0.75, 0.75, 0.75, 0.1);
        }
        BlockUtils.knockUpRandomNearbyBlocks(this.level, 0.5D, entity.blockPosition().below(), 5, 2, 5, false, true);
        return flag;
    }

    public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficultyIn, MobSpawnType reason, @Nullable SpawnGroupData spawnDataIn, @Nullable CompoundTag dataTag) {
        if (worldIn instanceof ServerLevel && BPConfig.COMMON.hellMode.get()) {
            this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(42 * BPConfig.COMMON.mobMeeleeDamageMultiplier.get());
            this.getAttribute(Attributes.ARMOR).setBaseValue(24.5 * BPConfig.COMMON.mobArmorMultiplier.get());
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(520 * BPConfig.COMMON.mobHealthMultiplier.get());
            this.setHealth(520 * BPConfig.COMMON.mobHealthMultiplier.get());
        }

        return super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    @Override
    public void aiStep() {
        super.aiStep();

        Level world = this.level;

        this.bossInfo.setProgress(this.getHealth() / this.getMaxHealth());

        if (this.isDodging()) {
            ++dodgeTimer;
            if (dodgeTimer == 20) {
                this.setDodging(false);
                this.dodgeTimer = 0;
            }
        }

        if (world instanceof ServerLevel) {
            ServerLevel serverLevel = ((ServerLevel) world);

            serverLevel.sendParticles(ParticleTypes.CLOUD, this.getX(), this.getY(), this.getZ(), 10, 1.2, 1.2, 1.2, 0.01);

            //this.summonParticleBarrier(serverLevel);
        }
    }

    public void summonParticleBarrier(ServerLevel serverLevel) {

        int loop = 0; int particleAmount = 10; int xRad = 10; int zRad = 10;

        serverLevel.sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE,
                (this.getX() + 0.5) + Math.cos(((Math.PI * 2) / particleAmount) * loop) * xRad,
                this.getY(),
                (this.getZ() + 0.5) + Math.sin(((Math.PI * 2) / particleAmount) * loop) * zRad,
                5, 0.5, 1.5, 0.5, 0.01);
        ++loop;
    }

    /*public void summonParticleBarrier(ServerLevel serverLevel) {

        int loop = 0; int particleAmount = 10; int xRad = 3; int zRad = 3;

        while (loop < particleAmount) {
            serverLevel.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE,
                    this.getX() + 0.5 + Math.cos(((Math.PI * 2) / particleAmount) * loop) * xRad,
                    this.getY(),
                    this.getZ() + 0.5 + Math.sin(((Math.PI * 2) / particleAmount) * loop) * zRad,
                    0.0, 1.0, 0.0);
            ++loop;
        }
    }*/

    public boolean isNoGravity() {
        return true;
    }

    @Override
    public void die(DamageSource source) {
        super.die(source);

        Entity sourceEnt = source.getEntity();

        AdvancementUtils.grantBioAdvancement(sourceEnt, "bioplethora:altyrus_kill");
    }

    @Override
    public void startSeenByPlayer(ServerPlayer player) {
        super.startSeenByPlayer(player);
        this.bossInfo.addPlayer(player);
    }

    @Override
    public void stopSeenByPlayer(ServerPlayer player) {
        super.stopSeenByPlayer(player);
        this.bossInfo.removePlayer(player);
    }

    @Override
    public SoundEvent getAmbientSound() {
        return BPSoundEvents.ALTYRUS_IDLE.get();
    }

    @Override
    public SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.BLAZE_HURT;
    }

    @Override
    public SoundEvent getDeathSound() {
        return BPSoundEvents.FROSTBITE_GOLEM_DEATH.get();
    }

    public SoundEvent getDodgeSound() {
        return SoundEvents.SHULKER_SHOOT;
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_IS_CHARGING, false);
        this.entityData.define(DATA_IS_SUMMONING, false);
        this.entityData.define(DATA_IS_DODGING, false);
    }

    public boolean isCharging() {
        return this.entityData.get(DATA_IS_CHARGING);
    }

    public void setCharging(boolean charging) {
        this.entityData.set(DATA_IS_CHARGING, charging);
    }

    public boolean isSummoning() {
        return this.entityData.get(DATA_IS_SUMMONING);
    }

    public void setSummoning(boolean summoning) {
        this.entityData.set(DATA_IS_SUMMONING, summoning);
    }

    public boolean isDodging() {
        return this.entityData.get(DATA_IS_DODGING);
    }

    public void setDodging(boolean dodging) {
        this.entityData.set(DATA_IS_DODGING, dodging);
    }


    public boolean causeFallDamage(float distance, float damageMultiplier) {
        return false;
    }

    protected void checkFallDamage(double y, boolean onGroundIn, BlockState state, BlockPos pos) {
    }

    public void checkDespawn() {
    }

    @Override
    public boolean ignoreExplosion() {
        return true;
    }

    @Override
    public boolean fireImmune() {
        return true;
    }

    @Override
    public boolean isPushedByFluid() {
        return false;
    }

    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        if (pCompound.contains("BoundX")) {
            this.boundOrigin = new BlockPos(pCompound.getInt("BoundX"), pCompound.getInt("BoundY"), pCompound.getInt("BoundZ"));
        }
    }

    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        if (this.boundOrigin != null) {
            pCompound.putInt("BoundX", this.boundOrigin.getX());
            pCompound.putInt("BoundY", this.boundOrigin.getY());
            pCompound.putInt("BoundZ", this.boundOrigin.getZ());
        }
    }

    public void setBoundOrigin(@Nullable BlockPos pBoundOrigin) {
        this.boundOrigin = pBoundOrigin;
    }

    @Nullable
    public BlockPos getBoundOrigin() {
        return this.boundOrigin;
    }

    @Override
    public int getMaxDamageCap() {
        return BPConfig.COMMON.altyrusMobCap.get();
    }

    class ChargeAttackGoal extends Goal {
        public ChargeAttackGoal() {
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }
        
        public boolean canUse() {
            if (AltyrusEntity.this.getTarget() != null &&
                    !AltyrusEntity.this.getMoveControl().hasWanted() &&
                    AltyrusEntity.this.random.nextInt(3) == 0) {

                return AltyrusEntity.this.distanceToSqr(AltyrusEntity.this.getTarget()) > 2.0D;
            } else {
                return false;
            }
        }
        
        public boolean canContinueToUse() {
            return AltyrusEntity.this.getMoveControl().hasWanted() &&
                    AltyrusEntity.this.getTarget() != null &&
                    AltyrusEntity.this.getTarget().isAlive();
        }
        
        public void start() {
            LivingEntity livingentity = AltyrusEntity.this.getTarget();
            Vec3 vector3d = livingentity.getEyePosition(1.0F);
            AltyrusEntity.this.moveControl.setWantedPosition(vector3d.x, vector3d.y, vector3d.z, 1.0D);
            AltyrusEntity.this.playSound(BPSoundEvents.ALTYRUS_CHARGE.get(), 1.0F, 1.0F);
        }
        
        public void stop() {
        }
        
        public void tick() {
            LivingEntity livingentity = AltyrusEntity.this.getTarget();

            if (!AltyrusEntity.this.getBoundingBox().intersects(livingentity.getBoundingBox())) {
                double d0 = AltyrusEntity.this.distanceToSqr(livingentity);
                if (d0 < 9.0D) {
                    Vec3 vector3d = livingentity.getEyePosition(1.0F);
                    AltyrusEntity.this.moveControl.setWantedPosition(vector3d.x, vector3d.y, vector3d.z, 1.0D);
                }
            }

            /*if (AltyrusEntity.this.getBoundingBox().intersects(livingentity.getBoundingBox())) {
                AltyrusEntity.this.doHurtTarget(livingentity);
            } else {
                double d0 = AltyrusEntity.this.distanceToSqr(livingentity);
                if (d0 < 9.0D) {
                    Vec3 vector3d = livingentity.getEyePosition(1.0F);
                    AltyrusEntity.this.moveControl.setWantedPosition(vector3d.x, vector3d.y, vector3d.z, 1.0D);
                }
            }*/
        }
    }

    class MoveHelperController extends MoveControl {
        public MoveHelperController(AltyrusEntity altyrus) {
            super(altyrus);
        }

        public void tick() {
            if (this.operation == MoveControl.Operation.MOVE_TO) {
                Vec3 vector3d = new Vec3(this.wantedX - AltyrusEntity.this.getX(), this.wantedY - AltyrusEntity.this.getY(), this.wantedZ - AltyrusEntity.this.getZ());
                double d0 = vector3d.length();
                if (d0 < AltyrusEntity.this.getBoundingBox().getSize()) {
                    this.operation = MoveControl.Operation.WAIT;
                    AltyrusEntity.this.setDeltaMovement(AltyrusEntity.this.getDeltaMovement().scale(0.5D));
                } else {
                    AltyrusEntity.this.setDeltaMovement(AltyrusEntity.this.getDeltaMovement().add(vector3d.scale(this.speedModifier * 0.05D / d0)));
                    if (AltyrusEntity.this.getTarget() == null) {
                        Vec3 vector3d1 = AltyrusEntity.this.getDeltaMovement();
                        AltyrusEntity.this.yRot = -((float) Mth.atan2(vector3d1.x, vector3d1.z)) * (180F / (float)Math.PI);
                        AltyrusEntity.this.yBodyRot = AltyrusEntity.this.yRot;
                    } else {
                        double d2 = AltyrusEntity.this.getTarget().getX() - AltyrusEntity.this.getX();
                        double d1 = AltyrusEntity.this.getTarget().getZ() - AltyrusEntity.this.getZ();
                        AltyrusEntity.this.yRot = -((float)Mth.atan2(d2, d1)) * (180F / (float)Math.PI);
                        AltyrusEntity.this.yBodyRot = AltyrusEntity.this.yRot;
                    }
                }

            }
        }
    }

    class MoveRandomGoal extends Goal {
        public MoveRandomGoal() {
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        public boolean canUse() {
            return !AltyrusEntity.this.getMoveControl().hasWanted() && AltyrusEntity.this.random.nextInt(7) == 0;
        }

        public boolean canContinueToUse() {
            return false;
        }

        public void tick() {
            BlockPos blockpos = AltyrusEntity.this.getBoundOrigin();
            if (blockpos == null) {
                blockpos = AltyrusEntity.this.blockPosition();
            }

            for(int i = 0; i < 3; ++i) {
                BlockPos blockpos1 = blockpos.offset(AltyrusEntity.this.random.nextInt(15) - 7, AltyrusEntity.this.random.nextInt(11) - 7, AltyrusEntity.this.random.nextInt(15) - 7);
                if (AltyrusEntity.this.level.isEmptyBlock(blockpos1)) {
                    AltyrusEntity.this.moveControl.setWantedPosition((double)blockpos1.getX() + 0.5D, (double)blockpos1.getY() + 0.5D, (double)blockpos1.getZ() + 0.5D, 0.25D);
                    if (AltyrusEntity.this.getTarget() == null) {
                        AltyrusEntity.this.getLookControl().setLookAt((double)blockpos1.getX() + 0.5D, (double)blockpos1.getY() + 0.5D, (double)blockpos1.getZ() + 0.5D, 180.0F, 20.0F);
                    }
                    break;
                }
            }
        }
    }

	@Override
	public boolean isFlying() {
		return false;
	}
}
