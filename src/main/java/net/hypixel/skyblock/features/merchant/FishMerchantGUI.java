package net.hypixel.skyblock.features.merchant;

import net.hypixel.skyblock.item.SMaterial;
import net.hypixel.skyblock.item.SItem;

public class FishMerchantGUI extends ShopGUI
{
    private static final SItem[] ITEMS;
    
    public FishMerchantGUI(final int page) {
        super("Fish Merchant", page, FishMerchantGUI.ITEMS);
    }
    
    public FishMerchantGUI() {
        this(1);
    }
    
    static {
        ITEMS = new SItem[] { MerchantItemHandler.getItem(SMaterial.FISHING_ROD), MerchantItemHandler.getItem(SMaterial.RAW_FISH), MerchantItemHandler.getItem(SMaterial.RAW_SALMON), MerchantItemHandler.getItem(SMaterial.PUFFERFISH) };
    }
}
