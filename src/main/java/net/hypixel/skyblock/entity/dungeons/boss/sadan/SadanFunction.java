package net.hypixel.skyblock.entity.dungeons.boss.sadan;

import net.hypixel.skyblock.Repeater;
import net.hypixel.skyblock.util.Sputnik;
import org.bukkit.event.entity.EntityDamageEvent;
import net.hypixel.skyblock.user.User;
import net.hypixel.skyblock.user.PlayerUtils;
import net.hypixel.skyblock.user.PlayerStatistics;
import org.bukkit.entity.Player;
import java.util.Iterator;
import org.bukkit.Effect;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.Color;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.metadata.FixedMetadataValue;
import net.hypixel.skyblock.SkyBlock;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import net.hypixel.skyblock.entity.SEntity;
import net.hypixel.skyblock.entity.SEntityType;
import org.bukkit.Location;
import org.bukkit.World;
import java.util.Random;
import net.hypixel.skyblock.util.SUtil;

public class SadanFunction
{
    public static String generateRandom() {
        final int leftLimit = 97;
        final int rightLimit = 122;
        final int targetStringLength = SUtil.random(5, 6);
        final Random random = new Random();
        final String generatedString = ((StringBuilder)random.ints(97, 123).limit((long)targetStringLength).collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)).toString();
        return generatedString;
    }
    
    public static void jlp(final World w) {
        final SEntity se_jollypink = new SEntity(new Location(w, 183.5, 84.0, 253.5, -45.0f, 0.0f), SEntityType.GIANT_DUMMY, new Object[0]);
        final LivingEntity e = se_jollypink.getEntity();
        final EntityEquipment eq = e.getEquipment();
        eq.setItemInHand((ItemStack)null);
        eq.setHelmet(b(14751108, Material.LEATHER_HELMET));
        eq.setChestplate(b(14751108, Material.LEATHER_CHESTPLATE));
        eq.setLeggings(b(14751108, Material.LEATHER_LEGGINGS));
        eq.setBoots(b(14751108, Material.LEATHER_BOOTS));
        e.setMetadata("JollyPink", (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)true));
    }
    
    public static void lasrg(final World w) {
        final SEntity se_jollypink = new SEntity(new Location(w, 199.5, 84.0, 253.5, 45.0f, 0.0f), SEntityType.GIANT_DUMMY, new Object[0]);
        final LivingEntity e = se_jollypink.getEntity();
        final EntityEquipment eq = e.getEquipment();
        eq.setItemInHand((ItemStack)null);
        eq.setHelmet(b(12228503, Material.LEATHER_HELMET));
        eq.setChestplate(b(12228503, Material.LEATHER_CHESTPLATE));
        eq.setLeggings(b(12228503, Material.LEATHER_LEGGINGS));
        eq.setBoots(b(12228503, Material.LEATHER_BOOTS));
        e.setMetadata("LASR", (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)true));
    }
    
    public static void diag(final World w) {
        final SEntity se_jollypink = new SEntity(new Location(w, 183.5, 84.0, 279.5, -135.0f, 0.0f), SEntityType.GIANT_DUMMY, new Object[0]);
        final LivingEntity e = se_jollypink.getEntity();
        final EntityEquipment eq = e.getEquipment();
        eq.setItemInHand(SUtil.enchant(c(Material.DIAMOND_SWORD)));
        eq.setHelmet(c(Material.DIAMOND_HELMET));
        eq.setChestplate(c(Material.DIAMOND_CHESTPLATE));
        eq.setLeggings(c(Material.DIAMOND_LEGGINGS));
        eq.setBoots(c(Material.DIAMOND_BOOTS));
        e.setMetadata("Diamond", (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)true));
    }
    
    public static void bfg(final World w) {
        final SEntity se_jollypink = new SEntity(new Location(w, 199.5, 84.0, 279.5, 135.0f, 0.0f), SEntityType.GIANT_DUMMY, new Object[0]);
        final LivingEntity e = se_jollypink.getEntity();
        final EntityEquipment eq = e.getEquipment();
        eq.setItemInHand((ItemStack)null);
        eq.setHelmet((ItemStack)null);
        eq.setChestplate((ItemStack)null);
        eq.setLeggings((ItemStack)null);
        eq.setBoots(b(8991025, Material.LEATHER_BOOTS));
        e.setMetadata("Bigfoot", (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)true));
    }
    
    public static ItemStack buildColorStack(final int hexcolor) {
        final ItemStack stack = SUtil.applyColorToLeatherArmor(new ItemStack(Material.LEATHER_HELMET), Color.fromRGB(hexcolor));
        final ItemMeta itemMeta = stack.getItemMeta();
        itemMeta.spigot().setUnbreakable(true);
        stack.setItemMeta(itemMeta);
        return stack;
    }
    
    public static ItemStack b(final int hexcolor, final Material m) {
        final ItemStack stack = SUtil.applyColorToLeatherArmor(new ItemStack(m), Color.fromRGB(hexcolor));
        final ItemMeta itemMeta = stack.getItemMeta();
        itemMeta.spigot().setUnbreakable(true);
        stack.setItemMeta(itemMeta);
        return stack;
    }
    
    public static ItemStack c(final Material m) {
        final ItemStack stack = new ItemStack(m);
        final ItemMeta itemMeta = stack.getItemMeta();
        itemMeta.spigot().setUnbreakable(true);
        stack.setItemMeta(itemMeta);
        return stack;
    }
    
    public static void endPhase1(final World w) {
        for (final Entity e : w.getEntities()) {
            if (e.hasMetadata("ftd")) {
                e.remove();
            }
            if (e.hasMetadata("t_sadan")) {
                e.remove();
                e.getWorld().playEffect(e.getLocation(), Effect.EXPLOSION, 10);
                e.getWorld().playEffect(e.getLocation(), Effect.EXPLOSION, 10);
                e.getWorld().playEffect(e.getLocation(), Effect.EXPLOSION, 10);
                e.getWorld().playEffect(e.getLocation(), Effect.EXPLOSION, 10);
                e.getWorld().playEffect(e.getLocation(), Effect.EXPLOSION, 10);
                e.getWorld().playEffect(e.getLocation(), Effect.EXPLOSION, 10);
                e.getWorld().playEffect(e.getLocation(), Effect.EXPLOSION, 10);
                e.getWorld().playEffect(e.getLocation(), Effect.EXPLOSION, 10);
                e.getWorld().playEffect(e.getLocation(), Effect.EXPLOSION, 10);
                e.getWorld().playEffect(e.getLocation(), Effect.EXPLOSION, 10);
                e.getWorld().playEffect(e.getLocation(), Effect.EXPLOSION, 10);
            }
        }
    }
    
    public static void release(final World w) {
        SUtil.delay(() -> rjp(w), 10L);
        SUtil.delay(() -> rdia(w), 30L);
        SUtil.delay(() -> rbf(w), 50L);
        SUtil.delay(() -> rlasr(w), 70L);
    }
    
    public static void rjp(final World w) {
        for (final Entity e : w.getEntities()) {
            if (e.hasMetadata("JollyPink")) {
                e.remove();
                new SEntity(new Location(e.getWorld(), e.getLocation().getX(), e.getLocation().getY(), e.getLocation().getZ(), e.getLocation().getYaw(), 0.0f), SEntityType.JOLLY_PINK_SADAN, new Object[0]);
                w.strikeLightningEffect(e.getLocation());
                break;
            }
        }
        AnimationSequence.edit(new Location(w, 187.0, 81.0, 257.0), new Location(w, 178.0, 101.0, 248.0), w);
    }
    
    public static void rdia(final World w) {
        for (final Entity e : w.getEntities()) {
            if (e.hasMetadata("Diamond")) {
                e.remove();
                new SEntity(new Location(e.getWorld(), e.getLocation().getX(), e.getLocation().getY(), e.getLocation().getZ(), e.getLocation().getYaw(), 0.0f), SEntityType.DIAMOND_SADAN, new Object[0]);
                w.strikeLightningEffect(e.getLocation());
                break;
            }
        }
        AnimationSequence.edit(new Location(w, 187.0, 82.0, 275.0), new Location(w, 178.0, 101.0, 284.0), w);
    }
    
    public static void rbf(final World w) {
        for (final Entity e : w.getEntities()) {
            if (e.hasMetadata("Bigfoot")) {
                e.remove();
                new SEntity(new Location(e.getWorld(), e.getLocation().getX(), e.getLocation().getY(), e.getLocation().getZ(), e.getLocation().getYaw(), 0.0f), SEntityType.BIGFOOT_SADAN, new Object[0]);
                w.strikeLightningEffect(e.getLocation());
                break;
            }
        }
        AnimationSequence.edit(new Location(w, 194.0, 82.0, 283.0), new Location(w, 203.0, 101.0, 275.0), w);
    }
    
    public static void rlasr(final World w) {
        for (final Entity e : w.getEntities()) {
            if (e.hasMetadata("LASR")) {
                e.remove();
                new SEntity(new Location(e.getWorld(), e.getLocation().getX(), e.getLocation().getY(), e.getLocation().getZ(), e.getLocation().getYaw(), 0.0f), SEntityType.LASR_SADAN, new Object[0]);
                w.strikeLightningEffect(e.getLocation());
                break;
            }
        }
        AnimationSequence.edit(new Location(w, 203.0, 101.0, 248.0), new Location(w, 195.0, 81.0, 257.0), w);
    }
    
    public static void a(final World w, double x, double z, final float yaw) {
        x += 0.5;
        z += 0.5;
        new SEntity(new Location(w, x, 69.0, z, yaw, 0.0f), SEntityType.TERRACOTTA_DUMMY, new Object[0]);
    }
    
    public static void aA(final World w, double x, double z, final float yaw) {
        x += 0.5;
        z += 0.5;
        new SEntity(new Location(w, x, 69.0, z, yaw, 0.0f), SEntityType.SLEEPING_GOLEM, new Object[0]);
    }
    
    public static void b(final World w) {
        new SEntity(new Location(w, 191.5, 54.0, 266.5, 180.0f, 0.0f), SEntityType.DUMMY_SADAN_1, new Object[0]);
    }
    
    public static void s_(final Entity e) {
        final World w = e.getWorld();
        lasrg(w);
        diag(w);
        bfg(w);
        jlp(w);
        aA(w, 184.0, 252.0, -45.0f);
        aA(w, 198.0, 252.0, 45.0f);
        aA(w, 204.0, 266.0, 90.0f);
        aA(w, 184.0, 280.0, -135.0f);
        aA(w, 178.0, 266.0, -90.0f);
        aA(w, 198.0, 280.0, 135.0f);
        a(w, 194.0, 295.0, 90.0f);
        a(w, 188.0, 295.0, -90.0f);
        a(w, 194.0, 290.0, 90.0f);
        a(w, 188.0, 290.0, -90.0f);
        a(w, 194.0, 285.0, 90.0f);
        a(w, 188.0, 285.0, -90.0f);
        a(w, 194.0, 280.0, 90.0f);
        a(w, 188.0, 280.0, -90.0f);
        a(w, 194.0, 275.0, 90.0f);
        a(w, 188.0, 275.0, -90.0f);
        b(w);
        a(w, 194.0, 257.0, 90.0f);
        a(w, 188.0, 257.0, -90.0f);
        a(w, 194.0, 252.0, 90.0f);
        a(w, 188.0, 252.0, -90.0f);
        a(w, 194.0, 248.0, 90.0f);
        a(w, 188.0, 248.0, -90.0f);
        a(w, 194.0, 243.0, 90.0f);
        a(w, 188.0, 243.0, -90.0f);
        a(w, 194.0, 238.0, 90.0f);
        a(w, 188.0, 238.0, -90.0f);
    }
    
    public static Integer dmgc(final int damage, final Player p, final Entity e) {
        final PlayerStatistics statistics = (PlayerStatistics)PlayerUtils.STATISTICS_CACHE.get((Object)p.getUniqueId());
        if (statistics == null) {
            return 0;
        }
        final double defense = statistics.getDefense().addAll();
        final int dmglater = (int)Math.round(damage - damage * (defense / (defense + 100.0)));
        User.getUser(p.getUniqueId()).damage(dmglater, EntityDamageEvent.DamageCause.ENTITY_ATTACK, e);
        p.damage(1.0E-6, (Entity)null);
        return dmglater;
    }
    
    public static void roomLoop(final World w) {
    }
    
    public static void endRoom1(final World w) {
        if (w.getName().contains((CharSequence)"f6")) {
            SUtil.broadcastWorld(Sputnik.trans("&c&lSKYSIM MC >> &e&lThis demo floor currently in development so you can't respawn or get reward, sorry! We will update later on, thanks for playing, leave rating on #server-rating"), w);
            SUtil.delay(() -> SUtil.broadcastWorld(Sputnik.trans("&c[Warning] &eThis dungeon will close in &c30s"), w), 600L);
            SUtil.delay(() -> SUtil.broadcastWorld(Sputnik.trans("&c[Warning] &eThis dungeon will close in &c20s"), w), 1000L);
            SUtil.delay(() -> SUtil.broadcastWorld(Sputnik.trans("&c[Warning] &eThis dungeon will close in &c10s"), w), 1200L);
            SUtil.delay(() -> SUtil.broadcastWorld(Sputnik.trans("&c[Warning] &eThis dungeon will close in &c5s"), w), 1300L);
            SUtil.delay(() -> SUtil.broadcastWorld(Sputnik.trans("&c[Warning] &eThis dungeon will close in &c4s"), w), 1320L);
            SUtil.delay(() -> SUtil.broadcastWorld(Sputnik.trans("&c[Warning] &eThis dungeon will close in &c3s"), w), 1340L);
            SUtil.delay(() -> SUtil.broadcastWorld(Sputnik.trans("&c[Warning] &eThis dungeon will close in &c2s"), w), 1360L);
            SUtil.delay(() -> SUtil.broadcastWorld(Sputnik.trans("&c[Warning] &eThis dungeon will close in &c1s"), w), 1380L);
            SUtil.delay(() -> SadanBossManager.endFloor(w), 1400L);
        }
    }
    
    public static void sendReMsg(final boolean finishornot, final World w) {
        if (w.getName().contains((CharSequence)"f6") && Repeater.FloorLivingSec.containsKey((Object)w.getUID())) {
            if (finishornot) {
                final int bitsReward = Math.round((float)((100000 - Math.min(100000, (int)Repeater.FloorLivingSec.get((Object)w.getUID()))) * 150 / 255));
                String rew = "&6+" + SUtil.commaify(bitsReward) + " Coins &7(Completion Reward)";
                if (bitsReward <= 0) {
                    rew = "&cYou have no rewards!";
                }
                else {
                    w.getPlayers().forEach(p -> User.getUser(p.getUniqueId()).addCoins(bitsReward));
                }
                SUtil.broadcastWorld(Sputnik.trans("&a\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac"), w);
                SUtil.broadcastWorld(Sputnik.trans("        &cThe Catacombs Demo &8- &eFloor VI"), w);
                SUtil.broadcastWorld(Sputnik.trans("&c"), w);
                SUtil.broadcastWorld(Sputnik.trans("        &c\u2620&e Defeated &cSadan &ein &a" + Sputnik.formatTime((int)Repeater.FloorLivingSec.get((Object)w.getUID()))), w);
                SUtil.broadcastWorld(Sputnik.trans("&c"), w);
                SUtil.broadcastWorld(Sputnik.trans("            " + rew), w);
                SUtil.broadcastWorld(Sputnik.trans("&c"), w);
                SUtil.broadcastWorld(Sputnik.trans("&a\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac"), w);
            }
            else {
                SUtil.broadcastWorld(Sputnik.trans("&a\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac"), w);
                SUtil.broadcastWorld(Sputnik.trans("        &cThe Catacombs Demo &8- &eFloor VI"), w);
                SUtil.broadcastWorld(Sputnik.trans("&c"), w);
                SUtil.broadcastWorld(Sputnik.trans("        &c\u2620&e You died, but you can try again!"), w);
                SUtil.broadcastWorld(Sputnik.trans("&c"), w);
                SUtil.broadcastWorld(Sputnik.trans("           &cYou have no rewards cause you died."), w);
                SUtil.broadcastWorld(Sputnik.trans("&c"), w);
                SUtil.broadcastWorld(Sputnik.trans("&a\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac"), w);
            }
        }
    }
    
    public static void endRoom2(final World w) {
        if (w.getName().contains((CharSequence)"f6")) {
            SUtil.delay(() -> SUtil.broadcastWorld(Sputnik.trans("&c[Warning] &eThis dungeon will close in &c5s"), w), 200L);
            SUtil.delay(() -> SUtil.broadcastWorld(Sputnik.trans("&c[Warning] &eThis dungeon will close in &c4s"), w), 220L);
            SUtil.delay(() -> SUtil.broadcastWorld(Sputnik.trans("&c[Warning] &eThis dungeon will close in &c3s"), w), 240L);
            SUtil.delay(() -> SUtil.broadcastWorld(Sputnik.trans("&c[Warning] &eThis dungeon will close in &c2s"), w), 260L);
            SUtil.delay(() -> SUtil.broadcastWorld(Sputnik.trans("&c[Warning] &eThis dungeon will close in &c1s"), w), 280L);
            SUtil.delay(() -> SUtil.broadcastWorld(Sputnik.trans("&c[Warning] &eWarping you back to the Hub"), w), 300L);
            SUtil.delay(() -> SadanBossManager.endFloor(w), 300L);
        }
    }
}
