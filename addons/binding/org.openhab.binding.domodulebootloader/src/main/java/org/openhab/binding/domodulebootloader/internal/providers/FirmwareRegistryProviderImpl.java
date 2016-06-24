package org.openhab.binding.domodulebootloader.internal.providers;

import org.openhab.binding.domodulebootloader.api.FirmwareProviderProvider;
import org.openhab.binding.domodulebootloader.api.FirmwareRegistryProvider;

import strat.domo.domodule.bootloader.api.firmware.registry.FirmwareRegistry;
import strat.domo.domodule.bootloader.api.firmware.registry.SimpleFirmwareRegistry;

/**
 * Provides an instance of a SimpleFirmwareRegistry.
 *
 * @author Antoine Besnard
 *
 */
public class FirmwareRegistryProviderImpl implements FirmwareRegistryProvider {

    private SimpleFirmwareRegistry registry;

    protected void activate() {
        this.registry = new SimpleFirmwareRegistry();
    }

    @Override
    public FirmwareRegistry getFirmwareRegistry() {
        return registry;
    }

    public void bindFirmwareProviderProvider(FirmwareProviderProvider provider) {
        registry.addFirmwareProvider(provider.getFirmwareProvider());
    }

    public void unbindFirmwareProviderProvider(FirmwareProviderProvider provider) {
        registry.removeFirmwareProvider(provider.getFirmwareProvider());
    }

}
