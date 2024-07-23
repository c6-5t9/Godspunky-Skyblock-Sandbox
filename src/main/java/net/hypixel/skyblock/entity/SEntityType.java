package net.hypixel.skyblock.entity;

import net.hypixel.skyblock.entity.nether.LargeMagmaCube;
import net.hypixel.skyblock.entity.nether.MediumMagmaCube;
import net.hypixel.skyblock.entity.nether.SmallMagmaCube;
import net.hypixel.skyblock.entity.nether.WitherSkeleton;
import net.hypixel.skyblock.entity.nms.SneakyCreeper;
import net.hypixel.skyblock.entity.den.SpidersDenSlime;
import net.hypixel.skyblock.entity.skeleton.HighLevelSkeleton;
import net.hypixel.skyblock.entity.den.SpidersDenSkeleton;
import net.hypixel.skyblock.entity.wolf.SoulOfTheAlpha;
import net.hypixel.skyblock.entity.wolf.HowlingSpirit;
import net.hypixel.skyblock.entity.wolf.PackSpirit;
import net.hypixel.skyblock.entity.wolf.OldWolf;
import net.hypixel.skyblock.entity.wolf.Wolf;
import net.hypixel.skyblock.entity.zombie.GoldenGhoul;
import net.hypixel.skyblock.entity.zombie.CryptGhoul;
import net.hypixel.skyblock.entity.zombie.ZombieVillager;
import net.hypixel.skyblock.entity.den.VoraciousSpider;
import net.hypixel.skyblock.entity.den.WeaverSpider;
import net.hypixel.skyblock.entity.den.DasherSpider;
import net.hypixel.skyblock.entity.den.BroodMother;
import net.hypixel.skyblock.entity.den.CaveSpider;
import net.hypixel.skyblock.entity.den.SpiderJockey;
import net.hypixel.skyblock.entity.den.Silverfish;
import net.hypixel.skyblock.entity.den.SplitterSpider;
import net.hypixel.skyblock.entity.end.StrongEnderman;
import net.hypixel.skyblock.entity.end.Enderman;
import net.hypixel.skyblock.entity.end.WeakEnderman;
import net.hypixel.skyblock.entity.caverns.EnchantedDiamondSkeleton;
import net.hypixel.skyblock.entity.caverns.EnchantedDiamondZombie;
import net.hypixel.skyblock.entity.caverns.DiamondSkeleton;
import net.hypixel.skyblock.entity.caverns.DiamondZombie;
import net.hypixel.skyblock.entity.caverns.LargeSlime;
import net.hypixel.skyblock.entity.caverns.MediumSlime;
import net.hypixel.skyblock.entity.caverns.SmallSlime;
import net.hypixel.skyblock.entity.caverns.Pigman;
import net.hypixel.skyblock.entity.caverns.LapisZombie;
import net.hypixel.skyblock.entity.insentient.WheatCrystal;
import net.hypixel.skyblock.entity.nms.UncollidableArmorStand;
import net.hypixel.skyblock.entity.nms.VelocityArmorStand;
import net.hypixel.skyblock.entity.end.ObsidianDefender;
import net.hypixel.skyblock.entity.end.Watcher;
import net.hypixel.skyblock.entity.den.MutantTarantula;
import net.hypixel.skyblock.entity.den.TarantulaBeast;
import net.hypixel.skyblock.entity.den.TarantulaVermin;
import net.hypixel.skyblock.entity.nms.TarantulaBroodfather;
import net.hypixel.skyblock.entity.wolf.SvenAlpha;
import net.hypixel.skyblock.entity.wolf.PackEnforcer;
import net.hypixel.skyblock.entity.wolf.SvenFollower;
import net.hypixel.skyblock.entity.wolf.SvenPup;
import net.hypixel.skyblock.entity.nms.SvenPackmaster;
import net.hypixel.skyblock.entity.zombie.AtonedChampion;
import net.hypixel.skyblock.entity.zombie.AtonedRevenant;
import net.hypixel.skyblock.entity.nms.AtonedHorror;
import net.hypixel.skyblock.entity.zombie.DeformedRevenant;
import net.hypixel.skyblock.entity.zombie.RevenantChampion;
import net.hypixel.skyblock.entity.zombie.RevenantSycophant;
import net.hypixel.skyblock.entity.nms.RevenantHorror;
import net.hypixel.skyblock.entity.dragon.type.SuperiorDragon;
import net.hypixel.skyblock.entity.dragon.type.StrongDragon;
import net.hypixel.skyblock.entity.dragon.type.YoungDragon;
import net.hypixel.skyblock.entity.dragon.type.UnstableDragon;
import net.hypixel.skyblock.entity.dragon.type.WiseDragon;
import net.hypixel.skyblock.entity.dragon.type.OldDragon;
import net.hypixel.skyblock.entity.dragon.type.ProtectorDragon;
import net.hypixel.skyblock.entity.end.VoidlingFanatic;
import net.hypixel.skyblock.entity.end.VoidlingExtremist;
import net.hypixel.skyblock.entity.end.VoidcrazedManiac;
import net.hypixel.skyblock.entity.end.VoidlingRadical;
import net.hypixel.skyblock.entity.end.VoidlingDevotee;
import net.hypixel.skyblock.entity.nms.CrimsonSathanas;
import net.hypixel.skyblock.entity.nms.VoidgloomSeraph;
import net.hypixel.skyblock.entity.end.Zealot;
import net.hypixel.skyblock.entity.dungeons.regularentity.SkeletorPrime;
import net.hypixel.skyblock.entity.dungeons.regularentity.Skeletor;
import net.hypixel.skyblock.entity.dungeons.regularentity.SkeletonSoldier;
import net.hypixel.skyblock.entity.dungeons.regularentity.ZombieSoldier;
import net.hypixel.skyblock.entity.dungeons.regularentity.ZombieKnight;
import net.hypixel.skyblock.entity.dungeons.regularentity.UndeadSkeleton;
import net.hypixel.skyblock.entity.dungeons.regularentity.SuperTankZombie;
import net.hypixel.skyblock.entity.dungeons.regularentity.SuperArcher;
import net.hypixel.skyblock.entity.dungeons.regularentity.Sniper;
import net.hypixel.skyblock.entity.dungeons.regularentity.SkeletonMaster;
import net.hypixel.skyblock.entity.dungeons.regularentity.ScaredSkeleton;
import net.hypixel.skyblock.entity.dungeons.regularentity.CryptLurker;
import net.hypixel.skyblock.entity.dungeons.regularentity.CryptSoulstealer;
import net.hypixel.skyblock.entity.dungeons.regularentity.CryptUndead;
import net.hypixel.skyblock.entity.dungeons.regularentity.CryptDreadlord;
import net.hypixel.skyblock.entity.dungeons.regularentity.TankZombie;
import net.hypixel.skyblock.entity.dungeons.regularentity.Withermancer;
import net.hypixel.skyblock.entity.dungeons.regularentity.Fels;
import net.hypixel.skyblock.entity.dungeons.boss.sadan.TerracottaDummy;
import net.hypixel.skyblock.entity.dungeons.boss.sadan.GiantsDummy;
import net.hypixel.skyblock.entity.dungeons.boss.sadan.SadanDummy;
import net.hypixel.skyblock.entity.dungeons.boss.sadan.SadanDummy_Idle;
import net.hypixel.skyblock.entity.dungeons.boss.sadan.SadanHuman;
import net.hypixel.skyblock.entity.dungeons.boss.sadan.SadanGiant;
import net.hypixel.skyblock.entity.dungeons.boss.sadan.SleepingGolem_S;
import net.hypixel.skyblock.entity.dungeons.boss.sadan.DiamondGiant;
import net.hypixel.skyblock.entity.dungeons.boss.sadan.JollyPinkGiant;
import net.hypixel.skyblock.entity.dungeons.boss.sadan.LASRGiant;
import net.hypixel.skyblock.entity.dungeons.boss.sadan.BigfootGiant;
import net.hypixel.skyblock.entity.dungeons.boss.sadan.TerracottaSadan;
import net.hypixel.skyblock.entity.dungeons.TestingMob;
import net.hypixel.skyblock.entity.zombie.Zombie;
import net.hypixel.skyblock.entity.nms.Giant;
import net.hypixel.skyblock.entity.dungeons.minibosses.ShadowAssassins;
import net.hypixel.skyblock.entity.caverns.UndeadGiaKhanhvn;
import net.hypixel.skyblock.entity.nms.BorisYeltsin;
import net.hypixel.skyblock.entity.dungeons.minibosses.FrozenAdv;
import net.hypixel.skyblock.entity.dungeons.minibosses.AngryArchaeologist;
import net.hypixel.skyblock.entity.dungeons.minibosses.HolyLostAdv;
import net.hypixel.skyblock.entity.dungeons.minibosses.Unstable;
import net.hypixel.skyblock.entity.dungeons.minibosses.YoungLostAdv;
import net.hypixel.skyblock.entity.dungeons.minibosses.SuperiorLostAdv;
import net.hypixel.skyblock.entity.zombie.DiamondGoblinzine;
import net.hypixel.skyblock.entity.zombie.Goblinzine;
import net.hypixel.skyblock.entity.dungeons.watcher.WatcherBonzo;
import net.hypixel.skyblock.entity.dungeons.watcher.WatcherLivid;
import net.hypixel.skyblock.entity.dungeons.watcher.WatcherWalker;
import net.hypixel.skyblock.entity.dungeons.watcher.WatcherVader;
import net.hypixel.skyblock.entity.dungeons.watcher.WatcherTear;
import net.hypixel.skyblock.entity.dungeons.watcher.WatcherSkull;
import net.hypixel.skyblock.entity.dungeons.watcher.WatcherRevoker;
import net.hypixel.skyblock.entity.dungeons.watcher.WatcherReaper;
import net.hypixel.skyblock.entity.dungeons.watcher.WatcherPutrid;
import net.hypixel.skyblock.entity.dungeons.watcher.WatcherPsycho;
import net.hypixel.skyblock.entity.dungeons.watcher.WatcherParasiteFish;
import net.hypixel.skyblock.entity.dungeons.watcher.WatcherParasite;
import net.hypixel.skyblock.entity.dungeons.watcher.WatcherOoze;
import net.hypixel.skyblock.entity.dungeons.watcher.WatcherMute;
import net.hypixel.skyblock.entity.dungeons.watcher.WatcherMrDead;
import net.hypixel.skyblock.entity.dungeons.watcher.WatcherLeech;
import net.hypixel.skyblock.entity.dungeons.watcher.WatcherFrost;
import net.hypixel.skyblock.entity.dungeons.watcher.WatcherFreak;
import net.hypixel.skyblock.entity.dungeons.watcher.WatcherFlamer;
import net.hypixel.skyblock.entity.dungeons.watcher.WatcherCannibal;
import java.lang.reflect.Method;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import net.minecraft.server.v1_8_R3.EntityTypes;
import java.util.ArrayList;
import java.lang.reflect.InvocationTargetException;
import net.minecraft.server.v1_8_R3.EntityInsentient;
import org.bukkit.entity.EntityType;

