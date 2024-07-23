package net.hypixel.skyblock.features.merchant;

import net.hypixel.skyblock.item.SMaterial;
import net.hypixel.skyblock.item.SItem;

public class MineMerchantGUI extends ShopGUI
{
    private static final SItem[] ITEMS;
    
    public MineMerchantGUI(final int page) {
        super("Mine Merchant", page, MineMerchantGUI.ITEMS);
    }
    
    public MineMerchantGUI() {
        this(1);
    }
    
    static {
        ITEMS = new SItem[] { MerchantItemHandler.getItem(SMaterial.COAL), MerchantItemHandler.getItem(SMaterial.IRON_INGOT), MerchantItemHandler.getItem(SMaterial.GOLD_INGOT), MerchantItemHandler.getItem(SMaterial.GOLD_PICKAXE), MerchantItemHandler.getItem(SMaterial.TORCH), MerchantItemHandler.getItem(SMaterial.GRAVEL), MerchantItemHandler.getItem(SMaterial.COBBLESTONE), MerchantItemHandler.getItem(SMaterial.STONE) };
    }
}
