package net.hypixel.skyblock.features.dungeons;

import org.bukkit.entity.Player;
import java.util.ArrayList;
import org.bukkit.World;
import java.util.UUID;

public class Dungeon
{
    private final UUID uuid;
    private final World world;
    private ArrayList<Player> dungeonsmate;
    private int deaths;
    private int totalsecretfound;
    private int score;
    private int percentagecomplete;
    private boolean isBloodCleared;
    private boolean bloodkey;
    private int witherkeys;
    
    public Dungeon(final UUID uuid, final World world, final ArrayList<Player> dungeonmembers) {
        this.dungeonsmate = (ArrayList<Player>)new ArrayList();
        this.world = world;
        this.deaths = 0;
        this.uuid = uuid;
        this.score = 0;
        this.totalsecretfound = 0;
        this.percentagecomplete = 0;
        this.dungeonsmate = dungeonmembers;
        this.bloodkey = false;
        this.witherkeys = 0;
    }
    
    public int getScore() {
        return this.score;
    }
    
    public World getOperatingWorld() {
        return this.world;
    }
    
    public void setScore(final int sc) {
        this.score = sc;
    }
    
    public UUID getRunUUID() {
        return this.uuid;
    }
    
    public boolean getBloodKey() {
        return this.bloodkey;
    }
    
    public void setBloodKey(final boolean bk) {
        this.bloodkey = bk;
    }
    
    public int getDungeonCompletePercent() {
        return this.percentagecomplete;
    }
    
    public void setBloodKey(final int percent) {
        this.percentagecomplete = percent;
    }
    
    public int getAllSecrets() {
        return this.totalsecretfound;
    }
    
    public void setSecretAmount(final int tsf) {
        this.totalsecretfound = tsf;
    }
    
    public int getWitherKeys() {
        return this.witherkeys;
    }
    
    public void setWithersKey(final int wk) {
        this.witherkeys = wk;
    }
    
    public int getDeaths() {
        return this.deaths;
    }
    
    public void setDeaths(final int d) {
        this.deaths = d;
    }
    
    public ArrayList<Player> getAllDungeonsMembers() {
        return this.dungeonsmate;
    }
    
    public void addPlayer(final Player p) {
        this.dungeonsmate.add((Object)p);
    }
    
    public boolean removePlayer(final Player p) {
        boolean success = false;
        if (this.dungeonsmate.contains((Object)p)) {
            this.dungeonsmate.remove((Object)p);
            success = true;
        }
        return success;
    }
    
    public boolean isBloodCleared() {
        return this.isBloodCleared;
    }
}
