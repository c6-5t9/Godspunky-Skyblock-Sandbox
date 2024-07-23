package net.hypixel.skyblock.item.oddities;

import net.hypixel.skyblock.item.SItem;
import java.util.Iterator;
import org.bukkit.ChatColor;
import net.hypixel.skyblock.util.SUtil;
import net.hypixel.skyblock.features.potion.PotionEffect;
import net.hypixel.skyblock.features.potion.PotionEffectType;
import java.util.ArrayList;
import java.util.List;
import net.hypixel.skyblock.item.GenericItemType;
import net.hypixel.skyblock.item.Rarity;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.hypixel.skyblock.item.ItemData;
import net.hypixel.skyblock.item.MaterialFunction;
import net.hypixel.skyblock.item.MaterialStatistics;

public class WaterBottle implements MaterialStatistics, MaterialFunction, ItemData
{
    @Override
    public NBTTagCompound getData() {
        final NBTTagCompound compound = new NBTTagCompound();
        compound.setBoolean("splash", false);
        return compound;
    }
    
    @Override
    public String getDisplayName() {
        return "Water Bottle";
    }
    
    @Override
    public Rarity getRarity() {
        return Rarity.COMMON;
    }
    
    @Override
    public GenericItemType getType() {
        return GenericItemType.ITEM;
    }
    
    @Override
    public List<String> getDataLore(final String key, final Object value) {
        if (!key.equals((Object)"effects")) {
            return null;
        }
        final NBTTagCompound compound = (NBTTagCompound)value;
        final List<String> lore = (List<String>)new ArrayList();
        for (final String k : compound.c()) {
            lore.add((Object)" ");
            final NBTTagCompound effectData = compound.getCompound(k);
            final PotionEffectType type = PotionEffectType.getByNamespace(k);
            final int level = effectData.getInt("level");
            final long duration = effectData.getLong("duration");
            final PotionEffect effect = new PotionEffect(type, level, duration);
            lore.add((Object)(type.getName() + " " + SUtil.toRomanNumeral(effect.getLevel()) + (effect.getType().isInstant() ? "" : ((Object)ChatColor.WHITE + " (" + effect.getDurationDisplay() + ")"))));
            for (final String line : SUtil.splitByWordAndLength(effect.getDescription(), 30, "\\s")) {
                lore.add((Object)((Object)ChatColor.GRAY + line));
            }
        }
        return lore;
    }
    
    @Override
    public void onInstanceUpdate(final SItem instance) {
        int max = 0;
        for (final PotionEffect effect : instance.getPotionEffects()) {
            if (effect.getLevel() > max) {
                max = effect.getLevel();
            }
        }
        instance.setRarity(SUtil.findPotionRarity(max), false);
        if (instance.getPotionEffects().size() == 1) {
            instance.setDisplayName(ChatColor.stripColor(((PotionEffect)instance.getPotionEffects().get(0)).getType().getName() + " " + SUtil.toRomanNumeral(((PotionEffect)instance.getPotionEffects().get(0)).getLevel())) + (instance.isSplashPotion() ? " Splash" : "") + " Potion");
        }
        if (instance.getPotionEffects().size() > 1) {
            instance.setDisplayName((instance.isSplashPotion() ? "Splash " : "") + "Potion");
        }
        if (instance.getPotionEffects().size() == 0) {
            instance.setDisplayName("Water Bottle");
        }
    }
}
