package net.hypixel.skyblock.util;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.entity.Player;
import net.hypixel.skyblock.api.nbt.NBTExplorer;
import org.bukkit.OfflinePlayer;

public class SkySimAPI
{
    public static void requestPlayerAPI(final OfflinePlayer player) {
        final Player p = player.getPlayer();
        final PlayerInventory inv = p.getInventory();
        final StringBuilder sb = new StringBuilder();
        for (int i = 39; i >= 0; --i) {
            final ItemStack stack = inv.getItem(i);
            if (stack != null) {
                sb.append((Object)NBTExplorer.NBTSaver(stack));
            }
        }
        SLog.info(sb.toString());
    }
}
