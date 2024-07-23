package net.hypixel.skyblock.features.potion;

import net.minecraft.server.v1_8_R3.NBTTagCompound;
import java.util.List;
import net.hypixel.skyblock.util.SUtil;
import java.util.Arrays;

public class PotionEffect
{
    private final PotionEffectType type;
    private final int level;
    private final long duration;
    
    public PotionEffect(final PotionEffectType type, final int level, final long duration) {
        this.type = type;
        this.level = level;
        this.duration = duration;
    }
    
    public String getDescription() {
        if (this.type == PotionEffectType.STRENGTH) {
            return this.type.getDescription(SUtil.getOrDefault((java.util.List<Integer>)Arrays.asList((Object[])new Integer[] { 5, 12, 20, 30, 40, 50, 60, 75 }), this.level - 1, this.level * 10));
        }
        if (this.type == PotionEffectType.FEROCITY) {
            return this.type.getDescription(SUtil.getOrDefault((java.util.List<Integer>)Arrays.asList((Object[])new Integer[] { 5, 12, 20, 30, 40, 50, 60, 75 }), this.level - 1, this.level * 10));
        }
        if (this.type == PotionEffectType.RABBIT) {
            return this.type.getDescription(SUtil.toRomanNumeral(this.level / 2), this.level * 10);
        }
        if (this.type == PotionEffectType.HEALING) {
            return this.type.getDescription(SUtil.getOrDefault((java.util.List<Integer>)Arrays.asList((Object[])new Integer[] { 20, 50, 100, 150, 200, 300, 400, 500 }), this.level - 1, (this.level - 3) * 100));
        }
        if (this.type == PotionEffectType.SPEED) {
            return this.type.getDescription(this.level * 5);
        }
        if (this.type == PotionEffectType.ARCHERY) {
            return this.type.getDescription(SUtil.getOrDefault((java.util.List<Integer>)Arrays.asList((Object[])new Number[] { (Number)12.5, (Number)25, (Number)50, (Number)75 }), this.level - 1, this.level * 25 - 25));
        }
        if (this.type == PotionEffectType.MANA) {
            return this.type.getDescription(SUtil.getOrDefault((java.util.List<Integer>)Arrays.asList((Object[])new Integer[] { 25, 50, 75, 100, 150, 200, 300, 400 }), this.level - 1, (this.level - 4) * 100));
        }
        if (this.type == PotionEffectType.ADRENALINE) {
            return this.type.getDescription(SUtil.getOrDefault((java.util.List<Integer>)Arrays.asList((Object[])new Integer[] { 20, 40, 60, 80, 100, 150, 200, 300 }), this.level - 1, (this.level - 5) * 100), this.level * 5);
        }
        if (this.type == PotionEffectType.CRITICAL) {
            return this.type.getDescription(5 + this.level * 5, this.level * 10);
        }
        if (this.type == PotionEffectType.ABSORPTION) {
            return this.type.getDescription(SUtil.getOrDefault((java.util.List<Integer>)Arrays.asList((Object[])new Integer[] { 20, 40, 60, 80, 100, 150, 200, 300 }), this.level - 1, (this.level - 5) * 100));
        }
        if (this.type == PotionEffectType.RESISTANCE) {
            return this.type.getDescription(SUtil.getOrDefault((java.util.List<Integer>)Arrays.asList((Object[])new Integer[] { 5, 10, 15, 20, 30, 40, 50, 60 }), this.level - 1, this.level * 10 - 20));
        }
        if (this.type == PotionEffectType.TRUE_RESISTANCE) {
            return this.type.getDescription(this.level * 5);
        }
        if (this.type == PotionEffectType.MAGIC_FIND) {
            return this.type.getDescription(this.level * 2);
        }
        if (this.type == PotionEffectType.STAMINA) {
            return this.type.getDescription(SUtil.getOrDefault((java.util.List<Integer>)Arrays.asList((Object[])new Integer[] { 150, 215, 315, 515 }), this.level - 1, (this.level + 1) * 100 + 15), 50 * this.level);
        }
        if (this.type == PotionEffectType.SPIRIT) {
            return this.type.getDescription(this.level * 10, this.level * 10);
        }
        return this.type.getDescription(new Object[0]);
    }
    
    public String getDurationDisplay() {
        return SUtil.getFormattedTime(this.duration);
    }
    
    public String getDisplayName() {
        return this.type.getName() + " " + SUtil.toRomanNumeral(this.level);
    }
    
    public NBTTagCompound toCompound() {
        final NBTTagCompound compound = new NBTTagCompound();
        compound.setInt("level", this.level);
        compound.setLong("duration", this.duration);
        return compound;
    }
    
    public static PotionEffect ofCompound(final String namespace, final NBTTagCompound compound) {
        final PotionEffectType type = PotionEffectType.getByNamespace(namespace);
        final int level = compound.getInt("level");
        final long duration = compound.getLong("duration");
        return new PotionEffect(type, level, duration);
    }
    
    public PotionEffectType getType() {
        return this.type;
    }
    
    public int getLevel() {
        return this.level;
    }
    
    public long getDuration() {
        return this.duration;
    }
}
