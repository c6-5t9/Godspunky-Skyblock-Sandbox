package net.hypixel.skyblock.features.skill;

import net.hypixel.skyblock.user.User;
import net.hypixel.skyblock.util.Sputnik;
import net.hypixel.skyblock.util.SUtil;
import java.util.Arrays;
import java.util.List;

public class CatacombsSkill extends Skill
{
    public static final CatacombsSkill INSTANCE;
    
    @Override
    public String getName() {
        return "The Catacombs";
    }
    
    @Override
    public String getAlternativeName() {
        return "{skip}";
    }
    
    @Override
    public List<String> getDescription() {
        return (List<String>)Arrays.asList((Object[])new String[] { "Complete this dungeon to earn", "experience and unlock new", "rewards!" });
    }
    
    @Override
    public List<String> getLevelUpInformation(final int level, final int lastLevel, final boolean showOld) {
        return (List<String>)Arrays.asList((Object[])new String[] { Sputnik.trans("&7Level " + SUtil.toRomanNumeral(level) + ": &7Increasing the stats"), Sputnik.trans("&7of your Dungeon items by"), Sputnik.trans("&c" + level * 5 + "% &7while in &cThe"), Sputnik.trans("&cCatacombs&7.") });
    }
    
    @Override
    public boolean hasSixtyLevels() {
        return false;
    }
    
    @Override
    public void onSkillUpdate(final User user, final double previousXP) {
        super.onSkillUpdate(user, previousXP);
    }
    
    static {
        INSTANCE = new CatacombsSkill();
    }
}
