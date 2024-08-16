package io.github.bioplethora.entity.creatures;

import io.github.bioplethora.config.BPConfig;
import io.github.bioplethora.entity.FloatingMonsterEntity;
import io.github.bioplethora.entity.IBioClassification;
import io.github.bioplethora.entity.IGrylynenTier;
import io.github.bioplethora.entity.ai.gecko.GeckoMeleeGoal;
import io.github.bioplethora.enums.BPEntityClasses;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomFlyingGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.entity.monster.Monster;
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

public class GrylynenEntity extends FloatingMonsterEntity implements IAnimatable, FlyingAnimal, IBioClassification {

    private final AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);
    private final IGrylynenTier tier;

    public GrylynenEntity(EntityType<? extends Monster> type, Level worldIn, IGrylynenTier IGrylynenTier) {
        super(type, worldIn);
        this.noCulling = true;
        this.xpReward = 15;
        this.moveControl = new MoveHelperController(this);
        this.tier = IGrylynenTier;
    }

    public static AttributeSupplier.Builder setCustomAttributes(IGrylynenTier tier) {
        return Mob.createLivingAttributes()
                .add(Attributes.ARMOR, 0)
                .add(Attributes.ATTACK_SPEED, 10)
                .add(Attributes.ATTACK_KNOCKBACK, 0.5D)
                .add(Attributes.ATTACK_DAMAGE, tier.getTierDamage() * BPConfig.COMMON.mobMeeleeDamageMultiplier.get())
                .add(Attributes.MAX_HEALTH, !BPConfig.IN_HELLMODE ? tier.getTierHealth() : tier.getHellTierHP())
                .add(Attributes.MOVEMENT_SPEED, 0.25 * BPConfig.COMMON.mobMovementSpeedMultiplier.get())
                .add(Attributes.FLYING_SPEED, 0.45 * BPConfig.COMMON.mobMovementSpeedMultiplier.get())
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.2D)
                .add(Attributes.FOLLOW_RANGE, 32D);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(2, new WaterAvoidingRandomFlyingGoal(this, 1.2));
        this.goalSelector.addGoal(3, new GrylynenEntity.ChargeAttackGoal());
        this.goalSelector.addGoal(3, new GeckoMeleeGoal<>(this, 20, 0.7, 0.8));
        this.goalSelector.addGoal(4, new GrylynenEntity.MoveRandomGoal());
        this.goalSelector.addGoal(5, new FloatGoal(this));

        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 24.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
        
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this).setAlertOthers());
    }

    @Override
    public BPEntityClasses getBioplethoraClass() {
        return BPEntityClasses.DANGERUM;
    }

    private <E extends IAnimatable> PlayState predicate(AnimationState<E> event) {

        if (this.isDeadOrDying()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.grylynen.death", EDefaultLoopTypes.LOOP));
            return PlayState.CONTINUE;
        }
        if (this.getAttacking()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.grylynen.attack", EDefaultLoopTypes.LOOP));
            return PlayState.CONTINUE;
        }
        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.grylynen.idle", EDefaultLoopTypes.LOOP));
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar data) {
        data.add(new AnimationController<>(this, "grylynen_controller", 0, this::predicate));
    }

    public int getMaxSpawnClusterSize() {
        return 3;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (source != DamageSource.OUT_OF_WORLD) {
            amount = 1;
        }
        return super.hurt(source, amount);
    }

    public void tick() {
        super.tick();
        double x = this.getX();
        double y = this.getY();
        double z = this.getZ();
        if (this.level instanceof ServerLevel) {
            ((ServerLevel) this.level).sendParticles(ParticleTypes.END_ROD, x, y, z, 5, 0.65, 0.65, 0.65, 0.01);
        }
    }

    @Override
    public boolean doHurtTarget(Entity pEntity) {
        return super.doHurtTarget(pEntity);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.factory;
    }

    @Override
    public SoundEvent getHurtSound(DamageSource damageSource) {
        return this.getGrylynenTier().getHurtSound();
    }

    @Override
	public float getVoicePitch() {
        return 0.95F + (this.getRandom().nextFloat() / 2);
    }

    @Override
    public SoundEvent getAmbientSound() {
        return SoundEvents.AXE_STRIP;
    }

    @Override
    public SoundEvent getDeathSound() {
        return SoundEvents.BAMBOO_BREAK;
    }

    public IGrylynenTier getGrylynenTier() {
        return tier;
    }

	@Override
	public boolean isFlying() {
		return false;
	}
}
