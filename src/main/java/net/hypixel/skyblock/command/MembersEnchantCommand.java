package net.hypixel.skyblock.command;

import org.bukkit.entity.Player;
import net.hypixel.skyblock.util.SUtil;
import org.bukkit.inventory.ItemStack;
import net.hypixel.skyblock.item.SItem;
import net.hypixel.skyblock.item.SMaterial;
import net.hypixel.skyblock.features.enchantment.EnchantmentType;
import net.hypixel.skyblock.util.Sputnik;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.ChatColor;
import net.hypixel.skyblock.features.ranks.PlayerRank;

@CommandParameters(description = "Adds an enchantment from Spec to the specified item.", aliases = "enc", permission = PlayerRank.DEFAULT)
public class MembersEnchantCommand extends SCommand
{
    @Override
    public void run(final CommandSource sender, final String[] args) {
        if (args.length != 2) {
            throw new CommandArgumentException();
        }
        if (args.length == 0) {
            this.send((Object)ChatColor.RED + "Wrong usage of the command! Usage /enc <enchant type> <level>!");
            return;
        }
        if (sender instanceof ConsoleCommandSender) {
            throw new CommandFailException("Console senders cannot use this command!");
        }
        final Player player = sender.getPlayer();
        if (Sputnik.isFullInv(player)) {
            this.send((Object)ChatColor.RED + "Your inventory ran out of spaces! Clean it up!");
            return;
        }
        final EnchantmentType type = EnchantmentType.getByNamespace(args[0]);
        if (type == null) {
            this.send((Object)ChatColor.RED + "Invalid enchantment type! It might not get added yet.");
            return;
        }
        final int i = Integer.parseInt(args[1]);
        if (i <= 0) {
            this.send((Object)ChatColor.RED + "Are you serious? If you want to remove enchantments, use /rench");
            return;
        }
        if (type == EnchantmentType.LUCKINESS) {
            this.send((Object)ChatColor.RED + "This enchantment is not free! Purchase it in the Community Shop.");
            return;
        }
        if (type == EnchantmentType.VICIOUS) {
            this.send((Object)ChatColor.RED + "This enchantment is not free! You need to slay Revenant Horror V for it!");
            return;
        }
        if (type == EnchantmentType.CHIMERA) {
            this.send((Object)ChatColor.RED + "This enchantment is not free! You need to slay Sven Packmaster IV for it!");
            return;
        }
        if (type == EnchantmentType.LEGION || type == EnchantmentType.FATAL_TEMPO) {
            this.send((Object)ChatColor.RED + "This enchantment is not free! You need to slay Voidgloom Seraph IV for it!");
            return;
        }
        if (i > 5 && type == EnchantmentType.FIRST_STRIKE) {
            this.send((Object)ChatColor.RED + "Too high enchantment level! This enchantment is capped at level 5 in case of /meb command! You need to slay Tarantula Broodfather IV to get a LVL VII version of this enchanment!");
            return;
        }
        if (type == EnchantmentType.EFFICIENCY) {
            this.send((Object)ChatColor.RED + "This enchantment is banned due to rewamping in mining system!");
            return;
        }
        if (i > 2 && type == EnchantmentType.KNOCKBACK) {
            this.send((Object)ChatColor.RED + "Too high enchantment level! This enchantment is capped at level 2!");
            return;
        }
        if (i > 1500) {
            this.send((Object)ChatColor.RED + "Too high enchantment level! This enchantment is capped at 1,500.");
            return;
        }
        if (i > 320 && type == EnchantmentType.POWER) {
            this.send((Object)ChatColor.RED + "Too high enchantment level! This enchantment is capped at level 320.");
            return;
        }
        if (i > 400 && type == EnchantmentType.SHARPNESS) {
            this.send((Object)ChatColor.RED + "Too high enchantment level! This enchantment is capped at level 400.");
            return;
        }
        if (i > 1 && (type == EnchantmentType.ONE_FOR_ALL || type == EnchantmentType.TELEKINESIS || type == EnchantmentType.AQUA_INFINITY)) {
            this.send((Object)ChatColor.RED + "Too high enchantment level! This enchantment is capped at level 1.");
            return;
        }
        if (i > 30 && (type == EnchantmentType.CRITICAL || type == EnchantmentType.EXECUTE || type == EnchantmentType.AIMING)) {
            this.send((Object)ChatColor.RED + "Too high enchantment level! This enchantment is capped at level 30.");
            return;
        }
        if (i > 10 && (type == EnchantmentType.ENDER_SLAYER || type == EnchantmentType.SMITE || type == EnchantmentType.BANE_OF_ARTHROPODS || type == EnchantmentType.DRAGON_HUNTER)) {
            this.send((Object)ChatColor.RED + "Too high enchantment level! This enchantment is capped at level 10.");
            return;
        }
        if (i > 10 && type == EnchantmentType.ULTIMATE_WISE) {
            this.send((Object)ChatColor.RED + "Too high enchantment level! This enchantment is capped at level 8.");
            return;
        }
        if (i > 10 && (type == EnchantmentType.SOUL_EATER || type == EnchantmentType.LIFE_STEAL || type == EnchantmentType.AIMING || type == EnchantmentType.VAMPIRISM)) {
            this.send((Object)ChatColor.RED + "Too high enchantment level! This enchantment is capped at level 5.");
            return;
        }
        final SItem eBook = SItem.of(SMaterial.ENCHANTED_BOOK);
        eBook.addEnchantment(type, i);
        player.getInventory().addItem(new ItemStack[] { eBook.getStack() });
        this.send(Sputnik.trans("&aYou have been given the &dEnchanted Book &awith &9" + type.getName() + " &9" + SUtil.toRomanNumeral(i) + " &aon it. Use an Anvil to apply it to your items."));
    }
    
    public void stop() {
        this.send("");
    }
}
