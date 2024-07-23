package net.hypixel.skyblock.item.armor;

import java.util.HashMap;
import java.util.Iterator;
import org.bukkit.util.Vector;
import org.bukkit.Location;
import org.bukkit.Effect;
import net.hypixel.skyblock.util.EntityManager;
import net.hypixel.skyblock.entity.SEntity;
import net.hypixel.skyblock.entity.SEntityType;
import net.hypixel.skyblock.util.SLog;
import org.bukkit.entity.Damageable;
import net.hypixel.skyblock.features.skill.Skill;
import net.hypixel.skyblock.user.User;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Villager;
import org.bukkit.entity.EnderDragonPart;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Entity;
import net.hypixel.skyblock.util.DefenseReplacement;
import net.hypixel.skyblock.util.ManaReplacement;
import net.hypixel.skyblock.Repeater;
import org.bukkit.Sound;
import net.hypixel.skyblock.util.SUtil;
import net.hypixel.skyblock.user.PlayerUtils;
import net.hypixel.skyblock.user.PlayerStatistics;
import java.util.Set;
import net.hypixel.skyblock.item.SMaterial;
import org.bukkit.plugin.Plugin;
import net.hypixel.skyblock.SkyBlock;
import org.bukkit.scheduler.BukkitRunnable;
import net.hypixel.skyblock.item.SItem;
import org.bukkit.entity.Player;
import net.hypixel.skyblock.item.AbilityActivation;
import net.md_5.bungee.api.ChatColor;
import net.hypixel.skyblock.item.SpecificItemType;
import net.hypixel.skyblock.item.GenericItemType;
import net.hypixel.skyblock.item.Rarity;
import java.util.UUID;
import java.util.Map;
import net.hypixel.skyblock.item.TickingMaterial;
import net.hypixel.skyblock.item.Ability;
import net.hypixel.skyblock.item.ToolStatistics;
import net.hypixel.skyblock.item.SkullStatistics;
import net.hypixel.skyblock.item.MaterialFunction;

public class PrecursorEye implements MaterialFunction, SkullStatistics, ToolStatistics, Ability, TickingMaterial
{
    public static final Map<UUID, Boolean> PrecursorLaser;
    public static final Map<UUID, Integer> PrecursorLivingSeconds;
    int boosting;
    
    @Override
    public double getBaseHealth() {
        return 222.0;
    }
    
    @Override
    public double getBaseDefense() {
        return 222.0;
    }
    
    @Override
    public double getBaseIntelligence() {
        return 222.0;
    }
    
    @Override
    public String getURL() {
        return "72c0683b2019ca3d3947273e394bfca1b4d71b61b67b39474c2d6d73a9b67508";
    }
    
    @Override
    public String getDisplayName() {
        return "Precursor Eye";
    }
    
    @Override
    public Rarity getRarity() {
        return Rarity.LEGENDARY;
    }
    
    @Override
    public GenericItemType getType() {
        return GenericItemType.ARMOR;
    }
    
    @Override
    public SpecificItemType getSpecificType() {
        return SpecificItemType.HELMET;
    }
    
    @Override
    public String getLore() {
        return null;
    }
    
    @Override
    public String getAbilityName() {
        return "Eye Beam";
    }
    
    @Override
    public String getAbilityDescription() {
        return ChatColor.translateAlternateColorCodes('&', "Fire a laser in front of you dealing &c4000 &7damage and costing &b40 &7mana. The damage increases by &c100% &7every second for &b5 &7seconds and the mana cost increases by &d25% &7every second. You can sneak again to de-activate the laser.");
    }
    
    @Override
    public int getAbilityCooldownTicks() {
        return 0;
    }
    
    @Override
    public boolean displayUsage() {
        return false;
    }
    
    @Override
    public AbilityActivation getAbilityActivation() {
        return AbilityActivation.SNEAK;
    }
    
