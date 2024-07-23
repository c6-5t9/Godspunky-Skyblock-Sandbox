package net.hypixel.skyblock.entity.dungeons.regularentity;

import org.bukkit.Sound;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.entity.WitherSkull;
import com.google.common.util.concurrent.AtomicDouble;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import net.hypixel.skyblock.entity.SEntityEquipment;
import net.minecraft.server.v1_8_R3.AttributeInstance;
import java.util.Iterator;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutAnimation;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import me.libraryaddict.disguise.disguisetypes.PlayerDisguise;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.metadata.FixedMetadataValue;
import net.hypixel.skyblock.SkyBlock;
import net.hypixel.skyblock.util.EntityManager;
import org.bukkit.entity.Entity;
import net.hypixel.skyblock.util.Sputnik;
import net.minecraft.server.v1_8_R3.GenericAttributes;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftZombie;
import net.hypixel.skyblock.entity.SEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.meta.ItemMeta;
import net.hypixel.skyblock.util.SUtil;
import org.bukkit.Color;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;
import net.hypixel.skyblock.entity.zombie.NPCMobs;
import net.hypixel.skyblock.entity.zombie.BaseZombie;

public class CryptDreadlord extends BaseZombie implements NPCMobs
{
    private boolean skullShoot;
    private boolean skullShootCD;
    
    public CryptDreadlord() {
        this.skullShoot = false;
        this.skullShootCD = true;
    }
    
    @Override
    public String getEntityName() {
        return "Crypt Dreadlord";
    }
    
    @Override
    public double getEntityMaxHealth() {
        return 1.0E8;
    }
    
    @Override
    public double getDamageDealt() {
        return 1500000.0;
    }
    
    public static ItemStack b(final int hexcolor, final Material m) {
        final ItemStack stack = SUtil.applyColorToLeatherArmor(new ItemStack(m), Color.fromRGB(hexcolor));
        final ItemMeta itemMeta = stack.getItemMeta();
        itemMeta.spigot().setUnbreakable(true);
        stack.setItemMeta(itemMeta);
        return stack;
    }
    
