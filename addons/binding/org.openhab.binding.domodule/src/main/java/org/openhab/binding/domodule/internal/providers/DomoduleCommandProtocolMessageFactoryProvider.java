package org.openhab.binding.domodule.internal.providers;

import org.openhab.binding.domodule.api.DomoduleCommandSetManagerProvider;
import org.openhab.binding.domodule.api.DomoduleEventBusProvider;
import org.openhab.binding.domodule.api.DomoduleProtocolMessageFactoryProvider;

import net.engio.mbassy.bus.MBassador;
import strat.domo.domodule.api.impl.protocol.command.CommandProtocolMessageFactory;
import strat.domo.domodule.api.protocol.ProtocolMessageFactory;
import strat.domo.domodule.api.protocol.command.CommandSetDefinitionManager;

public class DomoduleCommandProtocolMessageFactoryProvider implements DomoduleProtocolMessageFactoryProvider {

    private MBassador<Object> eventBus;

    private CommandSetDefinitionManager commandSetDefinitionManager;

    private CommandProtocolMessageFactory instance;

    protected void activate() {
        instance = new CommandProtocolMessageFactory(eventBus, commandSetDefinitionManager);
    }

    @Override
    public ProtocolMessageFactory getProtocolMessageFactory() {
        return instance;
    }

    public void setDomoduleEventBusProvider(DomoduleEventBusProvider provider) {
        this.eventBus = provider.getEventBus();
    }

    public void setCommandSetManagerProvider(DomoduleCommandSetManagerProvider provider) {
        commandSetDefinitionManager = provider.getCommandSetDefinitionManager();
    }

}
