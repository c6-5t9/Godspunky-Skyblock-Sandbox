package net.hypixel.skyblock.database;

import java.util.Iterator;
import java.util.List;
import net.hypixel.skyblock.item.MaterialQuantifiable;
import java.util.ArrayList;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.FindIterable;
import net.hypixel.skyblock.item.ShapelessRecipe;
import org.bson.Document;
import com.mongodb.client.MongoCollection;

public class RecipeDatabase
{
    public static void loadRecipes() {
        final FindIterable<Document> recipeDocuments = (FindIterable<Document>)((MongoCollection)DatabaseManager.getCollection("recipes").join()).find();
        try (final MongoCursor<Document> cursor = (MongoCursor<Document>)recipeDocuments.iterator()) {
            while (cursor.hasNext()) {
                final Document doc = (Document)cursor.next();
                ShapelessRecipe.fromDocument(doc);
            }
        }
    }
    
    public static void save(final ShapelessRecipe recipe) {
        final List<Document> ingredientList = (List<Document>)new ArrayList();
        for (final MaterialQuantifiable mq : recipe.getIngredientList()) {
            final Document ingredientDoc = new Document("material", (Object)mq.getMaterial().name()).append("amount", (Object)mq.getAmount());
            ingredientList.add((Object)ingredientDoc);
        }
        final Document doc = new Document("result", (Object)recipe.getResult().getType().name()).append("amount", (Object)recipe.getResult().getStack().getAmount()).append("ingredientList", (Object)ingredientList);
        ((MongoCollection)DatabaseManager.getCollection("recipes").join()).insertOne((Object)doc);
    }
}
