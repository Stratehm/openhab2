package org.openhab.binding.domodule.api;

import strat.domo.domodule.api.bearer.BearerManager;

/**
 * Provides a BererManager.
 *
 * @author Antoine Besnard
 *
 */
public interface DomoduleBearerManagerProvider {

    BearerManager get();

}
