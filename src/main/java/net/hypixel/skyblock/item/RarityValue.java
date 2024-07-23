package net.hypixel.skyblock.item;

public class RarityValue<T>
{
    private final T common;
    private final T uncommon;
    private final T rare;
    private final T epic;
    private final T legendary;
    private final T rest;
    
    public RarityValue(final T common, final T uncommon, final T rare, final T epic, final T legendary, final T rest) {
        this.common = common;
        this.uncommon = uncommon;
        this.rare = rare;
        this.epic = epic;
        this.legendary = legendary;
        this.rest = rest;
    }
    
    public T getForRarity(final Rarity rarity) {
        switch (rarity) {
            case COMMON: {
                return this.common;
            }
            case UNCOMMON: {
                return this.uncommon;
            }
            case RARE: {
                return this.rare;
            }
            case EPIC: {
                return this.epic;
            }
            case LEGENDARY: {
                return this.legendary;
            }
            default: {
                return this.rest;
            }
        }
    }
    
    public static RarityValue<Integer> zeroInteger() {
        return new RarityValue<Integer>(0, 0, 0, 0, 0, 0);
    }
    
    public static RarityValue<Double> zeroDouble() {
        return new RarityValue<Double>(0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
    }
    
    public static RarityValue<Integer> singleInteger(final Integer i) {
        return new RarityValue<Integer>(i, i, i, i, i, i);
    }
    
    public static RarityValue<Double> singleDouble(final Double d) {
        return new RarityValue<Double>(d, d, d, d, d, d);
    }
    
    public T getCommon() {
        return this.common;
    }
    
    public T getUncommon() {
        return this.uncommon;
    }
    
    public T getRare() {
        return this.rare;
    }
    
    public T getEpic() {
        return this.epic;
    }
    
    public T getLegendary() {
        return this.legendary;
    }
    
    public T getRest() {
        return this.rest;
    }
}
