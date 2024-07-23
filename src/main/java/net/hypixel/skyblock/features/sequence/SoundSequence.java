package net.hypixel.skyblock.features.sequence;

import org.bukkit.plugin.Plugin;
import net.hypixel.skyblock.SkyBlock;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import java.util.Iterator;
import org.bukkit.Location;
import java.util.Arrays;
import java.util.List;

public class SoundSequence implements Sequence
{
    private final List<DelayedSound> sounds;
    
    public SoundSequence(final DelayedSound... sounds) {
        this.sounds = (List<DelayedSound>)Arrays.asList((Object[])sounds);
    }
    
    public void append(final DelayedSound sound) {
        this.sounds.add((Object)sound);
    }
    
    @Override
    public void play(final Location location) {
        for (final DelayedSound sound : this.sounds) {
            sound.play(location);
        }
    }
    
    public void play(final Player p) {
        for (final DelayedSound sound : this.sounds) {
            sound.play(p);
        }
    }
    
    @Override
    public void play(final Entity entity) {
        this.play(entity.getLocation());
    }
    
    public static class DelayedSound
    {
        private final Sound sound;
        private final float volume;
        private final float pitch;
        private final long delay;
        
        public DelayedSound(final Sound sound, final float volume, final float pitch, final long delay) {
            this.sound = sound;
            this.volume = volume;
            this.pitch = pitch;
            this.delay = delay;
        }
        
        public DelayedSound(final Sound sound, final float volume, final float pitch) {
            this(sound, volume, pitch, 0L);
        }
        
        public void play(final Location location) {
            new BukkitRunnable() {
                public void run() {
                    location.getWorld().playSound(location, DelayedSound.this.sound, DelayedSound.this.volume, DelayedSound.this.pitch);
                }
            }.runTaskLater((Plugin)SkyBlock.getPlugin(), this.delay);
        }
        
        public void play(final Player entity) {
            new BukkitRunnable() {
                public void run() {
                    entity.playSound(entity.getLocation(), DelayedSound.this.sound, DelayedSound.this.volume, DelayedSound.this.pitch);
                }
            }.runTaskLater((Plugin)SkyBlock.getPlugin(), this.delay);
        }
    }
}
