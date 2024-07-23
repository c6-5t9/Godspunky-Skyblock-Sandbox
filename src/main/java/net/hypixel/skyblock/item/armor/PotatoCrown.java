package net.hypixel.skyblock.item.armor;

import org.bukkit.ChatColor;
import java.util.ArrayList;
import java.util.List;
import net.hypixel.skyblock.item.SpecificItemType;
import net.hypixel.skyblock.item.GenericItemType;
import net.hypixel.skyblock.item.Rarity;
import net.hypixel.skyblock.item.ToolStatistics;
import net.hypixel.skyblock.item.MaterialFunction;

public class PotatoCrown implements MaterialFunction, ToolStatistics
{
    @Override
    public double getBaseSpeed() {
        return 1.0;
    }
    
    @Override
    public String getDisplayName() {
        return "Potato Crown";
    }
    
    @Override
    public Rarity getRarity() {
        return Rarity.LEGENDARY;
    }
    
    @Override
    public GenericItemType getType() {
        return GenericItemType.ARMOR;
    }
    
    @Override
    public SpecificItemType getSpecificType() {
        return SpecificItemType.HELMET;
    }
    
    @Override
    public List<String> getListLore() {
        final List<String> lore = (List<String>)new ArrayList();
        lore.add((Object)this.formatLore("&6Ability: Potato Crown"));
        lore.add((Object)this.formatLore("Bestowed by Hypixel to the winner of"));
        lore.add((Object)this.formatLore("the Great Potato War."));
        lore.add((Object)this.formatLore(" "));
        lore.add((Object)this.formatLore("&dAll men can see the tactics whereby I"));
        lore.add((Object)this.formatLore("&dconquer, but what none can see is the"));
        lore.add((Object)this.formatLore("&dstrategy out of which victory is"));
        lore.add((Object)this.formatLore("&devolved."));
        lore.add((Object)this.formatLore("&8- Sun Tzu, The Art of War"));
        return lore;
    }
    
    private String formatLore(final String lore) {
        return ChatColor.translateAlternateColorCodes('&', lore);
    }
}
