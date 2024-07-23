package net.hypixel.skyblock.entity.nms;

import org.bukkit.Effect;
import net.hypixel.skyblock.features.enchantment.EnchantmentType;
import net.hypixel.skyblock.entity.EntityDropType;
import java.util.ArrayList;
import net.hypixel.skyblock.entity.EntityDrop;
import java.util.List;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.Location;
import org.bukkit.inventory.meta.ItemMeta;
import net.hypixel.skyblock.entity.SEntityEquipment;
import org.bukkit.entity.ArmorStand;
import net.hypixel.skyblock.entity.SEntityType;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.metadata.FixedMetadataValue;
import net.minecraft.server.v1_8_R3.Entity;
import org.bukkit.entity.Player;
import net.hypixel.skyblock.item.SItem;
import net.hypixel.skyblock.item.SMaterial;
import org.bukkit.Color;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;
import org.bukkit.plugin.Plugin;
import net.hypixel.skyblock.SkyBlock;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.bukkit.Sound;
import net.minecraft.server.v1_8_R3.GenericAttributes;
import org.bukkit.ChatColor;
import net.hypixel.skyblock.util.Sputnik;
import net.hypixel.skyblock.user.User;
import org.bukkit.entity.LivingEntity;
import net.hypixel.skyblock.util.SUtil;
import org.bukkit.entity.Skeleton;
import net.minecraft.server.v1_8_R3.World;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import java.util.UUID;
import net.hypixel.skyblock.entity.SEntity;
import net.hypixel.skyblock.entity.SkeletonStatistics;
import net.hypixel.skyblock.entity.EntityFunction;
import net.minecraft.server.v1_8_R3.EntitySkeleton;

public class CrimsonSathanas extends EntitySkeleton implements SNMSEntity, EntityFunction, SkeletonStatistics, SlayerBoss
{
    private static final TieredValue<Double> MAX_HEALTH_VALUES;
    private static final TieredValue<Double> DAMAGE_VALUES;
    private static final TieredValue<Double> SPEED_VALUES;
    private final int tier;
    private boolean enraged;
    private boolean raged;
    private boolean Cooldown;
    private boolean cooldownHellLaser;
    private final long end;
    private SEntity hologram;
    private SEntity hologram_name;
    private final UUID spawnerUUID;
    
    public CrimsonSathanas(final Integer tier, final UUID spawnerUUID) {
        super((World)((CraftWorld)Bukkit.getPlayer(spawnerUUID).getWorld()).getHandle());
        this.tier = tier;
        this.enraged = false;
        this.end = System.currentTimeMillis() + 180000L;
        this.spawnerUUID = spawnerUUID;
        this.Cooldown = true;
    }
    
    public CrimsonSathanas(final World world) {
        super(world);
        this.tier = 1;
        this.enraged = false;
        this.end = System.currentTimeMillis() + 180000L;
        this.spawnerUUID = UUID.randomUUID();
        this.Cooldown = true;
    }
    
