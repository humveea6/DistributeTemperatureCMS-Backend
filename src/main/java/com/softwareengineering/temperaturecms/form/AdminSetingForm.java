package com.softwareengineering.temperaturecms.form;

/**
 * author LingChen <lingchenkuaishou.com>
 * Created on 2020-06-10
 */
public class AdminSetingForm {

    private Integer mode;

    private Double targetTemperature;

    private Double lowestTemperature;

    private Double highestTemperature;

    private Double fanSpeed;

    private Double feeRateLow;

    private Double feeRateMid;

    private Double feeRateHigh;

    public Integer getMode() {
        return mode;
    }

    public void setMode(Integer mode) {
        this.mode = mode;
    }

    public Double getTargetTemperature() {
        return targetTemperature;
    }

    public void setTargetTemperature(Double targetTemperature) {
        this.targetTemperature = targetTemperature;
    }

    public Double getLowestTemperature() {
        return lowestTemperature;
    }

    public void setLowestTemperature(Double lowestTemperature) {
        this.lowestTemperature = lowestTemperature;
    }

    public Double getHighestTemperature() {
        return highestTemperature;
    }

    public void setHighestTemperature(Double highestTemperature) {
        this.highestTemperature = highestTemperature;
    }

    public Double getFanSpeed() {
        return fanSpeed;
    }

    public void setFanSpeed(Double fanSpeed) {
        this.fanSpeed = fanSpeed;
    }

    public Double getFeeRateLow() {
        return feeRateLow;
    }

    public void setFeeRateLow(Double feeRateLow) {
        this.feeRateLow = feeRateLow;
    }

    public Double getFeeRateMid() {
        return feeRateMid;
    }

    public void setFeeRateMid(Double feeRateMid) {
        this.feeRateMid = feeRateMid;
    }

    public Double getFeeRateHigh() {
        return feeRateHigh;
    }

    public void setFeeRateHigh(Double feeRateHigh) {
        this.feeRateHigh = feeRateHigh;
    }
}
