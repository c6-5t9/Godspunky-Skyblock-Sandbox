package net.hypixel.skyblock.entity.dungeons.boss.sadan;

import net.hypixel.skyblock.util.Sputnik;
import org.bukkit.Sound;
import net.hypixel.skyblock.entity.SEntityType;
import net.hypixel.skyblock.entity.SEntity;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.blocks.BaseBlock;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.BlockVector;
import com.sk89q.worldedit.bukkit.BukkitUtil;
import org.bukkit.Location;
import java.util.Iterator;
import org.bukkit.entity.Entity;
import net.hypixel.skyblock.util.SUtil;
import org.bukkit.World;

public class AnimationSequence
{
    public static void chainAnimation(final World w) {
        final boolean chaining = false;
        if (w.getName().contains((CharSequence)"f6")) {
            beginRenderDown(w);
            SUtil.delay(() -> beginRenderUp(w), 330L);
        }
    }
    
    public static void pasteBase(final World w, final int delay) {
        SUtil.delay(() -> Sputnik.pasteSchematic("chain_base", true, 186.0f, 105.0f, 271.0f, w), delay - 1);
        SUtil.delay(() -> Sputnik.pasteSchematic("chain_base", true, 186.0f, 105.0f, 271.0f, w), delay);
        SUtil.delay(() -> Sputnik.pasteSchematic("chain_base", true, 186.0f, 105.0f, 271.0f, w), delay + 1);
    }
    
    public static void pasteGlass(final World w, final int delay, final int phase) {
        if (phase == 0) {
            SUtil.delay(() -> Sputnik.pasteSchematic("f6_h1", true, 189.0f, 71.0f, 266.0f, w), delay);
        }
        else if (phase == 1) {
            SUtil.delay(() -> Sputnik.pasteSchematic("f6_h2", true, 190.0f, 71.0f, 269.0f, w), delay);
        }
        else if (phase == 2) {
            SUtil.delay(() -> Sputnik.pasteSchematic("f6_h3", true, 191.0f, 73.0f, 266.0f, w), delay);
        }
    }
    
    public static void pasteChain(final World w, final float y, final int delay, final boolean up) {
        SUtil.delay(() -> Sputnik.pasteSchematic("chain_main", true, 195.0f, y, 261.0f, w), delay);
        SUtil.delay(() -> w.playSound(new Location(w, 195.0, (double)y, 261.0), Sound.HORSE_ARMOR, 100.0f, 0.0f), delay);
    }
    
    public static void pasteAir(final World w, final float y, final int delay) {
        SUtil.delay(() -> edit(new Location(w, 191.0, (double)(y - 2.0f), 266.0), new Location(w, 191.0, 69.0, 266.0), w), delay + 1);
    }
    
    public static void beginRenderUp(final World w) {
        pasteChain(w, 69.0f, 1, false);
        pasteBase(w, 1);
        pasteChain(w, 71.0f, 20, false);
        pasteBase(w, 20);
        pasteAir(w, 71.0f, 20);
        pasteChain(w, 73.0f, 40, false);
        pasteBase(w, 40);
        pasteAir(w, 73.0f, 40);
        pasteChain(w, 75.0f, 60, false);
        pasteBase(w, 60);
        pasteAir(w, 75.0f, 60);
        pasteChain(w, 77.0f, 80, false);
        pasteBase(w, 80);
        pasteAir(w, 77.0f, 80);
        pasteChain(w, 79.0f, 100, false);
        pasteBase(w, 100);
        pasteAir(w, 79.0f, 100);
        pasteChain(w, 81.0f, 120, false);
        pasteBase(w, 120);
        pasteAir(w, 81.0f, 120);
        pasteChain(w, 83.0f, 140, false);
        pasteBase(w, 140);
        pasteGlass(w, 140, 0);
        pasteAir(w, 83.0f, 140);
        pasteChain(w, 85.0f, 160, false);
        pasteBase(w, 160);
        pasteAir(w, 85.0f, 160);
        pasteChain(w, 87.0f, 180, false);
        pasteBase(w, 180);
        pasteAir(w, 87.0f, 180);
        pasteChain(w, 89.0f, 200, false);
        pasteBase(w, 200);
        pasteAir(w, 89.0f, 200);
        pasteChain(w, 91.0f, 220, false);
        pasteBase(w, 220);
        pasteAir(w, 91.0f, 220);
        pasteChain(w, 93.0f, 240, false);
        pasteBase(w, 240);
        pasteAir(w, 93.0f, 240);
        pasteChain(w, 95.0f, 260, false);
        pasteBase(w, 260);
        pasteAir(w, 95.0f, 260);
        pasteChain(w, 97.0f, 280, false);
        pasteBase(w, 280);
        pasteAir(w, 97.0f, 280);
        pasteChain(w, 99.0f, 300, false);
        pasteBase(w, 300);
        pasteAir(w, 99.0f, 300);
        pasteBase(w, 301);
    }
    
    public static void beginRenderDown(final World w) {
        pasteChain(w, 99.0f, 1, false);
        pasteBase(w, 1);
        pasteChain(w, 97.0f, 20, false);
        pasteBase(w, 20);
        pasteChain(w, 95.0f, 40, false);
        pasteBase(w, 40);
        pasteChain(w, 93.0f, 60, false);
        pasteBase(w, 60);
        pasteChain(w, 91.0f, 80, false);
        pasteBase(w, 80);
        pasteChain(w, 89.0f, 110, false);
        pasteBase(w, 110);
        pasteChain(w, 87.0f, 130, false);
        pasteBase(w, 130);
        pasteChain(w, 85.0f, 150, false);
        pasteBase(w, 150);
        pasteChain(w, 83.0f, 170, false);
        pasteBase(w, 170);
        pasteChain(w, 81.0f, 180, false);
        pasteBase(w, 180);
        pasteChain(w, 79.0f, 200, false);
        pasteBase(w, 200);
        pasteChain(w, 77.0f, 220, false);
        pasteBase(w, 220);
        pasteChain(w, 75.0f, 240, false);
        pasteBase(w, 240);
        pasteChain(w, 73.0f, 260, false);
        pasteBase(w, 260);
        pasteChain(w, 71.0f, 280, false);
        pasteGlass(w, 280, 1);
        pasteBase(w, 280);
        pasteChain(w, 69.0f, 300, false);
        pasteBase(w, 300);
        pasteGlass(w, 300, 2);
        pasteBase(w, 301);
        SUtil.delay(() -> r(w), 301L);
        SUtil.delay(() -> new SEntity(new Location(w, 191.5, 54.0, 266.5, 180.0f, 0.0f), SEntityType.DUMMY_FUNCTION_2, new Object[0]), 300L);
    }
    
    public static void r(final World w) {
        for (final Entity e1 : w.getEntities()) {
            if (e1.hasMetadata("dummy_r")) {
                e1.remove();
            }
        }
    }
    
    public static void edit(final Location pos1, final Location pos2, final World w) {
        final com.sk89q.worldedit.world.World world = (com.sk89q.worldedit.world.World)BukkitUtil.getLocalWorld(w);
        final CuboidRegion selection = new CuboidRegion(world, (Vector)BlockVector.toBlockPoint(pos1.getX(), pos1.getY(), pos1.getZ()), (Vector)BlockVector.toBlockPoint(pos2.getX(), pos2.getY(), pos2.getZ()));
        final EditSession e = WorldEdit.getInstance().getEditSessionFactory().getEditSession(world, -1);
        try {
            e.setBlocks((Region)selection, new BaseBlock(0));
        }
        catch (final MaxChangedBlocksException e2) {
            e2.printStackTrace();
        }
    }
}
