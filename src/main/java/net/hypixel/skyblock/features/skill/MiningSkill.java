package net.hypixel.skyblock.features.skill;

import net.hypixel.skyblock.user.PlayerUtils;
import net.hypixel.skyblock.user.PlayerStatistics;
import net.hypixel.skyblock.user.User;
import org.bukkit.ChatColor;
import java.util.Arrays;
import java.util.List;

public class MiningSkill extends Skill
{
    public static final MiningSkill INSTANCE;
    
    @Override
    public String getName() {
        return "Mining";
    }
    
    @Override
    public String getAlternativeName() {
        return "Spelunker";
    }
    
    @Override
    public List<String> getDescription() {
        return (List<String>)Arrays.asList((Object[])new String[] { "Spelunk islands for ores and", "valuable materials to earn", "Mining XP!" });
    }
    
    public double getDoubleDropChance(final int level) {
        return level * 4.0 / 100.0;
    }
    
    public double getTripleDropChance(final int level) {
        return (level - 25.0) * 4.0 / 100.0;
    }
    
    public double getDefense(final int level) {
        return (level < 15.0) ? level : (level + (level - 14.0));
    }
    
    @Override
    public List<String> getLevelUpInformation(final int level, final int lastLevel, final boolean showOld) {
        String dropChance = (showOld ? ((Object)ChatColor.DARK_GRAY + "" + lastLevel * 4 + "\u279c") : "") + (Object)ChatColor.GREEN + level * 4;
        if (level > 25) {
            dropChance = (showOld ? ((Object)ChatColor.DARK_GRAY + "" + (lastLevel - 25) * 4 + "\u279c") : "") + (Object)ChatColor.GREEN + (level - 25) * 4;
        }
        return (List<String>)Arrays.asList((Object[])new String[] { (Object)ChatColor.WHITE + " Grants " + dropChance + "%" + (Object)ChatColor.WHITE + " chance", (Object)ChatColor.WHITE + " to drop " + ((level > 25) ? "3" : "2") + "x ores.", (Object)ChatColor.DARK_GRAY + "+" + (Object)ChatColor.GREEN + ((level >= 15) ? "2" : "1") + " " + (Object)ChatColor.GREEN + "\u2748 Defense" });
    }
    
    @Override
    public boolean hasSixtyLevels() {
        return true;
    }
    
    @Override
    public void onSkillUpdate(final User user, final double previousXP) {
        super.onSkillUpdate(user, previousXP);
        final PlayerStatistics statistics = (PlayerStatistics)PlayerUtils.STATISTICS_CACHE.get((Object)user.getUuid());
        statistics.zeroAll(11);
        statistics.getDefense().set(11, Double.valueOf(this.getDefense(Skill.getLevel(user.getSkillXP(this), this.hasSixtyLevels()))));
    }
    
    static {
        INSTANCE = new MiningSkill();
    }
}
