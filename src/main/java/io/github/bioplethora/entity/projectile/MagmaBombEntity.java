package io.github.bioplethora.entity.projectile;

import io.github.bioplethora.registry.BPEntities;
import io.github.bioplethora.registry.BPItems;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkHooks;

public class MagmaBombEntity extends ThrowableItemProjectile {

    public float explosionPower;

    public MagmaBombEntity(EntityType<? extends MagmaBombEntity> type, Level world) {
        super(type, world);
    }

    public MagmaBombEntity(Level world, LivingEntity entity) {
        super(BPEntities.MAGMA_BOMB.get(), entity, world);
    }

    public MagmaBombEntity(Level world, double v, double v1, double v2) {
        super(BPEntities.MAGMA_BOMB.get(), v, v1, v2, world);
    }

    protected Item getDefaultItem() {
        return BPItems.MAGMA_BOMB.get();
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    public void setExplosionPower(float explosionPower) {
        this.explosionPower = explosionPower;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level instanceof ServerLevel) {
            ((ServerLevel) this.level).sendParticles(ParticleTypes.LARGE_SMOKE, this.getX(), this.getY(), this.getZ(), 15, 0.4, 0.4, 0.4, 0);
        }
    }

    @OnlyIn(Dist.CLIENT)
    private ParticleOptions getParticle() {
        ItemStack itemstack = this.getItemRaw();
        return (itemstack.isEmpty() ? ParticleTypes.SMOKE : new ItemParticleOption(ParticleTypes.ITEM, itemstack));
    }

    @OnlyIn(Dist.CLIENT)
    public void handleEntityEvent(byte b) {
        if (b == 3) {
            ParticleOptions iparticledata = this.getParticle();

            for(int i = 0; i < 8; ++i) {
                this.level.addParticle(iparticledata, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
            }
        }
    }

    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        Entity entity = result.getEntity();
        entity.hurt(DamageSource.thrown(this, this.getOwner()), 3);
        this.level.explode(this, this.getX(), this.getY(0.0625D), this.getZ(), this.explosionPower, Explosion.BlockInteraction.BREAK);
        if (!this.level.isClientSide) {
            this.level.broadcastEntityEvent(this, (byte)3);
        }
        this.discard();
    }

    @Override
    protected void onHitBlock(BlockHitResult pResult) {
        super.onHitBlock(pResult);
        this.level.explode(this, this.getX(), this.getY(0.0625D), this.getZ(), this.explosionPower, Explosion.BlockInteraction.BREAK);
        if (!this.level.isClientSide) {
            this.level.broadcastEntityEvent(this, (byte)3);
            this.discard();
        }
    }

    public boolean isOnFire() {
        return true;
    }
}
