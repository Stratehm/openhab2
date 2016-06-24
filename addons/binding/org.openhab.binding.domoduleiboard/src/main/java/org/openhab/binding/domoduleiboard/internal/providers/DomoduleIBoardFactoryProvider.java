package org.openhab.binding.domoduleiboard.internal.providers;

import org.eclipse.smarthome.core.common.ThreadPoolManager;
import org.openhab.binding.domodule.api.DomoduleEventBusProvider;
import org.openhab.binding.domodule.api.DomoduleFactoryProvider;
import org.openhab.binding.domoduleiboard.DomoduleIBoardBindingConstants;

import net.engio.mbassy.bus.MBassador;
import strat.domo.domodule.api.factory.DomoduleFactory;
import strat.domo.domodule.driver.iboard.DomoduleIBoardFactory;

public class DomoduleIBoardFactoryProvider implements DomoduleFactoryProvider {

    private MBassador<Object> eventBus;

    private DomoduleIBoardFactory factory;

    protected void activate() {
        this.factory = new DomoduleIBoardFactory(eventBus, ThreadPoolManager
                .getScheduledPool(DomoduleIBoardBindingConstants.DOMODULE_IBOARD_SCHEDULED_EXECUTOR_SERVICE_NAME));
    }

    @Override
    public DomoduleFactory getDomoduleFactory() {
        return factory;
    }

    public void setDomoduleEventBusProvider(DomoduleEventBusProvider provider) {
        this.eventBus = provider.getEventBus();
    }

}
