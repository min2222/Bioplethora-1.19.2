package io.github.bioplethora.network.functions;

import java.util.function.Supplier;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

/**
 * Off-Hand combat integration
 */
public class NucleusActivatePacket {

    public int x, y, z;

    public NucleusActivatePacket(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public static void setState(NucleusActivatePacket message, Supplier<NetworkEvent.Context> context) {
        context.get().setPacketHandled(true);
        //TODO 
        //((AlphanumNucleusTileEntity) Minecraft.getInstance().level.getBlockEntity(new BlockPos(message.x, message.y, message.z))).tick();
    }

    public static void encode(NucleusActivatePacket message, FriendlyByteBuf buffer) {
        buffer.writeInt(message.x);
        buffer.writeInt(message.y);
        buffer.writeInt(message.z);
    }

    public static NucleusActivatePacket decode(FriendlyByteBuf buffer) {
        return new NucleusActivatePacket(buffer.readInt(), buffer.readInt(), buffer.readInt());
    }
}
