package net.hypixel.skyblock.npc.hub.villagers;

import org.bukkit.entity.Player;
import net.hypixel.skyblock.npc.impl.enums.NPCType;
import net.hypixel.skyblock.npc.impl.NPCParameters;
import net.hypixel.skyblock.npc.impl.SkyBlockNPC;

public class Lynn extends SkyBlockNPC
{
    public Lynn() {
        super(new NPCParameters() {
            @Override
            public String id() {
                return "LYNN";
            }
            
            @Override
            public String name() {
                return "&fLynn";
            }
            
            @Override
            public String[] messages() {
                return new String[] { "If you ever get lost during a quest, open your Quest Log in your SkyBlock Menu!" };
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
                return -21.0;
            }
            
            @Override
            public double y() {
                return 68.0;
            }
            
            @Override
            public double z() {
                return -124.0;
            }
            
            @Override
            public void onInteract(final Player player, final SkyBlockNPC npc) {
            }
        });
    }
}
