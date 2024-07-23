package net.hypixel.skyblock.npc.sandbox;

import org.bukkit.entity.Player;
import net.hypixel.skyblock.npc.impl.enums.NPCType;
import net.hypixel.skyblock.npc.impl.NPCParameters;
import net.hypixel.skyblock.npc.impl.SkyBlockNPC;

public class NPCJerry extends SkyBlockNPC
{
    public NPCJerry() {
        super(new NPCParameters() {
            @Override
            public String id() {
                return "JERRY";
            }
            
            @Override
            public String name() {
                return "&fSandbox Jerry";
            }
            
            @Override
            public String[] messages() {
                return new String[] { "Welcome to Hypixel Skyblock Sandbox!", "The Skyblock universe is full of islands to explore and resources to discover!", "I will help you start your journey in sandbox.", "Use /ib or free items npc to get starter goods.", "Use /enc <enchant type> <level> to enchant!", "Use /ie to maximize your items!", "Travel to your island!", "Complete Floor 6 Boss Room.", "Complete All Slayer Bosses.", "New Updates Coming Soon in future!" };
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
                return -2.0;
            }
            
            @Override
            public double y() {
                return 70.0;
            }
            
            @Override
            public double z() {
                return -79.0;
            }
            
            @Override
            public void onInteract(final Player player, final SkyBlockNPC npc) {
            }
        });
    }
}
