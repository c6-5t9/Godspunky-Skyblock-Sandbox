package net.hypixel.skyblock.command;

public class CommandFailException extends RuntimeException
{
    public CommandFailException(final String message) {
        super(message);
    }
}