public enum SEntityType
{
    WATCHER_CANNIBAL(EntityType.ZOMBIE, (Class<?>)WatcherCannibal.class), 
    WATCHER_FLAMER(EntityType.ZOMBIE, (Class<?>)WatcherFlamer.class), 
    WATCHER_FREAK(EntityType.ZOMBIE, (Class<?>)WatcherFreak.class), 
    WATCHER_FROST(EntityType.ZOMBIE, (Class<?>)WatcherFrost.class), 
    WATCHER_LEECH(EntityType.ZOMBIE, (Class<?>)WatcherLeech.class), 
    WATCHER_MR_DEAD(EntityType.ZOMBIE, (Class<?>)WatcherMrDead.class), 
    WATCHER_MUTE(EntityType.ZOMBIE, (Class<?>)WatcherMute.class), 
    WATCHER_OOZE(EntityType.ZOMBIE, (Class<?>)WatcherOoze.class), 
    WATCHER_PARASITE(EntityType.ZOMBIE, (Class<?>)WatcherParasite.class), 
    WATCHER_PARASITE_SILVERFISH(EntityType.SILVERFISH, (Class<?>)WatcherParasiteFish.class), 
    WATCHER_PSYCHO(EntityType.ZOMBIE, (Class<?>)WatcherPsycho.class), 
    WATCHER_PUTRID(EntityType.ZOMBIE, (Class<?>)WatcherPutrid.class), 
    WATCHER_REAPER(EntityType.ZOMBIE, (Class<?>)WatcherReaper.class), 
    WATCHER_REVOKER(EntityType.ZOMBIE, (Class<?>)WatcherRevoker.class), 
    WATCHER_SKULL(EntityType.ZOMBIE, (Class<?>)WatcherSkull.class), 
    WATCHER_TEAR(EntityType.ZOMBIE, (Class<?>)WatcherTear.class), 
    WATCHER_VADER(EntityType.ZOMBIE, (Class<?>)WatcherVader.class), 
    WATCHER_WALKER(EntityType.ZOMBIE, (Class<?>)WatcherWalker.class), 
    WATCHER_LIVID(EntityType.ZOMBIE, (Class<?>)WatcherLivid.class), 
    WATCHER_BONZO(EntityType.ZOMBIE, (Class<?>)WatcherBonzo.class), 
    GOBLIN_DIMOON(EntityType.ZOMBIE, (Class<?>)Goblinzine.class), 
    DGOBLIN_DIMOON(EntityType.ZOMBIE, (Class<?>)DiamondGoblinzine.class), 
    LOST_ADV_SUP(EntityType.ZOMBIE, (Class<?>)SuperiorLostAdv.class), 
    LOST_ADV_YOUNG(EntityType.ZOMBIE, (Class<?>)YoungLostAdv.class), 
    LOST_ADV_UNSTABLE(EntityType.ZOMBIE, (Class<?>)Unstable.class), 
    LOST_ADV_HOLY(EntityType.ZOMBIE, (Class<?>)HolyLostAdv.class), 
    ANGRY_ARCH(EntityType.ZOMBIE, (Class<?>)AngryArchaeologist.class), 
    FROZEN_ADV(EntityType.ZOMBIE, (Class<?>)FrozenAdv.class), 
    BOSS_BORIS_YELTSIN(EntityType.ZOMBIE, (Class<?>)BorisYeltsin.class), 
    SCARY_CAVE_UNDEADBOSS_1(EntityType.ZOMBIE, (Class<?>)UndeadGiaKhanhvn.class), 
    SHADOW_ASSASSINS(EntityType.ZOMBIE, (Class<?>)ShadowAssassins.class), 
    TERRORANT(EntityType.ZOMBIE, (Class<?>)Giant.class), 
    TEST_OBJECT(EntityType.ZOMBIE, (Class<?>)Zombie.class), 
    TEST_CHIMMY_OBJECT_T34(EntityType.ZOMBIE, (Class<?>)TestingMob.class), 
    TERRACOTTA_SADAN(EntityType.ZOMBIE, (Class<?>)TerracottaSadan.class), 
    BIGFOOT_SADAN(EntityType.ZOMBIE, (Class<?>)BigfootGiant.class), 
    LASR_SADAN(EntityType.ZOMBIE, (Class<?>)LASRGiant.class), 
    JOLLY_PINK_SADAN(EntityType.ZOMBIE, (Class<?>)JollyPinkGiant.class), 
    DIAMOND_SADAN(EntityType.ZOMBIE, (Class<?>)DiamondGiant.class), 
    SLEEPING_GOLEM(EntityType.ZOMBIE, (Class<?>)SleepingGolem_S.class), 
    WOKE_GOLEM(EntityType.ZOMBIE, (Class<?>)Stronker.class), 
    GIANT_SADAN(EntityType.ZOMBIE, (Class<?>)SadanGiant.class), 
    SADAN(EntityType.ZOMBIE, (Class<?>)SadanHuman.class), 
    DUMMY_SADAN_1(EntityType.ZOMBIE, (Class<?>)SadanDummy_Idle.class), 
    DUMMY_FUNCTION_2(EntityType.ZOMBIE, (Class<?>)SadanDummy.class), 
    GIANT_DUMMY(EntityType.ZOMBIE, (Class<?>)GiantsDummy.class), 
    TERRACOTTA_DUMMY(EntityType.ZOMBIE, (Class<?>)TerracottaDummy.class), 
    FELS(EntityType.ENDERMAN, (Class<?>)Fels.class), 
    WITHERMANCER(EntityType.SKELETON, (Class<?>)Withermancer.class), 
    TANK_ZOMBIE(EntityType.ZOMBIE, (Class<?>)TankZombie.class), 
    CRYPT_DREADLORD(EntityType.ZOMBIE, (Class<?>)CryptDreadlord.class), 
    CRYPT_UNDEAD(EntityType.ZOMBIE, (Class<?>)CryptUndead.class), 
    CRYPT_SOULSTEALER(EntityType.ZOMBIE, (Class<?>)CryptSoulstealer.class), 
    CRYPT_LURKER(EntityType.ZOMBIE, (Class<?>)CryptLurker.class), 
    SCARED_SKELETON(EntityType.SKELETON, (Class<?>)ScaredSkeleton.class), 
    SKELETON_MASTER(EntityType.SKELETON, (Class<?>)SkeletonMaster.class), 
    SNIPER(EntityType.SKELETON, (Class<?>)Sniper.class), 
    SUPER_ARCHER(EntityType.SKELETON, (Class<?>)SuperArcher.class), 
    SUPER_TANK_ZOMBIE(EntityType.ZOMBIE, (Class<?>)SuperTankZombie.class), 
    UNDEAD_SKELETON(EntityType.SKELETON, (Class<?>)UndeadSkeleton.class), 
    ZOMBIE_KNIGHT(EntityType.ZOMBIE, (Class<?>)ZombieKnight.class), 
    ZOMBIE_SOLDIER(EntityType.ZOMBIE, (Class<?>)ZombieSoldier.class), 
    SKELETON_SOLDIER(EntityType.SKELETON, (Class<?>)SkeletonSoldier.class), 
    SKELETOR(EntityType.ZOMBIE, (Class<?>)Skeletor.class), 
    SKELETOR_PRIME(EntityType.ZOMBIE, (Class<?>)SkeletorPrime.class), 
    ZEALOT(EntityType.ENDERMAN, (Class<?>)Zealot.class), 
    ENDER_CHEST_ZEALOT(EntityType.ENDERMAN, (Class<?>)Zealot.EnderChestZealot.class), 
    VOIDGLOOM_SERAPH(EntityType.ENDERMAN, (Class<?>)VoidgloomSeraph.class, true), 
    CRIMSON_SATHANAS(EntityType.SKELETON, (Class<?>)CrimsonSathanas.class, true), 
    VOIDLING_DEVOTEE(EntityType.ENDERMAN, (Class<?>)VoidlingDevotee.class), 
    VOIDLING_RADICAL(EntityType.ENDERMAN, (Class<?>)VoidlingRadical.class), 
    VOIDCRAZED_MANIAC(EntityType.ENDERMAN, (Class<?>)VoidcrazedManiac.class), 
    VOIDLING_EXTREMIST(EntityType.ENDERMAN, (Class<?>)VoidlingExtremist.class), 
    VOIDLING_FANATIC(EntityType.ENDERMAN, (Class<?>)VoidlingFanatic.class), 
    SPECIAL_ZEALOT(EntityType.ENDERMAN, (Class<?>)Zealot.SpecialZealot.class), 
    PROTECTOR_DRAGON(EntityType.ENDER_DRAGON, (Class<?>)ProtectorDragon.class), 
    OLD_DRAGON(EntityType.ENDER_DRAGON, (Class<?>)OldDragon.class), 
    WISE_DRAGON(EntityType.ENDER_DRAGON, (Class<?>)WiseDragon.class), 
    UNSTABLE_DRAGON(EntityType.ENDER_DRAGON, (Class<?>)UnstableDragon.class), 
    YOUNG_DRAGON(EntityType.ENDER_DRAGON, (Class<?>)YoungDragon.class), 
    STRONG_DRAGON(EntityType.ENDER_DRAGON, (Class<?>)StrongDragon.class), 
    SUPERIOR_DRAGON(EntityType.ENDER_DRAGON, (Class<?>)SuperiorDragon.class), 
    REVENANT_HORROR(EntityType.ZOMBIE, (Class<?>)RevenantHorror.class, true), 
    REVENANT_SYCOPHANT(EntityType.ZOMBIE, (Class<?>)RevenantSycophant.class), 
    REVENANT_CHAMPION(EntityType.ZOMBIE, (Class<?>)RevenantChampion.class), 
    DEFORMED_REVENANT(EntityType.ZOMBIE, (Class<?>)DeformedRevenant.class), 
    ATONED_HORROR(EntityType.ZOMBIE, (Class<?>)AtonedHorror.class), 
    ATONED_REVENANT(EntityType.ZOMBIE, (Class<?>)AtonedRevenant.class), 
    ATONED_CHAMPION(EntityType.ZOMBIE, (Class<?>)AtonedChampion.class), 
    SVEN_PACKMASTER(EntityType.WOLF, (Class<?>)SvenPackmaster.class, true), 
    SVEN_PUP(EntityType.WOLF, (Class<?>)SvenPup.class, true), 
    SVEN_FOLLOWER(EntityType.WOLF, (Class<?>)SvenFollower.class), 
    PACK_ENFORCER(EntityType.WOLF, (Class<?>)PackEnforcer.class), 
    SVEN_ALPHA(EntityType.WOLF, (Class<?>)SvenAlpha.class), 
    TARANTULA_BROODFATHER(EntityType.SPIDER, (Class<?>)TarantulaBroodfather.class), 
    TOP_CAVE_SPIDER(EntityType.CAVE_SPIDER, (Class<?>)TarantulaBroodfather.TopCaveSpider.class), 
    TARANTULA_VERMIN(EntityType.SPIDER, (Class<?>)TarantulaVermin.class), 
    TARANTULA_BEAST(EntityType.SPIDER, (Class<?>)TarantulaBeast.class), 
    MUTANT_TARANTULA(EntityType.SPIDER, (Class<?>)MutantTarantula.class), 
    WATCHER(EntityType.SKELETON, (Class<?>)Watcher.class), 
    OBSIDIAN_DEFENDER(EntityType.SKELETON, (Class<?>)ObsidianDefender.class), 
    VELOCITY_ARMOR_STAND(EntityType.ARMOR_STAND, (Class<?>)VelocityArmorStand.class), 
    UNCOLLIDABLE_ARMOR_STAND(EntityType.ARMOR_STAND, (Class<?>)UncollidableArmorStand.class), 
    WHEAT_CRYSTAL(EntityType.ARMOR_STAND, (Class<?>)WheatCrystal.class), 
    LAPIS_ZOMBIE(EntityType.ZOMBIE, (Class<?>)LapisZombie.class), 
    PIGMAN(EntityType.PIG_ZOMBIE, (Class<?>)Pigman.class), 
    SMALL_SLIME(EntityType.SLIME, (Class<?>)SmallSlime.class), 
    MEDIUM_SLIME(EntityType.SLIME, (Class<?>)MediumSlime.class), 
    LARGE_SLIME(EntityType.SLIME, (Class<?>)LargeSlime.class), 
    DIAMOND_ZOMBIE(EntityType.ZOMBIE, (Class<?>)DiamondZombie.class), 
    DIAMOND_SKELETON(EntityType.SKELETON, (Class<?>)DiamondSkeleton.class), 
    ENCHANTED_DIAMOND_ZOMBIE(EntityType.ZOMBIE, (Class<?>)EnchantedDiamondZombie.class), 
    ENCHANTED_DIAMOND_SKELETON(EntityType.SKELETON, (Class<?>)EnchantedDiamondSkeleton.class), 
    WEAK_ENDERMAN(EntityType.ENDERMAN, (Class<?>)WeakEnderman.class), 
    ENDERMAN(EntityType.ENDERMAN, (Class<?>)Enderman.class), 
    STRONG_ENDERMAN(EntityType.ENDERMAN, (Class<?>)StrongEnderman.class), 
    SPLITTER_SPIDER(EntityType.SPIDER, (Class<?>)SplitterSpider.class), 
    SILVERFISH(EntityType.SILVERFISH, (Class<?>)Silverfish.class), 
    SPIDER_JOCKEY(EntityType.SPIDER, (Class<?>)SpiderJockey.class), 
    JOCKEY_SKELETON(EntityType.SKELETON, (Class<?>)SpiderJockey.JockeySkeleton.class), 
    CAVE_SPIDER(EntityType.CAVE_SPIDER, (Class<?>)CaveSpider.class), 
    BROOD_MOTHER(EntityType.SPIDER, (Class<?>)BroodMother.class), 
    DASHER_SPIDER(EntityType.SPIDER, (Class<?>)DasherSpider.class), 
    WEAVER_SPIDER(EntityType.SPIDER, (Class<?>)WeaverSpider.class), 
    VORACIOUS_SPIDER(EntityType.SPIDER, (Class<?>)VoraciousSpider.class), 
    ZOMBIE(EntityType.ZOMBIE, (Class<?>)Zombie.class), 
    ZOMBIE_VILLAGER(EntityType.ZOMBIE, (Class<?>)ZombieVillager.class), 
    CRYPT_GHOUL(EntityType.ZOMBIE, (Class<?>)CryptGhoul.class), 
    GOLDEN_GHOUL(EntityType.ZOMBIE, (Class<?>)GoldenGhoul.class), 
    WOLF(EntityType.WOLF, (Class<?>)Wolf.class), 
    OLD_WOLF(EntityType.WOLF, (Class<?>)OldWolf.class), 
    PACK_SPIRIT(EntityType.WOLF, (Class<?>)PackSpirit.class), 
    HOWLING_SPIRIT(EntityType.WOLF, (Class<?>)HowlingSpirit.class), 
    SOUL_OF_THE_ALPHA(EntityType.WOLF, (Class<?>)SoulOfTheAlpha.class), 
    SPIDERS_DEN_SKELETON(EntityType.SKELETON, (Class<?>)SpidersDenSkeleton.class), 
    HIGH_LEVEL_SKELETON(EntityType.SKELETON, (Class<?>)HighLevelSkeleton.class), 
    SPIDERS_DEN_SLIME(EntityType.SLIME, (Class<?>)SpidersDenSlime.class), 
    SNEAKY_CREEPER(EntityType.CREEPER, (Class<?>)SneakyCreeper.class), 
    WITHER_SKELETON(EntityType.SKELETON, (Class<?>)WitherSkeleton.class), 
    SMALL_MAGMA_CUBE(EntityType.MAGMA_CUBE, (Class<?>)SmallMagmaCube.class), 
    MEDIUM_MAGMA_CUBE(EntityType.MAGMA_CUBE, (Class<?>)MediumMagmaCube.class), 
    LARGE_MAGMA_CUBE(EntityType.MAGMA_CUBE, (Class<?>)LargeMagmaCube.class);
    
