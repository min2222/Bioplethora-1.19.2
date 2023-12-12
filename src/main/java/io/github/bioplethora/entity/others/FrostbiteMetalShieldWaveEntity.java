package io.github.bioplethora.entity.others;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.network.NetworkHooks;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType.EDefaultLoopTypes;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class FrostbiteMetalShieldWaveEntity extends Entity implements IAnimatable {

    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);
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

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {

        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.frostbite_metal_shield_wave.default", EDefaultLoopTypes.LOOP));
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "frostbite_metal_shield_wave_controller", 0, this::predicate));
    }

    public void tick() {
        super.tick();

        if (this.getOwner() != null) {
            double x = this.getOwner().getX(), y = this.getOwner().getY(), z = this.getOwner().getZ();
            AABB area = new AABB(x - (10 / 2d), y, z - (10 / 2d), x + (10 / 2d), y + (10 / 2d), z + (10 / 2d));
            BlockPos pos = new BlockPos(x, y + 1, z);
            Level world = this.level;

            ++lifespan;
            this.moveTo(pos, 0.0F, 0.0F);
            for (Entity entityIterator : world.getEntitiesOfClass(Entity.class, area, null)) {
                if (entityIterator instanceof LivingEntity && entityIterator != this.getOwner()) {
                    entityIterator.hurt(DamageSource.indirectMagic(this.getOwner(), this.getOwner()), 1F);
                }
            }
        }

        if (this.lifespan == 40) {
            this.discard();
        }
    }

    @Override
    public AnimationFactory getFactory() {
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
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
