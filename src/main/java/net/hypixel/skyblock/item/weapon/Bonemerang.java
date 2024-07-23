package net.hypixel.skyblock.item.weapon;

import java.util.Iterator;
import org.bukkit.Location;
import net.hypixel.skyblock.util.FerocityCalculation;
import net.hypixel.skyblock.listener.PlayerListener;
import net.hypixel.skyblock.util.Sputnik;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Item;
import org.bukkit.entity.Villager;
import org.bukkit.entity.EnderDragonPart;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Entity;
import net.hypixel.skyblock.util.SUtil;
import org.bukkit.plugin.Plugin;
import net.hypixel.skyblock.SkyBlock;
import org.bukkit.Effect;
import org.bukkit.Material;
import net.hypixel.skyblock.user.User;
import java.util.List;
import org.bukkit.util.Vector;
import org.bukkit.inventory.ItemStack;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.scheduler.BukkitRunnable;
import java.util.ArrayList;
import org.bukkit.util.EulerAngle;
import net.hypixel.skyblock.item.SMaterial;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ArmorStand;
import org.bukkit.Sound;
import net.minecraft.server.v1_8_R3.NBTBase;
import net.minecraft.server.v1_8_R3.NBTTagInt;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import net.hypixel.skyblock.item.SItem;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;
import net.hypixel.skyblock.item.SpecificItemType;
import net.hypixel.skyblock.item.GenericItemType;
import net.hypixel.skyblock.item.Rarity;
import net.hypixel.skyblock.item.Ability;
import net.hypixel.skyblock.item.MaterialFunction;
import net.hypixel.skyblock.item.ToolStatistics;

public class Bonemerang implements ToolStatistics, MaterialFunction, Ability
{
    String Activable;
    private int slotThrew;
    
    public Bonemerang() {
        this.Activable = "true";
        this.slotThrew = 0;
    }
    
    @Override
    public int getBaseDamage() {
        return 270;
    }
    
    @Override
    public double getBaseStrength() {
        return 130.0;
    }
    
    @Override
    public String getDisplayName() {
        return "Bonemerang";
    }
    
    @Override
    public Rarity getRarity() {
        return Rarity.LEGENDARY;
    }
    
    @Override
    public GenericItemType getType() {
        return GenericItemType.RANGED_WEAPON;
    }
    
    @Override
    public SpecificItemType getSpecificType() {
        return SpecificItemType.BOW;
    }
    
    @Override
    public String getLore() {
        return null;
    }
    
    @Override
    public String getAbilityName() {
        return "Swing";
    }
    
    @Override
    public String getAbilityDescription() {
        return "Throw bone a short distance, dealing the damage an arrow would.                      Deals " + (Object)ChatColor.RED + "double" + (Object)ChatColor.GRAY + " damage when coming back. Pierces up to " + (Object)ChatColor.YELLOW + "10" + (Object)ChatColor.GRAY + " foes.";
    }
    
