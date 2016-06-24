package org.openhab.binding.domodulebootloader.internal.providers;

import org.openhab.binding.domodulebootloader.api.FirmwareRegistryProvider;
import org.openhab.binding.domodulebootloader.api.FirmwareResolverProvider;
import org.openhab.binding.domodulebootloader.api.FirmwareSelectorProvider;
import org.openhab.binding.domodulebootloader.api.RuledFirmwareSelectorProvider;

import strat.domo.domodule.bootloader.api.firmware.resolver.FirmwareResolver;
import strat.domo.domodule.bootloader.api.firmware.selector.FirmwareSelector;
import strat.domo.domodule.bootloader.api.firmware.selector.RuledFirmwareSelector;
import strat.domo.domodule.bootloader.api.firmware.selector.rule.RuledFirmwareSelectorAndResolver;

public class RuledFirmwareSelectorResolverProvider
        implements FirmwareSelectorProvider, RuledFirmwareSelectorProvider, FirmwareResolverProvider {

    private RuledFirmwareSelectorAndResolver selectorAndResolver;

    protected void activate() {
        selectorAndResolver = new RuledFirmwareSelectorAndResolver();
    }

    @Override
    public RuledFirmwareSelector getRuledFirmwareSelector() {
        return selectorAndResolver;
    }

    @Override
    public FirmwareSelector getFirmwareSelector() {
        return selectorAndResolver;
    }

    @Override
    public FirmwareResolver getFirmwareResolver() {
        return selectorAndResolver;
    }

    public void bindFirmwareRegistryProvider(FirmwareRegistryProvider provider) {
        selectorAndResolver.addFirmwareRegistry(provider.getFirmwareRegistry());
    }

    public void unbindFirmwareRegistryProvider(FirmwareRegistryProvider provider) {
        selectorAndResolver.removeFirmwareRegistry(provider.getFirmwareRegistry());
    }

}
