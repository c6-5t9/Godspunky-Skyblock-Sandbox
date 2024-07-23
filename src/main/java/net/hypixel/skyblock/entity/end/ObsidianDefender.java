package net.hypixel.skyblock.entity.end;

import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftSkeleton;
import net.minecraft.server.v1_8_R3.GenericAttributes;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftLivingEntity;
import net.hypixel.skyblock.entity.SEntity;
import org.bukkit.entity.LivingEntity;
import net.hypixel.skyblock.entity.SEntityEquipment;
import java.util.Arrays;
import net.hypixel.skyblock.item.SItem;
import net.hypixel.skyblock.item.SMaterial;
import net.hypixel.skyblock.entity.EntityDropType;
import org.bukkit.inventory.ItemStack;
import net.hypixel.skyblock.util.SUtil;
import org.bukkit.Material;
import net.hypixel.skyblock.entity.EntityDrop;
import java.util.List;
import net.hypixel.skyblock.entity.EntityStatistics;
import net.hypixel.skyblock.entity.EntityFunction;

public class ObsidianDefender implements EntityFunction, EntityStatistics
{
    @Override
    public String getEntityName() {
        return "Obsidian Defender";
    }
    
    @Override
    public double getEntityMaxHealth() {
        return 10000.0;
    }
    
    @Override
    public double getDamageDealt() {
        return 200.0;
    }
    
    @Override
    public List<EntityDrop> drops() {
        return (List<EntityDrop>)Arrays.asList((Object[])new EntityDrop[] { new EntityDrop(new ItemStack(Material.OBSIDIAN, SUtil.random(6, 7)), EntityDropType.GUARANTEED, 1.0), new EntityDrop(SItem.of(SMaterial.ENCHANTED_OBSIDIAN).getStack(), EntityDropType.RARE, 0.05), new EntityDrop(SItem.of(SMaterial.OBSIDIAN_CHESTPLATE).getStack(), EntityDropType.EXTRAORDINARILY_RARE, 0.001) });
    }
    
    @Override
    public SEntityEquipment getEntityEquipment() {
        return new SEntityEquipment(new ItemStack(Material.AIR), new ItemStack(Material.OBSIDIAN), SItem.of(SMaterial.OBSIDIAN_CHESTPLATE).getStack(), null, null);
    }
    
    @Override
    public void onSpawn(final LivingEntity entity, final SEntity sEntity) {
        ((CraftLivingEntity)entity).getHandle().getAttributeInstance(GenericAttributes.c).setValue(1.0);
        ((CraftSkeleton)entity).getHandle().setSkeletonType(1);
        entity.getEquipment().setItemInHand((ItemStack)null);
    }
    
    @Override
    public double getMovementSpeed() {
        return 0.6;
    }
    
    @Override
    public void onAttack(final EntityDamageByEntityEvent e) {
        if (!(e.getEntity() instanceof LivingEntity)) {
            return;
        }
        if (e.getEntity() instanceof Player) {
            final PlayerInventory inventory = ((Player)e.getEntity()).getInventory();
            final SItem sItem = SItem.find(inventory.getChestplate());
            if (null != sItem && SMaterial.OBSIDIAN_CHESTPLATE == sItem.getType()) {
                return;
            }
        }
        ((LivingEntity)e.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 80, 1));
    }
    
    @Override
    public double getXPDropped() {
        return 43.2;
    }
    
    @Override
    public int mobLevel() {
        return 55;
    }
}
