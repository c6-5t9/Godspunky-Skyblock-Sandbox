package net.hypixel.skyblock.api.placeholder;

import java.util.HashMap;
import net.hypixel.skyblock.features.potion.ActivePotionEffect;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import org.bukkit.inventory.ItemStack;
import net.hypixel.skyblock.item.pet.Pet;
import net.hypixel.skyblock.features.skill.Skill;
import net.hypixel.skyblock.features.skill.CombatSkill;
import net.md_5.bungee.api.ChatColor;
import net.hypixel.skyblock.SkyBlock;
import net.hypixel.skyblock.Repeater;
import org.apache.commons.codec.binary.Base64;
import net.hypixel.skyblock.item.SItem;
import net.hypixel.skyblock.util.Sputnik;
import net.hypixel.skyblock.util.SUtil;
import net.hypixel.skyblock.user.PlayerUtils;
import net.hypixel.skyblock.user.PlayerStatistics;
import net.hypixel.skyblock.user.User;
import org.bukkit.OfflinePlayer;
import java.util.UUID;
import java.util.Map;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;

public class SkyblockPlaceholder extends PlaceholderExpansion
{
    public static final Map<UUID, String> PTE_CACHE;
    
    public boolean canRegister() {
        return true;
    }
    
    public String getAuthor() {
        return "Hamza , Adarsh , EpicPortal";
    }
    
    public String getIdentifier() {
        return "skyblock";
    }
    
    public String getVersion() {
        return "0.1.6";
    }
    
    public String onRequest(final OfflinePlayer player, final String identifier) {
        final UUID uuid = player.getUniqueId();
        final User user = User.getUser(player.getUniqueId());
        final PlayerStatistics statistics = (PlayerStatistics)PlayerUtils.STATISTICS_CACHE.get((Object)uuid);
        double visualcap = statistics.getCritChance().addAll() * 100.0;
        if (visualcap > 100.0) {
            visualcap = 100.0;
        }
        if (player.isOnline()) {
            if (identifier.equals((Object)"defense")) {
                return SUtil.commaify(statistics.getDefense().addAll().intValue());
            }
            if (identifier.equals((Object)"strength")) {
                return SUtil.commaify(statistics.getStrength().addAll().intValue());
            }
            if (identifier.equals((Object)"speed")) {
                return String.valueOf(statistics.getSpeed().addAll() * 100.0);
            }
            if (identifier.equals((Object)"critchance")) {
                return String.valueOf((int)visualcap);
            }
            if (identifier.equals((Object)"critdamage")) {
                return String.valueOf(statistics.getCritDamage().addAll() * 100.0);
            }
            if (identifier.equals((Object)"int")) {
                return SUtil.commaify(statistics.getIntelligence().addAll().intValue());
            }
            if (identifier.equals((Object)"coins")) {
                return String.valueOf(user.getCoins());
            }
            if (identifier.equals((Object)"ferocity")) {
                return SUtil.commaify(statistics.getFerocity().addAll().intValue());
            }
            if (identifier.equals((Object)"atkSpeed")) {
                return String.valueOf((Object)SUtil.commaify(Math.min(100.0, (double)statistics.getAttackSpeed().addAll())));
            }
            if (identifier.equals((Object)"abilityDamage")) {
                return SUtil.commaify(statistics.getAbilityDamage().addAll().intValue());
            }
            if (identifier.equals((Object)"pet")) {
                return this.findPet(player);
            }
            if (identifier.equals((Object)"pet_lore")) {
                final StringBuilder sb = new StringBuilder();
                final Pet.PetItem pet = this.findPetClass(player);
                if (pet == null) {
                    return Sputnik.trans("&cNone");
                }
                final SItem item = SItem.of(pet.getType());
                item.setRarity(pet.getRarity());
                item.setDataDouble("xp", pet.getXp());
                item.getData().setBoolean("equipped", true);
                item.update();
                final ItemStack stacc = item.getStack();
                for (final String s : stacc.getItemMeta().getLore()) {
                    sb.append(s + "\n");
                }
                sb.append(item.getRarity().getBoldedColor() + item.getRarity().getDisplay());
                return sb.toString();
            }
            else if (identifier.equals((Object)"pet_texture")) {
                final Pet pet2 = this.findPetClassA(player);
                if (pet2 == null) {
                    return "Steve";
                }
                final String URL = "{\"textures\":{\"SKIN\":{\"url\":\"http://textures.minecraft.net/texture/" + pet2.getURL() + "\"}}}";
                final String encodedString = Base64.encodeBase64String(URL.getBytes());
                return encodedString;
            }
            else {
                if (identifier.equals((Object)"potion")) {
                    return this.getEffectLoop(player);
                }
                if (identifier.equals((Object)"hitshield")) {
                    return Repeater.get(player.getPlayer());
                }
                if (identifier.equals((Object)"cookie")) {
                    return PlayerUtils.getCookieDurationDisplay(player.getPlayer());
                }
                if (identifier.equals((Object)"server_name")) {
                    return SkyBlock.getPlugin().getServerName();
                }
                if (identifier.equals((Object)"server_date")) {
                    return SUtil.getDate();
                }
                if (identifier.equals((Object)"badge")) {
                    if (PlayerUtils.getCookieDurationTicks(player.getPlayer()) > 0L) {
                        return (Object)ChatColor.YELLOW + " \u272a";
                    }
                    return "";
                }
                else if (identifier.equals((Object)"cb_status")) {
                    if (PlayerUtils.getCookieDurationTicks(player.getPlayer()) > 0L) {
                        return SUtil.getFormattedTimeToDay(PlayerUtils.getCookieDurationTicks(player.getPlayer()));
                    }
                    return (Object)ChatColor.RED + "Not actived!";
                }
                else {
                    if (identifier.equals((Object)"combatlevel")) {
                        final Skill skill = CombatSkill.INSTANCE;
                        final double xp = (skill != null) ? user.getSkillXP(skill) : 0.0;
                        final int level = (skill != null) ? Skill.getLevel(xp, skill.hasSixtyLevels()) : 0;
                        return String.valueOf(level);
                    }
                    if (identifier.equals((Object)"theendlvl")) {
                        final Skill skill = CombatSkill.INSTANCE;
                        final double xp = (skill != null) ? user.getSkillXP(skill) : 0.0;
                        final int level = (skill != null) ? Skill.getLevel(xp, skill.hasSixtyLevels()) : 0;
                        if (level >= 5) {
                            return "true";
                        }
                        return "false";
                    }
                    else if (identifier.equals((Object)"dragonlvl")) {
                        final Skill skill = CombatSkill.INSTANCE;
                        final double xp = (skill != null) ? user.getSkillXP(skill) : 0.0;
                        final int level = (skill != null) ? Skill.getLevel(xp, skill.hasSixtyLevels()) : 0;
                        if (level >= 6) {
                            return "true";
                        }
                        return "false";
                    }
                }
            }
        }
        if (identifier.equals((Object)"info")) {
            return (Object)ChatColor.RED + "SKY" + (Object)ChatColor.GOLD + "SIM" + (Object)ChatColor.GREEN + " PLACEHOLDER v0.1.3 - POWERED BY PLACEHOLDERAPI";
        }
        return null;
    }
    
