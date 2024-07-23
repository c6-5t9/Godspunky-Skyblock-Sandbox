package net.hypixel.skyblock.gui;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Cancellable;
import org.bukkit.event.player.PlayerEvent;

public class GUIOpenEvent extends PlayerEvent implements Cancellable
{
    private static final HandlerList handlers;
    private final GUI opened;
    private String title;
    private final Inventory inventory;
    private boolean cancelled;
    
    public GUIOpenEvent(final Player player, final GUI opened, final Inventory inventory) {
        super(player);
        this.opened = opened;
        this.title = opened.getTitle();
        this.inventory = inventory;
    }
    
    public HandlerList getHandlers() {
        return GUIOpenEvent.handlers;
    }
    
    public boolean isCancelled() {
        return this.cancelled;
    }
    
    public void setCancelled(final boolean cancel) {
        this.cancelled = cancel;
    }
    
    public static HandlerList getHandlerList() {
        return GUIOpenEvent.handlers;
    }
    
    public GUI getOpened() {
        return this.opened;
    }
    
    public String getTitle() {
        return this.title;
    }
    
    public void setTitle(final String title) {
        this.title = title;
    }
    
    public Inventory getInventory() {
        return this.inventory;
    }
    
    static {
        handlers = new HandlerList();
    }
}
