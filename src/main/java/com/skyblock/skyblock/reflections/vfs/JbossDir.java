package com.skyblock.skyblock.reflections.vfs;

import java.util.Collection;
import java.util.Stack;
import java.util.Iterator;
import java.util.jar.JarFile;
import java.net.URL;
import org.jboss.vfs.VirtualFile;

public class JbossDir implements Vfs.Dir
{
    private final VirtualFile virtualFile;
    
    private JbossDir(final VirtualFile virtualFile) {
        this.virtualFile = virtualFile;
    }
    
    public static Vfs.Dir createDir(final URL url) throws Exception {
        final VirtualFile virtualFile = (VirtualFile)url.openConnection().getContent();
        if (virtualFile.isFile()) {
            return new ZipDir(new JarFile(virtualFile.getPhysicalFile()));
        }
        return new JbossDir(virtualFile);
    }
    
    @Override
    public String getPath() {
        return this.virtualFile.getPathName();
    }
    
    @Override
    public Iterable<Vfs.File> getFiles() {
        return (Iterable<Vfs.File>)(() -> new Iterator<Vfs.File>() {
            Vfs.File entry;
            final Stack stack;
            
            {
                this.stack = new Stack();
                this.entry = null;
                this.stack.addAll((Collection)JbossDir.this.virtualFile.getChildren());
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
                while (!this.stack.isEmpty()) {
                    final VirtualFile file = (VirtualFile)this.stack.pop();
                    if (!file.isDirectory()) {
                        return new JbossFile(JbossDir.this, file);
                    }
                    this.stack.addAll((Collection)file.getChildren());
                }
                return null;
            }
        });
    }
}
