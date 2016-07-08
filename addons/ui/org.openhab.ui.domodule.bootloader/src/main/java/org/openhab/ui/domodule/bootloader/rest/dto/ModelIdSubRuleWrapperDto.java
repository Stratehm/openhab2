package org.openhab.ui.domodule.bootloader.rest.dto;

/**
 * A wrapper DTO for subrules that act as a bridge between GSON and Jakcson.
 *
 * @author Antoine Besnard
 *
 */
public class ModelIdSubRuleWrapperDto {

    // The domodule ID to match
    private String domoduleId;

    // The domodule Model to match
    private String domoduleModel;

    // The firmware details to select the firmware to send to this domodule.
    private String firmwareIdHex;
    private Integer firmwareVersion;
    private Boolean forceCompatibility;
    private Boolean forceUpdate;

    public String getDomoduleId() {
        return domoduleId;
    }

    public void setDomoduleId(String domoduleId) {
        this.domoduleId = domoduleId;
    }

    public String getDomoduleModel() {
        return domoduleModel;
    }

    public void setDomoduleModel(String domoduleModel) {
        this.domoduleModel = domoduleModel;
    }

    public String getFirmwareIdHex() {
        return firmwareIdHex;
    }

    public void setFirmwareIdHex(String firmwareIdHex) {
        this.firmwareIdHex = firmwareIdHex;
    }

    public Integer getFirmwareVersion() {
        return firmwareVersion;
    }

    public void setFirmwareVersion(Integer firmwareVersion) {
        this.firmwareVersion = firmwareVersion;
    }

    public Boolean getForceCompatibility() {
        return forceCompatibility;
    }

    public void setForceCompatibility(Boolean forceCompatibility) {
        this.forceCompatibility = forceCompatibility;
    }

    public Boolean getForceUpdate() {
        return forceUpdate;
    }

    public void setForceUpdate(Boolean forceUpdate) {
        this.forceUpdate = forceUpdate;
    }

}
