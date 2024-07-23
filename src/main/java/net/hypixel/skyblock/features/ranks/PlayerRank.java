package net.hypixel.skyblock.features.ranks;

public enum PlayerRank
{
    DEFAULT("&7", 1), 
    VIP("&a[VIP]", 2), 
    VIPPLUS("&a[VIP&6+&a]", 3), 
    MVP("&b[MVP]", 4), 
    MVPPLUS("&b[MVP&c+&b]", 5), 
    MVPPLUSPLUS("&6[MVP&c++&6]", 6), 
    YOUTUBE("&c[&fYOUTUBE&c]", 7), 
    BT("&d[BT]", 8), 
    SPECIAL("&e[SPECIAL]", 9), 
    JRHELPER("&9[JR HELPER]", 10), 
    HELPER("&9[HELPER]", 11), 
    MOD("&2[MOD]", 12), 
    GAMEMASTER("&2[GM]", 13), 
    BUILD("&3[BUILD TEAM]", 14), 
    ADMIN("&c[ADMIN]", 15), 
    MANAGER("&c[MANAGER]", 16), 
    WATCHDOG("&c[WATCHDOG]", 17), 
    JERRY("&d[JERRY++]", 18), 
    OWNER("&c[OWNER]", 19);
    
    private final String prefix;
    private final int level;
    
    private PlayerRank(final String prefix, final int level) {
        this.prefix = prefix;
        this.level = level;
    }
    
    public static PlayerRank getRankOrDefault(final int level) {
        for (final PlayerRank rank : values()) {
            if (rank.level == level) {
                return rank;
            }
        }
        return PlayerRank.DEFAULT;
    }
    
    public String getPrefix() {
        return this.prefix;
    }
    
    public int getLevel() {
        return this.level;
    }
    
    public boolean isBelowOrEqual(final PlayerRank rank) {
        return this.level <= rank.level;
    }
    
    public boolean isAboveOrEqual(final PlayerRank rank) {
        return this.level >= rank.level;
    }
    
    public boolean hasRank(final PlayerRank requiredRank) {
        return this.level >= requiredRank.level;
    }
    
    public boolean isStaff() {
        return this.level >= PlayerRank.HELPER.level;
    }
    
    public boolean isDefaultPermission() {
        return this == PlayerRank.DEFAULT;
    }
    
    public String getFormattedRank() {
        return this.prefix;
    }
}
