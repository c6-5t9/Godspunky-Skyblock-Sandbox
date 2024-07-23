package net.hypixel.skyblock.features.itemeditor;

import net.hypixel.skyblock.features.enchantment.EnchantmentType;
import net.hypixel.skyblock.features.reforge.Reforge;
import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.Player;
import net.hypixel.skyblock.features.dungeons.stats.ItemSerial;
import net.hypixel.skyblock.item.SItem;

public class EditableItem
{
    SItem sItem;
    
    public EditableItem(final SItem item) {
        this.sItem = item;
    }
    
    public void addStar(final int amount) {
        if (amount == 0) {
            return;
        }
        if (amount > 5) {
            return;
        }
        if (!this.sItem.isStarrable()) {
            return;
        }
        final ItemSerial is = ItemSerial.createBlank();
        is.saveTo(this.sItem);
        this.sItem.setStarAmount(this.getStars() + amount);
    }
    
    public int getStars() {
        return this.sItem.getStar();
    }
    
    public void recom(final boolean setrecom) {
        this.sItem.setRecombobulated(setrecom);
    }
    
    public void clone(final Player player) {
        player.getInventory().addItem(new ItemStack[] { this.sItem.getStack() });
    }
    
    public void toDungeonItem() {
        this.sItem.setDungeonsItem(true);
    }
    
    public void reforge(final Reforge reforge) {
        this.sItem.setReforge(reforge);
    }
    
    public void enchant(final EnchantmentType type, final int level) {
        this.sItem.addEnchantment(type, level);
    }
    
    public void addhpb() {
        final int oldAmount = this.sItem.getHPBs();
        if (oldAmount == 20) {
            return;
        }
        this.sItem.setHPBs(oldAmount + 1);
    }
    
    public SItem getHandle() {
        return this.sItem;
    }
}
