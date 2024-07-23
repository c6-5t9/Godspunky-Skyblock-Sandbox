package net.hypixel.skyblock.command;

import java.util.Iterator;
import net.hypixel.skyblock.features.skill.Skill;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import net.hypixel.skyblock.features.ranks.PlayerRank;

@CommandParameters(description = "Shows your skills.", aliases = "skill", permission = PlayerRank.DEFAULT)
public class SkillsCommand extends SCommand
{
    @Override
    public void run(final CommandSource sender, final String[] args) {
        if (0 != args.length) {
            throw new CommandArgumentException();
        }
        if (sender instanceof ConsoleCommandSender) {
            throw new CommandFailException("Console senders cannot use this command!");
        }
        this.send((Object)ChatColor.AQUA + "Skills:");
        for (final Skill skill : Skill.getSkills()) {
            this.send(" - " + skill.getName() + ": LVL " + Skill.getLevel(sender.getUser().getSkillXP(skill), skill.hasSixtyLevels()) + ", " + sender.getUser().getSkillXP(skill) + " XP");
        }
    }
}