    @Override
    public void onAbilityUse(final Player player, final SItem sItem) {
        if (!PrecursorEye.PrecursorLaser.containsKey((Object)player.getUniqueId())) {
            PrecursorEye.PrecursorLaser.put((Object)player.getUniqueId(), (Object)false);
        }
        if (!(boolean)PrecursorEye.PrecursorLaser.get((Object)player.getUniqueId())) {
            PrecursorEye.PrecursorLaser.put((Object)player.getUniqueId(), (Object)true);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&dEye Beam &aActivated!"));
        }
        else {
            if (!PrecursorEye.PrecursorLaser.containsKey((Object)player.getUniqueId())) {
                PrecursorEye.PrecursorLaser.put((Object)player.getUniqueId(), (Object)false);
            }
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&dEye Beam &cDe-Activated!"));
            PrecursorEye.PrecursorLaser.put((Object)player.getUniqueId(), (Object)false);
            PrecursorEye.PrecursorLivingSeconds.put((Object)player.getUniqueId(), (Object)0);
        }
        new BukkitRunnable() {
            public void run() {
                if (!PrecursorEye.PrecursorLaser.containsKey((Object)player.getUniqueId())) {
                    this.cancel();
                    return;
                }
                if (!(boolean)PrecursorEye.PrecursorLaser.get((Object)player.getUniqueId())) {
                    this.cancel();
                    return;
                }
                PrecursorEye.this.ticking(sItem, player);
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 15L, 15L);
    }
    
    public void ticking(final SItem item, final Player player) {
        final SItem helmet = SItem.find(player.getInventory().getHelmet());
        if (helmet == null) {
            return;
        }
        if (helmet.getType() != SMaterial.PRECURSOR_EYE) {
            return;
        }
        String ACT = "true";
        if (!PrecursorEye.PrecursorLivingSeconds.containsKey((Object)player.getUniqueId())) {
            PrecursorEye.PrecursorLivingSeconds.put((Object)player.getUniqueId(), (Object)0);
        }
        else if (PrecursorEye.PrecursorLaser.get((Object)player.getUniqueId())) {
            PrecursorEye.PrecursorLivingSeconds.put((Object)player.getUniqueId(), (Object)((int)PrecursorEye.PrecursorLivingSeconds.get((Object)player.getUniqueId()) + 1));
        }
        if (PrecursorEye.PrecursorLaser.get((Object)player.getUniqueId())) {
            final Location blockLocation = player.getTargetBlock((Set)null, 30).getLocation();
            final Location crystalLocation = player.getEyeLocation();
            final Vector vector = blockLocation.clone().add(0.1, 0.0, 0.1).toVector().subtract(crystalLocation.clone().toVector());
            final double count = 40.0;
            int manaCost = 0;
            this.boosting = (int)PrecursorEye.PrecursorLivingSeconds.get((Object)player.getUniqueId());
            int damage = 0;
            switch (this.boosting - 1) {
                case 0: {
                    manaCost = 40;
                    damage = 4000;
                    break;
                }
                case 1: {
                    manaCost = 50;
                    damage = 8000;
                    break;
                }
                case 2: {
                    manaCost = 62;
                    damage = 16000;
                    break;
                }
                case 3: {
                    manaCost = 77;
                    damage = 32000;
                    break;
                }
                case 4: {
                    manaCost = 96;
                    damage = 64000;
                    break;
                }
                default: {
                    manaCost = 120;
                    damage = 128000;
                    break;
                }
            }
            final int manaPool = SUtil.blackMagic(((PlayerStatistics)PlayerUtils.STATISTICS_CACHE.get((Object)player.getUniqueId())).getIntelligence().addAll() + 100.0);
            final int cost = PlayerUtils.getFinalManaCost(player, item, manaCost);
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
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&dEye Beam &cDe-Activated!"));
                PrecursorEye.PrecursorLaser.put((Object)player.getUniqueId(), (Object)false);
                PrecursorEye.PrecursorLivingSeconds.put((Object)player.getUniqueId(), (Object)0);
                return;
            }
            final long c = System.currentTimeMillis();
            Repeater.DEFENSE_REPLACEMENT_MAP.put((Object)player.getUniqueId(), (Object)new DefenseReplacement() {
                @Override
                public String getReplacement() {
                    return (Object)ChatColor.AQUA + "-" + cost + " Mana (" + (Object)ChatColor.GOLD + PrecursorEye.this.getAbilityName() + (Object)ChatColor.AQUA + ")";
                }
                
                @Override
                public long getEnd() {
                    return c + 2000L;
                }
            });
            for (int i = 1; i <= 40; ++i) {
                for (final Entity entity : player.getWorld().getNearbyEntities(crystalLocation.clone().add(vector.clone().multiply(i / 40.0)), 0.5, 0.0, 0.5)) {
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
                    final double damage2 = damage;
                    final int combatLevel = Skill.getLevel(User.getUser(player.getUniqueId()).getCombatXP(), false);
                    final double damageMultiplier = 1.0 + combatLevel * 0.04;
                    user.damageEntity((Damageable)entity, (int)damage2 * damageMultiplier);
                    if (PlayerUtils.Debugmsg.debugmsg) {
                        SLog.info("[DEBUG] " + player.getName() + " have dealt " + (float)damage2 * damageMultiplier + " damage! (Eye Beam Ability)");
                    }
                    final ArmorStand stands = (ArmorStand)new SEntity(entity.getLocation().clone().add(SUtil.random(-1.5, 1.5), 1.0, SUtil.random(-1.5, 1.5)), SEntityType.UNCOLLIDABLE_ARMOR_STAND, new Object[0]).getEntity();
                    int finaldmg = (int)((int)damage2 * damageMultiplier);
                    if (EntityManager.DEFENSE_PERCENTAGE.containsKey((Object)entity)) {
                        int defensepercent = (int)EntityManager.DEFENSE_PERCENTAGE.get((Object)entity);
                        if (defensepercent > 100) {
                            defensepercent = 100;
                        }
                        finaldmg -= finaldmg * defensepercent / 100;
                    }
                    stands.setCustomName("" + (Object)ChatColor.GRAY + finaldmg);
                    stands.setCustomNameVisible(true);
                    stands.setGravity(false);
                    stands.setVisible(false);
                    new BukkitRunnable() {
                        public void run() {
                            stands.remove();
                            this.cancel();
                        }
                    }.runTaskLater((Plugin)SkyBlock.getPlugin(), 30L);
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
    
    static {
        PrecursorLaser = (Map)new HashMap();
        PrecursorLivingSeconds = (Map)new HashMap();
    }
}
