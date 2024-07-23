package net.hypixel.skyblock.features.dungeons.chest;

import java.util.HashMap;
import java.util.Iterator;
import net.minecraft.server.v1_8_R3.Packet;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import net.minecraft.server.v1_8_R3.PacketPlayOutBlockAction;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.BlockPosition;
import org.bukkit.Location;
import net.hypixel.skyblock.util.SUtil;
import org.bukkit.Material;
import net.hypixel.skyblock.util.Sputnik;
import net.hypixel.skyblock.gui.DungeonsLootGUI;
import org.bukkit.plugin.Plugin;
import org.bukkit.entity.Player;
import org.bukkit.entity.Entity;
import java.util.Collection;
import org.bukkit.scheduler.BukkitRunnable;
import net.hypixel.skyblock.SkyBlock;
import org.bukkit.inventory.ItemStack;
import org.bukkit.block.Block;
import java.util.Map;

public class ItemChest
{
    public static final Map<Block, ItemChest> ITEM_CHEST_CACHE;
    private boolean opened;
    private boolean locked;
    private final ItemStack type;
    private final byte state;
    private final Block chest;
    private final SkyBlock sse;
    
    public ItemChest(final ItemStack type, final Block chest, final byte state) {
        this.sse = SkyBlock.getPlugin();
        this.type = type;
        this.state = state;
        this.locked = false;
        this.opened = false;
        this.chest = chest;
        ItemChest.ITEM_CHEST_CACHE.put((Object)chest, (Object)this);
        new BukkitRunnable() {
            public void run() {
                if (!ItemChest.ITEM_CHEST_CACHE.containsKey((Object)chest)) {
                    this.cancel();
                    return;
                }
                final Collection<Entity> ce = (Collection<Entity>)chest.getWorld().getNearbyEntities(chest.getLocation(), 10.0, 10.0, 10.0);
                ce.removeIf(entity -> !(entity instanceof Player));
                if (ce.size() > 0) {
                    ItemChest.this.show();
                }
                else {
                    ItemChest.this.hide();
                }
            }
        }.runTaskTimer((Plugin)this.sse, 0L, 1L);
    }
    
    public void open(final Player opener) {
        if (!this.opened && !this.locked) {
            new DungeonsLootGUI(this.type, this.chest).open(opener);
            this.opened = true;
            return;
        }
        if (this.locked) {
            opener.sendMessage(Sputnik.trans("&cThat chest is locked!"));
            return;
        }
        if (this.opened) {
            opener.sendMessage(Sputnik.trans("&cThe chest has already been searched!"));
        }
    }
    
    public void destroy() {
        this.chest.setType(Material.AIR);
        ItemChest.ITEM_CHEST_CACHE.remove((Object)this.chest);
    }
    
    public void hide() {
        this.chest.getLocation().getBlock().setType(Material.AIR);
    }
    
    public void show() {
        if (this.chest.getType() != Material.CHEST) {
            this.chest.getLocation().getBlock().setType(Material.CHEST);
            this.chest.setData(this.state);
            final Location chestLocation = this.chest.getLocation();
            if (this.isOpened()) {
                SUtil.delay(() -> {
                    final BlockPosition pos = new BlockPosition(chestLocation.getBlockX(), chestLocation.getBlockY(), chestLocation.getBlockZ());
                    final PacketPlayOutBlockAction packet = new PacketPlayOutBlockAction(pos, (net.minecraft.server.v1_8_R3.Block)Blocks.CHEST, 1, 1);
                    for (final Player p : chestLocation.getWorld().getPlayers()) {
                        ((CraftPlayer)p).getHandle().playerConnection.sendPacket((Packet)packet);
                    }
                }, 1L);
            }
        }
    }
    
    public boolean isOpened() {
        return this.opened;
    }
    
    public void setOpened(final boolean opened) {
        this.opened = opened;
    }
    
    public boolean isLocked() {
        return this.locked;
    }
    
    public void setLocked(final boolean locked) {
        this.locked = locked;
    }
    
    public ItemStack getType() {
        return this.type;
    }
    
    static {
        ITEM_CHEST_CACHE = (Map)new HashMap();
    }
}
