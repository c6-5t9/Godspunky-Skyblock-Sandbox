package net.hypixel.skyblock.item.oddities;

import java.util.Collections;
import net.hypixel.skyblock.util.SUtil;
import org.bukkit.ChatColor;
import java.util.List;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.hypixel.skyblock.item.SItem;
import net.hypixel.skyblock.item.GenericItemType;
import net.hypixel.skyblock.item.Rarity;
import net.hypixel.skyblock.item.ItemData;
import net.hypixel.skyblock.item.MaterialFunction;
import net.hypixel.skyblock.item.SkullStatistics;

public class BagOfCoins implements SkullStatistics, MaterialFunction, ItemData
{
    @Override
    public String getDisplayName() {
        return "Bag of Coins";
    }
    
    @Override
    public Rarity getRarity() {
        return Rarity.COMMON;
    }
    
    @Override
    public GenericItemType getType() {
        return GenericItemType.ITEM;
    }
    
    @Override
    public void onInstanceUpdate(final SItem instance) {
        final long coins = instance.getDataLong("coins");
        if (10000L > coins) {
            instance.setRarity(Rarity.COMMON, false);
        }
        else if (100000L > coins) {
            instance.setRarity(Rarity.UNCOMMON, false);
        }
        else if (250000L > coins) {
            instance.setRarity(Rarity.RARE, false);
        }
        else if (4000000L > coins) {
            instance.setRarity(Rarity.EPIC, false);
        }
        else if (10000000L > coins) {
            instance.setRarity(Rarity.LEGENDARY, false);
        }
        else if (25000000L > coins) {
            instance.setRarity(Rarity.MYTHIC, false);
        }
        else if (100000000L > coins) {
            instance.setRarity(Rarity.SUPREME, false);
        }
        else if (500000000L > coins) {
            instance.setRarity(Rarity.SPECIAL, false);
        }
        else {
            instance.setRarity(Rarity.VERY_SPECIAL, false);
        }
    }
    
    @Override
    public NBTTagCompound getData() {
        final NBTTagCompound compound = new NBTTagCompound();
        compound.setLong("coins", 1L);
        return compound;
    }
    
    @Override
    public List<String> getDataLore(final String key, final Object value) {
        if (!key.equals((Object)"coins")) {
            return null;
        }
        return (List<String>)Collections.singletonList((Object)((Object)ChatColor.GOLD + "Contents: " + (Object)ChatColor.YELLOW + SUtil.commaify((long)value) + " coins"));
    }
    
    @Override
    public String getURL() {
        return "8381c529d52e03cd74c3bf38bb6ba3fde1337ae9bf50332faa889e0a28e8081f";
    }
}
