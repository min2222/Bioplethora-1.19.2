package io.github.bioplethora.network.keybindings;

import java.util.function.Supplier;

import io.github.bioplethora.entity.IVerticalMount;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

// Unused
public class VerticalMountDownPacket {

    public int key;

    public VerticalMountDownPacket() {
    }

    public VerticalMountDownPacket(int key) {
        this.key = key;
    }

    public static void encode(VerticalMountDownPacket message, FriendlyByteBuf buffer) {
        buffer.writeInt(message.key);
    }

    public static VerticalMountDownPacket decode(FriendlyByteBuf buffer) {
        return new VerticalMountDownPacket(buffer.readInt());
    }

    public static void verticalDown(VerticalMountDownPacket message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            Level world = player.getLevel();
            Entity riddenEntity = player.getVehicle();

            if (riddenEntity instanceof IVerticalMount) {
                if (((IVerticalMount) riddenEntity).shouldVerticalMove()) {
                    riddenEntity.setDeltaMovement(riddenEntity.getDeltaMovement().x(), -0.3D, riddenEntity.getDeltaMovement().z());
                }
            }
        });
        context.setPacketHandled(true);
    }
}
