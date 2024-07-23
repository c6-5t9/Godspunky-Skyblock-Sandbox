package net.hypixel.skyblock.user;

import org.bukkit.plugin.Plugin;
import net.hypixel.skyblock.SkyBlock;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import java.util.HashMap;
import org.bukkit.scheduler.BukkitTask;
import java.util.Map;
import net.hypixel.skyblock.item.armor.ArmorSet;
import java.util.UUID;

public class PlayerStatistics
{
    private final UUID uuid;
    private final DoublePlayerStatistic maxHealth;
    private final DoublePlayerStatistic defense;
    private final DoublePlayerStatistic strength;
    private final DoublePlayerStatistic speed;
    private final DoublePlayerStatistic critChance;
    private final DoublePlayerStatistic critDamage;
    private final DoublePlayerStatistic magicFind;
    private final DoublePlayerStatistic ferocity;
    private final DoublePlayerStatistic abilityDamage;
    private final DoublePlayerStatistic attackSpeed;
    private final DoublePlayerStatistic intelligence;
    private final DoublePlayerStatistic trueDefense;
    private double healthRegenerationPercentBonus;
    private double manaRegenerationPercentBonus;
    private ArmorSet armorSet;
    private final Map<Integer, BukkitTask> itemTicker;
    
    public PlayerStatistics(final UUID uuid, final DoublePlayerStatistic maxHealth, final DoublePlayerStatistic defense, final DoublePlayerStatistic strength, final DoublePlayerStatistic speed, final DoublePlayerStatistic critChance, final DoublePlayerStatistic critDamage, final DoublePlayerStatistic magicFind, final DoublePlayerStatistic intelligence, final DoublePlayerStatistic trueDefense, final DoublePlayerStatistic ferocity, final DoublePlayerStatistic abilityDamage, final DoublePlayerStatistic attackSpeed, final double healthRegenerationPercentBonus, final double manaRegenerationPercentBonus, final ArmorSet armorSet) {
        this.uuid = uuid;
        this.maxHealth = maxHealth;
        this.defense = defense;
        this.strength = strength;
        this.speed = speed;
        this.critChance = critChance;
        this.critDamage = critDamage;
        this.magicFind = magicFind;
        this.abilityDamage = abilityDamage;
        this.attackSpeed = attackSpeed;
        this.intelligence = intelligence;
        this.trueDefense = trueDefense;
        this.ferocity = ferocity;
        this.healthRegenerationPercentBonus = healthRegenerationPercentBonus;
        this.manaRegenerationPercentBonus = manaRegenerationPercentBonus;
        this.armorSet = armorSet;
        this.itemTicker = (Map<Integer, BukkitTask>)new HashMap();
    }
    
