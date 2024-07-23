package net.hypixel.skyblock.gui;

import java.util.HashMap;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityEquipment;
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntityLiving;
import net.minecraft.server.v1_8_R3.EntityLiving;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.LivingEntity;
import net.minecraft.server.v1_8_R3.Packet;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import java.util.Iterator;
import org.bukkit.World;
import org.bukkit.util.Vector;
import org.bukkit.entity.Entity;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.metadata.FixedMetadataValue;
import net.hypixel.skyblock.SkyBlock;
import org.bukkit.util.EulerAngle;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.Sound;
import net.hypixel.skyblock.util.Sputnik;
import java.util.List;
import net.hypixel.skyblock.item.SItem;
import net.hypixel.skyblock.util.SUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.event.inventory.InventoryClickEvent;
import java.util.Collection;
import net.hypixel.skyblock.item.pet.Pet;
import net.hypixel.skyblock.util.PaginationList;
import net.hypixel.skyblock.user.User;
import net.hypixel.skyblock.util.SLog;
import org.bukkit.entity.Player;
import java.util.UUID;
import java.util.Map;

public class PetsGUI extends GUI
{
    private static final int[] INTERIOR;
    public static final Map<UUID, Boolean> PET_SHOWN;
    private int page;
    private final boolean pickup;
    
    public static void setShowPets(final Player p, final boolean bo) {
        if (p == null) {
            SLog.severe("An unexpected error occured on Pets saving!");
        }
        PetsGUI.PET_SHOWN.put((Object)p.getUniqueId(), (Object)bo);
    }
    
    public static boolean getShowPet(final Player p) {
        if (p == null) {
            return true;
        }
        if (PetsGUI.PET_SHOWN.containsKey((Object)p.getUniqueId())) {
            return (boolean)PetsGUI.PET_SHOWN.get((Object)p.getUniqueId());
        }
        PetsGUI.PET_SHOWN.put((Object)p.getUniqueId(), (Object)true);
        return true;
    }
    
    public PetsGUI(final int page, final boolean pickup) {
        super("Pets", 54);
        this.page = page;
        this.pickup = pickup;
    }
    
    public PetsGUI(final boolean pickup) {
        this(1, pickup);
    }
    
    public PetsGUI() {
        this(false);
    }
    
