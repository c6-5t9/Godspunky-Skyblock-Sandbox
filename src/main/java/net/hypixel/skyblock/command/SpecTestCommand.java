package net.hypixel.skyblock.command;

import net.hypixel.skyblock.database.RecipeDatabase;
import net.hypixel.skyblock.gui.GUIType;
import net.hypixel.skyblock.features.ranks.PlayerRank;

@CommandParameters(description = "Spec test command.", aliases = "spectest", permission = PlayerRank.ADMIN)
public class SpecTestCommand extends SCommand
{
    @Override
    public void run(final CommandSource sender, final String[] args) {
        final String s = args[0];
        int n = -1;
        switch (s.hashCode()) {
            case 1810455551: {
                if (s.equals((Object)"giveRecipe")) {
                    n = 0;
                    break;
                }
                break;
            }
            case 3417674: {
                if (s.equals((Object)"open")) {
                    n = 1;
                    break;
                }
                break;
            }
            case 336612059: {
                if (s.equals((Object)"loadAll")) {
                    n = 2;
                    break;
                }
                break;
            }
        }
        switch (n) {
            case 0: {
                sender.getUser().getUnlockedRecipes().add((Object)args[1]);
                break;
            }
            case 1: {
                GUIType.RECIPE_CREATOR.getGUI().open(sender.getPlayer());
                break;
            }
            case 2: {
                RecipeDatabase.loadRecipes();
                break;
            }
        }
    }
}
