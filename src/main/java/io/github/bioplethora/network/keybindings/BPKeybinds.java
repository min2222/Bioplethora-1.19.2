package io.github.bioplethora.network.keybindings;

import javax.swing.text.JTextComponent.KeyBinding;

import io.github.bioplethora.Bioplethora;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@OnlyIn(Dist.CLIENT)
public class BPKeybinds {
    public static KeyBinding verticalMountUp;
    public static KeyBinding verticalMountDown;

    public static void register(final FMLClientSetupEvent event) {
        /*
        verticalMountUp = create("vertical_up", GLFW.GLFW_KEY_SPACE);
        ClientRegistry.registerKeyBinding(verticalMountUp);

        verticalMountDown = create("vertical_down", GLFW.GLFW_KEY_LEFT_CONTROL);
        ClientRegistry.registerKeyBinding(verticalMountDown);
        */
    }

    private static KeyMapping create(String name,  int key) {
        return new KeyMapping("key." + Bioplethora.MOD_ID + "." + name, key, "key.category." + Bioplethora.MOD_ID);
    }
}
