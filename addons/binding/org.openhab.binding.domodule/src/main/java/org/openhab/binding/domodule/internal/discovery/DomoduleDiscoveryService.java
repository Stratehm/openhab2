package org.openhab.binding.domodule.internal.discovery;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.smarthome.config.discovery.AbstractDiscoveryService;
import org.eclipse.smarthome.config.discovery.DiscoveryResult;
import org.eclipse.smarthome.config.discovery.DiscoveryResultBuilder;
import org.eclipse.smarthome.core.thing.Thing;
import org.eclipse.smarthome.core.thing.ThingTypeUID;
import org.eclipse.smarthome.core.thing.ThingUID;
import org.openhab.binding.domodule.DomoduleBindingConstants;
import org.openhab.binding.domodule.api.DomoduleDiscoveryManagerProvider;
import org.openhab.binding.domodule.api.DomoduleEventBusProvider;
import org.openhab.binding.domodule.api.DomoduleHandlerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Sets;

import net.engio.mbassy.bus.MBassador;
import net.engio.mbassy.listener.Handler;
import strat.domo.domodule.api.impl.event.DomoduleDiscoveredEvent;
import strat.domo.domodule.api.manager.DiscoveryManager;

public class DomoduleDiscoveryService extends AbstractDiscoveryService {

    private Logger logger = LoggerFactory.getLogger(DomoduleDiscoveryService.class);

    private DiscoveryManager discoveryManager;

    private MBassador<Object> eventBus;

    private Set<DomoduleHandlerFactory> domoduleHandlerFactories;

    public DomoduleDiscoveryService() throws IllegalArgumentException {
        super(5);
        this.domoduleHandlerFactories = Sets.newSetFromMap(new ConcurrentHashMap<DomoduleHandlerFactory, Boolean>());
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
        startScan();
    }

    @Handler
    protected void onDomoduleDiscovered(DomoduleDiscoveredEvent event) {
        ThingTypeUID thingType = new ThingTypeUID(DomoduleBindingConstants.BINDING_ID, event.getDomodule().getModel());
        ThingUID thingUID = new ThingUID(thingType, event.getDomodule().getId().toString());
        Map<String, Object> properties = new HashMap<>();
        properties.put(DomoduleBindingConstants.PROPERTY_DOMODULE_ID, event.getDomodule().getId().toString());

        String firmware = event.getDomodule().getFirmwareId() + "-" + event.getDomodule().getFirmwareVersion();
        properties.put(Thing.PROPERTY_FIRMWARE_VERSION, firmware);
        properties.put(Thing.PROPERTY_MODEL_ID, event.getDomodule().getModel());
        properties.putAll(event.getDomodule().getMetaData());

        DiscoveryResult result = DiscoveryResultBuilder.create(thingUID).withProperties(properties)
                .withLabel(event.getDomodule().getName()).build();

        thingDiscovered(result);
    }

    @Override
    public Set<ThingTypeUID> getSupportedThingTypes() {
        Set<ThingTypeUID> supportedThingTypes = new HashSet<>();
        for (DomoduleHandlerFactory domoduleHandlerFactory : domoduleHandlerFactories) {
            supportedThingTypes.addAll(domoduleHandlerFactory.getSupportedThings());
        }
        return supportedThingTypes;
    }

    public void setDmomoduleDiscoveryManagerProvider(DomoduleDiscoveryManagerProvider provider) {
        this.discoveryManager = provider.get();
    }

    public void setDomoduleEventBusProvider(DomoduleEventBusProvider provider) {
        this.eventBus = provider.get();
        this.eventBus.subscribe(this);
    }

    public void bindDomoduleFactory(DomoduleHandlerFactory factory) {
        domoduleHandlerFactories.add(factory);
    }

    public void unbindDomoduleFactory(DomoduleHandlerFactory factory) {
        domoduleHandlerFactories.remove(factory);
    }

}
