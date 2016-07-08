package org.openhab.ui.domodule.bootloader.rest;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.collections4.CollectionUtils;
import org.eclipse.smarthome.io.rest.RESTResource;
import org.glassfish.jersey.media.sse.EventOutput;
import org.glassfish.jersey.media.sse.OutboundEvent;
import org.glassfish.jersey.media.sse.SseBroadcaster;
import org.glassfish.jersey.media.sse.SseFeature;
import org.openhab.binding.domodulebootloader.api.RuledFirmwareSelectorProvider;
import org.openhab.ui.domodule.bootloader.rest.config.RulesConfigurationManagerJsonFile;
import org.openhab.ui.domodule.bootloader.rest.dto.ListRulesResultWrapperDto;
import org.openhab.ui.domodule.bootloader.rest.dto.ModelIdSubRuleWrapperDto;
import org.openhab.ui.domodule.bootloader.rest.dto.RuleAddedEventWrapperDto;
import org.openhab.ui.domodule.bootloader.rest.dto.RuleRemovedEventWrapperDto;
import org.openhab.ui.domodule.bootloader.rest.dto.RuleUpdatedEventWrapperDto;
import org.openhab.ui.domodule.bootloader.rest.dto.RuleWrapperDto;
import org.openhab.ui.domodule.bootloader.rest.dto.UpdateRuleWrapperDto;

import strat.domo.domodule.api.exception.ConfigurationException;
import strat.domo.domodule.bootloader.api.firmware.selector.RuledFirmwareSelector;
import strat.domo.domodule.bootloader.server.rest.dto.rule.DomoduleIdMatchingRuleDto;
import strat.domo.domodule.bootloader.server.rest.dto.rule.DomoduleIdRuleDto;
import strat.domo.domodule.bootloader.server.rest.dto.rule.DomoduleModelMatchingRuleDto;
import strat.domo.domodule.bootloader.server.rest.dto.rule.DomoduleModelRuleDto;
import strat.domo.domodule.bootloader.server.rest.dto.rule.InvalidFirmwareIdRuleDto;
import strat.domo.domodule.bootloader.server.rest.dto.rule.ListRulesResultDto;
import strat.domo.domodule.bootloader.server.rest.dto.rule.MandatoryMetaDataFieldsRuleDto;
import strat.domo.domodule.bootloader.server.rest.dto.rule.RuleAddedEventDto;
import strat.domo.domodule.bootloader.server.rest.dto.rule.RuleDto;
import strat.domo.domodule.bootloader.server.rest.dto.rule.RuleRemovedEventDto;
import strat.domo.domodule.bootloader.server.rest.dto.rule.RuleUpdatedEventDto;
import strat.domo.domodule.bootloader.server.rest.dto.rule.UpdateRuleDto;
import strat.domo.domodule.bootloader.server.rest.dto.rule.ValidFirmwareIdRuleDto;
import strat.domo.domodule.bootloader.server.rest.factory.RuleDtoFactory;

@Path("rules")
@Produces("application/json")
@Consumes("application/json")
public class RulesResources implements RESTResource {

    private static final String DOMODULE_ID_MATCHING_RULE = "domoduleIdMatchingRule";
    private static final String DOMODULE_MODEL_MATCHING_RULE = "domoduleModelMatchingRule";
    private static final String INVALID_FIRMWARE_ID_RULE = "invalidFirmwareIdRule";
    private static final String MANDATORY_META_DATA_FIELDS_RULE = "mandatoryMetaDataFieldsRule";
    private static final String VALID_FIRMWARE_ID_RULE = "validFirmwareIdRule";

    private strat.domo.domodule.bootloader.server.rest.RulesResources delegate;

    private RuledFirmwareSelector ruledFirmwareSelector;

