package com.skyblock.skyblock.reflections.serializers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.charset.Charset;
import java.io.File;
import java.io.Reader;
import java.io.InputStreamReader;
import com.google.gson.GsonBuilder;
import com.skyblock.skyblock.reflections.Reflections;
import java.io.InputStream;

public class JsonSerializer implements Serializer
{
    @Override
    public Reflections read(final InputStream inputStream) {
        return (Reflections)new GsonBuilder().setPrettyPrinting().create().fromJson((Reader)new InputStreamReader(inputStream), (Class)Reflections.class);
    }
    
    @Override
    public File save(final Reflections reflections, final String filename) {
        try {
            final File file = Serializer.prepareFile(filename);
            final String json = new GsonBuilder().setPrettyPrinting().create().toJson((Object)reflections);
            Files.write(file.toPath(), json.getBytes(Charset.defaultCharset()), new OpenOption[0]);
            return file;
        }
        catch (final IOException e) {
            throw new RuntimeException((Throwable)e);
        }
    }
}
