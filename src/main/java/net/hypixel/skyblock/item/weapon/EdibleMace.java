package net.hypixel.skyblock.item.weapon;

import java.util.HashMap;
import net.hypixel.skyblock.util.DefenseReplacement;
import net.hypixel.skyblock.util.ManaReplacement;
import net.hypixel.skyblock.Repeater;
import org.bukkit.Sound;
import net.hypixel.skyblock.util.SUtil;
import net.hypixel.skyblock.user.PlayerUtils;
import net.hypixel.skyblock.user.PlayerStatistics;
import net.hypixel.skyblock.item.SItem;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;
import net.hypixel.skyblock.item.SpecificItemType;
import net.hypixel.skyblock.item.GenericItemType;
import net.hypixel.skyblock.item.Rarity;
import java.util.UUID;
import java.util.Map;
import net.hypixel.skyblock.item.Ability;
import net.hypixel.skyblock.item.MaterialFunction;
import net.hypixel.skyblock.item.ToolStatistics;

public class EdibleMace implements ToolStatistics, MaterialFunction, Ability
{
    public static final Map<UUID, Boolean> edibleMace;
    
    @Override
    public int getBaseDamage() {
        return 125;
    }
    
    @Override
    public double getBaseStrength() {
        return 25.0;
    }
    
    @Override
    public String getDisplayName() {
        return "Edible Mace";
    }
    
    @Override
    public Rarity getRarity() {
        return Rarity.RARE;
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
    public String getLore() {
        return null;
    }
    
    @Override
    public String getAbilityName() {
        return "ME SMASH HEAD";
    }
    
    @Override
    public String getAbilityDescription() {
        return "Your next attack deals " + (Object)ChatColor.RED + "double " + (Object)ChatColor.RED + "damage " + (Object)ChatColor.GRAY + "and weakens animals, making them deal " + (Object)ChatColor.RED + "-35% " + (Object)ChatColor.GRAY + "damage for " + (Object)ChatColor.GREEN + "30 " + (Object)ChatColor.GRAY + "seconds.";
    }
    
    @Override
    public void onAbilityUse(final Player player1, final SItem sItem) {
        if (!EdibleMace.edibleMace.containsKey((Object)player1.getUniqueId())) {
            EdibleMace.edibleMace.put((Object)player1.getUniqueId(), (Object)false);
        }
        if (EdibleMace.edibleMace.containsKey((Object)player1.getUniqueId())) {
            if (!(boolean)EdibleMace.edibleMace.get((Object)player1.getUniqueId())) {
                final int manaPool = SUtil.blackMagic(((PlayerStatistics)PlayerUtils.STATISTICS_CACHE.get((Object)player1.getUniqueId())).getIntelligence().addAll() + 100.0);
                final int cost = PlayerUtils.getFinalManaCost(player1, sItem, 100);
                final boolean take = PlayerUtils.takeMana(player1, cost);
                if (!take) {
                    player1.playSound(player1.getLocation(), Sound.ENDERMAN_TELEPORT, 1.0f, -4.0f);
                    final long c = System.currentTimeMillis();
                    Repeater.MANA_REPLACEMENT_MAP.put((Object)player1.getUniqueId(), (Object)new ManaReplacement() {
                        @Override
                        public String getReplacement() {
                            return "" + (Object)ChatColor.RED + (Object)ChatColor.BOLD + "NOT ENOUGH MANA";
                        }
                        
                        @Override
                        public long getEnd() {
                            return c + 1500L;
                        }
                    });
                    return;
                }
                final long c = System.currentTimeMillis();
                Repeater.DEFENSE_REPLACEMENT_MAP.put((Object)player1.getUniqueId(), (Object)new DefenseReplacement() {
                    @Override
                    public String getReplacement() {
                        return (Object)ChatColor.AQUA + "-" + cost + " Mana (" + (Object)ChatColor.GOLD + EdibleMace.this.getAbilityName() + (Object)ChatColor.AQUA + ")";
                    }
                    
                    @Override
                    public long getEnd() {
                        return c + 2000L;
                    }
                });
                EdibleMace.edibleMace.put((Object)player1.getUniqueId(), (Object)true);
            }
            else {
                player1.sendMessage((Object)ChatColor.RED + "The ability is already active!");
            }
        }
    }
    
    @Override
    public int getAbilityCooldownTicks() {
        return 0;
    }
    
    @Override
    public int getManaCost() {
        return 0;
    }
    
    static {
        edibleMace = (Map)new HashMap();
    }
}
