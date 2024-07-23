package net.hypixel.skyblock.item;

import org.bson.Document;
import java.util.Iterator;
import java.util.Arrays;
import org.bukkit.inventory.ItemStack;
import net.hypixel.skyblock.util.SUtil;
import java.util.ArrayList;
import java.util.List;

public class ShapelessRecipe extends Recipe<ShapelessRecipe>
{
    public static final List<ShapelessRecipe> CACHED_RECIPES;
    private final List<MaterialQuantifiable> ingredientList;
    private boolean isVanilla;
    
    public ShapelessRecipe(final SItem result, final boolean usesExchangeables) {
        super(result, usesExchangeables);
        this.ingredientList = (List<MaterialQuantifiable>)new ArrayList();
        ShapelessRecipe.CACHED_RECIPES.add((Object)this);
    }
    
    public ShapelessRecipe(final SItem result) {
        this(result, false);
    }
    
    public ShapelessRecipe(final SMaterial material, final int amount) {
        this(SUtil.setSItemAmount(SItem.of(material), amount));
    }
    
    public ShapelessRecipe(final SMaterial material) {
        this(SItem.of(material));
    }
    
    @Override
    public ShapelessRecipe setResult(final SItem result) {
        this.result = result;
        return this;
    }
    
    public ShapelessRecipe add(final MaterialQuantifiable material, final boolean isVanilla) {
        this.ingredientList.add((Object)material.clone());
        this.isVanilla = isVanilla;
        return this;
    }
    
    public ShapelessRecipe add(final MaterialQuantifiable material) {
        this.ingredientList.add((Object)material.clone());
        return this;
    }
    
    public ShapelessRecipe add(final SMaterial material, final int amount) {
        return this.add(new MaterialQuantifiable(material, amount), false);
    }
    
    public ShapelessRecipe add(final SMaterial material, final int amount, final boolean isVanilla) {
        return this.add(new MaterialQuantifiable(material, amount), isVanilla);
    }
    
    @Override
    public List<MaterialQuantifiable> getIngredients() {
        return this.ingredientList;
    }
    
    @Override
    public String toString() {
        return "ShapelessRecipe{" + this.ingredientList.toString() + "}";
    }
    
    protected static ShapelessRecipe parseShapelessRecipe(final ItemStack[] stacks) {
        if (9 != stacks.length) {
            throw new UnsupportedOperationException("Recipe parsing requires a 9 element array!");
        }
        final MaterialQuantifiable[] materials = SUtil.unnest(Recipe.airless(new MaterialQuantifiable[][] { MaterialQuantifiable.of((ItemStack[])Arrays.copyOfRange((Object[])stacks, 0, 3)), MaterialQuantifiable.of((ItemStack[])Arrays.copyOfRange((Object[])stacks, 3, 6)), MaterialQuantifiable.of((ItemStack[])Arrays.copyOfRange((Object[])stacks, 6, 9)) }), MaterialQuantifiable.class);
        for (final ShapelessRecipe recipe : ShapelessRecipe.CACHED_RECIPES) {
            final List<MaterialQuantifiable> ingredients = recipe.getIngredientList();
            if (materials.length != ingredients.size()) {
                continue;
            }
            boolean found = true;
            final MaterialQuantifiable[] copy = (MaterialQuantifiable[])Arrays.copyOf((Object[])materials, materials.length);
            for (final MaterialQuantifiable ingredient : ingredients) {
                if (!contains(recipe.useExchangeables, copy, ingredient)) {
                    found = false;
                    break;
                }
            }
            if (!found) {
                continue;
            }
            return recipe;
        }
        return null;
    }
    
    private static boolean contains(final boolean usesExchangeables, final MaterialQuantifiable[] grid, final MaterialQuantifiable test) {
        final List<SMaterial> exchangeables = Recipe.getExchangeablesOf(test.getMaterial());
        for (int i = 0; i < grid.length; ++i) {
            final MaterialQuantifiable material = grid[i];
            if (null != material) {
                if (usesExchangeables && null != exchangeables && exchangeables.contains((Object)material.getMaterial()) && material.getAmount() >= test.getAmount()) {
                    grid[i] = null;
                    return true;
                }
                if (material.getMaterial() == test.getMaterial() && material.getAmount() >= test.getAmount()) {
                    grid[i] = null;
                    return true;
                }
            }
        }
        return false;
    }
    
    public static void fromDocument(final Document doc) {
        final String resultType = doc.getString((Object)"result");
        final int resultAmount = doc.getInteger((Object)"amount");
        final List<Document> ingredientList = (List<Document>)doc.get((Object)"ingredientList");
        final SItem resultItem = SItem.of(SMaterial.valueOf(resultType));
        resultItem.setAmount(resultAmount);
        final List<MaterialQuantifiable> ingredients = (List<MaterialQuantifiable>)new ArrayList();
        for (final Document ingredientDoc : ingredientList) {
            final String materialType = ingredientDoc.getString((Object)"material");
            final int amount = ingredientDoc.getInteger((Object)"amount");
            final SMaterial material = SMaterial.valueOf(materialType);
            final MaterialQuantifiable mq = new MaterialQuantifiable(material, amount);
            ingredients.add((Object)mq);
        }
        final ShapelessRecipe recipe = new ShapelessRecipe(resultItem);
        for (final MaterialQuantifiable ingredient : ingredients) {
            recipe.add(ingredient);
        }
    }
    
    public boolean isVanilla() {
        return this.isVanilla;
    }
    
    public List<MaterialQuantifiable> getIngredientList() {
        return this.ingredientList;
    }
    
    static {
        CACHED_RECIPES = (List)new ArrayList();
    }
}
