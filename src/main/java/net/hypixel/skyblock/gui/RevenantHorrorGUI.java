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

public class RevenantHorrorGUI extends GUI
{
    public RevenantHorrorGUI() {
        super("Revenant Horror", 54);
    }
    
    @Override
    public void onOpen(final GUIOpenEvent e) {
        this.fill(RevenantHorrorGUI.BLACK_STAINED_GLASS_PANE);
        final Player player = e.getPlayer();
        this.set(GUIClickableItem.createGUIOpenerItem(GUIType.SLAYER, player, (Object)ChatColor.GREEN + "Go Back", 49, Material.ARROW, (Object)ChatColor.GRAY + "To Slayer"));
        this.set(new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent e) {
                new SlayerConfirmGUI(SlayerBossType.REVENANT_HORROR_I, () -> {
                    final Object val$player = player;
                    User.getUser(player.getUniqueId()).startSlayerQuest(SlayerBossType.REVENANT_HORROR_I);
                }).open(player);
            }
            
            @Override
            public ItemStack getItem() {
                return SUtil.getStack(SlayerBossType.REVENANT_HORROR_I.getDisplayName(), SlayerBossType.REVENANT_HORROR_I.getType().getIcon(), (short)0, 1, SlayerBossType.REVENANT_HORROR_I.asLore(true));
            }
            
            @Override
            public int getSlot() {
                return 11;
            }
        });
        this.set(new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent e) {
                new SlayerConfirmGUI(SlayerBossType.REVENANT_HORROR_II, () -> {
                    final Object val$player = player;
                    User.getUser(player.getUniqueId()).startSlayerQuest(SlayerBossType.REVENANT_HORROR_II);
                }).open(player);
            }
            
            @Override
            public ItemStack getItem() {
                return SUtil.getStack(SlayerBossType.REVENANT_HORROR_II.getDisplayName(), SlayerBossType.REVENANT_HORROR_II.getType().getIcon(), (short)0, 2, SlayerBossType.REVENANT_HORROR_II.asLore(true));
            }
            
            @Override
            public int getSlot() {
                return 12;
            }
        });
        this.set(new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent e) {
                new SlayerConfirmGUI(SlayerBossType.REVENANT_HORROR_III, () -> {
                    final Object val$player = player;
                    User.getUser(player.getUniqueId()).startSlayerQuest(SlayerBossType.REVENANT_HORROR_III);
                }).open(player);
            }
            
            @Override
            public ItemStack getItem() {
                return SUtil.getStack(SlayerBossType.REVENANT_HORROR_III.getDisplayName(), SlayerBossType.REVENANT_HORROR_III.getType().getIcon(), (short)0, 3, SlayerBossType.REVENANT_HORROR_III.asLore(true));
            }
            
            @Override
            public int getSlot() {
                return 13;
            }
        });
        this.set(new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent e) {
                new SlayerConfirmGUI(SlayerBossType.REVENANT_HORROR_IV, () -> {
                    final Object val$player = player;
                    User.getUser(player.getUniqueId()).startSlayerQuest(SlayerBossType.REVENANT_HORROR_IV);
                }).open(player);
            }
            
            @Override
            public ItemStack getItem() {
                return SUtil.getStack(SlayerBossType.REVENANT_HORROR_IV.getDisplayName(), SlayerBossType.REVENANT_HORROR_IV.getType().getIcon(), (short)0, 4, SlayerBossType.REVENANT_HORROR_IV.asLore(true));
            }
            
            @Override
            public int getSlot() {
                return 14;
            }
        });
        this.set(new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent e) {
                new SlayerConfirmGUI(SlayerBossType.REVENANT_HORROR_V, () -> {
                    final Object val$player = player;
                    User.getUser(player.getUniqueId()).startSlayerQuest(SlayerBossType.REVENANT_HORROR_V);
                }).open(player);
            }
            
            @Override
            public ItemStack getItem() {
                return SUtil.getStack(SlayerBossType.REVENANT_HORROR_V.getDisplayName(), SlayerBossType.REVENANT_HORROR_V.getType().getIcon(), (short)0, 5, SlayerBossType.REVENANT_HORROR_V.asLore(true));
            }
            
            @Override
            public int getSlot() {
                return 15;
            }
        });
        this.set(new GUIClickableItem() {
            @Override
            public ItemStack getItem() {
                return SUtil.getStack((Object)ChatColor.DARK_PURPLE + "Boss Leveling Rewards", Material.GOLD_BLOCK, (short)0, 1, (Object)ChatColor.DARK_GRAY + "Zombie Slayer LVL", (Object)ChatColor.GRAY + " ", Sputnik.trans("&51. &7Kill boss to get XP"), Sputnik.trans("&52. &7Gain LVL from XP"), Sputnik.trans("&53. &7Unlock rewards per LVL"), Sputnik.trans(" "), Sputnik.trans("&7Current LVL: &e" + SlayerBossType.SlayerMobType.ZOMBIE.getLevelForXP(User.getUser(player.getUniqueId()).getZombieSlayerXP())), Sputnik.trans(" "), Sputnik.trans("&7Zombie Slayer XP to LVL " + (SlayerBossType.SlayerMobType.ZOMBIE.getLevelForXP(User.getUser(player.getUniqueId()).getZombieSlayerXP()) + 1) + ":"), Sputnik.trans(SUtil.createLineProgressBar(18, ChatColor.DARK_PURPLE, User.getUser(player.getUniqueId()).getZombieSlayerXP(), SlayerBossType.staticGetXPReqForLevel(SlayerBossType.SlayerMobType.ZOMBIE.getLevelForXP(User.getUser(player.getUniqueId()).getZombieSlayerXP()), EntityType.ZOMBIE))), " ", Sputnik.trans("&cNot available on Semi-Sandbox mode!"));
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
                return SUtil.getStack((Object)ChatColor.GOLD + "Boss Drops", Material.GOLD_NUGGET, (short)0, 1, (Object)ChatColor.DARK_GRAY + "Revenant Horror", " ", Sputnik.trans("&7Usually, the boss will drop"), Sputnik.trans("&aRevenant Flesh&7."), " ", Sputnik.trans("&7If you're lucky, you may get"), Sputnik.trans("&7one of &9 &7possible"), Sputnik.trans("&7drops from this boss."), " ", Sputnik.trans("&cMenu is not available!"));
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
                return SUtil.getStack((Object)ChatColor.GREEN + "Slayers Recipe", Material.BOOK, (short)0, 1, (Object)ChatColor.DARK_GRAY + "Revenant Horror", " ", Sputnik.trans("&cFeature is not available!"));
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
