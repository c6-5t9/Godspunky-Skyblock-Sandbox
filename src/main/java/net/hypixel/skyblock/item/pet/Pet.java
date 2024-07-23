package net.hypixel.skyblock.item.pet;

import org.bson.Document;
import java.util.HashMap;
import java.util.Map;
import net.hypixel.skyblock.item.SMaterial;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import java.util.Arrays;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;
import net.hypixel.skyblock.user.User;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import net.hypixel.skyblock.item.SpecificItemType;
import net.hypixel.skyblock.util.SUtil;
import org.bukkit.ChatColor;
import java.util.ArrayList;
import net.hypixel.skyblock.features.skill.Skill;
import net.hypixel.skyblock.item.SItem;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import java.util.Iterator;
import java.util.function.Consumer;
import net.hypixel.skyblock.item.Rarity;
import java.util.List;
import net.hypixel.skyblock.item.ItemData;
import net.hypixel.skyblock.item.MaterialFunction;
import net.hypixel.skyblock.item.LoreableMaterialStatistics;
import net.hypixel.skyblock.item.SkullStatistics;

public abstract class Pet implements SkullStatistics, LoreableMaterialStatistics, MaterialFunction, ItemData
{
    protected static List<Integer> COMMON_XP_GOALS;
    protected static List<Integer> UNCOMMON_XP_GOALS;
    protected static List<Integer> RARE_XP_GOALS;
    protected static List<Integer> EPIC_XP_GOALS;
    protected static List<Integer> LEGENDARY_XP_GOALS;
    
    private static List<Integer> getGoalsForRarity(final Rarity rarity) {
        List<Integer> goals = null;
        switch (rarity) {
            case COMMON: {
                goals = Pet.COMMON_XP_GOALS;
                break;
            }
            case UNCOMMON: {
                goals = Pet.UNCOMMON_XP_GOALS;
                break;
            }
            case RARE: {
                goals = Pet.RARE_XP_GOALS;
                break;
            }
            case EPIC: {
                goals = Pet.EPIC_XP_GOALS;
                break;
            }
            default: {
                goals = Pet.LEGENDARY_XP_GOALS;
                break;
            }
        }
        return goals;
    }
    
    public void runAbilities(final Consumer<PetAbility> consumer, final PetItem item) {
        if (item != null) {
            for (final PetAbility ability : this.getPetAbilities(item.toItem())) {
                consumer.accept((Object)ability);
            }
        }
    }
    
    public static int getLevel(final double xp, final Rarity rarity) {
        if (xp < 0.0) {
            return -1;
        }
        final List<Integer> goals = getGoalsForRarity(rarity);
        for (int i = 0; i < goals.size(); ++i) {
            if ((int)goals.get(i) > xp) {
                return i;
            }
        }
        return 100;
    }
    
    private static double getXP(int level, final Rarity rarity) {
        if (--level < 0 || level > 99) {
            return -1.0;
        }
        return (int)getGoalsForRarity(rarity).get(level);
    }
    
    @Override
    public NBTTagCompound getData() {
        final NBTTagCompound compound = new NBTTagCompound();
        compound.setDouble("xp", 0.0);
        compound.setBoolean("equipped", false);
        return compound;
    }
    
    public abstract List<PetAbility> getPetAbilities(final SItem p0);
    
    public abstract Skill getSkill();
    
