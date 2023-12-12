package io.github.bioplethora.entity.ai.gecko;

import java.util.Random;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;

/**
 * Credits: WeirdNerd (Permission Granted)
 */
public abstract class GeckoGoal<E extends Mob> extends Goal {

    protected static final Random RANDOM = new Random();
    public E entity;
    protected long tickDelta;
    protected double animationProgress;
    private long lastGameTime;
    private boolean isFirsLoop = true;

    protected static double getAttackReachSq(LivingEntity attacker, LivingEntity target) {
        return attacker.getBbWidth() * 2F * attacker.getBbWidth() * 2F + target.getBbWidth();
    }

    /**
     * Basic tick functionality that most AnimatableGoals will use
     */
    public void baseTick() {
        if (this.isFirsLoop) {
            this.isFirsLoop = false;
            this.animationProgress += 1;
            this.lastGameTime = this.entity.level.getGameTime();
            return;
        }
        this.tickDelta = this.entity.level.getGameTime() - this.lastGameTime;
        this.animationProgress += 1 + this.tickDelta / 100000.0;
        this.lastGameTime = this.entity.level.getGameTime();
    }

    @Override
    abstract public boolean canUse();
}
