package io.github.bioplethora.entity.projectile;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import io.github.bioplethora.entity.SummonableMonsterEntity;
import io.github.bioplethora.registry.BPDamageSources;
import io.github.bioplethora.registry.BPEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkHooks;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType.EDefaultLoopTypes;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class UltimateFrostbiteMetalClusterEntity extends AbstractHurtingProjectile implements IAnimatable {

    public double xPower;
    public double yPower;
    public double zPower;
    public int lifespan = 0;
    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);

    public UltimateFrostbiteMetalClusterEntity(EntityType<? extends AbstractHurtingProjectile> type, Level world) {
        super(type, world);
    }

    @OnlyIn(Dist.CLIENT)
    public UltimateFrostbiteMetalClusterEntity(Level world, double v, double v1, double v2, double v3, double v4, double v5) {
        super(BPEntities.ULTIMATE_BELLOPHITE_CLUSTER.get(), v, v1, v2, v3, v4, v5, world);
    }

    public UltimateFrostbiteMetalClusterEntity(Level world, LivingEntity entity, double v, double v1, double v2) {
        super(BPEntities.ULTIMATE_BELLOPHITE_CLUSTER.get(), entity, v, v1, v2, world);
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.frostbite_metal_cluster.main", EDefaultLoopTypes.LOOP));
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "ultimate_frostbite_metal_cluster_controller", 0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);
    }

    @Override
    protected void onHitBlock(BlockHitResult p_230299_1_) {
        super.onHitBlock(p_230299_1_);
        this.hitAndExplode();
    }

    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {
        Entity entity = entityHitResult.getEntity();
        if (entity instanceof Projectile) {
            if (((Projectile) entity).getOwner() != this.getOwner()) {
                this.hitAndExplode();
            }
        } else if (entity instanceof TamableAnimal) {
            if (((TamableAnimal) entity).getOwner() != this.getOwner()) {
                this.hitAndExplode();
            }
        } else if (entity instanceof SummonableMonsterEntity) {
            if (((SummonableMonsterEntity) entity).getOwner() != this.getOwner()) {
                this.hitAndExplode();
            }
        } else {
            this.hitAndExplode();
        }
    }

    public void tick() {
        super.tick();

        ++lifespan;
        if (lifespan == 100) {
            this.discard();
        }
    }

    public void hitAndExplode() {
        double x = this.getX(), y = this.getY(), z = this.getZ();
        BlockPos blockPos = new BlockPos(x, y, z);
        AABB area = new AABB(x - (7 / 2d), y, z - (7 / 2d), x + (7 / 2d), y + (7 / 2d), z + (7 / 2d));
        DamageSource castration = BPDamageSources.indirectCastration(this.getOwner(), this.getOwner());

        if (this.level instanceof ServerLevel) {
            ((ServerLevel) this.level).sendParticles(ParticleTypes.CLOUD, this.getX(), this.getY(), this.getZ(), 40, 0.6, 0.6, 0.6, 0.1);
        }

        this.level.playSound(null, blockPos, SoundEvents.GLASS_BREAK, SoundSource.NEUTRAL, (float) 1, (float) 1);

        if (this.level instanceof ServerLevel) {
            List<Entity> nearEntities = this.level
                    .getEntitiesOfClass(Entity.class, area)
                    .stream().sorted(new Object() {
                        Comparator<Entity> compareDistOf(double dx, double dy, double dz) {
                            return Comparator.comparing((getEnt -> getEnt.distanceToSqr(dx, dy, dz)));
                        }
                    }.compareDistOf(this.getX(), this.getY(), this.getZ())).collect(Collectors.toList());
            for (Entity entityArea : nearEntities) {
                if (entityArea instanceof LivingEntity && entityArea != this.getOwner()) {

                    entityArea.hurt(castration, (float) 15);

                    ((LivingEntity) entityArea).addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 3));
                    ((LivingEntity) entityArea).addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 100, 2));
                    ((LivingEntity) entityArea).addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 100, 1));
                }
            }
        }

        if (!this.level.isClientSide) {
            this.level.explode(this, x, this.getY(0.0625D), z, 3F, Explosion.BlockInteraction.BREAK);
            this.discard();
        }
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public boolean isNoGravity() {
        return true;
    }

    @Override
    public boolean shouldBurn() {
        return false;
    }

    @Override
    public boolean isOnFire() {
        return false;
    }

    @Override
    public boolean isPushedByFluid() {
        return false;
    }
}
