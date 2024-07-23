package net.hypixel.skyblock.item;

import net.hypixel.skyblock.features.skill.Skill;

public interface ExperienceRewardStatistics extends MaterialStatistics
{
    double getRewardXP();
    
    Skill getRewardedSkill();
}
