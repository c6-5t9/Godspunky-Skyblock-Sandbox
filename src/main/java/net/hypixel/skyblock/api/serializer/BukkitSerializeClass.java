package net.hypixel.skyblock.api.serializer;

import java.io.IOException;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.Bukkit;
import java.io.InputStream;
import org.bukkit.util.io.BukkitObjectInputStream;
import java.io.ByteArrayInputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;
import java.io.OutputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import java.io.ByteArrayOutputStream;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.PlayerInventory;

public class BukkitSerializeClass
{
    public static String[] playerInventoryToBase64(final PlayerInventory playerInventory) throws IllegalStateException {
        final String content = toBase64((Inventory)playerInventory);
        final String armor = itemStackArrayToBase64(playerInventory.getArmorContents());
        return new String[] { content, armor };
    }
    
    public static String itemStackArrayToBase64(final ItemStack[] items) throws IllegalStateException {
        try {
            final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            final BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream((OutputStream)outputStream);
            dataOutput.writeInt(items.length);
            for (int i = 0; i < items.length; ++i) {
                dataOutput.writeObject((Object)items[i]);
            }
            dataOutput.close();
            return Base64Coder.encodeLines(outputStream.toByteArray());
        }
        catch (final Exception e) {
            throw new IllegalStateException("Unable to save item stacks.", (Throwable)e);
        }
    }
    
    public static String toBase64(final Inventory inventory) throws IllegalStateException {
        try {
            final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            final BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream((OutputStream)outputStream);
            dataOutput.writeInt(inventory.getSize());
            for (int i = 0; i < inventory.getSize(); ++i) {
                dataOutput.writeObject((Object)inventory.getItem(i));
            }
            dataOutput.close();
            return Base64Coder.encodeLines(outputStream.toByteArray());
        }
        catch (final Exception e) {
            throw new IllegalStateException("Unable to save item stacks.", (Throwable)e);
        }
    }
    
    public static Inventory fromBase64(final String data) throws IOException {
        try {
            final ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
            final BukkitObjectInputStream dataInput = new BukkitObjectInputStream((InputStream)inputStream);
            final Inventory inventory = Bukkit.getServer().createInventory((InventoryHolder)null, dataInput.readInt());
            for (int i = 0; i < inventory.getSize(); ++i) {
                inventory.setItem(i, (ItemStack)dataInput.readObject());
            }
            dataInput.close();
            return inventory;
        }
        catch (final ClassNotFoundException e) {
            throw new IOException("Unable to decode class type.", (Throwable)e);
        }
    }
    
    public static ItemStack[] itemStackArrayFromBase64(final String data) throws IOException {
        try {
            final ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
            final BukkitObjectInputStream dataInput = new BukkitObjectInputStream((InputStream)inputStream);
            final ItemStack[] items = new ItemStack[dataInput.readInt()];
            for (int i = 0; i < items.length; ++i) {
                items[i] = (ItemStack)dataInput.readObject();
            }
            dataInput.close();
            return items;
        }
        catch (final ClassNotFoundException e) {
            throw new IOException("Unable to decode class type.", (Throwable)e);
        }
    }
}
