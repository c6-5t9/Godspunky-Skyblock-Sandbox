package net.hypixel.skyblock.event;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Creeper;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Cancellable;
import org.bukkit.event.entity.EntityEvent;

public class CreeperIgniteEvent extends EntityEvent implements Cancellable
{
    private static final HandlerList handlers;
    private boolean cancelled;
    
    public CreeperIgniteEvent(final Creeper what) {
        super((Entity)what);
    }
    
    public Creeper getEntity() {
        return (Creeper)this.entity;
    }
    
    public boolean isCancelled() {
        return this.cancelled;
    }
    
    public void setCancelled(final boolean cancel) {
        this.cancelled = cancel;
    }
    
    public HandlerList getHandlers() {
        return CreeperIgniteEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return CreeperIgniteEvent.handlers;
    }
    
    static {
        handlers = new HandlerList();
    }
}
