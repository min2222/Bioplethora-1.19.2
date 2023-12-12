package io.github.bioplethora.entity.ai.goals;

import io.github.bioplethora.entity.creatures.AltyrusEntity;
import io.github.bioplethora.entity.creatures.FrostbiteGolemEntity;
import io.github.bioplethora.registry.BPEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;

public class AltyrusSummonGolemGoal extends Goal {

    private final AltyrusEntity altyrus;
    public int summonTime;

    public AltyrusSummonGolemGoal(AltyrusEntity altyrusEntity) {
        this.altyrus = altyrusEntity;
    }

    public boolean canUse() {
        return this.altyrus.getTarget() != null;
    }

    public void start() {
        this.summonTime = 0;
    }

    public void stop() {
        this.altyrus.setSummoning(false);
    }

    public void tick() {
        LivingEntity target = this.altyrus.getTarget();
        ServerLevel serverworld = (ServerLevel) this.altyrus.level;
        Level world = this.altyrus.level;

        if (target != null && target.distanceToSqr(this.altyrus) < 4096.0D /*&& this.altyrus.hasLineOfSight(target)*/) {
            ++this.summonTime;

            if (this.summonTime == 400) {
                BlockPos blockpos = this.altyrus.blockPosition();

                FrostbiteGolemEntity frostbite_golemEntity = BPEntities.FROSTBITE_GOLEM.get().create(world);
                frostbite_golemEntity.moveTo(blockpos, 0.0F, 0.0F);
                frostbite_golemEntity.setOwner(this.altyrus);
                frostbite_golemEntity.finalizeSpawn(serverworld, world.getCurrentDifficultyAt(blockpos), MobSpawnType.MOB_SUMMONED, null, null);

                frostbite_golemEntity.setHasLimitedLife(true);
                frostbite_golemEntity.setExplodeOnExpiry(true);
                frostbite_golemEntity.setLifeLimitBeforeDeath(200);

                serverworld.addFreshEntity(frostbite_golemEntity);

                FrostbiteGolemEntity frostbite_golemEntity2 = BPEntities.FROSTBITE_GOLEM.get().create(world);
                frostbite_golemEntity2.moveTo(blockpos, 0.0F, 0.0F);
                frostbite_golemEntity2.setOwner(this.altyrus);
                frostbite_golemEntity2.finalizeSpawn(serverworld, world.getCurrentDifficultyAt(blockpos), MobSpawnType.MOB_SUMMONED, null, null);

                frostbite_golemEntity2.setHasLimitedLife(true);
                frostbite_golemEntity2.setExplodeOnExpiry(true);
                frostbite_golemEntity2.setLifeLimitBeforeDeath(200);
                serverworld.addFreshEntity(frostbite_golemEntity2);

                this.summonTime = 0;
            }

            this.altyrus.setSummoning(this.summonTime > 360);
        }
    }
}
