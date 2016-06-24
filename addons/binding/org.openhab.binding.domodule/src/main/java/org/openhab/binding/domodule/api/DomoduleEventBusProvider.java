package org.openhab.binding.domodule.api;

import net.engio.mbassy.bus.MBassador;

/**
 * Provides an EventBus
 *
 * @author Antoine Besnard
 *
 */
public interface DomoduleEventBusProvider {

    MBassador<Object> getEventBus();

}
