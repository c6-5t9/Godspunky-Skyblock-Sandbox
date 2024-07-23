package net.hypixel.skyblock.item.accessory;

import java.util.Arrays;
import net.hypixel.skyblock.util.Sputnik;
import java.util.List;
import net.hypixel.skyblock.item.Rarity;

public class GoldGiftTalisman implements AccessoryStatistics
{
    @Override
    public String getDisplayName() {
        return "Gold Gift";
    }
    
    @Override
    public Rarity getRarity() {
        return Rarity.LEGENDARY;
    }
    
    @Override
    public List<String> getListLore() {
        return (List<String>)Arrays.asList((Object[])new String[] { Sputnik.trans("&7Grants &a+25% &6Coin &7and", "&7&aEXP &7rewards from gifts.") });
    }
    
    @Override
    public String getURL() {
        return "abd98792dd92d9719894341ac9012a584c4428558fd2c712f78e5f0d4da85470";
    }
}
