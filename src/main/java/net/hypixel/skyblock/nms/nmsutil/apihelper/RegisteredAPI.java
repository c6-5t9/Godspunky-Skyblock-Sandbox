package net.hypixel.skyblock.nms.nmsutil.apihelper;

import java.util.Iterator;
import net.hypixel.skyblock.nms.nmsutil.apihelper.exception.MissingHostException;
import net.hypixel.skyblock.nms.nmsutil.apihelper.exception.HostRegistrationException;
import java.util.HashSet;
import org.bukkit.plugin.Plugin;
import java.util.Set;

public class RegisteredAPI
{
    protected final API api;
    protected final Set<Plugin> hosts;
    protected boolean initialized;
    protected Plugin initializerHost;
    protected boolean eventsRegistered;
    
    public RegisteredAPI(final API api) {
        this.hosts = (Set<Plugin>)new HashSet();
        this.initialized = false;
        this.eventsRegistered = false;
        this.api = api;
    }
    
    public void registerHost(final Plugin host) throws HostRegistrationException {
        if (this.hosts.contains((Object)host)) {
            throw new HostRegistrationException("API host '" + host.getName() + "' for '" + this.api.getClass().getName() + "' is already registered");
        }
        this.hosts.add((Object)host);
    }
    
    public Plugin getNextHost() throws MissingHostException {
        if (this.api instanceof Plugin && ((Plugin)this.api).isEnabled()) {
            return (Plugin)this.api;
        }
        if (this.hosts.isEmpty()) {
            throw new MissingHostException("API '" + this.api.getClass().getName() + "' is disabled, but no other Hosts have been registered");
        }
        for (final Plugin host : this.hosts) {
            if (host.isEnabled()) {
                return host;
            }
        }
        throw new MissingHostException("API '" + this.api.getClass().getName() + "' is disabled and all registered Hosts are as well");
    }
    
    public void init() {
        if (this.initialized) {
            return;
        }
        this.api.init(this.initializerHost = this.getNextHost());
        this.initialized = true;
    }
    
    public void disable() {
        if (!this.initialized) {
            return;
        }
        this.api.disable(this.initializerHost);
        this.initialized = false;
    }
}
