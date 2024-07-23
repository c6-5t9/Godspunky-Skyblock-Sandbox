package net.hypixel.skyblock.listener;

import org.bukkit.GameMode;
import net.hypixel.skyblock.user.User;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.entity.Player;
import org.bukkit.block.Block;
import net.hypixel.skyblock.features.region.Region;
import org.bukkit.ChatColor;
import net.hypixel.skyblock.features.region.RegionGenerator;
import net.hypixel.skyblock.command.RegionCommand;
import org.bukkit.event.player.PlayerInteractEvent;

public class BlockListener extends PListener
{
    @EventHandler
    public void onBlockInteract(final PlayerInteractEvent e) {
        final Block block = e.getClickedBlock();
        if (block == null) {
            return;
        }
        final Player player = e.getPlayer();
        if (!RegionCommand.REGION_GENERATION_MAP.containsKey((Object)player)) {
            return;
        }
        e.setCancelled(true);
        final RegionGenerator generator = (RegionGenerator)RegionCommand.REGION_GENERATION_MAP.get((Object)player);
        switch (generator.getPhase()) {
            case 1: {
                generator.setFirstLocation(block.getLocation());
                generator.setPhase(2);
                player.sendMessage((Object)ChatColor.GRAY + "Set your clicked block as the first location of the region!");
                player.sendMessage((Object)ChatColor.DARK_AQUA + "Click the second corner of your region.");
                break;
            }
            case 2: {
                generator.setSecondLocation(block.getLocation());
                if (generator.getModificationType().equals((Object)"create")) {
                    Region.create(generator.getName(), generator.getFirstLocation(), generator.getSecondLocation(), generator.getType());
                }
                else {
                    final Region region = Region.get(generator.getName());
                    region.setFirstLocation(generator.getFirstLocation());
                    region.setSecondLocation(generator.getSecondLocation());
                    region.setType(generator.getType());
                    region.save();
                }
                player.sendMessage((Object)ChatColor.GRAY + "Region \"" + generator.getName() + "\" has been fully set up and " + generator.getModificationType() + "d!");
                RegionCommand.REGION_GENERATION_MAP.remove((Object)player);
                break;
            }
        }
    }
    
    @EventHandler
    public void onBlockPlace(final BlockPlaceEvent event) {
        final Player player = event.getPlayer();
        final User user = User.getUser(player);
        if (user.isOnUserIsland() && player.getGameMode() != GameMode.CREATIVE && player.getWorld().getName().equalsIgnoreCase("world")) {
            event.setCancelled(true);
        }
    }
}
