package net.hypixel.skyblock.features.skill;

import net.hypixel.skyblock.user.PlayerUtils;
import net.hypixel.skyblock.user.PlayerStatistics;
import net.hypixel.skyblock.user.User;
import org.bukkit.ChatColor;
import java.util.Arrays;
import java.util.List;

public class EnchantingSkill extends Skill
{
    public static final EnchantingSkill INSTANCE;
    
    @Override
    public String getName() {
        return "Enchanting";
    }
    
    @Override
    public String getAlternativeName() {
        return "Conjurer";
    }
    
    @Override
    public List<String> getDescription() {
        return (List<String>)Arrays.asList((Object[])new String[] { "Enchant items to earn Enchanting", "XP!" });
    }
    
    @Override
    public List<String> getLevelUpInformation(final int level, final int lastLevel, final boolean showOld) {
        return (List<String>)Arrays.asList((Object[])new String[] { (Object)ChatColor.WHITE + " Gain " + (showOld ? ((Object)ChatColor.DARK_GRAY + "" + lastLevel * 4 + "\u279c") : "") + (Object)ChatColor.GREEN + level * 4 + "% " + (Object)ChatColor.WHITE + "more experience orbs", (Object)ChatColor.WHITE + " from any source.", (Object)ChatColor.DARK_GRAY + "+" + (Object)ChatColor.GREEN + "0.5% " + (Object)ChatColor.RED + "\u0e51 Ability Damage", (Object)ChatColor.DARK_GRAY + "+" + (Object)ChatColor.GREEN + "2 " + (Object)ChatColor.AQUA + "\u270e Intelligence" });
    }
    
    @Override
    public boolean hasSixtyLevels() {
        return false;
    }
    
    @Override
    public void onSkillUpdate(final User user, final double previousXP) {
        super.onSkillUpdate(user, previousXP);
        final PlayerStatistics statistics = (PlayerStatistics)PlayerUtils.STATISTICS_CACHE.get((Object)user.getUuid());
        statistics.zeroAll(14);
        statistics.getAbilityDamage().set(14, Double.valueOf(0.5 * Skill.getLevel(user.getEnchantXP(), false)));
        statistics.getIntelligence().set(14, Double.valueOf((double)(2 * Skill.getLevel(user.getEnchantXP(), false))));
    }
    
    static {
        INSTANCE = new EnchantingSkill();
    }
}
