package net.hypixel.skyblock.user;

import org.bukkit.Location;
import java.util.ArrayList;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import java.util.UUID;
import java.util.HashMap;

public class PetsFollow
{
    public static HashMap<UUID, PetsFollow> pets;
    private Player player;
    private ArmorStand nametag;
    private ArmorStand petitem;
    private ArrayList<Location> locs;
    private Location loc;
    
    public void setPlayer(final Player player) {
        this.player = player;
    }
    
    public void setNameTagAS(final ArmorStand as) {
        this.nametag = as;
    }
    
    public void setPetAS(final ArmorStand as) {
        this.petitem = as;
    }
    
    public void addLocation(final Location loc) {
        this.locs.add((Object)loc);
    }
    
    public void setLocs(final ArrayList<Location> locs) {
        this.locs = locs;
    }
    
    public void setLoc(final Location loc) {
        this.loc = loc;
    }
    
    public void removeLoc(final int index) {
        if (index < this.locs.size() && this.locs.size() != 0) {
            this.locs.remove(index);
        }
    }
    
    public void removeLoc(final Location loc) {
        this.locs.remove((Object)loc);
    }
    
    public Location getLoc() {
        return this.loc;
    }
    
    public Player getPlayer() {
        return this.player;
    }
    
    public ArmorStand getNameTagAS() {
        return this.nametag;
    }
    
    public ArmorStand getPetItemAS() {
        return this.petitem;
    }
    
    public ArrayList<Location> getLocs() {
        return this.locs;
    }
    
    static {
        PetsFollow.pets = (HashMap<UUID, PetsFollow>)new HashMap();
    }
}
