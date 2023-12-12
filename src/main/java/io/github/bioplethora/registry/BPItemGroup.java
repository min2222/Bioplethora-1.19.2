package io.github.bioplethora.registry;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.config.BPConfig;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.RegistryObject;

public class BPItemGroup {
    public static CreativeModeTab BioplethoraItemItemGroup = new CreativeModeTab("bioplethora_item_item_group") {

        @Override
        public ItemStack makeIcon() {
            return new ItemStack(BPItems.ARBITRARY_BALLISTA.get());
        }

        @OnlyIn(Dist.CLIENT)
        @Override
        public ResourceLocation getTabsImage() {
            if (BPConfig.COMMON.replaceCreativeTabBackground.get()) {
                return new ResourceLocation(Bioplethora.MOD_ID, "textures/gui/container/bpitem_tabs.png");
            } else {
                return super.getTabsImage();
            }
        }

        @OnlyIn(Dist.CLIENT)
        @Override
        public ResourceLocation getBackgroundImage() {
            if (BPConfig.COMMON.replaceCreativeTabBackground.get()) {
                return new ResourceLocation(Bioplethora.MOD_ID, "textures/gui/container/bp_creative_tab.png");
            } else {
                return super.getBackgroundImage();
            }
        }

        @Override
        public void fillItemList(NonNullList<ItemStack> items) {
            super.fillItemList(items);

            for (RegistryObject<Enchantment> enchants : BPEnchantments.ENCHANTMENTS.getEntries()) {
                Enchantment enchantment = enchants.get();

                if (enchantment.isAllowedOnBooks()) {
                    items.add(EnchantedBookItem.createForEnchantment(new EnchantmentInstance(enchantment, enchantment.getMaxLevel())));
                }
            }
        }
    };

    public static CreativeModeTab BioplethoraSpawnEggsItemGroup = new CreativeModeTab("bioplethora_spawn_eggs_item_group") {

        @Override
        public ItemStack makeIcon() {
            return new ItemStack(BPItems.PEAGUIN_SPAWN_EGG.get());
        }

        @OnlyIn(Dist.CLIENT)
        @Override
        public ResourceLocation getTabsImage() {
            if (BPConfig.COMMON.replaceCreativeTabBackground.get()) {
                return new ResourceLocation(Bioplethora.MOD_ID, "textures/gui/container/bpeggs_tabs.png");
            } else {
                return super.getTabsImage();
            }
        }

        @OnlyIn(Dist.CLIENT)
        @Override
        public ResourceLocation getBackgroundImage() {
            if (BPConfig.COMMON.replaceCreativeTabBackground.get()) {
                return new ResourceLocation(Bioplethora.MOD_ID, "textures/gui/container/bp_creative_tab.png");
            } else {
                return super.getBackgroundImage();
            }
        }
    };
}
