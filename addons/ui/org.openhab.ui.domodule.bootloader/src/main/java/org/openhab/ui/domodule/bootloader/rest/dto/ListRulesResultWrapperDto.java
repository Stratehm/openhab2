package org.openhab.ui.domodule.bootloader.rest.dto;

/**
 * A wrapper DTO for rules list that act as a bridge between GSON and Jakcson.
 *
 * @author Antoine Besnard
 *
 */
import java.util.List;

public class ListRulesResultWrapperDto {

    private List<RuleWrapperDto> rules;

    public List<RuleWrapperDto> getRules() {
        return rules;
    }

    public void setRules(List<RuleWrapperDto> rules) {
        this.rules = rules;
    }

}
