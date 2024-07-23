package net.hypixel.skyblock.item.farming;

import net.hypixel.skyblock.item.GenericItemType;
import net.hypixel.skyblock.item.Rarity;
import net.hypixel.skyblock.features.skill.FarmingSkill;
import net.hypixel.skyblock.features.skill.Skill;
import net.hypixel.skyblock.item.MaterialFunction;
import net.hypixel.skyblock.item.ExperienceRewardStatistics;

public class Potato implements ExperienceRewardStatistics, MaterialFunction
{
    @Override
    public double getRewardXP() {
        return 1.0;
    }
    
    @Override
    public Skill getRewardedSkill() {
        return FarmingSkill.INSTANCE;
    }
    
    @Override
    public String getDisplayName() {
        return "Potato";
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
