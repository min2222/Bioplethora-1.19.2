package io.github.bioplethora.entity.projectile;

import io.github.bioplethora.registry.BPEnchantments;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;

public abstract class GaidiusBaseEntity extends ThrowableItemProjectile {
    public boolean isCrit;

    public GaidiusBaseEntity(EntityType<? extends ThrowableItemProjectile> type, Level world) {
        super(type, world);
    }

    public GaidiusBaseEntity(EntityType<? extends ThrowableItemProjectile> type, LivingEntity entity, Level world) {
        super(type, entity, world);
    }

    public GaidiusBaseEntity(EntityType<? extends ThrowableItemProjectile> type, Level world, double v, double v1, double v2) {
        super(type, v, v1, v2, world);
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        Entity entity = result.getEntity();
        entity.hurt(entity.damageSources().thrown(this, this.getOwner()), getProjectileDamage(result)
                + (EnchantmentHelper.getItemEnchantmentLevel(BPEnchantments.RACKING_EDGE.get(), getItem()) * 1.5F)
        );
    }

    @Override
    public void tick() {
        super.tick();

        Vec3 vector3d = this.getDeltaMovement();
        double d3 = vector3d.x;
        double d4 = vector3d.y;
        double d0 = vector3d.z;
        if (this.isCrit()) {
            for(int i = 0; i < 4; ++i) {
                this.level.addParticle(ParticleTypes.CRIT, this.getX() + d3 * (double)i / 4.0D, this.getY() + d4 * (double)i / 4.0D, this.getZ() + d0 * (double)i / 4.0D, -d3, -d4 + 0.2D, -d0);
            }
        }
    }

    public void setCrit(boolean crit) {
        isCrit = crit;
    }

    public boolean isCrit() {
        return isCrit;
    }

    public boolean isOnFire() {
        return false;
    }

    public abstract int getProjectileDamage(EntityHitResult result);

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
