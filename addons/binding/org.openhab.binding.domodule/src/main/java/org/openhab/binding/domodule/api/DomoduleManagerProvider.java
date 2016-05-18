package org.openhab.binding.domodule.api;

import strat.domo.domodule.api.manager.DomoduleManager;

/**
 * Provides a DomoduleManager.
 *
 * @author Antoine Besnard
 *
 */
public interface DomoduleManagerProvider {

    DomoduleManager get();

}
