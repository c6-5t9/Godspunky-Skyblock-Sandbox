package net.hypixel.skyblock.command;

import java.util.Iterator;
import org.bukkit.entity.Player;
import net.hypixel.skyblock.util.SLog;
import net.hypixel.skyblock.util.SUtil;
import java.io.File;
import net.hypixel.skyblock.SkyBlock;
import net.hypixel.skyblock.gui.ConfirmWitherRuins;
import net.hypixel.skyblock.api.protocol.PacketFactory1_8_R3;
import net.minecraft.server.v1_8_R3.Packet;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntityLiving;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftVillager;
import net.minecraft.server.v1_8_R3.World;
import net.minecraft.server.v1_8_R3.EntityVillager;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import net.hypixel.skyblock.util.Sputnik;
import net.hypixel.skyblock.features.dungeons.blessing.Blessings;
import net.hypixel.skyblock.features.dungeons.blessing.BlessingType;
import org.bukkit.inventory.ItemStack;
import net.hypixel.skyblock.user.UserStash;
import org.bukkit.Material;
import net.hypixel.skyblock.features.collection.ItemCollection;
import net.hypixel.skyblock.user.User;
import net.hypixel.skyblock.item.SItem;
import net.hypixel.skyblock.features.dungeons.stats.ItemSerial;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import net.hypixel.skyblock.entity.dungeons.watcher.Watcher;
import org.bukkit.Location;
import org.bukkit.ChatColor;
import net.hypixel.skyblock.features.ranks.PlayerRank;

