package net.hypixel.skyblock.features.merchant;

import net.hypixel.skyblock.util.SUtil;
import net.hypixel.skyblock.item.SItem;
import net.hypixel.skyblock.item.SMaterial;
import java.util.HashMap;

public class MerchantItemHandler
{
    public static final HashMap<SMaterial, SItem> ITEMS;
    
    public static void init() {
        initFarmMerchant();
    }
    
    private static void initFarmMerchant() {
        SUtil.toShopItem(SMaterial.WHEAT, 3, 10L, 6L);
        SUtil.toShopItem(SMaterial.CARROT_ITEM, 3, 10L, 3L);
        SUtil.toShopItem(SMaterial.POTATO_ITEM, 3, 10L, 3L);
        SUtil.toShopItem(SMaterial.MELON, 10, 4L, 2L);
        SUtil.toShopItem(SMaterial.SUGAR_CANE, 3, 10L, 4L);
        SUtil.toShopItem(SMaterial.PUMPKIN, 1, 25L, 10L);
        SUtil.toShopItem(SMaterial.COCOA_BEANS, 1, 5L, 3L);
        SUtil.toShopItem(SMaterial.RED_MUSHROOM, 1, 12L, 10L);
        SUtil.toShopItem(SMaterial.BROWN_MUSHROOM, 1, 12L, 10L);
        SUtil.toShopItem(SMaterial.SAND, 2, 4L, 2L);
        SUtil.toShopItem(SMaterial.CACTUS, 1, 10L, 3L);
    }
    
    public static SItem getItem(final SMaterial material) {
        if (MerchantItemHandler.ITEMS.containsKey((Object)material)) {
            return (SItem)MerchantItemHandler.ITEMS.get((Object)material);
        }
        return null;
    }
    
    static {
        ITEMS = new HashMap();
    }
}