    @Override
    public void onSpawn(final LivingEntity entity, final SEntity sEntity) {
        SUtil.delay(() -> this.skullShootCD = false, 100L);
        ((CraftZombie)entity).setBaby(false);
        final AttributeInstance followRange = ((CraftLivingEntity)entity).getHandle().getAttributeInstance(GenericAttributes.FOLLOW_RANGE);
        followRange.setValue(40.0);
        final PlayerDisguise pl = Sputnik.applyPacketNPC((Entity)entity, "ewogICJ0aW1lc3RhbXAiIDogMTYyNjMyMjczMDkzMSwKICAicHJvZmlsZUlkIiA6ICIzZmM3ZmRmOTM5NjM0YzQxOTExOTliYTNmN2NjM2ZlZCIsCiAgInByb2ZpbGVOYW1lIiA6ICJZZWxlaGEiLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDA5M2Q2NTJjMWI5ZjIyZmUwZTgxZWIxYTAyOGZhNGIwYjY5MDRjZWQ1YzdlZTlkNjI5YTgxOTU2MDc2NDk5YyIKICAgIH0KICB9Cn0=", "NybQzjteIreKG8mUVlpy4+gVYEloMFxsdAQyfRk5+WS2braPgTWfwxVvv8sukxJLpgxQjrOzSOVhwW5k4cO9j2n8ugWUOzUWnrxGzKmvegZ5UTmDVanLhg2ESFce0oFadJ7RrrQeYYgfqFFjKsA/9Q+Aky0KfdV38pt8U2UsGq68IVSjyickXD3QiwHR9u4FINT98th6m4/9iwhm80Oz1wd9C3O4kdpqGwNWrxLJx8MlcTfzmqSnuuw8bpSNXjXeD1yuScqAXkr8CYg78vg106YFQMNMuwNyIJX65HtTnjJD01xjoKVDw+jKZkFy9v/9ejtQyUjv1cumzrD+lQDejbKyFDNq5cuS0FGza3cfZrqXDXLRr4ujxARNQGxDsbRaXHVbGhuVnHfKy2Z5SjjPOgAzk+ZLzt3nINsp0lRj9xxYilOnKLi+6ExC38+1xUwcU2jtqvkqqCHYDe35WtVIj6nir/sBSbOu93z2anM7/eFH2cboGP/JVwrAJ4o5gH2u644DTxfB9zd6uUqs2mKGwSDd6N/S8IYJmjjQbk87mj9NpnMvWbPVpAs7pmROzuLJ12w+wJtUz6LqU1Nr5YgZyT2NgGiG9xZl560RAAXtNDexM29Zy+gNfIL6aYuLoy6Jz0OhPcKmDfsVWsSsUO7AQDRSLcc5cgGO17m/P0E0l6o=", true);
        pl.getWatcher().setRightClicking(false);
        EntityManager.DEFENSE_PERCENTAGE.put((Object)entity, (Object)70);
        entity.setMetadata("LD", (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)true));
        entity.setMetadata("DungeonMobs", (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)true));
        entity.setMetadata("SlayerBoss", (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)true));
        new BukkitRunnable() {
            public void run() {
                if (entity.isDead()) {
                    this.cancel();
                    return;
                }
                if (((CraftZombie)entity).getTarget() != null) {
                    CryptDreadlord.this.skullShoot = true;
                    CryptDreadlord.this.throwSkull(entity, pl);
                    SUtil.delay(() -> {
                        final Object val$entity = entity;
                        final Object val$pl = pl;
                        CryptDreadlord.this.throwSkull(entity, pl);
                    }, 20L);
                    SUtil.delay(() -> {
                        final Object val$entity2 = entity;
                        final Object val$pl2 = pl;
                        CryptDreadlord.this.throwSkull(entity, pl);
                    }, 30L);
                    SUtil.delay(() -> {
                        final Object val$entity3 = entity;
                        final Object val$pl3 = pl;
                        CryptDreadlord.this.throwSkull(entity, pl);
                    }, 40L);
                    SUtil.delay(() -> {
                        final Object val$entity4 = entity;
                        final Object val$pl4 = pl;
                        CryptDreadlord.this.throwSkull(entity, pl);
                    }, 50L);
                    SUtil.delay(() -> {
                        final Object val$entity5 = entity;
                        final Object val$pl5 = pl;
                        CryptDreadlord.this.throwSkull(entity, pl);
                    }, 60L);
                    SUtil.delay(() -> {
                        final Object val$entity6 = entity;
                        final Object val$pl6 = pl;
                        CryptDreadlord.this.throwSkull(entity, pl);
                    }, 70L);
                    SUtil.delay(() -> {
                        final Object val$entity7 = entity;
                        final Object val$pl7 = pl;
                        CryptDreadlord.this.throwSkull(entity, pl);
                    }, 80L);
                    SUtil.delay(() -> {
                        final Object val$entity8 = entity;
                        final Object val$pl8 = pl;
                        CryptDreadlord.this.throwSkull(entity, pl);
                    }, 90L);
                    SUtil.delay(() -> {
                        final Object val$entity9 = entity;
                        final Object val$pl9 = pl;
                        CryptDreadlord.this.throwSkull(entity, pl);
                    }, 100L);
                    SUtil.delay(() -> {
                        final Object val$entity10 = entity;
                        final Object val$pl10 = pl;
                        CryptDreadlord.this.throwSkull(entity, pl);
                        CryptDreadlord.this.skullShoot = false;
                    }, 100L);
                }
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 40L, 200L);
        new BukkitRunnable() {
            public void run() {
                final EntityLiving nms = ((CraftLivingEntity)entity).getHandle();
                if (entity.isDead()) {
                    this.cancel();
                    return;
                }
                final LivingEntity target1 = (LivingEntity)((CraftZombie)entity).getTarget();
                for (final Entity entities : entity.getWorld().getNearbyEntities(entity.getLocation().add(entity.getLocation().getDirection().multiply(1.0)), 1.5, 1.5, 1.5)) {
                    if (CryptDreadlord.this.skullShoot) {
                        continue;
                    }
                    if (!(entities instanceof Player)) {
                        continue;
                    }
                    final Player target2 = (Player)entities;
                    if (target2.getGameMode() == GameMode.CREATIVE) {
                        continue;
                    }
                    if (target2.getGameMode() == GameMode.SPECTATOR) {
                        continue;
                    }
                    if (target2.hasMetadata("NPC")) {
                        continue;
                    }
                    if (target2.getNoDamageTicks() == 7) {
                        continue;
                    }
                    if (SUtil.random(0, 10) > 8) {
                        continue;
                    }
                    entity.teleport(entity.getLocation().setDirection(target2.getLocation().subtract(entities.getLocation()).toVector()));
                    for (final Player players : Bukkit.getOnlinePlayers()) {
                        ((CraftPlayer)players).getHandle().playerConnection.sendPacket((Packet)new PacketPlayOutAnimation((net.minecraft.server.v1_8_R3.Entity)((CraftLivingEntity)entity).getHandle(), 0));
                    }
                    nms.r((net.minecraft.server.v1_8_R3.Entity)((CraftPlayer)target2).getHandle());
                    break;
                }
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 0L, 2L);
    }
    
    @Override
    public SEntityEquipment getEntityEquipment() {
        return new SEntityEquipment(new ItemStack(Material.DIAMOND_SWORD), null, null, null, null);
    }
    
    @Override
    public void onDeath(final SEntity sEntity, final Entity killed, final Entity damager) {
    }
    
    @Override
    public boolean isBaby() {
        return false;
    }
    
    @Override
    public boolean hasNameTag() {
        return false;
    }
    
    @Override
    public boolean isVillager() {
        return false;
    }
    
    @Override
    public void onDamage(final SEntity sEntity, final Entity damager, final EntityDamageByEntityEvent e, final AtomicDouble damage) {
    }
    
    @Override
    public double getXPDropped() {
        return 56.0;
    }
    
    @Override
    public double getMovementSpeed() {
        return 0.2;
    }
    
    public void throwSkull(final LivingEntity e, final PlayerDisguise pl) {
        pl.getWatcher().setRightClicking(true);
        final WitherSkull skull = (WitherSkull)e.launchProjectile((Class)WitherSkull.class);
        skull.setShooter((ProjectileSource)e);
        e.getWorld().playSound(e.getLocation(), Sound.WITHER_SHOOT, 1.0f, 1.0f);
        SUtil.delay(() -> pl.getWatcher().setRightClicking(false), 10L);
    }
}
