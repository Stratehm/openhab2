package org.openhab.binding.domodulebootloader.api;

import strat.domo.domodule.bootloader.api.firmware.resolver.FirmwareResolver;

/**
 * Provides a FirmwareRegistry.
 *
 * @author Antoine Besnard
 *
 */
public interface FirmwareResolverProvider {

    FirmwareResolver getFirmwareResolver();

}
