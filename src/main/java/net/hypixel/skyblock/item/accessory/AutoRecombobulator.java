package net.hypixel.skyblock.item.accessory;

import net.hypixel.skyblock.util.SUtil;
import net.hypixel.skyblock.item.SItem;
import org.bukkit.entity.Player;
import org.bukkit.entity.Entity;
import net.hypixel.skyblock.util.Sputnik;
import net.hypixel.skyblock.item.SpecificItemType;
import net.hypixel.skyblock.item.GenericItemType;
import net.hypixel.skyblock.item.Rarity;
import net.hypixel.skyblock.item.MaterialFunction;

public class AutoRecombobulator implements AccessoryStatistics, MaterialFunction
{
    @Override
    public String getURL() {
        return "5dff8dbbab15bfbb11e23b1f50b34ef548ad9832c0bd7f5a13791adad0057e1b";
    }
    
    @Override
    public String getDisplayName() {
        return "Auto Recombobulator";
    }
    
    @Override
    public Rarity getRarity() {
        return Rarity.LEGENDARY;
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
    public boolean isStackable() {
        return false;
    }
    
    @Override
    public String getLore() {
        return Sputnik.trans("Grants a &a1% &7chance to automatically recombobulate mob drops.");
    }
    
    @Override
    public void onKill(final Entity damaged, final Player damager, final SItem item) {
        if (SUtil.random(1, 100) == 1) {
            if (item.getType().getStatistics().getType() == GenericItemType.PET) {
                return;
            }
            damager.sendMessage(Sputnik.trans("&eYour &6Auto-Recombobulator &erecombobulated " + item.getFullName() + "&e!"));
            item.setRecombobulated(true);
        }
    }
}
