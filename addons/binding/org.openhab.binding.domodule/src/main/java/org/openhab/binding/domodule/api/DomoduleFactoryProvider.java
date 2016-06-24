package org.openhab.binding.domodule.api;

import strat.domo.domodule.api.factory.DomoduleFactory;

/**
 * Provides a DomoduleFactory.
 *
 * @author Antoine Besnard
 *
 */
public interface DomoduleFactoryProvider {

    DomoduleFactory getDomoduleFactory();

}