    public void t_() {
        super.t_();
        final Player player = Bukkit.getPlayer(this.spawnerUUID);
        if (null == player) {
            return;
        }
        if (((Skeleton)this.bukkitEntity).getWorld() == player.getWorld() && 20.0 <= this.getBukkitEntity().getLocation().distance(player.getLocation()) && 0 == SUtil.random(0, 10)) {
            this.getBukkitEntity().teleport(player.getLocation());
        }
        final LivingEntity e = (LivingEntity)this.getBukkitEntity();
        if (System.currentTimeMillis() > this.end) {
            User.getUser(player.getUniqueId()).failSlayerQuest();
            ((Skeleton)this.bukkitEntity).remove();
            this.hologram.remove();
            return;
        }
        final Entity entity = this.getBukkitEntity().getHandle();
        final double height = entity.getBoundingBox().e - entity.getBoundingBox().b;
        this.hologram_name.getEntity().teleport(this.getBukkitEntity().getLocation().clone().add(0.0, height, 0.0));
        this.hologram_name.getEntity().setCustomName(Sputnik.trans(Sputnik.entityNameTag((LivingEntity)this.getBukkitEntity(), Sputnik.buildcustomString(this.getEntityName(), 0, true))));
        this.hologram.getEntity().teleport(this.getBukkitEntity().getLocation().clone().add(0.0, 2.3, 0.0));
        if (!this.raged) {
            this.hologram.getEntity().setCustomName((Object)ChatColor.RED + SUtil.getFormattedTime(this.end - System.currentTimeMillis(), 1000));
        }
        else {
            this.hologram.getEntity().setCustomName((Object)ChatColor.DARK_RED + "ENRAGED " + (Object)ChatColor.RED + SUtil.getFormattedTime(this.end - System.currentTimeMillis(), 1000));
        }
        ((Skeleton)this.bukkitEntity).setTarget((LivingEntity)player);
        if (3 <= this.tier && !this.enraged && 0 == SUtil.random(0, 20) && !this.Cooldown) {
            this.enraged = true;
            this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(this.getMovementSpeed());
            this.hologram.getEntity().setCustomName((Object)ChatColor.DARK_RED + "" + (Object)ChatColor.RED + SUtil.getFormattedTime(this.end - System.currentTimeMillis(), 1000));
            player.playSound(player.getLocation(), Sound.ZOMBIE_WOODBREAK, 1.0f, 1.0f);
            player.setVelocity(new Vector(SUtil.random(-1.0, 1.0), SUtil.random(0.0, 0.5), SUtil.random(-1.0, 1.0)));
            new BukkitRunnable() {
                public void run() {
                    CrimsonSathanas.this.enraged = false;
                    CrimsonSathanas.this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(CrimsonSathanas.this.getMovementSpeed());
                    CrimsonSathanas.this.hologram.getEntity().setCustomName((Object)ChatColor.RED + SUtil.getFormattedTime(CrimsonSathanas.this.end - System.currentTimeMillis(), 1000));
                }
            }.runTaskLater((Plugin)SkyBlock.getPlugin(), 200L);
        }
        if (3 <= this.tier && !this.raged && 0 == SUtil.random(0, 200) && !this.Cooldown) {
            this.raged = true;
            this.Cooldown = true;
            e.getEquipment().setChestplate(SUtil.enchant(SUtil.applyColorToLeatherArmor(new ItemStack(Material.LEATHER_CHESTPLATE), Color.fromRGB(12451840))));
            e.getEquipment().setHelmet(SItem.of(SMaterial.REV_HORROR_2).getStack());
            this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(this.getMovementSpeed() + 0.02);
            this.hologram.getEntity().setCustomName((Object)ChatColor.DARK_RED + "ENRAGED " + (Object)ChatColor.RED + SUtil.getFormattedTime(this.end - System.currentTimeMillis(), 1000));
            new BukkitRunnable() {
                public void run() {
                    e.getEquipment().setChestplate(SUtil.enchant(new ItemStack(Material.DIAMOND_CHESTPLATE)));
                    e.getEquipment().setHelmet(SItem.of(SMaterial.REVENANT_HORROR_HEAD).getStack());
                    CrimsonSathanas.this.raged = false;
                    CrimsonSathanas.this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(CrimsonSathanas.this.getMovementSpeed());
                    CrimsonSathanas.this.hologram.getEntity().setCustomName((Object)ChatColor.RED + SUtil.getFormattedTime(CrimsonSathanas.this.end - System.currentTimeMillis(), 1000));
                    SUtil.delay(() -> CrimsonSathanas.this.Cooldown = false, 550L);
                }
            }.runTaskLater((Plugin)SkyBlock.getPlugin(), 250L);
        }
    }
    
