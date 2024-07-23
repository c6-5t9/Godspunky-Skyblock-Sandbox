package net.hypixel.skyblock.features.quest.starting;

import org.bukkit.event.EventHandler;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerMoveEvent;
import net.hypixel.skyblock.features.quest.Objective;

public class TeleporterObjective extends Objective
{
    public TeleporterObjective() {
        super("teleporter", "Use the teleporter");
    }
    
    @EventHandler
    public void onMove(final PlayerMoveEvent e) {
        final Location from = e.getFrom();
        final Location to = e.getTo();
        if (to == null || from.getBlockX() != to.getBlockX() || from.getBlockY() != to.getBlockY() || from.getBlockZ() != to.getBlockZ()) {
            if (!this.isThisObjective(e.getPlayer())) {
                return;
            }
            if (e.getTo().getBlock().getType().equals((Object)Material.PORTAL)) {
                this.complete(e.getPlayer());
            }
        }
    }
}
