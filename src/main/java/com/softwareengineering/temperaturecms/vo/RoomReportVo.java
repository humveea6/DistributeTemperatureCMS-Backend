package com.softwareengineering.temperaturecms.vo;

import java.util.List;

/**
 * @author LingChen <lingchen@kuaishou.com>
 * Created on 2020-06-01
 */
public class RoomReportVo {

    private String reportTime;

    private Long totalRecordCount;

    private List<RoomStatusVo> roomRecordList;

    public String getReportTime() {
        return reportTime;
    }

    public void setReportTime(String reportTime) {
        this.reportTime = reportTime;
    }

    public Long getTotalRecordCount() {
        return totalRecordCount;
    }

    public void setTotalRecordCount(Long totalRecordCount) {
        this.totalRecordCount = totalRecordCount;
    }

    public List<RoomStatusVo> getRoomRecordList() {
        return roomRecordList;
    }

    public void setRoomRecordList(List<RoomStatusVo> roomRecordList) {
        this.roomRecordList = roomRecordList;
    }
}
