package io.github.bioplethora.entity.projectile;

import io.github.bioplethora.config.BPConfig;
import io.github.bioplethora.registry.BPEntities;
import io.github.bioplethora.registry.BPItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.network.NetworkHooks;

public class FrostbiteMetalArrowEntity extends AbstractArrow {

    /*private double baseDamage = 12.0D;*/
    private int duration = 200;

    public FrostbiteMetalArrowEntity(EntityType<? extends FrostbiteMetalArrowEntity> type, Level worldIn) {
        super(type, worldIn);
    }

    public FrostbiteMetalArrowEntity(Level worldIn, LivingEntity shooter) {
        super(BPEntities.BELLOPHITE_ARROW.get(), shooter, worldIn);
    }

    public FrostbiteMetalArrowEntity(Level worldIn, double x, double y, double z) {
        super(BPEntities.BELLOPHITE_ARROW.get(), x, y, z, worldIn);
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }


    public void tick() {
        super.tick();
        if (this.level instanceof ServerLevel) {
            if (!this.inGround) {
                ((ServerLevel) this.level).sendParticles(ParticleTypes.CLOUD, this.getX(), this.getY(), this.getZ(), (int) 2, 0.2, 0.2, 0.2, 0.01);
            }
        }
    }

    public void onHitEntity(EntityHitResult entityHitResult) {
        super.onHitEntity(entityHitResult);

        this.projectileHit();
    }

    @Override
    protected void onHitBlock(BlockHitResult blockHitResult) {
        super.onHitBlock(blockHitResult);

        this.projectileHit();
    }

    public void projectileHit() {
        double x = this.getX(), y = this.getY(), z = this.getZ();
        BlockPos pos = new BlockPos((int) this.getX(), (int) this.getY(), (int) this.getZ());
        AABB area = new AABB(this.getX() - (5 / 2d), this.getY() - (5 / 2d), this.getZ() - (5 / 2d), this.getX() + (5 / 2d), this.getY() + (5 / 2d), this.getZ() + (5 / 2d));

        if (this.level instanceof ServerLevel) {
            ((ServerLevel) this.level).sendParticles(ParticleTypes.CLOUD, x, y, z, 20, 0.4, 0.4, 0.4, 0.1);
        }

        this.level.playSound(null, pos, SoundEvents.GLASS_BREAK, SoundSource.NEUTRAL, (float) 1, (float) 1);

        if(this.level instanceof ServerLevel) {
            for (Entity entityIterator : this.level.getEntitiesOfClass(Entity.class, area)) {
                if (entityIterator instanceof LivingEntity && entityIterator != this.getOwner()) {
                    if (this.getOwner() != null) {
                        entityIterator.hurt(this.damageSources().indirectMagic(this.getOwner(), this.getOwner()), (float) 10.5);
                    } else {
                        entityIterator.hurt(this.damageSources().magic(), (float) 10.5);
                    }
                    ((LivingEntity) entityIterator).addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 2));
                    ((LivingEntity) entityIterator).addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 60, 2));
                    ((LivingEntity) entityIterator).addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 60, 1));
                }
            }
        }
    }

    @Override
    public double getBaseDamage() {
        return BPConfig.IN_HELLMODE ? 7.0D : 9.5D;
    }

    @Override
    protected ItemStack getPickupItem() {
        return new ItemStack(BPItems.BELLOPHITE_ARROW.get());
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
