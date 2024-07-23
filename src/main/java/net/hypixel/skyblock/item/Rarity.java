package net.hypixel.skyblock.item;

import org.bukkit.ChatColor;

public enum Rarity
{
    COMMON(ChatColor.WHITE), 
    UNCOMMON(ChatColor.GREEN), 
    RARE(ChatColor.BLUE), 
    EPIC(ChatColor.DARK_PURPLE), 
    LEGENDARY(ChatColor.GOLD), 
    MYTHIC(ChatColor.LIGHT_PURPLE), 
    SUPREME(ChatColor.AQUA), 
    SPECIAL(ChatColor.RED), 
    VERY_SPECIAL(ChatColor.RED), 
    EXCLUSIVE(ChatColor.GRAY);
    
    private final ChatColor color;
    
    private Rarity(final ChatColor color) {
        this.color = color;
    }
    
    public Rarity upgrade() {
        return values()[Math.min(this.ordinal() + 1, values().length - 1)];
    }
    
    public Rarity downgrade() {
        if (this.ordinal() - 1 < 0) {
            return this;
        }
        return values()[this.ordinal() - 1];
    }
    
    public boolean isAtLeast(final Rarity rarity) {
        return this.ordinal() >= rarity.ordinal();
    }
    
    public String getDisplay() {
        return "" + (Object)this.color + (Object)ChatColor.BOLD + this.name().replaceAll("_", " ");
    }
    
    public String getBoldedColor() {
        return "" + (Object)this.color + (Object)ChatColor.BOLD;
    }
    
    public static Rarity getRarity(final String string) {
        try {
            return valueOf(string.toUpperCase());
        }
        catch (final IllegalArgumentException ex) {
            return null;
        }
    }
    
    public ChatColor getColor() {
        return this.color;
    }
}
