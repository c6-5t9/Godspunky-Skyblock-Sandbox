package net.hypixel.skyblock.features.skill;

import net.hypixel.skyblock.user.PlayerUtils;
import net.hypixel.skyblock.user.PlayerStatistics;
import net.hypixel.skyblock.user.User;
import org.bukkit.ChatColor;
import java.util.Arrays;
import java.util.List;

public class FarmingSkill extends Skill
{
    public static final FarmingSkill INSTANCE;
    
    @Override
    public String getName() {
        return "Farming";
    }
    
    @Override
    public String getAlternativeName() {
        return "Farmhand";
    }
    
    @Override
    public List<String> getDescription() {
        return (List<String>)Arrays.asList((Object[])new String[] { "Harvest crops and shear sheep to", "earn Farming XP!" });
    }
    
    public double getDoubleDropChance(final int level) {
        return level * 4.0 / 100.0;
    }
    
    public double getHealth(final int level) {
        int health = level * 2;
        if (level >= 15) {
            health += level - 14;
        }
        if (level >= 20) {
            health += level - 19;
        }
        if (level >= 26) {
            health += level - 25;
        }
        return health;
    }
    
    @Override
    public List<String> getLevelUpInformation(final int level, final int lastLevel, final boolean showOld) {
        final String dropChance = (showOld ? ((Object)ChatColor.DARK_GRAY + "" + lastLevel * 4 + "\u279c") : "") + (Object)ChatColor.GREEN + level * 4;
        int healthPlus = 2;
        if (level >= 15) {
            healthPlus = 3;
        }
        if (level >= 20) {
            healthPlus = 4;
        }
        if (level >= 26) {
            healthPlus = 5;
        }
        return (List<String>)Arrays.asList((Object[])new String[] { (Object)ChatColor.WHITE + " Grants " + dropChance + "%" + (Object)ChatColor.WHITE + " chance", (Object)ChatColor.WHITE + " to drop 2x crops.", (Object)ChatColor.DARK_GRAY + "+" + (Object)ChatColor.GREEN + healthPlus + " " + (Object)ChatColor.RED + "\u2764 Health" });
    }
    
    @Override
    public boolean hasSixtyLevels() {
        return true;
    }
    
    @Override
    public void onSkillUpdate(final User user, final double previousXP) {
        super.onSkillUpdate(user, previousXP);
        final PlayerStatistics statistics = (PlayerStatistics)PlayerUtils.STATISTICS_CACHE.get((Object)user.getUuid());
        statistics.zeroAll(10);
        statistics.getMaxHealth().set(10, Double.valueOf(this.getHealth(Skill.getLevel(user.getSkillXP(this), this.hasSixtyLevels()))));
    }
    
    static {
        INSTANCE = new FarmingSkill();
    }
}
