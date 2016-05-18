package org.openhab.binding.domodule.api;

import strat.domo.domodule.api.protocol.command.definition.CommandDefinition;
import strat.domo.domodule.api.protocol.command.definition.CommandParameterDefinition;
import strat.domo.domodule.api.protocol.command.definition.CommandSetDefinition;

public interface DomoduleCommandSetProvider {

    public CommandSetDefinition<? extends CommandDefinition, ? extends CommandParameterDefinition> get();

}
