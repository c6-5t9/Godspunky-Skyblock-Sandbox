package net.hypixel.skyblock.entity.dungeons.boss.sadan;

import net.minecraft.server.v1_8_R3.PlayerConnection;
import net.minecraft.server.v1_8_R3.Scoreboard;
import org.bukkit.craftbukkit.v1_8_R3.scoreboard.CraftScoreboard;
import org.bukkit.craftbukkit.v1_8_R3.scoreboard.CraftScoreboardManager;
import java.util.Iterator;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntity;
import net.hypixel.skyblock.util.SUtil;
import net.minecraft.server.v1_8_R3.PacketPlayOutBed;
import java.util.Collections;
import net.minecraft.server.v1_8_R3.PacketPlayOutScoreboardTeam;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.PacketPlayOutNamedEntitySpawn;
import net.minecraft.server.v1_8_R3.Packet;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_8_R3.ScoreboardTeamBase;
import net.minecraft.server.v1_8_R3.ScoreboardTeam;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityMetadata;
import net.minecraft.server.v1_8_R3.DataWatcher;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityTeleport;
import net.minecraft.server.v1_8_R3.BlockPosition;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import java.util.Collection;
import net.minecraft.server.v1_8_R3.WorldServer;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.World;
import net.minecraft.server.v1_8_R3.PlayerInteractManager;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.GameProfile;
import java.util.UUID;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.Location;

public final class DeadBodyMaker
{
    private static final byte PLAYER_SLEEP_HEIGHT_FIX = 1;
    
    public static Object[] createPlayer(final Location loc) {
        final MinecraftServer minecraftServer = ((CraftServer)Bukkit.getServer()).getServer();
        final String name = SadanFunction.generateRandom();
        final WorldServer world = ((CraftWorld)loc.getWorld()).getHandle();
        final GameProfile botGameProfile = new GameProfile(UUID.randomUUID(), name);
        final GameProfile playerProfile = new GameProfile(UUID.randomUUID(), name);
        playerProfile.getProperties().put((Object)"textures", (Object)new Property("textures", "ewogICJ0aW1lc3RhbXAiIDogMTYxMjAyOTExOTA2MiwKICAicHJvZmlsZUlkIiA6ICI5ZDQyNWFiOGFmZjg0MGU1OWM3NzUzZjc5Mjg5YjMyZSIsCiAgInByb2ZpbGVOYW1lIiA6ICJUb21wa2luNDIiLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmEwNmNiMGM0NzFjMWM5YmMxNjlhZjI3MGNkNDY2ZWE3MDE5NDY3NzYwNTZlNDcyZWNkYWViNDlmMGY0YTRkYyIKICAgIH0KICB9Cn0=", "mpSbfgDmWvoHASfqQ+poj2b4Y0QEZYh4QlqcCqHrZ4DNKY7mIenlbY2s7Ptmhb46dONt5OVfHb1pDLDlCPnYP9QYDXhl/wR99wxA4F7HHjs1g1omvZBfGRCvwHU/Bc3aWhjlaKZCVotf0snzrPTWIHFYnQoVLnhXoz19b3SQfdztIipZoZFKgMxwM2l4y+hBS9p7b/u2loz6/kVLBiLxzzYtAayF+ekma+bWlQcqhdsaf/BAJJSjh/UtipZLvAo4L2E2JlBsoKhj9PVSRVk4eAS1KE7p9Dupbrr/Ypj4bYVpUH5KhMJlQn7vCGoWILwd1NjFWk6KVlGUCag8/3pE1BNeD5d3QOfiVCkFH/rofRfS0/w0Nv8ROK0JQP/cFaAQ3kQ2ilvifF0kzPiA1M7si22lbXGyLqhQAVFsNSgKIU0Fe2qfD536Rr+kkBc/sVAzfVh4ajfsOXtMuMoZGIDJULpA1RD9qsybGvl7kkVQd2jPzlvZD8Ef8ZW8wr64Lu+/zZEj30zISIKZiwIsMKM2vOO7eqbfTs+tu0BNKKjiRg7uLF0qhyCpQrlJENzFud04ZiaTyI1Btt2LpOHQmKASWfg7/TEr8rPVPWiVqRBPCpHe5xJlAtQc2+PrtBO8u+qG3TTRKVci2a+Mpx1SwuPtMY2ZRj1NmYW3yBuu9pQnvlg="));
        final Collection<Property> skinProperty = (Collection<Property>)playerProfile.getProperties().get((Object)"textures");
        botGameProfile.getProperties().putAll((Object)"textures", (Iterable)skinProperty);
        final PlayerInteractManager interactManager = new PlayerInteractManager((World)world);
        final EntityPlayer botPlayer = new EntityPlayer(minecraftServer, world, botGameProfile, interactManager);
        final Location playerLocation = loc;
        botPlayer.setLocation(playerLocation.getX(), playerLocation.getY(), playerLocation.getZ(), playerLocation.getYaw(), playerLocation.getPitch());
        return new Object[] { botPlayer, name };
    }
    
