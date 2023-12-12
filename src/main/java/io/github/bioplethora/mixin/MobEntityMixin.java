package io.github.bioplethora.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import io.github.bioplethora.item.weapons.FrostbiteMetalShieldItem;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

@Mixin(Mob.class)
public abstract class MobEntityMixin {

    @Shadow public abstract boolean requiresCustomPersistence();

    @Inject(at = @At("HEAD"), method = ("maybeDisableShield"), cancellable = true)
    private void maybeDisableShield(Player player, ItemStack attackerAxe, ItemStack defendantShield, CallbackInfo ci) {

        if (defendantShield.getItem() instanceof FrostbiteMetalShieldItem) {
            ci.cancel();
        }
    }
}
