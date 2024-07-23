package net.hypixel.skyblock.util;

import java.util.HashMap;
import org.bukkit.entity.Entity;
import net.hypixel.skyblock.listener.PlayerListener;
import net.hypixel.skyblock.entity.nms.VoidgloomSeraph;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.EnderDragon;
import net.hypixel.skyblock.user.PlayerUtils;
import net.hypixel.skyblock.user.PlayerStatistics;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import java.util.UUID;
import java.util.Map;

public class FerocityCalculation
{
    public static final Map<UUID, Integer> ONE_TYPE_FEROCITY_BONUS_ENDERMAN;
    
    @Deprecated
    public static void test(final int i) {
    }
    
    public static Integer ferocityStrikeNext(final int ferocity) {
        if (ferocity == 0) {
            return 0;
        }
        final int feroDiv = Math.round((float)(ferocity / 100)) + 1;
        return feroDiv;
    }
    
    public static Integer ferocityStrikeCurrent(final int ferocity) {
        if (ferocity < 100) {
            return 0;
        }
        final int feroDiv = Math.round((float)(ferocity / 100));
        return feroDiv;
    }
    
    public static Double ferocityPercentCurrent(final int ferocity) {
        double returnamount = 0.0;
        final int feroMinus = Math.round((float)(ferocity / 100)) * 100;
        final int feroLater = ferocity - feroMinus;
        if (feroMinus >= 100) {
            returnamount = 100.0;
        }
        else {
            returnamount = ferocity;
        }
        return returnamount;
    }
    
    public static Double ferocityPercentNext(final int ferocity) {
        final int feroMinus = Math.round((float)(ferocity / 100)) * 100;
        int feroLater = ferocity - feroMinus;
        if (feroLater > 100) {
            feroLater = 0;
        }
        return (double)feroLater;
    }
    
    public static void activeFerocityTimes(final Player p, final LivingEntity damaged, int finalDamage, final boolean crit) {
        final PlayerStatistics statistics = (PlayerStatistics)PlayerUtils.STATISTICS_CACHE.get((Object)p.getUniqueId());
        int finalhittime = 0;
        double fer = statistics.getFerocity().addAll();
        int plus_in_calc = 0;
        if (damaged instanceof EnderDragon) {
            final double formula = finalDamage / damaged.getMaxHealth() * 100.0;
            if (formula < 10.0) {
                finalDamage *= 1;
            }
            else if (formula > 10.0 && formula < 15.0) {
                finalDamage -= finalDamage * 90 / 100;
            }
            else if (formula > 15.0 && formula < 20.0) {
                finalDamage -= finalDamage * 99 / 100;
            }
            else if (formula > 20.0 && formula <= 25.0) {
                finalDamage -= (int)(finalDamage * 99.9 / 100.0);
            }
            else if (formula > 25.0) {
                finalDamage *= 0;
            }
            else {
                finalDamage *= 1;
            }
        }
        if (FerocityCalculation.ONE_TYPE_FEROCITY_BONUS_ENDERMAN.containsKey((Object)p.getUniqueId())) {
            plus_in_calc = (int)FerocityCalculation.ONE_TYPE_FEROCITY_BONUS_ENDERMAN.get((Object)p.getUniqueId());
        }
        if (damaged.hasMetadata("VWE")) {
            fer += plus_in_calc;
        }
        if (damaged instanceof Enderman) {
            fer += plus_in_calc;
            if (damaged.hasMetadata("Voidgloom")) {
                if (!VoidgloomSeraph.HIT_SHIELD.containsKey((Object)damaged)) {
                    fer = fer * 25.0 / 100.0;
                }
                else {
                    final int hitshield = (int)VoidgloomSeraph.HIT_SHIELD.get((Object)damaged);
                    if (hitshield <= 0) {
                        fer = fer * 25.0 / 100.0;
                    }
                }
            }
        }
        final double hittimebase = ferocityPercentNext((int)Math.round(fer));
        finalhittime = ferocityStrikeCurrent((int)Math.round(fer));
        final int chance = SUtil.random(0, 100);
        if (chance <= hittimebase) {
            finalhittime = ferocityStrikeNext((int)Math.round(fer) + 1);
        }
        PlayerListener.ferocityActive(finalhittime, p, finalDamage, (Entity)damaged, crit);
    }
    
    static {
        ONE_TYPE_FEROCITY_BONUS_ENDERMAN = (Map)new HashMap();
    }
}
