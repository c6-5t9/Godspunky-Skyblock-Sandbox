package net.hypixel.skyblock.features.sequence;

import org.bukkit.Sound;
import java.util.ArrayList;
import java.util.Iterator;
import org.bukkit.entity.Player;
import org.bukkit.entity.Entity;
import org.bukkit.Location;
import java.util.List;

public class SoundSequenceType
{
    private static final List<SoundSequenceType> TYPES;
    public static final SoundSequenceType MADDOX_BATPHONE;
    public static final SoundSequenceType TRADE_COMPLETE;
    public static final SoundSequenceType SLAYER_BOSS_SPAWN;
    public static final SoundSequenceType SLAYER_MINIBOSS_SPAWN;
    private final String namespace;
    private final SoundSequence sequence;
    
    public SoundSequenceType(final String namespace, final SoundSequence sequence) {
        this.namespace = namespace;
        this.sequence = sequence;
        SoundSequenceType.TYPES.add((Object)this);
    }
    
    public String getNamespace() {
        return this.namespace;
    }
    
    public SoundSequence getSequence() {
        return this.sequence;
    }
    
    public void play(final Location location) {
        this.sequence.play(location);
    }
    
    public void play(final Entity entity) {
        this.sequence.play(entity);
    }
    
    public void play(final Player p) {
        this.sequence.play(p);
    }
    
    public static SoundSequenceType getByNamespace(final String namespace) {
        for (final SoundSequenceType type : SoundSequenceType.TYPES) {
            if (namespace.equalsIgnoreCase(type.namespace)) {
                return type;
            }
        }
        return null;
    }
    
    static {
        TYPES = (List)new ArrayList();
        MADDOX_BATPHONE = new SoundSequenceType("maddox_batphone", new SoundSequence(new SoundSequence.DelayedSound[] { new SoundSequence.DelayedSound(Sound.NOTE_PLING, 1.0f, 26.0f, 1L), new SoundSequence.DelayedSound(Sound.NOTE_PLING, 1.0f, 26.0f, 3L), new SoundSequence.DelayedSound(Sound.NOTE_PLING, 1.0f, 26.0f, 5L), new SoundSequence.DelayedSound(Sound.NOTE_PLING, 1.0f, 26.0f, 7L), new SoundSequence.DelayedSound(Sound.NOTE_PLING, 1.0f, 26.0f, 9L), new SoundSequence.DelayedSound(Sound.NOTE_PLING, 1.0f, 26.0f, 18L), new SoundSequence.DelayedSound(Sound.NOTE_PLING, 1.0f, 26.0f, 20L), new SoundSequence.DelayedSound(Sound.NOTE_PLING, 1.0f, 26.0f, 22L), new SoundSequence.DelayedSound(Sound.NOTE_PLING, 1.0f, 26.0f, 24L), new SoundSequence.DelayedSound(Sound.NOTE_PLING, 1.0f, 26.0f, 26L), new SoundSequence.DelayedSound(Sound.NOTE_PLING, 1.0f, 26.0f, 35L), new SoundSequence.DelayedSound(Sound.NOTE_PLING, 1.0f, 26.0f, 37L), new SoundSequence.DelayedSound(Sound.NOTE_PLING, 1.0f, 26.0f, 39L), new SoundSequence.DelayedSound(Sound.NOTE_PLING, 1.0f, 26.0f, 41L), new SoundSequence.DelayedSound(Sound.NOTE_PLING, 1.0f, 26.0f, 43L), new SoundSequence.DelayedSound(Sound.WOOD_CLICK, 1.0f, 1.0f, 52L) }));
        TRADE_COMPLETE = new SoundSequenceType("trade_completed", new SoundSequence(new SoundSequence.DelayedSound[] { new SoundSequence.DelayedSound(Sound.NOTE_PLING, 0.3f, 0.5f, 1L), new SoundSequence.DelayedSound(Sound.NOTE_PLING, 0.3f, 0.69f, 1L), new SoundSequence.DelayedSound(Sound.NOTE_PLING, 0.3f, 0.84f, 1L), new SoundSequence.DelayedSound(Sound.NOTE_PLING, 0.3f, 1.12f, 3L), new SoundSequence.DelayedSound(Sound.NOTE_PLING, 0.3f, 0.55f, 3L), new SoundSequence.DelayedSound(Sound.NOTE_PLING, 0.3f, 0.69f, 3L), new SoundSequence.DelayedSound(Sound.NOTE_PLING, 0.3f, 0.84f, 5L), new SoundSequence.DelayedSound(Sound.NOTE_PLING, 0.3f, 1.12f, 5L) }));
        SLAYER_BOSS_SPAWN = new SoundSequenceType("slayer_boss_spawn", new SoundSequence(new SoundSequence.DelayedSound[] { new SoundSequence.DelayedSound(Sound.WITHER_SHOOT, 1.0f, 9.0f, 1L), new SoundSequence.DelayedSound(Sound.WITHER_SHOOT, 1.0f, 9.0f, 4L), new SoundSequence.DelayedSound(Sound.WITHER_SHOOT, 1.0f, 5.0f, 7L), new SoundSequence.DelayedSound(Sound.WITHER_SHOOT, 1.0f, 5.0f, 10L), new SoundSequence.DelayedSound(Sound.WITHER_SHOOT, 1.0f, 5.0f, 13L), new SoundSequence.DelayedSound(Sound.WITHER_SHOOT, 1.0f, 5.0f, 16L), new SoundSequence.DelayedSound(Sound.WITHER_SHOOT, 1.0f, 1.0f, 19L), new SoundSequence.DelayedSound(Sound.WITHER_SHOOT, 1.0f, 1.0f, 22L), new SoundSequence.DelayedSound(Sound.WITHER_SHOOT, 1.0f, 1.0f, 25L), new SoundSequence.DelayedSound(Sound.WITHER_SPAWN, 1.0f, -25.0f, 28L), new SoundSequence.DelayedSound(Sound.EXPLODE, 1.0f, 1.0f, 28L) }));
        SLAYER_MINIBOSS_SPAWN = new SoundSequenceType("slayer_miniboss_spawn", new SoundSequence(new SoundSequence.DelayedSound[] { new SoundSequence.DelayedSound(Sound.EXPLODE, 1.0f, 2.0f, 0L), new SoundSequence.DelayedSound(Sound.EXPLODE, 1.0f, 2.0f, 3L), new SoundSequence.DelayedSound(Sound.EXPLODE, 1.0f, 2.0f, 6L), new SoundSequence.DelayedSound(Sound.EXPLODE, 1.0f, 2.0f, 9L), new SoundSequence.DelayedSound(Sound.EXPLODE, 1.0f, 2.0f, 12L) }));
    }
}
