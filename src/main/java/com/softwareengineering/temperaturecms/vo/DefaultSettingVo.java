package com.softwareengineering.temperaturecms.vo;

/**
 * @author LingChen <lingchen@kuaishou.com>
 * Created on 2020-06-10
 */
public class DefaultSettingVo {

    private Integer id;

    private Double highestTemperature;

    private Double lowestTemperature;

    private Double defaultFanSpeed;

    private Double defaultTargetTemperature;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getHighestTemperature() {
        return highestTemperature;
    }

    public void setHighestTemperature(Double highestTemperature) {
        this.highestTemperature = highestTemperature;
    }

    public Double getLowestTemperature() {
        return lowestTemperature;
    }

    public void setLowestTemperature(Double lowestTemperature) {
        this.lowestTemperature = lowestTemperature;
    }

    public Double getDefaultFanSpeed() {
        return defaultFanSpeed;
    }

    public void setDefaultFanSpeed(Double defaultFanSpeed) {
        this.defaultFanSpeed = defaultFanSpeed;
    }

    public Double getDefaultTargetTemperature() {
        return defaultTargetTemperature;
    }

    public void setDefaultTargetTemperature(Double defaultTargetTemperature) {
        this.defaultTargetTemperature = defaultTargetTemperature;
    }
}
