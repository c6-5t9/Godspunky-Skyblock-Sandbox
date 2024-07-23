package net.hypixel.skyblock.npc.hub.merchants;

import net.hypixel.skyblock.features.merchant.LibrarianMerchantGUI;
import org.bukkit.entity.Player;
import net.hypixel.skyblock.npc.impl.enums.NPCType;
import net.hypixel.skyblock.npc.impl.NPCParameters;
import net.hypixel.skyblock.npc.impl.SkyBlockNPC;

public class LibrarianMerchant extends SkyBlockNPC
{
    public LibrarianMerchant() {
        super(new NPCParameters() {
            @Override
            public String id() {
                return "LIBRARIAN";
            }
            
            @Override
            public String name() {
                return "&fLibrarian";
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
                return -35.0;
            }
            
            @Override
            public double y() {
                return 69.0;
            }
            
            @Override
            public double z() {
                return -112.0;
            }
            
            @Override
            public boolean looking() {
                return true;
            }
            
            @Override
            public void onInteract(final Player player, final SkyBlockNPC npc) {
                final LibrarianMerchantGUI gui = new LibrarianMerchantGUI();
                gui.open(player);
            }
        });
    }
}
