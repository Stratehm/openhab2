package org.openhab.binding.domodulebootloader.api;

import strat.domo.domodule.bootloader.api.firmware.provider.FirmwareProvider;

/**
 * Provides a FirmwareProvider.
 *
 * @author Antoine Besnard
 *
 */
public interface FirmwareProviderProvider {

    FirmwareProvider getFirmwareProvider();

}
