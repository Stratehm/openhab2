/**
 * Copyright (c) 2014 openHAB UG (haftungsbeschraenkt) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.domoduleiboard.internal;

import static org.openhab.binding.domoduleiboard.DomoduleIBoardBindingConstants.THING_TYPE_IBOARD_UDP;

import java.util.Collections;
import java.util.Set;

import org.eclipse.smarthome.core.thing.Thing;
import org.eclipse.smarthome.core.thing.ThingTypeUID;
import org.eclipse.smarthome.core.thing.binding.ThingHandler;
import org.openhab.binding.domodule.api.DomoduleHandlerFactory;
import org.openhab.binding.domoduleiboard.handler.DomoduleIBoardHandler;

import strat.domo.domodule.api.manager.DomoduleManager;

/**
 * The {@link DomoduleIBoardHandlerFactory} is responsible for creating things and thing
 * handlers.
 *
 * @author Antoine Besnard - Initial contribution
 */
public class DomoduleIBoardHandlerFactory implements DomoduleHandlerFactory {

    private final static Set<ThingTypeUID> SUPPORTED_THING_TYPES_UIDS = Collections.singleton(THING_TYPE_IBOARD_UDP);

    @Override
    public Set<ThingTypeUID> getSupportedThings() {
        return SUPPORTED_THING_TYPES_UIDS;
    }

    @Override
    public ThingHandler createHandler(Thing thing, DomoduleManager domoduleManager) {
        ThingTypeUID thingTypeUID = thing.getThingTypeUID();

        if (thingTypeUID.equals(THING_TYPE_IBOARD_UDP)) {
            return new DomoduleIBoardHandler(thing, domoduleManager);
        }

        return null;
    }
}
