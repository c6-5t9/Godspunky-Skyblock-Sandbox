package net.hypixel.skyblock.event;

import org.bukkit.entity.Player;
import net.hypixel.skyblock.item.Recipe;

public class SkyBlockCraftEvent extends SkyblockEvent
{
    private final Recipe recipe;
    private final Player player;
    
    public Recipe getRecipe() {
        return this.recipe;
    }
    
    public Player getPlayer() {
        return this.player;
    }
    
    public SkyBlockCraftEvent(final Recipe recipe, final Player player) {
        this.recipe = recipe;
        this.player = player;
    }
}
