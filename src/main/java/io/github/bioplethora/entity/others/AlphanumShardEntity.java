package io.github.bioplethora.entity.others;

import io.github.bioplethora.entity.creatures.AlphemEntity;
import io.github.bioplethora.registry.BPEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class AlphanumShardEntity extends Entity implements GeoEntity {

	private static final RawAnimation IDLE_ANIM = RawAnimation.begin().thenPlay("animation.alphanum_shard.idle");
    private final AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);
    public LivingEntity owner;
    public LivingEntity target;
    public int timeBeforeExpire;

    public AlphanumShardEntity(EntityType<?> entityType, Level world) {
        super(entityType, world);
    }

    public void tick() {
        super.tick();

        ++timeBeforeExpire;
        if (timeBeforeExpire >= 40) {
            this.level.explode(this, getX(), getY(), getZ(), 0.5F, Level.ExplosionInteraction.NONE);
            if (!this.level.isClientSide()) {
                ((ServerLevel) this.level).sendParticles(ParticleTypes.FIREWORK, getX(), getY(), getZ(), 45, 0.45, 0.45, 0.45, 0.01);

                ServerLevel serverworld = (ServerLevel)this.level;
                BlockPos blockpos = this.blockPosition();
                AlphemEntity alphem = BPEntities.ALPHEM.get().create(this.level);

                alphem.setHasLimitedLife(true);
                alphem.setExplodeOnExpiry(false);
                alphem.setLifeLimitBeforeDeath(200);

                alphem.finalizeSpawn(serverworld, this.level.getCurrentDifficultyAt(blockpos), MobSpawnType.MOB_SUMMONED, null, null);
                alphem.moveTo(blockpos, 0.0F, 0.0F);
                if (this.target != null) {
                    alphem.setTarget(this.getTarget());
                }
                if (this.owner != null) {
                    alphem.setOwner(this.getOwner());
                }

                if (Math.random() < 0.10) {
                    this.level.addFreshEntity(alphem);
                }
            }
            this.discard();
        }
    }

    public void setOwner(LivingEntity owner) {
        this.owner = owner;
    }

    public LivingEntity getOwner() {
        return owner;
    }

    public void setTarget(LivingEntity target) {
        this.target = target;
    }

    public LivingEntity getTarget() {
        return target;
    }

    private <E extends GeoEntity> PlayState predicate(AnimationState<E> event) {

        event.getController().setAnimation(IDLE_ANIM);
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar data) {
        data.add(new AnimationController<>(this, "alphanum_shard_controller", 0, this::predicate));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.factory;
    }

    @Override
    protected void defineSynchedData() {
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag p_70037_1_) {
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag p_213281_1_) {
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
