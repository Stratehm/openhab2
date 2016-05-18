/**
 * Copyright (c) 2014-2015 openHAB UG (haftungsbeschraenkt) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.domoduleiboard;

import org.eclipse.smarthome.core.thing.ThingTypeUID;

/**
 * The {@link DomoduleIBoardBinding} class defines common constants, which are
 * used across the whole binding.
 *
 * @author Antoine Besnard - Initial contribution
 */
public class DomoduleIBoardBindingConstants {

    public static final String BINDING_ID = "domoduleiboard";

    public final static ThingTypeUID THING_TYPE_IBOARD_UDP = new ThingTypeUID(BINDING_ID, "IBoardUDP");

    public final static String CHANNEL_ROLLERSHUTTER = "rollershutter";
    public final static String CHANNEL_TEMPERATURE = "temperature";
    public final static String CHANNEL_HUMIDITY = "humidity";

}
