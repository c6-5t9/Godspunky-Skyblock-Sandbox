package net.hypixel.skyblock.item.bow;

import java.util.HashMap;
import com.google.common.util.concurrent.AtomicDouble;
import org.bukkit.inventory.PlayerInventory;
import java.util.Iterator;
import org.bukkit.util.Vector;
import org.bukkit.Effect;
import net.hypixel.skyblock.listener.PlayerListener;
import org.bukkit.entity.Damageable;
import net.hypixel.skyblock.util.EntityManager;
import net.hypixel.skyblock.item.weapon.EdibleMace;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Item;
import net.hypixel.skyblock.features.skill.Skill;
import net.hypixel.skyblock.item.PlayerBoostStatistics;
import net.hypixel.skyblock.entity.SEntity;
import net.hypixel.skyblock.util.Groups;
import net.hypixel.skyblock.features.enchantment.EnchantmentType;
import net.hypixel.skyblock.features.enchantment.Enchantment;
import net.hypixel.skyblock.item.SMaterial;
import net.hypixel.skyblock.user.User;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Villager;
import org.bukkit.entity.EnderDragonPart;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Entity;
import net.md_5.bungee.api.ChatColor;
import net.hypixel.skyblock.util.ManaReplacement;
import net.hypixel.skyblock.Repeater;
import java.util.Set;
import net.hypixel.skyblock.util.Sputnik;
import org.bukkit.event.entity.EntityShootBowEvent;
import net.hypixel.skyblock.item.SItem;
import org.bukkit.entity.Arrow;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import net.hypixel.skyblock.util.SUtil;
import net.hypixel.skyblock.user.PlayerUtils;
import net.hypixel.skyblock.user.PlayerStatistics;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.Sound;
import net.hypixel.skyblock.util.InventoryUpdate;
import org.bukkit.event.block.Action;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;
import net.hypixel.skyblock.item.SpecificItemType;
import net.hypixel.skyblock.item.GenericItemType;
import net.hypixel.skyblock.item.Rarity;
import java.util.UUID;
import java.util.Map;
import net.hypixel.skyblock.item.Ability;
import net.hypixel.skyblock.item.ToolStatistics;

public class Terminator implements ToolStatistics, BowFunction, Ability
{
    public static final Map<UUID, Integer> CountTerm;
    public static final Map<UUID, Boolean> USABLE_TERM;
    
    @Override
    public String getDisplayName() {
        return "Terminator";
    }
    
    @Override
    public Rarity getRarity() {
        return Rarity.LEGENDARY;
    }
    
    @Override
    public GenericItemType getType() {
        return GenericItemType.RANGED_WEAPON;
    }
    
    @Override
    public SpecificItemType getSpecificType() {
        return SpecificItemType.BOW;
    }
    
    @Override
    public int getBaseDamage() {
        return 310;
    }
    
    @Override
    public double getBaseCritDamage() {
        return 2.5;
    }
    
    @Override
    public double getBaseStrength() {
        return 50.0;
    }
    
    @Override
    public double getBaseAttackSpeed() {
        return 40.0;
    }
    
    @Override
    public boolean displayKills() {
        return false;
    }
    
