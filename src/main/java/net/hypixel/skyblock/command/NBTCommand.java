package net.hypixel.skyblock.command;

import java.util.Iterator;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.command.ConsoleCommandSender;
import net.hypixel.skyblock.features.ranks.PlayerRank;

@CommandParameters(description = "Gets the NBT of your current item.", permission = PlayerRank.ADMIN)
public class NBTCommand extends SCommand
{
    @Override
    public void run(final CommandSource sender, final String[] args) {
        if (0 != args.length) {
            throw new CommandArgumentException();
        }
        if (sender instanceof ConsoleCommandSender) {
            throw new CommandFailException("Console senders cannot use this command!");
        }
        final Player player = sender.getPlayer();
        final PlayerInventory inv = player.getInventory();
        final ItemStack stack = CraftItemStack.asNMSCopy(inv.getItemInHand());
        if (null == inv.getItemInHand()) {
            throw new CommandFailException((Object)ChatColor.RED + "Air do not have NBT kiddo, get something!");
        }
        final NBTTagCompound compound = stack.getTag();
        if (null == compound) {
            throw new CommandFailException((Object)ChatColor.RED + "This item does not have any NBT data!");
        }
        this.send((Object)ChatColor.GREEN + "NBT Explorer >");
        for (final String key : compound.c()) {
            this.send((Object)ChatColor.YELLOW + key + (Object)ChatColor.GREEN + ": " + (Object)ChatColor.RESET + compound.get(key).toString().replaceAll("§", "&"));
        }
    }
}
