package net.hypixel.skyblock.gui;

import net.hypixel.skyblock.features.merchant.FarmMerchantGUI;
import net.hypixel.skyblock.util.SUtil;

public enum GUIType
{
    CRAFTING_TABLE((Class<? extends GUI>)CraftingTableGUI.class), 
    ITEM_BROWSE((Class<? extends GUI>)ItemBrowserGUI.class), 
    MOB_GUI((Class<? extends GUI>)MobSummonGUI.class), 
    ANVIL((Class<? extends GUI>)AnvilGUI.class), 
    TRASH((Class<? extends GUI>)TrashGUI.class), 
    COOKIE_GUI((Class<? extends GUI>)CookieGUI.class), 
    ITEM_EDITOR((Class<? extends GUI>)ItemEditor.class), 
    COOKIE_CONSUME_CONFIRM((Class<? extends GUI>)CookieConfirmGUI.class), 
    RECIPE_BOOK((Class<? extends GUI>)RecipeBookListGUI.class), 
    REFORGE_ANVIL((Class<? extends GUI>)ReforgeAnvilGUI.class), 
    DUNGEON_CRAFTING((Class<? extends GUI>)DungeonsItemConverting.class), 
    DUNGEON_SKILL((Class<? extends GUI>)DungeonsLevelGUI.class), 
    BOOSTER_COOKIE_SHOP((Class<? extends GUI>)BoosterCookieShop.class), 
    BANKER((Class<? extends GUI>)BankerGUI.class), 
    BANKER_DEPOSIT((Class<? extends GUI>)DepositGUI.class), 
    BANKER_WITHDRAWAL((Class<? extends GUI>)WithdrawalGUI.class), 
    SKYBLOCK_MENU((Class<? extends GUI>)SkyBlockMenuGUI.class), 
    CATACOMBS_BOSS((Class<? extends GUI>)BossMenu.class), 
    BOSS_COLLECTION((Class<? extends GUI>)CollectionBoss.class), 
    SKYBLOCK_PROFILE((Class<? extends GUI>)SkyBlockProfileGUI.class), 
    QUIVER((Class<? extends GUI>)QuiverGUI.class), 
    LIFT((Class<? extends GUI>)LiftGUI.class), 
    SLAYER((Class<? extends GUI>)SlayerGUI.class), 
    REVENANT_HORROR((Class<? extends GUI>)RevenantHorrorGUI.class), 
    TARANTULA_BROODFATHER((Class<? extends GUI>)TarantulaBroodfatherGUI.class), 
    SVEN_PACKMASTER((Class<? extends GUI>)SvenPackmasterGUI.class), 
    COLLECTION_MENU((Class<? extends GUI>)CollectionMenuGUI.class), 
    SKILL_MENU((Class<? extends GUI>)SkillMenuGUI.class), 
    PETS((Class<? extends GUI>)PetsGUI.class), 
    FARM_MERCHANT((Class<? extends GUI>)FarmMerchantGUI.class), 
    ACTIVE_EFFECTS((Class<? extends GUI>)ActiveEffectsGUI.class), 
    AUCTION_HOUSE((Class<? extends GUI>)AuctionHouseGUI.class), 
    AUCTIONS_BROWSER((Class<? extends GUI>)AuctionsBrowserGUI.class), 
    CREATE_AUCTION((Class<? extends GUI>)CreateAuctionGUI.class), 
    AUCTION_CONFIRM((Class<? extends GUI>)AuctionConfirmGUI.class), 
    MANAGE_AUCTIONS((Class<? extends GUI>)ManageAuctionsGUI.class), 
    YOUR_BIDS((Class<? extends GUI>)YourBidsGUI.class), 
    WARP((Class<? extends GUI>)WarpGUI.class), 
    ADMIN_ITEM_BROWSER((Class<? extends GUI>)AdminItemBrowser.class), 
    RECIPE_CREATOR((Class<? extends GUI>)RecipeCreatorGUI.class), 
    VOIDGLOOM_SERAPH((Class<? extends GUI>)VoidgloomSeraph.class);
    
    private final Class<? extends GUI> gui;
    
    private GUIType(final Class<? extends GUI> gui) {
        this.gui = gui;
    }
    
    public GUI getGUI() {
        try {
            return (GUI)this.gui.newInstance();
        }
        catch (final IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public GUI getGUI(final Object... params) {
        return SUtil.instance(GUI.class, params);
    }
    
    public static GUI getGUI(final String title) {
        for (final GUIType type : values()) {
            if (type.getGUI().getTitle().contains((CharSequence)title)) {
                return type.getGUI();
            }
        }
        return null;
    }
}
