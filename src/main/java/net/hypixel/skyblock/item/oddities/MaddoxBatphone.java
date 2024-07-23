package net.hypixel.skyblock.item.oddities;

import java.util.ArrayList;
import java.util.Arrays;
import net.md_5.bungee.api.chat.ClickEvent;
import net.hypixel.skyblock.command.BatphoneCommand;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.hypixel.skyblock.features.sequence.SoundSequenceType;
import net.hypixel.skyblock.util.SUtil;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import net.hypixel.skyblock.item.SItem;
import org.bukkit.entity.Player;
import net.hypixel.skyblock.item.GenericItemType;
import net.hypixel.skyblock.item.Rarity;
import java.util.UUID;
import java.util.List;
import net.hypixel.skyblock.item.Ability;
import net.hypixel.skyblock.item.MaterialFunction;
import net.hypixel.skyblock.item.SkullStatistics;

public class MaddoxBatphone implements SkullStatistics, MaterialFunction, Ability
{
    private static final List<String> SUCCESSFUL_RESPONSES;
    private static final List<String> FAILED_RESPONSES;
    public static final List<UUID> RING_COOLDOWN;
    public static final List<UUID> CALL_COOLDOWN;
    
    @Override
    public String getURL() {
        return "9336d7cc95cbf6689f5e8c954294ec8d1efc494a4031325bb427bc81d56a484d";
    }
    
    @Override
    public String getDisplayName() {
        return "Maddox Batphone";
    }
    
    @Override
    public Rarity getRarity() {
        return Rarity.UNCOMMON;
    }
    
    @Override
    public GenericItemType getType() {
        return GenericItemType.ITEM;
    }
    
    @Override
    public String getAbilityName() {
        return "Whassup?";
    }
    
    @Override
    public String getAbilityDescription() {
        return "Lets you call Maddox, when he's not busy.";
    }
    
    @Override
    public int getAbilityCooldownTicks() {
        return 0;
    }
    
    @Override
    public int getManaCost() {
        return 0;
    }
    
    @Override
    public boolean displayUsage() {
        return false;
    }
    
    @Override
    public boolean isStackable() {
        return false;
    }
    
    @Override
    public void onAbilityUse(final Player player, final SItem sItem) {
        if (player.getWorld().getName().contains((CharSequence)"f6")) {
            player.playSound(player.getLocation(), Sound.WOOD_CLICK, 1.0f, 1.0f);
            player.sendMessage((Object)ChatColor.RED + "No service here!");
            return;
        }
        if (MaddoxBatphone.RING_COOLDOWN.contains((Object)player.getUniqueId())) {
            return;
        }
        MaddoxBatphone.RING_COOLDOWN.add((Object)player.getUniqueId());
        SUtil.delay(() -> MaddoxBatphone.RING_COOLDOWN.remove((Object)player.getUniqueId()), 52L);
        SoundSequenceType.MADDOX_BATPHONE.play(player);
        player.sendMessage((Object)ChatColor.YELLOW + "\u2706 Ring...");
        SUtil.delay(() -> player.sendMessage((Object)ChatColor.YELLOW + "\u2706 Ring... Ring..."), 18L);
        SUtil.delay(() -> player.sendMessage((Object)ChatColor.YELLOW + "\u2706 Ring... Ring... Ring..."), 35L);
        if (MaddoxBatphone.CALL_COOLDOWN.contains((Object)player.getUniqueId())) {
            SUtil.delay(() -> player.sendMessage((Object)ChatColor.RED + "\u2706 " + (String)SUtil.getRandom(MaddoxBatphone.FAILED_RESPONSES)), 52L);
            return;
        }
        final TextComponent message = new TextComponent((Object)ChatColor.DARK_GREEN + " " + (Object)ChatColor.BOLD + "[OPEN MENU]");
        message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (BaseComponent[])new TextComponent[] { new TextComponent((Object)ChatColor.YELLOW + "Click!") }));
        final String key = UUID.randomUUID().toString();
        BatphoneCommand.KEYS.add((Object)key);
        SUtil.delay(() -> BatphoneCommand.KEYS.remove((Object)key), 460L);
        message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/batphone " + (Object)BatphoneCommand.ACCESS_KEY + " " + key));
        SUtil.delay(() -> player.spigot().sendMessage((BaseComponent)new TextComponent(new BaseComponent[] { (BaseComponent)new TextComponent((Object)ChatColor.GREEN + "\u2706 " + (String)SUtil.getRandom(MaddoxBatphone.SUCCESSFUL_RESPONSES)), (BaseComponent)message })), 52L);
    }
    
    static {
        SUCCESSFUL_RESPONSES = Arrays.asList((Object[])new String[] { "Hello?", "Someone answers!", "How does a lobster answer? Shello!", "Hey what do you need?", "You hear the line pick up...", "You again? What do you want this time?" });
        FAILED_RESPONSES = Arrays.asList((Object[])new String[] { "Please leave your message after the beep.", "How can you tell if a bee is on the phone? You get a buzzy signal!", "The phone keeps ringing, is it broken?", "The phone picks up but it immediately hands up!", "What did the cat say on the phone? Can you hear meow?", "No answer.", "Seems like it's not picking up!", "\"Your call is important to us, please stay on the line\", so you hang up." });
        RING_COOLDOWN = (List)new ArrayList();
        CALL_COOLDOWN = (List)new ArrayList();
    }
}
