package io.github.bioplethora.entity.projectile;

import io.github.bioplethora.registry.BPDamageSources;
import io.github.bioplethora.registry.BPEntities;
import io.github.bioplethora.registry.BPItems;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.network.NetworkHooks;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class VermilionBladeProjectileEntity extends AbstractHurtingProjectile implements IAnimatable, ItemSupplier {

    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);
    public double lifespan = 0;
    public int bladeSize = 1;

    public VermilionBladeProjectileEntity(EntityType<? extends AbstractHurtingProjectile> entityType, Level world) {
        super(entityType, world);
    }

    public VermilionBladeProjectileEntity(Level world, LivingEntity entity, double v, double v1, double v2) {
        super(BPEntities.VERMILION_BLADE_PROJECTILE.get(), entity, v, v1, v2, world);
    }

    @Override
    public void registerControllers(AnimationData data) {

    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

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

        entity.hurt(BPDamageSources.helioSlashed(this.getOwner(), this.getOwner()), 5 * ((float) this.bladeSize * 0.75F));
        this.discard();
    }

    @Override
    protected void onHit(HitResult result) {
        double x = this.getX(), y = this.getY(), z = this.getZ();
        Entity owner = this.getOwner();
        super.onHit(result);

        if (result.getType() != HitResult.Type.ENTITY || !((EntityHitResult) result).getEntity().is(owner)) {
            this.level.explode(this, x, y, z, 1.5F * ((float) this.bladeSize * 0.5F), Explosion.BlockInteraction.BREAK);
            this.discard();
        }
    }

    public void setBladeSize(int value) {
        this.bladeSize = value;
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    protected ParticleOptions getTrailParticle() {
        return ParticleTypes.SMOKE;
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

    @Override
    public ItemStack getItem() {
        return new ItemStack(BPItems.VERMILION_BLADE.get());
    }
}
