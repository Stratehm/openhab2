package org.openhab.binding.domodulebootloader.internal.providers;

import org.openhab.binding.domodulebootloader.api.FirmwareRegistryProvider;
import org.openhab.binding.domodulebootloader.api.FirmwareResolverProvider;
import org.openhab.binding.domodulebootloader.api.FirmwareSelectorProvider;
import org.openhab.binding.domodulebootloader.api.RuledFirmwareSelectorProvider;

import strat.domo.domodule.bootloader.api.firmware.registry.FirmwareRegistry;
import strat.domo.domodule.bootloader.api.firmware.resolver.FirmwareResolver;
import strat.domo.domodule.bootloader.api.firmware.selector.FirmwareSelector;
import strat.domo.domodule.bootloader.api.firmware.selector.RuledFirmwareSelector;
import strat.domo.domodule.bootloader.impl.firmware.selector.RuledFirmwareSelectorAndResolver;

public class RuledFirmwareSelectorResolverProvider
        implements FirmwareSelectorProvider, RuledFirmwareSelectorProvider, FirmwareResolverProvider {

    private RuledFirmwareSelectorAndResolver instance;

    private FirmwareRegistry firmwareRegistry;

    protected void activate() {
        instance = new RuledFirmwareSelectorAndResolver(firmwareRegistry);
    }

    @Override
    public RuledFirmwareSelector getRuledFirmwareSelector() {
        return instance;
    }

    @Override
    public FirmwareSelector getFirmwareSelector() {
        return instance;
    }

    @Override
    public FirmwareResolver getFirmwareResolver() {
        return instance;
    }

    public void setFirmwareRegistryProvider(FirmwareRegistryProvider provider) {
        firmwareRegistry = provider.getFirmwareRegistry();
    }

}
