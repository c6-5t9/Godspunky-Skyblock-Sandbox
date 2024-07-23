package net.hypixel.skyblock.item.accessory;

import java.util.HashMap;
import com.google.common.util.concurrent.AtomicDouble;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import net.hypixel.skyblock.item.SItem;
import net.hypixel.skyblock.item.SpecificItemType;
import net.hypixel.skyblock.item.GenericItemType;
import net.hypixel.skyblock.item.Rarity;
import java.util.UUID;
import java.util.Map;

public class TarantulaTalisman implements AccessoryStatistics, AccessoryFunction
{
    private static final Map<UUID, Integer> HITS;
    
    @Override
    public String getDisplayName() {
        return "Tarantula Talisman";
    }
    
    @Override
    public Rarity getRarity() {
        return Rarity.EPIC;
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
    public String getURL() {
        return "442cf8ce487b78fa203d56cf01491434b4c33e5d236802c6d69146a51435b03d";
    }
    
    @Override
    public void onDamageInInventory(final SItem weapon, final Player damager, final Entity damaged, final SItem accessory, final AtomicDouble damage) {
        TarantulaTalisman.HITS.put((Object)damager.getUniqueId(), (Object)((int)TarantulaTalisman.HITS.getOrDefault((Object)damager.getUniqueId(), (Object)0) + 1));
        if ((int)TarantulaTalisman.HITS.get((Object)damager.getUniqueId()) >= 10) {
            damage.addAndGet(damage.get() * 0.1);
            TarantulaTalisman.HITS.remove((Object)damager.getUniqueId());
        }
    }
    
    static {
        HITS = (Map)new HashMap();
    }
}
