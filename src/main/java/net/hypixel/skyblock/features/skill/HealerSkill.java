package net.hypixel.skyblock.features.skill;

import org.bukkit.ChatColor;
import java.util.ArrayList;
import net.hypixel.skyblock.user.User;
import java.util.Collections;
import java.util.List;

public class HealerSkill extends Skill implements DungeonsSkill
{
    public static final HealerSkill INSTANCE;
    
    @Override
    public String getName() {
        return "Healer";
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
        t.add((Object)"Renew");
        t.add((Object)"Healing Aura");
        t.add((Object)"Revive");
        t.add((Object)("Orbies" + (Object)ChatColor.RED + " Soon!"));
        t.add((Object)("Soul Tether" + (Object)ChatColor.RED + " Soon!"));
        return t;
    }
    
    @Override
    public List<String> getOrb() {
        final List<String> t = (List<String>)new ArrayList();
        t.add((Object)"Healing Circle");
        t.add((Object)"Wish");
        return t;
    }
    
    @Override
    public List<String> getGhost() {
        final List<String> t = (List<String>)new ArrayList();
        t.add((Object)"Healing Potion");
        t.add((Object)"Revive Self");
        return t;
    }
    
    static {
        INSTANCE = new HealerSkill();
    }
}
