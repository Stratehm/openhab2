package org.openhab.binding.domodule.internal.providers;

import org.openhab.binding.domodule.api.DomoduleEventBusProvider;

import net.engio.mbassy.bus.MBassador;

public class DomoduleEventBusProviderImpl implements DomoduleEventBusProvider {

    private MBassador<Object> instance;

    protected void activate() {
        instance = new MBassador<>();
    }

    @Override
    public MBassador<Object> getEventBus() {
        return instance;
    }

}
