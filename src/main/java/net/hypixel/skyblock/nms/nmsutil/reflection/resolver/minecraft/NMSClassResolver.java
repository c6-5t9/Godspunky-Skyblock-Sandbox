package net.hypixel.skyblock.nms.nmsutil.reflection.resolver.minecraft;

import net.hypixel.skyblock.nms.nmsutil.reflection.minecraft.Minecraft;
import net.hypixel.skyblock.nms.nmsutil.reflection.resolver.ClassResolver;

public class NMSClassResolver extends ClassResolver
{
    @Override
    public Class resolve(final String... names) throws ClassNotFoundException {
        for (int i = 0; i < names.length; ++i) {
            if (!names[i].startsWith("net.minecraft.server")) {
                names[i] = "net.minecraft.server." + Minecraft.getVersion() + names[i];
            }
        }
        return super.resolve(names);
    }
}
