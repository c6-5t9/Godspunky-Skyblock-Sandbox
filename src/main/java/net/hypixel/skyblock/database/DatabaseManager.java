package net.hypixel.skyblock.database;

import com.mongodb.client.MongoClients;
import org.bson.Document;
import com.mongodb.client.MongoCollection;
import java.util.concurrent.CompletableFuture;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoClient;

public class DatabaseManager
{
    private static MongoClient mongoClient;
    private static MongoDatabase database;
    
    public static CompletableFuture<Void> connectToDatabase(final String connectionString, final String databaseName) {
        final CompletableFuture<MongoClient> clientFuture = (CompletableFuture<MongoClient>)CompletableFuture.supplyAsync(() -> MongoClients.create(connectionString));
        return (CompletableFuture<Void>)clientFuture.thenAccept(client -> {
            DatabaseManager.mongoClient = client;
            DatabaseManager.database = client.getDatabase(databaseName);
        });
    }
    
    public static CompletableFuture<MongoCollection<Document>> getCollection(final String collectionName) {
        if (DatabaseManager.database == null) {
            return (CompletableFuture<MongoCollection<Document>>)CompletableFuture.completedFuture((Object)null);
        }
        return (CompletableFuture<MongoCollection<Document>>)CompletableFuture.completedFuture((Object)DatabaseManager.database.getCollection(collectionName));
    }
    
    public static CompletableFuture<Void> closeConnection() {
        if (DatabaseManager.mongoClient != null) {
            return (CompletableFuture<Void>)CompletableFuture.runAsync(DatabaseManager.mongoClient::close);
        }
        return (CompletableFuture<Void>)CompletableFuture.completedFuture((Object)null);
    }
}
