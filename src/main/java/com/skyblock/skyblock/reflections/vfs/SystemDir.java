package com.skyblock.skyblock.reflections.vfs;

import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.io.IOException;
import com.skyblock.skyblock.reflections.ReflectionsException;
import java.nio.file.Files;
import java.nio.file.FileVisitOption;
import java.util.Iterator;
import java.util.Collections;
import java.io.File;

public class SystemDir implements Vfs.Dir
{
    private final java.io.File file;
    
    public SystemDir(final java.io.File file) {
        if (file != null && (!file.isDirectory() || !file.canRead())) {
            throw new RuntimeException("cannot use dir " + (Object)file);
        }
        this.file = file;
    }
    
    @Override
    public String getPath() {
        return (this.file != null) ? this.file.getPath().replace((CharSequence)"\\", (CharSequence)"/") : "/NO-SUCH-DIRECTORY/";
    }
    
    @Override
    public Iterable<Vfs.File> getFiles() {
        if (this.file == null || !this.file.exists()) {
            return (Iterable<Vfs.File>)Collections.emptyList();
        }
        return (Iterable<Vfs.File>)(() -> {
            try {
                return Files.walk(this.file.toPath(), new FileVisitOption[0]).filter(x$0 -> Files.isRegularFile(x$0, new LinkOption[0])).map(path -> new SystemFile(this, path.toFile())).iterator();
            }
            catch (final IOException e) {
                throw new ReflectionsException("could not get files for " + (Object)this.file, (Throwable)e);
            }
        });
    }
}
