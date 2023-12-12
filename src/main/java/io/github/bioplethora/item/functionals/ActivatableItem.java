package io.github.bioplethora.item.functionals;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ActivatableItem extends Item {

    public String getFullActivatedText = "is_activated";
    public boolean isCurios;

    public ActivatableItem(Properties pProperties, boolean isCurios) {
        super(pProperties);
        this.isCurios = isCurios;
    }

    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pItemSlot, boolean pIsSelected) {
        super.inventoryTick(pStack, pLevel, pEntity, pItemSlot, pIsSelected);

        if (this.getActivated(pStack)) {
            this.activatedTick(pStack, pLevel, pEntity);
        }
    }

    /**
     * Ticks in inventory if this ActivatableItem is activated.
     */
    public void activatedTick(ItemStack pStack, Level pLevel, Entity pEntity) {
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack stack = pPlayer.getItemInHand(pHand);

        if (pPlayer.isCrouching()) {
            activate(pLevel, pPlayer, stack);
            return InteractionResultHolder.success(stack);
        } else {
            return InteractionResultHolder.fail(stack);
        }
    }

    public void activate(Level pLevel, Player pPlayer, ItemStack stack) {

        if (!pPlayer.getCooldowns().isOnCooldown(stack.getItem())) {
            if (!getActivated(stack)) {
                setActivated(true, stack);
                pPlayer.playSound(getActivationSound(), 1.0F, 1.0F);
            } else if (getActivated(stack)) {
                setActivated(false, stack);
                pPlayer.playSound(getDeactivationSound(), 1.0F, 1.0F);
            }

            pPlayer.getCooldowns().addCooldown(stack.getItem(), 60);
        }
    }

    public boolean getActivated(ItemStack stack) {
        return stack.getOrCreateTag().getBoolean(getFullActivatedText);
    }

    public void setActivated(boolean activated, ItemStack stack) {
        stack.getOrCreateTag().putBoolean(getFullActivatedText, activated);
    }

    public SoundEvent getActivationSound() {
        return SoundEvents.ANVIL_USE;
    }

    public SoundEvent getDeactivationSound() {
        return SoundEvents.ANVIL_PLACE;
    }

    @Override
    public boolean isFoil(ItemStack pStack) {
        return this.getActivated(pStack);
    }
}
