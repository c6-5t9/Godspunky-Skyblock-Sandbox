package net.hypixel.skyblock.npc.hub.villagers;

import net.hypixel.skyblock.user.User;
import org.bukkit.entity.Player;
import net.hypixel.skyblock.npc.impl.enums.NPCType;
import net.hypixel.skyblock.npc.impl.NPCParameters;
import net.hypixel.skyblock.npc.impl.SkyBlockNPC;

public class Duke extends SkyBlockNPC
{
    public Duke() {
        super(new NPCParameters() {
            @Override
            public String id() {
                return "DUKE";
            }
            
            @Override
            public String name() {
                return "&fDuke";
            }
            
            @Override
            public String[] messages() {
                return new String[] { "Are you new here? As you can see there is a lot to explore!", "My advice is to start by visiting the Farm, or the Coal Mine both North of here.", "If you do need some wood, the best place to get some is West of the Village!" };
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
                return -6.0;
            }
            
            @Override
            public double y() {
                return 70.0;
            }
            
            @Override
            public double z() {
                return -89.0;
            }
            
            @Override
            public void onInteract(final Player player, final SkyBlockNPC npc) {
                if (!User.getUser(player).getTalkedVillagers().contains((Object)this.name())) {
                    User.getUser(player).getTalkedVillagers().add((Object)this.name());
                }
            }
        });
    }
}
