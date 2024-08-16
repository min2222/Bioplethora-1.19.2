package io.github.bioplethora.entity.projectile;

import javax.annotation.Nonnull;

import io.github.bioplethora.config.BPConfig;
import io.github.bioplethora.registry.BPEntities;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkHooks;

public class WindblazeEntity extends AbstractHurtingProjectile {

    public double lifespan = 0;

    public WindblazeEntity(EntityType<? extends AbstractHurtingProjectile> entityType, Level world) {
        super(entityType, world);
    }

    @OnlyIn(Dist.CLIENT)
    public WindblazeEntity(Level world, double p_i1768_2_, double p_i1768_4_, double p_i1768_6_, double p_i1768_8_, double p_i1768_10_, double p_i1768_12_) {
        super(BPEntities.WINDBLAZE.get(), p_i1768_2_, p_i1768_4_, p_i1768_6_, p_i1768_8_, p_i1768_10_, p_i1768_12_, world);
    }

    public WindblazeEntity(Level world, LivingEntity entity, double v, double v1, double v2) {
        super(BPEntities.WINDBLAZE.get(), entity, v, v1, v2, world);
    }


    @Override
    public void tick() {
        super.tick();

        ++lifespan;
        if (lifespan == 100) {
            this.discard();
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {
        Entity entity = entityHitResult.getEntity();
        AABB entArea = new AABB(this.getX() - (3 / 2d), this.getY() - (3 / 2d), this.getZ() - (3 / 2d), this.getX() + (3 / 2d), this.getY() + (3 / 2d), this.getZ() + (3 / 2d));

        if (this.level instanceof ServerLevel && this.getOwner() instanceof Mob) {
            if (((Mob) this.getOwner()).getTarget() != null) {

                for (LivingEntity entityArea : this.level.getEntitiesOfClass(LivingEntity.class, entArea)) {

                    if ((entityArea == ((Mob) this.getOwner()).getTarget())) {
                        hurtEntityArea(entityArea);
                    }

                    if (entityArea instanceof Mob) {
                        if (((Mob) entityArea).getTarget() == this.getOwner()) {
                            hurtEntityArea(entityArea);
                        }
                    }
                }
            }
        }
        level.playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.PLAYER_ATTACK_SWEEP, SoundSource.HOSTILE, 1.0F, 1.0F / (random.nextFloat() * 0.4F + 1.2F) + 0.5F);
    }

    public void hurtEntityArea(LivingEntity entityArea) {

        entityArea.setDeltaMovement(entityArea.getDeltaMovement().add(0, 0.3 - entityArea.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE), 0));

        if (this.getOwner() != null) {
            if (BPConfig.COMMON.hellMode.get()) {
                entityArea.hurt(this.damageSources().indirectMagic(this.getOwner(), this.getOwner()), 3);
                entityArea.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 1));
                entityArea.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 60));
            } else {
                entityArea.hurt(this.damageSources().indirectMagic(this.getOwner(), this.getOwner()), 1);
                entityArea.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60));
            }
        }
    }

    @Nonnull
    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    protected ParticleOptions getTrailParticle() {
        return ParticleTypes.CLOUD;
    }

    public boolean hurt(DamageSource damageSource, float v) {
        return false;
    }

    public boolean isPickable() {
        return false;
    }

    @Override
    public boolean isNoGravity() {
        return true;
    }

    protected boolean shouldBurn() {
        return false;
    }
}
