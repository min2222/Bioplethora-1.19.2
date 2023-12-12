package io.github.bioplethora.mixin;

import org.spongepowered.asm.mixin.Mixin;

import io.github.bioplethora.entity.IBioClassification;
import io.github.bioplethora.enums.BPEntityClasses;
import net.minecraft.world.entity.LivingEntity;

@Mixin(LivingEntity.class)
public class LivingEntityMixin implements IBioClassification {

    @Override
    public BPEntityClasses getBioplethoraClass() {
        return BPEntityClasses.NONE;
    }
}
