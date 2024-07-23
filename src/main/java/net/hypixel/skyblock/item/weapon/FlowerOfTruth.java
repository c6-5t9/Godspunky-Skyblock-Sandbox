package net.hypixel.skyblock.item.weapon;

import org.bukkit.plugin.Plugin;
import net.hypixel.skyblock.SkyBlock;
import java.util.Iterator;
import org.bukkit.Location;
import net.hypixel.skyblock.util.FerocityCalculation;
import net.hypixel.skyblock.listener.PlayerListener;
import net.hypixel.skyblock.util.Sputnik;
import net.hypixel.skyblock.user.User;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Item;
import org.bukkit.entity.Villager;
import org.bukkit.entity.EnderDragonPart;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Entity;
import org.bukkit.Effect;
import org.bukkit.Material;
import java.util.List;
import org.bukkit.scheduler.BukkitRunnable;
import java.util.ArrayList;
import org.bukkit.util.EulerAngle;
import net.hypixel.skyblock.item.SMaterial;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ArmorStand;
import net.hypixel.skyblock.util.DefenseReplacement;
import net.hypixel.skyblock.util.ManaReplacement;
import net.hypixel.skyblock.Repeater;
import org.bukkit.Sound;
import net.hypixel.skyblock.util.SUtil;
import net.hypixel.skyblock.user.PlayerUtils;
import net.hypixel.skyblock.user.PlayerStatistics;
import net.hypixel.skyblock.item.SItem;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;
import net.hypixel.skyblock.item.SpecificItemType;
import net.hypixel.skyblock.item.GenericItemType;
import net.hypixel.skyblock.item.Rarity;
import org.bukkit.util.Vector;
import net.hypixel.skyblock.item.Ability;
import net.hypixel.skyblock.item.MaterialFunction;
import net.hypixel.skyblock.item.ToolStatistics;

public class FlowerOfTruth implements ToolStatistics, MaterialFunction, Ability
{
    Vector teleportTo;
    String ACT3;
    
    public FlowerOfTruth() {
        this.ACT3 = "true";
    }
    
    @Override
    public int getBaseDamage() {
        return 100;
    }
    
    @Override
    public double getBaseStrength() {
        return 360.0;
    }
    
    @Override
    public String getDisplayName() {
        return "Flower of Truth";
    }
    
    @Override
    public Rarity getRarity() {
        return Rarity.LEGENDARY;
    }
    
    @Override
    public GenericItemType getType() {
        return GenericItemType.WEAPON;
    }
    
    @Override
    public SpecificItemType getSpecificType() {
        return SpecificItemType.SWORD;
    }
    
    @Override
    public String getLore() {
        return null;
    }
    
    @Override
    public String getAbilityName() {
        return "Heat-Seeking Rose";
    }
    
    @Override
    public String getAbilityDescription() {
        return "Shoots a rose that ricochets between enemies, damaging up to " + (Object)ChatColor.GREEN + "3 " + (Object)ChatColor.GRAY + "of your foes! Damage multiplies as more enemies are hit. Cost " + (Object)ChatColor.GREEN + "10.0% " + (Object)ChatColor.GRAY + "of your maximum mana";
    }
    
