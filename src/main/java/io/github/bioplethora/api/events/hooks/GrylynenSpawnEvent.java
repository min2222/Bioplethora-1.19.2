package io.github.bioplethora.api.events.hooks;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

@Cancelable
public class GrylynenSpawnEvent extends Event {

	Player player;

    public GrylynenSpawnEvent(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public Level getLevel() {
        return player.level;
    }
}