    private final EntityType craftType;
    private final Class<?> clazz;
    private final boolean specific;
    
    private SEntityType(final EntityType craftType, final Class<?> clazz, final boolean specific) {
        this.craftType = craftType;
        this.clazz = clazz;
        this.specific = specific;
        if (EntityInsentient.class.isAssignableFrom(clazz)) {
            registerEntity(this.name(), craftType.getTypeId(), (Class<? extends EntityInsentient>)clazz);
        }
    }
    
    private SEntityType(final EntityType craftType, final Class<?> clazz) {
        this(craftType, clazz, false);
    }
    
    public EntityStatistics getStatistics() {
        final Object generic = this.getGenericInstance();
        if (generic instanceof EntityStatistics) {
            return (EntityStatistics)generic;
        }
        return null;
    }
    
    public EntityFunction getFunction() {
        final Object generic = this.getGenericInstance();
        if (generic instanceof EntityFunction) {
            return (EntityFunction)generic;
        }
        return null;
    }
    
    public Object instance(final Object... params) {
        try {
            final Class<?>[] paramTypes = new Class[params.length];
            for (int i = 0; i < paramTypes.length; ++i) {
                paramTypes[i] = params[i].getClass();
            }
            return this.clazz.getConstructor(paramTypes).newInstance(params);
        }
        catch (final InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public Object getGenericInstance() {
        try {
            return this.clazz.newInstance();
        }
        catch (final InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    private static void registerEntity(final String name, final int id, final Class<? extends EntityInsentient> clazz) {
        try {
            final List<Map<?, ?>> dataMap = (List<Map<?, ?>>)new ArrayList();
            for (final Field f : EntityTypes.class.getDeclaredFields()) {
                if (f.getType().getSimpleName().equals((Object)Map.class.getSimpleName())) {
                    f.setAccessible(true);
                    dataMap.add((Object)f.get((Object)null));
                }
            }
            if (((Map)dataMap.get(2)).containsKey((Object)id)) {
                ((Map)dataMap.get(0)).remove((Object)name);
                ((Map)dataMap.get(2)).remove((Object)id);
            }
            final Method method = EntityTypes.class.getDeclaredMethod("a", Class.class, String.class, Integer.TYPE);
            method.setAccessible(true);
            method.invoke((Object)null, new Object[] { clazz, name, id });
        }
        catch (final Exception e) {
            e.printStackTrace();
        }
    }
    
    public static SEntityType getEntityType(final String name) {
        return valueOf(name.toUpperCase());
    }
    
    public EntityType getCraftType() {
        return this.craftType;
    }
    
    public Class<?> getClazz() {
        return this.clazz;
    }
    
    public boolean isSpecific() {
        return this.specific;
    }
}
