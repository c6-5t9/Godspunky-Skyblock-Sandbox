package net.hypixel.skyblock.item.weapon;

import org.bukkit.plugin.Plugin;
import net.hypixel.skyblock.SkyBlock;
import java.util.Iterator;
import org.bukkit.Location;
import java.math.RoundingMode;
import java.math.BigDecimal;
import org.bukkit.Sound;
import net.hypixel.skyblock.util.FerocityCalculation;
import net.hypixel.skyblock.listener.PlayerListener;
import net.hypixel.skyblock.util.Sputnik;
import net.hypixel.skyblock.user.User;
import net.hypixel.skyblock.util.Groups;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Item;
import org.bukkit.entity.Villager;
import org.bukkit.entity.EnderDragonPart;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.EulerAngle;
import net.hypixel.skyblock.util.SUtil;
import org.bukkit.Effect;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import net.hypixel.skyblock.item.SMaterial;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ArmorStand;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.PacketPlayOutAnimation;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import net.hypixel.skyblock.item.SItem;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;
import net.hypixel.skyblock.item.SpecificItemType;
import net.hypixel.skyblock.item.GenericItemType;
import net.hypixel.skyblock.item.Rarity;
import net.hypixel.skyblock.item.Ability;
import net.hypixel.skyblock.item.MaterialFunction;
import net.hypixel.skyblock.item.ToolStatistics;

public class LividDagger implements ToolStatistics, MaterialFunction, Ability
{
    String ACT3;
    private boolean active;
    
    public LividDagger() {
        this.ACT3 = "true";
    }
    
    @Override
    public int getBaseDamage() {
        return 210;
    }
    
    @Override
    public double getBaseStrength() {
        return 60.0;
    }
    
    @Override
    public double getBaseCritChance() {
        return 1.0;
    }
    
    @Override
    public double getBaseAttackSpeed() {
        return 100.0;
    }
    
    @Override
    public double getBaseCritDamage() {
        return 0.5;
    }
    
    @Override
    public String getDisplayName() {
        return "Livid Dagger";
    }
    
    @Override
    public Rarity getRarity() {
        return Rarity.LEGENDARY;
    }
    
    @Override
    public GenericItemType getType() {
        return GenericItemType.WEAPON;
    }
    
    @Override
    public SpecificItemType getSpecificType() {
        return SpecificItemType.SWORD;
    }
    
    @Override
    public String getLore() {
        return null;
    }
    
    @Override
    public String getAbilityName() {
        return "Throw";
    }
    
    @Override
    public String getAbilityDescription() {
        return "Throw your dagger at your enemies! Your Critical Hits deal " + (Object)ChatColor.BLUE + "100% " + (Object)ChatColor.GRAY + "more damage if you are behind your target.";
    }
    
    @Override
    public void onAbilityUse(final Player player1, final SItem sItem) {
        final Location throwLoc = player1.getLocation().add(0.0, 1.2, 0.0);
        final Vector throwVec = player1.getLocation().add(player1.getLocation().getDirection().multiply(10)).toVector().subtract(player1.getLocation().toVector()).normalize().multiply(1.2);
        for (final Player p : player1.getWorld().getPlayers()) {
            ((CraftPlayer)p).getHandle().playerConnection.sendPacket((Packet)new PacketPlayOutAnimation((Entity)((CraftLivingEntity)player1).getHandle(), 0));
        }
        final ArmorStand armorStand1 = (ArmorStand)player1.getWorld().spawnEntity(throwLoc, EntityType.ARMOR_STAND);
        armorStand1.getEquipment().setItemInHand(SItem.of(SMaterial.IRON_SWORD).getStack());
        armorStand1.setGravity(false);
        armorStand1.setVisible(false);
        final Player bukkitPlayer = player1.getPlayer();
        final Vector teleportTo = bukkitPlayer.getLocation().getDirection().normalize().multiply(1);
        final Vector[] previousVector = { throwVec };
        new BukkitRunnable() {
            private int run = -1;
            
            public void run() {
                final int ran;
                final int i = ran = 0;
                final int num = 90;
                final Location loc = null;
                ++this.run;
                if (this.run > 100) {
                    this.cancel();
                    return;
                }
                for (int j = 0; j < 7; ++j) {
                    armorStand1.getWorld().spigot().playEffect(armorStand1.getLocation().clone().add(0.0, 1.75, 0.0), Effect.CRIT, 0, 1, (float)SUtil.random(-0.5, 0.5), (float)SUtil.random(0.0, 0.5), (float)SUtil.random(-0.5, 0.5), 0.0f, 1, 100);
                }
                if (!armorStand1.getLocation().getBlock().getType().isTransparent() || armorStand1.isOnGround()) {
                    armorStand1.remove();
                    this.cancel();
                    return;
                }
                final double xPos = armorStand1.getRightArmPose().getX();
                armorStand1.setRightArmPose(new EulerAngle(xPos + 0.35, 0.0, 0.0));
                final Vector newVector = new Vector(throwVec.getX(), previousVector[0].getY() - 0.03, throwVec.getZ());
                previousVector[0] = newVector;
                armorStand1.setVelocity(newVector);
                if (i < 13) {
                    final int angle = i * 20 + 90;
                }
                else {
                    final int angle = i * 20 - 90;
                }
                if (!armorStand1.getLocation().getBlock().getType().isTransparent()) {
                    armorStand1.remove();
                    this.cancel();
                    return;
                }
                if (i % 2 == 0 && i < 13) {
                    armorStand1.teleport(armorStand1.getLocation().add(teleportTo).multiply(1.0));
                }
                else if (i % 2 == 0) {
                    armorStand1.teleport(armorStand1.getLocation().subtract(loc.getDirection().normalize().multiply(1)));
                }
                for (final org.bukkit.entity.Entity e : armorStand1.getNearbyEntities(0.7, 0.7, 0.7)) {
                    if (e instanceof LivingEntity && e != player1.getPlayer()) {
                        final Damageable entity = (Damageable)e;
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
                        if (entity.hasMetadata("GiantSword")) {
                            continue;
                        }
                        if (Groups.ENDERMAN.contains((Object)entity.getType())) {
                            continue;
                        }
                        final User user = User.getUser(player1.getUniqueId());
                        final Object[] atp = Sputnik.calculateDamage(player1, player1, sItem.getStack(), (LivingEntity)entity, false);
                        final double finalDamage1 = (float)atp[0];
                        PlayerListener.spawnDamageInd((org.bukkit.entity.Entity)entity, (float)atp[2], (boolean)atp[1]);
                        FerocityCalculation.activeFerocityTimes(player1, (LivingEntity)entity, (int)finalDamage1, (boolean)atp[1]);
                        user.damageEntity(entity, finalDamage1);
                        player1.playSound(player1.getLocation(), Sound.ITEM_BREAK, 1.0f, 1.0f);
                        player1.sendMessage(Sputnik.trans("&7Your Livid Dagger hit &c1 &7enemy for &c" + SUtil.commaify(new BigDecimal(finalDamage1).setScale(1, RoundingMode.HALF_EVEN).doubleValue()) + " &7damage."));
                        this.cancel();
                        armorStand1.remove();
                        break;
                    }
                }
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 1L, 1L);
        new BukkitRunnable() {
            public void run() {
                armorStand1.remove();
                this.cancel();
            }
        }.runTaskLater((Plugin)SkyBlock.getPlugin(), 100L);
    }
    
    @Override
    public int getAbilityCooldownTicks() {
        return 100;
    }
    
    @Override
    public int getManaCost() {
        return 150;
    }
}
