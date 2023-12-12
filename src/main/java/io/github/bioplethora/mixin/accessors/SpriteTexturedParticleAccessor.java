package io.github.bioplethora.mixin.accessors;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.client.particle.TextureSheetParticle;

@Mixin(TextureSheetParticle.class)
public interface SpriteTexturedParticleAccessor {

    @Invoker("getU0")
    float invokeGetU0();

    @Invoker("getU1")
    float invokeGetU1();

    @Invoker("getV0")
    float invokeGetV0();

    @Invoker("getV1")
    float invokeGetV1();
}
