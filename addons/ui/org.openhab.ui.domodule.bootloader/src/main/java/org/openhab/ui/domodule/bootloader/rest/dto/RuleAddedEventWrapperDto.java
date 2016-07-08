package org.openhab.ui.domodule.bootloader.rest.dto;

/**
 * A wrapper DTO for rule added event that act as a bridge between GSON and Jakcson.
 *
 * @author Antoine Besnard
 *
 */
public class RuleAddedEventWrapperDto {

    private RuleWrapperDto rule;
    private Integer rulePosition;

    public RuleWrapperDto getRule() {
        return rule;
    }

    public void setRule(RuleWrapperDto rule) {
        this.rule = rule;
    }

    public Integer getRulePosition() {
        return rulePosition;
    }

    public void setRulePosition(Integer rulePosition) {
        this.rulePosition = rulePosition;
    }

}
