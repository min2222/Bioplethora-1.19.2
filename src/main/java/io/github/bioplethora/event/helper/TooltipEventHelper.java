package io.github.bioplethora.event.helper;

import io.github.bioplethora.api.BPItemSettings;
import io.github.bioplethora.api.IReachWeapon;
import io.github.bioplethora.registry.BPEnchantments;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;

public class TooltipEventHelper {

    public static void onTooltip(ItemTooltipEvent event) {
        if (event.getEntity() == null) return;

        /*
        if (event.getItemStack().getItem() instanceof IReachWeapon) {
            IReachWeapon reachWeapon = (IReachWeapon) event.getItemStack().getItem();
            event.getToolTip().add(Component.translatable(ItemSettings.REACH_BONUS, (int) reachWeapon.getReachNBT(event.getItemStack()) - 4).withStyle(ItemSettings.REACH_BONUS_COLOR));
        }
         */

        if (event.getItemStack().getItem() instanceof IReachWeapon) {
            IReachWeapon reachWeapon = (IReachWeapon) event.getItemStack().getItem();
            event.getToolTip().add(Component.translatable(BPItemSettings.REACH_BONUS, reachWeapon.getReachDistance() - 5).withStyle(BPItemSettings.REACH_BONUS_COLOR));
        }

        if (EnchantmentHelper.getItemEnchantmentLevel(BPEnchantments.ANTIBIO_ECOHARMLESS.get(), event.getItemStack()) > 0) {
            event.getToolTip().add(Component.translatable(BPItemSettings.ECOHARMLESS_ENCH,
                    EnchantmentHelper.getItemEnchantmentLevel(BPEnchantments.ANTIBIO_ECOHARMLESS.get(), event.getItemStack()))
                    .withStyle(BPItemSettings.ANTIBIO_BONUS_COLOR));
        }
        if (EnchantmentHelper.getItemEnchantmentLevel(BPEnchantments.ANTIBIO_PLETHONEUTRAL.get(), event.getItemStack()) > 0) {
            event.getToolTip().add(Component.translatable(BPItemSettings.PLETHONEUTRAL_ENCH,
                    EnchantmentHelper.getItemEnchantmentLevel(BPEnchantments.ANTIBIO_ECOHARMLESS.get(), event.getItemStack()))
                    .withStyle(BPItemSettings.ANTIBIO_BONUS_COLOR));
        }
        if (EnchantmentHelper.getItemEnchantmentLevel(BPEnchantments.ANTIBIO_DANGERUM.get(), event.getItemStack()) > 0) {
            event.getToolTip().add(Component.translatable(BPItemSettings.DANGERUM_ENCH,
                    EnchantmentHelper.getItemEnchantmentLevel(BPEnchantments.ANTIBIO_ECOHARMLESS.get(), event.getItemStack()))
                    .withStyle(BPItemSettings.ANTIBIO_BONUS_COLOR));
        }
        if (EnchantmentHelper.getItemEnchantmentLevel(BPEnchantments.ANTIBIO_HELLSENT.get(), event.getItemStack()) > 0) {
            event.getToolTip().add(Component.translatable(BPItemSettings.HELLSENT_ENCH,
                    EnchantmentHelper.getItemEnchantmentLevel(BPEnchantments.ANTIBIO_ECOHARMLESS.get(), event.getItemStack()))
                    .withStyle(BPItemSettings.ANTIBIO_BONUS_COLOR));
        }
        if (EnchantmentHelper.getItemEnchantmentLevel(BPEnchantments.ANTIBIO_ELDERIA.get(), event.getItemStack()) > 0) {
            event.getToolTip().add(Component.translatable(BPItemSettings.ELDERIA_ENCH,
                    EnchantmentHelper.getItemEnchantmentLevel(BPEnchantments.ANTIBIO_ECOHARMLESS.get(), event.getItemStack()))
                    .withStyle(BPItemSettings.ANTIBIO_BONUS_COLOR));
        }

        /*
        if (
                false
        ) {
            event.getToolTip().add(Component.translatable(ItemSettings.NO_USE_YET).withStyle(ItemSettings.NO_USE_COLOR));
        }*/
    }
}
