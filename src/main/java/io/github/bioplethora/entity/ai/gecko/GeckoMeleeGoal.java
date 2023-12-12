package io.github.bioplethora.entity.ai.gecko;

import java.util.EnumSet;
import java.util.function.BiFunction;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;

/**
 * Credits: WeirdNerd (Permission Granted)
 */
public class GeckoMeleeGoal<E extends Mob> extends GeckoGoal<E> {
    public double animationLength;
    public BiFunction<Double, Double, Boolean> attackPredicate;
    public boolean hasHit;

    public GeckoMeleeGoal(E entity, double animationLength, double attackBegin, double attackEnd) {
        this.entity = entity;
        this.animationLength = animationLength;
        this.attackPredicate = (progress, length) -> attackBegin < progress / (length) && progress / (length) < attackEnd;
        this.setFlags(EnumSet.of(Flag.LOOK));
    }

    public static boolean checkIfValid(GeckoMeleeGoal goal, Mob attacker, LivingEntity target) {
        if (target == null) return false;
        if (target.isAlive() && !target.isSpectator()) {
            if (target instanceof Player && ((Player) target).isCreative()) {
                goal.setWhat((IGeckoBaseEntity) attacker, false);
                return false;
            }
            double distance = goal.entity.distanceToSqr(target.getX(), target.getY(), target.getZ());
            if (distance <= GeckoGoal.getAttackReachSq(attacker, target)) return true;
        }
        goal.setWhat((IGeckoBaseEntity) attacker, false);
        return false;
    }

    @Override
    public boolean canUse() {
        if (Math.random() <= 0.1) return false;

        return GeckoMeleeGoal.checkIfValid(this, entity, this.entity.getTarget());
    }

    @Override
    public boolean canContinueToUse() {
        if (Math.random() <= 0.1) return true;

        return GeckoMeleeGoal.checkIfValid(this, entity, this.entity.getTarget());
    }

    @Override
    public void start() {
        setWhat((IGeckoBaseEntity) this.entity, true);
        this.entity.setAggressive(true);
        this.animationProgress = 0;
    }

    @Override
    public void stop() {
        LivingEntity target = this.entity.getTarget();
        if (!EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(target)) {
            this.entity.setTarget(null);
        }
        setWhat((IGeckoBaseEntity) this.entity, false);
        this.entity.setAggressive(false);
        this.hasHit = false;
        this.animationProgress = 0;
    }

    @Override
    public void tick() {
        this.baseTick();
        LivingEntity target = this.entity.getTarget();
        if (target != null) {
            if (this.attackPredicate.apply(this.animationProgress, this.animationLength) && !this.hasHit) {
                this.entity.lookAt(target, 30.0F, 30.0F);
                this.entity.swing(InteractionHand.MAIN_HAND);
                this.entity.doHurtTarget(target);
                this.hasHit = true;
            }

            if (this.animationProgress > this.animationLength) {
                this.animationProgress = 0;
                this.hasHit = false;
            }
        }
    }

    public void setWhat(IGeckoBaseEntity entity, boolean value) {
        entity.setAttacking(value);
    }
}
