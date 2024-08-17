package io.github.bioplethora.registry;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.item.BioplethoraSpawnEggItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class BPItemGroup {
	
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Bioplethora.MOD_ID);
    
    public static final RegistryObject<CreativeModeTab> BP_ITEMS = CREATIVE_MODE_TAB.register("bp_items", () -> CreativeModeTab.builder()
    		.title(Component.translatable("itemGroup.bioplethora_item_item_group"))
    		.icon(() -> new ItemStack(BPItems.ARBITRARY_BALLISTA.get()))
    		/*.withTabsImage(new ResourceLocation(Bioplethora.MOD_ID, "textures/gui/container/bpitem_tabs.png"))
    		.withBackgroundLocation(new ResourceLocation(Bioplethora.MOD_ID, "textures/gui/container/bp_creative_tab.png"))*/
    		.displayItems((enabledFeatures, output) -> 
    		{
    			for (RegistryObject<Item> items : BPItems.ITEMS.getEntries()) {
    				if (!(items.get() instanceof BioplethoraSpawnEggItem)) {
    					if (!items.getId().getPath().equals("swivelbloom")) {
    						output.accept(items.get());
    					}
    				}
    			}
    			
    			for (RegistryObject<Item> items : BPBlocks.BLOCK_ITEMS.getEntries()) {
    				output.accept(items.get());
    			}
    			
                for (RegistryObject<Enchantment> enchants : BPEnchantments.ENCHANTMENTS.getEntries()) {
                    Enchantment enchantment = enchants.get();

                    if (enchantment.isAllowedOnBooks()) {
                    	output.accept(EnchantedBookItem.createForEnchantment(new EnchantmentInstance(enchantment, enchantment.getMaxLevel())));
                    }
                }
    		}).build());
    
    public static final RegistryObject<CreativeModeTab> BP_EGGS = CREATIVE_MODE_TAB.register("bp_eggs", () -> CreativeModeTab.builder()
    		.title(Component.translatable("itemGroup.bioplethora_spawn_eggs_item_group"))
    		.icon(() -> new ItemStack(BPItems.PEAGUIN_SPAWN_EGG.get()))
    		/*.withTabsImage(new ResourceLocation(Bioplethora.MOD_ID, "textures/gui/container/bpeggs_tabs.png"))
    		.withBackgroundLocation(new ResourceLocation(Bioplethora.MOD_ID, "textures/gui/container/bp_creative_tab.png"))*/
    		.displayItems((enabledFeatures, output) -> 
    		{
    			for (RegistryObject<Item> items : BPItems.ITEMS.getEntries()) {
    				if (items.get() instanceof BioplethoraSpawnEggItem) {
    					if (!items.getId().getPath().equals("swivelbloom")) {
    						output.accept(items.get());
    					}
    				}
    			}
    		}).build());
}
