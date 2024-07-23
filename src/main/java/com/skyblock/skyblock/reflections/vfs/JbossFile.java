package com.skyblock.skyblock.reflections.vfs;

import java.io.IOException;
import java.io.InputStream;
import org.jboss.vfs.VirtualFile;

public class JbossFile implements Vfs.File
{
    private final JbossDir root;
    private final VirtualFile virtualFile;
    
    public JbossFile(final JbossDir root, final VirtualFile virtualFile) {
        this.root = root;
        this.virtualFile = virtualFile;
    }
    
    @Override
    public String getName() {
        return this.virtualFile.getName();
    }
    
    @Override
    public String getRelativePath() {
        final String filepath = this.virtualFile.getPathName();
        if (filepath.startsWith(this.root.getPath())) {
            return filepath.substring(this.root.getPath().length() + 1);
        }
        return null;
    }
    
    @Override
    public InputStream openInputStream() throws IOException {
        return this.virtualFile.openStream();
    }
}
