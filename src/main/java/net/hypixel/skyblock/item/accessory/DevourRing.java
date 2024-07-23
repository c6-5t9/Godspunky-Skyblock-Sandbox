package net.hypixel.skyblock.item.accessory;

import java.util.HashMap;
import com.google.common.util.concurrent.AtomicDouble;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import net.hypixel.skyblock.item.SItem;
import net.hypixel.skyblock.item.Rarity;
import org.bukkit.ChatColor;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.Map;

public class DevourRing implements AccessoryStatistics, AccessoryFunction
{
    private static final Map<UUID, Integer> HITS;
    
    @Override
    public String getURL() {
        return "f06706eecb2d558ace27abda0b0b7b801d36d17dd7a890a9520dbe522374f8a6";
    }
    
    @Override
    public List<String> getListLore() {
        final ArrayList<String> lore = (ArrayList<String>)new ArrayList();
        lore.add((Object)((Object)ChatColor.GRAY + "Heal 5\u2764 when killing a"));
        lore.add((Object)((Object)ChatColor.GRAY + "monster."));
        return (List<String>)lore;
    }
    
    @Override
    public String getDisplayName() {
        return "Devour Ring";
    }
    
    @Override
    public Rarity getRarity() {
        return Rarity.RARE;
    }
    
    @Override
    public void onDamageInInventory(final SItem weapon, final Player player, final Entity damaged, final SItem accessory, final AtomicDouble damage) {
        player.setMaxHealth(Math.min(player.getMaxHealth(), player.getHealth() + player.getMaxHealth() * 0.02));
    }
    
    static {
        HITS = (Map)new HashMap();
    }
}