    protected void activate() throws ConfigurationException {
        RuleDtoFactory ruleDtoFactory = new RuleDtoFactory();
        this.delegate = new strat.domo.domodule.bootloader.server.rest.RulesResources(ruledFirmwareSelector,
                new RulesConfigurationManagerJsonFile(ruledFirmwareSelector, ruleDtoFactory), ruleDtoFactory) {

            @Override
            protected <T> void sendEvent(SseBroadcaster eventsBroadcaster, String eventName, T dto) {
                OutboundEvent.Builder eventBuilder = new OutboundEvent.Builder();
                eventBuilder.mediaType(MediaType.APPLICATION_JSON_TYPE);

                if (dto instanceof RuleAddedEventDto) {
                    RuleAddedEventWrapperDto newDto = new RuleAddedEventWrapperDto();
                    newDto.setRulePosition(((RuleAddedEventDto) dto).getRulePosition());
                    newDto.setRule(convertToRuleWrapperDto(((RuleAddedEventDto) dto).getRule()));
                    eventBuilder.data(newDto);
                } else if (dto instanceof RuleRemovedEventDto) {
                    RuleRemovedEventWrapperDto newDto = new RuleRemovedEventWrapperDto();
                    newDto.setRulePosition(((RuleRemovedEventDto) dto).getRulePosition());
                    newDto.setRule(convertToRuleWrapperDto(((RuleRemovedEventDto) dto).getRule()));
                    eventBuilder.data(newDto);
                } else if (dto instanceof RuleUpdatedEventDto) {
                    RuleUpdatedEventWrapperDto newDto = new RuleUpdatedEventWrapperDto();
                    newDto.setRulePosition(((RuleUpdatedEventDto) dto).getRulePosition());
                    newDto.setRule(convertToRuleWrapperDto(((RuleUpdatedEventDto) dto).getRule()));
                    eventBuilder.data(newDto);
                } else {
                    eventBuilder.data(dto);
                }

                eventBuilder.name(eventName);
                eventsBroadcaster.broadcast(eventBuilder.build());
            }

        };
    }

    @GET
    @Path("list")
    public Response listRules() {
        Response response = delegate.listRules();
        ListRulesResultDto listRulesDto = (ListRulesResultDto) response.getEntity();
        ListRulesResultWrapperDto wrapperDto = new ListRulesResultWrapperDto();
        wrapperDto.setRules(convertToRuleWrapperDtos(listRulesDto.getRules()));
        return Response.fromResponse(response).entity(wrapperDto).build();
    }

    @POST
    @Path("create")
    public Response createRule(RuleWrapperDto ruleWrapperDto) {
        RuleDto ruleDto = convertToRuleDto(ruleWrapperDto);
        return delegate.createRule(ruleDto);
    }

