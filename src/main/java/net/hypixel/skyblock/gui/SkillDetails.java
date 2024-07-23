package net.hypixel.skyblock.gui;

import java.util.Iterator;
import java.util.List;
import net.hypixel.skyblock.util.Sputnik;
import java.util.ArrayList;
import net.hypixel.skyblock.util.SUtil;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.event.inventory.InventoryClickEvent;
import net.hypixel.skyblock.user.User;
import net.hypixel.skyblock.features.skill.EnchantingSkill;
import net.hypixel.skyblock.features.skill.FarmingSkill;
import net.hypixel.skyblock.features.skill.ForagingSkill;
import net.hypixel.skyblock.features.skill.MiningSkill;
import net.hypixel.skyblock.features.skill.CombatSkill;
import org.bukkit.entity.Player;
import org.bukkit.Material;
import java.util.UUID;
import net.hypixel.skyblock.features.skill.Skill;

public class SkillDetails extends GUI
{
    private final Skill skill;
    private final UUID uuid;
    private Material skillInstanceMat;
    private Material skillLvlMat;
    private short data;
    private final Player player;
    private final int page;
    static final int[] slots;
    
    public SkillDetails(final Skill skill, final Player player, final int index) {
        super(skill.getName() + " Skill", 54);
        this.data = 0;
        this.skill = skill;
        this.page = index;
        this.player = player;
        this.uuid = player.getUniqueId();
    }
    
