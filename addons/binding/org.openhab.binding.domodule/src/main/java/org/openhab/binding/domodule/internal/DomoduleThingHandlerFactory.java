/**
 * Copyright (c) 2014 openHAB UG (haftungsbeschraenkt) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.domodule.internal;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.smarthome.core.thing.Thing;
import org.eclipse.smarthome.core.thing.ThingTypeUID;
import org.eclipse.smarthome.core.thing.binding.BaseThingHandlerFactory;
import org.eclipse.smarthome.core.thing.binding.ThingHandler;
import org.openhab.binding.domodule.api.DomoduleHandlerFactory;
import org.openhab.binding.domodule.api.DomoduleManagerProvider;

import com.google.common.collect.Sets;

import strat.domo.domodule.api.manager.DomoduleManager;

/**
 * The {@link DomoduleThingHandlerFactory} is responsible for creating things and thing
 * handlers.
 *
 * @author Antoine Besnard - Initial contribution
 */
public class DomoduleThingHandlerFactory extends BaseThingHandlerFactory {

    private Set<DomoduleHandlerFactory> domoduleHandlerFactories;

    private DomoduleManager domoduleManager;

    public DomoduleThingHandlerFactory() {
        this.domoduleHandlerFactories = Sets.newSetFromMap(new ConcurrentHashMap<DomoduleHandlerFactory, Boolean>());
    }

    @Override
    public boolean supportsThingType(ThingTypeUID thingTypeUID) {
        for (DomoduleHandlerFactory domoduleHandlerFactory : domoduleHandlerFactories) {
            if (domoduleHandlerFactory.getSupportedThings().contains(thingTypeUID)) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected ThingHandler createHandler(Thing thing) {
        ThingHandler thingHandler = null;
        for (DomoduleHandlerFactory domoduleHandlerFactory : domoduleHandlerFactories) {
            if (domoduleHandlerFactory.getSupportedThings().contains(thing.getThingTypeUID())) {
                thingHandler = domoduleHandlerFactory.createHandler(thing, domoduleManager);
            }
        }
        return thingHandler;
    }

    public void bindDomoduleFactory(DomoduleHandlerFactory factory) {
        domoduleHandlerFactories.add(factory);
    }

    public void unbindDomoduleFactory(DomoduleHandlerFactory factory) {
        domoduleHandlerFactories.remove(factory);
    }

    public void setDomoduleManagerProvider(DomoduleManagerProvider provider) {
        this.domoduleManager = provider.get();
    }
}