    @Override
    public List<String> getCustomLore(final SItem instance) {
        final List<String> lore = (List<String>)new ArrayList();
        lore.add((Object)((Object)ChatColor.DARK_GRAY + this.getSkill().getName() + " Pet"));
        if (this.hasStatBoosts()) {
            lore.add((Object)" ");
        }
        final int level = getLevel(instance);
        addPropertyInt("Magic Find", this.getPerMagicFind() * 100.0, lore, level);
        addPropertyPercent("Crit Damage", this.getPerCritDamage(), lore, level);
        addPropertyPercent("Crit Chance", this.getPerCritChance(), lore, level);
        final double health = this.getPerHealth();
        if (health > 0.0) {
            lore.add((Object)((Object)ChatColor.GRAY + "Health: " + (Object)ChatColor.GREEN + "+" + Math.round(health * level) + " HP"));
        }
        addPropertyInt("Strength", this.getPerStrength(), lore, level);
        addPropertyInt("Defense", this.getPerDefense(), lore, level);
        addPropertyInt("True Defense", this.getPerTrueDefense(), lore, level);
        addPropertyPercent("Speed", this.getPerSpeed(), lore, level);
        addPropertyInt("Intelligence", this.getPerIntelligence(), lore, level);
        addPropertyInt("Ferocity", this.getPerFerocity(), lore, level);
        addPropertyInt("Bonus Attack Speed", this.getPerAttackSpeed(), lore, level);
        final List<PetAbility> abilities = this.getPetAbilities(instance);
        for (final PetAbility ability : abilities) {
            lore.add((Object)" ");
            lore.add((Object)((Object)ChatColor.GOLD + ability.getName()));
            for (final String line : ability.getDescription(instance)) {
                lore.add((Object)((Object)ChatColor.GRAY + line));
            }
        }
        if (level != 100) {
            lore.add((Object)" ");
            final double xp = instance.getData().getDouble("xp");
            final int next = level + 1;
            final double progress = xp - getXP(level, instance.getRarity());
            final int goal = (int)(getXP(next, instance.getRarity()) - getXP(level, instance.getRarity()));
            lore.add((Object)SUtil.createProgressText("Progress to Level " + next, progress, goal));
            lore.add((Object)SUtil.createLineProgressBar(20, ChatColor.DARK_GREEN, progress, goal));
        }
        else if (level == 100) {
            lore.add((Object)"");
            lore.add((Object)(String.valueOf((Object)ChatColor.AQUA) + (Object)ChatColor.BOLD + "MAX LEVEL"));
        }
        if (!instance.getData().getBoolean("equipped")) {
            lore.add((Object)"");
            lore.add((Object)((Object)ChatColor.YELLOW + "Right-click to add this pet to"));
            lore.add((Object)((Object)ChatColor.YELLOW + "your pet menu!"));
        }
        if (instance.getType().getStatistics().displayRarity() && !instance.getData().getBoolean("equipped")) {
            final SpecificItemType type = instance.getType().getStatistics().getSpecificType();
            lore.add((Object)" ");
            lore.add((Object)((instance.isRecombobulated() ? (instance.getRarity().getBoldedColor() + (Object)ChatColor.MAGIC + "D" + (Object)ChatColor.RESET + " ") : "") + instance.getRarity().getDisplay() + ((type != SpecificItemType.NONE) ? (" " + type.getName()) : "") + (instance.isRecombobulated() ? (instance.getRarity().getBoldedColor() + " " + (Object)ChatColor.MAGIC + "D" + (Object)ChatColor.RESET) : "")));
        }
        return lore;
    }
    
    @Override
    public void onInstanceUpdate(final SItem instance) {
        instance.setDisplayName((Object)ChatColor.GRAY + "[Lvl " + getLevel(instance) + "] " + (Object)instance.getRarity().getColor() + this.getDisplayName());
    }
    
    @Override
    public Rarity getRarity() {
        return Rarity.COMMON;
    }
    
    @Override
    public void onInteraction(final PlayerInteractEvent e) {
        if (e.getAction() != Action.RIGHT_CLICK_AIR && e.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }
        final Player player = e.getPlayer();
        final User user = User.getUser(player.getUniqueId());
        final SItem item = SItem.find(e.getItem());
        user.addPet(item);
        player.setItemInHand((ItemStack)null);
        player.playSound(player.getLocation(), Sound.ORB_PICKUP, 1.0f, 1.0f);
        player.sendMessage((Object)ChatColor.GREEN + "Successfully added " + (Object)item.getRarity().getColor() + item.getType().getDisplayName(item.getType().getData()) + (Object)ChatColor.GREEN + " to your pet menu!");
    }
    
    @Override
    public boolean isStackable() {
        return false;
    }
    
    public static int getLevel(final SItem instance) {
        final double xp = instance.getData().getDouble("xp");
        return getLevel(xp, instance.getRarity());
    }
    
    private static void addPropertyInt(final String name, final double value, final List<String> lore, final int level) {
        final long fin = Math.round(value * level);
        if (value != 0.0) {
            lore.add((Object)((Object)ChatColor.GRAY + name + ": " + (Object)ChatColor.GREEN + ((fin >= 0L) ? "+" : "") + fin));
        }
    }
    
    private static void addPropertyPercent(final String name, final double value, final List<String> lore, final int level) {
        final long fin = Math.round(value * 100.0 * level);
        if (value != 0.0) {
            lore.add((Object)((Object)ChatColor.GRAY + name + ": " + (Object)ChatColor.GREEN + ((fin >= 0L) ? "+" : "") + fin + "%"));
        }
    }
    
    public double getPerHealth() {
        return 0.0;
    }
    
    public double getPerDefense() {
        return 0.0;
    }
    
    public double getPerStrength() {
        return 0.0;
    }
    
    public double getPerIntelligence() {
        return 0.0;
    }
    
    public double getPerSpeed() {
        return 0.0;
    }
    
    public double getPerCritChance() {
        return 0.0;
    }
    
    public double getPerCritDamage() {
        return 0.0;
    }
    
    public double getPerMagicFind() {
        return 0.0;
    }
    
    public double getPerTrueDefense() {
        return 0.0;
    }
    
    public double getPerFerocity() {
        return 0.0;
    }
    
