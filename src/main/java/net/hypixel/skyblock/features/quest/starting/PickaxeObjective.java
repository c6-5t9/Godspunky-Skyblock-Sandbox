package net.hypixel.skyblock.features.quest.starting;

import org.bukkit.event.EventHandler;
import net.hypixel.skyblock.item.SMaterial;
import net.hypixel.skyblock.event.SkyBlockCraftEvent;
import net.hypixel.skyblock.features.quest.Objective;

public class PickaxeObjective extends Objective
{
    public PickaxeObjective() {
        super("craft_pickaxe", "Craft a wood pickaxe");
    }
    
    @EventHandler
    public void onCraft(final SkyBlockCraftEvent e) {
        if (!this.isThisObjective(e.getPlayer())) {
            return;
        }
        if (e.getRecipe().getResult().getType().equals((Object)SMaterial.WOOD_PICKAXE)) {
            this.complete(e.getPlayer());
        }
    }
}
