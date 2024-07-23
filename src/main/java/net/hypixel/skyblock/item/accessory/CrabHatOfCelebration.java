package net.hypixel.skyblock.item.accessory;

import net.hypixel.skyblock.item.SpecificItemType;
import net.hypixel.skyblock.item.GenericItemType;
import java.util.Arrays;
import net.hypixel.skyblock.util.Sputnik;
import java.util.List;
import net.hypixel.skyblock.item.Rarity;

public class CrabHatOfCelebration implements AccessoryStatistics
{
    @Override
    public String getDisplayName() {
        return "Red Crab Hat of Celebration";
    }
    
    @Override
    public Rarity getRarity() {
        return Rarity.SPECIAL;
    }
    
    @Override
    public double getBaseMagicFind() {
        return 0.01;
    }
    
    @Override
    public List<String> getListLore() {
        return (List<String>)Arrays.asList((Object[])new String[] { Sputnik.trans5("&6Ability: Party Time", "&7Gain &b+1 Intelligence&7 while", "&7on your head for each SkyBlock", "&7year you've been playing.", "&7") });
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
        return "67df75185a0dca0b5cede4064b787a3a14004fdd4dca4942246db00c0c03e51b";
    }
}
