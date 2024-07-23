package net.hypixel.skyblock.features.requirement;

import net.hypixel.skyblock.user.User;

public interface AbstractRequirement
{
    boolean hasRequirement(final User p0);
    
    String getMessage();
}
