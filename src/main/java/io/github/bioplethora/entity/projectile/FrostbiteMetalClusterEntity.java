package io.github.bioplethora.entity.projectile;

import io.github.bioplethora.config.BPConfig;
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
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
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

public class FrostbiteMetalClusterEntity extends AbstractHurtingProjectile implements IAnimatable {

    public double xPower;
    public double yPower;
    public double zPower;
    public int lifespan = 0;
    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);

    public FrostbiteMetalClusterEntity(EntityType<? extends AbstractHurtingProjectile> type, Level world) {
        super(type, world);
    }

    @OnlyIn(Dist.CLIENT)
    public FrostbiteMetalClusterEntity(Level world, double p_i1768_2_, double p_i1768_4_, double p_i1768_6_, double p_i1768_8_, double p_i1768_10_, double p_i1768_12_) {
        super(BPEntities.BELLOPHITE_CLUSTER.get(), p_i1768_2_, p_i1768_4_, p_i1768_6_, p_i1768_8_, p_i1768_10_, p_i1768_12_, world);
    }

    public FrostbiteMetalClusterEntity(Level world, LivingEntity p_i1769_2_, double p_i1769_3_, double p_i1769_5_, double p_i1769_7_) {
        super(BPEntities.BELLOPHITE_CLUSTER.get(), p_i1769_2_, p_i1769_3_, p_i1769_5_, p_i1769_7_, world);
    }


    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.frostbite_metal_cluster.main", EDefaultLoopTypes.LOOP));
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "frostbite_metal_cluster_controller", 0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    protected void onHit(HitResult result) {
        super.onHit(result);
        Entity entity = this.getOwner();
        if (entity != null && (result.getType() != HitResult.Type.ENTITY || !((EntityHitResult) result).getEntity().is(entity))) {
            this.hitAndExplode();
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {
        Entity entity = entityHitResult.getEntity();
        if (entityHitResult.getType() != HitResult.Type.ENTITY || !entityHitResult.getEntity().is(entity)) {

            if (this.getOwner() instanceof SummonableMonsterEntity && ((SummonableMonsterEntity) this.getOwner()).getOwner() != null) {
                if (entity != ((SummonableMonsterEntity) this.getOwner()).getOwner()) {
                    this.hitAndExplode();
                }
            }
        }
    }

    @Override
    public void tick() {
        super.tick();

        ++lifespan;
        if (lifespan == 100) {
            this.discard();
        }
    }

    public void hitAndExplode() {
        double x = this.getX(), y = this.getY(), z = this.getZ();
        BlockPos blockpos = new BlockPos((int) this.getX(), (int) this.getY(), (int) this.getZ());
        AABB area = new AABB(this.getX() - (7 / 2d), this.getY() - (7 / 2d), this.getZ() - (7 / 2d), this.getX() + (7 / 2d), this.getY() + (7 / 2d), this.getZ() + (7 / 2d));
        DamageSource castration = BPDamageSources.indirectCastration(this.getOwner(), this.getOwner());

        if (this.level instanceof ServerLevel) {
            ((ServerLevel) this.level).sendParticles(ParticleTypes.CLOUD, x, y, z, 20, 0.4, 0.4, 0.4, 0.1);
        }

        this.level.playSound(null, blockpos, SoundEvents.GLASS_BREAK, SoundSource.NEUTRAL, (float) 1, (float) 1);

        if (this.level instanceof ServerLevel && !(this.getOwner() == null)) {

            for (Entity entityArea : this.level.getEntitiesOfClass(Entity.class, area, null)) {
                if (entityArea instanceof LivingEntity && entityArea != this.getOwner()) {

                    if (this.getOwner() != null) {
                        //hell mode + berserk
                        if (((LivingEntity) this.getOwner()).getHealth() <= 100 && BPConfig.COMMON.hellMode.get()) {
                            entityArea.hurt(castration, (float) 10.5);
                            //berserk only
                        } else if (((LivingEntity) this.getOwner()).getHealth() <= 100 && !BPConfig.COMMON.hellMode.get()) {
                            entityArea.hurt(castration, (float) 7);
                            //default
                        } else {
                            entityArea.hurt(castration, (float) 3.5);
                        }
                    }

                    ((LivingEntity) entityArea).addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 2));
                    ((LivingEntity) entityArea).addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 60, 2));
                    ((LivingEntity) entityArea).addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 60, 1));
                }
            }
        }

        if (!this.level.isClientSide && this.getOwner() != null) {
            if (((LivingEntity) this.getOwner()).getHealth() <= 100 && BPConfig.COMMON.hellMode.get()) {
                this.level.explode(this, this.getX(), this.getY(0.0625D), this.getZ(), 3F, Explosion.BlockInteraction.BREAK);
            } else {
                this.level.explode(this, this.getX(), this.getY(0.0625D), this.getZ(), 1.5F, Explosion.BlockInteraction.BREAK);
            }
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
