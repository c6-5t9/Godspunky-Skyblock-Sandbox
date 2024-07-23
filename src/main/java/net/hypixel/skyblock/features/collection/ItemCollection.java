package net.hypixel.skyblock.features.collection;

import java.util.stream.Collectors;
import java.util.HashMap;
import java.util.Iterator;
import org.bukkit.ChatColor;
import net.hypixel.skyblock.util.SUtil;
import java.util.concurrent.atomic.AtomicInteger;
import net.hypixel.skyblock.user.User;
import org.bukkit.entity.Player;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.hypixel.skyblock.item.SMaterial;
import java.util.Map;

public class ItemCollection
{
    private static final Map<String, ItemCollection> COLLECTION_MAP;
    public static final ItemCollection WHEAT;
    public static final ItemCollection OAK_WOOD;
    public static final ItemCollection STRING;
    private final String name;
    private final String identifier;
    private final ItemCollectionCategory category;
    private final SMaterial material;
    private final short data;
    private final List<ItemCollectionRewards> rewards;
    private final boolean temporary;
    
    private ItemCollection(final String name, final ItemCollectionCategory category, final SMaterial material, final short data, final ItemCollectionRewards... rewards) {
        this.name = name;
        this.identifier = name.toLowerCase().replaceAll(" ", "_");
        this.category = category;
        this.material = material;
        this.data = data;
        this.rewards = (List<ItemCollectionRewards>)new ArrayList((Collection)Arrays.asList((Object[])rewards));
        this.temporary = false;
        ItemCollection.COLLECTION_MAP.put((Object)this.identifier, (Object)this);
    }
    
    private ItemCollection(final String name, final ItemCollectionCategory category, final SMaterial material, final short data, final int size) {
        this.name = name;
        this.identifier = name.toLowerCase().replaceAll(" ", "_");
        this.category = category;
        this.material = material;
        this.data = data;
        this.rewards = (List<ItemCollectionRewards>)new ArrayList();
        for (int i = 0; i < size; ++i) {
            this.rewards.add((Object)null);
        }
        this.temporary = true;
        ItemCollection.COLLECTION_MAP.put((Object)this.identifier, (Object)this);
    }
    
    public static String[] getProgress(final Player player, final ItemCollectionCategory category) {
        final User user = User.getUser(player.getUniqueId());
        final AtomicInteger found = new AtomicInteger();
        final AtomicInteger completed = new AtomicInteger();
        final Collection<ItemCollection> collections = (Collection<ItemCollection>)((category != null) ? getCategoryCollections(category) : getCollections());
        for (final ItemCollection collection : collections) {
            if (user.getCollection(collection) > 0) {
                found.incrementAndGet();
            }
            if (user.getCollection(collection) >= collection.getMaxAmount()) {
                completed.incrementAndGet();
            }
        }
        String title;
        String progress;
        if (collections.size() == found.get()) {
            title = SUtil.createProgressText("Collection Maxed Out", completed.get(), collections.size());
            progress = SUtil.createLineProgressBar(20, ChatColor.DARK_GREEN, completed.get(), collections.size());
        }
        else {
            title = SUtil.createProgressText("Collection Unlocked", found.get(), collections.size());
            progress = SUtil.createLineProgressBar(20, ChatColor.DARK_GREEN, found.get(), collections.size());
        }
        return new String[] { title, progress };
    }
    
    public int getMaxAmount() {
        if (this.rewards.size() == 0 || this.rewards.get(this.rewards.size() - 1) == null) {
            return 0;
        }
        return ((ItemCollectionRewards)this.rewards.get(this.rewards.size() - 1)).getRequirement();
    }
    
    public int getTierAmount() {
        return this.rewards.size();
    }
    
    public int getTier(final int amount) {
        int tier = 0;
        for (final ItemCollectionRewards reward : this.rewards) {
            if (reward == null) {
                continue;
            }
            if (reward.getRequirement() > amount) {
                break;
            }
            ++tier;
        }
        return tier;
    }
    
    public int getRequirementForTier(int tier) {
        if (--tier < 0 || tier > this.rewards.size() - 1) {
            return -1;
        }
        final ItemCollectionRewards reward = (ItemCollectionRewards)this.rewards.get(tier);
        if (reward == null) {
            return -1;
        }
        return reward.getRequirement();
    }
    
