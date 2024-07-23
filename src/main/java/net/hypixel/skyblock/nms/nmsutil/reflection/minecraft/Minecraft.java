package net.hypixel.skyblock.nms.nmsutil.reflection.minecraft;

import java.util.regex.Matcher;
import org.bukkit.Bukkit;
import java.lang.reflect.Field;
import net.hypixel.skyblock.nms.nmsutil.reflection.resolver.MethodResolver;
import net.hypixel.skyblock.nms.nmsutil.reflection.resolver.FieldResolver;
import java.lang.reflect.Constructor;
import net.hypixel.skyblock.nms.nmsutil.reflection.resolver.ConstructorResolver;
import org.bukkit.entity.Entity;
import java.lang.reflect.Method;
import net.hypixel.skyblock.nms.nmsutil.reflection.util.AccessUtil;
import net.hypixel.skyblock.nms.nmsutil.reflection.resolver.minecraft.OBCClassResolver;
import net.hypixel.skyblock.nms.nmsutil.reflection.resolver.minecraft.NMSClassResolver;
import java.util.regex.Pattern;

public class Minecraft
{
    static final Pattern NUMERIC_VERSION_PATTERN;
    public static final Version VERSION;
    private static NMSClassResolver nmsClassResolver;
    private static OBCClassResolver obcClassResolver;
    private static Class<?> NmsEntity;
    private static Class<?> CraftEntity;
    
    public static String getVersion() {
        return Minecraft.VERSION.name() + ".";
    }
    
    public static Object getHandle(final Object object) throws ReflectiveOperationException {
        Method method;
        try {
            method = AccessUtil.setAccessible(object.getClass().getDeclaredMethod("getHandle", (Class<?>[])new Class[0]));
        }
        catch (final ReflectiveOperationException e) {
            method = AccessUtil.setAccessible(Minecraft.CraftEntity.getDeclaredMethod("getHandle", (Class<?>[])new Class[0]));
        }
        return method.invoke(object, new Object[0]);
    }
    
    public static Entity getBukkitEntity(final Object object) throws ReflectiveOperationException {
        Method method;
        try {
            method = AccessUtil.setAccessible(Minecraft.NmsEntity.getDeclaredMethod("getBukkitEntity", (Class<?>[])new Class[0]));
        }
        catch (final ReflectiveOperationException e) {
            method = AccessUtil.setAccessible(Minecraft.CraftEntity.getDeclaredMethod("getHandle", (Class<?>[])new Class[0]));
        }
        return (Entity)method.invoke(object, new Object[0]);
    }
    
    public static Object getHandleSilent(final Object object) {
        try {
            return getHandle(object);
        }
        catch (final Exception e) {
            return null;
        }
    }
    
    public static Object newEnumInstance(final Class clazz, final Class[] types, final Object[] values) throws ReflectiveOperationException {
        Constructor constructor = new ConstructorResolver(clazz).resolve((Class<?>[][])new Class[][] { types });
        constructor = AccessUtil.setAccessible(constructor);
        return constructor.newInstance(values);
    }
    
    static {
        NUMERIC_VERSION_PATTERN = Pattern.compile("v([0-9])_([0-9]*)_R([0-9])");
        Minecraft.nmsClassResolver = new NMSClassResolver();
        Minecraft.obcClassResolver = new OBCClassResolver();
        VERSION = Version.getVersion();
        System.out.println("[SkyBlock Reflection Injector] Version is " + (Object)Minecraft.VERSION);
        try {
            Minecraft.NmsEntity = Minecraft.nmsClassResolver.resolve("Entity");
            Minecraft.CraftEntity = Minecraft.obcClassResolver.resolve("entity.CraftEntity");
        }
        catch (final ReflectiveOperationException e) {
            throw new RuntimeException((Throwable)e);
        }
    }
    
    public enum Version
    {
        UNKNOWN(-1) {
            @Override
            public boolean matchesPackageName(final String packageName) {
                return false;
            }
        }, 
        v1_7_R1(10701), 
        v1_7_R2(10702), 
        v1_7_R3(10703), 
        v1_7_R4(10704), 
        v1_8_R1(10801), 
        v1_8_R2(10802), 
        v1_8_R3(10803), 
        v1_8_R4(10804), 
        v1_9_R1(10901), 
        v1_9_R2(10902), 
        v1_10_R1(11001), 
        v1_11_R1(11101), 
        v1_12_R1(11201);
        
        private final int version;
        
        private Version(final int version) {
            this.version = version;
        }
        
        public int version() {
            return this.version;
        }
        
        public boolean olderThan(final Version version) {
            return this.version() < version.version();
        }
        
        public boolean newerThan(final Version version) {
            return this.version() >= version.version();
        }
        
        public boolean inRange(final Version oldVersion, final Version newVersion) {
            return this.newerThan(oldVersion) && this.olderThan(newVersion);
        }
        
        public boolean matchesPackageName(final String packageName) {
            return packageName.toLowerCase().contains((CharSequence)this.name().toLowerCase());
        }
        
        public static Version getVersion() {
            final String name = Bukkit.getServer().getClass().getPackage().getName();
            final String versionPackage = name.substring(name.lastIndexOf(46) + 1) + ".";
            for (final Version version : values()) {
                if (version.matchesPackageName(versionPackage)) {
                    return version;
                }
            }
            System.err.println("[SkyBlock Reflection Injector] Failed to find version enum for '" + name + "'/'" + versionPackage + "'");
            System.out.println("[SkyBlock Reflection Injector] Generating dynamic constant...");
            final Matcher matcher = Minecraft.NUMERIC_VERSION_PATTERN.matcher((CharSequence)versionPackage);
            while (matcher.find()) {
                if (3 > matcher.groupCount()) {
                    continue;
                }
                final String majorString = matcher.group(1);
                String minorString = matcher.group(2);
                if (1 == minorString.length()) {
                    minorString = "0" + minorString;
                }
                String patchString = matcher.group(3);
                if (1 == patchString.length()) {
                    patchString = "0" + patchString;
                }
                final String numVersionString = majorString + minorString + patchString;
                final int numVersion = Integer.parseInt(numVersionString);
                final String packge = versionPackage.substring(0, versionPackage.length() - 1);
                try {
                    final Field valuesField = new FieldResolver(Version.class).resolve("$VALUES");
                    final Version[] oldValues = (Version[])valuesField.get((Object)null);
                    final Version[] newValues = new Version[oldValues.length + 1];
                    System.arraycopy((Object)oldValues, 0, (Object)newValues, 0, oldValues.length);
                    final Version dynamicVersion = (Version)Minecraft.newEnumInstance(Version.class, new Class[] { String.class, Integer.TYPE, Integer.TYPE }, new Object[] { packge, newValues.length - 1, numVersion });
                    newValues[newValues.length - 1] = dynamicVersion;
                    valuesField.set((Object)null, (Object)newValues);
                    System.out.println("[SkyBlock Reflection Injector] Injected dynamic version " + packge + " (#" + numVersion + ").");
                    System.out.println("[SkyBlock Reflection Injector] Please inform inventivetalent about the outdated version, as this is not guaranteed to work.");
                    return dynamicVersion;
                }
                catch (final ReflectiveOperationException e) {
                    e.printStackTrace();
                    continue;
                }
                break;
            }
            return Version.UNKNOWN;
        }
        
        public String toString() {
            return this.name() + " (" + this.version() + ")";
        }
    }
}
