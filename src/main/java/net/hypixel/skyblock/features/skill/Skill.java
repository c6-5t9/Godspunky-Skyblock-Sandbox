package net.hypixel.skyblock.features.skill;

import java.util.Collection;
import java.util.ArrayList;
import org.bukkit.Bukkit;
import net.hypixel.skyblock.Repeater;
import org.bukkit.Sound;
import net.hypixel.skyblock.item.pet.Pet;
import net.hypixel.skyblock.user.User;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;
import net.hypixel.skyblock.util.DefenseReplacement;
import java.util.Iterator;
import net.hypixel.skyblock.util.SUtil;
import java.util.Arrays;
import java.util.List;

public abstract class Skill
{
    public static final List<Integer> XP_GOALS;
    public static final List<Integer> COIN_REWARDS;
    
    public static int getLevel(final double xp, final boolean sixty) {
        if (xp == 0.0) {
            return 0;
        }
        if (xp >= 5.5172425E7 && !sixty) {
            return 50;
        }
        for (int i = Skill.XP_GOALS.size() - 1; i >= 0; --i) {
            if ((int)Skill.XP_GOALS.get(i) < xp) {
                return i;
            }
        }
        return 60;
    }
    
    public static int getCoinReward(final int level) {
        return (int)Skill.COIN_REWARDS.get(level);
    }
    
    public static List<Skill> getSkills() {
        return (List<Skill>)Arrays.asList((Object[])new Skill[] { FarmingSkill.INSTANCE, MiningSkill.INSTANCE, CombatSkill.INSTANCE, ForagingSkill.INSTANCE, EnchantingSkill.INSTANCE, CatacombsSkill.INSTANCE, TankSkill.INSTANCE, MageSkill.INSTANCE, BerserkSkill.INSTANCE, ArcherSkill.INSTANCE, HealerSkill.INSTANCE });
    }
    
    public static double getNextXPGoal(final double xp, final boolean sixty) {
        if (xp >= 5.5172425E7 && !sixty) {
            return 0.0;
        }
        for (int i = 0; i < Skill.XP_GOALS.size(); ++i) {
            final int goal = (int)Skill.XP_GOALS.get(i);
            if (goal > xp) {
                return goal - SUtil.getOrDefault(Skill.XP_GOALS, i - 1, 0);
            }
        }
        return 0.0;
    }
    
    public static double getNextOverallXPGoal(final double xp, final boolean sixty) {
        if (xp >= 5.5172425E7 && !sixty) {
            return 0.0;
        }
        for (final int goal : Skill.XP_GOALS) {
            if (goal > xp) {
                return goal;
            }
        }
        return 0.0;
    }
    
    public static double getXPUntilLevelUp(final double xp, final boolean sixty) {
        final double goal = getNextXPGoal(xp, sixty);
        if (goal == 0.0) {
            return Double.POSITIVE_INFINITY;
        }
        return goal - xp;
    }
    
    public static DefenseReplacement getProgressReplacement(final Skill skill, final double xp, final double additive, final long end) {
        return new DefenseReplacement() {
            @Override
            public String getReplacement() {
                final int next = (int)Skill.getNextXPGoal(xp, skill.hasSixtyLevels());
                return (Object)ChatColor.DARK_AQUA + "+" + SUtil.commaify(additive) + " " + skill.getName() + " (" + SUtil.commaify(next - (Skill.getNextOverallXPGoal(xp, skill.hasSixtyLevels()) - xp)) + "/" + SUtil.commaify(next) + ")";
            }
            
            @Override
            public long getEnd() {
                return end;
            }
        };
    }
    
    public static void reward(final Skill rewarded, final double rewardXP, final Player player) {
        final User user = User.getUser(player.getUniqueId());
        final Pet pet = user.getActivePetClass();
        if (pet != null && pet.getSkill() == rewarded) {
            final Pet.PetItem item = user.getActivePet();
            final int prevLevel = Pet.getLevel(item.getXp(), item.getRarity());
            item.setXp(item.getXp() + rewardXP);
            final int level = Pet.getLevel(item.getXp(), item.getRarity());
            if (prevLevel != level) {
                player.playSound(player.getLocation(), Sound.LEVEL_UP, 1.0f, 2.0f);
                player.sendMessage((Object)ChatColor.GREEN + "Your " + (Object)item.getRarity().getColor() + item.getType().getDisplayName(item.getType().getData()) + (Object)ChatColor.GREEN + " levelled up to level " + (Object)ChatColor.BLUE + level + (Object)ChatColor.GREEN + "!");
            }
        }
        else if (pet != null) {
            final Pet.PetItem item = user.getActivePet();
            final int prevLevel = Pet.getLevel(item.getXp(), item.getRarity());
            item.setXp(item.getXp() + rewardXP * 25.0 / 100.0);
            final int level = Pet.getLevel(item.getXp(), item.getRarity());
            if (prevLevel != level) {
                player.playSound(player.getLocation(), Sound.LEVEL_UP, 1.0f, 2.0f);
                player.sendMessage((Object)ChatColor.GREEN + "Your " + (Object)item.getRarity().getColor() + item.getType().getDisplayName(item.getType().getData()) + (Object)ChatColor.GREEN + " levelled up to level " + (Object)ChatColor.BLUE + level + (Object)ChatColor.GREEN + "!");
            }
        }
        user.addSkillXP(rewarded, rewardXP);
        player.playSound(player.getLocation(), Sound.ORB_PICKUP, 1.0f, 2.0f);
        if (rewardXP > 0.0) {
            Repeater.DEFENSE_REPLACEMENT_MAP.put((Object)player.getUniqueId(), (Object)getProgressReplacement(rewarded, user.getSkillXP(rewarded), rewardXP, System.currentTimeMillis() + 2000L));
        }
    }
    
