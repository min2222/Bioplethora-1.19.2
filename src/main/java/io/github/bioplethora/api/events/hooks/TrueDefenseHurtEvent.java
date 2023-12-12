package io.github.bioplethora.api.events.hooks;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.Cancelable;

@Cancelable
public class TrueDefenseHurtEvent extends LivingEvent {

    private final DamageSource source;

    public TrueDefenseHurtEvent(LivingEntity entity, DamageSource source) {
        super(entity);
        this.source = source;
    }

    public DamageSource getSource() {
        return source;
    }

    public Level getLevel() {
        return getEntity().level;
    }
}
