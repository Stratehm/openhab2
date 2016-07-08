package org.openhab.binding.domodulebootloader.internal.providers;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.openhab.binding.domodulebootloader.api.FirmwareProviderProvider;
import org.openhab.binding.domodulebootloader.api.FirmwareRegistryProvider;

import com.google.common.collect.Sets;

import strat.domo.domodule.bootloader.api.firmware.provider.FirmwareProvider;
import strat.domo.domodule.bootloader.api.firmware.registry.FirmwareRegistry;
import strat.domo.domodule.bootloader.impl.firmware.registry.SimpleFirmwareRegistry;

/**
 * Provides an instance of a SimpleFirmwareRegistry.
 *
 * @author Antoine Besnard
 *
 */
public class FirmwareRegistryProviderImpl implements FirmwareRegistryProvider {

    private SimpleFirmwareRegistry instance;

    private Set<FirmwareProvider> firmwareProviders;

    public FirmwareRegistryProviderImpl() {
        firmwareProviders = Sets.newSetFromMap(new ConcurrentHashMap<FirmwareProvider, Boolean>());
    }

    protected void activate() {
        this.instance = new SimpleFirmwareRegistry(firmwareProviders);
    }

    @Override
    public FirmwareRegistry getFirmwareRegistry() {
        return instance;
    }

    public void bindFirmwareProviderProvider(FirmwareProviderProvider provider) {
        firmwareProviders.add(provider.getFirmwareProvider());
    }

    public void unbindFirmwareProviderProvider(FirmwareProviderProvider provider) {
        firmwareProviders.remove(provider.getFirmwareProvider());
    }

}
