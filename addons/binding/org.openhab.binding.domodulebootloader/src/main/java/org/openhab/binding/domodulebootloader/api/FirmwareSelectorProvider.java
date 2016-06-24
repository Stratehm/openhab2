package org.openhab.binding.domodulebootloader.api;

import strat.domo.domodule.bootloader.api.firmware.selector.FirmwareSelector;

/**
 * Provides a FirmwareSelector.
 *
 * @author Antoine Besnard
 *
 */
public interface FirmwareSelectorProvider {

    FirmwareSelector getFirmwareSelector();

}
