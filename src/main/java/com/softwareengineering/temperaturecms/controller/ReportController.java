package com.softwareengineering.temperaturecms.controller;

import com.softwareengineering.temperaturecms.pojo.RoomStatus;
import com.softwareengineering.temperaturecms.service.RoomStatusService;
import com.softwareengineering.temperaturecms.utils.WebResultUtil;
import com.softwareengineering.temperaturecms.vo.ResponseVo;
import com.softwareengineering.temperaturecms.vo.RoomReportVo;
import com.softwareengineering.temperaturecms.vo.RoomStatusVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author LingChen <lingchen@kuaishou.com>
 * Created on 2020-06-01
 */
@RestController
@RequestMapping("/query")
@Api(tags = "报表相关")
public class ReportController {

    @Autowired
    private RoomStatusService roomStatusService;

    @GetMapping("/report")
    @ApiOperation("获取房间报表")
    public ResponseEntity<String> queryReport(@RequestParam  Long roomId){

        RoomReportVo roomReportVo= new RoomReportVo();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        roomReportVo.setReportTime(simpleDateFormat.format(System.currentTimeMillis()));

        roomReportVo.setTotalRecordCount(roomStatusService.countByRoomId(roomId));

        List<RoomStatus> roomStatusList = roomStatusService.getRoomStatusList(roomId);

        List<RoomStatusVo> roomStatusVoList = new ArrayList<>();
        for (RoomStatus roomStatus : roomStatusList) {
            RoomStatusVo roomStatusVo = new RoomStatusVo();
            BeanUtils.copyProperties(roomStatus,roomStatusVo);
            roomStatusVo.setStartTime(simpleDateFormat.format(roomStatus.getStartUp()));
            roomStatusVo.setEndTime(simpleDateFormat.format(roomStatus.getEndTime()));
            roomStatusVoList.add(roomStatusVo);
        }

        roomReportVo.setRoomRecordList(roomStatusVoList);

        return WebResultUtil.buildResult(ResponseVo.success(roomReportVo), HttpStatus.OK);
    }
}
