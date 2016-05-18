package org.openhab.binding.domodule.api;

import strat.domo.domodule.api.message.DomoduleMessage;
import strat.domo.domodule.api.protocol.ProtocolManager;

/**
 * Provides a protocolManager.
 *
 * @author Antoine Besnard
 *
 */
public interface DomoduleProtocolManagerProvider {

    ProtocolManager<? extends DomoduleMessage> get();

}
