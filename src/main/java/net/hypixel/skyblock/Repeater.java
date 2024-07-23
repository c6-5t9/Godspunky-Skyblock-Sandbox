package net.hypixel.skyblock;

import java.util.HashMap;
import net.hypixel.skyblock.features.quest.QuestLine;
import net.hypixel.skyblock.features.slayer.SlayerQuest;
import java.util.Objects;
import net.hypixel.skyblock.entity.StaticDragonManager;
import net.hypixel.skyblock.features.region.Region;
import net.hypixel.skyblock.command.RebootServerCommand;
import net.hypixel.skyblock.features.scoreboard.Sidebar;
import org.jetbrains.annotations.NotNull;
import net.hypixel.skyblock.item.armor.ArmorSet;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import net.hypixel.skyblock.item.armor.TickingSet;
import net.hypixel.skyblock.util.Sputnik;
import net.hypixel.skyblock.item.bow.Terminator;
import net.hypixel.skyblock.util.SputnikPlayer;
import net.hypixel.skyblock.item.SpecificItemType;
import net.hypixel.skyblock.item.ItemListener;
import net.hypixel.skyblock.user.PlayerUtils;
import net.hypixel.skyblock.user.PlayerStatistics;
import net.hypixel.skyblock.item.GenericItemType;
import net.hypixel.skyblock.features.enchantment.EnchantmentType;
import org.bukkit.ChatColor;
import net.hypixel.skyblock.features.enchantment.Enchantment;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import net.hypixel.skyblock.item.SItem;
import net.hypixel.skyblock.util.ZSHash;
import net.hypixel.skyblock.item.SMaterial;
import java.util.concurrent.atomic.AtomicInteger;
import net.hypixel.skyblock.entity.dungeons.boss.sadan.SadanHuman;
import net.hypixel.skyblock.features.calendar.SkyBlockCalendar;
import net.hypixel.skyblock.entity.dungeons.boss.sadan.SadanBossManager;
import org.bukkit.util.Vector;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.Location;
import org.bukkit.entity.Chicken;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import net.hypixel.skyblock.util.SUtil;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Arrow;
import org.bukkit.World;
import org.bukkit.Effect;
import org.bukkit.Sound;
import net.hypixel.skyblock.entity.nms.VoidgloomSeraph;
import java.util.Set;
import net.hypixel.skyblock.features.dungeons.blessing.Blessings;
import org.bukkit.plugin.Plugin;
import java.util.Iterator;
import org.bukkit.Bukkit;
import net.hypixel.skyblock.util.SLog;
import org.bukkit.scheduler.BukkitRunnable;
import java.util.ArrayList;
import net.hypixel.skyblock.features.potion.ActivePotionEffect;
import net.hypixel.skyblock.user.User;
import net.hypixel.skyblock.config.Config;
import org.bukkit.scheduler.BukkitTask;
import java.util.List;
import net.minecraft.server.v1_8_R3.EntityFallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.entity.Entity;
import net.hypixel.skyblock.util.ManaReplacement;
import net.hypixel.skyblock.util.DefenseReplacement;
import java.util.UUID;
import java.util.Map;

public class Repeater
{
    public static final Map<UUID, Integer> MANA_MAP;
    public static final Map<UUID, Boolean> MANA_REGEN_DEC;
    public static final Map<UUID, DefenseReplacement> DEFENSE_REPLACEMENT_MAP;
    public static final Map<UUID, ManaReplacement> MANA_REPLACEMENT_MAP;
    public static final Map<UUID, Entity> BEACON_THROW2;
    public static final Map<Entity, Player> BEACON_OWNER;
    public static final Map<Entity, EntityFallingBlock> BEACON;
    public static final Map<UUID, Integer> PTN_CACHE;
    private final List<BukkitTask> tasks;
    public static int EFFECT_COUNTING;
    public static final Map<UUID, Integer> FloorLivingSec;
    public static Config config;
    
    public static void cRun(final User u) {
        final List<ActivePotionEffect> apt = u.getEffects();
        if (Repeater.PTN_CACHE.containsKey((Object)u.getUuid())) {
            if ((int)Repeater.PTN_CACHE.get((Object)u.getUuid()) >= apt.size()) {
                Repeater.PTN_CACHE.put((Object)u.getUuid(), (Object)0);
            }
        }
        else {
            Repeater.PTN_CACHE.put((Object)u.getUuid(), (Object)0);
        }
    }
    
