package io.github.bioplethora.entity.creatures;

import javax.annotation.Nullable;

import io.github.bioplethora.api.world.EntityUtils;
import io.github.bioplethora.config.BPConfig;
import io.github.bioplethora.entity.IBioClassification;
import io.github.bioplethora.entity.SummonableMonsterEntity;
import io.github.bioplethora.entity.ai.gecko.GeckoDodgeableMeleeGoal;
import io.github.bioplethora.entity.ai.gecko.GeckoMoveToTargetGoal;
import io.github.bioplethora.entity.ai.goals.CopyTargetOwnerGoal;
import io.github.bioplethora.entity.ai.goals.FrostbiteGolemRangedAttackGoal;
import io.github.bioplethora.entity.others.part.BPPartEntity;
import io.github.bioplethora.enums.BPEntityClasses;
import io.github.bioplethora.registry.BPParticles;
import io.github.bioplethora.registry.BPSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
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
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.entity.PartEntity;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType.EDefaultLoopTypes;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class FrostbiteGolemEntity extends SummonableMonsterEntity implements IAnimatable, IBioClassification {

    private static final EntityDataAccessor<Boolean> DATA_IS_CHARGING = SynchedEntityData.defineId(FrostbiteGolemEntity.class, EntityDataSerializers.BOOLEAN);
    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);
    private boolean hasCracked = false;
    public final FrostbitePartEntity[] subEntities;
    public final FrostbitePartEntity head;

    public FrostbiteGolemEntity(EntityType<? extends FrostbiteGolemEntity> type, Level worldIn) {
        super(type, worldIn);
        this.noCulling = true;
        this.xpReward = 20;

        head = new FrostbitePartEntity<>(this, "head", 1.6f, 1.6f);
        subEntities = new FrostbitePartEntity[]{head};
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
        return BPEntityClasses.HELLSENT;
    }

    public static AttributeSupplier.Builder setCustomAttributes() {
        return Mob.createLivingAttributes()
                .add(Attributes.ARMOR, 10 * BPConfig.COMMON.mobArmorMultiplier.get())
                .add(Attributes.ATTACK_SPEED, 10)
                .add(Attributes.ATTACK_DAMAGE, 18.5 * BPConfig.COMMON.mobMeeleeDamageMultiplier.get())
                .add(Attributes.ATTACK_KNOCKBACK, 6D)
                .add(Attributes.MAX_HEALTH, 205 * BPConfig.COMMON.mobHealthMultiplier.get())
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.5)
                .add(Attributes.MOVEMENT_SPEED, 0.23 * BPConfig.COMMON.mobMovementSpeedMultiplier.get())
                .add(Attributes.FOLLOW_RANGE, 64D);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new RandomStrollGoal(this, 1F, 40));
        this.goalSelector.addGoal(2, new GeckoMoveToTargetGoal<>(this, 1.6, 8));
        //this.goalSelector.addGoal(1, new FrostbiteGolemAnimatedSmashingGoal(this));
        this.goalSelector.addGoal(2, new GeckoDodgeableMeleeGoal<>(this, 60, 0.6, 0.7));
        this.goalSelector.addGoal(7, new FloatGoal(this));
        this.goalSelector.addGoal(6, new FrostbiteGolemRangedAttackGoal(this));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AlphemEntity.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AlphemKingEntity.class, true));
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this, FrostbiteGolemEntity.class, AltyrusEntity.class).setAlertOthers(this.getClass()));
        this.targetSelector.addGoal(1, new CopyTargetOwnerGoal(this));
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (this.dead) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.frostbite_golem.death", EDefaultLoopTypes.LOOP));
            return PlayState.CONTINUE;
        }

        /*if (this.getSmashing()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.frostbite_golem.smashing", true));
            return PlayState.CONTINUE;
        }*/

        if (this.getAttacking()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.frostbite_golem.attack", EDefaultLoopTypes.LOOP));
            return PlayState.CONTINUE;
        }

        if (event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.frostbite_golem.walking", EDefaultLoopTypes.LOOP));
            return PlayState.CONTINUE;
        }

        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.frostbite_golem.idle", EDefaultLoopTypes.LOOP));
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "frostbite_golemcontroller", 0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Override
    public SoundEvent getAmbientSound() {
        return BPSoundEvents.FROSTBITE_GOLEM_IDLE.get();
    }

    @Override
    public SoundEvent getHurtSound(DamageSource damageSource) {
        return BPSoundEvents.FROSTBITE_GOLEM_HURT.get();
    }

    @Override
    public SoundEvent getDeathSound() {
        return BPSoundEvents.FROSTBITE_GOLEM_DEATH.get();
    }

    @Override
    public void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.IRON_GOLEM_STEP, 0.15f, 1);
    }

    @Override
    public void swing(InteractionHand pHand, boolean pUpdateSelf) {
        super.swing(pHand, pUpdateSelf);

        double d0 = -Mth.sin(this.yRot * ((float)Math.PI / 180F)) * 2.5;
        double d1 = Mth.cos(this.yRot * ((float)Math.PI / 180F)) * 2.5;

        if (!level.isClientSide()) {
            if (this.getHealth() <= 100 && BPConfig.IN_HELLMODE) {
                if (this.level instanceof ServerLevel) {
                    this.level.explode(null, getX() + d0, getY(), getZ() + d1, 2F, Explosion.BlockInteraction.BREAK);
                    ((ServerLevel) this.level).sendParticles(ParticleTypes.POOF, getX() + d0, getY(), getZ() + d1, 40, 0.75, 0.75,
                            0.75, 0.1);
                }
            } else {
                if (this.level instanceof ServerLevel) {
                    this.level.explode(null, getX() + d0, getY(), getZ() + d1, 1.2F, Explosion.BlockInteraction.NONE);
                    ((ServerLevel) this.level).sendParticles(ParticleTypes.POOF, getX() + d0, getY(), getZ() + d1, 20, 0.4, 0.4,
                            0.4, 0.1);
                }
            }
        }

        EntityUtils.shakeNearbyPlayersScreen(this, 28, 5);
    }

    public boolean doHurtTarget(Entity entity) {
        boolean flag = super.doHurtTarget(entity);
        /*f (flag && entity instanceof LivingEntity)*/

        this.doEnchantDamageEffects(this, entity);
        return flag;
    }

    @Override
    public void die(DamageSource source) {
        super.die(source);
        Entity sourceEnt = source.getEntity();

        //AdvancementUtils.grantBioAdvancement(sourceEnt, "bioplethora:frostbite_golem_kill");
    }

    public void aiStep() {
        super.aiStep();

        float rotYawHead = this.yHeadRot;
        if (rotYawHead > 180F) {
            rotYawHead -= 360F;
        }
        Vec3 v = this.getLookAngle().scale(this.getBbWidth() / 3.5 + this.getBbWidth() * 0.1);
        this.head.setPos(this.getX() + v.x, this.getY() + 2.75F, this.getZ() + v.z);
        this.head.setYHeadRot(rotYawHead);

        if (((LivingEntity) this).getHealth() <= 100 && BPConfig.COMMON.hellMode.get()) {
            ((LivingEntity) this).addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 5, 1));
        }

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

        if (this.getHealth() <= 100 && !this.hasCracked) {
            this.playSound(SoundEvents.IRON_GOLEM_DAMAGE, 1.0F, 1.0F);
            this.hasCracked = true;
        }
    }

    public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficultyIn, MobSpawnType reason, @Nullable SpawnGroupData spawnDataIn, @Nullable CompoundTag dataTag) {
        if (worldIn instanceof ServerLevel && BPConfig.COMMON.hellMode.get()) {
            this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(30 * BPConfig.COMMON.mobMeeleeDamageMultiplier.get());
            this.getAttribute(Attributes.ARMOR).setBaseValue(12.5 * BPConfig.COMMON.mobArmorMultiplier.get());
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(245 * BPConfig.COMMON.mobHealthMultiplier.get());
            this.setHealth(245 * BPConfig.COMMON.mobHealthMultiplier.get());
        }
        return super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
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

    @OnlyIn(Dist.CLIENT)
    public boolean isCharging() {
        return this.entityData.get(DATA_IS_CHARGING);
    }

    public void setCharging(boolean charging) {
        this.entityData.set(DATA_IS_CHARGING, charging);
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_IS_CHARGING, false);
    }

    public static class FrostbitePartEntity<T extends Mob> extends BPPartEntity<T> {

        public FrostbitePartEntity(T parent, String name, float width, float height) {
            super(parent, name, width, height);
        }

        @Override
        public boolean hurt(DamageSource pSource, float pAmount) {
            double d0 = -Mth.sin(parentMob.yRot * ((float)Math.PI / 180F)) * 1.5;
            double d1 = Mth.cos(parentMob.yRot * ((float)Math.PI / 180F)) * 1.5;
            this.level.addParticle(BPParticles.FROSTBITE_EYE.get(), parentMob.getX() + d0, getY() + 0.5, parentMob.getZ() + d1, 0d, 0d, 0d);
            return super.hurt(pSource, pAmount * 2.0F);
        }
    }
}
