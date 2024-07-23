package net.hypixel.skyblock.features.requirement;

import net.hypixel.skyblock.user.User;
import net.hypixel.skyblock.features.requirement.enums.SkillType;

public class SkillRequirement implements AbstractRequirement
{
    private final SkillType skillType;
    private final int level;
    
    public SkillRequirement(final SkillType skillType, final int level) {
        this.skillType = skillType;
        this.level = level;
    }
    
    @Override
    public boolean hasRequirement(final User user) {
        return user.getSkillLevel(this.skillType) >= this.level;
    }
    
    @Override
    public String getMessage() {
        return "You need " + this.skillType.getSkill().getName() + " level " + this.level + " to perform this action";
    }
}
