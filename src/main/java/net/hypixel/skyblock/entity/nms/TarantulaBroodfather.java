package net.hypixel.skyblock.entity.nms;

import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import net.hypixel.skyblock.features.enchantment.EnchantmentType;
import net.hypixel.skyblock.entity.EntityDropType;
import net.hypixel.skyblock.item.SItem;
import net.hypixel.skyblock.item.SMaterial;
import java.util.ArrayList;
import net.hypixel.skyblock.entity.EntityDrop;
import java.util.List;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.Location;
import com.google.common.util.concurrent.AtomicDouble;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.util.Vector;
import org.bukkit.Material;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.entity.ArmorStand;
import net.hypixel.skyblock.entity.SEntityType;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.metadata.FixedMetadataValue;
import net.hypixel.skyblock.SkyBlock;
import net.minecraft.server.v1_8_R3.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.CaveSpider;
import org.bukkit.ChatColor;
import net.hypixel.skyblock.util.Sputnik;
import org.bukkit.entity.LivingEntity;
import net.hypixel.skyblock.user.User;
import net.hypixel.skyblock.util.SUtil;
import org.bukkit.entity.Spider;
import net.minecraft.server.v1_8_R3.World;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import java.util.UUID;
import net.hypixel.skyblock.entity.SEntity;
import net.hypixel.skyblock.entity.EntityStatistics;
import net.hypixel.skyblock.entity.EntityFunction;
import net.minecraft.server.v1_8_R3.EntitySpider;

public class TarantulaBroodfather extends EntitySpider implements SNMSEntity, EntityFunction, EntityStatistics, SlayerBoss
{
    private static final TieredValue<Double> MAX_HEALTH_VALUES;
    private static final TieredValue<Double> DAMAGE_VALUES;
    private final int tier;
    private final long end;
    private SEntity hologram;
    private SEntity top;
    private SEntity hologram_name;
    private final UUID spawnerUUID;
    
    public TarantulaBroodfather(final Integer tier, final UUID spawnerUUID) {
        super((World)((CraftWorld)Bukkit.getPlayer(spawnerUUID).getWorld()).getHandle());
        this.tier = tier;
        this.end = System.currentTimeMillis() + 180000L;
        this.spawnerUUID = spawnerUUID;
    }
    
    public TarantulaBroodfather(final World world) {
        super(world);
        this.tier = 1;
        this.end = System.currentTimeMillis() + 180000L;
        this.spawnerUUID = UUID.randomUUID();
    }
    
    public void t_() {
        super.t_();
        final Player player = Bukkit.getPlayer(this.spawnerUUID);
        if (player == null) {
            return;
        }
        if (((Spider)this.bukkitEntity).getWorld() == player.getWorld() && this.top.getEntity().getWorld() == player.getWorld() && this.getBukkitEntity().getLocation().distance(player.getLocation()) >= 40.0 && SUtil.random(0, 10) == 0) {
            this.getBukkitEntity().teleport(player.getLocation());
        }
        if (System.currentTimeMillis() > this.end) {
            User.getUser(player.getUniqueId()).failSlayerQuest();
            ((Spider)this.bukkitEntity).remove();
            this.top.getEntity().remove();
            this.hologram.remove();
            return;
        }
        final Entity entity = this.getBukkitEntity().getHandle();
        final double height = entity.getBoundingBox().e - entity.getBoundingBox().b;
        this.hologram_name.getEntity().teleport(this.getBukkitEntity().getLocation().clone().add(0.0, height, 0.0));
        this.hologram_name.getEntity().setCustomName(Sputnik.trans(Sputnik.entityNameTag((LivingEntity)this.getBukkitEntity(), Sputnik.buildcustomString(this.getEntityName(), 0, true))));
        this.hologram.getEntity().teleport(this.getBukkitEntity().getLocation().clone().add(0.0, 1.3, 0.0));
        this.hologram.getEntity().setCustomName((Object)ChatColor.RED + SUtil.getFormattedTime(this.end - System.currentTimeMillis(), 1000));
        ((Spider)this.bukkitEntity).setTarget((LivingEntity)player);
        ((CaveSpider)this.top.getEntity()).setTarget((LivingEntity)player);
    }
    
