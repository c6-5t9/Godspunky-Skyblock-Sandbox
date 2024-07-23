package net.hypixel.skyblock.features.skill;

import java.util.ArrayList;
import net.hypixel.skyblock.user.User;
import java.util.Collections;
import java.util.List;

public class BerserkSkill extends Skill implements DungeonsSkill
{
    public static final BerserkSkill INSTANCE;
    
    @Override
    public String getName() {
        return "Berserk";
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
        t.add((Object)"Bloodlust");
        t.add((Object)"Lust for Blood");
        return t;
    }
    
    @Override
    public List<String> getOrb() {
        final List<String> t = (List<String>)new ArrayList();
        t.add((Object)"Throwing Axe");
        t.add((Object)"Ragnagrok");
        return t;
    }
    
    @Override
    public List<String> getGhost() {
        final List<String> t = (List<String>)new ArrayList();
        t.add((Object)"Throwing Axe");
        t.add((Object)"Strength Potion");
        return t;
    }
    
    static {
        INSTANCE = new BerserkSkill();
    }
}
