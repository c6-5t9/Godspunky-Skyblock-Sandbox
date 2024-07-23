package com.skyblock.skyblock.reflections.vfs;

import java.io.IOException;
import java.io.InputStream;
import com.skyblock.skyblock.reflections.util.ClasspathHelper;
import java.net.URLConnection;
import java.net.JarURLConnection;
import java.util.jar.JarFile;
import java.util.ArrayList;
import java.util.stream.StreamSupport;
import java.util.stream.Stream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URISyntaxException;
import java.io.File;
import java.util.function.Predicate;
import java.util.Collection;
import java.util.Arrays;
import java.util.Iterator;
import com.skyblock.skyblock.reflections.ReflectionsException;
import com.skyblock.skyblock.reflections.Reflections;
import java.net.URL;
import java.util.List;

public abstract class Vfs
{
    private static List<UrlType> defaultUrlTypes;
    
    public static List<UrlType> getDefaultUrlTypes() {
        return Vfs.defaultUrlTypes;
    }
    
    public static void setDefaultURLTypes(final List<UrlType> urlTypes) {
        Vfs.defaultUrlTypes = urlTypes;
    }
    
    public static void addDefaultURLTypes(final UrlType urlType) {
        Vfs.defaultUrlTypes.add(0, (Object)urlType);
    }
    
    public static Dir fromURL(final URL url) {
        return fromURL(url, Vfs.defaultUrlTypes);
    }
    
    public static Dir fromURL(final URL url, final List<UrlType> urlTypes) {
        for (final UrlType type : urlTypes) {
            try {
                if (!type.matches(url)) {
                    continue;
                }
                final Dir dir = type.createDir(url);
                if (dir != null) {
                    return dir;
                }
                continue;
            }
            catch (final Throwable e) {
                if (Reflections.log == null) {
                    continue;
                }
                Reflections.log.warn("could not create Dir using " + (Object)type + " from url " + url.toExternalForm() + ". skipping.", e);
            }
        }
        throw new ReflectionsException("could not create Vfs.Dir from url, no matching UrlType was found [" + url.toExternalForm() + "]\neither use fromURL(final URL url, final List<UrlType> urlTypes) or use the static setDefaultURLTypes(final List<UrlType> urlTypes) or addDefaultURLTypes(UrlType urlType) with your specialized UrlType.");
    }
    
    public static Dir fromURL(final URL url, final UrlType... urlTypes) {
        return fromURL(url, (List<UrlType>)Arrays.asList((Object[])urlTypes));
    }
    
    public static Iterable<File> findFiles(final Collection<URL> inUrls, final String packagePrefix, final Predicate<String> nameFilter) {
        final Predicate<File> fileNamePredicate = (Predicate<File>)(file -> {
            final String path = file.getRelativePath();
            if (path.startsWith(packagePrefix)) {
                final String filename = path.substring(path.indexOf(packagePrefix) + packagePrefix.length());
                return !filename.isEmpty() && nameFilter.test((Object)filename.substring(1));
            }
            return false;
        });
        return findFiles(inUrls, fileNamePredicate);
    }
    
    public static Iterable<File> findFiles(final Collection<URL> urls, final Predicate<File> filePredicate) {
        return (Iterable<File>)(() -> urls.stream().flatMap(url -> {
            try {
                return StreamSupport.stream(fromURL(url).getFiles().spliterator(), false);
            }
            catch (final Throwable e) {
                if (Reflections.log != null) {
                    Reflections.log.error("could not findFiles for url. continuing. [" + (Object)url + "]", e);
                }
                return Stream.of((Object[])new File[0]);
            }
        }).filter(filePredicate).iterator());
    }
    
    public static java.io.File getFile(final URL url) {
        try {
            final String path = url.toURI().getSchemeSpecificPart();
            final java.io.File file;
            if ((file = new java.io.File(path)).exists()) {
                return file;
            }
        }
        catch (final URISyntaxException ex) {}
        try {
            String path = URLDecoder.decode(url.getPath(), "UTF-8");
            if (path.contains((CharSequence)".jar!")) {
                path = path.substring(0, path.lastIndexOf(".jar!") + ".jar".length());
            }
            final java.io.File file;
            if ((file = new java.io.File(path)).exists()) {
                return file;
            }
        }
        catch (final UnsupportedEncodingException ex2) {}
        try {
            String path = url.toExternalForm();
            if (path.startsWith("jar:")) {
                path = path.substring("jar:".length());
            }
            if (path.startsWith("wsjar:")) {
                path = path.substring("wsjar:".length());
            }
            if (path.startsWith("file:")) {
                path = path.substring("file:".length());
            }
            if (path.contains((CharSequence)".jar!")) {
                path = path.substring(0, path.indexOf(".jar!") + ".jar".length());
            }
            if (path.contains((CharSequence)".war!")) {
                path = path.substring(0, path.indexOf(".war!") + ".war".length());
            }
            java.io.File file;
            if ((file = new java.io.File(path)).exists()) {
                return file;
            }
            path = path.replace((CharSequence)"%20", (CharSequence)" ");
            if ((file = new java.io.File(path)).exists()) {
                return file;
            }
        }
        catch (final Exception ex3) {}
        return null;
    }
    
