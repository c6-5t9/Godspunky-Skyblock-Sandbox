package net.hypixel.skyblock.features.merchant;

import net.hypixel.skyblock.item.SMaterial;
import net.hypixel.skyblock.item.SItem;

public class AdventurerMerchantGUI extends ShopGUI
{
    private static final SItem[] ITEMS;
    
    public AdventurerMerchantGUI(final int page) {
        super("Adventurer", page, AdventurerMerchantGUI.ITEMS);
    }
    
    public AdventurerMerchantGUI() {
        this(1);
    }
    
    static {
        ITEMS = new SItem[] { MerchantItemHandler.getItem(SMaterial.ROTTEN_FLESH), MerchantItemHandler.getItem(SMaterial.BONE), MerchantItemHandler.getItem(SMaterial.STRING), MerchantItemHandler.getItem(SMaterial.SLIME_BALL), MerchantItemHandler.getItem(SMaterial.GUNPOWDER) };
    }
}
