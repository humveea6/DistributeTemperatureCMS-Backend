package com.softwareengineering.temperaturecms.dto;

/**
 * @author LingChen <lingchen@kuaishou.com>
 * Created on 2020-05-31
 */
public class ChangeTargetTemperatureDto {

    private Integer id;

    private Double targetTemperature;

    private Double fanSpeed;

    public Double getFanSpeed() {
        return fanSpeed;
    }

    public void setFanSpeed(Double fanSpeed) {
        this.fanSpeed = fanSpeed;
    }

    public ChangeTargetTemperatureDto(Integer id, Double targetTemperature, Double fanSpeed) {
        this.id = id;
        this.targetTemperature = targetTemperature;
        this.fanSpeed = fanSpeed;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getTargetTemperature() {
        return targetTemperature;
    }

    public void setTargetTemperature(Double targetTemperature) {
        this.targetTemperature = targetTemperature;
    }
}