    public double getPerAttackSpeed() {
        return 0.0;
    }
    
    public boolean hasStatBoosts() {
        return this.getPerHealth() != 0.0 || this.getPerDefense() != 0.0 || this.getPerStrength() != 0.0 || this.getPerIntelligence() != 0.0 || this.getPerSpeed() != 0.0 || this.getPerCritChance() != 0.0 || this.getPerCritDamage() != 0.0 || this.getPerMagicFind() != 0.0 || this.getPerTrueDefense() != 0.0;
    }
    
    public void particleBelowA(final Player p, final Location l) {
    }
    
    static {
        Pet.COMMON_XP_GOALS = (List<Integer>)Arrays.asList((Object[])new Integer[] { 0, 100, 210, 330, 460, 605, 765, 940, 1130, 1340, 1570, 1820, 2095, 2395, 2725, 3085, 3485, 3925, 4415, 4955, 5555, 6215, 6945, 7745, 8625, 9585, 10635, 11785, 13045, 14425, 15935, 17585, 19385, 21345, 23475, 25785, 28285, 30985, 33905, 37065, 40485, 44185, 48185, 52535, 57285, 62485, 68185, 74485, 81485, 89285, 97985, 107685, 118485, 130485, 143785, 158485, 174685, 192485, 211985, 233285, 256485, 281685, 309085, 338885, 371285, 406485, 444685, 486085, 530885, 579285, 631485, 687685, 748085, 812885, 882285, 956485, 1035685, 1120385, 1211085, 1308285, 1412485, 1524185, 1643885, 1772085, 1909285, 2055985, 2212685, 2380385, 2560085, 2752785, 2959485, 3181185, 3418885, 3673585, 3946285, 4237985, 4549685, 4883385, 5241085, 5624785 });
        Pet.UNCOMMON_XP_GOALS = (List<Integer>)Arrays.asList((Object[])new Integer[] { 0, 175, 365, 575, 805, 1055, 1330, 1630, 1960, 2320, 2720, 3160, 3650, 4190, 4790, 5450, 6180, 6980, 7860, 8820, 9870, 11020, 12280, 13660, 15170, 16820, 18620, 20580, 22710, 25020, 27520, 30220, 33140, 36300, 39720, 43420, 47420, 51770, 56520, 61720, 67420, 73720, 80720, 88520, 97220, 106920, 117720, 129720, 143020, 157720, 173920, 191720, 211220, 232520, 255720, 280920, 308320, 338120, 370520, 405720, 443920, 485320, 530120, 578520, 630720, 686920, 747320, 812120, 881520, 955720, 1034920, 1119620, 1210320, 1307520, 1411720, 1523420, 1643120, 1771320, 1908520, 2055220, 2211920, 2379620, 2559320, 2752020, 2958720, 3180420, 3418120, 3672820, 3945520, 4237220, 4548920, 4882620, 5240320, 5624020, 6035720, 6477420, 6954120, 7470820, 8032520, 8644220 });
        Pet.RARE_XP_GOALS = (List<Integer>)Arrays.asList((Object[])new Integer[] { 0, 275, 575, 905, 1265, 1665, 2105, 2595, 3135, 3735, 4395, 5125, 5925, 6805, 7765, 8815, 9965, 11225, 12605, 14115, 15765, 17565, 19525, 21655, 23965, 26465, 29165, 32085, 35245, 38665, 42365, 46365, 50715, 55465, 60665, 66365, 72665, 79665, 87465, 96165, 105865, 116665, 128665, 141965, 156665, 172865, 190665, 210165, 231465, 254665, 279865, 307265, 337065, 369465, 404665, 442865, 484265, 529065, 577465, 629665, 685865, 746265, 811065, 880465, 954665, 1033865, 1118565, 1209265, 1306465, 1410665, 1522365, 1642065, 1770265, 1907465, 2054165, 2210865, 2378565, 2558265, 2750965, 2957665, 3179365, 3417065, 3671765, 3944465, 4236165, 4547865, 4881565, 5239265, 5622965, 6034665, 6476365, 6953065, 7469765, 8031465, 8643165, 9309865, 10036565, 10828265, 11689965, 12626665 });
        Pet.EPIC_XP_GOALS = (List<Integer>)Arrays.asList((Object[])new Integer[] { 0, 440, 930, 1470, 2070, 2730, 3460, 4260, 5140, 6100, 7150, 8300, 9560, 10940, 12450, 14100, 15900, 17860, 19990, 22300, 24800, 27500, 30420, 33580, 37000, 40700, 44700, 49050, 53800, 59000, 64700, 71000, 78000, 85800, 94500, 104200, 115000, 127000, 140300, 155000, 171200, 189000, 208500, 229800, 253000, 278200, 305600, 335400, 367800, 403000, 441200, 482600, 527400, 575800, 628000, 684200, 744600, 809400, 878800, 953000, 1032200, 1116900, 1207600, 1304800, 1409000, 1520700, 1640400, 1768600, 1905800, 2052500, 2209200, 2376900, 2556600, 2749300, 2956000, 3177700, 3415400, 3670100, 3942800, 4234500, 4546200, 4879900, 5237600, 5621300, 6033000, 6474700, 6951400, 7468100, 8029800, 8641500, 9308200, 10034900, 10826600, 11688300, 12625000, 13641700, 14743400, 15935100, 17221800, 18608500 });
        Pet.LEGENDARY_XP_GOALS = (List<Integer>)Arrays.asList((Object[])new Integer[] { 0, 660, 1390, 2190, 3070, 4030, 5080, 6230, 7490, 8870, 10380, 12030, 13830, 15790, 17920, 20230, 22730, 25430, 28350, 31510, 34930, 38630, 42630, 46980, 51730, 56930, 62630, 68930, 75930, 83730, 92430, 102130, 112930, 124930, 138230, 152930, 169130, 186930, 206430, 227730, 250930, 276130, 303530, 333330, 365730, 400930, 439130, 480530, 525330, 573730, 625930, 682130, 742530, 807330, 876730, 950930, 1030130, 1114830, 1205530, 1302730, 1406930, 1518630, 1638330, 1766530, 1903730, 2050430, 2207130, 2374830, 2554530, 2747230, 2953930, 3175630, 3413330, 3668030, 3940730, 4232430, 4544130, 4877830, 5235530, 5619230, 6030930, 6472630, 6949330, 7466030, 8027730, 8639430, 9306130, 10032830, 10824530, 11686230, 12622930, 13639630, 14741330, 15933030, 17219730, 18606430, 20103130, 21719830, 23466530, 25353230 });
    }
    
