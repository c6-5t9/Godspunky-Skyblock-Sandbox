package net.hypixel.skyblock.entity.end;

import org.bukkit.material.MaterialData;
import java.util.Collections;
import net.hypixel.skyblock.item.SItem;
import net.hypixel.skyblock.item.SMaterial;
import net.hypixel.skyblock.entity.EntityFunction;
import net.hypixel.skyblock.item.pet.Pet;
import net.hypixel.skyblock.entity.SEntityType;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import net.hypixel.skyblock.item.pet.PetAbility;
import java.util.function.Consumer;
import com.google.common.util.concurrent.AtomicDouble;
import net.hypixel.skyblock.user.User;
import org.bukkit.entity.Player;
import org.bukkit.entity.Entity;
import net.hypixel.skyblock.entity.SEntity;
import java.util.Arrays;
import net.hypixel.skyblock.entity.EntityDropType;
import org.bukkit.inventory.ItemStack;
import net.hypixel.skyblock.util.SUtil;
import org.bukkit.Material;
import net.hypixel.skyblock.entity.EntityDrop;
import java.util.List;

public class Zealot extends BaseEnderman
{
    @Override
    public String getEntityName() {
        return "Zealot";
    }
    
    @Override
    public double getEntityMaxHealth() {
        return 13000.0;
    }
    
    @Override
    public double getDamageDealt() {
        return 1250.0;
    }
    
    @Override
    public int mobLevel() {
        return 55;
    }
    
    @Override
    public List<EntityDrop> drops() {
        return (List<EntityDrop>)Arrays.asList((Object[])new EntityDrop[] { new EntityDrop(new ItemStack(Material.ENDER_PEARL, SUtil.random(3, 5)), EntityDropType.GUARANTEED, 1.0), new EntityDrop(new ItemStack(Material.ENDER_PEARL, SUtil.random(3, 5)), EntityDropType.GUARANTEED, 0.05) });
    }
    
    @Override
    public void onDeath(final SEntity sEntity, final Entity killed, final Entity damager) {
        final Player player = (Player)damager;
        final User user = User.getUser(player.getUniqueId());
        final Pet pet = user.getActivePetClass();
        final AtomicDouble chance = new AtomicDouble(420.0);
        if (pet != null) {
            pet.runAbilities((Consumer<PetAbility>)(ability -> ability.onZealotAttempt(chance)), user.getActivePet());
        }
        if (SUtil.random(1.0, chance.get()) != 1.0) {
            return;
        }
        player.playSound(player.getLocation(), Sound.WITHER_SPAWN, 1.0f, 1.0f);
        SUtil.sendTitle(player, (Object)ChatColor.RED + "SPECIAL ZEALOT");
        player.sendMessage((Object)ChatColor.GREEN + "A special " + (Object)ChatColor.LIGHT_PURPLE + "Zealot" + (Object)ChatColor.GREEN + " has spawned nearby!");
        new SEntity(killed, SEntityType.SPECIAL_ZEALOT, new Object[0]);
    }
    
    @Override
    public double getXPDropped() {
        return 40.0;
    }
    
    public static class SpecialZealot implements EndermanStatistics, EntityFunction
    {
        @Override
        public String getEntityName() {
            return "Zealot";
        }
        
        @Override
        public double getEntityMaxHealth() {
            return 2000.0;
        }
        
        @Override
        public double getDamageDealt() {
            return 1250.0;
        }
        
        @Override
        public int mobLevel() {
            return 55;
        }
        
        @Override
        public List<EntityDrop> drops() {
            return (List<EntityDrop>)Collections.singletonList((Object)new EntityDrop(SItem.of(SMaterial.SUMMONING_EYE).getStack(), EntityDropType.RARE, 1.0));
        }
        
        @Override
        public MaterialData getCarriedMaterial() {
            return new MaterialData(Material.ENDER_PORTAL_FRAME);
        }
        
        @Override
        public double getXPDropped() {
            return 40.0;
        }
    }
    
    public static class EnderChestZealot implements EndermanStatistics, EntityFunction
    {
        @Override
        public String getEntityName() {
            return "Zealot";
        }
        
        @Override
        public double getEntityMaxHealth() {
            return 13000.0;
        }
        
        @Override
        public double getDamageDealt() {
            return 1250.0;
        }
        
        @Override
        public int mobLevel() {
            return 55;
        }
        
        @Override
        public List<EntityDrop> drops() {
            return (List<EntityDrop>)Arrays.asList((Object[])new EntityDrop[] { new EntityDrop(new ItemStack(Material.ENDER_PEARL, SUtil.random(3, 5)), EntityDropType.GUARANTEED, 1.0), SUtil.getRandom((java.util.List<EntityDrop>)Arrays.asList((Object[])new EntityDrop[] { new EntityDrop(SUtil.setStackAmount(SItem.of(SMaterial.ENCHANTED_ENDER_PEARL).getStack(), SUtil.random(1, 2)), EntityDropType.OCCASIONAL, 1.0), new EntityDrop(SMaterial.CRYSTAL_FRAGMENT, EntityDropType.OCCASIONAL, 1.0), new EntityDrop(SMaterial.ENCHANTED_END_STONE, EntityDropType.OCCASIONAL, 1.0), new EntityDrop(SMaterial.ENCHANTED_OBSIDIAN, EntityDropType.OCCASIONAL, 1.0) })) });
        }
        
        @Override
        public MaterialData getCarriedMaterial() {
            return new MaterialData(Material.ENDER_CHEST);
        }
        
        @Override
        public void onDeath(final SEntity sEntity, final Entity killed, final Entity damager) {
            final Player player = (Player)damager;
            final User user = User.getUser(player.getUniqueId());
            final Pet pet = user.getActivePetClass();
            final AtomicDouble chance = new AtomicDouble(420.0);
            if (pet != null) {
                pet.runAbilities((Consumer<PetAbility>)(ability -> ability.onZealotAttempt(chance)), user.getActivePet());
            }
            if (SUtil.random(1.0, chance.get()) != 1.0) {
                return;
            }
            player.playSound(player.getLocation(), Sound.WITHER_SPAWN, 1.0f, 1.0f);
            SUtil.sendTitle(player, (Object)ChatColor.RED + "SPECIAL ZEALOT");
            player.sendMessage((Object)ChatColor.GREEN + "A special " + (Object)ChatColor.LIGHT_PURPLE + "Zealot" + (Object)ChatColor.GREEN + " has spawned nearby!");
            new SEntity(killed, SEntityType.SPECIAL_ZEALOT, new Object[0]);
        }
        
        @Override
        public double getXPDropped() {
            return 40.0;
        }
    }
}
