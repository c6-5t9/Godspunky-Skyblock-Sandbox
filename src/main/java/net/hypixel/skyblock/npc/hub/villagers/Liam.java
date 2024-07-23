package net.hypixel.skyblock.npc.hub.villagers;

import org.bukkit.entity.Player;
import net.hypixel.skyblock.npc.impl.enums.NPCType;
import net.hypixel.skyblock.npc.impl.NPCParameters;
import net.hypixel.skyblock.npc.impl.SkyBlockNPC;

public class Liam extends SkyBlockNPC
{
    public Liam() {
        super(new NPCParameters() {
            @Override
            public String id() {
                return "LIAM";
            }
            
            @Override
            public String name() {
                return "&fLiam";
            }
            
            @Override
            public String[] messages() {
                return new String[] { "One day those houses in the Village will be rentable for Coins!", "Anyone will be able to rent them, players, co-ops, even Guilds!" };
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
                return -75.0;
            }
            
            @Override
            public double y() {
                return 70.0;
            }
            
            @Override
            public double z() {
                return -107.0;
            }
            
            @Override
            public void onInteract(final Player player, final SkyBlockNPC npc) {
            }
        });
    }
}