    @POST
    @Path("update")
    public Response updateRule(UpdateRuleWrapperDto updateRuleWrapperDto) {
        UpdateRuleDto updateRuleDto = new UpdateRuleDto();
        updateRuleDto.setRulePosition(updateRuleWrapperDto.getRulePosition());
        updateRuleDto.setRule(convertToRuleDto(updateRuleWrapperDto.getRule()));
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

    protected List<RuleWrapperDto> convertToRuleWrapperDtos(List<RuleDto> ruleDtos) {
        List<RuleWrapperDto> result = new ArrayList<>();

        if (CollectionUtils.isNotEmpty(ruleDtos)) {
            for (RuleDto ruleDto : ruleDtos) {
                result.add(convertToRuleWrapperDto(ruleDto));
            }
        }
        return result;
    }

    protected RuleWrapperDto convertToRuleWrapperDto(RuleDto ruleDto) {
        RuleWrapperDto result = new RuleWrapperDto();

        result.setId(ruleDto.getId());
        result.setName(ruleDto.getName());
        result.setEnabled(ruleDto.isEnabled());

        if (ruleDto instanceof DomoduleIdMatchingRuleDto) {
            DomoduleIdMatchingRuleDto src = (DomoduleIdMatchingRuleDto) ruleDto;
            result.setDisabledDomoduleIds(src.getDisabledDomoduleIds());
            result.setRules(convertToIdSubRuleWrapperDto(src.getRules()));
            result.setRuleType(DOMODULE_ID_MATCHING_RULE);
        } else if (ruleDto instanceof DomoduleModelMatchingRuleDto) {
            DomoduleModelMatchingRuleDto src = (DomoduleModelMatchingRuleDto) ruleDto;
            result.setDisabledDomoduleModels(src.getDisabledDomoduleModels());
            result.setRules(convertToModelSubRuleWrapperDto(src.getRules()));
            result.setRuleType(DOMODULE_MODEL_MATCHING_RULE);
        } else if (ruleDto instanceof InvalidFirmwareIdRuleDto) {
            result.setRuleType(INVALID_FIRMWARE_ID_RULE);
        } else if (ruleDto instanceof MandatoryMetaDataFieldsRuleDto) {
            result.setRuleType(MANDATORY_META_DATA_FIELDS_RULE);
        } else if (ruleDto instanceof ValidFirmwareIdRuleDto) {
            result.setRuleType(VALID_FIRMWARE_ID_RULE);
        }

        return result;
    }

    protected List<RuleDto> convertToRuleDto(List<RuleWrapperDto> ruleWrapperDtos) {
        List<RuleDto> result = new ArrayList<>();

        if (CollectionUtils.isNotEmpty(ruleWrapperDtos)) {
            for (RuleWrapperDto ruleWrapperDto : ruleWrapperDtos) {
                result.add(convertToRuleDto(ruleWrapperDto));
            }
        }
        return result;
    }

    protected RuleDto convertToRuleDto(RuleWrapperDto ruleWrapperDto) {
        RuleDto result = null;

        if (DOMODULE_ID_MATCHING_RULE.equalsIgnoreCase(ruleWrapperDto.getRuleType())) {
            DomoduleIdMatchingRuleDto dto = new DomoduleIdMatchingRuleDto();
            dto.setDisabledDomoduleIds(ruleWrapperDto.getDisabledDomoduleIds());
            dto.setRules(convertToIdSubRuleDto(ruleWrapperDto.getRules()));
            result = dto;
        } else if (DOMODULE_MODEL_MATCHING_RULE.equalsIgnoreCase(ruleWrapperDto.getRuleType())) {
            DomoduleModelMatchingRuleDto dto = new DomoduleModelMatchingRuleDto();
            dto.setDisabledDomoduleModels(ruleWrapperDto.getDisabledDomoduleModels());
            dto.setRules(convertToModelSubRuleDto(ruleWrapperDto.getRules()));
            result = dto;
        } else if (INVALID_FIRMWARE_ID_RULE.equalsIgnoreCase(ruleWrapperDto.getRuleType())) {
            result = new InvalidFirmwareIdRuleDto();
        } else if (MANDATORY_META_DATA_FIELDS_RULE.equalsIgnoreCase(ruleWrapperDto.getRuleType())) {
            result = new MandatoryMetaDataFieldsRuleDto();
        } else if (VALID_FIRMWARE_ID_RULE.equalsIgnoreCase(ruleWrapperDto.getRuleType())) {
            result = new ValidFirmwareIdRuleDto();
        } else {
            throw new InvalidParameterException("The ruleType " + ruleWrapperDto.getRuleType() + " is not supportted");
        }

        result.setId(ruleWrapperDto.getId());
        result.setName(ruleWrapperDto.getName());
        result.setEnabled(ruleWrapperDto.isEnabled());

        return result;
    }

    protected List<ModelIdSubRuleWrapperDto> convertToModelSubRuleWrapperDto(List<DomoduleModelRuleDto> subRuleDtos) {
        List<ModelIdSubRuleWrapperDto> result = new ArrayList<>();

        if (CollectionUtils.isNotEmpty(subRuleDtos)) {
            for (DomoduleModelRuleDto domoduleModelRuleDto : subRuleDtos) {
                ModelIdSubRuleWrapperDto wrapperDto = new ModelIdSubRuleWrapperDto();

                wrapperDto.setDomoduleModel(domoduleModelRuleDto.getDomoduleModel());
                wrapperDto.setFirmwareIdHex(domoduleModelRuleDto.getFirmwareIdHex());
                wrapperDto.setFirmwareVersion(domoduleModelRuleDto.getFirmwareVersion());
                wrapperDto.setForceCompatibility(domoduleModelRuleDto.getForceCompatibility());
                wrapperDto.setForceUpdate(domoduleModelRuleDto.getForceUpdate());

                result.add(wrapperDto);
            }
        }

        return result;
    }

    protected List<ModelIdSubRuleWrapperDto> convertToIdSubRuleWrapperDto(List<DomoduleIdRuleDto> subRuleDtos) {
        List<ModelIdSubRuleWrapperDto> result = new ArrayList<>();

        if (CollectionUtils.isNotEmpty(subRuleDtos)) {
            for (DomoduleIdRuleDto domoduleIdRuleDto : subRuleDtos) {
                ModelIdSubRuleWrapperDto wrapperDto = new ModelIdSubRuleWrapperDto();

                wrapperDto.setDomoduleId(domoduleIdRuleDto.getDomoduleId());
                wrapperDto.setFirmwareIdHex(domoduleIdRuleDto.getFirmwareIdHex());
                wrapperDto.setFirmwareVersion(domoduleIdRuleDto.getFirmwareVersion());
                wrapperDto.setForceCompatibility(domoduleIdRuleDto.getForceCompatibility());
                wrapperDto.setForceUpdate(domoduleIdRuleDto.getForceUpdate());

                result.add(wrapperDto);
            }
        }

        return result;
    }

    protected List<DomoduleIdRuleDto> convertToIdSubRuleDto(List<ModelIdSubRuleWrapperDto> subRuleWrapperDtos) {
        List<DomoduleIdRuleDto> result = new ArrayList<>();

        if (CollectionUtils.isNotEmpty(subRuleWrapperDtos)) {
            for (ModelIdSubRuleWrapperDto modelIdSubRuleWrapperDto : subRuleWrapperDtos) {
                DomoduleIdRuleDto dto = new DomoduleIdRuleDto();

                dto.setDomoduleId(modelIdSubRuleWrapperDto.getDomoduleId());
                dto.setFirmwareIdHex(modelIdSubRuleWrapperDto.getFirmwareIdHex());
                dto.setFirmwareVersion(modelIdSubRuleWrapperDto.getFirmwareVersion());
                dto.setForceCompatibility(modelIdSubRuleWrapperDto.getForceCompatibility());
                dto.setForceUpdate(modelIdSubRuleWrapperDto.getForceUpdate());

                result.add(dto);
            }
        }

        return result;
    }

    protected List<DomoduleModelRuleDto> convertToModelSubRuleDto(List<ModelIdSubRuleWrapperDto> subRuleWrapperDtos) {
        List<DomoduleModelRuleDto> result = new ArrayList<>();

        if (CollectionUtils.isNotEmpty(subRuleWrapperDtos)) {
            for (ModelIdSubRuleWrapperDto modelIdSubRuleWrapperDto : subRuleWrapperDtos) {
                DomoduleModelRuleDto dto = new DomoduleModelRuleDto();

                dto.setDomoduleModel(modelIdSubRuleWrapperDto.getDomoduleModel());
                dto.setFirmwareIdHex(modelIdSubRuleWrapperDto.getFirmwareIdHex());
                dto.setFirmwareVersion(modelIdSubRuleWrapperDto.getFirmwareVersion());
                dto.setForceCompatibility(modelIdSubRuleWrapperDto.getForceCompatibility());
                dto.setForceUpdate(modelIdSubRuleWrapperDto.getForceUpdate());

                result.add(dto);
            }
        }

        return result;
    }

}
