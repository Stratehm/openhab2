package org.openhab.ui.domodule.bootloader.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.eclipse.smarthome.io.rest.RESTResource;
import org.glassfish.jersey.media.sse.EventOutput;
import org.glassfish.jersey.media.sse.SseFeature;
import org.openhab.binding.domodulebootloader.api.RuledFirmwareSelectorProvider;
import org.openhab.ui.domodule.bootloader.rest.config.RulesConfigurationManagerJsonFile;

import strat.domo.domodule.api.exception.ConfigurationException;
import strat.domo.domodule.bootloader.api.firmware.selector.RuledFirmwareSelector;
import strat.domo.domodule.bootloader.server.rest.dto.rule.RuleDto;
import strat.domo.domodule.bootloader.server.rest.dto.rule.UpdateRuleDto;
import strat.domo.domodule.bootloader.server.rest.factory.RuleDtoFactory;

@Path("rules")
@Produces("application/json")
@Consumes("application/json")
public class RulesResources implements RESTResource {

    private strat.domo.domodule.bootloader.server.rest.RulesResources delegate;

    private RuledFirmwareSelector ruledFirmwareSelector;

    protected void activate() throws ConfigurationException {
        RuleDtoFactory ruleDtoFactory = new RuleDtoFactory();
        this.delegate = new strat.domo.domodule.bootloader.server.rest.RulesResources(ruledFirmwareSelector,
                new RulesConfigurationManagerJsonFile(ruledFirmwareSelector, ruleDtoFactory), ruleDtoFactory);
    }

    @GET
    @Path("list")
    public Response listRules() {
        return delegate.listRules();
    }

    @POST
    @Path("create")
    public Response createRule(RuleDto ruleDto) {
        return delegate.createRule(ruleDto);
    }

    @POST
    @Path("update")
    public Response updateRule(UpdateRuleDto updateRuleDto) {
        return delegate.updateRule(updateRuleDto);
    }

    @DELETE
    @Path("{ruleId}")
    public Response deleteRule(@PathParam("ruleId") Integer ruleId) {
        return delegate.deleteRule(ruleId);
    }

    @GET
    @Path("events")
    @Produces(SseFeature.SERVER_SENT_EVENTS)
    public EventOutput subscribeEvents() {
        return delegate.subscribeEvents();
    }

    public void setRuledFirmwareSelectorProvider(RuledFirmwareSelectorProvider provider) {
        this.ruledFirmwareSelector = provider.getRuledFirmwareSelector();
    }

}
