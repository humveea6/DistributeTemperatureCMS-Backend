package com.softwareengineering.temperaturecms.vo;

import com.softwareengineering.temperaturecms.pojo.RoomStatus;

/**
 * @author LingChen <lingchen@kuaishou.com>
 * Created on 2020-06-01
 */
public class RoomDetailListVo {

    private String requestTime;

    private Double fee;

    private RoomStatus roomStatus;

    public String getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(String requestTime) {
        this.requestTime = requestTime;
    }

    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }

    public RoomStatus getRoomStatus() {
        return roomStatus;
    }

    public void setRoomStatus(RoomStatus roomStatus) {
        this.roomStatus = roomStatus;
    }
}
