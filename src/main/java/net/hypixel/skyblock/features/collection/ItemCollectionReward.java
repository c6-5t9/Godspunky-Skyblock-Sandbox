package net.hypixel.skyblock.features.collection;

import org.bukkit.entity.Player;

public abstract class ItemCollectionReward
{
    private final Type type;
    
    protected ItemCollectionReward(final Type type) {
        this.type = type;
    }
    
    public abstract void onAchieve(final Player p0);
    
    public abstract String toRewardString();
    
    protected enum Type
    {
        RECIPE, 
        UPGRADE, 
        EXPERIENCE;
    }
}
