package net.hypixel.skyblock.features.skill;

import net.hypixel.skyblock.user.PlayerUtils;
import net.hypixel.skyblock.user.PlayerStatistics;
import net.hypixel.skyblock.user.User;
import org.bukkit.ChatColor;
import java.util.Arrays;
import java.util.List;

public class ForagingSkill extends Skill
{
    public static final ForagingSkill INSTANCE;
    
    @Override
    public String getName() {
        return "Foraging";
    }
    
    @Override
    public String getAlternativeName() {
        return "Logger";
    }
    
    @Override
    public List<String> getDescription() {
        return (List<String>)Arrays.asList((Object[])new String[] { "Cut trees and forage for other", "plants to earn Foraging XP!" });
    }
    
    public double getDoubleDropChance(final int level) {
        return level * 4.0 / 100.0;
    }
    
    public double getTripleDropChance(final int level) {
        return (level - 25.0) * 4.0 / 100.0;
    }
    
    public double getStrength(final int level) {
        return (level < 15.0) ? level : (level + (level - 14.0));
    }
    
    @Override
    public List<String> getLevelUpInformation(final int level, final int lastLevel, final boolean showOld) {
        String dropChance = (showOld ? ((Object)ChatColor.DARK_GRAY + "" + lastLevel * 4 + "\u279c") : "") + (Object)ChatColor.GREEN + level * 4;
        if (level > 25) {
            dropChance = (showOld ? ((Object)ChatColor.DARK_GRAY + "" + (lastLevel - 25) * 4 + "\u279c") : "") + (Object)ChatColor.GREEN + (level - 25) * 4;
        }
        return (List<String>)Arrays.asList((Object[])new String[] { (Object)ChatColor.WHITE + " Grants " + dropChance + "%" + (Object)ChatColor.WHITE + " chance", (Object)ChatColor.WHITE + " to drop " + ((level > 25) ? "3" : "2") + "x logs.", (Object)ChatColor.DARK_GRAY + "+" + (Object)ChatColor.GREEN + ((level >= 15) ? "2" : "1") + " " + (Object)ChatColor.RED + "\u2741 Strength" });
    }
    
    @Override
    public boolean hasSixtyLevels() {
        return false;
    }
    
    @Override
    public void onSkillUpdate(final User user, final double previousXP) {
        super.onSkillUpdate(user, previousXP);
        final PlayerStatistics statistics = (PlayerStatistics)PlayerUtils.STATISTICS_CACHE.get((Object)user.getUuid());
        statistics.zeroAll(13);
        statistics.getStrength().set(13, Double.valueOf(this.getStrength(Skill.getLevel(user.getSkillXP(this), this.hasSixtyLevels()))));
    }
    
    static {
        INSTANCE = new ForagingSkill();
    }
}