@CommandParameters(description = "Modify your absorption amount.", permission = PlayerRank.ADMIN)
public class SSTest extends SCommand
{
    @Override
    public void run(final CommandSource sender, final String[] args) {
        final Player player = sender.getPlayer();
        if (player.isOp()) {
            if (args[0].contains((CharSequence)"sp")) {
                player.sendMessage((Object)ChatColor.YELLOW + "Intizing Map...");
                final long s = System.currentTimeMillis();
                final Watcher w = new Watcher(new Location(player.getWorld(), 96.0, 99.0, 96.0), new Location(player.getWorld(), 126.0, 66.0, 126.0), 69);
                w.intitize();
                final long s_ = System.currentTimeMillis() - s;
                player.sendMessage((Object)ChatColor.GREEN + "All actions completed (Loop, placing heads, spawn Watcher)! This took " + (Object)ChatColor.YELLOW + s_ + "ms");
            }
            else if (args[0].contains((CharSequence)"pl")) {
                player.sendMessage((Object)ChatColor.YELLOW + "Done!");
                for (final Entity e : player.getWorld().getEntities()) {
                    if (e instanceof ArmorStand && e.hasMetadata("WATCHER_ENTITY")) {
                        e.getLocation().add(0.0, 1.7, 0.0).getBlock().setTypeIdAndData(35, (byte)4, true);
                    }
                }
            }
            else if (args[0].contains((CharSequence)"test")) {
                final ItemSerial is = ItemSerial.createBlank();
                if (player.getItemInHand() != null) {
                    final SItem sitem = SItem.find(player.getItemInHand());
                    is.saveTo(sitem);
                    sitem.setStarAmount(5);
                }
            }
            else if (args[0].contains((CharSequence)"collectionup")) {
                User.getUser(player.getUniqueId()).addToCollection(ItemCollection.WHEAT, 50);
            }
            else if (!args[0].contains((CharSequence)"wipe")) {
                if (!args[0].contains((CharSequence)"vlw")) {
                    if (!args[0].contains((CharSequence)"bung")) {
                        if (args[0].contains((CharSequence)"putitemstash")) {
                            if (player.getItemInHand() != null) {
                                if (player.getItemInHand().getType() == Material.AIR) {
                                    return;
                                }
                                UserStash.getStash(player.getUniqueId()).addItemInStash(player.getItemInHand());
                                player.setItemInHand((ItemStack)null);
                            }
                        }
                        else if (args[0].contains((CharSequence)"bs")) {
                            Blessings.dropBlessingPickable(player.getLocation().clone().add(2.0, 0.0, 2.0), new Blessings(BlessingType.valueOf(args[1]), Integer.parseInt(args[2]), player.getWorld()));
                        }
                        else if (args[0].contains((CharSequence)"blessshow")) {
                            for (final Blessings b : Blessings.getFrom(player.getWorld())) {
                                player.sendMessage((Object)ChatColor.YELLOW + b.toText());
                                final float[] n = b.getBlessingStats(User.getUser(player.getUniqueId())).getDefaultArray();
                                final StringBuilder sb = new StringBuilder();
                                for (int i = 0; i < n.length; ++i) {
                                    sb.append(n[i] + " ");
                                }
                                player.sendMessage((Object)ChatColor.RED + sb.toString());
                            }
                        }
                        else if (args[0].contains((CharSequence)"rs")) {
                            Blessings.resetForWorld(player.getWorld());
                        }
                        else if (args[0].contains((CharSequence)"chest")) {
                            Sputnik.makeChestBlessings(player.getLocation(), new Blessings(BlessingType.valueOf(args[1]), Integer.parseInt(args[2]), player.getWorld()), Boolean.parseBoolean(args[3]), Byte.parseByte(args[4]));
                        }
                        else if (args[0].contains((CharSequence)"citem")) {
                            Sputnik.makeChestItemLoot(player.getLocation(), player.getItemInHand(), Boolean.parseBoolean(args[3]), Byte.parseByte(args[4]));
                        }
                        else if (args[0].equals((Object)"npc")) {
                            player.sendMessage("Spawning npc1!");
                            final CraftVillager villager = new CraftVillager((CraftServer)Bukkit.getServer(), new EntityVillager((World)((CraftWorld)player.getLocation().getWorld()).getHandle()));
                            final PacketPlayOutSpawnEntityLiving packetPlayOutSpawnEntityLiving = new PacketPlayOutSpawnEntityLiving((EntityLiving)villager.getHandle());
                            ((CraftPlayer)player).getHandle().playerConnection.sendPacket((Packet)packetPlayOutSpawnEntityLiving);
                        }
                        else if (args[0].equals((Object)"npc2")) {
                            System.out.println("spawning npc2");
                            PacketFactory1_8_R3.createPacketVillagerSpawn(player.getLocation()).send(player);
                        }
                        else if (args[0].contains((CharSequence)"ruins")) {
                            new ConfirmWitherRuins().open(player);
                        }
                        else if (args[0].contains((CharSequence)"musicbgm")) {
                            if (args[1].contains((CharSequence)"play")) {
                                if (sender.getUser().isPlayingSong()) {
                                    this.send("&d[\u266b] &cYou're currently playing a song already! Use /sstest musicbgm stop to stop it!");
                                    return;
                                }
                                final File s2 = new File((Object)SkyBlock.getPlugin().getDataFolder() + File.separator + "/songs/" + args[2] + ".nbs");
                                if (!s2.exists()) {
                                    this.send("&d[\u266b] &cThe specified BGM file does not exist!");
                                    return;
                                }
                                this.send("&d[\u266b] &aPlaying BGM File &e" + args[2] + ".nbs &afrom disk&a for this world!");
                                SUtil.broadcastWorld(Sputnik.trans("&c"), player.getWorld());
                                SUtil.broadcastWorld(Sputnik.trans("&d[\u266b] &eYou're listening to &6" + args[2].replaceAll("_", " ") + " &efrom the &dGodspunky Radio&e, requested by &d" + player.getName() + "&e, enjoy!"), player.getWorld());
                                SUtil.broadcastWorld(Sputnik.trans("&c"), player.getWorld());
                                Sputnik.playSound(s2, 1000, 10, true, player, player.getLocation());
                            }
                            else if (args[1].contains((CharSequence)"stop")) {
                                this.send("&d[\u266b] &eStopped all music played by you!");
                                sender.getUser().setPlayingSong(false);
                            }
                            else {
                                this.send("&d\u266b GodSpunky Radiowave Usage \u266b");
                                this.send("&eCommand (Play): &6/sstest musicbgm play <song name>");
                                this.send("&eCommand (Stop): &6/sstest musicbgm stop");
                                this.send("&eAbout: &cSSMusicEngine-v0.1.0-ALPHA");
                            }
                        }
                        else if (args[0].contains((CharSequence)"gc")) {
                            SLog.info("Cleaning up the JVM (This may cause a short lag spike!)");
                            final long before = System.currentTimeMillis();
                            System.gc();
                            final long after = System.currentTimeMillis();
                            SLog.info("It took " + (after - before) + "ms to cleanup the JVM heap");
                        }
                    }
                }
            }
        }
        else {
            this.send((Object)ChatColor.RED + "no, bad.");
        }
    }
}
