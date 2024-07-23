package net.hypixel.skyblock.item.oddities;

import net.hypixel.skyblock.item.SItem;
import org.bukkit.entity.Player;
import net.hypixel.skyblock.util.Sputnik;
import net.hypixel.skyblock.item.GenericItemType;
import net.hypixel.skyblock.item.Rarity;
import net.hypixel.skyblock.item.Ability;
import net.hypixel.skyblock.item.MaterialFunction;
import net.hypixel.skyblock.item.SkullStatistics;

public class EtherwarpTranscoder implements SkullStatistics, MaterialFunction, Ability
{
    @Override
    public String getURL() {
        return "6e425e5689a9c855f2eb9f1d124f5596c46e1d7731748c0238718d0e6a4da1a8";
    }
    
    @Override
    public String getDisplayName() {
        return "Etherwarp Transcoder";
    }
    
    @Override
    public Rarity getRarity() {
        return Rarity.LEGENDARY;
    }
    
    @Override
    public GenericItemType getType() {
        return GenericItemType.ITEM;
    }
    
    @Override
    public String getAbilityName() {
        return "Ether Transmission";
    }
    
    @Override
    public String getAbilityDescription() {
        return Sputnik.trans("&7Teleport to your targetted block up to &a57 blocks &7away.");
    }
    
    @Override
    public String getLore() {
        return Sputnik.trans("&7Apply this Transcoder to your &9Aspect of the End &7or &5Aspect of the Void &7in an Anvil.");
    }
    
    @Override
    public int getAbilityCooldownTicks() {
        return 0;
    }
    
    @Override
    public int getManaCost() {
        return 250;
    }
    
    @Override
    public boolean displayUsage() {
        return true;
    }
    
    @Override
    public boolean isStackable() {
        return false;
    }
    
    @Override
    public boolean requirementsUse(final Player player, final SItem sItem) {
        return !SItem.isAbleToDoEtherWarpTeleportation(player, sItem);
    }
    
    @Override
    public String getAbilityReq() {
        return "&cNo block in range!";
    }
    
    @Override
    public void onAbilityUse(final Player player, final SItem sItem) {
        SItem.etherWarpTeleportation(player, sItem);
    }
}
