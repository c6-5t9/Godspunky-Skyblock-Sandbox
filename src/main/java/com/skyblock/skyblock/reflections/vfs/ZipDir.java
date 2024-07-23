package com.skyblock.skyblock.reflections.vfs;

import java.util.zip.ZipEntry;
import java.util.Iterator;
import java.io.IOException;
import com.skyblock.skyblock.reflections.Reflections;
import java.util.jar.JarFile;
import java.util.zip.ZipFile;

public class ZipDir implements Vfs.Dir
{
    final ZipFile jarFile;
    
    public ZipDir(final JarFile jarFile) {
        this.jarFile = (ZipFile)jarFile;
    }
    
    @Override
    public String getPath() {
        return (this.jarFile != null) ? this.jarFile.getName().replace((CharSequence)"\\", (CharSequence)"/") : "/NO-SUCH-DIRECTORY/";
    }
    
    @Override
    public Iterable<Vfs.File> getFiles() {
        return (Iterable<Vfs.File>)(() -> this.jarFile.stream().filter(entry -> !entry.isDirectory()).map(entry -> new com.skyblock.skyblock.reflections.vfs.ZipFile(this, entry)).iterator());
    }
    
    @Override
    public void close() {
        try {
            this.jarFile.close();
        }
        catch (final IOException e) {
            if (Reflections.log != null) {
                Reflections.log.warn("Could not close JarFile", (Throwable)e);
            }
        }
    }
    
    @Override
    public String toString() {
        return this.jarFile.getName();
    }
}
