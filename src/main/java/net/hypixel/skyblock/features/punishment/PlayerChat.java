package net.hypixel.skyblock.features.punishment;

import java.util.concurrent.TimeUnit;
import org.bukkit.event.EventHandler;
import org.bukkit.entity.Player;
import java.io.IOException;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import net.hypixel.skyblock.SkyBlock;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import net.hypixel.skyblock.config.Config;
import net.hypixel.skyblock.listener.PListener;

public class PlayerChat extends PListener
{
    public static Config config;
    
    @EventHandler
    public void onChat(final AsyncPlayerChatEvent event) {
        final Player player = event.getPlayer();
        final File playerfile = new File((Object)((SkyBlock)SkyBlock.getPlugin((Class)SkyBlock.class)).getDataFolder() + File.separator, "punishments.yml");
        final YamlConfiguration loadConfiguration = YamlConfiguration.loadConfiguration(playerfile);
        final String uuid = event.getPlayer().getUniqueId().toString();
        final long unixTime = System.currentTimeMillis() / 1000L;
        if (loadConfiguration.contains(uuid) && loadConfiguration.getBoolean(String.valueOf((Object)uuid) + ".mute.ismuted")) {
            if (loadConfiguration.getInt(String.valueOf((Object)uuid) + ".mute.length") <= unixTime) {
                try {
                    loadConfiguration.set(String.valueOf((Object)uuid) + ".mute.ismuted", (Object)false);
                    loadConfiguration.set(String.valueOf((Object)uuid) + ".mute.reason", (Object)"");
                    loadConfiguration.set(String.valueOf((Object)uuid) + ".mute.length", (Object)0);
                    loadConfiguration.set(String.valueOf((Object)uuid) + ".mute.id", (Object)"");
                    loadConfiguration.save(playerfile);
                }
                catch (final IOException exception) {
                    exception.printStackTrace();
                }
            }
            if (loadConfiguration.getInt(String.valueOf((Object)uuid) + ".mute.length") <= 0) {
                return;
            }
            player.sendMessage("§c§l§m---------------------------------------------");
            player.sendMessage("§cYou are currently muted for " + loadConfiguration.getString(String.valueOf((Object)uuid) + ".mute.reason") + ".");
            player.sendMessage("§7Your mute will expire in §c" + calculateTime(loadConfiguration.getInt(String.valueOf((Object)uuid) + ".mute.length") - unixTime));
            player.sendMessage("");
            player.sendMessage("§7Find out more here: §e" + PlayerChat.config.getString("discord"));
            player.sendMessage("§7Mute ID: §f#" + loadConfiguration.getString(String.valueOf((Object)uuid) + ".mute.id"));
            player.sendMessage("§c§l§m---------------------------------------------");
            event.setCancelled(true);
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
        PlayerChat.config = SkyBlock.getInstance().config;
    }
}