    public static EntityPlayer spawn(final Location location) {
        final Location bedLocation = new Location(location.getWorld(), 1.0, 1.0, 1.0);
        final Location deathLocation = location;
        for (final Player pl : Bukkit.getOnlinePlayers()) {
            pl.sendBlockChange(bedLocation, Material.BED_BLOCK, (byte)0);
        }
        final BlockPosition deathBlockPosition = new BlockPosition(bedLocation.getBlockX(), bedLocation.getBlockY(), bedLocation.getBlockZ());
        final Object[] obj = createPlayer(bedLocation);
        final String name = (String)obj[1];
        final EntityPlayer botPlayer = (EntityPlayer)obj[0];
        botPlayer.setLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
        final PacketPlayOutEntityTeleport astp = new PacketPlayOutEntityTeleport((Entity)botPlayer);
        final PacketPlayOutPlayerInfo pack = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, new EntityPlayer[] { botPlayer });
        final DataWatcher data = new DataWatcher((Entity)botPlayer);
        data.a(10, (Object)127);
        final PacketPlayOutEntityMetadata packet = new PacketPlayOutEntityMetadata(botPlayer.getId(), data, true);
        final CraftScoreboardManager scoreboardManager = ((CraftServer)Bukkit.getServer()).getScoreboardManager();
        assert scoreboardManager != null;
        final CraftScoreboard mainScoreboard = scoreboardManager.getMainScoreboard();
        final Scoreboard scoreboard = mainScoreboard.getHandle();
        ScoreboardTeam scoreboardTeam = scoreboard.getTeam(name);
        if (scoreboardTeam == null) {
            scoreboardTeam = new ScoreboardTeam(scoreboard, name);
        }
        scoreboardTeam.setNameTagVisibility(ScoreboardTeamBase.EnumNameTagVisibility.NEVER);
        scoreboardTeam.setPrefix((Object)ChatColor.DARK_GRAY + "[NPC] ");
        for (final Player p : location.getWorld().getPlayers()) {
            final PlayerConnection conn = ((CraftPlayer)p).getHandle().playerConnection;
            conn.sendPacket((Packet)astp);
            conn.sendPacket((Packet)new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, new EntityPlayer[] { botPlayer }));
            conn.sendPacket((Packet)new PacketPlayOutNamedEntitySpawn((EntityHuman)botPlayer));
            conn.sendPacket((Packet)packet);
            sendPacketBundle(p, new Packet[] { (Packet)new PacketPlayOutScoreboardTeam(scoreboardTeam, 1), (Packet)new PacketPlayOutScoreboardTeam(scoreboardTeam, 0), (Packet)new PacketPlayOutScoreboardTeam(scoreboardTeam, (Collection)Collections.singletonList((Object)name), 3) });
            conn.sendPacket((Packet)new PacketPlayOutBed((EntityHuman)botPlayer, deathBlockPosition));
            SUtil.delay(() -> conn.sendPacket((Packet)pack), 10L);
            conn.sendPacket((Packet)new PacketPlayOutEntity.PacketPlayOutRelEntityMove(botPlayer.getId(), (byte)0, (byte)1, (byte)0, true));
        }
        return botPlayer;
    }
    
    private static void sendPacketBundle(final Player p, final Packet[] pk) {
        for (int i = 0; i < pk.length; ++i) {
            ((CraftPlayer)p).getHandle().playerConnection.sendPacket(pk[i]);
        }
    }
}
