package io.github.bioplethora.entity;

import javax.annotation.Nullable;

import io.github.bioplethora.entity.ai.controller.WaterMoveController;
import io.github.bioplethora.entity.ai.goals.BPCustomSwimmingGoal;
import io.github.bioplethora.entity.ai.goals.BPWaterChargingCoal;
import io.github.bioplethora.entity.ai.navigator.WaterAndLandPathNavigator;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;

public abstract class WaterAndLandAnimalEntity extends BPAnimalEntity {

    public boolean isLandNavigator;
    public BlockPos boundOrigin;

    public WaterAndLandAnimalEntity(EntityType<? extends TamableAnimal> type, Level worldIn) {
        super(type, worldIn);
        this.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
        this.setPathfindingMalus(BlockPathTypes.WATER_BORDER, 0.0F);
        switchNavigator(false);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new BPWaterChargingCoal(this));
        this.goalSelector.addGoal(2, new BPCustomSwimmingGoal(this));
    }

    public void switchNavigator(boolean onLand) {
        if (onLand) {
            this.moveControl = new MoveControl(this);
            this.navigation = new GroundPathNavigation(this, level);
            this.isLandNavigator = true;
        } else {
            this.moveControl = new WaterMoveController(this, 1.2F);
            this.navigation = new WaterAndLandPathNavigator(this, level);
            this.isLandNavigator = false;
        }
    }

    public void tick() {
        super.tick();
        if (isInWater() && this.isLandNavigator) {
            switchNavigator(false);
        }
        if ((!isInWater() || this.isVehicle()) && !this.isLandNavigator) {
            switchNavigator(true);
        }
    }

    public void setBoundOrigin(@Nullable BlockPos pBoundOrigin) {
        this.boundOrigin = pBoundOrigin;
    }

    @Nullable
    public BlockPos getBoundOrigin() {
        return this.boundOrigin;
    }

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

    @Nullable
    @Override
    public abstract AgeableMob getBreedOffspring(ServerLevel p_241840_1_, AgeableMob p_241840_2_);

    @Override
    public abstract void registerControllers(AnimatableManager.ControllerRegistrar data);

    @Override
    public abstract AnimatableInstanceCache getAnimatableInstanceCache();
}
