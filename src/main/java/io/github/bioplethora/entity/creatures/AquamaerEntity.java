package io.github.bioplethora.entity.creatures;

import javax.annotation.Nullable;

import io.github.bioplethora.entity.BPAirWaterLandEntity;
import io.github.bioplethora.entity.IBioClassification;
import io.github.bioplethora.entity.ISaddleable;
import io.github.bioplethora.entity.IVerticalMount;
import io.github.bioplethora.enums.BPEntityClasses;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.ItemSteerable;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.DismountHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType.EDefaultLoopTypes;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class AquamaerEntity extends BPAirWaterLandEntity implements IAnimatable, IBioClassification, ItemSteerable, IVerticalMount, ISaddleable {

    private static final EntityDataAccessor<Boolean> HAS_SADDLE = SynchedEntityData.defineId(TrapjawEntity.class, EntityDataSerializers.BOOLEAN);
    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);

    public AquamaerEntity(EntityType<? extends TamableAnimal> type, Level worldIn) {
        super(type, worldIn);
        this.setTame(false);
        this.noCulling = true;
    }

    @Override
    public BPEntityClasses getBioplethoraClass() {
        return BPEntityClasses.ECOHARMLESS;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel p_241840_1_, AgeableMob p_241840_2_) {
        return null;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(HAS_SADDLE, false);
    }

    public boolean isSaddled() {
        return this.entityData.get(HAS_SADDLE);
    }

    public void setSaddled(boolean saddled) {
        this.entityData.set(HAS_SADDLE, saddled);
    }

    @Override
    public boolean fireImmune() {
        return true;
    }

    @Override
    public boolean rideableUnderWater() {
        return true;
    }

    @Override
    public boolean shouldVerticalMove() {
        return true;
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Override
    public float getSteeringSpeed() {
        return 0.5F;
    }

    public boolean canBeLeashed(Player entity) {
        return this.isTame() && super.canBeLeashed(entity);
    }

    @OnlyIn(Dist.CLIENT)
    public Vec3 getLeashOffset() {
        return new Vec3(0.0D, 0.6D * this.getEyeHeight(), this.getBbWidth() * 0.4F);
    }

    public boolean canBreatheUnderwater() {
        return true;
    }

    public MobType getMobType() {
        return MobType.WATER;
    }

    @Override
    public double getPassengersRidingOffset() {
        return super.getPassengersRidingOffset() * 1.75;
    }

    @Override
    protected boolean canRide(Entity entity) {
        return super.canRide(entity);
    }

    public boolean canBeControlledByRider() {
        return this.getControllingPassenger() == this.getOwner();
    }

    @Nullable
    public Entity getControllingPassenger() {
        return this.getPassengers().isEmpty() ? null : this.getPassengers().get(0);
    }

    public int getAmbientSoundInterval() {
        return 120;
    }

    protected int getExperienceReward(Player playerEntity) {
        return 2 + this.level.random.nextInt(3);
    }

    public boolean isPushedByFluid() {
        return false;
    }

    @Override
    protected float getWaterSlowDown() {
        if (this.isVehicle()) {
            return 0.95F;
        } else {
            return super.getWaterSlowDown();
        }
    }

    @Override
    public void travel(Vec3 dir) {
        Entity entity = this.getControllingPassenger();
        if (this.isVehicle()) {
            assert entity != null;
            this.yRot = entity.yRot;
            this.yRotO = this.yRot;
            this.xRot = entity.xRot * 0.5F;
            this.setRot(this.yRot, this.xRot);
            this.flyingSpeed = this.getSpeed() * 0.15F;
            this.yBodyRot = entity.yRot;
            this.yHeadRot = entity.yRot;
            this.maxUpStep = 1.0F;
            if (entity instanceof LivingEntity) {
                this.setSpeed((float) this.getAttributeValue(Attributes.MOVEMENT_SPEED));
                float forward = ((LivingEntity) entity).zza;
                float strafe = ((LivingEntity) entity).xxa;
                super.travel(new Vec3(strafe, 0, forward));
            }
            this.animationSpeedOld = this.animationSpeed;
            double d1 = this.getX() - this.xo;
            double d0 = this.getZ() - this.zo;
            float f1 = Mth.sqrt((float) (d1 * d1 + d0 * d0)) * 4.0F;
            if (f1 > 1.0F)
                f1 = 1.0F;
            this.animationSpeed += (f1 - this.animationSpeed) * 0.4F;
            this.animationPosition += this.animationSpeed;
            return;
        }
        this.maxUpStep = 0.5F;
        this.flyingSpeed = 0.02F;
        super.travel(dir);
    }

    public Vec3 getDismountLocationForPassenger(LivingEntity pLivingEntity) {
        Vec3 vector3d = getCollisionHorizontalEscapeVector(this.getBbWidth(), pLivingEntity.getBbWidth(), this.yRot + (pLivingEntity.getMainArm() == HumanoidArm.RIGHT ? 90.0F : -90.0F));
        Vec3 vector3d1 = this.getDismountLocationInDirection(vector3d, pLivingEntity);
        if (vector3d1 != null) {
            return vector3d1;
        } else {
            Vec3 vector3d2 = getCollisionHorizontalEscapeVector(this.getBbWidth(), pLivingEntity.getBbWidth(), this.yRot + (pLivingEntity.getMainArm() == HumanoidArm.LEFT ? 90.0F : -90.0F));
            Vec3 vector3d3 = this.getDismountLocationInDirection(vector3d2, pLivingEntity);
            return vector3d3 != null ? vector3d3 : this.position();
        }
    }

    @Nullable
    private Vec3 getDismountLocationInDirection(Vec3 vector3d1, LivingEntity entity) {
        double d0 = this.getX() + vector3d1.x;
        double d1 = this.getBoundingBox().minY;
        double d2 = this.getZ() + vector3d1.z;
        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();

        for(Pose pose : entity.getDismountPoses()) {
            mutable.set(d0, d1, d2);
            double d3 = this.getBoundingBox().maxY + 0.75D;

            while(true) {
                double d4 = this.level.getBlockFloorHeight(mutable);
                if ((double)mutable.getY() + d4 > d3) {
                    break;
                }
                
                if (DismountHelper.isBlockFloorValid(d4)) {
                    AABB axisalignedbb = entity.getLocalBoundsForPose(pose);
                    Vec3 vector3d = new Vec3(d0, (double)mutable.getY() + d4, d2);
                    if (DismountHelper.canDismountTo(this.level, entity, axisalignedbb.move(vector3d))) {
                        entity.setPose(pose);
                        return vector3d;
                    }
                }

                mutable.move(Direction.UP);
                if (!((double)mutable.getY() < d3)) {
                    break;
                }
            }
        }

        return null;
    }

    @Override
    public void travelWithInput(Vec3 pTravelVec) {
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (event.isMoving() && this.getTarget() == null) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.aquamaer.walk", EDefaultLoopTypes.LOOP));
            return PlayState.CONTINUE;
        }
        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.aquamaer.idle", EDefaultLoopTypes.LOOP));
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "aquamaer_controller", 0, this::predicate));
    }

    @Override
    public boolean boost() {
        return false;
    }
}
