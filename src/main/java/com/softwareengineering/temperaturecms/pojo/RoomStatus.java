package com.softwareengineering.temperaturecms.pojo;

public class RoomStatus {
    private Integer id;

    private Long roomId;

    private Double currentTemperature;

    private Double targetTemperature;

    private Double fansSpeed;

    private Double fareRate;

    private Long startUp;

    private Integer mode;

    private Long endTime;

    private Integer state;

    private Integer onOffTime;

    private Integer dispatchTimes;

    private Integer rdrNum;

    private Integer changeTempTime;

    private Integer changeSpeedTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public Double getCurrentTemperature() {
        return currentTemperature;
    }

    public void setCurrentTemperature(Double currentTemperature) {
        this.currentTemperature = currentTemperature;
    }

    public Double getTargetTemperature() {
        return targetTemperature;
    }

    public void setTargetTemperature(Double targetTemperature) {
        this.targetTemperature = targetTemperature;
    }

    public Double getFansSpeed() {
        return fansSpeed;
    }

    public void setFansSpeed(Double fansSpeed) {
        this.fansSpeed = fansSpeed;
    }

    public Double getFareRate() {
        return fareRate;
    }

    public void setFareRate(Double fareRate) {
        this.fareRate = fareRate;
    }

    public Long getStartUp() {
        return startUp;
    }

    public void setStartUp(Long startUp) {
        this.startUp = startUp;
    }

    public Integer getMode() {
        return mode;
    }

    public void setMode(Integer mode) {
        this.mode = mode;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getOnOffTime() {
        return onOffTime;
    }

    public void setOnOffTime(Integer onOffTime) {
        this.onOffTime = onOffTime;
    }

    public Integer getDispatchTimes() {
        return dispatchTimes;
    }

    public void setDispatchTimes(Integer dispatchTimes) {
        this.dispatchTimes = dispatchTimes;
    }

    public Integer getRdrNum() {
        return rdrNum;
    }

    public void setRdrNum(Integer rdrNum) {
        this.rdrNum = rdrNum;
    }

    public Integer getChangeTempTime() {
        return changeTempTime;
    }

    public void setChangeTempTime(Integer changeTempTime) {
        this.changeTempTime = changeTempTime;
    }

    public Integer getChangeSpeedTime() {
        return changeSpeedTime;
    }

    public void setChangeSpeedTime(Integer changeSpeedTime) {
        this.changeSpeedTime = changeSpeedTime;
    }
}