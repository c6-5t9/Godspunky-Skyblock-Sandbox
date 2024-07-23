package net.hypixel.skyblock.util;

import java.util.Iterator;
import com.xxmicloxx.NoteBlockAPI.model.Song;
import org.bukkit.plugin.Plugin;
import net.hypixel.skyblock.entity.dungeons.boss.sadan.SadanHuman;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.entity.Player;
import com.xxmicloxx.NoteBlockAPI.songplayer.PositionSongPlayer;
import com.xxmicloxx.NoteBlockAPI.utils.NBSDecoder;
import java.io.File;
import net.hypixel.skyblock.SkyBlock;
import org.bukkit.Location;

public class PlayJingle
{
    public static void play(final SkySimSong eff, final byte volume, final Location loc, final int radius) {
        final Song song = NBSDecoder.parse(new File((Object)SkyBlock.getPlugin().getDataFolder() + File.separator + "/songs/dungeon_drama.nbs"));
        final PositionSongPlayer esp = new PositionSongPlayer(song);
        esp.setDistance(radius);
        esp.setVolume((byte)100);
        esp.setLoop(true);
        esp.setTargetLocation(loc);
        for (final Player p : loc.getWorld().getPlayers()) {
            esp.addPlayer(p);
        }
        esp.setPlaying(true);
        new BukkitRunnable() {
            public void run() {
                if (loc.getWorld() == null) {
                    esp.destroy();
                    this.cancel();
                    return;
                }
                if (SadanHuman.IsMusicPlaying.containsKey((Object)loc.getWorld().getUID()) && !(boolean)SadanHuman.IsMusicPlaying.get((Object)loc.getWorld().getUID())) {
                    esp.destroy();
                    this.cancel();
                }
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 0L, 1L);
    }
    
    public enum SkySimSong
    {
        WIDE_IS_MY_MOTHERLAND, 
        DUNGEONS_DRAMA, 
        FLOOR7_NECRONTHEME, 
        THE_WATCHER, 
        SYSTEM_JINGLE, 
        FESTIVAL_NEW_YEAR, 
        FESTIVAL_LUNAR_NEW_YEAR, 
        FESTIVAL_CHRISTMAS, 
        FESTIVAL_VICTORY_DAY, 
        FESTIVAL_SKYSIM_ANNIVERSARY_DAY, 
        FESTIVAL_MAY_DAY;
    }
    
    public enum SkySimSoundEffect
    {
        DUNGEONS_WITHER_DOOR_OPEN, 
        DUNGEONS_BLOOD_DOOR_OPEN, 
        DUNGEONS_WATCHER_AMBIENT, 
        DUNGEONS_WATCHER_SUMMON, 
        DUNGEONS_WATCHER_HURT, 
        LASER_SHOOT, 
        LASER_AMBIENT;
    }
}
