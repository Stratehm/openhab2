package org.openhab.binding.domoduleiboard.internal.providers;

import org.eclipse.smarthome.core.common.ThreadPoolManager;
import org.openhab.binding.domodule.api.DomoduleEventBusProvider;
import org.openhab.binding.domodule.api.DomoduleFactoryProvider;

import net.engio.mbassy.bus.MBassador;
import strat.domo.domodule.api.factory.DomoduleFactory;
import strat.domo.domodule.driver.iboard.DomoduleIBoardFactory;

public class DomoduleIBoardFactoryProvider implements DomoduleFactoryProvider {

    private MBassador<Object> eventBus;

    private DomoduleIBoardFactory factory;

    protected void activate() {
        this.factory = new DomoduleIBoardFactory(eventBus, ThreadPoolManager.getScheduledPool("DomoduleIBoardTimeout"));
    }

    @Override
    public DomoduleFactory get() {
        return factory;
    }

    public void setDomoduleEventBusProvider(DomoduleEventBusProvider provider) {
        this.eventBus = provider.get();
    }

}
