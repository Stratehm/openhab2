package org.openhab.binding.domodule.internal.providers;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.openhab.binding.domodule.api.DomoduleProtocolMessageFactoryManagerProvider;
import org.openhab.binding.domodule.api.DomoduleProtocolMessageFactoryProvider;

import strat.domo.domodule.api.impl.protocol.ProtocolMessageFactoryManagerImpl;
import strat.domo.domodule.api.protocol.ProtocolMessageFactory;
import strat.domo.domodule.api.protocol.ProtocolMessageFactoryManager;

public class DomoduleProtocolMessageFactoryManagerProviderImpl
        implements DomoduleProtocolMessageFactoryManagerProvider {

    private Set<ProtocolMessageFactory> protocolMessageFactories;

    private ProtocolMessageFactoryManager instance;

    public DomoduleProtocolMessageFactoryManagerProviderImpl() {
        protocolMessageFactories = Collections.newSetFromMap(new ConcurrentHashMap<ProtocolMessageFactory, Boolean>());
    }

    protected void activate() {
        instance = new ProtocolMessageFactoryManagerImpl(protocolMessageFactories);
    }

    @Override
    public ProtocolMessageFactoryManager getProtocolMessageFactoryManager() {
        return instance;
    }

    public void bindDomoduleProtocolMessageFactoryProvider(DomoduleProtocolMessageFactoryProvider provider) {
        protocolMessageFactories.add(provider.getProtocolMessageFactory());
    }

    public void unbindDomoduleProtocolMessageFactoryProvider(DomoduleProtocolMessageFactoryProvider provider) {
        protocolMessageFactories.remove(provider.getProtocolMessageFactory());
    }

}
