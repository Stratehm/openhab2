package org.openhab.binding.domoduleiboard.internal.providers;

import org.openhab.binding.domodule.api.DomoduleCommandSetProvider;

import strat.domo.domodule.api.protocol.command.definition.CommandDefinition;
import strat.domo.domodule.api.protocol.command.definition.CommandParameterDefinition;
import strat.domo.domodule.api.protocol.command.definition.CommandSetDefinition;
import strat.domo.domodule.driver.iboard.command.IBoardCommandSetDefinition;

public class DomoduleIBoardCommandSetProvider implements DomoduleCommandSetProvider {

    @Override
    public CommandSetDefinition<? extends CommandDefinition, ? extends CommandParameterDefinition> get() {
        return IBoardCommandSetDefinition.getInstance();
    }

}
