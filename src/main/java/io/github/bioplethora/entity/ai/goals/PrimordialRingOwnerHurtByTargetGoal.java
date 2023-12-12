package io.github.bioplethora.entity.ai.goals;

import java.util.EnumSet;

import io.github.bioplethora.entity.SummonableMonsterEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;

public class PrimordialRingOwnerHurtByTargetGoal extends TargetGoal {
    private final SummonableMonsterEntity tameAnimal;
    private LivingEntity ownerLastHurtBy;
    private int timestamp;

    public PrimordialRingOwnerHurtByTargetGoal(SummonableMonsterEntity p_i1667_1_) {
        super(p_i1667_1_, false);
        this.tameAnimal = p_i1667_1_;
        this.setFlags(EnumSet.of(Goal.Flag.TARGET));
    }

    public boolean canUse() {
        LivingEntity livingentity = this.tameAnimal.getOwner();
        if (livingentity == null) {
            return false;
        } else {
            this.ownerLastHurtBy = livingentity.getLastHurtByMob();
            int i = livingentity.getLastHurtByMobTimestamp();
            return i != this.timestamp && this.canAttack(this.ownerLastHurtBy, TargetingConditions.DEFAULT);
        }
    }

    public void start() {
        if (this.ownerLastHurtBy instanceof SummonableMonsterEntity) {
            if (((SummonableMonsterEntity) this.ownerLastHurtBy).getOwner() != this.tameAnimal.getOwner()) {
                this.mob.setTarget(this.ownerLastHurtBy);
            }
        } else {
            this.mob.setTarget(this.ownerLastHurtBy);
        }
        LivingEntity livingentity = this.tameAnimal.getOwner();
        if (livingentity != null) {
            this.timestamp = livingentity.getLastHurtByMobTimestamp();
        }

        super.start();
    }
}
