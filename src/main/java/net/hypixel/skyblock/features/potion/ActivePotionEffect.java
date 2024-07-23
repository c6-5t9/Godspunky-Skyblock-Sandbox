package net.hypixel.skyblock.features.potion;

import net.hypixel.skyblock.util.SUtil;

public class ActivePotionEffect
{
    private final PotionEffect effect;
    private long remaining;
    
    public ActivePotionEffect(final PotionEffect effect, final long remaining) {
        this.effect = effect;
        this.remaining = remaining;
    }
    
    public String getRemainingDisplay() {
        return SUtil.getFormattedTime(this.remaining);
    }
    
    public PotionEffect getEffect() {
        return this.effect;
    }
    
    public long getRemaining() {
        return this.remaining;
    }
    
    public void setRemaining(final long remaining) {
        this.remaining = remaining;
    }
}
