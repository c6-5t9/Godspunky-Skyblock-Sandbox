package net.hypixel.skyblock.item;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class MaterialQuantifiable
{
    private SMaterial material;
    private int amount;
    
    public MaterialQuantifiable(final SMaterial material, final int amount) {
        this.material = material;
        this.amount = amount;
    }
    
    public MaterialQuantifiable(final SMaterial material) {
        this(material, 1);
    }
    
    public MaterialQuantifiable setMaterial(final SMaterial material) {
        this.material = material;
        return this;
    }
    
    public MaterialQuantifiable setAmount(final int amount) {
        this.amount = amount;
        return this;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof MaterialQuantifiable)) {
            return false;
        }
        final MaterialQuantifiable material = (MaterialQuantifiable)o;
        return material.material == this.material && material.amount == this.amount;
    }
    
    public MaterialQuantifiable clone() {
        return new MaterialQuantifiable(this.material, this.amount);
    }
    
    @Override
    public String toString() {
        return "MQ{material=" + ((null != this.material) ? this.material.name() : "?") + ", amount=" + this.amount + "}";
    }
    
    public static MaterialQuantifiable of(final ItemStack stack) {
        if (null == stack || Material.AIR == stack.getType()) {
            return new MaterialQuantifiable(SMaterial.AIR, (null != stack) ? stack.getAmount() : 1);
        }
        SItem found = SItem.find(stack);
        if (null == found) {
            found = SItem.of(SMaterial.getSpecEquivalent(stack.getType(), stack.getDurability()));
        }
        return new MaterialQuantifiable(found.getType(), stack.getAmount());
    }
    
    public static MaterialQuantifiable[] of(final ItemStack[] stacks) {
        final MaterialQuantifiable[] materials = new MaterialQuantifiable[stacks.length];
        for (int i = 0; i < stacks.length; ++i) {
            materials[i] = of(stacks[i]);
        }
        return materials;
    }
    
    public SMaterial getMaterial() {
        return this.material;
    }
    
    public int getAmount() {
        return this.amount;
    }
}