    @Override
    public void onAbilityUse(final Player player, final SItem sItem) {
        if (player.getInventory().getItemInHand().toString().contains((CharSequence)"GHAST_TEAR")) {
            return;
        }
        final int slot = player.getInventory().getHeldItemSlot();
        this.slotThrew = player.getInventory().getHeldItemSlot();
        final ItemStack item = player.getInventory().getItemInHand();
        final net.minecraft.server.v1_8_R3.ItemStack tagStack = CraftItemStack.asNMSCopy(item);
        final NBTTagCompound tagCompound = tagStack.hasTag() ? tagStack.getTag() : new NBTTagCompound();
        tagCompound.set("ejectedBonemerang", (NBTBase)new NBTTagInt(0));
        tagStack.setTag(tagCompound);
        this.releaseBone(player, item, slot);
        final ItemStack bone = player.getInventory().getItemInHand();
        final boolean activate = true;
        player.playSound(player.getLocation(), Sound.SKELETON_HURT, 3.0f, 2.0f);
        if ("true" == this.Activable) {
            final Player bukkitPlayer = player.getPlayer();
            final ArmorStand stand = (ArmorStand)bukkitPlayer.getWorld().spawnEntity(bukkitPlayer.getLocation().add(0.0, 0.7, 0.0), EntityType.ARMOR_STAND);
            final Vector teleportTo = bukkitPlayer.getLocation().getDirection().normalize().multiply(1);
            stand.setVisible(false);
            stand.setGravity(false);
            stand.setArms(false);
            stand.setMarker(true);
            stand.getEquipment().setItemInHand(SItem.of(SMaterial.BONE).getStack());
            stand.setRightArmPose(new EulerAngle(Math.toRadians(350.0), Math.toRadians(120.0), Math.toRadians(0.0)));
            stand.setBasePlate(false);
            final List<Entity> goBone = (List<Entity>)new ArrayList();
            final List<Entity> backBone = (List<Entity>)new ArrayList();
            new BukkitRunnable() {
                public int ran = 0;
                final int slot1 = player.getInventory().getHeldItemSlot();
                final NBTItem nbtItem = new NBTItem(player.getInventory().getItem(this.slot1));
                
                public void run() {
                    ++this.ran;
                    final int slot1 = player.getInventory().getHeldItemSlot();
                    if (26 == this.ran) {
                        Bonemerang.this.returnBone(player, item, slot);
                        User.getUser(player.getUniqueId()).setBoneToZeroDamage(false);
                        stand.remove();
                        this.cancel();
                        return;
                    }
                    final int i = this.ran;
                    final int num = 120;
                    Location loc = null;
                    int angle;
                    boolean back;
                    if (13 > i) {
                        angle = i * 20 + 120;
                        back = false;
                    }
                    else {
                        angle = i * 20 - 120;
                        back = true;
                        loc = player.getLocation();
                        loc.setDirection(teleportTo);
                    }
                    final Location locof = stand.getLocation();
                    locof.setY(locof.getY() + 1.0);
                    if (Material.AIR != locof.getBlock().getType() && Material.WATER != locof.getBlock().getType()) {
                        stand.getWorld().playEffect(stand.getLocation(), Effect.EXPLOSION, 1);
                        stand.getWorld().playEffect(stand.getLocation(), Effect.EXPLOSION, 1);
                        stand.getWorld().playEffect(stand.getLocation(), Effect.EXPLOSION, 1);
                        stand.getWorld().playEffect(stand.getLocation(), Effect.EXPLOSION, 1);
                        stand.getWorld().playEffect(stand.getLocation(), Effect.EXPLOSION, 1);
                        stand.remove();
                        new BukkitRunnable() {
                            public void run() {
                                if (BukkitRunnable.this.nbtItem.hasKey("ejectedBonemerang")) {
                                    Bonemerang.this.returnBone(player, item, slot);
                                }
                                this.cancel();
                            }
                        }.runTaskLater((Plugin)SkyBlock.getPlugin(), 30L);
                        this.cancel();
                        return;
                    }
                    stand.setRightArmPose(new EulerAngle(Math.toRadians(0.0), Math.toRadians((double)angle), Math.toRadians(350.0)));
                    if (0 == i % 2 && 13 > i) {
                        stand.teleport(stand.getLocation().add(teleportTo).multiply(1.0));
                        stand.teleport(stand.getLocation().add(teleportTo).multiply(1.0));
                    }
                    else if (0 == i % 2) {
                        stand.getWorld().spigot().playEffect(stand.getEyeLocation().add(0.0, 1.0, 0.0).clone().add(SUtil.random(-0.5, 0.5), 0.0, SUtil.random(-0.5, 0.5)), Effect.FLYING_GLYPH, 24, 1, 0.0f, 0.0f, 0.0f, 1.0f, 0, 64);
                        stand.teleport(stand.getLocation().subtract(loc.getDirection().normalize().multiply(1)));
                        stand.teleport(stand.getLocation().subtract(loc.getDirection().normalize().multiply(1)));
                    }
                    for (final Entity e : stand.getNearbyEntities(1.0, 1.0, 1.0)) {
                        if (e instanceof LivingEntity && e != player.getPlayer()) {
                            final Damageable entity = (Damageable)e;
                            if (!back && goBone.contains((Object)e)) {
                                continue;
                            }
                            if (back && backBone.contains((Object)e)) {
                                continue;
                            }
                            if (entity.isDead()) {
                                continue;
                            }
                            if (entity.hasMetadata("VWE")) {
                                stand.remove();
                                stand.getWorld().playEffect(stand.getLocation(), Effect.EXPLOSION, 1);
                                stand.getWorld().playEffect(stand.getLocation(), Effect.EXPLOSION, 1);
                                stand.getWorld().playEffect(stand.getLocation(), Effect.EXPLOSION, 1);
                                stand.getWorld().playEffect(stand.getLocation(), Effect.EXPLOSION, 1);
                                stand.getWorld().playEffect(stand.getLocation(), Effect.EXPLOSION, 1);
                                return;
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
                            if (entity.hasMetadata("GiantSword")) {
                                continue;
                            }
                            final double decr = 1.0;
                            final User user = User.getUser(player.getUniqueId());
                            if (user.toBukkitPlayer().getInventory().getHeldItemSlot() != Bonemerang.this.slotThrew) {
                                user.setBoneToZeroDamage(true);
                            }
                            final Object[] atp = Sputnik.calculateDamage(player, player, sItem.getStack(), (LivingEntity)entity, true);
                            double finalDamage1 = (float)atp[0] * (back ? 2 : 1) * 1.0;
                            double rm = 100.0;
                            if (User.getUser(player.getUniqueId()).isBoneToZeroDamage()) {
                                rm = 5.0;
                            }
                            finalDamage1 = finalDamage1 * rm / 100.0;
                            if (!back) {
                                goBone.add((Object)e);
                            }
                            else {
                                backBone.add((Object)e);
                            }
                            PlayerListener.spawnDamageInd((Entity)entity, (float)atp[2] * (back ? 2 : 1) * 1.0 * rm / 100.0, (boolean)atp[1]);
                            FerocityCalculation.activeFerocityTimes(player, (LivingEntity)entity, (int)finalDamage1, (boolean)atp[1]);
                            user.damageEntity(entity, finalDamage1);
                            if (back) {
                                User.getUser(player.getUniqueId()).setBoneToZeroDamage(true);
                            }
                            new BukkitRunnable() {
                                public void run() {
                                    this.cancel();
                                }
                            }.runTaskLater((Plugin)SkyBlock.getPlugin(), 30L);
                        }
                    }
                }
            }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 1L, 1L);
        }
    }
    
