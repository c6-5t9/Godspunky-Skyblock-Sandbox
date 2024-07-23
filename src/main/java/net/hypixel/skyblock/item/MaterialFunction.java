package net.hypixel.skyblock.item;

import com.google.common.util.concurrent.AtomicDouble;
import org.bukkit.entity.Player;
import org.bukkit.entity.Entity;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public interface MaterialFunction
{
    default void onInteraction(final PlayerInteractEvent e) {
    }
    
    default void onInventoryClick(final SItem instance, final InventoryClickEvent e) {
    }
    
    default void onDamage(final Entity damaged, final Player damager, final AtomicDouble damage, final SItem item) {
    }
    
    default void onKill(final Entity damaged, final Player damager, final SItem item) {
    }
    
    default void whileHolding(final Player holding) {
    }
    
    default void onInstanceUpdate(final SItem instance) {
    }
}
