package net.hypixel.skyblock.features.dungeons.stats;

import net.hypixel.skyblock.item.SItem;
import java.util.ArrayList;
import java.util.List;

public class ItemSerial
{
    private double damage;
    private double strength;
    private double critchance;
    private double critdamage;
    private double ferocity;
    private double intelligence;
    private double atkSpeed;
    private double speed;
    private double health;
    private double defense;
    private double magicFind;
    private final List<Double> intarr;
    
    public ItemSerial(final double damage, final double strength, final double critdamage, final double critchance, final double ferocity, final double intelligence, final double speed, final double atkSpeed, final double health, final double defense, final double magicFind) {
        this.damage = 0.0;
        this.strength = 0.0;
        this.critchance = 0.0;
        this.critdamage = 0.0;
        this.ferocity = 0.0;
        this.intelligence = 0.0;
        this.atkSpeed = 0.0;
        this.speed = 0.0;
        this.health = 0.0;
        this.defense = 0.0;
        this.magicFind = 0.0;
        this.intarr = (List<Double>)new ArrayList();
        this.damage = damage;
        this.strength = strength;
        this.critdamage = critdamage;
        this.critchance = critchance;
        this.ferocity = ferocity;
        this.intelligence = intelligence;
        this.speed = speed;
        this.atkSpeed = atkSpeed;
        this.health = health;
        this.defense = defense;
        this.magicFind = magicFind;
    }
    
    public void saveTo(final SItem sitem) {
        this.intarr.add((Object)this.damage);
        this.intarr.add((Object)this.strength);
        this.intarr.add((Object)this.critdamage);
        this.intarr.add((Object)this.critchance);
        this.intarr.add((Object)this.ferocity);
        this.intarr.add((Object)this.intelligence);
        this.intarr.add((Object)this.speed);
        this.intarr.add((Object)this.atkSpeed);
        this.intarr.add((Object)this.health);
        this.intarr.add((Object)this.defense);
        this.intarr.add((Object)this.magicFind);
        final StringBuilder sb = new StringBuilder();
        sb.append("ItemBoost[");
        for (int j = 0; j < this.intarr.size(); ++j) {
            if (j == this.intarr.size() - 1) {
                sb.append(this.intarr.get(j));
            }
            else {
                sb.append(this.intarr.get(j) + ",");
            }
        }
        sb.append("]");
        sitem.setDataString("boost", sb.toString());
        sitem.setDataBoolean("dungeons_item", true);
    }
    
    public static ItemSerial getItemBoostStatistics(final SItem sitem) {
        String s = sitem.getDataString("boost");
        if (!s.contains((CharSequence)"ItemBoost")) {
            return createBlank();
        }
        s = s.replace((CharSequence)"ItemBoost[", (CharSequence)"");
        s = s.replace((CharSequence)"]", (CharSequence)"");
        final String[] sta = s.split(",");
        final float[] f = new float[11];
        for (int i = 0; i < sta.length; ++i) {
            try {
                f[i] = Float.parseFloat(sta[i]);
            }
            catch (final NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return new ItemSerial(f[0], f[1], f[2], f[3], f[4], f[5], f[6], f[7], f[8], f[9], f[10]);
    }
    
    public static ItemSerial createBlank() {
        final float[] f = new float[11];
        return new ItemSerial(f[0], f[1], f[2], f[3], f[4], f[5], f[6], f[7], f[8], f[9], f[10]);
    }
    
    public void setDamage(final double damage) {
        this.damage = damage;
    }
    
    public double getDamage() {
        return this.damage;
    }
    
    public void setStrength(final double strength) {
        this.strength = strength;
    }
    
    public double getStrength() {
        return this.strength;
    }
    
    public void setCritchance(final double critchance) {
        this.critchance = critchance;
    }
    
    public double getCritchance() {
        return this.critchance;
    }
    
    public void setCritdamage(final double critdamage) {
        this.critdamage = critdamage;
    }
    
    public double getCritdamage() {
        return this.critdamage;
    }
    
    public void setFerocity(final double ferocity) {
        this.ferocity = ferocity;
    }
    
    public double getFerocity() {
        return this.ferocity;
    }
    
    public void setIntelligence(final double intelligence) {
        this.intelligence = intelligence;
    }
    
    public double getIntelligence() {
        return this.intelligence;
    }
    
    public void setAtkSpeed(final double atkSpeed) {
        this.atkSpeed = atkSpeed;
    }
    
    public double getAtkSpeed() {
        return this.atkSpeed;
    }
    
    public void setSpeed(final double speed) {
        this.speed = speed;
    }
    
    public double getSpeed() {
        return this.speed;
    }
    
    public void setHealth(final double health) {
        this.health = health;
    }
    
    public double getHealth() {
        return this.health;
    }
    
    public void setDefense(final double defense) {
        this.defense = defense;
    }
    
    public double getDefense() {
        return this.defense;
    }
    
    public void setMagicFind(final double magicFind) {
        this.magicFind = magicFind;
    }
    
    public double getMagicFind() {
        return this.magicFind;
    }
}
