package net.hypixel.skyblock.item.pet;

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

public class SheepPet extends Pet
{
    @Override
    public List<PetAbility> getPetAbilities(final SItem instance) {
        final RarityValue<Double> enderianMul = new RarityValue<Double>(0.1, 0.2, 0.2, 0.3, 0.3, 0.3);
        final RarityValue<Double> savvyMul = new RarityValue<Double>(0.0, 0.0, 0.4, 0.5, 0.5, 0.5);
        final int level = Pet.getLevel(instance);
        final BigDecimal endstrike = new BigDecimal(level * 0.25).setScale(1, RoundingMode.HALF_EVEN);
        final BigDecimal aotd1 = new BigDecimal(level * 0.5).setScale(1, RoundingMode.HALF_EVEN);
        final BigDecimal aotd2 = new BigDecimal(level * 0.3).setScale(1, RoundingMode.HALF_EVEN);
        final BigDecimal buffstat = new BigDecimal(level * 0.1).setScale(2, RoundingMode.HALF_EVEN);
        final List<PetAbility> abilities = (List<PetAbility>)new ArrayList((Collection)Collections.singletonList((Object)new PetAbility() {
            @Override
            public String getName() {
                return "End Strike";
            }
            
            @Override
            public List<String> getDescription(final SItem instance) {
                return (List<String>)Arrays.asList((Object[])new String[] { "Deal +" + (Object)ChatColor.GREEN + endstrike.toPlainString() + "%" + (Object)ChatColor.GRAY + " more damage to", "end mobs." });
            }
        }));
        if (instance.getRarity().isAtLeast(Rarity.EPIC)) {
            abilities.add((Object)new PetAbility() {
                @Override
                public String getName() {
                    return "One with the Dragons";
                }
                
                @Override
                public List<String> getDescription(final SItem instance) {
                    return (List<String>)Arrays.asList((Object[])new String[] { "Buffs the Aspect of the", "Dragons sword by " + (Object)ChatColor.GREEN + aotd1.toPlainString() + (Object)ChatColor.RED + " \u2741", (Object)ChatColor.RED + "Damage" + (Object)ChatColor.GRAY + " and " + (Object)ChatColor.GREEN + aotd2.toPlainString() + (Object)ChatColor.RED + " \u2741 Strength" });
                }
            });
        }
        if (instance.getRarity().isAtLeast(Rarity.LEGENDARY)) {
            abilities.add((Object)new PetAbility() {
                @Override
                public String getName() {
                    return "Superior";
                }
                
                @Override
                public List<String> getDescription(final SItem instance) {
                    return (List<String>)Collections.singletonList((Object)("Increases most stats by " + (Object)ChatColor.GREEN + buffstat.toPlainString() + "%"));
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
        return "aec3ff563290b13ff3bcc36898af7eaa988b6cc18dc254147f58374afe9b21b9";
    }
    
    @Override
    public String getDisplayName() {
        return "Sheep";
    }
    
    @Override
    public GenericItemType getType() {
        return GenericItemType.PET;
    }
    
    @Override
    public double getPerIntelligence() {
        return 3.0;
    }
    
    @Override
    public Rarity getRarity() {
        return Rarity.LEGENDARY;
    }
    
    @Override
    public void particleBelowA(final Player p, final Location l) {
    }
}
