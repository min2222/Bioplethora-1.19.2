package io.github.bioplethora.entity.creatures;

import javax.annotation.Nullable;

import io.github.bioplethora.config.BPConfig;
import io.github.bioplethora.entity.BPMonsterEntity;
import io.github.bioplethora.entity.IBioClassification;
import io.github.bioplethora.entity.ai.goals.CavernFleignarMeleeGoal;
import io.github.bioplethora.entity.others.part.BPPartEntity;
import io.github.bioplethora.enums.BPEntityClasses;
import io.github.bioplethora.registry.BPEffects;
import io.github.bioplethora.registry.BPTags;
import io.github.bioplethora.world.BPVanillaBiomeFeatureGeneration;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;
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
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class CavernFleignarEntity extends BPMonsterEntity implements GeoEntity, IBioClassification {

    private final AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);
    public boolean isHuge;
    public boolean finalize = false;

    //public final BPPartEntity[] subEntities;
    //public final BPPartEntity testPart;

    public CavernFleignarEntity(EntityType<? extends Monster> type, Level worldIn) {
        super(type, worldIn);
        this.noCulling = true;

        //testPart = new BPPartEntity<>(this, "test", 0.8f, 4.5f);
        //subEntities = new BPPartEntity[]{testPart};
    }

    /*
    @Override
    public boolean isMultipartEntity() {
        return true;
    }

    @Nullable
    @Override
    public PartEntity<?>[] getParts() {
        return subEntities;
    }*/


    @Override
    public void tick() {
        super.tick();
        if (this.hasEffect(MobEffects.POISON)) {
            this.removeEffect(MobEffects.POISON);
        }

        //tickPart(testPart, 3, 0, 3);

        if (!finalize) {
            if (Math.random() <= 0.5) {
                this.isHuge = true;
            }
            finalize = true;
        }

        /*
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
        }*/
    }

    private void tickPart(BPPartEntity pPart, double offsetX, double offsetY, double offsetZ) {
        pPart.setPos(getX() + offsetX, getY() + offsetY, getZ() + offsetZ);
    }


    @Override
    public BPEntityClasses getBioplethoraClass() {
        return BPEntityClasses.PLETHONEUTRAL;
    }

    public static AttributeSupplier.Builder setCustomAttributes() {
        return Mob.createLivingAttributes()
                .add(Attributes.ARMOR, 4 * BPConfig.COMMON.mobArmorMultiplier.get())
                .add(Attributes.ATTACK_SPEED, 10)
                .add(Attributes.ATTACK_DAMAGE, 8 * BPConfig.COMMON.mobMeeleeDamageMultiplier.get())
                .add(Attributes.ATTACK_KNOCKBACK, 1.5D)
                .add(Attributes.MAX_HEALTH, 40 * BPConfig.COMMON.mobHealthMultiplier.get())
                .add(Attributes.KNOCKBACK_RESISTANCE, 10)
                .add(Attributes.MOVEMENT_SPEED, 0.1 * BPConfig.COMMON.mobMovementSpeedMultiplier.get())
                .add(Attributes.FOLLOW_RANGE, 24D);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 24.0F));
        this.goalSelector.addGoal(2, new CavernFleignarMeleeGoal(this, 20, 0.8, 0.9));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));

        //this.targetSelector.addGoal(2, new CavernFleignarTargetGoal(this, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, LivingEntity.class, 10, false, false, this::validTarget));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this).setAlertOthers(this.getClass()));
    }

    private <E extends GeoEntity> PlayState predicate(AnimationState<E> event) {
        if (this.dead) {
            event.getController().setAnimation(RawAnimation.begin().thenPlay("animation.cavern_fleignar.death"));
            return PlayState.CONTINUE;
        }

        if (this.getAttacking()) {
            event.getController().setAnimation(RawAnimation.begin().thenPlay("animation.cavern_fleignar.attack"));
            return PlayState.CONTINUE;
        }

        event.getController().setAnimation(RawAnimation.begin().thenPlay("animation.cavern_fleignar.idle"));
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar data) {
        data.add(new AnimationController<>(this, "cavern_fleignar_controller", 0, this::predicate));
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficultyIn, MobSpawnType reason, @Nullable SpawnGroupData spawnDataIn, @Nullable CompoundTag dataTag) {
        if (worldIn instanceof ServerLevel && BPConfig.COMMON.hellMode.get()) {
            this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(10 * BPConfig.COMMON.mobMeeleeDamageMultiplier.get());
            this.getAttribute(Attributes.ARMOR).setBaseValue(8 * BPConfig.COMMON.mobArmorMultiplier.get());
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(80 * BPConfig.COMMON.mobHealthMultiplier.get());
            this.setHealth(80 * BPConfig.COMMON.mobHealthMultiplier.get());
        }

        return super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.factory;
    }

    @Override
    public float getScale() {
        return this.isHuge ? 1.5F : 1.0F;
    }

    @Override
    public SoundEvent getAmbientSound() {
        return SoundEvents.SQUID_AMBIENT;
    }

    @Override
    public SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.SQUID_HURT;
    }

    @Override
    public SoundEvent getDeathSound() {
        return SoundEvents.SQUID_DEATH;
    }

    @Override
	public float getVoicePitch() {
        return 0.5F;
    }

    public boolean doHurtTarget(Entity entity) {
        boolean flag = super.doHurtTarget(entity);

        for (LivingEntity targetArea : this.level.getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate(4, 2, 4))) {

            if (targetArea != this) {
                float knockbackValue = ((float) this.getAttributeValue(Attributes.ATTACK_KNOCKBACK) / 2);
                int poisonDuration = BPConfig.IN_HELLMODE ? 60 : 100;
                int poisonAmplification = BPConfig.IN_HELLMODE ? 0 : 1;

                if (this.level instanceof ServerLevel) {
                    ((ServerLevel) this.level).sendParticles(ParticleTypes.POOF, entity.getX(), entity.getY(), entity.getZ(), 15, 1.2, 0.2, 1.2, 0.01);
                }

                targetArea.knockback(knockbackValue * 0.5F, Mth.sin(this.yRot * ((float) Math.PI / 180F)), -Mth.cos(this.yRot * ((float) Math.PI / 180F)));
                targetArea.hurt(this.damageSources().mobAttack(this), (float) (this.getAttributeValue(Attributes.ATTACK_KNOCKBACK) * 0.75));
                if (targetArea instanceof CavernFleignarEntity) {
                    targetArea.addEffect(new MobEffectInstance(MobEffects.POISON, poisonDuration, poisonAmplification));
                }
            }
        }
        return flag;
    }

    @Override
    protected void tickDeath() {

        ++this.deathTime;

        if (this.deathTime == 40) {
            this.discard();

            for (int i = 0; i < 100; ++i) {
                double d0 = this.random.nextGaussian() * 0.02D;
                double d1 = this.random.nextGaussian() * 0.02D;
                double d2 = this.random.nextGaussian() * 0.02D;

                this.level.addParticle(ParticleTypes.POOF, this.getRandomX(1.0D), this.getRandomY(), this.getRandomZ(1.0D), d0, d1, d2);
            }
        }
    }

    @Override
    public void checkDespawn() {
        BlockPos posBelow = this.blockPosition().below();
        if (!this.level.getBlockState(posBelow).is(BPTags.Blocks.ALPHANIA)) {
            super.checkDespawn();
        } else {
            if (this.level.getDifficulty() == Difficulty.PEACEFUL && this.shouldDespawnInPeaceful()) {
                this.discard();
            }
        }
    }

    @Override
    public boolean checkSpawnRules(LevelAccessor world, MobSpawnType reason) {
        return super.checkSpawnRules(world, reason) && CavernFleignarEntity.checkFleignarSpawnRules(level, blockPosition());
    }

    public static boolean checkFleignarSpawnRules(LevelAccessor world, BlockPos pos) {
        if (world instanceof WorldGenLevel) {
            return pos.getY() <= 50 && BPVanillaBiomeFeatureGeneration.isFleignariteChunk(pos, (WorldGenLevel) world);
        } else {
            return  false;
        }
    }

    public boolean isPushable() {
        return false;
    }

    public boolean validTarget(LivingEntity target) {
        boolean getTag = target.getType().is(BPTags.Entities.FLEIGNAR_TARGETS);
        
        if (this.canAttack(target) && (getTag || target instanceof Player)) {
            return !target.hasEffect(BPEffects.SPIRIT_MANIPULATION.get());
        } else {
            return false;
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putBoolean("fleignar_huge", this.isHuge);
        pCompound.putBoolean("hasFinalized", this.finalize);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        this.isHuge = pCompound.getBoolean("fleignar_huge");
        this.finalize = pCompound.getBoolean("hasFinalized");
    }
}