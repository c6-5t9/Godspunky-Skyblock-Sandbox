package net.hypixel.skyblock.npc.hub.villagers;

import org.bukkit.entity.Player;
import net.hypixel.skyblock.npc.impl.enums.NPCType;
import net.hypixel.skyblock.npc.impl.NPCParameters;
import net.hypixel.skyblock.npc.impl.SkyBlockNPC;

public class Vex extends SkyBlockNPC
{
    public Vex() {
        super(new NPCParameters() {
            @Override
            public String id() {
                return "VEX";
            }
            
            @Override
            public String name() {
                return "&fVex";
            }
            
            @Override
            public String[] messages() {
                return new String[] { "Once both players are ready to trade, click on Accept trade!", "You can trade with me if you want!" };
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
                return -16.0;
            }
            
            @Override
            public double y() {
                return 70.0;
            }
            
            @Override
            public double z() {
                return -78.0;
            }
            
            @Override
            public void onInteract(final Player player, final SkyBlockNPC npc) {
            }
        });
    }
}
