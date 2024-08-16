package io.github.bioplethora.entity.creatures;

import io.github.bioplethora.config.BPConfig;
import io.github.bioplethora.entity.BPMonsterEntity;
import io.github.bioplethora.entity.IBioClassification;
import io.github.bioplethora.entity.ai.gecko.GeckoMoveToTargetGoal;
import io.github.bioplethora.entity.ai.goals.NandbriBiteAttackGoal;
import io.github.bioplethora.entity.ai.goals.NandbriScratchAttackGoal;
import io.github.bioplethora.enums.BPEntityClasses;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.AbstractGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType.EDefaultLoopTypes;

public class NandbriEntity extends BPMonsterEntity implements IAnimatable, IBioClassification {
    public int attackPhase;
    protected static final EntityDataAccessor<Boolean> SCRATCHING = SynchedEntityData.defineId(NandbriEntity.class, EntityDataSerializers.BOOLEAN);
    protected static final EntityDataAccessor<Boolean> SPITTING = SynchedEntityData.defineId(NandbriEntity.class, EntityDataSerializers.BOOLEAN);

    // TODO: Toxic Spit Attack
    // public int timeBeforeSpit = 60;

    private final AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);

    public NandbriEntity(EntityType<? extends Monster> type, Level world) {
        super(type, world);
    }

    public static AttributeSupplier.Builder setCustomAttributes() {
        return Mob.createLivingAttributes()
                .add(Attributes.ARMOR, 6 * BPConfig.COMMON.mobArmorMultiplier.get())
                .add(Attributes.ATTACK_SPEED, 10.5)
                .add(Attributes.ATTACK_DAMAGE, 4 * BPConfig.COMMON.mobMeeleeDamageMultiplier.get())
                .add(Attributes.ATTACK_KNOCKBACK, 0.25D)
                .add(Attributes.MAX_HEALTH, 50 * BPConfig.COMMON.mobHealthMultiplier.get())
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.4)
                .add(Attributes.MOVEMENT_SPEED, 0.6 * BPConfig.COMMON.mobMovementSpeedMultiplier.get())
                .add(Attributes.FOLLOW_RANGE, 32.0D);
    }

    @Override
    public BPEntityClasses getBioplethoraClass() {
        return BPEntityClasses.PLETHONEUTRAL;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 24.0F));
        this.goalSelector.addGoal(4, new RandomStrollGoal(this, 0.5F));
        this.goalSelector.addGoal(1, new GeckoMoveToTargetGoal<>(this, 0.75, 8));
        this.goalSelector.addGoal(1, new NandbriBiteAttackGoal(this, 16, 0.45, 0.75));
        this.goalSelector.addGoal(1, new NandbriScratchAttackGoal(this, 16.8, 0.23, 0.38));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(5, new FloatGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, AlphemEntity.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, AbstractGolem.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, DwarfMossadileEntity.class, true));
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this, NandbriEntity.class)).setAlertOthers());
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar data) {
        data.add(new AnimationController<>(this, "nandbri_controller", 0, this::predicate));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.factory;
    }

    private <E extends IAnimatable>PlayState predicate(AnimationState<E> event) {
        if(this.getAttacking()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.nandbri.attack", EDefaultLoopTypes.LOOP));
            return PlayState.CONTINUE;
        }

        if(event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.nandbri.walk", EDefaultLoopTypes.LOOP));
            return PlayState.CONTINUE;
        }

        if(this.getScratching()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.nandbri.scratch", EDefaultLoopTypes.LOOP));
            return PlayState.CONTINUE;
        }

        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.nandbri.idle", EDefaultLoopTypes.LOOP));
        return PlayState.CONTINUE;
    }

    public boolean doHurtTarget(Entity entity) {
        boolean flag = super.doHurtTarget(entity);
        Level world = entity.level;
        if(flag && entity instanceof LivingEntity) {
            ((LivingEntity) entity).addEffect(new MobEffectInstance(MobEffects.POISON, 100, 1));
            if(!world.isClientSide()) {
                if(this.attackPhase == 0) {
                    world.playSound(null, this, SoundEvents.ZOMBIE_INFECT, SoundSource.HOSTILE, 1, 1);
                }

                if(this.attackPhase == 1) {
                    world.playSound(null, this, SoundEvents.PLAYER_ATTACK_SWEEP, SoundSource.HOSTILE, 1, 1);
                }
            }
        }
        return flag;
    }

    @Override
    protected void doPush(Entity entity) {
        boolean flag = !entity.isCrouching() && (entity instanceof Player || entity instanceof Villager || ((LivingEntity)entity).getMobType() == MobType.ILLAGER);
        if(flag) {
            this.setTarget((LivingEntity) entity);
        }
    }

    /*
    @Override
    public void baseTick() {
        --timeBeforeSpit;
        if(timeBeforeSpit <= 0) {
            timeBeforeSpit = 60;
            setSpitting(true);
        }
    } */

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(SCRATCHING, false);
        this.entityData.define(SPITTING, false);
    }

    public boolean getScratching() {
        return this.entityData.get(SCRATCHING);
    }

    public void setScratching(boolean scratching) {
        this.entityData.set(SCRATCHING, scratching);
    }

    public boolean getSpitting() {
        return this.entityData.get(SPITTING);
    }

    public void setSpitting(boolean spitting) {
        this.entityData.set(SPITTING, spitting);
    }
}
