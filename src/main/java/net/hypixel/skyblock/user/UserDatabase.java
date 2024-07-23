package net.hypixel.skyblock.user;

import net.hypixel.skyblock.database.DatabaseManager;
import org.bson.conversions.Bson;
import org.bson.Document;
import com.mongodb.client.MongoCollection;
import java.util.concurrent.CompletableFuture;

public class UserDatabase
{
    static final CompletableFuture<MongoCollection<Document>> collectionFuture;
    public final String id;
    
    public UserDatabase(final String uuid) {
        this.id = uuid;
    }
    
    public CompletableFuture<Boolean> existsAsync() {
        return (CompletableFuture<Boolean>)UserDatabase.collectionFuture.thenApply(userCollection -> {
            final Document user = (Document)userCollection.find((Bson)new Document("uuid", (Object)this.id)).first();
            return user != null;
        });
    }
    
    static {
        collectionFuture = DatabaseManager.getCollection("users");
    }
}
