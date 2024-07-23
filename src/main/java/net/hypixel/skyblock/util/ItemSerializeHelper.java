package net.hypixel.skyblock.util;

import java.io.OutputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import org.bukkit.util.io.BukkitObjectInputStream;
import java.io.ByteArrayInputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;
import org.bukkit.inventory.ItemStack;

public class ItemSerializeHelper
{
    public static ItemStack[] deserializeStackArray(final String data) {
        try {
            final byte[] decodedBytes = Base64Coder.decodeLines(data);
            try (final ByteArrayInputStream inputStream = new ByteArrayInputStream(decodedBytes);
                 final BukkitObjectInputStream dataInput = new BukkitObjectInputStream((InputStream)inputStream)) {
                final int length = dataInput.readInt();
                final ItemStack[] items = new ItemStack[length];
                for (int i = 0; i < length; ++i) {
                    items[i] = (ItemStack)dataInput.readObject();
                }
                return items;
            }
            catch (final ClassNotFoundException e) {
                throw new IOException("Unable to decode class type.", (Throwable)e);
            }
        }
        catch (final Exception e2) {
            return new ItemStack[0];
        }
    }
    
    public static String serializeStackArray(final ItemStack[] items) throws IllegalStateException {
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
}
