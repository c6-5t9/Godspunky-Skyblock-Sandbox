package net.hypixel.skyblock.item.armor.enderman;

import net.hypixel.skyblock.item.SpecificItemType;
import net.hypixel.skyblock.item.GenericItemType;
import net.hypixel.skyblock.item.Rarity;
import org.bukkit.ChatColor;
import java.util.ArrayList;
import java.util.List;
import net.hypixel.skyblock.item.armor.LeatherArmorStatistics;
import net.hypixel.skyblock.item.MaterialFunction;
import net.hypixel.skyblock.item.ToolStatistics;

public class VoidbaneChestplate implements ToolStatistics, MaterialFunction, LeatherArmorStatistics
{
    @Override
    public String getDisplayName() {
        return "Corrupted Voidbane Chestplate";
    }
    
    @Override
    public double getBaseStrength() {
        return 65.0;
    }
    
    @Override
    public double getBaseCritDamage() {
        return 0.5;
    }
    
    @Override
    public double getBaseHealth() {
        return 255.0;
    }
    
    @Override
    public double getBaseDefense() {
        return 415.0;
    }
    
    @Override
    public List<String> killReplacementLore() {
        final List<String> lore = (List<String>)new ArrayList();
        lore.add((Object)this.formatLore("&8Tiered Bonus: Void Conquer"));
        lore.add((Object)this.formatLore("While sneaking in the"));
        lore.add((Object)this.formatLore("&9End Dimension&7, gain &a45%"));
        lore.add((Object)this.formatLore("&7damage against enderman per"));
        lore.add((Object)this.formatLore("piece of this armor worn."));
        lore.add((Object)" ");
        lore.add((Object)this.formatLore("&6Full Set Bonus: Rebound"));
        lore.add((Object)this.formatLore("All damage you receive from"));
        lore.add((Object)this.formatLore("Enderman is &areflected&7 back"));
        lore.add((Object)this.formatLore("after &a3s&7 if you are above &a50%"));
        lore.add((Object)this.formatLore("health. The amount of times this ability"));
        lore.add((Object)this.formatLore("activates scales with Bonus Defense."));
        lore.add((Object)this.formatLore(" "));
        lore.add((Object)this.formatLore("&6Piece Bonus: Enderman Bulwark"));
        lore.add((Object)this.formatLore("Kill Enderman to accumulate"));
        lore.add((Object)this.formatLore("more Defense against them."));
        lore.add((Object)this.formatLore("Piece Bonus: &a+<SKYBLOCK_BONUS_DEFENSE>\u2748&7"));
        lore.add((Object)this.formatLore("Next Upgrade: &a+<SKYBLOCK_NEXT_DEFENSE>\u2748&8 (&a<SKYBLOCK_CURRENT_KILLS>&7/&c<SKYBLOCK_REQUIRED_KILLS>&8)"));
        return lore;
    }
    
    private String formatLore(final String lore) {
        return ChatColor.translateAlternateColorCodes('&', lore);
    }
    
    @Override
    public Rarity getRarity() {
        return Rarity.LEGENDARY;
    }
    
    @Override
    public GenericItemType getType() {
        return GenericItemType.ARMOR;
    }
    
    @Override
    public SpecificItemType getSpecificType() {
        return SpecificItemType.CHESTPLATE;
    }
    
    @Override
    public int getColor() {
        return 655377;
    }
}
