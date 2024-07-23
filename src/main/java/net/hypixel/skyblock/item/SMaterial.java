package net.hypixel.skyblock.item;

import net.hypixel.skyblock.entity.SEntityType;
import java.util.ArrayList;
import net.hypixel.skyblock.item.entity.BS9;
import net.hypixel.skyblock.item.entity.BS8;
import net.hypixel.skyblock.item.entity.BS7;
import net.hypixel.skyblock.item.entity.BS6;
import net.hypixel.skyblock.item.entity.BS5;
import net.hypixel.skyblock.item.entity.BS4;
import net.hypixel.skyblock.item.entity.BS3;
import net.hypixel.skyblock.item.entity.BS2;
import net.hypixel.skyblock.item.entity.BS1;
import net.hypixel.skyblock.item.entity.Nukekubi;
import net.hypixel.skyblock.item.entity.RevenantHorrorHead2;
import net.hypixel.skyblock.item.entity.AtonedHorrorHead;
import net.hypixel.skyblock.item.entity.JerryGunBullet;
import net.hypixel.skyblock.item.weapon.JerryChineGun;
import net.hypixel.skyblock.item.entity.TerracottaHead;
import net.hypixel.skyblock.item.foraging.DarkOakWood;
import net.hypixel.skyblock.item.foraging.JungleWood;
import net.hypixel.skyblock.item.foraging.BirchWood;
import net.hypixel.skyblock.item.foraging.SpruceWood;
import net.hypixel.skyblock.item.armor.vanilla.golden.GoldenBoots;
import net.hypixel.skyblock.item.armor.vanilla.golden.GoldenLeggings;
import net.hypixel.skyblock.item.armor.vanilla.golden.GoldenChestplate;
import net.hypixel.skyblock.item.armor.vanilla.golden.GoldenHelmet;
import net.hypixel.skyblock.item.armor.vanilla.diamond.DiamondBoots;
import net.hypixel.skyblock.item.armor.vanilla.diamond.DiamondLeggings;
import net.hypixel.skyblock.item.armor.vanilla.diamond.DiamondChestplate;
import net.hypixel.skyblock.item.armor.vanilla.diamond.DiamondHelmet;
import net.hypixel.skyblock.item.armor.vanilla.iron.IronBoots;
import net.hypixel.skyblock.item.armor.vanilla.iron.IronLeggings;
import net.hypixel.skyblock.item.armor.vanilla.iron.IronChestplate;
import net.hypixel.skyblock.item.armor.vanilla.iron.IronHelmet;
import net.hypixel.skyblock.item.armor.vanilla.chainmail.ChainmailBoots;
import net.hypixel.skyblock.item.armor.vanilla.chainmail.ChainmailLeggings;
import net.hypixel.skyblock.item.armor.vanilla.chainmail.ChainmailChestplate;
import net.hypixel.skyblock.item.armor.vanilla.chainmail.ChainmailHelmet;
import net.hypixel.skyblock.item.armor.vanilla.leather.LeatherBoots;
import net.hypixel.skyblock.item.armor.vanilla.leather.LeatherLeggings;
import net.hypixel.skyblock.item.armor.vanilla.leather.LeatherChestplate;
import net.hypixel.skyblock.item.armor.vanilla.leather.LeatherHelmet;
import net.hypixel.skyblock.item.hoe.vanilla.GoldenHoe;
import net.hypixel.skyblock.item.hoe.vanilla.DiamondHoe;
import net.hypixel.skyblock.item.hoe.vanilla.IronHoe;
import net.hypixel.skyblock.item.hoe.vanilla.StoneHoe;
import net.hypixel.skyblock.item.hoe.vanilla.WoodenHoe;
import net.hypixel.skyblock.item.axe.vanilla.GoldenAxe;
import net.hypixel.skyblock.item.pickaxe.vanilla.GoldenPickaxe;
import net.hypixel.skyblock.item.shovel.vanilla.GoldenShovel;
import net.hypixel.skyblock.item.weapon.vanilla.GoldenSword;
import net.hypixel.skyblock.item.axe.vanilla.DiamondAxe;
import net.hypixel.skyblock.item.pickaxe.vanilla.DiamondPickaxe;
import net.hypixel.skyblock.item.shovel.vanilla.DiamondShovel;
import net.hypixel.skyblock.item.weapon.vanilla.DiamondSword;
import net.hypixel.skyblock.item.axe.vanilla.StoneAxe;
import net.hypixel.skyblock.item.pickaxe.vanilla.StonePickaxe;
import net.hypixel.skyblock.item.shovel.vanilla.StoneShovel;
import net.hypixel.skyblock.item.weapon.vanilla.StoneSword;
import net.hypixel.skyblock.item.axe.vanilla.WoodenAxe;
import net.hypixel.skyblock.item.pickaxe.vanilla.WoodenPickaxe;
import net.hypixel.skyblock.item.shovel.vanilla.WoodenShovel;
import net.hypixel.skyblock.item.weapon.vanilla.WoodenSword;
import net.hypixel.skyblock.item.weapon.vanilla.IronSword;
import net.hypixel.skyblock.item.bow.Bow;
import net.hypixel.skyblock.item.axe.vanilla.IronAxe;
import net.hypixel.skyblock.item.pickaxe.vanilla.IronPickaxe;
import net.hypixel.skyblock.item.shovel.vanilla.IronShovel;
import net.hypixel.skyblock.item.foraging.AcaciaWood;
import net.hypixel.skyblock.item.mining.NetherQuartzOre;
import net.hypixel.skyblock.item.farming.Potato;
import net.hypixel.skyblock.item.farming.Carrot;
import net.hypixel.skyblock.item.mining.EmeraldOre;
import net.hypixel.skyblock.item.farming.CocoaBeans;
import net.hypixel.skyblock.item.mining.EndStone;
import net.hypixel.skyblock.item.farming.Melon;
import net.hypixel.skyblock.item.mining.Glowstone;
import net.hypixel.skyblock.item.mining.Netherrack;
import net.hypixel.skyblock.item.farming.Pumpkin;
import net.hypixel.skyblock.item.farming.SugarCane;
import net.hypixel.skyblock.item.farming.Cactus;
import net.hypixel.skyblock.item.mining.Ice;
import net.hypixel.skyblock.item.mining.RedstoneOre;
import net.hypixel.skyblock.item.farming.WheatSeeds;
import net.hypixel.skyblock.item.mining.DiamondBlock;
import net.hypixel.skyblock.item.mining.DiamondOre;
import net.hypixel.skyblock.item.mining.Obsidian;
import net.hypixel.skyblock.item.farming.RedMushroom;
import net.hypixel.skyblock.item.farming.BrownMushroom;
import net.hypixel.skyblock.item.mining.LapisLazuliOre;
import net.hypixel.skyblock.item.foraging.OakWood;
import net.hypixel.skyblock.item.mining.CoalOre;
import net.hypixel.skyblock.item.mining.IronOre;
import net.hypixel.skyblock.item.mining.GoldOre;
import net.hypixel.skyblock.item.mining.Gravel;
import net.hypixel.skyblock.item.mining.Sand;
import net.hypixel.skyblock.item.oddities.Bedrock;
import net.hypixel.skyblock.item.mining.Cobblestone;
import net.hypixel.skyblock.item.mining.Stone;
import net.hypixel.skyblock.item.oddities.WardenSummoningFrame;
import net.hypixel.skyblock.item.oddities.WardenSummoningEye;
import net.hypixel.skyblock.item.oddities.SummoningFrame;
import net.hypixel.skyblock.item.oddities.SleepingEye;
import net.hypixel.skyblock.item.pet.EndermanPet;
import net.hypixel.skyblock.item.rune.CoutureRune;
import net.hypixel.skyblock.item.rune.SpiritRune;
import net.hypixel.skyblock.item.rune.BiteRune;
import net.hypixel.skyblock.item.rune.SnakeRune;
import net.hypixel.skyblock.item.rune.PestilenceRune;
import net.hypixel.skyblock.item.oddities.NullSphere;
import net.hypixel.skyblock.item.sven.GrizzlyBait;
import net.hypixel.skyblock.item.sven.OverfluxCapacitor;
import net.hypixel.skyblock.item.sven.RedClawEgg;
import net.hypixel.skyblock.item.sven.HamsterWheel;
import net.hypixel.skyblock.item.sven.WolfTooth;
import net.hypixel.skyblock.item.tarantula.DigestedMosquito;
import net.hypixel.skyblock.item.tarantula.FlySwatter;
import net.hypixel.skyblock.item.tarantula.SpiderCatalyst;
import net.hypixel.skyblock.item.tarantula.ToxicArrowPoison;
import net.hypixel.skyblock.item.tarantula.TarantulaWeb;
import net.hypixel.skyblock.item.oddities.RevenantViscera;
import net.hypixel.skyblock.item.oddities.ReforgeStone;
import net.hypixel.skyblock.item.oddities.ShardoftheShredded;
import net.hypixel.skyblock.item.oddities.JudgementCore;
import net.hypixel.skyblock.item.oddities.WardenHeart;
import net.hypixel.skyblock.item.revenant.ScytheBlade;
import net.hypixel.skyblock.item.revenant.RevenantCatalyst;
import net.hypixel.skyblock.item.revenant.BeheadedHorror;
import net.hypixel.skyblock.item.revenant.UndeadCatalyst;
import net.hypixel.skyblock.item.revenant.FoulFlesh;
import net.hypixel.skyblock.item.revenant.RevenantFlesh;
import net.hypixel.skyblock.item.storage.JumboBackpack;
import net.hypixel.skyblock.item.storage.GreaterBackpack;
import net.hypixel.skyblock.item.storage.LargeBackpack;
import net.hypixel.skyblock.item.storage.MediumBackpack;
import net.hypixel.skyblock.item.storage.SmallBackpack;
import net.hypixel.skyblock.item.oddities.Recombobulator3000;
import net.hypixel.skyblock.item.enchanting.EnchantedBook;
import net.hypixel.skyblock.item.entity.RevenantHorrorHead;
import net.hypixel.skyblock.item.oddities.WeakWolfCatalyst;
import net.hypixel.skyblock.item.oddities.GoldenPowder;
import net.hypixel.skyblock.item.oddities.CrystalFragment;
import net.hypixel.skyblock.item.oddities.MaddoxBatphone;
import net.hypixel.skyblock.item.oddities.WaterBottle;
import net.hypixel.skyblock.item.oddities.GrapplingHook;
import net.hypixel.skyblock.item.oddities.QuiverArrow;
import net.hypixel.skyblock.item.oddities.SkyBlockMenu;
import net.hypixel.skyblock.item.oddities.BagOfCoins;
import net.hypixel.skyblock.item.enchanted.EnchantedPotato;
import net.hypixel.skyblock.item.enchanted.EnchantedDarkOakWood;
import net.hypixel.skyblock.item.enchanted.EnchantedAcaciaWood;
import net.hypixel.skyblock.item.enchanted.EnchantedJungleWood;
import net.hypixel.skyblock.item.enchanted.EnchantedBirchWood;
import net.hypixel.skyblock.item.enchanted.EnchantedSpruceWood;
import net.hypixel.skyblock.item.enchanted.EnchantedOakWood;
import net.hypixel.skyblock.item.enchanted.EnchantedDiamondBlock;
import net.hypixel.skyblock.item.enchanted.EnchantedDiamond;
import net.hypixel.skyblock.item.enchanted.EnchantedCharcoal;
import net.hypixel.skyblock.item.enchanted.EnchantedCoal;
import net.hypixel.skyblock.item.enchanted.EnchantedEndStone;
import net.hypixel.skyblock.item.enchanted.EnchantedEyeOfEnder;
import net.hypixel.skyblock.item.enchanted.EnchantedEnderPearl;
import net.hypixel.skyblock.item.enchanted.EnchantedObsidian;
import net.hypixel.skyblock.item.enchanted.EnchantedBone;
import net.hypixel.skyblock.item.accessory.BrokenPiggyBank;
import net.hypixel.skyblock.item.accessory.CrackedPiggyBank;
import net.hypixel.skyblock.item.accessory.ArtifactOfControl;
import net.hypixel.skyblock.item.accessory.CandyRelic;
import net.hypixel.skyblock.item.accessory.CrabHatOfCelebration;
import net.hypixel.skyblock.item.accessory.EnderRelic;
import net.hypixel.skyblock.item.accessory.GoldGiftTalisman;
import net.hypixel.skyblock.item.accessory.HegemonyArtifact;
import net.hypixel.skyblock.item.accessory.RingOfLove;
import net.hypixel.skyblock.item.accessory.SlothHatOfCelebration;
import net.hypixel.skyblock.item.accessory.TreasureArtifact;
import net.hypixel.skyblock.item.accessory.TarantulaTalisman;
import net.hypixel.skyblock.item.accessory.RedClawArtifact;
import net.hypixel.skyblock.item.accessory.GoldenJerryArtifact;
import net.hypixel.skyblock.item.accessory.BatArtifact;
import net.hypixel.skyblock.item.armor.enderman.VoidbaneBoots;
import net.hypixel.skyblock.item.armor.enderman.VoidbaneLeggings;
import net.hypixel.skyblock.item.armor.enderman.VoidbaneChestplate;
import net.hypixel.skyblock.item.armor.enderman.VoidbaneHelmet;
import net.hypixel.skyblock.item.accessory.PiggyBank;
import net.hypixel.skyblock.item.accessory.PerfectTalisman;
import net.hypixel.skyblock.item.accessory.AutoRecombobulator;
import net.hypixel.skyblock.item.accessory.SuperspeedTalisman;
import net.hypixel.skyblock.item.armor.BigBounceBoots;
import net.hypixel.skyblock.item.armor.SpidersBoots;
import net.hypixel.skyblock.item.armor.ObsidianChestplate;
import net.hypixel.skyblock.item.armor.hardened.HardenedDiamondBoots;
import net.hypixel.skyblock.item.armor.hardened.HardenedDiamondLeggings;
import net.hypixel.skyblock.item.armor.hardened.HardenedDiamondChestplate;
import net.hypixel.skyblock.item.armor.hardened.HardenedDiamondHelmet;
import net.hypixel.skyblock.item.armor.miner.MinerBoots;
import net.hypixel.skyblock.item.armor.miner.MinerLeggings;
import net.hypixel.skyblock.item.armor.miner.MinerChestplate;
import net.hypixel.skyblock.item.armor.miner.MinerHelmet;
import net.hypixel.skyblock.item.armor.lapis.LapisArmorBoots;
import net.hypixel.skyblock.item.armor.lapis.LapisArmorLeggings;
import net.hypixel.skyblock.item.armor.lapis.LapisArmorChestplate;
import net.hypixel.skyblock.item.armor.lapis.LapisArmorHelmet;
import net.hypixel.skyblock.item.dragon.protector.ProtectorDragonFragment;
import net.hypixel.skyblock.item.dragon.protector.ProtectorDragonBoots;
import net.hypixel.skyblock.item.dragon.protector.ProtectorDragonLeggings;
import net.hypixel.skyblock.item.dragon.protector.ProtectorDragonChestplate;
import net.hypixel.skyblock.item.dragon.protector.ProtectorDragonHelmet;
import net.hypixel.skyblock.item.dragon.old.OldDragonFragment;
import net.hypixel.skyblock.item.dragon.old.OldDragonBoots;
import net.hypixel.skyblock.item.dragon.old.OldDragonLeggings;
import net.hypixel.skyblock.item.dragon.old.OldDragonChestplate;
import net.hypixel.skyblock.item.dragon.old.OldDragonHelmet;
import net.hypixel.skyblock.item.dragon.strong.StrongDragonFragment;
import net.hypixel.skyblock.item.dragon.strong.StrongDragonBoots;
import net.hypixel.skyblock.item.dragon.strong.StrongDragonLeggings;
import net.hypixel.skyblock.item.dragon.strong.StrongDragonChestplate;
import net.hypixel.skyblock.item.dragon.strong.StrongDragonHelmet;
import net.hypixel.skyblock.item.dragon.unstable.UnstableDragonFragment;
import net.hypixel.skyblock.item.dragon.unstable.UnstableDragonBoots;
import net.hypixel.skyblock.item.dragon.unstable.UnstableDragonLeggings;
import net.hypixel.skyblock.item.dragon.unstable.UnstableDragonChestplate;
import net.hypixel.skyblock.item.dragon.unstable.UnstableDragonHelmet;
import net.hypixel.skyblock.item.dragon.superior.SuperiorDragonFragment;
import net.hypixel.skyblock.item.dragon.superior.SuperiorDragonBoots;
import net.hypixel.skyblock.item.dragon.superior.SuperiorDragonLeggings;
import net.hypixel.skyblock.item.dragon.superior.SuperiorDragonChestplate;
import net.hypixel.skyblock.item.dragon.superior.SuperiorDragonHelmet;
import net.hypixel.skyblock.item.dragon.young.YoungDragonFragment;
import net.hypixel.skyblock.item.dragon.young.YoungDragonBoots;
import net.hypixel.skyblock.item.dragon.young.YoungDragonLeggings;
import net.hypixel.skyblock.item.dragon.young.YoungDragonChestplate;
import net.hypixel.skyblock.item.dragon.young.YoungDragonHelmet;
import net.hypixel.skyblock.item.dragon.wise.WiseDragonFragment;
import net.hypixel.skyblock.item.dragon.wise.WiseDragonBoots;
import net.hypixel.skyblock.item.dragon.wise.WiseDragonLeggings;
import net.hypixel.skyblock.item.dragon.wise.WiseDragonChestplate;
import net.hypixel.skyblock.item.dragon.wise.WiseDragonHelmet;
import net.hypixel.skyblock.item.armor.DarkGoggles;
import net.hypixel.skyblock.item.armor.ShadowGoggles;
import net.hypixel.skyblock.item.armor.WitherGoggles;
import net.hypixel.skyblock.item.oddities.RemnantOfTheEye;
import net.hypixel.skyblock.item.oddities.SummoningEye;
import net.hypixel.skyblock.item.oddities.GoldSadanTrophy;
import net.hypixel.skyblock.item.oddities.CreativeMind;
import net.hypixel.skyblock.item.oddities.GameAnnihilator;
import net.hypixel.skyblock.item.oddities.GameBreaker;
import net.hypixel.skyblock.item.oddities.DeadBushOfLove;
import net.hypixel.skyblock.item.oddities.HotPotatoBook;
import net.hypixel.skyblock.item.orb.PlasmafluxPowerOrb;
import net.hypixel.skyblock.item.orb.OverfluxPowerOrb;
import net.hypixel.skyblock.item.orb.ManaFluxPowerOrb;
import net.hypixel.skyblock.item.orb.RadiantPowerOrb;
import net.hypixel.skyblock.item.oddities.GodPot;
import net.hypixel.skyblock.item.oddities.WeirdTuba;
import net.hypixel.skyblock.item.pet.BlackCat;
import net.hypixel.skyblock.item.pet.BabyYeti2;
import net.hypixel.skyblock.item.pet.BabyYeti;
import net.hypixel.skyblock.item.pet.EnderDragonPet2;
import net.hypixel.skyblock.item.pet.EnderDragonPet;
import net.hypixel.skyblock.item.bow.EndStoneBow;
import net.hypixel.skyblock.item.bow.HurricaneBow;
import net.hypixel.skyblock.item.bow.RunaansBow;
import net.hypixel.skyblock.item.bow.MosquitoBow;
import net.hypixel.skyblock.item.bow.DeathBow;
import net.hypixel.skyblock.item.bow.JujuShortBow;
import net.hypixel.skyblock.item.armor.sorrow.SorrowArmorBoots;
import net.hypixel.skyblock.item.armor.sorrow.SorrowArmorLeggings;
import net.hypixel.skyblock.item.armor.sorrow.SorrowArmorChestplate;
import net.hypixel.skyblock.item.armor.sorrow.SorrowArmorHelmet;
import net.hypixel.skyblock.item.oddities.NecronHandle;
import net.hypixel.skyblock.item.armor.TarantulaHelmet;
import net.hypixel.skyblock.item.weapon.AtomsplitKatana;
import net.hypixel.skyblock.item.weapon.VorpalKatana;
import net.hypixel.skyblock.item.weapon.VoidedgeKatana;
import net.hypixel.skyblock.item.weapon.VoidwalkerKatana;
import net.hypixel.skyblock.item.armor.storm.StormBoots;
import net.hypixel.skyblock.item.armor.storm.StormLeggings;
import net.hypixel.skyblock.item.armor.storm.StormChestplate;
import net.hypixel.skyblock.item.armor.storm.StormHelmet;
import net.hypixel.skyblock.item.armor.necron.NecronBoots;
import net.hypixel.skyblock.item.armor.necron.NecronLeggings;
import net.hypixel.skyblock.item.armor.necron.NecronChestplate;
import net.hypixel.skyblock.item.armor.necron.NecronHelmet;
import net.hypixel.skyblock.item.armor.PrecursorEye;
import net.hypixel.skyblock.item.armor.CrownOfGreed;
import net.hypixel.skyblock.item.armor.WardenHelmet;
import net.hypixel.skyblock.item.weapon.RevantusSword;
import net.hypixel.skyblock.item.oddities.ShardoftheDiamondOrb;
import net.hypixel.skyblock.item.weapon.Excrarion;
import net.hypixel.skyblock.item.weapon.PrismarineBlade;
import net.hypixel.skyblock.item.weapon.FloridZombieSword;
import net.hypixel.skyblock.item.weapon.ZombieSword;
import net.hypixel.skyblock.item.weapon.IceSprayWand;
import net.hypixel.skyblock.item.weapon.BonzoStaff;
import net.hypixel.skyblock.item.weapon.FrozenScythe;
import net.hypixel.skyblock.item.weapon.PoochSword;
import net.hypixel.skyblock.item.weapon.ShadowFury;
import net.hypixel.skyblock.item.weapon.AspectOfTheVoid;
import net.hypixel.skyblock.item.weapon.EdibleMace;
import net.hypixel.skyblock.item.weapon.LividDagger;
import net.hypixel.skyblock.item.weapon.FlowerOfTruth;
import net.hypixel.skyblock.item.weapon.ReaperFalchion;
import net.hypixel.skyblock.item.bow.Terminator;
import net.hypixel.skyblock.item.weapon.AxeOfTheShredded;
import net.hypixel.skyblock.item.weapon.Bonemerang;
import net.hypixel.skyblock.item.weapon.MidasStaff;
import net.hypixel.skyblock.item.weapon.GiantSword;
import net.hypixel.skyblock.item.weapon.Scylla;
import net.hypixel.skyblock.item.weapon.SpiritSceptre;
import net.hypixel.skyblock.item.weapon.Valkyrie;
import net.hypixel.skyblock.item.weapon.Hyperion;
import net.hypixel.skyblock.item.weapon.EmeraldBlade;
import net.hypixel.skyblock.item.weapon.RogueSword;
import net.hypixel.skyblock.item.weapon.AspectOfTheJerry;
import net.hypixel.skyblock.item.weapon.AspectOfTheDragons;
import net.hypixel.skyblock.item.weapon.LeapingSword;
import net.hypixel.skyblock.item.weapon.AspectOfTheEnd;
import net.hypixel.skyblock.item.weapon.Dagger;
import net.hypixel.skyblock.item.oddities.VoidFragment;
import net.hypixel.skyblock.item.oddities.RefinedPowder;
import net.hypixel.skyblock.item.pet.TankPet;
import net.hypixel.skyblock.item.pet.MagicivyPet;
import net.hypixel.skyblock.item.pet.GoldenTigerPet;
import net.hypixel.skyblock.item.armor.DonatorHelmet;
import net.hypixel.skyblock.item.armor.BlyatHelmet;
import net.hypixel.skyblock.item.oddities.EtherwarpTranscoder;
import net.hypixel.skyblock.item.oddities.EtherwarpMerger;
import net.hypixel.skyblock.item.oddities.EtherwarpConduit;
import net.hypixel.skyblock.item.oddities.BoosterCookie;
import net.hypixel.skyblock.item.mining.Titanium;
import net.hypixel.skyblock.item.mining.Mitril;
import org.bukkit.inventory.PlayerInventory;
import net.hypixel.skyblock.item.orb.OrbBuff;
import net.hypixel.skyblock.util.SUtil;
import java.util.Iterator;
import java.util.stream.Collectors;
import java.util.Arrays;
import org.bukkit.Material;
import net.hypixel.skyblock.item.armor.sorrow.SorrowArmorSet;
import net.hypixel.skyblock.item.armor.storm.StormFullSet;
import net.hypixel.skyblock.item.armor.necron.NecronFullSet;
import net.hypixel.skyblock.item.armor.miner.MinerSet;
import net.hypixel.skyblock.item.armor.lapis.LapisArmorSet;
import net.hypixel.skyblock.item.dragon.protector.ProtectorDragonSet;
import net.hypixel.skyblock.item.dragon.old.OldDragonSet;
import net.hypixel.skyblock.item.dragon.strong.StrongDragonSet;
import net.hypixel.skyblock.item.dragon.unstable.UnstableDragonSet;
import net.hypixel.skyblock.item.dragon.wise.WiseDragonSet;
import net.hypixel.skyblock.item.dragon.superior.SuperiorDragonSet;
import net.hypixel.skyblock.item.dragon.young.YoungDragonSet;
import net.hypixel.skyblock.item.armor.ArmorSet;
import java.util.List;

