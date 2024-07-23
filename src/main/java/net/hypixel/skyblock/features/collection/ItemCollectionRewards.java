package net.hypixel.skyblock.features.collection;

import java.util.Collection;
import java.util.Arrays;
import java.util.ArrayList;

public class ItemCollectionRewards extends ArrayList<ItemCollectionReward>
{
    private final int requirement;
    
    public ItemCollectionRewards(final int requirement, final ItemCollectionReward... rewards) {
        super((Collection)Arrays.asList((Object[])rewards));
        this.requirement = requirement;
    }
    
    public int getRequirement() {
        return this.requirement;
    }
}
