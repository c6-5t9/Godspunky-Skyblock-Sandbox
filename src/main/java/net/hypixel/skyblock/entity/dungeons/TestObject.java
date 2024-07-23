package net.hypixel.skyblock.entity.dungeons;

import com.google.common.util.concurrent.AtomicDouble;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import net.hypixel.skyblock.entity.SEntityEquipment;
import me.libraryaddict.disguise.disguisetypes.PlayerDisguise;
import net.minecraft.server.v1_8_R3.AttributeInstance;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.metadata.FixedMetadataValue;
import net.hypixel.skyblock.SkyBlock;
import net.hypixel.skyblock.util.EntityManager;
import org.bukkit.entity.Entity;
import net.hypixel.skyblock.util.Sputnik;
import net.minecraft.server.v1_8_R3.GenericAttributes;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftZombie;
import net.hypixel.skyblock.entity.SEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.meta.ItemMeta;
import net.hypixel.skyblock.util.SUtil;
import org.bukkit.Color;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;
import net.hypixel.skyblock.entity.zombie.BaseZombie;

public class TestObject extends BaseZombie
{
    private final boolean isEating;
    private final boolean isBowing;
    private final boolean EatingCooldown;
    
    public TestObject() {
        this.isEating = false;
        this.isBowing = false;
        this.EatingCooldown = false;
    }
    
    @Override
    public String getEntityName() {
        return "Test Object";
    }
    
    @Override
    public double getEntityMaxHealth() {
        return 2.0E7;
    }
    
    @Override
    public double getDamageDealt() {
        return 4.0E8;
    }
    
    public static ItemStack b(final int hexcolor, final Material m) {
        final ItemStack stack = SUtil.applyColorToLeatherArmor(new ItemStack(m), Color.fromRGB(hexcolor));
        final ItemMeta itemMeta = stack.getItemMeta();
        itemMeta.spigot().setUnbreakable(true);
        stack.setItemMeta(itemMeta);
        return stack;
    }
    
    @Override
    public void onSpawn(final LivingEntity entity, final SEntity sEntity) {
        ((CraftZombie)entity).setBaby(false);
        final AttributeInstance followRange = ((CraftLivingEntity)entity).getHandle().getAttributeInstance(GenericAttributes.FOLLOW_RANGE);
        followRange.setValue(40.0);
        final PlayerDisguise pl = Sputnik.applyPacketNPC((Entity)entity, "dungeon", null, false);
        pl.getWatcher().setRightClicking(false);
        EntityManager.DEFENSE_PERCENTAGE.put((Object)entity, (Object)99);
        entity.setMetadata("LD", (MetadataValue)new FixedMetadataValue((Plugin)SkyBlock.getPlugin(), (Object)true));
    }
    
    @Override
    public SEntityEquipment getEntityEquipment() {
        return new SEntityEquipment(new ItemStack(Material.IRON_AXE), null, null, null, null);
    }
    
    @Override
    public boolean isBaby() {
        return false;
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
    public void onDamage(final SEntity sEntity, final Entity damager, final EntityDamageByEntityEvent e, final AtomicDouble damage) {
    }
    
    @Override
    public double getXPDropped() {
        return 5.0;
    }
    
    @Override
    public double getMovementSpeed() {
        return 0.35;
    }
}