public enum SMaterial
{
    DWARVEN_MITRIL(Material.PRISMARINE_CRYSTALS, (Class<?>)Mitril.class), 
    DWARVEN_TITANIUM(Material.SKULL_ITEM, (Class<?>)Titanium.class), 
    HIDDEN_BOOSTER_COOKIE(Material.COOKIE, (Class<?>)BoosterCookie.class), 
    HIDDEN_ETHERWARP_CONDUIT(Material.SKULL_ITEM, (Class<?>)EtherwarpConduit.class), 
    HIDDEN_ETHERWARP_MERGER(Material.SKULL_ITEM, (Class<?>)EtherwarpMerger.class), 
    HIDDEN_ETHERWARP_TRANSCODER(Material.SKULL_ITEM, (Class<?>)EtherwarpTranscoder.class), 
    HIDDEN_USSR_HELMET(Material.STAINED_GLASS, (Class<?>)BlyatHelmet.class, (short)14), 
    HIDDEN_DONATOR_HELMET(Material.STAINED_GLASS, (Class<?>)DonatorHelmet.class, (short)14), 
    HIDDEN_GOLDEN_TIGER_2022(Material.SKULL_ITEM, (Class<?>)GoldenTigerPet.class), 
    HIDDEN_MAGICIVY(Material.SKULL_ITEM, (Class<?>)MagicivyPet.class), 
    HIDDEN_USSR_T34_TANK_PET(Material.SKULL_ITEM, (Class<?>)TankPet.class), 
    HIDDEN_REFINED_POWDER(Material.SULPHUR, (Class<?>)RefinedPowder.class), 
    HIDDEN_VOID_FRAGMENT(Material.EYE_OF_ENDER, (Class<?>)VoidFragment.class), 
    DAGGER(Material.IRON_SWORD, (Class<?>)Dagger.class), 
    ASPECT_OF_THE_END(Material.DIAMOND_SWORD, (Class<?>)AspectOfTheEnd.class), 
    LEAPING_SWORD(Material.GOLD_SWORD, (Class<?>)LeapingSword.class), 
    ASPECT_OF_THE_DRAGONS(Material.DIAMOND_SWORD, (Class<?>)AspectOfTheDragons.class), 
    ASPECT_OF_THE_JERRY(Material.WOOD_SWORD, (Class<?>)AspectOfTheJerry.class), 
    ROGUE_SWORD(Material.GOLD_SWORD, (Class<?>)RogueSword.class), 
    EMERALD_BLADE(Material.EMERALD, (Class<?>)EmeraldBlade.class), 
    HYPERION(Material.IRON_SWORD, (Class<?>)Hyperion.class), 
    VALKYIRE(Material.IRON_SWORD, (Class<?>)Valkyrie.class), 
    SPITIT_SCEPTRE(Material.RED_ROSE, (short)2, (Class<?>)SpiritSceptre.class), 
    SCYLLA(Material.IRON_SWORD, (Class<?>)Scylla.class), 
    GIANTS_SWORD(Material.IRON_SWORD, (Class<?>)GiantSword.class), 
    MIDAS_STAFF(Material.GOLD_SPADE, (Class<?>)MidasStaff.class), 
    BONEMERANG(Material.BONE, (Class<?>)Bonemerang.class), 
    AXE_OF_THE_SHREDDED(Material.DIAMOND_AXE, (Class<?>)AxeOfTheShredded.class), 
    TERMINATOR(Material.BOW, (Class<?>)Terminator.class), 
    REAPER_FALCHION(Material.DIAMOND_SWORD, (Class<?>)ReaperFalchion.class), 
    FLOWER_OF_TRUTH(Material.RED_ROSE, (Class<?>)FlowerOfTruth.class), 
    LIVID_DAGGER(Material.IRON_SWORD, (Class<?>)LividDagger.class), 
    EDIBLE_MACE(Material.MUTTON, (Class<?>)EdibleMace.class), 
    ASPECT_OF_THE_VOID(Material.DIAMOND_SPADE, (Class<?>)AspectOfTheVoid.class), 
    SHADOW_FURY(Material.DIAMOND_SWORD, (Class<?>)ShadowFury.class), 
    POOCH_SWORD(Material.GOLD_SWORD, (Class<?>)PoochSword.class), 
    FROZEN_SCYTHE(Material.IRON_HOE, (Class<?>)FrozenScythe.class), 
    BONZO_STAFF(Material.BLAZE_ROD, (Class<?>)BonzoStaff.class), 
    ICE_WAND(Material.STICK, (Class<?>)IceSprayWand.class), 
    ZOMBIE_SWORD_T2(Material.GOLD_SWORD, (Class<?>)ZombieSword.class), 
    ZOMBIE_SWORD_T3(Material.GOLD_SWORD, (Class<?>)FloridZombieSword.class), 
    PRISMARINE_BLADE(Material.PRISMARINE_SHARD, (Class<?>)PrismarineBlade.class), 
    HIDDEN_EXCRARION(Material.GOLD_SWORD, (Class<?>)Excrarion.class), 
    HIDDEN_SHARD_DIAMOND(Material.SKULL_ITEM, (Class<?>)ShardoftheDiamondOrb.class), 
    HIDDEN_REVANTUS_SWORD(Material.IRON_SWORD, (Class<?>)RevantusSword.class), 
    WARDEN_HELMET(Material.SKULL_ITEM, (Class<?>)WardenHelmet.class), 
    CROWN_OF_GREED(Material.GOLD_HELMET, (Class<?>)CrownOfGreed.class), 
    PRECURSOR_EYE(Material.SKULL_ITEM, (Class<?>)PrecursorEye.class), 
    NECRON_HELMET(Material.SKULL_ITEM, (Class<?>)NecronHelmet.class), 
    NECRON_CHESTPLATE(Material.LEATHER_CHESTPLATE, (Class<?>)NecronChestplate.class), 
    NECRON_LEGGINGS(Material.LEATHER_LEGGINGS, (Class<?>)NecronLeggings.class), 
    NECRON_BOOTS(Material.LEATHER_BOOTS, (Class<?>)NecronBoots.class), 
    STORM_HELMET(Material.SKULL_ITEM, (Class<?>)StormHelmet.class), 
    STORM_CHESTPLATE(Material.LEATHER_CHESTPLATE, (Class<?>)StormChestplate.class), 
    STORM_LEGGINGS(Material.LEATHER_LEGGINGS, (Class<?>)StormLeggings.class), 
    STORM_BOOTS(Material.LEATHER_BOOTS, (Class<?>)StormBoots.class), 
    VOIDWALKER_KATANA(Material.IRON_SWORD, (Class<?>)VoidwalkerKatana.class), 
    VOIDEDGE_KATANA(Material.DIAMOND_SWORD, (Class<?>)VoidedgeKatana.class), 
    VORPAL_KATANA(Material.DIAMOND_SWORD, (Class<?>)VorpalKatana.class), 
    ATOMSPLIT_KATANA(Material.DIAMOND_SWORD, (Class<?>)AtomsplitKatana.class), 
    TARANTULA_HELMET(Material.LEATHER_HELMET, (Class<?>)TarantulaHelmet.class), 
    NECRONS_HANDLE(Material.STICK, (Class<?>)NecronHandle.class), 
    SORROW_HELMET(Material.CHAINMAIL_HELMET, (Class<?>)SorrowArmorHelmet.class), 
    SORROW_CHESTPLATE(Material.CHAINMAIL_CHESTPLATE, (Class<?>)SorrowArmorChestplate.class), 
    SORROW_LEGGINGS(Material.CHAINMAIL_LEGGINGS, (Class<?>)SorrowArmorLeggings.class), 
    SORROW_BOOTS(Material.CHAINMAIL_BOOTS, (Class<?>)SorrowArmorBoots.class), 
    JUJU_SHORTBOW(Material.BOW, (Class<?>)JujuShortBow.class), 
    DEATH_BOW(Material.BOW, (Class<?>)DeathBow.class), 
    MOSQUITO_BOW(Material.BOW, (Class<?>)MosquitoBow.class), 
    RUNAANS_BOW(Material.BOW, (Class<?>)RunaansBow.class), 
    HURRICANE_BOW(Material.BOW, (Class<?>)HurricaneBow.class), 
    END_STONE_BOW(Material.BOW, (Class<?>)EndStoneBow.class), 
    ENDER_DRAGON_PET(Material.SKULL_ITEM, (Class<?>)EnderDragonPet.class), 
    ENDER_DRAGON_PET2(Material.SKULL_ITEM, (Class<?>)EnderDragonPet2.class), 
    BABY_YETI_PET(Material.SKULL_ITEM, (Class<?>)BabyYeti.class), 
    BABY_YETI_PET2(Material.SKULL_ITEM, (Class<?>)BabyYeti2.class), 
    BLACK_CAT_PET(Material.SKULL_ITEM, (Class<?>)BlackCat.class), 
    WEIRD_TUBA(Material.HOPPER, (Class<?>)WeirdTuba.class), 
    GOD_POT(Material.SKULL_ITEM, (Class<?>)GodPot.class), 
    RADIANT_POWER_ORB(Material.SKULL_ITEM, (Class<?>)RadiantPowerOrb.class), 
    MANA_FLUX_POWER_ORB(Material.SKULL_ITEM, (Class<?>)ManaFluxPowerOrb.class), 
    OVERFLUX_POWER_ORB(Material.SKULL_ITEM, (Class<?>)OverfluxPowerOrb.class), 
    PLASMAFLUX_POWER_ORB(Material.SKULL_ITEM, (Class<?>)PlasmafluxPowerOrb.class), 
    HOT_POTATO_BOOK(Material.BOOK, (Class<?>)HotPotatoBook.class), 
    DEAD_BUSH_OF_LOVE(Material.DEAD_BUSH, (Class<?>)DeadBushOfLove.class), 
    GAME_BREAKER(Material.TNT, (Class<?>)GameBreaker.class), 
    GAME_ANNIHILATOR(Material.SKULL_ITEM, (Class<?>)GameAnnihilator.class), 
    CREATIVE_MIND(Material.PAINTING, (Class<?>)CreativeMind.class), 
    GOLDEN_TROPHY_SADAN(Material.SKULL_ITEM, (Class<?>)GoldSadanTrophy.class), 
    SUMMONING_EYE(Material.SKULL_ITEM, (Class<?>)SummoningEye.class), 
    REMNANT_OF_THE_EYE(Material.SKULL_ITEM, (Class<?>)RemnantOfTheEye.class), 
    WITHER_GOGGLES(Material.SKULL_ITEM, (Class<?>)WitherGoggles.class), 
    SHADOW_GOGGLES(Material.SKULL_ITEM, (Class<?>)ShadowGoggles.class), 
    DARK_GOGGLES(Material.SKULL_ITEM, (Class<?>)DarkGoggles.class), 
    WISE_DRAGON_HELMET(Material.SKULL_ITEM, (Class<?>)WiseDragonHelmet.class), 
    WISE_DRAGON_CHESTPLATE(Material.LEATHER_CHESTPLATE, (Class<?>)WiseDragonChestplate.class), 
    WISE_DRAGON_LEGGINGS(Material.LEATHER_LEGGINGS, (Class<?>)WiseDragonLeggings.class), 
    WISE_DRAGON_BOOTS(Material.LEATHER_BOOTS, (Class<?>)WiseDragonBoots.class), 
    WISE_DRAGON_FRAGMENT(Material.SKULL_ITEM, (Class<?>)WiseDragonFragment.class), 
    YOUNG_DRAGON_HELMET(Material.SKULL_ITEM, (Class<?>)YoungDragonHelmet.class), 
    YOUNG_DRAGON_CHESTPLATE(Material.LEATHER_CHESTPLATE, (Class<?>)YoungDragonChestplate.class), 
    YOUNG_DRAGON_LEGGINGS(Material.LEATHER_LEGGINGS, (Class<?>)YoungDragonLeggings.class), 
    YOUNG_DRAGON_BOOTS(Material.LEATHER_BOOTS, (Class<?>)YoungDragonBoots.class), 
    YOUNG_DRAGON_FRAGMENT(Material.SKULL_ITEM, (Class<?>)YoungDragonFragment.class), 
    SUPERIOR_DRAGON_HELMET(Material.SKULL_ITEM, (Class<?>)SuperiorDragonHelmet.class), 
    SUPERIOR_DRAGON_CHESTPLATE(Material.LEATHER_CHESTPLATE, (Class<?>)SuperiorDragonChestplate.class), 
    SUPERIOR_DRAGON_LEGGINGS(Material.LEATHER_LEGGINGS, (Class<?>)SuperiorDragonLeggings.class), 
    SUPERIOR_DRAGON_BOOTS(Material.LEATHER_BOOTS, (Class<?>)SuperiorDragonBoots.class), 
    SUPERIOR_DRAGON_FRAGMENT(Material.SKULL_ITEM, (Class<?>)SuperiorDragonFragment.class), 
    UNSTABLE_DRAGON_HELMET(Material.SKULL_ITEM, (Class<?>)UnstableDragonHelmet.class), 
    UNSTABLE_DRAGON_CHESTPLATE(Material.LEATHER_CHESTPLATE, (Class<?>)UnstableDragonChestplate.class), 
    UNSTABLE_DRAGON_LEGGINGS(Material.LEATHER_LEGGINGS, (Class<?>)UnstableDragonLeggings.class), 
    UNSTABLE_DRAGON_BOOTS(Material.LEATHER_BOOTS, (Class<?>)UnstableDragonBoots.class), 
    UNSTABLE_DRAGON_FRAGMENT(Material.SKULL_ITEM, (Class<?>)UnstableDragonFragment.class), 
    STRONG_DRAGON_HELMET(Material.SKULL_ITEM, (Class<?>)StrongDragonHelmet.class), 
    STRONG_DRAGON_CHESTPLATE(Material.LEATHER_CHESTPLATE, (Class<?>)StrongDragonChestplate.class), 
    STRONG_DRAGON_LEGGINGS(Material.LEATHER_LEGGINGS, (Class<?>)StrongDragonLeggings.class), 
    STRONG_DRAGON_BOOTS(Material.LEATHER_BOOTS, (Class<?>)StrongDragonBoots.class), 
    STRONG_DRAGON_FRAGMENT(Material.SKULL_ITEM, (Class<?>)StrongDragonFragment.class), 
    OLD_DRAGON_HELMET(Material.SKULL_ITEM, (Class<?>)OldDragonHelmet.class), 
    OLD_DRAGON_CHESTPLATE(Material.LEATHER_CHESTPLATE, (Class<?>)OldDragonChestplate.class), 
    OLD_DRAGON_LEGGINGS(Material.LEATHER_LEGGINGS, (Class<?>)OldDragonLeggings.class), 
    OLD_DRAGON_BOOTS(Material.LEATHER_BOOTS, (Class<?>)OldDragonBoots.class), 
    OLD_DRAGON_FRAGMENT(Material.SKULL_ITEM, (Class<?>)OldDragonFragment.class), 
    PROTECTOR_DRAGON_HELMET(Material.SKULL_ITEM, (Class<?>)ProtectorDragonHelmet.class), 
    PROTECTOR_DRAGON_CHESTPLATE(Material.LEATHER_CHESTPLATE, (Class<?>)ProtectorDragonChestplate.class), 
    PROTECTOR_DRAGON_LEGGINGS(Material.LEATHER_LEGGINGS, (Class<?>)ProtectorDragonLeggings.class), 
    PROTECTOR_DRAGON_BOOTS(Material.LEATHER_BOOTS, (Class<?>)ProtectorDragonBoots.class), 
    PROTECTOR_DRAGON_FRAGMENT(Material.SKULL_ITEM, (Class<?>)ProtectorDragonFragment.class), 
    LAPIS_ARMOR_HELMET(Material.SEA_LANTERN, (Class<?>)LapisArmorHelmet.class), 
    LAPIS_ARMOR_CHESTPLATE(Material.LEATHER_CHESTPLATE, (Class<?>)LapisArmorChestplate.class), 
    LAPIS_ARMOR_LEGGINGS(Material.LEATHER_LEGGINGS, (Class<?>)LapisArmorLeggings.class), 
    LAPIS_ARMOR_BOOTS(Material.LEATHER_BOOTS, (Class<?>)LapisArmorBoots.class), 
    MINER_HELMET(Material.DIAMOND_HELMET, (Class<?>)MinerHelmet.class), 
    MINER_CHESTPLATE(Material.DIAMOND_CHESTPLATE, (Class<?>)MinerChestplate.class), 
    MINER_LEGGINGS(Material.DIAMOND_LEGGINGS, (Class<?>)MinerLeggings.class), 
    MINER_BOOTS(Material.DIAMOND_BOOTS, (Class<?>)MinerBoots.class), 
    HARDENED_DIAMOND_HELMET(Material.DIAMOND_HELMET, (Class<?>)HardenedDiamondHelmet.class), 
    HARDENED_DIAMOND_CHESTPLATE(Material.DIAMOND_CHESTPLATE, (Class<?>)HardenedDiamondChestplate.class), 
    HARDENED_DIAMOND_LEGGINGS(Material.DIAMOND_LEGGINGS, (Class<?>)HardenedDiamondLeggings.class), 
    HARDENED_DIAMOND_BOOTS(Material.DIAMOND_BOOTS, (Class<?>)HardenedDiamondBoots.class), 
    OBSIDIAN_CHESTPLATE(Material.LEATHER_CHESTPLATE, (Class<?>)ObsidianChestplate.class), 
    SPIDERS_BOOTS(Material.IRON_BOOTS, (Class<?>)SpidersBoots.class), 
    BIG_BOUNCE_BOOTS(Material.LEATHER_BOOTS, (Class<?>)BigBounceBoots.class), 
    SUPERSPEED_TALISMAN(Material.SKULL_ITEM, (Class<?>)SuperspeedTalisman.class), 
    AUTO_RECOMBOBULATOR(Material.SKULL_ITEM, (Class<?>)AutoRecombobulator.class), 
    PERFECT_TALISMAN(Material.SKULL_ITEM, (Class<?>)PerfectTalisman.class), 
    PIGGY_BANK(Material.SKULL_ITEM, (Class<?>)PiggyBank.class), 
    VOIDBANE_HELMET(Material.SKULL_ITEM, (Class<?>)VoidbaneHelmet.class), 
    VOIDBANE_CHESTPLATE(Material.LEATHER_CHESTPLATE, (Class<?>)VoidbaneChestplate.class), 
    VOIDBANE_LEGGINGS(Material.LEATHER_LEGGINGS, (Class<?>)VoidbaneLeggings.class), 
    VOIDBANE_BOOTS(Material.LEATHER_BOOTS, (Class<?>)VoidbaneBoots.class), 
    BAT_ARTIFACT(Material.SKULL_ITEM, (Class<?>)BatArtifact.class), 
    JERRY_ARTIFACT(Material.SKULL_ITEM, (Class<?>)GoldenJerryArtifact.class), 
    RED_CLAW_ARTIFACT(Material.SKULL_ITEM, (Class<?>)RedClawArtifact.class), 
    TARANTULA_TALISMAN(Material.SKULL_ITEM, (Class<?>)TarantulaTalisman.class), 
    TREASURE_ARTIFACT(Material.SKULL_ITEM, (Class<?>)TreasureArtifact.class), 
    SLOTH_HAT_OF_CELEBRATION(Material.SKULL_ITEM, (Class<?>)SlothHatOfCelebration.class), 
    RING_OF_LOVE(Material.SKULL_ITEM, (Class<?>)RingOfLove.class), 
    HEGEMONY_ARTIFACT(Material.SKULL_ITEM, (Class<?>)HegemonyArtifact.class), 
    GOLD_GIFT_TALISMAN(Material.SKULL_ITEM, (Class<?>)GoldGiftTalisman.class), 
    ENDER_RELIC(Material.SKULL_ITEM, (Class<?>)EnderRelic.class), 
    CRAB_HAT_OF_CELEBRATION(Material.SKULL_ITEM, (Class<?>)CrabHatOfCelebration.class), 
    CANDY_RELIC(Material.SKULL_ITEM, (Class<?>)CandyRelic.class), 
    ARTIFACT_OF_CONTROL(Material.SKULL_ITEM, (Class<?>)ArtifactOfControl.class), 
    CRACKED_PIGGY_BANK(Material.SKULL_ITEM, (Class<?>)CrackedPiggyBank.class), 
    BROKEN_PIGGY_BANK(Material.SKULL_ITEM, (Class<?>)BrokenPiggyBank.class), 
    ENCHANTED_BONE(Material.BONE, (Class<?>)EnchantedBone.class), 
    ENCHANTED_OBSIDIAN(Material.OBSIDIAN, (Class<?>)EnchantedObsidian.class), 
    ENCHANTED_ENDER_PEARL(Material.ENDER_PEARL, (Class<?>)EnchantedEnderPearl.class), 
    ENCHANTED_EYE_OF_ENDER(Material.EYE_OF_ENDER, (Class<?>)EnchantedEyeOfEnder.class), 
    ENCHANTED_END_STONE(Material.ENDER_STONE, (Class<?>)EnchantedEndStone.class), 
    ENCHANTED_COAL(Material.COAL, (Class<?>)EnchantedCoal.class), 
    ENCHANTED_CHARCOAL(Material.COAL, (Class<?>)EnchantedCharcoal.class, (short)1), 
    ENCHANTED_DIAMOND(Material.DIAMOND, (Class<?>)EnchantedDiamond.class), 
    ENCHANTED_DIAMOND_BLOCK(Material.DIAMOND_BLOCK, (Class<?>)EnchantedDiamondBlock.class), 
    ENCHANTED_OAK_WOOD(Material.LOG, (Class<?>)EnchantedOakWood.class), 
    ENCHANTED_SPRUCE_WOOD(Material.LOG, (Class<?>)EnchantedSpruceWood.class, (short)1), 
    ENCHANTED_BIRCH_WOOD(Material.LOG, (Class<?>)EnchantedBirchWood.class, (short)2), 
    ENCHANTED_JUNGLE_WOOD(Material.LOG, (Class<?>)EnchantedJungleWood.class, (short)3), 
    ENCHANTED_ACACIA_WOOD(Material.LOG_2, (Class<?>)EnchantedAcaciaWood.class), 
    ENCHANTED_DARK_OAK_WOOD(Material.LOG_2, (Class<?>)EnchantedDarkOakWood.class, (short)1), 
    ENCHANTED_POTATO(Material.POTATO_ITEM, (Class<?>)EnchantedPotato.class), 
    ENCHANTED_BAKED_POTATO(Material.BAKED_POTATO, (Class<?>)EnchantedPotato.class), 
    BAG_OF_COINS(Material.SKULL_ITEM, (Class<?>)BagOfCoins.class), 
    SKYBLOCK_MENU(Material.NETHER_STAR, (Class<?>)SkyBlockMenu.class), 
    QUIVER_ARROW(Material.ARROW, (Class<?>)QuiverArrow.class), 
    GRAPPLING_HOOK(Material.FISHING_ROD, (Class<?>)GrapplingHook.class), 
    WATER_BOTTLE(Material.POTION, (Class<?>)WaterBottle.class), 
    MADDOX_BATPHONE(Material.SKULL_ITEM, (Class<?>)MaddoxBatphone.class), 
    CRYSTAL_FRAGMENT(Material.QUARTZ, (Class<?>)CrystalFragment.class), 
    GOLDEN_POWDER(Material.GLOWSTONE_DUST, (Class<?>)GoldenPowder.class), 
    WEAK_WOLF_CATALYST(Material.BONE, (Class<?>)WeakWolfCatalyst.class), 
    REVENANT_HORROR_HEAD(Material.SKULL_ITEM, (Class<?>)RevenantHorrorHead.class), 
    ENCHANTED_BOOK(Material.ENCHANTED_BOOK, (Class<?>)EnchantedBook.class, true), 
    RECOMBOBULATOR_3000(Material.SKULL_ITEM, (Class<?>)Recombobulator3000.class), 
    SMALL_BACKPACK(Material.SKULL_ITEM, (Class<?>)SmallBackpack.class), 
    MEDIUM_BACKPACK(Material.SKULL_ITEM, (Class<?>)MediumBackpack.class), 
    LARGE_BACKPACK(Material.SKULL_ITEM, (Class<?>)LargeBackpack.class), 
    GREATER_BACKPACK(Material.SKULL_ITEM, (Class<?>)GreaterBackpack.class), 
    JUMBO_BACKPACK(Material.SKULL_ITEM, (Class<?>)JumboBackpack.class), 
    REVENANT_FLESH(Material.ROTTEN_FLESH, (Class<?>)RevenantFlesh.class), 
    FOUL_FLESH(Material.COAL, (Class<?>)FoulFlesh.class, (short)1), 
    UNDEAD_CATALYST(Material.SKULL_ITEM, (Class<?>)UndeadCatalyst.class), 
    BEHEADED_HORROR(Material.SKULL_ITEM, (Class<?>)BeheadedHorror.class), 
    REVENANT_CATALYST(Material.SKULL_ITEM, (Class<?>)RevenantCatalyst.class), 
    SCYTHE_BLADE(Material.DIAMOND, (Class<?>)ScytheBlade.class), 
    WARDEN_HEART(Material.SKULL_ITEM, (Class<?>)WardenHeart.class), 
    JUDGEMENT_CORE(Material.SKULL_ITEM, (Class<?>)JudgementCore.class), 
    SHARD_OF_THE_SHREDDED(Material.SKULL_ITEM, (Class<?>)ShardoftheShredded.class), 
    REFORGE_STONE(Material.SKULL_ITEM, (Class<?>)ReforgeStone.class), 
    REVENANT_VISCERA(Material.GRILLED_PORK, (Class<?>)RevenantViscera.class), 
    TARANTULA_WEB(Material.STRING, (Class<?>)TarantulaWeb.class), 
    TOXIC_ARROW_POISON(Material.INK_SACK, (Class<?>)ToxicArrowPoison.class, (short)10), 
    SPIDER_CATALYST(Material.SKULL_ITEM, (Class<?>)SpiderCatalyst.class), 
    FLY_SWATTER(Material.GOLD_SPADE, (Class<?>)FlySwatter.class), 
    DIGESTED_MOSQUITO(Material.ROTTEN_FLESH, (Class<?>)DigestedMosquito.class), 
    WOLF_TOOTH(Material.GHAST_TEAR, (Class<?>)WolfTooth.class), 
    HAMSTER_WHEEL(Material.TRAP_DOOR, (Class<?>)HamsterWheel.class), 
    RED_CLAW_EGG(Material.MONSTER_EGG, (Class<?>)RedClawEgg.class, (short)96), 
    OVERFLUX_CAPACITOR(Material.QUARTZ, (Class<?>)OverfluxCapacitor.class), 
    GRIZZLY_BAIT(Material.RAW_FISH, (Class<?>)GrizzlyBait.class, (short)1), 
    NULL_SPHERE(Material.FIREWORK_CHARGE, (Class<?>)NullSphere.class), 
    PESTILENCE_RUNE(Material.SKULL_ITEM, (Class<?>)PestilenceRune.class), 
    SNAKE_RUNE(Material.SKULL_ITEM, (Class<?>)SnakeRune.class), 
    BITE_RUNE(Material.SKULL_ITEM, (Class<?>)BiteRune.class), 
    SPIRIT_RUNE(Material.SKULL_ITEM, (Class<?>)SpiritRune.class), 
    COUTURE_RUNE(Material.SKULL_ITEM, (Class<?>)CoutureRune.class), 
    ENDERMAN_PET(Material.SKULL_ITEM, (Class<?>)EndermanPet.class), 
    SLEEPING_EYE(Material.SKULL_ITEM, (Class<?>)SleepingEye.class), 
    SUMMONING_FRAME(Material.ENDER_PORTAL_FRAME, (Class<?>)SummoningFrame.class), 
    WARDEN_SUMMONING_EYE(Material.SKULL_ITEM, (Class<?>)WardenSummoningEye.class), 
    WARDEN_SUMMONING_FRAME(Material.ENDER_PORTAL_FRAME, (Class<?>)WardenSummoningFrame.class), 
    AIR(Material.AIR), 
    STONE(Material.STONE, (Class<?>)Stone.class, true), 
    GRASS_BLOCK(Material.GRASS), 
    DIRT(Material.DIRT), 
    COBBLESTONE(Material.COBBLESTONE, (Class<?>)Cobblestone.class, true), 
    OAK_WOOD_PLANKS(Material.WOOD), 
    SAPLING(Material.SAPLING), 
    BEDROCK(Material.BEDROCK, (Class<?>)Bedrock.class, true), 
    WATER(Material.WATER), 
    STATIONARY_WATER(Material.STATIONARY_WATER), 
    LAVA(Material.LAVA), 
    STATIONARY_LAVA(Material.STATIONARY_LAVA), 
    SAND(Material.SAND, (Class<?>)Sand.class, true), 
    GRAVEL(Material.GRAVEL, (Class<?>)Gravel.class, true), 
    GOLD_ORE(Material.GOLD_ORE, (Class<?>)GoldOre.class, true), 
    IRON_ORE(Material.IRON_ORE, (Class<?>)IronOre.class, true), 
    COAL_ORE(Material.COAL_ORE, (Class<?>)CoalOre.class, true), 
    OAK_WOOD(Material.LOG, (Class<?>)OakWood.class, true), 
    LEAVES(Material.LEAVES), 
    SPONGE(Material.SPONGE), 
    GLASS(Material.GLASS), 
    LAPIS_LAZULI_ORE(Material.LAPIS_ORE, (Class<?>)LapisLazuliOre.class, true), 
    LAPIS_BLOCK(Material.LAPIS_BLOCK), 
    DISPENSER(Material.DISPENSER), 
    SANDSTONE(Material.SANDSTONE), 
    NOTE_BLOCK(Material.NOTE_BLOCK), 
    BED_BLOCK(Material.BED_BLOCK), 
    POWERED_RAIL(Material.POWERED_RAIL), 
    DETECTOR_RAIL(Material.DETECTOR_RAIL), 
    PISTON_STICKY_BASE(Material.PISTON_STICKY_BASE), 
    WEB(Material.WEB), 
    LONG_GRASS(Material.LONG_GRASS), 
    DEAD_BUSH(Material.DEAD_BUSH), 
    PISTON_BASE(Material.PISTON_BASE), 
    PISTON_EXTENSION(Material.PISTON_EXTENSION), 
    WOOL(Material.WOOL), 
    PISTON_MOVING_PIECE(Material.PISTON_MOVING_PIECE), 
    YELLOW_FLOWER(Material.YELLOW_FLOWER), 
    RED_ROSE(Material.RED_ROSE), 
    BROWN_MUSHROOM(Material.BROWN_MUSHROOM, (Class<?>)BrownMushroom.class, true), 
    RED_MUSHROOM(Material.RED_MUSHROOM, (Class<?>)RedMushroom.class, true), 
    GOLD_BLOCK(Material.GOLD_BLOCK), 
    IRON_BLOCK(Material.IRON_BLOCK), 
    DOUBLE_STEP(Material.DOUBLE_STEP), 
    STEP(Material.STEP), 
    BRICK(Material.BRICK), 
    TNT(Material.TNT), 
    BOOKSHELF(Material.BOOKSHELF), 
    MOSSY_COBBLESTONE(Material.MOSSY_COBBLESTONE), 
    OBSIDIAN(Material.OBSIDIAN, (Class<?>)Obsidian.class, true), 
    TORCH(Material.TORCH), 
    FIRE(Material.FIRE), 
    MOB_SPAWNER(Material.MOB_SPAWNER), 
    WOOD_STAIRS(Material.WOOD_STAIRS), 
    CHEST(Material.CHEST), 
    REDSTONE_WIRE(Material.REDSTONE_WIRE), 
    DIAMOND_ORE(Material.DIAMOND_ORE, (Class<?>)DiamondOre.class, true), 
    DIAMOND_BLOCK(Material.DIAMOND_BLOCK, (Class<?>)DiamondBlock.class, true), 
    CRAFTING_TABLE(Material.WORKBENCH), 
    WHEAT_SEEDS(Material.CROPS, (Class<?>)WheatSeeds.class, true), 
    SOIL(Material.SOIL), 
    FURNACE(Material.FURNACE), 
    BURNING_FURNACE(Material.BURNING_FURNACE), 
    SIGN_POST(Material.SIGN_POST), 
    WOODEN_DOOR(Material.WOODEN_DOOR), 
    LADDER(Material.LADDER), 
    RAILS(Material.RAILS), 
    COBBLESTONE_STAIRS(Material.COBBLESTONE_STAIRS), 
    WALL_SIGN(Material.WALL_SIGN), 
    LEVER(Material.LEVER), 
    STONE_PLATE(Material.STONE_PLATE), 
    IRON_DOOR_BLOCK(Material.IRON_DOOR_BLOCK), 
    WOOD_PLATE(Material.WOOD_PLATE), 
    REDSTONE_ORE(Material.REDSTONE_ORE, (Class<?>)RedstoneOre.class, true), 
    GLOWING_REDSTONE_ORE(Material.GLOWING_REDSTONE_ORE), 
    REDSTONE_TORCH_OFF(Material.REDSTONE_TORCH_OFF), 
    REDSTONE_TORCH_ON(Material.REDSTONE_TORCH_ON), 
    STONE_BUTTON(Material.STONE_BUTTON), 
    SNOW(Material.SNOW), 
    ICE(Material.ICE, (Class<?>)Ice.class, true), 
    SNOW_BLOCK(Material.SNOW_BLOCK), 
    CACTUS(Material.CACTUS, (Class<?>)Cactus.class, true), 
    CLAY(Material.CLAY), 
    SUGAR_CANE_BLOCK(Material.SUGAR_CANE_BLOCK, (Class<?>)SugarCane.class, true), 
    JUKEBOX(Material.JUKEBOX), 
    FENCE(Material.FENCE), 
    PUMPKIN(Material.PUMPKIN, (Class<?>)Pumpkin.class, true), 
    NETHERRACK(Material.NETHERRACK, (Class<?>)Netherrack.class, true), 
    SOUL_SAND(Material.SOUL_SAND), 
    GLOWSTONE(Material.GLOWSTONE, (Class<?>)Glowstone.class, true), 
    PORTAL(Material.PORTAL), 
    JACK_O_LANTERN(Material.JACK_O_LANTERN), 
    CAKE_BLOCK(Material.CAKE_BLOCK), 
    DIODE_BLOCK_OFF(Material.DIODE_BLOCK_OFF), 
    DIODE_BLOCK_ON(Material.DIODE_BLOCK_ON), 
    STAINED_GLASS(Material.STAINED_GLASS), 
    TRAP_DOOR(Material.TRAP_DOOR), 
    MONSTER_EGGS(Material.MONSTER_EGGS), 
    SMOOTH_BRICK(Material.SMOOTH_BRICK), 
    HUGE_MUSHROOM_1(Material.HUGE_MUSHROOM_1), 
    HUGE_MUSHROOM_2(Material.HUGE_MUSHROOM_2), 
    IRON_FENCE(Material.IRON_FENCE), 
    THIN_GLASS(Material.THIN_GLASS), 
    MELON_BLOCK(Material.MELON_BLOCK, (Class<?>)Melon.class, true), 
    PUMPKIN_STEM(Material.PUMPKIN_STEM), 
    MELON_STEM(Material.MELON_STEM), 
    VINE(Material.VINE), 
    FENCE_GATE(Material.FENCE_GATE), 
    BRICK_STAIRS(Material.BRICK_STAIRS), 
    SMOOTH_STAIRS(Material.SMOOTH_STAIRS), 
    MYCEL(Material.MYCEL), 
    WATER_LILY(Material.WATER_LILY), 
    NETHER_BRICK(Material.NETHER_BRICK), 
    NETHER_FENCE(Material.NETHER_FENCE), 
    NETHER_BRICK_STAIRS(Material.NETHER_BRICK_STAIRS), 
    NETHER_WARTS(Material.NETHER_WARTS), 
    ENCHANTMENT_TABLE(Material.ENCHANTMENT_TABLE), 
    BREWING_STAND(Material.BREWING_STAND), 
    CAULDRON(Material.CAULDRON), 
    END_PORTAL(Material.ENDER_PORTAL), 
    END_PORTAL_FRAME(Material.ENDER_PORTAL_FRAME), 
    END_STONE(Material.ENDER_STONE, (Class<?>)EndStone.class, true), 
    DRAGON_EGG(Material.DRAGON_EGG), 
    REDSTONE_LAMP_OFF(Material.REDSTONE_LAMP_OFF), 
    REDSTONE_LAMP_ON(Material.REDSTONE_LAMP_ON), 
    WOOD_DOUBLE_STEP(Material.WOOD_DOUBLE_STEP), 
    WOOD_STEP(Material.WOOD_STEP), 
    COCOA(Material.COCOA, (Class<?>)CocoaBeans.class, true), 
    SANDSTONE_STAIRS(Material.SANDSTONE_STAIRS), 
    EMERALD_ORE(Material.EMERALD_ORE, (Class<?>)EmeraldOre.class, true), 
    ENDER_CHEST(Material.ENDER_CHEST), 
    TRIPWIRE_HOOK(Material.TRIPWIRE_HOOK), 
    TRIPWIRE(Material.TRIPWIRE), 
    EMERALD_BLOCK(Material.EMERALD_BLOCK), 
    SPRUCE_WOOD_STAIRS(Material.SPRUCE_WOOD_STAIRS), 
    BIRCH_WOOD_STAIRS(Material.BIRCH_WOOD_STAIRS), 
    JUNGLE_WOOD_STAIRS(Material.JUNGLE_WOOD_STAIRS), 
    COMMAND(Material.COMMAND), 
    BEACON(Material.BEACON), 
    COBBLE_WALL(Material.COBBLE_WALL), 
    FLOWER_POT(Material.FLOWER_POT), 
    CARROT(Material.CARROT, (Class<?>)Carrot.class, true), 
    POTATO(Material.POTATO, (Class<?>)Potato.class, true), 
    WOOD_BUTTON(Material.WOOD_BUTTON), 
    SKULL(Material.SKULL), 
    ANVIL(Material.ANVIL), 
    TRAPPED_CHEST(Material.TRAPPED_CHEST), 
    GOLD_PLATE(Material.GOLD_PLATE), 
    IRON_PLATE(Material.IRON_PLATE), 
    REDSTONE_COMPARATOR_OFF(Material.REDSTONE_COMPARATOR_OFF), 
    REDSTONE_COMPARATOR_ON(Material.REDSTONE_COMPARATOR_ON), 
    DAYLIGHT_DETECTOR(Material.DAYLIGHT_DETECTOR), 
    REDSTONE_BLOCK(Material.REDSTONE_BLOCK), 
    NETHER_QUARTZ_ORE(Material.QUARTZ_ORE, (Class<?>)NetherQuartzOre.class, true), 
    HOPPER(Material.HOPPER), 
    QUARTZ_BLOCK(Material.QUARTZ_BLOCK), 
    QUARTZ_STAIRS(Material.QUARTZ_STAIRS), 
    ACTIVATOR_RAIL(Material.ACTIVATOR_RAIL), 
    DROPPER(Material.DROPPER), 
    STAINED_CLAY(Material.STAINED_CLAY), 
    STAINED_GLASS_PANE(Material.STAINED_GLASS_PANE), 
    LEAVES_2(Material.LEAVES_2), 
    ACACIA_WOOD(Material.LOG_2, (Class<?>)AcaciaWood.class, true), 
    ACACIA_STAIRS(Material.ACACIA_STAIRS), 
    DARK_OAK_STAIRS(Material.DARK_OAK_STAIRS), 
    SLIME_BLOCK(Material.SLIME_BLOCK), 
    BARRIER(Material.BARRIER), 
    IRON_TRAPDOOR(Material.IRON_TRAPDOOR), 
    PRISMARINE(Material.PRISMARINE), 
    SEA_LANTERN(Material.SEA_LANTERN), 
    HAY_BLOCK(Material.HAY_BLOCK), 
    CARPET(Material.CARPET), 
    HARD_CLAY(Material.HARD_CLAY), 
    COAL_BLOCK(Material.COAL_BLOCK), 
    PACKED_ICE(Material.PACKED_ICE), 
    DOUBLE_PLANT(Material.DOUBLE_PLANT), 
    STANDING_BANNER(Material.STANDING_BANNER), 
    WALL_BANNER(Material.WALL_BANNER), 
    DAYLIGHT_DETECTOR_INVERTED(Material.DAYLIGHT_DETECTOR_INVERTED), 
    RED_SANDSTONE(Material.RED_SANDSTONE), 
    RED_SANDSTONE_STAIRS(Material.RED_SANDSTONE_STAIRS), 
    DOUBLE_STONE_SLAB2(Material.DOUBLE_STONE_SLAB2), 
    STONE_SLAB2(Material.STONE_SLAB2), 
    SPRUCE_FENCE_GATE(Material.SPRUCE_FENCE_GATE), 
    BIRCH_FENCE_GATE(Material.BIRCH_FENCE_GATE), 
    JUNGLE_FENCE_GATE(Material.JUNGLE_FENCE_GATE), 
    DARK_OAK_FENCE_GATE(Material.DARK_OAK_FENCE_GATE), 
    ACACIA_FENCE_GATE(Material.ACACIA_FENCE_GATE), 
    SPRUCE_FENCE(Material.SPRUCE_FENCE), 
    BIRCH_FENCE(Material.BIRCH_FENCE), 
    JUNGLE_FENCE(Material.JUNGLE_FENCE), 
    DARK_OAK_FENCE(Material.DARK_OAK_FENCE), 
    ACACIA_FENCE(Material.ACACIA_FENCE), 
    SPRUCE_DOOR(Material.SPRUCE_DOOR), 
    BIRCH_DOOR(Material.BIRCH_DOOR), 
    JUNGLE_DOOR(Material.JUNGLE_DOOR), 
    ACACIA_DOOR(Material.ACACIA_DOOR), 
    DARK_OAK_DOOR(Material.DARK_OAK_DOOR), 
    IRON_SHOVEL(Material.IRON_SPADE, (Class<?>)IronShovel.class, true), 
    IRON_PICKAXE(Material.IRON_PICKAXE, (Class<?>)IronPickaxe.class, true), 
    IRON_AXE(Material.IRON_AXE, (Class<?>)IronAxe.class, true), 
    FLINT_AND_STEEL(Material.FLINT_AND_STEEL), 
    APPLE(Material.APPLE), 
    BOW(Material.BOW, (Class<?>)Bow.class, true), 
    ARROW(Material.ARROW), 
    COAL(Material.COAL), 
    DIAMOND(Material.DIAMOND), 
    IRON_INGOT(Material.IRON_INGOT), 
    GOLD_INGOT(Material.GOLD_INGOT), 
    IRON_SWORD(Material.IRON_SWORD, (Class<?>)IronSword.class, true), 
    WOOD_SWORD(Material.WOOD_SWORD, (Class<?>)WoodenSword.class, true), 
    WOOD_SHOVEL(Material.WOOD_SPADE, (Class<?>)WoodenShovel.class, true), 
    WOOD_PICKAXE(Material.WOOD_PICKAXE, (Class<?>)WoodenPickaxe.class, true), 
    WOOD_AXE(Material.WOOD_AXE, (Class<?>)WoodenAxe.class, true), 
    STONE_SWORD(Material.STONE_SWORD, (Class<?>)StoneSword.class, true), 
    STONE_SHOVEL(Material.STONE_SPADE, (Class<?>)StoneShovel.class, true), 
    STONE_PICKAXE(Material.STONE_PICKAXE, (Class<?>)StonePickaxe.class, true), 
    STONE_AXE(Material.STONE_AXE, (Class<?>)StoneAxe.class, true), 
    DIAMOND_SWORD(Material.DIAMOND_SWORD, (Class<?>)DiamondSword.class, true), 
    DIAMOND_SHOVEL(Material.DIAMOND_SPADE, (Class<?>)DiamondShovel.class, true), 
    DIAMOND_PICKAXE(Material.DIAMOND_PICKAXE, (Class<?>)DiamondPickaxe.class, true), 
    DIAMOND_AXE(Material.DIAMOND_AXE, (Class<?>)DiamondAxe.class, true), 
    STICK(Material.STICK), 
    BOWL(Material.BOWL), 
    MUSHROOM_SOUP(Material.MUSHROOM_SOUP), 
    GOLD_SWORD(Material.GOLD_SWORD, (Class<?>)GoldenSword.class, true), 
    GOLD_SHOVEL(Material.GOLD_SPADE, (Class<?>)GoldenShovel.class, true), 
    GOLD_PICKAXE(Material.GOLD_PICKAXE, (Class<?>)GoldenPickaxe.class, true), 
    GOLD_AXE(Material.GOLD_AXE, (Class<?>)GoldenAxe.class, true), 
    STRING(Material.STRING), 
    FEATHER(Material.FEATHER), 
    GUNPOWDER(Material.SULPHUR), 
    WOOD_HOE(Material.WOOD_HOE, (Class<?>)WoodenHoe.class, true), 
    STONE_HOE(Material.STONE_HOE, (Class<?>)StoneHoe.class, true), 
    IRON_HOE(Material.IRON_HOE, (Class<?>)IronHoe.class, true), 
    DIAMOND_HOE(Material.DIAMOND_HOE, (Class<?>)DiamondHoe.class, true), 
    GOLD_HOE(Material.GOLD_HOE, (Class<?>)GoldenHoe.class, true), 
    SEEDS(Material.SEEDS), 
    WHEAT(Material.WHEAT), 
    BREAD(Material.BREAD), 
    LEATHER_HELMET(Material.LEATHER_HELMET, (Class<?>)LeatherHelmet.class, true), 
    LEATHER_CHESTPLATE(Material.LEATHER_CHESTPLATE, (Class<?>)LeatherChestplate.class, true), 
    LEATHER_LEGGINGS(Material.LEATHER_LEGGINGS, (Class<?>)LeatherLeggings.class, true), 
    LEATHER_BOOTS(Material.LEATHER_BOOTS, (Class<?>)LeatherBoots.class, true), 
    CHAINMAIL_HELMET(Material.CHAINMAIL_HELMET, (Class<?>)ChainmailHelmet.class, true), 
    CHAINMAIL_CHESTPLATE(Material.CHAINMAIL_CHESTPLATE, (Class<?>)ChainmailChestplate.class, true), 
    CHAINMAIL_LEGGINGS(Material.CHAINMAIL_LEGGINGS, (Class<?>)ChainmailLeggings.class, true), 
    CHAINMAIL_BOOTS(Material.CHAINMAIL_BOOTS, (Class<?>)ChainmailBoots.class, true), 
    IRON_HELMET(Material.IRON_HELMET, (Class<?>)IronHelmet.class, true), 
    IRON_CHESTPLATE(Material.IRON_CHESTPLATE, (Class<?>)IronChestplate.class, true), 
    IRON_LEGGINGS(Material.IRON_LEGGINGS, (Class<?>)IronLeggings.class, true), 
    IRON_BOOTS(Material.IRON_BOOTS, (Class<?>)IronBoots.class, true), 
    DIAMOND_HELMET(Material.DIAMOND_HELMET, (Class<?>)DiamondHelmet.class, true), 
    DIAMOND_CHESTPLATE(Material.DIAMOND_CHESTPLATE, (Class<?>)DiamondChestplate.class, true), 
    DIAMOND_LEGGINGS(Material.DIAMOND_LEGGINGS, (Class<?>)DiamondLeggings.class, true), 
    DIAMOND_BOOTS(Material.DIAMOND_BOOTS, (Class<?>)DiamondBoots.class, true), 
    GOLDEN_HELMET(Material.GOLD_HELMET, (Class<?>)GoldenHelmet.class, true), 
    GOLDEN_CHESTPLATE(Material.GOLD_CHESTPLATE, (Class<?>)GoldenChestplate.class, true), 
    GOLDEN_LEGGINGS(Material.GOLD_LEGGINGS, (Class<?>)GoldenLeggings.class, true), 
    GOLDEN_BOOTS(Material.GOLD_BOOTS, (Class<?>)GoldenBoots.class, true), 
    FLINT(Material.FLINT), 
    PORK(Material.PORK), 
    GRILLED_PORK(Material.GRILLED_PORK), 
    PAINTING(Material.PAINTING), 
    GOLDEN_APPLE(Material.GOLDEN_APPLE), 
    SIGN(Material.SIGN), 
    WOOD_DOOR(Material.WOOD_DOOR), 
    BUCKET(Material.BUCKET), 
    WATER_BUCKET(Material.WATER_BUCKET), 
    LAVA_BUCKET(Material.LAVA_BUCKET), 
    MINECART(Material.MINECART), 
    SADDLE(Material.SADDLE), 
    IRON_DOOR(Material.IRON_DOOR), 
    REDSTONE(Material.REDSTONE), 
    SNOW_BALL(Material.SNOW_BALL), 
    BOAT(Material.BOAT), 
    LEATHER(Material.LEATHER), 
    MILK_BUCKET(Material.MILK_BUCKET), 
    CLAY_BRICK(Material.CLAY_BRICK), 
    CLAY_BALL(Material.CLAY_BALL), 
    SUGAR_CANE(Material.SUGAR_CANE), 
    PAPER(Material.PAPER), 
    BOOK(Material.BOOK), 
    SLIME_BALL(Material.SLIME_BALL), 
    STORAGE_MINECART(Material.STORAGE_MINECART), 
    POWERED_MINECART(Material.POWERED_MINECART), 
    EGG(Material.EGG), 
    COMPASS(Material.COMPASS), 
    FISHING_ROD(Material.FISHING_ROD), 
    WATCH(Material.WATCH), 
    GLOWSTONE_DUST(Material.GLOWSTONE_DUST), 
    RAW_FISH(Material.RAW_FISH), 
    COOKED_FISH(Material.COOKED_FISH), 
    INK_SACK(Material.INK_SACK), 
    BONE(Material.BONE), 
    SUGAR(Material.SUGAR), 
    CAKE(Material.CAKE), 
    BED(Material.BED), 
    DIODE(Material.DIODE), 
    COOKIE(Material.COOKIE), 
    MAP(Material.MAP), 
    SHEARS(Material.SHEARS), 
    MELON(Material.MELON), 
    PUMPKIN_SEEDS(Material.PUMPKIN_SEEDS), 
    MELON_SEEDS(Material.MELON_SEEDS), 
    RAW_BEEF(Material.RAW_BEEF), 
    COOKED_BEEF(Material.COOKED_BEEF), 
    RAW_CHICKEN(Material.RAW_CHICKEN), 
    COOKED_CHICKEN(Material.COOKED_CHICKEN), 
    ROTTEN_FLESH(Material.ROTTEN_FLESH), 
    ENDER_PEARL(Material.ENDER_PEARL), 
    BLAZE_ROD(Material.BLAZE_ROD), 
    GHAST_TEAR(Material.GHAST_TEAR), 
    GOLD_NUGGET(Material.GOLD_NUGGET), 
    NETHER_STALK(Material.NETHER_STALK), 
    GLASS_BOTTLE(Material.GLASS_BOTTLE), 
    SPIDER_EYE(Material.SPIDER_EYE), 
    FERMENTED_SPIDER_EYE(Material.FERMENTED_SPIDER_EYE), 
    BLAZE_POWDER(Material.BLAZE_POWDER), 
    MAGMA_CREAM(Material.MAGMA_CREAM), 
    BREWING_STAND_ITEM(Material.BREWING_STAND_ITEM), 
    CAULDRON_ITEM(Material.CAULDRON_ITEM), 
    EYE_OF_ENDER(Material.EYE_OF_ENDER), 
    SPECKLED_MELON(Material.SPECKLED_MELON), 
    MONSTER_EGG(Material.MONSTER_EGG), 
    EXP_BOTTLE(Material.EXP_BOTTLE), 
    FIREBALL(Material.FIREBALL), 
    BOOK_AND_QUILL(Material.BOOK_AND_QUILL), 
    WRITTEN_BOOK(Material.WRITTEN_BOOK), 
    EMERALD(Material.EMERALD), 
    ITEM_FRAME(Material.ITEM_FRAME), 
    FLOWER_POT_ITEM(Material.FLOWER_POT_ITEM), 
    CARROT_ITEM(Material.CARROT_ITEM), 
    POTATO_ITEM(Material.POTATO_ITEM), 
    BAKED_POTATO(Material.BAKED_POTATO), 
    POISONOUS_POTATO(Material.POISONOUS_POTATO), 
    EMPTY_MAP(Material.EMPTY_MAP), 
    GOLDEN_CARROT(Material.GOLDEN_CARROT), 
    SKULL_ITEM(Material.SKULL_ITEM), 
    CARROT_STICK(Material.CARROT_STICK), 
    NETHER_STAR(Material.NETHER_STAR), 
    PUMPKIN_PIE(Material.PUMPKIN_PIE), 
    FIREWORK(Material.FIREWORK), 
    FIREWORK_CHARGE(Material.FIREWORK_CHARGE), 
    REDSTONE_COMPARATOR(Material.REDSTONE_COMPARATOR), 
    NETHER_BRICK_ITEM(Material.NETHER_BRICK_ITEM), 
    QUARTZ(Material.QUARTZ), 
    EXPLOSIVE_MINECART(Material.EXPLOSIVE_MINECART), 
    HOPPER_MINECART(Material.HOPPER_MINECART), 
    PRISMARINE_SHARD(Material.PRISMARINE_SHARD), 
    PRISMARINE_CRYSTALS(Material.PRISMARINE_CRYSTALS), 
    RABBIT(Material.RABBIT), 
    COOKED_RABBIT(Material.COOKED_RABBIT), 
    RABBIT_STEW(Material.RABBIT_STEW), 
    RABBIT_FOOT(Material.RABBIT_FOOT), 
    RABBIT_HIDE(Material.RABBIT_HIDE), 
    ARMOR_STAND(Material.ARMOR_STAND), 
    IRON_BARDING(Material.IRON_BARDING), 
    GOLD_BARDING(Material.GOLD_BARDING), 
    DIAMOND_BARDING(Material.DIAMOND_BARDING), 
    LEASH(Material.LEASH), 
    NAME_TAG(Material.NAME_TAG), 
    COMMAND_MINECART(Material.COMMAND_MINECART), 
    MUTTON(Material.MUTTON), 
    COOKED_MUTTON(Material.COOKED_MUTTON), 
    BANNER(Material.BANNER), 
    SPRUCE_DOOR_ITEM(Material.SPRUCE_DOOR_ITEM), 
    BIRCH_DOOR_ITEM(Material.BIRCH_DOOR_ITEM), 
    JUNGLE_DOOR_ITEM(Material.JUNGLE_DOOR_ITEM), 
    ACACIA_DOOR_ITEM(Material.ACACIA_DOOR_ITEM), 
    DARK_OAK_DOOR_ITEM(Material.DARK_OAK_DOOR_ITEM), 
    GOLD_RECORD(Material.GOLD_RECORD), 
    GREEN_RECORD(Material.GREEN_RECORD), 
    RECORD_3(Material.RECORD_3), 
    RECORD_4(Material.RECORD_4), 
    RECORD_5(Material.RECORD_5), 
    RECORD_6(Material.RECORD_6), 
    RECORD_7(Material.RECORD_7), 
    RECORD_8(Material.RECORD_8), 
    RECORD_9(Material.RECORD_9), 
    RECORD_10(Material.RECORD_10), 
    RECORD_11(Material.RECORD_11), 
    RECORD_12(Material.RECORD_12), 
    SLIGHTLY_DAMAGED_ANVIL(Material.ANVIL, (short)1, "Slightly Damaged Anvil"), 
    VERY_DAMAGED_ANVIL(Material.ANVIL, (short)2, "Very Damaged Anvil"), 
    RED_BANNER(Material.BANNER, (short)1, "Red Banner"), 
    GREEN_BANNER(Material.BANNER, (short)2, "Green Banner"), 
    BROWN_BANNER(Material.BANNER, (short)3, "Brown Banner"), 
    BLUE_BANNER(Material.BANNER, (short)4, "Blue Banner"), 
    PURPLE_BANNER(Material.BANNER, (short)5, "Purple Banner"), 
    CYAN_BANNER(Material.BANNER, (short)6, "Cyan Banner"), 
    LIGHT_GRAY_BANNER(Material.BANNER, (short)7, "Light Gray Banner"), 
    GRAY_BANNER(Material.BANNER, (short)8, "Gray Banner"), 
    PINK_BANNER(Material.BANNER, (short)9, "Pink Banner"), 
    LIME_BANNER(Material.BANNER, (short)10, "Lime Banner"), 
    YELLOW_BANNER(Material.BANNER, (short)11, "Yellow Banner"), 
    LIGHT_BLUE_BANNER(Material.BANNER, (short)12, "Light Blue Banner"), 
    MAGENTA_BANNER(Material.BANNER, (short)13, "Magenta Banner"), 
    ORANGE_BANNER(Material.BANNER, (short)14, "Orange Banner"), 
    ORANGE_CARPET(Material.CARPET, (short)1, "Orange Carpet"), 
    MAGENTA_CARPET(Material.CARPET, (short)2, "Magenta Carpet"), 
    LIGHT_BLUE_CARPET(Material.CARPET, (short)3, "Light Blue Carpet"), 
    YELLOW_CARPET(Material.CARPET, (short)4, "Yellow Carpet"), 
    LIME_CARPET(Material.CARPET, (short)5, "Lime Carpet"), 
    PINK_CARPET(Material.CARPET, (short)6, "Pink Carpet"), 
    GRAY_CARPET(Material.CARPET, (short)7, "Gray Carpet"), 
    LIGHT_GRAY_CARPET(Material.CARPET, (short)8, "Light Gray Carpet"), 
    CYAN_CARPET(Material.CARPET, (short)9, "Cyan Carpet"), 
    PURPLE_CARPET(Material.CARPET, (short)10, "Purple Carpet"), 
    BLUE_CARPET(Material.CARPET, (short)11, "Blue Carpet"), 
    BROWN_CARPET(Material.CARPET, (short)12, "Brown Carpet"), 
    GREEN_CARPET(Material.CARPET, (short)13, "Green Carpet"), 
    RED_CARPET(Material.CARPET, (short)14, "Red Carpet"), 
    BLACK_CARPET(Material.CARPET, (short)15, "Black Carpet"), 
    CHARCOAL(Material.COAL, (short)1, "Charcoal"), 
    MOSSY_COBBLESTONE_WALL(Material.COBBLE_WALL, (short)1, "Mossy Cobblestone Wall"), 
    COOKED_SALMON(Material.COOKED_FISH, (short)1, "Cooked Salmon"), 
    COARSE_DIRT(Material.DIRT, (short)1, "Coarse Dirt"), 
    PODZOL(Material.DIRT, (short)2, "Podzol"), 
    LILAC(Material.DOUBLE_PLANT, (short)1, "Lilac"), 
    DOUBLE_TALLGRASS(Material.DOUBLE_PLANT, (short)2, "Double Tallgrass"), 
    LARGE_FERN(Material.DOUBLE_PLANT, (short)3, "Large Fern"), 
    ROSE_BUSH(Material.DOUBLE_PLANT, (short)4, "Rose Bush"), 
    PEONY(Material.DOUBLE_PLANT, (short)5, "Peony"), 
    RED_DYE(Material.INK_SACK, (short)1, "Red Dye"), 
    GREEN_DYE(Material.INK_SACK, (short)2, "Green Dye"), 
    COCOA_BEANS(Material.INK_SACK, (short)3, "Cocoa Beans"), 
    LAPIS_LAZULI(Material.INK_SACK, (short)4, "Lapis Lazuli"), 
    PURPLE_DYE(Material.INK_SACK, (short)5, "Purple Dye"), 
    CYAN_DYE(Material.INK_SACK, (short)6, "Cyan Dye"), 
    LIGHT_GRAY_DYE(Material.INK_SACK, (short)7, "Light Gray Dye"), 
    GRAY_DYE(Material.INK_SACK, (short)8, "Gray Dye"), 
    PINK_DYE(Material.INK_SACK, (short)9, "Pink Dye"), 
    LIME_DYE(Material.INK_SACK, (short)10, "Lime Dye"), 
    YELLOW_DYE(Material.INK_SACK, (short)11, "Yellow Dye"), 
    LIGHT_BLUE_DYE(Material.INK_SACK, (short)12, "Light Blue Dye"), 
    MAGENTA_DYE(Material.INK_SACK, (short)13, "Magenta Dye"), 
    ORANGE_DYE(Material.INK_SACK, (short)14, "Orange Dye"), 
    BONE_MEAL(Material.INK_SACK, (short)15, "Bone Meal"), 
    RAW_SALMON(Material.RAW_FISH, (short)1, "Raw Salmon"), 
    TROPICAL_FISH(Material.RAW_FISH, (short)2, "Tropical Fish"), 
    PUFFERFISH(Material.RAW_FISH, (short)3, "Pufferfish"), 
    ENCHANTED_GOLDEN_APPLE(Material.GOLDEN_APPLE, (short)1, "Enchanted Golden Apple"), 
    SPRUCE_LEAVES(Material.LEAVES, (short)1, "Spruce Leaves"), 
    BIRCH_LEAVES(Material.LEAVES, (short)2, "Birch Leaves"), 
    JUNGLE_LEAVES(Material.LEAVES, (short)3, "Jungle Leaves"), 
    DARK_OAK_LEAVES(Material.LEAVES_2, (short)1, "Dark Oak Leaves"), 
    SPRUCE_WOOD(Material.LOG, (short)1, (Class<?>)SpruceWood.class, true), 
    BIRCH_WOOD(Material.LOG, (short)2, (Class<?>)BirchWood.class, true), 
    JUNGLE_WOOD(Material.LOG, (short)3, (Class<?>)JungleWood.class, true), 
    DARK_OAK_WOOD(Material.LOG_2, (short)1, (Class<?>)DarkOakWood.class, true), 
    COBBLESTONE_MONSTER_EGG(Material.MONSTER_EGGS, (short)1, "Cobblestone Monster Egg"), 
    STONE_BRICK_MONSTER_EGG(Material.MONSTER_EGGS, (short)2, "Stone Brick Monster Egg"), 
    MOSSY_STONE_BRICK_MONSTER_EGG(Material.MONSTER_EGGS, (short)3, "Mossy Stone Brick Monster Egg"), 
    CRACKED_STONE_BRICK_MONSTER_EGG(Material.MONSTER_EGGS, (short)4, "Cracked Stone Brick Monster Egg"), 
    CHISELED_STONE_BRICK_MONSTER_EGG(Material.MONSTER_EGGS, (short)5, "Chiseled Stone Brick Monster Egg"), 
    SPRUCE_WOOD_PLANKS(Material.WOOD, (short)1, "Spruce Wood Planks"), 
    BIRCH_WOOD_PLANKS(Material.WOOD, (short)2, "Birch Wood Planks"), 
    JUNGLE_WOOD_PLANKS(Material.WOOD, (short)3, "Jungle Wood Planks"), 
    ACACIA_WOOD_PLANKS(Material.WOOD, (short)4, "Acacia Wood Planks"), 
    DARK_OAK_WOOD_PLANKS(Material.WOOD, (short)5, "Dark Oak Wood Planks"), 
    PRISMARINE_BRICKS(Material.PRISMARINE, (short)1, "Prismarine Bricks"), 
    DARK_PRISMARINE(Material.PRISMARINE, (short)2, "Dark Prismarine"), 
    CHISELED_QUARTZ_BLOCK(Material.QUARTZ_BLOCK, (short)1, "Chiseled Quartz Block"), 
    PILLAR_QUARTZ_BLOCK(Material.QUARTZ_BLOCK, (short)2, "Pillar Quartz Block"), 
    BLUE_ORCHID(Material.RED_ROSE, (short)1, "Blue Orchid"), 
    ALLIUM(Material.RED_ROSE, (short)2, "Allium"), 
    AZURE_BLUET(Material.RED_ROSE, (short)3, "Azure Bluet"), 
    RED_TULIP(Material.RED_ROSE, (short)4, "Red Tulip"), 
    ORANGE_TULIP(Material.RED_ROSE, (short)5, "Orange Tulip"), 
    WHITE_TULIP(Material.RED_ROSE, (short)6, "White Tulip"), 
    PINK_TULIP(Material.RED_ROSE, (short)7, "Pink Tulip"), 
    OXEYE_DAISY(Material.RED_ROSE, (short)8, "Oxeye Daisy"), 
    CHISELED_RED_SANDSTONE(Material.RED_SANDSTONE, (short)1, "Chiseled Red Sandstone"), 
    PILLAR_RED_SANDSTONE(Material.RED_SANDSTONE, (short)2, "Pillar Red Sandstone"), 
    RED_SAND(Material.SAND, (short)1, "Red Sand"), 
    CHISELED_SANDSTONE(Material.SANDSTONE, (short)1, "Chiseled Sandstone"), 
    SMOOTH_SANDSTONE(Material.SANDSTONE, (short)2, "Smooth Sandstone"), 
    SPRUCE_SAPLING(Material.SAPLING, (short)1, "Spruce Sapling"), 
    BIRCH_SAPLING(Material.SAPLING, (short)2, "Birch Sapling"), 
    JUNGLE_SAPLING(Material.SAPLING, (short)3, "Jungle Sapling"), 
    ACACIA_SAPLING(Material.SAPLING, (short)4, "Acacia Sapling"), 
    DARK_OAK_SAPLING(Material.SAPLING, (short)5, "Dark Oak Sapling"), 
    WITHER_SKELETON_SKULL(Material.SKULL_ITEM, (short)1, "Wither Skeleton Skull"), 
    ZOMBIE_HEAD(Material.SKULL_ITEM, (short)2, "Zombie Head"), 
    HEAD(Material.SKULL_ITEM, (short)3, "Head"), 
    T_(Material.SKULL_ITEM, (Class<?>)TerracottaHead.class), 
    JERRY_GUN(Material.GOLD_BARDING, (Class<?>)JerryChineGun.class), 
    CREEPER_HEAD(Material.SKULL_ITEM, (short)4, "Creeper Head"), 
    CREEPER_SPAWN_EGG(Material.MONSTER_EGG, (short)50, "Spawn Creeper"), 
    SKELETON_SPAWN_EGG(Material.MONSTER_EGG, (short)51, "Spawn Skeleton"), 
    SPIDER_SPAWN_EGG(Material.MONSTER_EGG, (short)52, "Spawn Spider"), 
    ZOMBIE_SPAWN_EGG(Material.MONSTER_EGG, (short)54, "Spawn Zombie"), 
    SLIME_SPAWN_EGG(Material.MONSTER_EGG, (short)55, "Spawn Slime"), 
    GHAST_SPAWN_EGG(Material.MONSTER_EGG, (short)56, "Spawn Ghast"), 
    ZOMBIE_PIGMAN_SPAWN_EGG(Material.MONSTER_EGG, (short)57, "Spawn Zombie Pigman"), 
    ENDERMAN_SPAWN_EGG(Material.MONSTER_EGG, (short)58, "Spawn Enderman"), 
    CAVE_SPIDER_SPAWN_EGG(Material.MONSTER_EGG, (short)59, "Spawn Cave Spider"), 
    SILVERFISH_SPAWN_EGG(Material.MONSTER_EGG, (short)60, "Spawn Silverfish"), 
    BLAZE_SPAWN_EGG(Material.MONSTER_EGG, (short)61, "Spawn Blaze"), 
    MAGMA_CUBE_SPAWN_EGG(Material.MONSTER_EGG, (short)62, "Spawn Magma Cube"), 
    BAT_SPAWN_EGG(Material.MONSTER_EGG, (short)65, "Spawn Bat"), 
    WITCH_SPAWN_EGG(Material.MONSTER_EGG, (short)66, "Spawn Witch"), 
    ENDERMITE_SPAWN_EGG(Material.MONSTER_EGG, (short)67, "Spawn Endermite"), 
    GUARDIAN_SPAWN_EGG(Material.MONSTER_EGG, (short)68, "Spawn Guardian"), 
    PIG_SPAWN_EGG(Material.MONSTER_EGG, (short)90, "Spawn Pig"), 
    SHEEP_SPAWN_EGG(Material.MONSTER_EGG, (short)91, "Spawn Sheep"), 
    COW_SPAWN_EGG(Material.MONSTER_EGG, (short)92, "Spawn Cow"), 
    CHICKEN_SPAWN_EGG(Material.MONSTER_EGG, (short)93, "Spawn Chicken"), 
    SQUID_SPAWN_EGG(Material.MONSTER_EGG, (short)94, "Spawn Squid"), 
    WOLF_SPAWN_EGG(Material.MONSTER_EGG, (short)95, "Spawn Wolf"), 
    MOOSHROOM_SPAWN_EGG(Material.MONSTER_EGG, (short)96, "Spawn Mooshroom"), 
    OCELOT_SPAWN_EGG(Material.MONSTER_EGG, (short)98, "Spawn Ocelot"), 
    HORSE_SPAWN_EGG(Material.MONSTER_EGG, (short)100, "Spawn Horse"), 
    RABBIT_SPAWN_EGG(Material.MONSTER_EGG, (short)101, "Spawn Rabbit"), 
    VILLAGER_SPAWN_EGG(Material.MONSTER_EGG, (short)120, "Spawn Villager"), 
    WET_SPONGE(Material.SPONGE, (short)1, "Wet Sponge"), 
    ORANGE_STAINED_GLASS(Material.STAINED_GLASS, (short)1, "Orange Stained Glass"), 
    MAGENTA_STAINED_GLASS(Material.STAINED_GLASS, (short)2, "Magenta Stained Glass"), 
    LIGHT_BLUE_STAINED_GLASS(Material.STAINED_GLASS, (short)3, "Light Blue Stained Glass"), 
    YELLOW_STAINED_GLASS(Material.STAINED_GLASS, (short)4, "Yellow Stained Glass"), 
    LIME_STAINED_GLASS(Material.STAINED_GLASS, (short)5, "Lime Stained Glass"), 
    PINK_STAINED_GLASS(Material.STAINED_GLASS, (short)6, "Pink Stained Glass"), 
    GRAY_STAINED_GLASS(Material.STAINED_GLASS, (short)7, "Gray Stained Glass"), 
    LIGHT_GRAY_STAINED_GLASS(Material.STAINED_GLASS, (short)8, "Light Gray Stained Glass"), 
    CYAN_STAINED_GLASS(Material.STAINED_GLASS, (short)9, "Cyan Stained Glass"), 
    PURPLE_STAINED_GLASS(Material.STAINED_GLASS, (short)10, "Purple Stained Glass"), 
    BLUE_STAINED_GLASS(Material.STAINED_GLASS, (short)11, "Blue Stained Glass"), 
    BROWN_STAINED_GLASS(Material.STAINED_GLASS, (short)12, "Brown Stained Glass"), 
    GREEN_STAINED_GLASS(Material.STAINED_GLASS, (short)13, "Green Stained Glass"), 
    RED_STAINED_GLASS(Material.STAINED_GLASS, (short)14, "Red Stained Glass"), 
    BLACK_STAINED_GLASS(Material.STAINED_GLASS, (short)15, "Black Stained Glass"), 
    ORANGE_STAINED_GLASS_PANE(Material.STAINED_GLASS_PANE, (short)1, "Orange Stained Glass Pane"), 
    MAGENTA_STAINED_GLASS_PANE(Material.STAINED_GLASS_PANE, (short)2, "Magenta Stained Glass Pane"), 
    LIGHT_BLUE_STAINED_GLASS_PANE(Material.STAINED_GLASS_PANE, (short)3, "Light Blue Stained Glass Pane"), 
    YELLOW_STAINED_GLASS_PANE(Material.STAINED_GLASS_PANE, (short)4, "Yellow Stained Glass Pane"), 
    LIME_STAINED_GLASS_PANE(Material.STAINED_GLASS_PANE, (short)5, "Lime Stained Glass Pane"), 
    PINK_STAINED_GLASS_PANE(Material.STAINED_GLASS_PANE, (short)6, "Pink Stained Glass Pane"), 
    GRAY_STAINED_GLASS_PANE(Material.STAINED_GLASS_PANE, (short)7, "Gray Stained Glass Pane"), 
    LIGHT_GRAY_STAINED_GLASS_PANE(Material.STAINED_GLASS_PANE, (short)8, "Light Gray Stained Glass Pane"), 
    CYAN_STAINED_GLASS_PANE(Material.STAINED_GLASS_PANE, (short)9, "Cyan Stained Glass Pane"), 
    PURPLE_STAINED_GLASS_PANE(Material.STAINED_GLASS_PANE, (short)10, "Purple Stained Glass Pane"), 
    BLUE_STAINED_GLASS_PANE(Material.STAINED_GLASS_PANE, (short)11, "Blue Stained Glass Pane"), 
    BROWN_STAINED_GLASS_PANE(Material.STAINED_GLASS_PANE, (short)12, "Brown Stained Glass Pane"), 
    GREEN_STAINED_GLASS_PANE(Material.STAINED_GLASS_PANE, (short)13, "Green Stained Glass Pane"), 
    RED_STAINED_GLASS_PANE(Material.STAINED_GLASS_PANE, (short)14, "Red Stained Glass Pane"), 
    BLACK_STAINED_GLASS_PANE(Material.STAINED_GLASS_PANE, (short)15, "Black Stained Glass Pane"), 
    ORANGE_STAINED_CLAY(Material.STAINED_CLAY, (short)1, "Orange Stained Clay"), 
    MAGENTA_STAINED_CLAY(Material.STAINED_CLAY, (short)2, "Magenta Stained Clay"), 
    LIGHT_BLUE_STAINED_CLAY(Material.STAINED_CLAY, (short)3, "Light Blue Stained Clay"), 
    YELLOW_STAINED_CLAY(Material.STAINED_CLAY, (short)4, "Yellow Stained Clay"), 
    LIME_STAINED_CLAY(Material.STAINED_CLAY, (short)5, "Lime Stained Clay"), 
    PINK_STAINED_CLAY(Material.STAINED_CLAY, (short)6, "Pink Stained Clay"), 
    GRAY_STAINED_CLAY(Material.STAINED_CLAY, (short)7, "Gray Stained Clay"), 
    LIGHT_GRAY_STAINED_CLAY(Material.STAINED_CLAY, (short)8, "Light Gray Stained Clay"), 
    CYAN_STAINED_CLAY(Material.STAINED_CLAY, (short)9, "Cyan Stained Clay"), 
    PURPLE_STAINED_CLAY(Material.STAINED_CLAY, (short)10, "Purple Stained Clay"), 
    BLUE_STAINED_CLAY(Material.STAINED_CLAY, (short)11, "Blue Stained Clay"), 
    BROWN_STAINED_CLAY(Material.STAINED_CLAY, (short)12, "Brown Stained Clay"), 
    GREEN_STAINED_CLAY(Material.STAINED_CLAY, (short)13, "Green Stained Clay"), 
    RED_STAINED_CLAY(Material.STAINED_CLAY, (short)14, "Red Stained Clay"), 
    BLACK_STAINED_CLAY(Material.STAINED_CLAY, (short)15, "Black Stained Clay"), 
    GRANITE(Material.STONE, (short)1, "Granite"), 
    POLISHED_GRANITE(Material.STONE, (short)2, "Polished Granite"), 
    DIORITE(Material.STONE, (short)3, "Diorite"), 
    POLISHED_DIORITE(Material.STONE, (short)4, "Polished Diorite"), 
    ANDESITE(Material.STONE, (short)5, "Andesite"), 
    POLISHED_ANDESITE(Material.STONE, (short)6, "Polished Andesite"), 
    SANDSTONE_SLAB(Material.STEP, (short)1, "Sandstone Slab"), 
    COBBLESTONE_SLAB(Material.STEP, (short)3, "Cobblestone Slab"), 
    BRICK_SLAB(Material.STEP, (short)4, "Brick Slab"), 
    STONE_BRICK_SLAB(Material.STEP, (short)5, "Stone Brick Slab"), 
    NETHER_BRICK_SLAB(Material.STEP, (short)6, "Nether Brick Slab"), 
    QUARTZ_SLAB(Material.STEP, (short)7, "Quartz Slab"), 
    MOSSY_STONE_BRICKS(Material.SMOOTH_BRICK, (short)1, "Mossy Stone Bricks"), 
    CRACKED_STONE_BRICKS(Material.SMOOTH_BRICK, (short)2, "Cracked Stone Bricks"), 
    CHISELED_STONE_BRICKS(Material.SMOOTH_BRICK, (short)3, "Chiseled Stone Bricks"), 
    GRASS(Material.LONG_GRASS, (short)1, "Grass"), 
    FERN(Material.LONG_GRASS, (short)2, "Fern"), 
    SPRUCE_WOOD_SLAB(Material.WOOD_STEP, (short)1, "Spruce Wood Slab"), 
    BIRCH_WOOD_SLAB(Material.WOOD_STEP, (short)2, "Birch Wood Slab"), 
    JUNGLE_WOOD_SLAB(Material.WOOD_STEP, (short)3, "Jungle Wood Slab"), 
    ACACIA_WOOD_SLAB(Material.WOOD_STEP, (short)4, "Acacia Wood Slab"), 
    DARK_OAK_WOOD_SLAB(Material.WOOD_STEP, (short)5, "Dark Oak Wood Slab"), 
    ORANGE_WOOL(Material.WOOL, (short)1, "Orange Wool"), 
    MAGENTA_WOOL(Material.WOOL, (short)2, "Magenta Wool"), 
    LIGHT_BLUE_WOOL(Material.WOOL, (short)3, "Light Blue Wool"), 
    YELLOW_WOOL(Material.WOOL, (short)4, "Yellow Wool"), 
    LIME_WOOL(Material.WOOL, (short)5, "Lime Wool"), 
    PINK_WOOL(Material.WOOL, (short)6, "Pink Wool"), 
    GRAY_WOOL(Material.WOOL, (short)7, "Gray Wool"), 
    LIGHT_GRAY_WOOL(Material.WOOL, (short)8, "Light Gray Wool"), 
    CYAN_WOOL(Material.WOOL, (short)9, "Cyan Wool"), 
    PURPLE_WOOL(Material.WOOL, (short)10, "Purple Wool"), 
    BLUE_WOOL(Material.WOOL, (short)11, "Blue Wool"), 
    BROWN_WOOL(Material.WOOL, (short)12, "Brown Wool"), 
    GREEN_WOOL(Material.WOOL, (short)13, "Green Wool"), 
    RED_WOOL(Material.WOOL, (short)14, "Red Wool"), 
    JERRY_HEAD(Material.SKULL_ITEM, (Class<?>)JerryGunBullet.class), 
    ATONED_HEAD(Material.SKULL_ITEM, (Class<?>)AtonedHorrorHead.class), 
    REV_HORROR_2(Material.SKULL_ITEM, (Class<?>)RevenantHorrorHead2.class), 
    NUKEKUBI(Material.SKULL_ITEM, (Class<?>)Nukekubi.class), 
    BONZO_BALLOON_1(Material.SKULL_ITEM, (Class<?>)BS1.class), 
    BONZO_BALLOON_2(Material.SKULL_ITEM, (Class<?>)BS2.class), 
    BONZO_BALLOON_3(Material.SKULL_ITEM, (Class<?>)BS3.class), 
    BONZO_BALLOON_4(Material.SKULL_ITEM, (Class<?>)BS4.class), 
    BONZO_BALLOON_5(Material.SKULL_ITEM, (Class<?>)BS5.class), 
    BONZO_BALLOON_6(Material.SKULL_ITEM, (Class<?>)BS6.class), 
    BONZO_BALLOON_7(Material.SKULL_ITEM, (Class<?>)BS7.class), 
    BONZO_BALLOON_8(Material.SKULL_ITEM, (Class<?>)BS8.class), 
    BONZO_BALLOON_9(Material.SKULL_ITEM, (Class<?>)BS9.class), 
    BLACK_WOOL(Material.WOOL, (short)15, "Black Wool");
    
