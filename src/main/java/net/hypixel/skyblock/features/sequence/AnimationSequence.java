package net.hypixel.skyblock.features.sequence;

import org.bukkit.plugin.Plugin;
import net.hypixel.skyblock.SkyBlock;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.Effect;
import org.bukkit.entity.Entity;
import java.util.Iterator;
import org.bukkit.Location;
import java.util.Arrays;
import java.util.List;

public class AnimationSequence implements Sequence
{
    private final List<DelayedAnimation> animations;
    
    public AnimationSequence(final DelayedAnimation... animations) {
        this.animations = (List<DelayedAnimation>)Arrays.asList((Object[])animations);
    }
    
    public void append(final DelayedAnimation sound) {
        this.animations.add((Object)sound);
    }
    
    @Override
    public void play(final Location location) {
        for (final DelayedAnimation animation : this.animations) {
            animation.play(location);
        }
    }
    
    @Override
    public void play(final Entity entity) {
        for (final DelayedAnimation animation : this.animations) {
            animation.play(entity);
        }
    }
    
    public static class DelayedAnimation
    {
        private final Effect effect;
        private final int data;
        private final long delay;
        private final float speed;
        private final int particleCount;
        
        public DelayedAnimation(final Effect effect, final int data, final long delay, final float speed, final int particleCount) {
            this.effect = effect;
            this.data = data;
            this.delay = delay;
            this.speed = speed;
            this.particleCount = particleCount;
        }
        
        public void play(final Location location) {
            new BukkitRunnable() {
                public void run() {
                    location.getWorld().spigot().playEffect(location, DelayedAnimation.this.effect, 1, DelayedAnimation.this.data, 0.0f, 0.0f, 0.0f, DelayedAnimation.this.speed, DelayedAnimation.this.particleCount, 16);
                }
            }.runTaskLater((Plugin)SkyBlock.getPlugin(), this.delay);
        }
        
        public void play(final Entity entity) {
            this.play(entity.getLocation());
        }
    }
}
