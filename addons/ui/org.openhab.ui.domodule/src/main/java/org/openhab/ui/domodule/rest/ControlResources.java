package org.openhab.ui.domodule.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.eclipse.smarthome.io.rest.RESTResource;
import org.glassfish.jersey.media.sse.EventOutput;
import org.glassfish.jersey.media.sse.SseFeature;
import org.openhab.binding.domodule.api.DomoduleCommandSetManagerProvider;
import org.openhab.binding.domodule.api.DomoduleEventBusProvider;
import org.openhab.binding.domodule.api.DomoduleManagerProvider;
import org.openhab.binding.domodule.api.DomoduleProtocolMessageFactoryManagerProvider;

import net.engio.mbassy.bus.MBassador;
import strat.domo.domodule.api.manager.DomoduleManager;
import strat.domo.domodule.api.protocol.ProtocolMessageFactoryManager;
import strat.domo.domodule.api.protocol.command.CommandSetDefinitionManager;
import strat.domo.domodule.server.rest.dto.control.CommandToSendDto;

@Path("control")
@Produces("application/json")
@Consumes("application/json")
public class ControlResources implements RESTResource {

    private strat.domo.domodule.server.rest.ControlResources delegate;

    private MBassador<Object> eventBus;

    private DomoduleManager domoduleManager;

    private CommandSetDefinitionManager commandSetManager;

    private ProtocolMessageFactoryManager commandMessageFactoryManager;

    protected void activate() {
        delegate = new strat.domo.domodule.server.rest.ControlResources(eventBus, domoduleManager, commandSetManager,
                commandMessageFactoryManager);
    }

    @POST
    @Path("command/send")
    public Response sendCommand(CommandToSendDto commandToSend) {
        return delegate.sendCommand(commandToSend);
    }

    @GET
    @Path("events")
    @Produces(SseFeature.SERVER_SENT_EVENTS)
    public EventOutput subscribeEvents() {
        return delegate.subscribeEvents();
    }

    public void setDomoduleEventBusProvider(DomoduleEventBusProvider provider) {
        this.eventBus = provider.getEventBus();
    }

    public void setDomoduleManagerProvider(DomoduleManagerProvider provider) {
        this.domoduleManager = provider.getManager();
    }

    public void setDomoduleCommandSetManagerProvider(DomoduleCommandSetManagerProvider provider) {
        this.commandSetManager = provider.getCommandSetDefinitionManager();
    }

    public void setDomoduleProtocolMessageFactoryManagerProvider(
            DomoduleProtocolMessageFactoryManagerProvider provider) {
        commandMessageFactoryManager = provider.getProtocolMessageFactoryManager();
    }
}
