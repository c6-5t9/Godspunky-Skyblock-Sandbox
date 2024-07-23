package net.hypixel.skyblock.api.protocol;

import net.minecraft.server.v1_8_R3.Packet;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.World;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.Item;
import java.util.Iterator;
import org.bukkit.event.entity.EntityDamageEvent;
import net.hypixel.skyblock.user.User;
import net.hypixel.skyblock.item.Rarity;
import net.hypixel.skyblock.features.enchantment.EnchantmentType;
import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitRunnable;
import net.hypixel.skyblock.item.SItem;
import net.hypixel.skyblock.item.SMaterial;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.metadata.FixedMetadataValue;
import net.hypixel.skyblock.SkyBlock;
import org.bukkit.Sound;
import org.bukkit.Effect;
import org.bukkit.entity.ArmorStand;
import net.hypixel.skyblock.util.Sputnik;
import net.hypixel.skyblock.util.SUtil;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class PacketInvoker
{
    public static void dropChest(final Player owner, final Location loc) {
        final boolean isExplosive = SUtil.random(0, 3) == 1;
        owner.sendMessage(Sputnik.trans("&e&oYou dropped a Loot Chest!"));
        final ArmorStand drop = (ArmorStand)owner.getWorld().spawn(loc.clone().add(0.0, -1.4, 0.0), (Class)ArmorStand.class);
        drop.getWorld().playEffect(drop.getLocation(), Effect.EXPLOSION_HUGE, 1);
        owner.playSound(owner.getLocation(), Sound.ENDERMAN_TELEPORT, 1.0f, 1.0f);
        drop.setVisible(false);
        drop.setGravity(false);
        drop.setCustomNameVisible(true);
        drop.setCustomName(Sputnik.trans("&6&lLOOT CHEST"));
        drop.setMetadata("ss_drop", (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)true));
        if (!isExplosive) {
            drop.getEquipment().setHelmet(SItem.of(SMaterial.CHEST).getStack());
        }
        else {
            drop.getEquipment().setHelmet(SItem.of(SMaterial.TRAPPED_CHEST).getStack());
        }
        SUtil.delay(() -> {
            if (!drop.isDead()) {
                drop.remove();
            }
        }, 1000L);
        new BukkitRunnable() {
            boolean noticeable = true;
            
            public void run() {
                if (drop.isDead()) {
                    this.cancel();
                    return;
                }
                if (owner.getWorld() != drop.getWorld()) {
                    drop.remove();
                    this.cancel();
                    return;
                }
                if (drop.isDead()) {
                    this.cancel();
                    return;
                }
                if (!owner.getWorld().getEntities().contains((Object)drop)) {
                    this.cancel();
                    return;
                }
                if (!owner.isOnline()) {
                    drop.remove();
                    this.cancel();
                    return;
                }
                PacketInvoker.invokeChannel((Entity)drop, drop.getWorld(), owner);
                owner.spigot().playEffect(drop.getLocation().add(0.0, 1.0, 0.0), Effect.HAPPY_VILLAGER, 0, 1, (float)SUtil.random(-1.0, 1.0), 1.0f, (float)SUtil.random(-1.0, 1.0), 0.0f, 1, 100);
                if (!drop.getNearbyEntities(0.07, 0.07, 0.07).contains((Object)owner)) {
                    this.noticeable = true;
                }
                for (final Entity e : drop.getNearbyEntities(0.07, 0.07, 0.07)) {
                    if (e instanceof Player && e == owner) {
                        drop.teleport((Entity)owner);
                        owner.playSound(owner.getLocation(), Sound.CHEST_OPEN, 1.0f, 1.0f);
                        if (!isExplosive) {
                            final double r = SUtil.random(0.0, 1.0);
                            final double c = 5.0E-4;
                            final double r2 = SUtil.random(0.0, 1.0);
                            final double c2 = 0.001;
                            if (r < 5.0E-4) {
                                owner.playSound(owner.getLocation(), Sound.LEVEL_UP, 1.0f, 0.4f);
                                owner.sendMessage(Sputnik.trans("&5&lLUCKY! &fYou found a &5Luckiness VII &fbook inside the loot chest!"));
                                final SItem Book = SItem.of(SMaterial.ENCHANTED_BOOK);
                                Book.addEnchantment(EnchantmentType.LUCKINESS, 7);
                                final Item item = SUtil.spawnPersonalItem(Book.getStack(), drop.getLocation(), owner);
                                item.setCustomNameVisible(true);
                                item.setCustomName(Sputnik.trans("&e&ka&r &f1x &5Luckiness VII &e&ka"));
                            }
                            else if (r2 < 0.001) {
                                owner.playSound(owner.getLocation(), Sound.LEVEL_UP, 1.0f, 0.0f);
                                owner.sendMessage(Sputnik.trans("&6&lLUCKY! &fYou found a &7[Lvl 1] &6Enderman &finside the loot chest!"));
                                final SItem eman = SItem.of(SMaterial.ENDERMAN_PET);
                                eman.setRarity(Rarity.LEGENDARY);
                                final Item item = SUtil.spawnPersonalItem(eman.getStack(), drop.getLocation(), owner);
                                item.setCustomNameVisible(true);
                                item.setCustomName(Sputnik.trans("&e&ka&r &f1x &7[Lvl 1] &6Enderman &e&ka"));
                            }
                            else {
                                final int bitsReward = SUtil.random(80, 400);
                                User.getUser(owner.getUniqueId()).addBits(bitsReward);
                                owner.sendMessage(Sputnik.trans("&b&lBITS! &fYou found &b" + SUtil.commaify(bitsReward) + " Bits&f inside the loot chest!"));
                            }
                        }
                        else {
                            owner.getWorld().playEffect(owner.getLocation(), Effect.EXPLOSION_HUGE, 1);
                            owner.getWorld().playEffect(owner.getLocation(), Effect.EXPLOSION_HUGE, 1);
                            owner.getWorld().playEffect(owner.getLocation(), Effect.EXPLOSION_HUGE, 1);
                            owner.getWorld().playEffect(owner.getLocation(), Effect.EXPLOSION_HUGE, 1);
                            owner.getWorld().playEffect(owner.getLocation(), Effect.EXPLOSION_HUGE, 1);
                            owner.getWorld().playEffect(owner.getLocation(), Effect.EXPLOSION_HUGE, 1);
                            owner.getWorld().playSound(owner.getLocation(), Sound.EXPLODE, 1.5f, 0.0f);
                            owner.getWorld().playSound(owner.getLocation(), Sound.AMBIENCE_THUNDER, 1.5f, 0.0f);
                            User.getUser(owner.getUniqueId()).damage(owner.getMaxHealth() * 10.0, EntityDamageEvent.DamageCause.ENTITY_ATTACK, (Entity)drop);
                            owner.damage(1.0E-5);
                        }
                        drop.remove();
                    }
                }
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 0L, 1L);
    }
    
    public static void dropT34Pet(final Player owner, final Location loc) {
        owner.sendMessage(Sputnik.trans("&7&oYou dropped something, check around you..."));
        owner.playSound(owner.getLocation(), Sound.ENDERMAN_TELEPORT, 1.0f, 2.0f);
        final ArmorStand drop = (ArmorStand)owner.getWorld().spawn(loc.clone().add(0.0, -0.5, 0.0), (Class)ArmorStand.class);
        drop.getWorld().playEffect(drop.getLocation(), Effect.EXPLOSION_HUGE, 1);
        drop.setVisible(false);
        drop.setGravity(false);
        drop.setCustomNameVisible(true);
        drop.setMetadata("ss_drop", (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)true));
        drop.getEquipment().setHelmet(SItem.of(SMaterial.HIDDEN_USSR_T34_TANK_PET).getStack());
        drop.setCustomName(Sputnik.trans("&e&ka&r &f1x &7[Lvl 1] &6Mini T-34 &e&ka"));
        SUtil.delay(() -> {
            if (!drop.isDead()) {
                owner.sendMessage((Object)ChatColor.RED + "Oh no, you didn't picked up your T-34 pet, it despawned, good luck next time, F.");
                drop.remove();
            }
        }, 6000L);
        new BukkitRunnable() {
            boolean noticeable = true;
            
            public void run() {
                if (drop.isDead()) {
                    this.cancel();
                    return;
                }
                if (!owner.getWorld().getEntities().contains((Object)drop)) {
                    this.cancel();
                    return;
                }
                if (!owner.isOnline()) {
                    drop.remove();
                    this.cancel();
                    return;
                }
                final Location l = drop.getLocation();
                l.setYaw(l.getYaw() + 3.0f);
                drop.teleport(l);
                PacketInvoker.invokeChannel((Entity)drop, drop.getWorld(), owner);
                owner.spigot().playEffect(drop.getLocation().add(0.0, 1.0, 0.0), Effect.FIREWORKS_SPARK, 0, 1, (float)SUtil.random(-1.0, 1.0), 1.0f, (float)SUtil.random(-1.0, 1.0), 0.0f, 1, 100);
                if (!drop.getNearbyEntities(0.07, 0.07, 0.07).contains((Object)owner)) {
                    this.noticeable = true;
                }
                for (final Entity e : drop.getNearbyEntities(0.07, 0.07, 0.07)) {
                    if (e instanceof Player && e == owner) {
                        if (!Sputnik.isFullInv(owner)) {
                            drop.teleport((Entity)owner);
                            drop.remove();
                            SUtil.globalBroadcast(Sputnik.trans(""));
                            SUtil.globalBroadcast(Sputnik.trans("&c>&e>&b> &6&lWOW! &e" + owner.getDisplayName() + " &ehas obtained &7[Lvl 1] &6Mini T-34&e! &b<&e<&c<"));
                            SUtil.globalBroadcast(Sputnik.trans(""));
                            owner.sendMessage(Sputnik.trans("&6&lPET DROP! &eYou have obtained &7[Lvl 1] &6Mini T-34&e! &d&lGG&e!"));
                            owner.playSound(owner.getLocation(), Sound.LEVEL_UP, 1.0f, 0.1f);
                            owner.playSound(owner.getLocation(), Sound.ITEM_PICKUP, 1.0f, 1.0f);
                            owner.getInventory().addItem(new ItemStack[] { SItem.of(SMaterial.HIDDEN_USSR_T34_TANK_PET).getStack() });
                        }
                        else {
                            SUtil.delay(() -> {
                                final Object val$drop = drop;
                                final Object val$owner = owner;
                                if (this.noticeable && drop.getNearbyEntities(0.07, 0.07, 0.07).contains((Object)owner)) {
                                    owner.playSound(owner.getLocation(), Sound.VILLAGER_NO, 1.0f, 1.0f);
                                    owner.sendMessage((Object)ChatColor.RED + "Your inventory is full, clean it up!");
                                    this.noticeable = false;
                                }
                            }, 20L);
                        }
                    }
                }
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 0L, 1L);
    }
    
    public static void dropEye(final Player owner, final Location loc, final int amount) {
        final ArmorStand drop = (ArmorStand)owner.getWorld().spawn(loc.clone().add(0.0, -0.7, 0.0), (Class)ArmorStand.class);
        drop.setVisible(false);
        drop.setGravity(false);
        drop.setCustomNameVisible(true);
        drop.setMetadata("ss_drop", (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)true));
        drop.getEquipment().setHelmet(SItem.of(SMaterial.REMNANT_OF_THE_EYE).getStack());
        drop.setCustomName(Sputnik.trans("&7" + amount + "x &9Ender's Fragment"));
        SUtil.delay(() -> {
            if (!drop.isDead()) {
                drop.remove();
            }
        }, 2000L);
        new BukkitRunnable() {
            boolean noticeable = true;
            
            public void run() {
                if (owner.getWorld() != drop.getWorld()) {
                    drop.remove();
                }
                if (drop.isDead()) {
                    this.cancel();
                    return;
                }
                if (!owner.getWorld().getEntities().contains((Object)drop)) {
                    drop.remove();
                    this.cancel();
                    return;
                }
                if (!owner.isOnline()) {
                    drop.remove();
                    this.cancel();
                    return;
                }
                final Location l = drop.getLocation();
                l.setYaw(l.getYaw() + 3.0f);
                drop.teleport(l);
                PacketInvoker.invokeChannel((Entity)drop, drop.getWorld(), owner);
                if (!drop.getNearbyEntities(0.07, 0.07, 0.07).contains((Object)owner)) {
                    this.noticeable = true;
                }
                owner.spigot().playEffect(drop.getLocation().add(0.0, 1.0, 0.0), Effect.WITCH_MAGIC, 0, 1, (float)SUtil.random(-1.0, 1.0), 1.0f, (float)SUtil.random(-1.0, 1.0), 0.0f, 1, 100);
                for (final Entity e : drop.getNearbyEntities(0.07, 0.07, 0.07)) {
                    if (e instanceof Player && e == owner) {
                        if (!Sputnik.isFullInv(owner)) {
                            drop.teleport((Entity)owner);
                            drop.remove();
                            owner.playSound(owner.getLocation(), Sound.ITEM_PICKUP, 1.0f, 1.0f);
                            owner.getWorld().playEffect(drop.getLocation(), Effect.LAVA_POP, 1);
                            for (int i = 0; i < amount; ++i) {
                                final SItem sitem = SItem.of(SMaterial.HIDDEN_VOID_FRAGMENT);
                                owner.getInventory().addItem(new ItemStack[] { sitem.getStack() });
                            }
                        }
                        else {
                            SUtil.delay(() -> {
                                final Object val$drop = drop;
                                final Object val$owner = owner;
                                if (this.noticeable && drop.getNearbyEntities(0.07, 0.07, 0.07).contains((Object)owner)) {
                                    owner.playSound(owner.getLocation(), Sound.VILLAGER_NO, 1.0f, 1.0f);
                                    owner.sendMessage((Object)ChatColor.RED + "Your inventory is full, clean it up!");
                                    this.noticeable = false;
                                }
                            }, 20L);
                        }
                    }
                }
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 0L, 1L);
    }
    
    public static void dropGoldenTigerPet(final Player owner, final Location loc, final boolean isMythic) {
        owner.sendMessage(Sputnik.trans("&7&oYou dropped something, check around you..."));
        SUtil.sendTitle(owner, Sputnik.trans(""));
        SUtil.sendSubtitle(owner, Sputnik.trans("&6&oYou dropped something, check around!"));
        new BukkitRunnable() {
            public void run() {
                System.out.println("**A TIGER PET DROPPED!** Player `" + owner.getName() + "` dropped a Golden Tiger Pet at `" + owner.getWorld().getName() + "`, Mythic: " + isMythic);
            }
        }.runTaskAsynchronously((Plugin)SkyBlock.getPlugin());
        owner.playSound(owner.getLocation(), Sound.ENDERMAN_TELEPORT, 1.0f, 2.0f);
        final ArmorStand drop = (ArmorStand)owner.getWorld().spawn(loc.clone().add(0.0, -0.5, 0.0), (Class)ArmorStand.class);
        drop.getWorld().playEffect(drop.getLocation(), Effect.EXPLOSION_HUGE, 1);
        drop.setVisible(false);
        drop.setGravity(false);
        drop.setCustomNameVisible(true);
        drop.setMetadata("ss_drop", (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)true));
        drop.getEquipment().setHelmet(SItem.of(SMaterial.HIDDEN_GOLDEN_TIGER_2022).getStack());
        if (!isMythic) {
            drop.setCustomName(Sputnik.trans("&e&ka&r &f1x &7[Lvl 1] &6Golden Tiger &e&ka"));
        }
        else {
            drop.setCustomName(Sputnik.trans("&e&ka&r &f1x &7[Lvl 1] &dGolden Tiger &e&ka"));
        }
        SUtil.delay(() -> {
            if (!drop.isDead()) {
                new BukkitRunnable() {
                    final /* synthetic */ Player val$owner;
                    final /* synthetic */ boolean val$isMythic;
                    
                    public void run() {
                        System.out.println("**PET DESPAWNED! ** Player `" + this.val$owner.getName() + "` let a Golden Tiger Pet at `" + this.val$owner.getWorld().getName() + "` despawned, Mythic: " + this.val$isMythic);
                    }
                }.runTaskAsynchronously((Plugin)SkyBlock.getPlugin());
                owner.sendMessage((Object)ChatColor.RED + "Oh no, you didn't picked up your Golden Tiger pet, it despawned, good luck next time, F.");
                drop.remove();
            }
        }, 6000L);
        new BukkitRunnable() {
            boolean noticeable = true;
            
            public void run() {
                if (owner.getWorld() != drop.getWorld()) {
                    new BukkitRunnable() {
                        public void run() {
                            System.out.println("**PET DESPAWNED! ** Player `" + owner.getName() + "` let a Golden Tiger Pet at `" + owner.getWorld().getName() + "` despawned, Mythic: " + isMythic);
                        }
                    }.runTaskAsynchronously((Plugin)SkyBlock.getPlugin());
                    owner.sendMessage((Object)ChatColor.RED + "Oh no, you didn't picked up your Golden Tiger pet, it despawned, good luck next time, F.");
                    drop.remove();
                }
                if (drop.isDead()) {
                    this.cancel();
                    return;
                }
                if (!owner.getWorld().getEntities().contains((Object)drop)) {
                    drop.remove();
                    this.cancel();
                    return;
                }
                if (!owner.isOnline()) {
                    drop.remove();
                    this.cancel();
                    return;
                }
                final Location l = drop.getLocation();
                l.setYaw(l.getYaw() + 3.0f);
                drop.teleport(l);
                PacketInvoker.invokeChannel((Entity)drop, drop.getWorld(), owner);
                owner.spigot().playEffect(drop.getLocation().add(0.0, 1.0, 0.0), Effect.FIREWORKS_SPARK, 0, 1, (float)SUtil.random(-1.0, 1.0), 1.0f, (float)SUtil.random(-1.0, 1.0), 0.0f, 1, 100);
                if (isMythic) {
                    owner.spigot().playEffect(drop.getLocation().add(0.0, 1.0, 0.0), Effect.FLYING_GLYPH, 0, 1, (float)SUtil.random(-1.0, 1.0), 1.0f, (float)SUtil.random(-1.0, 1.0), 0.0f, 1, 100);
                    owner.spigot().playEffect(drop.getLocation().add(0.0, 1.0, 0.0), Effect.WITCH_MAGIC, 0, 1, (float)SUtil.random(-1.0, 1.0), 1.0f, (float)SUtil.random(-1.0, 1.0), 0.0f, 1, 100);
                }
                if (!drop.getNearbyEntities(0.07, 0.07, 0.07).contains((Object)owner)) {
                    this.noticeable = true;
                }
                for (final Entity e : drop.getNearbyEntities(0.07, 0.07, 0.07)) {
                    if (e instanceof Player && e == owner) {
                        if (!Sputnik.isFullInv(owner)) {
                            drop.teleport((Entity)owner);
                            drop.remove();
                            SUtil.globalBroadcast(Sputnik.trans(""));
                            if (!isMythic) {
                                SUtil.globalBroadcast(Sputnik.trans("&c>&e>&b> &6&lWOW! &e" + owner.getDisplayName() + " &ehas obtained &7[Lvl 1] &6Golden Tiger&e! &b<&e<&c<"));
                            }
                            else {
                                SUtil.globalBroadcast(Sputnik.trans("&c>&e>&b> &6&lNICE! &e" + owner.getDisplayName() + " &ehas obtained &7[Lvl 1] &dGolden Tiger&e! &b<&e<&c<"));
                            }
                            SUtil.globalBroadcast(Sputnik.trans(""));
                            if (!isMythic) {
                                owner.sendMessage(Sputnik.trans("&6&lPET DROP! &eYou have obtained &7[Lvl 1] &6Golden Tiger&e! &d&lGG&e!"));
                            }
                            else {
                                owner.sendMessage(Sputnik.trans("&6&lSUPER RARE PET DROP! &eYou have obtained &7[Lvl 1] &dGolden Tiger&e! &d&lWOW&e!"));
                            }
                            owner.playSound(owner.getLocation(), Sound.LEVEL_UP, 1.0f, 0.1f);
                            owner.playSound(owner.getLocation(), Sound.ITEM_PICKUP, 1.0f, 1.0f);
                            owner.getWorld().playEffect(drop.getLocation(), Effect.LAVA_POP, 1);
                            new BukkitRunnable() {
                                public void run() {
                                    System.out.println("**PET PICKUP!** Player `" + owner.getName() + "` picked up a Golden Tiger Pet at `" + owner.getWorld().getName() + "`, Mythic: " + isMythic);
                                }
                            }.runTaskAsynchronously((Plugin)SkyBlock.getPlugin());
                            if (!isMythic) {
                                owner.getInventory().addItem(new ItemStack[] { SItem.of(SMaterial.HIDDEN_GOLDEN_TIGER_2022).getStack() });
                            }
                            else {
                                final SItem sitem = SItem.of(SMaterial.HIDDEN_GOLDEN_TIGER_2022);
                                sitem.setRarity(Rarity.MYTHIC);
                                owner.getInventory().addItem(new ItemStack[] { sitem.getStack() });
                            }
                        }
                        else {
                            SUtil.delay(() -> {
                                final Object val$drop = drop;
                                final Object val$owner = owner;
                                if (this.noticeable && drop.getNearbyEntities(0.07, 0.07, 0.07).contains((Object)owner)) {
                                    owner.playSound(owner.getLocation(), Sound.VILLAGER_NO, 1.0f, 1.0f);
                                    owner.sendMessage((Object)ChatColor.RED + "Your inventory is full, clean it up!");
                                    this.noticeable = false;
                                }
                            }, 20L);
                        }
                    }
                }
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 0L, 1L);
    }
    
    public static void invokeChannel(final Entity as1, final World w, final Player owner) {
        final net.minecraft.server.v1_8_R3.Entity el = ((CraftEntity)as1).getHandle();
        final PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(new int[] { el.getId() });
        for (final Entity e : w.getNearbyEntities(owner.getLocation(), 35.0, 35.0, 35.0)) {
            if (e instanceof Player) {
                final Player p = (Player)e;
                if (p == owner) {
                    continue;
                }
                ((CraftPlayer)p).getHandle().playerConnection.sendPacket((Packet)packet);
            }
        }
    }
}