    @Override
    public void onOpen(final GUIOpenEvent e) {
        final Player player = e.getPlayer();
        final User user = User.getUser(player.getUniqueId());
        this.border(PetsGUI.BLACK_STAINED_GLASS_PANE);
        final PaginationList<Pet.PetItem> paged = new PaginationList<Pet.PetItem>(28);
        paged.addAll((Collection)user.getPets());
        if (paged.size() == 0) {
            this.page = 0;
        }
        final int finalPage = this.page;
        if (this.page > 1) {
            this.title = "(" + finalPage + "/" + this.page + ") Pets";
            this.set(new GUIClickableItem() {
                @Override
                public void run(final InventoryClickEvent e) {
                    new PetsGUI(finalPage - 1, false).open((Player)e.getWhoClicked());
                }
                
                @Override
                public int getSlot() {
                    return 45;
                }
                
                @Override
                public ItemStack getItem() {
                    return SUtil.createNamedItemStack(Material.ARROW, (Object)ChatColor.GREEN + "Pervious Page");
                }
            });
        }
        if (this.page != paged.getPageCount()) {
            this.set(new GUIClickableItem() {
                @Override
                public void run(final InventoryClickEvent e) {
                    new PetsGUI(finalPage + 1, false).open((Player)e.getWhoClicked());
                }
                
                @Override
                public int getSlot() {
                    return 53;
                }
                
                @Override
                public ItemStack getItem() {
                    return SUtil.createNamedItemStack(Material.ARROW, (Object)ChatColor.GREEN + "Next Page");
                }
            });
        }
        final Pet.PetItem active = user.getActivePet();
        String name;
        if (active == null) {
            name = (Object)ChatColor.RED + "None";
        }
        else {
            name = (Object)active.getRarity().getColor() + active.getType().getDisplayName(active.getType().getData());
        }
        this.set(4, SUtil.getStack((Object)ChatColor.GREEN + "Pets", Material.BONE, (short)0, 1, (Object)ChatColor.GRAY + "View and manage all of your", (Object)ChatColor.GRAY + "Pets.", " ", (Object)ChatColor.GRAY + "Level up your pets faster by", (Object)ChatColor.GRAY + "gaining XP in their favorite", (Object)ChatColor.GRAY + "skill!", " ", (Object)ChatColor.GRAY + "Selected pet: " + name));
        this.set(47, SUtil.getStack((Object)ChatColor.GREEN + "Pet Score Rewards", Material.DIAMOND, (short)0, 1, (Object)ChatColor.GRAY + "Pet score is calculated based", (Object)ChatColor.GRAY + "on how many " + (Object)ChatColor.GREEN + "unique" + (Object)ChatColor.GRAY + " pets you", (Object)ChatColor.GRAY + "have and the " + (Object)ChatColor.GREEN + "rarity" + (Object)ChatColor.GRAY + " of these", (Object)ChatColor.GRAY + "pets.", " ", (Object)ChatColor.GOLD + "10 Score: " + (Object)ChatColor.GRAY + "+" + (Object)ChatColor.AQUA + "1 Magic Find", (Object)ChatColor.GOLD + "25 Score: " + (Object)ChatColor.GRAY + "+" + (Object)ChatColor.AQUA + "2 Magic Find", (Object)ChatColor.GOLD + "50 Score: " + (Object)ChatColor.GRAY + "+" + (Object)ChatColor.AQUA + "3 Magic Find", (Object)ChatColor.GOLD + "75 Score: " + (Object)ChatColor.GRAY + "+" + (Object)ChatColor.AQUA + "4 Magic Find", (Object)ChatColor.GOLD + "100 Score: " + (Object)ChatColor.GRAY + "+" + (Object)ChatColor.AQUA + "5 Magic Find", (Object)ChatColor.GOLD + "130 Score: " + (Object)ChatColor.GRAY + "+" + (Object)ChatColor.AQUA + "6 Magic Find", (Object)ChatColor.GOLD + "175 Score: " + (Object)ChatColor.GRAY + "+" + (Object)ChatColor.AQUA + "7 Magic Find", " ", (Object)ChatColor.BLUE + "Your Pet Score: " + (Object)ChatColor.RED + "Coming soon!"));
        this.set(GUIClickableItem.createGUIOpenerItem(GUIType.SKYBLOCK_MENU, player, (Object)ChatColor.GREEN + "Go Back", 48, Material.ARROW, (Object)ChatColor.GRAY + "To SkyBlock Menu"));
        this.set(GUIClickableItem.getCloseItem(49));
        this.set(new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent e) {
                new PetsGUI(PetsGUI.this.page, !PetsGUI.this.pickup).open(player);
            }
            
            @Override
            public int getSlot() {
                return 50;
            }
            
            @Override
            public ItemStack getItem() {
                return SUtil.getStack((Object)ChatColor.GREEN + "Convert Pet to an Item", Material.INK_SACK, (short)(PetsGUI.this.pickup ? 10 : 8), 1, (Object)ChatColor.GRAY + "Enable this setting and", (Object)ChatColor.GRAY + "click any pet to convert it", (Object)ChatColor.GRAY + "to an item.", " ", PetsGUI.this.pickup ? ((Object)ChatColor.GREEN + "Enabled") : ((Object)ChatColor.RED + "Disabled"));
            }
        });
        this.set(new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent e) {
                final Player p = (Player)e.getWhoClicked();
                if (p == null) {
                    return;
                }
                if (PetsGUI.getShowPet(player)) {
                    PetsGUI.setShowPets(player, false);
                    player.sendMessage((Object)ChatColor.GREEN + "Hide Pets is now enabled!");
                    player.closeInventory();
                }
                else {
                    PetsGUI.this.showPetInRange(player);
                    PetsGUI.setShowPets(player, true);
                    player.sendMessage((Object)ChatColor.RED + "Hide Pets is now disabled!");
                    player.closeInventory();
                }
            }
            
            @Override
            public int getSlot() {
                return 51;
            }
            
            @Override
            public ItemStack getItem() {
                ItemStack isBuilder = new ItemStack(Material.BEDROCK, 1);
                if (PetsGUI.getShowPet(player)) {
                    isBuilder = SUtil.getStack((Object)ChatColor.GREEN + "Hide Pets", Material.STONE_BUTTON, (short)0, 1, (Object)ChatColor.GRAY + "Hide all pets which are", (Object)ChatColor.GRAY + "little heads from being", (Object)ChatColor.GRAY + "visible in the world.", " ", (Object)ChatColor.GRAY + "Pet effects remain active.", " ", (Object)ChatColor.GRAY + "Currently: " + (Object)ChatColor.GREEN + "Pets shown!", (Object)ChatColor.GRAY + "Selected pet: " + name, " ", (Object)ChatColor.YELLOW + "Click to hide!");
                }
                else {
                    isBuilder = SUtil.getStack((Object)ChatColor.RED + "Hide Pets", Material.STONE_BUTTON, (short)0, 1, (Object)ChatColor.GRAY + "Hide all pets which are", (Object)ChatColor.GRAY + "little heads from being", (Object)ChatColor.GRAY + "visible in the world.", " ", (Object)ChatColor.GRAY + "Pet effects remain active.", " ", (Object)ChatColor.GRAY + "Currently: " + (Object)ChatColor.RED + "Pets hidden!", (Object)ChatColor.GRAY + "Selected pet: " + name, " ", (Object)ChatColor.YELLOW + "Click to show!");
                }
                return isBuilder;
            }
        });
        final List<Pet.PetItem> p = paged.getPage(this.page);
        if (p == null) {
            return;
        }
        for (int i = 0; i < p.size(); ++i) {
            final int slot = PetsGUI.INTERIOR[i];
            final Pet.PetItem pet = (Pet.PetItem)p.get(i);
            final String n = (Object)pet.getRarity().getColor() + pet.getType().getDisplayName(pet.getType().getData());
            final SItem item = SItem.of(pet.getType());
            item.setRarity(pet.getRarity());
            item.setDataDouble("xp", pet.getXp());
            item.getData().setBoolean("equipped", true);
            item.update();
            if (!this.pickup) {
                final ItemMeta meta = item.getStack().getItemMeta();
                final List<String> lore = (List<String>)meta.getLore();
                lore.add((Object)" ");
                if (pet.isActive()) {
                    lore.add((Object)((Object)ChatColor.RED + "Click to despawn"));
                }
                else {
                    lore.add((Object)((Object)ChatColor.YELLOW + "Click to summon"));
                }
                meta.setLore((List)lore);
                item.getStack().setItemMeta(meta);
            }
            this.set(new GUIClickableItem() {
                @Override
                public void run(final InventoryClickEvent e) {
                    if (PetsGUI.this.pickup) {
                        if (Sputnik.isFullInv(player)) {
                            player.sendMessage((Object)ChatColor.RED + "Your inventory is full! Clean it up!");
                            player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1.0f, 1.0f);
                            return;
                        }
                        final SItem n = SItem.of(pet.getType());
                        n.setRarity(pet.getRarity());
                        n.setDataDouble("xp", pet.getXp());
                        player.getInventory().addItem(new ItemStack[] { n.getStack() });
                        pet.setActive(false);
                        if (user.getActivePet() == pet) {
                            PetsGUI.destroyArmorStandWithUUID(player.getUniqueId(), player.getWorld());
                        }
                        user.removePet(pet);
                        new PetsGUI(PetsGUI.this.page, false).open(player);
                        player.playSound(player.getLocation(), Sound.ORB_PICKUP, 1.0f, 1.0f);
                    }
                    else {
                        if (pet.isActive()) {
                            pet.setActive(false);
                            player.closeInventory();
                            PetsGUI.destroyArmorStandWithUUID(player.getUniqueId(), player.getWorld());
                            player.sendMessage((Object)ChatColor.GREEN + "You despawned your " + n + (Object)ChatColor.GREEN + "!");
                            return;
                        }
                        user.equipPet(pet);
                        PetsGUI.destroyArmorStandWithUUID(player.getUniqueId(), player.getWorld());
                        player.closeInventory();
                        player.sendMessage((Object)ChatColor.GREEN + "You spawned your " + n + (Object)ChatColor.GREEN + "!");
                        PetsGUI.spawnFlyingHeads(player, user.getActivePetClass(), pet.toItem().getStack());
                    }
                }
                
                @Override
                public int getSlot() {
                    return slot;
                }
                
                @Override
                public ItemStack getItem() {
                    return item.getStack();
                }
            });
        }
    }
    
    public static void applyThingy(final ArmorStand as, final boolean a) {
        as.setCustomNameVisible(a);
        as.setMarker(true);
        as.setVisible(false);
        as.setGravity(false);
        as.setArms(true);
        as.setRightArmPose(new EulerAngle(0.0, 45.0, 0.0));
        as.setMetadata("pets", (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)true));
        as.setRemoveWhenFarAway(false);
    }
    
    public static BukkitTask spawnFlyingHeads(final Player player, final Pet petclass, final ItemStack stacc) {
        destroyArmorStandWithUUID(player.getUniqueId(), player.getWorld());
        final Pet.PetItem active = User.getUser(player.getUniqueId()).getActivePet();
        final int level = Pet.getLevel(active.getXp(), active.getRarity());
        final Location location = player.getLocation();
        final ArmorStand name = (ArmorStand)player.getWorld().spawn(player.getLocation(), (Class)ArmorStand.class);
        applyThingy(name, true);
        name.setSmall(true);
        name.setMetadata(player.getUniqueId().toString() + "_pets", (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)true));
        final ArmorStand stand = (ArmorStand)player.getWorld().spawn(player.getLocation(), (Class)ArmorStand.class);
        applyThingy(stand, false);
        stand.setMetadata(player.getUniqueId().toString() + "_pets", (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)true));
        final String displayname = Sputnik.trans("&8[&7Lv" + level + "&8] " + (Object)active.toItem().getRarity().getColor() + player.getName() + "'s " + petclass.getDisplayName());
        stand.getEquipment().setItemInHand(stacc);
        name.setCustomName(displayname);
        stand.setMetadata(player.getUniqueId().toString(), (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)true));
        return new BukkitRunnable() {
            int count = 0;
            int stat = 0;
            
            public void run() {
                final Pet.PetItem active1 = User.getUser(player.getUniqueId()).getActivePet();
                if (active1 == null || !player.isOnline() || !player.getWorld().equals(stand.getWorld())) {
                    name.remove();
                    stand.remove();
                    this.cancel();
                    return;
                }
                if (name.isDead()) {
                    stand.remove();
                    this.cancel();
                    return;
                }
                if (!player.getWorld().getEntities().contains((Object)name)) {
                    name.remove();
                    stand.remove();
                    if (active1 != null) {
                        Sputnik.createPet(player);
                    }
                    this.cancel();
                    return;
                }
                if (player.getWorld().getName().contains((CharSequence)"f6")) {
                    name.setCustomNameVisible(false);
                }
                final Pet.PetItem active2 = User.getUser(player.getUniqueId()).getActivePet();
                final int level1 = Pet.getLevel(active2.getXp(), active2.getRarity());
                name.setCustomName(Sputnik.trans("&8[&7Lv" + level1 + "&8] " + (Object)active2.toItem().getRarity().getColor() + player.getName() + "'s " + petclass.getDisplayName()));
                stand.getEquipment().setItemInHand(stacc);
                final Location target = player.getLocation();
                target.setPitch(0.0f);
                target.add(target.getDirection().multiply(-1));
                final double distance = target.distance(location);
                final double yoffset = Math.sin((double)(this.count / 4.0f)) / 1.7 + 1.0;
                if ((distance < 5.0 && this.stat >= 1) || distance < 0.6) {
                    if (this.stat < 5) {
                        ++this.stat;
                        return;
                    }
                    ++this.count;
                    if (this.count > 24) {
                        this.count = 0;
                    }
                    location.setY((location.getY() + target.getY()) / 2.0);
                }
                else {
                    this.stat = 0;
                    final Vector v = target.toVector().subtract(location.toVector()).normalize().multiply(Math.min(9.0, Math.min(15.0, distance / 2.0) / 4.0 + 0.2));
                    location.setDirection(v);
                    location.add(v);
                    if (this.count > 13) {
                        this.count /= 1;
                    }
                    else if (this.count < 11) {
                        this.count *= 1;
                    }
                }
                final Location nameLoc = location.clone();
                if (nameLoc.distanceSquared(target) > 25.0) {
                    name.setCustomNameVisible(false);
                }
                else if (!target.getWorld().getName().contains((CharSequence)"f6")) {
                    name.setCustomNameVisible(true);
                }
                nameLoc.setPitch(0.0f);
                nameLoc.add(nameLoc.getDirection().multiply(0.15));
                nameLoc.setYaw(nameLoc.getYaw() + 90.0f);
                nameLoc.add(nameLoc.getDirection().multiply(0.527));
                name.teleport(nameLoc.add(0.0, yoffset + 0.85, 0.0));
                stand.teleport(location.clone().add(0.0, yoffset, 0.0));
                final Pet pet_ = User.getUser(player.getUniqueId()).getActivePetClass();
                if ((distance < 5.0 && this.stat >= 1) || distance < 0.6) {
                    PetsGUI.spawnParticle(pet_, nameLoc.clone().add(0.0, -0.2, 0.0));
                }
                else {
                    PetsGUI.spawnParticle(pet_, nameLoc.clone().add(0.0, -0.2, 0.0).add(location.clone().add(0.0, yoffset, 0.0).getDirection().clone().multiply(-0.6)));
                }
                PetsGUI.sendDestroyPacket((Entity)stand, (Entity)name, player.getWorld(), player);
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 3L, 3L);
    }
    
    public static void destroyArmorStandWithUUID(final UUID uuid, final World w) {
        final String uuidString = uuid.toString() + "_pets";
        for (final Entity e : w.getEntities()) {
            if (e.hasMetadata(uuidString)) {
                e.remove();
            }
        }
    }
    
    public static void spawnParticle(final Pet pet, final Location l) {
        final World w = l.getWorld();
        for (final Entity e : w.getNearbyEntities(l, 30.0, 35.0, 30.0)) {
            if (e instanceof Player) {
                final Player p = (Player)e;
                if (!getShowPet(p)) {
                    continue;
                }
                pet.particleBelowA(p, l);
            }
        }
    }
    
    public static void sendDestroyPacket(final Entity as1, final Entity as2, final World w, final Player owner) {
        final net.minecraft.server.v1_8_R3.Entity el = ((CraftEntity)as1).getHandle();
        final net.minecraft.server.v1_8_R3.Entity el_ = ((CraftEntity)as2).getHandle();
        final PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(new int[] { el.getId() });
        final PacketPlayOutEntityDestroy packet_ = new PacketPlayOutEntityDestroy(new int[] { el_.getId() });
        for (final Entity e : w.getNearbyEntities(as1.getLocation(), 30.0, 35.0, 30.0)) {
            if (e instanceof Player) {
                final Player p = (Player)e;
                if (getShowPet(p)) {
                    continue;
                }
                ((CraftPlayer)p).getHandle().playerConnection.sendPacket((Packet)packet);
                ((CraftPlayer)p).getHandle().playerConnection.sendPacket((Packet)packet_);
            }
        }
    }
    
    public void showPetInRange(final Player p) {
        for (final Entity e : p.getNearbyEntities(30.0, 35.0, 30.0)) {
            if (e.hasMetadata("pets")) {
                final net.minecraft.server.v1_8_R3.Entity el = ((CraftEntity)e).getHandle();
                final net.minecraft.server.v1_8_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(((LivingEntity)e).getEquipment().getItemInHand());
                el.setEquipment(0, nmsItem);
                final PacketPlayOutSpawnEntityLiving packet = new PacketPlayOutSpawnEntityLiving((EntityLiving)el);
                final PacketPlayOutEntityEquipment packet2 = new PacketPlayOutEntityEquipment(el.getId(), 0, nmsItem);
                ((CraftPlayer)p).getHandle().playerConnection.sendPacket((Packet)packet);
                ((CraftPlayer)p).getHandle().playerConnection.sendPacket((Packet)packet2);
            }
        }
    }
    
    static {
        INTERIOR = new int[] { 10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25, 28, 29, 30, 31, 32, 33, 34, 37, 38, 39, 40, 41, 42, 43 };
        PET_SHOWN = (Map)new HashMap();
    }
}
