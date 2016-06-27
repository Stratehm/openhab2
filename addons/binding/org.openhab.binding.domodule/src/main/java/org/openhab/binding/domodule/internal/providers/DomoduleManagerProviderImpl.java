package org.openhab.binding.domodule.internal.providers;

import org.openhab.binding.domodule.api.DomoduleEventBusProvider;
import org.openhab.binding.domodule.api.DomoduleManagerProvider;

import net.engio.mbassy.bus.MBassador;
import strat.domo.domodule.api.impl.manager.DomoduleManagerImpl;
import strat.domo.domodule.api.manager.DomoduleManager;

public class DomoduleManagerProviderImpl implements DomoduleManagerProvider {

    private MBassador<Object> eventBus;

    private DomoduleManager instance;

    protected void activate() {
        instance = new DomoduleManagerImpl(eventBus);
    }

    @Override
    public DomoduleManager getManager() {
        return instance;
    }

    public void setDomoduleEventBusProvider(DomoduleEventBusProvider provider) {
        this.eventBus = provider.getEventBus();
    }

}
