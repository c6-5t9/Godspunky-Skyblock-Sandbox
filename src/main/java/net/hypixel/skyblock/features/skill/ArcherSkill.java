package net.hypixel.skyblock.features.skill;

import org.bukkit.ChatColor;
import java.util.ArrayList;
import net.hypixel.skyblock.user.User;
import java.util.Collections;
import java.util.List;

public class ArcherSkill extends Skill implements DungeonsSkill
{
    public static final ArcherSkill INSTANCE;
    
    @Override
    public String getName() {
        return "Archer";
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
        t.add((Object)"Doubleshot");
        t.add((Object)"Bone Plating");
        t.add((Object)("Bouncy Arrows" + (Object)ChatColor.RED + " Soon!"));
        return t;
    }
    
    @Override
    public List<String> getOrb() {
        final List<String> t = (List<String>)new ArrayList();
        t.add((Object)"Explosive Shot");
        t.add((Object)"Machine Gun Bow");
        return t;
    }
    
    @Override
    public List<String> getGhost() {
        final List<String> t = (List<String>)new ArrayList();
        t.add((Object)"Stun Bow");
        t.add((Object)"Healing Bow");
        return t;
    }
    
    static {
        INSTANCE = new ArcherSkill();
    }
}
