package net.hypixel.skyblock.util;

import java.util.HashMap;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.entity.Player;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.Entity;
import java.util.Map;

public class EntityManager
{
    public static final Map<Entity, Integer> DEFENSE_PERCENTAGE;
    
    public static void noAI(final Entity bukkitEntity) {
        final net.minecraft.server.v1_8_R3.Entity nmsEntity = ((CraftEntity)bukkitEntity).getHandle();
        NBTTagCompound tag = nmsEntity.getNBTTag();
        if (tag == null) {
            tag = new NBTTagCompound();
        }
        nmsEntity.c(tag);
        tag.setInt("NoAI", 1);
        nmsEntity.f(tag);
    }
    
    public static void yesAI(final Entity bukkitEntity) {
        final net.minecraft.server.v1_8_R3.Entity nmsEntity = ((CraftEntity)bukkitEntity).getHandle();
        NBTTagCompound tag = nmsEntity.getNBTTag();
        if (tag == null) {
            tag = new NBTTagCompound();
        }
        nmsEntity.c(tag);
        tag.setInt("NoAI", 0);
        nmsEntity.f(tag);
    }
    
    public static void setNBTTag(final Entity bukkitEntity, final String t, final int obj) {
        final net.minecraft.server.v1_8_R3.Entity nmsEntity = ((CraftEntity)bukkitEntity).getHandle();
        NBTTagCompound tag = nmsEntity.getNBTTag();
        if (tag == null) {
            tag = new NBTTagCompound();
        }
        nmsEntity.c(tag);
        tag.setInt(t, obj);
        nmsEntity.f(tag);
    }
    
    public static void noHit(final Entity bukkitEntity) {
        final net.minecraft.server.v1_8_R3.Entity nmsEntity = ((CraftEntity)bukkitEntity).getHandle();
        NBTTagCompound tag = nmsEntity.getNBTTag();
        if (tag == null) {
            tag = new NBTTagCompound();
        }
        nmsEntity.c(tag);
        tag.setInt("Invulnerable", 1);
        nmsEntity.f(tag);
    }
    
    public static void shutTheFuckUp(final Entity bukkitEntity) {
        final net.minecraft.server.v1_8_R3.Entity nmsEntity = ((CraftEntity)bukkitEntity).getHandle();
        NBTTagCompound tag = nmsEntity.getNBTTag();
        if (tag == null) {
            tag = new NBTTagCompound();
        }
        nmsEntity.c(tag);
        tag.setInt("Silent", 1);
        nmsEntity.f(tag);
    }
    
    public static void Woosh(final Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 100000, 1));
    }
    
    public static void NoWoosh(final Player player) {
        player.removePotionEffect(PotionEffectType.INVISIBILITY);
    }
    
    public static void Woosh(final LivingEntity player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 100000, 1));
    }
    
    public static void NoWoosh(final LivingEntity player) {
        player.removePotionEffect(PotionEffectType.INVISIBILITY);
    }
    
    static {
        DEFENSE_PERCENTAGE = (Map)new HashMap();
    }
}
