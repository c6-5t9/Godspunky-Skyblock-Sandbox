package net.hypixel.skyblock.features.quest.starting;

import net.hypixel.skyblock.features.quest.Objective;
import net.hypixel.skyblock.features.quest.QuestLine;

public class GettingStartedQuest extends QuestLine
{
    public GettingStartedQuest() {
        super("getting_started", "Getting Started", new Objective[] { new BreakLogObjective(), new WorkbenchObjective(), new PickaxeObjective(), new TeleporterObjective() });
    }
}
