package io.github.bioplethora.event;

import io.github.bioplethora.entity.ai.goals.BPAvoidEntityGoal;
import io.github.bioplethora.entity.creatures.EurydnEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.ZombifiedPiglin;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class AIModifyEvent {

    @SubscribeEvent
    public static void onEntitySpawn(EntityJoinLevelEvent event) {
        Level world = event.getLevel();
        Entity entity = event.getEntity();

        if (!(entity instanceof Mob)) return;

        Mob mob = ((Mob) event.getEntity());

        if (mob instanceof ZombifiedPiglin) {
            mob.goalSelector.addGoal(2, new BPAvoidEntityGoal<>(mob, EurydnEntity.class, 6.0F, 1.0D, 1.2D));
        }
    }
}
