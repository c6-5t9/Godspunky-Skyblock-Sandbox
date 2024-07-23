package net.hypixel.skyblock.item;

import java.util.Collection;
import org.bukkit.Material;
import java.util.Map;
import net.hypixel.skyblock.util.Groups;
import org.bukkit.Bukkit;
import java.util.Iterator;
import java.util.Collections;
import java.util.Arrays;
import java.util.ArrayList;
import net.hypixel.skyblock.user.User;
import org.bukkit.inventory.ItemStack;
import java.util.List;

public abstract class Recipe<T>
{
    protected static final List<List<SMaterial>> EXCHANGEABLES;
    protected SItem result;
    protected boolean useExchangeables;
    
    protected Recipe(final SItem result, final boolean useExchangeables) {
        this.result = result;
        this.useExchangeables = useExchangeables;
    }
    
    protected Recipe(final SItem result) {
        this(result, false);
    }
    
    public abstract T setResult(final SItem p0);
    
    public abstract List<MaterialQuantifiable> getIngredients();
    
    public static Recipe<?> parseRecipe(final ItemStack[] stacks) {
        final ShapedRecipe shaped = ShapedRecipe.parseShapedRecipe(stacks);
        if (shaped != null) {
            return shaped;
        }
        return ShapelessRecipe.parseShapelessRecipe(stacks);
    }
    
    public boolean isUnlockedForPlayer(final User user) {
        try {
            return user.getUnlockedRecipes().contains((Object)this.result.getDisplayName());
        }
        catch (final NullPointerException ex) {
            System.out.println("Result is null");
            return false;
        }
    }
    
    protected static MaterialQuantifiable[][] airless(final MaterialQuantifiable[][] grid) {
        final List<Integer> excluded = (List<Integer>)new ArrayList(0);
        for (int i = 0; i < grid.length; ++i) {
            boolean ex = true;
            for (final MaterialQuantifiable material : grid[i]) {
                if (material.getMaterial() != SMaterial.AIR) {
                    ex = false;
                    break;
                }
            }
            if (ex) {
                excluded.add((Object)i);
            }
        }
        final int amountExcluded = excluded.size();
        final MaterialQuantifiable[][] g = new MaterialQuantifiable[grid.length - amountExcluded][];
        int j = 0;
        int b = 0;
        while (j < grid.length) {
            if (excluded.contains((Object)j)) {
                ++b;
            }
            else {
                final MaterialQuantifiable[] line = grid[j];
                final int remaining = (int)Arrays.stream((Object[])line).filter(mat -> mat.getMaterial() != SMaterial.AIR).count();
                g[j - b] = new MaterialQuantifiable[remaining];
                int k = 0;
                int r = 0;
                while (k < line.length) {
                    if (line[k].getMaterial() != SMaterial.AIR) {
                        g[j - b][r] = line[k];
                        ++r;
                    }
                    ++k;
                }
            }
            ++j;
        }
        return g;
    }
    
    public static List<SMaterial> getExchangeablesOf(final SMaterial material) {
        for (final List<SMaterial> materials : Recipe.EXCHANGEABLES) {
            final int f = Collections.binarySearch((List)materials, (Object)material);
            if (f < 0) {
                continue;
            }
            return materials;
        }
        return null;
    }
    
    public static void loadRecipes() {
        final Iterator<org.bukkit.inventory.Recipe> iter = (Iterator<org.bukkit.inventory.Recipe>)Bukkit.recipeIterator();
        while (iter.hasNext()) {
            final org.bukkit.inventory.Recipe recipe = (org.bukkit.inventory.Recipe)iter.next();
            if (recipe.getResult() == null) {
                continue;
            }
            final Material result = recipe.getResult().getType();
            if (recipe instanceof org.bukkit.inventory.ShapedRecipe) {
                final org.bukkit.inventory.ShapedRecipe shaped = (org.bukkit.inventory.ShapedRecipe)recipe;
                final ShapedRecipe specShaped = new ShapedRecipe(SItem.convert(shaped.getResult()), Groups.EXCHANGEABLE_RECIPE_RESULTS.contains((Object)result)).shape(shaped.getShape());
                for (final Map.Entry<Character, ItemStack> entry : shaped.getIngredientMap().entrySet()) {
                    if (entry.getValue() == null) {
                        continue;
                    }
                    final ItemStack stack = (ItemStack)entry.getValue();
                    specShaped.set((char)entry.getKey(), SMaterial.getSpecEquivalent(stack.getType(), stack.getDurability()), stack.getAmount(), true);
                }
            }
            if (!(recipe instanceof org.bukkit.inventory.ShapelessRecipe)) {
                continue;
            }
            final org.bukkit.inventory.ShapelessRecipe shapeless = (org.bukkit.inventory.ShapelessRecipe)recipe;
            final ShapelessRecipe specShapeless = new ShapelessRecipe(SItem.convert(shapeless.getResult()), Groups.EXCHANGEABLE_RECIPE_RESULTS.contains((Object)result));
            for (final ItemStack stack2 : shapeless.getIngredientList()) {
                specShapeless.add(SMaterial.getSpecEquivalent(stack2.getType(), stack2.getDurability()), stack2.getAmount(), true);
            }
        }
    }
    
    public SItem getResult() {
        return this.result;
    }
    
    public boolean isUseExchangeables() {
        return this.useExchangeables;
    }
    
    public void setUseExchangeables(final boolean useExchangeables) {
        this.useExchangeables = useExchangeables;
    }
    
    static {
        EXCHANGEABLES = (List)new ArrayList((Collection)Arrays.asList((Object[])new List[] { Arrays.asList((Object[])new SMaterial[] { SMaterial.OAK_WOOD, SMaterial.SPRUCE_WOOD, SMaterial.BIRCH_WOOD, SMaterial.JUNGLE_WOOD, SMaterial.ACACIA_WOOD, SMaterial.DARK_OAK_WOOD }), Arrays.asList((Object[])new SMaterial[] { SMaterial.OAK_WOOD_PLANKS, SMaterial.SPRUCE_WOOD_PLANKS, SMaterial.BIRCH_WOOD_PLANKS, SMaterial.JUNGLE_WOOD_PLANKS, SMaterial.ACACIA_WOOD_PLANKS, SMaterial.DARK_OAK_WOOD_PLANKS }) }));
    }
}
