package net.hypixel.skyblock.item.accessory;

import net.hypixel.skyblock.item.SpecificItemType;
import net.hypixel.skyblock.item.GenericItemType;
import java.util.Arrays;
import net.hypixel.skyblock.util.Sputnik;
import java.util.List;
import net.hypixel.skyblock.item.Rarity;

public class CandyRelic implements AccessoryStatistics
{
    @Override
    public String getDisplayName() {
        return "Candy Relic";
    }
    
    @Override
    public Rarity getRarity() {
        return Rarity.LEGENDARY;
    }
    
    @Override
    public List<String> getListLore() {
        return (List<String>)Arrays.asList((Object[])new String[] { Sputnik.trans4("&7Increases the drop chance of", "&7candies from mobs by &a20%", "&7during the &6Spooky", "&6Festival&7.") });
    }
    
    @Override
    public GenericItemType getType() {
        return GenericItemType.ACCESSORY;
    }
    
    @Override
    public SpecificItemType getSpecificType() {
        return SpecificItemType.ACCESSORY;
    }
    
    @Override
    public String getURL() {
        return "94ff88f21e0149c4de89aa31c7dc0cd3429cb6c9ab237bbf94bf60910f789b99";
    }
}
