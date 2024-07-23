package net.hypixel.skyblock.item.weapon;

import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.entity.Player;
import java.util.Collections;
import net.hypixel.skyblock.user.User;
import org.bukkit.Bukkit;
import java.util.UUID;
import java.util.Arrays;
import org.bukkit.ChatColor;
import java.util.List;
import net.hypixel.skyblock.item.SpecificItemType;
import net.hypixel.skyblock.item.GenericItemType;
import net.hypixel.skyblock.item.Rarity;
import net.hypixel.skyblock.item.Ownable;
import net.hypixel.skyblock.item.MaterialFunction;
import net.hypixel.skyblock.item.ToolStatistics;

public class EmeraldBlade implements ToolStatistics, MaterialFunction, Ownable
{
    @Override
    public String getDisplayName() {
        return "Emerald Blade";
    }
    
    @Override
    public Rarity getRarity() {
        return Rarity.EPIC;
    }
    
    @Override
    public GenericItemType getType() {
        return GenericItemType.WEAPON;
    }
    
    @Override
    public SpecificItemType getSpecificType() {
        return SpecificItemType.SWORD;
    }
    
    @Override
    public int getBaseDamage() {
        return 130;
    }
    
    @Override
    public List<String> getListLore() {
        return (List<String>)Arrays.asList((Object[])new String[] { "A powerful blade made from pure", (Object)ChatColor.DARK_GREEN + "Emeralds" + (Object)ChatColor.GRAY + ". This blade becomes", "stronger as you carry more", (Object)ChatColor.GOLD + "coins" + (Object)ChatColor.GRAY + " in your purse." });
    }
    
    @Override
    public List<String> getDataLore(final String key, final Object value) {
        if (!key.equals((Object)"owner")) {
            return null;
        }
        final Player player = Bukkit.getPlayer(UUID.fromString(String.valueOf(value)));
        if (player == null) {
            return null;
        }
        final User user = User.getUser(player.getUniqueId());
        final long cap = 35000000000L;
        final double d1 = Math.pow((double)Math.min(35000000000L, User.getUser(player.getUniqueId()).getCoins()), 0.25);
        final double finald = 2.5 * d1;
        final double dmgfin = Math.round(finald / 10.0) * 10.0;
        return (List<String>)Collections.singletonList((Object)((Object)ChatColor.GRAY + "Current Damage Bonus: " + (Object)ChatColor.GREEN + dmgfin));
    }
    
    @Override
    public NBTTagCompound getData() {
        return new NBTTagCompound();
    }
}
