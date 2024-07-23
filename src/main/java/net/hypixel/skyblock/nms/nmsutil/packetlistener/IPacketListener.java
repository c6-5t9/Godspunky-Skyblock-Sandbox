package net.hypixel.skyblock.nms.nmsutil.packetlistener;

import org.bukkit.event.Cancellable;

public interface IPacketListener
{
    Object onPacketSend(final Object p0, final Object p1, final Cancellable p2);
    
    Object onPacketReceive(final Object p0, final Object p1, final Cancellable p2);
}