    private static final List<ArmorSet> CACHED_SETS;
    public static YoungDragonSet YOUNG_DRAGON_SET;
    public static SuperiorDragonSet SUPERIOR_DRAGON_SET;
    public static WiseDragonSet WISE_DRAGON_SET;
    public static UnstableDragonSet UNSTABLE_DRAGON_SET;
    public static StrongDragonSet STRONG_DRAGON_SET;
    public static OldDragonSet OLD_DRAGON_SET;
    public static ProtectorDragonSet PROTECTOR_DRAGON_SET;
    public static LapisArmorSet LAPIS_ARMOR_SET;
    public static MinerSet MINER_SET;
    public static NecronFullSet NECRONS_SET;
    public static StormFullSet STORMS_SET;
    public static SorrowArmorSet SORROW_SET;
    private final Material craftMaterial;
    private final short data;
    private final Class<?> clazz;
    private final boolean craft;
    private final String baseName;
    
    private SMaterial(final Material craftMaterial, final short data, final Class<?> clazz, final boolean craft, final String baseName) {
        this.craftMaterial = craftMaterial;
        this.data = data;
        this.clazz = clazz;
        this.craft = craft;
        this.baseName = baseName;
    }
    
    private SMaterial(final Material craftMaterial, final short data, final Class<?> clazz, final boolean craft) {
        this(craftMaterial, data, clazz, craft, null);
    }
    
