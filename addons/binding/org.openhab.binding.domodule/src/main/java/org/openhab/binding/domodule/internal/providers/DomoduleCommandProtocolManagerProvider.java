package org.openhab.binding.domodule.internal.providers;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.openhab.binding.domodule.api.DomoduleCommandSetProvider;
import org.openhab.binding.domodule.api.DomoduleEventBusProvider;
import org.openhab.binding.domodule.api.DomoduleProtocolManagerProvider;

import com.google.common.collect.Sets;

import net.engio.mbassy.bus.MBassador;
import strat.domo.domodule.api.impl.protocol.command.CommandDomoduleMessageFactoryImpl;
import strat.domo.domodule.api.impl.protocol.command.CommandProtocolManager;
import strat.domo.domodule.api.message.DomoduleMessage;
import strat.domo.domodule.api.protocol.ProtocolManager;
import strat.domo.domodule.api.protocol.command.definition.CommandSetDefinition;

public class DomoduleCommandProtocolManagerProvider implements DomoduleProtocolManagerProvider {

    private MBassador<Object> eventBus;

    private CommandProtocolManager protocolManager;

    private Set<CommandSetDefinition<?, ?>> commandSets;

    public DomoduleCommandProtocolManagerProvider() {
        commandSets = Sets.newSetFromMap(new ConcurrentHashMap<CommandSetDefinition<?, ?>, Boolean>());
    }

    protected void activate() {
        protocolManager = new CommandProtocolManager(eventBus, new CommandDomoduleMessageFactoryImpl(commandSets));
    }

    @Override
    public ProtocolManager<? extends DomoduleMessage> get() {
        return protocolManager;
    }

    public void setDomoduleEventBusProvider(DomoduleEventBusProvider provider) {
        this.eventBus = provider.get();
    }

    public void bindCommandSetDefinition(DomoduleCommandSetProvider provider) {
        commandSets.add(provider.get());
    }

    public void unbindCommandSetDefinition(DomoduleCommandSetProvider provider) {
        commandSets.remove(provider.get());
    }

}
