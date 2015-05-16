/**
 * Copyright (c) 2014-2015 openHAB UG (haftungsbeschraenkt) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.pioneeravr.protocol;

import org.eclipse.smarthome.core.types.Command;
import org.openhab.binding.pioneeravr.protocol.event.AvrDisconnectionListener;
import org.openhab.binding.pioneeravr.protocol.event.AvrUpdateListener;

/**
 * Represent a connection to a remote Pioneer AVR.
 * 
 * @author Antoine Besnard
 *
 */
public interface AvrConnection {

	/**
	 * Add an update listener. It is notified when an update is received from the AVR.
	 * 
	 * @param listener
	 */
	public void addUpdateListener(AvrUpdateListener listener);

	/**
	 * Add a disconnection listener. It is notified when the AVR is disconnected.
	 * 
	 * @param listener
	 */
	public void addDisconnectionListener(AvrDisconnectionListener listener);

	/**
	 * Connect to the receiver. Return true if the connection has succeeded or if already connected.
	 * 
	 **/
	public boolean connect();

	/**
	 * Return true if this manager is connected to the AVR.
	 * 
	 * @return
	 */
	public boolean isConnected();

	/**
	 * Closes the connection.
	 **/
	public void close();

	/**
	 * Send a power state query to the AVR
	 * 
	 * @return
	 */
	public boolean sendPowerQuery();

	/**
	 * Send a volume level query to the AVR
	 * 
	 * @return
	 */
	public boolean sendVolumeQuery();

	/**
	 * Send a mute state query to the AVR
	 * 
	 * @return
	 */
	public boolean sendMuteQuery();

	/**
	 * Send a source input state query to the AVR
	 * 
	 * @return
	 */
	public boolean sendSourceInputQuery();

	/**
	 * Send a power command ot the AVR based on the OpenHAB command
	 * 
	 * @param command
	 * @return
	 * @throws CommandTypeNotSupportedException
	 */
	public boolean sendPowerCommand(Command command) throws CommandTypeNotSupportedException;

	/**
	 * Send a volume command to the AVR based on the OpenHAB command
	 * 
	 * @param command
	 * @return
	 * @throws CommandTypeNotSupportedException
	 */
	public boolean sendVolumeCommand(Command command) throws CommandTypeNotSupportedException;

	/**
	 * Send a source input selection command to the AVR based on the OpenHAB command
	 * 
	 * @param command
	 * @return
	 * @throws CommandTypeNotSupportedException
	 */
	public boolean sendInputSourceCommand(Command command) throws CommandTypeNotSupportedException;

	/**
	 * Send a mute command to the AVR based on the OpenHAB command
	 * 
	 * @param command
	 * @return
	 * @throws CommandTypeNotSupportedException
	 */
	public boolean sendMuteCommand(Command command) throws CommandTypeNotSupportedException;

	/**
	 * Send a command to the AVR to request the name of the input with the given number.
	 * 
	 * @param command
	 * @return
	 * @throws CommandTypeNotSupportedException thrown if the command is not of type DecimalType
	 */
	boolean sendInputNameCommand(Command command) throws CommandTypeNotSupportedException;
	
	/**
	 * Return the connection name
	 * 
	 * @return
	 */
	public String getConnectionName();



}