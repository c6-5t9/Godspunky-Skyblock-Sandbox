package net.hypixel.skyblock.command;

import org.bukkit.entity.Player;
import net.hypixel.skyblock.user.User;
import net.hypixel.skyblock.features.skill.MiningSkill;
import net.hypixel.skyblock.features.skill.ForagingSkill;
import net.hypixel.skyblock.features.skill.FarmingSkill;
import net.hypixel.skyblock.util.SUtil;
import net.hypixel.skyblock.features.skill.Skill;
import net.hypixel.skyblock.features.skill.CombatSkill;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import net.hypixel.skyblock.features.ranks.PlayerRank;

@CommandParameters(description = "Modify your coin amount.", permission = PlayerRank.ADMIN, aliases = "ssx")
public class SetSkillCommand extends SCommand
{
    @Override
    public void run(final CommandSource sender, final String[] args) {
        if (args.length != 0 && args.length != 2) {
            throw new CommandArgumentException();
        }
        if (sender instanceof ConsoleCommandSender) {
            throw new CommandFailException("Console senders cannot use this command!");
        }
        final User user = sender.getUser();
        final Player player = sender.getPlayer();
        if (args.length == 0) {
            this.send((Object)ChatColor.RED + "Incorrect Syntax! Usage /ssx <skillname> <xp>");
            return;
        }
        final double xp = Double.parseDouble(args[1]);
        final String lowerCase2;
        final String lowerCase = lowerCase2 = args[0].toLowerCase();
        int n = -1;
        switch (lowerCase2.hashCode()) {
            case -1354825996: {
                if (lowerCase2.equals((Object)"combat")) {
                    n = 0;
                    break;
                }
                break;
            }
            case 1993470708: {
                if (lowerCase2.equals((Object)"COMBAT")) {
                    n = 1;
                    break;
                }
                break;
            }
            case 3167: {
                if (lowerCase2.equals((Object)"cb")) {
                    n = 2;
                    break;
                }
                break;
            }
            case -1078244372: {
                if (lowerCase2.equals((Object)"farming")) {
                    n = 3;
                    break;
                }
                break;
            }
            case -360261684: {
                if (lowerCase2.equals((Object)"FARMING")) {
                    n = 4;
                    break;
                }
                break;
            }
            case 3271: {
                if (lowerCase2.equals((Object)"fm")) {
                    n = 5;
                    break;
                }
                break;
            }
            case 463166163: {
                if (lowerCase2.equals((Object)"foraging")) {
                    n = 6;
                    break;
                }
                break;
            }
            case 1245792979: {
                if (lowerCase2.equals((Object)"FORAGING")) {
                    n = 7;
                    break;
                }
                break;
            }
            case 3265: {
                if (lowerCase2.equals((Object)"fg")) {
                    n = 8;
                    break;
                }
                break;
            }
            case -1074038704: {
                if (lowerCase2.equals((Object)"mining")) {
                    n = 9;
                    break;
                }
                break;
            }
            case -2020709296: {
                if (lowerCase2.equals((Object)"MINING")) {
                    n = 10;
                    break;
                }
                break;
            }
            case 3489: {
                if (lowerCase2.equals((Object)"mn")) {
                    n = 11;
                    break;
                }
                break;
            }
        }
        switch (n) {
            case 0:
            case 1:
            case 2: {
                if (xp > 0.0) {
                    Skill.reward(CombatSkill.INSTANCE, xp, player);
                    player.sendMessage((Object)ChatColor.GREEN + "You (or someone) have added " + (Object)ChatColor.GOLD + SUtil.commaify(xp) + (Object)ChatColor.GREEN + " Combat XP to your profile.");
                    return;
                }
                if (xp < 0.0 && xp != 0.0) {
                    player.sendMessage((Object)ChatColor.RED + "You (or someone) have " + (Object)ChatColor.GOLD + SUtil.commaify(xp) + (Object)ChatColor.RED + " Combat XP from your profile.");
                    return;
                }
                if (xp == 0.0) {
                    player.sendMessage((Object)ChatColor.RED + "Are you kidding me? You add 0 XP?");
                }
            }
            case 3:
            case 4:
            case 5: {
                if (xp > 0.0) {
                    Skill.reward(FarmingSkill.INSTANCE, xp, player);
                    player.sendMessage((Object)ChatColor.GREEN + "You (or someone) have added " + (Object)ChatColor.GOLD + SUtil.commaify(xp) + (Object)ChatColor.GREEN + " Farming XP to your profile.");
                    return;
                }
                if (xp < 0.0 && xp != 0.0) {
                    player.sendMessage((Object)ChatColor.RED + "You (or someone) have " + (Object)ChatColor.GOLD + SUtil.commaify(xp) + (Object)ChatColor.RED + " Farming XP from your profile.");
                    return;
                }
                if (xp == 0.0) {
                    player.sendMessage((Object)ChatColor.RED + "Are you kidding me? You add 0 XP?");
                }
            }
            case 6:
            case 7:
            case 8: {
                if (xp > 0.0) {
                    Skill.reward(ForagingSkill.INSTANCE, xp, player);
                    player.sendMessage((Object)ChatColor.GREEN + "You (or someone) have added " + (Object)ChatColor.GOLD + SUtil.commaify(xp) + (Object)ChatColor.GREEN + " Foraging XP to your profile.");
                    return;
                }
                if (xp < 0.0 && xp != 0.0) {
                    player.sendMessage((Object)ChatColor.RED + "You (or someone) have " + (Object)ChatColor.GOLD + SUtil.commaify(xp) + (Object)ChatColor.RED + " Foraging XP from your profile.");
                    return;
                }
                if (xp == 0.0) {
                    player.sendMessage((Object)ChatColor.RED + "Are you kidding me? You add 0 XP?");
                }
            }
            case 9:
            case 10:
            case 11: {
                if (xp > 0.0) {
                    Skill.reward(MiningSkill.INSTANCE, xp, player);
                    player.sendMessage((Object)ChatColor.GREEN + "You (or someone) have added " + (Object)ChatColor.GOLD + SUtil.commaify(xp) + (Object)ChatColor.GREEN + " Mining XP to your profile.");
                    return;
                }
                if (xp < 0.0 && xp != 0.0) {
                    player.sendMessage((Object)ChatColor.RED + "You (or someone) have " + (Object)ChatColor.GOLD + SUtil.commaify(xp) + (Object)ChatColor.RED + " Mining XP from your profile.");
                    return;
                }
                if (xp == 0.0) {
                    player.sendMessage((Object)ChatColor.RED + "Are you kidding me? You add 0 XP?");
                    break;
                }
                break;
            }
        }
        throw new CommandArgumentException();
    }
}
