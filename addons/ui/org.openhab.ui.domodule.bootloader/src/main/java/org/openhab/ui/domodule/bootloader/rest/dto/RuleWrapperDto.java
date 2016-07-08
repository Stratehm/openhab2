package org.openhab.ui.domodule.bootloader.rest.dto;

import java.util.List;

/**
 * A wrapper DTO for rules that act as a bridge between GSON and Jakcson.
 *
 * @author Antoine Besnard
 *
 */
public class RuleWrapperDto {

    private String ruleType;
    private Integer id;
    private boolean enabled;
    private String name;

    // Only for domoduleModelMatchingRule
    private List<String> disabledDomoduleModels;

    // Only for domoduleModelMatchingRule
    private List<String> disabledDomoduleIds;

    // Only for domoduleModelMatchingRule and domoduleModelMatchingRule
    private List<ModelIdSubRuleWrapperDto> rules;

    public String getRuleType() {
        return ruleType;
    }

    public void setRuleType(String ruleType) {
        this.ruleType = ruleType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean isEnabled) {
        this.enabled = isEnabled;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getDisabledDomoduleModels() {
        return disabledDomoduleModels;
    }

    public void setDisabledDomoduleModels(List<String> disabledDomoduleModels) {
        this.disabledDomoduleModels = disabledDomoduleModels;
    }

    public List<String> getDisabledDomoduleIds() {
        return disabledDomoduleIds;
    }

    public void setDisabledDomoduleIds(List<String> disabledDomoduleIds) {
        this.disabledDomoduleIds = disabledDomoduleIds;
    }

    public List<ModelIdSubRuleWrapperDto> getRules() {
        return rules;
    }

    public void setRules(List<ModelIdSubRuleWrapperDto> rules) {
        this.rules = rules;
    }

}
