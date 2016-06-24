package org.openhab.binding.domodulebootloader.api;

import strat.domo.domodule.bootloader.api.firmware.selector.RuledFirmwareSelector;

/**
 * Provides a RuledFirmwareSelector.
 *
 * @author Antoine Besnard
 *
 */
public interface RuledFirmwareSelectorProvider {

    RuledFirmwareSelector getRuledFirmwareSelector();

}
