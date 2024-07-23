package net.hypixel.skyblock.item.pet;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import net.hypixel.skyblock.item.GenericItemType;
import net.hypixel.skyblock.features.skill.CombatSkill;
import net.hypixel.skyblock.features.skill.Skill;
import net.hypixel.skyblock.item.Rarity;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Arrays;
import org.bukkit.ChatColor;
import java.math.RoundingMode;
import java.math.BigDecimal;
import net.hypixel.skyblock.item.RarityValue;
import java.util.List;
import net.hypixel.skyblock.item.SItem;

public class BabyYeti2 extends Pet
{
    @Override
    public List<PetAbility> getPetAbilities(final SItem instance) {
        final RarityValue<Double> enderianMul = new RarityValue<Double>(0.1, 0.2, 0.2, 0.3, 0.3, 0.3);
        final RarityValue<Double> savvyMul = new RarityValue<Double>(0.0, 0.0, 0.4, 0.5, 0.5, 0.5);
        final int level = Pet.getLevel(instance);
        final BigDecimal coldbreeze = new BigDecimal(level * 0.5).setScale(1, RoundingMode.HALF_EVEN);
        final BigDecimal yeti1 = new BigDecimal(level).setScale(1, RoundingMode.HALF_EVEN);
        final BigDecimal yeti2 = new BigDecimal(level).setScale(1, RoundingMode.HALF_EVEN);
        final BigDecimal iceshield = new BigDecimal(level).setScale(2, RoundingMode.HALF_EVEN);
        final List<PetAbility> abilities = (List<PetAbility>)new ArrayList((Collection)Collections.singletonList((Object)new PetAbility() {
            @Override
            public String getName() {
                return "Cold Breeze";
            }
            
            @Override
            public List<String> getDescription(final SItem instance) {
                return (List<String>)Arrays.asList((Object[])new String[] { "Gives +" + (Object)ChatColor.GREEN + coldbreeze.toPlainString() + (Object)ChatColor.RED + " \u2741 Strength" + (Object)ChatColor.GRAY + " and", (Object)ChatColor.BLUE + "\u2620 Crit Damage" + (Object)ChatColor.GRAY + " when near snow" });
            }
        }));
        if (instance.getRarity().isAtLeast(Rarity.EPIC)) {
            abilities.add((Object)new PetAbility() {
                @Override
                public String getName() {
                    return "Ice Shield";
                }
                
                @Override
                public List<String> getDescription(final SItem instance) {
                    return (List<String>)Arrays.asList((Object[])new String[] { "Gain " + (Object)ChatColor.GREEN + iceshield.toPlainString() + "%" + (Object)ChatColor.GRAY + " of your strength", (Object)ChatColor.GRAY + "as " + (Object)ChatColor.GREEN + "\u2748 Defense" });
                }
            });
        }
        if (instance.getRarity().isAtLeast(Rarity.LEGENDARY)) {
            abilities.add((Object)new PetAbility() {
                @Override
                public String getName() {
                    return "Yeti Fury" + (Object)ChatColor.RED + (Object)ChatColor.BOLD + " COMING SOON!";
                }
                
                @Override
                public List<String> getDescription(final SItem instance) {
                    return (List<String>)Arrays.asList((Object[])new String[] { "Buffs the Yeti sword  by " + (Object)ChatColor.GREEN + yeti1.toPlainString() + (Object)ChatColor.RED + " \u2741", (Object)ChatColor.RED + "Damage" + (Object)ChatColor.GRAY + " and" + (Object)ChatColor.AQUA + " \u270e", (Object)ChatColor.AQUA + "Intelligence" });
                }
            });
        }
        return abilities;
    }
    
    @Override
    public Skill getSkill() {
        return CombatSkill.INSTANCE;
    }
    
    @Override
    public String getURL() {
        return "ab126814fc3fa846dad934c349628a7a1de5b415021a03ef4211d62514d5";
    }
    
    @Override
    public String getDisplayName() {
        return "Baby Yeti";
    }
    
    @Override
    public GenericItemType getType() {
        return GenericItemType.PET;
    }
    
    @Override
    public double getPerStrength() {
        return 0.4;
    }
    
    @Override
    public double getPerIntelligence() {
        return 0.75;
    }
    
    @Override
    public Rarity getRarity() {
        return Rarity.EPIC;
    }
    
    @Override
    public void particleBelowA(final Player p, final Location l) {
        p.spigot().playEffect(l, Effect.COLOURED_DUST, 0, 1, 1.0f, 1.0f, 1.0f, 1.0f, 0, 64);
        p.spigot().playEffect(l, Effect.COLOURED_DUST, 0, 1, 0.1882353f, 0.5411765f, 0.67058825f, 1.0f, 0, 64);
    }
}
