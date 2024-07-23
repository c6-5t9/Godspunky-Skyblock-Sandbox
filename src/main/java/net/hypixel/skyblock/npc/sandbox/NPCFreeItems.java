package net.hypixel.skyblock.npc.sandbox;

import net.hypixel.skyblock.gui.GUIType;
import org.bukkit.entity.Player;
import net.hypixel.skyblock.npc.impl.NPCSkin;
import net.hypixel.skyblock.npc.impl.NPCParameters;
import net.hypixel.skyblock.npc.impl.SkyBlockNPC;

public class NPCFreeItems extends SkyBlockNPC
{
    public NPCFreeItems() {
        super(new NPCParameters() {
            @Override
            public String id() {
                return "FREE_ITEMS";
            }
            
            @Override
            public String name() {
                return "&fFree Items";
            }
            
            @Override
            public NPCSkin skin() {
                return new NPCSkin("ewogICJ0aW1lc3RhbXAiIDogMTU5NjQ5MTk2NDAxNiwKICAicHJvZmlsZUlkIiA6ICJkNjBmMzQ3MzZhMTI0N2EyOWI4MmNjNzE1YjAwNDhkYiIsCiAgInByb2ZpbGVOYW1lIiA6ICJCSl9EYW5pZWwiLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTY5MmU2MmI0MTExNGM1YjFhNWU5ZTM2YzA2Nzk4Mjc4ZWI2YTUxMjZkYzI2ZGRiMTY2ZDIwMjFmZTBmYTAwZSIKICAgIH0KICB9Cn0=", "MDUyChd8qormCDVIR0+zKe4XqtQaVUfsAXj8Pt57sL8Djjf922aOFHr0bwoKfRInDQzrDfvXMFqSptrGnng0W4CTi2aPsEbc0rjy4+SwcSKrK1BJgYUCF4RI1XIc5adPQ7n1U5qEWccgvCx3gWLLE4vDq1yQR+qdk6458CXDWVqkieXRMX0VPlxg0FwT8XlPzxa038leN39x1Zqfrh61/rYmUc6dGoI8xncqqlkxClVIM0OBXHqQT1xcLLEogOoCUNNEFT8nB6qAGs29fo+yTT+zlpMUI9zqk5fGx6nMQV60h8Fm4kWviN1HBObHQ4vzgclLKhBDQuFdl6SsO5dNK05nFWfDhSu71ts5xjNXHrO3cgzntpFMXb9odCkUGZw+25Rq7TDAOBGtYDIe3iovmk1Yr2Ht20dv+boq17rnpWrycLKlYDZiSJFebAzXI8DcIY/TPq9Xc7MlCUlYzAlMsqTlvTfpK3bJc/JmstRrUNxfvZqK+OWfhVC1thY+ulb0vxtnEqwbUk+v1EZ8eNYK9R2HiOdzafuABk7u0PbNu1VaXirSB6G8B/VVnaoUo2Tw7odqXsx8MWKXth4vAUgsfKbOA655vPA9cCLQTMUFESh0UjC9msPlR8aR+XTc9LN7V686BH5+c0rhx5N3XzAqDpaDV4kpQCoq5xiUmU9cwj8=");
            }
            
            @Override
            public String world() {
                return "world";
            }
            
            @Override
            public double x() {
                return -11.0;
            }
            
            @Override
            public double y() {
                return 70.0;
            }
            
            @Override
            public double z() {
                return -77.0;
            }
            
            @Override
            public void onInteract(final Player player, final SkyBlockNPC npc) {
                GUIType.ITEM_BROWSE.getGUI().open(player);
            }
        });
    }
}
