package io.github.bioplethora.entity.others;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.enums.BPEffectTypes;
import io.github.bioplethora.registry.BPEntities;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
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

public class BPEffectEntity extends Entity implements GeoEntity {
    private static final EntityDataAccessor<String> TYPE_ID = SynchedEntityData.defineId(BPEffectEntity.class, EntityDataSerializers.STRING);
    private static final EntityDataAccessor<Integer> FRAME_LEVEL = SynchedEntityData.defineId(BPEffectEntity.class, EntityDataSerializers.INT);
	private static final RawAnimation SPIN_ANIM = RawAnimation.begin().thenPlay("animation.bp_effect.spin");
    private final AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);
    private LivingEntity owner;
    public int lifespan;
    public int frameTimer;

    public BPEffectEntity(EntityType<?> pType, Level pLevel) {
        super(pType, pLevel);
        this.noCulling = true;
    }

    public BPEffectTypes getEffectType() {
        for (BPEffectTypes candids : BPEffectTypes.values()) {
            if (candids.getName().equals(this.getTypeID())) {
                return candids;
            }
        }
        return getDefaultEffect();
    }

    public void setEffectType(BPEffectTypes getEffectType) {
        this.setTypeID(getEffectType.getName());
    }

    public String getTypeID() {
        return this.entityData.get(TYPE_ID);
    }

    public void setTypeID(String value) {
        this.entityData.set(TYPE_ID, value);
    }

    public int getFrameLevel() {
        return this.entityData.get(FRAME_LEVEL);
    }

    public void setFrameLevel(int value) {
        this.entityData.set(FRAME_LEVEL, value);
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

    public static BPEffectEntity getEffectInstance(Entity owner, BPEffectTypes effectTypes) {
        BPEffectEntity slash = BPEntities.BP_EFFECT.get().create(owner.level);
        slash.setEffectType(effectTypes);
        if (owner instanceof LivingEntity) {
            slash.setOwner((LivingEntity) owner);
        }
        slash.moveTo(owner.getX(), owner.getY() - 0.25, owner.getZ());
        if (owner instanceof Mob) {
            slash.setYRot(((Mob) owner).yBodyRot);
            slash.yRotO = ((Mob) owner).yBodyRot;
        } else {
            slash.setYRot(owner.getYRot());
            slash.yRotO = owner.yRotO;
        }
        return slash;
    }

    public static void createInstance(Entity owner, BPEffectTypes effectTypes) {
        owner.level.addFreshEntity(getEffectInstance(owner, effectTypes));
    }

    private <E extends GeoEntity> PlayState predicate(AnimationState<E> event) {
        if (getEffectType() != null) {
            event.getController().setAnimation(RawAnimation.begin().thenLoop(getEffectType().getAnimation().getAnimationString()));
        } else {
            Bioplethora.LOGGER.info("EffectType for BPEffectEntity is null!");
            event.getController().setAnimation(SPIN_ANIM);
        }
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar data) {
        data.add(new AnimationController<>(this, "bp_effect_controller", 0, this::predicate));
    }

    @Override
    public void tick() {
        ++lifespan;
        if (lifespan >= this.getEffectType().getAnimation().getLifespan()) {
            this.setLifespan(0);
            this.discard();
        }
        ++frameTimer;
        if (frameTimer >= this.getEffectType().getFramesSpeed()) {
            this.setFrameLevel(this.getFrameLevel() == this.getEffectType().getFrames() ? 1 : this.getFrameLevel() + 1);
            frameTimer = 0;
        }
        super.tick();
        //this.moveTo(getOwner().getX(), getOwner().getY(), getOwner().getZ());
        if (this.getOwner() != null) {
        	this.setYRot(this.getOwner().getYRot());
        }
    }

    protected void defineSynchedData() {
        this.entityData.define(TYPE_ID, "none");
        this.entityData.define(FRAME_LEVEL, 1);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.factory;
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundNBT) {
        this.setLifespan(compoundNBT.getInt("lifespan"));
        this.setTypeID(compoundNBT.getString("typeId"));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundNBT) {
        compoundNBT.putInt("lifespan", this.lifespan);
        compoundNBT.putString("typeId", this.getTypeID());
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    public BPEffectTypes getDefaultEffect() {
        return BPEffectTypes.AERIAL_SHOCKWAVE;
    }
}
