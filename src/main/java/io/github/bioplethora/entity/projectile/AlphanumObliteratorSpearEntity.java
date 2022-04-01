package io.github.bioplethora.entity.projectile;

import io.github.bioplethora.BPConfig;
import io.github.bioplethora.registry.BPDamageSources;
import io.github.bioplethora.registry.BPEntities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.DamagingProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class AlphanumObliteratorSpearEntity extends DamagingProjectileEntity implements IAnimatable {

    private final AnimationFactory factory = new AnimationFactory(this);
    public int lifespan = 0;
    public float baseDamage = BPConfig.getHellMode ? 15.0F : 12.0F;

    public AlphanumObliteratorSpearEntity(EntityType<? extends DamagingProjectileEntity> type, World world) {
        super(type, world);
    }

    @OnlyIn(Dist.CLIENT)
    public AlphanumObliteratorSpearEntity(World world, double p_i1768_2_, double p_i1768_4_, double p_i1768_6_, double p_i1768_8_, double p_i1768_10_, double p_i1768_12_) {
        super(BPEntities.ALPHANUM_OBLITERATOR_SPEAR.get(), p_i1768_2_, p_i1768_4_, p_i1768_6_, p_i1768_8_, p_i1768_10_, p_i1768_12_, world);
    }

    public AlphanumObliteratorSpearEntity(World world, LivingEntity entity, double v, double v1, double v2) {
        super(BPEntities.ALPHANUM_OBLITERATOR_SPEAR.get(), entity, v, v1, v2, world);
    }

    @Override
    public void tick() {
        super.tick();
        double x = this.getX(), y = this.getY(), z = this.getZ();
        if (this.level instanceof ServerWorld) {
            ((ServerWorld) this.level).sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE, x, y, z, 3, 0.1, 0.1, 0.1, 0.001);
        }

        ++lifespan;
        if (lifespan == 100) {
            this.remove();
        }
    }

    @Override
    protected void onHitEntity(EntityRayTraceResult entityHitResult) {
        super.onHitEntity(entityHitResult);

        Entity entity = entityHitResult.getEntity();

        if (entity instanceof ProjectileEntity) {
            if (!(((ProjectileEntity) entity).getOwner() == this.getOwner())) {
                this.hitAndExplode();
            }
        } else {
            if (entity != this.getOwner()) {
                this.hitAndExplode();
            }
        }
    }

    @Override
    protected void onHitBlock(BlockRayTraceResult p_230299_1_) {
        super.onHitBlock(p_230299_1_);
        this.hitAndExplode();
    }

    public float getBaseDamage() {
        return baseDamage;
    }

    public void setBaseDamage(float baseDamage) {
        this.baseDamage = baseDamage;
    }

    public void hitAndExplode() {
        double x = this.getX(), y = this.getY(), z = this.getZ();
        BlockPos blockpos = new BlockPos((int) this.getX(), (int) this.getY(), (int) this.getZ());
        DamageSource castration = BPDamageSources.indirectCastration(this.getOwner(), this.getOwner());

        if (this.level instanceof ServerWorld) {
            ((ServerWorld) this.level).sendParticles(ParticleTypes.CLOUD, x, y, z, 20, 0.75, 0.75, 0.75, 0.01);
        }

        if (this.level instanceof ServerWorld && !(this.getOwner() == null)) {

            for (LivingEntity entityArea : this.level.getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(5, 2, 5))) {
                if (entityArea != null && entityArea != this.getOwner()) {

                    entityArea.hurt(castration, getBaseDamage());
                    
                    if (BPConfig.getHellMode) {
                        entityArea.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 80, 2));
                        entityArea.addEffect(new EffectInstance(Effects.DIG_SLOWDOWN, 80, 1));
                        entityArea.addEffect(new EffectInstance(Effects.WEAKNESS, 80, 1));
                    } else {
                        entityArea.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 60, 1));
                        entityArea.addEffect(new EffectInstance(Effects.DIG_SLOWDOWN, 60, 1));
                        entityArea.addEffect(new EffectInstance(Effects.WEAKNESS, 40));
                    }
                }
            }
        }

        if (!this.level.isClientSide && this.getOwner() != null) {
            if (BPConfig.COMMON.hellMode.get()) {
                this.level.explode(this, this.getX(), this.getY(), this.getZ(), 3F, Explosion.Mode.BREAK);
            } else {
                this.level.explode(this, this.getX(), this.getY(), this.getZ(), 2F, Explosion.Mode.BREAK);
            }
            this.remove();
        }
    }

    @Override
    public IPacket<?> getAddEntityPacket() {
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
        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.alphanum_obliterator.idle", true));
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
