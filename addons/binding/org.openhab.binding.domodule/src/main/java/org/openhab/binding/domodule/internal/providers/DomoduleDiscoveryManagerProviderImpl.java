package org.openhab.binding.domodule.internal.providers;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.openhab.binding.domodule.api.DomoduleDiscoveryManagerProvider;
import org.openhab.binding.domodule.api.DomoduleEventBusProvider;
import org.openhab.binding.domodule.api.DomoduleFactoryProvider;
import org.openhab.binding.domodule.api.DomoduleManagerProvider;

import com.google.common.collect.Sets;

import net.engio.mbassy.bus.MBassador;
import strat.domo.domodule.api.factory.DomoduleFactory;
import strat.domo.domodule.api.impl.manager.DiscoveryManagerImpl;
import strat.domo.domodule.api.manager.DiscoveryManager;
import strat.domo.domodule.api.manager.DomoduleManager;

public class DomoduleDiscoveryManagerProviderImpl implements DomoduleDiscoveryManagerProvider {

    private DiscoveryManager instance;

    private MBassador<Object> eventBus;

    private DomoduleManager domoduleManager;

    private Set<DomoduleFactory> domodulesFactories;

    public DomoduleDiscoveryManagerProviderImpl() {
        domodulesFactories = Sets.newSetFromMap(new ConcurrentHashMap<DomoduleFactory, Boolean>());
    }

    @Override
    public DiscoveryManager getDiscoveryManager() {
        return instance;
    }

    protected void activate() {
        instance = new DiscoveryManagerImpl(eventBus, domoduleManager, domodulesFactories);
    }

    public void setDomoduleEventBusProvider(DomoduleEventBusProvider provider) {
        this.eventBus = provider.getEventBus();
    }

    public void setDomoduleManagerProvider(DomoduleManagerProvider provider) {
        this.domoduleManager = provider.getManager();
    }

    public void bindDomoduleFactoryProvider(DomoduleFactoryProvider provider) {
        domodulesFactories.add(provider.getDomoduleFactory());
    }

    public void unbindDomoduleFactoryProvider(DomoduleFactoryProvider provider) {
        domodulesFactories.remove(provider.getDomoduleFactory());
    }

}
