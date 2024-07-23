package net.hypixel.skyblock.item.mining;

import net.hypixel.skyblock.item.GenericItemType;
import net.hypixel.skyblock.item.Rarity;
import net.hypixel.skyblock.features.skill.MiningSkill;
import net.hypixel.skyblock.features.skill.Skill;
import net.hypixel.skyblock.item.MaterialFunction;
import net.hypixel.skyblock.item.ExperienceRewardStatistics;

public class Gravel implements ExperienceRewardStatistics, MaterialFunction
{
    @Override
    public double getRewardXP() {
        return 4.0;
    }
    
    @Override
    public Skill getRewardedSkill() {
        return MiningSkill.INSTANCE;
    }
    
    @Override
    public String getDisplayName() {
        return "Sand";
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
