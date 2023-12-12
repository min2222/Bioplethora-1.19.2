package io.github.bioplethora.entity.ai.gecko;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.pathfinder.Path;

/**
 * Credits: WeirdNerd (Permission Granted)
 */
public abstract class GeckoMovableGoal<E extends Mob> extends GeckoGoal<E> {

    protected Path path;

    @Override
    abstract public boolean canUse();

    protected boolean isExecutable(GeckoMovableGoal goal, E attacker, LivingEntity target) {
        if (target == null) return false;
        if (target.isAlive() && !target.isSpectator()) {
            if (target instanceof Player && ((Player) target).isCreative()) return false;

            double distance = goal.entity.distanceToSqr(target.getX(), target.getY(), target.getZ());
            goal.path = attacker.getNavigation().createPath(target, 0);

            return attacker.getSensing().hasLineOfSight(target) && distance >= GeckoGoal.getAttackReachSq(attacker, target);
        }
        return false;
    }
}
