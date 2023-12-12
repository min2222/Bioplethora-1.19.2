package io.github.bioplethora.entity.ai.goals;

import io.github.bioplethora.entity.SummonableMonsterEntity;
import io.github.bioplethora.entity.creatures.AlphemEntity;
import io.github.bioplethora.entity.creatures.AlphemKingEntity;
import io.github.bioplethora.entity.creatures.AltyrusEntity;
import io.github.bioplethora.entity.projectile.UltimateFrostbiteMetalClusterEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class AltyrusRangedAttackGoal extends Goal {

    private final AltyrusEntity altyrus;
    public boolean goingUp;
    public int chargeTime;

    public AltyrusRangedAttackGoal(AltyrusEntity altyrusEntity) {
        this.altyrus = altyrusEntity;
    }

    public boolean canUse() {
        return (this.altyrus.getTarget() != null);
    }

    public void start() {
        this.chargeTime = 0;
    }

    public void stop() {
        this.altyrus.setCharging(false);
        this.goingUp = false;
    }

    public boolean canContinueToUse() {
        return this.altyrus.getTarget() != null;
    }

    public void tick() {
        LivingEntity target = this.altyrus.getTarget();

        if (target != null && target.distanceToSqr(this.altyrus) < 4096.0D /*&& this.altyrus.hasLineOfSight(target)*/) {

            ++this.chargeTime;

            Level world = this.altyrus.level;
            BlockPos pos = new BlockPos((int) this.altyrus.getX(), (int) this.altyrus.getY(), (int) this.altyrus.getZ());

            if (this.goingUp) {
                this.altyrus.setDeltaMovement(this.altyrus.getDeltaMovement().add(0, 0.05, 0));
            }


                if (this.chargeTime == 10) {
                    this.altyrus.playSound(SoundEvents.ELDER_GUARDIAN_CURSE, (float) 1, (float) 1);
                    this.goingUp = true;
                }

                if (this.chargeTime == 30) {
                    this.altyrus.playSound(SoundEvents.SHULKER_SHOOT, (float) 1, (float) 1);
                    this.shootProjectile(world);
                }
                if (this.chargeTime == 35) {
                    this.altyrus.playSound(SoundEvents.SHULKER_SHOOT, (float) 1, (float) 1);
                    this.shootProjectile(world);
                }
                if (this.chargeTime == 40) {
                    this.altyrus.playSound(SoundEvents.SHULKER_SHOOT, (float) 1, (float) 1);
                    this.shootProjectile(world);
                    this.goingUp = false;
                }
                if (this.chargeTime == 45) {
                    this.altyrus.playSound(SoundEvents.SHULKER_SHOOT, (float) 1, (float) 1);
                    this.shootProjectile(world);
                }
                if (this.chargeTime == 50) {
                    this.altyrus.playSound(SoundEvents.SHULKER_SHOOT, (float) 1, (float) 1);
                    this.shootProjectile(world);

                    this.chargeTime = -100;
                }
        }

        this.altyrus.setCharging(this.chargeTime > 10);
    }

    public void shootProjectile(Level world) {

        int rad = 128;
        AABB aabb = new AABB(altyrus.getX() - (rad / 2d), altyrus.getY() - (rad / 2d), altyrus.getZ() - (rad / 2d), altyrus.getX() + (rad / 2d), altyrus.getY() + (rad / 2d), altyrus.getZ() + (rad / 2d));

        for (LivingEntity targetCandidates : world.getEntitiesOfClass(LivingEntity.class, aabb)) {
            if (isValidTarget(targetCandidates)) {
                Vec3 vector3d = this.altyrus.getViewVector(1.0F);
                double d2 = targetCandidates.getX() - (this.altyrus.getX() + vector3d.x * 4.0D);
                double d3 = targetCandidates.getY(0.5D) - (0.5D + this.altyrus.getY(0.5D));
                double d4 = targetCandidates.getZ() - (this.altyrus.getZ() + vector3d.z * 4.0D);
                UltimateFrostbiteMetalClusterEntity ultimateFrostbiteMetalClusterEntity = new UltimateFrostbiteMetalClusterEntity(world, this.altyrus, d2, d3, d4);
                ultimateFrostbiteMetalClusterEntity.setPos(this.altyrus.getX() + vector3d.x * 4.0D, this.altyrus.getY(0.5D) + 0.5D, ultimateFrostbiteMetalClusterEntity.getZ() + vector3d.z * 4.0D);

                world.addFreshEntity(ultimateFrostbiteMetalClusterEntity);
            }
        }
    }

    public boolean isValidTarget(LivingEntity target) {
        if (target instanceof Mob) {
            if (target instanceof AlphemEntity || target instanceof AlphemKingEntity) {
                return true;

            } else if (((Mob) target).getTarget() instanceof SummonableMonsterEntity) {
                return ((SummonableMonsterEntity) ((Mob) target).getTarget()).getOwner() == this.altyrus;

            } else if (((Mob) target).getTarget() instanceof TamableAnimal) {
                return ((TamableAnimal) ((Mob) target).getTarget()).getOwner() == this.altyrus;

            } else {
                return ((Mob) target).getTarget() == this.altyrus;
            }
        } else {
            return target instanceof Player && EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(target);
        }
    }
}
