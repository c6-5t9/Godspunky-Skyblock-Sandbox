package net.hypixel.skyblock.item;

import net.hypixel.skyblock.util.SUtil;
import org.bukkit.inventory.ItemStack;
import org.bukkit.ChatColor;

public enum ItemCategory
{
    WEAPONS("Weapons", ChatColor.GOLD, (short)1), 
    ARMOR("Armor", ChatColor.AQUA, (short)11), 
    ACCESSORIES("Accessories", ChatColor.DARK_GREEN, (short)13), 
    CONSUMABLES("Consumables", ChatColor.RED, (short)14), 
    BLOCKS("Blocks", ChatColor.YELLOW, (short)12), 
    TOOLS_MISC("Tools & Misc", ChatColor.LIGHT_PURPLE, (short)10);
    
    private final String name;
    private final ChatColor text;
    private final short item;
    
    private ItemCategory(final String name, final ChatColor text, final short item) {
        this.name = name;
        this.text = text;
        this.item = item;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getColoredName() {
        return (Object)this.text + this.name;
    }
    
    public ItemStack getStainedGlassPane() {
        return SUtil.createColoredStainedGlassPane(this.item, " ");
    }
}
