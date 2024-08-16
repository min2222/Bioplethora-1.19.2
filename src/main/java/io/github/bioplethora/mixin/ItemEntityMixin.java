package io.github.bioplethora.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import io.github.bioplethora.registry.BPItems;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin {

    @Shadow public abstract ItemStack getItem();

    @Inject(method = "hurt", at = @At(value = "HEAD"), cancellable = true)
    public void hurt(DamageSource pSource, float pAmount, CallbackInfoReturnable<Boolean> cir) {
        if (!this.getItem().isEmpty() && pSource.is(DamageTypeTags.IS_EXPLOSION)) {

            if (this.getItem().getItem() == BPItems.ALPHANUM_OBLITERATOR.get()) {
                cir.setReturnValue(false);

            } else if (this.getItem().getItem() == BPItems.ALPHANUM_GEM.get()) {
                cir.setReturnValue(false);
            }
        }
    }
}
