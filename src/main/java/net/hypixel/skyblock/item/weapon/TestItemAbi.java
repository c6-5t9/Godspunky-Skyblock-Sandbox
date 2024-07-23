package net.hypixel.skyblock.item.weapon;

import net.hypixel.skyblock.item.SpecificItemType;
import net.hypixel.skyblock.item.GenericItemType;
import net.hypixel.skyblock.item.Rarity;
import java.util.Iterator;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.Location;
import net.hypixel.skyblock.item.SItem;
import org.bukkit.entity.Player;
import net.hypixel.skyblock.item.Ability;
import net.hypixel.skyblock.item.MaterialFunction;
import net.hypixel.skyblock.item.ToolStatistics;

public class TestItemAbi implements ToolStatistics, MaterialFunction, Ability
{
    @Override
    public String getAbilityName() {
        return "Admin Item!";
    }
    
    @Override
    public String getAbilityDescription() {
        return "Active";
    }
    
    @Override
    public int getAbilityCooldownTicks() {
        return 0;
    }
    
    @Override
    public void onAbilityUse(final Player player, final SItem sItem) {
        SItem.etherWarpTeleportation(player, sItem);
    }
    
    public void cylinder(final Location loc, final int r) {
        final int cx = loc.getBlockX();
        final int cy = loc.getBlockY();
        final int cz = loc.getBlockZ();
        final World w = loc.getWorld();
        final int rSquared = r * r;
        for (int x = cx - r; x <= cx + r; ++x) {
            for (int z = cz - r; z <= cz + r; ++z) {
                if ((cx - x) * (cx - x) + (cz - z) * (cz - z) <= rSquared) {
                    final Location l = new Location(w, (double)x, (double)cy, (double)z);
                    this.sendPacket(w, l);
                }
            }
        }
    }
    
    public void sendPacket(final World w, final Location l) {
        for (final Player p : w.getPlayers()) {
            p.sendBlockChange(l, Material.BEDROCK, (byte)0);
        }
    }
    
    @Override
    public int getManaCost() {
        return 0;
    }
    
    @Override
    public String getDisplayName() {
        return "Test Item";
    }
    
    @Override
    public int getBaseDamage() {
        return 1;
    }
    
    @Override
    public Rarity getRarity() {
        return Rarity.EXCLUSIVE;
    }
    
    @Override
    public GenericItemType getType() {
        return GenericItemType.WEAPON;
    }
    
    @Override
    public SpecificItemType getSpecificType() {
        return SpecificItemType.SWORD;
    }
}