    @Override
    public void onAbilityUse(final Player player1, final SItem sItem) {
        final int manaPool = SUtil.blackMagic(((PlayerStatistics)PlayerUtils.STATISTICS_CACHE.get((Object)player1.getUniqueId())).getIntelligence().addAll() + 100.0);
        final int manaCost = manaPool * 10 / 100;
        final int cost = PlayerUtils.getFinalManaCost(player1, sItem, manaCost);
        final boolean take = PlayerUtils.takeMana(player1, cost);
        if (!take) {
            player1.playSound(player1.getLocation(), Sound.ENDERMAN_TELEPORT, 1.0f, -4.0f);
            final long c = System.currentTimeMillis();
            Repeater.MANA_REPLACEMENT_MAP.put((Object)player1.getUniqueId(), (Object)new ManaReplacement() {
                @Override
                public String getReplacement() {
                    return "" + (Object)ChatColor.RED + (Object)ChatColor.BOLD + "NOT ENOUGH MANA";
                }
                
                @Override
                public long getEnd() {
                    return c + 1500L;
                }
            });
            return;
        }
        final long c = System.currentTimeMillis();
        Repeater.DEFENSE_REPLACEMENT_MAP.put((Object)player1.getUniqueId(), (Object)new DefenseReplacement() {
            @Override
            public String getReplacement() {
                return (Object)ChatColor.AQUA + "-" + cost + " Mana (" + (Object)ChatColor.GOLD + FlowerOfTruth.this.getAbilityName() + (Object)ChatColor.AQUA + ")";
            }
            
            @Override
            public long getEnd() {
                return c + 2000L;
            }
        });
        final Location throwLoc = player1.getLocation().add(0.0, 0.2, 0.0);
        player1.playSound(player1.getLocation(), Sound.EAT, 1.0f, 1.0f);
        final ArmorStand armorStand1 = (ArmorStand)player1.getWorld().spawnEntity(throwLoc, EntityType.ARMOR_STAND);
        armorStand1.getEquipment().setHelmet(SItem.of(SMaterial.RED_ROSE).getStack());
        armorStand1.setHeadPose(new EulerAngle(-92.55000305175781, 0.0, 0.0));
        armorStand1.setGravity(false);
        armorStand1.setVisible(false);
        final Player bukkitPlayer = player1.getPlayer();
        this.teleportTo = bukkitPlayer.getLocation().getDirection().normalize().multiply(1);
        final List<LivingEntity> le = (List<LivingEntity>)new ArrayList();
        new BukkitRunnable() {
            private int run = -1;
            int entityhit = 0;
            
            public void run() {
                final Vector teleportTo = armorStand1.getLocation().getDirection().normalize().multiply(1);
                final int ran;
                final int i = ran = 0;
                int j = 0;
                final int num = 90;
                final Location loc = null;
                ++this.run;
                ++j;
                if (this.run > 100) {
                    this.cancel();
                    return;
                }
                if (j >= 40) {
                    armorStand1.remove();
                    this.cancel();
                    return;
                }
                final Location locof = armorStand1.getLocation();
                locof.setY(locof.getY() + 1.0);
                if (locof.getBlock().getType() != Material.AIR) {
                    armorStand1.getWorld().playEffect(locof, Effect.SNOWBALL_BREAK, 3);
                    armorStand1.getWorld().playEffect(locof, Effect.SNOWBALL_BREAK, 10);
                    armorStand1.getWorld().playEffect(locof, Effect.SNOWBALL_BREAK, 10);
                    armorStand1.getWorld().playEffect(locof, Effect.SNOWBALL_BREAK, 10);
                    armorStand1.remove();
                    this.cancel();
                    return;
                }
                if (this.entityhit >= 3 || le.size() >= 3) {
                    armorStand1.getWorld().playEffect(locof, Effect.SNOWBALL_BREAK, 3);
                    armorStand1.getWorld().playEffect(locof, Effect.SNOWBALL_BREAK, 10);
                    armorStand1.getWorld().playEffect(locof, Effect.SNOWBALL_BREAK, 10);
                    armorStand1.getWorld().playEffect(locof, Effect.SNOWBALL_BREAK, 10);
                    armorStand1.remove();
                    this.cancel();
                    return;
                }
                if (this.entityhit < 3) {
                    for (final Entity e2 : armorStand1.getNearbyEntities(8.0, 8.0, 8.0)) {
                        if (e2 instanceof LivingEntity) {
                            if (e2.isDead()) {
                                continue;
                            }
                            if (!(e2 instanceof LivingEntity)) {
                                continue;
                            }
                            if (e2 instanceof Player || e2 instanceof EnderDragonPart || e2 instanceof Villager || e2 instanceof ArmorStand) {
                                continue;
                            }
                            if (e2 instanceof Item) {
                                continue;
                            }
                            if (e2 instanceof ItemFrame) {
                                continue;
                            }
                            if (e2.hasMetadata("GiantSword")) {
                                continue;
                            }
                            armorStand1.teleport(armorStand1.getLocation().setDirection(e2.getLocation().toVector().subtract(armorStand1.getLocation().toVector())));
                            break;
                        }
                    }
                }
                if (i % 2 == 0 && i < 13) {
                    armorStand1.teleport(armorStand1.getLocation().add(teleportTo).multiply(1.0));
                }
                else if (i % 2 == 0) {
                    armorStand1.teleport(armorStand1.getLocation().subtract(loc.getDirection().normalize().multiply(1)));
                }
                for (final Entity e3 : armorStand1.getNearbyEntities(1.0, 1.0, 1.0)) {
                    if (e3 instanceof LivingEntity && e3 != player1.getPlayer()) {
                        final Damageable entity = (Damageable)e3;
                        if (le.contains((Object)e3)) {
                            continue;
                        }
                        if (entity.isDead()) {
                            continue;
                        }
                        if (!(entity instanceof LivingEntity)) {
                            continue;
                        }
                        if (entity instanceof Player || entity instanceof EnderDragonPart || entity instanceof Villager || entity instanceof ArmorStand) {
                            continue;
                        }
                        if (entity instanceof Item) {
                            continue;
                        }
                        if (entity instanceof ItemFrame) {
                            continue;
                        }
                        if (entity.hasMetadata("GiantSword")) {
                            continue;
                        }
                        final User user = User.getUser(player1.getUniqueId());
                        final Object[] atp = Sputnik.calculateDamage(player1, player1, sItem.getStack(), (LivingEntity)entity, false);
                        final double finalDamage1 = (float)atp[0];
                        le.add((Object)e3);
                        PlayerListener.spawnDamageInd((Entity)entity, (float)atp[2], (boolean)atp[1]);
                        FerocityCalculation.activeFerocityTimes(player1, (LivingEntity)entity, (int)finalDamage1, (boolean)atp[1]);
                        user.damageEntity(entity, finalDamage1);
                        ++this.entityhit;
                        int k = 0;
                        for (final Entity e4 : armorStand1.getNearbyEntities(20.0, 20.0, 20.0)) {
                            if (e4 instanceof LivingEntity) {
                                if (e4.isDead()) {
                                    continue;
                                }
                                if (!(e4 instanceof LivingEntity)) {
                                    continue;
                                }
                                if (e4 instanceof Player || e4 instanceof EnderDragonPart || e4 instanceof Villager || e4 instanceof ArmorStand) {
                                    continue;
                                }
                                if (e4 instanceof Item) {
                                    continue;
                                }
                                if (e4 instanceof ItemFrame) {
                                    continue;
                                }
                                if (e4.hasMetadata("GiantSword")) {
                                    continue;
                                }
                                ++k;
                            }
                        }
                        if (k > 0) {
                            continue;
                        }
                        armorStand1.getWorld().playEffect(armorStand1.getLocation().clone().add(0.0, 1.8, 0.0), Effect.SNOWBALL_BREAK, 10);
                        armorStand1.getWorld().playEffect(armorStand1.getLocation().clone().add(0.0, 1.8, 0.0), Effect.SNOWBALL_BREAK, 10);
                        armorStand1.getWorld().playEffect(armorStand1.getLocation().clone().add(0.0, 1.8, 0.0), Effect.SNOWBALL_BREAK, 10);
                        armorStand1.getWorld().playEffect(armorStand1.getLocation().clone().add(0.0, 1.8, 0.0), Effect.SNOWBALL_BREAK, 10);
                        armorStand1.remove();
                        this.cancel();
                    }
                }
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 1L, 1L);
        new BukkitRunnable() {
            public void run() {
                if (armorStand1.isDead()) {
                    return;
                }
                armorStand1.getWorld().playEffect(armorStand1.getLocation().clone().add(0.0, 1.8, 0.0), Effect.SNOWBALL_BREAK, 10);
                armorStand1.getWorld().playEffect(armorStand1.getLocation().clone().add(0.0, 1.8, 0.0), Effect.SNOWBALL_BREAK, 10);
                armorStand1.getWorld().playEffect(armorStand1.getLocation().clone().add(0.0, 1.8, 0.0), Effect.SNOWBALL_BREAK, 10);
                armorStand1.getWorld().playEffect(armorStand1.getLocation().clone().add(0.0, 1.8, 0.0), Effect.SNOWBALL_BREAK, 10);
                armorStand1.remove();
                this.cancel();
            }
        }.runTaskLater((Plugin)SkyBlock.getPlugin(), 40L);
    }
    
    @Override
    public int getAbilityCooldownTicks() {
        return 20;
    }
    
    @Override
    public int getManaCost() {
        return 0;
    }
    
    @Override
    public boolean isEnchanted() {
        return true;
    }
}
