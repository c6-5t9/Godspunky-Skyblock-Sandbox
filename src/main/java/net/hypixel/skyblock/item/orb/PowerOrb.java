package net.hypixel.skyblock.item.orb;

import java.util.HashMap;
import org.bukkit.Effect;
import org.bukkit.World;
import java.util.Iterator;
import org.bukkit.entity.Entity;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.metadata.FixedMetadataValue;
import net.hypixel.skyblock.SkyBlock;
import org.bukkit.util.Vector;
import net.hypixel.skyblock.item.SMaterial;
import net.hypixel.skyblock.util.SUtil;
import java.util.concurrent.atomic.AtomicInteger;
import net.hypixel.skyblock.entity.SEntity;
import net.hypixel.skyblock.entity.SEntityType;
import org.bukkit.ChatColor;
import net.hypixel.skyblock.item.SItem;
import org.bukkit.entity.Player;
import net.hypixel.skyblock.item.GenericItemType;
import org.bukkit.entity.ArmorStand;
import java.util.UUID;
import java.util.Map;
import net.hypixel.skyblock.item.Ability;
import net.hypixel.skyblock.item.MaterialFunction;
import net.hypixel.skyblock.item.SkullStatistics;

public abstract class PowerOrb implements SkullStatistics, MaterialFunction, Ability, OrbBuff
{
    private static final Map<UUID, ArmorStand> USING_POWER_ORB_MAP;
    private static final Map<UUID, PowerOrbInstance> POWER_ORB_MAP;
    
    @Override
    public String getAbilityName() {
        return "Deploy";
    }
    
    @Override
    public int getAbilityCooldownTicks() {
        return 5;
    }
    
    @Override
    public boolean displayCooldown() {
        return false;
    }
    
    @Override
    public boolean displayUsage() {
        return false;
    }
    
    @Override
    public int getManaCost() {
        return -2;
    }
    
    @Override
    public GenericItemType getType() {
        return GenericItemType.ITEM;
    }
    
