package net.hypixel.skyblock.features.requirement.enums;

import net.hypixel.skyblock.features.skill.MiningSkill;
import net.hypixel.skyblock.features.skill.ForagingSkill;
import net.hypixel.skyblock.features.skill.EnchantingSkill;
import net.hypixel.skyblock.features.skill.FarmingSkill;
import net.hypixel.skyblock.features.skill.CombatSkill;
import net.hypixel.skyblock.features.skill.Skill;

public enum SkillType
{
    COMBAT((Skill)new CombatSkill()), 
    FARMING((Skill)new FarmingSkill()), 
    ENCHANTING((Skill)new EnchantingSkill()), 
    FORAGING((Skill)new ForagingSkill()), 
    MINING((Skill)new MiningSkill());
    
    private final Skill skill;
    
    private SkillType(final Skill skill) {
        this.skill = skill;
    }
    
    public Skill getSkill() {
        return this.skill;
    }
}
