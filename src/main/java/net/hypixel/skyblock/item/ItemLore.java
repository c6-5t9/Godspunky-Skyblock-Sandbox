package net.hypixel.skyblock.item;

import net.hypixel.skyblock.features.dungeons.stats.ItemSerial;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.hypixel.skyblock.item.orb.OrbBuff;
import net.hypixel.skyblock.item.armor.ArmorSet;
import java.util.Iterator;
import net.hypixel.skyblock.features.slayer.SlayerBossType;
import java.util.Collection;
import net.hypixel.skyblock.features.enchantment.Enchantment;
import org.bukkit.ChatColor;
import net.hypixel.skyblock.features.enchantment.EnchantmentType;
import net.hypixel.skyblock.util.Sputnik;
import net.hypixel.skyblock.util.SUtil;
import org.bukkit.Bukkit;
import java.util.UUID;
import net.hypixel.skyblock.features.reforge.Reforge;
import java.util.ArrayList;
import java.util.List;
import net.hypixel.skyblock.user.User;
import org.bukkit.entity.Player;

public class ItemLore
{
    private static final String SSE_ID;
    private final SItem parent;
    private Player player;
    private User user;
    
    public ItemLore(final SItem parent) {
        this.player = null;
        this.user = null;
        this.parent = parent;
    }
    
