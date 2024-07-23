package net.hypixel.skyblock.npc.hub.villagers;

import org.bukkit.entity.Player;
import net.hypixel.skyblock.npc.impl.enums.NPCType;
import net.hypixel.skyblock.npc.impl.NPCParameters;
import net.hypixel.skyblock.npc.impl.SkyBlockNPC;

public class Jack extends SkyBlockNPC
{
    public Jack() {
        super(new NPCParameters() {
            @Override
            public String id() {
                return "JACK";
            }
            
            @Override
            public String name() {
                return "&fJack";
            }
            
            @Override
            public String[] messages() {
                return new String[] { "You might have noticed that you have a Mana bar!", "Some items have mysterious properties called Abilities", "Abilities use your Mana as a resource. Here take this Rogue Sword. I don't need it!" };
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
                return 0.0;
            }
            
            @Override
            public double y() {
                return 70.0;
            }
            
            @Override
            public double z() {
                return -54.0;
            }
            
            @Override
            public void onInteract(final Player player, final SkyBlockNPC npc) {
            }
        });
    }
}
