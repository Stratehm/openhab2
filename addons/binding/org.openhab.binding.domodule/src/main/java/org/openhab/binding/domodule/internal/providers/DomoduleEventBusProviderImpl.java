package org.openhab.binding.domodule.internal.providers;

import org.openhab.binding.domodule.api.DomoduleEventBusProvider;

import net.engio.mbassy.bus.MBassador;

public class DomoduleEventBusProviderImpl implements DomoduleEventBusProvider {

    private MBassador<Object> mBassador;

    @Override
    public MBassador<Object> getEventBus() {
        return mBassador;
    }

    protected void activate() {
        mBassador = new MBassador<>();
    }

}
