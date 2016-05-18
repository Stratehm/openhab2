/**
 * Copyright (c) 2014-2015 openHAB UG (haftungsbeschraenkt) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.domodule.handler;

import java.util.Collection;

import org.eclipse.smarthome.config.discovery.DiscoveryListener;
import org.eclipse.smarthome.config.discovery.DiscoveryResult;
import org.eclipse.smarthome.config.discovery.DiscoveryService;
import org.eclipse.smarthome.core.thing.Thing;
import org.eclipse.smarthome.core.thing.ThingStatus;
import org.eclipse.smarthome.core.thing.ThingStatusDetail;
import org.eclipse.smarthome.core.thing.ThingTypeUID;
import org.eclipse.smarthome.core.thing.ThingUID;
import org.eclipse.smarthome.core.thing.binding.BaseThingHandler;
import org.openhab.binding.domodule.DomoduleBindingConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import strat.domo.domodule.api.domodule.Domodule;

/**
 * The {@link DomoduleThingHandler} is responsible for handling commands, which are
 * sent to one of the channels.
 *
 * @author Antoine Besnard - Initial contribution
 *
 * @param <T>
 */
public abstract class DomoduleThingHandler<T extends Domodule> extends BaseThingHandler implements DiscoveryListener {

    private Logger logger = LoggerFactory.getLogger(DomoduleThingHandler.class);

    private T domodule;

    @SuppressWarnings("unchecked")
    public DomoduleThingHandler(Thing thing) {
        super(thing);
        Object domodule = thing.getConfiguration().get(DomoduleBindingConstants.PROPERTY_DOMODULE);

        if (domodule != null) {
            // No ClassCastException should be thrown.
            // If so, it is a bug since the *HandlerFactory has not built the right ThingHandler for the domodule.
            this.domodule = (T) thing.getConfiguration().get(DomoduleBindingConstants.PROPERTY_DOMODULE);
        }
    }

    @Override
    public void initialize() {
        if (domodule == null) {
            updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.HANDLER_CONFIGURATION_PENDING,
                    "Waiting to discover the domodule...");
        } else {
            updateStatus(ThingStatus.ONLINE);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void thingDiscovered(DiscoveryService source, DiscoveryResult result) {
        if (result.getThingUID().equals(this.getThing().getUID())) {
            // No ClassCastException should be thrown.
            // If so, it is a bug since the *HandlerFactory has not built the right ThingHandler for the domodule.
            this.domodule = (T) result.getProperties().get(DomoduleBindingConstants.PROPERTY_DOMODULE);
            updateStatus(ThingStatus.ONLINE);
        }
    }

    @Override
    public void thingRemoved(DiscoveryService source, ThingUID thingUID) {
        if (thingUID.equals(this.getThing().getUID())) {
            this.domodule = null;
            updateStatus(ThingStatus.OFFLINE);
        }

    }

    @Override
    public Collection<ThingUID> removeOlderResults(DiscoveryService source, long timestamp,
            Collection<ThingTypeUID> thingTypeUIDs) {
        // Do nothing.
        return null;
    }

    /**
     * Return the domodule handled by this handler.
     *
     * @return
     */
    public T getDomodule() {
        return domodule;
    }

    /**
     * Log the given Throwable and update the status of the domodule.
     *
     * @param e
     */
    protected void onError(Throwable e) {
        logger.error("Error on domodule with ID {}.", getDomodule().getId(), e);
        updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.COMMUNICATION_ERROR, e.getMessage());
    }

}
