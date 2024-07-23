package net.hypixel.skyblock.features.quest.starting;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.EventHandler;
import net.hypixel.skyblock.item.SMaterial;
import net.hypixel.skyblock.event.SkyBlockCraftEvent;
import net.hypixel.skyblock.features.quest.Objective;

public class WorkbenchObjective extends Objective
{
    public WorkbenchObjective() {
        super("craft_workbench", "Craft a workbench");
    }
    
    @EventHandler
    public void onCraft(final SkyBlockCraftEvent e) {
        if (!this.isThisObjective(e.getPlayer())) {
            return;
        }
        if (e.getRecipe().getResult().getType().equals((Object)SMaterial.CRAFTING_TABLE)) {
            this.complete(e.getPlayer());
        }
    }
    
    @EventHandler
    public void onCraft(final CraftItemEvent e) {
        if (!this.isThisObjective((Player)e.getWhoClicked())) {
            return;
        }
        if (e.getRecipe().getResult().getType().equals((Object)Material.WORKBENCH)) {
            this.complete(((Player)e.getWhoClicked()).getPlayer());
        }
    }
}
