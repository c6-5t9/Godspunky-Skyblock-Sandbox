package com.skyblock.skyblock.reflections.vfs;

import java.io.InputStream;
import java.util.zip.ZipEntry;
import com.skyblock.skyblock.reflections.ReflectionsException;
import java.util.Iterator;
import java.io.IOException;
import com.skyblock.skyblock.reflections.Reflections;
import java.util.jar.JarInputStream;
import java.net.URL;

public class JarInputDir implements Vfs.Dir
{
    private final URL url;
    JarInputStream jarInputStream;
    long cursor;
    long nextCursor;
    
    public JarInputDir(final URL url) {
        this.cursor = 0L;
        this.nextCursor = 0L;
        this.url = url;
    }
    
    @Override
    public String getPath() {
        return this.url.getPath();
    }
    
    @Override
    public Iterable<Vfs.File> getFiles() {
        return (Iterable<Vfs.File>)(() -> new Iterator<Vfs.File>() {
            Vfs.File entry;
            
            {
                try {
                    JarInputDir.this.jarInputStream = new JarInputStream(JarInputDir.this.url.openConnection().getInputStream());
                }
                catch (final Exception e) {
                    throw new ReflectionsException("Could not open url connection", (Throwable)e);
                }
                this.entry = null;
            }
            
            public boolean hasNext() {
                return this.entry != null || (this.entry = this.computeNext()) != null;
            }
            
            public Vfs.File next() {
                final Vfs.File next = this.entry;
                this.entry = null;
                return next;
            }
            
            private Vfs.File computeNext() {
                try {
                    while (true) {
                        final ZipEntry entry = (ZipEntry)JarInputDir.this.jarInputStream.getNextJarEntry();
                        if (entry == null) {
                            return null;
                        }
                        long size = entry.getSize();
                        if (size < 0L) {
                            size += 4294967295L;
                        }
                        final JarInputDir this$0 = JarInputDir.this;
                        this$0.nextCursor += size;
                        if (!entry.isDirectory()) {
                            return new JarInputFile(entry, JarInputDir.this, JarInputDir.this.cursor, JarInputDir.this.nextCursor);
                        }
                    }
                }
                catch (final IOException e) {
                    throw new ReflectionsException("could not get next zip entry", (Throwable)e);
                }
            }
        });
    }
    
    @Override
    public void close() {
        try {
            if (this.jarInputStream != null) {
                ((InputStream)this.jarInputStream).close();
            }
        }
        catch (final IOException e) {
            if (Reflections.log != null) {
                Reflections.log.warn("Could not close InputStream", (Throwable)e);
            }
        }
    }
}