    public List<String> asBukkitLore() {
        final String baseMgD = "0";
        final List<String> lore = (List<String>)new ArrayList();
        final List<Enchantment> enchantments = this.parent.getEnchantments();
        final SMaterial material = this.parent.getType();
        final MaterialStatistics statistics = material.getStatistics();
        final Reforge reforge = (this.parent.getReforge() == null) ? Reforge.blank() : this.parent.getReforge();
        try {
            this.player = Bukkit.getPlayer(UUID.fromString(this.parent.getDataString("owner")));
        }
        catch (final IllegalArgumentException ex) {}
        if (this.player != null) {
            this.user = User.getUser(this.player.getUniqueId());
        }
        if (statistics instanceof PlayerBoostStatistics) {
            final PlayerBoostStatistics playerBoostStatistics = (PlayerBoostStatistics)material.getStatistics();
            String hpb1 = "";
            String hpb2 = "";
            String hpb3 = "";
            int finalhpb1 = 0;
            int finalhpb2 = 0;
            int finalhpb3 = 0;
            long grantedHP = 0L;
            long grantedDEF = 0L;
            long grantedATKDmg = 0L;
            if (this.parent.getDataInt("hpb") > 0) {
                if (this.parent.getType().getStatistics().getType() == GenericItemType.WEAPON || this.parent.getType().getStatistics().getType() == GenericItemType.RANGED_WEAPON) {
                    finalhpb1 = this.parent.getDataInt("hpb") * 2;
                    hpb1 = Sputnik.trans(" &e(+" + SUtil.commaify(finalhpb1) + ")");
                }
                else if (this.parent.getType().getStatistics().getType() == GenericItemType.ARMOR) {
                    finalhpb2 = this.parent.getDataInt("hpb") * 2;
                    finalhpb3 = this.parent.getDataInt("hpb") * 4;
                    hpb2 = Sputnik.trans(" &e(+" + SUtil.commaify(finalhpb2) + ")");
                    hpb3 = Sputnik.trans(" &e(+" + SUtil.commaify(finalhpb3) + " HP)");
                }
            }
            if (this.parent.getType().getStatistics().getType() == GenericItemType.WEAPON && this.parent.getEnchantment(EnchantmentType.ONE_FOR_ALL) != null) {
                final Enchantment e = this.parent.getEnchantment(EnchantmentType.ONE_FOR_ALL);
                grantedATKDmg = playerBoostStatistics.getBaseDamage() * (e.getLevel() * 210L) / 100L;
            }
            if (this.parent.getType().getStatistics().getType() == GenericItemType.ARMOR) {
                final Enchantment growth = this.parent.getEnchantment(EnchantmentType.GROWTH);
                final Enchantment defense = this.parent.getEnchantment(EnchantmentType.PROTECTION);
                if (growth != null) {
                    grantedHP = Math.round(growth.getLevel() * 15.0);
                }
                if (defense != null) {
                    grantedDEF = Math.round(defense.getLevel() * 3.0);
                }
            }
            final List<String> KillBonusLore = this.parent.getType().getStatistics().killReplacementLore();
            if (KillBonusLore != null) {
                for (final String line : KillBonusLore) {
                    final String line2 = line.replaceAll("<SKYBLOCK_BONUS_DEFENSE>", String.valueOf(this.parent.getBonusDefense())).replaceAll("<SKYBLOCK_NEXT_DEFENSE>", String.valueOf(this.parent.getNextDefense())).replaceAll("<SKYBLOCK_CURRENT_KILLS>", String.valueOf(this.parent.getProgressKills())).replaceAll("<SKYBLOCK_REQUIRED_KILLS>", String.valueOf(this.parent.getRequiredKills()));
                    lore.add((Object)SUtil.color((Object)ChatColor.GRAY + line2));
                }
                if (KillBonusLore.size() != 0) {
                    lore.add((Object)"");
                }
            }
            if (this.parent.getType() == SMaterial.DARK_GOGGLES) {
                lore.add((Object)((Object)ChatColor.GRAY + "Ability Damage: " + (Object)ChatColor.RED + "+25%"));
                lore.add((Object)"");
            }
            if (this.parent.getType() == SMaterial.SHADOW_GOGGLES) {
                lore.add((Object)((Object)ChatColor.GRAY + "Ability Damage: " + (Object)ChatColor.RED + "+35%"));
                lore.add((Object)"");
            }
            if (this.parent.getType() == SMaterial.WITHER_GOGGLES) {
                lore.add((Object)((Object)ChatColor.GRAY + "Ability Damage: " + (Object)ChatColor.RED + "+45%"));
                lore.add((Object)"");
            }
            boolean damage;
            if (this.parent.getType() == SMaterial.EMERALD_BLADE) {
                if (this.user != null) {
                    final long cap = 35000000000L;
                    final double d1 = Math.pow((double)Math.min(35000000000L, this.user.getCoins()), 0.25);
                    final double finald = 2.5 * d1;
                    if (finald != 0.0) {
                        damage = this.addPossiblePropertyInt("Damage", playerBoostStatistics.getBaseDamage() + finald + finalhpb1 + grantedATKDmg, hpb1, false, lore);
                    }
                    else {
                        damage = this.addPossiblePropertyInt("Damage", (double)(playerBoostStatistics.getBaseDamage() + finalhpb1 + grantedATKDmg), hpb1, false, lore);
                    }
                }
                else {
                    damage = this.addPossiblePropertyInt("Damage", (double)(playerBoostStatistics.getBaseDamage() + finalhpb1 + grantedATKDmg), hpb1, false, lore);
                }
            }
            else {
                damage = this.addPossiblePropertyInt("Damage", (double)(playerBoostStatistics.getBaseDamage() + finalhpb1 + grantedATKDmg), hpb1, false, lore);
            }
            final boolean strength = this.addPossiblePropertyInt("Strength", playerBoostStatistics.getBaseStrength() + finalhpb1, SUtil.blackMagic(reforge.getStrength().getForRarity(this.parent.getRarity())), hpb1, false, lore);
            final boolean critChance = this.addPossiblePropertyInt("Crit Chance", (int)(playerBoostStatistics.getBaseCritChance() * 100.0), (int)(reforge.getCritChance().getForRarity(this.parent.getRarity()) * 100.0), "%", false, lore);
            final boolean critDamage = this.addPossiblePropertyInt("Crit Damage", (int)(playerBoostStatistics.getBaseCritDamage() * 100.0), (int)(reforge.getCritDamage().getForRarity(this.parent.getRarity()) * 100.0), "%", false, lore);
            final boolean atkSpeed = this.addPossiblePropertyIntAtkSpeed("Bonus Attack Speed", (int)Math.min(100.0, playerBoostStatistics.getBaseAttackSpeed()), (int)Math.round((double)reforge.getAttackSpeed().getForRarity(this.parent.getRarity())), "%", false, lore);
            if (damage || strength || critChance || critDamage || atkSpeed) {
                lore.add((Object)"");
            }
            final boolean health = this.addPossiblePropertyInt("Health", playerBoostStatistics.getBaseHealth() + finalhpb3 + grantedHP, " HP" + hpb3, true, lore);
            final boolean defense2 = this.addPossiblePropertyInt("Defense", playerBoostStatistics.getBaseDefense() + finalhpb2 + grantedDEF, hpb2, true, lore);
            final boolean speed = this.addPossiblePropertyInt("Speed", (int)(playerBoostStatistics.getBaseSpeed() * 100.0), "", true, lore);
            final boolean intelligence = this.addPossiblePropertyInt("Intelligence", playerBoostStatistics.getBaseIntelligence(), SUtil.blackMagic(reforge.getIntelligence().getForRarity(this.parent.getRarity())), "", true, lore);
            final boolean magicFind = this.addPossiblePropertyInt("Magic Find", (int)(playerBoostStatistics.getBaseMagicFind() * 100.0), "", true, lore);
            final boolean ferocity = this.addPossiblePropertyInt("Ferocity", playerBoostStatistics.getBaseFerocity(), SUtil.blackMagic(reforge.getFerocity().getForRarity(this.parent.getRarity())), "", true, lore);
            if (health || defense2 || speed || intelligence || magicFind || ferocity) {
                lore.add((Object)"");
            }
        }
        if (enchantments != null && enchantments.size() != 0) {
            final int amount = enchantments.size();
            final List<String> stringEnchantments = (List<String>)new ArrayList();
            final List<Enchantment> filteredList_ultimate_a = (List<Enchantment>)new ArrayList();
            final List<Enchantment> filteredList_normal_a = (List<Enchantment>)new ArrayList();
            for (final Enchantment enchantment : enchantments) {
                if (enchantment.getDisplayName().contains((CharSequence)ChatColor.LIGHT_PURPLE.toString())) {
                    filteredList_ultimate_a.add((Object)enchantment);
                }
                else {
                    filteredList_normal_a.add((Object)enchantment);
                }
            }
            filteredList_ultimate_a.addAll((Collection)filteredList_normal_a);
            for (final Enchantment enchantment : filteredList_ultimate_a) {
                stringEnchantments.add((Object)enchantment.getDisplayName());
            }
            if (amount <= 5) {
                final List<Enchantment> filteredList_ultimate = (List<Enchantment>)new ArrayList();
                final List<Enchantment> filteredList_normal = (List<Enchantment>)new ArrayList();
                for (final Enchantment enchantment2 : enchantments) {
                    if (enchantment2.getDisplayName().contains((CharSequence)ChatColor.LIGHT_PURPLE.toString())) {
                        filteredList_ultimate.add((Object)enchantment2);
                    }
                    else {
                        filteredList_normal.add((Object)enchantment2);
                    }
                }
                for (final Enchantment enchantment2 : filteredList_ultimate) {
                    lore.add((Object)enchantment2.getDisplayName());
                    for (final String line3 : SUtil.splitByWordAndLength(enchantment2.getDescription(), 30, "\\s")) {
                        lore.add((Object)((Object)ChatColor.GRAY + line3));
                    }
                }
                for (final Enchantment enchantment2 : filteredList_normal) {
                    lore.add((Object)enchantment2.getDisplayName());
                    for (final String line3 : SUtil.splitByWordAndLength(enchantment2.getDescription(), 30, "\\s")) {
                        lore.add((Object)((Object)ChatColor.GRAY + line3));
                    }
                }
            }
            else if (amount <= 10) {
                final List<Enchantment> filteredList_ultimate = (List<Enchantment>)new ArrayList();
                final List<Enchantment> filteredList_normal = (List<Enchantment>)new ArrayList();
                for (final Enchantment enchantment2 : enchantments) {
                    if (enchantment2.getDisplayName().contains((CharSequence)ChatColor.LIGHT_PURPLE.toString())) {
                        filteredList_ultimate.add((Object)enchantment2);
                    }
                    else {
                        filteredList_normal.add((Object)enchantment2);
                    }
                }
                filteredList_ultimate.addAll((Collection)filteredList_normal);
                for (final Enchantment enchantment2 : filteredList_ultimate) {
                    lore.add((Object)enchantment2.getDisplayName());
                }
            }
            else if (amount <= 25) {
                lore.addAll((Collection)SUtil.combineElements(stringEnchantments, ", ", 2));
            }
            else {
                lore.addAll((Collection)SUtil.combineElements(stringEnchantments, ", ", 3));
            }
            lore.add((Object)"");
        }
        final ArmorSet set = SMaterial.findArmorSet(material);
        if (set != null) {
            lore.add((Object)((Object)ChatColor.GOLD + "Full Set Bonus: " + set.getName()));
            for (final String line4 : SUtil.splitByWordAndLength(set.getDescription(), 30, "\\s")) {
                lore.add((Object)((Object)ChatColor.GRAY + line4));
            }
            lore.add((Object)"");
        }
        if (this.parent.getType() == SMaterial.JUJU_SHORTBOW) {
            lore.add((Object)((Object)ChatColor.DARK_PURPLE + "Shortbow: Instantly Shoots"));
            lore.add((Object)((Object)ChatColor.GRAY + "Hits " + (Object)ChatColor.RED + "3 " + (Object)ChatColor.GRAY + "mobs on impact."));
            lore.add((Object)((Object)ChatColor.GRAY + "Can damage Endermen."));
            lore.add((Object)"");
        }
        if (this.parent.getType() == SMaterial.TERMINATOR) {
            lore.add((Object)((Object)ChatColor.GOLD + "Shortbow: Instantly Shoots"));
            lore.add((Object)Sputnik.trans("&7Shoots &b3 &7arrows at once."));
            lore.add((Object)((Object)ChatColor.GRAY + "Can damage Endermen."));
            lore.add((Object)"");
            lore.add((Object)Sputnik.trans("&cDivides your &9\u2623 Crit Chance &cby 4!"));
            lore.add((Object)"");
        }
        final Ability ability = material.getAbility();
        if (ability != null) {
            final StringBuilder abilityTitle = new StringBuilder().append((Object)ChatColor.GOLD).append("Ability: ").append(ability.getAbilityName());
            switch (ability.getAbilityActivation()) {
                case RIGHT_CLICK: {
                    abilityTitle.append(" ").append((Object)ChatColor.YELLOW).append((Object)ChatColor.BOLD).append("RIGHT CLICK");
                    break;
                }
                case LEFT_CLICK: {
                    abilityTitle.append(" ").append((Object)ChatColor.YELLOW).append((Object)ChatColor.BOLD).append("LEFT CLICK");
                    break;
                }
                case SNEAK: {
                    abilityTitle.append(" ").append((Object)ChatColor.YELLOW).append((Object)ChatColor.BOLD).append("SNEAK");
                    break;
                }
            }
            if (this.parent.getType() != SMaterial.GOD_POT && this.parent.getType() != SMaterial.HIDDEN_BOOSTER_COOKIE) {
                lore.add((Object)abilityTitle.toString());
            }
            for (final String line5 : SUtil.splitByWordAndLength(ability.getAbilityDescription(), 35, "\\s")) {
                lore.add((Object)((Object)ChatColor.GRAY + line5));
            }
            if (this.parent.getType() == SMaterial.HIDDEN_BOOSTER_COOKIE) {
                lore.add((Object)" ");
                lore.add((Object)Sputnik.trans("&8\u25b6 &b+35% &7Bonus Combat XP"));
                lore.add((Object)Sputnik.trans("&8\u25b6 &b+30\u272f &7Bonus Magic Find"));
                lore.add((Object)Sputnik.trans("&8\u25b6 &c+100\u2741 &7Bonus Strength"));
                lore.add((Object)Sputnik.trans("&8\u25b6 &a+200\u2748 &7Bonus Defense"));
                lore.add((Object)Sputnik.trans("&8\u25b6 &9+25\u2620 &7Bonus Crit Damage"));
                lore.add((Object)Sputnik.trans("&8\u25b6 &c+35\u2afd &7Bonus Ferocity"));
                lore.add((Object)Sputnik.trans("&8\u25b6 &b+2000\u270e &7Bonus Intelligence"));
                lore.add((Object)Sputnik.trans("&8\u25b6 &7Keep &6coins &7and &deffects &7on death"));
                lore.add((Object)Sputnik.trans("&8\u25b6 &7Access to &6/auh &7and &6/fm"));
                lore.add((Object)Sputnik.trans("&8\u25b6 &7Access to &6/av &7and &6/bin &7(Trash Bin)"));
                lore.add((Object)Sputnik.trans("&8\u25b6 &7A shiny &e\u272a &6Badge &7on your &aname tag."));
            }
            if (this.parent.getType() == SMaterial.WEIRD_TUBA) {
                lore.add((Object)Sputnik.trans("&c+30\u2741 Strength"));
                lore.add((Object)Sputnik.trans("&f+30\u2726 Speed"));
                lore.add((Object)Sputnik.trans("&7for &a20 &7seconds."));
                lore.add((Object)((Object)ChatColor.DARK_GRAY + "Buff doesn't stack."));
            }
            if (this.parent.getType() == SMaterial.EDIBLE_MACE) {
                lore.add((Object)((Object)ChatColor.DARK_GRAY + "Debuff doesn't stack."));
                lore.add((Object)((Object)ChatColor.DARK_GRAY + "Mana Cost: " + (Object)ChatColor.DARK_AQUA + "100"));
            }
            if (this.parent.getType() == SMaterial.ZOMBIE_SWORD_T2) {
                lore.add((Object)((Object)ChatColor.DARK_GRAY + "Mana Cost: " + (Object)ChatColor.DARK_AQUA + "70"));
                lore.add((Object)ChatColor.translateAlternateColorCodes('&', "&8Charges: &e5 &8/ &a15s"));
            }
            if (this.parent.getType() == SMaterial.GOD_POT) {
                lore.add((Object)" ");
                lore.add((Object)Sputnik.trans("&eRight-click to consume!"));
            }
            if (this.parent.getType() == SMaterial.TERMINATOR) {
                lore.add((Object)((Object)ChatColor.DARK_GRAY + "Mana Cost: " + (Object)ChatColor.DARK_AQUA + "100"));
                lore.add((Object)((Object)ChatColor.DARK_GRAY + "Cooldown: " + (Object)ChatColor.GREEN + "1s"));
            }
            if (this.parent.getType() == SMaterial.ZOMBIE_SWORD_T3) {
                lore.add((Object)((Object)ChatColor.DARK_GRAY + "Mana Cost: " + (Object)ChatColor.DARK_AQUA + "70"));
                lore.add((Object)ChatColor.translateAlternateColorCodes('&', "&8Charges: &e5 &8/ &a15s"));
            }
            if (this.parent.getType() == SMaterial.AXE_OF_THE_SHREDDED) {
                lore.add((Object)((Object)ChatColor.DARK_GRAY + "Mana Cost: " + (Object)ChatColor.DARK_AQUA + "20"));
            }
            if (ability.getManaCost() > 0) {
                lore.add((Object)((Object)ChatColor.DARK_GRAY + "Mana Cost: " + (Object)ChatColor.DARK_AQUA + ability.getManaCost()));
            }
            if (ability.getManaCost() == -1) {
                lore.add((Object)((Object)ChatColor.DARK_GRAY + "Mana Cost: " + (Object)ChatColor.DARK_AQUA + "All"));
            }
            if (ability.getAbilityCooldownTicks() >= 20 && ability.getAbilityCooldownTicks() <= 1200) {
                lore.add((Object)((Object)ChatColor.DARK_GRAY + "Cooldown: " + (Object)ChatColor.GREEN + ability.getAbilityCooldownTicks() / 20 + "s"));
            }
            else if (ability.getAbilityCooldownTicks() > 1200) {
                lore.add((Object)((Object)ChatColor.DARK_GRAY + "Cooldown: " + (Object)ChatColor.GREEN + ability.getAbilityCooldownTicks() / 20 / 60 + "m"));
            }
            lore.add((Object)"");
        }
        if (this.parent.getDataString("etherwarp_trans").equals((Object)"true")) {
            lore.add((Object)Sputnik.trans("&6Ability: Ether Transmission &e&lSNEAK RIGHT CLICK"));
            lore.add((Object)Sputnik.trans("&7Teleport to your targetted block"));
            lore.add((Object)Sputnik.trans("&7up to &a57 blocks &7away."));
            lore.add((Object)((Object)ChatColor.DARK_GRAY + "Mana Cost: " + (Object)ChatColor.DARK_AQUA + "250"));
            lore.add((Object)"");
        }
        final OrbBuff buff = material.getOrbBuff();
        if (buff != null) {
            lore.add((Object)((Object)this.parent.getRarity().getColor() + "Orb Buff: " + buff.getBuffName()));
            for (final String line5 : SUtil.splitByWordAndLength(buff.getBuffDescription(), 30, "\\s")) {
                lore.add((Object)((Object)ChatColor.GRAY + line5));
            }
            lore.add((Object)"");
        }
        final int kills = this.parent.getDataInt("kills");
        final double coins = this.parent.getDataInt("coins");
        if (statistics.displayKills()) {
            lore.add((Object)((Object)ChatColor.GOLD + "Kills: " + (Object)ChatColor.AQUA + kills));
            lore.add((Object)"");
        }
        if (statistics.displayCoins()) {
            lore.add((Object)((Object)ChatColor.GRAY + "Price paid: " + (Object)ChatColor.GOLD + SUtil.commaify(coins)));
            lore.add((Object)"");
        }
        if (this.parent.getType() == SMaterial.MIDAS_STAFF) {
            lore.add((Object)Sputnik.trans("&6Ability: Greed"));
        }
        if (this.parent.getType() == SMaterial.ENCHANTED_BOOK && this.parent.getEnchantments() != null && Enchantment.ultimateEnchantsListFromList(this.parent.getEnchantments()).size() > 0) {
            lore.add((Object)Sputnik.trans("&cYou can only have 1 Ultimate"));
            lore.add((Object)Sputnik.trans("&cEnchantment on an item!"));
            lore.add((Object)" ");
        }
        final String l = this.parent.getType().getStatistics().getLore();
        if (l != null) {
            for (final String string : SUtil.splitByWordAndLength(l, 30, "\\s")) {
                lore.add((Object)((Object)ChatColor.GRAY + string));
            }
            if (l.length() != 0) {
                lore.add((Object)"");
            }
        }
        if (this.parent.getType() == SMaterial.HIDDEN_DONATOR_HELMET) {
            final String p = this.parent.getDataString("p_given");
            if (Bukkit.getPlayer(p) != null) {
                lore.add((Object)(Sputnik.trans("&7From: ") + Bukkit.getPlayer(p).getDisplayName()));
            }
        }
        if (this.parent.getType() == SMaterial.HIDDEN_DONATOR_HELMET) {
            final String p = this.parent.getDataString("p_rcv");
            if (Bukkit.getPlayer(p) != null) {
                lore.add((Object)(Sputnik.trans("&7To: ") + Bukkit.getPlayer(p).getDisplayName()));
            }
        }
        if (this.parent.getType() == SMaterial.HIDDEN_DONATOR_HELMET) {
            final String bf = this.parent.getDataString("lore_d");
            final String a = bf.replaceAll("<>", " ");
            if (a != null && a != "null") {
                lore.add((Object)"");
                for (final String string2 : SUtil.splitByWordAndLength(a, 25, "\\s")) {
                    lore.add((Object)Sputnik.trans("&7" + string2));
                }
                if (l.length() != 0) {
                    lore.add((Object)"");
                }
            }
        }
        final List<String> ll = this.parent.getType().getStatistics().getListLore();
        if (ll != null) {
            for (final String line3 : ll) {
                lore.add((Object)((Object)ChatColor.GRAY + line3));
            }
            if (ll.size() != 0) {
                lore.add((Object)"");
            }
        }
        if (statistics.displayCoins()) {
            lore.add((Object)((Object)ChatColor.GRAY + "Price paid: " + (Object)ChatColor.GOLD + SUtil.commaify(coins)));
            lore.add((Object)"");
        }
        if (this.parent.getType() == SMaterial.MIDAS_STAFF) {
            lore.add((Object)Sputnik.trans("&7Price paid: &6100,000,000"));
            lore.add((Object)Sputnik.trans("&7Base Ability Damage Bonus: &326000"));
            lore.add((Object)"");
        }
        if (this.parent.getDataInt("anvil") != 0) {
            lore.add((Object)((Object)ChatColor.GRAY + "Anvil Uses: " + (Object)ChatColor.RED + this.parent.getDataInt("anvil")));
        }
        if (material.getItemData() != null) {
            final NBTTagCompound compound = this.parent.getData();
            for (final String key : compound.c()) {
                final List<String> dl = material.getItemData().getDataLore(key, SUtil.getObjectFromCompound(this.parent.getData(), key));
                if (dl != null) {
                    lore.addAll((Collection)dl);
                    lore.add((Object)"");
                }
            }
        }
        if (this.parent.isReforgable() && !this.parent.isReforged()) {
            lore.add((Object)Sputnik.trans("&8This item can be reforged!"));
        }
        if (this.user != null && SlayerBossType.SlayerMobType.ENDERMAN.getLevelForXP(this.user.getEndermanSlayerXP()) < 6) {
            lore.add((Object)Sputnik.trans("&4\u2620 &cRequires &5Enderman Slayer 6."));
        }
        final SpecificItemType type = statistics.getSpecificType();
        if (statistics.displayRarity()) {
            String s = "";
            if (this.parent.getDataBoolean("dungeons_item")) {
                s = "DUNGEON ";
            }
            lore.add((Object)((this.parent.isRecombobulated() ? (this.parent.getRarity().getBoldedColor() + (Object)ChatColor.MAGIC + "D" + (Object)ChatColor.RESET + " ") : "") + this.parent.getRarity().getDisplay() + ((type != SpecificItemType.NONE) ? (" " + s + type.getName()) : "") + (this.parent.isRecombobulated() ? (this.parent.getRarity().getBoldedColor() + " " + (Object)ChatColor.MAGIC + "D" + (Object)ChatColor.RESET) : "")));
        }
        return lore;
    }
    
