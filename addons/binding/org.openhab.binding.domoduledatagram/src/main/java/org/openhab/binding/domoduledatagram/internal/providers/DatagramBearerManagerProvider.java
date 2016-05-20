package org.openhab.binding.domoduledatagram.internal.providers;

import java.io.IOException;

import org.eclipse.smarthome.core.common.ThreadPoolManager;
import org.openhab.binding.domodule.api.DomoduleBearerManagerProvider;
import org.openhab.binding.domodule.api.DomoduleEventBusProvider;

import net.engio.mbassy.bus.MBassador;
import strat.domo.domodule.bearer.datagram.DatagramBearerManagerImpl;

public class DatagramBearerManagerProvider implements DomoduleBearerManagerProvider {

    private MBassador<Object> eventBus;

    private DatagramBearerManagerImpl bearerManager;

    protected void activate() throws IOException {
        bearerManager = new DatagramBearerManagerImpl(eventBus,
                ThreadPoolManager.getPool("DomoduleDatagramBrearerManager"));
        bearerManager.start();
    }

    @Override
    public DatagramBearerManagerImpl get() {
        return bearerManager;
    }

    public void setDomoduleEventBusProvider(DomoduleEventBusProvider provider) {
        this.eventBus = provider.get();
    }

}
