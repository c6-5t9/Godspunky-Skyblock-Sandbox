package net.hypixel.skyblock.item.enchanted;

import net.hypixel.skyblock.item.MaterialQuantifiable;
import net.hypixel.skyblock.item.SMaterial;
import net.hypixel.skyblock.item.GenericItemType;
import net.hypixel.skyblock.item.Rarity;
import net.hypixel.skyblock.item.MaterialFunction;

public class EnchantedBone implements EnchantedMaterialStatistics, MaterialFunction
{
    @Override
    public String getDisplayName() {
        return "Enchanted Bone";
    }
    
    @Override
    public Rarity getRarity() {
        return Rarity.COMMON;
    }
    
    @Override
    public GenericItemType getType() {
        return GenericItemType.ITEM;
    }
    
    @Override
    public boolean isEnchanted() {
        return true;
    }
    
    @Override
    public SMaterial getCraftingMaterial() {
        return SMaterial.BONE;
    }
    
    @Override
    public MaterialQuantifiable getResult() {
        return new MaterialQuantifiable(SMaterial.ENCHANTED_BONE);
    }
}
