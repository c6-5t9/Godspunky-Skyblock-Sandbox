package net.hypixel.skyblock.npc.hub;

import net.hypixel.skyblock.gui.GUIType;
import org.bukkit.entity.Player;
import net.hypixel.skyblock.npc.impl.NPCParameters;
import net.hypixel.skyblock.npc.impl.SkyBlockNPC;

public class NPCCommunityShop extends SkyBlockNPC
{
    public NPCCommunityShop() {
        super(new NPCParameters() {
            @Override
            public String id() {
                return "COMMUNITY_SHOP";
            }
            
            @Override
            public String name() {
                return "&fCommunity Shop";
            }
            
            @Override
            public String world() {
                return "world";
            }
            
            @Override
            public double x() {
                return 0.0;
            }
            
            @Override
            public double y() {
                return 71.0;
            }
            
            @Override
            public double z() {
                return -101.5;
            }
            
            @Override
            public void onInteract(final Player player, final SkyBlockNPC npc) {
                GUIType.BOOSTER_COOKIE_SHOP.getGUI().open(player);
            }
        });
    }
}
