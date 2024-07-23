package net.hypixel.skyblock.features.skill;

import java.util.ArrayList;
import net.hypixel.skyblock.user.User;
import java.util.Collections;
import java.util.List;

public class MageSkill extends Skill implements DungeonsSkill
{
    public static final MageSkill INSTANCE;
    
    @Override
    public String getName() {
        return "Mage";
    }
    
    @Override
    public String getAlternativeName() {
        return "{skip}";
    }
    
    @Override
    public List<String> getDescription() {
        return (List<String>)Collections.singletonList((Object)"");
    }
    
    @Override
    public List<String> getLevelUpInformation(final int level, final int lastLevel, final boolean showOld) {
        return (List<String>)Collections.singletonList((Object)"");
    }
    
    @Override
    public boolean hasSixtyLevels() {
        return false;
    }
    
    @Override
    public void onSkillUpdate(final User user, final double previousXP) {
        super.onSkillUpdate(user, previousXP);
    }
    
    @Override
    public List<String> getPassive() {
        final List<String> t = (List<String>)new ArrayList();
        t.add((Object)"Mage Staff");
        t.add((Object)"Efficent Spells");
        return t;
    }
    
    @Override
    public List<String> getOrb() {
        final List<String> t = (List<String>)new ArrayList();
        t.add((Object)"Guided Sheep");
        t.add((Object)"Thunderstorm");
        return t;
    }
    
    @Override
    public List<String> getGhost() {
        final List<String> t = (List<String>)new ArrayList();
        t.add((Object)"Pop-up Wall");
        t.add((Object)"Fireball");
        return t;
    }
    
    static {
        INSTANCE = new MageSkill();
    }
}
