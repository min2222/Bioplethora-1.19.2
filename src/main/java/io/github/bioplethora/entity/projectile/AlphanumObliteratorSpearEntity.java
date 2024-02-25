package io.github.bioplethora.entity.projectile;

import io.github.bioplethora.config.BPConfig;
import io.github.bioplethora.registry.BPDamageSources;
import io.github.bioplethora.registry.BPEntities;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
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

public class AlphanumObliteratorSpearEntity extends AbstractHurtingProjectile implements IAnimatable {

    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);
    public int lifespan = 0;
    public float baseDamage = BPConfig.IN_HELLMODE ? 15.0F : 12.0F;

    public AlphanumObliteratorSpearEntity(EntityType<? extends AbstractHurtingProjectile> type, Level world) {
        super(type, world);
    }

    @OnlyIn(Dist.CLIENT)
    public AlphanumObliteratorSpearEntity(Level world, double p_i1768_2_, double p_i1768_4_, double p_i1768_6_, double p_i1768_8_, double p_i1768_10_, double p_i1768_12_) {
        super(BPEntities.ALPHANUM_OBLITERATOR_SPEAR.get(), p_i1768_2_, p_i1768_4_, p_i1768_6_, p_i1768_8_, p_i1768_10_, p_i1768_12_, world);
    }

    public AlphanumObliteratorSpearEntity(Level world, LivingEntity entity, double v, double v1, double v2) {
        super(BPEntities.ALPHANUM_OBLITERATOR_SPEAR.get(), entity, v, v1, v2, world);
    }

    @Override
    public void tick() {
        super.tick();
        double x = this.getX(), y = this.getY(), z = this.getZ();
        if (this.level instanceof ServerLevel) {
            ((ServerLevel) this.level).sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE, x, y, z, 3, 0.1, 0.1, 0.1, 0.001);
        }

        ++lifespan;
        if (lifespan == 100) {
            this.discard();
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {
        super.onHitEntity(entityHitResult);

        Entity entity = entityHitResult.getEntity();

        if (entity instanceof Projectile) {
            if (!(((Projectile) entity).getOwner() == this.getOwner())) {
                this.hitAndExplode();
            }
        } else {
            if (entity != this.getOwner()) {
                this.hitAndExplode();
            }
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult p_230299_1_) {
        super.onHitBlock(p_230299_1_);
        this.hitAndExplode();
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        return false;
    }

    public float getBaseDamage() {
        return baseDamage;
    }

    public void setBaseDamage(float baseDamage) {
        this.baseDamage = baseDamage;
    }

    public void hitAndExplode() {
        double x = this.getX(), y = this.getY(), z = this.getZ();
        DamageSource castration = BPDamageSources.indirectCastration(this.getOwner(), this.getOwner());

        if (this.level instanceof ServerLevel) {
            ((ServerLevel) this.level).sendParticles(ParticleTypes.CLOUD, x, y, z, 20, 0.75, 0.75, 0.75, 0.01);
        }

        if (this.level instanceof ServerLevel && !(this.getOwner() == null)) {

            for (LivingEntity entityArea : this.level.getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(5, 2, 5))) {
                if (entityArea != null && entityArea != this.getOwner()) {

                    entityArea.hurt(castration, getBaseDamage());
                    
                    if (BPConfig.IN_HELLMODE) {
                        entityArea.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 80, 2));
                        entityArea.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 80, 1));
                        entityArea.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 80, 1));
                    } else {
                        entityArea.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 1));
                        entityArea.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 60, 1));
                        entityArea.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 40));
                    }
                }
            }
        }

        if (!this.level.isClientSide && this.getOwner() != null) {
            if (BPConfig.COMMON.hellMode.get()) {
                this.level.explode(this, this.getX(), this.getY(), this.getZ(), 3F, Explosion.BlockInteraction.BREAK);
            } else {
                this.level.explode(this, this.getX(), this.getY(), this.getZ(), 2F, Explosion.BlockInteraction.BREAK);
            }
            this.discard();
        }
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public boolean isOnFire() {
        return false;
    }

    @Override
    public boolean isNoGravity() {
        return false;
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.alphanum_obliterator_spear.idle", EDefaultLoopTypes.LOOP));
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "alphanum_obliterator_controller", 0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }
}
