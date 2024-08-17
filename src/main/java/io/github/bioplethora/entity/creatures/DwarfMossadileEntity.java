package io.github.bioplethora.entity.creatures;

import javax.annotation.Nullable;

import io.github.bioplethora.config.BPConfig;
import io.github.bioplethora.entity.BPMonsterEntity;
import io.github.bioplethora.entity.IBioClassification;
import io.github.bioplethora.entity.ai.gecko.GeckoMeleeGoal;
import io.github.bioplethora.entity.ai.gecko.GeckoMoveToTargetGoal;
import io.github.bioplethora.enums.BPEntityClasses;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class DwarfMossadileEntity extends BPMonsterEntity implements GeoEntity, IBioClassification {

    private static final EntityDataAccessor<Boolean> DATA_IS_NETHER_VARIANT = SynchedEntityData.defineId(DwarfMossadileEntity.class, EntityDataSerializers.BOOLEAN);
    private final AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);

    public DwarfMossadileEntity(EntityType<? extends Monster> type, Level world) {
        super(type, world);
        this.xpReward = 10;
    }

    public static AttributeSupplier.Builder setCustomAttributes() {
        return Mob.createLivingAttributes()
                .add(Attributes.ARMOR, 2 * BPConfig.COMMON.mobArmorMultiplier.get())
                .add(Attributes.ATTACK_SPEED, 1.5)
                .add(Attributes.ATTACK_DAMAGE, 5 * BPConfig.COMMON.mobMeeleeDamageMultiplier.get())
                .add(Attributes.ATTACK_KNOCKBACK, 0.3D)
                .add(Attributes.MAX_HEALTH, 25 * BPConfig.COMMON.mobHealthMultiplier.get())
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.2)
                .add(Attributes.MOVEMENT_SPEED, 0.25 * BPConfig.COMMON.mobMovementSpeedMultiplier.get())
                .add(Attributes.FOLLOW_RANGE, 32.0D);
    }

    @Override
    public BPEntityClasses getBioplethoraClass() {
        return BPEntityClasses.DANGERUM;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 24.0F));
        this.goalSelector.addGoal(4, new RandomStrollGoal(this, 1F));
        this.goalSelector.addGoal(1, new GeckoMoveToTargetGoal<>(this, 1, 8));
        this.goalSelector.addGoal(1, new GeckoMeleeGoal<>(this, 40, 0.5, 0.6));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(5, new FloatGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, NandbriEntity.class, true));
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this, DwarfMossadileEntity.class)).setAlertOthers());
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar data) {
        data.add(new AnimationController<>(this, "dwarf_mossadile_controller", 0, this::predicate));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.factory;
    }

    private <E extends GeoEntity> PlayState predicate(AnimationState<E> event) {
        if (this.getAttacking()) {
            event.getController().setAnimation(RawAnimation.begin().thenPlay("animation.dwarf_mossadile.attacking"));
            return PlayState.CONTINUE;
        }

        if (event.isMoving()) {
            event.getController().setAnimation(RawAnimation.begin().thenPlay("animation.dwarf_mossadile.walking"));
            return PlayState.CONTINUE;
        }

        event.getController().setAnimation(RawAnimation.begin().thenPlay("animation.dwarf_mossadile.idle"));
        return PlayState.CONTINUE;
    }

    @Override
    public SoundEvent getAmbientSound() {
        return SoundEvents.SHULKER_AMBIENT;
    }

    @Override
    public SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.SHULKER_HURT;
    }

    @Override
    public SoundEvent getDeathSound() {
        return SoundEvents.SHULKER_DEATH;
    }

    @Override
    public void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.SALMON_FLOP, 0.15f, 1);
    }

    public boolean doHurtTarget(Entity entity) {
        boolean flag = super.doHurtTarget(entity);

        Level world = entity.level;
        BlockPos blockPos = new BlockPos((int) getTarget().getX(), (int) getTarget().getY(), (int) getTarget().getZ());

        if (flag && entity instanceof LivingEntity) {
            if (this.isNetherVariant()) {
                entity.setSecondsOnFire(5);
                ((ServerLevel) this.level).sendParticles(ParticleTypes.FLAME, getTarget().getX(), getTarget().getY(), getTarget().getZ(), 20, 0.4, 0.4, 0.4, 0.1);
                this.level.playSound(null, blockPos, SoundEvents.BLAZE_SHOOT, SoundSource.HOSTILE, (float) 1, (float) 1);
            } else {
                ((LivingEntity) entity).addEffect(new MobEffectInstance(MobEffects.POISON, 40, 1));
            }
        }
        return flag;
    }

    public int getMaxSpawnClusterSize() {
        return 3;
    }

    @Override
    public boolean checkSpawnRules(LevelAccessor p_213380_1_, MobSpawnType p_213380_2_) {
        return super.checkSpawnRules(p_213380_1_, p_213380_2_) && !p_213380_1_.getBlockState(this.blockPosition().below()).isAir();
    }

    public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficultyIn, MobSpawnType reason, @Nullable SpawnGroupData spawnDataIn, @Nullable CompoundTag dataTag) {

        if ((this.level.dimension().equals(Level.NETHER) || (this.level.dimension().equals(Level.NETHER)))) {
            this.setNetherVariant(true);
        }

        if (BPConfig.COMMON.hellMode.get()) {
            this.getAttribute(Attributes.ARMOR).setBaseValue(4 * BPConfig.COMMON.mobArmorMultiplier.get());
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(37 * BPConfig.COMMON.mobHealthMultiplier.get());
            this.setHealth(37 * BPConfig.COMMON.mobHealthMultiplier.get());
        }

        if (this.isNetherVariant()) {
            this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(7.5 * BPConfig.COMMON.mobMeeleeDamageMultiplier.get());
        }

        return super.finalizeSpawn(world, difficultyIn, reason, spawnDataIn, dataTag);
    }

    public boolean checkSpawnObstruction(LevelReader pLevel) {
        return pLevel.isUnobstructed(this) && !pLevel.containsAnyLiquid(this.getBoundingBox()) && Math.random() < 0.25;
    }

    @Override
    public boolean fireImmune() {
        return this.isNetherVariant();
    }

    public boolean isNetherVariant() {
        return this.entityData.get(DATA_IS_NETHER_VARIANT);
    }

    public void setNetherVariant(boolean netherVariant) {
        this.entityData.set(DATA_IS_NETHER_VARIANT, netherVariant);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundNBT) {
        super.addAdditionalSaveData(compoundNBT);
        compoundNBT.putBoolean("isNetherVariant", this.isNetherVariant());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundNBT) {
        super.readAdditionalSaveData(compoundNBT);
        this.setNetherVariant(compoundNBT.getBoolean("isNetherVariant"));
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_IS_NETHER_VARIANT, false);
    }
}
