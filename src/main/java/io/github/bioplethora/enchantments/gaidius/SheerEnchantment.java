package io.github.bioplethora.enchantments.gaidius;

import io.github.bioplethora.enchantments.BPEnchantmentHelper;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;

public class SheerEnchantment extends Enchantment {

    public SheerEnchantment(Rarity rarity, EquipmentSlot... slotTypes) {
        super(rarity, BPEnchantmentHelper.GAIDIUS, slotTypes);
    }

    public int getMinCost(int Int) {
        return 1 + (Int - 1) * 10;
    }

    public int getMaxCost(int Int) {
        return this.getMinCost(Int) + 15;
    }

    public int getMaxLevel() {
        return 2;
    }
}