    public void releaseBone(final Player player, final ItemStack stack, final Integer slot) {
        final net.minecraft.server.v1_8_R3.ItemStack tagStack = CraftItemStack.asNMSCopy(stack);
        final NBTTagCompound tagCompound = tagStack.hasTag() ? tagStack.getTag() : new NBTTagCompound();
        tagCompound.set("ejectedBonemerang", (NBTBase)new NBTTagInt(1));
        tagStack.setTag(tagCompound);
        final ItemStack itemStack = CraftItemStack.asBukkitCopy(tagStack);
        if (1 == tagStack.getTag().getInt("ejectedBonemerang")) {
            itemStack.setType(Material.GHAST_TEAR);
            player.getInventory().setItem((int)slot, itemStack);
        }
    }
    
    public void returnBone(final Player player, final ItemStack stack, final Integer slot) {
        final net.minecraft.server.v1_8_R3.ItemStack tagStack = CraftItemStack.asNMSCopy(stack);
        final NBTTagCompound tagCompound = tagStack.hasTag() ? tagStack.getTag() : new NBTTagCompound();
        tagCompound.set("ejectedBonemerang", (NBTBase)new NBTTagInt(0));
        tagStack.setTag(tagCompound);
        final SItem sitem = SItem.find(player.getInventory().getItem((int)slot));
        if (null != sitem && SMaterial.BONEMERANG == sitem.getType() && sitem.getDataString("uuid").equals((Object)SItem.find(stack).getDataString("uuid"))) {
            final ItemStack itemStack = CraftItemStack.asBukkitCopy(tagStack);
            if (0 == tagStack.getTag().getInt("ejectedBonemerang")) {
                itemStack.setType(Material.BONE);
                player.getInventory().setItem((int)slot, itemStack);
            }
        }
    }
    
    @Override
    public int getAbilityCooldownTicks() {
        return 0;
    }
    
    @Override
    public boolean isStackable() {
        return false;
    }
    
    @Override
    public int getManaCost() {
        return 0;
    }
}