    public static class PetItem implements ConfigurationSerializable
    {
        private final SMaterial type;
        private Rarity rarity;
        private double xp;
        private boolean active;
        
        private PetItem(final SMaterial type, final Rarity rarity, final double xp, final boolean active) {
            this.type = type;
            this.rarity = rarity;
            this.xp = xp;
            this.active = active;
        }
        
        public PetItem(final SMaterial type, final Rarity rarity, final double xp) {
            this(type, rarity, xp, false);
        }
        
        public Map<String, Object> serialize() {
            final Map<String, Object> map = (Map<String, Object>)new HashMap();
            map.put((Object)"type", (Object)this.type.name());
            map.put((Object)"rarity", (Object)this.rarity.name());
            map.put((Object)"xp", (Object)this.xp);
            map.put((Object)"active", (Object)this.active);
            return map;
        }
        
        public Document toDocument() {
            final Document document = new Document();
            document.append("type", (Object)this.type.name());
            document.append("rarity", (Object)this.rarity.name());
            document.append("xp", (Object)this.xp);
            document.append("active", (Object)this.active);
            return document;
        }
        
        @Override
        public boolean equals(final Object o) {
            if (!(o instanceof PetItem)) {
                return false;
            }
            final PetItem pet = (PetItem)o;
            return this.type == pet.type && this.rarity == pet.rarity && this.xp == pet.xp && this.active == pet.active;
        }
        
        public boolean equalsItem(final SItem item) {
            return this.type == item.getType() && this.rarity == item.getRarity() && this.xp == item.getData().getDouble("xp");
        }
        
        public SItem toItem() {
            final SItem sItem = SItem.of(this.type);
            sItem.setRarity(this.rarity);
            sItem.getData().setDouble("xp", this.xp);
            return sItem;
        }
        
        public static PetItem deserialize(final Map<String, Object> map) {
            return new PetItem(SMaterial.getMaterial((String)map.get((Object)"type")), Rarity.getRarity((String)map.get((Object)"rarity")), (double)map.get((Object)"xp"), (boolean)map.get((Object)"active"));
        }
        
        public static PetItem fromDocument(final Document document) {
            final String type = document.getString((Object)"type");
            final String rarity = document.getString((Object)"rarity");
            final double xp = document.getDouble((Object)"xp");
            final boolean active = document.getBoolean((Object)"active");
            return new PetItem(SMaterial.getMaterial(type), Rarity.getRarity(rarity), xp, active);
        }
        
        public SMaterial getType() {
            return this.type;
        }
        
        public Rarity getRarity() {
            return this.rarity;
        }
        
        public double getXp() {
            return this.xp;
        }
        
        public boolean isActive() {
            return this.active;
        }
        
        public void setRarity(final Rarity rarity) {
            this.rarity = rarity;
        }
        
        public void setXp(final double xp) {
            this.xp = xp;
        }
        
        public void setActive(final boolean active) {
            this.active = active;
        }
    }
}
