package org.openhab.ui.domodule.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.eclipse.smarthome.io.rest.RESTResource;
import org.glassfish.jersey.media.sse.EventOutput;
import org.glassfish.jersey.media.sse.SseFeature;
import org.openhab.binding.domodule.api.DomoduleDiscoveryManagerProvider;
import org.openhab.binding.domodule.api.DomoduleEventBusProvider;
import org.openhab.binding.domodule.api.DomoduleManagerProvider;

import net.engio.mbassy.bus.MBassador;
import strat.domo.domodule.api.manager.DiscoveryManager;
import strat.domo.domodule.api.manager.DomoduleManager;

@Path("domodules")
@Produces("application/json")
@Consumes("application/json")
public class DomoduleResources implements RESTResource {

    private strat.domo.domodule.server.rest.DomoduleResources delegate;

    private MBassador<Object> eventBus;

    private DomoduleManager domoduleManager;

    private DiscoveryManager discoveryManager;

    protected void activate() {
        delegate = new strat.domo.domodule.server.rest.DomoduleResources(eventBus, domoduleManager, discoveryManager);
    }

    @GET
    @Path("discover")
    public Response startDiscovery() {
        return delegate.startDiscovery();
    }

    @GET
    @Path("list")
    public Response getDomodules() {
        return delegate.getDomodules();
    }

    @GET
    @Path("events")
    @Produces(SseFeature.SERVER_SENT_EVENTS)
    public EventOutput subscribeEvents() {
        return delegate.subscribeEvents();
    }

    public void setEventBusProvider(DomoduleEventBusProvider provider) {
        eventBus = provider.getEventBus();
    }

    public void setDomoduleManagerProvider(DomoduleManagerProvider provider) {
        domoduleManager = provider.getManager();
    }

    public void setDomoduleDiscoveryManagerProvider(DomoduleDiscoveryManagerProvider provider) {
        discoveryManager = provider.getDiscoveryManager();
    }

}
