package net.hypixel.skyblock.item.weapon;

import java.util.Iterator;
import org.bukkit.plugin.Plugin;
import net.hypixel.skyblock.SkyBlock;
import net.hypixel.skyblock.listener.PlayerListener;
import org.bukkit.entity.Damageable;
import net.hypixel.skyblock.user.User;
import org.bukkit.entity.Villager;
import org.bukkit.entity.EnderDragonPart;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Entity;
import net.hypixel.skyblock.util.SSU;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.util.Vector;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;
import net.hypixel.skyblock.item.SMaterial;
import org.bukkit.entity.ArmorStand;
import org.bukkit.Color;
import java.util.Random;
import org.bukkit.Sound;
import net.hypixel.skyblock.item.SItem;
import org.bukkit.entity.Player;
import net.hypixel.skyblock.util.Sputnik;
import net.hypixel.skyblock.item.SpecificItemType;
import net.hypixel.skyblock.item.GenericItemType;
import net.hypixel.skyblock.item.Rarity;
import net.hypixel.skyblock.item.Ability;
import net.hypixel.skyblock.item.MaterialFunction;
import net.hypixel.skyblock.item.ToolStatistics;

public class BonzoStaff implements ToolStatistics, MaterialFunction, Ability
{
    String ACT;
    
    public BonzoStaff() {
        this.ACT = "true";
    }
    
    @Override
    public int getBaseDamage() {
        return 160;
    }
    
    @Override
    public double getBaseIntelligence() {
        return 250.0;
    }
    
    @Override
    public String getDisplayName() {
        return "Bonzo's Staff";
    }
    
    @Override
    public Rarity getRarity() {
        return Rarity.RARE;
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
        return "Showtime";
    }
    
    @Override
    public String getAbilityDescription() {
        return Sputnik.trans("&7Shoots balloons that create a large explosion on impact, dealing up to &c1,000 &7damage.");
    }
    
    @Override
    public boolean isEnchanted() {
        return true;
    }
    
    @Override
    public void onAbilityUse(final Player player1, final SItem sItem) {
        player1.getWorld().playSound(player1.getLocation(), Sound.GHAST_MOAN, 1.0f, 2.0f);
        final Location location = player1.getLocation().add(0.0, -0.5, 0.0);
        final Vector vecTo = location.getDirection().normalize().multiply(1);
        final Location location2 = player1.getLocation();
        final Random random = new Random();
        final int i = random.nextInt(9);
        Color color = Color.RED;
        final ArmorStand stand = (ArmorStand)player1.getWorld().spawn(location.add(player1.getLocation().getDirection().multiply(1)), (Class)ArmorStand.class);
        stand.setVisible(false);
        stand.setGravity(false);
        stand.setMarker(true);
        stand.teleport(player1.getEyeLocation().add(vecTo));
        if (i == 1) {
            stand.setHelmet(SItem.of(SMaterial.BONZO_BALLOON_1).getStack());
            color = Color.RED;
        }
        if (i == 2) {
            stand.setHelmet(SItem.of(SMaterial.BONZO_BALLOON_2).getStack());
            color = Color.ORANGE;
        }
        if (i == 3) {
            stand.setHelmet(SItem.of(SMaterial.BONZO_BALLOON_3).getStack());
            color = Color.YELLOW;
        }
        if (i == 4) {
            stand.setHelmet(SItem.of(SMaterial.BONZO_BALLOON_4).getStack());
            color = Color.PURPLE;
        }
        if (i == 5) {
            stand.setHelmet(SItem.of(SMaterial.BONZO_BALLOON_5).getStack());
            color = Color.BLUE;
        }
        if (i == 6) {
            stand.setHelmet(SItem.of(SMaterial.BONZO_BALLOON_6).getStack());
            color = Color.AQUA;
        }
        if (i == 7) {
            stand.setHelmet(SItem.of(SMaterial.BONZO_BALLOON_7).getStack());
            color = Color.LIME;
        }
        if (i == 8) {
            stand.setHelmet(SItem.of(SMaterial.BONZO_BALLOON_8).getStack());
            color = Color.FUCHSIA;
        }
        if (i == 9) {
            stand.setHelmet(SItem.of(SMaterial.BONZO_BALLOON_9).getStack());
            color = Color.GREEN;
        }
        final Color color2 = color;
        final Vector direction = location2.getDirection();
        new BukkitRunnable() {
            double t = 0.0;
            int tick = 0;
            
            public void run() {
                this.t += 1.0995574287564276;
                ++this.tick;
                float yaw = location.getYaw() + 10.0f;
                if (yaw >= 180.0f) {
                    yaw *= -1.0f;
                }
                location.setYaw(yaw);
                final double x = direction.getX() * this.t;
                final double y = direction.getY() * this.t;
                final double z = direction.getZ() * this.t;
                location.add(x, y, z);
                stand.teleport(location);
                location.subtract(x, y, z);
                if (this.t >= 50.0) {
                    this.cancel();
                    stand.remove();
                }
                final Location locof = stand.getLocation();
                locof.setY(locof.getY() + 1.0);
                if (locof.getBlock().getType() != Material.AIR) {
                    stand.remove();
                    final FireworkEffect.Builder builder = FireworkEffect.builder();
                    final FireworkEffect effect = builder.flicker(false).trail(false).with(FireworkEffect.Type.BURST).withColor(color2).build();
                    SSU.spawn(stand.getLocation(), effect, new Player[0]);
                    this.cancel();
                }
                if (stand.getNearbyEntities(0.7, 1.0, 0.7).contains((Object)player1) && locof.getBlock().getType() != Material.AIR) {
                    final Vector vec = player1.getLocation().getDirection().normalize().multiply(2.5);
                    vec.setY(0.8);
                    player1.setVelocity(vec);
                }
                if (BonzoStaff.this.ACT == "false") {
                    return;
                }
                for (final Entity entity : stand.getWorld().getNearbyEntities(stand.getLocation(), 1.5, 1.0, 1.5)) {
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
                    final User user = User.getUser(player1.getUniqueId());
                    final double baseDamage = Sputnik.calculateMagicDamage(entity, player1, 1000, 0.2);
                    user.damageEntityIgnoreShield((Damageable)entity, (int)baseDamage);
                    stand.remove();
                    PlayerListener.spawnDamageInd(entity, baseDamage, false);
                    new BukkitRunnable() {
                        public void run() {
                            this.cancel();
                        }
                    }.runTaskLater((Plugin)SkyBlock.getPlugin(), 30L);
                    BonzoStaff.this.ACT = "false";
                    final FireworkEffect.Builder builder2 = FireworkEffect.builder();
                    final FireworkEffect effect2 = builder2.flicker(false).trail(false).with(FireworkEffect.Type.BURST).withColor(color2).build();
                    SSU.spawn(stand.getLocation(), effect2, new Player[0]);
                }
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 0L, 1L);
    }
    
    @Override
    public int getAbilityCooldownTicks() {
        return 4;
    }
    
    @Override
    public boolean displayCooldown() {
        return false;
    }
    
    @Override
    public int getManaCost() {
        return 100;
    }
}
