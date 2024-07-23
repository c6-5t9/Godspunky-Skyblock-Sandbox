package net.hypixel.skyblock.npc.hub.merchants;

import net.hypixel.skyblock.features.merchant.AdventurerMerchantGUI;
import org.bukkit.entity.Player;
import net.hypixel.skyblock.npc.impl.NPCParameters;
import net.hypixel.skyblock.npc.impl.SkyBlockNPC;

public class AdventurerMerchant extends SkyBlockNPC
{
    public AdventurerMerchant() {
        super(new NPCParameters() {
            @Override
            public String id() {
                return "ADVENTURER";
            }
            
            @Override
            public String name() {
                return "&fAdventurer";
            }
            
            @Override
            public String[] messages() {
                return new String[] { "I've seen it all - every island from here to the edge of the world!", "Over the years I've acquired a variety of Talismans and Artifacts.", "For a price, you can have it all!", "Click me again to open the Adventurer Shop!" };
            }
            
            @Override
            public String world() {
                return "world";
            }
            
            @Override
            public double x() {
                return -41.0;
            }
            
            @Override
            public double y() {
                return 70.0;
            }
            
            @Override
            public double z() {
                return -64.0;
            }
            
            @Override
            public float yaw() {
                return 97.8751f;
            }
            
            @Override
            public float pitch() {
                return 0.0f;
            }
            
            @Override
            public void onInteract(final Player player, final SkyBlockNPC npc) {
                new AdventurerMerchantGUI().open(player);
            }
        });
    }
}
