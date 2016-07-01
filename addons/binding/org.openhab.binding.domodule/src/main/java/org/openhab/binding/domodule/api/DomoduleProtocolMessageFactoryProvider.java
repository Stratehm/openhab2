package org.openhab.binding.domodule.api;

import strat.domo.domodule.api.protocol.ProtocolMessageFactory;

/**
 * Provides a protocolMessageFactory.
 *
 * @author Antoine Besnard
 *
 */
public interface DomoduleProtocolMessageFactoryProvider {

    ProtocolMessageFactory getProtocolMessageFactory();

}