    public Repeater() {
        (this.tasks = (List<BukkitTask>)new ArrayList()).add((Object)new BukkitRunnable() {
            public void run() {
                SLog.info("[SYSTEM] Auto-Saving players data and world data...");
                for (final Player player : Bukkit.getOnlinePlayers()) {
                    if (!player.isOnline()) {
                        return;
                    }
                    final User user = User.getUser(player.getUniqueId());
                    user.asyncSavingData();
                }
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 3000L, 3000L));
        this.tasks.add((Object)new BukkitRunnable() {
            public void run() {
                for (final User user : User.getCachedUsers()) {
                    if (Bukkit.getPlayer(user.getUuid()) != null && !Bukkit.getPlayer(user.getUuid()).isOnline()) {
                        User.getHash().remove((Object)user.getUuid());
                    }
                }
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 1L, 1L));
        this.tasks.add((Object)new BukkitRunnable() {
            public void run() {
                Blessings.update();
                for (final Player player : Bukkit.getOnlinePlayers()) {
                    Location blockLocation = player.getEyeLocation();
                    try {
                        blockLocation = player.getTargetBlock((Set)null, 30).getLocation();
                    }
                    catch (final IllegalStateException ex) {}
                    final Location crystalLocation = player.getEyeLocation();
                    final Vector vector = blockLocation.clone().add(0.1, 0.0, 0.1).toVector().subtract(crystalLocation.clone().toVector());
                    final double count = 10.0;
                    final double length = 0.0;
                    for (int i = 1; i <= 10; ++i) {
                        for (final Entity entity : player.getWorld().getNearbyEntities(crystalLocation.clone().add(vector.clone().multiply(i / 10.0)), 0.25, 0.2, 0.25)) {
                            if (entity.hasMetadata("Nukekubi") && VoidgloomSeraph.NUKEKUBI_TARGET.containsKey((Object)entity) && VoidgloomSeraph.NUKEKUBI_TARGET.get((Object)entity) == player) {
                                entity.remove();
                                player.playSound(player.getLocation(), Sound.SILVERFISH_HIT, 1.0f, 0.0f);
                                entity.getWorld().playEffect(entity.getLocation(), Effect.EXPLOSION, 1);
                                entity.getWorld().playEffect(entity.getLocation(), Effect.EXPLOSION, 1);
                                if (!VoidgloomSeraph.LivingSkulls.containsKey((Object)((Player)VoidgloomSeraph.NUKEKUBI_TARGET.get((Object)entity)).getUniqueId())) {
                                    continue;
                                }
                                ((List)VoidgloomSeraph.LivingSkulls.get((Object)((Player)VoidgloomSeraph.NUKEKUBI_TARGET.get((Object)entity)).getUniqueId())).remove((Object)entity);
                            }
                        }
                    }
                }
                for (final World world : Bukkit.getWorlds()) {
                    if (world.getName().toLowerCase().contains((CharSequence)"f6")) {
                        for (final Entity e : world.getEntities()) {
                            if (e.hasMetadata("Giant_")) {
                                for (final Entity entity2 : e.getNearbyEntities(3.0, 11.0, 3.0)) {
                                    if (entity2 instanceof Arrow) {
                                        final Projectile arr = (Projectile)entity2;
                                        if (!(arr.getShooter() instanceof Player)) {
                                            return;
                                        }
                                        SUtil.giantsHitboxFix(arr);
                                    }
                                }
                            }
                            if (e instanceof Item) {
                                if (((Item)e).getItemStack().getType() == Material.FLOWER_POT_ITEM) {
                                    e.remove();
                                }
                                if (((Item)e).getItemStack().getType() == Material.IRON_TRAPDOOR) {
                                    e.remove();
                                }
                            }
                            if (e instanceof Chicken) {
                                e.remove();
                            }
                        }
                        for (final Entity e : world.getNearbyEntities(new Location(world, 191.0, 54.0, 266.0), 4.0, 11.0, 4.0)) {
                            if (e instanceof Player) {
                                final Player player2 = (Player)e;
                                User.getUser(player2.getUniqueId()).kill(EntityDamageEvent.DamageCause.VOID, null);
                            }
                        }
                    }
                }
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 0L, 1L));
        this.tasks.add((Object)new BukkitRunnable() {
            public void run() {
                for (final Player player : Bukkit.getOnlinePlayers()) {
                    if (Repeater.PTN_CACHE.containsKey((Object)User.getUser(player.getUniqueId()).getUuid())) {
                        Repeater.PTN_CACHE.put((Object)User.getUser(player.getUniqueId()).getUuid(), (Object)((int)Repeater.PTN_CACHE.get((Object)User.getUser(player.getUniqueId()).getUuid()) + 1));
                    }
                    else {
                        Repeater.PTN_CACHE.put((Object)User.getUser(player.getUniqueId()).getUuid(), (Object)0);
                    }
                }
                for (final World w : Bukkit.getWorlds()) {
                    if (w.getName().contains((CharSequence)"f6") && !w.getName().equals((Object)"f6") && w.getPlayers().size() == 0) {
                        SadanBossManager.endFloor(w);
                        SLog.warn("[WORLD CLEARER] Cleared F6 Bossroom world " + w.getName() + ". Reason: No player inside");
                    }
                }
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 0L, 45L));
        this.tasks.add((Object)new BukkitRunnable() {
            public void run() {
                SkyBlockCalendar.ELAPSED += 20L;
                for (final World w : Bukkit.getWorlds()) {
                    if (SadanHuman.BossRun.containsKey((Object)w.getUID()) && w.getName().contains((CharSequence)"f6") && SadanHuman.BossRun.containsKey((Object)w.getUID()) && (boolean)SadanHuman.BossRun.get((Object)w.getUID())) {
                        Repeater.FloorLivingSec.put((Object)w.getUID(), (Object)((int)Repeater.FloorLivingSec.get((Object)w.getUID()) + 1));
                    }
                }
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 0L, 20L));
    }
    
    public void stop() {
        for (final BukkitTask task : this.tasks) {
            task.cancel();
        }
    }
    
    public int runningTasks() {
        return this.tasks.size();
    }
    
    public static void runPlayerTask(final Player player, final int[] counters, final List<AtomicInteger> counters2) {
        cRun(User.getUser(player.getUniqueId()));
        if (User.getUser(player.getUniqueId()).getActivePet() != null && User.getUser(player.getUniqueId()).getActivePet().getType() == SMaterial.HIDDEN_USSR_T34_TANK_PET) {
            player.getWorld().playSound(player.getLocation(), Sound.MINECART_INSIDE, 0.09f, 0.3f);
        }
        if (!ZSHash.Charges.containsKey((Object)player.getUniqueId())) {
            ZSHash.Charges.put((Object)player.getUniqueId(), (Object)5);
        }
        if ((int)ZSHash.Charges.get((Object)player.getUniqueId()) < 5) {
            if (!ZSHash.Cooldown.containsKey((Object)player.getUniqueId())) {
                ZSHash.Cooldown.put((Object)player.getUniqueId(), (Object)15);
            }
            ZSHash.Cooldown.put((Object)player.getUniqueId(), (Object)((int)ZSHash.Cooldown.get((Object)player.getUniqueId()) - 1));
            if ((int)ZSHash.Cooldown.get((Object)player.getUniqueId()) <= 0) {
                ZSHash.Cooldown.put((Object)player.getUniqueId(), (Object)15);
                ZSHash.Charges.put((Object)player.getUniqueId(), (Object)((int)ZSHash.Charges.get((Object)player.getUniqueId()) + 1));
            }
        }
        final PlayerInventory inv1 = player.getInventory();
        final SItem helm = SItem.find(inv1.getHelmet());
        final SItem chest = SItem.find(inv1.getChestplate());
        final SItem legs = SItem.find(inv1.getLeggings());
        final SItem boot = SItem.find(inv1.getBoots());
        if (helm != null && chest != null && legs != null && boot != null && helm.getType() == SMaterial.SORROW_HELMET && chest.getType() == SMaterial.SORROW_CHESTPLATE && legs.getType() == SMaterial.SORROW_LEGGINGS && boot.getType() == SMaterial.SORROW_BOOTS) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 100000, 1));
        }
        else {
            player.removePotionEffect(PotionEffectType.INVISIBILITY);
        }
        if (helm != null && (helm.getType() == SMaterial.HIDDEN_USSR_HELMET || helm.getType() == SMaterial.HIDDEN_DONATOR_HELMET)) {
            final ItemStack SSRstack = helm.getStack();
            SSRstack.setDurability((short)SUtil.random(0, 15));
            player.getInventory().setHelmet(SSRstack);
        }
        final PlayerInventory inventory = player.getInventory();
        final SItem sitem = SItem.find(player.getItemInHand());
        if (sitem != null) {
            if (sitem.getType() != SMaterial.ENCHANTED_BOOK && sitem.getEnchantments() != null) {
                final List<Enchantment> enchL = Enchantment.ultimateEnchantsListFromList(sitem.getEnchantments());
                if (enchL.size() > 1) {
                    for (final Enchantment ench : enchL) {
                        sitem.removeEnchantment(ench.getType());
                    }
                    player.sendMessage((Object)ChatColor.RED + "Your item have multiple legacy ultimate enchantments so we need to remove all of them, sorry! You can always get a new one, also just to remind you, only 1 ultimate enchantment is allowed per weapon.");
                }
            }
            if (sitem.getEnchantment(EnchantmentType.ONE_FOR_ALL) != null && sitem.getType() != SMaterial.ENCHANTED_BOOK && sitem.getType().getStatistics().getType() == GenericItemType.WEAPON) {
                for (final Enchantment enchantment : sitem.getEnchantments()) {
                    if (enchantment.getType() != EnchantmentType.TELEKINESIS && enchantment.getType() != EnchantmentType.ONE_FOR_ALL) {
                        sitem.removeEnchantment(enchantment.getType());
                    }
                }
            }
        }
        final PlayerStatistics statistics1 = (PlayerStatistics)PlayerUtils.STATISTICS_CACHE.get((Object)player.getUniqueId());
        for (int i = 0; i <= inventory.getSize(); ++i) {
            final ItemStack stack = inventory.getItem(i);
            final SItem sItem = SItem.find(stack);
            final int slot = 15 + i;
            if (sItem != null && sItem.getType().getStatistics().getType() != GenericItemType.ACCESSORY) {
                statistics1.zeroAll(slot);
            }
            if (stack == null) {
                statistics1.zeroAll(slot);
            }
        }
        player.setSaturation(1000.0f);
        player.setFoodLevel(20);
        final UUID uuid = player.getUniqueId();
        if (!PlayerUtils.STATISTICS_CACHE.containsKey((Object)uuid)) {
            PlayerUtils.STATISTICS_CACHE.put((Object)uuid, (Object)PlayerUtils.getStatistics(player));
        }
        final PlayerStatistics statistics2 = (PlayerStatistics)PlayerUtils.STATISTICS_CACHE.get((Object)uuid);
        final int manaPool = SUtil.blackMagic(100.0 + statistics2.getIntelligence().addAll());
        SItem hand = SItem.find(inventory.getItemInHand());
        if (hand == null) {
            hand = SItem.of(inventory.getItemInHand());
            if (hand != null) {
                if (player.getItemInHand().hasItemMeta() && player.getItemInHand().getItemMeta().getDisplayName().contains((CharSequence)"minion")) {
                    return;
                }
                player.setItemInHand(hand.getStack());
            }
        }
        PlayerUtils.updateHandStatistics(hand, statistics2);
        PlayerUtils.updatePetStatistics(statistics2);
        PlayerUtils.updateInventoryStatistics(player, statistics2);
        ItemListener.updateStatistics1(player);
        final User user = User.getUser(player.getUniqueId());
        for (final ActivePotionEffect effect : user.getEffects()) {
            effect.setRemaining(effect.getRemaining() - 20L);
        }
        PlayerUtils.subtractDurationCookie(player, 20L);
        PlayerUtils.updatePotionEffects(user, statistics2);
        if (!player.getWorld().getName().equalsIgnoreCase("limbo")) {
            if (hand != null) {
                if (hand.getType().getStatistics().getType() != GenericItemType.BLOCK && hand.getType().getStatistics().getType() != GenericItemType.ITEM && hand.getType().getStatistics().getType() != GenericItemType.PET) {
                    hand.getData().setString("owner", player.getUniqueId().toString());
                }
                hand.update();
                if (hand.getType().getStatistics().getType() != GenericItemType.BLOCK && hand.getType().getStatistics().getType() != GenericItemType.ITEM && hand.getType().getStatistics().getType() != GenericItemType.PET) {
                    player.getItemInHand().setItemMeta(user.updateItemBoost(hand).getItemMeta());
                }
                final SItem last = SItem.find(inventory.getItem(8));
                if (hand.getType().getStatistics().getSpecificType() == SpecificItemType.BOW && user.hasQuiverItem(SMaterial.ARROW) && (last == null || last.getType() != SMaterial.QUIVER_ARROW)) {
                    inventory.setItem(8, SUtil.setStackAmount(SItem.of(SMaterial.QUIVER_ARROW).getStack(), Math.min(64, user.getQuiver(SMaterial.ARROW))));
                }
                if (hand.getType().getStatistics().getSpecificType() != SpecificItemType.BOW) {
                    inventory.setItem(8, SItem.of(SMaterial.SKYBLOCK_MENU).getStack());
                }
            }
            else {
                inventory.setItem(8, SItem.of(SMaterial.SKYBLOCK_MENU).getStack());
            }
        }
        if (!Repeater.MANA_MAP.containsKey((Object)uuid)) {
            Repeater.MANA_MAP.put((Object)uuid, (Object)manaPool);
        }
        if (counters[0] == 2) {
            final int mana = (int)Repeater.MANA_MAP.get((Object)uuid);
            if (mana <= manaPool) {
                int reg = Math.min(manaPool, Math.min(manaPool, manaPool / 50 + (int)(manaPool / 50 * statistics2.getManaRegenerationPercentBonus())));
                if (Repeater.MANA_REGEN_DEC.containsKey((Object)uuid)) {
                    if (Repeater.MANA_REGEN_DEC.get((Object)uuid)) {
                        reg = mana + Math.round((float)(reg / 10));
                    }
                    else {
                        reg += mana;
                    }
                }
                else {
                    reg += mana;
                }
                Repeater.MANA_MAP.remove((Object)uuid);
                Repeater.MANA_MAP.put((Object)uuid, (Object)Math.min(manaPool, reg));
            }
        }
        if (counters[1] == 4 && player.getHealth() <= player.getMaxHealth()) {
            player.setHealth(Math.min(player.getMaxHealth(), player.getHealth() + 1.5 + (int)player.getMaxHealth() * 0.01 + (1.5 + (int)player.getMaxHealth() * 0.01) * statistics2.getHealthRegenerationPercentBonus()));
        }
        PlayerUtils.updateSetStatistics(player, SItem.find(inventory.getHelmet()), SItem.find(inventory.getChestplate()), SItem.find(inventory.getLeggings()), SItem.find(inventory.getBoots()), statistics2);
        final double health = statistics2.getMaxHealth().addAll();
        player.setHealthScale(Math.min(40.0, 20.0 + (health - 100.0) / 25.0));
        SputnikPlayer.updateScaledAHP(player);
        SUtil.runSync(() -> player.setWalkSpeed(Math.min((float)(statistics2.getSpeed().addAll() / 5.0), 1.0f)));
        final int defense = SUtil.blackMagic(statistics2.getDefense().addAll());
        final float absorption = SputnikPlayer.getCustomAbsorptionHP(player);
        final ChatColor color = (absorption > 0.0f) ? ChatColor.GOLD : ChatColor.RED;
        DefenseReplacement replacement = (DefenseReplacement)Repeater.DEFENSE_REPLACEMENT_MAP.get((Object)player.getUniqueId());
        ManaReplacement replacement2 = (ManaReplacement)Repeater.MANA_REPLACEMENT_MAP.get((Object)player.getUniqueId());
        if (replacement != null && System.currentTimeMillis() >= replacement.getEnd()) {
            Repeater.DEFENSE_REPLACEMENT_MAP.remove((Object)player.getUniqueId());
            replacement = null;
        }
        if (replacement2 != null && System.currentTimeMillis() >= replacement2.getEnd()) {
            Repeater.MANA_REPLACEMENT_MAP.remove((Object)player.getUniqueId());
            replacement2 = null;
        }
        String ZSActionBar = "";
        String ABTerminator = "";
        if (!ZSHash.Charges.containsKey((Object)player.getUniqueId())) {
            ZSHash.Charges.put((Object)player.getUniqueId(), (Object)5);
        }
        if ((int)ZSHash.Charges.get((Object)player.getUniqueId()) == 5) {
            ZSActionBar = ChatColor.translateAlternateColorCodes('&', "     &e&l\u24e9\u24e9\u24e9\u24e9\u24e9");
        }
        else if ((int)ZSHash.Charges.get((Object)player.getUniqueId()) == 4) {
            ZSActionBar = ChatColor.translateAlternateColorCodes('&', "     &e&l\u24e9\u24e9\u24e9\u24e9&6&l\u24c4");
        }
        else if ((int)ZSHash.Charges.get((Object)player.getUniqueId()) == 3) {
            ZSActionBar = ChatColor.translateAlternateColorCodes('&', "     &e&l\u24e9\u24e9\u24e9&6&l\u24c4\u24c4");
        }
        else if ((int)ZSHash.Charges.get((Object)player.getUniqueId()) == 2) {
            ZSActionBar = ChatColor.translateAlternateColorCodes('&', "     &e&l\u24e9\u24e9&6&l\u24c4\u24c4\u24c4");
        }
        else if ((int)ZSHash.Charges.get((Object)player.getUniqueId()) == 1) {
            ZSActionBar = ChatColor.translateAlternateColorCodes('&', "     &e&l\u24e9&6&l\u24c4\u24c4\u24c4\u24c4");
        }
        else if ((int)ZSHash.Charges.get((Object)player.getUniqueId()) == 0) {
            ZSActionBar = ChatColor.translateAlternateColorCodes('&', "     &6&l\u24c4\u24c4\u24c4\u24c4\u24c4");
        }
        if (!Terminator.CountTerm.containsKey((Object)player.getUniqueId())) {
            Terminator.CountTerm.put((Object)player.getUniqueId(), (Object)0);
        }
        if ((int)Terminator.CountTerm.get((Object)player.getUniqueId()) == 0) {
            ABTerminator = "";
        }
        else if ((int)Terminator.CountTerm.get((Object)player.getUniqueId()) == 1) {
            ABTerminator = Sputnik.trans("  &6T1");
        }
        else if ((int)Terminator.CountTerm.get((Object)player.getUniqueId()) == 2) {
            ABTerminator = Sputnik.trans("  &6T2");
        }
        else if ((int)Terminator.CountTerm.get((Object)player.getUniqueId()) >= 3) {
            ABTerminator = Sputnik.trans("  &a&lT3!");
        }
        if (SItem.find(player.getItemInHand()) != null) {
            if (SItem.find(player.getItemInHand()).getType() != SMaterial.ZOMBIE_SWORD_T2 && SItem.find(player.getItemInHand()).getType() != SMaterial.ZOMBIE_SWORD_T3) {
                ZSActionBar = "";
            }
            if (SItem.find(player.getItemInHand()).getType() != SMaterial.TERMINATOR) {
                ABTerminator = "";
            }
        }
        else {
            ZSActionBar = "";
            ABTerminator = "";
        }
        if (!player.getWorld().getName().equalsIgnoreCase("limbo")) {
            SUtil.sendActionBar(player, (Object)color + "" + Math.round(player.getHealth() + absorption) + "/" + SUtil.blackMagic(statistics2.getMaxHealth().addAll()) + "\u2764" + get(player) + "     " + ((replacement == null) ? ((defense != 0) ? ("" + (Object)ChatColor.GREEN + defense + "\u2748 Defense" + ABTerminator + "     ") : "") : (replacement.getReplacement() + "     ")) + ((replacement2 == null) ? ((manaPool >= 0) ? ("" + (Object)ChatColor.AQUA + Repeater.MANA_MAP.get((Object)player.getUniqueId()) + "/" + manaPool + "\u270e Mana") : "") : replacement2.getReplacement()) + ZSActionBar);
        }
        else {
            SUtil.sendActionBar(player, (Object)color + "" + (Object)ChatColor.RED + (Object)ChatColor.BOLD + "YOU ARE CURRENTLY IN THE LIMBO SERVER, USE " + (Object)ChatColor.GOLD + (Object)ChatColor.BOLD + "/HUB " + (Object)ChatColor.RED + (Object)ChatColor.BOLD + "TO WARP OUT");
        }
        statistics2.zeroAll(8);
        final ArmorSet set = SMaterial.getIncompleteArmorSet(inventory);
        if (set instanceof TickingSet) {
            ((TickingSet)set).tick(player, SItem.find(inventory.getHelmet()), SItem.find(inventory.getChestplate()), SItem.find(inventory.getLeggings()), SItem.find(inventory.getBoots()), counters2);
        }
        SUtil.runSync(() -> {
            final Sidebar sidebar = new Sidebar(Sputnik.trans("&e&lSKYBLOCK &c&lSANDBOX"), "SKYBLOCK");
            SUtil.runAsync(() -> {
                String strd = SUtil.getDate();
                if (RebootServerCommand.secondMap.containsKey((Object)Bukkit.getServer())) {
                    if ((int)RebootServerCommand.secondMap.get((Object)Bukkit.getServer()) >= 10) {
                        strd = (Object)ChatColor.RED + "Server closing: 00:" + RebootServerCommand.secondMap.get((Object)Bukkit.getServer());
                    }
                    else {
                        strd = (Object)ChatColor.RED + "Server closing: 00:0" + RebootServerCommand.secondMap.get((Object)Bukkit.getServer());
                    }
                }
                sidebar.add((Object)ChatColor.GRAY + strd + " " + (Object)ChatColor.DARK_GRAY + SkyBlock.getPlugin().getServerName());
                sidebar.add("  ");
                sidebar.add(" " + SkyBlockCalendar.getMonthName() + " " + SUtil.ntify(SkyBlockCalendar.getDay()));
                final int hours = 12;
                final int minutes = 31;
                sidebar.add((Object)ChatColor.GRAY + " " + hours + ":" + SUtil.zeroed(minutes) + "pm " + (Object)ChatColor.YELLOW + "\u2600");
                String location = (Object)ChatColor.GRAY + "None";
                final Region region = Region.getRegionOfEntity((Entity)player);
                if (region != null) {
                    user.setLastRegion(region);
                    if (region.getType().getName() != null) {
                        location = (Object)region.getType().getColor() + region.getType().getName();
                    }
                }
                if (user.isOnIsland()) {
                    location = (Object)ChatColor.GREEN + "Your Island";
                }
                if (user.isOnUserIsland()) {
                    location = (Object)ChatColor.AQUA + "Others Island";
                }
                else if (player.getWorld().getName().contains((CharSequence)"f6")) {
                    sidebar.add((Object)ChatColor.GRAY + " \u23e3 " + (Object)ChatColor.RED + "Catacombs" + (Object)ChatColor.GRAY + " (F6)");
                }
                else if (player.getWorld().getName().contains((CharSequence)"arena")) {
                    sidebar.add((Object)ChatColor.GRAY + " \u23e3 " + (Object)ChatColor.RED + "Withering Ruins");
                }
                else if (player.getWorld().getName().contains((CharSequence)"gisland")) {
                    sidebar.add((Object)ChatColor.GRAY + " \u23e3 " + (Object)ChatColor.YELLOW + "Giant's Island");
                }
                else {
                    sidebar.add((Object)ChatColor.GRAY + " \u23e3 " + location);
                }
                if (!player.getWorld().getName().startsWith("f6") && !player.getWorld().getName().equalsIgnoreCase("arena")) {
                    sidebar.add(" ");
                    final StringBuilder coinsDisplay = new StringBuilder();
                    if (user.isPermanentCoins()) {
                        coinsDisplay.append("Purse: ");
                    }
                    else if (PlayerUtils.hasItem(player, SMaterial.PIGGY_BANK) || PlayerUtils.hasItem(player, SMaterial.CRACKED_PIGGY_BANK)) {
                        coinsDisplay.append("Piggy: ");
                    }
                    else {
                        coinsDisplay.append("Purse: ");
                    }
                    sidebar.add((Object)coinsDisplay.append((Object)ChatColor.GOLD).append(SUtil.commaify(user.getCoins())) + ".0" + (Object)ChatColor.YELLOW);
                    sidebar.add("Bits: " + (Object)ChatColor.AQUA + user.getBits());
                    sidebar.add("   ");
                    final SlayerQuest quest = user.getSlayerQuest();
                    final QuestLine line = user.getQuestLine();
                    if ((!StaticDragonManager.ACTIVE || StaticDragonManager.DRAGON == null) && quest == null && line != null) {
                        sidebar.add((Object)ChatColor.WHITE + "Objective");
                        sidebar.add((Object)ChatColor.YELLOW + line.getObjective(user).getDisplay());
                        if (line.getObjective(user).hasSuffix(user)) {
                            sidebar.add(line.getObjective(user).getSuffix(user));
                        }
                        sidebar.add("      ");
                    }
                    if ((!StaticDragonManager.ACTIVE || StaticDragonManager.DRAGON == null) && quest != null && (quest.getDied() == 0L || quest.getKilled() != 0L)) {
                        sidebar.add("Slayer Quest");
                        sidebar.add(quest.getType().getDisplayName());
                        if (quest.getKilled() != 0L) {
                            sidebar.add((Object)ChatColor.GREEN + "Boss slain!");
                        }
                        else if (quest.getXp() >= quest.getType().getSpawnXP()) {
                            sidebar.add((Object)ChatColor.YELLOW + "Slay the boss!");
                        }
                        else if (quest.getLastKilled() != null) {
                            double xpDropped = quest.getLastKilled().getStatistics().getXPDropped();
                            if (PlayerUtils.getCookieDurationTicks(player) > 0L) {
                                xpDropped += quest.getLastKilled().getStatistics().getXPDropped() * 35.0 / 100.0;
                            }
                            sidebar.add((Object)ChatColor.YELLOW + " " + SUtil.commaify((int)(quest.getXp() / xpDropped)) + (Object)ChatColor.GRAY + "/" + (Object)ChatColor.RED + SUtil.commaify((int)(quest.getType().getSpawnXP() / xpDropped)) + (Object)ChatColor.GRAY + " Kills");
                        }
                        else {
                            sidebar.add((Object)ChatColor.GRAY + " (" + (Object)ChatColor.YELLOW + SUtil.commaify((int)quest.getXp()) + (Object)ChatColor.GRAY + "/" + (Object)ChatColor.RED + Sputnik.formatFull((float)quest.getType().getSpawnXP()) + (Object)ChatColor.GRAY + ") Combat XP");
                        }
                        sidebar.add("    ");
                    }
                    if (StaticDragonManager.ACTIVE && StaticDragonManager.DRAGON != null) {
                        sidebar.add("Dragon HP: " + (Object)ChatColor.GREEN + SUtil.commaify((int)StaticDragonManager.DRAGON.getEntity().getHealth()) + (Object)ChatColor.RED + " \u2764");
                        int dmgdealt;
                        if (StaticDragonManager.DRAGON.getDamageDealt().containsKey((Object)uuid)) {
                            final double dealt = (double)StaticDragonManager.DRAGON.getDamageDealt().get((Object)uuid);
                            dmgdealt = (int)Math.round(dealt);
                            if (dmgdealt < 0) {
                                dmgdealt = Integer.MAX_VALUE;
                            }
                        }
                        else {
                            dmgdealt = 0;
                        }
                        sidebar.add("Your Damage: " + (Object)ChatColor.RED + SUtil.commaify(dmgdealt));
                        sidebar.add("     ");
                    }
                }
                else if (player.getWorld().getName().startsWith("f6") && !player.getWorld().getName().equals((Object)"f6")) {
                    sidebar.add((Object)ChatColor.RED + " ");
                    if (Repeater.FloorLivingSec.containsKey((Object)player.getWorld().getUID())) {
                        sidebar.add(ChatColor.translateAlternateColorCodes('&', "&fTime Elapsed: &a" + Sputnik.formatTime((int)Repeater.FloorLivingSec.get((Object)player.getWorld().getUID()))));
                    }
                    else {
                        sidebar.add(ChatColor.translateAlternateColorCodes('&', "&fTime Elapsed: &a00m 00s"));
                    }
                    sidebar.add(ChatColor.translateAlternateColorCodes('&', "&fDungeon Cleared: &cN/A%"));
                    sidebar.add((Object)ChatColor.RED + "  ");
                    final String playerName = player.getName();
                    if (player.getWorld().getPlayers().size() > 1) {
                        for (final Player dungeonMate : player.getWorld().getPlayers()) {
                            final String backend = getFormatted(dungeonMate);
                            if (Objects.equals((Object)dungeonMate.getName(), (Object)playerName)) {
                                continue;
                            }
                            sidebar.add(ChatColor.translateAlternateColorCodes('&', "&e[N/A&e] &b" + dungeonMate.getName() + backend));
                        }
                    }
                    else if (player.getWorld().getPlayers().size() == 1) {
                        sidebar.add((Object)ChatColor.DARK_GRAY + "SOLO");
                    }
                    else if (player.getWorld().getName().startsWith("f6") && player.getWorld().getPlayers().size() > 5) {
                        sidebar.add((Object)ChatColor.RED + "Cannot display more than 5 players at once!");
                    }
                    final QuestLine line2 = user.getQuestLine();
                    if (line2 != null) {
                        sidebar.add(" ");
                        sidebar.add((Object)ChatColor.WHITE + "Objective");
                        sidebar.add((Object)ChatColor.YELLOW + line2.getObjective(user).getDisplay());
                        if (line2.getObjective(user).hasSuffix(user)) {
                            sidebar.add(line2.getObjective(user).getSuffix(user));
                        }
                        sidebar.add("      ");
                    }
                    sidebar.add((Object)ChatColor.AQUA + "     ");
                }
                sidebar.add((Object)ChatColor.YELLOW + Repeater.config.getString("ip"));
                if (!player.getWorld().getName().equalsIgnoreCase("dungeon")) {
                    sidebar.apply(player);
                }
            });
        });
    }
    
    @NotNull
    private static String getFormatted(final Player dungeonMate) {
        String colorCode;
        if (dungeonMate.getHealth() <= dungeonMate.getMaxHealth() / 2.0 && dungeonMate.getHealth() > dungeonMate.getMaxHealth() * 25.0 / 100.0) {
            colorCode = "e";
        }
        else if (dungeonMate.getHealth() <= dungeonMate.getMaxHealth() * 25.0 / 100.0) {
            colorCode = "c";
        }
        else {
            colorCode = "a";
        }
        return " &" + colorCode + (int)dungeonMate.getHealth() + "&c\u2764";
    }
    
    public static String get(final Player p) {
        return "";
    }
    
    static {
        MANA_MAP = (Map)new HashMap();
        MANA_REGEN_DEC = (Map)new HashMap();
        DEFENSE_REPLACEMENT_MAP = (Map)new HashMap();
        MANA_REPLACEMENT_MAP = (Map)new HashMap();
        BEACON_THROW2 = (Map)new HashMap();
        BEACON_OWNER = (Map)new HashMap();
        BEACON = (Map)new HashMap();
        PTN_CACHE = (Map)new HashMap();
        Repeater.EFFECT_COUNTING = 0;
        FloorLivingSec = (Map)new HashMap();
        Repeater.config = SkyBlock.getPlugin().config;
    }
}
