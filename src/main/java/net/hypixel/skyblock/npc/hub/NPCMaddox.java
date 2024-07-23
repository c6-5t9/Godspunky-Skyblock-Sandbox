package net.hypixel.skyblock.npc.hub;

import net.hypixel.skyblock.gui.GUIType;
import org.bukkit.entity.Player;
import net.hypixel.skyblock.npc.impl.NPCParameters;
import net.hypixel.skyblock.npc.impl.SkyBlockNPC;

public class NPCMaddox extends SkyBlockNPC
{
    public NPCMaddox() {
        super(new NPCParameters() {
            @Override
            public String id() {
                return "MADDOX_SLAYER";
            }
            
            @Override
            public String name() {
                return "&5Maddox the slayer";
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
                return 66.0;
            }
            
            @Override
            public double z() {
                return -56.0;
            }
            
            @Override
            public void onInteract(final Player player, final SkyBlockNPC npc) {
                GUIType.SLAYER.getGUI().open(player);
            }
        });
    }
}
