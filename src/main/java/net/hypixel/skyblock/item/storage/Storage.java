package net.hypixel.skyblock.item.storage;

import java.util.HashMap;
import net.hypixel.skyblock.item.GenericItemType;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import java.util.UUID;
import java.util.Map;
import net.hypixel.skyblock.item.Untradeable;
import net.hypixel.skyblock.item.ItemData;
import net.hypixel.skyblock.item.MaterialFunction;
import net.hypixel.skyblock.item.MaterialStatistics;

public abstract class Storage implements MaterialStatistics, MaterialFunction, ItemData, Untradeable
{
    private static final Map<UUID, Inventory> OPENED_STORAGE_UNITS;
    
    public static Inventory getCurrentStorageOpened(final Player player) {
        return (Inventory)Storage.OPENED_STORAGE_UNITS.get((Object)player.getUniqueId());
    }
    
    public static void closeCurrentStorage(final Player player) {
        Storage.OPENED_STORAGE_UNITS.remove((Object)player.getUniqueId());
    }
    
    @Override
    public boolean isStackable() {
        return false;
    }
    
    @Override
    public void onInteraction(final PlayerInteractEvent e) {
        e.getPlayer().sendMessage((Object)ChatColor.RED + "This item is currently disabled due to an exploit!");
    }
    
    @Override
    public NBTTagCompound getData() {
        return new NBTTagCompound();
    }
    
    @Override
    public GenericItemType getType() {
        return GenericItemType.ITEM;
    }
    
    public abstract int getSlots();
    
    static {
        OPENED_STORAGE_UNITS = (Map)new HashMap();
    }
}
