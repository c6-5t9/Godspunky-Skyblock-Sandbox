package net.hypixel.skyblock.item.enchanted;

import net.hypixel.skyblock.item.ShapelessRecipe;
import net.hypixel.skyblock.item.MaterialQuantifiable;
import net.hypixel.skyblock.item.SMaterial;
import net.hypixel.skyblock.item.MaterialStatistics;

public interface EnchantedMaterialStatistics extends MaterialStatistics
{
    SMaterial getCraftingMaterial();
    
    MaterialQuantifiable getResult();
    
    default SMaterial getBlockCraftingMaterial() {
        return null;
    }
    
    default MaterialQuantifiable getBlockResult() {
        return null;
    }
    
    default int getCraftingRequiredAmount() {
        return 32;
    }
    
    default void load() {
        if (null != this.getBlockCraftingMaterial() && null != this.getBlockResult()) {
            this.createRecipe(new MaterialQuantifiable(this.getBlockCraftingMaterial(), this.getCraftingRequiredAmount()), this.getBlockResult());
        }
        this.createRecipe(new MaterialQuantifiable(this.getCraftingMaterial(), this.getCraftingRequiredAmount()), this.getResult());
    }
    
    default void createRecipe(final MaterialQuantifiable material, final MaterialQuantifiable result) {
        new ShapelessRecipe(result.getMaterial(), result.getAmount()).add(material).add(material).add(material).add(material).add(material);
    }
}
