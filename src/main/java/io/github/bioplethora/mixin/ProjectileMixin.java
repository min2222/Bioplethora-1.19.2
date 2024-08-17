package io.github.bioplethora.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.event.ForgeEventFactory;

@Mixin(Projectile.class)
public class ProjectileMixin 
{
	@Inject(at = @At("HEAD"), method = "onHit", cancellable = true)
    private void onHit(HitResult p_37260_, CallbackInfo ci)
    {
		if(ForgeEventFactory.onProjectileImpact(Projectile.class.cast(this), p_37260_))
		{
			ci.cancel();
		}
    }
}
