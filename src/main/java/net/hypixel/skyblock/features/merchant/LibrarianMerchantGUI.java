package net.hypixel.skyblock.features.merchant;

import net.hypixel.skyblock.item.SMaterial;
import net.hypixel.skyblock.item.SItem;

public class LibrarianMerchantGUI extends ShopGUI
{
    private static final SItem[] ITEMS;
    
    public LibrarianMerchantGUI(final int page) {
        super("Librarian", page, LibrarianMerchantGUI.ITEMS);
    }
    
    public LibrarianMerchantGUI() {
        this(1);
    }
    
    static {
        ITEMS = new SItem[] { MerchantItemHandler.getItem(SMaterial.EXP_BOTTLE), MerchantItemHandler.getItem(SMaterial.BOOK) };
    }
}
