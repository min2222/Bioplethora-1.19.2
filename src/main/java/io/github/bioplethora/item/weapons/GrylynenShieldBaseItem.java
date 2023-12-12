package io.github.bioplethora.item.weapons;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

public abstract class GrylynenShieldBaseItem extends ShieldItem {
    public boolean canBeCooldown = false;

    /**
     * @return Default cooldown it will take from Axes.
     */
    public abstract int getCooldownReduction();

    /**
     * @return How much durability will the armor used by the user recover everytime the shield gets damaged?
     */
    public abstract int getArmorBonus();

    /**
     * This skill will trigger when the shield gets damaged. This custom void is executed through ForgeHooks in {@link io.github.bioplethora.event.ServerLevelEvents#onLivingAttack(LivingAttackEvent)}
     */
    public abstract void blockingSkill(ItemStack stack, LivingEntity user, Entity attacker, Level world);

    public GrylynenShieldBaseItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean canPerformAction(ItemStack stack, ToolAction toolAction) {
    	return toolAction == ToolActions.SHIELD_BLOCK;
    }

    @Override
    public int getUseDuration(ItemStack itemStack) {
        return 75000;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack itemStack) {
        return UseAnim.BLOCK;
    }

    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pItemSlot, boolean pIsSelected) {
        super.inventoryTick(pStack, pLevel, pEntity, pItemSlot, pIsSelected);

        if (pEntity instanceof Player) {
            Player player = (Player) pEntity;

            if (!player.getCooldowns().isOnCooldown(pStack.getItem())) {
                canBeCooldown = false;
            }

            if (!canBeCooldown) {
                if (player.getCooldowns().getCooldownPercent(pStack.getItem(), 0.0F) >= 0.9F) {
                    player.getCooldowns().removeCooldown(pStack.getItem());
                    player.getCooldowns().addCooldown(pStack.getItem(), getCooldownReduction());
                    canBeCooldown = true;
                }
            }
        }
    }
}
