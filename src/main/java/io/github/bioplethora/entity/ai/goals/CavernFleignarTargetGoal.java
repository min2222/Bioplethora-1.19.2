package io.github.bioplethora.entity.ai.goals;

import io.github.bioplethora.entity.creatures.CavernFleignarEntity;
import io.github.bioplethora.registry.BPEffects;
import io.github.bioplethora.registry.BPTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;

public class CavernFleignarTargetGoal extends TargetGoal {

    private final CavernFleignarEntity fleignar;
    private static final TargetingConditions CONDITION = TargetingConditions.forNonCombat().range(16.0D);

    public CavernFleignarTargetGoal(CavernFleignarEntity fleignar, boolean mustSee) {
        super(fleignar, mustSee, false);
        this.fleignar = fleignar;
    }

    @Override
    public boolean canUse() {
        return true;
    }

    @Override
    public boolean canContinueToUse() {
        return true;
    }

    @Override
    public void start() {
    }

    @Override
    public void stop() {
    }

    @Override
    public void tick() {
        LivingEntity target = this.fleignar.getTarget();

        if (target == null) {
            AABB searchRadius = this.fleignar.getBoundingBox().inflate(12.0D, 4.0D, 12.0D);
            LivingEntity targetCandidate = fleignar.level.getNearestEntity(LivingEntity.class, CONDITION, fleignar, fleignar.getX(), fleignar.getY(), fleignar.getZ(), searchRadius);

            if (targetCandidate != null) {
                boolean getTag = targetCandidate.getType().is(BPTags.Entities.FLEIGNAR_TARGETS);
                if (validCheck(targetCandidate) && getTag) {
                    if (!targetCandidate.hasEffect(BPEffects.SPIRIT_MANIPULATION.get())) {
                        fleignar.setTarget(targetCandidate);
                    }
                }
            }
        }
    }

    public boolean validCheck(LivingEntity target) {
        if (target.isAlive() && !target.isSpectator()) {
            return !(target instanceof Player && ((Player) target).isCreative());
        } else {
            return false;
        }
    }
}
