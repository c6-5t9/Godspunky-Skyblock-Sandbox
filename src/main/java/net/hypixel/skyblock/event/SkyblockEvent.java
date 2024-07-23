package net.hypixel.skyblock.event;

import org.bukkit.event.HandlerList;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;

public abstract class SkyblockEvent extends Event implements Cancellable
{
    private static final HandlerList HANDLERS;
    private boolean isCancelled;
    
    public static HandlerList getHandler() {
        return SkyblockEvent.HANDLERS;
    }
    
    public static HandlerList getHandlerList() {
        return SkyblockEvent.HANDLERS;
    }
    
    public static HandlerList getHandlersList() {
        return SkyblockEvent.HANDLERS;
    }
    
    public HandlerList getHandlers() {
        return SkyblockEvent.HANDLERS;
    }
    
    public boolean isCancelled() {
        return this.isCancelled;
    }
    
    public void setCancelled(final boolean b) {
        this.isCancelled = b;
    }
    
    static {
        HANDLERS = new HandlerList();
    }
}