    public abstract String getName();
    
    public abstract String getAlternativeName();
    
    public abstract List<String> getDescription();
    
    public abstract List<String> getLevelUpInformation(final int p0, final int p1, final boolean p2);
    
    public abstract boolean hasSixtyLevels();
    
    public void onSkillUpdate(final User user, final double previousXP) {
        final int prevLevel = getLevel(previousXP, this.hasSixtyLevels());
        final int level = getLevel(user.getSkillXP(this), this.hasSixtyLevels());
        if (prevLevel != level) {
            final Player player = Bukkit.getPlayer(user.getUuid());
            if (player != null) {
                player.playSound(player.getLocation(), Sound.LEVEL_UP, 1.0f, 1.0f);
            }
            final StringBuilder builder = new StringBuilder();
            builder.append((Object)ChatColor.DARK_AQUA).append((Object)ChatColor.BOLD).append("\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\n");
            builder.append((Object)ChatColor.AQUA).append((Object)ChatColor.BOLD).append("  SKILL LEVEL UP ").append((Object)ChatColor.RESET).append((Object)ChatColor.DARK_AQUA).append(this.getName()).append(" ");
            if (prevLevel != 0) {
                builder.append((Object)ChatColor.DARK_GRAY).append(SUtil.toRomanNumeral(prevLevel)).append("\u279c");
            }
            builder.append((Object)ChatColor.DARK_AQUA).append(SUtil.toRomanNumeral(level)).append("\n");
            builder.append(" \n");
            builder.append((Object)ChatColor.GREEN).append((Object)ChatColor.BOLD).append("  REWARDS");
            for (final String line : this.getRewardLore(level, prevLevel, true)) {
                builder.append("\n   ").append(line);
            }
            builder.append((Object)ChatColor.DARK_AQUA).append((Object)ChatColor.BOLD).append("\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac");
            user.send(builder.toString());
            if (this != CatacombsSkill.INSTANCE) {
                user.addCoins(getCoinReward(level));
            }
        }
    }
    
    public List<String> getRewardLore(final int level, final int prevLevel, final boolean showOld) {
        final List<String> lore = (List<String>)new ArrayList();
        final String s = this.getAlternativeName();
        if (!s.contains((CharSequence)"{skip}")) {
            lore.add((Object)((Object)ChatColor.YELLOW + s + " " + SUtil.toRomanNumeral(level) + (Object)ChatColor.WHITE));
        }
        lore.addAll((Collection)this.getLevelUpInformation(level, prevLevel, showOld));
        if (this != CatacombsSkill.INSTANCE) {
            lore.add((Object)((Object)ChatColor.DARK_GRAY + "+" + (Object)ChatColor.GOLD + SUtil.commaify(getCoinReward(level)) + (Object)ChatColor.GRAY + " Coins"));
        }
        return lore;
    }
    
    static {
        XP_GOALS = Arrays.asList((Object[])new Integer[] { 0, 50, 175, 375, 675, 1175, 1925, 2925, 4425, 6425, 9925, 14925, 22425, 32425, 47425, 67425, 97425, 147425, 222425, 322425, 522425, 822425, 1222425, 1722425, 2322425, 3022425, 3822425, 4722425, 5722425, 6822425, 8022425, 9322425, 10722425, 12222425, 13822425, 15522425, 17322425, 19222425, 21222425, 23322425, 25522425, 27822425, 30222425, 32722425, 35322425, 38072425, 40972425, 44072425, 47472425, 51172425, 55172425, 59472425, 64072425, 68972425, 74172425, 79672425, 85472425, 91572425, 97972425, 104672425, 111672425 });
        COIN_REWARDS = Arrays.asList((Object[])new Integer[] { 0, 25, 50, 100, 200, 300, 400, 500, 600, 700, 800, 900, 1000, 1100, 1200, 1300, 1400, 1500, 1600, 1700, 1800, 1900, 2000, 2200, 2400, 2600, 2800, 3000, 3500, 4000, 5000, 6000, 7500, 10000, 12500, 15000, 17500, 20000, 25000, 30000, 35000, 40000, 45000, 50000, 60000, 70000, 80000, 90000, 100000, 125000, 150000, 175000, 200000, 250000, 300000, 350000, 400000, 450000, 500000, 600000, 700000, 800000, 1000000 });
    }
}
