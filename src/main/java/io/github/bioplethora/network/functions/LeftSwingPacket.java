package io.github.bioplethora.network.functions;

import java.util.function.Supplier;

import io.github.bioplethora.event.ServerWorldEvents;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

public class LeftSwingPacket {

    public LeftSwingPacket() {
    }

    public static void leftClickTrigger(LeftSwingPacket message, Supplier<NetworkEvent.Context> context) {
        context.get().setPacketHandled(true);
        Player player = context.get().getSender();
        if(player != null) {
            ServerWorldEvents.hitHandler(player, player.getItemInHand(InteractionHand.MAIN_HAND));
        }
    }

    public static void encode(LeftSwingPacket message, FriendlyByteBuf buffer) {
    }

    public static LeftSwingPacket decode(FriendlyByteBuf buffer) {
        return new LeftSwingPacket();
    }
}