    @Override
    public void onAbilityUse(final Player player, final SItem sItem) {
        final Location sloc = player.getLocation().clone().add(player.getLocation().getDirection().multiply(1.5));
        this.destroyArmorStandWithUUID(player.getUniqueId(), player.getWorld());
        this.r(player, sloc);
        if (PowerOrb.POWER_ORB_MAP.containsKey((Object)player.getUniqueId())) {
            final PowerOrbInstance instance = (PowerOrbInstance)PowerOrb.POWER_ORB_MAP.get((Object)player.getUniqueId());
            final ArmorStand s = instance.getArmorStand();
            s.remove();
            player.sendMessage((Object)ChatColor.YELLOW + "Your previous " + instance.getColoredName() + (Object)ChatColor.YELLOW + " was removed!");
        }
        final SEntity sEntity = new SEntity(sloc, SEntityType.VELOCITY_ARMOR_STAND, new Object[0]);
        final ArmorStand stand = (ArmorStand)sEntity.getEntity();
        PowerOrb.POWER_ORB_MAP.put((Object)player.getUniqueId(), (Object)new PowerOrbInstance() {
            @Override
            public String getColoredName() {
                return (Object)sItem.getRarity().getColor() + sItem.getType().getDisplayName(sItem.getVariant());
            }
            
            @Override
            public ArmorStand getArmorStand() {
                return stand;
            }
        });
        stand.setVisible(false);
        final AtomicInteger seconds = new AtomicInteger((int)(this.getOrbLifeTicks() / 20L));
        stand.setCustomName((Object)sItem.getRarity().getColor() + ((this.getCustomOrbName() == null) ? this.getBuffName() : this.getCustomOrbName()) + " " + (Object)ChatColor.YELLOW + seconds.get() + "s");
        stand.setCustomNameVisible(true);
        stand.setHelmet(SUtil.getSkull(this.getURL(), null));
        stand.setVelocity(new Vector(0.0, 0.1, 0.0));
        stand.setMetadata(player.getUniqueId().toString() + "_powerorb", (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)true));
        new BukkitRunnable() {
            public void run() {
                if (stand.isDead()) {
                    this.cancel();
                    return;
                }
                final Vector velClone = stand.getVelocity().clone();
                stand.setVelocity(new Vector(0.0, (velClone.getY() < 0.0) ? 0.1 : -0.1, 0.0));
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 25L, 25L);
        new BukkitRunnable() {
            public void run() {
                if (stand.isDead()) {
                    this.cancel();
                    return;
                }
                final Location location = stand.getLocation();
                location.setYaw(stand.getLocation().getYaw() + 15.0f);
                stand.teleport(location);
                PowerOrb.this.playEffect(stand.getEyeLocation().clone().add(stand.getLocation().getDirection().divide(new Vector(2, 2, 2))));
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 0L, 1L);
        new BukkitRunnable() {
            public void run() {
                if (stand.isDead()) {
                    this.cancel();
                    return;
                }
                int c = 0;
                for (final Entity entity : stand.getNearbyEntities(18.0, 18.0, 18.0)) {
                    if (!(entity instanceof Player)) {
                        continue;
                    }
                    final Player p = (Player)entity;
                    if (c >= 5) {
                        break;
                    }
                    ++c;
                    if (PowerOrb.USING_POWER_ORB_MAP.containsKey((Object)p.getUniqueId()) && !PowerOrb.USING_POWER_ORB_MAP.get((Object)p.getUniqueId()).equals(stand)) {
                        continue;
                    }
                    PowerOrb.USING_POWER_ORB_MAP.put((Object)p.getUniqueId(), (Object)stand);
                    new BukkitRunnable() {
                        public void run() {
                            PowerOrb.USING_POWER_ORB_MAP.remove((Object)p.getUniqueId());
                        }
                    }.runTaskLater((Plugin)SkyBlock.getPlugin(), 20L);
                    PowerOrb.this.buff(p);
                    for (int i = 0; i < 8; ++i) {
                        PowerOrb.this.playEffect(p.getLocation().add(SUtil.random(-0.5, 0.5), 0.1, SUtil.random(-0.5, 0.5)));
                    }
                }
                stand.setCustomName((Object)sItem.getRarity().getColor() + ((PowerOrb.this.getCustomOrbName() == null) ? PowerOrb.this.getBuffName() : PowerOrb.this.getCustomOrbName()) + " " + (Object)ChatColor.YELLOW + Math.max(0, seconds.decrementAndGet()) + "s");
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 20L, 20L);
        new BukkitRunnable() {
            public void run() {
                PowerOrb.POWER_ORB_MAP.remove((Object)player.getUniqueId());
                stand.remove();
            }
        }.runTaskLater((Plugin)SkyBlock.getPlugin(), this.getOrbLifeTicks() + 15L);
    }
    
    protected abstract void buff(final Player p0);
    
    protected abstract long getOrbLifeTicks();
    
    protected abstract void playEffect(final Location p0);
    
    public void destroyArmorStandWithUUID(final UUID uuid, final World w) {
        final String uuidString = uuid.toString() + "_powerorb";
        for (final Entity e : w.getEntities()) {
            if (e.hasMetadata(uuidString)) {
                e.remove();
            }
        }
    }
    
    public void r(final Player player, final Location loc1) {
        final Location loc2 = loc1.clone().add(0.0, 1.0, 0.0);
        player.playEffect(loc2, Effect.POTION_SWIRL, 0);
        player.playEffect(loc2, Effect.POTION_SWIRL, 0);
        player.playEffect(loc2, Effect.POTION_SWIRL, 0);
        player.playEffect(loc2, Effect.POTION_SWIRL, 0);
        player.playEffect(loc2, Effect.POTION_SWIRL, 0);
        player.playEffect(loc2, Effect.POTION_SWIRL, 0);
        player.playEffect(loc2, Effect.POTION_SWIRL, 0);
    }
    
    static {
        USING_POWER_ORB_MAP = (Map)new HashMap();
        POWER_ORB_MAP = (Map)new HashMap();
    }
    
    private interface PowerOrbInstance
    {
        String getColoredName();
        
        ArmorStand getArmorStand();
    }
}
