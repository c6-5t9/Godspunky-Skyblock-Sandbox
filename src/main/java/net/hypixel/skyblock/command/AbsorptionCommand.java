package net.hypixel.skyblock.command;

import net.minecraft.server.v1_8_R3.EntityHuman;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;
import net.hypixel.skyblock.util.SputnikPlayer;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftHumanEntity;
import org.bukkit.command.ConsoleCommandSender;
import net.hypixel.skyblock.features.ranks.PlayerRank;

@CommandParameters(description = "Modify your absorption amount.", permission = PlayerRank.ADMIN)
public class AbsorptionCommand extends SCommand
{
    @Override
    public void run(final CommandSource sender, final String[] args) {
        if (1 != args.length) {
            throw new CommandArgumentException();
        }
        if (sender instanceof ConsoleCommandSender) {
            throw new CommandFailException("Console senders cannot use this command!");
        }
        final Player player = sender.getPlayer();
        final EntityHuman human = ((CraftHumanEntity)player).getHandle();
        final float f = Float.parseFloat(args[0]);
        SputnikPlayer.setCustomAbsorptionHP(player, f);
        this.send((Object)ChatColor.GREEN + "You now have " + (Object)ChatColor.GOLD + f + (Object)ChatColor.GREEN + " absorption hearts.");
    }
}
