package io.github.bioplethora.mixin.accessors;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.client.particle.Particle;

@Mixin(Particle.class)
public interface ParticleAccessor {

    @Invoker("getLightColor")
    int invokeGetLightColor(float pPartialTick);
}
