package io.github.bioplethora.entity;

import java.util.EnumSet;

import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;

/**
 * Must implement a.i. goals {@link MoveRandomGoal} and move controller {@link MoveHelperController} for the floating to work.
 * {@link ChargeAttackGoal} is optional.
 */
public abstract class FloatingCreatureEntity extends BPCreatureEntity implements FlyingAnimal {
    public BlockPos boundOrigin;

    public FloatingCreatureEntity(EntityType<? extends PathfinderMob> type, Level worldIn) {
        super(type, worldIn);
    }

    @Override
    public abstract void registerControllers(AnimatableManager.ControllerRegistrar data);

    @Override
    public abstract AnimatableInstanceCache getAnimatableInstanceCache();

    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        if (pCompound.contains("BoundX")) {
            this.boundOrigin = new BlockPos(pCompound.getInt("BoundX"), pCompound.getInt("BoundY"), pCompound.getInt("BoundZ"));
        }
    }

    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        if (this.boundOrigin != null) {
            pCompound.putInt("BoundX", this.boundOrigin.getX());
            pCompound.putInt("BoundY", this.boundOrigin.getY());
            pCompound.putInt("BoundZ", this.boundOrigin.getZ());
        }
    }

    public void setBoundOrigin(@Nullable BlockPos pBoundOrigin) {
        this.boundOrigin = pBoundOrigin;
    }

    @Nullable
    public BlockPos getBoundOrigin() {
        return this.boundOrigin;
    }

    public boolean isNoGravity() {
        return true;
    }

    public boolean causeFallDamage(float distance, float damageMultiplier) {
        return false;
    }

    protected void checkFallDamage(double y, boolean onGroundIn, BlockState state, BlockPos pos) {
    }

    public class ChargeAttackGoal extends Goal {
        public ChargeAttackGoal() {
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        public boolean canUse() {
            if (FloatingCreatureEntity.this.getTarget() != null &&
                    !FloatingCreatureEntity.this.getMoveControl().hasWanted() &&
                    FloatingCreatureEntity.this.random.nextInt(3) == 0) {

                return FloatingCreatureEntity.this.distanceToSqr(FloatingCreatureEntity.this.getTarget()) > 2.0D;
            } else {
                return false;
            }
        }

        public boolean canContinueToUse() {
            return FloatingCreatureEntity.this.getMoveControl().hasWanted() &&
                    FloatingCreatureEntity.this.getTarget() != null &&
                    FloatingCreatureEntity.this.getTarget().isAlive();
        }

        public void start() {
            LivingEntity livingentity = FloatingCreatureEntity.this.getTarget();
            Vec3 vector3d = livingentity.getEyePosition(1.0F);
            FloatingCreatureEntity.this.moveControl.setWantedPosition(vector3d.x, vector3d.y, vector3d.z, 1.0D);
        }

        public void stop() {
        }

        public void tick() {
            LivingEntity livingentity = FloatingCreatureEntity.this.getTarget();

            if (!FloatingCreatureEntity.this.getBoundingBox().intersects(livingentity.getBoundingBox())) {
                double d0 = FloatingCreatureEntity.this.distanceToSqr(livingentity);
                if (d0 < 9.0D) {
                    Vec3 vector3d = livingentity.getEyePosition(1.0F);
                    FloatingCreatureEntity.this.moveControl.setWantedPosition(vector3d.x, vector3d.y, vector3d.z, 1.0D);
                }
            }
        }
    }

    public class MoveHelperController extends MoveControl {
        public MoveHelperController(FloatingCreatureEntity floatingMob) {
            super(floatingMob);
        }

        public void tick() {
            if (this.operation == MoveControl.Operation.MOVE_TO) {
                Vec3 vector3d = new Vec3(this.wantedX - FloatingCreatureEntity.this.getX(), this.wantedY - FloatingCreatureEntity.this.getY(), this.wantedZ - FloatingCreatureEntity.this.getZ());
                double d0 = vector3d.length();
                if (d0 < FloatingCreatureEntity.this.getBoundingBox().getSize()) {
                    this.operation = MoveControl.Operation.WAIT;
                    FloatingCreatureEntity.this.setDeltaMovement(FloatingCreatureEntity.this.getDeltaMovement().scale(0.5D));
                } else {
                    FloatingCreatureEntity.this.setDeltaMovement(FloatingCreatureEntity.this.getDeltaMovement().add(vector3d.scale(this.speedModifier * 0.05D / d0)));
                    if (FloatingCreatureEntity.this.getTarget() == null) {
                        Vec3 vector3d1 = FloatingCreatureEntity.this.getDeltaMovement();
                        FloatingCreatureEntity.this.yRot = -((float) Mth.atan2(vector3d1.x, vector3d1.z)) * (180F / (float)Math.PI);
                        FloatingCreatureEntity.this.yBodyRot = FloatingCreatureEntity.this.yRot;
                    } else {
                        double d2 = FloatingCreatureEntity.this.getTarget().getX() - FloatingCreatureEntity.this.getX();
                        double d1 = FloatingCreatureEntity.this.getTarget().getZ() - FloatingCreatureEntity.this.getZ();
                        FloatingCreatureEntity.this.yRot = -((float)Mth.atan2(d2, d1)) * (180F / (float)Math.PI);
                        FloatingCreatureEntity.this.yBodyRot = FloatingCreatureEntity.this.yRot;
                    }
                }
            }
        }
    }

    public class MoveRandomGoal extends Goal {
        public MoveRandomGoal() {
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        public boolean canUse() {
            return !FloatingCreatureEntity.this.getMoveControl().hasWanted() && FloatingCreatureEntity.this.random.nextInt(5) == 0;
        }

        public boolean canContinueToUse() {
            return false;
        }

        public void tick() {
            BlockPos blockpos = FloatingCreatureEntity.this.getBoundOrigin();
            if (blockpos == null) {
                blockpos = FloatingCreatureEntity.this.blockPosition();
            }

            for(int i = 0; i < 3; ++i) {
                BlockPos blockpos1 = blockpos.offset(FloatingCreatureEntity.this.random.nextInt(15) - 7, FloatingCreatureEntity.this.random.nextInt(11) - 5, FloatingCreatureEntity.this.random.nextInt(15) - 7);
                if (FloatingCreatureEntity.this.level.isEmptyBlock(blockpos1)) {
                    FloatingCreatureEntity.this.moveControl.setWantedPosition((double)blockpos1.getX() + 0.5D, (double)blockpos1.getY() + 0.5D, (double)blockpos1.getZ() + 0.5D, 0.25D);
                    if (FloatingCreatureEntity.this.getTarget() == null) {
                        FloatingCreatureEntity.this.getLookControl().setLookAt((double)blockpos1.getX() + 0.5D, (double)blockpos1.getY() + 0.5D, (double)blockpos1.getZ() + 0.5D, 180.0F, 20.0F);
                    }
                    break;
                }
            }
        }
    }
}
