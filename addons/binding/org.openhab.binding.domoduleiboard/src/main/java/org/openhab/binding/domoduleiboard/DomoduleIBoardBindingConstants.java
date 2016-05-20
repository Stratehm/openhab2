/**
 * Copyright (c) 2014-2015 openHAB UG (haftungsbeschraenkt) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.domoduleiboard;

import org.eclipse.smarthome.core.thing.ThingTypeUID;
import org.openhab.binding.domodule.DomoduleBindingConstants;

/**
 * The {@link DomoduleIBoardBinding} class defines common constants, which are
 * used across the whole binding.
 *
 * @author Antoine Besnard - Initial contribution
 */
public class DomoduleIBoardBindingConstants {

    public final static ThingTypeUID THING_TYPE_IBOARD_UDP = new ThingTypeUID(DomoduleBindingConstants.BINDING_ID,
            "IBoardUDP");

    public final static String CHANNEL_ROLLERSHUTTER = "rollershutter";
    public final static String CHANNEL_TEMPERATURE = "temperature";
    public final static String CHANNEL_HUMIDITY = "humidity";

    public final static String DOMODULE_IBOARD_SCHEDULED_EXECUTOR_SERVICE_NAME = "DomoduleIBoardTimeout";

    public final static int SENSOR_POLLING_DELAY_IN_SECONDS = 10;

}
