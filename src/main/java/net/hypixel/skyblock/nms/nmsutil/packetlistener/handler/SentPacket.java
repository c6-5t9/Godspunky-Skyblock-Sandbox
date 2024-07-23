package net.hypixel.skyblock.nms.nmsutil.packetlistener.handler;

import net.hypixel.skyblock.nms.nmsutil.packetlistener.channel.ChannelWrapper;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;

public class SentPacket extends PacketAbstract
{
    public SentPacket(final Object packet, final Cancellable cancellable, final Player player) {
        super(packet, cancellable, player);
    }
    
    public SentPacket(final Object packet, final Cancellable cancellable, final ChannelWrapper channelWrapper) {
        super(packet, cancellable, channelWrapper);
    }
}
