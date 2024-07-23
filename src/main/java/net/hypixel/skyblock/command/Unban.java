package net.hypixel.skyblock.command;

import java.util.Iterator;
import org.bukkit.entity.Player;
import java.io.IOException;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import net.hypixel.skyblock.SkyBlock;
import org.bukkit.Bukkit;
import net.hypixel.skyblock.config.Config;
import net.hypixel.skyblock.features.ranks.PlayerRank;

@CommandParameters(description = "unban player", aliases = "unban", permission = PlayerRank.MOD)
public class Unban extends SCommand
{
    public static Config config;
    
    @Override
    public void run(final CommandSource sender, final String[] args) {
        if (args.length >= 1) {
            final Player target = Bukkit.getPlayerExact(args[0]);
            final File playerfile = new File((Object)((SkyBlock)SkyBlock.getPlugin((Class)SkyBlock.class)).getDataFolder() + File.separator, "punishments.yml");
            final YamlConfiguration loadConfiguration = YamlConfiguration.loadConfiguration(playerfile);
            String uuid = null;
            if (target != null) {
                uuid = target.getPlayer().getUniqueId().toString();
            }
            if (uuid == null) {
                for (final String key : loadConfiguration.getKeys(false)) {
                    if (loadConfiguration.getString(String.valueOf((Object)key) + ".name").equalsIgnoreCase(args[0])) {
                        uuid = key;
                    }
                }
            }
            if (uuid == null) {
                sender.send("§cPlayer does not exist.");
            }
            else if (loadConfiguration.contains(uuid)) {
                if (loadConfiguration.getBoolean(String.valueOf((Object)uuid) + ".ban.isbanned")) {
                    try {
                        loadConfiguration.set(String.valueOf((Object)uuid) + ".ban.isbanned", (Object)false);
                        loadConfiguration.set(String.valueOf((Object)uuid) + ".ban.reason", (Object)"");
                        loadConfiguration.set(String.valueOf((Object)uuid) + ".ban.length", (Object)0);
                        loadConfiguration.set(String.valueOf((Object)uuid) + ".ban.id", (Object)"");
                        loadConfiguration.save(playerfile);
                        if (target != null) {
                            sender.send("§aUnbanned " + Bukkit.getPlayer(args[0]).getName());
                        }
                        else {
                            sender.send("§aUnbanned " + args[0]);
                        }
                    }
                    catch (final IOException exception) {
                        exception.printStackTrace();
                    }
                }
                sender.send("§cPlayer is not banned!");
            }
        }
        sender.send("§cInvalid syntax. Correct: /unban <name>");
    }
    
    static {
        Unban.config = SkyBlock.getInstance().config;
    }
}
