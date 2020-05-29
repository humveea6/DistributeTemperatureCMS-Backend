package com.softwareengineering.temperaturecms.pojo;

public class RoomStatus {
    private Integer id;

    private Long roomId;

    private Double currentTemperature;

    private Double targetTemperature;

    private Double fansSpeed;

    private Double fareRate;

    private Long startUp;

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
}