package net.hypixel.skyblock.item.foraging;

import net.hypixel.skyblock.item.GenericItemType;
import net.hypixel.skyblock.item.Rarity;
import net.hypixel.skyblock.features.skill.ForagingSkill;
import net.hypixel.skyblock.features.skill.Skill;
import net.hypixel.skyblock.item.MaterialFunction;
import net.hypixel.skyblock.item.ExperienceRewardStatistics;

public class OakWood implements ExperienceRewardStatistics, MaterialFunction
{
    @Override
    public double getRewardXP() {
        return 6.0;
    }
    
    @Override
    public Skill getRewardedSkill() {
        return ForagingSkill.INSTANCE;
    }
    
    @Override
    public String getDisplayName() {
        return "Oak Wood";
    }
    
    @Override
    public Rarity getRarity() {
        return Rarity.COMMON;
    }
    
    @Override
    public GenericItemType getType() {
        return GenericItemType.ITEM;
    }
}