    public String findPet(final OfflinePlayer player) {
        final Pet.PetItem active = User.getUser(player.getUniqueId()).getActivePet();
        final Pet petclass = User.getUser(player.getUniqueId()).getActivePetClass();
        String displayname = Sputnik.trans("&cNone");
        if (active != null && petclass != null) {
            final int level = Pet.getLevel(active.getXp(), active.getRarity());
            displayname = Sputnik.trans("&7[Lvl " + level + "&7] " + (Object)active.toItem().getRarity().getColor() + petclass.getDisplayName());
        }
        return displayname;
    }
    
    public Pet.PetItem findPetClass(final OfflinePlayer player) {
        final Pet.PetItem active = User.getUser(player.getUniqueId()).getActivePet();
        final Pet petclass = User.getUser(player.getUniqueId()).getActivePetClass();
        final String displayname = Sputnik.trans("&cNone");
        if (active != null && petclass != null) {
            return active;
        }
        return null;
    }
    
    public Pet findPetClassA(final OfflinePlayer player) {
        final Pet.PetItem active = User.getUser(player.getUniqueId()).getActivePet();
        final Pet petclass = User.getUser(player.getUniqueId()).getActivePetClass();
        final String displayname = Sputnik.trans("&cNone");
        if (active != null && petclass != null) {
            return petclass;
        }
        return null;
    }
    
    public String getEffectLoop(final OfflinePlayer player) {
        String returnString = Sputnik.trans(" &7No active effects. Drink Potions or splash \nthem to the ground to buff yourself.");
        final User user = User.getUser(player.getUniqueId());
        List<ActivePotionEffect> pte = (List<ActivePotionEffect>)new ArrayList();
        if (user != null) {
            pte = user.getEffects();
            if (user.getEffects().size() > 0) {
                returnString = Sputnik.trans(" &7You have &6" + user.getEffects().size() + " &7effects. Use \"&6/potions&7\" to see them ") + "\n" + Sputnik.trans(this.a(user, pte));
            }
        }
        return returnString;
    }
    
    public String a(final User user, final List<ActivePotionEffect> pte) {
        final ActivePotionEffect effect = (ActivePotionEffect)pte.get(Math.min(pte.size(), (int)Repeater.PTN_CACHE.get((Object)user.getUuid())));
        SkyblockPlaceholder.PTE_CACHE.put((Object)user.getUuid(), (Object)(effect.getEffect().getType().getName() + " " + SUtil.toRomanNumeral(effect.getEffect().getLevel()) + " " + (Object)ChatColor.WHITE + effect.getRemainingDisplay()));
        if (SkyblockPlaceholder.PTE_CACHE.containsKey((Object)user.getUuid())) {
            return (String)SkyblockPlaceholder.PTE_CACHE.get((Object)user.getUuid());
        }
        return "";
    }
    
    static {
        PTE_CACHE = (Map)new HashMap();
    }
}
