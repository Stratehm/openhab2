package org.openhab.binding.domodulebootloader.internal.providers;

import org.openhab.binding.domodule.api.DomoduleEventBusProvider;
import org.openhab.binding.domodulebootloader.api.BootloaderManagerProvider;
import org.openhab.binding.domodulebootloader.api.FirmwareResolverProvider;
import org.openhab.binding.domodulebootloader.api.FirmwareSelectorProvider;

import net.engio.mbassy.bus.MBassador;
import strat.domo.domodule.bootloader.api.BootloaderManager;
import strat.domo.domodule.bootloader.api.firmware.resolver.FirmwareResolver;
import strat.domo.domodule.bootloader.api.firmware.selector.FirmwareSelector;
import strat.domo.domodule.bootloader.impl.BootloaderManagerImpl;

public class BootloaderManagerProviderImpl implements BootloaderManagerProvider {

    private MBassador<Object> eventBus;

    private FirmwareSelector firmwareSelector;

    private FirmwareResolver firmwareResolver;

    private BootloaderManagerImpl instance;

    protected void activate() {
        instance = new BootloaderManagerImpl(eventBus, firmwareSelector, firmwareResolver);
    }

    @Override
    public BootloaderManager getBootloaderManager() {
        return instance;
    }

    public void setEventBusProvider(DomoduleEventBusProvider provider) {
        this.eventBus = provider.getEventBus();
    }

    public void setFirmwareSelectorProvider(FirmwareSelectorProvider provider) {
        this.firmwareSelector = provider.getFirmwareSelector();
    }

    public void setFirmwareResolverProvider(FirmwareResolverProvider provider) {
        this.firmwareResolver = provider.getFirmwareResolver();
    }

}
