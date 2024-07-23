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

public class VoidgloomSeraph extends GUI
{
    public VoidgloomSeraph() {
        super("Voidgloom Seraph", 54);
    }
    
    @Override
    public void onOpen(final GUIOpenEvent e) {
        this.fill(VoidgloomSeraph.BLACK_STAINED_GLASS_PANE);
        final Player player = e.getPlayer();
        this.set(GUIClickableItem.createGUIOpenerItem(GUIType.SLAYER, player, (Object)ChatColor.GREEN + "Go Back", 49, Material.ARROW, (Object)ChatColor.GRAY + "To Slayer"));
        this.set(new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent e) {
                new SlayerConfirmGUI(SlayerBossType.VOIDGLOOM_SERAPH_I, () -> User.getUser(player.getUniqueId()).startSlayerQuest(SlayerBossType.VOIDGLOOM_SERAPH_I)).open(player);
            }
            
            @Override
            public ItemStack getItem() {
                return SUtil.getStack(SlayerBossType.VOIDGLOOM_SERAPH_I.getDisplayName(), SlayerBossType.VOIDGLOOM_SERAPH_I.getType().getIcon(), (short)0, 1, SlayerBossType.VOIDGLOOM_SERAPH_I.asLore(true));
            }
            
            @Override
            public int getSlot() {
                return 11;
            }
        });
        this.set(new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent e) {
                new SlayerConfirmGUI(SlayerBossType.VOIDGLOOM_SERAPH_II, () -> User.getUser(player.getUniqueId()).startSlayerQuest(SlayerBossType.VOIDGLOOM_SERAPH_II)).open(player);
            }
            
            @Override
            public ItemStack getItem() {
                return SUtil.getStack(SlayerBossType.VOIDGLOOM_SERAPH_II.getDisplayName(), SlayerBossType.VOIDGLOOM_SERAPH_II.getType().getIcon(), (short)0, 2, SlayerBossType.VOIDGLOOM_SERAPH_II.asLore(true));
            }
            
            @Override
            public int getSlot() {
                return 12;
            }
        });
        this.set(new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent e) {
                new SlayerConfirmGUI(SlayerBossType.VOIDGLOOM_SERAPH_III, () -> User.getUser(player.getUniqueId()).startSlayerQuest(SlayerBossType.VOIDGLOOM_SERAPH_III)).open(player);
            }
            
            @Override
            public ItemStack getItem() {
                return SUtil.getStack(SlayerBossType.VOIDGLOOM_SERAPH_III.getDisplayName(), SlayerBossType.VOIDGLOOM_SERAPH_III.getType().getIcon(), (short)0, 3, SlayerBossType.VOIDGLOOM_SERAPH_III.asLore(true));
            }
            
            @Override
            public int getSlot() {
                return 13;
            }
        });
        this.set(new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent e) {
                new SlayerConfirmGUI(SlayerBossType.VOIDGLOOM_SERAPH_IV, () -> User.getUser(player.getUniqueId()).startSlayerQuest(SlayerBossType.VOIDGLOOM_SERAPH_IV)).open(player);
            }
            
            @Override
            public ItemStack getItem() {
                return SUtil.getStack(SlayerBossType.VOIDGLOOM_SERAPH_IV.getDisplayName(), SlayerBossType.VOIDGLOOM_SERAPH_IV.getType().getIcon(), (short)0, 4, SlayerBossType.VOIDGLOOM_SERAPH_IV.asLore(true));
            }
            
            @Override
            public int getSlot() {
                return 14;
            }
        });
        this.set(new GUIClickableItem() {
            @Override
            public ItemStack getItem() {
                return SUtil.getStack((Object)ChatColor.DARK_PURPLE + "Voidgloom Seraph V", Material.COAL_BLOCK, (short)0, 1, (Object)ChatColor.GRAY + "This excruciatingly difficult", (Object)ChatColor.GRAY + "boss tier will release at a", (Object)ChatColor.GRAY + "later date.");
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
                return SUtil.getStack((Object)ChatColor.DARK_PURPLE + "Boss Leveling Rewards", Material.GOLD_BLOCK, (short)0, 1, (Object)ChatColor.DARK_GRAY + "Enderman Slayer LVL", (Object)ChatColor.GRAY + " ", Sputnik.trans("&51. &7Kill boss to get XP"), Sputnik.trans("&52. &7Gain LVL from XP"), Sputnik.trans("&53. &7Unlock rewards per LVL"), Sputnik.trans(" "), Sputnik.trans("&7Current LVL: &e" + SlayerBossType.SlayerMobType.ENDERMAN.getLevelForXP(User.getUser(player.getUniqueId()).getEndermanSlayerXP())), Sputnik.trans(" "), Sputnik.trans("&7Enderman Slayer XP to LVL " + (SlayerBossType.SlayerMobType.ENDERMAN.getLevelForXP(User.getUser(player.getUniqueId()).getEndermanSlayerXP()) + 1) + ":"), Sputnik.trans(SUtil.createLineProgressBar(18, ChatColor.DARK_PURPLE, User.getUser(player.getUniqueId()).getEndermanSlayerXP(), SlayerBossType.staticGetXPReqForLevel(SlayerBossType.SlayerMobType.ENDERMAN.getLevelForXP(User.getUser(player.getUniqueId()).getEndermanSlayerXP()), EntityType.ENDERMAN))), " ", Sputnik.trans("&cNot available on Semi-Sandbox mode!"));
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
                return SUtil.getStack((Object)ChatColor.GOLD + "Boss Drops", Material.GOLD_NUGGET, (short)0, 1, (Object)ChatColor.DARK_GRAY + "Voidgloom Seraph", " ", Sputnik.trans("&7Usually, the boss will drop"), Sputnik.trans("&aNull Sphere&7."), " ", Sputnik.trans("&7If you're lucky, you may get"), Sputnik.trans("&7one of &d5 &7possible"), Sputnik.trans("&7drops from this boss."), " ", Sputnik.trans("&cMenu is not available!"));
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
                return SUtil.getStack((Object)ChatColor.GREEN + "Slayers Recipe", Material.BOOK, (short)0, 1, (Object)ChatColor.DARK_GRAY + "Voidgloom Seraph", " ", Sputnik.trans("&cFeature is not available!"));
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
