package io.github.bioplethora.entity.creatures;

import javax.annotation.Nullable;

import io.github.bioplethora.config.BPConfig;
import io.github.bioplethora.entity.FloatingMonsterEntity;
import io.github.bioplethora.entity.IBioClassification;
import io.github.bioplethora.entity.ai.gecko.GeckoMeleeGoal;
import io.github.bioplethora.enums.BPEntityClasses;
import io.github.bioplethora.item.weapons.StellarScytheItem;
import io.github.bioplethora.registry.BPItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.SpawnGroupData;
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.EntityTeleportEvent;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType.EDefaultLoopTypes;

public class GaugalemEntity extends FloatingMonsterEntity implements IAnimatable, FlyingAnimal, IBioClassification {
    private final AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);

    public GaugalemEntity(EntityType<? extends Monster> type, Level world) {
        super(type, world);
        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(BPItems.STELLAR_SCYTHE.get()));
        this.moveControl = new GaugalemEntity.MoveHelperController(this);
    }

    public static AttributeSupplier.Builder setCustomAttributes() {
        return Mob.createLivingAttributes()
                .add(Attributes.ARMOR, 4 * BPConfig.COMMON.mobArmorMultiplier.get())
                .add(Attributes.ATTACK_SPEED, 1.5)
                .add(Attributes.ATTACK_DAMAGE, 7 * BPConfig.COMMON.mobMeeleeDamageMultiplier.get())
                .add(Attributes.ATTACK_KNOCKBACK, 0.5D)
                .add(Attributes.MAX_HEALTH, 40 * BPConfig.COMMON.mobHealthMultiplier.get())
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.1)
                .add(Attributes.MOVEMENT_SPEED, 0.5 * BPConfig.COMMON.mobMovementSpeedMultiplier.get())
                .add(Attributes.FOLLOW_RANGE, 32.0D)
                .add(Attributes.FLYING_SPEED, 1.5F);
    }

    @Override
    public BPEntityClasses getBioplethoraClass() {
        return BPEntityClasses.DANGERUM;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 24.0F));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomFlyingGoal(this, 1.2));
        this.goalSelector.addGoal(3, new GaugalemEntity.ChargeAttackGoal());
        this.goalSelector.addGoal(4, new GaugalemEntity.MoveRandomGoal());
        this.goalSelector.addGoal(1, new GeckoMeleeGoal<>(this, 40, 0.5, 0.6));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(5, new FloatGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        /*this.targetSelector.addGoal(2, new GaugalemTeleportToTargetGoal(this, null));*/
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this, GaugalemEntity.class).setAlertOthers());
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar data) {
        data.add(new AnimationController<>(this, "gaugalem_controller", 0, this::predicate));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.factory;
    }

    private <E extends IAnimatable>PlayState predicate(AnimationState<E> event) {
        if(this.getAttacking()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.gaugalem.attack", EDefaultLoopTypes.LOOP));
            return PlayState.CONTINUE;
        }

        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.gaugalem.idle", EDefaultLoopTypes.LOOP));
        return PlayState.CONTINUE;
    }

    public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficultyIn, MobSpawnType reason, @Nullable SpawnGroupData spawnDataIn, @Nullable CompoundTag dataTag) {
        if (worldIn instanceof ServerLevel && BPConfig.COMMON.hellMode.get()) {
            this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(10 * BPConfig.COMMON.mobMeeleeDamageMultiplier.get());
            this.getAttribute(Attributes.ARMOR).setBaseValue(6 * BPConfig.COMMON.mobArmorMultiplier.get());
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(33 * BPConfig.COMMON.mobHealthMultiplier.get());
            this.setHealth(33 * BPConfig.COMMON.mobHealthMultiplier.get());
        }
        return super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    public static boolean checkGaugalemSpawnRules(EntityType<GaugalemEntity> gaugalemEntityEntityType, ServerLevelAccessor pLevel, MobSpawnType pSpawnType, BlockPos pPos, RandomSource pRandom) {
        return pPos.getY() > 40 && pRandom.nextInt(5) == 1;
    }

    public boolean doHurtTarget(Entity entity) {
        boolean flag = super.doHurtTarget(entity);
        Level world = entity.level;
        if(flag && entity instanceof LivingEntity) {
            ((LivingEntity) entity).addEffect(new MobEffectInstance(MobEffects.WITHER, 100, 2));
            this.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, 100, 2));
            if (this.level instanceof ServerLevel) {
                ((ServerLevel) this.level).sendParticles(ParticleTypes.LARGE_SMOKE, this.getX(), this.getY(), this.getZ(), 20, 0.4, 0.4, 0.4, 0.1);
            }
        }

        if (!this.level.isClientSide() /*&& !(damageSource.getEntity() instanceof LivingEntity) */) {
            this.teleport();
        }

        if (this.getMainHandItem().getItem() instanceof SwordItem) {
            world.playSound(null, new BlockPos(entity.getX(), entity.getY(), entity.getZ()), SoundEvents.PLAYER_ATTACK_SWEEP, SoundSource.PLAYERS, 1, 1);
            if(!world.isClientSide) {
                world.addParticle(ParticleTypes.SWEEP_ATTACK, entity.getX(), entity.getY() + 2, entity.getZ(), 1, 1, 0.1);
            }

            if (this.getMainHandItem().getItem() instanceof StellarScytheItem) {

                double x = entity.getX(), y = entity.getY(), z = entity.getZ();

                if(world instanceof ServerLevel) {
                    for (Entity entityIterator : world.getEntitiesOfClass(Entity.class, new AABB(x - (10 / 2d), y, z - (10 / 2d), x + (10 / 2d), y + (10 / 2d), z + (10 / 2d)))) {
                        if (entityIterator instanceof LivingEntity && entityIterator != this) {
                            if (entityIterator != entity) {
                                entityIterator.hurt(DamageSource.mobAttack(this), ((StellarScytheItem) this.getMainHandItem().getItem()).getDamage() * 0.8F);
                            }
                        }
                    }
                }
            }
        }

        return flag;
    }

    @Override
    public void aiStep() {
        super.aiStep();

        if (this.level instanceof ServerLevel) {
            if (!this.hasEffect(MobEffects.INVISIBILITY)) {
                ((ServerLevel) this.level).sendParticles(ParticleTypes.LARGE_SMOKE, this.getX(), this.getY(), this.getZ(), 5, 0.4, 0.4, 0.4, 0.1);
            }
        }
    }

    public int getMaxSpawnClusterSize() {
        return 1;
    }

    public MobType getMobType() {
        return MobType.UNDEAD;
    }

    public boolean hurt(DamageSource damageSource, float p_70097_2_) {
        if (this.isInvulnerableTo(damageSource)) {
            return false;
        } else {
            this.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, 100, 2));
            if (this.level instanceof ServerLevel) {
                ((ServerLevel) this.level).sendParticles(ParticleTypes.LARGE_SMOKE, this.getX(), this.getY(), this.getZ(), 20, 0.4, 0.4, 0.4, 0.1);
            }

            if (!this.level.isClientSide() /*&& !(damageSource.getEntity() instanceof LivingEntity) */) {
                this.teleport();
            }

            return super.hurt(damageSource, p_70097_2_);
        }
    }

    public boolean teleport() {
        if (!this.level.isClientSide() && this.isAlive()) {
            double d0 = this.getX() + (this.random.nextDouble() - 0.5D) * 64.0D;
            double d1 = this.getY() + (double)(this.random.nextInt(64) - 32);
            double d2 = this.getZ() + (this.random.nextDouble() - 0.5D) * 64.0D;
            return this.teleport(d0, d1, d2);
        } else {
            return false;
        }
    }

    public boolean teleport(double p_70825_1_, double p_70825_3_, double p_70825_5_) {
        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos(p_70825_1_, p_70825_3_, p_70825_5_);

        while(mutable.getY() > 0 && !this.level.getBlockState(mutable).getMaterial().blocksMotion()) {
            mutable.move(Direction.DOWN);
        }

        BlockState blockstate = this.level.getBlockState(mutable);
        boolean flag = blockstate.getMaterial().blocksMotion();
        boolean flag1 = blockstate.getFluidState().is(FluidTags.WATER);
        if (flag && !flag1) {
            EntityTeleportEvent.EnderEntity event = net.minecraftforge.event.ForgeEventFactory.onEnderTeleport(this, p_70825_1_, p_70825_3_, p_70825_5_);
            if (event.isCanceled()) return false;
            boolean flag2 = this.randomTeleport(event.getTargetX(), event.getTargetY(), event.getTargetZ(), true);
            if (flag2 && !this.isSilent()) {
                this.level.playSound(null, this.xo, this.yo, this.zo, SoundEvents.ENDERMAN_TELEPORT, this.getSoundSource(), 1.0F, 1.0F);
                this.playSound(SoundEvents.ENDERMAN_TELEPORT, 1.0F, 1.0F);
            }

            return flag2;
        } else {
            return false;
        }
    }

    public boolean teleportTowards(Entity p_70816_1_) {
        Vec3 vector3d = new Vec3(this.getX() - p_70816_1_.getX(), this.getY(0.5D) - p_70816_1_.getEyeY(), this.getZ() - p_70816_1_.getZ());
        vector3d = vector3d.normalize();
        double d0 = 16.0D;
        double d1 = this.getX() + (this.random.nextDouble() - 0.5D) * 8.0D - vector3d.x * 16.0D;
        double d2 = this.getY() + (double)(this.random.nextInt(16) - 8) - vector3d.y * 16.0D;
        double d3 = this.getZ() + (this.random.nextDouble() - 0.5D) * 8.0D - vector3d.z * 16.0D;
        return this.teleport(d1, d2, d3);
    }

	@Override
	public boolean isFlying() {
		return false;
	}
}
