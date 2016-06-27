package org.openhab.binding.domodule.internal.providers;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.openhab.binding.domodule.api.DomoduleCommandSetProvider;
import org.openhab.binding.domodule.api.DomoduleEventBusProvider;
import org.openhab.binding.domodule.api.DomoduleProtocolMessageFactoryProvider;

import com.google.common.collect.Sets;

import net.engio.mbassy.bus.MBassador;
import strat.domo.domodule.api.impl.protocol.command.CommandProtocolMessageFactory;
import strat.domo.domodule.api.message.DomoduleMessage;
import strat.domo.domodule.api.protocol.ProtocolMessageFactory;
import strat.domo.domodule.api.protocol.command.definition.CommandSetDefinition;

public class DomoduleCommandProtocolMessageFactoryProvider implements DomoduleProtocolMessageFactoryProvider {

    private MBassador<Object> eventBus;

    private CommandProtocolMessageFactory instance;

    private Set<CommandSetDefinition<?, ?>> commandSets;

    public DomoduleCommandProtocolMessageFactoryProvider() {
        commandSets = Sets.newSetFromMap(new ConcurrentHashMap<CommandSetDefinition<?, ?>, Boolean>());
    }

    protected void activate() {
        instance = new CommandProtocolMessageFactory(eventBus, commandSets);
    }

    @Override
    public ProtocolMessageFactory<? extends DomoduleMessage> getProtocolMessageFactory() {
        return instance;
    }

    public void setDomoduleEventBusProvider(DomoduleEventBusProvider provider) {
        this.eventBus = provider.getEventBus();
    }

    public void bindCommandSetDefinition(DomoduleCommandSetProvider provider) {
        commandSets.add(provider.getCommandSetDefinition());
    }

    public void unbindCommandSetDefinition(DomoduleCommandSetProvider provider) {
        commandSets.remove(provider.getCommandSetDefinition());
    }

}
