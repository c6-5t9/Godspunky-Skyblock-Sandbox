package net.hypixel.skyblock.item;

import java.util.HashMap;
import net.hypixel.skyblock.entity.dungeons.boss.sadan.JollyPinkGiant;
import net.hypixel.skyblock.entity.dungeons.boss.sadan.SadanGiant;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Arrow;
import org.bukkit.event.entity.ProjectileHitEvent;
import net.hypixel.skyblock.item.armor.Witherborn;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import net.hypixel.skyblock.features.collection.ItemCollection;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.block.BlockState;
import org.bukkit.entity.EntityType;
import org.bukkit.block.Block;
import org.bukkit.Bukkit;
import java.util.List;
import java.util.ArrayList;
import net.hypixel.skyblock.entity.StaticDragonManager;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.GameMode;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.inventory.Inventory;
import net.minecraft.server.v1_8_R3.NBTBase;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.hypixel.skyblock.item.storage.Storage;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.util.Vector;
import org.bukkit.Location;
import org.bukkit.Effect;
import org.bukkit.plugin.Plugin;
import net.hypixel.skyblock.SkyBlock;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.entity.Damageable;
import net.hypixel.skyblock.entity.SEntity;
import net.hypixel.skyblock.entity.SEntityType;
import net.hypixel.skyblock.item.weapon.EdibleMace;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Item;
import net.hypixel.skyblock.util.SUtil;
import net.hypixel.skyblock.features.skill.Skill;
import net.hypixel.skyblock.util.Groups;
import net.hypixel.skyblock.features.enchantment.EnchantmentType;
import net.hypixel.skyblock.features.enchantment.Enchantment;
import net.hypixel.skyblock.user.PlayerStatistics;
import net.hypixel.skyblock.user.User;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Villager;
import org.bukkit.entity.EnderDragonPart;
import java.util.Set;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutAnimation;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import java.util.Iterator;
import net.hypixel.skyblock.util.Sputnik;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.entity.Entity;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.EventHandler;
import java.util.UUID;
import net.hypixel.skyblock.util.ManaReplacement;
import org.bukkit.Sound;
import net.md_5.bungee.api.ChatColor;
import net.hypixel.skyblock.util.DefenseReplacement;
import net.hypixel.skyblock.Repeater;
import net.hypixel.skyblock.user.PlayerUtils;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.entity.Player;
import java.util.Map;
import net.hypixel.skyblock.listener.PListener;

public class ItemListener extends PListener
{
    public static final Map<Player, String> Classes;
    public static final Map<Player, Boolean> IsDead;
    
