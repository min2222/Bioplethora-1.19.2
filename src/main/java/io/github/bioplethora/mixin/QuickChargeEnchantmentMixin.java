package io.github.bioplethora.mixin;

import org.spongepowered.asm.mixin.Mixin;

import io.github.bioplethora.item.weapons.AlphanumObliteratorItem;
import io.github.bioplethora.item.weapons.VermilionBladeItem;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.QuickChargeEnchantment;

@Mixin(QuickChargeEnchantment.class)
public class QuickChargeEnchantmentMixin extends Enchantment {

    protected QuickChargeEnchantmentMixin(Rarity p_i46731_1_, EnchantmentCategory p_i46731_2_, EquipmentSlot[] p_i46731_3_) {
        super(p_i46731_1_, p_i46731_2_, p_i46731_3_);
    }

    @Override
    public boolean canEnchant(ItemStack stack) {
        return stack.getItem() instanceof VermilionBladeItem || stack.getItem() instanceof AlphanumObliteratorItem || super.canEnchant(stack);
    }
}
