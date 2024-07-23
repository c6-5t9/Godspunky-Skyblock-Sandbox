package net.hypixel.skyblock.nms.nmsutil.apihelper;

import java.util.HashMap;
import net.hypixel.skyblock.nms.nmsutil.apihelper.exception.MissingHostException;
import java.util.HashSet;
import java.util.Iterator;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import net.hypixel.skyblock.nms.nmsutil.apihelper.exception.APIRegistrationException;
import java.util.logging.Logger;
import org.bukkit.plugin.Plugin;
import java.util.Set;
import java.util.Map;

public class APIManager
{
    private static final Map<API, RegisteredAPI> HOST_MAP;
    private static final Map<Class<? extends API>, Set<Plugin>> PENDING_API_CLASSES;
    private static final Logger LOGGER;
    
    public static RegisteredAPI registerAPI(final API api) throws APIRegistrationException {
        if (APIManager.HOST_MAP.containsKey((Object)api)) {
            throw new APIRegistrationException("API for '" + api.getClass().getName() + "' is already registered");
        }
        final RegisteredAPI registeredAPI = new RegisteredAPI(api);
        APIManager.HOST_MAP.put((Object)api, (Object)registeredAPI);
        api.load();
        APIManager.LOGGER.fine("'" + api.getClass().getName() + "' registered as new API");
        return registeredAPI;
    }
    
    public static RegisteredAPI registerAPI(final API api, final Plugin host) throws IllegalArgumentException, APIRegistrationException {
        validatePlugin(host);
        registerAPI(api);
        return registerAPIHost(api, host);
    }
    
    public static API registerEvents(final API api, final Listener listener) throws APIRegistrationException {
        if (!APIManager.HOST_MAP.containsKey((Object)api)) {
            throw new APIRegistrationException("API for '" + api.getClass().getName() + "' is not registered");
        }
        final RegisteredAPI registeredAPI = (RegisteredAPI)APIManager.HOST_MAP.get((Object)api);
        if (registeredAPI.eventsRegistered) {
            return api;
        }
        Bukkit.getPluginManager().registerEvents(listener, registeredAPI.getNextHost());
        registeredAPI.eventsRegistered = true;
        return api;
    }
    
    private static void initAPI(final API api) throws APIRegistrationException {
        if (!APIManager.HOST_MAP.containsKey((Object)api)) {
            throw new APIRegistrationException("API for '" + api.getClass().getName() + "' is not registered");
        }
        final RegisteredAPI registeredAPI = (RegisteredAPI)APIManager.HOST_MAP.get((Object)api);
        registeredAPI.init();
    }
    
    public static void initAPI(final Class<? extends API> clazz) throws APIRegistrationException {
        API clazzAPI = null;
        for (final API api : APIManager.HOST_MAP.keySet()) {
            if (api.getClass().equals(clazz)) {
                clazzAPI = api;
                break;
            }
        }
        if (clazzAPI == null) {
            if (!APIManager.PENDING_API_CLASSES.containsKey((Object)clazz)) {
                throw new APIRegistrationException("API for class '" + clazz.getName() + "' is not registered");
            }
            APIManager.LOGGER.info("API class '" + clazz.getName() + "' is not yet initialized. Creating new instance.");
            try {
                clazzAPI = (API)clazz.newInstance();
                registerAPI(clazzAPI);
                for (final Plugin plugin : (Set)APIManager.PENDING_API_CLASSES.get((Object)clazz)) {
                    if (plugin != null) {
                        registerAPIHost(clazzAPI, plugin);
                    }
                }
            }
            catch (final ReflectiveOperationException e) {
                APIManager.LOGGER.warning("API class '" + clazz.getName() + "' is missing valid constructor");
            }
            APIManager.PENDING_API_CLASSES.remove((Object)clazz);
        }
        initAPI(clazzAPI);
    }
    
    private static void disableAPI(final API api) {
        if (!APIManager.HOST_MAP.containsKey((Object)api)) {
            return;
        }
        final RegisteredAPI registeredAPI = (RegisteredAPI)APIManager.HOST_MAP.get((Object)api);
        registeredAPI.disable();
        APIManager.HOST_MAP.remove((Object)api);
    }
    
    public static void disableAPI(final Class<? extends API> clazz) {
        API clazzAPI = null;
        for (final API api : APIManager.HOST_MAP.keySet()) {
            if (api.getClass().equals(clazz)) {
                clazzAPI = api;
                break;
            }
        }
        disableAPI(clazzAPI);
    }
    
    public static void require(final Class<? extends API> clazz, final Plugin host) {
        try {
            if (host == null) {
                throw new APIRegistrationException();
            }
            registerAPIHost(clazz, host);
        }
        catch (final APIRegistrationException e) {
            if (APIManager.PENDING_API_CLASSES.containsKey((Object)clazz)) {
                ((Set)APIManager.PENDING_API_CLASSES.get((Object)clazz)).add((Object)host);
            }
            else {
                final Set<Plugin> hosts = (Set<Plugin>)new HashSet();
                hosts.add((Object)host);
                APIManager.PENDING_API_CLASSES.put((Object)clazz, (Object)hosts);
            }
        }
    }
    
    private static RegisteredAPI registerAPIHost(final API api, final Plugin host) throws APIRegistrationException {
        validatePlugin(host);
        if (!APIManager.HOST_MAP.containsKey((Object)api)) {
            throw new APIRegistrationException("API for '" + api.getClass().getName() + "' is not registered");
        }
        final RegisteredAPI registeredAPI = (RegisteredAPI)APIManager.HOST_MAP.get((Object)api);
        registeredAPI.registerHost(host);
        APIManager.LOGGER.fine("'" + host.getName() + "' registered as Host for '" + (Object)api + "'");
        return registeredAPI;
    }
    
    public static RegisteredAPI registerAPIHost(final Class<? extends API> clazz, final Plugin host) throws APIRegistrationException {
        validatePlugin(host);
        API clazzAPI = null;
        for (final API api : APIManager.HOST_MAP.keySet()) {
            if (api.getClass().equals(clazz)) {
                clazzAPI = api;
                break;
            }
        }
        if (clazzAPI == null) {
            throw new APIRegistrationException("API for class '" + clazz.getName() + "' is not registered");
        }
        return registerAPIHost(clazzAPI, host);
    }
    
    public static Plugin getAPIHost(final API api) throws APIRegistrationException, MissingHostException {
        if (!APIManager.HOST_MAP.containsKey((Object)api)) {
            throw new APIRegistrationException("API for '" + api.getClass().getName() + "' is not registered");
        }
        return ((RegisteredAPI)APIManager.HOST_MAP.get((Object)api)).getNextHost();
    }
    
    private static void validatePlugin(final Plugin plugin) {
        if (plugin instanceof API) {
            throw new IllegalArgumentException("Plugin must not implement API");
        }
    }
    
    static {
        HOST_MAP = (Map)new HashMap();
        PENDING_API_CLASSES = (Map)new HashMap();
        LOGGER = Logger.getLogger("APIManager");
    }
}
