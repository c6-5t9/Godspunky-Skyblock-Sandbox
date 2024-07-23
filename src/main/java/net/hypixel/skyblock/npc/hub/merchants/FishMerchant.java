package net.hypixel.skyblock.npc.hub.merchants;

import net.hypixel.skyblock.features.merchant.FishMerchantGUI;
import org.bukkit.entity.Player;
import net.hypixel.skyblock.npc.impl.NPCParameters;
import net.hypixel.skyblock.npc.impl.SkyBlockNPC;

public class FishMerchant extends SkyBlockNPC
{
    public FishMerchant() {
        super(new NPCParameters() {
            @Override
            public String id() {
                return "FISH_MERCHANT";
            }
            
            @Override
            public String name() {
                return "&fFisherman";
            }
            
            @Override
            public String[] messages() {
                return new String[] { "Fishing is my trade. I buy and sell any fish, rod, or treasure you can find!", "Click me again to open the Fisherman Shop!" };
            }
            
            @Override
            public String world() {
                return "world";
            }
            
            @Override
            public double x() {
                return 52.0;
            }
            
            @Override
            public double y() {
                return 68.0;
            }
            
            @Override
            public double z() {
                return -82.0;
            }
            
            @Override
            public void onInteract(final Player player, final SkyBlockNPC npc) {
                final FishMerchantGUI gui = new FishMerchantGUI();
                gui.open(player);
            }
        });
    }
}
