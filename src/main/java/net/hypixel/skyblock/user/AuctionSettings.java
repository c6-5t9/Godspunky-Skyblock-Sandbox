package net.hypixel.skyblock.user;

import org.bson.Document;
import java.util.HashMap;
import java.util.Map;
import net.hypixel.skyblock.item.Rarity;
import net.hypixel.skyblock.item.ItemCategory;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

public class AuctionSettings implements Cloneable, ConfigurationSerializable
{
    private ItemCategory category;
    private String query;
    private Sort sort;
    private Rarity tier;
    private Type type;
    
    public AuctionSettings(final ItemCategory category, final String query, final Sort sort, final Rarity tier, final Type type) {
        this.category = category;
        this.query = query;
        this.sort = sort;
        this.tier = tier;
        this.type = type;
    }
    
    public AuctionSettings() {
        this(ItemCategory.WEAPONS, null, Sort.HIGHEST_BID, null, Type.SHOW_ALL);
    }
    
    public Map<String, Object> serialize() {
        final Map<String, Object> map = (Map<String, Object>)new HashMap();
        map.put((Object)"category", (Object)this.category.name());
        map.put((Object)"query", (Object)this.query);
        map.put((Object)"sort", (Object)this.sort.name());
        if (this.tier != null) {
            map.put((Object)"tier", (Object)this.tier.name());
        }
        map.put((Object)"type", (Object)this.type.name());
        return map;
    }
    
    public static Document serializeAuctionSettings(final AuctionSettings settings) {
        final Document objectDocument = new Document("category", (Object)settings.getCategory().name());
        if (settings.getQuery() == null) {
            objectDocument.append("query", (Object)settings.getQuery());
        }
        if (settings.getSort() != null) {
            objectDocument.append("sort", (Object)settings.getSort().name());
        }
        if (settings.getTier() != null) {
            objectDocument.append("tier", (Object)settings.getTier().name());
        }
        if (settings.getType() != null) {
            objectDocument.append("type", (Object)settings.getType().name());
        }
        return objectDocument;
    }
    
    @Override
    public String toString() {
        return "AuctionSettings{category=" + this.category.name() + ", query=" + this.query + ", sort=" + this.sort.name() + ", tier=" + this.tier.name() + ", type=" + this.type.name() + "}";
    }
    
    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof AuctionSettings)) {
            return false;
        }
        final AuctionSettings settings = (AuctionSettings)o;
        return this.category == settings.category && this.query.equals((Object)settings.query) && this.sort == settings.sort && this.tier == settings.tier && this.type == settings.type;
    }
    
    public AuctionSettings clone() {
        return new AuctionSettings(this.category, this.query, this.sort, this.tier, this.type);
    }
    
    public ItemCategory getCategory() {
        return this.category;
    }
    
    public void setCategory(final ItemCategory category) {
        this.category = category;
    }
    
    public String getQuery() {
        return this.query;
    }
    
    public void setQuery(final String query) {
        this.query = query;
    }
    
    public Sort getSort() {
        return this.sort;
    }
    
    public void setSort(final Sort sort) {
        this.sort = sort;
    }
    
    public Rarity getTier() {
        return this.tier;
    }
    
    public void setTier(final Rarity tier) {
        this.tier = tier;
    }
    
    public Type getType() {
        return this.type;
    }
    
    public void setType(final Type type) {
        this.type = type;
    }
    
    public static AuctionSettings deserialize(final Map<String, Object> map) {
        return new AuctionSettings(ItemCategory.valueOf((String)map.get((Object)"category")), (String)map.getOrDefault((Object)"query", (Object)null), Sort.valueOf((String)map.get((Object)"sort")), map.containsKey((Object)"tier") ? Rarity.getRarity((String)map.get((Object)"tier")) : null, Type.valueOf((String)map.get((Object)"type")));
    }
    
    public static AuctionSettings deserializeAuctionSettings(final Document document) {
        final ItemCategory category = ItemCategory.valueOf(document.getString((Object)"category"));
        final String query = document.getString((Object)"query");
        final Sort sort = Sort.valueOf(document.getString((Object)"sort"));
        final Rarity tier = document.containsKey((Object)"tier") ? Rarity.getRarity(document.getString((Object)"tier")) : null;
        final Type type = Type.valueOf(document.getString((Object)"type"));
        return new AuctionSettings(category, query, sort, tier, type);
    }
    
    public enum Sort
    {
        HIGHEST_BID("Highest Bid"), 
        LOWEST_BID("Lowest Bid"), 
        ENDING_SOON("Ending soon"), 
        MOST_BIDS("Most Bids");
        
        private final String display;
        
        private Sort(final String display) {
            this.display = display;
        }
        
        public String getDisplay() {
            return this.display;
        }
        
        public Sort previous() {
            final int prev = this.ordinal() - 1;
            if (prev < 0) {
                return values()[values().length - 1];
            }
            return values()[prev];
        }
        
        public Sort next() {
            final int nex = this.ordinal() + 1;
            if (nex > values().length - 1) {
                return values()[0];
            }
            return values()[nex];
        }
    }
    
    public enum Type
    {
        SHOW_ALL("Show All"), 
        BIN_ONLY("BIN Only"), 
        AUCTIONS_ONLY("Auctions Only");
        
        private final String display;
        
        private Type(final String display) {
            this.display = display;
        }
        
        public String getDisplay() {
            return this.display;
        }
        
        public Type previous() {
            final int prev = this.ordinal() - 1;
            if (prev < 0) {
                return values()[values().length - 1];
            }
            return values()[prev];
        }
        
        public Type next() {
            final int nex = this.ordinal() + 1;
            if (nex > values().length - 1) {
                return values()[0];
            }
            return values()[nex];
        }
    }
}
