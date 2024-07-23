package net.hypixel.skyblock.features.minion;

import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.Material;
import org.bukkit.Color;
import java.util.List;

public abstract class SkyBlockMinion
{
    public abstract List<MinionTier> getTiers();
    
    public abstract Color getBootColour();
    
    public abstract Color getLeggingsColour();
    
    public abstract Color getChestplateColour();
    
    public abstract List<MinionExpectations> getExpectations();
    
    public class MinionTier
    {
        private int tier;
        private int timeBetweenActions;
        private int storage;
        private String texture;
        private Material heldItem;
        private boolean craftable;
        
        public MinionTier(final int tier, final int timeBetweenActions, final int storage, final String texture, final Material heldItem, final boolean craftable) {
            this.tier = tier;
            this.timeBetweenActions = timeBetweenActions;
            this.storage = storage;
            this.texture = texture;
            this.heldItem = heldItem;
            this.craftable = craftable;
        }
        
        public int getTier() {
            return this.tier;
        }
        
        public int getTimeBetweenActions() {
            return this.timeBetweenActions;
        }
        
        public int getStorage() {
            return this.storage;
        }
        
        public String getTexture() {
            return this.texture;
        }
        
        public Material getHeldItem() {
            return this.heldItem;
        }
        
        public boolean isCraftable() {
            return this.craftable;
        }
        
        public int getSlots() {
            return this.storage / 64;
        }
    }
    
    public class MinionExpectations
    {
        private int yLevel;
        private Block[] materials;
        
        public MinionExpectations(final int yLevel, final Block... materials) {
            this.yLevel = yLevel;
            this.materials = materials;
        }
        
        public int getYLevel() {
            return this.yLevel;
        }
        
        public Block[] getMaterials() {
            return this.materials;
        }
    }
}
