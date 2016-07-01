package org.openhab.ui.domodule.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.eclipse.smarthome.io.rest.RESTResource;
import org.openhab.binding.domodule.api.DomoduleCommandSetManagerProvider;

import strat.domo.domodule.api.protocol.command.CommandSetDefinitionManager;

@Path("commandSets")
@Produces("application/json")
@Consumes("application/json")
public class CommandSetResources implements RESTResource {

    private strat.domo.domodule.server.rest.CommandSetResources delegate;

    private CommandSetDefinitionManager commandSetDefinitionManager;

    protected void activate() {
        delegate = new strat.domo.domodule.server.rest.CommandSetResources(commandSetDefinitionManager);
    }

    @GET
    @Path("list")
    public Response getCommandSets() {
        return delegate.getCommandSets();
    }

    public void setCommandSetManagerProvider(DomoduleCommandSetManagerProvider provider) {
        commandSetDefinitionManager = provider.getCommandSetDefinitionManager();
    }

}
