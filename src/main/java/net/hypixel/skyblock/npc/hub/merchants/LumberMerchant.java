package net.hypixel.skyblock.npc.hub.merchants;

import net.hypixel.skyblock.features.merchant.LumberMerchantGUI;
import org.bukkit.entity.Player;
import net.hypixel.skyblock.npc.impl.NPCParameters;
import net.hypixel.skyblock.npc.impl.SkyBlockNPC;

public class LumberMerchant extends SkyBlockNPC
{
    public LumberMerchant() {
        super(new NPCParameters() {
            @Override
            public String id() {
                return "LUMBER_JACK";
            }
            
            @Override
            public String name() {
                return "&fLumberJack";
            }
            
            @Override
            public String[] messages() {
                return new String[] { "Buy and sell wood and axes with me!", "Click me again to open the Lumberjack Shop!" };
            }
            
            @Override
            public String world() {
                return "world";
            }
            
            @Override
            public double x() {
                return -49.0;
            }
            
            @Override
            public double y() {
                return 70.0;
            }
            
            @Override
            public double z() {
                return -68.0;
            }
            
            @Override
            public void onInteract(final Player player, final SkyBlockNPC npc) {
                final LumberMerchantGUI gui = new LumberMerchantGUI();
                gui.open(player);
            }
        });
    }
}
