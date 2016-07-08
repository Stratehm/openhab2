package org.openhab.ui.domodule.bootloader.rest.config;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.codec.DecoderException;
import org.eclipse.smarthome.config.core.ConfigConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import strat.domo.domodule.api.exception.ConfigurationException;
import strat.domo.domodule.bootloader.api.exception.RuleAlreadyExistsException;
import strat.domo.domodule.bootloader.api.firmware.selector.RuledFirmwareSelector;
import strat.domo.domodule.bootloader.api.firmware.selector.rule.RulesConfigurationManager;
import strat.domo.domodule.bootloader.api.firmware.selector.rule.SelectionRule;
import strat.domo.domodule.bootloader.server.rest.dto.rule.RuleDto;
import strat.domo.domodule.bootloader.server.rest.factory.RuleDtoFactory;

/**
 * A {@link RulesConfigurationManager} implementation that read/store the
 * configuration from/to a JSON configuration file.
 *
 * The configuration file has to be set before the load/save methods are called.
 *
 * @author Stratehm
 *
 */
public class RulesConfigurationManagerJsonFile implements RulesConfigurationManager {

    private final Logger logger = LoggerFactory.getLogger(RulesConfigurationManagerJsonFile.class);

    private RuledFirmwareSelector ruledFirmwareSelector;

    private ObjectMapper jsonParser;

    private RuleDtoFactory ruleDtoFactory;

    private File configurationFile;

    public RulesConfigurationManagerJsonFile(RuledFirmwareSelector ruledFirmwareSelector, RuleDtoFactory ruleDtoFactory)
            throws ConfigurationException {
        this.ruledFirmwareSelector = ruledFirmwareSelector;
        this.ruleDtoFactory = ruleDtoFactory;
        this.jsonParser = new ObjectMapper();
        jsonParser.configure(SerializationFeature.INDENT_OUTPUT, true);
        jsonParser.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        jsonParser.configure(com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_COMMENTS, true);
        jsonParser.configure(com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        jsonParser.configure(com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);

        String userDataFolder = ConfigConstants.getUserDataFolder();
        configurationFile = new File(userDataFolder + "/domodule/bootloader/rules.json");

        loadRules();
    }

    @Override
    public void loadRules() throws ConfigurationException {
        if (configurationFile != null && configurationFile.exists()) {
            try {
                logger.info("Loading rules from configuration file {}", configurationFile.getAbsolutePath());
                RulesConfigurationDto configuration = jsonParser.readValue(configurationFile,
                        RulesConfigurationDto.class);
                List<SelectionRule> rules = ruleDtoFactory.convertDtosToSelectionRules(configuration.getRules());
                ruledFirmwareSelector.removeAllRules();
                for (SelectionRule selectionRule : rules) {
                    ruledFirmwareSelector.appendRule(selectionRule);
                }

            } catch (IOException e) {
                throw new ConfigurationException(e);
            } catch (RuleAlreadyExistsException e) {
                throw new ConfigurationException("Rules ID duplicated detected.", e);
            } catch (DecoderException e) {
                throw new ConfigurationException("Failed to decode an Hexa-Decimal value", e);
            }
        } else {
            logger.warn("The rules configuration file {} does not exist. No rule loaded.",
                    configurationFile != null ? configurationFile.getAbsolutePath() : "Undefined");
        }
    }

    @Override
    public void saveRules() throws ConfigurationException {
        if (configurationFile != null) {
            try {
                logger.info("Saving rules to configuration file {}", configurationFile.getAbsolutePath());
                RulesConfigurationDto configuration = new RulesConfigurationDto();
                configuration.setRules(ruleDtoFactory.convertRulesToDtos(ruledFirmwareSelector.getRules()));
                jsonParser.writeValue(configurationFile, configuration);
            } catch (IOException e) {
                throw new ConfigurationException(e);
            }
        } else {
            logger.warn("The rules configuration file is not defined. Rules are not saved");
        }
    }

    public File getConfigurationFile() {
        return configurationFile;
    }

    public void setConfigurationFile(File configurationFile) {
        this.configurationFile = configurationFile;
    }

    @Override
    public RuledFirmwareSelector getRuledFirmwareSelector() {
        return ruledFirmwareSelector;
    }

    /**
     * A container used for the rules configuration.
     *
     * @author Stratehm
     *
     */
    private static class RulesConfigurationDto {

        private List<RuleDto> rules;

        public List<RuleDto> getRules() {
            return rules;
        }

        public void setRules(List<RuleDto> rules) {
            this.rules = rules;
        }

    }

}
