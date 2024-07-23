package net.hypixel.skyblock.entity.dragon;

import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.entity.Item;
import net.hypixel.skyblock.entity.SEntityType;
import java.util.Arrays;
import net.hypixel.skyblock.item.SItem;
import net.hypixel.skyblock.item.SMaterial;
import java.util.UUID;
import org.bukkit.entity.Arrow;
import java.util.List;
import java.util.Collections;
import java.util.Collection;
import net.hypixel.skyblock.entity.KillEnderCrystal;
import java.util.Map;
import java.util.HashMap;
import net.hypixel.skyblock.entity.StaticDragonManager;
import org.bukkit.Effect;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.entity.Fireball;
import org.bukkit.event.entity.EntityDamageEvent;
import net.hypixel.skyblock.user.User;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.Plugin;
import net.hypixel.skyblock.SkyBlock;
import java.util.ArrayList;
import org.bukkit.scheduler.BukkitRunnable;
import net.hypixel.skyblock.entity.SEntity;
import org.bukkit.Location;
import net.hypixel.skyblock.util.SUtil;
import org.bukkit.util.Vector;
import org.bukkit.entity.LivingEntity;
import java.util.Iterator;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;
import net.hypixel.skyblock.entity.dungeons.watcher.GlobalBossBar;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import net.minecraft.server.v1_8_R3.World;
import org.apache.commons.lang3.Range;
import net.hypixel.skyblock.entity.EntityStatistics;
import net.hypixel.skyblock.entity.EntityFunction;
import net.hypixel.skyblock.entity.nms.SNMSEntity;
import net.minecraft.server.v1_8_R3.EntityEnderDragon;

public abstract class Dragon extends EntityEnderDragon implements SNMSEntity, EntityFunction, EntityStatistics
{
    public static final long DEFAULT_ATTACK_COOLDOWN = 300L;
    public static final Range DEFAULT_DAMAGE_DEGREE_RANGE;
    public static final double DEFAULT_SPEED_MODIFIER = 1.4;
    private boolean frozen;
    private double yD;
    private double speedModifier;
    private Range<Double> damageDegree;
    private final long attackCooldown;
    
    protected Dragon(final World world, final double speedModifier, final Range<Double> damageDegree, final long attackCooldown) {
        super(world);
        this.frozen = false;
        this.yD = 1.0;
        this.speedModifier = speedModifier;
        this.damageDegree = damageDegree;
        this.attackCooldown = attackCooldown;
    }
    
    protected Dragon(final double speedModifier, final Range<Double> damageDegree, final long attackCooldown) {
        this((World)((CraftWorld)Bukkit.getWorlds().get(0)).getHandle(), speedModifier, damageDegree, attackCooldown);
    }
    
    public double getXPDropped() {
        return 0.0;
    }
    
    public GlobalBossBar setBar(final org.bukkit.World w) {
        final GlobalBossBar bb = new GlobalBossBar((Object)ChatColor.RED + this.getEntityName(), w);
        for (final Player p : w.getPlayers()) {
            bb.addPlayer(p);
        }
        return bb;
    }
    
    public void updateBar(final float percent, final GlobalBossBar bb) {
        bb.setProgress(percent);
    }
    
    public boolean tick(final LivingEntity entity) {
        this.target = null;
        if (this.frozen) {
            entity.setVelocity(new Vector(0, 0, 0));
            return true;
        }
        final Location location = entity.getLocation();
        if (location.getY() < SUtil.random(7.0, 13.0)) {
            this.yD = SUtil.random(0.6, 1.2);
        }
        if (location.getY() > SUtil.random(57.0, 63.0)) {
            this.yD = SUtil.random(-1.2, -0.6);
        }
        if (-718.0 > location.getX() || -623.0 < location.getX() || -328.0 > location.getZ() || -224.0 < location.getZ()) {
            final Vector vector = entity.getLocation().clone().subtract(new Vector(-670.5, 58.0, -275.5)).toVector();
            final Location center = location.clone();
            center.setDirection(vector);
            entity.teleport(center);
            entity.setVelocity(vector.clone().normalize().multiply(-1.0).multiply(3.0));
            return true;
        }
        entity.setVelocity(entity.getLocation().getDirection().clone().multiply(-1.0).multiply(this.speedModifier).setY(this.yD));
        return true;
    }
    
