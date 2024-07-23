package net.hypixel.skyblock.item.pet;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import net.hypixel.skyblock.item.GenericItemType;
import net.hypixel.skyblock.features.skill.CombatSkill;
import net.hypixel.skyblock.features.skill.Skill;
import com.google.common.util.concurrent.AtomicDouble;
import net.hypixel.skyblock.item.Rarity;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Collections;
import net.hypixel.skyblock.util.Groups;
import net.hypixel.skyblock.entity.SEntity;
import org.bukkit.entity.Entity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import java.util.Arrays;
import org.bukkit.ChatColor;
import java.math.RoundingMode;
import java.math.BigDecimal;
import net.hypixel.skyblock.item.RarityValue;
import java.util.List;
import net.hypixel.skyblock.item.SItem;

public class EndermanPet extends Pet
{
    @Override
    public List<PetAbility> getPetAbilities(final SItem instance) {
        final RarityValue<Double> enderianMul = new RarityValue<Double>(0.1, 0.2, 0.2, 0.3, 0.3, 0.3);
        final RarityValue<Double> savvyMul = new RarityValue<Double>(0.0, 0.0, 0.4, 0.5, 0.5, 0.5);
        final int level = Pet.getLevel(instance);
        final BigDecimal enderian = new BigDecimal(level * enderianMul.getForRarity(instance.getRarity())).setScale(1, RoundingMode.HALF_EVEN);
        final BigDecimal savvy = new BigDecimal(level * savvyMul.getForRarity(instance.getRarity())).setScale(1, RoundingMode.HALF_EVEN);
        final BigDecimal zealot = new BigDecimal(level * 0.25).setScale(2, RoundingMode.HALF_EVEN);
        final List<PetAbility> abilities = (List<PetAbility>)new ArrayList((Collection)Collections.singletonList((Object)new PetAbility() {
            @Override
            public String getName() {
                return "Enderian";
            }
            
            @Override
            public List<String> getDescription(final SItem instance) {
                return (List<String>)Arrays.asList((Object[])new String[] { "Take " + (Object)ChatColor.GREEN + enderian.toPlainString() + "%" + (Object)ChatColor.GRAY + " less damage", "from end monsters" });
            }
            
            @Override
            public void onHurt(final EntityDamageByEntityEvent e, final Entity damager) {
                final SEntity entity = SEntity.findSEntity(damager);
                if (entity == null) {
                    return;
                }
                if (Groups.END_MOBS.contains((Object)entity.getSpecType())) {
                    e.setDamage(e.getDamage() - e.getDamage() * enderian.doubleValue() * 0.01);
                }
            }
        }));
        if (instance.getRarity().isAtLeast(Rarity.RARE)) {
            abilities.add((Object)new PetAbility() {
                @Override
                public String getName() {
                    return "Teleport Savvy";
                }
                
                @Override
                public List<String> getDescription(final SItem instance) {
                    return (List<String>)Arrays.asList((Object[])new String[] { "Buffs the Aspect of the End", "ability granting " + (Object)ChatColor.GREEN + savvy.toPlainString() + (Object)ChatColor.GRAY + " weapon", "damage for 5s on use" });
                }
            });
        }
        if (instance.getRarity().isAtLeast(Rarity.LEGENDARY)) {
            abilities.add((Object)new PetAbility() {
                @Override
                public String getName() {
                    return "Zealot Madness";
                }
                
                @Override
                public List<String> getDescription(final SItem instance) {
                    return (List<String>)Arrays.asList((Object[])new String[] { "Increases your odds to find a", "special Zealot by " + (Object)ChatColor.GREEN + zealot.toPlainString() + "%" });
                }
                
                @Override
                public void onZealotAttempt(final AtomicDouble chance) {
                    chance.set(chance.get() - chance.get() * zealot.doubleValue());
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
        return "6eab75eaa5c9f2c43a0d23cfdce35f4df632e9815001850377385f7b2f039ce1";
    }
    
    @Override
    public String getDisplayName() {
        return "Enderman";
    }
    
    @Override
    public GenericItemType getType() {
        return GenericItemType.PET;
    }
    
    @Override
    public double getPerCritDamage() {
        return 0.0075;
    }
    
    @Override
    public void particleBelowA(final Player p, final Location l) {
        p.spigot().playEffect(l, Effect.WITCH_MAGIC, 0, 1, 1.0f, 1.0f, 1.0f, 0.0f, 0, 64);
        p.spigot().playEffect(l, Effect.WITCH_MAGIC, 0, 1, 1.0f, 1.0f, 1.0f, 0.0f, 0, 64);
        p.spigot().playEffect(l, Effect.WITCH_MAGIC, 0, 1, 1.0f, 1.0f, 1.0f, 0.0f, 0, 64);
    }
}