    private boolean addPossiblePropertyInt(final String name, double i, final int r, final String succeeding, final boolean green, final List<String> list) {
        if (this.player == null) {
            i += r;
            i += this.getBoostStats(this.parent, name);
            if (i == 0.0) {
                return false;
            }
            final StringBuilder builder = new StringBuilder();
            builder.append((Object)ChatColor.GRAY).append(name).append(": ").append((Object)(green ? ChatColor.GREEN : ChatColor.RED)).append((i < 0.0) ? "" : "+").append(SUtil.commaify(Math.round(i))).append(succeeding);
            if (r != 0) {
                builder.append((Object)ChatColor.BLUE).append(" (").append((r < 0) ? "" : "+").append(r).append(")");
            }
            list.add((Object)builder.toString());
            return true;
        }
        else if (this.player.getWorld().getName().contains((CharSequence)"f6") || this.player.getWorld().getName().contains((CharSequence)"dungeon")) {
            i += r;
            i += this.getBoostStats(this.parent, name);
            if (i == 0.0) {
                return false;
            }
            final StringBuilder builder = new StringBuilder();
            builder.append((Object)ChatColor.GRAY).append(name).append(": ").append((Object)(green ? ChatColor.GREEN : ChatColor.RED)).append((i < 0.0) ? "" : "+").append(SUtil.commaify(Math.round(i))).append(succeeding);
            if (r != 0) {
                builder.append((Object)ChatColor.BLUE).append(" (").append((r < 0) ? "" : "+").append(r).append(")");
            }
            list.add((Object)builder.toString());
            return true;
        }
        else {
            i += r;
            if (i == 0.0) {
                return false;
            }
            final StringBuilder builder = new StringBuilder();
            builder.append((Object)ChatColor.GRAY).append(name).append(": ").append((Object)(green ? ChatColor.GREEN : ChatColor.RED)).append((i < 0.0) ? "" : "+").append(SUtil.commaify(Math.round(i))).append(succeeding);
            if (r != 0) {
                builder.append((Object)ChatColor.BLUE).append(" (").append((r < 0) ? "" : "+").append(r).append(")");
            }
            builder.append(" " + this.getBoostLore(this.parent, i, name));
            list.add((Object)builder.toString());
            return true;
        }
    }
    
