package net.hypixel.skyblock.command;

import java.util.HashMap;
import java.util.Iterator;
import org.bukkit.plugin.Plugin;
import net.hypixel.skyblock.SkyBlock;
import net.hypixel.skyblock.util.SUtil;
import org.bukkit.scheduler.BukkitRunnable;
import net.hypixel.skyblock.util.Sputnik;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.command.CommandSender;
import org.bukkit.Bukkit;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Server;
import java.util.Map;
import net.hypixel.skyblock.features.ranks.PlayerRank;

@CommandParameters(description = "Spec test command.", aliases = "rebootserver", permission = PlayerRank.OWNER)
public class RebootServerCommand extends SCommand
{
    public static Map<Server, Integer> secondMap;
    
    @Override
    public void run(final CommandSource sender, final String[] args) {
        if (!sender.getPlayer().isOp()) {
            sender.getPlayer().sendMessage((Object)ChatColor.RED + "This command is highly restricted!");
            return;
        }
        if (RebootServerCommand.secondMap.containsKey((Object)Bukkit.getServer())) {
            this.send((Object)ChatColor.RED + "You cannot schedule more than 1 server reboot at a time");
            return;
        }
        String reasonraw = "";
        if (args[0].contains((CharSequence)"time")) {
            Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "ptitle2");
            reasonraw = "Scheduled Reboot";
        }
        else if (args[0].contains((CharSequence)"update")) {
            Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "ptitle1");
            reasonraw = "For a game update";
        }
        else {
            Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "ptitle3");
            reasonraw = "Unknown Reason!";
        }
        for (final Player p : Bukkit.getOnlinePlayers()) {
            p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0f, 0.0f);
        }
        final String reason = reasonraw;
        Bukkit.broadcastMessage(Sputnik.trans("&c[Important] &eThe server will restart soon: &b" + reason));
        Bukkit.broadcastMessage(Sputnik.trans("&eYou have &a30 seconds &eto disconnect to prevent &cdata corruptions &ethat can result to inventories wipes!"));
        RebootServerCommand.secondMap.put((Object)Bukkit.getServer(), (Object)30);
        new BukkitRunnable() {
            public void run() {
                RebootServerCommand.secondMap.put((Object)Bukkit.getServer(), (Object)((int)RebootServerCommand.secondMap.get((Object)Bukkit.getServer()) - 1));
                if (5 >= (int)RebootServerCommand.secondMap.get((Object)Bukkit.getServer()) && 0 < (int)RebootServerCommand.secondMap.get((Object)Bukkit.getServer())) {
                    Bukkit.broadcastMessage(Sputnik.trans("&c[Important] &eThe server will restart soon: &b" + reason));
                    Bukkit.broadcastMessage(Sputnik.trans("&eServer closing down in &c" + RebootServerCommand.secondMap.get((Object)Bukkit.getServer()) + " &eseconds"));
                }
                else if (0 >= (int)RebootServerCommand.secondMap.get((Object)Bukkit.getServer())) {
                    Bukkit.broadcastMessage(Sputnik.trans("&c[Important] &eThe server will restart soon: &b" + reason));
                    Bukkit.broadcastMessage(Sputnik.trans("&eServer is &cshutting down&e!"));
                    this.cancel();
                    SUtil.delay(() -> {
                        Bukkit.broadcastMessage(Sputnik.trans("&7Saving..."));
                        Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "fsd");
                        Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "kickall &cThe Server is restarting!");
                        Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "stop");
                    }, 10L);
                }
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 20L, 20L);
    }
    
    public static boolean isPrimeNumber(final int n) {
        if (2 > n) {
            return false;
        }
        for (int squareRoot = (int)Math.sqrt((double)n), i = 2; i <= squareRoot; ++i) {
            if (0 == n % i) {
                return false;
            }
        }
        return true;
    }
    
    static {
        RebootServerCommand.secondMap = (Map<Server, Integer>)new HashMap();
    }
}
