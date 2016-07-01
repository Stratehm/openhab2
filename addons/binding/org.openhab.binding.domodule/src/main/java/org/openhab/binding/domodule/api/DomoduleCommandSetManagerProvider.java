package org.openhab.binding.domodule.api;

import strat.domo.domodule.api.protocol.command.CommandSetDefinitionManager;

/**
 * Provides a DomoduleCommandSetManager
 *
 * @author Antoine Besnard
 *
 */
public interface DomoduleCommandSetManagerProvider {

    CommandSetDefinitionManager getCommandSetDefinitionManager();

}
