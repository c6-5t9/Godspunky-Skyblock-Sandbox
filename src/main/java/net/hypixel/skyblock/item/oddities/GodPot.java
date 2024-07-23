package net.hypixel.skyblock.item.oddities;

import java.util.Iterator;
import org.bukkit.inventory.ItemStack;
import net.hypixel.skyblock.user.PlayerUtils;
import net.hypixel.skyblock.user.PlayerStatistics;
import org.bukkit.plugin.Plugin;
import net.hypixel.skyblock.SkyBlock;
import org.bukkit.Effect;
import org.bukkit.scheduler.BukkitRunnable;
import net.hypixel.skyblock.user.User;
import net.hypixel.skyblock.features.potion.PotionEffect;
import net.hypixel.skyblock.features.potion.PotionEffectType;
import java.util.ArrayList;
import net.hypixel.skyblock.util.SUtil;
import net.hypixel.skyblock.item.SItem;
import org.bukkit.entity.Player;
import net.hypixel.skyblock.util.Sputnik;
import net.hypixel.skyblock.item.GenericItemType;
import net.hypixel.skyblock.item.Rarity;
import net.hypixel.skyblock.item.Ability;
import net.hypixel.skyblock.item.MaterialFunction;
import net.hypixel.skyblock.item.SkullStatistics;

public class GodPot implements SkullStatistics, MaterialFunction, Ability
{
    private boolean bool;
    
    public GodPot() {
        this.bool = true;
    }
    
    @Override
    public String getURL() {
        return "60226d4c1d30fbebecae939da900603e4cd0fed8592a1bb3e11f9ac92391a45a";
    }
    
    @Override
    public String getDisplayName() {
        return "God Potion";
    }
    
    @Override
    public Rarity getRarity() {
        return Rarity.SPECIAL;
    }
    
    @Override
    public GenericItemType getType() {
        return GenericItemType.ITEM;
    }
    
    @Override
    public String getAbilityName() {
        return "Drink!";
    }
    
    @Override
    public String getAbilityDescription() {
        return Sputnik.trans("&7Consume this potion give the player &a24 hours &7of positive effects!");
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
        SUtil.delay(() -> this.bool = false, 35L);
        final ArrayList<PotionEffect> effects = (ArrayList<PotionEffect>)new ArrayList();
        effects.add((Object)new PotionEffect(PotionEffectType.FEROCITY, 3, 1728000L));
        effects.add((Object)new PotionEffect(PotionEffectType.ARCHERY, 4, 1728000L));
        effects.add((Object)new PotionEffect(PotionEffectType.CRITICAL, 6, 1728000L));
        effects.add((Object)new PotionEffect(PotionEffectType.STRENGTH, 8, 1728000L));
        if (!player.getWorld().getName().contains((CharSequence)"arena")) {
            effects.add((Object)new PotionEffect(PotionEffectType.JUMP_BOOST, 4, 1728000L));
            effects.add((Object)new PotionEffect(PotionEffectType.RABBIT, 6, 1728000L));
        }
        effects.add((Object)new PotionEffect(PotionEffectType.RESISTANCE, 8, 1728000L));
        effects.add((Object)new PotionEffect(PotionEffectType.SPEED, 8, 1728000L));
        effects.add((Object)new PotionEffect(PotionEffectType.HASTE, 4, 1728000L));
        effects.add((Object)new PotionEffect(PotionEffectType.SPIRIT, 4, 1728000L));
        effects.add((Object)new PotionEffect(PotionEffectType.TRUE_RESISTANCE, 4, 1728000L));
        effects.add((Object)new PotionEffect(PotionEffectType.NIGHT_VISION, 1, 1728000L));
        effects.add((Object)new PotionEffect(PotionEffectType.WATER_BREATH, 1, 1728000L));
        effects.add((Object)new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 1, 1728000L));
        effects.add((Object)new PotionEffect(PotionEffectType.STAMINA, 4, 1728000L));
        effects.add((Object)new PotionEffect(PotionEffectType.MAGIC_FIND, 5, 1728000L));
        final User user = User.getUser(player.getUniqueId());
        new BukkitRunnable() {
            public void run() {
                if (!GodPot.this.bool) {
                    player.getWorld().playEffect(player.getLocation().add(0.0, 0.5, 0.0), Effect.EXPLOSION_HUGE, 0);
                    player.getWorld().playEffect(player.getLocation().add(0.0, 0.5, 0.0), Effect.EXPLOSION_HUGE, 0);
                    player.sendMessage(Sputnik.trans("&a&lSIP! &eThe &cGod Potion &egrants you powers for &924 hours&e!"));
                    this.cancel();
                    return;
                }
                for (int i = 0; i < 100; ++i) {
                    final double r = SUtil.random(0.0, 1.0);
                    player.getWorld().playEffect(player.getLocation().add(0.0, r, 0.0), Effect.POTION_SWIRL, 0);
                }
            }
        }.runTaskTimer((Plugin)SkyBlock.getPlugin(), 0L, 1L);
        for (final PotionEffect effect : effects) {
            user.removePotionEffect(effect.getType());
            PlayerUtils.updatePotionEffects(user, (PlayerStatistics)PlayerUtils.STATISTICS_CACHE.get((Object)user.getUuid()));
            if (effect.getType().getOnDrink() != null) {
                effect.getType().getOnDrink().accept((Object)effect, (Object)player);
            }
            user.addPotionEffect(effect);
        }
        player.setItemInHand((ItemStack)null);
    }
}
