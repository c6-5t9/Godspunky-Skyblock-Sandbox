package net.hypixel.skyblock.command;

import java.util.Iterator;
import org.bukkit.entity.Player;
import java.io.IOException;
import org.apache.commons.lang.RandomStringUtils;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import net.hypixel.skyblock.SkyBlock;
import org.bukkit.Bukkit;
import net.hypixel.skyblock.config.Config;
import net.hypixel.skyblock.features.ranks.PlayerRank;

@CommandParameters(description = "ban player", aliases = "ban", permission = PlayerRank.MOD)
public class Ban extends SCommand
{
    public static Config config;
    
    @Override
    public void run(final CommandSource sender, final String[] args) {
        if (args.length >= 2) {
            String reason = "";
            for (int i = 1; i < args.length; ++i) {
                reason = String.valueOf((Object)reason) + args[i] + " ";
            }
            final String reason2 = reason.substring(0, reason.length() - 1);
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
                if (!loadConfiguration.getBoolean(String.valueOf((Object)uuid) + ".ban.isbanned")) {
                    try {
                        final String pwd = RandomStringUtils.random(8, "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789");
                        loadConfiguration.set(String.valueOf((Object)uuid) + ".ban.isbanned", (Object)true);
                        loadConfiguration.set(String.valueOf((Object)uuid) + ".ban.reason", (Object)reason2);
                        loadConfiguration.set(String.valueOf((Object)uuid) + ".ban.length", (Object)(-1));
                        loadConfiguration.set(String.valueOf((Object)uuid) + ".ban.id", (Object)pwd);
                        loadConfiguration.save(playerfile);
                        if (target == null) {
                            sender.send("§aPermanently banned " + args[0] + " for " + reason2);
                        }
                        if (target != null) {
                            sender.send("§aPermanently banned " + Bukkit.getPlayer(args[0]).getName() + " for " + reason2);
                            target.getPlayer().kickPlayer("§cYou are permanently banned from this server!\n\n§7Reason: §f" + loadConfiguration.getString(String.valueOf((Object)uuid) + ".ban.reason") + "\n§7Find out more: §b§n" + Ban.config.getString("discord") + "\n\n§7Ban ID: §f#" + loadConfiguration.getString(String.valueOf((Object)uuid) + ".ban.id") + "\n§7Sharing your Ban ID may affect the processing of your appeal!");
                        }
                    }
                    catch (final IOException exception) {
                        exception.printStackTrace();
                    }
                }
                sender.send("§cPlayer is already banned!");
            }
        }
        sender.send("§cInvalid syntax. Correct: /ban <name> <reason>");
    }
    
    static {
        Ban.config = SkyBlock.getInstance().config;
    }
}
