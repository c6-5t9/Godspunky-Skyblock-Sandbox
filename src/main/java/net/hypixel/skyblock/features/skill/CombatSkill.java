package net.hypixel.skyblock.features.skill;

import net.hypixel.skyblock.user.PlayerUtils;
import net.hypixel.skyblock.user.PlayerStatistics;
import net.hypixel.skyblock.user.User;
import org.bukkit.ChatColor;
import java.util.Arrays;
import java.util.List;

public class CombatSkill extends Skill
{
    public static final CombatSkill INSTANCE;
    
    @Override
    public String getName() {
        return "Combat";
    }
    
    @Override
    public String getAlternativeName() {
        return "Warrior";
    }
    
    @Override
    public List<String> getDescription() {
        return (List<String>)Arrays.asList((Object[])new String[] { "Fight mobs and players to earn", "Combat XP!" });
    }
    
    @Override
    public List<String> getLevelUpInformation(final int level, final int lastLevel, final boolean showOld) {
        return (List<String>)Arrays.asList((Object[])new String[] { (Object)ChatColor.WHITE + " Deal " + (showOld ? ((Object)ChatColor.DARK_GRAY + "" + lastLevel * 4 + "\u279c") : "") + (Object)ChatColor.GREEN + level * 4 + "% " + (Object)ChatColor.WHITE + "more damage to mobs.", (Object)ChatColor.DARK_GRAY + "+" + (Object)ChatColor.GREEN + "0.5% " + (Object)ChatColor.BLUE + "\u2623 Crit Chance" });
    }
    
    @Override
    public boolean hasSixtyLevels() {
        return false;
    }
    
    @Override
    public void onSkillUpdate(final User user, final double previousXP) {
        super.onSkillUpdate(user, previousXP);
        final PlayerStatistics statistics = (PlayerStatistics)PlayerUtils.STATISTICS_CACHE.get((Object)user.getUuid());
        statistics.zeroAll(12);
        statistics.getCritChance().set(12, Double.valueOf(0.005 * Skill.getLevel(user.getCombatXP(), false)));
    }
    
    static {
        INSTANCE = new CombatSkill();
    }
}
