/**
 * Copyright (c) 2014-2015 openHAB UG (haftungsbeschraenkt) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.domoduleiboard.handler;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.smarthome.core.library.types.DecimalType;
import org.eclipse.smarthome.core.library.types.StopMoveType;
import org.eclipse.smarthome.core.library.types.UpDownType;
import org.eclipse.smarthome.core.thing.ChannelUID;
import org.eclipse.smarthome.core.thing.Thing;
import org.eclipse.smarthome.core.types.Command;
import org.eclipse.smarthome.core.types.RefreshType;
import org.openhab.binding.domodule.handler.DomoduleThingHandler;
import org.openhab.binding.domoduleiboard.DomoduleIBoardBindingConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import strat.domo.domodule.api.protocol.command.CommandDomoduleMessage;
import strat.domo.domodule.api.protocol.command.CommandParameter;
import strat.domo.domodule.api.protocol.command.CommandResponseCallback;
import strat.domo.domodule.driver.iboard.DomoduleIBoardUDP;
import strat.domo.domodule.driver.iboard.command.IBoardCommandDefinition;
import strat.domo.domodule.driver.iboard.command.IBoardCommandParameterDefinition;

/**
 * The {@link DomoduleIBoardHandler} is responsible for handling commands, which are
 * sent to one of the channels.
 *
 * @author Antoine Besnard - Initial contribution
 */
public class DomoduleIBoardHandler extends DomoduleThingHandler<DomoduleIBoardUDP> {

    private Logger logger = LoggerFactory.getLogger(DomoduleIBoardHandler.class);

    public DomoduleIBoardHandler(Thing thing) {
        super(thing);
    }

    @Override
    public void handleCommand(ChannelUID channelUID, Command command) {
        try {
            if (channelUID.getId().equals(DomoduleIBoardBindingConstants.CHANNEL_ROLLERSHUTTER)) {
                processRollershutterCommand(channelUID, command);
            } else if (channelUID.getId().equals(DomoduleIBoardBindingConstants.CHANNEL_TEMPERATURE)
                    || channelUID.getId().equals(DomoduleIBoardBindingConstants.CHANNEL_HUMIDITY)) {
                processSensorCommand(channelUID, command);
            }
        } catch (IOException e) {
            onError(e);
        }
    }

    protected void processRollershutterCommand(final ChannelUID channelUID, Command command) throws IOException {
        if (command == UpDownType.UP) {
            getDomodule().sendCommand(IBoardCommandDefinition.UP);
        } else if (command == UpDownType.DOWN) {
            getDomodule().sendCommand(IBoardCommandDefinition.DOWN);
        } else if (command == StopMoveType.STOP) {
            getDomodule().sendCommand(IBoardCommandDefinition.STOP);
        }
    }

    protected void processSensorCommand(final ChannelUID channelUID, Command command) throws IOException {
        if (command instanceof DecimalType || command == RefreshType.REFRESH) {
            List<CommandParameter> parameters = new ArrayList<>();
            // The IBoard domodule has only one temp sensor and one humidity sensor => the sensor index is 0 in each
            // case.
            parameters.add(new CommandParameter(IBoardCommandParameterDefinition.SENSOR_INDEX, (byte) 0));

            IBoardCommandDefinition domoduleCommand = channelUID.getId()
                    .equals(DomoduleIBoardBindingConstants.CHANNEL_TEMPERATURE)
                            ? IBoardCommandDefinition.GET_TEMPERATURE : IBoardCommandDefinition.GET_HUMIDITY;

            getDomodule().sendCommand(domoduleCommand, parameters, new CommandResponseCallback() {
                @Override
                public void onFailure(CommandDomoduleMessage response, Throwable t) {
                    onError(t);
                }

                @Override
                public void onResponse(CommandDomoduleMessage request, CommandDomoduleMessage response) {
                    CommandParameter sensorValue = response.getParameter(IBoardCommandParameterDefinition.SENSOR_VALUE);
                    if (sensorValue != null) {
                        updateState(channelUID, new DecimalType(new BigDecimal(sensorValue.getValueAsFloat())));
                    } else {
                        logger.info("No sensor value in the response of the domodule {}.", getDomodule().getId());
                    }
                }
            });
        }
    }

}
