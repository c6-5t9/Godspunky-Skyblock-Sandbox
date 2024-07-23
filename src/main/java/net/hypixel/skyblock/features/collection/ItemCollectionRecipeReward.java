package net.hypixel.skyblock.features.collection;

import org.bukkit.ChatColor;
import net.hypixel.skyblock.user.User;
import net.hypixel.skyblock.item.SItem;
import java.util.Iterator;
import net.hypixel.skyblock.item.ShapelessRecipe;
import net.hypixel.skyblock.item.Recipe;
import net.hypixel.skyblock.item.ShapedRecipe;
import org.bukkit.entity.Player;
import net.hypixel.skyblock.item.SMaterial;

public class ItemCollectionRecipeReward extends ItemCollectionReward
{
    private final SMaterial material;
    
    public ItemCollectionRecipeReward(final SMaterial material) {
        super(Type.RECIPE);
        this.material = material;
    }
    
    @Override
    public void onAchieve(final Player player) {
        for (final ShapedRecipe shapedRecipe : ShapedRecipe.CACHED_RECIPES) {
            if (this.recipeMatchesMaterial(shapedRecipe, this.material) && !shapedRecipe.isVanilla()) {
                this.unlockRecipe(player, shapedRecipe);
            }
        }
        for (final ShapelessRecipe shapelessRecipe : ShapelessRecipe.CACHED_RECIPES) {
            if (this.recipeMatchesMaterial(shapelessRecipe, this.material) && !shapelessRecipe.isVanilla()) {
                this.unlockRecipe(player, shapelessRecipe);
            }
        }
    }
    
    private boolean recipeMatchesMaterial(final Recipe recipe, final SMaterial material) {
        final SItem sItem = SItem.of(material);
        return recipe.getResult().getDisplayName().equalsIgnoreCase(sItem.getDisplayName());
    }
    
    private void unlockRecipe(final Player player, final Recipe recipe) {
        final SItem sItem = SItem.of(this.material);
        final User user = User.getUser(player.getUniqueId());
        if (!user.getUnlockedRecipes().contains((Object)sItem.getDisplayName())) {
            user.getUnlockedRecipes().add((Object)sItem.getDisplayName());
        }
    }
    
    @Override
    public String toRewardString() {
        return (Object)ChatColor.GRAY + this.material.getDisplayName(this.material.getData()) + (Object)ChatColor.GRAY + " Recipe";
    }
}
