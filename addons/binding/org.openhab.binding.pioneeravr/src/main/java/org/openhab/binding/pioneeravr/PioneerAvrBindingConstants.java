/**
 * Copyright (c) 2014-2015 openHAB UG (haftungsbeschraenkt) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.pioneeravr;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.eclipse.smarthome.core.thing.ThingTypeUID;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

/**
 * The {@link PioneerAvrBinding} class defines common constants, which are used across the whole binding.
 * 
 * @author Antoine Besnard - Initial contribution
 */
public class PioneerAvrBindingConstants {

	public static final String BINDING_ID = "pioneeravr";

	public final static Set<String> SUPPORTED_DEVICE_MODELS = ImmutableSet.of("SC-57", "SC-LX85", "SC-55", "SC-1526", "SC-LX75", "VSX-53", "VSX-1326", "VSX-LX55", "VSX-2021", "VSA-LX55", "VSX-52", "VSX-1126", "VSX-1121", "VSX-51", "VSX-1021", "VSX-1026", "VSA-1021", "VSX-50", "VSX-926", "VSX-921", "VSA-921");
	
	// List of all Thing Type UIDs
	public final static ThingTypeUID IP_AVR_THING_TYPE = new ThingTypeUID(BINDING_ID, "ipAvr");
	public final static ThingTypeUID IP_AVR_UNSUPPORTED_THING_TYPE = new ThingTypeUID(BINDING_ID, "ipAvrUnsupported");
	public final static ThingTypeUID SERIAL_AVR_THING_TYPE = new ThingTypeUID(BINDING_ID, "serialAvr");
	
	// List of thing parameters names
	public final static String PROTOCOL_PARAMETER = "protocol";
	public final static String HOST_PARAMETER = "address";
	public final static String TCP_PORT_PARAMETER = "tcpPort";
	public final static String SERIAL_PORT_PARAMETER = "serialPort";
	
	public final static String IP_PROTOCOL_NAME = "IP";
	public final static String SERIAL_PROTOCOL_NAME = "serial";
	
	// List of all Channel ids
	public final static String POWER_CHANNEL = "power";
	public final static String VOLUME_DIMMER_CHANNEL = "volumeDimmer";
	public final static String VOLUME_DB_CHANNEL = "volumeDb";
	public final static String MUTE_CHANNEL = "mute";
	public final static String SET_INPUT_SOURCE_CHANNEL = "setInputSource";
	public final static String DISPLAY_INFORMATION_CHANNEL = "displayInformation";
	
	// Used for Discovery service
	public final static String MANUFACTURER = "PIONEER";
	public final static String UPNP_DEVICE_TYPE = "MediaRenderer";

	public final static Set<ThingTypeUID> SUPPORTED_THING_TYPES_UIDS = ImmutableSet.of(IP_AVR_THING_TYPE, IP_AVR_UNSUPPORTED_THING_TYPE);

	// Default input names and Ids
	public final static Map<String, String> DEFAULT_INPUT_NAMES = ImmutableMap.<String, String> builder().put("00","PHONO").put("01","CD").put("02","TUNER").put("03","CD-R/TAPE").put("04","DVD").put("05","TV").put("06","SAT/CBL").put("10","VIDEO 1 (VIDEO)").put("12","MULTI CH IN").put("13","USB-DAC").put("14","VIDEO 2").put("15","DVR/BDR").put("17","iPod/USB").put("19","HDMI 1").put("20","HDMI 2").put("21","HDMI 3").put("22","HDMI 4").put("23","HDMI 5").put("24","HDMI 6").put("25","BD").put("26","NETWORK").put("27","SIRIUS").put("31","HDMI (cyclic)").put("33","ADAPTER PORT").put("34","HDMI 7").put("35","HDMI 8").put("38","INTERNET RADIO").put("40","SiriusXM").put("41","PANDORA").put("44","MEDIA SERVER").put("45","FAVORITES").put("46","AirPlay").put("47","DMR").put("48","MHL").build();

}
