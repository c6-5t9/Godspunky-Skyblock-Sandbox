package net.hypixel.skyblock.gui;

import net.hypixel.skyblock.util.Sputnik;
import net.hypixel.skyblock.user.PlayerUtils;
import net.hypixel.skyblock.features.slayer.SlayerBossType;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import net.hypixel.skyblock.util.SUtil;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.entity.Player;
import net.hypixel.skyblock.features.slayer.SlayerQuest;
import net.hypixel.skyblock.user.User;

public class SlayerGUI extends GUI
{
    public SlayerGUI() {
        super("Slayer", 36);
    }
    
    @Override
    public void onOpen(final GUIOpenEvent e) {
        final Player player = e.getPlayer();
        final User user = User.getUser(e.getPlayer().getUniqueId());
        this.fill(SlayerGUI.BLACK_STAINED_GLASS_PANE);
        this.set(GUIClickableItem.getCloseItem(31));
        final SlayerQuest quest = user.getSlayerQuest();
        if (quest != null) {
            if (quest.getKilled() != 0L) {
                this.set(new GUIClickableItem() {
                    @Override
                    public void run(final InventoryClickEvent e) {
                        user.setSlayerXP(quest.getType().getType(), user.getSlayerXP(quest.getType().getType()) + quest.getType().getRewardXP());
                        final int level = quest.getType().getType().getLevelForXP(user.getSlayerXP(quest.getType().getType()));
                        player.playSound(player.getLocation(), Sound.LEVEL_UP, 1.0f, 2.0f);
                        player.sendMessage("  " + (Object)ChatColor.GREEN + (Object)ChatColor.BOLD + "SLAYER QUEST COMPLETED!");
                        player.sendMessage("   " + (Object)ChatColor.YELLOW + quest.getType().getType().getName() + " Slayer LVL " + level + (Object)ChatColor.DARK_RED + " - " + (Object)ChatColor.GRAY + "Next LVL in " + (Object)ChatColor.LIGHT_PURPLE + SUtil.commaify(quest.getType().getXPReqForLevel(level) - user.getSlayerXP(quest.getType().getType())) + " XP" + (Object)ChatColor.GRAY + "!");
                        user.setSlayerQuest(null);
                        GUIType.SLAYER.getGUI().open(player);
                    }
                    
                    @Override
                    public ItemStack getItem() {
                        return SUtil.getStack((Object)ChatColor.GREEN + "Slayer Quest Complete!", quest.getType().getType().getIcon(), (short)0, 1, (Object)ChatColor.GRAY + "You've slain the boss!", (Object)ChatColor.GRAY + "SkySim is now a little", (Object)ChatColor.GRAY + "safer thanks to you!", "", (Object)ChatColor.GRAY + "Boss: " + quest.getType().getDisplayName(), "", (Object)ChatColor.DARK_GRAY + "Time to spawn: " + SUtil.getSlayerFormattedTime(quest.getSpawned() - quest.getStarted()), (Object)ChatColor.DARK_GRAY + "Time to kill: " + SUtil.getSlayerFormattedTime(quest.getKilled() - quest.getSpawned()), "", (Object)ChatColor.GRAY + "Reward: " + (Object)ChatColor.DARK_PURPLE + quest.getType().getRewardXP() + " " + quest.getType().getType().getName() + " Slayer XP", "", (Object)ChatColor.YELLOW + "Click to collect reward!");
                    }
                    
                    @Override
                    public int getSlot() {
                        return 13;
                    }
                });
            }
            else if (quest.getDied() != 0L) {
                this.set(new GUIClickableItem() {
                    @Override
                    public void run(final InventoryClickEvent e) {
                        user.setSlayerQuest(null);
                        player.sendMessage((Object)ChatColor.YELLOW + "Your unsuccessful quest has been cleared out!");
                        GUIType.SLAYER.getGUI().open(player);
                    }
                    
                    @Override
                    public ItemStack getItem() {
                        return SUtil.getStack((Object)ChatColor.GREEN + "Slayer Quest Failed", Material.STAINED_CLAY, (short)14, 1, (Object)ChatColor.GRAY + "You've didn't succeed in", (Object)ChatColor.GRAY + "killing the boss on your", (Object)ChatColor.GRAY + "last Slayer quest.", "", (Object)ChatColor.GRAY + "Quest from: " + (Object)ChatColor.AQUA + SUtil.getSlayerFormattedTime(System.currentTimeMillis() - quest.getStarted()) + " ago", "", (Object)ChatColor.DARK_GRAY + "It's no big deal! You can", (Object)ChatColor.DARK_GRAY + "always try again!", "", (Object)ChatColor.YELLOW + "Ok, thanks for reminding me!");
                    }
                    
                    @Override
                    public int getSlot() {
                        return 13;
                    }
                });
            }
            else {
                this.set(new GUIClickableItem() {
                    @Override
                    public void run(final InventoryClickEvent e) {
                        new SlayerCancellationConfirmGUI(user).open(player);
                    }
                    
                    @Override
                    public ItemStack getItem() {
                        return SUtil.getStack((Object)ChatColor.GREEN + "Ongoing Slayer Quest", quest.getType().getType().getIcon(), (short)0, 1, (Object)ChatColor.GRAY + "You have an active Slayer", (Object)ChatColor.GRAY + "quest.", "", (Object)ChatColor.GRAY + "Boss: " + quest.getType().getDisplayName(), (Object)ChatColor.YELLOW + "Kill " + quest.getType().getType().getPluralName() + " to spawn the boss!", "", (Object)ChatColor.YELLOW + "Click to cancel the quest!");
                    }
                    
                    @Override
                    public int getSlot() {
                        return 13;
                    }
                });
            }
            return;
        }
        this.set(30, SUtil.getStack((Object)ChatColor.GREEN + "Random Slayer Quest", Material.WATCH, (short)0, 1, (Object)ChatColor.DARK_GRAY + "Extra Rewards", "", (Object)ChatColor.GRAY + "Start a slayer quest for a", (Object)ChatColor.GRAY + "random boss.", "", (Object)ChatColor.GRAY + "Quests started this way reward", (Object)ChatColor.GRAY + "more items and " + (Object)ChatColor.LIGHT_PURPLE + "XP" + (Object)ChatColor.GRAY + ".", "", (Object)ChatColor.RED + "Coming soon!"));
        this.set(32, SUtil.getStack((Object)ChatColor.GREEN + "Global Combat XP Buff", Material.WHEAT, (short)0, 1, (Object)ChatColor.DARK_GRAY + "Slayer Bonus", "", (Object)ChatColor.GRAY + "Total buff: " + (Object)ChatColor.AQUA + "+" + user.getSlayerCombatXPBuff() + "% Combat XP", "", (Object)ChatColor.GRAY + "Earn extra Combat XP based on", (Object)ChatColor.GRAY + "your unique slayer boss kills.", "", (Object)ChatColor.DARK_GRAY + "Highest slain tiers", (Object)ChatColor.GRAY + "Revenant Horror: " + getTierText(user.getHighestRevenantHorror()), (Object)ChatColor.GRAY + "Tarantula Broodfather: " + getTierText(user.getHighestTarantulaBroodfather()), (Object)ChatColor.GRAY + "Sven Packmaster: " + getTierText(user.getHighestSvenPackmaster()), "", (Object)ChatColor.GRAY + "Tier I, II, III grant " + (Object)ChatColor.AQUA + "+1% XP" + (Object)ChatColor.GRAY + ".", (Object)ChatColor.GRAY + "Tier IV grants " + (Object)ChatColor.AQUA + "+2% XP" + (Object)ChatColor.GRAY + "."));
        this.set(new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent e) {
                GUIType.REVENANT_HORROR.getGUI().open((Player)e.getWhoClicked());
            }
            
            @Override
            public int getSlot() {
                return 10;
            }
            
            @Override
            public ItemStack getItem() {
                return SUtil.getStack((Object)ChatColor.RED + "\u2620 " + (Object)ChatColor.YELLOW + "Revenant Horror", Material.ROTTEN_FLESH, (short)0, 1, (Object)ChatColor.GRAY + "Abhorrant Zombie stuck", (Object)ChatColor.GRAY + "between life and death for", (Object)ChatColor.GRAY + "an eternity.", "", (Object)ChatColor.GRAY + "Zombie Slayer: " + (Object)ChatColor.YELLOW + "LVL " + SlayerBossType.SlayerMobType.ZOMBIE.getLevelForXP(user.getZombieSlayerXP()), "", (Object)ChatColor.YELLOW + "Click to view boss!");
            }
        });
        this.set(new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent e) {
                GUIType.TARANTULA_BROODFATHER.getGUI().open((Player)e.getWhoClicked());
            }
            
            @Override
            public int getSlot() {
                return 11;
            }
            
            @Override
            public ItemStack getItem() {
                return SUtil.getStack((Object)ChatColor.RED + "\u2620 " + (Object)ChatColor.YELLOW + "Tarantula Broodfather", Material.WEB, (short)0, 1, (Object)ChatColor.GRAY + "Monstrous Spider who poisons", (Object)ChatColor.GRAY + "and devours its victims.", "", (Object)ChatColor.GRAY + "Spider Slayer: " + (Object)ChatColor.YELLOW + "LVL " + SlayerBossType.SlayerMobType.SPIDER.getLevelForXP(user.getSpiderSlayerXP()), "", (Object)ChatColor.YELLOW + "Click to view boss!");
            }
        });
        this.set(new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent e) {
                final Player p = (Player)e.getWhoClicked();
                if (p == null) {
                    return;
                }
                if (PlayerUtils.isAutoSlayer(player)) {
                    PlayerUtils.AUTO_SLAYER.put((Object)player.getUniqueId(), (Object)false);
                }
                else {
                    PlayerUtils.AUTO_SLAYER.put((Object)player.getUniqueId(), (Object)true);
                }
                GUIType.SLAYER.getGUI().open((Player)e.getWhoClicked());
            }
            
            @Override
            public int getSlot() {
                return 28;
            }
            
            @Override
            public ItemStack getItem() {
                ItemStack isBuilder = new ItemStack(Material.BEDROCK, 1);
                if (PlayerUtils.isAutoSlayer(player)) {
                    isBuilder = SUtil.getStack(Sputnik.trans("&bAuto-Slayer"), Material.INK_SACK, (short)10, 1, (Object)ChatColor.GRAY + "Upon defeating a boss,", Sputnik.trans("&aautomatically &7completes"), (Object)ChatColor.GRAY + "the quest and starts", (Object)ChatColor.GRAY + "another one of the same type.", "", Sputnik.trans("&7Currently: &aEnabled"), "", (Object)ChatColor.YELLOW + "Click to disable!");
                }
                else {
                    isBuilder = SUtil.getStack(Sputnik.trans("&bAuto-Slayer"), Material.INK_SACK, (short)8, 1, (Object)ChatColor.GRAY + "Upon defeating a boss,", Sputnik.trans("&aautomatically &7completes"), (Object)ChatColor.GRAY + "the quest and starts", (Object)ChatColor.GRAY + "another one of the same type.", "", Sputnik.trans("&7Currently: &cDisabled"), "", (Object)ChatColor.YELLOW + "Click to enable!");
                }
                return isBuilder;
            }
        });
        this.set(new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent e) {
                GUIType.SVEN_PACKMASTER.getGUI().open((Player)e.getWhoClicked());
            }
            
            @Override
            public int getSlot() {
                return 12;
            }
            
            @Override
            public ItemStack getItem() {
                return SUtil.getStack((Object)ChatColor.RED + "\u2620 " + (Object)ChatColor.YELLOW + "Sven Packmaster", Material.MUTTON, (short)0, 1, (Object)ChatColor.GRAY + "Rabid Wolf genetically", (Object)ChatColor.GRAY + "modified by a famous mad", (Object)ChatColor.GRAY + "scientist. Eats bones and", (Object)ChatColor.GRAY + "flesh.", "", (Object)ChatColor.GRAY + "Wolf Slayer: " + (Object)ChatColor.YELLOW + "LVL " + SlayerBossType.SlayerMobType.WOLF.getLevelForXP(user.getWolfSlayerXP()), "", (Object)ChatColor.YELLOW + "Click to view boss!");
            }
        });
        this.set(new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent e) {
                GUIType.VOIDGLOOM_SERAPH.getGUI().open((Player)e.getWhoClicked());
            }
            
            @Override
            public int getSlot() {
                return 13;
            }
            
            @Override
            public ItemStack getItem() {
                return SUtil.getStack((Object)ChatColor.RED + "\u2620 " + (Object)ChatColor.YELLOW + "Voidgloom Seraph", Material.ENDER_PEARL, (short)0, 1, (Object)ChatColor.GRAY + "If Necron is the right-hand", (Object)ChatColor.GRAY + "of the Wither King, this dark", (Object)ChatColor.GRAY + "demigod is the left-hand.", "", (Object)ChatColor.GRAY + "Enderman Slayer: " + (Object)ChatColor.YELLOW + "LVL " + SlayerBossType.SlayerMobType.WOLF.getLevelForXP(user.getEndermanSlayerXP()), "", (Object)ChatColor.YELLOW + "Click to view boss!");
            }
        });
        this.fill(SUtil.getStack((Object)ChatColor.RED + "Not released yet!", Material.COAL_BLOCK, (short)0, 1, (Object)ChatColor.GRAY + "This boss is still in", (Object)ChatColor.GRAY + "development!"), 14, 16);
    }
    
    public static String getTierText(final int highest) {
        if (highest == 0) {
            return (Object)ChatColor.GREEN + "Not played!";
        }
        ChatColor color = ChatColor.GREEN;
        if (highest == 2) {
            color = ChatColor.YELLOW;
        }
        if (highest == 3) {
            color = ChatColor.RED;
        }
        if (highest == 4) {
            color = ChatColor.DARK_RED;
        }
        if (highest == 5) {
            color = ChatColor.DARK_PURPLE;
        }
        return (Object)color + "Tier " + SUtil.toRomanNumeral(highest);
    }
    
    public static void claimReward(final Player player) {
        final User user = User.getUser(player.getUniqueId());
        final SlayerQuest quest = user.getSlayerQuest();
        user.setSlayerXP(quest.getType().getType(), user.getSlayerXP(quest.getType().getType()) + quest.getType().getRewardXP());
        final int level = quest.getType().getType().getLevelForXP(user.getSlayerXP(quest.getType().getType()));
        player.playSound(player.getLocation(), Sound.LEVEL_UP, 1.0f, 2.0f);
        player.sendMessage("  " + (Object)ChatColor.GREEN + (Object)ChatColor.BOLD + "SLAYER QUEST COMPLETED!");
        player.sendMessage("   " + (Object)ChatColor.YELLOW + quest.getType().getType().getName() + " Slayer LVL " + level + (Object)ChatColor.DARK_RED + " - " + (Object)ChatColor.GRAY + "Next LVL in " + (Object)ChatColor.LIGHT_PURPLE + SUtil.commaify(quest.getType().getXPReqForLevel(level) - user.getSlayerXP(quest.getType().getType())) + " XP" + (Object)ChatColor.GRAY + "!");
        user.setSlayerQuest(null);
    }
}
