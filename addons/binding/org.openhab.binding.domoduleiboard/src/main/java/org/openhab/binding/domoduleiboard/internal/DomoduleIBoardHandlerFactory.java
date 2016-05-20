/**
 * Copyright (c) 2014 openHAB UG (haftungsbeschraenkt) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.domoduleiboard.internal;

import static org.openhab.binding.domoduleiboard.DomoduleIBoardBindingConstants.THING_TYPE_IBOARD_UDP;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Collections;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.eclipse.smarthome.core.common.ThreadPoolManager;
import org.eclipse.smarthome.core.thing.Thing;
import org.eclipse.smarthome.core.thing.ThingTypeUID;
import org.eclipse.smarthome.core.thing.binding.ThingHandler;
import org.openhab.binding.domodule.DomoduleBindingConstants;
import org.openhab.binding.domodule.api.DomoduleBearerManagerProvider;
import org.openhab.binding.domodule.api.DomoduleEventBusProvider;
import org.openhab.binding.domodule.api.DomoduleHandlerFactory;
import org.openhab.binding.domodule.api.DomoduleManagerProvider;
import org.openhab.binding.domoduleiboard.DomoduleIBoardBindingConstants;
import org.openhab.binding.domoduleiboard.handler.DomoduleIBoardHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.engio.mbassy.bus.MBassador;
import strat.domo.domodule.api.domodule.Domodule;
import strat.domo.domodule.api.domodule.DomoduleId;
import strat.domo.domodule.api.exception.DomoduleAlreadyRegisteredException;
import strat.domo.domodule.api.exception.MissingPropertyException;
import strat.domo.domodule.api.manager.DomoduleManager;
import strat.domo.domodule.bearer.datagram.AbstractDatagramBearerManager;
import strat.domo.domodule.driver.iboard.DomoduleIBoardUDP;

/**
 * The {@link DomoduleIBoardHandlerFactory} is responsible for creating things and thing
 * handlers.
 *
 * @author Antoine Besnard - Initial contribution
 */
public class DomoduleIBoardHandlerFactory implements DomoduleHandlerFactory {

    private final static Set<ThingTypeUID> SUPPORTED_THING_TYPES_UIDS = Collections.singleton(THING_TYPE_IBOARD_UDP);

    private final Logger logger = LoggerFactory.getLogger(DomoduleIBoardHandlerFactory.class);

    private DomoduleManager domoduleManager;

    private AbstractDatagramBearerManager datagramBearer;

    private MBassador<Object> eventBus;

    @Override
    public Set<ThingTypeUID> getSupportedThings() {
        return SUPPORTED_THING_TYPES_UIDS;
    }

    @Override
    public ThingHandler createHandler(Thing thing) {
        ThingTypeUID thingTypeUID = thing.getThingTypeUID();

        if (thingTypeUID.equals(THING_TYPE_IBOARD_UDP)) {
            DomoduleIBoardUDP domodule = getDomoduleFromThing(thing);
            return new DomoduleIBoardHandler(thing, domodule);
        }

        return null;
    }

    protected DomoduleIBoardUDP getDomoduleFromThing(Thing thing) {
        String domoduleId = (String) thing.getConfiguration().get(DomoduleBindingConstants.PROPERTY_DOMODULE_ID);
        Domodule domodule = domoduleManager.getDomoduleById(domoduleId);
        DomoduleIBoardUDP result = null;

        if (domodule != null) {
            // No ClassCastException should be thrown.
            // If so, it is a bug since the DomoduleDiscoveryService has not built the right ThingType for the
            // domoduleModel.
            result = (DomoduleIBoardUDP) domodule;
        } else {
            // If the domodule is not registered in the domoduleManager, then create a new one.
            String domoduleAddress = (String) thing.getConfiguration()
                    .get(AbstractDatagramBearerManager.REMOTE_INET_ADDRESS_METADATA);
            String domodulePort = (String) thing.getConfiguration()
                    .get(AbstractDatagramBearerManager.REMOTE_PORT_METADATA);

            if (StringUtils.isNotBlank(domoduleAddress) && StringUtils.isNotBlank(domodulePort)) {
                try {
                    // Create the new domodule
                    result = new DomoduleIBoardUDP(InetAddress.getByName(domoduleAddress),
                            Integer.valueOf(domodulePort), new DomoduleId(domoduleId), datagramBearer, eventBus,
                            ThreadPoolManager.getScheduledPool(
                                    DomoduleIBoardBindingConstants.DOMODULE_IBOARD_SCHEDULED_EXECUTOR_SERVICE_NAME));

                    // Then register it.
                    domoduleManager.register(result);
                } catch (NumberFormatException | IOException | MissingPropertyException
                        | DomoduleAlreadyRegisteredException e) {
                    result = null;
                    logger.error("Failed to create a new domodule.", e);
                }
            }
        }
        return result;
    }

    public void setDomoduleEventBusProvider(DomoduleEventBusProvider provider) {
        this.eventBus = provider.get();
    }

    public void setDomoduleManagerProvider(DomoduleManagerProvider provider) {
        this.domoduleManager = provider.get();
    }

    public void setBearerManagerProvider(DomoduleBearerManagerProvider provider) {
        if (provider.get() instanceof AbstractDatagramBearerManager) {
            this.datagramBearer = (AbstractDatagramBearerManager) provider.get();
        }
    }
}
