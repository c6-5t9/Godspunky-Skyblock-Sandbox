package net.hypixel.skyblock.command;

import java.util.regex.Matcher;
import java.time.temporal.TemporalAmount;
import java.time.Duration;
import java.time.Instant;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.Iterator;
import org.bukkit.entity.Player;
import java.io.IOException;
import org.apache.commons.lang.RandomStringUtils;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import net.hypixel.skyblock.SkyBlock;
import org.bukkit.Bukkit;
import java.util.regex.Pattern;
import net.hypixel.skyblock.config.Config;
import net.hypixel.skyblock.features.ranks.PlayerRank;

@CommandParameters(description = "tempban player", aliases = "tempban", permission = PlayerRank.MOD)
public class TempBan extends SCommand
{
    public static Config config;
    private static final Pattern periodPattern;
    
    @Override
    public void run(final CommandSource sender, final String[] args) {
        if (args.length >= 3) {
            String reason = "";
            for (int i = 2; i < args.length; ++i) {
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
            final long unixTime = System.currentTimeMillis() / 1000L;
            final long banTime = parsePeriod(args[1]) / 1000L - 1L;
            if (banTime < 59L) {
                sender.send("§cYou can not ban someone for less than 1 minute.");
            }
            else if (loadConfiguration.contains(uuid)) {
                if (!loadConfiguration.getBoolean(String.valueOf((Object)uuid) + ".ban.isbanned")) {
                    try {
                        final String pwd = RandomStringUtils.random(8, "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789");
                        loadConfiguration.set(String.valueOf((Object)uuid) + ".ban.isbanned", (Object)true);
                        loadConfiguration.set(String.valueOf((Object)uuid) + ".ban.reason", (Object)reason2);
                        loadConfiguration.set(String.valueOf((Object)uuid) + ".ban.length", (Object)(unixTime + banTime));
                        loadConfiguration.set(String.valueOf((Object)uuid) + ".ban.id", (Object)pwd);
                        loadConfiguration.save(playerfile);
                        if (target != null) {
                            sender.send("§aTempbanned " + Bukkit.getPlayer(args[0]).getName() + " for " + args[1] + " for " + reason2);
                            target.kickPlayer("§cYou are temporarily banned for §f" + calculateTime(loadConfiguration.getInt(String.valueOf((Object)uuid) + ".ban.length") - unixTime) + " §cfrom this server!\n\n§7Reason: §f" + loadConfiguration.getString(String.valueOf((Object)uuid) + ".ban.reason") + "\n§7Find out more: §b§n" + TempBan.config.getString("discord") + "\n\n§7Ban ID: §f#" + loadConfiguration.getString(String.valueOf((Object)uuid) + ".ban.id") + "\n§7Sharing your Ban ID may affect the processing of your appeal!");
                        }
                        else {
                            sender.send("§aTempbanned " + args[0] + " for " + args[1] + " for " + reason2);
                        }
                    }
                    catch (final IOException exception) {
                        exception.printStackTrace();
                    }
                }
                sender.send("§cPlayer is already banned!");
            }
        }
        sender.send("§cInvalid syntax. Correct: /tempban <name> <length> <reason>");
    }
    
    public static String calculateTime(final long seconds) {
        final int days = (int)TimeUnit.SECONDS.toDays(seconds);
        final long hours = TimeUnit.SECONDS.toHours(seconds) - days * 24;
        final long minute = TimeUnit.SECONDS.toMinutes(seconds) - TimeUnit.SECONDS.toHours(seconds) * 60L;
        final long second = TimeUnit.SECONDS.toSeconds(seconds) - TimeUnit.SECONDS.toMinutes(seconds) * 60L;
        final String time = (" " + days + "d " + hours + "h " + minute + "m " + second + "s").toString().replace((CharSequence)" 0d", (CharSequence)"").replace((CharSequence)" 0h", (CharSequence)"").replace((CharSequence)" 0m", (CharSequence)"").replace((CharSequence)" 0s", (CharSequence)"").replaceFirst(" ", "");
        return time;
    }
    
    public static Long parsePeriod(final String period) {
        if (period == null) {
            return null;
        }
        final Matcher matcher = TempBan.periodPattern.matcher((CharSequence)period.toLowerCase(Locale.ENGLISH));
        Instant instant = Instant.EPOCH;
        while (matcher.find()) {
            final int num = Integer.parseInt(matcher.group(1));
            final String typ = matcher.group(2);
            switch (typ.hashCode()) {
                case 100: {
                    if (!typ.equals((Object)"d")) {
                        continue;
                    }
                    instant = instant.plus((TemporalAmount)Duration.ofDays((long)num));
                    continue;
                }
                case 104: {
                    if (!typ.equals((Object)"h")) {
                        continue;
                    }
                    instant = instant.plus((TemporalAmount)Duration.ofHours((long)num));
                    continue;
                }
                case 109: {
                    if (!typ.equals((Object)"m")) {
                        continue;
                    }
                    instant = instant.plus((TemporalAmount)Duration.ofMinutes((long)num));
                    continue;
                }
            }
        }
        return instant.toEpochMilli();
    }
    
    static {
        TempBan.config = SkyBlock.getPlugin().config;
        periodPattern = Pattern.compile("([0-9]+)([hdwmy])");
    }
}
