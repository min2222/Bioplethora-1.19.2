package io.github.bioplethora.entity.ai.goals;

import io.github.bioplethora.entity.SummonableMonsterEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;

public class CopyTargetOwnerGoal extends TargetGoal {

    private final TargetingConditions copyOwnerTargeting = TargetingConditions.forCombat().ignoreLineOfSight().ignoreInvisibilityTesting();
    private final SummonableMonsterEntity summonableMonster;

    public CopyTargetOwnerGoal(SummonableMonsterEntity summonableMonster) {
        super(summonableMonster, false);
        this.summonableMonster = summonableMonster;
    }

    public boolean canUse() {
        return this.summonableMonster.getOwner() != null && this.summonableMonster.getOwner() instanceof Mob &&
                ((Mob) this.summonableMonster.getOwner()).getTarget() != null &&
                this.canAttack(((Mob) this.summonableMonster.getOwner()).getTarget(), this.copyOwnerTargeting);
    }

    public void start() {
        this.summonableMonster.setTarget(((Mob) this.summonableMonster.getOwner()).getTarget());
        super.start();
    }
}