    public void tickItem(final int slot, final long interval, final Runnable runnable) {
        this.itemTicker.put((Object)slot, (Object)new BukkitRunnable() {
            public void run() {
                if (Bukkit.getPlayer(PlayerStatistics.this.uuid) == null) {
                    this.cancel();
                    return;
                }
                runnable.run();
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 0L, interval));
    }
    
    public void cancelTickingItem(final int slot) {
        if (this.itemTicker.containsKey((Object)slot)) {
            ((BukkitTask)this.itemTicker.get((Object)slot)).cancel();
        }
        this.itemTicker.remove((Object)slot);
    }
    
    public void zeroAll(final int slot) {
        this.maxHealth.zero(slot);
        this.defense.zero(slot);
        this.strength.zero(slot);
        this.intelligence.zero(slot);
        this.speed.zero(slot);
        this.critChance.zero(slot);
        this.critDamage.zero(slot);
        this.magicFind.zero(slot);
        this.trueDefense.zero(slot);
        this.ferocity.zero(slot);
        this.abilityDamage.zero(slot);
        this.attackSpeed.zero(slot);
        this.cancelTickingItem(slot);
    }
    
    @Override
    public String toString() {
        return (Object)this.maxHealth.addAll() + ", " + (Object)this.defense.addAll() + ", " + (Object)this.strength.addAll() + ", " + (Object)this.speed.addAll() + ", " + (Object)this.critChance.addAll() + ", " + (Object)this.critDamage.addAll() + ", " + (Object)this.magicFind.addAll() + ", " + (Object)this.intelligence.addAll() + ", " + (Object)this.ferocity.addAll() + ", " + (Object)this.abilityDamage.addAll() + ", " + (Object)this.attackSpeed.addAll();
    }
    
    public void boostManaRegeneration(final double percent, final long ticks) {
        this.manaRegenerationPercentBonus += percent;
        new BukkitRunnable() {
            public void run() {
                final PlayerStatistics this$0 = PlayerStatistics.this;
                this$0.manaRegenerationPercentBonus -= percent;
            }
        }.runTaskLater((Plugin)SkyBlock.getPlugin(), ticks);
    }
    
    public void boostHealthRegeneration(final double percent, final long ticks) {
        this.healthRegenerationPercentBonus += percent;
        new BukkitRunnable() {
            public void run() {
                final PlayerStatistics this$0 = PlayerStatistics.this;
                this$0.healthRegenerationPercentBonus -= percent;
            }
        }.runTaskLater((Plugin)SkyBlock.getPlugin(), ticks);
    }
    
    public static PlayerStatistics blank(final UUID uuid) {
        return new PlayerStatistics(uuid, new DoublePlayerStatistic(100.0), new DoublePlayerStatistic(), new DoublePlayerStatistic(), new DoublePlayerStatistic(1.0), new DoublePlayerStatistic(0.3), new DoublePlayerStatistic(0.5), new DoublePlayerStatistic(), new DoublePlayerStatistic(), new DoublePlayerStatistic(), new DoublePlayerStatistic(), new DoublePlayerStatistic(), new DoublePlayerStatistic(), 0.0, 0.0, null);
    }
    
    public UUID getUuid() {
        return this.uuid;
    }
    
    public DoublePlayerStatistic getMaxHealth() {
        return this.maxHealth;
    }
    
    public DoublePlayerStatistic getDefense() {
        return this.defense;
    }
    
    public DoublePlayerStatistic getStrength() {
        return this.strength;
    }
    
    public DoublePlayerStatistic getSpeed() {
        return this.speed;
    }
    
    public DoublePlayerStatistic getCritChance() {
        return this.critChance;
    }
    
    public DoublePlayerStatistic getCritDamage() {
        return this.critDamage;
    }
    
    public DoublePlayerStatistic getMagicFind() {
        return this.magicFind;
    }
    
    public DoublePlayerStatistic getFerocity() {
        return this.ferocity;
    }
    
    public DoublePlayerStatistic getAbilityDamage() {
        return this.abilityDamage;
    }
    
    public DoublePlayerStatistic getAttackSpeed() {
        return this.attackSpeed;
    }
    
    public DoublePlayerStatistic getIntelligence() {
        return this.intelligence;
    }
    
    public DoublePlayerStatistic getTrueDefense() {
        return this.trueDefense;
    }
    
    public double getHealthRegenerationPercentBonus() {
        return this.healthRegenerationPercentBonus;
    }
    
    public double getManaRegenerationPercentBonus() {
        return this.manaRegenerationPercentBonus;
    }
    
    public ArmorSet getArmorSet() {
        return this.armorSet;
    }
    
    public Map<Integer, BukkitTask> getItemTicker() {
        return this.itemTicker;
    }
    
    public void setHealthRegenerationPercentBonus(final double healthRegenerationPercentBonus) {
        this.healthRegenerationPercentBonus = healthRegenerationPercentBonus;
    }
    
    public void setManaRegenerationPercentBonus(final double manaRegenerationPercentBonus) {
        this.manaRegenerationPercentBonus = manaRegenerationPercentBonus;
    }
    
    public void setArmorSet(final ArmorSet armorSet) {
        this.armorSet = armorSet;
    }
}
