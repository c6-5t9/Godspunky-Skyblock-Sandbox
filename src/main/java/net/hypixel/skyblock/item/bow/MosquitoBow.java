package net.hypixel.skyblock.item.bow;

import com.google.common.util.concurrent.AtomicDouble;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.metadata.FixedMetadataValue;
import net.hypixel.skyblock.SkyBlock;
import net.hypixel.skyblock.util.DefenseReplacement;
import net.hypixel.skyblock.util.ManaReplacement;
import net.hypixel.skyblock.Repeater;
import org.bukkit.Sound;
import net.hypixel.skyblock.util.SUtil;
import net.hypixel.skyblock.user.PlayerUtils;
import net.hypixel.skyblock.user.PlayerStatistics;
import org.bukkit.event.entity.EntityShootBowEvent;
import net.hypixel.skyblock.item.AbilityActivation;
import net.hypixel.skyblock.item.SpecificItemType;
import net.hypixel.skyblock.item.GenericItemType;
import net.hypixel.skyblock.item.Rarity;
import net.hypixel.skyblock.item.SItem;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;
import net.hypixel.skyblock.item.Ability;
import net.hypixel.skyblock.item.ToolStatistics;

public class MosquitoBow implements ToolStatistics, BowFunction, Ability
{
    @Override
    public String getAbilityName() {
        return "Nasty Bite";
    }
    
    @Override
    public String getAbilityDescription() {
        return "Fully charged shots while sneaking. Costs " + (Object)ChatColor.AQUA + "11%  " + (Object)ChatColor.GRAY + "of max Mana, deals " + (Object)ChatColor.RED + "+19% " + (Object)ChatColor.GRAY + "more damage, heal for " + (Object)ChatColor.GREEN + "2x " + (Object)ChatColor.GRAY + "the Mana cost";
    }
    
    @Override
    public void onAbilityUse(final Player player, final SItem sItem) {
    }
    
    @Override
    public int getAbilityCooldownTicks() {
        return 0;
    }
    
    @Override
    public int getManaCost() {
        return 0;
    }
    
    @Override
    public String getDisplayName() {
        return "Mosquito Bow";
    }
    
    @Override
    public Rarity getRarity() {
        return Rarity.LEGENDARY;
    }
    
    @Override
    public GenericItemType getType() {
        return GenericItemType.RANGED_WEAPON;
    }
    
    @Override
    public SpecificItemType getSpecificType() {
        return SpecificItemType.BOW;
    }
    
    @Override
    public AbilityActivation getAbilityActivation() {
        return AbilityActivation.NO_ACTIVATION;
    }
    
    @Override
    public int getBaseDamage() {
        return 251;
    }
    
    @Override
    public double getBaseStrength() {
        return 151.0;
    }
    
    @Override
    public double getBaseCritDamage() {
        return 0.39;
    }
    
    @Override
    public void onBowShoot(final SItem bow, final EntityShootBowEvent e) {
        final Player player = (Player)e.getEntity();
        if (!player.isSneaking()) {
            return;
        }
        if (e.getForce() != 1.0f) {
            return;
        }
        final int manaPool = SUtil.blackMagic(((PlayerStatistics)PlayerUtils.STATISTICS_CACHE.get((Object)player.getUniqueId())).getIntelligence().addAll() + 100.0);
        final int cost = PlayerUtils.getFinalManaCost(player, bow, (int)(manaPool * 0.11));
        final boolean take = PlayerUtils.takeMana(player, cost);
        if (!take) {
            player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1.0f, -4.0f);
            final long c = System.currentTimeMillis();
            Repeater.MANA_REPLACEMENT_MAP.put((Object)player.getUniqueId(), (Object)new ManaReplacement() {
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
        Repeater.DEFENSE_REPLACEMENT_MAP.put((Object)player.getUniqueId(), (Object)new DefenseReplacement() {
            @Override
            public String getReplacement() {
                return (Object)ChatColor.AQUA + "-" + cost + " Mana (" + (Object)ChatColor.GOLD + MosquitoBow.this.getAbilityName() + (Object)ChatColor.AQUA + ")";
            }
            
            @Override
            public long getEnd() {
                return c + 2000L;
            }
        });
        player.setHealth(Math.min(player.getMaxHealth(), player.getHealth() + manaPool * 0.11 * 2.0));
        e.getProjectile().setMetadata("bite", (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)true));
    }
    
    @Override
    public void onBowHit(final Entity hit, final Player shooter, final Arrow arrow, final SItem weapon, final AtomicDouble finalDamage) {
        if (!arrow.hasMetadata("bite")) {
            return;
        }
        finalDamage.set(finalDamage.get() + finalDamage.get() * 0.19);
    }
}
