package net.hypixel.skyblock.features.auction;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

public class AuctionBid implements ConfigurationSerializable
{
    private UUID bidder;
    private long amount;
    private long timestamp;
    
    public AuctionBid(final UUID bidder, final long amount, final long timestamp) {
        this.bidder = bidder;
        this.amount = amount;
        this.timestamp = timestamp;
    }
    
    public Map<String, Object> serialize() {
        final Map<String, Object> map = (Map<String, Object>)new HashMap();
        map.put((Object)"bidder", (Object)this.bidder.toString());
        map.put((Object)"amount", (Object)this.amount);
        map.put((Object)"timestamp", (Object)this.timestamp);
        return map;
    }
    
    public static AuctionBid deserialize(final Map<String, Object> map) {
        return new AuctionBid(UUID.fromString((String)map.get((Object)"bidder")), (long)map.get((Object)"amount"), (long)map.get((Object)"timestamp"));
    }
    
    public UUID getBidder() {
        return this.bidder;
    }
    
    public void setBidder(final UUID bidder) {
        this.bidder = bidder;
    }
    
    public long getAmount() {
        return this.amount;
    }
    
    public void setAmount(final long amount) {
        this.amount = amount;
    }
    
    public long getTimestamp() {
        return this.timestamp;
    }
    
    public void setTimestamp(final long timestamp) {
        this.timestamp = timestamp;
    }
    
    public long timeSinceBid() {
        return System.currentTimeMillis() - this.timestamp;
    }
}
