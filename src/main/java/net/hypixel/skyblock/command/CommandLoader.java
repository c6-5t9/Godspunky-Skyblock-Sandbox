package net.hypixel.skyblock.command;

import java.util.ArrayList;
import java.util.List;

public class CommandLoader
{
    private final List<SCommand> commands;
    
    public CommandLoader() {
        this.commands = (List<SCommand>)new ArrayList();
    }
    
    public void register(final SCommand command) {
        this.commands.add((Object)command);
        command.register();
    }
    
    public int getCommandAmount() {
        return this.commands.size();
    }
}
