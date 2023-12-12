package io.github.bioplethora.network.functions;

import java.util.function.Supplier;

import io.github.bioplethora.event.ServerWorldEvents;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

/**
 * Off-Hand combat integration
 */
public class RightSwingPacket {

    public RightSwingPacket() {
    }

    public static void rightClickTrigger(RightSwingPacket message, Supplier<NetworkEvent.Context> context) {
        context.get().setPacketHandled(true);
        Player player = context.get().getSender();
        if(player != null) {
            ServerWorldEvents.hitHandler(player, player.getItemInHand(InteractionHand.OFF_HAND));
        }
    }

    public static void encode(RightSwingPacket message, FriendlyByteBuf buffer) {
    }

    public static RightSwingPacket decode(FriendlyByteBuf buffer) {
        return new RightSwingPacket();
    }
}
