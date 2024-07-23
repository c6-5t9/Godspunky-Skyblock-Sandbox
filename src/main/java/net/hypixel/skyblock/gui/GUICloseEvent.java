package net.hypixel.skyblock.gui;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class GUICloseEvent extends PlayerEvent
{
    private static final HandlerList handlers;
    private final GUI closed;
    
    public GUICloseEvent(final Player player, final GUI opened) {
        super(player);
        this.closed = opened;
    }
    
    public HandlerList getHandlers() {
        return GUICloseEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return GUICloseEvent.handlers;
    }
    
    public GUI getClosed() {
        return this.closed;
    }
    
    static {
        handlers = new HandlerList();
    }
}
