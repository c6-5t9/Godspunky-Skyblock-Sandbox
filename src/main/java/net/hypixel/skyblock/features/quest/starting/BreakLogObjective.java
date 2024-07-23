package net.hypixel.skyblock.features.quest.starting;

import org.bukkit.event.EventHandler;
import org.bukkit.Material;
import org.bukkit.event.block.BlockBreakEvent;
import net.hypixel.skyblock.features.quest.Objective;

public class BreakLogObjective extends Objective
{
    public BreakLogObjective() {
        super("break_log", "Break a log");
    }
    
    @EventHandler
    public void onBreak(final BlockBreakEvent e) {
        if (!this.isThisObjective(e.getPlayer())) {
            return;
        }
        if (e.getBlock().getType().equals((Object)Material.LOG)) {
            this.complete(e.getPlayer());
        }
    }
}
