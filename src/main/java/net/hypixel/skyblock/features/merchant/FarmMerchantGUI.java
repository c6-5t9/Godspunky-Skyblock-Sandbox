package net.hypixel.skyblock.features.merchant;

import net.hypixel.skyblock.item.SMaterial;
import net.hypixel.skyblock.item.SItem;

public class FarmMerchantGUI extends ShopGUI
{
    private static final SItem[] ITEMS;
    
    public FarmMerchantGUI(final int page) {
        super("Farm Merchant", page, FarmMerchantGUI.ITEMS);
    }
    
    public FarmMerchantGUI() {
        this(1);
    }
    
    static {
        ITEMS = new SItem[] { MerchantItemHandler.getItem(SMaterial.WHEAT), MerchantItemHandler.getItem(SMaterial.CARROT_ITEM), MerchantItemHandler.getItem(SMaterial.POTATO_ITEM), MerchantItemHandler.getItem(SMaterial.MELON), MerchantItemHandler.getItem(SMaterial.SUGAR_CANE), MerchantItemHandler.getItem(SMaterial.PUMPKIN), MerchantItemHandler.getItem(SMaterial.COCOA_BEANS), MerchantItemHandler.getItem(SMaterial.RED_MUSHROOM), MerchantItemHandler.getItem(SMaterial.BROWN_MUSHROOM), MerchantItemHandler.getItem(SMaterial.SAND), MerchantItemHandler.getItem(SMaterial.CACTUS) };
    }
}
