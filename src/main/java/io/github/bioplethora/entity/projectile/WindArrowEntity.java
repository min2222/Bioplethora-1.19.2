package io.github.bioplethora.entity.projectile;

import static io.github.bioplethora.api.extras.MathUtils.angleBetween;
import static io.github.bioplethora.api.extras.MathUtils.clampAbs;
import static io.github.bioplethora.api.extras.MathUtils.transform;
import static io.github.bioplethora.api.extras.MathUtils.wrap180Radian;

import java.awt.Color;
import java.util.Comparator;
import java.util.List;

import io.github.bioplethora.api.world.EntityUtils;
import io.github.bioplethora.config.BPConfig;
import io.github.bioplethora.particles.WindPoofParticleData;
import io.github.bioplethora.registry.BPEntities;
import io.github.bioplethora.registry.BPItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;

/**
 * @credits
 * Copyright (c) 2020 Sin Tachikawa
 * ProjectE - https://www.curseforge.com/minecraft/mc-mods/projecte
 */
public class WindArrowEntity extends AbstractArrow {

    public static final EntityDataAccessor<Integer> TARGET_ID = SynchedEntityData.defineId(WindArrowEntity.class, EntityDataSerializers.INT);
    public static final int NULL_TARGET_INT = -1;
    public int relocateCD = 0;

    private int duration = 200;

    public WindArrowEntity(EntityType<? extends WindArrowEntity> type, Level worldIn) {
        super(type, worldIn);
    }

    public WindArrowEntity(Level worldIn, LivingEntity shooter) {
        super(BPEntities.WIND_ARROW.get(), shooter, worldIn);
    }

    public WindArrowEntity(Level worldIn, double x, double y, double z) {
        super(BPEntities.WIND_ARROW.get(), x, y, z, worldIn);
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    public void tick() {
        super.tick();

        if (!this.inGround) {
            double px = getDeltaMovement().x() / 10, py = getDeltaMovement().y() / 10, pz = getDeltaMovement().z() / 10;

            for (int i = 0; i < 10; i += 1) {
                Color color = new Color(1.0f, 1.0f, 1.0f);
                WindPoofParticleData wPoof = new WindPoofParticleData(color, 1);
                this.level.addParticle(wPoof, getX() + (px * i), getY() + (py * i), getZ() + (pz * i), 0d, 0d, 0d);
            }
        }

        ++duration;
        int maxDuration = 200;
        if (this.duration == maxDuration) {
            this.projectileHit();
            this.discard();
        }

        if (!level.isClientSide && this.tickCount > 3) {
            if (hasTarget() && (!getTarget().isAlive() || this.inGround)) {
                entityData.set(TARGET_ID, NULL_TARGET_INT);
            }

            if (!hasTarget() && !this.inGround && relocateCD <= 0) {
                locateAnotherTarget();
            } else {
                relocateCD--;
            }
        }

        if (tickCount > 3 && hasTarget() && !this.inGround) {
            double deltaX = getDeltaMovement().x(), deltaY = getDeltaMovement().y(), deltaZ = getDeltaMovement().z();
            Entity target = getTarget();

            Vec3 arrowLoc = new Vec3(getX(), getY(), getZ());
            Vec3 targetLoc = new Vec3(target.getX(), target.getY() + target.getBbHeight() / 2, target.getZ());
            Vec3 lookVec = targetLoc.subtract(arrowLoc);
            Vec3 arrowMotion = new Vec3(deltaX, deltaY, deltaZ);

            double radian = wrap180Radian(angleBetween(arrowMotion, lookVec));
            radian = clampAbs(radian, Math.PI / 2);

            Vec3 crossProduct = arrowMotion.cross(lookVec).normalize();
            Vec3 adjustedLookVec = transform(crossProduct, radian, arrowMotion);
            shoot(adjustedLookVec.x, adjustedLookVec.y, adjustedLookVec.z, 1.0F, 0);
        }

    }

    public void locateAnotherTarget() {
        double targetRadius = BPConfig.COMMON.hellMode.get() ? 13.0D : 10.0D;
        List<Mob> candidates = level.getEntitiesOfClass(Mob.class, this.getBoundingBox().inflate(targetRadius, targetRadius, targetRadius));

        if (!candidates.isEmpty()) {
            if (isValidTarget(candidates.get(0))) {
                candidates.sort(Comparator.comparing(WindArrowEntity.this::distanceToSqr, Double::compare));
                entityData.set(TARGET_ID, candidates.get(0).getId());
            }
        }

        relocateCD = 5;
    }

    @Override
    public void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(TARGET_ID, NULL_TARGET_INT);
    }

    private Mob getTarget() {
        return (Mob) level.getEntity(entityData.get(TARGET_ID));
    }

    private boolean hasTarget() {
        return getTarget() != null;
    }

    public boolean isValidTarget(LivingEntity entity) {
        return EntityUtils.IsNotPet(this.getOwner()).test(entity);
    }

    public void onHitEntity(EntityHitResult entityHitResult) {
        super.onHitEntity(entityHitResult);

        this.projectileHit();
    }

    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);

        this.projectileHit();
    }

    public void projectileHit() {
        BlockPos pos = new BlockPos((int) this.getX(), (int) this.getY(), (int) this.getZ());
        AABB area = new AABB(this.getX() - (5 / 2d), this.getY() - (5 / 2d), this.getZ() - (5 / 2d), this.getX() + (5 / 2d), this.getY() + (5 / 2d), this.getZ() + (5 / 2d));

        this.level.playSound(null, pos, SoundEvents.PLAYER_ATTACK_SWEEP, SoundSource.NEUTRAL, (float) 1, (float) 1);

        if (this.level instanceof ServerLevel) {
            for (LivingEntity eI : this.level.getEntitiesOfClass(LivingEntity.class, area)) {
                if (eI != null && eI != this.getOwner()) {

                    if (this.getOwner() != null) {
                        eI.hurt(this.damageSources().mobProjectile(this.getOwner(), (LivingEntity) this.getOwner()), BPConfig.IN_HELLMODE ? 3 : 5);
                    }

                    eI.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 2));
                    eI.invulnerableTime = 0;
                    ((ServerLevel) this.level).sendParticles(ParticleTypes.SWEEP_ATTACK, eI.getX(), eI.getY() + 1.5, eI.getZ(), 1, 0.1, 0.1, 0.1, 0);
                }
            }
        }
    }

    @Override
    public double getBaseDamage() {
        return BPConfig.IN_HELLMODE ? 3.0D : 5.5D;
    }

    @Override
    protected ItemStack getPickupItem() {
        return new ItemStack(BPItems.WIND_ARROW.get());
    }

    public void readAdditionalSaveData(CompoundTag compoundNBT) {
        super.readAdditionalSaveData(compoundNBT);
        if (compoundNBT.contains("Duration")) {
            this.duration = compoundNBT.getInt("Duration");
        }
    }

    public void addAdditionalSaveData(CompoundTag compoundNBT) {
        super.addAdditionalSaveData(compoundNBT);
        compoundNBT.putInt("Duration", this.duration);
    }

    @Override
    protected float getWaterInertia() {
        return 1F;
    }
}
