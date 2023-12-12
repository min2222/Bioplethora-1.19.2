package io.github.bioplethora.item.extras;

import java.util.function.Supplier;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.MobBucketItem;
import net.minecraft.world.level.material.Fluid;

public class TriggerfishBucketItem extends MobBucketItem {

    public TriggerfishBucketItem(Supplier<? extends EntityType<?>> p_i244797_1_, Supplier<? extends Fluid> p_i244797_2_, Supplier<? extends SoundEvent> soundSupplier, Properties p_i244797_3_) {
        super(p_i244797_1_, p_i244797_2_, soundSupplier, p_i244797_3_);
    }
}