    public ItemCollectionRewards getRewardsFor(int tier) {
        if (--tier < 0 || tier > this.rewards.size()) {
            return null;
        }
        return (ItemCollectionRewards)this.rewards.get(tier);
    }
    
    public static ItemCollection getByIdentifier(final String identifier) {
        return (ItemCollection)ItemCollection.COLLECTION_MAP.get((Object)identifier.toLowerCase());
    }
    
    public static ItemCollection getByMaterial(final SMaterial material, final short data) {
        for (final ItemCollection collection : ItemCollection.COLLECTION_MAP.values()) {
            if (collection.material == material && collection.data == data) {
                return collection;
            }
        }
        return null;
    }
    
    public static Map<ItemCollection, Integer> getDefaultCollections() {
        final Map<ItemCollection, Integer> collections = (Map<ItemCollection, Integer>)new HashMap();
        for (final ItemCollection collection : ItemCollection.COLLECTION_MAP.values()) {
            collections.put((Object)collection, (Object)0);
        }
        return collections;
    }
    
    public static Collection<ItemCollection> getCollections() {
        return (Collection<ItemCollection>)ItemCollection.COLLECTION_MAP.values();
    }
    
    public static List<ItemCollection> getCategoryCollections(final ItemCollectionCategory category) {
        return (List<ItemCollection>)getCollections().stream().filter(collection -> collection.category == category).collect(Collectors.toList());
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getIdentifier() {
        return this.identifier;
    }
    
    public ItemCollectionCategory getCategory() {
        return this.category;
    }
    
    public SMaterial getMaterial() {
        return this.material;
    }
    
    public short getData() {
        return this.data;
    }
    
    public List<ItemCollectionRewards> getRewards() {
        return this.rewards;
    }
    
    public boolean isTemporary() {
        return this.temporary;
    }
    
    static {
        COLLECTION_MAP = (Map)new HashMap();
        WHEAT = new ItemCollection("Wheat", ItemCollectionCategory.FARMING, SMaterial.WHEAT, (short)0, new ItemCollectionRewards[] { new ItemCollectionRewards(50, new ItemCollectionReward[] { new ItemCollectionRecipeReward(SMaterial.ASPECT_OF_THE_END) }), new ItemCollectionRewards(100, new ItemCollectionReward[0]), new ItemCollectionRewards(250, new ItemCollectionReward[0]), new ItemCollectionRewards(500, new ItemCollectionReward[0]), new ItemCollectionRewards(1000, new ItemCollectionReward[0]), new ItemCollectionRewards(2500, new ItemCollectionReward[0]), new ItemCollectionRewards(10000, new ItemCollectionReward[0]), new ItemCollectionRewards(15000, new ItemCollectionReward[0]), new ItemCollectionRewards(25000, new ItemCollectionReward[0]), new ItemCollectionRewards(50000, new ItemCollectionReward[0]), new ItemCollectionRewards(100000, new ItemCollectionReward[0]) });
        OAK_WOOD = new ItemCollection("Oak Wood", ItemCollectionCategory.FORAGING, SMaterial.OAK_WOOD, (short)0, 9);
        STRING = new ItemCollection("String", ItemCollectionCategory.COMBAT, SMaterial.STRING, (short)0, new ItemCollectionRewards[] { new ItemCollectionRewards(50, new ItemCollectionReward[0]), new ItemCollectionRewards(100, new ItemCollectionReward[0]), new ItemCollectionRewards(250, new ItemCollectionReward[] { new ItemCollectionUpgradeReward("Quiver", ChatColor.GREEN) }), new ItemCollectionRewards(1000, new ItemCollectionReward[0]), new ItemCollectionRewards(2500, new ItemCollectionReward[0]), new ItemCollectionRewards(5000, new ItemCollectionReward[0]), new ItemCollectionRewards(10000, new ItemCollectionReward[0]), new ItemCollectionRewards(25000, new ItemCollectionReward[0]), new ItemCollectionRewards(50000, new ItemCollectionReward[0]) });
    }
}
