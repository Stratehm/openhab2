package org.openhab.ui.domodule.bootloader.rest;

import java.io.InputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.smarthome.io.rest.RESTResource;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.glassfish.jersey.media.sse.EventOutput;
import org.glassfish.jersey.media.sse.SseFeature;
import org.openhab.binding.domodulebootloader.api.FirmwareRegistryProvider;

import strat.domo.domodule.bootloader.api.firmware.registry.FirmwareRegistry;
import strat.domo.domodule.bootloader.server.rest.factory.FirmwareDtoFactory;

@Path("firmwares")
@Produces("application/json")
@Consumes("application/json")
public class FirmwaresResources implements RESTResource {

    private strat.domo.domodule.bootloader.server.rest.FirmwaresResources delegate;

    private FirmwareRegistry firmwareRegistry;

    protected void activate() {
        this.delegate = new strat.domo.domodule.bootloader.server.rest.FirmwaresResources(firmwareRegistry,
                new FirmwareDtoFactory());
    }

    @GET
    @Path("list")
    public Response listFirmwares() {
        return delegate.listFirmwares();
    }

    @POST
    @Path("create")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response createFirmware(@FormDataParam("firmwareDto") String firmwareDtoJson,
            @FormDataParam("file") InputStream uploadFileInputStream,
            @FormDataParam("file") FormDataContentDisposition contentDisposition) {
        return delegate.createFirmware(firmwareDtoJson, uploadFileInputStream, contentDisposition);
    }

    @POST
    @Path("update")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response updateFirmware(@FormDataParam("firmwareDto") String firmwareDtoJson,
            @FormDataParam("file") InputStream uploadFileInputStream,
            @FormDataParam("file") FormDataContentDisposition contentDisposition) {
        return delegate.updateFirmware(firmwareDtoJson, uploadFileInputStream, contentDisposition);
    }

    @DELETE
    @Path("{firmwareId}")
    public Response deleteFirmware(@PathParam("firmwareId") String firmwareId, @QueryParam("version") Integer version) {
        return delegate.deleteFirmware(firmwareId, version);
    }

    @GET
    @Path("events")
    @Produces(SseFeature.SERVER_SENT_EVENTS)
    public EventOutput subscribeEvents() {
        return delegate.subscribeEvents();
    }

    public void setFirmwareRegistryProvider(FirmwareRegistryProvider provder) {
        this.firmwareRegistry = provder.getFirmwareRegistry();
    }

}
