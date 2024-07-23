package net.hypixel.skyblock.command;

import net.hypixel.skyblock.gui.GUIType;
import net.hypixel.skyblock.features.ranks.PlayerRank;

@CommandParameters(permission = PlayerRank.DEFAULT, aliases = "ie")
public class ItemEditorCommand extends SCommand
{
    @Override
    public void run(final CommandSource sender, final String[] args) {
        GUIType.ITEM_EDITOR.getGUI().open(sender.getPlayer());
    }
}
