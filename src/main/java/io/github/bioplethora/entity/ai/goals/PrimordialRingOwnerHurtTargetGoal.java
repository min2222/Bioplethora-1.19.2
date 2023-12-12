package io.github.bioplethora.entity.ai.goals;

import java.util.EnumSet;

import io.github.bioplethora.entity.SummonableMonsterEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;

public class PrimordialRingOwnerHurtTargetGoal extends TargetGoal {
    private final SummonableMonsterEntity tameAnimal;
    private LivingEntity ownerLastHurt;
    private int timestamp;

    public PrimordialRingOwnerHurtTargetGoal(SummonableMonsterEntity p_i1668_1_) {
        super(p_i1668_1_, false);
        this.tameAnimal = p_i1668_1_;
        this.setFlags(EnumSet.of(Goal.Flag.TARGET));
    }

    public boolean canUse() {
        LivingEntity livingentity = this.tameAnimal.getOwner();
        if (livingentity == null) {
            return false;
        } else {
            this.ownerLastHurt = livingentity.getLastHurtMob();
            int i = livingentity.getLastHurtMobTimestamp();
            return i != this.timestamp && this.canAttack(this.ownerLastHurt, TargetingConditions.DEFAULT);
        }
    }

    public void start() {
        if (this.ownerLastHurt instanceof SummonableMonsterEntity) {
            if (((SummonableMonsterEntity) this.ownerLastHurt).getOwner() != this.tameAnimal.getOwner()) {
                this.mob.setTarget(this.ownerLastHurt);
            }
        } else {
            this.mob.setTarget(this.ownerLastHurt);
        }
        LivingEntity livingentity = this.tameAnimal.getOwner();
        if (livingentity != null) {
            this.timestamp = livingentity.getLastHurtMobTimestamp();
        }

        super.start();
    }
}