    public void onSpawn(final LivingEntity entity, final SEntity sEntity) {
        final GlobalBossBar bb = this.setBar(entity.getWorld());
        new BukkitRunnable() {
            public void run() {
                if (entity.isDead()) {
                    Dragon.this.updateBar(1.0E-4f, bb);
                    SUtil.delay(() -> {
                        final ArrayList<Player> plist = (ArrayList<Player>)new ArrayList();
                        for (final Player p : bb.players) {
                            plist.add((Object)p);
                        }
                        plist.forEach(pl -> bb.removePlayer(pl));
                        bb.setProgress(0.0);
                        bb.cancel();
                    }, 400L);
                    this.cancel();
                    return;
                }
                Dragon.this.updateBar((float)(entity.getHealth() / entity.getMaxHealth()), bb);
            }
        }.runTaskTimerAsynchronously((Plugin)SkyBlock.getPlugin(), 0L, 1L);
        new BukkitRunnable() {
            public void run() {
                if (entity.isDead()) {
                    this.cancel();
                    return;
                }
                switch (SUtil.random(0, 1)) {
                    case 0: {
                        Dragon.this.frozen = true;
                        for (int i = 1; 4 >= i; ++i) {
                            SUtil.lightningLater(entity.getLocation(), true, 10 * i);
                        }
                        new BukkitRunnable() {
                            public void run() {
                                if (entity.isDead()) {
                                    return;
                                }
                                for (final Entity e : entity.getNearbyEntities(200.0, 200.0, 200.0)) {
                                    e.getWorld().strikeLightningEffect(e.getLocation());
                                    final double r = SUtil.random((double)Dragon.this.damageDegree.getMinimum(), (double)Dragon.this.damageDegree.getMaximum());
                                    if (!(e instanceof LivingEntity)) {
                                        continue;
                                    }
                                    final LivingEntity le = (LivingEntity)e;
                                    final int damage = (int)(le.getMaxHealth() * r);
                                    if (le instanceof Player) {
                                        User.getUser(le.getUniqueId()).damage(damage, EntityDamageEvent.DamageCause.ENTITY_ATTACK, (Entity)entity);
                                    }
                                    else {
                                        le.damage((double)damage);
                                    }
                                    e.sendMessage((Object)ChatColor.DARK_PURPLE + "\u262c " + (Object)ChatColor.RED + Dragon.this.getEntityName() + (Object)ChatColor.LIGHT_PURPLE + " used " + (Object)ChatColor.YELLOW + "Lightning Strike" + (Object)ChatColor.LIGHT_PURPLE + " on you for " + (Object)ChatColor.RED + damage + " damage.");
                                }
                                Dragon.this.frozen = false;
                            }
                        }.runTaskLater((Plugin)SkyBlock.getPlugin(), 50L);
                        return;
                    }
                    case 1: {
                        Dragon.this.frozen = true;
                        Entity near = null;
                        for (final Entity n : entity.getNearbyEntities(50.0, 50.0, 50.0)) {
                            if (n instanceof Player) {
                                near = n;
                            }
                        }
                        final Entity finalNear;
                        if (null != (finalNear = near)) {
                            SUtil.runIntervalForTicks(() -> {
                                final Object val$entity = entity;
                                if (!entity.isDead()) {
                                    for (int j = 0; 15 > j; ++j) {
                                        entity.getWorld().spigot().playEffect(entity.getEyeLocation().subtract(0.0, 8.0, 0.0).add(entity.getLocation().getDirection().multiply(-8.0)).add(SUtil.random(-0.5, 0.5), SUtil.random(-0.5, 0.5), SUtil.random(-0.5, 0.5)), Effect.FLAME, 0, 1, 0.0f, 0.0f, 0.0f, 0.0f, 1, 50);
                                    }
                                }
                            }, 5L, 140L);
                            final float fn = SUtil.getLookAtYaw(near.getLocation().toVector());
                            new BukkitRunnable() {
                                public void run() {
                                    SUtil.runIntervalForTicks(() -> {
                                        final Object val$entity = entity;
                                        final Object val$fn = fn;
                                        if (!entity.isDead() && (int)fn != (int)entity.getLocation().getYaw()) {
                                            final Location location = entity.getLocation().clone();
                                            location.setYaw(entity.getLocation().clone().getYaw() + 1.0f);
                                            entity.teleport(location);
                                        }
                                    }, 1L, 120L);
                                }
                            }.runTaskLater((Plugin)SkyBlock.getPlugin(), 20L);
                            new BukkitRunnable() {
                                public void run() {
                                    SUtil.runIntervalForTicks(() -> {
                                        final Object val$entity = entity;
                                        final Object val$sEntity = sEntity;
                                        final Object val$finalNear = finalNear;
                                        if (!entity.isDead()) {
                                            final Fireball fireball = (Fireball)entity.getWorld().spawn(entity.getEyeLocation().subtract(0.0, 8.0, 0.0).add(entity.getLocation().getDirection().multiply(-10.0)), (Class)Fireball.class);
                                            fireball.setMetadata("dragon", (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)sEntity));
                                            fireball.setDirection(finalNear.getLocation().getDirection().multiply(-1.0).normalize());
                                        }
                                    }, 5L, 60L);
                                }
                            }.runTaskLater((Plugin)SkyBlock.getPlugin(), 80L);
                        }
                        new BukkitRunnable() {
                            public void run() {
                                Dragon.this.frozen = false;
                            }
                        }.runTaskLater((Plugin)SkyBlock.getPlugin(), 140L);
                        break;
                    }
                }
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 100L, this.attackCooldown);
    }
    
