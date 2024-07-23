package net.hypixel.skyblock.npc.hub;

import net.hypixel.skyblock.gui.GUIType;
import org.bukkit.entity.Player;
import net.hypixel.skyblock.npc.impl.NPCSkin;
import net.hypixel.skyblock.npc.impl.NPCParameters;
import net.hypixel.skyblock.npc.impl.SkyBlockNPC;

public class NPCMort extends SkyBlockNPC
{
    public NPCMort() {
        super(new NPCParameters() {
            @Override
            public String id() {
                return "MORT";
            }
            
            @Override
            public String name() {
                return "&5Mort";
            }
            
            @Override
            public NPCSkin skin() {
                return new NPCSkin("eyJ0aW1lc3RhbXAiOjE1ODcwMTg2Nzk1NzYsInByb2ZpbGVJZCI6IjJkYzc3YWU3OTQ2MzQ4MDI5NDI4MGM4NDIyNzRiNTY3IiwicHJvZmlsZU5hbWUiOiJzYWR5MDYxMCIsInNpZ25hdHVyZVJlcXVpcmVkIjp0cnVlLCJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWI1Njg5NWI5NjU5ODk2YWQ2NDdmNTg1OTkyMzhhZjUzMmQ0NmRiOWMxYjAzODliOGJiZWI3MDk5OWRhYjMzZCJ9fX0=", "ey9vkW8VXzne4hcYi93Qy8byhmeFjWO/jzoYMJ2R8Njg/daCGIqwp1JnWhnI3WWDZYfpqNF00UojtsaFH6RICa2+1jS+le82siwIFqH5/TufeVfjg8wn611PcbCMtlIbfTIP0jgDPCFTiXK+8gY4ES7fsFVy6Rs26xynPFABdZzPEMECW+gukpyz1hbc1gR57QRDfvKGe55e1Usqxh5v5q5B3uFV78WSJ8faZiItE2Re9HesqcR314Zst7On/jKOtmYfl7opCKQ/q7ySu2y55Tn0dRWWDQwZmHOuYuFE1hF9g1dtmNrPfGs7WuSbI5qZk9GfUJbSOe2naB6ZwF+C2WY77M9U8gDzlipGN7yEDDTBGvhbzhmXTHnEoRPxSBv2gpO6WEmfIrLvQzqWVS96rmwv/pMx62U3pxTJAcQRKBCzgT/EOh2lT886h6Gj71z43zCg+u3smvv0bjoe4IIUe8omurBXNLXXGY01vboXNXs2NpcVg1sX4Uk0NPhuR9Wh/S05bj6T4Tqke007g9lWFI8+gM8zRl4yLbafsQmk6ZmPO/6sF2oT+qfqgv2Tw1PH0nafoHYxGarjIEmVlNNS1mkFx8+CHmb36ntk/FKcFtX9zmuRXbMLWSP3XB9YjTeYZPtlOjzXdx5sXKrRJsJl/pfop8XWTjfI0HBvo30h2K0=");
            }
            
            @Override
            public String world() {
                return "world";
            }
            
            @Override
            public double x() {
                return -45.0;
            }
            
            @Override
            public double y() {
                return 88.0;
            }
            
            @Override
            public double z() {
                return 9.0;
            }
            
            @Override
            public void onInteract(final Player player, final SkyBlockNPC npc) {
                GUIType.CATACOMBS_BOSS.getGUI().open(player);
            }
        });
    }
}
