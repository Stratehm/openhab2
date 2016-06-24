package org.openhab.binding.domodule.api;

import strat.domo.domodule.api.bearer.BearerManager;

/**
 * Provides a BearerManager.
 *
 * @author Antoine Besnard
 *
 */
public interface DomoduleBearerManagerProvider {

    BearerManager getBearerManager();

}
