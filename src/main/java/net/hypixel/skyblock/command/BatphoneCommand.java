package net.hypixel.skyblock.command;

import java.util.ArrayList;
import net.hypixel.skyblock.gui.GUI;
import org.bukkit.entity.Player;
import net.hypixel.skyblock.gui.GUIType;
import net.hypixel.skyblock.util.SUtil;
import net.hypixel.skyblock.item.oddities.MaddoxBatphone;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import java.util.List;
import java.util.UUID;
import net.hypixel.skyblock.features.ranks.PlayerRank;

@CommandParameters(description = "Hidden command for Maddox Batphone.", permission = PlayerRank.DEFAULT)
public class BatphoneCommand extends SCommand
{
    public static final UUID ACCESS_KEY;
    public static final List<String> KEYS;
    
    @Override
    public void run(final CommandSource sender, final String[] args) {
        if (sender instanceof ConsoleCommandSender) {
            throw new CommandFailException("Console senders cannot use this command!");
        }
        if (!args[0].equals((Object)BatphoneCommand.ACCESS_KEY.toString())) {
            return;
        }
        if (!BatphoneCommand.KEYS.contains((Object)args[1])) {
            throw new CommandFailException((Object)ChatColor.RED + "\u2706 It's too late now, the phone line is off! Call again!");
        }
        final Player player = sender.getPlayer();
        MaddoxBatphone.CALL_COOLDOWN.add((Object)player.getUniqueId());
        SUtil.delay(() -> MaddoxBatphone.CALL_COOLDOWN.remove((Object)player.getUniqueId()), 400L);
        final GUI gui = GUIType.SLAYER.getGUI();
        gui.open(player);
    }
    
    static {
        ACCESS_KEY = UUID.randomUUID();
        KEYS = (List)new ArrayList();
    }
}
