package io.github.bioplethora.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.mojang.serialization.Lifecycle;

import net.minecraft.world.level.storage.PrimaryLevelData;

@Mixin(PrimaryLevelData.class)
public class ServerWorldInfoMixin {

    @Inject(at = @At("HEAD"), method = "worldGenSettingsLifecycle", cancellable = true)
    private void forceStableLifeCycle(CallbackInfoReturnable<Lifecycle> cir) {
        cir.setReturnValue(Lifecycle.stable());
    }
}
