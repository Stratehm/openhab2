package org.openhab.binding.domodule.api;

import strat.domo.domodule.api.manager.DiscoveryManager;

/**
 * Provides a DiscoveryManager.
 *
 * @author Antoine Besnard
 *
 */
public interface DomoduleDiscoveryManagerProvider {

    DiscoveryManager get();
}
