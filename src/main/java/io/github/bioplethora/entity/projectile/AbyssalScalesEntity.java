package io.github.bioplethora.entity.projectile;

import io.github.bioplethora.registry.BPEntities;
import io.github.bioplethora.registry.BPItems;
import net.minecraft.network.protocol.Packet;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.network.NetworkHooks;

public class AbyssalScalesEntity extends ThrowableItemProjectile {

    public AbyssalScalesEntity(EntityType<? extends AbyssalScalesEntity> type, Level world) {
        super(type, world);
    }

    public AbyssalScalesEntity(Level world, LivingEntity entity) {
        super(BPEntities.ABYSSAL_SCALES.get(), entity, world);
    }

    public AbyssalScalesEntity(Level world, double v, double v1, double v2) {
        super(BPEntities.ABYSSAL_SCALES.get(), v, v1, v2, world);
    }

    @Override
    protected Item getDefaultItem() {
        return BPItems.ABYSSAL_SCALES.get();
    }

    @Override
    public void tick() {
        super.tick();
        if (this.isInWaterRainOrBubble()) {
            this.setDeltaMovement(this.getDeltaMovement().scale(1.5D));
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        Entity entity = result.getEntity();
        entity.hurt(DamageSource.thrown(this, this.getOwner()), 6);
        this.playSound(SoundEvents.GLASS_BREAK, 1.2F, 0.8F);
        if (entity instanceof LivingEntity && entity != this.getOwner()) {
            LivingEntity living = (LivingEntity) entity;
            if (!(this.getOwner() instanceof Player)) {
                living.invulnerableTime = 0;
            }
            living.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 200, 6));
            living.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 200, 3));
        }
        if (!this.level.isClientSide) {
            this.level.broadcastEntityEvent(this, (byte) 3);
            this.discard();
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult pResult) {
        super.onHitBlock(pResult);
        BlockState block = level.getBlockState(pResult.getBlockPos());

        this.playSound(SoundEvents.GLASS_BREAK, 1.2F, 0.8F);
        if (!block.is(BlockTags.DRAGON_IMMUNE)) {
            if (this.random.nextBoolean()) {
                level.setBlock(pResult.getBlockPos(), Blocks.ICE.defaultBlockState(), 2);
            } else {
                level.setBlock(pResult.getBlockPos(), Blocks.BLUE_ICE.defaultBlockState(), 2);
            }
        }
        if (!this.level.isClientSide) {
            this.level.broadcastEntityEvent(this, (byte)3);
            this.discard();
        }
    }

    public boolean isOnFire() {
        return false;
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
