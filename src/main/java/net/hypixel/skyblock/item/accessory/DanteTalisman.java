package net.hypixel.skyblock.item.accessory;

import org.bukkit.ChatColor;
import java.util.ArrayList;
import java.util.List;
import net.hypixel.skyblock.item.SpecificItemType;
import net.hypixel.skyblock.item.GenericItemType;
import net.hypixel.skyblock.item.Rarity;

public class DanteTalisman implements AccessoryStatistics, AccessoryFunction
{
    @Override
    public String getURL() {
        return "cf92982f1a302310643a20ce51623f8199b7545e70dc6b93ed6bd61dc42ff213";
    }
    
    @Override
    public String getDisplayName() {
        return "Dante Talisman";
    }
    
    @Override
    public Rarity getRarity() {
        return Rarity.COMMON;
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
    public List<String> getListLore() {
        final ArrayList<String> lore = (ArrayList<String>)new ArrayList();
        lore.add((Object)((Object)ChatColor.GRAY + "Official medal proving your"));
        lore.add((Object)((Object)ChatColor.GRAY + "eternal support for Dante."));
        lore.add((Object)((Object)ChatColor.GRAY + "&7"));
        lore.add((Object)((Object)ChatColor.GRAY + "When damaged, you"));
        lore.add((Object)((Object)ChatColor.GRAY + "occasionally spit straight"));
        lore.add((Object)((Object)ChatColor.GRAY + "facts into the chat."));
        lore.add((Object)((Object)ChatColor.GRAY + "&7"));
        lore.add((Object)((Object)ChatColor.DARK_GRAY + "'dante best' - Goons, 2021 - 2021"));
        return (List<String>)lore;
    }
}
