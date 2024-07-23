package net.hypixel.skyblock.entity.dungeons.boss.sadan;

import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.Color;
import net.hypixel.skyblock.util.SUtil;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;
import net.hypixel.skyblock.entity.SEntityEquipment;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.metadata.FixedMetadataValue;
import net.hypixel.skyblock.SkyBlock;
import net.hypixel.skyblock.util.EntityManager;
import org.bukkit.entity.Entity;
import org.bukkit.Location;
import net.hypixel.skyblock.entity.SEntity;
import org.bukkit.entity.LivingEntity;
import net.hypixel.skyblock.util.Sputnik;
import net.hypixel.skyblock.entity.zombie.BaseZombie;

public class SadanDummy_Idle extends BaseZombie
{
    @Override
    public String getEntityName() {
        return Sputnik.trans("");
    }
    
    @Override
    public double getEntityMaxHealth() {
        return 4.0E7;
    }
    
    @Override
    public double getDamageDealt() {
        return 120000.0;
    }
    
    @Override
    public void onSpawn(final LivingEntity entity, final SEntity sEntity) {
        entity.teleport(new Location(entity.getWorld(), 191.5, 54.0, 266.5, 180.0f, 0.0f));
        final Location l = entity.getLocation();
        Sputnik.applyPacketGiant((Entity)entity);
        EntityManager.noAI((Entity)entity);
        EntityManager.noHit((Entity)entity);
        entity.setMetadata("GiantSword", (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)true));
        entity.setMetadata("NoAffect", (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)true));
        EntityManager.shutTheFuckUp((Entity)entity);
        EntityManager.DEFENSE_PERCENTAGE.put((Object)entity, (Object)100);
        l.setYaw(180.0f);
        entity.teleport(l);
        entity.setMetadata("notDisplay", (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)true));
        entity.setMetadata("dummy_r", (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)true));
    }
    
    @Override
    public SEntityEquipment getEntityEquipment() {
        return new SEntityEquipment(SUtil.enchant(new ItemStack(Material.DIAMOND_SWORD)), b(5385260, Material.LEATHER_HELMET), b(14751108, Material.LEATHER_CHESTPLATE), c(Material.DIAMOND_LEGGINGS), b(8991025, Material.LEATHER_BOOTS));
    }
    
    public void laser(final LivingEntity e) {
        final int[] array_colors = { 12100772, 12089721, 12080726, 12204602, 12194322 };
        SUtil.delay(() -> e.getEquipment().setHelmet(buildColorStack(array_colors[4])), 270L);
        SUtil.delay(() -> e.getEquipment().setHelmet(buildColorStack(array_colors[3])), 290L);
        SUtil.delay(() -> e.getEquipment().setHelmet(buildColorStack(array_colors[2])), 310L);
        SUtil.delay(() -> e.getEquipment().setHelmet(buildColorStack(array_colors[1])), 330L);
        SUtil.delay(() -> e.getEquipment().setHelmet(buildColorStack(array_colors[0])), 350L);
        SUtil.delay(() -> e.getEquipment().setHelmet(b(15249075, Material.LEATHER_HELMET)), 370L);
    }
    
    public static void t(final LivingEntity e) {
        SUtil.delay(() -> e.teleport(new Location(e.getWorld(), 191.0, 54.0, 266.0)), 1L);
        SUtil.delay(() -> e.teleport(new Location(e.getWorld(), 191.0, 56.0, 266.0)), 20L);
        SUtil.delay(() -> e.teleport(new Location(e.getWorld(), 191.0, 58.0, 266.0)), 40L);
        SUtil.delay(() -> e.teleport(new Location(e.getWorld(), 191.0, 60.0, 266.0)), 60L);
        SUtil.delay(() -> e.teleport(new Location(e.getWorld(), 191.0, 62.0, 266.0)), 80L);
        SUtil.delay(() -> e.teleport(new Location(e.getWorld(), 191.0, 64.0, 266.0)), 100L);
        SUtil.delay(() -> e.teleport(new Location(e.getWorld(), 191.0, 66.0, 266.0)), 120L);
        SUtil.delay(() -> e.teleport(new Location(e.getWorld(), 191.0, 68.0, 266.0)), 140L);
        SUtil.delay(() -> e.teleport(new Location(e.getWorld(), 191.0, 69.0, 266.0)), 160L);
    }
    
    @Override
    public boolean hasNameTag() {
        return false;
    }
    
    @Override
    public boolean isVillager() {
        return false;
    }
    
    @Override
    public boolean isBaby() {
        return false;
    }
    
    @Override
    public double getXPDropped() {
        return 0.0;
    }
    
    @Override
    public double getMovementSpeed() {
        return 0.0;
    }
    
    public static ItemStack buildColorStack(final int hexcolor) {
        final ItemStack stack = SUtil.applyColorToLeatherArmor(new ItemStack(Material.LEATHER_HELMET), Color.fromRGB(hexcolor));
        final ItemMeta itemMeta = stack.getItemMeta();
        itemMeta.spigot().setUnbreakable(true);
        stack.setItemMeta(itemMeta);
        return stack;
    }
    
    public static ItemStack b(final int hexcolor, final Material m) {
        final ItemStack stack = SUtil.applyColorToLeatherArmor(new ItemStack(m), Color.fromRGB(hexcolor));
        final ItemMeta itemMeta = stack.getItemMeta();
        itemMeta.spigot().setUnbreakable(true);
        stack.setItemMeta(itemMeta);
        return stack;
    }
    
    public static ItemStack c(final Material m) {
        final ItemStack stack = new ItemStack(m);
        final ItemMeta itemMeta = stack.getItemMeta();
        itemMeta.spigot().setUnbreakable(true);
        stack.setItemMeta(itemMeta);
        return stack;
    }
}
