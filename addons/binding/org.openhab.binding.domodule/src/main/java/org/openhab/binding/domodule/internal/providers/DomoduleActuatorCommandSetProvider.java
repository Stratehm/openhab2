package org.openhab.binding.domodule.internal.providers;

import org.openhab.binding.domodule.api.DomoduleCommandSetProvider;

import strat.domo.domodule.api.impl.protocol.command.definition.actuator.ActuatorCommandSetDefinition;
import strat.domo.domodule.api.protocol.command.definition.CommandDefinition;
import strat.domo.domodule.api.protocol.command.definition.CommandParameterDefinition;
import strat.domo.domodule.api.protocol.command.definition.CommandSetDefinition;

public class DomoduleActuatorCommandSetProvider implements DomoduleCommandSetProvider {

    private ActuatorCommandSetDefinition instance;

    protected void activate() {
        instance = new ActuatorCommandSetDefinition();
    }

    @Override
    public CommandSetDefinition<? extends CommandDefinition, ? extends CommandParameterDefinition> getCommandSetDefinition() {
        return instance;
    }

}