    @Override
    public void onInteraction(final PlayerInteractEvent e) {
        final Player shooter = e.getPlayer();
        if (!Terminator.CountTerm.containsKey((Object)shooter.getUniqueId())) {
            Terminator.CountTerm.put((Object)shooter.getUniqueId(), (Object)0);
        }
        if (shooter.getPlayer().getInventory().contains(Material.ARROW) || shooter.getPlayer().getGameMode() == GameMode.CREATIVE) {
            if ((e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) && (int)Terminator.CountTerm.get((Object)shooter.getUniqueId()) < 3) {
                shooter.updateInventory();
                if (Terminator.USABLE_TERM.containsKey((Object)shooter.getUniqueId()) && !(boolean)Terminator.USABLE_TERM.get((Object)shooter.getUniqueId())) {
                    return;
                }
                if (shooter.getGameMode() != GameMode.CREATIVE) {
                    InventoryUpdate.removeInventoryItems(shooter.getInventory(), Material.ARROW, 1);
                }
                shooter.playSound(shooter.getLocation(), Sound.SHOOT_ARROW, 1.0f, 1.0f);
                final Location location = shooter.getEyeLocation().add(shooter.getEyeLocation().getDirection().toLocation(shooter.getWorld()));
                final Location l = location.clone();
                l.setYaw(location.getYaw());
                final Arrow a = shooter.getWorld().spawnArrow(l, l.getDirection(), 2.1f, 1.6f);
                a.setShooter((ProjectileSource)shooter);
                l.setYaw(location.getYaw() - 13.5f);
                shooter.getWorld().spawnArrow(l, l.getDirection(), 2.1f, 1.6f).setShooter((ProjectileSource)shooter);
                l.setYaw(location.getYaw() + 13.5f);
                shooter.getWorld().spawnArrow(l, l.getDirection(), 2.1f, 1.6f).setShooter((ProjectileSource)shooter);
                Terminator.USABLE_TERM.put((Object)shooter.getUniqueId(), (Object)false);
                final PlayerStatistics statistics = (PlayerStatistics)PlayerUtils.STATISTICS_CACHE.get((Object)shooter.getUniqueId());
                final double atkSpeed = (double)Math.min(100L, Math.round((double)statistics.getAttackSpeed().addAll()));
                SUtil.delay(() -> {
                    final Boolean b = (Boolean)Terminator.USABLE_TERM.put((Object)shooter.getUniqueId(), (Object)true);
                }, (long)(14.0 / (1.0 + atkSpeed / 100.0)));
            }
            else if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) {
                shooter.updateInventory();
                if (Terminator.USABLE_TERM.containsKey((Object)shooter.getUniqueId()) && !(boolean)Terminator.USABLE_TERM.get((Object)shooter.getUniqueId())) {
                    return;
                }
                if (shooter.getGameMode() != GameMode.CREATIVE) {
                    InventoryUpdate.removeInventoryItems(shooter.getInventory(), Material.ARROW, 1);
                }
                shooter.playSound(shooter.getLocation(), Sound.SHOOT_ARROW, 1.0f, 1.0f);
                final Location location = shooter.getEyeLocation().add(shooter.getEyeLocation().getDirection().toLocation(shooter.getWorld()));
                final Location l = location.clone();
                l.setYaw(location.getYaw());
                final Arrow a2 = shooter.getWorld().spawnArrow(l, l.getDirection(), 2.2f, 1.7f);
                a2.setShooter((ProjectileSource)shooter);
                l.setYaw(location.getYaw() - 13.5f);
                shooter.getWorld().spawnArrow(l, l.getDirection(), 2.2f, 1.7f).setShooter((ProjectileSource)shooter);
                l.setYaw(location.getYaw() + 13.5f);
                shooter.getWorld().spawnArrow(l, l.getDirection(), 2.2f, 1.7f).setShooter((ProjectileSource)shooter);
                Terminator.USABLE_TERM.put((Object)shooter.getUniqueId(), (Object)false);
                final PlayerStatistics statistics = (PlayerStatistics)PlayerUtils.STATISTICS_CACHE.get((Object)shooter.getUniqueId());
                final double atkSpeed = (double)Math.min(100L, Math.round((double)statistics.getAttackSpeed().addAll()));
                SUtil.delay(() -> {
                    final Boolean b = (Boolean)Terminator.USABLE_TERM.put((Object)shooter.getUniqueId(), (Object)true);
                }, (long)(14.0 / (1.0 + atkSpeed / 100.0)));
            }
        }
    }
    
    @Override
    public void onBowShoot(final SItem bow, final EntityShootBowEvent e) {
        final Player player = (Player)e.getEntity();
        e.setCancelled(true);
        player.updateInventory();
    }
    
    @Override
    public String getAbilityName() {
        return "Salvation";
    }
    
    @Override
    public String getAbilityDescription() {
        return Sputnik.trans("Can be casted after landing &63 &7hits. &7Shoot a beam, penetrating up to &e5 &7foes and dealing &c2x &7the damage an arrow would. &7The beam always crits.");
    }
    
    @Override
    public int getAbilityCooldownTicks() {
        return 0;
    }
    
    @Override
    public void onAbilityUse(final Player player, final SItem sItem) {
        if (!Terminator.CountTerm.containsKey((Object)player.getUniqueId())) {
            Terminator.CountTerm.put((Object)player.getUniqueId(), (Object)0);
        }
        String ACT = "true";
        if ((int)Terminator.CountTerm.get((Object)player.getUniqueId()) >= 3) {
            final Location blockLocation = player.getTargetBlock((Set)null, 30).getLocation();
            final Location crystalLocation = player.getEyeLocation().add(0.0, -0.1, 0.0);
            final Vector vector = blockLocation.clone().add(0.1, 0.0, 0.1).toVector().subtract(crystalLocation.clone().toVector());
            final double count = 40.0;
            final int manaPool = SUtil.blackMagic(((PlayerStatistics)PlayerUtils.STATISTICS_CACHE.get((Object)player.getUniqueId())).getIntelligence().addAll() + 100.0);
            final int cost = PlayerUtils.getFinalManaCost(player, sItem, 100);
            final boolean take = PlayerUtils.takeMana(player, cost);
            if (!take) {
                player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1.0f, -4.0f);
                final long c = System.currentTimeMillis();
                Repeater.MANA_REPLACEMENT_MAP.put((Object)player.getUniqueId(), (Object)new ManaReplacement() {
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
            Terminator.CountTerm.put((Object)player.getUniqueId(), (Object)0);
            player.getWorld().playSound(player.getLocation(), Sound.GHAST_FIREBALL, 1.0f, 1.0f);
            for (int i = 1; i <= 40; ++i) {
                for (final Entity entity : player.getWorld().getNearbyEntities(crystalLocation.clone().add(vector.clone().multiply(i / 40.0)), 0.7, 1.0, 0.7)) {
                    if (ACT == "false") {
                        return;
                    }
                    if (entity.isDead()) {
                        continue;
                    }
                    if (!(entity instanceof LivingEntity)) {
                        continue;
                    }
                    if (entity.hasMetadata("GiantSword")) {
                        continue;
                    }
                    if (entity instanceof Player || entity instanceof EnderDragonPart) {
                        continue;
                    }
                    if (entity instanceof Villager) {
                        continue;
                    }
                    if (entity instanceof ArmorStand) {
                        continue;
                    }
                    final User user = User.getUser(player.getUniqueId());
                    double enchantBonus = 0.0;
                    final double potionBonus = 0.0;
                    double bonusDamage = 0.0;
                    final PlayerStatistics statistics1 = (PlayerStatistics)PlayerUtils.STATISTICS_CACHE.get((Object)player.getUniqueId());
                    double critDamage = statistics1.getCritDamage().addAll();
                    final double speed = statistics1.getSpeed().addAll();
                    final double realSpeed = speed * 100.0 % 25.0;
                    final double realSpeedDIV = speed - realSpeed;
                    final double realSpeedDIVC = realSpeedDIV / 25.0;
                    final PlayerInventory inv = player.getInventory();
                    final SItem helmet = SItem.find(inv.getHelmet());
                    if (helmet != null && helmet.getType() == SMaterial.WARDEN_HELMET) {
                        enchantBonus += (100.0 + 20.0 * realSpeedDIVC) / 100.0;
                    }
                    for (final Enchantment enchantment : sItem.getEnchantments()) {
                        final EnchantmentType type1 = enchantment.getType();
                        final int level = enchantment.getLevel();
                        if (type1 == EnchantmentType.POWER) {
                            enchantBonus += level * 8 / 100.0;
                        }
                        if (type1 == EnchantmentType.SMITE && Groups.UNDEAD_MOBS.contains((Object)entity.getType())) {
                            enchantBonus += level * 8 / 100.0;
                        }
                        if (type1 == EnchantmentType.ENDER_SLAYER && Groups.ENDERMAN.contains((Object)entity.getType())) {
                            enchantBonus += level * 12 / 100.0;
                        }
                        if (type1 == EnchantmentType.BANE_OF_ARTHROPODS && Groups.ARTHROPODS.contains((Object)entity.getType())) {
                            enchantBonus += level * 8 / 100.0;
                        }
                        if (type1 == EnchantmentType.DRAGON_HUNTER && Groups.ENDERDRAGON.contains((Object)entity.getType())) {
                            enchantBonus += level * 8 / 100.0;
                        }
                        if (type1 == EnchantmentType.CRITICAL) {
                            critDamage += level * 10 / 100.0;
                        }
                        if (type1 == EnchantmentType.SOUL_EATER && PlayerUtils.SOUL_EATER_MAP.containsKey((Object)player.getUniqueId()) && PlayerUtils.SOUL_EATER_MAP.get((Object)player.getUniqueId()) != null) {
                            bonusDamage += ((SEntity)PlayerUtils.SOUL_EATER_MAP.get((Object)player.getUniqueId())).getStatistics().getDamageDealt() * (level * 2);
                            PlayerUtils.SOUL_EATER_MAP.put((Object)player.getUniqueId(), (Object)null);
                        }
                    }
                    final SMaterial material = sItem.getType();
                    double hpbwea = 0.0;
                    if (sItem.getDataInt("hpb") > 0) {
                        hpbwea = sItem.getDataInt("hpb") * 2;
                    }
                    final PlayerBoostStatistics playerBoostStatistics = (PlayerBoostStatistics)material.getStatistics();
                    final double baseDamage = (5.0 + (playerBoostStatistics.getBaseDamage() + hpbwea)) * (1.0 + statistics1.getStrength().addAll() / 100.0);
                    final int combatLevel = Skill.getLevel(User.getUser(player.getUniqueId()).getCombatXP(), false);
                    final double weaponBonus = 0.0;
                    final double armorBonus = 1.0;
                    final int critChanceMul = 100;
                    final int chance = SUtil.random(0, 100);
                    if (chance > 100) {
                        critDamage = 0.0;
                    }
                    final double damageMultiplier = 1.0 + combatLevel * 0.04 + enchantBonus + 0.0;
                    final double finalCritDamage = critDamage;
                    double finalDamage = baseDamage * damageMultiplier * 1.0 * (1.0 + finalCritDamage) + bonusDamage;
                    final double finalPotionBonus = 0.0;
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
                    if (EdibleMace.edibleMace.containsKey((Object)player.getUniqueId()) && (boolean)EdibleMace.edibleMace.get((Object)player.getUniqueId())) {
                        finalDamage = finalDamage;
                        EdibleMace.edibleMace.put((Object)player.getUniqueId(), (Object)false);
                    }
                    if (EntityManager.DEFENSE_PERCENTAGE.containsKey((Object)entity)) {
                        int defensepercent = (int)EntityManager.DEFENSE_PERCENTAGE.get((Object)entity);
                        if (defensepercent > 100) {
                            defensepercent = 100;
                        }
                        finalDamage -= finalDamage * defensepercent / 100.0;
                    }
                    user.damageEntity((Damageable)entity, finalDamage * 1.2);
                    player.playSound(player.getLocation(), Sound.SUCCESSFUL_HIT, 1.0f, 0.0f);
                    PlayerListener.spawnDamageInd(entity, finalDamage * 1.2, true);
                    ACT = "false";
                }
                player.getWorld().spigot().playEffect(crystalLocation.clone().add(vector.clone().multiply(i / 40.0)), Effect.COLOURED_DUST, 0, 1, 0.5882353f, 0.03529412f, 0.007843138f, 1.0f, 0, 64);
                player.getWorld().spigot().playEffect(crystalLocation.clone().add(vector.clone().multiply(i / 40.0)), Effect.COLOURED_DUST, 0, 1, 0.84313726f, 0.03529412f, 0.007843138f, 1.0f, 0, 64);
            }
        }
    }
    
    @Override
    public int getManaCost() {
        return 0;
    }
    
    @Override
    public boolean displayUsage() {
        return false;
    }
    
    @Override
    public void onBowHit(final Entity hit, final Player shooter, final Arrow arrow, final SItem weapon, final AtomicDouble finalDamage) {
        if (hit.isDead()) {
            return;
        }
        if (!(hit instanceof LivingEntity)) {
            return;
        }
        if (hit.hasMetadata("GiantSword")) {
            return;
        }
        if (hit instanceof Player || hit instanceof Villager || hit instanceof ArmorStand) {
            return;
        }
        Terminator.CountTerm.put((Object)shooter.getUniqueId(), (Object)((int)Terminator.CountTerm.get((Object)shooter.getUniqueId()) + 1));
    }
    
    @Override
    public boolean isEnchanted() {
        return true;
    }
    
    static {
        CountTerm = (Map)new HashMap();
        USABLE_TERM = (Map)new HashMap();
    }
}
