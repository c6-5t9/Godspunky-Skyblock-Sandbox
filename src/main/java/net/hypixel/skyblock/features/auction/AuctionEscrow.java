package net.hypixel.skyblock.features.auction;

import java.util.HashMap;
import java.util.Map;
import net.hypixel.skyblock.item.SItem;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

public class AuctionEscrow implements ConfigurationSerializable
{
    private SItem item;
    private long starter;
    private long duration;
    
    public AuctionEscrow(final SItem item, final long starter, final long duration) {
        this.item = item;
        this.starter = starter;
        this.duration = duration;
    }
    
    public AuctionEscrow() {
        this(null, 50L, 21600000L);
    }
    
    public Map<String, Object> serialize() {
        final Map<String, Object> map = (Map<String, Object>)new HashMap();
        map.put((Object)"item", (Object)this.item);
        map.put((Object)"starter", (Object)this.starter);
        map.put((Object)"duration", (Object)this.duration);
        return map;
    }
    
    public static AuctionEscrow deserialize(final Map<String, Object> map) {
        return new AuctionEscrow((SItem)map.get((Object)"item"), (map.get((Object)"starter") instanceof Long) ? ((long)map.get((Object)"starter")) : ((long)map.get((Object)"starter")), (map.get((Object)"duration") instanceof Long) ? ((long)map.get((Object)"duration")) : ((long)map.get((Object)"duration")));
    }
    
    public SItem getItem() {
        return this.item;
    }
    
    public void setItem(final SItem item) {
        this.item = item;
    }
    
    public long getStarter() {
        return this.starter;
    }
    
    public void setStarter(final long starter) {
        this.starter = starter;
    }
    
    public long getDuration() {
        return this.duration;
    }
    
    public void setDuration(final long duration) {
        this.duration = duration;
    }
    
    public long getCreationFee(final boolean bin) {
        return Math.round(this.starter * (bin ? 0.01 : 0.05));
    }
}
