package net.hypixel.skyblock.features.skill;

import org.bukkit.ChatColor;
import java.util.ArrayList;
import net.hypixel.skyblock.user.User;
import java.util.Collections;
import java.util.List;

public class TankSkill extends Skill implements DungeonsSkill
{
    public static final TankSkill INSTANCE;
    
    @Override
    public String getName() {
        return "Tank";
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
        t.add((Object)("Protective Barrier" + (Object)ChatColor.RED + " Soon!"));
        t.add((Object)"Taunt");
        t.add((Object)"Diversion");
        t.add((Object)"Defensive Stance");
        return t;
    }
    
    @Override
    public List<String> getOrb() {
        final List<String> t = (List<String>)new ArrayList();
        t.add((Object)"Seismic Wave");
        t.add((Object)"Castle of Stone");
        return t;
    }
    
    @Override
    public List<String> getGhost() {
        final List<String> t = (List<String>)new ArrayList();
        t.add((Object)"Stun Potion");
        t.add((Object)"Absorption Potion");
        return t;
    }
    
    static {
        INSTANCE = new TankSkill();
    }
}