    @Override
    public void onOpen(final GUIOpenEvent e) {
        this.fill(SkillDetails.BLACK_STAINED_GLASS_PANE);
        this.set(GUIClickableItem.getCloseItem(49));
        if (this.skill instanceof CombatSkill) {
            this.skillInstanceMat = Material.STONE_SWORD;
            this.skillLvlMat = Material.DIAMOND_HELMET;
        }
        else if (this.skill instanceof MiningSkill) {
            this.skillInstanceMat = Material.STONE_PICKAXE;
            this.skillLvlMat = Material.IRON_BLOCK;
        }
        else if (this.skill instanceof ForagingSkill) {
            this.skillInstanceMat = Material.SAPLING;
            this.data = 3;
            this.skillLvlMat = Material.LOG;
        }
        else if (this.skill instanceof FarmingSkill) {
            this.skillInstanceMat = Material.GOLD_HOE;
            this.skillLvlMat = Material.HAY_BLOCK;
        }
        else if (this.skill instanceof EnchantingSkill) {
            this.skillInstanceMat = Material.ENCHANTMENT_TABLE;
            this.skillLvlMat = Material.ENCHANTED_BOOK;
        }
        final double xp = (this.skill != null) ? User.getUser(this.uuid).getSkillXP(this.skill) : 0.0;
        final int level = (this.skill != null) ? Skill.getLevel(xp, this.skill.hasSixtyLevels()) : 0;
        this.set(new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent e) {
                GUIType.SKILL_MENU.getGUI().open(SkillDetails.this.player);
            }
            
            @Override
            public int getSlot() {
                return 48;
            }
            
            @Override
            public ItemStack getItem() {
                return SUtil.getStack((Object)ChatColor.GREEN + "Go Back", Material.ARROW, (short)0, 1, (Object)ChatColor.GRAY + "To Skill Menu");
            }
        });
        String name = "";
        final List<String> l = (List<String>)new ArrayList();
        if (this.skill != null) {
            for (final String line : this.skill.getDescription()) {
                l.add((Object)((Object)ChatColor.GRAY + line));
            }
        }
        if (this.skill != null && ((level < 50 && !this.skill.hasSixtyLevels()) || (level < 60 && this.skill.hasSixtyLevels()))) {
            name = this.skill.getName();
            final int nextLevel = level + 1;
            final String numeral = SUtil.toRomanNumeral(nextLevel);
            final double nextXP = Skill.getNextOverallXPGoal(xp, this.skill.hasSixtyLevels());
            l.add((Object)" ");
            l.add((Object)SUtil.createProgressText("Progress to Level " + numeral, xp, nextXP));
            l.add((Object)SUtil.createSLineProgressBar(20, ChatColor.DARK_GREEN, xp, nextXP));
            l.add((Object)" ");
            for (final String line2 : this.skill.getRewardLore(nextLevel, nextLevel, false)) {
                l.add((Object)("  " + line2));
            }
            l.add((Object)" ");
        }
        else if (this.skill != null) {
            name = this.skill.getName();
            l.add((Object)" ");
        }
        if (this.skill != null) {
            l.add((Object)Sputnik.trans("&8Increase your " + this.skill.getName() + " Level to"));
            l.add((Object)Sputnik.trans("&8unlock Perks, statistic bonuses,"));
            l.add((Object)Sputnik.trans("&8and more!"));
        }
        this.set(0, SUtil.getStack((Object)ChatColor.GREEN + this.skill.getName() + " Skill", this.skillInstanceMat, this.data, 1, l));
        if (this.page == 1 && level >= 25) {
            this.set(new GUIClickableItem() {
                @Override
                public void run(final InventoryClickEvent e) {
                    new SkillDetails(SkillDetails.this.skill, (Player)e.getWhoClicked(), SkillDetails.this.page + 1).open(SkillDetails.this.player);
                }
                
                @Override
                public int getSlot() {
                    return 50;
                }
                
                @Override
                public ItemStack getItem() {
                    return SUtil.getStack((Object)ChatColor.GREEN + "Levels 26-50", Material.ARROW, (short)0, 1, (Object)ChatColor.YELLOW + "Click to view!");
                }
            });
        }
        else if (this.page == 2 && level >= 25) {
            this.set(new GUIClickableItem() {
                @Override
                public void run(final InventoryClickEvent e) {
                    new SkillDetails(SkillDetails.this.skill, (Player)e.getWhoClicked(), SkillDetails.this.page - 1).open(SkillDetails.this.player);
                }
                
                @Override
                public int getSlot() {
                    return 50;
                }
                
                @Override
                public ItemStack getItem() {
                    return SUtil.getStack((Object)ChatColor.GREEN + "Levels 1-25", Material.ARROW, (short)0, 1, (Object)ChatColor.YELLOW + "Click to view!");
                }
            });
        }
        else if (level < 25 && this.page > 1) {
            this.player.closeInventory();
        }
        if (this.page == 1) {
            int i = 1;
            short data = 0;
            ChatColor c = ChatColor.GRAY;
            final double nextXP2 = Skill.getNextOverallXPGoal(xp, this.skill.hasSixtyLevels());
            for (final int slot : SkillDetails.slots) {
                final List<String> lore = (List<String>)new ArrayList();
                lore.add((Object)Sputnik.trans("&7Rewards:"));
                for (final String str : this.skill.getRewardLore(i, i, false)) {
                    lore.add((Object)("  " + str));
                }
                if (i > level && i != level + 1) {
                    data = 14;
                    c = ChatColor.RED;
                }
                else if (i == level + 1) {
                    data = 4;
                    c = ChatColor.YELLOW;
                    lore.add((Object)" ");
                    lore.add((Object)SUtil.createProgressText((Object)ChatColor.GRAY + "Progress" + (Object)ChatColor.YELLOW, xp, nextXP2));
                    lore.add((Object)SUtil.createSLineProgressBar(20, ChatColor.DARK_GREEN, xp, nextXP2));
                }
                else if (i <= level) {
                    data = 5;
                    c = ChatColor.GREEN;
                    lore.add((Object)" ");
                    lore.add((Object)((Object)c + "UNLOCKED"));
                }
                if (i <= level && i % 5 == 0) {
                    this.set(slot, SUtil.getStack((Object)c + this.skill.getName() + " Level " + SUtil.toRomanNumeral(i), this.skillLvlMat, this.data, i, lore));
                }
                else {
                    this.set(slot, SUtil.getColorStack(data, (Object)c + this.skill.getName() + " Level " + SUtil.toRomanNumeral(i), lore, (short)0, i));
                }
                ++i;
            }
        }
        else if (this.page == 2) {
            int i = 26;
            short data = 0;
            ChatColor c = ChatColor.GRAY;
            final double nextXP2 = Skill.getNextOverallXPGoal(xp, this.skill.hasSixtyLevels());
            for (final int slot : SkillDetails.slots) {
                final List<String> lore = (List<String>)new ArrayList();
                lore.add((Object)Sputnik.trans("&7Rewards:"));
                for (final String str : this.skill.getRewardLore(i, i, false)) {
                    lore.add((Object)("  " + str));
                }
                if (i > level && i != level + 1) {
                    data = 14;
                    c = ChatColor.RED;
                }
                else if (i == level + 1) {
                    data = 4;
                    c = ChatColor.YELLOW;
                    lore.add((Object)" ");
                    lore.add((Object)SUtil.createProgressText((Object)ChatColor.GRAY + "Progress" + (Object)ChatColor.YELLOW, xp, nextXP2));
                    lore.add((Object)SUtil.createSLineProgressBar(20, ChatColor.DARK_GREEN, xp, nextXP2));
                }
                else if (i <= level) {
                    data = 5;
                    c = ChatColor.GREEN;
                    lore.add((Object)" ");
                    lore.add((Object)((Object)c + "UNLOCKED"));
                }
                if (i <= level && i % 5 == 0) {
                    this.set(slot, SUtil.getStack((Object)c + this.skill.getName() + " Level " + SUtil.toRomanNumeral(i), this.skillInstanceMat, this.data, i, lore));
                }
                else {
                    this.set(slot, SUtil.getColorStack(data, (Object)c + this.skill.getName() + " Level " + SUtil.toRomanNumeral(i), lore, (short)0, i));
                }
                ++i;
            }
        }
    }
    
    static {
        slots = new int[] { 9, 18, 27, 28, 29, 20, 11, 2, 3, 4, 13, 22, 31, 32, 33, 24, 15, 6, 7, 8, 17, 26, 35, 44, 53 };
    }
}
