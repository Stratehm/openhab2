/**
 * Copyright (c) 2014-2015 openHAB UG (haftungsbeschraenkt) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.domodule.handler;

import org.eclipse.smarthome.core.thing.Thing;
import org.eclipse.smarthome.core.thing.ThingStatus;
import org.eclipse.smarthome.core.thing.ThingStatusDetail;
import org.eclipse.smarthome.core.thing.binding.BaseThingHandler;
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
public abstract class DomoduleThingHandler<T extends Domodule> extends BaseThingHandler {

    private Logger logger = LoggerFactory.getLogger(DomoduleThingHandler.class);

    private T domodule;

    public DomoduleThingHandler(Thing thing, T domodule) {
        super(thing);
        this.domodule = domodule;
    }

    /**
     * Return the domodule handled by this handler.
     *
     * @return
     */
    public T getDomodule() {
        return domodule;
    }

    @Override
    public void dispose() {
        super.dispose();
        domodule = null;
    }

    /**
     * Log the given Throwable and update the status of the domodule.
     *
     * @param e
     */
    protected void onError(Throwable e) {
        logger.error("Communication error with the domodule {}.", getDomodule().getId(), e);
        updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.COMMUNICATION_ERROR, e.getMessage());
    }

}
