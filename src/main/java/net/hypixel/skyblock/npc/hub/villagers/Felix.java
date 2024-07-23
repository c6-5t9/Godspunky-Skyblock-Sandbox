package net.hypixel.skyblock.npc.hub.villagers;

import org.bukkit.entity.Player;
import net.hypixel.skyblock.npc.impl.enums.NPCType;
import net.hypixel.skyblock.npc.impl.NPCParameters;
import net.hypixel.skyblock.npc.impl.SkyBlockNPC;

public class Felix extends SkyBlockNPC
{
    public Felix() {
        super(new NPCParameters() {
            @Override
            public String id() {
                return "FELIX";
            }
            
            @Override
            public String name() {
                return "&fFelix";
            }
            
            @Override
            public String[] messages() {
                return new String[] { "You can access your Ender Chest in your SkyBlock Menu.", "Store items in this chest and access them at any time!" };
            }
            
            @Override
            public NPCType type() {
                return NPCType.VILLAGER;
            }
            
            @Override
            public String world() {
                return "world";
            }
            
            @Override
            public double x() {
                return -25.0;
            }
            
            @Override
            public double y() {
                return 68.0;
            }
            
            @Override
            public double z() {
                return -103.0;
            }
            
            @Override
            public void onInteract(final Player player, final SkyBlockNPC npc) {
            }
        });
    }
}
