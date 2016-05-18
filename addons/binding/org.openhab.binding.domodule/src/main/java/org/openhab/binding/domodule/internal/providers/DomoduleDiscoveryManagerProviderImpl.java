package org.openhab.binding.domodule.internal.providers;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.openhab.binding.domodule.api.DomoduleDiscoveryManagerProvider;
import org.openhab.binding.domodule.api.DomoduleEventBusProvider;
import org.openhab.binding.domodule.api.DomoduleFactoryProvider;

import com.google.common.collect.Sets;

import net.engio.mbassy.bus.MBassador;
import strat.domo.domodule.api.factory.DomoduleFactory;
import strat.domo.domodule.api.impl.manager.DiscoveryManagerImpl;
import strat.domo.domodule.api.manager.DiscoveryManager;

public class DomoduleDiscoveryManagerProviderImpl implements DomoduleDiscoveryManagerProvider {

    private DiscoveryManager discoveryManager;

    private MBassador<Object> eventBus;

    private Set<DomoduleFactory> domodulesFactories;

    public DomoduleDiscoveryManagerProviderImpl() {
        domodulesFactories = Sets.newSetFromMap(new ConcurrentHashMap<DomoduleFactory, Boolean>());
    }

    @Override
    public DiscoveryManager get() {
        return discoveryManager;
    }

    protected void activate() {
        discoveryManager = new DiscoveryManagerImpl(eventBus, domodulesFactories);
    }

    public void setDomoduleEventBusProvider(DomoduleEventBusProvider provider) {
        this.eventBus = provider.get();
    }

    public void bindDomoduleFactoryProvider(DomoduleFactoryProvider provider) {
        domodulesFactories.add(provider.get());
    }

    public void unbindDomoduleFactoryProvider(DomoduleFactoryProvider provider) {
        domodulesFactories.remove(provider.get());
    }

}
