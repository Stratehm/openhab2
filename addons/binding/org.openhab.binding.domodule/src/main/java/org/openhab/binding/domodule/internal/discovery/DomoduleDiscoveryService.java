package org.openhab.binding.domodule.internal.discovery;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.smarthome.config.discovery.AbstractDiscoveryService;
import org.eclipse.smarthome.config.discovery.DiscoveryResult;
import org.eclipse.smarthome.config.discovery.DiscoveryResultBuilder;
import org.eclipse.smarthome.core.thing.Thing;
import org.eclipse.smarthome.core.thing.ThingUID;
import org.openhab.binding.domodule.DomoduleBindingConstants;
import org.openhab.binding.domodule.api.DomoduleDiscoveryManagerProvider;
import org.openhab.binding.domodule.api.DomoduleEventBusProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.engio.mbassy.bus.MBassador;
import net.engio.mbassy.listener.Handler;
import strat.domo.domodule.api.impl.event.DomoduleDiscoveredEvent;
import strat.domo.domodule.api.manager.DiscoveryManager;

public class DomoduleDiscoveryService extends AbstractDiscoveryService {

    private Logger logger = LoggerFactory.getLogger(DomoduleDiscoveryService.class);

    private DiscoveryManager discoveryManager;

    private MBassador<Object> eventBus;

    private boolean isBackgroundScanStartedOnce;

    public DomoduleDiscoveryService() throws IllegalArgumentException {
        super(5);
        this.isBackgroundScanStartedOnce = false;
    }

    @Override
    protected void startScan() {
        try {
            discoveryManager.discoverDomodules();
        } catch (IOException e) {
            logger.error("Failed to start discovery process.", e);
        }
    }

    @Override
    protected synchronized void startBackgroundDiscovery() {
        // Start a scan only once at startup. Other call are not are not necessary since
        // the domodules advertise themselves when they are started.
        if (!isBackgroundScanStartedOnce) {
            startScan();
            isBackgroundScanStartedOnce = true;
        }
    }

    @Handler
    protected void onDomoduleDiscovered(DomoduleDiscoveredEvent event) {
        ThingUID thingUID = new ThingUID(DomoduleBindingConstants.BINDING_ID, event.getDomodule().getId().toString());
        Map<String, Object> properties = new HashMap<>();
        properties.put(DomoduleBindingConstants.PROPERTY_DOMODULE, event.getDomodule());

        String firmware = event.getDomodule().getFirmwareId() + "-" + event.getDomodule().getFirmwareVersion();
        properties.put(Thing.PROPERTY_FIRMWARE_VERSION, firmware);
        properties.put(Thing.PROPERTY_MODEL_ID, event.getDomodule().getModel());

        DiscoveryResult result = DiscoveryResultBuilder.create(thingUID).withProperties(properties)
                .withLabel(event.getDomodule().getName()).build();

        thingDiscovered(result);
    }

    public void setDmomoduleDiscoveryManagerProvider(DomoduleDiscoveryManagerProvider provider) {
        this.discoveryManager = provider.get();
    }

    public void setDomoduleEventBusProvider(DomoduleEventBusProvider provider) {
        this.eventBus = provider.get();
        this.eventBus.subscribe(this);
    }

}
