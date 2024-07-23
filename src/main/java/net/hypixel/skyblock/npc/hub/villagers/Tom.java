package net.hypixel.skyblock.npc.hub.villagers;

import org.bukkit.entity.Player;
import net.hypixel.skyblock.npc.impl.enums.NPCType;
import net.hypixel.skyblock.npc.impl.NPCParameters;
import net.hypixel.skyblock.npc.impl.SkyBlockNPC;

public class Tom extends SkyBlockNPC
{
    public Tom() {
        super(new NPCParameters() {
            @Override
            public String id() {
                return "TOM";
            }
            
            @Override
            public String name() {
                return "&fTom";
            }
            
            @Override
            public String[] messages() {
                return new String[] { "I will teach you the Promising Axe Recipe to get you started!", "All Skyblock recipes can be found by opening the Recipe Book in your Skyblock Menu" };
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
                return -27.0;
            }
            
            @Override
            public double y() {
                return 70.0;
            }
            
            @Override
            public double z() {
                return -49.0;
            }
            
            @Override
            public void onInteract(final Player player, final SkyBlockNPC npc) {
            }
        });
    }
}