    private SMaterial(final Material craftMaterial, final Class<?> clazz, final boolean craft) {
        this(craftMaterial, (short)0, clazz, craft);
    }
    
    private SMaterial(final Material craftMaterial, final Class<?> clazz) {
        this(craftMaterial, clazz, false);
    }
    
    private SMaterial(final Material craftMaterial, final Class<?> clazz, final short data) {
        this(craftMaterial, data, clazz, false);
    }
    
    private SMaterial(final Material craftMaterial, final short data, final String baseName) {
        this(craftMaterial, data, null, true, baseName);
    }
    
    private SMaterial(final Material craftMaterial, final short data, final Class<?> clazz) {
        this(craftMaterial, data, clazz, false, null);
    }
    
    private SMaterial(final Material craftMaterial) {
        this(craftMaterial, null, true);
    }
    
    public static SMaterial getMaterial(final String name) {
        try {
            return valueOf(name.toUpperCase());
        }
        catch (final IllegalArgumentException ex) {
            return null;
        }
    }
    
    public static SMaterial getSpecEquivalent(final Material material, short data) {
        if (Material.LOG == material || Material.LOG_2 == material || Material.LEAVES == material || Material.LEAVES_2 == material) {
            data %= 4;
        }
        final List<SMaterial> results = (List<SMaterial>)Arrays.stream((Object[])values()).filter(m -> m.craft && m.getCraftMaterial() == material).collect(Collectors.toList());
        for (final SMaterial result : results) {
            if (result.data == data) {
                return result;
            }
        }
        if (results.isEmpty()) {
            return null;
        }
        return (SMaterial)results.get(0);
    }
    