    private String getBoostLore(final SItem sitem, final double baseDamagem, final String stats) {
        if (sitem != null) {
            final ItemSerial is = ItemSerial.getItemBoostStatistics(sitem);
            double amount = 0.0;
            String suffix = "";
            if (stats.contains((CharSequence)"Damage") && !stats.contains((CharSequence)"Crit")) {
                amount = is.getDamage();
            }
            else if (stats.contains((CharSequence)"Strength")) {
                amount = is.getStrength();
            }
            else if (stats.contains((CharSequence)"Chance")) {
                amount = is.getCritchance() * 100.0;
                suffix = "%";
            }
            else if (stats.contains((CharSequence)"Crit") && stats.contains((CharSequence)"Damage")) {
                amount = is.getCritdamage() * 100.0;
                suffix = "%";
            }
            else if (stats.contains((CharSequence)"Ferocity")) {
                amount = is.getFerocity();
            }
            else if (stats.contains((CharSequence)"Intelligence")) {
                amount = is.getIntelligence();
            }
            else if (stats.contains((CharSequence)"Health")) {
                amount = is.getHealth();
            }
            else if (stats.contains((CharSequence)"Defense")) {
                amount = is.getDefense();
            }
            else if (stats.contains((CharSequence)"Magic")) {
                amount = is.getMagicFind() * 100.0;
            }
            else if (stats.contains((CharSequence)"Bonus")) {
                amount = is.getAtkSpeed();
                suffix = "%";
            }
            else if (stats.contains((CharSequence)"Speed")) {
                amount = is.getSpeed();
            }
            if (sitem.getDataBoolean("dungeons_item")) {
                amount += baseDamagem;
            }
            if (amount > 0.0) {
                if (amount - Math.round(amount) > 0.1) {
                    return (Object)ChatColor.DARK_GRAY + "(+" + SUtil.commaify(amount) + suffix + ")";
                }
                return (Object)ChatColor.DARK_GRAY + "(+" + SUtil.commaify(Math.round(amount)) + suffix + ")";
            }
        }
        return "";
    }
    
