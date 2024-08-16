package io.github.bioplethora.entity.others;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.network.NetworkHooks;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class FrostbiteMetalShieldWaveEntity extends Entity implements GeoEntity {

	private static final RawAnimation IDLE_ANIM = RawAnimation.begin().thenPlay("animation.frostbite_metal_shield_wave.default");
    private final AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);
    private LivingEntity owner;
    public int lifespan;

    public FrostbiteMetalShieldWaveEntity(EntityType<?> entityType, Level world) {
        super(entityType, world);
        this.lifespan = 0;
    }

    public LivingEntity getOwner() {
        return this.owner;
    }

    public void setOwner(LivingEntity livingEntity) {
        this.owner = livingEntity;
    }

    public void setLifespan(int value) {
        lifespan = value;
    }

    private <E extends GeoEntity> PlayState predicate(AnimationState<E> event) {

        event.getController().setAnimation(IDLE_ANIM);
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar data) {
        data.add(new AnimationController<>(this, "frostbite_metal_shield_wave_controller", 0, this::predicate));
    }

    public void tick() {
        super.tick();

        if (this.getOwner() != null) {
            double x = this.getOwner().getX(), y = this.getOwner().getY(), z = this.getOwner().getZ();
            AABB area = new AABB(x - (10 / 2d), y, z - (10 / 2d), x + (10 / 2d), y + (10 / 2d), z + (10 / 2d));
            BlockPos pos = BlockPos.containing(x, y + 1, z);
            Level world = this.level;

            ++lifespan;
            this.moveTo(pos, 0.0F, 0.0F);
            for (Entity entityIterator : world.getEntitiesOfClass(Entity.class, area)) {
                if (entityIterator instanceof LivingEntity && entityIterator != this.getOwner()) {
                    entityIterator.hurt(this.damageSources().indirectMagic(this.getOwner(), this.getOwner()), 1F);
                }
            }
        }

        if (this.lifespan == 40) {
            this.discard();
        }
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.factory;
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compoundNBT) {
        this.setLifespan(compoundNBT.getInt("lifespan"));
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compoundNBT) {
        compoundNBT.putInt("lifespan", this.lifespan);
    }

    @Override
    protected void defineSynchedData() {
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
