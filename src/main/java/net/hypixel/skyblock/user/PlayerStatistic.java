package net.hypixel.skyblock.user;

public interface PlayerStatistic<T>
{
    public static final int HELMET = 0;
    public static final int CHESTPLATE = 1;
    public static final int LEGGINGS = 2;
    public static final int BOOTS = 3;
    public static final int HAND = 4;
    public static final int SET = 5;
    public static final int BOOST = 6;
    public static final int PET = 7;
    public static final int MINER_BUFF = 8;
    public static final int OBSIDIAN_CHESTPLATE = 9;
    public static final int FARMING = 10;
    public static final int MINING = 11;
    public static final int COMBAT = 12;
    public static final int ENCHANTING = 14;
    public static final int FORAGING = 13;
    public static final int ADD_FOR_INVENTORY = 15;
    public static final int ADD_FOR_POTION_EFFECTS = 52;
    public static final int CRYSTALBUFF = 150;
    public static final int COOKIE_BUFF = 151;
    public static final int TEMPORARY_STATS = 152;
    public static final int FATAL_SLOT = 153;
    
    T addAll();
    
    void add(final int p0, final T p1);
    
    void sub(final int p0, final T p1);
    
    void zero(final int p0);
    
    boolean contains(final int p0);
    
    T safeGet(final int p0);
    
    void set(final int p0, final T p1);
    
    int next();
    
    T getFor(final int p0);
}
