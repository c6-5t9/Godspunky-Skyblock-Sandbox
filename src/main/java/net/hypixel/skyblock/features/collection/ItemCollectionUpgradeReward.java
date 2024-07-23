package net.hypixel.skyblock.features.collection;

import org.bukkit.entity.Player;
import org.bukkit.ChatColor;

public class ItemCollectionUpgradeReward extends ItemCollectionReward
{
    private final String name;
    private final ChatColor color;
    
    public ItemCollectionUpgradeReward(final String name, final ChatColor color) {
        super(Type.UPGRADE);
        this.name = name;
        this.color = color;
    }
    
    @Override
    public String toRewardString() {
        return (Object)this.color + this.name + " Upgrade";
    }
    
    @Override
    public void onAchieve(final Player player) {
    }
}
