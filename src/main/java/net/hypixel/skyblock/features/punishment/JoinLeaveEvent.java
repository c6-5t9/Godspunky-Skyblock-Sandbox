package net.hypixel.skyblock.features.punishment;

import java.util.concurrent.TimeUnit;
import java.io.IOException;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import net.hypixel.skyblock.SkyBlock;
import org.bukkit.event.player.PlayerQuitEvent;
import net.hypixel.skyblock.config.Config;
import net.hypixel.skyblock.listener.PListener;

public class JoinLeaveEvent extends PListener
{
    public static Config config;
    
    @EventHandler
    public void onLeave(final PlayerQuitEvent event) {
        final String uuid = event.getPlayer().getUniqueId().toString();
        final File playerfile = new File((Object)((SkyBlock)SkyBlock.getPlugin((Class)SkyBlock.class)).getDataFolder() + File.separator, "punishments.yml");
        final YamlConfiguration loadConfiguration = YamlConfiguration.loadConfiguration(playerfile);
        if (loadConfiguration.contains(uuid) && loadConfiguration.getBoolean(String.valueOf((Object)uuid) + ".ban.isbanned")) {
            event.setQuitMessage((String)null);
        }
    }
    
    @EventHandler
    public void onJoin(final PlayerJoinEvent event) {
        final File playerfile = new File((Object)((SkyBlock)SkyBlock.getPlugin((Class)SkyBlock.class)).getDataFolder() + File.separator, "punishments.yml");
        final YamlConfiguration loadConfiguration = YamlConfiguration.loadConfiguration(playerfile);
        final String uuid = event.getPlayer().getUniqueId().toString();
        final long unixTime = System.currentTimeMillis() / 1000L;
        if (loadConfiguration.contains(uuid) && loadConfiguration.getBoolean(String.valueOf((Object)uuid) + ".ban.isbanned")) {
            if (loadConfiguration.getInt(String.valueOf((Object)uuid) + ".ban.length") <= unixTime && loadConfiguration.getInt(String.valueOf((Object)uuid) + ".ban.length") != -1) {
                try {
                    loadConfiguration.set(String.valueOf((Object)uuid) + ".ban.isbanned", (Object)false);
                    loadConfiguration.set(String.valueOf((Object)uuid) + ".ban.reason", (Object)"");
                    loadConfiguration.set(String.valueOf((Object)uuid) + ".ban.length", (Object)0);
                    loadConfiguration.set(String.valueOf((Object)uuid) + ".ban.id", (Object)"");
                    loadConfiguration.save(playerfile);
                }
                catch (final IOException exception) {
                    exception.printStackTrace();
                }
            }
            event.setJoinMessage((String)null);
            if (loadConfiguration.getInt(String.valueOf((Object)uuid) + ".ban.length") == -1) {
                event.getPlayer().kickPlayer("§cYou are permanently banned from this server!\n\n§7Reason: §f" + loadConfiguration.getString(String.valueOf((Object)uuid) + ".ban.reason") + "\n§7Find out more: §b§n" + JoinLeaveEvent.config.getString("discord") + "\n\n§7Ban ID: §f#" + loadConfiguration.getString(String.valueOf((Object)uuid) + ".ban.id") + "\n§7Sharing your Ban ID may affect the processing of your appeal!");
            }
            else {
                if (loadConfiguration.getInt(String.valueOf((Object)uuid) + ".ban.length") == 0) {
                    return;
                }
                event.getPlayer().kickPlayer("§cYou are temporarily banned for §f" + calculateTime(loadConfiguration.getInt(String.valueOf((Object)uuid) + ".ban.length") - unixTime) + " §cfrom this server!\n\n§7Reason: §f" + loadConfiguration.getString(String.valueOf((Object)uuid) + ".ban.reason") + "\n§7Find out more: §b§n" + JoinLeaveEvent.config.getString("discord") + "\n\n§7Ban ID: §f#" + loadConfiguration.getString(String.valueOf((Object)uuid) + ".ban.id") + "\n§7Sharing your Ban ID may affect the processing of your appeal!");
            }
        }
        if (!loadConfiguration.contains(uuid)) {
            try {
                loadConfiguration.createSection(uuid);
                loadConfiguration.set(String.valueOf((Object)uuid) + ".name", (Object)event.getPlayer().getName());
                loadConfiguration.createSection(String.valueOf((Object)uuid) + ".ban");
                loadConfiguration.createSection(String.valueOf((Object)uuid) + ".mute");
                loadConfiguration.set(String.valueOf((Object)uuid) + ".ban.isbanned", (Object)false);
                loadConfiguration.set(String.valueOf((Object)uuid) + ".ban.reason", (Object)"");
                loadConfiguration.set(String.valueOf((Object)uuid) + ".ban.length", (Object)0);
                loadConfiguration.set(String.valueOf((Object)uuid) + ".ban.id", (Object)"");
                loadConfiguration.set(String.valueOf((Object)uuid) + ".mute.ismuted", (Object)false);
                loadConfiguration.set(String.valueOf((Object)uuid) + ".mute.reason", (Object)"");
                loadConfiguration.set(String.valueOf((Object)uuid) + ".mute.length", (Object)0);
                loadConfiguration.set(String.valueOf((Object)uuid) + ".mute.id", (Object)"");
                loadConfiguration.save(playerfile);
            }
            catch (final IOException exception2) {
                exception2.printStackTrace();
            }
        }
    }
    
    public static String calculateTime(final long seconds) {
        final int days = (int)TimeUnit.SECONDS.toDays(seconds);
        final long hours = TimeUnit.SECONDS.toHours(seconds) - days * 24;
        final long minute = TimeUnit.SECONDS.toMinutes(seconds) - TimeUnit.SECONDS.toHours(seconds) * 60L;
        final long second = TimeUnit.SECONDS.toSeconds(seconds) - TimeUnit.SECONDS.toMinutes(seconds) * 60L;
        final String time = (" " + days + "d " + hours + "h " + minute + "m " + second + "s").toString().replace((CharSequence)" 0d", (CharSequence)"").replace((CharSequence)" 0h", (CharSequence)"").replace((CharSequence)" 0m", (CharSequence)"").replace((CharSequence)" 0s", (CharSequence)"").replaceFirst(" ", "");
        return time;
    }
    
    static {
        JoinLeaveEvent.config = SkyBlock.getPlugin().config;
    }
}