    public static <T extends ArmorSet> T registerArmorSet(final Class<? extends ArmorSet> set) {
        try {
            final ArmorSet s = (ArmorSet)set.newInstance();
            SMaterial.CACHED_SETS.add((Object)s);
            return (T)s;
        }
        catch (final InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static ArmorSet findArmorSet(final SMaterial helmet, final SMaterial chestplate, final SMaterial leggings, final SMaterial boots) {
        final List<ArmorSet> subList = (List<ArmorSet>)SMaterial.CACHED_SETS.stream().filter(s -> s.getHelmet().equals(helmet.getStatistics().getClass()) && s.getChestplate().equals(chestplate.getStatistics().getClass()) && s.getLeggings().equals(leggings.getStatistics().getClass()) && s.getBoots().equals(boots.getStatistics().getClass())).collect(Collectors.toList());
        if (0 == subList.size()) {
            return null;
        }
        return (ArmorSet)subList.get(0);
    }
    
    public static ArmorSet findArmorSet(final SMaterial piece) {
        final List<ArmorSet> subList = (List<ArmorSet>)SMaterial.CACHED_SETS.stream().filter(s -> s.getHelmet().equals(piece.getStatistics().getClass()) || s.getChestplate().equals(piece.getStatistics().getClass()) || s.getLeggings().equals(piece.getStatistics().getClass()) || s.getBoots().equals(piece.getStatistics().getClass())).collect(Collectors.toList());
        if (0 == subList.size()) {
            return null;
        }
        return (ArmorSet)subList.get(0);
    }
    
    public MaterialFunction getFunction() {
        final Object generic = this.getGenericInstance();
        if (generic instanceof MaterialFunction) {
            return (MaterialFunction)generic;
        }
        return null;
    }
    
    public MaterialStatistics getStatistics() {
        if (!this.hasClass()) {
            return new MaterialStatistics() {
                @Override
                public String getDisplayName() {
                    return null;
                }
                
                @Override
                public Rarity getRarity() {
                    return Rarity.COMMON;
                }
                
                @Override
                public String getLore() {
                    return null;
                }
                
                @Override
                public GenericItemType getType() {
                    return SUtil.getItemType(SMaterial.this.craftMaterial);
                }
            };
        }
        final Object generic = this.getGenericInstance();
        if (generic instanceof MaterialStatistics) {
            return (MaterialStatistics)generic;
        }
        return null;
    }
    
    public String getDisplayName(final short variant) {
        if (this.hasClass()) {
            return this.getStatistics().getDisplayName();
        }
        return SUtil.getMaterialDisplayName(this.craftMaterial, variant);
    }
    
    public TickingMaterial getTickingInstance() {
        final Object generic = this.getGenericInstance();
        if (generic instanceof TickingMaterial) {
            return (TickingMaterial)generic;
        }
        return null;
    }
    
    public PlayerBoostStatistics getBoostStatistics() {
        final MaterialStatistics statistics = this.getStatistics();
        if (!(statistics instanceof PlayerBoostStatistics)) {
            return null;
        }
        return (PlayerBoostStatistics)statistics;
    }
    
    public SkullStatistics getSkullStatistics() {
        final MaterialStatistics statistics = this.getStatistics();
        if (!(statistics instanceof SkullStatistics)) {
            return null;
        }
        return (SkullStatistics)statistics;
    }
    
    public Ability getAbility() {
        if (!this.hasClass()) {
            return null;
        }
        final Object generic = this.getGenericInstance();
        if (generic instanceof Ability) {
            return (Ability)generic;
        }
        return null;
    }
    
    public OrbBuff getOrbBuff() {
        if (!this.hasClass()) {
            return null;
        }
        final Object generic = this.getGenericInstance();
        if (generic instanceof OrbBuff) {
            return (OrbBuff)generic;
        }
        return null;
    }
    
    public ItemData getItemData() {
        if (!this.hasClass()) {
            return null;
        }
        final Object generic = this.getGenericInstance();
        if (generic instanceof ItemData) {
            return (ItemData)generic;
        }
        return null;
    }
    
    public Object getGenericInstance() {
        if (null == this.clazz) {
            return null;
        }
        try {
            return this.clazz.newInstance();
        }
        catch (final InstantiationException | IllegalAccessException ex) {
            return null;
        }
    }
    
    public boolean hasClass() {
        return null != this.clazz;
    }
    
    public static ArmorSet getIncompleteArmorSet(final PlayerInventory inventory) {
        final SItem helmet = SItem.find(inventory.getHelmet());
        final SItem chestplate = SItem.find(inventory.getChestplate());
        final SItem leggings = SItem.find(inventory.getLeggings());
        final SItem boots = SItem.find(inventory.getBoots());
        for (final ArmorSet set : SMaterial.CACHED_SETS) {
            if (null != set.getHelmet() && null != helmet && helmet.getType().getStatistics().getClass() == set.getHelmet()) {
                return set;
            }
            if (null != set.getChestplate() && null != chestplate && chestplate.getType().getStatistics().getClass() == set.getChestplate()) {
                return set;
            }
            if (null != set.getLeggings() && null != leggings && leggings.getType().getStatistics().getClass() == set.getLeggings()) {
                return set;
            }
            if (null != set.getBoots() && null != boots && boots.getType().getStatistics().getClass() == set.getBoots()) {
                return set;
            }
        }
        return null;
    }
    
    public static void loadItems() {
        for (final SMaterial material : values()) {
            if (material.hasClass()) {
                material.getStatistics().load();
            }
        }
    }
    
    public Material getCraftMaterial() {
        return this.craftMaterial;
    }
    
    public short getData() {
        return this.data;
    }
    
    public boolean isCraft() {
        return this.craft;
    }
    
    public String getBaseName() {
        return this.baseName;
    }
    
    static {
        CACHED_SETS = (List)new ArrayList();
        SMaterial.YOUNG_DRAGON_SET = registerArmorSet(YoungDragonSet.class);
        SMaterial.SUPERIOR_DRAGON_SET = registerArmorSet(SuperiorDragonSet.class);
        SMaterial.WISE_DRAGON_SET = registerArmorSet(WiseDragonSet.class);
        SMaterial.UNSTABLE_DRAGON_SET = registerArmorSet(UnstableDragonSet.class);
        SMaterial.STRONG_DRAGON_SET = registerArmorSet(StrongDragonSet.class);
        SMaterial.OLD_DRAGON_SET = registerArmorSet(OldDragonSet.class);
        SMaterial.PROTECTOR_DRAGON_SET = registerArmorSet(ProtectorDragonSet.class);
        SMaterial.LAPIS_ARMOR_SET = registerArmorSet(LapisArmorSet.class);
        SMaterial.MINER_SET = registerArmorSet(MinerSet.class);
        SMaterial.NECRONS_SET = registerArmorSet(NecronFullSet.class);
        SMaterial.STORMS_SET = registerArmorSet(StormFullSet.class);
        SMaterial.SORROW_SET = registerArmorSet(SorrowArmorSet.class);
    }
    
    public enum VagueEntityMaterial
    {
        HELMET, 
        CHESTPLATE, 
        LEGGINGS, 
        BOOTS, 
        FRAGMENT;
        
        public SMaterial getEntityArmorPiece(final SEntityType type) {
            return SMaterial.getMaterial(type.name() + "_" + this.name());
        }
    }
}