    public void onDeath(final SEntity sEntity, final Entity killed, final Entity damager) {
        final Map<UUID, List<Location>> eyes = (Map<UUID, List<Location>>)new HashMap((Map)StaticDragonManager.EYES);
        KillEnderCrystal.killEC(killed.getWorld());
        SUtil.delay(() -> StaticDragonManager.endFight(), 500L);
        final StringBuilder message = new StringBuilder();
        message.append((Object)ChatColor.GREEN).append((Object)ChatColor.BOLD).append("\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\n");
        message.append((Object)ChatColor.GOLD).append((Object)ChatColor.BOLD).append("                 ").append(sEntity.getStatistics().getEntityName().toUpperCase()).append(" DOWN!\n \n");
        final List<Map.Entry<UUID, Double>> damageDealt = (List<Map.Entry<UUID, Double>>)new ArrayList((Collection)sEntity.getDamageDealt().entrySet());
        damageDealt.sort(Map.Entry.comparingByValue());
        Collections.reverse((List)damageDealt);
        String name = null;
        if (damager instanceof Player) {
            name = damager.getName();
        }
        if (damager instanceof Arrow && ((Arrow)damager).getShooter() instanceof Player) {
            name = ((Player)((Arrow)damager).getShooter()).getName();
        }
        if (null != name) {
            message.append("            ").append((Object)ChatColor.GREEN).append(name).append((Object)ChatColor.GRAY).append(" dealt the final blow.\n \n");
        }
        for (int i = 0; i < Math.min(3, damageDealt.size()); ++i) {
            message.append("\n");
            final Map.Entry<UUID, Double> d = (Map.Entry<UUID, Double>)damageDealt.get(i);
            final int place = i + 1;
            switch (place) {
                case 1: {
                    message.append("        ").append((Object)ChatColor.YELLOW);
                    break;
                }
                case 2: {
                    message.append("        ").append((Object)ChatColor.GOLD);
                    break;
                }
                case 3: {
                    message.append("        ").append((Object)ChatColor.RED);
                    break;
                }
            }
            message.append((Object)ChatColor.BOLD).append(place);
            switch (place) {
                case 1: {
                    message.append("st");
                    break;
                }
                case 2: {
                    message.append("nd");
                    break;
                }
                case 3: {
                    message.append("rd");
                    break;
                }
            }
            message.append(" Damager").append((Object)ChatColor.RESET).append((Object)ChatColor.GRAY).append(" - ").append((Object)ChatColor.GREEN).append(Bukkit.getOfflinePlayer((UUID)d.getKey()).getName()).append((Object)ChatColor.GRAY).append(" - ").append((Object)ChatColor.YELLOW).append(SUtil.commaify(((Double)d.getValue()).intValue()));
        }
        message.append("\n \n").append("         ").append((Object)ChatColor.RESET).append((Object)ChatColor.YELLOW).append("Your Damage: ").append("%s").append((Object)ChatColor.RESET).append("\n").append("             ").append((Object)ChatColor.YELLOW).append("Runecrafting Experience: ").append((Object)ChatColor.LIGHT_PURPLE).append("N/A").append((Object)ChatColor.RESET).append("\n \n");
        message.append((Object)ChatColor.GREEN).append((Object)ChatColor.BOLD).append("\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac");
        for (final Player player : Bukkit.getOnlinePlayers()) {
            int place = -1;
            int damage = 0;
            for (int j = 0; j < damageDealt.size(); ++j) {
                final Map.Entry<UUID, Double> d2 = (Map.Entry<UUID, Double>)damageDealt.get(j);
                if (((UUID)d2.getKey()).equals((Object)player.getUniqueId())) {
                    place = j + 1;
                    damage = ((Double)d2.getValue()).intValue();
                }
            }
            if (player.getWorld().getName().equals((Object)"world")) {
                player.sendMessage(String.format(message.toString(), new Object[] { (-1 != place) ? ((Object)ChatColor.GREEN + SUtil.commaify(damage) + (Object)ChatColor.GRAY + " (Position #" + place + ")") : ((Object)ChatColor.RED + "N/A" + (Object)ChatColor.GRAY + " (Position #N/A)") }));
            }
        }
        new BukkitRunnable() {
            public void run() {
                for (int i = 0; i < damageDealt.size(); ++i) {
                    final Map.Entry<UUID, Double> d = (Map.Entry<UUID, Double>)damageDealt.get(i);
                    final Player player = Bukkit.getPlayer((UUID)d.getKey());
                    if (null != player) {
                        int weight = 0;
                        if (eyes.containsKey((Object)player.getUniqueId())) {
                            weight += Math.min(400, ((List)eyes.get((Object)player.getUniqueId())).size() * 100);
                        }
                        if (0 == i) {
                            weight += 300;
                        }
                        if (1 == i) {
                            weight += 250;
                        }
                        if (2 == i) {
                            weight += 200;
                        }
                        if (3 <= i && 6 >= i) {
                            weight += 125;
                        }
                        if (7 <= i && 14 >= i) {
                            weight += 100;
                        }
                        if (15 <= i) {
                            weight += 75;
                        }
                        final List<DragonDrop> possibleMajorDrops = (List<DragonDrop>)new ArrayList();
                        final SEntityType type = sEntity.getSpecType();
                        SUtil.addIf(new DragonDrop(SMaterial.ASPECT_OF_THE_DRAGONS, 450), possibleMajorDrops, 450 <= weight);
                        SUtil.addIf(new DragonDrop(SMaterial.ENDER_DRAGON_PET, 450), possibleMajorDrops, 450 <= weight);
                        SUtil.addIf(new DragonDrop(SMaterial.ENDER_DRAGON_PET2, 450), possibleMajorDrops, 450 <= weight);
                        SUtil.addIf(new DragonDrop(SMaterial.VagueEntityMaterial.HELMET, 400, type), possibleMajorDrops, 400 <= weight);
                        SUtil.addIf(new DragonDrop(SMaterial.VagueEntityMaterial.CHESTPLATE, 325, type), possibleMajorDrops, 325 <= weight);
                        SUtil.addIf(new DragonDrop(SMaterial.VagueEntityMaterial.LEGGINGS, 350, type), possibleMajorDrops, 350 <= weight);
                        SUtil.addIf(new DragonDrop(SMaterial.VagueEntityMaterial.BOOTS, 300, type), possibleMajorDrops, 300 <= weight);
                        int remainingWeight = weight;
                        if (0 < possibleMajorDrops.size()) {
                            final DragonDrop drop = (DragonDrop)possibleMajorDrops.get(SUtil.random(0, possibleMajorDrops.size() - 1));
                            final SMaterial majorDrop = drop.getMaterial();
                            remainingWeight -= drop.getWeight();
                            if (null != majorDrop) {
                                final SItem sItem = SItem.of(majorDrop);
                                if (!sItem.getFullName().equals((Object)"§6Ender Dragon") && !sItem.getFullName().equals((Object)"§5Ender Dragon")) {
                                    final Item item = SUtil.spawnPersonalItem(sItem.getStack(), killed.getLocation(), player);
                                    item.setMetadata("obtained", (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)true));
                                    item.setCustomNameVisible(true);
                                    item.setCustomName(item.getItemStack().getAmount() + "x " + sItem.getFullName());
                                }
                                else {
                                    final Item item = SUtil.spawnPersonalItem(sItem.getStack(), killed.getLocation(), player);
                                    item.setMetadata("obtained", (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)true));
                                    item.setCustomNameVisible(true);
                                    item.setCustomName(item.getItemStack().getAmount() + "x " + (Object)ChatColor.GRAY + "[Lvl 1] " + sItem.getFullName());
                                }
                            }
                        }
                        final List<DragonDrop> minorDrops = (List<DragonDrop>)new ArrayList((Collection)Arrays.asList((Object[])new DragonDrop[] { new DragonDrop(SMaterial.ENDER_PEARL, 0), new DragonDrop(SMaterial.ENCHANTED_ENDER_PEARL, 0) }));
                        SUtil.addIf(new DragonDrop(SMaterial.VagueEntityMaterial.FRAGMENT, 22, type), minorDrops, 22 <= weight);
                        int frags;
                        for (frags = 0; 22 <= remainingWeight; remainingWeight -= 22, ++frags) {}
                        for (final DragonDrop minorDrop : minorDrops) {
                            final SItem sItem2 = SItem.of(minorDrop.getMaterial());
                            if (null == minorDrop.getMaterial()) {
                                continue;
                            }
                            if (null != minorDrop.getVagueEntityMaterial() && 0 != frags) {
                                final Item item2 = SUtil.spawnPersonalItem(SUtil.setStackAmount(sItem2.getStack(), frags), killed.getLocation(), player);
                                item2.setCustomNameVisible(true);
                                item2.setCustomName(item2.getItemStack().getAmount() + "x " + sItem2.getFullName());
                            }
                            else {
                                final Item item2 = SUtil.spawnPersonalItem(SUtil.setStackAmount(sItem2.getStack(), SUtil.random(5, 10)), killed.getLocation(), player);
                                item2.setCustomNameVisible(true);
                                item2.setCustomName(item2.getItemStack().getAmount() + "x " + sItem2.getFullName());
                            }
                        }
                    }
                }
            }
        }.runTaskLater((Plugin)SkyBlock.getPlugin(), 200L);
    }
    
    public LivingEntity spawn(final Location location) {
        this.world = (World)((CraftWorld)location.getWorld()).getHandle();
        this.setPosition(location.getX(), location.getY(), location.getZ());
        this.world.addEntity((net.minecraft.server.v1_8_R3.Entity)this, CreatureSpawnEvent.SpawnReason.CUSTOM);
        return (LivingEntity)this.getBukkitEntity();
    }
    
    public void onAttack(final EntityDamageByEntityEvent e) {
        final int d = SUtil.random(354, 902);
        if (e.getEntity() instanceof Player) {
            User.getUser(e.getEntity().getUniqueId()).damage(d, e.getCause(), e.getDamager());
        }
        else if (e.getEntity() instanceof LivingEntity) {
            ((LivingEntity)e.getEntity()).damage((double)d);
        }
        e.getEntity().setVelocity(e.getEntity().getVelocity().multiply(8.0));
        e.getEntity().sendMessage((Object)ChatColor.DARK_PURPLE + "\u262c " + (Object)ChatColor.RED + this.getEntityName() + (Object)ChatColor.LIGHT_PURPLE + " used " + (Object)ChatColor.YELLOW + "Rush" + (Object)ChatColor.LIGHT_PURPLE + " on you for " + (Object)ChatColor.RED + d + " damage.");
    }
    
    public double getSpeedModifier() {
        return this.speedModifier;
    }
    
    public void setSpeedModifier(final double speedModifier) {
        this.speedModifier = speedModifier;
    }
    
    public Range<Double> getDamageDegree() {
        return this.damageDegree;
    }
    
    public void setDamageDegree(final Range<Double> damageDegree) {
        this.damageDegree = damageDegree;
    }
    
    public long getAttackCooldown() {
        return this.attackCooldown;
    }
    
    static {
        DEFAULT_DAMAGE_DEGREE_RANGE = Range.between((Comparable)0.3, (Comparable)0.7);
    }
    
    private static class DragonDrop
    {
        private final SMaterial material;
        private final SMaterial.VagueEntityMaterial vagueEntityMaterial;
        private final int weight;
        
        public DragonDrop(final SMaterial material, final int weight) {
            this.material = material;
            this.vagueEntityMaterial = null;
            this.weight = weight;
        }
        
        public DragonDrop(final SMaterial.VagueEntityMaterial material, final int weight, final SEntityType type) {
            this.material = material.getEntityArmorPiece(type);
            this.vagueEntityMaterial = material;
            this.weight = weight;
        }
        
        public SMaterial getMaterial() {
            return this.material;
        }
        
        public SMaterial.VagueEntityMaterial getVagueEntityMaterial() {
            return this.vagueEntityMaterial;
        }
        
        public int getWeight() {
            return this.weight;
        }
    }
}
