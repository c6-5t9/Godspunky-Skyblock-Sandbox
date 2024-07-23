package net.hypixel.skyblock.item.accessory;

import net.hypixel.skyblock.item.SpecificItemType;
import net.hypixel.skyblock.item.GenericItemType;
import java.util.Arrays;
import net.hypixel.skyblock.util.Sputnik;
import java.util.List;
import net.hypixel.skyblock.item.Rarity;

public class ArtifactOfControl implements AccessoryStatistics
{
    @Override
    public String getDisplayName() {
        return "Artifact of Control";
    }
    
    @Override
    public Rarity getRarity() {
        return Rarity.LEGENDARY;
    }
    
    @Override
    public List<String> getListLore() {
        return (List<String>)Arrays.asList((Object[])new String[] { Sputnik.trans3("&7Holding this artifact will", "&7grant you &a2x&7 voting power in", "&7Mayor Elections!") });
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
        return "31f748a43f3b3ada04f44d5d290a8b9bf583d93e1c83ab93c60c4dec1fde1c5c";
    }
}
