package io.github.bioplethora.enchantments.alphanum_obliterator;

import io.github.bioplethora.enchantments.BPEnchantmentHelper;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;

public class DevastatingBlastEnchantment extends Enchantment {

    public DevastatingBlastEnchantment(Enchantment.Rarity rarity, EquipmentSlot... slotTypes) {
        super(rarity, BPEnchantmentHelper.ALPHANUM_OBLITERATOR, slotTypes);
    }

    public int getMinCost(int Int) {
        return 1 + (Int - 1) * 10;
    }

    public int getMaxCost(int Int) {
        return this.getMinCost(Int) + 15;
    }

    public int getMaxLevel() {
        return 5;
    }

    public boolean canEnchant(ItemStack stack) {
        return stack.getItem() instanceof AxeItem || super.canEnchant(stack);
    }
}