    public void onSpawn(final LivingEntity entity, final SEntity sEntity) {
        ((Skeleton)entity).setSkeletonType(Skeleton.SkeletonType.WITHER);
        SUtil.delay(() -> this.Cooldown = false, 400L);
        entity.setMetadata("BOSS_OWNER_" + Bukkit.getPlayer(this.getSpawnerUUID()).getUniqueId().toString(), (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)true));
        entity.setMetadata("SlayerBoss", (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)true));
        this.hologram = new SEntity(entity.getLocation().add(0.0, 2.3, 0.0), SEntityType.UNCOLLIDABLE_ARMOR_STAND, new Object[0]);
        ((ArmorStand)this.hologram.getEntity()).setVisible(false);
        ((ArmorStand)this.hologram.getEntity()).setGravity(false);
        this.hologram.getEntity().setCustomNameVisible(true);
        entity.setMetadata("notDisplay", (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)true));
        this.hologram_name = new SEntity(entity.getLocation().add(0.0, 2.0, 0.0), SEntityType.UNCOLLIDABLE_ARMOR_STAND, new Object[0]);
        ((ArmorStand)this.hologram_name.getEntity()).setVisible(false);
        final Entity e = this.getBukkitEntity().getHandle();
        final double height = e.getBoundingBox().e - e.getBoundingBox().b;
        ((ArmorStand)this.hologram_name.getEntity()).setGravity(false);
        this.hologram_name.getEntity().setCustomNameVisible(true);
        new BukkitRunnable() {
            public void run() {
                if (entity.isDead()) {
                    this.cancel();
                    return;
                }
                final Player player = Bukkit.getPlayer(CrimsonSathanas.this.spawnerUUID);
                if (null == player) {
                    return;
                }
                player.damage(CrimsonSathanas.this.getDamageDealt() * 0.5, (org.bukkit.entity.Entity)entity);
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 60L, 60L);
        if (2 <= this.tier) {
            new BukkitRunnable() {
                public void run() {
                    if (entity.isDead()) {
                        this.cancel();
                        return;
                    }
                    final Player player = Bukkit.getPlayer(CrimsonSathanas.this.spawnerUUID);
                    if (null == player) {
                        return;
                    }
                    player.damage(CrimsonSathanas.this.getDamageDealt(), (org.bukkit.entity.Entity)entity);
                }
            }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 20L, 20L);
        }
        new BukkitRunnable() {
            public void run() {
                if (entity.isDead()) {
                    SUtil.delay(() -> CrimsonSathanas.this.hologram_name.remove(), 20L);
                    CrimsonSathanas.this.hologram.remove();
                    this.cancel();
                }
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 0L, 1L);
    }
    
    public void onDeath(final SEntity sEntity, final org.bukkit.entity.Entity killed, final org.bukkit.entity.Entity damager) {
        this.hologram.remove();
        SUtil.delay(() -> this.hologram_name.remove(), 20L);
    }
    
    public String getEntityName() {
        return (Object)ChatColor.DARK_RED + "\u2620 " + (Object)ChatColor.RED + "Crimson Sathanas";
    }
    
    public double getEntityMaxHealth() {
        return CrimsonSathanas.MAX_HEALTH_VALUES.getByNumber(this.tier);
    }
    
    public double getDamageDealt() {
        return CrimsonSathanas.DAMAGE_VALUES.getByNumber(this.tier);
    }
    
    public double getMovementSpeed() {
        return CrimsonSathanas.SPEED_VALUES.getByNumber(this.tier);
    }
    
    public SEntityEquipment getEntityEquipment() {
        return new SEntityEquipment(new ItemStack(Material.DIAMOND_HOE), SUtil.getSkullURLStack("stack", "e7a39afc3a93652b6ea36925f81b45a4b8235f170cf2c541688f1c3fbbb594e6", 1, new String[0]), buildColorStack(0), new ItemStack(Material.CHAINMAIL_LEGGINGS), new ItemStack(Material.IRON_BOOTS));
    }
    
    public static ItemStack buildColorStack(final int hexcolor) {
        final ItemStack stack = SUtil.applyColorToLeatherArmor(new ItemStack(Material.LEATHER_HELMET), Color.fromRGB(hexcolor));
        final ItemMeta itemMeta = stack.getItemMeta();
        itemMeta.spigot().setUnbreakable(true);
        stack.setItemMeta(itemMeta);
        return stack;
    }
    
    public LivingEntity spawn(final Location location) {
        this.world = (World)((CraftWorld)location.getWorld()).getHandle();
        this.setPosition(location.getX(), location.getY(), location.getZ());
        this.world.addEntity((Entity)this, CreatureSpawnEvent.SpawnReason.CUSTOM);
        return (LivingEntity)this.getBukkitEntity();
    }
    
    public List<EntityDrop> drops() {
        final List<EntityDrop> drops = (List<EntityDrop>)new ArrayList();
        int revFlesh = SUtil.random(1, 3);
        if (2 == this.tier) {
            revFlesh = SUtil.random(9, 18);
        }
        if (3 == this.tier) {
            revFlesh = SUtil.random(30, 50);
        }
        if (4 == this.tier) {
            revFlesh = SUtil.random(50, 64);
        }
        drops.add((Object)new EntityDrop(SUtil.setStackAmount(SItem.of(SMaterial.REVENANT_FLESH).getStack(), revFlesh), EntityDropType.GUARANTEED, 1.0));
        if (2 <= this.tier) {
            int foulFlesh = 1;
            if (3 == this.tier) {
                foulFlesh = SUtil.random(1, 2);
            }
            if (4 == this.tier) {
                foulFlesh = SUtil.random(2, 3);
            }
            drops.add((Object)new EntityDrop(SUtil.setStackAmount(SItem.of(SMaterial.FOUL_FLESH).getStack(), foulFlesh), EntityDropType.OCCASIONAL, 0.2));
            drops.add((Object)new EntityDrop(SMaterial.PESTILENCE_RUNE, EntityDropType.RARE, 0.05));
            drops.add((Object)new EntityDrop(SMaterial.UNDEAD_CATALYST, EntityDropType.EXTRAORDINARILY_RARE, 0.01));
            drops.add((Object)new EntityDrop(SMaterial.REVENANT_CATALYST, EntityDropType.EXTRAORDINARILY_RARE, 0.01));
        }
        if (3 <= this.tier) {
            final SItem smiteBook = SItem.of(SMaterial.ENCHANTED_BOOK);
            smiteBook.addEnchantment(EnchantmentType.SMITE, 6);
            drops.add((Object)new EntityDrop(smiteBook.getStack(), EntityDropType.EXTRAORDINARILY_RARE, 0.01));
            drops.add((Object)new EntityDrop(SMaterial.BEHEADED_HORROR, EntityDropType.CRAZY_RARE, 0.005));
        }
        if (4 <= this.tier) {
            drops.add((Object)new EntityDrop(SMaterial.SNAKE_RUNE, EntityDropType.CRAZY_RARE, 0.005));
            drops.add((Object)new EntityDrop(SMaterial.SCYTHE_BLADE, EntityDropType.CRAZY_RARE, 5.384615384615384E-4));
        }
        return drops;
    }
    
    public void startLoop(final org.bukkit.entity.Entity e) {
        new BukkitRunnable() {
            float cout = e.getLocation().getYaw();
            final int i = 0;
            
            public void run() {
                if (e.isDead()) {
                    this.cancel();
                    return;
                }
                final Location loc = e.getLocation();
                loc.setYaw(this.cout);
                loc.setPitch(0.0f);
                loc.add(loc.getDirection().normalize().multiply(0.6));
                final int hitshield = 100;
                final int hitshieldmax = 100;
                final int stage = 3;
                e.getWorld().spigot().playEffect(loc, Effect.WITCH_MAGIC, 0, 1, 1.0f, 1.0f, 1.0f, 0.0f, 0, 64);
                e.getWorld().spigot().playEffect(loc.clone().add(0.0, 0.6, 0.0), Effect.MAGIC_CRIT, 0, 1, 1.0f, 1.0f, 1.0f, 0.0f, 0, 64);
                if (2 <= stage) {
                    e.getWorld().spigot().playEffect(loc.clone().add(0.0, 1.2, 0.0), Effect.MAGIC_CRIT, 0, 1, 1.0f, 1.0f, 1.0f, 0.0f, 0, 64);
                }
                if (3 == stage) {
                    e.getWorld().spigot().playEffect(loc.clone().add(0.0, 1.8, 0.0), Effect.MAGIC_CRIT, 0, 1, 1.0f, 1.0f, 1.0f, 0.0f, 0, 64);
                }
                this.cout += 18.0f;
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 0L, 1L);
    }
    
    public double getXPDropped() {
        return 0.0;
    }
    
    public boolean isBaby() {
        return false;
    }
    
    public UUID getSpawnerUUID() {
        return this.spawnerUUID;
    }
    
    public int getTier() {
        return this.tier;
    }
    
    static {
        MAX_HEALTH_VALUES = new TieredValue<Double>(6.0E7, 3.0E8, 6.66E8, 2.0E9);
        DAMAGE_VALUES = new TieredValue<Double>(120000.0, 1700000.0, 2000000.0, 2600000.0);
        SPEED_VALUES = new TieredValue<Double>(0.35, 0.4, 0.45, 0.55);
    }
}
