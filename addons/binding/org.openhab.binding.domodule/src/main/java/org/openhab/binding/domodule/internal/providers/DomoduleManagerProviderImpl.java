package org.openhab.binding.domodule.internal.providers;

import org.openhab.binding.domodule.api.DomoduleEventBusProvider;
import org.openhab.binding.domodule.api.DomoduleManagerProvider;

import net.engio.mbassy.bus.MBassador;
import strat.domo.domodule.api.impl.manager.DomoduleManagerImpl;
import strat.domo.domodule.api.manager.DomoduleManager;

public class DomoduleManagerProviderImpl implements DomoduleManagerProvider {

    private MBassador<Object> eventBus;

    private DomoduleManager domoduleManager;

    @Override
    public DomoduleManager get() {
        return domoduleManager;
    }

    protected void activate() {
        domoduleManager = new DomoduleManagerImpl(eventBus);
    }

    public void setDomoduleEventBusProvider(DomoduleEventBusProvider provider) {
        this.eventBus = provider.get();
    }

}
