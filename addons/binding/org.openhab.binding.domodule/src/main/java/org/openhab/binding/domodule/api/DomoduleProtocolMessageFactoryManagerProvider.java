package org.openhab.binding.domodule.api;

import strat.domo.domodule.api.protocol.ProtocolMessageFactoryManager;

/**
 * Provides a ProtocolMessageFactoryManager.
 *
 * @author Antoine Besnard
 *
 */
public interface DomoduleProtocolMessageFactoryManagerProvider {

    ProtocolMessageFactoryManager getProtocolMessageFactoryManager();

}
