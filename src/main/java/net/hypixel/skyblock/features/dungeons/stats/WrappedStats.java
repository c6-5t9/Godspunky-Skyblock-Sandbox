package net.hypixel.skyblock.features.dungeons.stats;

public class WrappedStats
{
    private final float health;
    private final float healthRegen;
    private final float strength;
    private final float critDamage;
    private final float intelligence;
    private final float speed;
    private final float defense;
    private final float[] defaultArray;
    
    public WrappedStats(final float[] z) {
        this.health = z[0];
        this.healthRegen = z[1];
        this.strength = z[2];
        this.critDamage = z[3];
        this.intelligence = z[4];
        this.speed = z[5];
        this.defense = z[6];
        this.defaultArray = z;
    }
    
    public float getHealth() {
        return this.health;
    }
    
    public float getHealthRegen() {
        return this.healthRegen;
    }
    
    public float getStrength() {
        return this.strength;
    }
    
    public float getCritDamage() {
        return this.critDamage;
    }
    
    public float getIntelligence() {
        return this.intelligence;
    }
    
    public float getSpeed() {
        return this.speed;
    }
    
    public float getDefense() {
        return this.defense;
    }
    
    public float[] getDefaultArray() {
        return this.defaultArray;
    }
}
