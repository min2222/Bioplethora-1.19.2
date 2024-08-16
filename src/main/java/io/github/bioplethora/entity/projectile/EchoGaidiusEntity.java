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
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

public class EchoGaidiusEntity extends GaidiusBaseEntity {

    public static final EntityDataAccessor<Integer> TARGET_ID = SynchedEntityData.defineId(EchoGaidiusEntity.class, EntityDataSerializers.INT);
    public static final int NULL_TARGET_INT = -1;
    public int relocateCD = 0;

    public EchoGaidiusEntity(EntityType<? extends EchoGaidiusEntity> type, Level world) {
        super(type, world);
    }

    public EchoGaidiusEntity(Level world, LivingEntity entity) {
        super(BPEntities.ECHO_GAIDIUS.get(), entity, world);
    }

    public EchoGaidiusEntity(Level world, double v, double v1, double v2) {
        super(BPEntities.ECHO_GAIDIUS.get(), world, v, v1, v2);
    }

    @Override
    public int getProjectileDamage(EntityHitResult result) {
        return isCrit() ? 7 : 4;
    }

    @Override
    protected Item getDefaultItem() {
        return BPItems.ECHO_GAIDIUS.get();
    }

    @Override
    public void tick() {
        super.tick();

        if (!this.onGround()) {
            double px = getDeltaMovement().x() / 10, py = getDeltaMovement().y() / 10, pz = getDeltaMovement().z() / 10;

            for (int i = 0; i < 10; i += 1) {
                Color color = new Color(1.0f, 1.0f, 1.0f);
                WindPoofParticleData wPoof = new WindPoofParticleData(color, 1);
                this.level.addParticle(wPoof, getX() + (px * i), getY(0.35) + (py * i), getZ() + (pz * i), 0d, 0d, 0d);
            }
        }

        if (!level.isClientSide && this.tickCount > 3) {
            if (hasTarget() && (!getTarget().isAlive() || this.onGround())) {
                entityData.set(TARGET_ID, NULL_TARGET_INT);
            }

            if (!hasTarget() && !this.onGround() && relocateCD <= 0) {
                locateAnotherTarget();
            } else {
                relocateCD--;
            }
        }

        if (tickCount > 3 && hasTarget() && !this.onGround()) {
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
                candidates.sort(Comparator.comparing(EchoGaidiusEntity.this::distanceToSqr, Double::compare));
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

        if (entityHitResult.getEntity() instanceof LivingEntity) {
            LivingEntity entity = (LivingEntity) entityHitResult.getEntity();
            entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 3));
            if (random.nextInt(10) == 1) {
                entity.invulnerableTime = 0;
            }
        }
        this.projectileHit();
    }

    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);

        this.projectileHit();
    }

    public void projectileHit() {
        this.discard();
    }
}
