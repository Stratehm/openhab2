package org.openhab.binding.domodulebootloader.api;

import strat.domo.domodule.bootloader.api.firmware.registry.FirmwareRegistry;

/**
 * Provides a FirmwareRegistry.
 *
 * @author Antoine Besnard
 *
 */
public interface FirmwareRegistryProvider {

    FirmwareRegistry getFirmwareRegistry();

}
