package net.hypixel.skyblock.item.weapon;

import net.hypixel.skyblock.item.SpecificItemType;
import org.bukkit.ChatColor;
import net.hypixel.skyblock.item.GenericItemType;
import net.hypixel.skyblock.item.Rarity;
import net.hypixel.skyblock.item.MaterialFunction;
import net.hypixel.skyblock.item.ToolStatistics;

public class PoochSword implements ToolStatistics, MaterialFunction
{
    @Override
    public int getBaseDamage() {
        return 120;
    }
    
    @Override
    public double getBaseStrength() {
        return 20.0;
    }
    
    @Override
    public double getBaseCritDamage() {
        return 0.5;
    }
    
    @Override
    public double getBaseSpeed() {
        return 0.05;
    }
    
    @Override
    public String getDisplayName() {
        return "Pooch Sword";
    }
    
    @Override
    public Rarity getRarity() {
        return Rarity.LEGENDARY;
    }
    
    @Override
    public GenericItemType getType() {
        return GenericItemType.WEAPON;
    }
    
    @Override
    public String getLore() {
        return ChatColor.translateAlternateColorCodes('&', "Deal &c+1 Damage &7per &c50 &cmax Health \u2764&7. Receive &a-20% &7damage from wolves. &7Gain &c+150\u2741 Strength &7against wolves.");
    }
    
    @Override
    public SpecificItemType getSpecificType() {
        return SpecificItemType.SWORD;
    }
}