    private double getBoostStats(final SItem sitem, final String stats) {
        if (sitem != null) {
            final ItemSerial is = ItemSerial.getItemBoostStatistics(sitem);
            double amount = 0.0;
            String suffix = "";
            if (stats.contains((CharSequence)"Damage") && !stats.contains((CharSequence)"Crit")) {
                amount = is.getDamage();
            }
            else if (stats.contains((CharSequence)"Strength")) {
                amount = is.getStrength();
            }
            else if (stats.contains((CharSequence)"Chance")) {
                amount = is.getCritchance() * 100.0;
                suffix = "%";
            }
            else if (stats.contains((CharSequence)"Crit") && stats.contains((CharSequence)"Damage")) {
                amount = is.getCritdamage() * 100.0;
                suffix = "%";
            }
            else if (stats.contains((CharSequence)"Ferocity")) {
                amount = is.getFerocity();
            }
            else if (stats.contains((CharSequence)"Intelligence")) {
                amount = is.getIntelligence();
            }
            else if (stats.contains((CharSequence)"Health")) {
                amount = is.getHealth();
            }
            else if (stats.contains((CharSequence)"Defense")) {
                amount = is.getDefense();
            }
            else if (stats.contains((CharSequence)"Magic")) {
                amount = is.getMagicFind() * 100.0;
            }
            else if (stats.contains((CharSequence)"Bonus")) {
                amount = is.getAtkSpeed();
                suffix = "%";
            }
            else if (stats.contains((CharSequence)"Speed")) {
                amount = is.getSpeed();
            }
            return amount;
        }
        return 0.0;
    }
    
