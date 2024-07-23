package net.hypixel.skyblock.nms.packetevents;

import org.bukkit.event.HandlerList;
import org.bukkit.event.Event;

public class PluginMessageReceived extends Event
{
    private static final HandlerList handlers;
    private final WrappedPluginMessage a;
    
    public PluginMessageReceived(final WrappedPluginMessage b) {
        this.a = b;
    }
    
    public WrappedPluginMessage getWrappedPluginMessage() {
        return this.a;
    }
    
    public HandlerList getHandlers() {
        return PluginMessageReceived.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return PluginMessageReceived.handlers;
    }
    
    static {
        handlers = new HandlerList();
    }
}
