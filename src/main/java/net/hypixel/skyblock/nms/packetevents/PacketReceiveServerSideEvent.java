package net.hypixel.skyblock.nms.packetevents;

import net.minecraft.server.v1_8_R3.Packet;
import net.hypixel.skyblock.nms.nmsutil.packetlistener.handler.ReceivedPacket;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;

public class PacketReceiveServerSideEvent extends Event implements Cancellable
{
    private static final HandlerList handlers;
    private final ReceivedPacket receivedPacket;
    
    public PacketReceiveServerSideEvent(final ReceivedPacket packet) {
        this.receivedPacket = packet;
    }
    
    public Packet<?> getPacket() {
        return (Packet<?>)this.receivedPacket.getPacket();
    }
    
    public ReceivedPacket getWrappedPacket() {
        return this.receivedPacket;
    }
    
    public boolean isCancelled() {
        return this.receivedPacket.isCancelled();
    }
    
    public void setCancelled(final boolean cancel) {
        this.receivedPacket.setCancelled(cancel);
    }
    
    public HandlerList getHandlers() {
        return PacketReceiveServerSideEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return PacketReceiveServerSideEvent.handlers;
    }
    
    static {
        handlers = new HandlerList();
    }
}