    private boolean addPossiblePropertyIntAtkSpeed(final String name, double i, final int r, final String succeeding, final boolean green, final List<String> list) {
        if (this.player == null) {
            i += r;
            if (i == 0.0) {
                return false;
            }
            final StringBuilder builder = new StringBuilder();
            builder.append((Object)ChatColor.GRAY).append(name).append(": ").append((Object)(green ? ChatColor.GREEN : ChatColor.RED)).append((i < 0.0) ? "" : "+").append(SUtil.commaify(Math.round(Math.min(100.0, i)))).append(succeeding);
            if (r != 0) {
                builder.append((Object)ChatColor.BLUE).append(" (").append((r < 0) ? "" : "+").append(r).append(succeeding).append(")");
            }
            builder.append(" " + this.getBoostLore(this.parent, i, name));
            list.add((Object)builder.toString());
            return true;
        }
        else if (this.player.getWorld().getName().contains((CharSequence)"f6") || this.player.getWorld().getName().contains((CharSequence)"dungeon")) {
            i += r;
            i += this.getBoostStats(this.parent, name);
            if (i == 0.0) {
                return false;
            }
            final StringBuilder builder = new StringBuilder();
            builder.append((Object)ChatColor.GRAY).append(name).append(": ").append((Object)(green ? ChatColor.GREEN : ChatColor.RED)).append((i < 0.0) ? "" : "+").append(SUtil.commaify(Math.round(Math.min(100.0, i)))).append(succeeding);
            if (r != 0) {
                builder.append((Object)ChatColor.BLUE).append(" (").append((r < 0) ? "" : "+").append(r).append(succeeding).append(")");
            }
            list.add((Object)builder.toString());
            return true;
        }
        else {
            i += r;
            if (i == 0.0) {
                return false;
            }
            final StringBuilder builder = new StringBuilder();
            builder.append((Object)ChatColor.GRAY).append(name).append(": ").append((Object)(green ? ChatColor.GREEN : ChatColor.RED)).append((i < 0.0) ? "" : "+").append(SUtil.commaify(Math.round(Math.min(100.0, i)))).append(succeeding);
            if (r != 0) {
                builder.append((Object)ChatColor.BLUE).append(" (").append((r < 0) ? "" : "+").append(r).append(succeeding).append(")");
            }
            builder.append(" " + this.getBoostLore(this.parent, i, name));
            list.add((Object)builder.toString());
            return true;
        }
    }
    
    private boolean addPossiblePropertyInt(final String name, final double i, final String succeeding, final boolean green, final List<String> list) {
        return this.addPossiblePropertyInt(name, i, 0, succeeding, green, list);
    }
    
    private boolean addPossiblePropertyDouble(final String name, double d, final int r, final String succeeding, final boolean green, final List<String> list) {
        d += r;
        if (d == 0.0) {
            return false;
        }
        final StringBuilder builder = new StringBuilder();
        builder.append((Object)ChatColor.GRAY).append(name).append(": ").append((Object)(green ? ChatColor.GREEN : ChatColor.RED)).append((d < 0.0) ? "" : "+").append(d).append(succeeding);
        if (r != 0) {
            builder.append((Object)ChatColor.BLUE).append(" (").append(this.parent.getReforge().getName()).append(" ").append((r < 0) ? "" : "+").append(r).append(succeeding).append(")");
        }
        list.add((Object)builder.toString());
        return true;
    }
    
    static {
        SSE_ID = (Object)ChatColor.DARK_GRAY + "SKYSIM_ID: %s";
    }
}