    public void onSpawn(final LivingEntity entity, final SEntity sEntity) {
        entity.setMetadata("BOSS_OWNER_" + Bukkit.getPlayer(this.getSpawnerUUID()).getUniqueId().toString(), (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)true));
        entity.setMetadata("SlayerBoss", (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)true));
        this.top = new SEntity((org.bukkit.entity.Entity)entity, SEntityType.TOP_CAVE_SPIDER, new Object[] { this });
        this.hologram = new SEntity(entity.getLocation().add(0.0, 1.3, 0.0), SEntityType.UNCOLLIDABLE_ARMOR_STAND, new Object[0]);
        ((ArmorStand)this.hologram.getEntity()).setVisible(false);
        ((ArmorStand)this.hologram.getEntity()).setGravity(false);
        this.hologram.getEntity().setCustomNameVisible(true);
        final Entity e = this.getBukkitEntity().getHandle();
        final double height = e.getBoundingBox().e - e.getBoundingBox().b;
        entity.setMetadata("notDisplay", (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)true));
        this.hologram_name = new SEntity(entity.getLocation().add(0.0, height, 0.0), SEntityType.UNCOLLIDABLE_ARMOR_STAND, new Object[0]);
        ((ArmorStand)this.hologram_name.getEntity()).setVisible(false);
        ((ArmorStand)this.hologram_name.getEntity()).setGravity(false);
        this.hologram_name.getEntity().setCustomNameVisible(true);
        new BukkitRunnable() {
            public void run() {
                if (entity.isDead()) {
                    SUtil.delay(() -> TarantulaBroodfather.this.hologram_name.remove(), 20L);
                    TarantulaBroodfather.this.hologram.remove();
                    this.cancel();
                }
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 0L, 1L);
        final Player player = Bukkit.getPlayer(this.spawnerUUID);
        if (player == null) {
            return;
        }
        new BukkitRunnable() {
            public void run() {
                final org.bukkit.entity.Entity e = (org.bukkit.entity.Entity)TarantulaBroodfather.this.getBukkitEntity();
                if (e.isDead()) {
                    this.cancel();
                    return;
                }
                entity.setPassenger((org.bukkit.entity.Entity)TarantulaBroodfather.this.top.getEntity());
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 5L, 5L);
        if (this.tier >= 2) {
            new BukkitRunnable() {
                public void run() {
                    if (entity.isDead()) {
                        this.cancel();
                        return;
                    }
                    if (player.getLocation().distance(TarantulaBroodfather.this.bukkitEntity.getLocation()) > 5.0) {
                        return;
                    }
                    player.damage(TarantulaBroodfather.this.getDamageDealt() * 0.5, (org.bukkit.entity.Entity)entity);
                }
            }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 20L, 20L);
        }
        new BukkitRunnable() {
            public void run() {
                final org.bukkit.entity.Entity e = (org.bukkit.entity.Entity)TarantulaBroodfather.this.getBukkitEntity();
                if (e.isDead()) {
                    this.cancel();
                    return;
                }
                if (e.getLocation().clone().distance(player.getLocation().clone()) < 5.0) {
                    return;
                }
                if (e.getLocation().clone().subtract(0.0, 1.0, 0.0).getBlock().getType() == Material.AIR) {
                    return;
                }
                final Vector vector = e.getLocation().clone().toVector().subtract(player.getLocation().clone().toVector()).multiply(-1.0).multiply(new Vector(0.1, 0.2, 0.1));
                vector.setY(Math.abs(vector.getY()));
                if (vector.getY() < 0.8) {
                    vector.setY(1.5);
                }
                if (vector.getY() > 5.0) {
                    vector.setY(5.0);
                }
                e.setVelocity(e.getVelocity().add(vector));
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 40L, 40L);
    }
    
    public void onDeath(final SEntity sEntity, final org.bukkit.entity.Entity killed, final org.bukkit.entity.Entity damager) {
        SUtil.delay(() -> this.hologram_name.remove(), 20L);
        this.hologram.remove();
        this.top.remove();
        final User user = User.getUser(damager.getUniqueId());
        user.addCoins(50000L);
        user.send((Object)ChatColor.GOLD + "+50000 Coins");
    }
    
    public void onDamage(final SEntity sEntity, final org.bukkit.entity.Entity damager, final EntityDamageByEntityEvent e, final AtomicDouble damage) {
        this.top.getEntity().damage(damage.get());
    }
    
    public String getEntityName() {
        return (Object)ChatColor.DARK_PURPLE + "\u2620 " + (Object)ChatColor.DARK_RED + "Tarantula Broodfather";
    }
    
    public double getEntityMaxHealth() {
        return TarantulaBroodfather.MAX_HEALTH_VALUES.getByNumber(this.tier);
    }
    
    public double getDamageDealt() {
        return TarantulaBroodfather.DAMAGE_VALUES.getByNumber(this.tier);
    }
    
    public double getMovementSpeed() {
        return 0.55;
    }
    
    public LivingEntity spawn(final Location location) {
        this.world = (World)((CraftWorld)location.getWorld()).getHandle();
        this.setPosition(location.getX(), location.getY(), location.getZ());
        this.world.addEntity((Entity)this, CreatureSpawnEvent.SpawnReason.CUSTOM);
        return (LivingEntity)this.getBukkitEntity();
    }
    
    public List<EntityDrop> drops() {
        final List<EntityDrop> drops = (List<EntityDrop>)new ArrayList();
        int web = SUtil.random(1, 3);
        if (this.tier == 2) {
            web = SUtil.random(9, 18);
        }
        if (this.tier == 3) {
            web = SUtil.random(28, 48);
        }
        if (this.tier == 4) {
            web = SUtil.random(52, 64);
        }
        drops.add((Object)new EntityDrop(SUtil.setStackAmount(SItem.of(SMaterial.TARANTULA_WEB).getStack(), web), EntityDropType.GUARANTEED, 1.0));
        if (this.tier >= 2) {
            int toxicArrowPoison = 16;
            if (this.tier == 3) {
                toxicArrowPoison = SUtil.random(24, 30);
            }
            if (this.tier == 4) {
                toxicArrowPoison = SUtil.random(60, 64);
            }
            drops.add((Object)new EntityDrop(SUtil.setStackAmount(SItem.of(SMaterial.TOXIC_ARROW_POISON).getStack(), toxicArrowPoison), EntityDropType.OCCASIONAL, 0.2));
            drops.add((Object)new EntityDrop(SMaterial.BITE_RUNE, EntityDropType.RARE, 0.05));
        }
        if (this.tier >= 3) {
            final SItem arthoBook = SItem.of(SMaterial.ENCHANTED_BOOK);
            arthoBook.addEnchantment(EnchantmentType.BANE_OF_ARTHROPODS, 6);
            drops.add((Object)new EntityDrop(SMaterial.SPIDER_CATALYST, EntityDropType.EXTRAORDINARILY_RARE, 0.01));
            drops.add((Object)new EntityDrop(arthoBook.getStack(), EntityDropType.EXTRAORDINARILY_RARE, 0.01));
            drops.add((Object)new EntityDrop(SMaterial.FLY_SWATTER, EntityDropType.CRAZY_RARE, 0.002));
            drops.add((Object)new EntityDrop(SMaterial.TARANTULA_TALISMAN, EntityDropType.CRAZY_RARE, 0.002));
        }
        if (this.tier >= 4) {
            final SItem fsBook = SItem.of(SMaterial.ENCHANTED_BOOK);
            fsBook.addEnchantment(EnchantmentType.FIRST_STRIKE, 7);
            drops.add((Object)new EntityDrop(fsBook.getStack(), EntityDropType.INSANE_RARE, 0.002));
            drops.add((Object)new EntityDrop(SMaterial.DIGESTED_MOSQUITO, EntityDropType.CRAZY_RARE, 0.0054));
        }
        return drops;
    }
    
    public double getXPDropped() {
        return 0.0;
    }
    
    public UUID getSpawnerUUID() {
        return this.spawnerUUID;
    }
    
    public int getTier() {
        return this.tier;
    }
    
    static {
        MAX_HEALTH_VALUES = new TieredValue<Double>(750.0, 30000.0, 900000.0, 2400000.0);
        DAMAGE_VALUES = new TieredValue<Double>(35.0, 110.0, 525.0, 1325.0);
    }
    
    public static class TopCaveSpider implements EntityStatistics, EntityFunction
    {
        private final TarantulaBroodfather parent;
        
        public TopCaveSpider(final TarantulaBroodfather parent) {
            this.parent = parent;
        }
        
        @Override
        public String getEntityName() {
            return "";
        }
        
        @Override
        public double getEntityMaxHealth() {
            return 9.99999999E8;
        }
        
        @Override
        public double getDamageDealt() {
            return 0.0;
        }
        
        @Override
        public double getXPDropped() {
            return 0.0;
        }
        
        @Override
        public boolean hasNameTag() {
            return false;
        }
        
        @Override
        public void onSpawn(final LivingEntity entity, final SEntity sEntity) {
            entity.setCustomNameVisible(false);
            entity.setMetadata("SlayerBoss", (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)true));
            entity.setMetadata("notDisplay", (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)true));
        }
        
        @Override
        public boolean tick(final LivingEntity entity) {
            return true;
        }
        
        @Override
        public void onDamage(final SEntity sEntity, final org.bukkit.entity.Entity damager, final EntityDamageByEntityEvent e, final AtomicDouble damage) {
            e.setCancelled(true);
            final Spider taran = (Spider)this.parent.getBukkitEntity();
            taran.damage(0.0, damager);
        }
    }
}
