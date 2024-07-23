package net.hypixel.skyblock.gui;

import org.bukkit.entity.EntityType;
import net.hypixel.skyblock.util.Sputnik;
import net.hypixel.skyblock.user.User;
import net.hypixel.skyblock.util.SUtil;
import org.bukkit.inventory.ItemStack;
import net.hypixel.skyblock.features.slayer.SlayerBossType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.entity.Player;
import org.bukkit.Material;
import org.bukkit.ChatColor;

public class TarantulaBroodfatherGUI extends GUI
{
    public TarantulaBroodfatherGUI() {
        super("Tarantula Broodfather", 54);
    }
    
    @Override
    public void onOpen(final GUIOpenEvent e) {
        this.fill(TarantulaBroodfatherGUI.BLACK_STAINED_GLASS_PANE);
        final Player player = e.getPlayer();
        this.set(GUIClickableItem.createGUIOpenerItem(GUIType.SLAYER, player, (Object)ChatColor.GREEN + "Go Back", 49, Material.ARROW, (Object)ChatColor.GRAY + "To Slayer"));
        this.set(new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent e) {
                new SlayerConfirmGUI(SlayerBossType.TARANTULA_BROODFATHER_I, () -> {
                    final Object val$player = player;
                    User.getUser(player.getUniqueId()).startSlayerQuest(SlayerBossType.TARANTULA_BROODFATHER_I);
                }).open(player);
            }
            
            @Override
            public ItemStack getItem() {
                return SUtil.getStack(SlayerBossType.TARANTULA_BROODFATHER_I.getDisplayName(), SlayerBossType.TARANTULA_BROODFATHER_I.getType().getIcon(), (short)0, 1, SlayerBossType.TARANTULA_BROODFATHER_I.asLore(true));
            }
            
            @Override
            public int getSlot() {
                return 11;
            }
        });
        this.set(new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent e) {
                new SlayerConfirmGUI(SlayerBossType.TARANTULA_BROODFATHER_II, () -> {
                    final Object val$player = player;
                    User.getUser(player.getUniqueId()).startSlayerQuest(SlayerBossType.TARANTULA_BROODFATHER_II);
                }).open(player);
            }
            
            @Override
            public ItemStack getItem() {
                return SUtil.getStack(SlayerBossType.TARANTULA_BROODFATHER_II.getDisplayName(), SlayerBossType.TARANTULA_BROODFATHER_II.getType().getIcon(), (short)0, 2, SlayerBossType.TARANTULA_BROODFATHER_II.asLore(true));
            }
            
            @Override
            public int getSlot() {
                return 12;
            }
        });
        this.set(new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent e) {
                new SlayerConfirmGUI(SlayerBossType.TARANTULA_BROODFATHER_III, () -> {
                    final Object val$player = player;
                    User.getUser(player.getUniqueId()).startSlayerQuest(SlayerBossType.TARANTULA_BROODFATHER_III);
                }).open(player);
            }
            
            @Override
            public ItemStack getItem() {
                return SUtil.getStack(SlayerBossType.TARANTULA_BROODFATHER_III.getDisplayName(), SlayerBossType.TARANTULA_BROODFATHER_III.getType().getIcon(), (short)0, 3, SlayerBossType.TARANTULA_BROODFATHER_III.asLore(true));
            }
            
            @Override
            public int getSlot() {
                return 13;
            }
        });
        this.set(new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent e) {
                new SlayerConfirmGUI(SlayerBossType.TARANTULA_BROODFATHER_IV, () -> {
                    final Object val$player = player;
                    User.getUser(player.getUniqueId()).startSlayerQuest(SlayerBossType.TARANTULA_BROODFATHER_IV);
                }).open(player);
            }
            
            @Override
            public ItemStack getItem() {
                return SUtil.getStack(SlayerBossType.TARANTULA_BROODFATHER_IV.getDisplayName(), SlayerBossType.TARANTULA_BROODFATHER_IV.getType().getIcon(), (short)0, 4, SlayerBossType.TARANTULA_BROODFATHER_IV.asLore(true));
            }
            
            @Override
            public int getSlot() {
                return 14;
            }
        });
        this.set(new GUIClickableItem() {
            @Override
            public ItemStack getItem() {
                return SUtil.getStack((Object)ChatColor.DARK_PURPLE + "Tarantula Broodfather V", Material.COAL_BLOCK, (short)0, 1, (Object)ChatColor.GRAY + "This excruciatingly difficult", (Object)ChatColor.GRAY + "boss tier will release at a", (Object)ChatColor.GRAY + "later date.");
            }
            
            @Override
            public int getSlot() {
                return 15;
            }
            
            @Override
            public void run(final InventoryClickEvent e) {
            }
        });
        this.set(new GUIClickableItem() {
            @Override
            public ItemStack getItem() {
                return SUtil.getStack((Object)ChatColor.DARK_PURPLE + "Boss Leveling Rewards", Material.GOLD_BLOCK, (short)0, 1, (Object)ChatColor.DARK_GRAY + "Spider Slayer LVL", (Object)ChatColor.GRAY + " ", Sputnik.trans("&51. &7Kill boss to get XP"), Sputnik.trans("&52. &7Gain LVL from XP"), Sputnik.trans("&53. &7Unlock rewards per LVL"), Sputnik.trans(" "), Sputnik.trans("&7Current LVL: &e" + SlayerBossType.SlayerMobType.SPIDER.getLevelForXP(User.getUser(player.getUniqueId()).getSpiderSlayerXP())), Sputnik.trans(" "), Sputnik.trans("&7Spider Slayer XP to LVL " + (SlayerBossType.SlayerMobType.SPIDER.getLevelForXP(User.getUser(player.getUniqueId()).getSpiderSlayerXP()) + 1) + ":"), Sputnik.trans(SUtil.createLineProgressBar(18, ChatColor.DARK_PURPLE, User.getUser(player.getUniqueId()).getSpiderSlayerXP(), SlayerBossType.staticGetXPReqForLevel(SlayerBossType.SlayerMobType.SPIDER.getLevelForXP(User.getUser(player.getUniqueId()).getSpiderSlayerXP()), EntityType.SPIDER))), " ", Sputnik.trans("&cNot available on Semi-Sandbox mode!"));
            }
            
            @Override
            public int getSlot() {
                return 29;
            }
            
            @Override
            public void run(final InventoryClickEvent e) {
            }
        });
        this.set(new GUIClickableItem() {
            @Override
            public ItemStack getItem() {
                return SUtil.getStack((Object)ChatColor.GOLD + "Boss Drops", Material.GOLD_NUGGET, (short)0, 1, (Object)ChatColor.DARK_GRAY + "Tarantula Broodfather", " ", Sputnik.trans("&7Usually, the boss will drop"), Sputnik.trans("&aTarantula Web&7."), " ", Sputnik.trans("&7If you're lucky, you may get"), Sputnik.trans("&7one of &d6 &7possible"), Sputnik.trans("&7drops from this boss."), " ", Sputnik.trans("&cMenu is not available!"));
            }
            
            @Override
            public int getSlot() {
                return 31;
            }
            
            @Override
            public void run(final InventoryClickEvent e) {
            }
        });
        this.set(new GUIClickableItem() {
            @Override
            public ItemStack getItem() {
                return SUtil.getStack((Object)ChatColor.GREEN + "Slayers Recipe", Material.BOOK, (short)0, 1, (Object)ChatColor.DARK_GRAY + "Tarantula Broodfather", " ", Sputnik.trans("&cFeature is not available!"));
            }
            
            @Override
            public int getSlot() {
                return 33;
            }
            
            @Override
            public void run(final InventoryClickEvent e) {
            }
        });
    }
}
