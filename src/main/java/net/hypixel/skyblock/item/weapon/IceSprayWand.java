package net.hypixel.skyblock.item.weapon;

import java.util.Iterator;
import org.bukkit.Location;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;
import org.bukkit.util.EulerAngle;
import net.hypixel.skyblock.listener.PlayerListener;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.entity.Damageable;
import net.hypixel.skyblock.user.User;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Villager;
import org.bukkit.entity.EnderDragonPart;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Entity;
import org.bukkit.Sound;
import net.hypixel.skyblock.util.SUtil;
import net.hypixel.skyblock.user.PlayerUtils;
import net.hypixel.skyblock.user.PlayerStatistics;
import org.bukkit.Color;
import de.slikey.effectlib.util.ParticleEffect;
import de.slikey.effectlib.effect.ConeEffect;
import net.hypixel.skyblock.SkyBlock;
import net.hypixel.skyblock.item.SItem;
import org.bukkit.entity.Player;
import net.hypixel.skyblock.util.Sputnik;
import net.hypixel.skyblock.item.SpecificItemType;
import net.hypixel.skyblock.item.GenericItemType;
import net.hypixel.skyblock.item.Rarity;
import net.hypixel.skyblock.item.Ability;
import net.hypixel.skyblock.item.MaterialFunction;
import net.hypixel.skyblock.item.ToolStatistics;

public class IceSprayWand implements ToolStatistics, MaterialFunction, Ability
{
    @Override
    public int getBaseDamage() {
        return 140;
    }
    
    @Override
    public double getBaseIntelligence() {
        return 345.0;
    }
    
    @Override
    public String getDisplayName() {
        return "Ice Spray Wand";
    }
    
    @Override
    public Rarity getRarity() {
        return Rarity.EPIC;
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
        return "Ice Spray";
    }
    
    @Override
    public String getAbilityDescription() {
        return Sputnik.trans("&7Produces a cone of ice in front of the caster that deals &c25,000 &7damage to mobs and freezes them in place for &e5 &7seconds! Frozen mobs take &c10% &7incresed damage!");
    }
    
    @Override
    public void onAbilityUse(final Player player, final SItem sItem) {
        final Location loc = player.getEyeLocation();
        final ConeEffect Effect = new ConeEffect(SkyBlock.effectManager);
        Effect.setLocation(loc.clone().add(loc.getDirection().normalize().multiply(-0.25)).add(0.0, -0.1, 0.0));
        Effect.particle = ParticleEffect.SNOW_SHOVEL;
        Effect.color = Color.WHITE;
        Effect.angularVelocity = 0.39269908169872414;
        Effect.lengthGrow = 0.025f;
        Effect.particles = 30;
        Effect.period = 3;
        Effect.iterations = 5;
        Effect.start();
        final PlayerStatistics statistics = (PlayerStatistics)PlayerUtils.STATISTICS_CACHE.get((Object)player.getUniqueId());
        final int manaPool = SUtil.blackMagic(100.0 + statistics.getIntelligence().addAll());
        player.playSound(player.getLocation(), Sound.ENDERDRAGON_GROWL, 5.0f, 5.0f);
        for (final Entity entity : player.getWorld().getNearbyEntities(player.getLocation().add(player.getLocation().getDirection().multiply(3.0)), 3.0, 3.0, 3.0)) {
            if (!(entity instanceof LivingEntity)) {
                continue;
            }
            if (entity instanceof Player || entity instanceof EnderDragon || entity instanceof EnderDragonPart) {
                continue;
            }
            if (entity instanceof Villager) {
                continue;
            }
            if (entity instanceof ArmorStand) {
                continue;
            }
            if (entity.hasMetadata("GiantSword")) {
                continue;
            }
            if (entity.hasMetadata("NoAffect")) {
                continue;
            }
            if (entity.isDead()) {
                continue;
            }
            final User user = User.getUser(player.getUniqueId());
            final double baseDamage = Sputnik.calculateMagicDamage(entity, player, 32000, 0.1);
            user.damageEntityIgnoreShield((Damageable)entity, baseDamage);
            entity.setMetadata("frozen", (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)true));
            PlayerListener.spawnDamageInd(entity, baseDamage, false);
            double b = 0.0;
            for (int i = 0; i < 2; ++i) {
                final int d;
                if ((d = i) == 0) {
                    b = 0.2;
                }
                else if (i == 1) {
                    b = 0.4;
                }
                else if (i == 2) {
                    b = 0.6;
                }
                final ArmorStand stands = (ArmorStand)entity.getWorld().spawn(entity.getLocation().add(0.0, b + 1.0, 0.0), (Class)ArmorStand.class);
                stands.setCustomNameVisible(false);
                stands.setVisible(false);
                stands.setArms(true);
                stands.setMarker(true);
                stands.setGravity(false);
                stands.setRightArmPose(new EulerAngle(0.0, 0.0, 12.0));
                stands.getEquipment().setItemInHand(new ItemStack(Material.PACKED_ICE));
                SUtil.delay(() -> stands.remove(), 100L);
                new BukkitRunnable() {
                    public void run() {
                        double c = 0.0;
                        if (d == 0) {
                            c = 0.2;
                        }
                        else if (d == 1) {
                            c = 0.4;
                        }
                        else if (d == 2) {
                            c = 0.6;
                        }
                        if (stands.isDead()) {
                            ((LivingEntity)entity).removePotionEffect(PotionEffectType.SLOW);
                            entity.removeMetadata("frozen", (Plugin)SkyBlock.getPlugin());
                            this.cancel();
                            return;
                        }
                        if (entity.isDead()) {
                            stands.remove();
                        }
                        ((LivingEntity)entity).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100, 20));
                        stands.teleport(entity.getLocation().add(0.0, c + 1.0, 0.0));
                    }
                }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 0L, 1L);
            }
        }
    }
    
    @Override
    public int getAbilityCooldownTicks() {
        return 100;
    }
    
    @Override
    public int getManaCost() {
        return 50;
    }
}
