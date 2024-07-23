package net.hypixel.skyblock.item;

import java.util.Iterator;
import org.bukkit.configuration.ConfigurationSection;
import net.hypixel.skyblock.util.SUtil;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.Location;
import net.hypixel.skyblock.SkyBlock;

public class SBlock
{
    protected static final SkyBlock plugin;
    private final Location location;
    private SMaterial type;
    private final NBTTagCompound data;
    
    public SBlock(final Location location, final SMaterial type, final NBTTagCompound data) {
        this.location = location;
        this.type = type;
        this.data = data;
    }
    
    public float getDataFloat(final String key) {
        return this.data.getFloat(key);
    }
    
    public long getDataLong(final String key) {
        return this.data.getLong(key);
    }
    
    public double getDataDouble(final String key) {
        return this.data.getDouble(key);
    }
    
    public String getDataString(final String key) {
        return this.data.getString(key);
    }
    
    public static SBlock getBlock(final Location location) {
        final ConfigurationSection cs = SBlock.plugin.blocks.getConfigurationSection(toLocationString(location));
        if (null == cs) {
            return null;
        }
        final NBTTagCompound compound = new NBTTagCompound();
        for (final String key : cs.getKeys(false)) {
            if (key.equals((Object)"type")) {
                continue;
            }
            compound.set(key, SUtil.getBaseFromObject(cs, key));
        }
        return new SBlock(location, SMaterial.getMaterial(cs.getString("type")), compound);
    }
    
    public void save() {
        SBlock.plugin.blocks.set(this.toLocationString() + ".type", (Object)this.type.name());
        for (final String key : this.data.c()) {
            final Object o = SUtil.getObjectFromCompound(this.data, key);
            if (o instanceof NBTTagCompound) {
                continue;
            }
            SBlock.plugin.blocks.set(this.toLocationString() + "." + key, o);
        }
        SBlock.plugin.blocks.save();
    }
    
    public void delete() {
        SBlock.plugin.blocks.set(this.toLocationString(), (Object)null);
        SBlock.plugin.blocks.save();
    }
    
    private String toLocationString() {
        return toLocationString(this.location);
    }
    
    private static String toLocationString(final Location location) {
        return location.getBlockX() + "," + location.getBlockY() + "," + location.getBlockZ() + "," + location.getWorld().getName();
    }
    
    public Location getLocation() {
        return this.location;
    }
    
    public SMaterial getType() {
        return this.type;
    }
    
    public NBTTagCompound getData() {
        return this.data;
    }
    
    public void setType(final SMaterial type) {
        this.type = type;
    }
    
    static {
        plugin = SkyBlock.getPlugin();
    }
}
