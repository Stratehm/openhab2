package org.openhab.binding.domodule.internal.providers;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.openhab.binding.domodule.api.DomoduleCommandSetManagerProvider;
import org.openhab.binding.domodule.api.DomoduleCommandSetProvider;

import strat.domo.domodule.api.impl.protocol.command.CommandSetDefinitionManagerImpl;
import strat.domo.domodule.api.protocol.command.CommandSetDefinitionManager;
import strat.domo.domodule.api.protocol.command.definition.CommandDefinition;
import strat.domo.domodule.api.protocol.command.definition.CommandParameterDefinition;
import strat.domo.domodule.api.protocol.command.definition.CommandSetDefinition;

public class DomoduleCommandSetManagerProviderImpl implements DomoduleCommandSetManagerProvider {

    private CommandSetDefinitionManager instance;

    private Set<CommandSetDefinition<? extends CommandDefinition, ? extends CommandParameterDefinition>> commandSets;

    public DomoduleCommandSetManagerProviderImpl() {
        commandSets = Collections.newSetFromMap(
                new ConcurrentHashMap<CommandSetDefinition<? extends CommandDefinition, ? extends CommandParameterDefinition>, Boolean>());
    }

    protected void activate() {
        instance = new CommandSetDefinitionManagerImpl(commandSets);
    }

    @Override
    public CommandSetDefinitionManager getCommandSetDefinitionManager() {
        return instance;
    }

    public void bindDomoduleCommandSetProvider(DomoduleCommandSetProvider provider) {
        commandSets.add(provider.getCommandSetDefinition());
    }

    public void unbindDomoduleCommandSetProvider(DomoduleCommandSetProvider provider) {
        commandSets.remove(provider.getCommandSetDefinition());
    }

}
