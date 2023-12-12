package io.github.bioplethora.enchantments;

import io.github.bioplethora.item.weapons.AlphanumObliteratorItem;
import io.github.bioplethora.item.weapons.GaidiusBaseItem;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class BPEnchantmentHelper {

    public static final EnchantmentCategory BP_WEAPON_AND_AXE = EnchantmentCategory.create("bp_weapon_and_axe", (item) -> (item instanceof SwordItem || item instanceof AxeItem));
    public static final EnchantmentCategory ALPHANUM_OBLITERATOR = EnchantmentCategory.create("alphanum_obliterator", (item) -> item instanceof AlphanumObliteratorItem);
    public static final EnchantmentCategory GAIDIUS = EnchantmentCategory.create("gaidius", (item) -> item instanceof GaidiusBaseItem);
}
