package net.hypixel.skyblock.api.worldmanager;

import org.bukkit.entity.Player;
import java.io.OutputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.io.IOException;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.File;
import org.bukkit.Bukkit;
import org.bukkit.World;

public class SkyBlockWorldManager
{
    private final World bukkitWorld;
    
    public SkyBlockWorldManager(final World world) {
        this.bukkitWorld = world;
    }
    
    public static void loadWorlds() {
        new BlankWorldCreator("f6").createWorld();
    }
    
    public boolean unload(final boolean save) {
        try {
            Bukkit.unloadWorld(this.bukkitWorld, save);
            return true;
        }
        catch (final Exception ignored) {
            return false;
        }
    }
    
    public void delete() {
        final World hub = Bukkit.getWorld("world");
        this.bukkitWorld.getPlayers().forEach(player -> player.teleport(hub.getSpawnLocation()));
        this.deleteWorldFolder(this.bukkitWorld.getWorldFolder());
    }
    
    private void deleteWorldFolder(final File folder) {
        this.unload(false);
        if (folder.isDirectory()) {
            final File[] files = folder.listFiles();
            if (null != files) {
                for (final File file : files) {
                    this.deleteWorldFolder(file);
                }
            }
        }
        if (!folder.delete()) {
            System.out.println("Failed to delete file or directory: " + (Object)folder);
        }
    }
    
    public void cloneWorld(final String newWorldName) {
        final File copiedFile = new File(Bukkit.getWorldContainer(), newWorldName);
        this.copyFileStructure(this.bukkitWorld.getWorldFolder(), copiedFile);
        new BlankWorldCreator(newWorldName).createWorld();
    }
    
    private void copyFileStructure(final File source, final File target) {
        try {
            final ArrayList<String> ignore = (ArrayList<String>)new ArrayList((Collection)Arrays.asList((Object[])new String[] { "uid.dat", "session.lock" }));
            if (!ignore.contains((Object)source.getName())) {
                if (source.isDirectory()) {
                    if (!target.exists() && !target.mkdirs()) {
                        throw new IOException("Couldn't create world directory!");
                    }
                    final String[] list;
                    final String[] files = list = source.list();
                    for (final String file : list) {
                        final File srcFile = new File(source, file);
                        final File destFile = new File(target, file);
                        this.copyFileStructure(srcFile, destFile);
                    }
                }
                else {
                    final InputStream in = Files.newInputStream(source.toPath(), new OpenOption[0]);
                    final OutputStream out = Files.newOutputStream(target.toPath(), new OpenOption[0]);
                    final byte[] buffer = new byte[1024];
                    int length;
                    while (0 < (length = in.read(buffer))) {
                        out.write(buffer, 0, length);
                    }
                    in.close();
                    out.close();
                }
            }
        }
        catch (final IOException e) {
            throw new RuntimeException((Throwable)e);
        }
    }
}
