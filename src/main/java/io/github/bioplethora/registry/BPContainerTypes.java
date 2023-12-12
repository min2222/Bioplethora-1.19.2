package io.github.bioplethora.registry;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.gui.container.AbstractReinforcingContainer;
import io.github.bioplethora.gui.container.ReinforcingTableContainer;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BPContainerTypes {
    public static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, Bioplethora.MOD_ID);

    public static final RegistryObject<MenuType<AbstractReinforcingContainer>> REINFORCING_TABLE_CONTAINER
            = CONTAINERS.register("reinforcing", () -> IForgeMenuType.create((windowId, inv, data) ->
            new ReinforcingTableContainer(windowId, inv)));
}
