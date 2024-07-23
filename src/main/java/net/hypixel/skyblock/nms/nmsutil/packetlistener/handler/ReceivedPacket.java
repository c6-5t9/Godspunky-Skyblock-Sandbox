package net.hypixel.skyblock.nms.nmsutil.packetlistener.handler;

import net.hypixel.skyblock.nms.nmsutil.packetlistener.channel.ChannelWrapper;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;

public class ReceivedPacket extends PacketAbstract
{
    public ReceivedPacket(final Object packet, final Cancellable cancellable, final Player player) {
        super(packet, cancellable, player);
    }
    
    public ReceivedPacket(final Object packet, final Cancellable cancellable, final ChannelWrapper channelWrapper) {
        super(packet, cancellable, channelWrapper);
    }
}
