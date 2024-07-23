package net.hypixel.skyblock.item.enchanting;

import net.hypixel.skyblock.item.SMaterial;
import java.util.Iterator;
import net.hypixel.skyblock.features.enchantment.Enchantment;
import net.hypixel.skyblock.item.SItem;
import net.hypixel.skyblock.item.GenericItemType;
import net.hypixel.skyblock.item.Rarity;
import net.hypixel.skyblock.item.MaterialQuantifiable;
import net.hypixel.skyblock.item.Enchantable;
import net.hypixel.skyblock.item.MaterialFunction;
import net.hypixel.skyblock.item.MaterialStatistics;

public class EnchantedBook implements MaterialStatistics, MaterialFunction, Enchantable
{
    private static final MaterialQuantifiable PAPER_16;
    
    @Override
    public String getDisplayName() {
        return "Enchanted Book";
    }
    
    @Override
    public Rarity getRarity() {
        return Rarity.COMMON;
    }
    
    @Override
    public boolean isStackable() {
        return false;
    }
    
    @Override
    public String getLore() {
        return "Use this on an item in an Anvil to apply it!";
    }
    
    @Override
    public GenericItemType getType() {
        return GenericItemType.ITEM;
    }
    
    @Override
    public void onInstanceUpdate(final SItem instance) {
        int max = 1;
        for (final Enchantment enchantment : instance.getEnchantments()) {
            if (enchantment.getLevel() > max) {
                max = enchantment.getLevel();
            }
        }
        switch (max) {
            case 1:
            case 2:
            case 3:
            case 4: {
                instance.setRarity(Rarity.COMMON, false);
                break;
            }
            case 5: {
                instance.setRarity(Rarity.UNCOMMON, false);
                break;
            }
            case 6: {
                instance.setRarity(Rarity.RARE, false);
                break;
            }
            case 7: {
                instance.setRarity(Rarity.EPIC, false);
                break;
            }
            case 8: {
                instance.setRarity(Rarity.LEGENDARY, false);
                break;
            }
            case 9: {
                instance.setRarity(Rarity.MYTHIC, false);
                break;
            }
            default: {
                instance.setRarity(Rarity.SUPREME, false);
                break;
            }
        }
    }
    
    static {
        PAPER_16 = new MaterialQuantifiable(SMaterial.PAPER, 16);
    }
}