    @EventHandler
    public void useEtherWarp(final PlayerInteractEvent e) {
        if (!SItem.isSpecItem(e.getItem())) {
            return;
        }
        final SItem sItem = SItem.find(e.getItem());
        if (null == sItem) {
            return;
        }
        final Player player = e.getPlayer();
        final UUID uuid = player.getUniqueId();
        updateStatistics(e.getPlayer());
        final Action action = e.getAction();
        if (sItem.getDataString("etherwarp_trans").equals((Object)"true") && (Action.RIGHT_CLICK_AIR == action || Action.RIGHT_CLICK_BLOCK == action) && e.getPlayer().isSneaking()) {
            if (!SItem.isAbleToDoEtherWarpTeleportation(player, sItem)) {
                final Ability ability = sItem.getType().getAbility();
                if (null != ability) {
                    final AbilityActivation activation = ability.getAbilityActivation();
                    if (AbilityActivation.LEFT_CLICK == activation || AbilityActivation.RIGHT_CLICK == activation) {
                        if (AbilityActivation.LEFT_CLICK == activation) {
                            if (Action.LEFT_CLICK_AIR != action && Action.LEFT_CLICK_BLOCK != action) {
                                return;
                            }
                        }
                        else if (Action.RIGHT_CLICK_AIR != action && Action.RIGHT_CLICK_BLOCK != action) {
                            return;
                        }
                        PlayerUtils.useAbility(player, sItem);
                    }
                }
                return;
            }
            final int mana = (int)Repeater.MANA_MAP.get((Object)uuid);
            final int cost = PlayerUtils.getFinalManaCost(player, sItem, 250);
            final int resMana = mana - cost;
            if (0 <= resMana) {
                Repeater.MANA_MAP.remove((Object)uuid);
                Repeater.MANA_MAP.put((Object)uuid, (Object)resMana);
                final long c = System.currentTimeMillis();
                Repeater.DEFENSE_REPLACEMENT_MAP.put((Object)player.getUniqueId(), (Object)new DefenseReplacement() {
                    @Override
                    public String getReplacement() {
                        return (Object)ChatColor.AQUA + "-" + cost + " Mana (" + (Object)ChatColor.GOLD + "Ether Transmission" + (Object)ChatColor.AQUA + ")";
                    }
                    
                    @Override
                    public long getEnd() {
                        return c + 2000L;
                    }
                });
                SItem.etherWarpTeleportation(e.getPlayer(), sItem);
            }
            else {
                player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1.0f, -4.0f);
                final long c = System.currentTimeMillis();
                Repeater.MANA_REPLACEMENT_MAP.put((Object)player.getUniqueId(), (Object)new ManaReplacement() {
                    @Override
                    public String getReplacement() {
                        return "" + (Object)ChatColor.RED + (Object)ChatColor.BOLD + "NOT ENOUGH MANA";
                    }
                    
                    @Override
                    public long getEnd() {
                        return c + 1500L;
                    }
                });
            }
        }
    }
    
    @EventHandler
    public void onPlayerInteracting(final PlayerInteractEvent e) {
    }
    
    @EventHandler
    public void PotionsSplash(final PotionSplashEvent e) {
        for (final Entity ef : e.getAffectedEntities()) {
            if (ef.hasMetadata("LD")) {
                for (final PotionEffect pe : e.getEntity().getEffects()) {
                    if (pe.getType() == PotionEffectType.HEAL) {
                        e.setCancelled(true);
                    }
                    else {
                        if (pe.getType() != PotionEffectType.HARM) {
                            continue;
                        }
                        e.setCancelled(true);
                        ((LivingEntity)ef).damage(1.0E-4);
                    }
                }
            }
        }
        if (e.getEntity().getShooter() instanceof LivingEntity && ((LivingEntity)e.getEntity().getShooter()).hasMetadata("LD")) {
            for (final Entity ef : e.getAffectedEntities()) {
                if (ef.hasMetadata("LD")) {
                    e.setCancelled(true);
                    if (ef.isDead()) {
                        return;
                    }
                    ((LivingEntity)ef).setHealth(Math.min(((LivingEntity)ef).getMaxHealth(), ((LivingEntity)ef).getHealth() + ((LivingEntity)ef).getMaxHealth() * 10.0 / 100.0));
                }
                else {
                    if (!(ef instanceof Player)) {
                        continue;
                    }
                    e.setCancelled(true);
                    ((LivingEntity)ef).setHealth(Math.min(((LivingEntity)ef).getMaxHealth(), ((LivingEntity)ef).getHealth() + 500.0));
                    ef.sendMessage(Sputnik.trans("&a&lBUFF! &fYou were splashed with &cHealing V&f!"));
                }
            }
        }
    }
    
    @EventHandler
    public void onPlayerInteract(final PlayerInteractEvent e) {
        if (Action.RIGHT_CLICK_AIR != e.getAction()) {
            for (final Player p : e.getPlayer().getWorld().getPlayers()) {
                if (p == e.getPlayer()) {
                    continue;
                }
                ((CraftPlayer)p).getHandle().playerConnection.sendPacket((Packet)new PacketPlayOutAnimation((net.minecraft.server.v1_8_R3.Entity)((CraftLivingEntity)e.getPlayer()).getHandle(), 0));
            }
        }
        if (!SItem.isSpecItem(e.getItem())) {
            return;
        }
        final SItem sItem = SItem.find(e.getItem());
        if (null == sItem) {
            return;
        }
        if ((Material.MONSTER_EGG == sItem.getStack().getType() || Material.MONSTER_EGGS == sItem.getStack().getType()) && !e.getPlayer().isOp()) {
            e.setCancelled(true);
        }
        updateStatistics(e.getPlayer());
        final Action action = e.getAction();
        if (SpecificItemType.HELMET == sItem.getType().getStatistics().getSpecificType() && Action.RIGHT_CLICK_AIR == action && isAir(e.getPlayer().getInventory().getHelmet())) {
            e.getPlayer().getInventory().setHelmet(sItem.getStack());
            e.getPlayer().setItemInHand((ItemStack)null);
        }
        final Player player = e.getPlayer();
        final Ability ability = sItem.getType().getAbility();
        Label_0374: {
            if (null != ability) {
                final AbilityActivation activation = ability.getAbilityActivation();
                if (AbilityActivation.LEFT_CLICK == activation || AbilityActivation.RIGHT_CLICK == activation) {
                    if (AbilityActivation.LEFT_CLICK == activation) {
                        if (Action.LEFT_CLICK_AIR != action && Action.LEFT_CLICK_BLOCK != action) {
                            break Label_0374;
                        }
                    }
                    else if (Action.RIGHT_CLICK_AIR != action && Action.RIGHT_CLICK_BLOCK != action) {
                        break Label_0374;
                    }
                    if (sItem.getDataString("etherwarp_trans").equals((Object)"true")) {
                        if (!player.isSneaking()) {
                            PlayerUtils.useAbility(player, sItem);
                        }
                    }
                    else {
                        PlayerUtils.useAbility(player, sItem);
                    }
                }
            }
        }
        final MaterialFunction function = sItem.getType().getFunction();
        if (null != function) {
            function.onInteraction(e);
        }
    }
    
    @EventHandler
    public void onPlayerMage(final PlayerInteractEvent e) {
        final Player player = e.getPlayer();
        final Action action = e.getAction();
        if (!player.getWorld().getName().equals((Object)"dungeon")) {
            return;
        }
        if (!ItemListener.Classes.containsKey((Object)player)) {
            ItemListener.Classes.put((Object)player, (Object)"M");
        }
        if ("M" == ItemListener.Classes.get((Object)player) && Action.LEFT_CLICK_AIR == action) {
            String ACT = "true";
            final ItemStack item = player.getInventory().getItemInHand();
            final SItem sitem = SItem.find(item);
            if (null == sitem) {
                return;
            }
            final SMaterial material = sitem.getType();
            final MaterialStatistics statistics = material.getStatistics();
            final SpecificItemType type = statistics.getSpecificType();
            if (type.getName().contains((CharSequence)"SWORD")) {
                final Location blockLocation = player.getTargetBlock((Set)null, 30).getLocation();
                final Location crystalLocation = player.getEyeLocation();
                final Vector vector = blockLocation.clone().add(0.1, 0.0, 0.1).toVector().subtract(crystalLocation.clone().toVector());
                final double count = 25.0;
                for (int i = 1; 25 >= i; ++i) {
                    for (final Entity entity : player.getWorld().getNearbyEntities(crystalLocation.clone().add(vector.clone().multiply(i / 25.0)), 0.5, 0.0, 0.5)) {
                        if ("false" == ACT) {
                            return;
                        }
                        if (entity.isDead()) {
                            continue;
                        }
                        if (!(entity instanceof LivingEntity)) {
                            continue;
                        }
                        if (entity.hasMetadata("GiantSword")) {
                            continue;
                        }
                        if (entity instanceof Player || entity instanceof EnderDragonPart) {
                            continue;
                        }
                        if (entity instanceof Villager) {
                            continue;
                        }
                        if (entity instanceof ArmorStand) {
                            continue;
                        }
                        final User user = User.getUser(player.getUniqueId());
                        final int damage = 0;
                        double enchantBonus = 0.0;
                        final double potionBonus = 0.0;
                        final PlayerStatistics statistics2 = (PlayerStatistics)PlayerUtils.STATISTICS_CACHE.get((Object)player.getUniqueId());
                        double critDamage = statistics2.getCritDamage().addAll();
                        final double speed = statistics2.getSpeed().addAll();
                        final double realSpeed = speed * 100.0 % 25.0;
                        final double realSpeedDIV = speed - realSpeed;
                        final double realSpeedDIVC = realSpeedDIV / 25.0;
                        final PlayerInventory inv = player.getInventory();
                        final SItem helmet = SItem.find(inv.getHelmet());
                        if (null != helmet && SMaterial.WARDEN_HELMET == helmet.getType()) {
                            enchantBonus += (100.0 + 20.0 * realSpeedDIVC) / 100.0;
                        }
                        for (final Enchantment enchantment : sitem.getEnchantments()) {
                            final EnchantmentType type2 = enchantment.getType();
                            final int level = enchantment.getLevel();
                            if (type2 == EnchantmentType.SHARPNESS) {
                                enchantBonus += level * 6 / 100.0;
                            }
                            if (type2 == EnchantmentType.SMITE && Groups.UNDEAD_MOBS.contains((Object)entity.getType())) {
                                enchantBonus += level * 8 / 100.0;
                            }
                            if (type2 == EnchantmentType.ENDER_SLAYER && Groups.ENDERMAN.contains((Object)entity.getType())) {
                                enchantBonus += level * 12 / 100.0;
                            }
                            if (type2 == EnchantmentType.BANE_OF_ARTHROPODS && Groups.ARTHROPODS.contains((Object)entity.getType())) {
                                enchantBonus += level * 8 / 100.0;
                            }
                            if (type2 == EnchantmentType.DRAGON_HUNTER && Groups.ENDERDRAGON.contains((Object)entity.getType())) {
                                enchantBonus += level * 8 / 100.0;
                            }
                            if (type2 == EnchantmentType.CRITICAL) {
                                critDamage += level * 10 / 100.0;
                            }
                        }
                        final PlayerBoostStatistics playerBoostStatistics = (PlayerBoostStatistics)material.getStatistics();
                        final double baseDamage = (5 + playerBoostStatistics.getBaseDamage() + statistics2.getStrength().addAll() / 5.0) * (1.0 + statistics2.getStrength().addAll() / 100.0);
                        final int combatLevel = Skill.getLevel(User.getUser(player.getUniqueId()).getCombatXP(), false);
                        final double weaponBonus = 0.0;
                        final double armorBonus = 1.0;
                        final int critChanceMul = (int)(statistics2.getCritChance().addAll() * 100.0);
                        final int chance = SUtil.random(0, 100);
                        if (chance > critChanceMul) {
                            critDamage = 0.0;
                        }
                        final double damageMultiplier = 1.0 + combatLevel * 0.04 + enchantBonus + 0.0;
                        final double finalCritDamage = critDamage;
                        double finalDamage = baseDamage * damageMultiplier * 1.0 * (1.0 + finalCritDamage);
                        final double finalPotionBonus = 0.0;
                        if (entity.isDead()) {
                            continue;
                        }
                        if (!(entity instanceof LivingEntity)) {
                            continue;
                        }
                        if (entity instanceof Player || entity instanceof EnderDragonPart || entity instanceof Villager || entity instanceof ArmorStand) {
                            continue;
                        }
                        if (entity instanceof Item) {
                            continue;
                        }
                        if (entity instanceof ItemFrame) {
                            continue;
                        }
                        if (EdibleMace.edibleMace.containsKey((Object)player.getUniqueId()) && (boolean)EdibleMace.edibleMace.get((Object)player.getUniqueId())) {
                            finalDamage *= 2.0;
                            EdibleMace.edibleMace.put((Object)player.getUniqueId(), (Object)false);
                        }
                        final ArmorStand stand3 = (ArmorStand)new SEntity(entity.getLocation().clone().add(SUtil.random(-1.5, 1.5), 1.0, SUtil.random(-1.5, 1.5)), SEntityType.UNCOLLIDABLE_ARMOR_STAND, new Object[0]).getEntity();
                        if (0.0 == finalCritDamage) {
                            stand3.setCustomName("" + (Object)ChatColor.GRAY + (int)finalDamage);
                        }
                        else {
                            stand3.setCustomName(SUtil.rainbowize("\u2727" + (int)finalDamage + "\u2727"));
                        }
                        stand3.setCustomNameVisible(true);
                        stand3.setGravity(false);
                        stand3.setVisible(false);
                        user.damageEntity((Damageable)entity, finalDamage);
                        new BukkitRunnable() {
                            public void run() {
                                stand3.remove();
                                this.cancel();
                            }
                        }.runTaskLater((Plugin)SkyBlock.getPlugin(), 30L);
                        ACT = "false";
                    }
                    player.getWorld().spigot().playEffect(crystalLocation.clone().add(vector.clone().multiply(i / 25.0)), Effect.FIREWORKS_SPARK, 24, 1, 0.0f, 0.0f, 0.0f, 1.0f, 0, 64);
                }
            }
        }
    }
    
    @EventHandler
    public void onInventoryClose(final InventoryCloseEvent e) {
        if (!(e.getPlayer() instanceof Player)) {
            return;
        }
        final Player player = (Player)e.getPlayer();
        final Inventory storage = Storage.getCurrentStorageOpened(player);
        if (null == storage) {
            return;
        }
        final Inventory inventory = e.getInventory();
        final SItem hand = SItem.find(player.getItemInHand());
        if (null == hand) {
            return;
        }
        final NBTTagCompound storageData = new NBTTagCompound();
        for (int i = 0; i < inventory.getSize(); ++i) {
            final SItem sItem = SItem.find(inventory.getItem(i));
            if (null == sItem) {
                final SItem equiv = SItem.of(inventory.getItem(i));
                if (null != equiv) {
                    storageData.setByteArray(String.valueOf(i), SUtil.gzipCompress(equiv.toCompound().toString().getBytes()));
                }
                else {
                    storageData.remove(String.valueOf(i));
                }
            }
            else {
                storageData.setByteArray(String.valueOf(i), SUtil.gzipCompress(sItem.toCompound().toString().getBytes()));
            }
        }
        hand.getData().set("storage_data", (NBTBase)storageData);
        hand.update();
        Storage.closeCurrentStorage(player);
    }
    
    @EventHandler
    public void onPlayerFlight(final PlayerToggleFlightEvent e) {
        final Player player = e.getPlayer();
        final GameMode gameMode = player.getGameMode();
        if (GameMode.CREATIVE == gameMode || GameMode.SPECTATOR == gameMode) {
            return;
        }
        for (final ItemStack stack : player.getInventory().getArmorContents()) {
            final SItem sItem = SItem.find(stack);
            if (null != sItem) {
                final Ability ability = sItem.getType().getAbility();
                if (null != ability && e.isFlying() && AbilityActivation.FLIGHT == ability.getAbilityActivation()) {
                    e.setCancelled(true);
                    PlayerUtils.useAbility(player, sItem);
                }
            }
        }
    }
    
    @EventHandler
    public void onPlayerSneak(final PlayerToggleSneakEvent e) {
        final Player player = e.getPlayer();
        final GameMode gameMode = player.getGameMode();
        for (final ItemStack stack : player.getInventory().getArmorContents()) {
            final SItem sItem = SItem.find(stack);
            if (null != sItem) {
                final Ability ability = sItem.getType().getAbility();
                if (null != ability && player.isSneaking() && AbilityActivation.SNEAK == ability.getAbilityActivation()) {
                    PlayerUtils.useAbility(player, sItem);
                }
            }
        }
    }
    
    @EventHandler
    public void onInventoryClick(final InventoryClickEvent e) {
        if (InventoryType.CRAFTING != e.getView().getTopInventory().getType()) {
            return;
        }
        if (InventoryType.SlotType.CONTAINER != e.getSlotType() && InventoryType.SlotType.QUICKBAR != e.getSlotType()) {
            return;
        }
        if (InventoryAction.MOVE_TO_OTHER_INVENTORY != e.getAction()) {
            return;
        }
        final Inventory inventory = e.getClickedInventory();
        if (null == inventory) {
            return;
        }
        if (InventoryType.PLAYER != inventory.getType()) {
            return;
        }
        final ItemStack current = e.getCurrentItem();
        if (null == current) {
            return;
        }
        SItem sItem = SItem.find(current);
        if (null == sItem) {
            sItem = SItem.of(current);
        }
        updateStatistics((Player)e.getWhoClicked());
        if (null == sItem.getType().getStatistics().getSpecificType() || SpecificItemType.HELMET != sItem.getType().getStatistics().getSpecificType()) {
            return;
        }
        final PlayerInventory playerInventory = (PlayerInventory)inventory;
        if (!isAir(playerInventory.getHelmet())) {
            return;
        }
        e.setCancelled(true);
        e.setCurrentItem(new ItemStack(Material.AIR));
        playerInventory.setHelmet(current);
    }
    
    @EventHandler
    public void onArmorChange(final InventoryClickEvent e) {
        final Player player = (Player)e.getWhoClicked();
        if (null == e.getClickedInventory()) {
            return;
        }
        if (InventoryType.PLAYER != e.getClickedInventory().getType() && InventoryType.CRAFTING != e.getClickedInventory().getType()) {
            return;
        }
        updateStatistics((Player)e.getWhoClicked());
        player.getInventory().setHelmet(player.getInventory().getHelmet());
        player.getInventory().setChestplate(player.getInventory().getChestplate());
        player.getInventory().setLeggings(player.getInventory().getLeggings());
        player.getInventory().setBoots(player.getInventory().getBoots());
    }
    
    @EventHandler
    public void onArmorChange1(final InventoryCloseEvent e) {
        final Player player = (Player)e.getPlayer();
        player.getInventory().setHelmet(player.getInventory().getHelmet());
        player.getInventory().setChestplate(player.getInventory().getChestplate());
        player.getInventory().setLeggings(player.getInventory().getLeggings());
        player.getInventory().setBoots(player.getInventory().getBoots());
        updateStatistics(player);
    }
    
    @EventHandler
    public void onArmorChange2(final PlayerJoinEvent e) {
        final Player player = e.getPlayer();
        final PlayerStatistics statistics = (PlayerStatistics)PlayerUtils.STATISTICS_CACHE.get((Object)player.getUniqueId());
        final ItemStack helmet = player.getInventory().getHelmet();
        SUtil.delay(() -> player.getInventory().setHelmet(helmet), 10L);
        final SItem helmet2 = SItem.find(player.getInventory().getHelmet());
        if (null == helmet2) {
            return;
        }
        final TickingMaterial tickingMaterial = helmet2.getType().getTickingInstance();
        if (null != tickingMaterial) {
            statistics.tickItem(39, tickingMaterial.getInterval(), () -> tickingMaterial.tick(helmet2, Bukkit.getPlayer(statistics.getUuid())));
        }
        updateStatistics(player);
    }
    
    @EventHandler(priority = EventPriority.HIGH)
    public void onItemClick(final InventoryClickEvent e) {
        final ItemStack stack = e.getCurrentItem();
        if (null == stack) {
            return;
        }
        final SItem sItem = SItem.find(stack);
        if (null == sItem) {
            return;
        }
        if (null == sItem.getType().getFunction()) {
            return;
        }
        sItem.getType().getFunction().onInventoryClick(sItem, e);
    }
    
    @EventHandler
    public void onItemMove(final InventoryClickEvent e) {
        if (null == e.getClickedInventory()) {
            return;
        }
        if (InventoryType.PLAYER != e.getClickedInventory().getType()) {
            return;
        }
        if (8 != e.getSlot()) {
            return;
        }
        e.setCancelled(true);
    }
    
    @EventHandler
    public void onBlockPlace(final BlockPlaceEvent e) {
        final SItem sItem = SItem.find(e.getItemInHand());
        if (null == sItem) {
            return;
        }
        if (SpecificItemType.HELMET == sItem.getType().getStatistics().getSpecificType() && isAir(e.getPlayer().getInventory().getHelmet())) {
            e.setCancelled(true);
            e.getPlayer().getInventory().setHelmet(sItem.getStack());
            e.getPlayer().setItemInHand((ItemStack)null);
            return;
        }
        if (!sItem.getType().isCraft()) {
            if (GenericItemType.BLOCK != sItem.getType().getStatistics().getType()) {
                e.setCancelled(true);
            }
            else {
                new SBlock(e.getBlockPlaced().getLocation(), sItem.getType(), sItem.getData()).save();
            }
        }
    }
    
    @EventHandler
    public void onFrameInteract(final PlayerInteractEvent e) {
        if (Action.RIGHT_CLICK_BLOCK != e.getAction()) {
            return;
        }
        final Player player = e.getPlayer();
        final Block block = e.getClickedBlock();
        final ItemStack hand = e.getItem();
        if (null == hand) {
            return;
        }
        final SItem item = SItem.find(hand);
        if (null == item) {
            return;
        }
        if (Material.ENDER_PORTAL_FRAME != block.getType()) {
            return;
        }
        final SBlock sBlock = SBlock.getBlock(block.getLocation());
        if (null == sBlock) {
            e.setCancelled(true);
            return;
        }
        if (SMaterial.SUMMONING_FRAME != sBlock.getType()) {
            e.setCancelled(true);
            return;
        }
        if (!block.hasMetadata("placer")) {
            if (SMaterial.SUMMONING_EYE != item.getType()) {
                return;
            }
            block.setMetadata("placer", (MetadataValue)new FixedMetadataValue((Plugin)this.plugin, (Object)player.getUniqueId()));
            final BlockState state = block.getState();
            state.setRawData((byte)4);
            state.update();
            player.getInventory().setItemInHand(SItem.of(SMaterial.SLEEPING_EYE).getStack());
            final List<Location> locations = (List<Location>)(StaticDragonManager.EYES.containsKey((Object)player.getUniqueId()) ? StaticDragonManager.EYES.get((Object)player.getUniqueId()) : new ArrayList());
            locations.add((Object)block.getLocation());
            StaticDragonManager.EYES.remove((Object)player.getUniqueId());
            StaticDragonManager.EYES.put((Object)player.getUniqueId(), (Object)locations);
            int quantity = 0;
            for (final List<Location> ls : StaticDragonManager.EYES.values()) {
                quantity += ls.size();
            }
            for (final Player p : Bukkit.getOnlinePlayers()) {
                if (p.getWorld().getName().equals((Object)"world")) {
                    p.sendMessage((Object)ChatColor.DARK_PURPLE + "\u262c " + (Object)ChatColor.GREEN + player.getName() + (Object)ChatColor.LIGHT_PURPLE + " placed a Summoning Eye! " + ((8 == quantity) ? "Brace yourselves! " : "") + (Object)ChatColor.GRAY + "(" + (Object)((8 == quantity) ? ChatColor.GREEN : ChatColor.YELLOW) + quantity + (Object)ChatColor.GRAY + "/" + (Object)ChatColor.GREEN + "8" + (Object)ChatColor.GRAY + ")");
                }
            }
            if (8 != quantity) {
                return;
            }
            final List<UUID> cleared = (List<UUID>)new ArrayList();
            for (final List<Location> ls2 : StaticDragonManager.EYES.values()) {
                for (final Location location : ls2) {
                    final Block b = location.getBlock();
                    final List<MetadataValue> values = (List<MetadataValue>)b.getMetadata("placer");
                    final Player p2 = Bukkit.getPlayer((UUID)((MetadataValue)values.get(0)).value());
                    if (null == p2) {
                        continue;
                    }
                    if (cleared.contains((Object)p2.getUniqueId())) {
                        continue;
                    }
                    final PlayerInventory inventory = p2.getInventory();
                    for (int i = 0; i < inventory.getSize(); ++i) {
                        final SItem si = SItem.find(inventory.getItem(i));
                        if (null != si && SMaterial.SLEEPING_EYE == si.getType()) {
                            inventory.setItem(i, SItem.of(SMaterial.REMNANT_OF_THE_EYE).getStack());
                        }
                    }
                    p2.sendMessage((Object)ChatColor.DARK_PURPLE + "Your Sleeping Eyes have been awoken by the magic of the Dragon. They are now Remnants of the Eye!");
                    cleared.add((Object)p2.getUniqueId());
                }
            }
            StaticDragonManager.ACTIVE = true;
            block.getWorld().playSound(block.getLocation(), Sound.ENDERMAN_STARE, 50.0f, -2.0f);
            new BukkitRunnable() {
                public void run() {
                    block.getWorld().playSound(block.getLocation(), Sound.ENDERDRAGON_DEATH, 50.0f, -2.0f);
                }
            }.runTaskLater((Plugin)this.plugin, 90L);
            new BukkitRunnable() {
                public void run() {
                    for (int i = 0; 3 > i; ++i) {
                        block.getWorld().playSound(block.getLocation(), Sound.EXPLODE, 50.0f, -2.0f);
                    }
                    SEntityType dragonType = SEntityType.PROTECTOR_DRAGON;
                    final int chance = SUtil.random(0, 100);
                    if (16 <= chance) {
                        dragonType = SEntityType.OLD_DRAGON;
                    }
                    if (32 <= chance) {
                        dragonType = SEntityType.WISE_DRAGON;
                    }
                    if (48 <= chance) {
                        dragonType = SEntityType.UNSTABLE_DRAGON;
                    }
                    if (64 <= chance) {
                        dragonType = SEntityType.YOUNG_DRAGON;
                    }
                    if (80 <= chance) {
                        dragonType = SEntityType.STRONG_DRAGON;
                    }
                    if (96 <= chance) {
                        dragonType = SEntityType.SUPERIOR_DRAGON;
                    }
                    final SEntity entity = new SEntity(block.getLocation().clone().add(0.0, 53.0, 0.0), dragonType, new Object[0]);
                    block.getWorld().spawnEntity(block.getWorld().getBlockAt(-642, 36, -246).getLocation().add(-0.5, 0.7, -0.5), EntityType.ENDER_CRYSTAL);
                    block.getWorld().spawnEntity(block.getWorld().getBlockAt(-635, 51, -233).getLocation().add(-0.5, 0.7, -0.5), EntityType.ENDER_CRYSTAL);
                    block.getWorld().spawnEntity(block.getWorld().getBlockAt(-717, 39, -255).getLocation().add(-0.5, 0.7, -0.5), EntityType.ENDER_CRYSTAL);
                    block.getWorld().spawnEntity(block.getWorld().getBlockAt(-715, 44, -299).getLocation().add(-0.5, 0.7, -0.5), EntityType.ENDER_CRYSTAL);
                    block.getWorld().spawnEntity(block.getWorld().getBlockAt(-682, 58, -323).getLocation().add(-0.5, 0.7, -0.5), EntityType.ENDER_CRYSTAL);
                    block.getWorld().spawnEntity(block.getWorld().getBlockAt(-638, 51, -318).getLocation().add(-0.5, 0.7, -0.5), EntityType.ENDER_CRYSTAL);
                    block.getWorld().spawnEntity(block.getWorld().getBlockAt(-623, 58, -293).getLocation().add(-0.5, 0.7, -0.5), EntityType.ENDER_CRYSTAL);
                    block.getWorld().spawnEntity(block.getWorld().getBlockAt(-678, 31, -287).getLocation().add(-0.5, 0.7, -0.5), EntityType.ENDER_CRYSTAL);
                    block.getWorld().spawnEntity(block.getWorld().getBlockAt(-697, 35, -249).getLocation().add(-0.5, 0.7, -0.5), EntityType.ENDER_CRYSTAL);
                    block.getWorld().spawnEntity(block.getWorld().getBlockAt(-638, 40, -309).getLocation().add(-0.5, 0.7, -0.5), EntityType.ENDER_CRYSTAL);
                    for (final Player p : Bukkit.getOnlinePlayers()) {
                        if (!p.getWorld().getName().equals((Object)"dragon")) {
                            continue;
                        }
                        final Vector vector = p.getLocation().clone().subtract(new Vector(-670.5, 58.0, -275.5)).toVector();
                        p.setVelocity(vector.normalize().multiply(40.0).setY(100.0));
                    }
                    StaticDragonManager.DRAGON = entity;
                    block.getWorld().playSound(block.getLocation(), Sound.ENDERDRAGON_GROWL, 50.0f, 1.0f);
                    for (final Player p : Bukkit.getOnlinePlayers()) {
                        p.sendMessage((Object)ChatColor.DARK_PURPLE + "\u262c " + (Object)ChatColor.LIGHT_PURPLE + (Object)ChatColor.BOLD + "The " + (Object)ChatColor.RED + (Object)ChatColor.BOLD + entity.getStatistics().getEntityName() + (Object)ChatColor.LIGHT_PURPLE + (Object)ChatColor.BOLD + " has spawned!");
                    }
                }
            }.runTaskLater((Plugin)this.plugin, 180L);
        }
        else {
            final List<MetadataValue> values2 = (List<MetadataValue>)block.getMetadata("placer");
            final Player p3 = Bukkit.getPlayer((UUID)((MetadataValue)values2.get(0)).value());
            if (null == p3) {
                return;
            }
            if (SMaterial.SLEEPING_EYE != item.getType()) {
                return;
            }
            if (!p3.getUniqueId().equals((Object)player.getUniqueId())) {
                player.sendMessage((Object)ChatColor.RED + "You can only recover Summoning Eyes that you placed!");
                return;
            }
            if (StaticDragonManager.ACTIVE) {
                player.sendMessage((Object)ChatColor.RED + "You cannot recover Summoning Eyes after the dragon has been summoned!");
                return;
            }
            block.removeMetadata("placer", (Plugin)this.plugin);
            final BlockState state2 = block.getState();
            state2.setRawData((byte)0);
            state2.update();
            player.getInventory().setItemInHand(SItem.of(SMaterial.SUMMONING_EYE).getStack());
            ((List)StaticDragonManager.EYES.get((Object)p3.getUniqueId())).remove((Object)block.getLocation());
            player.sendMessage((Object)ChatColor.DARK_PURPLE + "You recovered a Summoning Eye!");
        }
    }
    
    @EventHandler
    public void onItemPickup(final PlayerPickupItemEvent e) {
        final Item item = e.getItem();
        final Player player = e.getPlayer();
        updateStatistics(player);
        NBTTagCompound compound = CraftItemStack.asNMSCopy(item.getItemStack()).getTag();
        if (null == compound) {
            compound = new NBTTagCompound();
        }
        if (!compound.hasKey("type")) {
            item.getItemStack().setItemMeta(SItem.of(item.getItemStack()).getStack().getItemMeta());
        }
        if (item.hasMetadata("owner")) {
            final List<MetadataValue> o = (List<MetadataValue>)item.getMetadata("owner");
            if (0 != o.size() && !((MetadataValue)o.get(0)).asString().equals((Object)e.getPlayer().getUniqueId().toString())) {
                e.setCancelled(true);
                return;
            }
        }
        final User user = User.getUser(player.getUniqueId());
        final ItemStack stack = item.getItemStack();
        final SItem sItem = SItem.find(stack);
        if (null == sItem) {
            throw new NullPointerException("Something messed up! Check again");
        }
        if (item.hasMetadata("obtained")) {
            for (final Player p : Bukkit.getOnlinePlayers()) {
                if (p.getWorld().getName().equals((Object)"world")) {
                    if ((!sItem.getFullName().equals((Object)"§6Ender Dragon") && !sItem.getFullName().equals((Object)"§5Ender Dragon")) || !sItem.getFullName().equals((Object)"§6Voidling Destroyer")) {
                        p.sendMessage((Object)ChatColor.GREEN + player.getName() + (Object)ChatColor.YELLOW + " has obtained " + sItem.getFullName() + (Object)ChatColor.YELLOW + "!");
                    }
                    else {
                        p.sendMessage((Object)ChatColor.GREEN + player.getName() + (Object)ChatColor.YELLOW + " has obtained " + (Object)ChatColor.GRAY + "[Lvl 1] " + sItem.getFullName() + (Object)ChatColor.YELLOW + "!");
                    }
                }
            }
        }
        if (ItemOrigin.NATURAL_BLOCK == sItem.getOrigin() || ItemOrigin.MOB == sItem.getOrigin()) {
            sItem.setOrigin(ItemOrigin.UNKNOWN);
            final ItemCollection collection = ItemCollection.getByMaterial(sItem.getType(), stack.getDurability());
            if (null != collection) {
                final int prev = user.getCollection(collection);
                user.addToCollection(collection, stack.getAmount());
                if (user != null) {
                    if (SkyBlock.getPlugin().config.getBoolean("Config")) {
                        user.configsave();
                    }
                    else {
                        user.save();
                    }
                }
                if (0 == prev) {
                    player.sendMessage((Object)ChatColor.GOLD + "" + (Object)ChatColor.BOLD + "  COLLECTION UNLOCKED " + (Object)ChatColor.RESET + (Object)ChatColor.YELLOW + collection.getName());
                    player.playSound(player.getLocation(), Sound.LEVEL_UP, 1.0f, 2.0f);
                }
            }
        }
    }
    
    @EventHandler
    public void onItemDrop(final PlayerDropItemEvent e) {
        final SItem sItem = SItem.find(e.getItemDrop().getItemStack());
        if (null != sItem && (SMaterial.SKYBLOCK_MENU == sItem.getType() || SMaterial.QUIVER_ARROW == sItem.getType())) {
            e.setCancelled(true);
        }
        updateStatistics(e.getPlayer());
    }
    
    @EventHandler
    public void onItemMove(final PlayerDropItemEvent e) {
        final SItem sItem = SItem.find(e.getItemDrop().getItemStack());
        if (null != sItem && (SMaterial.SKYBLOCK_MENU == sItem.getType() || SMaterial.QUIVER_ARROW == sItem.getType())) {
            e.setCancelled(true);
        }
        updateStatistics(e.getPlayer());
    }
    
    @EventHandler
    public void onItemDrop1(final PlayerDropItemEvent e) {
        final SItem sItem = SItem.find(e.getItemDrop().getItemStack());
        if (null != sItem && SMaterial.BONEMERANG == sItem.getType() && e.getItemDrop().getItemStack().toString().contains((CharSequence)"GHAST_TEAR")) {
            e.setCancelled(true);
        }
    }
    
    @EventHandler
    public void onItemMove1(final PlayerDropItemEvent e) {
        final SItem sItem = SItem.find(e.getItemDrop().getItemStack());
        if (null != sItem && (SMaterial.SKYBLOCK_MENU == sItem.getType() || SMaterial.QUIVER_ARROW == sItem.getType())) {
            e.setCancelled(true);
        }
    }
    
    @EventHandler
    public void onFishingRodReel(final PlayerFishEvent e) {
        final SItem rod = SItem.find(e.getPlayer().getItemInHand());
        if (null == rod) {
            return;
        }
        e.getHook().setMetadata("owner", (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)e.getPlayer()));
        final MaterialFunction function = rod.getType().getFunction();
        if (null == function) {
            return;
        }
        if (function instanceof FishingRodFunction) {
            ((FishingRodFunction)function).onFish(rod, e);
        }
    }
    
    @EventHandler
    public void onPotionSplash(final PotionSplashEvent e) {
        final SItem item = SItem.find(e.getPotion().getItem());
        if (null == item) {
            return;
        }
        if (!item.isPotion()) {
            return;
        }
        e.setCancelled(true);
        for (final LivingEntity entity : e.getAffectedEntities()) {
            if (!(entity instanceof Player)) {
                continue;
            }
            final User user = User.getUser(entity.getUniqueId());
            if (null == user) {
                continue;
            }
            for (final net.hypixel.skyblock.features.potion.PotionEffect effect : item.getPotionEffects()) {
                PlayerUtils.updatePotionEffects(user, (PlayerStatistics)PlayerUtils.STATISTICS_CACHE.get((Object)user.getUuid()));
                if (null != effect.getType().getOnDrink()) {
                    effect.getType().getOnDrink().accept((Object)effect, (Object)entity);
                }
                final long ticks = (long)(effect.getDuration() * e.getIntensity(entity));
                if (!user.hasPotionEffect(effect.getType()) || (user.hasPotionEffect(effect.getType()) && ticks > user.getPotionEffect(effect.getType()).getRemaining())) {
                    user.removePotionEffect(effect.getType());
                    user.addPotionEffect(new net.hypixel.skyblock.features.potion.PotionEffect(effect.getType(), effect.getLevel(), ticks));
                }
                entity.sendMessage((effect.getType().isBuff() ? ((Object)ChatColor.GREEN + "" + (Object)ChatColor.BOLD + "BUFF!") : ((Object)ChatColor.RED + "" + (Object)ChatColor.BOLD + "DEBUFF!")) + (Object)ChatColor.RESET + (Object)ChatColor.WHITE + " You " + (e.getPotion().getShooter().equals(entity) ? "splashed yourself" : "were splashed") + " with " + effect.getDisplayName() + (Object)ChatColor.WHITE + "!");
            }
        }
    }
    
    public static void updateStatistics(final Player player) {
        final PlayerInventory inv = player.getInventory();
        final ItemStack beforeHelmet = inv.getHelmet();
        final ItemStack beforeChestplate = inv.getChestplate();
        final ItemStack beforeLeggings = inv.getLeggings();
        final ItemStack beforeBoots = inv.getBoots();
        new BukkitRunnable() {
            public void run() {
                final PlayerStatistics statistics = (PlayerStatistics)PlayerUtils.STATISTICS_CACHE.get((Object)player.getUniqueId());
                final ItemStack afterHelmet = inv.getHelmet();
                final ItemStack afterChestplate = inv.getChestplate();
                final ItemStack afterLeggings = inv.getLeggings();
                final ItemStack afterBoots = inv.getBoots();
                final boolean helmetSimilar = similar(beforeHelmet, afterHelmet);
                final boolean chestplateSimilar = similar(beforeChestplate, afterChestplate);
                final boolean leggingsSimilar = similar(beforeLeggings, afterLeggings);
                final boolean bootsSimilar = similar(beforeBoots, afterBoots);
                SItem helmet = null;
                SItem chestplate = null;
                SItem leggings = null;
                SItem boots = null;
                if (!helmetSimilar) {
                    PlayerUtils.updateArmorStatistics(helmet = SItem.find(afterHelmet), statistics, 0);
                }
                if (!chestplateSimilar) {
                    PlayerUtils.updateArmorStatistics(chestplate = SItem.find(afterChestplate), statistics, 1);
                }
                if (!leggingsSimilar) {
                    PlayerUtils.updateArmorStatistics(leggings = SItem.find(afterLeggings), statistics, 2);
                }
                if (!bootsSimilar) {
                    PlayerUtils.updateArmorStatistics(boots = SItem.find(afterBoots), statistics, 3);
                }
                PlayerUtils.updateInventoryStatistics(player, statistics);
                ItemListener.checkCondition(player);
            }
        }.runTaskLater((Plugin)SkyBlock.getPlugin(), 1L);
    }
    
    public static void checkCondition(final Player p) {
        final SItem helm = SItem.find(p.getInventory().getHelmet());
        final SItem chest = SItem.find(p.getInventory().getChestplate());
        final SItem leg = SItem.find(p.getInventory().getLeggings());
        final SItem boots = SItem.find(p.getInventory().getBoots());
        if (null != helm && null != chest && null != leg && null != boots) {
            if (Groups.WITHER_HELMETS.contains((Object)helm.getType()) && Groups.WITHER_CHESTPLATES.contains((Object)chest.getType()) && Groups.WITHER_LEGGINGS.contains((Object)leg.getType()) && Groups.WITHER_BOOTS.contains((Object)boots.getType())) {
                if (Witherborn.WITHER_COOLDOWN.containsKey((Object)p.getUniqueId())) {
                    if (!(boolean)Witherborn.WITHER_COOLDOWN.get((Object)p.getUniqueId()) && !Witherborn.WITHER_MAP.containsKey((Object)p.getUniqueId())) {
                        final Witherborn w = new Witherborn(p);
                        w.spawnWither();
                    }
                }
                else if (!Witherborn.WITHER_MAP.containsKey((Object)p.getUniqueId())) {
                    final Witherborn w = new Witherborn(p);
                    w.spawnWither();
                }
            }
            else {
                Witherborn.WITHER_MAP.remove((Object)p.getUniqueId());
            }
        }
        else {
            Witherborn.WITHER_MAP.remove((Object)p.getUniqueId());
        }
    }
    
    public static void updateStatistics1(final Player player) {
        final PlayerInventory inv = player.getInventory();
        final PlayerStatistics statistics = (PlayerStatistics)PlayerUtils.STATISTICS_CACHE.get((Object)player.getUniqueId());
        final ItemStack afterHelmet = inv.getHelmet();
        final ItemStack afterChestplate = inv.getChestplate();
        final ItemStack afterLeggings = inv.getLeggings();
        final ItemStack afterBoots = inv.getBoots();
        SItem helmet = null;
        SItem chestplate = null;
        SItem leggings = null;
        SItem boots = null;
        PlayerUtils.updateArmorStatistics(helmet = SItem.find(afterHelmet), statistics, 0);
        PlayerUtils.updateArmorStatistics(chestplate = SItem.find(afterChestplate), statistics, 1);
        PlayerUtils.updateArmorStatistics(leggings = SItem.find(afterLeggings), statistics, 2);
        PlayerUtils.updateArmorStatistics(boots = SItem.find(afterBoots), statistics, 3);
        SUtil.delay(() -> PlayerUtils.updateInventoryStatistics(player, statistics), 1L);
    }
    
    private static boolean similar(final ItemStack is, final ItemStack is1) {
        return (null == is && null == is1) || ((null == is || null != is1) && null != is && is.isSimilar(is1));
    }
    
    private static boolean isAir(final ItemStack is) {
        return null == is || Material.AIR == is.getType();
    }
    
    @EventHandler
    public void aAaB(final ProjectileHitEvent enn) {
        final Entity e = (Entity)enn.getEntity();
        if (!(e instanceof Arrow)) {
            return;
        }
        if (!(((Arrow)e).getShooter() instanceof Player)) {
            return;
        }
        final Player player = (Player)((Arrow)e).getShooter();
        boolean ACT = true;
        if (e.isOnGround()) {
            return;
        }
        for (final Entity enderman_1 : e.getWorld().getNearbyEntities(e.getLocation(), 1.5, 1.5, 1.5)) {
            if (enderman_1 instanceof Enderman && ACT && !e.isOnGround() && !enderman_1.isDead()) {
                if (null == SItem.find(player.getItemInHand())) {
                    e.remove();
                    break;
                }
                if (SMaterial.TERMINATOR != SItem.find(player.getItemInHand()).getType() && SMaterial.JUJU_SHORTBOW != SItem.find(player.getItemInHand()).getType()) {
                    e.remove();
                    return;
                }
                ACT = false;
                final EntityDamageByEntityEvent bl = new EntityDamageByEntityEvent(e, enderman_1, EntityDamageEvent.DamageCause.CUSTOM, 1);
                Bukkit.getPluginManager().callEvent((Event)bl);
                ((LivingEntity)enderman_1).setHealth(((LivingEntity)enderman_1).getHealth() - Math.min(((LivingEntity)enderman_1).getHealth(), bl.getDamage()));
                e.remove();
            }
        }
    }
    
    @EventHandler
    public void changeBlock(final EntityChangeBlockEvent event) {
        final Entity fallingBlock = event.getEntity();
        if (EntityType.FALLING_BLOCK == event.getEntityType() && fallingBlock.hasMetadata("t")) {
            final Block block = event.getBlock();
            block.setType(Material.AIR);
            event.setCancelled(true);
            final List<Entity> entityList = (List<Entity>)fallingBlock.getNearbyEntities(3.0, 3.0, 3.0);
            fallingBlock.getWorld().playSound(fallingBlock.getLocation(), Sound.EXPLODE, 2.0f, 0.0f);
            fallingBlock.getWorld().playEffect(fallingBlock.getLocation(), Effect.EXPLOSION_HUGE, 0);
            for (final Entity e : fallingBlock.getNearbyEntities(7.0, 7.0, 7.0)) {
                if (e instanceof Item) {
                    e.remove();
                }
            }
            fallingBlock.setVelocity(new Vector(0, 0, 0));
            final List<Entity> fallingBlockList = (List<Entity>)fallingBlock.getNearbyEntities(7.0, 7.0, 7.0);
            fallingBlockList.forEach(entity -> {
                if (EntityType.FALLING_BLOCK == entity.getType() && entity.hasMetadata("t")) {
                    entity.remove();
                }
            });
            entityList.forEach(entity -> {
                if (entity instanceof Player) {
                    final Player p = (Player)entity;
                    JollyPinkGiant.damagePlayer(p);
                }
                else if (entity instanceof LivingEntity && !(entity instanceof Player)) {
                    ((LivingEntity)entity).damage(0.0);
                }
            });
            fallingBlock.remove();
        }
    }
    
    @EventHandler
    public void changeBlockF1(final EntityChangeBlockEvent event) {
        final Entity fallingBlock = event.getEntity();
        if (EntityType.FALLING_BLOCK == event.getEntityType() && fallingBlock.hasMetadata("f")) {
            final Block block = event.getBlock();
            block.setType(Material.AIR);
            event.setCancelled(true);
            final List<Entity> entityList = (List<Entity>)fallingBlock.getNearbyEntities(3.0, 3.0, 3.0);
            fallingBlock.getWorld().playSound(fallingBlock.getLocation(), Sound.EXPLODE, 2.0f, 0.0f);
            fallingBlock.getWorld().playEffect(fallingBlock.getLocation(), Effect.EXPLOSION_HUGE, 0);
            for (final Entity e : fallingBlock.getNearbyEntities(7.0, 7.0, 7.0)) {
                if (e instanceof Item) {
                    e.remove();
                }
            }
            fallingBlock.setVelocity(new Vector(0, 0, 0));
            final List<Entity> fallingBlockList = (List<Entity>)fallingBlock.getNearbyEntities(7.0, 7.0, 7.0);
            fallingBlockList.forEach(entity -> {
                if (EntityType.FALLING_BLOCK == entity.getType() && entity.hasMetadata("f")) {
                    entity.remove();
                }
            });
            entityList.forEach(entity -> {
                if (entity instanceof Player) {
                    final Player p = (Player)entity;
                    SadanGiant.damagePlayer(p);
                }
                else if (entity instanceof LivingEntity && !(entity instanceof Player)) {
                    ((LivingEntity)entity).damage(0.0);
                }
            });
            fallingBlock.remove();
        }
    }
    
    @EventHandler
    public void onentityded(final EntityDeathEvent e) {
        if (e.getEntity().getWorld().getName().contains((CharSequence)"f6")) {
            e.setDroppedExp(0);
        }
    }
    
    static {
        Classes = (Map)new HashMap();
        IsDead = (Map)new HashMap();
    }
}
