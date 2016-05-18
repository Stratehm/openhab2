/**
 * Copyright (c) 2014 openHAB UG (haftungsbeschraenkt) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.domodule.internal;

import org.eclipse.smarthome.core.thing.Thing;
import org.eclipse.smarthome.core.thing.ThingTypeUID;
import org.eclipse.smarthome.core.thing.binding.BaseThingHandlerFactory;
import org.eclipse.smarthome.core.thing.binding.ThingHandler;
import org.openhab.binding.domodule.api.DomoduleManagerProvider;

import strat.domo.domodule.api.manager.DomoduleManager;

/**
 * The {@link DomoduleHandlerFactory} is responsible for creating things and thing
 * handlers.
 *
 * @author Antoine Besnard - Initial contribution
 */
public class DomoduleHandlerFactory extends BaseThingHandlerFactory {

    private DomoduleManager domoduleManager;

    @Override
    public boolean supportsThingType(ThingTypeUID thingTypeUID) {
        return false;
    }

    @Override
    protected ThingHandler createHandler(Thing thing) {
        return null;
    }

    public void setDomoduleManagerProvider(DomoduleManagerProvider provider) {
        this.domoduleManager = provider.get();
    }

}
