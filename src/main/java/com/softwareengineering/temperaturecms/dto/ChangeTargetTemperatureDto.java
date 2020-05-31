package com.softwareengineering.temperaturecms.dto;

/**
 * @author LingChen <lingchen@kuaishou.com>
 * Created on 2020-05-31
 */
public class ChangeTargetTemperatureDto {

    private Integer id;

    private Double targetTemperature;

    public ChangeTargetTemperatureDto(Integer id, Double targetTemperature) {
        this.id = id;
        this.targetTemperature = targetTemperature;
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