    private static boolean hasJarFileInPath(final URL url) {
        return url.toExternalForm().matches(".*\\.jar(!.*|$)");
    }
    
    private static boolean hasInnerJarFileInPath(final URL url) {
        return url.toExternalForm().matches(".+\\.jar!/.+");
    }
    
    static {
        Vfs.defaultUrlTypes = (List<UrlType>)new ArrayList((Collection)Arrays.asList((Object[])DefaultUrlTypes.values()));
    }
    
    public interface Dir
    {
        String getPath();
        
        Iterable<File> getFiles();
        
        default void close() {
        }
    }
    
    public enum DefaultUrlTypes implements UrlType
    {
        jarFile {
            public boolean matches(final URL url) {
                return url.getProtocol().equals((Object)"file") && hasJarFileInPath(url);
            }
            
            public Dir createDir(final URL url) throws Exception {
                return new ZipDir(new JarFile(Vfs.getFile(url)));
            }
        }, 
        jarUrl {
            public boolean matches(final URL url) {
                return ("jar".equals((Object)url.getProtocol()) || "zip".equals((Object)url.getProtocol()) || "wsjar".equals((Object)url.getProtocol())) && !hasInnerJarFileInPath(url);
            }
            
            public Dir createDir(final URL url) throws Exception {
                try {
                    final URLConnection urlConnection = url.openConnection();
                    if (urlConnection instanceof JarURLConnection) {
                        urlConnection.setUseCaches(false);
                        return new ZipDir(((JarURLConnection)urlConnection).getJarFile());
                    }
                }
                catch (final Throwable t) {}
                final java.io.File file = Vfs.getFile(url);
                if (file != null) {
                    return new ZipDir(new JarFile(file));
                }
                return null;
            }
        }, 
        directory {
            public boolean matches(final URL url) {
                if (url.getProtocol().equals((Object)"file") && !hasJarFileInPath(url)) {
                    final java.io.File file = Vfs.getFile(url);
                    return file != null && file.isDirectory();
                }
                return false;
            }
            
            public Dir createDir(final URL url) throws Exception {
                return new SystemDir(Vfs.getFile(url));
            }
        }, 
        jboss_vfs {
            public boolean matches(final URL url) {
                return url.getProtocol().equals((Object)"vfs");
            }
            
            public Dir createDir(final URL url) throws Exception {
                return JbossDir.createDir(url);
            }
        }, 
        jboss_vfsfile {
            public boolean matches(final URL url) throws Exception {
                return "vfszip".equals((Object)url.getProtocol()) || "vfsfile".equals((Object)url.getProtocol());
            }
            
            public Dir createDir(final URL url) throws Exception {
                return new UrlTypeVFS().createDir(url);
            }
        }, 
        bundle {
            public boolean matches(final URL url) throws Exception {
                return url.getProtocol().startsWith("bundle");
            }
            
            public Dir createDir(final URL url) throws Exception {
                return Vfs.fromURL((URL)ClasspathHelper.contextClassLoader().loadClass("org.eclipse.core.runtime.FileLocator").getMethod("resolve", URL.class).invoke((Object)null, new Object[] { url }));
            }
        }, 
        jarInputStream {
            public boolean matches(final URL url) throws Exception {
                return url.toExternalForm().contains((CharSequence)".jar");
            }
            
            public Dir createDir(final URL url) throws Exception {
                return new JarInputDir(url);
            }
        };
    }
    
    public interface UrlType
    {
        boolean matches(final URL p0) throws Exception;
        
        Dir createDir(final URL p0) throws Exception;
    }
    
    public interface File
    {
        String getName();
        
        String getRelativePath();
        
        InputStream openInputStream() throws IOException;
    }
}
