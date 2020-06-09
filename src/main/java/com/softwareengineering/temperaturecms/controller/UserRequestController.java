package com.softwareengineering.temperaturecms.controller;

import com.softwareengineering.temperaturecms.dto.ChangeTargetTemperatureDto;
import com.softwareengineering.temperaturecms.enums.ResponseEnum;
import com.softwareengineering.temperaturecms.pojo.RoomStatus;
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

import java.util.HashMap;
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
                                            @RequestParam Double currentTemperature){

        DefaultSettingVo defaultSettingVo = roomStatusService.ArrangeService(roomId, currentTemperature);


        if(defaultSettingVo != null){
            return WebResultUtil.buildResult(ResponseVo.success(defaultSettingVo), HttpStatus.OK);
        }
        else{
            return WebResultUtil.buildResult(ResponseVo.error(ResponseEnum.AC_ON_FAIL),HttpStatus.OK);
        }
    }

    @GetMapping("/service")
    @ApiOperation("获取房间状态")
    public ResponseEntity<String> updateStatus(Integer id){
        RoomStatus roomStatusFromRedis = roomStatusService.getRoomStatusFromRedis(id);

        return WebResultUtil.buildResult(ResponseVo.success(roomStatusFromRedis),HttpStatus.OK);
    }

    @PostMapping("/set")
    @ApiOperation("设置房间状态")
    public ResponseEntity<String> changeTargetTemperature(@RequestParam Integer id,
                                                          @RequestParam Double targetTemperature,
                                                          @RequestParam Double fanSpeed){

        ChangeTargetTemperatureDto changeTargetTemperatureDto = new ChangeTargetTemperatureDto(id,targetTemperature,fanSpeed);
        Boolean requestStatus = roomStatusService.RequestTemperature(changeTargetTemperatureDto);


        if(requestStatus){
            return WebResultUtil.buildResult(ResponseVo.successByMsg(), HttpStatus.OK);
        }
        else{
            return WebResultUtil.buildResult(ResponseVo.error(ResponseEnum.CHANGE_TARGET_TEMPERATURE_FAIL),HttpStatus.OK);
        }
    }

    @PutMapping("/service")
    @ApiOperation("关机")
    public ResponseEntity<String> requestOff(@RequestParam Integer id){

        Boolean requestStatus = roomStatusService.WriteBack(id);

        if(requestStatus){
            return WebResultUtil.buildResult(ResponseVo.successByMsg(), HttpStatus.OK);
        }
        else{
            return WebResultUtil.buildResult(ResponseVo.error(ResponseEnum.AC_OFF_FAIL),HttpStatus.OK);
        }
    }

    @GetMapping("/fee")
    @ApiOperation("获取费用")
    public ResponseEntity<String> getFee(Integer id){
        Double fee = roomStatusService.getFee(id);

        Map<String,Object> res = new HashMap<>();
        res.put("id",id);
        res.put("fee",fee);

        return WebResultUtil.buildResult(ResponseVo.success(res),HttpStatus.OK);
    }

    @PostMapping("/fee")
    @ApiOperation("暂停计费")
    public ResponseEntity<String> pauseFee(Integer id){
        roomStatusService.pauseFee(id);

        return WebResultUtil.buildResult(ResponseVo.successByMsg(),HttpStatus.OK);
    }

    @PutMapping("/fee")
    @ApiOperation("继续计费")
    public ResponseEntity<String> continueFee(Integer id){
        Boolean aBoolean = roomStatusService.continueFee(id);
        if(aBoolean){
            return WebResultUtil.buildResult(ResponseVo.successByMsg(),HttpStatus.OK);
        }
        else{
            return WebResultUtil.buildResult(ResponseVo.error(ResponseEnum.CONTINUE_FEE_FAIL),HttpStatus.OK);
        }
    }
}
