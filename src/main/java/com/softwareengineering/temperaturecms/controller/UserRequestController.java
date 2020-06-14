package com.softwareengineering.temperaturecms.controller;

import com.softwareengineering.temperaturecms.dto.ChangeTargetTemperatureDto;
import com.softwareengineering.temperaturecms.enums.ResponseEnum;
import com.softwareengineering.temperaturecms.enums.StateEnum;
import com.softwareengineering.temperaturecms.pojo.RoomStatus;
import com.softwareengineering.temperaturecms.service.Impl.RoomStatusServiceImpl;
import com.softwareengineering.temperaturecms.service.RoomStatusService;
import com.softwareengineering.temperaturecms.utils.WebResultUtil;
import com.softwareengineering.temperaturecms.vo.DefaultSettingVo;
import com.softwareengineering.temperaturecms.vo.ResponseVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author LingChen <lingchen@kuaishou.com>
 * Created on 2020-05-29
 */
@RestController
@Slf4j
@RequestMapping("/room")
@Api(tags = "客户模块")
public class UserRequestController {

    @Autowired
    private RoomStatusService roomStatusService;


    @PostMapping("/initial")
    @ApiOperation("开机，记得保存返回的记录id")
    public ResponseEntity<String> requestOn(@RequestParam Long roomId,
                                            @RequestParam Double currentTemperature) {

        DefaultSettingVo defaultSettingVo = roomStatusService.ArrangeService(roomId, currentTemperature);

        if (defaultSettingVo != null) {
            return WebResultUtil.buildResult(ResponseVo.success(defaultSettingVo), HttpStatus.OK);
        } else {
            return WebResultUtil.buildResult(ResponseVo.error(ResponseEnum.AC_ON_FAIL), HttpStatus.OK);
        }
    }

    @ApiOperation("房间开机")
    @PostMapping("/service")
    public ResponseEntity<String> startService(@RequestParam Integer id) {

        RoomStatus roomStatus = roomStatusService.getRoomStatusFromRedis(id);

        roomStatusService.addJob(roomStatus);

        return WebResultUtil.buildResult(ResponseVo.successByMsg(), HttpStatus.OK);
    }


    @PostMapping("/temp")
    @ApiOperation("设置房间温度")
    public ResponseEntity<String> changeTargetTemperature(@RequestParam Integer id,
                                                          @RequestParam Double targetTemperature) {
        RoomStatus roomStatus = roomStatusService.getRoomStatusFromRedis(id);
        roomStatus.setTargetTemperature(targetTemperature);

        // 若当前在服务状态
        if (roomStatus.getState() == 0) {
            roomStatus.setChangeTempTime(roomStatus.getChangeTempTime() + 1);
            roomStatusService.updateRoomStatusInRedis(id, roomStatus);// 更新redis
            roomStatusService.modify(roomStatus);
        }

        return WebResultUtil.buildResult(ResponseVo.successByMsg(), HttpStatus.OK);

    }

    @PostMapping("/fan")
    @ApiOperation("设置房间风速")
    public ResponseEntity<String> changeFanSpeed(@RequestParam Integer id,
                                                 @RequestParam Double fanSpeed) {

        RoomStatus roomStatus = roomStatusService.getRoomStatusFromRedis(id);
        roomStatus.setFansSpeed(fanSpeed);

        // 若当前在服务状态
        if (roomStatus.getState() == 0) {
            roomStatus.setChangeSpeedTime(roomStatus.getChangeSpeedTime() + 1); //风速变更次数+1
            roomStatusService.updateRoomStatusInRedis(id, roomStatus);// 更新redis
            roomStatusService.modify(roomStatus);
        }

        return WebResultUtil.buildResult(ResponseVo.successByMsg(), HttpStatus.OK);
    }

    @PutMapping("/service")
    @ApiOperation("关机")
    public ResponseEntity<String> requestOff(@RequestParam Integer id) {

        Boolean requestStatus = roomStatusService.WriteBack(id);

        RoomStatus roomStatus = roomStatusService.getRoomStatusFromRedis(id);

        roomStatus.setOnOffTime(roomStatus.getOnOffTime() + 1); // 开关次数+1

        roomStatusService.createRDR(id); //关机创建详单

        roomStatusService.endJob(roomStatus);

        roomStatus.setState(StateEnum.SHUTDOWN.getState()); //设置状态
        roomStatusService.updateRoomStatusInRedis(id, roomStatus);


        if (requestStatus) {
            return WebResultUtil.buildResult(ResponseVo.successByMsg(), HttpStatus.OK);
        } else {
            return WebResultUtil.buildResult(ResponseVo.error(ResponseEnum.AC_OFF_FAIL), HttpStatus.OK);
        }
    }

    @GetMapping("/fee")
    @ApiOperation("获取费用")
    public ResponseEntity<String> getFee(@RequestParam Integer id,
                                         @RequestParam Double currentTemperature,
                                         @RequestParam Double changeTemperature) {

        RoomStatus roomStatus = roomStatusService.getRoomStatusFromRedis(id);
        roomStatus.setCurrentTemperature(currentTemperature);


        if (currentTemperature.equals(roomStatus.getTargetTemperature()) && changeTemperature == 0) // 达到目标温度
        {

            // 达到目标温度
            roomStatus.setState(StateEnum.FREE.getState());
        } else {
            if (roomStatus.getState() == 0) {
                //todo:服务时长+1
                roomStatus.setDuration(roomStatus.getDuration() + 1000);
                //todo:计费
                roomStatus.setFee(roomStatus.getFee() + changeTemperature);// 1元/度，在基础上加上温度值即可

                roomStatusService.modify(roomStatus);

            }
        }
        roomStatusService.updateRoomStatusInRedis(id, roomStatus);

        Map<String, Object> res = new HashMap<>();
        res.put("id", id);
        res.put("fee", roomStatus.getFee());
        res.put("roomState", roomStatus.getState());

        return WebResultUtil.buildResult(ResponseVo.success(res), HttpStatus.OK);
    }

    @PutMapping("/exit")
    public ResponseEntity<String> checkOut(@RequestParam Integer id) {
        return WebResultUtil.buildResult(ResponseVo.successByMsg(), HttpStatus.OK);
    }
}
