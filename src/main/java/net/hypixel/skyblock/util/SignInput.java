package net.hypixel.skyblock.util;

import java.util.HashMap;
import org.bukkit.plugin.Plugin;
import net.hypixel.skyblock.SkyBlock;
import net.hypixel.skyblock.gui.GUI;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Sound;
import net.hypixel.skyblock.gui.TradeMenu;
import org.bukkit.scheduler.BukkitRunnable;
import net.minecraft.server.v1_8_R3.Packet;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import net.minecraft.server.v1_8_R3.PacketPlayOutOpenSignEditor;
import net.minecraft.server.v1_8_R3.PacketPlayOutUpdateSign;
import net.minecraft.server.v1_8_R3.ChatComponentText;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.World;
import net.minecraft.server.v1_8_R3.PacketPlayOutBlockChange;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.hypixel.skyblock.gui.GUISignItem;
import java.util.Map;
import net.hypixel.skyblock.user.User;
import java.util.UUID;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class SignInput
{
    private final Player player;
    private Location signLoc;
    private final int timeoutSec;
    private final UUID tradeUUID;
    private final User user;
    public static final Map<UUID, GUISignItem> SIGN_INPUT_QUERY;
    
    public SignInput(final Player p, final String[] text, final int timeoutSec, final UUID tradeUUID) {
        this.player = p;
        this.timeoutSec = timeoutSec;
        this.tradeUUID = tradeUUID;
        this.user = User.getUser(p.getUniqueId());
        this.openSign(text);
    }
    
    public void openSign(final String[] strings) {
        (this.signLoc = this.player.getLocation()).setY(1.0);
        final BlockPosition p = new BlockPosition(this.signLoc.getBlockX(), this.signLoc.getBlockY(), this.signLoc.getBlockZ());
        final PacketPlayOutBlockChange blockPacket = new PacketPlayOutBlockChange((World)((CraftWorld)this.signLoc.getWorld()).getHandle(), p);
        blockPacket.block = Blocks.STANDING_SIGN.getBlockData();
        final IChatBaseComponent[] lines = new IChatBaseComponent[4];
        for (int i = 0; i < 4; ++i) {
            lines[i] = (IChatBaseComponent)new ChatComponentText(strings[i]);
        }
        final PacketPlayOutUpdateSign sign = new PacketPlayOutUpdateSign((World)((CraftWorld)this.signLoc.getWorld()).getHandle(), p, lines);
        final PacketPlayOutOpenSignEditor packet = new PacketPlayOutOpenSignEditor(p);
        ((CraftPlayer)this.player).getHandle().playerConnection.sendPacket((Packet)blockPacket);
        ((CraftPlayer)this.player).getHandle().playerConnection.sendPacket((Packet)sign);
        ((CraftPlayer)this.player).getHandle().playerConnection.sendPacket((Packet)packet);
        this.user.setWaitingForSign(true);
        this.user.setCompletedSign(false);
        this.user.setSignContent(null);
        new BukkitRunnable() {
            int i = 0;
            
            public void run() {
                ++this.i;
                if (TradeMenu.tradeClose.containsKey((Object)SignInput.this.tradeUUID)) {
                    this.cancel();
                    SignInput.this.signLoc.getBlock().getState().update();
                    SignInput.SIGN_INPUT_QUERY.remove((Object)SignInput.this.player.getUniqueId());
                    SignInput.this.player.closeInventory();
                    SignInput.this.user.setWaitingForSign(false);
                    SignInput.this.user.setSignContent(null);
                    SignInput.this.user.setCompletedSign(false);
                    return;
                }
                if (SignInput.this.user.isWaitingForSign() && SignInput.this.user.isCompletedSign()) {
                    this.cancel();
                    SignInput.this.signLoc.getBlock().getState().update();
                    final GUI gui = ((GUISignItem)SignInput.SIGN_INPUT_QUERY.get((Object)SignInput.this.player.getUniqueId())).onSignClose(SignInput.this.user.getSignContent(), SignInput.this.user.toBukkitPlayer());
                    if (gui != null && !TradeMenu.tradeClose.containsKey((Object)SignInput.this.tradeUUID)) {
                        gui.open(SignInput.this.player);
                    }
                    SignInput.SIGN_INPUT_QUERY.remove((Object)SignInput.this.player.getUniqueId());
                    SignInput.this.user.setWaitingForSign(false);
                    SignInput.this.user.setSignContent(null);
                    SignInput.this.user.setCompletedSign(false);
                    return;
                }
                if (!SignInput.this.player.isOnline() || this.i >= SignInput.this.timeoutSec * 20) {
                    SignInput.this.player.playSound(SignInput.this.player.getLocation(), Sound.ENDERMAN_TELEPORT, 1.0f, -4.0f);
                    SignInput.this.player.sendMessage((Object)ChatColor.RED + "Timeout exceeded! You only have " + SignInput.this.timeoutSec + "s to type in the input!");
                    this.cancel();
                    SignInput.this.signLoc.getBlock().getState().update();
                    final GUI gui = ((GUISignItem)SignInput.SIGN_INPUT_QUERY.get((Object)SignInput.this.player.getUniqueId())).onSignClose("$canc", SignInput.this.user.toBukkitPlayer());
                    if (gui != null && !TradeMenu.tradeClose.containsKey((Object)SignInput.this.tradeUUID)) {
                        gui.open(SignInput.this.player);
                    }
                    SignInput.SIGN_INPUT_QUERY.remove((Object)SignInput.this.player.getUniqueId());
                    SignInput.this.user.setWaitingForSign(false);
                    SignInput.this.user.setSignContent(null);
                    SignInput.this.user.setCompletedSign(false);
                }
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 0L, 1L);
    }
    
    static {
        SIGN_INPUT_QUERY = (Map)new HashMap();
    }
}
